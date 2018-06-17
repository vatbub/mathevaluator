package com.github.vatbub;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpressionTest {

    @Test
    public void simpleTest(){
        MathExpression expression = new MathExpression("2*10");
        Assert.assertEquals(20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void negativeNumberAtTheBeginningTest(){
        MathExpression expression = new MathExpression("-2*10");
        Assert.assertEquals(-20, expression.evaluate().getValue(), 0);
    }

    @Test
    public void chainedOperationsTest(){
        MathExpression expression = new MathExpression("5+10*4/5*2+2");
        Assert.assertEquals(11, expression.evaluate().getValue(), 0);
    }

    @Test
    public void parenthesisTest(){
        MathExpression expression = new MathExpression("(5+10)*4/5*(2+2)");
        Assert.assertEquals(3, expression.evaluate().getValue(), 0);
    }

    @Test
    public void nestedParenthesisTest(){
        MathExpression expression = new MathExpression("5+(10*(5/(4-3)+1)/3*2)");
        Assert.assertEquals(15, expression.evaluate().getValue(), 0);
    }

    @Test
    public void expressionWithSpacesTest(){
        MathExpression expression = new MathExpression("   5   +     (  10  *  (   5 / ( 4 - 3 ) + 1   ) / 3 * 2 )    ");
        Assert.assertEquals(15, expression.evaluate().getValue(), 0);
    }
}
