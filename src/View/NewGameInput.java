package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameInput {
    /**
     * Displays a new game window.
     * @param myViewController - MyViewController - The controller of the main window
     * @param stageTitle - String - the stage title
     * @param firstLabel - String - text in the first label
     * @param firstPrompt - String - text in the first prompt
     * @param secondLabel - String - text in the second label
     * @param secondPrompt - String - text in the second prompt
     */
    public static void display(MyViewController myViewController,String stageTitle, String firstLabel, String firstPrompt, String secondLabel, String secondPrompt) {
        Stage window = new Stage();
        window.setTitle(stageTitle);
        window.setMinWidth(320);
        window.setMinHeight(180);
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(NewGameInput.class.getResource("NewGameInput.fxml").openStream());
        }catch (IOException e) {
            System.out.println("Couldn't open the \"NewGameInput.fxml\" fxml file");
            e.printStackTrace();
        }
        window.initModality(Modality.APPLICATION_MODAL); //Blocking user interaction with other stages\windows until this window is closed

        //sends the window controller to the main window controller
        myViewController.setNewGameInputController(fxmlLoader.getController());

        //Setting the first label
        Label lFirstLabel = (Label) root.lookup("#firstLabel");
        lFirstLabel.setText(firstLabel + ":");

        //Setting the first TextField
        TextField tfFirstPrompt = (TextField)root.lookup("#firstInput");
        tfFirstPrompt.setPromptText(firstPrompt);

        //Setting the second label
        Label lSecondLabel = (Label) root.lookup("#secondLabel");
        lSecondLabel.setText(secondLabel + ":");

        //Setting the second TextField
        TextField tfSecondPrompt = (TextField)root.lookup("#secondInput");
        tfSecondPrompt.setPromptText(secondPrompt);

        Scene scene = new Scene(root,200,150);
        window.setScene(scene);
        scene.getStylesheets().add(NewGameInput.class.getResource("NewGameInput.css").toExternalForm());
        window.show();
    }
}

