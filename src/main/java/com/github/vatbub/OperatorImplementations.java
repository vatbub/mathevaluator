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


/**
 * Created by Kammel on 15.06.2018.
 */
public abstract class OperatorImplementations {
    public static class AddOperator implements Operator {

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return new Number(leftNumber.getValue() + rightNumber.getValue());
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public String toString() {
            return "+";
        }
    }

    public static class SubtractOperator implements Operator {

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return new Number(leftNumber.getValue() - rightNumber.getValue());
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    public static class DivideOperator implements Operator {

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return new Number(leftNumber.getValue() / rightNumber.getValue());
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public String toString() {
            return "/";
        }
    }

    public static class MultiplyOperator implements Operator {

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return new Number(leftNumber.getValue() * rightNumber.getValue());
        }

        @Override
        public int getPriority() {
            return 3;
        }

        @Override
        public String toString() {
            return "*";
        }
    }

    public static class PowerOperator implements Operator {

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return new Number(Math.pow(leftNumber.getValue(), rightNumber.getValue()));
        }

        @Override
        public int getPriority() {
            return 4;
        }

        @Override
        public String toString() {
            return "^";
        }
    }
}
