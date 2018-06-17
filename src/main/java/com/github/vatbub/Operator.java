package com.github.vatbub;

public interface Operator extends MathLiteral {
    Number evaluate(Number leftNumber, Number rightNumber);

    int getPriority();
}
