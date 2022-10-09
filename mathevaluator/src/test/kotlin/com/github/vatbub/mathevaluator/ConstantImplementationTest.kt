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
