package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface IView extends EventHandler<ActionEvent> {

    void displayMaze();
    void displayCharacter();
    void displaySolution();

}
