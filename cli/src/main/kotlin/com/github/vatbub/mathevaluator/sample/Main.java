package com.github.vatbub.mathevaluator.sample;

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
import com.github.vatbub.commandlineUserPromptProcessor.parsables.ParsableString;
import com.github.vatbub.commandlineUserPromptProcessor.parsables.ParseException;
import com.github.vatbub.mathevaluator.MathExpression;

public class Main {
    public static final String exitInput = "exit";

    public static void main(String[] args) throws ParseException {
        while (true) {
            Prompt prompt = new Prompt("Please enter your math expression (Hit Ctrl+C or type 'exit' to quit the program)", new ParsableString(exitInput));
            ParsableString input = (ParsableString) prompt.doPrompt();
            if (input.toValue().equals(exitInput))
                break;

            try {
                String inputValue = input.toValue();
                MathExpression expression = new MathExpression(inputValue);
                if (inputValue.contains("="))
                    System.out.println("Variable successfully defined, variable currently evaluates to " + expression.evaluate().getValue());
                else
                    System.out.println("The result is " + expression.evaluate().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
