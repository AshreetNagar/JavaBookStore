package coe528FinalProject;

import java.util.ArrayList;

public class Backend {
    BackendState currentUser;
    static Backend instance;
    
    public static Backend getInstance(){
        if(instance == null)
        {
            instance = new Backend();
        }
        return instance;
    }
    
    
    Backend(){
        currentUser = new UnauthorizedBackend();
    }
    
    int login(String name, String pass){
        int validity = customerManager.getInstance().validateLogin(name, pass);
        switch(validity){
            case 0:
                currentUser = new UnauthorizedBackend();
                break;
            case 1:
                customer c = customerManager.getInstance().getCustomer(name, pass);
                currentUser = new CustomerBackend(c);
                break;
            case 2:
                currentUser = new AdminBackend();
                break;
            default:
                currentUser = new UnauthorizedBackend();
                validity = 0;
                break;
        }

        return validity;
    }
    
    void logout(){
        currentUser = new UnauthorizedBackend();
    }
    
    boolean addCustomer(String name, String pass){
        return currentUser.addCustomer(name,pass);
    }
   
    boolean removeCustomer(String name){
        return currentUser.removeCustomer(name);
    }
    String getName(){
        return currentUser.getName();
    }
    int getPoints(){
        return currentUser.getPoints();
    }
    
    String getStatus(){
        return currentUser.getStatus();
    }
    
    ArrayList<customer> getCustomers(){
        return currentUser.getCustomers();
    }
    
    boolean addBooks(String name, double cost){
        return currentUser.addBooks(name, cost);
    }
    
    boolean removeBooks(String name, double cost){
        return currentUser.removeBooks(name, cost);
    }
    
    ArrayList<book> getBooks(){
        return currentUser.getBooks();
    }
    
    int buyBook(ArrayList<book> books){
        return currentUser.buyBook(books);
    }
    
    int redeemAndBuy(ArrayList<book> books){
        return currentUser.redeemAndBuy(books);
    }
    
    void saveAndExit(){
        bookManager.getInstance().saveToFile();
        customerManager.getInstance().saveToFile();
    }
    
    public static void main (String [] args){
        
    }
    
}
