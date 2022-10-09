package com.github.vatbub.mathevaluator

import com.github.vatbub.mathevaluator.MathLiteral.Companion.registerFunction
import kotlin.math.*

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
 * Built-in functions
 */
object FunctionImplementations {
    fun registerBuiltInFunctions() {
        registerFunction(SquareRootFunction::class.java)
        registerFunction(SineFunction::class.java)
        registerFunction(CosineFunction::class.java)
        registerFunction(TangentFunction::class.java)
        registerFunction(CotangentFunction::class.java)
        registerFunction(FactorialFunction::class.java)
    }
}

/**
 * General superclass for all functions that take exactly one argument
 */
abstract class OneArgumentFunction(formulaRepresentation: String) : MathFunction(formulaRepresentation, 1, 1)

class SquareRootFunction : OneArgumentFunction("sqrt") {
    override fun evaluateImpl(): MathNumber = sqrt(params[0].evaluate().value).toMathLiteral()
}

class SineFunction : OneArgumentFunction("sin") {
    override fun evaluateImpl(): MathNumber = sin(params[0].evaluate().value).toMathLiteral()
}

class CosineFunction : OneArgumentFunction("cos") {
    override fun evaluateImpl(): MathNumber = cos(params[0].evaluate().value).toMathLiteral()
}

class TangentFunction : OneArgumentFunction("tan") {
    override fun evaluateImpl(): MathNumber = tan(params[0].evaluate().value).toMathLiteral()
}

class CotangentFunction : OneArgumentFunction("cot") {
    override fun evaluateImpl(): MathNumber = (1.0 / tan(params[0].evaluate().value)).toMathLiteral()
}

class FactorialFunction : OneArgumentFunction("factorial") {
    override fun evaluateImpl(): MathNumber = calculateFactorial(params[0].evaluate().value).toMathLiteral()

    private fun calculateFactorial(input: Double): Double {
        require(input >= 0) { "Factorial is not defined for negative inputs" }
        require(input == floor(input)) { "Factorial can only be calculated for integer inputs" }
        return if (input == 0.0) 1.0 else input * calculateFactorial(input - 1)
    }
}
