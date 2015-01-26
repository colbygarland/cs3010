/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author francoc
 */
public class Lab2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        TextField txf = new TextField();
        Rectangle rect = new Rectangle();
        ImageView imgview = new ImageView("file:happy-dog2.png");
        Text txt = new Text();
        double r = 0;
        
        btn.setText("Fix");
        btn.setTextFill(Color.RED);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                imgview.setRotate(0);
                txt.setFill(Color.HOTPINK);
                rect.setRotate(0);
            }
        });
        
        
        BorderPane root = new BorderPane();
        
        // rectangle stuff
        rect.setFill(Color.RED);
        rect.setWidth(200);
        rect.setHeight(100);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(10);
        rect.relocate(25, 200);
        rect.setArcHeight(50);
        rect.setArcWidth(50);
        rect.setRotate(10);
        
        // text stuff
        txt.setText("Help");
        Font courier = new Font(30);
        txt.setFont(courier);
        txt.relocate(50,100);
        
        // image view
        imgview.relocate(250,100);
        imgview.setRotate(-10);
        
        // text box
        txf.relocate(50,75);
        txf.setText("Help");
        
        
        
        root.setLeft(btn);
        root.setCenter(imgview);
        root.setRight(txt);
        root.setBottom(rect);
        
        Scene scene = new Scene(root, 640, 480);
        
        primaryStage.setTitle("Hello World!");
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
