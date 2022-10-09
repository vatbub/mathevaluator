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
package com.github.vatbub.mathevaluator

/**
 * Built-in constants
 */
object ConstantImplementations {
    @JvmStatic
    fun registerBuiltInConstants() {
        MathLiteral.registerConstant(PiConstant::class.java)
        MathLiteral.registerConstant(EConstant::class.java)
    }
}

/**
 * Represents the mathematical constant pi (3.14 ...)
 */
class PiConstant : Constant("pi", Math.PI.toMathLiteral())

/**
 * Represents the mathematical constant e
 */
class EConstant : Constant("e", Math.E.toMathLiteral())
