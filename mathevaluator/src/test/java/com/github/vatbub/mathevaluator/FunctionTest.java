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


import com.github.vatbub.mathevaluator.Function;
import com.github.vatbub.mathevaluator.MathExpression;
import com.github.vatbub.mathevaluator.Number;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FunctionTest {
    @Test
    public void tooFewArgumentsTest() {
        Function function = getAnonymousFunction();

        MathExpression expression = new MathExpression("2");
        List<MathExpression> params = new ArrayList<>();
        params.add(expression);
        function.setParams(params);

        assertIllegalArgumentException(function);
    }

    private Function getAnonymousFunction() {
        return new Function() {
            @Override
            public int getMinNumberOfArguments() {
                return 2;
            }

            @Override
            public int getMaxNumberOfArguments() {
                return 2;
            }

            @Override
            public Number evaluateImpl() {
                return null;
            }

            @Override
            public String getFormulaRepresentation() {
                return null;
            }
        };
    }

    private void assertIllegalArgumentException(Function function){
        try {
            function.evaluate();
            Assert.fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            System.out.println("Expected IllegalArgumentException encountered");
        }
    }

    @Test
    public void tooManyArgumentsTest() {
        Function function = getAnonymousFunction();

        List<MathExpression> params = new ArrayList<>();

        for (int i = 0; i < 3; i++)
            params.add(new MathExpression(Integer.toString(i)));
        function.setParams(params);

        assertIllegalArgumentException(function);
    }

    @Test
    public void successfulEValuateCallTest(){
        final boolean[] called = {false};
        Function function = new Function() {
            @Override
            public int getMinNumberOfArguments() {
                return 1;
            }

            @Override
            public int getMaxNumberOfArguments() {
                return 1;
            }

            @Override
            public Number evaluateImpl() {
                called[0] = true;
                return null;
            }

            @Override
            public String getFormulaRepresentation() {
                return null;
            }
        };

        MathExpression expression = new MathExpression("2");
        List<MathExpression> params = new ArrayList<>();
        params.add(expression);
        function.setParams(params);
        function.evaluate();

        Assert.assertTrue(called[0]);
    }
}
