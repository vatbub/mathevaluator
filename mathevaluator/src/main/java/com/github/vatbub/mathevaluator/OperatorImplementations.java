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

/**
 * Built-in operators
 */
public abstract class OperatorImplementations {
	public static class AddOperator extends SingleArgumentOperator {

		@Override
		public Number evaluate(Number leftNumber, Number rightNumber) {
			return new Number(leftNumber.getValue() + rightNumber.getValue());
		}

		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public String getFormulaRepresentation() {
			return "+";
		}

		@Override
		public Number evaluate(Number argument) {
			return argument;
		}
	}

	public static class SubtractOperator extends SingleArgumentOperator {

		@Override
		public Number evaluate(Number leftNumber, Number rightNumber) {
			return new Number(leftNumber.getValue() - rightNumber.getValue());
		}

		@Override
		public int getPriority() {
			return 100;
		}

		@Override
		public String getFormulaRepresentation() {
			return "-";
		}

		@Override
		public Number evaluate(Number argument) {
			return new Number(-argument.getValue());
		}
	}

	public static class DivideOperator extends Operator {

		@Override
		public Number evaluate(Number leftNumber, Number rightNumber) {
			return new Number(leftNumber.getValue() / rightNumber.getValue());
		}

		@Override
		public int getPriority() {
			return 200;
		}

		@Override
		public String getFormulaRepresentation() {
			return "/";
		}
	}

	public static class MultiplyOperator extends Operator {

		@Override
		public Number evaluate(Number leftNumber, Number rightNumber) {
			return new Number(leftNumber.getValue() * rightNumber.getValue());
		}

		@Override
		public int getPriority() {
			return 300;
		}

		@Override
		public String getFormulaRepresentation() {
			return "*";
		}
	}

	public static class PowerOperator extends Operator {

		@Override
		public Number evaluate(Number leftNumber, Number rightNumber) {
			return new Number(Math.pow(leftNumber.getValue(),
					rightNumber.getValue()));
		}

		@Override
		public int getPriority() {
			return 400;
		}

		@Override
		public String getFormulaRepresentation() {
			return "^";
		}
	}

	public static void registerBuiltInOperators() {
		MathLiteral.registerOperator(AddOperator.class);
		MathLiteral.registerOperator(SubtractOperator.class);
		MathLiteral.registerOperator(DivideOperator.class);
		MathLiteral.registerOperator(MultiplyOperator.class);
		MathLiteral.registerOperator(PowerOperator.class);
	}
}
