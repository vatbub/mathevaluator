package com.github.vatbub;

public class Number implements MathLiteral {
    private double value;

    public Number() {
        this(0);
    }

    public Number(double value) {
        setValue(value);
    }

    public java.lang.Double getValue() {
        return value;
    }

    public void setValue(java.lang.Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
