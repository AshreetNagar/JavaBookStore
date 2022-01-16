package coe528FinalProject;
import java.util.ArrayList;

abstract public class BackendState {
    
    boolean addCustomer(String name, String pass){
        return false;
    }
    
    boolean removeCustomer(String name){
        return false;
    }
    
    int getPoints(){
        return -1;
    }
    
    String getName(){
        return "Invalid";
    }
    String getStatus(){
        return "Invalid";
    }
    ArrayList<customer> getCustomers(){
        return new ArrayList<customer>();
    }
    
    boolean addBooks(String name, double cost){
        return false;
    }
    
    boolean removeBooks(String name, double cost){
        return false;
    }
    
    ArrayList<book> getBooks(){
        return new ArrayList<book>();
    }
    
    int buyBook(ArrayList<book> books){
       return -1; 
    }
    
    int redeemAndBuy(ArrayList<book> books){
        return -1;
    }
    
}
