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

public class ConstantImplementationTest {
    @Test
    public void piValueTest() {
        Assert.assertEquals(Math.PI, new ConstantImplementations.PiConstant().getValue().getValue(), 10e-10);
    }

    @Test
    public void eValueTest() {
        Assert.assertEquals(Math.E, new ConstantImplementations.EConstant().getValue().getValue(), 10e-10);
    }

    @Test
    public void piRepresentationTest() {
        Assert.assertEquals("pi", new ConstantImplementations.PiConstant().getFormulaRepresentation());
    }

    @Test
    public void eRepresentationTest() {
        Assert.assertEquals("e", new ConstantImplementations.EConstant().getFormulaRepresentation());
    }
}
