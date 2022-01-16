/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528FinalProject;

import java.util.ArrayList;

/**
 *
 * @author tourist
 */
public class AdminBackend extends BackendState{
    AdminBackend(){
        super();
    }
    @Override
    String getName(){
        return "Owner";
    }
    @Override
    boolean addCustomer(String name,String pass){
        return customerManager.getInstance().addCustomer(name,pass);
    }    
    
    @Override
    boolean removeCustomer(String name){
        return customerManager.getInstance().removeCustomer(name);
    }
    
    @Override
    ArrayList<customer> getCustomers(){
        return customerManager.getInstance().getCustomers();
    }
    
    @Override
    boolean addBooks(String name,double cost){
        return bookManager.getInstance().addBook(name,cost);
    }    
    
    @Override
    boolean removeBooks(String name,double cost){
        return bookManager.getInstance().removeBook(name,cost);
    }
    
    @Override
    ArrayList<book> getBooks(){
        return bookManager.getInstance().getBooks();
    }
    
}
