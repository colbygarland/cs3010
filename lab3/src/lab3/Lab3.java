/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

import java.io.File;
import java.io.FileFilter;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author francoc
 */
public class Lab3 extends Application {
    
      /*
        Image image = new Image("file:" + pictures[0]);
        imgView.setImage(image);
      */
    int index = 0;
    
    @Override
    public void start(Stage primaryStage) {
        
         class JPGFilter implements FileFilter{
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                if (pathname.isDirectory()) return false;
                if(name==null)return false;
                return name.toLowerCase().endsWith(".jpg") || 
                       name.toLowerCase().endsWith(".png") ||
                       name.toLowerCase().endsWith(".gif");
            }
        }
        
        File f = new File(".");
        File [] pictures = f.listFiles(new JPGFilter());
        int NUMBER_OF_PICTURES = pictures.length;
        
        // image stuff here
        ImageView imgView = new ImageView();
        imgView.setFitHeight(700);
        imgView.setFitWidth(500);
        imgView.setPreserveRatio(true);
        
        // text stuff for bottom corner
        Text text = new Text();
        Font font = new Font(20);
        
        
// BUTTON HANDLING 
        
    // previous button
        Button btnPrev = new Button();
        btnPrev.setText("Prev");
        btnPrev.setFont(font);
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if (index > 0) index--;
                else index = NUMBER_OF_PICTURES - 1;
                Image img = new Image("file:" + pictures[index]);
                imgView.setImage(img);
                String str = pictures[index].getName();
                text.setText(str);
                
                FadeTransition ft = new FadeTransition(Duration.millis(500), imgView);
                ft.setFromValue(0.3);
                ft.setToValue(1.0);
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play();
            }
        });
        
        btnPrev.setOnMouseEntered(e -> {
            btnPrev.setTextFill(Color.CORAL);
            DropShadow shadow = new DropShadow();
            btnPrev.setEffect(shadow);
        });
        
        btnPrev.setOnMouseExited(e -> {
            btnPrev.setTextFill(Color.BLACK);
            btnPrev.setEffect(null);
        });
                
                
    // next button
        Button btnNext = new Button();
        btnNext.setText("Next");
        btnNext.setFont(font);
        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if (index < NUMBER_OF_PICTURES - 1) index++;
                else index = 0;
                Image img = new Image("file:" + pictures[index]);
                imgView.setImage(img);
                String str = pictures[index].getName();
                text.setText(str);
                
                FadeTransition ft = new FadeTransition(Duration.millis(500), imgView);
                ft.setFromValue(0.3);
                ft.setToValue(1.0);
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play();
            }
        });
        
         btnNext.setOnMouseEntered(e -> {
            btnNext.setTextFill(Color.CORAL);
            DropShadow shadow = new DropShadow();
            btnNext.setEffect(shadow);
        });
        
        btnNext.setOnMouseExited(e -> {
            btnNext.setTextFill(Color.BLACK);
            btnNext.setEffect(null);
        });
        
        
        BorderPane borderPane = new BorderPane();
        
        borderPane.setLeft(btnPrev);
        borderPane.setRight(btnNext);
        
        Image begin = new Image("file:" + pictures[0]);
        imgView.setImage(begin);
        borderPane.setCenter(imgView);
        String str = pictures[0].getName();
        text.setText(str);
        borderPane.setBottom(text);
        
        borderPane.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.RIGHT || ke.getCode() == KeyCode.D){
                if (index < NUMBER_OF_PICTURES - 1) index++;
                    else index = 0;
                    Image img = new Image("file:" + pictures[index]);
                    imgView.setImage(img);
                    String str1 = pictures[index].getName();
                    text.setText(str1);
                
                    FadeTransition ft = new FadeTransition(Duration.millis(500), imgView);
                    ft.setFromValue(0.3);
                    ft.setToValue(1.0);
                    ft.setCycleCount(1);
                    ft.setAutoReverse(true);
                    ft.play();
            }
            if (ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.S){
                if (index > 0) index--;
                else index = NUMBER_OF_PICTURES - 1;
                Image img = new Image("file:" + pictures[index]);
                imgView.setImage(img);
                String str2 = pictures[index].getName();
                text.setText(str2);
                
                FadeTransition ft = new FadeTransition(Duration.millis(500), imgView);
                ft.setFromValue(0.3);
                ft.setToValue(1.0);
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play(); 
            }
            if (ke.getCode() == KeyCode.ESCAPE){
                System.exit(1);
            }
        });
        
        
        Scene scene = new Scene(borderPane, 800, 600);
      
        
        primaryStage.setTitle("Image Viewer");
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
