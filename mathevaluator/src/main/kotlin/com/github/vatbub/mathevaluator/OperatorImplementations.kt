package com.github.vatbub.mathevaluator

import com.github.vatbub.mathevaluator.MathLiteral.Companion.registerOperator
import kotlin.math.pow

/*-
 * #%L
 * math-evaluator
 * %%
 * Copyright (C) 2016 - 2018 Frederik Kammel
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
 */ /**
 * Built-in operators
 */
object OperatorImplementations {
    fun registerBuiltInOperators() {
        registerOperator(AddOperator::class.java)
        registerOperator(SubtractOperator::class.java)
        registerOperator(DivideOperator::class.java)
        registerOperator(MultiplyOperator::class.java)
        registerOperator(PowerOperator::class.java)
    }
}

class AddOperator : SingleArgumentOperator("+", 0) {
    override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber =
        (leftNumber.value + rightNumber.value).toMathLiteral()

    override fun evaluate(argument: MathNumber): MathNumber = argument
}

class SubtractOperator : SingleArgumentOperator("-", 100) {
    override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber =
        (leftNumber.value - rightNumber.value).toMathLiteral()

    override fun evaluate(argument: MathNumber): MathNumber = (-argument.value).toMathLiteral()
}

class DivideOperator : Operator("/", 200) {
    override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber =
        (leftNumber.value / rightNumber.value).toMathLiteral()
}

class MultiplyOperator : Operator("*", 300) {
    override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber =
        (leftNumber.value * rightNumber.value).toMathLiteral()
}

class PowerOperator : Operator("^", 400) {
    override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber =
        leftNumber.value.pow(rightNumber.value).toMathLiteral()
}
