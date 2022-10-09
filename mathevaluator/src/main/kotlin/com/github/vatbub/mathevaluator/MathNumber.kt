/*-
 * #%L
 * math-evaluator
 * %%
 * Copyright (C) 2019 - 2022 Frederik Kammel
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
package com.github.vatbub.mathevaluator

import java.util.regex.Pattern

/**
 * Wrapper for a double that extends the [MathLiteral] class
 */
data class MathNumber
constructor(val value: Double = 0.0) : MathLiteral() {

    override fun toString(): String = formulaRepresentation

    override val formulaRepresentation: String
        get() = value.toString()

    override fun supportsImplicitMultiplication(previousLiteral: MathLiteral): Boolean = false

    companion object {
        @JvmStatic
        fun isParsableDouble(string: String): Boolean {
            val digits = "(\\p{Digit}+)"
            val hexDigits = "(\\p{XDigit}+)"
            // an exponent is 'e' or 'E' followed by an optionally
            // signed decimal integer.
            val exponent = "[eE][+-]?$digits"
            val fpRegex = "[\\x00-\\x20]*" + // Optional leading
                // "whitespace"
                "[+-]?(" + // Optional sign character
                "NaN|" + // "NaN" string
                "Infinity|" + // "Infinity" string
                // A decimal floating-point string representing a finite
                // positive
                // number without a leading sign has at most five basic pieces:
                // Digits . Digits ExponentPart FloatTypeSuffix
                //
                // Since this method allows integer-only strings as input
                // in addition to strings of floating-point literals, the
                // two sub-patterns below are simplifications of the grammar
                // productions from section 3.10.2 of
                // The Java Language Specification.
                // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                "(((" + digits + "(\\.)?(" + digits + "?)(" + exponent + ")?)|" + // . Digits ExponentPart_opt FloatTypeSuffix_opt
                "(\\.(" + digits + ")(" + exponent + ")?)|" + // Hexadecimal strings
                "((" + // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                "(0[xX]" + hexDigits + "(\\.)?)|" + // 0[xX] HexDigits_opt . HexDigits BinaryExponent
                // FloatTypeSuffix_opt
                "(0[xX]" + hexDigits + "?(\\.)" + hexDigits + ")" +
                ")[pP][+-]?" + digits + "))" + "[fFdD]?))" + "[\\x00-\\x20]*" // Optional
            // trailing
            // "whitespace"
            return Pattern.matches(fpRegex, string)
        }
    }
}

fun Double.toMathLiteral() = MathNumber(this)
