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
public class customer {
    private String userName;
    private int points;
    private String passwd;
    
    customer(String name, int points, String passwd){
        this.userName = name;
        this.points = points;
        this.passwd = passwd;
    }
    
    customer(String name, String passwd){
        this.userName = name;
        this.passwd = passwd;
    }
    
    customer(){
        passwd="";
    }
    
    public String getUserName(){
        return userName;
    }
    
    void setName(String name){
        this.userName = name;
    }
    
    public int getPoints(){
        return points;
    }
    
    void setPoints(int points){
        this.points = points;
    }
    
    public String getPasswd(){
        return passwd;
    }
    
    void setPasswd(String passwd){
        this.passwd = passwd;
    }
    
    
}
