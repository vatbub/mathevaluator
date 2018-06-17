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
    public void simpleTest() {
        MathExpression expression = new MathExpression("2*10");
        Assert.assertEquals(20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negativeNumberAtTheBeginningTest() {
        MathExpression expression = new MathExpression("-2*10");
        Assert.assertEquals(-20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void chainedOperationsTest() {
        MathExpression expression = new MathExpression("5+10*4/5*2+2");
        Assert.assertEquals(11, expression.evaluate().getValue(), 0);
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
        assertThrowable(() -> new MathExpression(inputString), new NumberFormatException("For input string: \"" + inputString + "\""));
    }

    @Test
    public void operatorsOnlyTest() {
        assertThrowable(() -> new MathExpression("-*^+/"), new NumberFormatException("For input string: \"-\""));
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
