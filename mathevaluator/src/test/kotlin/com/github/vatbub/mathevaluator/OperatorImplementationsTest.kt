package com.github.vatbub.mathevaluator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OperatorImplementationsTest {
    @Test
    fun addTwoInputsTest() {
        val inputs1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val inputs2 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val expected = listOf(2.0, 4.0, 6.0, 8.0, 10.0)
        inputs1.indices.forEach {
            assertEquals(
                expected[it],
                AddOperator().evaluate(inputs1[it].toMathLiteral(), inputs2[it].toMathLiteral()).value,
                0.0
            )
        }
    }

    @Test
    fun addOneInputTest() {
        val inputs = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val expected = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        inputs.indices.forEach {
            assertEquals(
                expected[it],
                AddOperator().evaluate(inputs[it].toMathLiteral()).value,
                0.0
            )
        }
    }

    @Test
    fun addPriorityTest() {
        assertEquals(0, AddOperator().priority)
    }

    @Test
    fun addRepresentationTest() {
        assertEquals("+", AddOperator().formulaRepresentation)
    }

    @Test
    fun subtractTwoInputsTest() {
        val inputs1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val inputs2 = listOf(5.0, 4.0, 3.0, 2.0, 1.0)
        val expected = listOf(-4.0, -2.0, 0.0, 2.0, 4.0)
        inputs1.indices.forEach {
            assertEquals(
                expected[it],
                SubtractOperator().evaluate(
                    inputs1[it].toMathLiteral(),
                    inputs2[it].toMathLiteral()
                ).value,
                0.0
            )
        }
    }

    @Test
    fun subtractOneInputTest() {
        val inputs = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val expected = listOf(-1.0, -2.0, -3.0, -4.0, -5.0)
        inputs.indices.forEach {
            assertEquals(
                expected[it],
                SubtractOperator().evaluate(inputs[it].toMathLiteral()).value,
                0.0
            )
        }
    }

    @Test
    fun subtractPriorityTest() {
        assertEquals(100, SubtractOperator().priority)
    }

    @Test
    fun subtractRepresentationTest() {
        assertEquals("-", SubtractOperator().formulaRepresentation)
    }

    @Test
    fun divideTwoInputsTest() {
        val inputs1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val inputs2 = listOf(5.0, 4.0, 3.0, 2.0, 1.0)
        val expected = listOf(1.0 / 5.0, 1.0 / 2.0, 1.0, 2.0, 5.0)
        inputs1.indices.forEach {
            assertEquals(
                expected[it],
                DivideOperator().evaluate(
                    inputs1[it].toMathLiteral(),
                    inputs2[it]
                        .toMathLiteral()
                ).value,
                0.0
            )
        }
    }

    @Test
    fun dividePriorityTest() {
        assertEquals(200, DivideOperator().priority)
    }

    @Test
    fun divideRepresentationTest() {
        assertEquals("/", DivideOperator().formulaRepresentation)
    }

    @Test
    fun multiplyTwoInputsTest() {
        val inputs1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val inputs2 = listOf(5.0, 4.0, 3.0, 2.0, 1.0)
        val expected = listOf(5.0, 8.0, 9.0, 8.0, 5.0)
        inputs1.indices.forEach {
            assertEquals(
                expected[it],
                MultiplyOperator().evaluate(
                    inputs1[it].toMathLiteral(),
                    inputs2[it]
                        .toMathLiteral()
                ).value,
                0.0
            )
        }
    }

    @Test
    fun multiplyPriorityTest() {
        assertEquals(300, MultiplyOperator().priority)
    }

    @Test
    fun multiplyRepresentationTest() {
        assertEquals("*", MultiplyOperator().formulaRepresentation)
    }

    @Test
    fun powerTwoInputsTest() {
        val inputs1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val inputs2 = listOf(5.0, 4.0, 3.0, 2.0, 1.0)
        val expected = listOf(1.0, 16.0, 27.0, 16.0, 5.0)
        inputs1.indices.forEach {
            assertEquals(
                expected[it],
                PowerOperator().evaluate(
                    inputs1[it].toMathLiteral(),
                    inputs2[it]
                        .toMathLiteral()
                ).value,
                0.0
            )
        }
    }

    @Test
    fun powerPriorityTest() {
        assertEquals(400, PowerOperator().priority)
    }

    @Test
    fun powerRepresentationTest() {
        assertEquals("^", PowerOperator().formulaRepresentation)
    }
}
