package com.github.vatbub.mathevaluator

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

/**
 * Created by Kammel on 15.06.2018.
 */
class MathExpressionTest {
    @AfterEach
    fun cleanUp() {
        MathLiteral.reset()
    }

    @Test
    fun simpleMultiplicationTest() {
        val expression = "2*10".toMathExpression()
        assertEquals(20.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun negativeNumberAtTheBeginningTest() {
        val expression = "-2*10".toMathExpression()
        assertEquals(-20.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun negativeNumberAtTheEndWithoutParenthesisTest() {
        val expression = "1+-2".toMathExpression()
        assertEquals(-1.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun simpleSubtractionTest() {
        val expression = "10-2".toMathExpression()
        assertEquals(8.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun simpleDivisionTest() {
        val expression = "4/2".toMathExpression()
        assertEquals(2.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun negativeParenthesisTest() {
        val expression = "-(2+10)".toMathExpression()
        assertEquals(-12.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun chainedOperationsTest() {
        val expression = "5+10*4/5*2+2".toMathExpression()
        assertEquals(11.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun multiplyExpressionWithNumberTest() {
        val expression = "(5+10)*4".toMathExpression()
        assertEquals(60.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun parenthesisTest() {
        val expression = "(5+10)*4/5*(2+2)".toMathExpression()
        assertEquals(3.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun nestedParenthesisTest() {
        val expression = "5+(10*(5/(4-3)+1)/3*2)".toMathExpression()
        assertEquals(15.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun expressionWithSpacesTest() {
        val expression = "   5   +     (  10  *  (   5 / ( 4 - 3 ) + 1   ) / 3 * 2 )    ".toMathExpression()
        assertEquals(15.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun simpleSquareRootTest() {
        val expression = "sqrt(4)".toMathExpression()
        assertEquals(2.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun nestedParenthesisWithFunctionTest() {
        val expression = "5+(10*(5/(4-3)+1)/3*sqrt(4))".toMathExpression()
        assertEquals(15.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun squareRootWithInnerExpressionTest() {
        val expression = "sqrt((5+10)*4+4)".toMathExpression()
        assertEquals(8.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun negatedSquareRootTest() {
        val expression = "-sqrt(4)".toMathExpression()
        assertEquals(-2.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun squareRootOfNegativeNumberTest() {
        val expression = "sqrt(-4)".toMathExpression()
        assertEquals(Double.NaN, expression.evaluate().value, 0.0)
    }

    @Test
    fun powerTest() {
        val expression = "5^2".toMathExpression()
        assertEquals(25.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun constantTest() {
        val expression = "2*pi^2".toMathExpression()
        assertEquals(2 * PI.pow(2.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun multiplyNegatedConstantTest() {
        val expression = "2*-pi".toMathExpression()
        assertEquals(2 * -PI, expression.evaluate().value, 0.0)
    }

    @Test
    fun functionInTheMiddleOfTheExpressionTest() {
        val expression = "5+10*sqrt(4/5)*2+2".toMathExpression()
        assertEquals(8 * sqrt(5.0) + 7, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfMathExpressionWithMathExpressionTest() {
        val expression = "(2^2)(3*5)".toMathExpression()
        assertEquals(60.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfNumberWithMathExpressionTest() {
        val expression = "2(3*5)".toMathExpression()
        assertEquals(30.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfConstantWithMathExpressionTest() {
        val expression = "pi(3*5)".toMathExpression()
        assertEquals(PI * 15, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfFunctionWithMathExpressionTest() {
        val expression = "sin(2)(3*5)".toMathExpression()
        assertEquals(sin(2.0) * 15, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfMathExpressionWithConstantTest() {
        val expression = "(3*5)pi".toMathExpression()
        assertEquals(PI * 15, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfMathExpressionWithFunctionTest() {
        val expression = "(3*5)sin(2)".toMathExpression()
        assertEquals(15 * sin(2.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfNumberWithConstantTest() {
        val expression = "2pi".toMathExpression()
        assertEquals(2 * PI, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfNumberWithFunctionTest() {
        val expression = "2sin(2)".toMathExpression()
        assertEquals(2 * sin(2.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfConstantWithConstantTest() {
        val expression = "pipi".toMathExpression()
        assertEquals(PI * PI, expression.evaluate().value, 0.0)
    }

    @Test
    fun implicitMultiplicationOfConstantWithFunctionTest() {
        val expression = "pisin(2)".toMathExpression()
        assertEquals(PI * sin(2.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun tanTest() {
        val expression = "tan(5)".toMathExpression()
        assertEquals(tan(5.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun cotTest() {
        val expression = "cot(5)".toMathExpression()
        assertEquals(1 / tan(5.0), expression.evaluate().value, 0.0)
    }

    @Test
    fun unbalancedParenthesisTest1() {
        assertFaultyExpression("(5+10*4/5*(2+2)", "Unbalanced parenthesis")
    }

    @Test
    fun unbalancedParenthesisTest2() {
        assertFaultyExpression("(5+10)*4/5*2+2)", "Unbalanced parenthesis")
    }

    @Test
    fun emptyStringTest() {
        assertFaultyExpression("", "Empty expression")
    }

    @Test
    fun illegalCharacterTest() {
        val inputString = "52ß?uh;:"
        val expectedIllegalString = "ß?uh;:"
        assertThrowable(NumberFormatException::class.java, "For input string: \"$expectedIllegalString\"") {
            inputString.toMathExpression()
        }
    }

    @Test
    fun operatorsOnlyTest() {
        assertThrowable(NumberFormatException::class.java, "For input string: \"*^+/\"") {
            "-*^+/".toMathExpression()
        }
    }

    @Test
    fun functionWithoutParametersTest() {
        assertThrowable(
            IllegalArgumentException::class.java,
            "Function names must be followed by parenthesis to declare the function parameters"
        ) {
            "2*sqrt+3".toMathExpression()
        }
    }

    @Test
    fun runtimeConstantTest() {
        val expression1 = "something = 2*5".toMathExpression()
        assertEquals(10.0, expression1.evaluate().value, 0.0)
        val expression2 = "2*something".toMathExpression()
        assertEquals(20.0, expression2.evaluate().value, 0.0)
    }

    @Test
    fun multipleEqualSignsTest() {
        assertThrowable(
            IllegalArgumentException::class.java,
            "Runtime constant declarations must not contain more than one equal sign"
        ) { "something = 2*5 = 3".toMathExpression() }
    }

    @Test
    fun runtimeConstantUpdateTest() {
        "x = 10".toMathExpression()
        val expression2 = "y = 2*x".toMathExpression()
        assertEquals(20.0, expression2.evaluate().value, 0.0)
        "x = 5".toMathExpression()
        assertEquals(10.0, expression2.evaluate().value, 0.0)
    }

    @Test
    fun circularRuntimeConstantDefinitionTest() {
        assertThrowable(
            NumberFormatException::class.java,
            "For input string: \"something\""
        ) { "something = 2*something".toMathExpression() }
    }

    @Test
    fun notInstantiableConstantTest() {
        MathLiteral.registerConstant(ConstantWithNonDefaultConstructor::class.java)
        try {
            assertThrowable(
                NoSuchMethodException::class.java,
                "com.github.vatbub.mathevaluator.MathExpressionTest\$ConstantWithNonDefaultConstructor"
            ) { "5+special+8".toMathExpression() }
        } finally {
            MathLiteral.deregisterConstant(ConstantWithNonDefaultConstructor::class.java)
        }
    }

    @Test
    fun notInstantiableOperatorTest() {
        MathLiteral.registerOperator(OperatorWithNonDefaultConstructor::class.java)
        try {
            assertThrowable(
                NoSuchMethodException::class.java,
                "com.github.vatbub.mathevaluator.MathExpressionTest\$OperatorWithNonDefaultConstructor"
            ) { "5bla8".toMathExpression() }
        } finally {
            MathLiteral.deregisterOperator(OperatorWithNonDefaultConstructor::class.java)
        }
    }

    @Test
    fun notInstantiableFunctionTest() {
        MathLiteral.registerFunction(FunctionWithNonDefaultConstructor::class.java)
        try {
            assertThrowable(
                NoSuchMethodException::class.java,
                "com.github.vatbub.mathevaluator.MathExpressionTest\$FunctionWithNonDefaultConstructor"
            ) { "5*func(8)".toMathExpression() }
        } finally {
            MathLiteral.deregisterFunction(FunctionWithNonDefaultConstructor::class.java)
        }
    }

    @Test
    fun circularReferenceTest1() {
        val expression = "r = 5".toMathExpression()
        assertEquals(5.0, expression.evaluate().value, 0.0)
        assertThrowable(
            IllegalArgumentException::class.java,
            "Circular reference detected"
        ) { "r = r + 1".toMathExpression() }
    }

    @Test
    fun circularReferenceTest2() {
        val expression1 = "r = 5".toMathExpression()
        assertEquals(5.0, expression1.evaluate().value, 0.0)
        val expression2 = "c = r + 1".toMathExpression()
        assertEquals(6.0, expression2.evaluate().value, 0.0)
        assertThrowable(
            IllegalArgumentException::class.java,
            "Circular reference detected"
        ) { "r = c + 1".toMathExpression() }
    }

    @Test
    fun doubleUnaryOperatorTest1() {
        val expression = "7--3".toMathExpression()
        assertEquals(10.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun doubleUnaryOperatorTest2() {
        val expression = "7++3".toMathExpression()
        assertEquals(10.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun doubleUnaryOperatorTest3() {
        val expression = "7---3".toMathExpression()
        assertEquals(4.0, expression.evaluate().value, 0.0)
    }

    @Test
    fun chainedExpressionRegressionTest() {
        val expression = "8+3-5-9".toMathExpression()
        assertEquals(-3.0, expression.evaluate().value, 0.0)
    }

    private fun assertFaultyExpression(expression: String, expectedMessage: String) {
        assertThrowable(
            IllegalArgumentException::class.java,
            expectedMessage
        ) {
            val mathExpression = expression.toMathExpression()
            mathExpression.evaluate()
        }
    }

    private fun <T : Throwable?> assertThrowable(
        expectedException: Class<T>,
        expectedMessage: String,
        delegate: () -> Unit
    ) {
        Assertions.assertThrows(expectedException, {
            delegate()
        }, expectedMessage)
    }

    /**
     * The only purpose of this constructor is to hide the default constructor -->
     * Destroys the reflection mechanism used to instantiate constants
     *
     * @param value dummy argument
     */
    class ConstantWithNonDefaultConstructor(value: MathNumber) : Constant("special", value)

    /**
     * The only purpose of this constructor is to hide the default constructor -->
     * Destroys the reflection mechanism used to instantiate operators
     *
     * @param value dummy argument
     */
    class OperatorWithNonDefaultConstructor(private val value: MathNumber) : Operator("bla", 0) {
        override fun evaluate(leftNumber: MathNumber, rightNumber: MathNumber): MathNumber = value
    }

    /**
     * The only purpose of this constructor is to hide the default constructor -->
     * Destroys the reflection mechanism used to instantiate operators
     *
     * @param value dummy argument
     */
    class FunctionWithNonDefaultConstructor(private val value: MathNumber) : MathFunction("func", 0, 0) {

        override fun evaluateImpl(): MathNumber = value
    }
}
