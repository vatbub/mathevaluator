package com.github.vatbub.mathevaluator

/**
 * Superclass for operators which can act upon one single argument.
 *
 *
 * Example: The `-` operator can act upon two arguments (`5 - 2 = 3`) and upon one argument (`-5` negates the `5`).
 *
 *
 * Note: All operators which extend this class must be able to act upon one and two arguments.
 * If your operator only interacts with one input, consider subclassing the [MathFunction] class instead.
 */
abstract class SingleArgumentOperator(override val formulaRepresentation: String, priority: Int) :
    Operator(formulaRepresentation, priority) {
    abstract fun evaluate(argument: MathNumber): MathNumber
}
