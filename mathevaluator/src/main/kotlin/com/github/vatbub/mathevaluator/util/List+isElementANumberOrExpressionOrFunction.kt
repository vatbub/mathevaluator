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
package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.*

internal fun List<MathLiteral>.isElementANumberOrExpressionOrFunction(index: Int): Boolean {
    if (isEmpty()) return false
    val element = this[index]
    return element is MathNumber || element is MathExpression || element is Constant || element is RuntimeConstant || element is MathFunction
}
