/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528FinalProject;

/**
 *
 * @author tourist
 */
public class book {
    private String name;
    private double cost;
    
    book(String name, double cost){
        this.name = name;
        this.cost = cost;
    }
    book(){
    }
    public String getName(){
        return name;
    }
    
    void setName(String name){
        this.name = name;
    }
    
    public double getCost(){
        return cost;
    }
    
    void setCost(double cost){
        this.cost = cost;
    }
    
}
