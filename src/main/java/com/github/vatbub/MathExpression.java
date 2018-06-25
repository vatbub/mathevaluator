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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpression extends MathLiteral {
	private List<MathLiteral> expression;

	static {
		OperatorImplementations.registerBuiltInOperators();
		FunctionImplementations.registerBuiltInFunctions();
	}

	public MathExpression() {
		setExpression(null);
	}

	public MathExpression(String expression) {
		parse(expression);
	}

	private void parse(String expression) {
		expression = expression.replaceAll(" ", "");
		StringBuffer parseBuffer = new StringBuffer();
		List<MathLiteral> res = new ArrayList<>(expression.length());
		int openBrackets = 0;
		boolean previousElementIsNumberOrExpression = false;
		Function pendingFunction = null;

		for (int i = 0; i < expression.length(); i++) {
			String character = expression.substring(i, i + 1);

			if (character.equals("(")) {
				if (openBrackets > 0) // we already have open brackets, add this
										// one to the parse buffer
					parseBuffer.append(character);
				openBrackets++;
			} else if (character.equals(")")) {
				if (openBrackets > 1)
					parseBuffer.append(character);

				openBrackets--;

				if (openBrackets == 0) {
					if (pendingFunction != null) {
						String[] params = parseBuffer.toString().split(",");
						List<MathExpression> parsedParams = new ArrayList<>();

						for (String param : params)
							parsedParams.add(new MathExpression(param));

						pendingFunction.setParams(parsedParams);
						res.add(pendingFunction);
						pendingFunction = null;
					} else {
						res.add(new MathExpression(parseBuffer.toString()));
					}
					parseBuffer = new StringBuffer();
					previousElementIsNumberOrExpression = true;
				}
			} else if (openBrackets > 0) {
				parseBuffer.append(character);
				previousElementIsNumberOrExpression = false;
			} else if (pendingFunction != null) {
				// pending function is null but openBrackets == 0 --> functions
				// require parenthesis to declare arguments
				throw new IllegalArgumentException(
						"Function names must be followed by parenthesis to declare the function parameters");
			} else {
				if (Number.isParsableDouble(parseBuffer.toString())
						&& !Number.isParsableDouble(parseBuffer.toString()
								+ character)) {
					// parseBuffer was previously parsable as a double but when
					// adding the current char, it becomes unparsable --> we
					// need to parse the number now
					parseNumber(parseBuffer.toString(), res);
					parseBuffer = new StringBuffer();
					previousElementIsNumberOrExpression = true;
				}

				parseBuffer.append(character);

				// parse operators
				for (Class<? extends Operator> operatorClass : MathLiteral
						.getOperators()) {
					Operator operator;
					try {
						operator = operatorClass.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
					if (parseBuffer.toString().equals(
							operator.getFormulaRepresentation())
							&& (previousElementIsNumberOrExpression || operator instanceof SingleArgumentOperator)) {
						res.add(operator);
						parseBuffer = new StringBuffer();
						previousElementIsNumberOrExpression = false;
						break;
					}
				}

				// parse functions
				for (Class<? extends Function> functionClass : MathLiteral
						.getFunctions()) {
					Function function;
					try {
						function = functionClass.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
					if (parseBuffer.toString().equals(
							function.getFormulaRepresentation())) {
						pendingFunction = function;
						parseBuffer = new StringBuffer();
						previousElementIsNumberOrExpression = false;
						break;
					}
				}
			}
		}

		if (openBrackets != 0)
			throw new IllegalArgumentException("Unbalanced parenthesis");

		// parse the last number in the buffer
		if (parseBuffer.length() > 0)
			parseNumber(parseBuffer.toString(), res);

		setExpression(res);
	}

	private void parseNumber(String number,
			List<MathLiteral> expressionToAddNumberTo) {
		expressionToAddNumberTo.add(new Number(Double.parseDouble(number)));
	}

	public List<MathLiteral> getExpression() {
		return expression;
	}

	public void setExpression(List<MathLiteral> expression) {
		this.expression = expression;
	}

	public Number evaluate() {
		if (getExpression().size() == 0)
			throw new IllegalArgumentException("Empty expression");

		// eliminate any remaining MathExpressions
		List<MathLiteral> simplifiedExpression = new ArrayList<>(
				getExpression().size());

		for (MathLiteral literal : getExpression()) {
			if (literal instanceof MathExpression)
				literal = ((MathExpression) literal).evaluate();
			else if (literal instanceof Function)
				literal = ((Function) literal).evaluate();

			simplifiedExpression.add(literal);
		}

		// our expression now only contains numbers and operators

		while (simplifiedExpression.size() > 1) {
			// find the operator with the highest priority
			int indexOfOperatorWithHighestPriority = Integer.MIN_VALUE;
			for (int i = 0; i < simplifiedExpression.size(); i++) {
				MathLiteral literal = simplifiedExpression.get(i);
				if (!(literal instanceof Operator))
					continue;

				if (indexOfOperatorWithHighestPriority == Integer.MIN_VALUE)
					indexOfOperatorWithHighestPriority = i;
				else {
					Operator operator = (Operator) literal;
					if (operator.getPriority() > ((Operator) simplifiedExpression
							.get(indexOfOperatorWithHighestPriority))
							.getPriority())
						indexOfOperatorWithHighestPriority = i;
				}
			}

			if ((indexOfOperatorWithHighestPriority - 1 < 0 || simplifiedExpression
					.get(indexOfOperatorWithHighestPriority - 1) instanceof Operator)
					&& simplifiedExpression
							.get(indexOfOperatorWithHighestPriority) instanceof SingleArgumentOperator) {
				SingleArgumentOperator operator = (SingleArgumentOperator) simplifiedExpression
						.get(indexOfOperatorWithHighestPriority);
				Number right = (Number) simplifiedExpression
						.get(indexOfOperatorWithHighestPriority + 1);
				simplifiedExpression.set(indexOfOperatorWithHighestPriority,
						operator.evaluate(right));

				simplifiedExpression
						.remove(indexOfOperatorWithHighestPriority + 1);
			} else {

				Number left = (Number) simplifiedExpression
						.get(indexOfOperatorWithHighestPriority - 1);
				Operator operator = (Operator) simplifiedExpression
						.get(indexOfOperatorWithHighestPriority);
				Number right = (Number) simplifiedExpression
						.get(indexOfOperatorWithHighestPriority + 1);

				simplifiedExpression.set(
						indexOfOperatorWithHighestPriority - 1,
						operator.evaluate(left, right));

				simplifiedExpression
						.remove(indexOfOperatorWithHighestPriority + 1);
				simplifiedExpression.remove(indexOfOperatorWithHighestPriority);
			}
		}

		return (Number) simplifiedExpression.get(0);
	}

	@Override
	public String toString() {
		return getFormulaRepresentation();
	}

	@Override
	public String getFormulaRepresentation() {
		StringBuilder builder = new StringBuilder();
		for (MathLiteral literal : getExpression()) {
			builder.append(" ");
			if (literal instanceof MathExpression)
				builder.append("(").append(literal.toString()).append(")");
			else
				builder.append(literal.toString());

			builder.append(" ");
		}

		return builder.toString();
	}
}
