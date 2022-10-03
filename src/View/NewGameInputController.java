package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewGameInputController {
    /**
     * The controller of the main window
     */
    private MyViewController controller;
    /**
     * NewGameInput text field
     */
    @FXML
    private TextField firstInput;
    /**
     * NewGameInput text field
     */
    @FXML
    private TextField secondInput;
    /**
     * NewGameInput Label
     */
    @FXML
    private Label wrongInputLabel;

    /**
     * sets the controller field
     * @param controller - MyViewController
     */
    public void initialize(MyViewController controller) {
        this.controller = controller;
    }

    /**
     * Handles the submit button being pressed.
     * Checks if the input is good and if so forwards it to the main stage, if not wait for another input.
     * @param event - ActionEvent
     */
    public void submitHandler(ActionEvent event) {
        Boolean goodInput = false;
        //Check if the inputs conform with the Model logic
        if (controller.CheckInput(firstInput,secondInput)){
            goodInput = true;
        } else { //Wrong input - alert the user
            String sLabel = "wrong input - try again or\nread the instructions";
            wrongInputLabel.setText(sLabel);
            wrongInputLabel.setVisible(true);
        }

        if (goodInput) {
            int row = Integer.parseInt(firstInput.getText());
            int column = Integer.parseInt(secondInput.getText());
            //start a new game
            controller.newGame(row, column);
            //close the input window
            Node source = (Node) event.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}
