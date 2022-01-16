package coe528FinalProject;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class loginPageController extends Application{
    
    Backend back = Backend.getInstance();
    final HBox hb = new HBox();
    Stage primaryStage;
    
    public static void main(String [] args){
        launch(args);
    }
    
    @Override 
    public void start(Stage primaryStage){
        primaryStage.setOnCloseRequest(event ->{
            back.saveAndExit();
        });
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Bookstore App");
        primaryStage.setWidth(650);
        primaryStage.setHeight(450);
        
        final TextField userNameField = new TextField();
        userNameField.setPromptText("User Name");
        final PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        final Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int result = back.login(userNameField.getText(), passwordField.getText());
                switch(result){
                    case 0:
                        Label wrongPass = new Label("Invalid Username or Password");
                        vbox.getChildren().add(wrongPass);
                        if(vbox.getChildren().size() >= 3)
                            vbox.getChildren().remove(2);
                        break;
                    case 1:
                        customerBuyScene cbs = new customerBuyScene();
                        cbs.start(primaryStage);
                        break;
                    case 2:
                        new ownerHomePage().start(primaryStage);
                        break;
                }
            }
        });
        
        hb.getChildren().addAll(userNameField,passwordField, loginButton);
        hb.setSpacing(3);
 
        vbox.getChildren().addAll(hb);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
