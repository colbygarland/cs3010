/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            scribble.setStrokeWidth(3.0);
            this.getChildren().add(scribble);
        });
        this.setOnMouseDragged(e -> {
            scribble.getPoints().add(e.getX());
            scribble.getPoints().add(e.getY());
        });
        this.setOnMouseReleased(e -> {
            scribble.getPoints().add(e.getX());
            scribble.getPoints().add(e.getY());
        });
    }
    
    
    
    
    
    
    
    
    
    
    
}
