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

public abstract class Function extends MathLiteral {
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

	public abstract int getMinNumberOfArguments();

	public abstract int getMaxNumberOfArguments();

	public abstract Number evaluateImpl();

	private List<MathExpression> params;

	public List<MathExpression> getParams() {
		return params;
	}

	public void setParams(List<MathExpression> params) {
		this.params = params;
	}
}
