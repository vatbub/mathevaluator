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


import java.util.List;

/**
 * Represents a function (like sine, cosine or sqrt).
 * A function may take no, one or more inputs and computes an output from it.
 * If teh function does not take any input, consider extending {@link Constant} instead.
 */
public abstract class Function extends MathLiteral {
    private List<MathExpression> params;

    /**
     * Called by the evaluator. Checks the number of input arguments and then calls {@link #evaluateImpl()}.
     * Use {@link #setParams(List)} to specify the inputs.
     *
     * @return The result of {@link #evaluateImpl()}
     */
    public final Number evaluate() {
        if (getParams().size() < getMinNumberOfArguments())
            throw new IllegalArgumentException(
                    "Too few arguments for function "
                            + this.getFormulaRepresentation()
                            + ", number of supplied arguments: "
                            + getParams().size()
                            + ", minimum number of arguments: "
                            + this.getMinNumberOfArguments());
        if (getParams().size() > getMaxNumberOfArguments())
            throw new IllegalArgumentException(
                    "Too many arguments for function "
                            + this.getFormulaRepresentation()
                            + ", number of supplied arguments: "
                            + getParams().size()
                            + ", maximum number of arguments: "
                            + this.getMaxNumberOfArguments());

        return this.evaluateImpl();
    }

    /**
     * The minimum number of inputs that this function requires
     *
     * @return The minimum number of inputs that this function requires
     */
    public abstract int getMinNumberOfArguments();

    /**
     * The maximum number of inputs that this function can accept
     *
     * @return The maximum number of inputs that this function can accept
     */
    public abstract int getMaxNumberOfArguments();

    /**
     * Performs the actual computation. Use {@link #getParams()} to get the inputs.
     * <p>
     * There is no need to check the number of input arguments as the number of inout arguments is already verified by {@link #evaluate()}
     * <p>
     * Note: Do not call this method directly, call {@link #evaluate()} instead as it performs additional checks on the inputs!
     *
     * @return The result of the function
     */
    public abstract Number evaluateImpl();

    /**
     * Get the inputs for this function
     *
     * @return The inputs for this function
     */
    public List<MathExpression> getParams() {
        return params;
    }

    /**
     * Set the input arguments for the function
     *
     * @param params The input arguments of the function.
     */
    public void setParams(List<MathExpression> params) {
        this.params = params;
    }
}
