package sample;
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

import com.github.vatbub.commandlineUserPromptProcessor.Prompt;
import com.github.vatbub.commandlineUserPromptProcessor.parsers.StringParser;
import com.github.vatbub.mathevaluator.MathExpression;

import java.util.Objects;

class JavaSample {
    public static void main(String[] args) {
        String exitInput = "exit";

        System.out.println("Math evaluator (Java sample)");

        while (true) {
            Prompt<String> prompt = new Prompt<>(
                    "Please enter your math expression (Hit Ctrl+C or type 'exit' to quit the program)",
                    new StringParser()
            );
            String input = prompt.doPrompt(exitInput);
            if (Objects.equals(input, exitInput)) break;

            try {
                MathExpression expression = new MathExpression(input);
                if (input.contains("=")) {
                    System.out.println("Variable successfully defined, variable currently evaluates to " + expression.evaluate().getValue());
                } else {
                    System.out.println("The result is " + expression.evaluate().getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
