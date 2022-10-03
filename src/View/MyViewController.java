package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Observer,IView, Initializable {


    private MyViewModel viewModel;
    private GameDisplayer gameDisplayer;
    private boolean alreadyFinished = false;

    private String audioPathStart = new File("resources/Audio/Come and get Your Love(Guardians of the Galaxy Intro song) - Redbone.mp3").toURI().toString();
    private String audioPathEnd = new File("resources/Audio/Electric Light Orchestra - Mr Blue Sky (Guardians of the Galaxy 2 Awesome Mix Vol. 2 ).mp3").toURI().toString();
    private MediaPlayer mediaPlayerStart = new MediaPlayer(new Media(audioPathStart));
    private MediaPlayer mediaPlayerEnd = new MediaPlayer(new Media(audioPathEnd));

    private NewGameInputController newGameInputController;
    @FXML
    private MazeDisplayer baseLayerDisplayer;

    @FXML
    private CharacterDisplayer characterLayerDisplayer;

    @FXML
    private SolutionDisplayer solutionLayerDisplayer;

    @FXML
    private MenuItem solution;
    @FXML
    private MenuItem save;
    @FXML
    private BorderPane borderPane;

    @FXML
    private PannablePane pane;

    private Boolean dragCharacter = false;

    /**
     * sets the view model private field
     * @param viewModel - MyViewModel
     */
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.prefHeightProperty().bind(borderPane.heightProperty());
        pane.prefWidthProperty().bind(borderPane.widthProperty());
        baseLayerDisplayer.heightProperty().bind(pane.heightProperty());
        baseLayerDisplayer.widthProperty().bind(pane.widthProperty());
        baseLayerDisplayer.heightProperty().addListener((observable, oldValue, newValue) -> {
            redrawGameDisplayer();
        });
        baseLayerDisplayer.widthProperty().addListener((observable, oldValue, newValue) -> {
            redrawGameDisplayer();
        });

        characterLayerDisplayer.heightProperty().bind(pane.heightProperty());
        characterLayerDisplayer.widthProperty().bind(pane.widthProperty());

        solutionLayerDisplayer.heightProperty().bind(pane.heightProperty());
        solutionLayerDisplayer.widthProperty().bind(pane.widthProperty());
    }

    /**
     * Drawing the entire maze after a resize event.
     */
    private void redrawGameDisplayer()
    {
        if(gameDisplayer!=null) {
            if(viewModel.Solved()) {
                gameDisplayer.drawGame(viewModel.getMaze(), viewModel.getCharacterPosition(), viewModel.getSolution());
            }else{
                gameDisplayer.drawGame(viewModel.getMaze(), viewModel.getCharacterPosition(), null);
            }
        }

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            if(viewModel.mazeChanged()){
                displayMaze();
            }
            if(viewModel.characterPositionChanged()){

                if(viewModel.characterPositionChangedRight())
                {
                    gameDisplayer.setCharacterPositionImage("resources/Images/CharacterRight.png");
                }
                else if(viewModel.characterPositionChangedLeft())
                {
                    gameDisplayer.setCharacterPositionImage("resources/Images/CharacterLeft.png");
                }
                displayCharacter();

            }
            if(viewModel.solutionChanged()){
                displaySolution();
            }
        }
    }

    /**
     * Receives the maze from the viewModel and displays it using the game displayer.
     */
    public void displayMaze() {
        gameDisplayer.setMaze(viewModel.getMaze());
    }
    /**
     * Receives the character position and the maze from the viewModel and displays them using the game displayer.
     */
    public void displayCharacter() {
        gameDisplayer.setCharacterPosition(viewModel.getCharacterPosition(),viewModel.getMaze());

    }
    /**
     * Receives the solution from the viewModel and displays it using the game displayer
     */
    public void displaySolution() {
        gameDisplayer.setSolutionDisplayer(viewModel.getSolution());
    }


    /**
     * Sets the newGameInputController field in order to be able to receive the user input.
     * @param newGameInputController - NewGameInputController
     */
    public void setNewGameInputController(NewGameInputController newGameInputController) {
        this.newGameInputController = newGameInputController;
        //media player
        mediaPlayerEnd.stop();
        mediaPlayerStart.stop();
        mediaPlayerStart.play();

        alreadyFinished = false;
    }

    /**
     * Handles the keyboard presses.
     * @param keyEvent - KeyEvent
     */
    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
        if(gameDisplayer!=null) {
            if (viewModel.finished() && !alreadyFinished) {
                gameOver();
                alreadyFinished = true;
            }
        }
    }

    @Override
    public void handle(ActionEvent event) {
    }

    /**
     * Handles the "about" button
     */
    public void gameOver (){

        mediaPlayerStart.stop();
        mediaPlayerEnd .play();
        AlertBox.display("Win","YOU WON!!!", "Congratulations\n\n\n\n\n","Back to menu" ,"/Images/win.gif");
    }

    /**
     * Handles the "about" button
     */
    public void aboutHandler (){
        String sAbout = "The Creators:\n" + "        Merav Shaked\n" + "        Yiftach Savransky";
        AlertBox.display("About","About us:", sAbout,"Close" ,"default background");
    }
    /**
     * Handles the "instructions" button
     */
    public void instructionsHandler (){
        String sInstructions = "Move with:\n         4 - left\n         8 - up\n         2 - down\n         6 - right "+"\nOther keys: 7, 9 ,1 ,3\n"+"Input must be between\n"+"            5-1000";
        AlertBox.display("Instructions","Instructions", sInstructions,"Close" , "default background");
    }

    /**
     * Handles the "new" button . Displays a new NewGameInput window.
     * @param actionEvent
     */
    public void newGameHandler(ActionEvent actionEvent){
        NewGameInput.display(this,"New Game","Number of rows","Number of rows","Number of columns","Number of columns");
        newGameInputController.initialize(this);
    }

    /**
     * Initialize a new game.
     * @param row - int
     * @param column - int
     */
    public void newGame(int row , int column){
        gameDisplayer = new GameDisplayer(this.baseLayerDisplayer, this.characterLayerDisplayer ,this.solutionLayerDisplayer);
        viewModel.generateMaze(row, column);
        save.setDisable(false);
        solution.setDisable(false);
    }

    /**
     * Handles the "show solution" button.
     * @param event - ActionEvent
     */
    public void solutionHandler(ActionEvent event) {
        viewModel.getSolution();
    }

    /**
     * Handles the "save" button.
     * @param event - ActionEvent
     */
    public void saveHandler(ActionEvent event) {

        FileChooser fileChooser =  new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Maze File" , "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        MenuItem menuItem = (MenuItem) event.getSource();
        while(menuItem.getParentPopup() == null)
        {
            menuItem = menuItem.getParentMenu();
        }
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage stage = (Stage) scene.getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null)
        {
            viewModel.saveGame(file);
        }
    }

    /**
     * Handles the "load" button.
     * @param event - ActionEvent
     */
    public void loadHandler(ActionEvent event) {
        FileChooser fileChooser =  new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Maze File" , "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        MenuItem menuItem = (MenuItem) event.getSource();
        while(menuItem.getParentPopup() == null)
        {
            menuItem = menuItem.getParentMenu();
        }
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        Stage stage = (Stage) scene.getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            alreadyFinished = false;
            gameDisplayer = new GameDisplayer(this.baseLayerDisplayer,this.characterLayerDisplayer , this.solutionLayerDisplayer);
            save.setDisable(false);
            solution.setDisable(false);
            mediaPlayerEnd.stop();
            mediaPlayerStart.play();
            viewModel.loadGame(file);
        }
    }

    /**
     * Handles the "Properties" button.
     * @param event - ActionEvent
     */
    public void propertiesHandler(ActionEvent event) {
        String sProperties = "";
        try(FileReader reader = new FileReader("resources//config.properties");
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while((line= bufferedReader.readLine()) != null)
            {
                sProperties = sProperties + line + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertBox.display("Properties","Properties", sProperties,"Close" ,"default background");
    }

    /**
     * Zooming function.
     * Handles the mouse scroll.
     * @param scrollEvent - ScrollEvent
     */
    public void scrollHandler(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double delta = 1.2;

            double scale = pane.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (scrollEvent.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = PannablePane.clamp(scale, .1d, 10.0d);

            double f = (scale / oldScale) - 1;

            double dx = (scrollEvent.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX()));
            double dy = (scrollEvent.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY()));

            pane.setScale(scale);

            // note: pivot value must be untransformed, i. e. without scaling
            pane.setPivot(f * dx, f * dy);

            scrollEvent.consume();

        }
    }

    /**
     * Handles the mouse being pressed.
     * Decides if the mouse was pressed on the current character position
     * @param mouseEvent - MouseEvent
     */
    public void MousePressedHandler(MouseEvent mouseEvent) {
        if(gameDisplayer!=null) {
            if (!alreadyFinished) {
                int[] positionArr = getPositionFromMouseDrag(mouseEvent);
                if (viewModel.getCharacterPosition().getRowIndex() == positionArr[0] && viewModel.getCharacterPosition().getColumnIndex() == positionArr[1]) {
                    this.dragCharacter = true;
                }
            }
        }
    }

    /**
     * Handles the mouse release.
     * @param mouseEvent - MouseEvent
     */
    public void MouseReleasedHandler(MouseEvent mouseEvent) {
        dragCharacter = false;
    }

    /**
     * Handles the mouse being dragged.
     * Moves the character according to the mouse dragging position.
     * @param event - MouseEvent
     */
    public void MouseDragHandler(MouseEvent event){
        if(gameDisplayer!=null) {
            if (!alreadyFinished && dragCharacter) {
                int[] positionArr = getPositionFromMouseDrag(event);
                if(viewModel.isNeighborhingPosition(positionArr[0], positionArr[1])) {
                    viewModel.moveCharacter(positionArr[0], positionArr[1]);
                    if (viewModel.finished() && !alreadyFinished) {
                        alreadyFinished = true;
                        gameOver();
                    }
                }
            }
        }
    }

    /**
     * Receives a MouseEvent and returns the corresponding position to the mouse drag location
     * @param event - MouseEvent
     * @return - int[] - rowPosition, columnPosition
     */
    private int[] getPositionFromMouseDrag(MouseEvent event) {
        int[] mazeDimensions = viewModel.getMazeDimensions();
        double heightCellSize = mazeDimensions[0] / gameDisplayer.getCanvasHeight();
        double widthCellSize = mazeDimensions[1]/ gameDisplayer.getCanvasWidth();
        int columnPosition = (int) (event.getX() * widthCellSize);
        int rowPosition = (int) (event.getY() * heightCellSize);
        int[] positionArr = new int[]{rowPosition, columnPosition};
        return positionArr;
    }

    /**
     * Closes the main stage.
     * Calls exitHandler to orderly close the application.
     * @param event - ActionEvent - exit button was pressed
     */
    public void exit(ActionEvent event){
        exitHandler(event);
    }

    /**
     * Handles the 'x' button being pressed.
     * Closes the application in an orderly fashion.
     * @param event - Event
     */
    public void exitHandler(Event event)
    {
        //Decide whether to close the application based on the user input
        Boolean closeWindow = true;
        closeWindow = ExitConfirmBox.display("confirmBoxButton","Are you sure you \n want to exit?");
        //closing the main window - depends on the button used to close (exit button or the 'X' sign)
        if (closeWindow){
            if(event instanceof WindowEvent){
                //'X' was pressed
                viewModel.shutdown();
                close((WindowEvent)event);
            }else{
                //"exit" button was pressed
                viewModel.shutdown();
                close((ActionEvent) event);
            }
        }
    }

    /**
     * Closes a stage based on an ActionEvent
     * @param actionEvent - ActionEvent - "exit" button was pressed
     */
    private static void close (ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes a stage based on an WindowEvent
     * @param windowEvent - WindowEvent - 'X' was pressed
     */
    private static void close (WindowEvent windowEvent){
        Stage stage  = (Stage) windowEvent.getSource();
        stage.close();
    }

    /**
     * sends the user input to the viewModel and return whether the input is good
     * @param firstInput - TextField
     * @param secondInput - TextField
     * @return - boolean
     */
    public boolean CheckInput(TextField firstInput, TextField secondInput) {
        return viewModel.CheckInput(firstInput , secondInput);
    }

}


