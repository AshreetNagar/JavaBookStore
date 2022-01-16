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
public class CustomerBackend extends BackendState {

    customer currentCustomer;
    
    CustomerBackend(customer c){
        currentCustomer = c;
    }
    
    @Override
    ArrayList<book> getBooks(){
        return bookManager.getInstance().getBooks();
    }
    
    @Override
    String getStatus(){
        int points = currentCustomer.getPoints();
        String status;
        if(points>= 1000){
            status = "Gold.";
        }
        else if(points <1000){
            status = "Silver.";
        }
        else{
            status = "Invalid";
        }
        return status;
    }
    
    @Override
    int getPoints(){
        return currentCustomer.getPoints();
    }
    @Override
    String getName(){
        return currentCustomer.getUserName();
    }
    @Override
    int buyBook(ArrayList<book> books){
        int points = 0;
        int cost = 0;
        for(book b : books){
            cost += b.getCost();
            points += b.getCost() / 10;
            bookManager.getInstance().removeBook(b.getName(), b.getCost());
        }
        
        int newPoints = currentCustomer.getPoints() + points;
        currentCustomer.setPoints(newPoints);
        return cost;
    }
    @Override
    int redeemAndBuy(ArrayList<book> books){
        int points = 0;
        int cost = 0;
        for(book b : books){
            cost += b.getCost();
            points -= b.getCost() * 100;
            bookManager.getInstance().removeBook(b.getName(), b.getCost());
        }
        int newPoints = currentCustomer.getPoints() + points;
        
        if(newPoints >= 0){
            cost = 0;
            currentCustomer.setPoints(newPoints);     
        }
        else if(newPoints<0){
            cost = cost - currentCustomer.getPoints()/100;
            currentCustomer.setPoints(currentCustomer.getPoints()%100);
        }
        
        return cost;
    }
    
}
