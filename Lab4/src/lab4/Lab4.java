/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author francoc
 */

class functionPane extends Pane {
    
        private SimpleDoubleProperty a,b,c,d;
        private Polyline curve;
        
        // axis
        private Line xAxis;
        private Line yAxis;
        
        // for the scale
        private double xMax,xMin, yMax, yMin;
        
        Rectangle rect;
        
        // default constructor
        public functionPane() {
            a = new SimpleDoubleProperty(1.0);
            b = new SimpleDoubleProperty(1.0);
            c = new SimpleDoubleProperty(0.0);
            d = new SimpleDoubleProperty(0.0);
            
            xMax = 5;
            xMin = -5;
            yMax = 3.14;
            yMin = -3.14;
            
            xAxis = new Line();
            yAxis = new Line();
            
            xAxis.setStrokeWidth(5.0);
            yAxis.setStrokeWidth(5.0);
            
            
            rect = new Rectangle();
            rect.setHeight(this.getHeight());
            rect.setWidth(this.getWidth());
            rect.setFill(Color.LIGHTGREEN);
            rect.setOpacity(0.2);
            
        }
        
        // draws the axis
        public void init(){
            
            Text txt5 = new Text("5");
            Text txt_5 = new Text("-5");
            Text txtPie = new Text("\u03c0");
            Text txt_Pie = new Text("-" + "\u03c0");
            
            Font font = new Font(20);
            txt5.setFont(font);
            txt_5.setFont(font);
            txtPie.setFont(font);
            txt_Pie.setFont(font);
            
            this.getChildren().addAll(xAxis,yAxis,rect, txt5, txt_5, 
                    txtPie, txt_Pie);
            
            this.widthProperty().addListener(e ->{
                xAxis.setEndX(this.getWidth());
                xAxis.setTranslateY(this.getHeight() / 2);
                xAxis.setTranslateY(this.getHeight() / 2);
                xAxis.setTranslateZ(1.0);
                
                txt5.setTranslateX(this.getWidth() / 2 + 5);
                txt5.setTranslateY(20);
                txt_5.setTranslateX(this.getWidth() / 2 + 5);
                txt_5.setTranslateY(this.getHeight() - 5);
                
                txt_Pie.setTranslateX(4);
                txt_Pie.setTranslateY(this.getHeight() / 2 + 20);
                txtPie.setTranslateX(this.getWidth() - 20);
                txtPie.setTranslateY(this.getHeight() / 2 + 20);
                
                yAxis.setEndY(this.getHeight());
                yAxis.setTranslateX(this.getWidth() / 2);
                yAxis.setTranslateZ(1.0);
               
            });
            this.heightProperty().addListener(e ->{
                yAxis.setEndY(this.getHeight());
                yAxis.setTranslateX(this.getWidth() / 2);
                yAxis.setTranslateZ(1.0);
                
                xAxis.setEndX(this.getWidth());
                xAxis.setTranslateY(this.getHeight() / 2);
                xAxis.setTranslateY(this.getHeight() / 2);
                xAxis.setTranslateZ(1.0);
                
                txt5.setTranslateX(this.getWidth() / 2 + 5);
                txt5.setTranslateY(20);
                txt_5.setTranslateX(this.getWidth() / 2 + 5);
                txt_5.setTranslateY(this.getHeight() - 5);
                
                txt_Pie.setTranslateX(4);
                txt_Pie.setTranslateY(this.getHeight() / 2 + 20);
                txtPie.setTranslateX(this.getWidth() - 20);
                txtPie.setTranslateY(this.getHeight() / 2 + 20);
            });
            
        }
        
        // setters for the properties, pass in a Double
        public void setA(Double d){
            a.set(d);
        }
        public void setB(Double d){
            b.set(d);
        }
        public void setC(Double d){
            c.set(d);
        }
        public void setD(Double a){
            d.set(a);
        }
        
        // getters for the properties, returns Double
        public Double getA(){
            return a.get();
        }
        public Double getB(){
            return b.get();
        }
        public Double getC(){
            return c.get();
        }
        public Double getD(){
            return d.get();
        }
        
        
        
        
}
public class Lab4 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        BorderPane root = new BorderPane();
        
        functionPane fpane = new functionPane();
        fpane.init();
        
        root.setCenter(fpane);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Sin Curve");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
