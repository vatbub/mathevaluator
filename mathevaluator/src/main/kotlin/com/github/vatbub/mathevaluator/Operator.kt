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
 * Represents a mathematical operator which can handle two inputs (e. g. `5 + 2`)
 */
abstract class Operator(
    override val formulaRepresentation: String,
    /**
     * Specifies the order in which operators are evaluated. Operators with a higher integer value will be evaluated first.
     * The priorities of the built-in operators are:
     *
     *  * +: 0
     *  * -: 100
     *  * *: 200
     *  * /: 300
     *  * ^: 400
     *
     * Please avoid operators with colliding priorities as the behaviour is undefined for that case.
     *
     * @return The priority of the operator.
     */
    val priority: Int
) : MathLiteral() {
    abstract fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber

    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean = false
}
