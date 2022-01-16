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
import javafx.scene.control.SelectionMode;;

public class ownerBookScene extends Application{
    
    private TableView<book> table = new TableView<book>();
    final HBox hb = new HBox();
    final HBox hb2 = new HBox();
    final ObservableList<book> data = FXCollections.observableArrayList();
    final Backend back = Backend.getInstance();
    final VBox vbox = new VBox();

    
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new Group());
        stage.setTitle("Bookstore App");
        stage.setWidth(450);
        stage.setHeight(550);

        TableColumn firstNameCol = new TableColumn("Book Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
            new PropertyValueFactory<book, String>("name"));
        
        TableColumn dokok = new TableColumn("Book Price");
        dokok.setMinWidth(100);
        dokok.setCellValueFactory(
            new PropertyValueFactory<book, String>("cost"));
        
        
        updateBookList();
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, dokok);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("Name:");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(dokok.getPrefWidth());
        addLastName.setPromptText("Price");
        
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                    double price = Double.parseDouble(addLastName.getText());
                    boolean result = back.addBooks(addFirstName.getText(), price);
                    if(result == false){
                        if(vbox.getChildren().size() >= 4)
                            vbox.getChildren().remove(3);
                        Label wrongPass = new Label("ERROR: Book was not added (No duplicates or books with '</' in their name)");
                        vbox.getChildren().add(wrongPass);

                    }
                }catch(NumberFormatException err){
                    if(vbox.getChildren().size() >= 4)
                        vbox.getChildren().remove(3);
                    Label wrongPass = new Label("ERROR: Book Price was not a decimal number");
                    vbox.getChildren().add(wrongPass);
                }
                updateBookList();
            }
        });
        
        
        hb.getChildren().addAll(addFirstName, addLastName, addButton);
        hb.setSpacing(3);
        
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean result = true;
                for(book b: table.getSelectionModel().getSelectedItems()){
                    result = back.removeBooks(b.getName(), b.getCost());
                    
                }
                if(result == false){
                    if(vbox.getChildren().size() >= 4)
                        vbox.getChildren().remove(3);
                    Label wrongPass = new Label("Unknown ERROR: Book could not be removed");
                    vbox.getChildren().add(wrongPass);
                }
                updateBookList();
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
    
    public void updateBookList(){
        data.clear();
        for(book b : bookManager.getInstance().getBooks()){
            data.add(b);
        }
    }
    
}
