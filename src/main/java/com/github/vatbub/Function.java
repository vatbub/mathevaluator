package com.github.vatbub;

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
		if (getParams().size() > getMinNumberOfArguments())
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
