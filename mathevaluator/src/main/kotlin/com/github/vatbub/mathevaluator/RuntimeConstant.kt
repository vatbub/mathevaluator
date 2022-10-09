package com.github.vatbub.mathevaluator

data class RuntimeConstant(val name: String) : MathLiteral() {
    val expression: MathExpression
        get() = RuntimeConstantRegistry.values[name]!!
    val value: MathNumber
        get() = expression.evaluate()
    override val formulaRepresentation: String
        get() = name

    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean {
        return previousLiteral is MathNumber || previousLiteral is Constant || previousLiteral is RuntimeConstant || previousLiteral is MathFunction || previousLiteral is MathExpression
    }
}

object RuntimeConstantRegistry {
    internal val values = mutableMapOf<String, MathExpression>()

    @JvmStatic
    fun registerRuntimeConstant(name: String, expression: MathExpression) {
        deregisterRuntimeConstant(name)
        values[name] = expression
    }

    @JvmStatic
    fun deregisterRuntimeConstant(runtimeConstant: RuntimeConstant): Boolean {
        return deregisterRuntimeConstant(runtimeConstant.name)
    }

    @JvmStatic
    fun deregisterRuntimeConstant(constantName: String): Boolean {
        return values.remove(constantName) != null
    }
}
