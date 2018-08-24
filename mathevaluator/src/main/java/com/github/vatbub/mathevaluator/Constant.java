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
 * A constant represents any mathematical constant like e. g. pi (3.14...).
 * NOTE: All implementing classes must be registered using {@link MathLiteral#registerConstant(Class)}
 */
public abstract class Constant extends MathLiteral {
    public abstract Number getValue();

    @Override
    public boolean supportsImplicitMultiplication(MathLiteral previousLiteral) {
        return previousLiteral instanceof Number || previousLiteral instanceof Constant || previousLiteral instanceof Function || previousLiteral instanceof MathExpression;
    }
}
