/*-
 * #%L
 * math-evaluator
 * %%
 * Copyright (C) 2019 - 2022 Frederik Kammel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.vatbub.mathevaluator

import com.github.vatbub.mathevaluator.util.isElementANumberOrExpressionOrFunction
import com.github.vatbub.mathevaluator.util.superclassPriority

/**
 * Represents a mathematical term.
 * Use [.MathExpression] to parse a string and [.evaluate] to evaluate the expression.
 */
class MathExpression(
    /**
     * The list of [MathLiteral]s that this expression consists of. Usually, the returned expression consists of:
     *
     *  * [MathNumber]s
     *  * [MathExpression]s
     *  * [MathFunction]s and
     *  * [Constant]s
     *
     * ... which are separated by [Operator]s.
     */
    var expression: List<MathLiteral>
) : MathLiteral() {

    /**
     * Parses the specified term and returns its object representation
     *
     * @param expression The expression to parse
     */
    constructor(expression: String) : this(expression.toMathExpression().expression)

    /**
     * Evaluates this expression. Please note: The evaluation is performed using a copy of [.getExpression] to avoid
     * modifications of the original expression.
     * If this expression contains other expressions, they will be evaluated recursively.
     *
     * @return The result of this expression.
     * @throws ArrayIndexOutOfBoundsException if this expression fails to evaluate
     */
    fun evaluate(): MathNumber {
        if (expression.isEmpty()) throw IllegalArgumentException("Empty expression")

        // eliminate any remaining MathExpressions
        var simplifiedExpression: MutableList<MathLiteral> = ArrayList(
            expression.size
        )
        expression.forEach { literal ->
            val evaluatedLiteral = when (literal) {
                is MathExpression -> literal.evaluate()
                is MathFunction -> literal.evaluate()
                is Constant -> literal.value
                is RuntimeConstant -> literal.value
                else -> literal
            }

            simplifiedExpression.add(evaluatedLiteral)
        }

        // our expression now only contains numbers and operators
        simplifiedExpression = simplifiedExpression.evaluateConsecutiveUnaryOperators()
        while (simplifiedExpression.size > 1) {
            // find the operator with the highest priority
            var indexOfOperatorWithHighestPriority = simplifiedExpression.indexOfFirst { it is Operator }
            // TODO Can indexOf be -1, i. e. can it happen that there are no operators?
            var operatorWithHighestPriority = simplifiedExpression[indexOfOperatorWithHighestPriority] as Operator
            simplifiedExpression.forEachIndexed { index, literal ->
                if (literal !is Operator) return@forEachIndexed
                if (literal.priority > operatorWithHighestPriority.priority) {
                    indexOfOperatorWithHighestPriority = index
                    operatorWithHighestPriority = literal
                    return@forEachIndexed
                }
                if (literal.superclassPriority > operatorWithHighestPriority.superclassPriority && !simplifiedExpression.isElementANumberOrExpressionOrFunction(
                        index - 1
                    )
                ) {
                    indexOfOperatorWithHighestPriority = index
                    operatorWithHighestPriority = literal
                }
            }
            if ((
                indexOfOperatorWithHighestPriority == 0 ||
                    simplifiedExpression[indexOfOperatorWithHighestPriority - 1] is Operator
                ) &&
                operatorWithHighestPriority is SingleArgumentOperator
            ) {
                val right = simplifiedExpression[indexOfOperatorWithHighestPriority + 1] as MathNumber
                simplifiedExpression[indexOfOperatorWithHighestPriority] =
                    (operatorWithHighestPriority as SingleArgumentOperator).evaluate(right)
                simplifiedExpression.removeAt(indexOfOperatorWithHighestPriority + 1)
            } else {
                val left = simplifiedExpression[indexOfOperatorWithHighestPriority - 1] as MathNumber
                val operator = simplifiedExpression[indexOfOperatorWithHighestPriority] as Operator
                val right = simplifiedExpression[indexOfOperatorWithHighestPriority + 1] as MathNumber
                simplifiedExpression[indexOfOperatorWithHighestPriority - 1] = operator.evaluate(left, right)
                simplifiedExpression.removeAt(indexOfOperatorWithHighestPriority + 1)
                simplifiedExpression.removeAt(indexOfOperatorWithHighestPriority)
            }
        }
        return simplifiedExpression[0] as MathNumber
    }

    private fun List<MathLiteral>.evaluateConsecutiveUnaryOperators(): MutableList<MathLiteral> {
        val result: MutableList<MathLiteral> = ArrayList()
        val unaryOperators: MutableList<SingleArgumentOperator> = ArrayList()

        this.forEach { nextElement ->
            if (nextElement is SingleArgumentOperator) {
                unaryOperators.add(nextElement)
                return@forEach
            }
            if (unaryOperators.isEmpty()) {
                result.add(nextElement)
                return@forEach
            }

            var currentValue = nextElement as MathNumber
            while (unaryOperators.isNotEmpty()) {
                val nextOperator = unaryOperators.removeAt(unaryOperators.size - 1)
                currentValue = nextOperator.evaluate(currentValue)
            }
            result.add(AddOperator())
            result.add(currentValue)
        }
        return result
    }

    override fun toString(): String {
        return formulaRepresentation
    }

    override val formulaRepresentation: String
        get() {
            val builder = StringBuilder()
            expression.forEach { literal ->
                builder.append(" ")
                if (literal is MathExpression) builder.append("(").append(literal.toString())
                    .append(")") else builder.append(literal.toString())
                builder.append(" ")
            }
            return builder.toString()
        }

    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean {
        return previousLiteral is MathNumber || previousLiteral is Constant || previousLiteral is RuntimeConstant || previousLiteral is MathFunction || previousLiteral is MathExpression
    }
}
