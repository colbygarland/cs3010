
package drawing;

import java.awt.Image;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

interface Drawable {
    void setFillColor(Color c);
    void setStrokeColor(Color c); 
    void setStrokeWidth(int width);
}

class MyShape extends StackPane implements Drawable{
    public static final int CIRCLE = 0;
    public static final int RECTANGLE = 1;
    public static final int ROUNDED_RECTANGLE = 2;
    public static final int OVAL = 3;
    public static final int TRIANGLE = 4;
    public static final int LINE = 5;
    public static final int SQUIGGLE = 6;
    public static final int TEXT_BOX = 7;
    private final SimpleBooleanProperty selected;
    private final Node shape;
    private static Paint defaultFillPaint=Color.RED;
    private static Paint defaultStrokePaint=Color.BLACK;
    private static Paint defaultSelectedPaint=Color.BROWN;
    private static double defaultStrokeWidth=3;
    private static int defaultShapeType = CIRCLE;
    private static double defaultWidth = 50;
    private static double defaultHeight = 50;

    
    private Node makeShape(){
        Node s = null;
        switch(defaultShapeType){
            case CIRCLE: s = new Circle(Math.min(defaultWidth/2, defaultHeight/2));break;
            case RECTANGLE: s = new Rectangle(defaultWidth,defaultHeight);break;
            case ROUNDED_RECTANGLE: Rectangle r = new Rectangle(defaultWidth*2, defaultHeight);
                                    r.setArcHeight(20);
                                    r.setArcWidth(20);
                                    s = r;
                                    break;
            case OVAL:  Ellipse e = new Ellipse();
                        e.setCenterX(defaultWidth/2);
                        e.setCenterY(defaultHeight/2);
                        e.setRadiusX(defaultWidth);
                        e.setRadiusY(defaultHeight/2);
                        s = e;
                        break;
            case TRIANGLE: s = new Polygon(defaultWidth*1.5,0,defaultWidth*2,defaultWidth,
                    defaultWidth,defaultWidth); 
                           break;
            case LINE: Line l = new Line();
                       l.setStartX(0);
                       l.setStartY(0);
                       l.setEndX(defaultWidth);
                       l.setEndY(defaultHeight);
                       s = l;
                       break;
            case TEXT_BOX: Text t = new Text("Hi");
                           //t.setScaleX(5);
                           //t.setScaleY(5);
                           s = t;
                            break;
                
        }
        return s;
    }

    public MyShape(){
        super();
        selected = new SimpleBooleanProperty(false);
        selected.set(false);
        shape = makeShape();
        if (shape instanceof Shape){
            ((Shape)shape).setFill(defaultFillPaint);
            ((Shape)shape).setStroke(defaultStrokePaint);
            ((Shape)shape).setStrokeWidth(defaultStrokeWidth);
        }
        this.getChildren().add(shape);
        this.setPadding(new Insets(5,5,5,5));//A couple of magic numbers 5..10
        this.setMinWidth(10);
        this.setMinHeight(10);
    }
   
    public void changeSizeButOnlyDuringADrag(double width, double height){ //Buggy
        if(shape instanceof Circle){
            Circle c = (Circle)shape; 
            c.setRadius(Math.min(width/2.0,height/2.0)-this.getInsets().getLeft()-c.getStrokeWidth()/2.0);
        } else if(shape instanceof Rectangle){
            Rectangle r = (Rectangle)shape;
            r.setWidth(width-this.getInsets().getLeft()-this.getInsets().getRight()-r.getStrokeWidth());
            r.setHeight(height-this.getInsets().getTop()-this.getInsets().getBottom()-r.getStrokeWidth());
        } else if(shape instanceof Ellipse){
            Ellipse e = (Ellipse)shape;
            e.setCenterX(width-this.getInsets().getLeft()-this.getInsets().getRight()-e.getStrokeWidth());
            e.setCenterY(height-this.getInsets().getTop()-this.getInsets().getBottom()-e.getStrokeWidth());
        }
    }
    public boolean isSelected(){
        return selected.get();
    }
    public void setSelected(boolean value){
        selected.set(value);
        if(value)this.setBackground(new Background(new BackgroundFill(defaultSelectedPaint,CornerRadii.EMPTY,Insets.EMPTY)));
        else this.setBackground(Background.EMPTY);

    }
    public BooleanProperty selectedProperty(){
        
        return selected;
    } 
    public boolean shapeContains(double x,  double y){
        if (shape instanceof Circle) return shape.contains(x-this.getWidth()/2, y-this.getHeight()/2);
        else if(shape instanceof Rectangle)
            return shape.contains(x-this.getInsets().getLeft(), y-this.getInsets().getTop());
        else return false;
    }
    public static void setDefaultFillPaint(Paint value){
        defaultFillPaint = value;
    }
    public static void setSDefaulttrokePaint(Paint value){
        defaultStrokePaint = value;
    }
    public static void setDefaultSelectedPaint(Paint value){
        defaultSelectedPaint = value;
    }
    public static void setDefaultStrokeWidth(double value){
        defaultStrokeWidth = value;
    }
    public static void setDefaultShapeType(int value){
        defaultShapeType = value;
    }
    public static void setDefaultWidth(double value){
        defaultWidth = value;
    }
    public static void setDefaultHeight(double value){
        defaultHeight = value;
    }
    public static double getDefaultWidth(){
        return defaultWidth;
    }
    public static double getDefaultHeight(){
        return defaultHeight;
    } 
    public void setFillColor(Color value){
        if(shape instanceof Shape){
            ((Shape)shape).setFill(value);
        }
    }
    public void setStrokeColor(Color value){
        if(shape instanceof Shape){
            ((Shape)shape).setStroke(value);
        }
    }
    public void setStrokeWidth(int value){
        if(shape instanceof Shape){
            ((Shape)shape).setStrokeWidth(value);
        }
    }
    public void setStrokeDashArray(double [] value){
        if(shape instanceof Shape){
            ((Shape)shape).getStrokeDashArray().clear();
            for(int i = 0; i < value.length; i++)
               ((Shape)shape).getStrokeDashArray().add(value[i]);
        }
    }
    public void editText(Text t){
        if (this.isSelected()) t.setOnMouseClicked(e -> {
            String msg;
            msg = this.getOnKeyTyped().toString();
                
           
            t.setText(msg);
        });
    }
}

class DrawPane extends Pane{
    private MyShape selectedShape=null;
    private boolean dragging = false;
    private double oldMouseX;
    private double oldMouseY;

    public DrawPane(){
        super();
        this.setPrefSize(800, 600);
        this.setOnMousePressed(e->mousePressed(e));
        this.setOnMouseReleased(e->mouseReleased(e));
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
    private void mousePressed(MouseEvent me){
        //System.out.println("MousePressed");
        
        
        
        MyShape s = new MyShape();
        s.relocate(me.getSceneX()-s.getInsets().getLeft()-MyShape.getDefaultWidth()/2 - 48, 
                me.getSceneY()-s.getInsets().getTop()-MyShape.getDefaultHeight()/2-30);
        s.setOnMousePressed(e->shapePressed(e,s));
        s.setOnMouseReleased(e->shapeReleased(e,s));
        s.setOnMouseDragged(e->shapeDragged(e,s));
        this.getChildren().add(s);
    }
    
    private void mouseReleased(MouseEvent me){
        //System.out.println("MouseReleased");
    }

    private void shapePressed(MouseEvent e, MyShape s) {
       // System.out.println("ShapePressed");
        if(e.isSecondaryButtonDown())s.setSelected(!s.isSelected());
        if(s.isSelected()){
            selectedShape = s;
            oldMouseX = e.getSceneX();
            oldMouseY = e.getSceneY();
        } else selectedShape = null;
        e.consume();//Don't trigger any clicks in the parent
    }

    private void shapeReleased(MouseEvent e, MyShape s) {
        //System.out.println("ShapeReleased");
        dragging=false;
    }

    private void shapeDragged(MouseEvent e, MyShape s) {
        //System.out.println("ShapeDragged");
        if(s.isSelected()) {
            double newMouseX = e.getSceneX();
            double newMouseY = e.getSceneY();
            double dx = newMouseX-oldMouseX;
            double dy = newMouseY-oldMouseY;
            oldMouseX = newMouseX;
            oldMouseY = newMouseY;
            if(s.shapeContains(e.getX(),e.getY())||dragging){
               dragging=true;
               s.relocate(newMouseX+dx-e.getX(),newMouseY+dy-e.getY());
            }
            else {
               s.setPrefHeight(s.getHeight()+dy);
               s.setPrefWidth(s.getWidth()+dx); 
            }
            s.changeSizeButOnlyDuringADrag(s.getWidth(), s.getHeight());
        }
    }
}

public class Drawing extends Application {
    
    DrawPane pane = new DrawPane();
    BorderPane root = new BorderPane();
    ColorPicker colorpicker = new ColorPicker();
    
    // all the menu bar code here
    public void menuBar(){
        // menu bar to hold everything
        MenuBar menubar = new MenuBar();
        menubar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        menubar.setStyle("-fx-background-color: darkgray;");
        // menu shape to select which shape to use
        Menu shapemenu = new Menu("Shape");
        
        Menu linemenu = new Menu("Line");
        Menu picturemenu = new Menu("Picture");
        Menu filemenu = new Menu("File");
        // menu items of all the shapes
        MenuItem circle = new MenuItem("Circle");
        MenuItem rectangle = new MenuItem("Square");
        MenuItem roundedrectangle = new MenuItem("Rounded Rectangle");
        MenuItem oval = new MenuItem("Oval");
        MenuItem triangle = new MenuItem("Triangle");
        MenuItem scribble = new MenuItem("Scribble");
        MenuItem line = new MenuItem("Line");
        MenuItem textbox = new MenuItem("Text Box");
        MenuItem pixelspray = new MenuItem("Pixel Spray");
        MenuItem choosepicture = new MenuItem("Open Image");
        MenuItem print = new MenuItem("Print");
        MenuItem close = new MenuItem("Close");
        MenuItem save = new MenuItem("Save");
        MenuItem NEW = new MenuItem("New Drawing");
        
        // set all the actions to change the shapes
        circle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.CIRCLE));
        rectangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.RECTANGLE));
        roundedrectangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.ROUNDED_RECTANGLE));
        oval.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.OVAL));
        triangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TRIANGLE));
        line.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.LINE));
        textbox.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TEXT_BOX));
        
        
        choosepicture.setOnAction(e -> {
            FileChooser picChooser = new FileChooser();
            picChooser.setTitle("Open Image");
            picChooser.getExtensionFilters().add(new ExtensionFilter("Image Files",
                     "*.png", "*.jpg", "*.gif"));
            File selectedFile = picChooser.showOpenDialog(new Stage());
            ImageView imgview = new ImageView();
        });
        print.setOnAction(e -> this.print(pane));
        
        close.setOnAction(e -> Platform.exit());
        // disable things that either don't work or I don't want to work
        print.setDisable(true);
        scribble.setDisable(true);
        pixelspray.setDisable(true);
        choosepicture.setDisable(false);
        choosepicture.setDisable(true);
        save.setDisable(true);
        
        colorpicker.setValue(Color.RED);
        colorpicker.setStyle("-fx-background-color: darkgray;");
        colorpicker.setOnAction(e -> {
                Color c = colorpicker.getValue();
                if (!(pane.getSelectedShape() == null))
                    pane.getSelectedShape().setFillColor(c);
        });
        
        NEW.setOnAction(e -> {
            pane = new DrawPane();
            root.setCenter(pane);
        });
        
        menubar.getMenus().addAll(filemenu,shapemenu, linemenu, picturemenu);
        shapemenu.getItems().addAll(circle, rectangle, roundedrectangle, oval, triangle);
        linemenu.getItems().addAll(line, scribble, pixelspray, textbox);
        picturemenu.getItems().add(choosepicture);
        filemenu.getItems().addAll(NEW,save,print,close);
        
        HBox hbox = new HBox();
        hbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        hbox.getChildren().addAll(menubar, colorpicker);
        hbox.setPrefWidth(600);
        root.setTop(hbox);
    }
    // all the side toolbar code here
    public void sideBar(){
        VBox sidebar = new VBox();
        sidebar.setSpacing(10);
        sidebar.setStyle("-fx-background-color: darkgray;");
        Button btncircle = new Button("Circle");
        Button btnsquare = new Button("Square");
        Button btnrounded = new Button("Rounded Rectangle");
        Button btntriangle = new Button("Triangle");
        Button btnoval = new Button("Oval");
        
        btncircle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.CIRCLE));
        btnsquare.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.RECTANGLE));
        btnrounded.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.ROUNDED_RECTANGLE));
        btntriangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TRIANGLE));
        btnoval.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.OVAL));
        
        sidebar.getChildren().addAll(btncircle, btnsquare, btnrounded, btntriangle
        , btnoval);
        
        Rectangle rect = new Rectangle(50,25);
        rect.setRotate(90);
        Rectangle rect2 = new Rectangle(50,25);
        rect2.setRotate(90);
        rect2.setFill(Color.LIGHTGRAY);
            
        
        VBox vbox = new VBox();
        vbox.getChildren().add(rect);
        vbox.setTranslateY(250);
        vbox.setTranslateX(-20);
        rect.setFill(Color.LIGHTGRAY);
        
        root.setLeft(vbox);
        
        rect.setOnMouseEntered(e -> {
            root.setLeft(vbox);
            vbox.setOnMouseEntered(g -> {
                root.setLeft(sidebar);
                sidebar.setOnMouseExited(f -> root.setLeft(vbox));
            });
        });
        VBox vbox2 = new VBox();
        vbox2.getChildren().add(rect2);
        vbox2.setTranslateY(250);
        vbox2.setTranslateX(20);
        root.setRight(vbox2);
        VBox sidebar2 = new VBox();
        
        sidebar2.setStyle("-fx-background-color: darkgray;");
        sidebar2.setSpacing(10);
        Button btnline = new Button("Line");
        Button btnsquiggle = new Button("Squiggle");
        Button btntext = new Button("Text Box");
        sidebar2.getChildren().addAll(btnline, btnsquiggle, btntext);
        
        btnline.setOnAction(a -> MyShape.setDefaultShapeType(MyShape.LINE));
        btntext.setOnAction(b -> MyShape.setDefaultShapeType(MyShape.TEXT_BOX));
        btnsquiggle.setOnAction(c -> MyShape.setDefaultShapeType(MyShape.SQUIGGLE));
        
        rect2.setOnMouseEntered(d -> {
            vbox2.setOnMouseEntered(h -> {
                root.setRight(sidebar2);
                sidebar2.setOnMouseExited(f -> root.setRight(vbox2));
            });
        
        });
                
    }
    @Override
    public void start(Stage primaryStage) {

        menuBar();
        // buggy, when enabled doesn't place objects properly
        sideBar();
        
        root.setCenter(pane);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Drawesome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void print(final Node node) {
        ObservableSet<Printer> allPrinters = Printer.getAllPrinters();
        for(Printer p:allPrinters){ //List  all the printers
            System.out.println(p.getName());
        }
    
        Printer printer = Printer.getDefaultPrinter();
        PrinterAttributes printerAttributes = printer.getPrinterAttributes();
        // Do something with the attributes of the default printer
        
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, 
                PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
 
        System.out.println("Printable Height:"+pageLayout.getPrintableHeight());
        System.out.println("Printable Width :"+pageLayout.getPrintableWidth());
        System.out.println("Top Margin      :"+pageLayout.getTopMargin());
        System.out.println("Bottom Margin   :"+pageLayout.getBottomMargin());
        System.out.println("Left Margin     :"+pageLayout.getLeftMargin());
        System.out.println("Right Margin    :"+pageLayout.getRightMargin());
        
        //You may need to apply a scale transform to the node
        //  and/or use PageOrientation.LANDSCAPE
        
        //Since printing may take some time you may print on a different thread.
        //Otherwise we may slow down the UI and make it seem un-responsive
        
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }    
    public static void main(String[] args) {
        launch(args);
    }
    
}
