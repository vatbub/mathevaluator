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

import java.util.ArrayList;
import java.util.List;

public class FunctionImplementationsTest {
    private static double simpleFactorial(double n) {
        if (n == 0)
            return 1;
        else
            return n * simpleFactorial(n - 1);
    }

    @Test
    public void squareRootTest() {
        final double[] inputs = {4, 9, 16, 25};
        final double[] results = {2, 3, 4, 5};

        testOneParamFunctionFunction(new FunctionImplementations.SquareRootFunction(), inputs, results);
    }

    @Test
    public void sineTest() {
        final double[] inputs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI};
        final double[] results = {0, 1, 0, -1, 0};

        testOneParamFunctionFunction(new FunctionImplementations.SineFunction(), inputs, results);
    }

    @Test
    public void cosineTest() {
        final double[] inputs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI};
        final double[] results = {1, 0, -1, 0, 1};

        testOneParamFunctionFunction(new FunctionImplementations.CosineFunction(), inputs, results);
    }

    @Test
    public void tangentTest() {
        final double[] inputs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI};
        final double[] results = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++)
            results[i] = Math.tan(inputs[i]);

        testOneParamFunctionFunction(new FunctionImplementations.TangentFunction(), inputs, results);
    }

    @Test
    public void cotangentTest() {
        final double[] inputs = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI};
        final double[] results = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++)
            results[i] = 1 / Math.tan(inputs[i]);

        testOneParamFunctionFunction(new FunctionImplementations.CotangentFunction(), inputs, results);
    }

    @Test
    public void factorialTest() {
        final double[] inputs = {5, 10, 60};
        final double[] results = new double[inputs.length];

        for (int i = 0; i < inputs.length; i++)
            results[i] = simpleFactorial(inputs[i]);

        testOneParamFunctionFunction(new FunctionImplementations.FactorialFunction(), inputs, results);
    }

    @Test
    public void factorialNegativeInputTest() {
        try {
            testFunction(new FunctionImplementations.FactorialFunction(), 0, -1);
            Assert.fail("IllegalArgumentException expected!");
        } catch (IllegalArgumentException e) {
            System.out.println("Expected IllegalArgumentException was thrown:");
            e.printStackTrace(System.out);
        }
    }

    @Test
    public void factorialDoubleInputTest() {
        try {
            testFunction(new FunctionImplementations.FactorialFunction(), 0, 1.5);
            Assert.fail("IllegalArgumentException expected!");
        } catch (IllegalArgumentException e) {
            System.out.println("Expected IllegalArgumentException was thrown:");
            e.printStackTrace(System.out);
        }
    }

    @Test
    public void squareRootRepresentationTest() {
        Assert.assertEquals("sqrt", new FunctionImplementations.SquareRootFunction().getFormulaRepresentation());
    }

    @Test
    public void sineRepresentationTest() {
        Assert.assertEquals("sin", new FunctionImplementations.SineFunction().getFormulaRepresentation());
    }

    @Test
    public void cosineRepresentationTest() {
        Assert.assertEquals("cos", new FunctionImplementations.CosineFunction().getFormulaRepresentation());
    }

    @Test
    public void tangentRepresentationTest() {
        Assert.assertEquals("tan", new FunctionImplementations.TangentFunction().getFormulaRepresentation());
    }

    @Test
    public void cotangentRepresentationTest() {
        Assert.assertEquals("cot", new FunctionImplementations.CotangentFunction().getFormulaRepresentation());
    }

    @Test
    public void factorialRepresentationTest() {
        Assert.assertEquals("factorial", new FunctionImplementations.FactorialFunction().getFormulaRepresentation());
    }

    private void testOneParamFunctionFunction(Function function, double[] inputs, double[] expectedOutputs) {
        for (int i = 0; i < inputs.length; i++)
            testFunction(function, expectedOutputs[i], inputs[i]);
    }

    private void testFunction(Function function, double expectedResult, double... inputs) {
        List<MathExpression> params = new ArrayList<>();

        for (double input : inputs) {
            MathExpression expression = new MathExpression();
            expression.setExpression(new ArrayList<>());
            expression.getExpression().add(new Number(input));
            params.add(expression);
        }

        function.setParams(params);
        Assert.assertEquals(expectedResult, function.evaluate().getValue(), Math.pow(10, -10));
    }
}
