package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.*

internal fun List<MathLiteral>.isElementANumberOrExpressionOrFunction(index: Int): Boolean {
    if (isEmpty()) return false
    val element = this[index]
    return element is MathNumber || element is MathExpression || element is Constant || element is RuntimeConstant || element is MathFunction
}
