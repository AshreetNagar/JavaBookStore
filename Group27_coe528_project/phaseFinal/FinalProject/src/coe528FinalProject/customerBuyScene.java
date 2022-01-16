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

public class customerBuyScene extends Application {
 
    //private TableView<customer> table = new TableView<customer>();
    private TableView<bookInterface> table = new TableView<bookInterface>();
    final ObservableList<bookInterface> data = FXCollections.observableArrayList();
    final HBox hb1 = new HBox();
    final HBox hb2 = new HBox();
    final Backend back = Backend.getInstance();
    Stage window;
    

 
    @Override
    public void start(Stage stage) {
        window = stage;
        Scene scene = new Scene(new Group());
        stage.setTitle("Bookstore App");
        
        updateLabel();
        updateBookList();
        
        table.setEditable(true);
        table.setItems(data);
        
        TableColumn firstNameCol = new TableColumn("Book Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
            new PropertyValueFactory<bookInterface, String>("name"));

        TableColumn dokok = new TableColumn("Book Price");
        dokok.setMinWidth(100);
        dokok.setCellValueFactory(
            new PropertyValueFactory<bookInterface, String>("cost"));
        
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditStart(
                new EventHandler<CellEditEvent<customer, String>>() {
                @Override
                public void handle(CellEditEvent<customer, String> t) {
                    for(bookInterface bean : data)
                    {
                       if(bean.getCheckbox().isSelected())
                       {
                         System.out.println(bean.getName());
                       }

                    }
                }
        });
        
        TableColumn actionCol = new TableColumn("Select"); 
        actionCol.setCellValueFactory(     
                 new PropertyValueFactory<bookInterface,String>("checkbox")
        );   
        
        table.getColumns().addAll(firstNameCol,dokok,actionCol);
 
        
        
        final Button buyButton = new Button("Buy");
        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                double tc=0;
                ArrayList<book> books = new ArrayList<book>();
                for(bookInterface book : data){
                    if(book.getCheckbox().isSelected())
                    {
                      tc += book.getCost();
                      books.add(book.b);
                    }
                }
                System.out.println("Total Cost:" + tc);
                back.buyBook(books);
                updateLabel();
                updateBookList();
                customerCostScene ccs = new customerCostScene();
                ccs.start(window);
                ccs.setMessage(tc);
            }
        });
        final Button redeemButton = new Button("Redeem and Buy");
        redeemButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                double tc=0;
                ArrayList<book> books = new ArrayList<book>();
                for(bookInterface book : data){
                    if(book.getCheckbox().isSelected())
                    {
                      tc += book.getCost();
                      books.add(book.b);
                    }
                }
                System.out.println("Total Cost:" + tc);
                int cost = back.redeemAndBuy(books);
                updateLabel();
                updateBookList();
                customerCostScene ccs = new customerCostScene();
                ccs.start(window);
                ccs.setMessage(cost);
            }
        });
        final Button logOutButton = new Button("Logout");
        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                new loginPageController().start(window);
            }
        });
        
 
        hb2.getChildren().addAll(buyButton, redeemButton,logOutButton);
        hb2.setSpacing(3);
        

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(hb1,table,hb2);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }

    public void updateLabel(){
        hb1.getChildren().clear();
        String status = back.getStatus();
        String name = back.getName();
        int points = back.getPoints();
        final Label label = new Label("Welcome "+name+". You have "+points+" points."+" Your status is "+status);
        hb1.getChildren().add(label);
    }
    
    public void updateBookList(){
        data.clear();
        for(book b : bookManager.getInstance().getBooks()){
            data.add(new bookInterface(b));
        }
    }
    
    public class bookInterface {
        private book b;
        private CheckBox checkbox;

        bookInterface(book b) {
            this.b = b;
            this.checkbox = new CheckBox();         

        }
        
        public String getName(){
            return b.getName();
        }
        
        public double getCost(){
            return b.getCost();
        }
        
         public CheckBox getCheckbox() {
            return checkbox;
        }
        public void setCheckBox(CheckBox checkbox) {
            this.checkbox = checkbox;
        }
    }

}
