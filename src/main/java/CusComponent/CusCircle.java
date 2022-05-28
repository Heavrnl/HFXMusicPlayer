package CusComponent;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class CusCircle {
    public static Circle getCusCusCircle(Color color){
        Circle circle = new Circle(0,0,5, Color.WHITE);
        circle.setStroke(color);
        circle.setStrokeWidth(2);
        circle.setCursor(Cursor.HAND);
        return circle;
    }
}
