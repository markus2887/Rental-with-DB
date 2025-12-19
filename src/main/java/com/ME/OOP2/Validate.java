package com.ME.OOP2;

import com.ME.OOP2.entity.Rental;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Validate {
RentalService rSer = new RentalService();

    public Validate() throws Exception {
    }

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

    public void runTime(TextField input1, TextField input2, TableView<Rental> rTable) {
        try {
            rSer.rentButtonClicked(input1, input2, rTable);
            input1.setStyle(null);
        } catch (Exception ex) {
            if (input1.getText().trim().isEmpty()) {
                input1.setStyle("-fx-border-color:red;");
                input1.clear();
                input1.setPromptText("FEL: Du måste mata in något!");
            } else if (input2.getText().trim().isEmpty()) {
                input2.setStyle("-fx-border-color:red;");
                input2.clear();
                input2.setPromptText("FEL: Du måste mata in något!");
            } else {

            }
            throw new RuntimeException(ex);
        }
    }
}
