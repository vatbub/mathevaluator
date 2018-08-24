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
 * Represents a mathematical operator which can handle two inputs (e. g. {@code 5 + 2})
 */
public abstract class Operator extends MathLiteral {
    public abstract Number evaluate(Number leftNumber, Number rightNumber);

    /**
     * Specifies the order in which operators are evaluated. Operators with a higher integer value will be evaluated first.
     * The priorities of the built-in operators are:
     * <ul>
     * <li>+: 0</li>
     * <li>-: 100</li>
     * <li>*: 200</li>
     * <li>/: 300</li>
     * <li>^: 400</li>
     * </ul>
     * Please avoid operators with colliding priorities as the behaviour is undefined for that case.
     *
     * @return The priority of the operator.
     */
    public abstract int getPriority();

    @Override
    public boolean supportsImplicitMultiplication(MathLiteral previousLiteral) {
        return false;
    }
}
