package com.github.vatbub;

import com.github.vatbub.commandlineUserPromptProcessor.Prompt;
import com.github.vatbub.commandlineUserPromptProcessor.parsables.ParsableString;
import com.github.vatbub.commandlineUserPromptProcessor.parsables.ParseException;

public class Main {
    public static final String exitInput = "exit";
    public static void main(String[] args) throws ParseException {
        while(true){
            Prompt prompt = new Prompt("Please enter your math expression (enter an empty expression to exit)", new ParsableString(exitInput));
            ParsableString input = (ParsableString) prompt.doPrompt();
            if (input.toValue().equals(exitInput))
                break;

            try {
                MathExpression expression = new MathExpression(input.toValue());
                System.out.println("The result is " + expression.evaluate().getValue());
            }catch(IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    }
}
