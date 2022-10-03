package View;

import javafx.beans.property.*;
import javafx.scene.layout.Pane;

public class PannablePane extends Pane {


    DoubleProperty scale = new SimpleDoubleProperty(1.0);

    /**
     * Constructor
     */
    public PannablePane() {
        // add scale transform
        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    /**
     * returns the scale value
     * @return - double
     */
    public double getScale() {
        return scale.get();
    }
    /**
     * returns the scale property
     * @return - DoubleProperty
     */
    public DoubleProperty scaleProperty() {
        return scale;
    }

    /**
     * sets the scale value
     * @param scale - double
     */
    public void setScale( double scale) {
        this.scale.set(scale);
    }

    /**
     * sets the pivot
     * @param x - double
     * @param y - double
     */
    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }

    /**
     * returns the value that was given if it is between the min and max , if not returns the min or max
     * @param value - double
     * @param min - double
     * @param max - double
     * @return - double
     */
    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
