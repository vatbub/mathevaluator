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


import com.github.vatbub.mathevaluator.Number;
import org.junit.Assert;
import org.junit.Test;

public class NumberTest {

    @Test
    public void defaultConstructorTest() {
        Assert.assertEquals(0, new Number().getValue(), 0);
    }

    @Test
    public void valueConstructorTest() {
        double[] inputs = {0, 1.25, 23456.8, -1};
        for (double input : inputs)
            Assert.assertEquals(input, new Number(input).getValue(), 0);
    }

    @Test
    public void setValueTest() {
        double[] inputs = {0, 1.25, 23456.8, -1};
        for (double input : inputs) {
            Number number = new Number();
            number.setValue(input);
            Assert.assertEquals(input, number.getValue(), 0);
        }
    }

    @Test
    public void representationTest(){
        double[] inputs = {0, 1.25, 23456.8, -1};
        for (double input : inputs)
            Assert.assertEquals(Double.toString(input), new Number(input).getFormulaRepresentation());
    }

    @Test
    public void toStringTest(){
        double[] inputs = {0, 1.25, 23456.8, -1};
        for (double input : inputs)
            Assert.assertEquals(Double.toString(input), new Number(input).toString());
    }

    @Test
    public void isParsableTest(){
        String[] inputs = {"687.4", "623,9", "*9653", "65467ü", "468.1e10", "134f"};
        boolean[] expected = {true, false, false, false, true, true};
        for (int i=0; i<inputs.length; i++) {
            System.out.println("Testing string: \"" + inputs[i] + "\"");
            Assert.assertEquals(expected[i], Number.isParsableDouble(inputs[i]));
        }
    }
}
