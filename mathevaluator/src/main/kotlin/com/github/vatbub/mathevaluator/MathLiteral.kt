package com.github.vatbub.mathevaluator

import com.github.vatbub.mathevaluator.ConstantImplementations.registerBuiltInConstants

/**
 * Represents any mathematical item. A literal can be an [Operator], a [MathNumber] or a [MathFunction].
 * A [MathExpression] is a collection of [MathLiteral]s but it's also a [MathLiteral] itself
 * (which allows [MathExpression]s to be nested e. g. using parenthesis)
 */
abstract class MathLiteral {
    abstract val formulaRepresentation: String

    /**
     * Tells the parser whether this literal supports implicit multiplication when preceded by the supplied math literal
     *
     * @param previousLiteral The [MathLiteral] that precedes this math literal.
     * @return `true` if this math literal supports implicit multiplication, `false` otherwise
     */
    abstract fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean
    override fun toString(): String = formulaRepresentation

    companion object {
        val operators = mutableListOf<Class<out Operator>>()
        val functions = mutableListOf<Class<out MathFunction>>()
        val constants = mutableListOf<Class<out Constant>>()

        init {
            registerBuiltIns()
        }

        @JvmStatic
        fun registerOperator(operator: Class<out Operator?>) {
            operators.add(operator)
        }

        @JvmStatic
        fun registerFunction(function: Class<out MathFunction?>) {
            functions.add(function)
        }

        @JvmStatic
        fun registerConstant(constant: Class<out Constant?>) {
            constants.add(constant)
        }

        @JvmStatic
        @Deprecated(
            "Use RuntimeConstantRegistry",
            ReplaceWith("RuntimeConstantRegistry.registerRuntimeConstant(runtimeConstant)")
        )
        fun registerRuntimeConstant(runtimeConstant: RuntimeConstant, mathExpression: MathExpression) {
            RuntimeConstantRegistry.registerRuntimeConstant(runtimeConstant.name, mathExpression)
        }

        @JvmStatic
        fun deregisterOperator(operator: Class<out Operator?>): Boolean {
            return operators.remove(operator)
        }

        @JvmStatic
        fun deregisterFunction(function: Class<out MathFunction?>): Boolean {
            return functions.remove(function)
        }

        @JvmStatic
        fun deregisterConstant(constant: Class<out Constant?>): Boolean {
            return constants.remove(constant)
        }

        @Suppress("DEPRECATION")
        @JvmStatic
        @Deprecated(
            "Use RuntimeConstantRegistry",
            ReplaceWith("RuntimeConstantRegistry.runtimeConstant(constantName)")
        )
        fun deregisterRuntimeConstant(runtimeConstant: RuntimeConstant): Boolean {
            return deregisterRuntimeConstant(runtimeConstant.name)
        }

        @JvmStatic
        @Deprecated(
            "Use RuntimeConstantRegistry",
            ReplaceWith("RuntimeConstantRegistry.deregisterRuntimeConstant(constantName)")
        )
        fun deregisterRuntimeConstant(constantName: String): Boolean {
            return RuntimeConstantRegistry.deregisterRuntimeConstant(constantName)
        }

        /**
         * De-registers all [Operator]s, [MathFunction]s, [Constant]s and [RuntimeConstant]s
         * and then re-registers all built-in [Operator]s, [MathFunction]s and [Constant]s.
         *
         *
         * This effectively means that the parser is reset to the initial state prior to any calls to any of the `register` methods.
         */
        @JvmStatic
        fun reset() {
            operators.clear()
            functions.clear()
            constants.clear()
            RuntimeConstantRegistry.values.clear()
            registerBuiltIns()
        }

        private fun registerBuiltIns() {
            OperatorImplementations.registerBuiltInOperators()
            FunctionImplementations.registerBuiltInFunctions()
            registerBuiltInConstants()
        }
    }
}
