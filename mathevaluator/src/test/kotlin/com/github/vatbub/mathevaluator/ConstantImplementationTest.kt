package com.github.vatbub.mathevaluator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConstantImplementationTest {
    @Test
    fun piValueTest() {
        assertEquals(Math.PI, PiConstant().value.value, 10e-10)
    }

    @Test
    fun eValueTest() {
        assertEquals(Math.E, EConstant().value.value, 10e-10)
    }

    @Test
    fun piRepresentationTest() {
        assertEquals("pi", PiConstant().formulaRepresentation)
    }

    @Test
    fun eRepresentationTest() {
        assertEquals("e", EConstant().formulaRepresentation)
    }
}
