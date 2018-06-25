package com.github.vatbub;

public abstract class FunctionImplementations {
	public static class SquareRootFunction extends Function {

		@Override
		public Number evaluateImpl() {
			return new Number(Math.sqrt(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "sqrt";
		}

		@Override
		public int getMinNumberOfArguments() {
			return 1;
		}

		@Override
		public int getMaxNumberOfArguments() {
			return 1;
		}
	}
	
	public static void registerBuiltInFunctions(){
		MathLiteral.registerFunction(SquareRootFunction.class);
	}
}
