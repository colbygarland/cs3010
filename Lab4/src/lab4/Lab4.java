/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
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
            
            curve = new Polyline();
            
        }
        
        // draws the axis
        public void init(){
            
        // Text initiating stuff
            Text txt5 = new Text("5");
            Text txt_5 = new Text("-5");
            Text txtPie = new Text("\u03c0");
            Text txt_Pie = new Text("-" + "\u03c0");
            
            Font font = new Font(20);
            txt5.setFont(font);
            txt_5.setFont(font);
            txtPie.setFont(font);
            txt_Pie.setFont(font);
            
        // Curve initiation
            compute();
            curve.setScaleX(10);
            curve.setScaleY(15);
            curve.setTranslateY(300);
            
            this.getChildren().addAll(xAxis,yAxis, txt5, txt_5, 
                    txtPie, txt_Pie, curve);
            this.setStyle("-fx-background-color: lightgreen;");
                    
            this.widthProperty().addListener(e ->{
                xAxis.setEndX(this.getWidth());
                xAxis.setTranslateY(this.getHeight() / 2);
                
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
                
                curve.setTranslateX(this.getWidth() / 2);
                curve.setTranslateY(this.getHeight() / 2);
               
            });
            this.heightProperty().addListener(e ->{
                yAxis.setEndY(this.getHeight());
                yAxis.setTranslateX(this.getWidth() / 2);
                
                xAxis.setEndX(this.getWidth());
                xAxis.setTranslateY(this.getHeight() / 2);
                
                txt5.setTranslateX(this.getWidth() / 2 + 5);
                txt5.setTranslateY(20);
                txt_5.setTranslateX(this.getWidth() / 2 + 5);
                txt_5.setTranslateY(this.getHeight() - 5);
                
                txt_Pie.setTranslateX(4);
                txt_Pie.setTranslateY(this.getHeight() / 2 + 20);
                txtPie.setTranslateX(this.getWidth() - 20);
                txtPie.setTranslateY(this.getHeight() / 2 + 20);
                
                curve.setTranslateX(this.getWidth() / 2);
                curve.setTranslateY(this.getHeight() / 2);
            });
            
        }
        
        // setters for the properties, pass in a Double
        public void setA(Double d){
            a.set(d);
            remakeCurve();
        }
        public void setB(Double d){
            b.set(d);
            remakeCurve();
        }
        public void setC(Double d){
            c.set(d);
            remakeCurve();
        }
        public void setD(Double a){
            d.set(a);
            remakeCurve();
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
        
        // deletes the old curve, and recomputes the new curve
        private void remakeCurve(){
            this.getChildren().remove(curve);
            curve = new Polyline();
            compute();
            this.getChildren().add(curve);
        }
        
        // computes the points using the a,b,c,d
        private void compute(){
            /**
             * 
             *  y = asin(bx + c)+d
             * 
             */
            double x = 0.0,y = 0.0;
            
            for (int i = 0; i < 100; i++){
                
                y = a.doubleValue() * Math.sin(b.doubleValue() * x + c.doubleValue())
                        + d.doubleValue();
                
                curve.getPoints().add(x);
                curve.getPoints().add(y);
                
                x++;
            }
        }
        
        
}
public class Lab4 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        BorderPane root = new BorderPane();
        
        functionPane fpane = new functionPane();
        fpane.init();
        
        root.setCenter(fpane);
        
        HBox hbox = new HBox();
        
        Text txt1 = new Text("Enter a:");
        TextField txtbox1 = new TextField();
        txtbox1.setPrefColumnCount(5);
        Text txt2 = new Text("Enter b:");
        TextField txtbox2 = new TextField();
        txtbox2.setPrefColumnCount(5);
        Text txt3 = new Text("Enter c:");
        TextField txtbox3 = new TextField();
        txtbox3.setPrefColumnCount(5);
        Text txt4 = new Text("Enter d:");
        TextField txtbox4 = new TextField();
        txtbox4.setPrefColumnCount(5);
        
        // A
        txtbox1.setOnAction((ActionEvent e) ->{
            fpane.setA(Double.parseDouble(txtbox1.getText()));
            txtbox1.clear();
        });
        
        // B
        txtbox2.setOnAction((ActionEvent e) ->{
            fpane.setB(Double.parseDouble(txtbox2.getText()));
            txtbox2.clear();
        });
        
        // C
        txtbox3.setOnAction((ActionEvent e) ->{
            fpane.setC(Double.parseDouble(txtbox3.getText()));
            txtbox3.clear();
        });
        
        // D
        txtbox4.setOnAction((ActionEvent e) ->{
            fpane.setD(Double.parseDouble(txtbox4.getText()));
            txtbox4.clear();
        });
        
        // Reset button
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            fpane.setA(1.0);
            fpane.setB(1.0);
            fpane.setC(0.0);
            fpane.setD(0.0);
        });
      
        
        hbox.getChildren().addAll(txt1, txtbox1, txt2, txtbox2
        , txt3, txtbox3, txt4, txtbox4, resetBtn);
        
        root.setBottom(hbox);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Sine Curve");
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
