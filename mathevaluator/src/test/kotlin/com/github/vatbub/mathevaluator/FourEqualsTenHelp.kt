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

import org.junit.jupiter.api.Test

class FourEqualsTenHelp {
    private val numbersWithoutOperators =
        listOf(7, 1, 4, 0)
            .permutations()
            .distinct()
            .map { outerList ->
                outerList.map { it.toDouble().toMathLiteral() }
            }

    private val possibleOperators = listOf(AddOperator(), SubtractOperator(), MultiplyOperator(), DivideOperator())

    private val operatorPermutations = mutableListOf<List<Operator>>()

    init {
        createOperatorPermutations()
    }

    private fun createOperatorPermutations() {
        val maxIndex = (possibleOperators.size - 1).toString()
        val start = maxIndex + maxIndex + maxIndex
        val result = (start.toInt(possibleOperators.size) downTo 0)
            .map {
                var number = it.toString(possibleOperators.size)
                while (number.length < start.length)
                    number = "0$number"
                number
            }
            .map { line -> line.map { possibleOperators[it.toString().toInt()] } }

        operatorPermutations.addAll(result)
    }

    @Test
    fun findSolutions() {
        val possibleCombinationsNoParenthesis = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        numberCombination[0],
                        operatorCombination[0],
                        numberCombination[1],
                        operatorCombination[1],
                        numberCombination[2],
                        operatorCombination[2],
                        numberCombination[3]
                    )
                )
            }
        }

        val possibleCombinationsParenthesis1 = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        MathExpression(
                            listOf(
                                numberCombination[0],
                                operatorCombination[0],
                                numberCombination[1]
                            )
                        ),
                        operatorCombination[1],
                        numberCombination[2],
                        operatorCombination[2],
                        numberCombination[3]
                    )
                )
            }
        }

        val possibleCombinationsParenthesis2 = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        MathExpression(
                            listOf(
                                numberCombination[0],
                                operatorCombination[0],
                                numberCombination[1],
                                operatorCombination[1],
                                numberCombination[2]
                            )
                        ),
                        operatorCombination[2],
                        numberCombination[3]
                    )
                )
            }
        }

        val possibleCombinationsParenthesis3 = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        numberCombination[0],
                        operatorCombination[0],
                        MathExpression(
                            listOf(
                                numberCombination[1],
                                operatorCombination[1],
                                numberCombination[2]
                            )
                        ),
                        operatorCombination[2],
                        numberCombination[3]
                    )
                )
            }
        }

        val possibleCombinationsParenthesis4 = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        numberCombination[0],
                        operatorCombination[0],
                        MathExpression(
                            listOf(
                                numberCombination[1],
                                operatorCombination[1],
                                numberCombination[2],
                                operatorCombination[2],
                                numberCombination[3]
                            )
                        )
                    )
                )
            }
        }

        val possibleCombinationsParenthesis5 = numbersWithoutOperators.flatMap { numberCombination ->
            operatorPermutations.map { operatorCombination ->
                MathExpression(
                    listOf(
                        numberCombination[0],
                        operatorCombination[0],
                        numberCombination[1],
                        operatorCombination[1],
                        MathExpression(
                            listOf(
                                numberCombination[2],
                                operatorCombination[2],
                                numberCombination[3]
                            )
                        )
                    )
                )
            }
        }

        println("Solutions:")
        (
            possibleCombinationsNoParenthesis +
                possibleCombinationsParenthesis1 +
                possibleCombinationsParenthesis2 +
                possibleCombinationsParenthesis3 +
                possibleCombinationsParenthesis4 +
                possibleCombinationsParenthesis5
            ).filter { it.evaluate().value == 10.0 }
            .forEach { println(it) }
    }

    private fun <T> List<T>.permutations(): List<List<T>> =
        if (isEmpty()) listOf(emptyList())
        else mutableListOf<List<T>>().also { result ->
            for (i in this.indices) {
                (this - this[i]).permutations().forEach {
                    result.add(it + this[i])
                }
            }
        }
}
