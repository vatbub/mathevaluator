package com.github.vatbub.mathevaluator;

import java.util.Objects;

public class RuntimeConstant extends Constant {
    private String name;
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
