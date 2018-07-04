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


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpressionTest {

    @Test
    public void simpleMultiplicationTest() {
        MathExpression expression = new MathExpression("2*10");
        Assert.assertEquals(20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negativeNumberAtTheBeginningTest() {
        MathExpression expression = new MathExpression("-2*10");
        Assert.assertEquals(-20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void simpleSubtractionTest() {
        MathExpression expression = new MathExpression("10-2");
        Assert.assertEquals(8, expression.evaluate().getValue(), 0);
    }

    @Test
    public void simpleDivisionTest() {
        MathExpression expression = new MathExpression("4/2");
        Assert.assertEquals(2, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negativeParenthesisTest() {
        MathExpression expression = new MathExpression("-(2+10)");
        Assert.assertEquals(-12, expression.evaluate().getValue(), 0);
    }

    @Test
    public void chainedOperationsTest() {
        MathExpression expression = new MathExpression("5+10*4/5*2+2");
        Assert.assertEquals(11, expression.evaluate().getValue(), 0);
    }

    @Test
    public void multiplyExpressionWithNumberTest() {
        MathExpression expression = new MathExpression("(5+10)*4");
        Assert.assertEquals(60, expression.evaluate().getValue(), 0);
    }

    @Test
    public void parenthesisTest() {
        MathExpression expression = new MathExpression("(5+10)*4/5*(2+2)");
        Assert.assertEquals(3, expression.evaluate().getValue(), 0);
    }

    @Test
    public void nestedParenthesisTest() {
        MathExpression expression = new MathExpression("5+(10*(5/(4-3)+1)/3*2)");
        Assert.assertEquals(15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void expressionWithSpacesTest() {
        MathExpression expression = new MathExpression("   5   +     (  10  *  (   5 / ( 4 - 3 ) + 1   ) / 3 * 2 )    ");
        Assert.assertEquals(15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void simpleSquareRootTest() {
        MathExpression expression = new MathExpression("sqrt(4)");
        Assert.assertEquals(2, expression.evaluate().getValue(), 0);
    }

    @Test
    public void nestedParenthesisWithFunctionTest() {
        MathExpression expression = new MathExpression("5+(10*(5/(4-3)+1)/3*sqrt(4))");
        Assert.assertEquals(15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void squareRootWithInnerExpressionTest() {
        MathExpression expression = new MathExpression("sqrt((5+10)*4+4)");
        Assert.assertEquals(8, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negatedSquareRootTest() {
        MathExpression expression = new MathExpression("-sqrt(4)");
        Assert.assertEquals(-2, expression.evaluate().getValue(), 0);
    }

    @Test
    public void squareRootOfNegativeNumberTest() {
        MathExpression expression = new MathExpression("sqrt(-4)");
        Assert.assertEquals(Double.NaN, expression.evaluate().getValue(), 0);
    }

    @Test
    public void powerTest() {
        MathExpression expression = new MathExpression("5^2");
        Assert.assertEquals(25, expression.evaluate().getValue(), 0);
    }

    @Test
    public void constantTest() {
        MathExpression expression = new MathExpression("2*pi^2");
        Assert.assertEquals(2 * Math.pow(Math.PI, 2), expression.evaluate().getValue(), 0);
    }

    @Test
    public void multiplyNegatedConstantTest() {
        MathExpression expression = new MathExpression("2*-pi");
        Assert.assertEquals(2 * -Math.PI, expression.evaluate().getValue(), 0);
    }

    @Test
    public void functionInTheMiddleOfTheExpressionTest() {
        MathExpression expression = new MathExpression("5+10*sqrt(4/5)*2+2");
        Assert.assertEquals(8 * Math.sqrt(5) + 7, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationDetectionTest1() {
        assertThrowable(() -> new MathExpression("(2^2)(3*5)"), new UnsupportedOperationException("Implicit multiplication is not yet supported"));
    }

    @Test
    public void implicitMultiplicationDetectionTest2() {
        assertThrowable(() -> new MathExpression("2(3*5)"), new UnsupportedOperationException("Implicit multiplication is not yet supported"));
    }

    @Test
    public void unbalancedParenthesisTest1() {
        assertFaultyExpression("(5+10*4/5*(2+2)", "Unbalanced parenthesis");
    }

    @Test
    public void unbalancedParenthesisTest2() {
        assertFaultyExpression("(5+10)*4/5*2+2)", "Unbalanced parenthesis");
    }

    @Test
    public void emptyStringTest() {
        assertFaultyExpression("", "Empty expression");
    }

    @Test
    public void illegalCharacterTest() {
        String inputString = "52ß?uh;:";
        String expectedIllegalString = "ß?uh;:";
        assertThrowable(() -> new MathExpression(inputString), new NumberFormatException("For input string: \"" + expectedIllegalString + "\""));
    }

    @Test
    public void operatorsOnlyTest() {
        assertThrowable(() -> new MathExpression("-*^+/"), new NumberFormatException("For input string: \"*^+/\""));
    }

    @Test
    public void functionWithoutParametersTest() {
        assertThrowable(() -> new MathExpression("2*sqrt+3"), new IllegalArgumentException("Function names must be followed by parenthesis to declare the function parameters"));
    }

    @Test
    public void notInstantiableConstantTest() {
        MathLiteral.registerConstant(ConstantWithNonDefaultConstructor.class);
        try {
            assertThrowable(() -> new MathExpression("5+special+8"), new RuntimeException("java.lang.InstantiationException: com.github.vatbub.mathevaluator.MathExpressionTest$ConstantWithNonDefaultConstructor"));
        } finally {
            MathLiteral.deregisterConstant(ConstantWithNonDefaultConstructor.class);
        }
    }

    @Test
    public void notInstantiableOperatorTest() {
        MathLiteral.registerOperator(OperatorWithNonDefaultConstructor.class);
        try {
            assertThrowable(() -> new MathExpression("5bla8"), new RuntimeException("java.lang.InstantiationException: com.github.vatbub.mathevaluator.MathExpressionTest$OperatorWithNonDefaultConstructor"));
        } finally {
            MathLiteral.deregisterOperator(OperatorWithNonDefaultConstructor.class);
        }
    }

    @Test
    public void notInstantiableFunctionTest() {
        MathLiteral.registerFunction(FunctionWithNonDefaultConstructor.class);
        try {
            assertThrowable(() -> new MathExpression("5*func(8)"), new RuntimeException("java.lang.InstantiationException: com.github.vatbub.mathevaluator.MathExpressionTest$FunctionWithNonDefaultConstructor"));
        } finally {
            MathLiteral.deregisterFunction(FunctionWithNonDefaultConstructor.class);
        }
    }

    private void assertFaultyExpression(String expression, String expectedMessage) {
        assertThrowable(() -> {
            MathExpression mathExpression = new MathExpression(expression);
            mathExpression.evaluate();
        }, new IllegalArgumentException(expectedMessage));
    }

    private <T extends Throwable> void assertThrowable(Runnable delegate, T expectedException) {
        try {
            delegate.run();
            Assert.fail("Exception expected");
        } catch (AssertionError e) {
            throw e;
        } catch (Throwable e) {
            Assert.assertEquals(expectedException.getClass().getName(), e.getClass().getName());
            Assert.assertEquals(expectedException.getMessage(), e.getMessage());
        }
    }

    public static class ConstantWithNonDefaultConstructor extends Constant {
        /**
         * The only purpose of this constructor is to hide the default constructor -->
         * Destroys the reflection mechanism used to instantiate constants
         *
         * @param value dummy argument
         */
        @SuppressWarnings("unused")
        public ConstantWithNonDefaultConstructor(Number value) {
        }

        @Override
        public Number getValue() {
            return null;
        }

        @Override
        public String getFormulaRepresentation() {
            return "special";
        }
    }

    public static class OperatorWithNonDefaultConstructor extends Operator {

        /**
         * The only purpose of this constructor is to hide the default constructor -->
         * Destroys the reflection mechanism used to instantiate operators
         *
         * @param value dummy argument
         */
        @SuppressWarnings("unused")
        public OperatorWithNonDefaultConstructor(Number value) {
        }

        @Override
        public Number evaluate(Number leftNumber, Number rightNumber) {
            return null;
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public String getFormulaRepresentation() {
            return "bla";
        }
    }

    public static class FunctionWithNonDefaultConstructor extends Function {

        /**
         * The only purpose of this constructor is to hide the default constructor -->
         * Destroys the reflection mechanism used to instantiate operators
         *
         * @param value dummy argument
         */
        @SuppressWarnings("unused")
        public FunctionWithNonDefaultConstructor(Number value) {
        }

        @Override
        public int getMinNumberOfArguments() {
            return 0;
        }

        @Override
        public int getMaxNumberOfArguments() {
            return 0;
        }

        @Override
        public Number evaluateImpl() {
            return null;
        }

        @Override
        public String getFormulaRepresentation() {
            return "func";
        }
    }
}
