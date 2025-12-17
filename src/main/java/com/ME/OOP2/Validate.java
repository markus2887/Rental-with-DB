package com.ME.OOP2;

import javafx.scene.control.TextField;

public class Validate {

    public void isInt(TextField input, String prompt) {
        try {
            int number = Integer.parseInt(input.getText());
            input.setStyle(null);
            input.setPromptText(prompt);
        }
        catch(NumberFormatException e) {
            input.setStyle("-fx-border-color:red;");
            input.clear();
            input.setPromptText("FEL: Du måste mata in siffror!");
        }
    }

    public void isDouble(TextField input, String prompt) {
        try {
            double number = Double.parseDouble(input.getText());
            input.setPromptText(prompt);
            input.setStyle(null);
        }
        catch (NumberFormatException e) {
            input.setStyle("-fx-border-color:red;");
            input.clear();
            input.setPromptText("FEL: Du måste mata in siffror!");
        }
    }

}
