/*
All the code relating to the MyShape class
*/
package drawing;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

interface Drawable {
    void setFillColor(Color c);
    void setStrokeColor(Color c); 
    void setStrokeWidth(int width);
    void setFont(Font value);
    void setText(String value);
    Paint getFillColor();
    Paint getStrokeColor(); 
    double getStrokeWidth();
    double [] getStrokeDashArray();    
    Font getFont();
    String getText();
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
    public static final int PICTURE = 8;
    private final SimpleBooleanProperty selected;
    protected Node shape;
    private static Paint defaultFillPaint=Color.RED;
    protected static Paint defaultStrokePaint=Color.BLACK;
    private static Paint defaultSelectedPaint=Color.LIGHTBLUE;
    private static double defaultStrokeWidth=3;
    private static int defaultShapeType = CIRCLE;
    private static double defaultWidth = 50;
    private static double defaultHeight = 50;
    protected int shapeType = 0;
    private static String defaultFontName = "Times Roman";
    private static double defaultFontSize = 15;
    private static double currentFontSize = 0;
    private static String defaultText = "Text";

    
    private Node makeShape(){
        Node s = null;
        switch(defaultShapeType){
            case CIRCLE: s = new Circle(Math.min(defaultWidth/2, defaultHeight/2)); shapeType = CIRCLE;break;
            case RECTANGLE: s = new Rectangle(defaultWidth,defaultHeight);shapeType = RECTANGLE;break;
            case ROUNDED_RECTANGLE: Rectangle r = new Rectangle(defaultWidth*2, defaultHeight);
                                    r.setArcHeight(20);
                                    r.setArcWidth(20);
                                    s = r;
                                    shapeType = ROUNDED_RECTANGLE;
                                    break;
            case OVAL:  Ellipse e = new Ellipse();
                        e.setCenterX(defaultWidth/2);
                        e.setCenterY(defaultHeight/2);
                        e.setRadiusX(defaultWidth);
                        e.setRadiusY(defaultHeight/2);
                        s = e;
                        shapeType = OVAL;
                        break;
            case TRIANGLE: s = new Polygon(defaultWidth*1.5,0,defaultWidth*2,defaultWidth,
                    defaultWidth,defaultWidth); shapeType = TRIANGLE;
                           break;
            case LINE: Line l = new Line();
                       l.setStartX(0);
                       l.setStartY(0);
                       l.setEndX(defaultWidth);
                       l.setEndY(defaultHeight);
                       s = l;
                       shapeType = LINE;
                       break;
            case TEXT_BOX: Text t = new Text(defaultText);
                           t.setStroke(defaultFillPaint);
                           s = t;
                           shapeType = TEXT_BOX;
                            break;
            case PICTURE: shapeType = PICTURE;break;
            case SQUIGGLE: shapeType = SQUIGGLE;
                           s = new Scribble();
                           break;
        }
        return s;
    }

    public MyShape(){
        super();
        selected = new SimpleBooleanProperty(false);
        selected.set(false);
        shape = makeShape();
        if (shape instanceof Text){
           ((Text)shape).setStroke(defaultStrokePaint);
           ((Text)shape).setFont(new Font(defaultFontName,defaultFontSize));
           ((Text)shape).setBoundsType(TextBoundsType.VISUAL);
        }
        else if (shape instanceof Shape){
            ((Shape)shape).setFill(defaultFillPaint);
            ((Shape)shape).setStroke(defaultStrokePaint);
            ((Shape)shape).setStrokeWidth(defaultStrokeWidth);
        } 
        this.getChildren().add(shape);
        this.setPadding(new Insets(5,5,5,5));//A couple of magic numbers 5..10
        this.setMinWidth(10);
        this.setMinHeight(10);
    }

    public MyShape(MyShape other){
        super();
        this.shape = new MyShape();
        this.selected = new SimpleBooleanProperty(false);
        this.selected.set(false);
        this.shapeType = other.shapeType;
        setDefaultShapeType(this.shapeType);
        this.makeShape();
        if (this.shape instanceof Shape){
            ((Shape)this.shape).setFill(defaultFillPaint);
            ((Shape)this.shape).setStroke(defaultStrokePaint);
            ((Shape)this.shape).setStrokeWidth(defaultStrokeWidth);
        }
        this.getChildren().add(shape);
       // this.setPadding(new Insets(5,5,5,5));//A couple of magic numbers 5..10
       // this.setMinWidth(10);
        //this.setMinHeight(10);
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
            e.setCenterX(width/2);
            e.setCenterY(height);
            e.setRadiusX(width/2.0 - this.getInsets().getLeft() - e.getStrokeWidth()/2.0);
            e.setRadiusY(height/2.0 - this.getInsets().getTop() - this.getInsets().getBottom() - e.getStrokeWidth());
        } else if(shape instanceof Polygon){
            Polygon s = (Polygon)shape;
            s.setScaleX(width/10.0 - this.getInsets().getLeft() - this.getInsets().getRight() - s.getStrokeWidth()/2.0);
            s.setScaleY(height/10.0 - this.getInsets().getTop() - this.getInsets().getBottom() - s.getStrokeWidth()/2.0);
        } else if(shape instanceof Text){
            Text t = (Text)shape;
            Bounds boundsInLocal = t.getBoundsInLocal();
            double h = boundsInLocal.getHeight();
            double w = boundsInLocal.getWidth();
            double newHeight = height - getInsets().getTop() - getInsets().getBottom();
            double newWidth = width - getInsets().getLeft() - getInsets().getRight();
            double wr = newWidth/w;
            double hr = newHeight/h;
            double scale = Math.min(wr, hr);
            double newSize = Math.max(t.getFont().getSize()*scale,2);
            currentFontSize = newSize;
            String name = t.getFont().getName();
            t.setFont(new Font(name,newSize));   
        } 
    }
    
    public void changeLineSize(MouseEvent me, MyShape ms){
        Line l = (Line)ms.shape;
        
        double x = me.getSceneX();
        double y = me.getSceneY();
        double endX = me.getX();
        double endY = me.getY();
        l.setStartX(x);
        l.setStartY(y);
        
        l.setEndX(endX);
        l.setEndY(endY);
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
        else if(shape instanceof Ellipse)
            return shape.contains(x - this.getInsets().getLeft(), y-this.getInsets().getTop());
        else if(shape instanceof Polygon)
            return shape.contains(x - this.getInsets().getLeft(), y-this.getInsets().getTop());
        else if(shape instanceof Line)
            return shape.contains(x - this.getInsets().getLeft(), y-this.getInsets().getTop());
        else if(shape instanceof Text){
            Insets insets = this.getInsets();
            return x>insets.getLeft()&& x < this.getWidth()-insets.getRight()&&y>insets.getTop()&&y<this.getHeight()-insets.getBottom();
        }
        else return false;
    }
    public static void setDefaultFillPaint(Paint value){
        defaultFillPaint = value;
    }
    public static void setDefaultStrokePaint(Paint value){
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
    public static void setDefaultText(String text){
        defaultText = text;
    }
    public static double getCurrentFontSize(){
        return currentFontSize;
    }
    public static double getDefaultWidth(){
        return defaultWidth;
    }
    public static double getDefaultHeight(){
        return defaultHeight;
    } 
    public static int getDefaultShapeType(){
        return defaultShapeType;
    }
    public static double getDefaultStrokeWidth(){
        return defaultStrokeWidth;
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
     @Override
    public void setFont(Font value) {
        if(shape instanceof Text){
           ((Text)shape).setFont(value);
        }
    }
    public void editText(Text t){
        if (this.isSelected()) t.setOnMouseClicked(e -> {
            String msg;
            msg = this.getOnKeyTyped().toString();
            t.setText(msg);
        });
    }

@Override
    public Paint getFillColor() {
        if(shape instanceof Shape)
            return ((Shape)shape).getFill();
        else return Color.BLACK;
    }
    @Override
    public Paint getStrokeColor() {
        if(shape instanceof Shape)
            return ((Shape)shape).getStroke();
        else return Color.BLACK;
    }
    @Override
    public double getStrokeWidth() {
        if(shape instanceof Shape){
            return ((Shape)shape).getStrokeWidth();
        } else return 0;
    }
    @Override
    public double[] getStrokeDashArray() {
        if(shape instanceof Shape){
            ObservableList<Double> l= ((Shape)shape).getStrokeDashArray();
            double [] a = new double[l.size()];
            for(int i = 0; i < a.length; i++)
                a[i] = l.get(i);
            return a;
        } else return new double[0];        
    }
    @Override
    public Font getFont() {
        if(shape instanceof Text){
           return ((Text)shape).getFont();
        }
        else return Font.getDefault();//System default
    }

    @Override
    public void setText(String value) {
        if(shape instanceof Text){
           ((Text)shape).setText(value);
        }
    }

    @Override
    public String getText() {
        if(shape instanceof Text){
           return ((Text)shape).getText();
        }
        else return "";
    }
}

