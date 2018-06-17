package com.github.vatbub;

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
