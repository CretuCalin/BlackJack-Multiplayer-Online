package sample.MenuScreen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static java.lang.Integer.parseInt;

/**
 * Created by teo on 14.04.2017.
 */
public class EditProfileScreen {


    public void display() {



        //Basic Pane
        Stage primaryStage = new Stage();

        primaryStage.setTitle("JavaFX 2 Login");

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(120,0,0,0));
        bp.setPrefSize(530,430);

        VBox imagePane = new VBox();
        imagePane.setPadding(new Insets(-80,0,0,50));
        imagePane.setSpacing(5);


        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));



        imagePane.getChildren().add(image);





        final TextField txtName = new TextField("Adam Levine");
        txtName.setFocusTraversable(false);
        txtName.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        Label textNoteName = new Label("Use your real name so people can recognize you.");
        textNoteName.setFont(Font.font("Courier New", FontWeight.BOLD, 11));



        VBox namePane = new VBox();
        namePane.setSpacing(5);
        namePane.setPadding(new Insets(-83,0,0,200));
        namePane.getChildren().add(txtName);
        namePane.getChildren().add(textNoteName);


        HBox usernamePane = new HBox();
        usernamePane.setSpacing(15);
        usernamePane.setPadding(new Insets(30,0,0,100));

        Label textUsername = new Label("Username");
        textUsername.setFont(Font.font("Courier New", FontWeight.BOLD, 17));
        textUsername.setTextFill(Color.web("663F15"));

        final TextField txtUsername = new TextField("adam.levine");
        txtUsername.setFocusTraversable(false);
        txtUsername.setPrefWidth(310);
        txtUsername.setDisable(true);
        txtUsername.setStyle("-fx-opacity: 1.0;");
        txtUsername.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        usernamePane.getChildren().add(textUsername);
        usernamePane.getChildren().add(txtUsername);

        HBox emailPane = new HBox();
        emailPane.setSpacing(45);
        emailPane.setPadding(new Insets(20,0,0,100));

        Label textEmail = new Label("Email");
        textEmail.setFont(Font.font("Courier New", FontWeight.BOLD, 17));
        textEmail.setTextFill(Color.web("663F15"));

        final TextField txtEmail = new TextField("adam.levine@gmail.com");
        txtEmail.setFocusTraversable(false);
        txtEmail.setPrefWidth(310);
        txtEmail.setDisable(true);
        txtEmail.setStyle("-fx-opacity: 1.0;");
        txtEmail.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        emailPane.getChildren().add(textEmail);
        emailPane.getChildren().add(txtEmail);

        HBox locationPane = new HBox();
        locationPane.setSpacing(15);
        locationPane.setPadding(new Insets(20,0,0,100));

        Label textLocation = new Label("Location");
        textLocation.setFont(Font.font("Courier New", FontWeight.BOLD, 17));
        textLocation.setTextFill(Color.web("663F15"));

        final TextField txtLocation = new TextField("Los Angeles, USA");
        txtLocation.setFocusTraversable(false);
        txtLocation.setPrefWidth(310);
        txtLocation.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));

        locationPane.getChildren().add(textLocation);
        locationPane.getChildren().add(txtLocation);



        GridPane ratingPane = new GridPane();
        ratingPane.setHgap(7);
        ratingPane.setVgap(5);
        ratingPane.setPadding(new Insets(20,0,0,100));

        Label textRating = new Label("Rating");
        textRating.setFont(Font.font("Courier New", FontWeight.BOLD, 17));
        textRating.setTextFill(Color.web("663F15"));

        Label textScore = new Label("82");
        textScore.setFont(Font.font("Courier New", FontWeight.BOLD, 17));
        textScore.setTextFill(Color.web("663F15"));

        ImageView image1;
        int wins = parseInt(textScore.getText());

        if(wins < 50){
            image1 = new ImageView(new Image(getClass().getResourceAsStream("/resources/brown1.png"),20,0,true,true));
        }
        else {
            if (wins < 100) {
                image1 = new ImageView(new Image(getClass().getResourceAsStream("/resources/brown2.png"), 20, 0, true, true));
            }
            else {
                if(wins < 200){
                    image1 = new ImageView(new Image(getClass().getResourceAsStream("/resources/orange2.png"), 20, 0, true, true));
                }
                else{
                    image1 = new ImageView(new Image(getClass().getResourceAsStream("/resources/brown.png"), 20, 0, true, true));
                }
            }
        }

        ratingPane.add(textRating,0,0);
        ratingPane.add(textScore,6,0);
        ratingPane.add(image1,7,0);

        GridPane buttonsPane = new GridPane();
        buttonsPane.setHgap(7);
        buttonsPane.setVgap(5);
        buttonsPane.setPadding(new Insets(50,0,0,100));

        Button saveButton = new Button("Save");
        saveButton.setPrefSize(90,10);


        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefSize(90,10);

        buttonsPane.add(saveButton,8,0);
        buttonsPane.add(cancelButton,22,0);





        //Adding GridPane



        VBox gridPane = new VBox();
        gridPane.getChildren().add(imagePane);
        gridPane.getChildren().add(namePane);
        gridPane.getChildren().add(usernamePane);
        gridPane.getChildren().add(emailPane);
        gridPane.getChildren().add(locationPane);
        gridPane.getChildren().add(ratingPane);
        gridPane.getChildren().add(buttonsPane);



        //Implementing Nodes for GridPane









        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setStyle("-fx-background-color: #C69F5E;");
        txtUsername.setId("textField");
        txtEmail.setId("textField");
        txtLocation.setId("textField");
        txtName.setId("textField");

        textNoteName.setId("textNote");
        saveButton.setId("btnEditProfile");
        cancelButton.setId("btnLogout");



        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Save");
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/resources/profile_picture.jpg"),100,100,true,true));
                imagePane.getChildren().clear();
                imagePane.getChildren().add(image);
                txtName.setText("Adam Levine");
                txtLocation.setText("Los Angeles, USA");
            }
        });

        imagePane.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                FileChooser fc = new FileChooser();

                //Set extension filter
                fc.getExtensionFilters().addAll();
                File selectedFile = fc.showOpenDialog(null);

                if( selectedFile != null){

                    String s = selectedFile.getAbsolutePath();
                    s = s.replace("\\", "/");
                    s = "file:///" + s;
                    ImageView image = new ImageView(new Image((s),100, 100, true, true));
                    imagePane.getChildren().clear();
                    imagePane.getChildren().add(image);



                }

            }
        });









        //AGridPane layout to BorderPane Layout

        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("resources/editProfile.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
        // primaryStage.setMaximized(true);
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}
