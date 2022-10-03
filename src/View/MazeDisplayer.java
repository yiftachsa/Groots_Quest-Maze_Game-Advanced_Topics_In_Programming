package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends ADisplayer {


    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty("resources/Images/wall.jpg");
    private StringProperty ImageFileNamePath = new SimpleStringProperty("resources/Images/path.jpg");
    private StringProperty ImageFileNameGoal = new SimpleStringProperty("resources/Images/Goal.png");

    /**
     * Draws the given maze using canvas functions.
     * @param maze - Maze
     */
    public void draw( Maze maze) {

        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.getRowLength();
        double cellWidth = canvasWidth / maze.getColumnLength();

        try {
            Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
            Image goalImage = new Image(new FileInputStream(ImageFileNameGoal.get()));
            Image pathImage = new Image(new FileInputStream(ImageFileNamePath.get()));

            GraphicsContext graphicsContextMaze = getGraphicsContext2D();
            graphicsContextMaze.clearRect(0, 0, getWidth(), getHeight());

            //Draw Maze
            for (int i = 0; i < maze.getRowLength(); i++) {
                for (int j = 0; j < maze.getColumnLength(); j++) {
                    if (maze.getValue(i, j) == 1) {
                        //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        graphicsContextMaze.drawImage(wallImage,  j * cellWidth,i * cellHeight, cellWidth , cellHeight);
                    }
                    else
                    {
                        graphicsContextMaze.drawImage(pathImage, j * cellWidth,i * cellHeight,  cellWidth , cellHeight);
                    }
                }
            }
            graphicsContextMaze.drawImage(goalImage, maze.getGoalPosition().getColumnIndex()* cellWidth,maze.getGoalPosition().getRowIndex()* cellHeight , cellWidth , cellHeight);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion
}
