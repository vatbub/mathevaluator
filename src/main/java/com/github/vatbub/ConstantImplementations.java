package com.github.vatbub;

public abstract class ConstantImplementations {
	public static class PiConstant extends Constant {

		@Override
		public Number getValue() {
			return new Number(Math.PI);
		}

		@Override
		public String getFormulaRepresentation() {
			return "pi";
		}
	}
	
	public static class EConstant extends Constant {

		@Override
		public Number getValue() {
			return new Number(Math.E);
		}

		@Override
		public String getFormulaRepresentation() {
			return "e";
		}
	}
	
	public static void registerBuiltInConstants(){
		MathLiteral.registerConstant(PiConstant.class);
		MathLiteral.registerConstant(EConstant.class);
	}
}
