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


import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpressionTest {

    @After
    public void cleanUp(){
        MathLiteral.reset();
    }

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
    public void negativeNumberAtTheEndWithoutParenthesisTest() {
        MathExpression expression = new MathExpression("1+-2");
        Assert.assertEquals(-1, expression.evaluate().getValue(), 0);
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
    public void implicitMultiplicationOfMathExpressionWithMathExpressionTest() {
        MathExpression expression = new MathExpression("(2^2)(3*5)");
        Assert.assertEquals(60, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfNumberWithMathExpressionTest() {
        MathExpression expression = new MathExpression("2(3*5)");
        Assert.assertEquals(30, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfConstantWithMathExpressionTest() {
        MathExpression expression = new MathExpression("pi(3*5)");
        Assert.assertEquals(Math.PI * 15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfFunctionWithMathExpressionTest() {
        MathExpression expression = new MathExpression("sin(2)(3*5)");
        Assert.assertEquals(Math.sin(2) * 15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfMathExpressionWithConstantTest() {
        MathExpression expression = new MathExpression("(3*5)pi");
        Assert.assertEquals(Math.PI * 15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfMathExpressionWithFunctionTest() {
        MathExpression expression = new MathExpression("(3*5)sin(2)");
        Assert.assertEquals(15 * Math.sin(2), expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfNumberWithConstantTest() {
        MathExpression expression = new MathExpression("2pi");
        Assert.assertEquals(2 * Math.PI, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfNumberWithFunctionTest() {
        MathExpression expression = new MathExpression("2sin(2)");
        Assert.assertEquals(2 * Math.sin(2), expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfConstantWithConstantTest() {
        MathExpression expression = new MathExpression("pipi");
        Assert.assertEquals(Math.PI * Math.PI, expression.evaluate().getValue(), 0);
    }

    @Test
    public void implicitMultiplicationOfConstantWithFunctionTest() {
        MathExpression expression = new MathExpression("pisin(2)");
        Assert.assertEquals(Math.PI * Math.sin(2), expression.evaluate().getValue(), 0);
    }

    @Test
    public void tanTest() {
        MathExpression expression = new MathExpression("tan(5)");
        Assert.assertEquals(Math.tan(5), expression.evaluate().getValue(), 0);
    }

    @Test
    public void cotTest() {
        MathExpression expression = new MathExpression("cot(5)");
        Assert.assertEquals(1 / Math.tan(5), expression.evaluate().getValue(), 0);
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
    public void runtimeConstantTest() {
        MathExpression expression1 = new MathExpression("something = 2*5");
        Assert.assertEquals(10, expression1.evaluate().getValue(), 0);
        MathExpression expression2 = new MathExpression("2*something");
        Assert.assertEquals(20, expression2.evaluate().getValue(), 0);
    }

    @Test
    public void multipleEqualSignsTest() {
        assertThrowable(() -> new MathExpression("something = 2*5 = 3"), new IllegalArgumentException("Runtime constant declarations must not contain more than one equal sign"));
    }

    @Test
    public void runtimeConstantUpdateTest() {
        new MathExpression("x = 10");
        MathExpression expression2 = new MathExpression("y = 2*x");
        Assert.assertEquals(20, expression2.evaluate().getValue(), 0);

        new MathExpression("x = 5");
        Assert.assertEquals(10, expression2.evaluate().getValue(), 0);
    }

    @Test
    public void circularRuntimeConstantDefinitionTest() {
        assertThrowable(() -> new MathExpression("something = 2*something"), new NumberFormatException("For input string: \"something\""));
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

    @Test
    public void circularReferenceTest1() {
        MathExpression expression = new MathExpression("r = 5");
        Assert.assertEquals(5, expression.evaluate().getValue(), 0);

        assertThrowable(() -> new MathExpression("r = r + 1"), new IllegalArgumentException("Circular reference detected"));
    }

    @Test
    public void circularReferenceTest2() {
        MathExpression expression1 = new MathExpression("r = 5");
        Assert.assertEquals(5, expression1.evaluate().getValue(), 0);
        MathExpression expression2 = new MathExpression("c = r + 1");
        Assert.assertEquals(6, expression2.evaluate().getValue(), 0);

        assertThrowable(() -> new MathExpression("r = c + 1"), new IllegalArgumentException("Circular reference detected"));
    }

    @Test
    public void doubleUnaryOperatorTest1() {
        MathExpression expression = new MathExpression("7--3");
        Assert.assertEquals(10, expression.evaluate().getValue(), 0);
    }

    @Test
    public void doubleUnaryOperatorTest2() {
        MathExpression expression = new MathExpression("7++3");
        Assert.assertEquals(10, expression.evaluate().getValue(), 0);
    }

    @Test
    public void doubleUnaryOperatorTest3() {
        MathExpression expression = new MathExpression("7---3");
        Assert.assertEquals(4, expression.evaluate().getValue(), 0);
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
