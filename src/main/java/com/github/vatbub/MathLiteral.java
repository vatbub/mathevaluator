package com.github.vatbub;

import java.util.ArrayList;
import java.util.List;

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
 * Created by Kammel on 15.06.2018.
 */
public abstract class MathLiteral {
	public abstract String getFormulaRepresentation();
	
	@Override
    public String toString() {
        return getFormulaRepresentation();
    }
	
	public int getMinimumParseBufferLength(){
		return 1;
	}
	
	public static List<Class<? extends Operator>> getOperators() {
		if (operators==null)
			operators = new ArrayList<>();
		return operators;
	}

	public static List<Class<? extends Function>> getFunctions() {
		if (functions == null)
			functions = new ArrayList<>();
		return functions;
	}
	
	public static List<Class<? extends Constant>> getConstants() {
		if (constants == null)
			constants = new ArrayList<>();
		return constants;
	}
	
	public static void registerOperator(Class<? extends Operator> operator){
		getOperators().add(operator);
	}
	
	public static void registerFunction(Class<? extends Function> function){
		getFunctions().add(function);
	}
	
	public static void registerConstant(Class<? extends Constant> constant){
		getConstants().add(constant);
	}

	private static List<Class<? extends Operator>> operators;
	private static List<Class<? extends Function>> functions;
	private static List<Class<? extends Constant>> constants;
}
