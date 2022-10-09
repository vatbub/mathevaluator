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

/**
 * Superclass for operators which can act upon one single argument.
 *
 *
 * Example: The `-` operator can act upon two arguments (`5 - 2 = 3`) and upon one argument (`-5` negates the `5`).
 *
 *
 * Note: All operators which extend this class must be able to act upon one and two arguments.
 * If your operator only interacts with one input, consider subclassing the [MathFunction] class instead.
 */
abstract class SingleArgumentOperator(override val formulaRepresentation: String, priority: Int) :
    Operator(formulaRepresentation, priority) {
    abstract fun evaluate(argument: MathNumber): MathNumber
}
