package View;

import View.ADisplayer;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CharacterDisplayer extends ADisplayer {

    private StringProperty ImageFileNameCharacter = new SimpleStringProperty("resources/Images/CharacterRight.png");

    /**
     * Draws a character in a given position based on given row and column count.
     * @param position - Position
     * @param row - int
     * @param column - int
     */
    public void draw(Position position , int row , int column){

        //Draw Character
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / row;
        double cellWidth = canvasWidth / column;
        try {
            Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
            GraphicsContext graphicsContextCharacter = getGraphicsContext2D();
            graphicsContextCharacter.clearRect(0,0 , getWidth() , getHeight());
            graphicsContextCharacter.drawImage(characterImage, position.getColumnIndex() * cellWidth,position.getRowIndex() * cellHeight,cellWidth,cellHeight);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the image filename of the Character
     * @return - String
     */
    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    /**
     * sets the image filename of the Character
     * @param imageFileNameCharacter - String
     */
    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

}
