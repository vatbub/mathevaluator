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
}
