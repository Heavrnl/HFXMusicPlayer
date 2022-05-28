package CusComponent;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CusProLine {
    public static Line getCusProLine(Color color){
        Line line = new Line();
        line.setStroke(color);
        line.setStrokeWidth(5);
        line.setLayoutX(0);
        line.setLayoutY(0);
        line.setCursor(Cursor.HAND);
        return line;
    }

}
