package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.MathExpression
import com.github.vatbub.mathevaluator.MathLiteral
import com.github.vatbub.mathevaluator.RuntimeConstant

internal fun RuntimeConstant.checkForCircularReferences(literal: MathLiteral) {
    if (literal is RuntimeConstant) {
        if (literal.name == this.name) throw IllegalArgumentException("Circular reference detected")
        checkForCircularReferences(literal.expression)
    } else if (literal is MathExpression) {
        literal.expression.forEach { item ->
            checkForCircularReferences(item)
        }
    }
}
