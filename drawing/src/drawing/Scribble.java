/*
The scribble code
*/
package drawing;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

/**
 *
 * @author colbyg
 */
class Scribble extends Pane{
    private Polyline scribble;
    
    public Scribble(){
        super();
        this.setOnMousePressed( e -> {
            scribble = new Polyline();
            scribble.getPoints().add(e.getX());
            scribble.getPoints().add(e.getY());
            scribble.setStroke(MyShape.defaultStrokePaint);
            scribble.setStrokeWidth(MyShape.getDefaultStrokeWidth());
            this.getChildren().add(scribble);
        });
        this.setOnMouseDragged(e -> {
            scribble.getPoints().add(e.getX());
            scribble.getPoints().add(e.getY());
            System.err.println("in dragging scribble");
        });
        this.setOnMouseReleased(e -> {
            scribble.getPoints().add(e.getX());
            scribble.getPoints().add(e.getY());
        });
        System.err.println("testing scribble constructor");
    } 
}
