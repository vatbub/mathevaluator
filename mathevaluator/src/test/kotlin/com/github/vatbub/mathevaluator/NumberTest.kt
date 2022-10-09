package com.github.vatbub.mathevaluator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumberTest {
    @Test
    fun defaultConstructorTest() {
        assertEquals(0.0, 0.0.toMathLiteral().value, 0.0)
    }

    @Test
    fun valueConstructorTest() {
        val inputs = listOf(0.0, 1.25, 23456.8, -1.0)
        inputs.forEach { input ->
            assertEquals(input, input.toMathLiteral().value, 0.0)
        }
    }

    @Test
    fun setValueTest() {
        val inputs = listOf(0.0, 1.25, 23456.8, -1.0)
        inputs.forEach { input ->
            val number = input.toMathLiteral()
            assertEquals(input, number.value, 0.0)
        }
    }

    @Test
    fun representationTest() {
        val inputs = listOf(0.0, 1.25, 23456.8, -1.0)
        inputs.forEach { input ->
            assertEquals(input.toString(), input.toMathLiteral().formulaRepresentation)
        }
    }

    @Test
    fun toStringTest() {
        val inputs = listOf(0.0, 1.25, 23456.8, -1.0)
        inputs.forEach { input ->
            assertEquals(input.toString(), input.toMathLiteral().toString())
        }
    }

    @Test
    fun isParsableTest() {
        val inputs = listOf("687.4", "623,9", "*9653", "65467ü", "468.1e10", "134f")
        val expected = listOf(true, false, false, false, true, true)
        inputs.indices.forEach { i ->
            println("Testing string: \"" + inputs[i] + "\"")
            assertEquals(expected[i], MathNumber.isParsableDouble(inputs[i]))
        }
    }
}
