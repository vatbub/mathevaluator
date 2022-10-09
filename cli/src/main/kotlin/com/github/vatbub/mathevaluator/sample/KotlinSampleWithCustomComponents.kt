package com.github.vatbub.mathevaluator.sample
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
import com.github.vatbub.commandlineUserPromptProcessor.Prompt
import com.github.vatbub.commandlineUserPromptProcessor.parsers.StringParser
import com.github.vatbub.mathevaluator.*
import kotlin.random.Random

fun main() {
    println("Math evaluator (Kotlin sample with custom components)")

    MathLiteral.registerFunction(RandomFunction::class.java)

    while (true) {
        val prompt = Prompt(
            "Please enter your math expression (Hit Ctrl+C or type 'exit' to quit the program)",
            StringParser()
        )
        val input = prompt.doPrompt(exitInput)
        if (input == exitInput) break
        try {
            val expression = input.toMathExpression()
            if (input.contains("=")) {
                println("Variable successfully defined, variable currently evaluates to " + expression.evaluate().value)
            } else {
                println("The result is " + expression.evaluate().value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

class RandomFunction : MathFunction("rnd", 0, 0) {
    override fun evaluateImpl(): MathNumber = Random.nextDouble().toMathLiteral()
}
