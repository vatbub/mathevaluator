package com.github.vatbub;

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


public class Number implements MathLiteral {
    private double value;

    public Number() {
        this(0);
    }

    public Number(double value) {
        setValue(value);
    }

    public java.lang.Double getValue() {
        return value;
    }

    public void setValue(java.lang.Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
