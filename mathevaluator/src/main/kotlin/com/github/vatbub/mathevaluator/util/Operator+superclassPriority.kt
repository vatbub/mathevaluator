package com.github.vatbub.mathevaluator.util

import com.github.vatbub.mathevaluator.Operator
import com.github.vatbub.mathevaluator.SingleArgumentOperator

internal val Operator.superclassPriority: Int
    get() = if (this is SingleArgumentOperator) 1 else 0
