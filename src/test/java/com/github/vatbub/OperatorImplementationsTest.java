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

public class OperatorImplementationsTest {
    @Test
    public void addTwoInputsTest() {
        final double[] inputs1 = {1, 2, 3, 4, 5};
        final double[] inputs2 = {1, 2, 3, 4, 5};
        final double[] expected = {2, 4, 6, 8, 10};

        for (int i = 0; i < inputs1.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.AddOperator().evaluate(new Number(inputs1[i]), new Number(inputs2[i])).getValue(), 0);
    }

    @Test
    public void addOneInputTest() {
        final double[] inputs = {1, 2, 3, 4, 5};
        final double[] expected = {1, 2, 3, 4, 5};

        for (int i = 0; i < inputs.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.AddOperator().evaluate(new Number(inputs[i])).getValue(), 0);
    }

    @Test
    public void addPriorityTest() {
        Assert.assertEquals(0, new OperatorImplementations.AddOperator().getPriority());
    }

    @Test
    public void addRepresentationTest() {
        Assert.assertEquals("+", new OperatorImplementations.AddOperator().getFormulaRepresentation());
    }

    @Test
    public void subtractTwoInputsTest() {
        final double[] inputs1 = {1, 2, 3, 4, 5};
        final double[] inputs2 = {5, 4, 3, 2, 1};
        final double[] expected = {-4, -2, 0, 2, 4};

        for (int i = 0; i < inputs1.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.SubtractOperator().evaluate(new Number(inputs1[i]), new Number(inputs2[i])).getValue(), 0);
    }

    @Test
    public void subtractOneInputTest() {
        final double[] inputs = {1, 2, 3, 4, 5};
        final double[] expected = {-1, -2, -3, -4, -5};

        for (int i = 0; i < inputs.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.SubtractOperator().evaluate(new Number(inputs[i])).getValue(), 0);
    }

    @Test
    public void subtractPriorityTest() {
        Assert.assertEquals(1, new OperatorImplementations.SubtractOperator().getPriority());
    }

    @Test
    public void subtractRepresentationTest() {
        Assert.assertEquals("-", new OperatorImplementations.SubtractOperator().getFormulaRepresentation());
    }

    @Test
    public void divideTwoInputsTest() {
        final double[] inputs1 = {1, 2, 3, 4, 5};
        final double[] inputs2 = {5, 4, 3, 2, 1};
        final double[] expected = {1.0 / 5.0, 1.0 / 2.0, 1, 2, 5};

        for (int i = 0; i < inputs1.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.DivideOperator().evaluate(new Number(inputs1[i]), new Number(inputs2[i])).getValue(), 0);
    }

    @Test
    public void dividePriorityTest() {
        Assert.assertEquals(2, new OperatorImplementations.DivideOperator().getPriority());
    }

    @Test
    public void divideRepresentationTest() {
        Assert.assertEquals("/", new OperatorImplementations.DivideOperator().getFormulaRepresentation());
    }

    @Test
    public void multiplyTwoInputsTest() {
        final double[] inputs1 = {1, 2, 3, 4, 5};
        final double[] inputs2 = {5, 4, 3, 2, 1};
        final double[] expected = {5, 8, 9, 8, 5};

        for (int i = 0; i < inputs1.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.MultiplyOperator().evaluate(new Number(inputs1[i]), new Number(inputs2[i])).getValue(), 0);
    }

    @Test
    public void multiplyPriorityTest() {
        Assert.assertEquals(3, new OperatorImplementations.MultiplyOperator().getPriority());
    }

    @Test
    public void multiplyRepresentationTest() {
        Assert.assertEquals("*", new OperatorImplementations.MultiplyOperator().getFormulaRepresentation());
    }

    @Test
    public void powerTwoInputsTest() {
        final double[] inputs1 = {1, 2, 3, 4, 5};
        final double[] inputs2 = {5, 4, 3, 2, 1};
        final double[] expected = {1, 16, 27, 16, 5};

        for (int i = 0; i < inputs1.length; i++)
            Assert.assertEquals(expected[i], new OperatorImplementations.PowerOperator().evaluate(new Number(inputs1[i]), new Number(inputs2[i])).getValue(), 0);
    }

    @Test
    public void powerPriorityTest() {
        Assert.assertEquals(4, new OperatorImplementations.PowerOperator().getPriority());
    }

    @Test
    public void powerRepresentationTest() {
        Assert.assertEquals("^", new OperatorImplementations.PowerOperator().getFormulaRepresentation());
    }
}
