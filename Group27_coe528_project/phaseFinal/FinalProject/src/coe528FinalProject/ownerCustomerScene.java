package coe528FinalProject;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.control.SelectionMode;

public class ownerCustomerScene extends Application{
    
    private TableView<customer> table = new TableView<customer>();
    final VBox vbox = new VBox();
    final HBox hb = new HBox();
    final HBox hb2 = new HBox();
    final ObservableList<customer> data = FXCollections.observableArrayList();
    final Backend back = Backend.getInstance();

    
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new Group());
        stage.setTitle("Bookstore App");
        stage.setWidth(450);
        stage.setHeight(550);
                
        TableColumn nameCol = new TableColumn("Username");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
            new PropertyValueFactory<customer, String>("userName"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<book, String>>() {
                @Override
                public void handle(CellEditEvent<book, String> t) {
                    ((book) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setName(t.getNewValue());
                }
            }
        );    
        
        TableColumn dokok = new TableColumn("Password");
        dokok.setMinWidth(100);
        dokok.setCellValueFactory(
            new PropertyValueFactory<customer, String>("passwd"));
        
        TableColumn pointsCol = new TableColumn("Points");
        pointsCol.setMinWidth(100);
        pointsCol.setCellValueFactory(
            new PropertyValueFactory<customer, String>("points"));
        
        updateCustomerList();
        table.setItems(data);
        table.getColumns().addAll(nameCol, dokok, pointsCol);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("Name");
        addFirstName.setMaxWidth(nameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(dokok.getPrefWidth());
        addLastName.setPromptText("Password");
        
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean result = back.addCustomer(addFirstName.getText(), addLastName.getText());
                updateCustomerList();
                if(result == false){
                        if(vbox.getChildren().size() >= 4)
                            vbox.getChildren().remove(3);
                        Label wrongPass = new Label("ERROR: Customer was not added (No duplicates or customers with '</' in their name)");
                        vbox.getChildren().add(wrongPass);

                }
            }
        });
        
        hb.getChildren().addAll(addFirstName, addLastName, addButton);
        hb.setSpacing(3);
        
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for(customer c: table.getSelectionModel().getSelectedItems()){
                    back.removeCustomer(c.getUserName());
                }
                updateCustomerList();
            }
        });
        
        final Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new ownerHomePage().start(stage);
            }
        });
        
        hb2.getChildren().addAll(deleteButton,backButton);
        hb2.setSpacing(3);
        
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hb,hb2);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    public void updateCustomerList(){
        data.clear();
        for(customer c : customerManager.getInstance().getCustomers()){
            data.add(c);
        }
    }
    
}
