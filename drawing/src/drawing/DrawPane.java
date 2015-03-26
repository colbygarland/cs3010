/*
All the code relating to the DrawPane class
*/
package drawing;

import java.util.Stack;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;

class DrawPane extends Pane{
    private MyShape selectedShape=null;
    private boolean dragging = false;
    private double oldMouseX;
    private double oldMouseY;
    private MyShape temp = null;
    private ContextMenu contextmenu;
    private boolean contextFlag = true;
    private boolean contextFlag2 = true;
    private String capturedText = "";
    private Stack stack;

    public DrawPane(){
        super();
        stack = new Stack();
        contextmenu = new ContextMenu();
        this.setPrefSize(800, 600);
        this.setOnMousePressed(e->mousePressed(e));
        this.setOnMouseReleased(e->mouseReleased(e));
        this.setOnMouseDragged(e -> mouseDragged(e));
    }
    public MyShape getSelectedShape(){
        return selectedShape;
    }
    public MyShape [] getSelectedShapes(){ //This could be useful!
      return null;  
    }
    public MyShape [] getUnSelectedShapes(){ //This could be useful too!
      return null;  
    } 
    public void setZOrderingBack(MyShape s){
        s.toBack();
    }
    public void setZOrderingFront(MyShape s){
        s.toFront();
    }
    
    protected void keyTyped(KeyEvent ke){
        if (ke.getCode() == KeyCode.DELETE)
            if (this.selectedShape != null) this.getChildren().remove(this.selectedShape);
        if (ke.getCode() == KeyCode.C && this.selectedShape != null) 
             this.copy(this.selectedShape);
    }
    private Node linePressed(MouseEvent me){
        Line l = new Line();
        l.setStartX(me.getSceneX()-50);
        l.setStartY(me.getSceneY()-30);
        l.setEndX(me.getSceneX() + 100);
        l.setEndY(me.getSceneY());
        Node n = l;
        return n;
    }
    private void mouseDragged(MouseEvent e){
        Polyline scribble = new Polyline();
        scribble.getPoints().add(e.getX());
        scribble.getPoints().add(e.getY());
        this.getChildren().add(scribble);
    }
    private void mousePressed(MouseEvent me){
       
        if (me.getButton() == MouseButton.SECONDARY) contextMenu(me);
        else {
            if (contextmenu.isShowing()){
                contextmenu.hide();
            }
            else if (this.selectedShape == null){
                MyShape s = new MyShape();
                if(MyShape.getDefaultShapeType()==MyShape.LINE){
                    
                    // TODO: fix this so the line starts where the mouse is
                    
                    s.shape = linePressed(me);
                } else if(MyShape.getDefaultShapeType()==MyShape.TEXT_BOX){
                    if (me.getButton() == MouseButton.SECONDARY){
                        // somehow edit text in here
                        MyShape.setDefaultSelectedPaint(Color.YELLOW);
                        s.setSelected(true);
                    } else {
                        Bounds boundsInParent = s.getBoundsInParent();
                        double width = boundsInParent.getWidth();
                        double height = boundsInParent.getHeight();
                        s.relocate(me.getX()-s.getInsets().getLeft()-width/2,
                           me.getY()-s.getInsets().getTop()-height/2);   
                    }
                } else if(MyShape.getDefaultShapeType() == MyShape.SQUIGGLE){
                    Polyline scribble = new Polyline();
                    scribble.getPoints().add(me.getX());
                    scribble.getPoints().add(me.getY());
                    scribble.setStroke(MyShape.defaultStrokePaint);
                    scribble.setStrokeWidth(MyShape.getDefaultStrokeWidth());
                    this.getChildren().add(scribble);
                    System.err.println("test scribble");
                }
                else {
                    s.relocate(me.getX()-s.getInsets().getLeft()-MyShape.getDefaultWidth()/2, 
                    me.getY()-s.getInsets().getTop()-MyShape.getDefaultHeight()/2); 
                }
                s.setOnMousePressed(e->shapePressed(e,s));
                s.setOnMouseReleased(e->shapeReleased(e,s));
                s.setOnMouseDragged(e->shapeDragged(e,s));
                this.getChildren().add(s);
            }
        
            // deselect everything 
            if (!(this.getSelectedShape() == null)){
                this.getSelectedShape().setSelected(false);
                selectedShape = null;
            }
        }
    }
    
    private void contextMenu(MouseEvent me){
        if (!contextmenu.isShowing()){
            if (contextFlag){
                MenuItem copy = new MenuItem("Copy");
                MenuItem paste = new MenuItem("Paste");
                MenuItem undo = new MenuItem("Undo");
                MenuItem redo = new MenuItem("Redo");
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(e -> this.getChildren().remove(this.selectedShape));
                copy.setOnAction(e -> this.copy(this.selectedShape));
                paste.setOnAction(e -> this.paste(me));
                contextmenu.getItems().addAll(copy,paste,undo,redo,delete);
                contextFlag = false;
            }
            contextmenu.show(this, me.getScreenX(), me.getScreenY());
        }
    }
    
    private void mouseReleased(MouseEvent me){
        //System.out.println("MouseReleased");
        
    }

    private void shapePressed(MouseEvent e, MyShape s) {
        if(e.isSecondaryButtonDown()) contextMenu(e);
        if(e.isPrimaryButtonDown() && !s.isSelected()){
            s.setSelected(true);
        }
        if(s.isSelected()){
            selectedShape = s;
            oldMouseX = e.getSceneX();
            oldMouseY = e.getSceneY();  
        } else {
            selectedShape = null;
        }
        e.consume();//Don't trigger any clicks in the parent
    }

    private void shapeReleased(MouseEvent e, MyShape s) {
        dragging=false;
    }

    private void shapeDragged(MouseEvent e, MyShape s) {
        if(s.isSelected()) {
            double newMouseX = e.getSceneX();
            double newMouseY = e.getSceneY();
            double dx = newMouseX-oldMouseX;
            double dy = newMouseY-oldMouseY;
            oldMouseX = newMouseX;
            oldMouseY = newMouseY;
            double centerX = s.getWidth() / 2 + 25;
            double centerY = s.getHeight() / 2 + 25;
            double x = newMouseX-centerX;
            double y = newMouseY-centerY;
            if(s.shapeContains(e.getX(),e.getY())||dragging){
               dragging=true;
               s.relocate(x,y);
            }
            else {
               s.setPrefHeight(s.getHeight()+dy);
               s.setPrefWidth(s.getWidth()+dx); 
            }
            if (s.shapeType == MyShape.LINE) s.changeLineSize(e, s);
            else s.changeSizeButOnlyDuringADrag(s.getWidth(), s.getHeight());
        }
    }
    
    public void copy(MyShape s) {
        if (this.selectedShape == null) return;
        temp = new MyShape(s);
    }
    
    public void paste(MouseEvent me){
        if (!(temp == null)){
            if (selectedShape == null){
                MyShape s = new MyShape(temp);
                
                double x = contextmenu.getAnchorX() - this.getWidth()/2;
                double y = contextmenu.getAnchorY() - this.getHeight()/4;
                
                s.relocate(x,y);
                s.setOnMousePressed(e->shapePressed(e,s));
                s.setOnMouseReleased(e->shapeReleased(e,s));
                s.setOnMouseDragged(e->shapeDragged(e,s));
                this.getChildren().add(s);
            }
        }
    }
    
    public void changeText(String text){
        if (this.selectedShape == null) return;
        MyShape.setDefaultShapeType(MyShape.TEXT_BOX);
        MyShape.setDefaultText(text);
        temp = new MyShape();
        
        String name = temp.getFont().getName();
        temp.setFont(new Font(name,MyShape.getCurrentFontSize()));   
        
        double x = this.selectedShape.getLayoutX();
        double y = this.selectedShape.getLayoutY();
        
        this.getChildren().remove(this.selectedShape);
        this.getChildren().add(temp);
        
        Bounds boundsInParent = temp.getBoundsInParent();
        double width = boundsInParent.getWidth();
        double height = boundsInParent.getHeight();
        temp.relocate(x-temp.getInsets().getLeft()-width/2,
            y-temp.getInsets().getTop()-height/2);   
    }
}