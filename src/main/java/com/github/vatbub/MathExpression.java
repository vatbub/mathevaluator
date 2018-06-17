package com.github.vatbub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kammel on 15.06.2018.
 */
public class MathExpression implements MathLiteral {
    private List<MathLiteral> expression;

    public MathExpression() {
        setExpression(null);
    }

    public MathExpression(String expression) {
        parse(expression);
    }

    private void parse(String expression) {
        expression = expression.replaceAll(" ", "");
        StringBuffer parseBuffer = new StringBuffer();
        List<MathLiteral> res = new ArrayList<>(expression.length());
        int openBrackets = 0;

        for (int i = 0; i < expression.length(); i++) {
            String character = expression.substring(i, i + 1);
            if (character.equals("(")) {
                if (openBrackets > 0) // we already have open brackets, add this one to the parse buffer
                    parseBuffer.append(character);
                openBrackets++;
            } else if (character.equals(")")) {
                if (openBrackets > 1)
                    parseBuffer.append(character);

                openBrackets--;

                if (openBrackets == 0) {
                    res.add(new MathExpression(parseBuffer.toString()));
                    parseBuffer = new StringBuffer();
                }
            } else if (character.equals("+") && openBrackets == 0) {
                if (parseBuffer.length() > 0) {
                    parseNumber(parseBuffer.toString(), res);
                    parseBuffer = new StringBuffer();
                }
                res.add(new OperatorImplementations.AddOperator());
            } else if (character.equals("-") && openBrackets == 0 && parseBuffer.length() > 0) {
                parseNumber(parseBuffer.toString(), res);
                parseBuffer = new StringBuffer();
                res.add(new OperatorImplementations.SubtractOperator());
            } else if (character.equals("*") && openBrackets == 0) {
                if (parseBuffer.length() > 0) {
                    parseNumber(parseBuffer.toString(), res);
                    parseBuffer = new StringBuffer();
                }
                res.add(new OperatorImplementations.MultiplyOperator());
            } else if (character.equals("/") && openBrackets == 0) {
                if (parseBuffer.length() > 0) {
                    parseNumber(parseBuffer.toString(), res);
                    parseBuffer = new StringBuffer();
                }
                res.add(new OperatorImplementations.DivideOperator());
            } else if (character.equals("^") && openBrackets == 0) {
                if (parseBuffer.length() > 0) {
                    parseNumber(parseBuffer.toString(), res);
                    parseBuffer = new StringBuffer();
                }
                res.add(new OperatorImplementations.PowerOperator());
            } else {
                // character is a number or is inside of a parenthesis
                parseBuffer.append(character);
            }
        }

        if (openBrackets != 0)
            throw new IllegalArgumentException("Unbalanced parenthesis");

        // parse the last number in the buffer
        if (parseBuffer.length() > 0)
            parseNumber(parseBuffer.toString(), res);

        setExpression(res);
    }

    private void parseNumber(String number, List<MathLiteral> expressionToAddNumberTo) {
        expressionToAddNumberTo.add(new Number(Double.parseDouble(number)));
    }

    public List<MathLiteral> getExpression() {
        return expression;
    }

    public void setExpression(List<MathLiteral> expression) {
        this.expression = expression;
    }

    public Number evaluate() {
        if (getExpression().size() == 0)
            throw new IllegalArgumentException("Empty expression");

        // eliminate any remaining MathExpressions
        List<MathLiteral> simplifiedExpression = new ArrayList<>(getExpression().size());

        for (MathLiteral literal : getExpression()) {
            if (literal instanceof MathExpression)
                literal = ((MathExpression) literal).evaluate();

            simplifiedExpression.add(literal);
        }

        // our expression now only contains numbers and operators

        while (simplifiedExpression.size() > 1) {
            // find the operator with the highest priority
            int indexOfOperatorWithHighestPriority = Integer.MIN_VALUE;
            for (int i = 0; i < simplifiedExpression.size(); i++) {
                MathLiteral literal = simplifiedExpression.get(i);
                if (!(literal instanceof Operator)) continue;

                if (indexOfOperatorWithHighestPriority == Integer.MIN_VALUE)
                    indexOfOperatorWithHighestPriority = i;
                else {
                    Operator operator = (Operator) literal;
                    if (operator.getPriority() > ((Operator) simplifiedExpression.get(indexOfOperatorWithHighestPriority)).getPriority())
                        indexOfOperatorWithHighestPriority = i;
                }
            }

            Number left = (Number) simplifiedExpression.get(indexOfOperatorWithHighestPriority - 1);
            Operator operator = (Operator) simplifiedExpression.get(indexOfOperatorWithHighestPriority);
            Number right = (Number) simplifiedExpression.get(indexOfOperatorWithHighestPriority + 1);

            simplifiedExpression.set(indexOfOperatorWithHighestPriority - 1, operator.evaluate(left, right));

            simplifiedExpression.remove(indexOfOperatorWithHighestPriority + 1);
            simplifiedExpression.remove(indexOfOperatorWithHighestPriority);
        }

        return (Number) simplifiedExpression.get(0);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (MathLiteral literal : getExpression()) {
            builder.append(" ");
            if (literal instanceof MathExpression)
                builder.append("(").append(literal.toString()).append(")");
            else
                builder.append(literal.toString());

            builder.append(" ");
        }

        return builder.toString();
    }
}
