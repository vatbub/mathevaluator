package com.github.vatbub.mathevaluator
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
 */
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.math.tan

class FunctionImplementationsTest {
    @Test
    fun squareRootTest() {
        val inputs = listOf(4.0, 9.0, 16.0, 25.0)
        val results = listOf(2.0, 3.0, 4.0, 5.0)
        testOneParamFunctionFunction(SquareRootFunction(), inputs, results)
    }

    @Test
    fun sineTest() {
        val inputs = listOf(0.0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI)
        val results = listOf(0.0, 1.0, 0.0, -1.0, 0.0)
        testOneParamFunctionFunction(SineFunction(), inputs, results)
    }

    @Test
    fun cosineTest() {
        val inputs = listOf(0.0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI)
        val results = listOf(1.0, 0.0, -1.0, 0.0, 1.0)
        testOneParamFunctionFunction(CosineFunction(), inputs, results)
    }

    @Test
    fun tangentTest() {
        val inputs = listOf(0.0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI)
        val results = inputs.map { tan(it) }
        testOneParamFunctionFunction(TangentFunction(), inputs, results)
    }

    @Test
    fun cotangentTest() {
        val inputs = listOf(0.0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI)
        val results = inputs.map { 1 / tan(it) }
        testOneParamFunctionFunction(CotangentFunction(), inputs, results)
    }

    @Test
    fun factorialTest() {
        val inputs = listOf(5.0, 10.0, 60.0)
        val results = inputs.map { simpleFactorial(it) }
        testOneParamFunctionFunction(FactorialFunction(), inputs, results)
    }

    @Test
    fun factorialNegativeInputTest() {
        assertThrows(IllegalArgumentException::class.java) {
            testFunction(FactorialFunction(), 0.0, -1.0)
        }
    }

    @Test
    fun factorialDoubleInputTest() {
        assertThrows(IllegalArgumentException::class.java) {
            testFunction(FactorialFunction(), 0.0, 1.5)
        }
    }

    @Test
    fun squareRootRepresentationTest() {
        assertEquals("sqrt", SquareRootFunction().formulaRepresentation)
    }

    @Test
    fun sineRepresentationTest() {
        assertEquals("sin", SineFunction().formulaRepresentation)
    }

    @Test
    fun cosineRepresentationTest() {
        assertEquals("cos", CosineFunction().formulaRepresentation)
    }

    @Test
    fun tangentRepresentationTest() {
        assertEquals("tan", TangentFunction().formulaRepresentation)
    }

    @Test
    fun cotangentRepresentationTest() {
        assertEquals("cot", CotangentFunction().formulaRepresentation)
    }

    @Test
    fun factorialRepresentationTest() {
        assertEquals("factorial", FactorialFunction().formulaRepresentation)
    }

    private fun testOneParamFunctionFunction(
        function: MathFunction,
        inputs: List<Double>,
        expectedOutputs: List<Double>
    ) {
        inputs.indices.forEach {
            testFunction(function, expectedOutputs[it], inputs[it])
        }
    }

    private fun testFunction(function: MathFunction, expectedResult: Double, vararg inputs: Double) {
        function.params = inputs.map { input ->
            MathExpression(listOf(input.toMathLiteral()))
        }
        assertEquals(expectedResult, function.evaluate().value, 10.0.pow(-10.0))
    }

    companion object {
        private fun simpleFactorial(n: Double): Double =
            if (n == 0.0) 1.0
            else n * simpleFactorial(n - 1)
    }
}
