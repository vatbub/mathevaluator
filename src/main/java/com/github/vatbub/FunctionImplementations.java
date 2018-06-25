package com.github.vatbub;

public abstract class FunctionImplementations {
	public static abstract class OneArgumentFunction extends Function{
		@Override
		public int getMinNumberOfArguments() {
			return 1;
		}

		@Override
		public int getMaxNumberOfArguments() {
			return 1;
		}
	}
	
	public static class SquareRootFunction extends OneArgumentFunction {

		@Override
		public Number evaluateImpl() {
			return new Number(Math.sqrt(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "sqrt";
		}
	}
	
	public static class SineFunction extends OneArgumentFunction {

		@Override
		public Number evaluateImpl() {
			return new Number(Math.sin(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "sin";
		}
	}
	
	public static class CosineFunction extends OneArgumentFunction {

		@Override
		public Number evaluateImpl() {
			return new Number(Math.cos(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "cos";
		}
	}
	
	public static class TangentFunction extends OneArgumentFunction {

		@Override
		public Number evaluateImpl() {
			return new Number(Math.tan(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "tan";
		}
	}
	
	public static class CotangentFunction extends OneArgumentFunction {

		@Override
		public Number evaluateImpl() {
			return new Number(1 / Math.tan(getParams().get(0).evaluate().getValue()));
		}

		@Override
		public String getFormulaRepresentation() {
			return "cot";
		}
	}
	
	public static void registerBuiltInFunctions(){
		MathLiteral.registerFunction(SquareRootFunction.class);
		MathLiteral.registerFunction(SineFunction.class);
		MathLiteral.registerFunction(CosineFunction.class);
	}
}
