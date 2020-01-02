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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a mathematical term.
 * Use {@link #MathExpression(String)} to parse a string and {@link #evaluate()} to evaluate the expression.
 */
public class MathExpression extends MathLiteral {
    private List<MathLiteral> expression;

    /**
     * Initializes an empty expression
     */
    public MathExpression() {
        setExpression(null);
    }

    /**
     * Parses the specified term and returns its object representation
     *
     * @param expression The expression to parse
     */
    public MathExpression(String expression) {
        parse(expression);
    }

    private void parse(String expression) {
        expression = expression.replaceAll(" ", "");
        RuntimeConstant runtimeConstant = null;

        // check for runtime constant definitions
        String[] splitAtEqualSign = expression.split("=");
        if (splitAtEqualSign.length > 2)
            throw new IllegalArgumentException("Runtime constant declarations must not contain more than one equal sign");
        if (splitAtEqualSign.length == 2) {
            runtimeConstant = new RuntimeConstant(splitAtEqualSign[0], this);
            expression = splitAtEqualSign[1];
        }

        StringBuffer parseBuffer = new StringBuffer();
        List<MathLiteral> res = new ArrayList<>(expression.length());
        int openBrackets = 0;
        Function pendingFunction = null;

        for (int i = 0; i < expression.length(); i++) {
            String character = expression.substring(i, i + 1);

            if (character.equals("(")) {
                if (openBrackets > 0) // we already have open brackets, add this
                    // one to the parse buffer
                    parseBuffer.append(character);
                else if (parseBuffer.length() > 0) {
                    // there was something in the parse buffer --> implicit multiplication
                    Function f = parseCurrentBuffer(parseBuffer, res, new OperatorImplementations.MultiplyOperator().getFormulaRepresentation());
                    if (f != null)
                        pendingFunction = f;
                }
                openBrackets++;
            } else if (character.equals(")")) {
                if (openBrackets > 1)
                    parseBuffer.append(character);

                openBrackets--;

                if (openBrackets == 0) {
                    if (pendingFunction != null) {
                        String[] params = parseBuffer.toString().split(",");
                        List<MathExpression> parsedParams = new ArrayList<>();

                        for (String param : params)
                            parsedParams.add(new MathExpression(param));

                        pendingFunction.setParams(parsedParams);
                        addItemToListOfMathLiterals(res, pendingFunction);
                        pendingFunction = null;
                    } else {
                        MathExpression mathExpression = new MathExpression(parseBuffer.toString());
                        addItemToListOfMathLiterals(res, mathExpression);
                    }
                    parseBuffer = new StringBuffer();
                }
            } else if (openBrackets > 0) {
                parseBuffer.append(character);
            } else if (pendingFunction != null) {
                // pending function is null but openBrackets == 0 --> functions
                // require parenthesis to declare arguments
                throw new IllegalArgumentException(
                        "Function names must be followed by parenthesis to declare the function parameters");
            } else {
                Function f = parseCurrentBuffer(parseBuffer, res, character);
                if (f != null)
                    pendingFunction = f;
            }
        }

        if (openBrackets != 0)
            throw new IllegalArgumentException("Unbalanced parenthesis");

        // parse the last number in the buffer
        if (parseBuffer.length() > 0)
            parseNumber(parseBuffer.toString(), res);

        setExpression(res);
        if (runtimeConstant != null)
            registerRuntimeConstant(runtimeConstant);
    }

    private Function parseCurrentBuffer(StringBuffer parseBuffer, List<MathLiteral> res, String character) {
        if (Number.isParsableDouble(parseBuffer.toString())
                && !Number.isParsableDouble(parseBuffer.toString()
                + character)) {
            // parseBuffer was previously parsable as a double but when
            // adding the current char, it becomes unparsable --> we
            // need to parse the number now
            parseNumber(parseBuffer.toString(), res);
            parseBuffer.setLength(0);
        }

        parseBuffer.append(character);

        // parse constants
        for (Class<? extends Constant> constantClass : MathLiteral
                .getConstants()) {
            Constant constant;
            try {
                constant = constantClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (parseBuffer.toString().equals(
                    constant.getFormulaRepresentation())) {
                addItemToListOfMathLiterals(res, constant);
                parseBuffer.setLength(0);
                break;
            }
        }

        // parse runtime constants
        for (Map.Entry<String, RuntimeConstant> runtimeConstant : MathLiteral.getRuntimeConstants().entrySet()) {
            if (parseBuffer.toString().equals(runtimeConstant.getKey())) {
                addItemToListOfMathLiterals(res, runtimeConstant.getValue());
                parseBuffer.setLength(0);
                break;
            }
        }

        // parse operators
        for (Class<? extends Operator> operatorClass : MathLiteral
                .getOperators()) {
            Operator operator;
            try {
                operator = operatorClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (parseBuffer.toString().equals(
                    operator.getFormulaRepresentation())
                    && (operator instanceof SingleArgumentOperator || isLastElementANumberOrExpressionOrFunction(res))) {
                addItemToListOfMathLiterals(res, operator);
                parseBuffer.setLength(0);
                break;
            }
        }

        // parse functions
        for (Class<? extends Function> functionClass : MathLiteral
                .getFunctions()) {
            Function function;
            try {
                function = functionClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (parseBuffer.toString().equals(
                    function.getFormulaRepresentation())) {
                parseBuffer.setLength(0);
                return function;
            }
        }

        return null;
    }

    private void addItemToListOfMathLiterals(List<MathLiteral> res, MathLiteral itemToAdd) {
        if (res.size() > 0 && itemToAdd.supportsImplicitMultiplication(res.get(res.size() - 1)))
            // implicit multiplication
            res.add(new OperatorImplementations.MultiplyOperator());

        res.add(itemToAdd);
    }

    private boolean isLastElementANumberOrExpressionOrFunction(@NotNull List<MathLiteral> expression) {
        if (expression.size() == 0)
            return false;
        return isElementANumberOrExpressionOrFunction(expression, expression.size() - 1);
    }

    private boolean isElementANumberOrExpressionOrFunction(@NotNull List<MathLiteral> expression, int index) {
        if (expression.size() == 0)
            return false;
        MathLiteral element = expression.get(index);
        return element instanceof Number || element instanceof MathExpression || element instanceof Constant || element instanceof Function;
    }

    private void parseNumber(String number,
                             List<MathLiteral> expressionToAddNumberTo) {
        expressionToAddNumberTo.add(new Number(Double.parseDouble(number)));
    }

    /**
     * Returns the list of {@link MathLiteral}s that this expression consists of. Usually, the returned expression consists of:
     * <ul>
     * <li>{@link Number}s</li>
     * <li>{@link MathExpression}s</li>
     * <li>{@link Function}s and</li>
     * <li>{@link Constant}s</li>
     * </ul>
     * ... which are separated by {@link Operator}s.
     *
     * @return The list of {@link MathLiteral}s that this expression consists of
     */
    public List<MathLiteral> getExpression() {
        return expression;
    }

    /**
     * Sets the expression represented by this object. This method DOES NOT check the supplied expression for errors.
     *
     * @param expression The expression to set.
     */
    public void setExpression(List<MathLiteral> expression) {
        this.expression = expression;
    }

    /**
     * Evaluates this expression. Please note: The evaluation is performed using a copy of {@link #getExpression()} to avoid
     * modifications of the original expression.
     * If this expression contains other expressions, they will be evaluated recursively.
     *
     * @return The result of this expression.
     * @throws ArrayIndexOutOfBoundsException if this expression fails to evaluate
     */
    public Number evaluate() {
        if (getExpression().size() == 0)
            throw new IllegalArgumentException("Empty expression");

        // eliminate any remaining MathExpressions
        List<MathLiteral> simplifiedExpression = new ArrayList<>(
                getExpression().size());

        for (MathLiteral literal : getExpression()) {
            if (literal instanceof MathExpression)
                literal = ((MathExpression) literal).evaluate();
            else if (literal instanceof Function)
                literal = ((Function) literal).evaluate();
            else if (literal instanceof Constant)
                literal = ((Constant) literal).getValue();

            simplifiedExpression.add(literal);
        }

        // our expression now only contains numbers and operators

        while (simplifiedExpression.size() > 1) {
            // find the operator with the highest priority
            int indexOfOperatorWithHighestPriority = Integer.MIN_VALUE;
            for (int i = 0; i < simplifiedExpression.size(); i++) {
                MathLiteral literal = simplifiedExpression.get(i);
                if (!(literal instanceof Operator))
                    continue;

                if (indexOfOperatorWithHighestPriority == Integer.MIN_VALUE)
                    indexOfOperatorWithHighestPriority = i;
                else {
                    Operator operator = (Operator) literal;
                    if (operator.getPriority() > ((Operator) simplifiedExpression.get(indexOfOperatorWithHighestPriority)).getPriority()
                            || (getOperatorSuperclassPriority(operator) > getOperatorSuperclassPriority((Operator) simplifiedExpression.get(indexOfOperatorWithHighestPriority)) && !isElementANumberOrExpressionOrFunction(simplifiedExpression, i - 1)))
                        indexOfOperatorWithHighestPriority = i;
                }
            }

            if ((indexOfOperatorWithHighestPriority - 1 < 0 || simplifiedExpression
                    .get(indexOfOperatorWithHighestPriority - 1) instanceof Operator)
                    && simplifiedExpression
                    .get(indexOfOperatorWithHighestPriority) instanceof SingleArgumentOperator) {
                SingleArgumentOperator operator = (SingleArgumentOperator) simplifiedExpression
                        .get(indexOfOperatorWithHighestPriority);
                Number right = (Number) simplifiedExpression
                        .get(indexOfOperatorWithHighestPriority + 1);
                simplifiedExpression.set(indexOfOperatorWithHighestPriority,
                        operator.evaluate(right));

                simplifiedExpression
                        .remove(indexOfOperatorWithHighestPriority + 1);
            } else {

                Number left = (Number) simplifiedExpression
                        .get(indexOfOperatorWithHighestPriority - 1);
                Operator operator = (Operator) simplifiedExpression
                        .get(indexOfOperatorWithHighestPriority);
                Number right = (Number) simplifiedExpression
                        .get(indexOfOperatorWithHighestPriority + 1);

                simplifiedExpression.set(
                        indexOfOperatorWithHighestPriority - 1,
                        operator.evaluate(left, right));

                simplifiedExpression
                        .remove(indexOfOperatorWithHighestPriority + 1);
                simplifiedExpression.remove(indexOfOperatorWithHighestPriority);
            }
        }

        return (Number) simplifiedExpression.get(0);
    }

    private int getOperatorSuperclassPriority(Operator operator) {
        return operator instanceof SingleArgumentOperator ? 1 : 0;
    }

    @Override
    public String toString() {
        return getFormulaRepresentation();
    }

    @Override
    public String getFormulaRepresentation() {
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

    @Override
    public boolean supportsImplicitMultiplication(MathLiteral previousLiteral) {
        return previousLiteral instanceof Number || previousLiteral instanceof Constant || previousLiteral instanceof Function || previousLiteral instanceof MathExpression;
    }
}
