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
 * Built-in constants
 */
public abstract class ConstantImplementations {
    public static void registerBuiltInConstants() {
        MathLiteral.registerConstant(PiConstant.class);
        MathLiteral.registerConstant(EConstant.class);
    }

    /**
     * Represents the mathematical constant pi (3.14 ...)
     */
    public static class PiConstant extends Constant {

        @Override
        public Number getValue() {
            return new Number(Math.PI);
        }

        @Override
        public String getFormulaRepresentation() {
            return "pi";
        }
    }

    /**
     * Represents the mathematical constant e
     */
    public static class EConstant extends Constant {

        @Override
        public Number getValue() {
            return new Number(Math.E);
        }

        @Override
        public String getFormulaRepresentation() {
            return "e";
        }
    }
}
