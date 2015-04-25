/*
All the interface code
*/

package drawing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Drawing extends Application {
    
    DrawPane pane = new DrawPane();
    BorderPane root = new BorderPane();
    ColorPicker colorpicker = new ColorPicker();
    ColorPicker strokepicker = new ColorPicker();
    
    public void help(){
        BorderPane helpPane = new BorderPane();
        VBox helpBox = new VBox();
        Font myFont = new Font("Arial", 16);
        
        Text help1 = new Text("Welcome to Drawesome!");
        help1.setUnderline(true);
        help1.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.ITALIC,25));
        
        Text help2 = new Text("To use Drawesome:");
        help2.setFont(Font.font("Arial", FontWeight.BOLD,16));
        
        
        Text help3 = new Text("   • Click anywhere to place an object");
        help3.setFont(myFont);
        Text help4 = new Text("   • An object can be a shape, line, text, pixel spray, scribble or picture");
        help4.setFont(myFont);
        Text help5 = new Text("   • To select an object, left click on the desired object to move/resize");
        help5.setFont(myFont);
        Text help6 = new Text("   • Click anywhere else to deselect the object");
        help6.setFont(myFont);
        Text help7 = new Text("   • To copy/paste/delete, select an object and right click to pick option");
        help7.setFont(myFont);
        Text help8 = new Text("   • Hover over boxes on the side for the sidebar selectors");
        help8.setFont(myFont);
        Text help9 = new Text("   • In left sidebar, move the slider or type in desired outline width");
        help9.setFont(myFont);
        Text help10 = new Text("   • Right click anywhere (including on objects) to bring up the context menu");
        help10.setFont(myFont);
        
        helpBox.getChildren().addAll(help1,help2,help3,help4,help5,help6,help7,help8,help9,help10);
        helpBox.setStyle("-fx-background-color: lightgray;");
        helpPane.setCenter(helpBox);
        
        Stage helpStage = new Stage();
        Scene helpScene = new Scene(helpPane, 650, 210);
        helpStage.setResizable(false);
        helpStage.setScene(helpScene);
        helpStage.setTitle("Help");
        helpStage.show();
    }
    // all the menu bar code here
    public void menuBar(){
        // menu bar to hold everything
        MenuBar menubar = new MenuBar();
        menubar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        menubar.setStyle("-fx-background-color: darkgray;");
        // menu shape to select which shape to use
        Menu shapemenu = new Menu("Shape");
        Menu editmenu = new Menu("Edit");
        Menu linemenu = new Menu("Line");
        Menu picturemenu = new Menu("Picture");
        Menu filemenu = new Menu("File");
        Menu helpmenu = new Menu("Help");
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
        MenuItem open = new MenuItem("Open");
        MenuItem undo = new MenuItem("Undo");
        MenuItem redo = new MenuItem("Redo");
        MenuItem help = new MenuItem("Help Contents");
        
        Text colorchoosertext = new Text("Adjust Fill Colour:");
        colorchoosertext.setTranslateY(7);
        Text colorstroketext = new Text("Adjust Outline Colour:");
        colorstroketext.setTranslateY(7);
        
        // set all the actions to change the shapes
        circle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.CIRCLE));
        rectangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.RECTANGLE));
        roundedrectangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.ROUNDED_RECTANGLE));
        oval.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.OVAL));
        triangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TRIANGLE));
        line.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.LINE));
        textbox.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TEXT_BOX));
        scribble.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.SQUIGGLE));
        
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
        help.setOnAction(e -> help());
        save.setOnAction(e -> save());
        open.setOnAction(e -> load());
        
        // disable things that either don't work or I don't want to work
        print.setDisable(false);
        //scribble.setDisable(true);
        pixelspray.setDisable(true);
        choosepicture.setDisable(false);
        
        colorpicker.setValue(Color.RED);
        colorpicker.setStyle("-fx-background-color: darkgray;");
        colorpicker.setOnAction(e -> {
                Color c = colorpicker.getValue();
                MyShape.setDefaultFillPaint(c);
                if (!(pane.getSelectedShape() == null))
                    pane.getSelectedShape().setFillColor(c);
        });
        strokepicker.setValue(Color.BLACK);
        strokepicker.setStyle("-fx-background-color: darkgray;");
        strokepicker.setOnAction(e -> {
            Color c = strokepicker.getValue();
            MyShape.setDefaultStrokePaint(c);
            if (!(pane.getSelectedShape() == null)) pane.getSelectedShape().setStrokeColor(c);
        });
        
        NEW.setOnAction(e -> {
            pane = new DrawPane();
            root.setCenter(pane);
        });
        
        menubar.getMenus().addAll(filemenu,editmenu,shapemenu, linemenu, picturemenu,helpmenu);
        shapemenu.getItems().addAll(circle, rectangle, roundedrectangle, oval, triangle);
        editmenu.getItems().addAll(undo, redo);
        linemenu.getItems().addAll(line, scribble, pixelspray, textbox);
        picturemenu.getItems().add(choosepicture);
        filemenu.getItems().addAll(NEW,save,open, print,close);
        helpmenu.getItems().addAll(help);
        
        HBox hbox = new HBox();
        hbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,CornerRadii.EMPTY,Insets.EMPTY)));
        hbox.getChildren().addAll(menubar, colorchoosertext, colorpicker, colorstroketext, strokepicker);
        hbox.setPrefWidth(600);
        
        root.setTop(hbox);
    }
    // all the side toolbar code here
    public void sideBar(){
        VBox sidebar = new VBox();
        sidebar.setSpacing(10);
        sidebar.setFillWidth(false);
        sidebar.setStyle("-fx-background-color: darkgray;");
        Button btncircle = new Button("Circle");
        Button btnsquare = new Button("Square");
        Button btnrounded = new Button("Rounded Rectangle");
        Button btntriangle = new Button("Triangle");
        Button btnoval = new Button("Oval");
        Button btnzordering = new Button(" Send  Shape  Back ");
        Button btnzorder2 = new Button(" Bring Shape Forward");
        
        Text slidertext = new Text("Adjust Outline Width");
        Slider strokeslider = new Slider(0,20,3);
        strokeslider.setShowTickMarks(true);
        strokeslider.setShowTickLabels(true);
        strokeslider.setOnMouseClicked(e -> MyShape.setDefaultStrokeWidth(strokeslider.getValue()));
        
        Text slidertext2 = new Text("Enter Outline Width:");
        TextField sliderfield = new TextField();
        sliderfield.setPrefColumnCount(2);
        sliderfield.setOnAction(e ->{
            
            // constrain A from 0-5
            try {
                Double a = Double.parseDouble(sliderfield.getText());
                
                if (a >= 0 && a <=25) {
                    MyShape.setDefaultStrokeWidth(a);
                    strokeslider.setValue(a);
                } else {
                    System.out.println("Enter a value from 0-20");
                }
            } catch(NumberFormatException incorrect){
                System.out.println("Enter a number from 0-5");
            }
            sliderfield.clear();
        });
        
        btncircle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.CIRCLE));
        btncircle.setTooltip(new Tooltip("Sets default shape to Circle"));
        btnsquare.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.RECTANGLE));
        btnsquare.setTooltip(new Tooltip("Sets default shape to Square"));
        btnrounded.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.ROUNDED_RECTANGLE));
        btnrounded.setTooltip(new Tooltip("Sets default shape to Rounded Rectangle"));
        btntriangle.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.TRIANGLE));
        btntriangle.setTooltip(new Tooltip("Sets default shape to Triangle"));
        btnoval.setOnAction(e -> MyShape.setDefaultShapeType(MyShape.OVAL));
        btnoval.setTooltip(new Tooltip("Sets default shape to Oval"));
        btnzordering.setOnAction(e -> {
            if (pane.getSelectedShape() != null){
                pane.setZOrderingBack(pane.getSelectedShape());
            }
        });
        btnzordering.setTooltip(new Tooltip("Sends the selected object to the back"));
        btnzorder2.setTooltip(new Tooltip("Brings the selected object to the front"));
        btnzorder2.setOnAction(e -> {
            if (pane.getSelectedShape() != null){
                pane.setZOrderingFront(pane.getSelectedShape());
            }
        });

  sidebar.getChildren().addAll(btncircle, btnsquare, btnrounded, btntriangle
        , btnoval, slidertext, strokeslider, slidertext2, sliderfield, btnzordering, btnzorder2);
        
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
            root.setLeft(sidebar);
            sidebar.setOnMouseExited(f -> root.setLeft(vbox));
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
        Button btnsquiggle = new Button("Scribble");
        Button btntext = new Button("Text Box");
        Label lbl = new Label("Change Text:");
        TextField txtfield = new TextField();
        
        Label fontlabel = new Label("Change Font:");
        Button font1 = new Button("Serif");
        Button font2 = new Button("Sans-Serif");
        Button font3 = new Button("Purisa");
        sidebar2.getChildren().addAll(btnline, btnsquiggle, btntext, lbl,txtfield,
                fontlabel, font1, font2, font3);
        
        txtfield.setOnAction(e -> {
            pane.changeText(txtfield.getText());
            txtfield.clear();
        });
        
        btnline.setOnAction(a -> MyShape.setDefaultShapeType(MyShape.LINE));
        btnline.setTooltip(new Tooltip("Sets default shape to Line"));
        btntext.setOnAction(b -> MyShape.setDefaultShapeType(MyShape.TEXT_BOX));
        btntext.setTooltip(new Tooltip("Sets default shape to Text"));
        btnsquiggle.setOnAction(c -> MyShape.setDefaultShapeType(MyShape.SQUIGGLE));
        btnsquiggle.setTooltip(new Tooltip("Sets default shape to Scribble"));
        txtfield.setTooltip(new Tooltip("Type in text to change the text"));
     
        font1.setOnAction(e -> {
            if (pane.getSelectedShape() != null){
                if (pane.getSelectedShape().shapeType == MyShape.TEXT_BOX){
                    pane.getSelectedShape().setFont(Font.font(java.awt.Font.SERIF, pane.getSelectedShape().getCurrentFontSize()));
                }  
            }
            
        });
        font2.setOnAction(e -> {
            if (pane.getSelectedShape() != null){
                if (pane.getSelectedShape().shapeType == MyShape.TEXT_BOX){
                    pane.getSelectedShape().setFont(Font.font(java.awt.Font.SANS_SERIF, pane.getSelectedShape().getCurrentFontSize()));
                }
            }
        });
        font3.setOnAction(e -> {
            if (pane.getSelectedShape() != null){
                if (pane.getSelectedShape().shapeType == MyShape.TEXT_BOX){
                    pane.getSelectedShape().setFont(new Font("Purisa", pane.getSelectedShape().getCurrentFontSize()));
                }
            }
        });
        rect2.setOnMouseEntered(d -> {
            root.setRight(sidebar2);
            sidebar2.setOnMouseExited(f -> root.setRight(vbox2));
        });
    }

    public void save(){
        
        FileChooser saveChooser = new FileChooser();
        File file = saveChooser.showSaveDialog(new Stage());
        String name = file.getName().concat(".png");
        File newFile = new File(name);
        
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", newFile);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }
    
    public void load(){
        FileChooser loadChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        loadChooser.getExtensionFilters().add(extFilter);
        File file = loadChooser.showOpenDialog(new Stage());
        
        ImageView img = new ImageView();
        if (file != null) img.setImage(new Image("File:" + file.getPath()));
        img.setPreserveRatio(true);
        img.setFitHeight(pane.getHeight());
        img.setFitWidth(pane.getWidth());
        
        if (pane.getChildren() != null) pane.getChildren().add(img);
    }
    
    
    @Override
    public void start(Stage primaryStage) {

        menuBar();
        sideBar();
        
        root.setCenter(pane);
        root.setOnKeyPressed(e -> pane.keyTyped(e));
        
        File f = new File(".");
        File[] pictures = f.listFiles(new JpgFilter());
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Drawesome");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:" + pictures[0]));
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
