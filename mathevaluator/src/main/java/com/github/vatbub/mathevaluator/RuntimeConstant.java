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


import java.util.Objects;

public class RuntimeConstant extends Constant {
    private final String name;
    private MathExpression expression;

    public RuntimeConstant(String name, MathExpression expression) {
        this.name = name;
        setExpression(expression);
    }

    @Override
    public Number getValue() {
        return getExpression().evaluate();
    }

    @Override
    public String getFormulaRepresentation() {
        return getName();
    }

    public MathExpression getExpression() {
        return expression;
    }

    public void setExpression(MathExpression expression) {
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void updateValue() {
        updateValue(getExpression());
    }

    private void updateValue(MathExpression expression) {
        for (int i = 0; i < expression.getExpression().size(); i++) {
            MathLiteral literal = expression.getExpression().get(i);

            if (literal instanceof MathExpression)
                updateValue((MathExpression) literal);
            else if (literal instanceof RuntimeConstant) {
                expression.getExpression().set(i, MathLiteral.getRuntimeConstants().get(((RuntimeConstant) literal).getName()));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuntimeConstant that = (RuntimeConstant) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }
}
