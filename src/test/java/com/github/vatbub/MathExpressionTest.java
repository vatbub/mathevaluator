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


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpressionTest {

    @Test
    public void simpleMultplicationTest() {
        MathExpression expression = new MathExpression("2*10");
        Assert.assertEquals(20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negativeNumberAtTheBeginningTest() {
        MathExpression expression = new MathExpression("-2*10");
        Assert.assertEquals(-20, expression.evaluate().getValue(), 0);
    }
    
    @Test
    public void simpleSubtractionTest(){
    	MathExpression expression = new MathExpression("10-2");
        Assert.assertEquals(8, expression.evaluate().getValue(), 0);
    }
    
    @Test
    public void simpleDivisionTest(){
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
}
