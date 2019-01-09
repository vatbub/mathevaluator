package com.github.vatbub.mathevaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Represents any mathematical item. A literal can be an {@link Operator}, a {@link Number} or a {@link Function}.
 * A {@link MathExpression} is a collection of {@link MathLiteral}s but it's also a {@link MathLiteral} itself
 * (which allows {@link MathExpression}s to be nested e. g. using parenthesis)
 */
public abstract class MathLiteral {
    private static List<Class<? extends Operator>> operators;
    private static List<Class<? extends Function>> functions;
    private static List<Class<? extends Constant>> constants;
    private static Map<String, RuntimeConstant> runtimeConstants;

    public static List<Class<? extends Operator>> getOperators() {
        if (operators == null)
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

    public static Map<String, RuntimeConstant> getRuntimeConstants() {
        if (runtimeConstants == null)
            runtimeConstants = new HashMap<>();
        return runtimeConstants;
    }

    public static void registerOperator(Class<? extends Operator> operator) {
        getOperators().add(operator);
    }

    public static void registerFunction(Class<? extends Function> function) {
        getFunctions().add(function);
    }

    public static void registerConstant(Class<? extends Constant> constant) {
        getConstants().add(constant);
    }

    public static void registerRuntimeConstant(RuntimeConstant runtimeConstant) {
        deregisterRuntimeConstant(runtimeConstant);
        getRuntimeConstants().put(runtimeConstant.getName(), runtimeConstant);
        updateValuesOfRuntimeConstants();
    }

    private static void updateValuesOfRuntimeConstants() {
        for (Map.Entry<String, RuntimeConstant> runtimeConstantEntry : getRuntimeConstants().entrySet()) {
            runtimeConstantEntry.getValue().updateValue();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean deregisterOperator(Class<? extends Operator> operator) {
        return getOperators().remove(operator);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean deregisterFunction(Class<? extends Function> function) {
        return getFunctions().remove(function);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean deregisterConstant(Class<? extends Constant> constant) {
        return getConstants().remove(constant);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean deregisterRuntimeConstant(RuntimeConstant runtimeConstant) {
        return deregisterRuntimeConstant(runtimeConstant.getName());
    }

    public static boolean deregisterRuntimeConstant(String constantName) {
        return getRuntimeConstants().remove(constantName) != null;
    }

    public abstract String getFormulaRepresentation();

    /**
     * Tells the parser whether this literal supports implicit multiplication when preceded by the supplied math literal
     *
     * @param previousLiteral The {@link MathLiteral} that precedes this math literal.
     * @return {@code true} if this math literal supports implicit multiplication, {@code false} otherwise
     */
    public abstract boolean supportsImplicitMultiplication(MathLiteral previousLiteral);

    @Override
    public String toString() {
        return getFormulaRepresentation();
    }
}
