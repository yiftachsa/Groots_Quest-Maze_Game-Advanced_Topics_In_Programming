package View;

import View.ADisplayer;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SolutionDisplayer extends ADisplayer {
    private StringProperty ImageFileNameSolution = new SimpleStringProperty("resources/Images/SolPosition.png");
    private GraphicsContext graphicsContextSolution = getGraphicsContext2D();

    /**
     * Draws solution based on a given Solution
     * @param solution - Solution
     */
    public void draw(Solution solution ) {
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int row = ((MazeState) solutionPath.get(0)).getMaze().getRowLength();
        int column = ((MazeState) solutionPath.get(0)).getMaze().getColumnLength();
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / row;
        double cellWidth = canvasWidth / column;
        try {
            Image solutionImage = new Image(new FileInputStream(ImageFileNameSolution.get()));
            graphicsContextSolution.clearRect(0, 0, getWidth(), getHeight());
            for (int i = 0; i < solutionPath.size()-1; ++i) {
                Position currentPosition = ((MazeState) solutionPath.get(i)).getCurrentPosition();
                graphicsContextSolution.drawImage(solutionImage, currentPosition.getColumnIndex() * cellWidth, currentPosition.getRowIndex() * cellHeight, cellWidth, cellHeight);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void clearRectSolution()
    {
        graphicsContextSolution.clearRect(0, 0, getWidth(), getHeight());
    }

}
