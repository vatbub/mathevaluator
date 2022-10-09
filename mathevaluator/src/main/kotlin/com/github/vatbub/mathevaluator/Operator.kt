package com.github.vatbub.mathevaluator

/**
 * Represents a mathematical operator which can handle two inputs (e. g. `5 + 2`)
 */
abstract class Operator(
    override val formulaRepresentation: String,
    /**
     * Specifies the order in which operators are evaluated. Operators with a higher integer value will be evaluated first.
     * The priorities of the built-in operators are:
     *
     *  * +: 0
     *  * -: 100
     *  * *: 200
     *  * /: 300
     *  * ^: 400
     *
     * Please avoid operators with colliding priorities as the behaviour is undefined for that case.
     *
     * @return The priority of the operator.
     */
    val priority: Int
) : MathLiteral() {
    abstract fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber

    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean = false
}
