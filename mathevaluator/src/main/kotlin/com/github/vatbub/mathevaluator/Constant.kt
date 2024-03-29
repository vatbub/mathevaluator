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
 * A constant represents any mathematical constant like e. g. pi (3.14...).
 * NOTE: All implementing classes must be registered using [MathLiteral.registerConstant]
 */
abstract class Constant(override val formulaRepresentation: String, val value: MathNumber) : MathLiteral() {
    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean {
        return previousLiteral is MathNumber ||
            previousLiteral is Constant ||
            previousLiteral is RuntimeConstant ||
            previousLiteral is MathFunction ||
            previousLiteral is MathExpression
    }
}
