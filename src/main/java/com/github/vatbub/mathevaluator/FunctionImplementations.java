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
 * Built-in functions
 */
public abstract class FunctionImplementations {
    public static void registerBuiltInFunctions() {
        MathLiteral.registerFunction(SquareRootFunction.class);
        MathLiteral.registerFunction(SineFunction.class);
        MathLiteral.registerFunction(CosineFunction.class);
    }

    /**
     * General superclass for all functions that take exactly one argument
     */
    public static abstract class OneArgumentFunction extends Function {
        @Override
        public int getMinNumberOfArguments() {
            return 1;
        }

        @Override
        public int getMaxNumberOfArguments() {
            return 1;
        }
    }

    public static class SquareRootFunction extends OneArgumentFunction {

        @Override
        public Number evaluateImpl() {
            return new Number(Math.sqrt(getParams().get(0).evaluate().getValue()));
        }

        @Override
        public String getFormulaRepresentation() {
            return "sqrt";
        }
    }

    public static class SineFunction extends OneArgumentFunction {

        @Override
        public Number evaluateImpl() {
            return new Number(Math.sin(getParams().get(0).evaluate().getValue()));
        }

        @Override
        public String getFormulaRepresentation() {
            return "sin";
        }
    }

    public static class CosineFunction extends OneArgumentFunction {

        @Override
        public Number evaluateImpl() {
            return new Number(Math.cos(getParams().get(0).evaluate().getValue()));
        }

        @Override
        public String getFormulaRepresentation() {
            return "cos";
        }
    }

    public static class TangentFunction extends OneArgumentFunction {

        @Override
        public Number evaluateImpl() {
            return new Number(Math.tan(getParams().get(0).evaluate().getValue()));
        }

        @Override
        public String getFormulaRepresentation() {
            return "tan";
        }
    }

    public static class CotangentFunction extends OneArgumentFunction {

        @Override
        public Number evaluateImpl() {
            return new Number(1 / Math.tan(getParams().get(0).evaluate().getValue()));
        }

        @Override
        public String getFormulaRepresentation() {
            return "cot";
        }
    }
}
