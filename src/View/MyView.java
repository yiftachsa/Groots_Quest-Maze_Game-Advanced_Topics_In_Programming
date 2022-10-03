package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class MyView extends Application {

    private MyViewController controller;
    private MyModel model;
    private MyViewModel viewModel;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        model = new MyModel();
        model.startServers();

        viewModel =  new MyViewModel(model);
        model.addObserver(viewModel);

        primaryStage.setTitle("My Board Game");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());
        Scene scene = new Scene(root , 500 , 500);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        controller = fxmlLoader.getController();
        controller.setViewModel(viewModel);
        viewModel.addObserver(controller);

        SolutionDisplayer solutionDisplayer = (SolutionDisplayer) root.lookup("#solutionLayerDisplayer");
        solutionDisplayer.toFront();

        CharacterDisplayer characterDisplayer = (CharacterDisplayer) root.lookup("#characterLayerDisplayer");
        characterDisplayer.toFront();


        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            controller.exitHandler(event);
        });

        //css
        scene.getStylesheets().add(getClass().getResource("MyView.css").toExternalForm());

        primaryStage.show();
        //Welcome window
        AlertBox.display("Welcome" , "Welcome to \nGroot's Quest" ,"\n\n\n\n\n\n\n\n","Play","/Images/characterPosition1.gif");


    }




}
