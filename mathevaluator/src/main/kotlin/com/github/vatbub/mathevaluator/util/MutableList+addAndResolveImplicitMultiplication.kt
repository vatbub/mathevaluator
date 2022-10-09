package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.MathLiteral
import com.github.vatbub.mathevaluator.MultiplyOperator

internal fun MutableList<MathLiteral>.addAndResolveImplicitMultiplication(itemToAdd: MathLiteral) {
    if (isNotEmpty() && itemToAdd.supportsImplicitMultiplication(this[size - 1])) { // implicit multiplication
        add(MultiplyOperator())
    }
    add(itemToAdd)
}
