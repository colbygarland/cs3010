/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

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
        
        // for the x scale of the curve
        private double xScaler;
        private double yScaler;
        
        // default constructor
        public functionPane() {
            a = new SimpleDoubleProperty(1.0);
            b = new SimpleDoubleProperty(1.0);
            c = new SimpleDoubleProperty(0.0);
            d = new SimpleDoubleProperty(0.0);
            
            xAxis = new Line();
            yAxis = new Line();
            
            xAxis.setStrokeWidth(5.0);
            yAxis.setStrokeWidth(5.0);
            
            curve = new Polyline();
            
            xScaler = 100.0;
            yScaler = 80.0;
            
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
            xScaler = this.getWidth() / 6.25;
            curve.setTranslateY(this.getHeight() / 2);
            
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
                
                xScaler = this.getWidth() / 6.25;
                remakeCurve();
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
                
                curve.setTranslateY(this.getHeight() / 2);
            });
            
            a.addListener(e -> {
                remakeCurve();
            });
            b.addListener(e -> {
                remakeCurve();
            });
            c.addListener(e -> {
                remakeCurve();
            });
            d.addListener(e -> {
                remakeCurve();
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
            c.set(-d);
            remakeCurve();
        }
        public void setD(Double a){
            d.set(-a);
            remakeCurve();
        }
        
        // getters for the properties, returns Double
        public SimpleDoubleProperty getA(){
            return a;
        }
        public SimpleDoubleProperty getB(){
            return b;
        }
        public SimpleDoubleProperty getC(){
            return c;
        }
        public SimpleDoubleProperty getD(){
            return d;
        }
        
        
        
        // deletes the old curve, and recomputes the new curve
        private void remakeCurve(){
            this.getChildren().remove(curve);
            curve = new Polyline();
            compute();
            xScaler = this.getWidth() / 6.25;
            curve.setTranslateY(this.getHeight() / 2);
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
            
            for (int i = 0; i < 300; i++){
                
                y = a.doubleValue() * Math.sin(b.doubleValue() * x + c.doubleValue())
                        + d.doubleValue();
                
                curve.getPoints().add(x*xScaler);
                curve.getPoints().add(y*yScaler);
                
                x+=0.1;
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
            
            // constrain A from 0-5
            try {
                Double a = Double.parseDouble(txtbox1.getText());
                
                if (a >= 0 && a <=5) 
                    fpane.setA(a);
                else {
                    System.out.println("Enter a value from 0-5");
                    fpane.setA(1.0);
                    fpane.setB(1.0);
                    fpane.setC(0.0);
                    fpane.setD(0.0);
                }
            } catch(NumberFormatException incorrect){
                System.out.println("Enter a number from 0-5");
            }
            txtbox1.clear();
        });
        
        // B
        txtbox2.setOnAction((ActionEvent e) ->{
            
            // constrain B from 0-10
            try {
                Double b = Double.parseDouble(txtbox2.getText());
                
                if (b >= 0 && b <=10) 
                    fpane.setB(b);
                else {
                    System.out.println("Enter a value from 0-10");
                    fpane.setA(1.0);
                    fpane.setB(1.0);
                    fpane.setC(0.0);
                    fpane.setD(0.0);
                }
            } catch(NumberFormatException incorrect){
                System.out.println("Enter a number from 0-10");
            }
            txtbox2.clear();
        });
        
        // C
        txtbox3.setOnAction((ActionEvent e) ->{
            
           // constrain C from -pie to pie
            try {
                Double c = Double.parseDouble(txtbox3.getText());
                
                if (c >= -Math.PI && c <= Math.PI) 
                    fpane.setC(c);
                else {
                    System.out.println("Enter a value from -3.14 to 3.14");
                    fpane.setA(1.0);
                    fpane.setB(1.0);
                    fpane.setC(0.0);
                    fpane.setD(0.0);
                }
            } catch(NumberFormatException incorrect){
                System.out.println("Enter a number from -3.14 to 3.14");
            }
            txtbox3.clear();
        });
        
        // D
        txtbox4.setOnAction((ActionEvent e) ->{
            
           // constrain D from -5 to 5
            try {
                Double d = Double.parseDouble(txtbox4.getText());
                
                if (d >= -5 && d <= 5) 
                    fpane.setD(d);
                else {
                    System.out.println("Enter a value from -5 to 5");
                    fpane.setA(1.0);
                    fpane.setB(1.0);
                    fpane.setC(0.0);
                    fpane.setD(0.0);
                }
            } catch(NumberFormatException incorrect){
                System.out.println("Enter a number from -5 to 5");
            }
            txtbox4.clear();
        });
        
        // Reset button
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            fpane.setA(1.0);
            fpane.setB(1.0);
            fpane.setC(0.0);
            fpane.setD(0.0);
            
            txtbox1.setDisable(false);
            txtbox2.setDisable(false);
            txtbox3.setDisable(false);
            txtbox4.setDisable(false);
        });
        
        // Animate button
        Button animateBtn = new Button("Animate");
        animateBtn.setOnAction(e -> {
            
            EventHandler<ActionEvent> finished = f-> {
                fpane.setA(1.0);
                fpane.setB(1.0);
                fpane.setC(0.0);
                fpane.setD(0.0);

                txtbox1.setDisable(false);
                txtbox2.setDisable(false);
                txtbox3.setDisable(false);
                txtbox4.setDisable(false);
            };
            
            Timeline timeline = new Timeline();
            timeline.setCycleCount(0);
            timeline.getKeyFrames().add(new KeyFrame (Duration.millis(5000),
                new KeyValue (fpane.getA(), 5)));
            timeline.getKeyFrames().add(new KeyFrame (Duration.millis(5000),
                new KeyValue (fpane.getB(), 10)));
            timeline.getKeyFrames().add(new KeyFrame (Duration.millis(6000),
                finished));
            timeline.play();
            
            
            txtbox1.setDisable(true);
            txtbox2.setDisable(true);
            txtbox3.setDisable(true);
            txtbox4.setDisable(true);
        });
      
        
        hbox.getChildren().addAll(txt1, txtbox1, txt2, txtbox2
        , txt3, txtbox3, txt4, txtbox4, resetBtn, animateBtn);
        
        hbox.setSpacing(15);
        
        root.setBottom(hbox);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Sine Curve");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
