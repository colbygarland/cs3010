/*
All the code relating to the DrawPane class
*/
package drawing;

import java.io.File;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class DrawPane extends Pane{
    private MyShape selectedShape=null;
    private boolean dragging = false;
    private double oldMouseX;
    private double oldMouseY;
    private MyShape temp = null;
    private ContextMenu contextmenu = new ContextMenu();
    private boolean contextFlag = true;
    private boolean contextFlag2 = true;
    private String capturedText = "";
    private Stack stack;

    public DrawPane(){
        super();
        stack = new Stack();
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
    private void editText(MouseEvent me){
        
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
            MenuItem editText = null;
            if (MyShape.getDefaultShapeType() == MyShape.TEXT_BOX && contextFlag2){
                    editText = new MenuItem("Edit Text");
                    editText.setOnAction(e -> {
                        
                    });
                    contextmenu.getItems().add(editText);
                    contextFlag2 = false;
            }
            if (contextFlag){
                MenuItem copy = new MenuItem("Cut");
                MenuItem paste = new MenuItem("Paste");
                MenuItem undo = new MenuItem("Undo");
                MenuItem redo = new MenuItem("Redo");
                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(e -> this.getChildren().remove(this.selectedShape));
                copy.setOnAction(e -> this.copy(this.selectedShape));
                paste.setOnAction(e -> this.paste(me));
                undo.setOnAction(e -> undo());
                redo.setOnAction(e -> redo());
                contextmenu.getItems().addAll(copy,paste,undo,redo,delete);
                contextFlag = false;
            }
            if (!(MyShape.getDefaultShapeType() == MyShape.TEXT_BOX)) System.err.println(contextmenu.getItems().remove(editText));
            contextmenu.show(this, me.getScreenX(), me.getScreenY());
        }
    }
    
    private void mouseReleased(MouseEvent me){
        //System.out.println("MouseReleased");
        
    }

    private void shapePressed(MouseEvent e, MyShape s) {
       // System.out.println("ShapePressed");
        if(e.isSecondaryButtonDown()) contextMenu(e);
        if(e.isPrimaryButtonDown() && !s.isSelected()){
            s.setSelected(true);
        }
        if(s.isSelected()){
            selectedShape = s;
            oldMouseX = e.getSceneX();
            oldMouseY = e.getSceneY();
            //** See if this is a good idea
            //MyShape.setDefaultShapeType(s.shapeType);   
        } else {
            selectedShape = null;
        }
        e.consume();//Don't trigger any clicks in the parent
    }

    private void shapeReleased(MouseEvent e, MyShape s) {
        //System.out.println("ShapeReleased");
        dragging=false;
    }

    private void shapeDragged(MouseEvent e, MyShape s) {
        //System.out.println("ShapeDragged");
        if(s.isSelected()) {
            stack.push(s);
            String shapeDraggedMsg = new String("ShapeDragged");
            stack.push(shapeDraggedMsg);
            
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
               s.relocate(x/*+dx*/,y/*+dy*/);
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
                s.relocate(/*me.getSceneX()-s.getInsets().getLeft()-MyShape.getDefaultWidth()/2*/me.getSceneX() ,
                  me.getSceneY());
                s.setOnMousePressed(e->shapePressed(e,s));
                s.setOnMouseReleased(e->shapeReleased(e,s));
                s.setOnMouseDragged(e->shapeDragged(e,s));
                this.getChildren().add(s);
            }
        }
    }
    
    public void undo(){
        String tmp = (String)stack.pop();
        
        if (tmp.equals("ShapeDragged")){
            MyShape ms = (MyShape)stack.pop();
            
            
        }
    }
    
    public void redo(){
        
    }

    public void save(){
        FileChooser saveChooser = new FileChooser();
        saveChooser.setTitle("Save Drawing");
        File f = saveChooser.showSaveDialog(new Stage());
        
        // locations for the nodes
        double x,y;
        // file to save to

        
        for (Node shape: this.getChildren()){
            x = shape.getLayoutX();
            y = shape.getLayoutY();
            
            
        }
    }
    
    public void open(){
        FileChooser drawingChooser = new FileChooser();
        drawingChooser.setTitle("Open Drawing");
        File drawing = drawingChooser.showOpenDialog(new Stage());
    }
    

}