package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.MathLiteral

internal fun List<MathLiteral>.isLastElementANumberOrExpressionOrFunction() =
    if (isEmpty()) false
    else isElementANumberOrExpressionOrFunction(size - 1)
