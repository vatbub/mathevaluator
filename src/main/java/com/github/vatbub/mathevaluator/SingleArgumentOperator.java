package com.github.vatbub.mathevaluator;

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

/**
 * Superclass for operators which can act upon one single argument.
 * <p>
 * Example: The {@code -} operator can act upon two arguments ({@code 5 - 2 = 3}) and upon one argument ({@code -5} negates the {@code 5}).
 * <p>
 * Note: All operators which extend this class must be able to act upon one and two arguments.
 * If your operator only interacts with one input, consider subclassing the {@link Function} class instead.
 */
public abstract class SingleArgumentOperator extends Operator {
    public abstract Number evaluate(Number argument);
}
