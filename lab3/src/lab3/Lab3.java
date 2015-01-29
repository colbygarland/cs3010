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
import javafx.geometry.Pos;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author francoc
 */
public class Lab3 extends Application {

    class JPGFilter implements FileFilter {

            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                if (pathname.isDirectory()) {
                    return false;
                }
                if (name == null) {
                    return false;
                }
                return name.toLowerCase().endsWith(".jpg")
                        || name.toLowerCase().endsWith(".png")
                        || name.toLowerCase().endsWith(".gif");
            }
        }
    
    int index = 0;
    File f = new File(".");
    File[] pictures = f.listFiles(new JPGFilter());
    int NUMBER_OF_PICTURES = pictures.length;

    // image stuff here
    ImageView imgView = new ImageView();
    //imgView.setFitHeight(700);
    //imgView.setFitWidth(500);
    //imgView.setPreserveRatio(true);

    // text stuff for bottom corner
    Text text = new Text();
    Font font = new Font(20);
    
    
    
    public void previous(){
         if (index > 0) {
                    index--;
                } else {
                    index = NUMBER_OF_PICTURES - 1;
                }
                Image img = new Image("file:" + pictures[index]);
                imgView.setFitHeight(700);
                imgView.setFitWidth(500);
                imgView.setPreserveRatio(true);
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
    
    public void next(){
        if (index < NUMBER_OF_PICTURES - 1) {
                    index++;
                } else {
                    index = 0;
                }
                Image img = new Image("file:" + pictures[index]);
                imgView.setFitHeight(700);
                imgView.setFitWidth(500);
                imgView.setPreserveRatio(true);
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

    @Override
    public void start(Stage primaryStage) {

        

       

// BUTTON HANDLING 
        // previous button
        Button btnPrev = new Button();
        btnPrev.setText("Prev");
        btnPrev.setFont(font);
        btnPrev.setStyle("-fx-font: 22 Arial; -fx-base: #d3d3d3;");
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                previous();
            }
        });

        btnPrev.setOnMouseEntered(e -> {
            btnPrev.setTextFill(Color.BLUE);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(5);
            dropShadow.setOffsetY(5);
            dropShadow.setRadius(5);
            dropShadow.setColor(Color.color(0, 0, 0, 0.45));
            btnPrev.setEffect(dropShadow);
        });

        btnPrev.setOnMouseExited(e -> {
            btnPrev.setTextFill(Color.BLACK);
            btnPrev.setEffect(null);
        });

        // next button
        Button btnNext = new Button();
        btnNext.setText("Next");
        btnNext.setStyle("-fx-font: 22 jokerman; -fx-base: #d3d3d3;");
        btnNext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                next();
            }
        });

        btnNext.setOnMouseEntered(e -> {
            btnNext.setTextFill(Color.BLUE);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(-5);
            dropShadow.setOffsetY(5);
            dropShadow.setRadius(5);
            dropShadow.setColor(Color.color(0, 0, 0, 0.45));
            btnNext.setEffect(dropShadow);
        });

        btnNext.setOnMouseExited(e -> {
            btnNext.setTextFill(Color.BLACK);
            btnNext.setEffect(null);
        });

        BorderPane borderPane = new BorderPane();

        Image begin = new Image("file:" + pictures[0]);
        imgView.setImage(begin);
        imgView.setFitHeight(700);
        imgView.setFitWidth(500);
        imgView.setPreserveRatio(true);
        borderPane.setCenter(imgView);
        String str = pictures[0].getName();
        text.setText(str);
        borderPane.setTop(text);

        borderPane.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.RIGHT || ke.getCode() == KeyCode.D) {
                next();
            }
            if (ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.S) {
                previous();
            }
            if (ke.getCode() == KeyCode.ESCAPE) {
                System.exit(1);
            }
        });
        
        VBox vbox = new VBox();
        vbox.getChildren().add(btnPrev);
        
        vbox.setAlignment(Pos.CENTER);
        btnPrev.setAlignment(Pos.CENTER);
        borderPane.setLeft(vbox);
        
        VBox vbox2 = new VBox();
        vbox2.getChildren().add(btnNext);
        vbox2.setAlignment(Pos.CENTER);
        btnNext.setAlignment(Pos.CENTER);
        borderPane.setRight(vbox2);
        
        Rectangle rectLeft = new Rectangle(200,100);
        rectLeft.relocate(0, 0);
        rectLeft.setFill(Color.LIGHTGREY);
        
        Text leftText = new Text("Previous Picture");
        leftText.relocate(0,0);
        Text rightText = new Text("Next Picture");
        rightText.relocate(600,0);
        
        rectLeft.setOnMouseEntered(e -> {
             previous();
        });
        
        Rectangle rectRight = new Rectangle(200,100);
        rectRight.relocate(600,0);
        rectRight.setFill(Color.LIGHTGREY);
        
        
        rectRight.setOnMouseEntered(e -> {
               next();
        });
        
        Pane pane = new Pane();
        pane.getChildren().addAll(rectRight, rectLeft, leftText, rightText);
        
        
        
        borderPane.setBottom(pane);
       
        Scene scene = new Scene(borderPane, 800, 600);

        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
