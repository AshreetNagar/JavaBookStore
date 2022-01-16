
package coe528FinalProject;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue; 
import javafx.beans.Observable; 
import javafx.event.EventType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import javafx.geometry.Insets;


public class customerCostScene extends Application{
    
    Label costLabel = new Label();
    Label statusLabel = new Label();
    final VBox vbox = new VBox();
    final Button logOutButton = new Button("Logout");
    Backend back = Backend.getInstance();
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        
        double points = 0;
        
        String status = "error.";
        if(points>= 1000){
            status = "Gold.";
        }
        else if(points <1000){
            status = "Silver.";
        }
        
        setMessage(0);
        
        
        logOutButton.setPadding(new Insets(10, 10, 10, 10));
        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new loginPageController().start(stage);
            }
        });
        vbox.setPadding(new Insets(100, 100, 100, 100));
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        
        stage.setScene(scene);
        stage.show();
    }
    
    public void setMessage(double cost){
        vbox.getChildren().clear();
        String status = back.getStatus();
        int points = back.getPoints();
        costLabel = new Label("Total Cost: "+cost);
        statusLabel = new Label("Points: "+ points+", Status: "+status);
        costLabel.setPadding(new Insets(10, 10, 10, 10)); 
        statusLabel.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(costLabel,statusLabel,logOutButton);
    }


}
