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

import com.github.vatbub.mathevaluator.MathLiteral.Companion.constants
import com.github.vatbub.mathevaluator.MathLiteral.Companion.functions
import com.github.vatbub.mathevaluator.MathLiteral.Companion.operators
import com.github.vatbub.mathevaluator.util.addAndResolveImplicitMultiplication
import com.github.vatbub.mathevaluator.util.checkForCircularReferences
import com.github.vatbub.mathevaluator.util.isLastElementANumberOrExpressionOrFunction

fun String.toMathExpression(): MathExpression {
    val expression = this.replace(" ".toRegex(), "")

    // check for runtime constant definitions
    val splitAtEqualSign = expression.split("=").filter { it.isNotEmpty() }
    require(splitAtEqualSign.size <= 2) { "Runtime constant declarations must not contain more than one equal sign" }
    if (splitAtEqualSign.size == 2) {
        val expressionWithoutAssignment = splitAtEqualSign[1].toMathExpression()
        RuntimeConstant(splitAtEqualSign[0]).checkForCircularReferences(expressionWithoutAssignment)
        RuntimeConstantRegistry.registerRuntimeConstant(splitAtEqualSign[0], expressionWithoutAssignment)
        return expressionWithoutAssignment
    }

    var parseBuffer = StringBuffer()
    val result = mutableListOf<MathLiteral>()
    var openBrackets = 0
    var pendingFunction: MathFunction? = null

    expression.forEach { character ->
        if (character == '(') {
            if (openBrackets > 0) {
                parseBuffer.append(character)
            } else if (parseBuffer.isNotEmpty()) {
                // there was something in the parse buffer --> implicit multiplication
                val function = parseCurrentBuffer(parseBuffer, result, MultiplyOperator().formulaRepresentation)
                if (function != null) pendingFunction = function
            }
            openBrackets++
        } else if (character == ')') {
            if (openBrackets > 1) parseBuffer.append(character)
            openBrackets--
            if (openBrackets == 0) {
                if (pendingFunction != null) {
                    // we have a pending function and the buffer contains its parameters
                    val parsedParams = parseBuffer
                        .toString()
                        .split(",")
                        .filter { it.isNotEmpty() }
                        .map { it.toMathExpression() }
                    pendingFunction!!.params = parsedParams
                    result.addAndResolveImplicitMultiplication(pendingFunction!!)
                    pendingFunction = null
                } else {
                    // We just finished a sub-expression
                    result.addAndResolveImplicitMultiplication(
                        parseBuffer.toString().toMathExpression()
                    )
                }
                parseBuffer = StringBuffer()
            }
        } else if (openBrackets > 0) {
            parseBuffer.append(character)
        } else if (pendingFunction != null) {
            // pending function is null but openBrackets == 0 --> functions
            // require parenthesis to declare arguments
            throw IllegalArgumentException("Function names must be followed by parenthesis to declare the function parameters")
        } else {
            val mathFunction = parseCurrentBuffer(parseBuffer, result, character.toString())
            if (mathFunction != null) pendingFunction = mathFunction
        }
    }
    if (openBrackets != 0) throw IllegalArgumentException("Unbalanced parenthesis")

    // parse the last number in the buffer
    if (parseBuffer.isNotEmpty()) result.add(parseBuffer.toString().toDouble().toMathLiteral())
    return MathExpression(result)
}

private fun parseCurrentBuffer(
    parseBuffer: StringBuffer,
    res: MutableList<MathLiteral>,
    nextCharacter: String
): MathFunction? {
    if (parseBuffer.toString().toDoubleOrNull() != null &&
        (parseBuffer.toString() + nextCharacter).toDoubleOrNull() == null
    ) {
        // parseBuffer was previously parsable as a double but when
        // adding the current char, it becomes unparsable --> we
        // need to parse the number now
        res.add(parseBuffer.toString().toDouble().toMathLiteral())
        parseBuffer.setLength(0)
    }
    parseBuffer.append(nextCharacter)

    // parse constants
    constants
        .map { it.getDeclaredConstructor().newInstance()!! }
        .firstOrNull { parseBuffer.toString() == it.formulaRepresentation }
        ?.let { constant ->
            res.addAndResolveImplicitMultiplication(constant)
            parseBuffer.setLength(0)
            return null
        }

    // parse runtime constants
    RuntimeConstantRegistry.values
        .filter { parseBuffer.toString() == it.key }
        .keys
        .firstOrNull()
        ?.let { key ->
            res.addAndResolveImplicitMultiplication(RuntimeConstant(key))
            parseBuffer.setLength(0)
            return null
        }

    // parse operators
    operators
        .map { it.getDeclaredConstructor().newInstance() }
        .filter { parseBuffer.toString() == it.formulaRepresentation }
        .firstOrNull { it is SingleArgumentOperator || res.isLastElementANumberOrExpressionOrFunction() }
        ?.let { operator ->
            res.addAndResolveImplicitMultiplication(operator)
            parseBuffer.setLength(0)
            return null
        }

    // parse functions
    functions
        .map { it.getDeclaredConstructor().newInstance() }
        .firstOrNull { parseBuffer.toString() == it.formulaRepresentation }
        ?.let { function ->
            parseBuffer.setLength(0)
            return function
        }

    return null
}
