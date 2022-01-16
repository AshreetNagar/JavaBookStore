/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528FinalProject;
import java.util.ArrayList;
import java.io.*;
import java.math.BigDecimal;
import java.nio.CharBuffer;
/**
 *
 * @author tourist
 */


public class bookManager {
    ArrayList<book> books = new ArrayList<book>();
    static bookManager instance;
    
    public static bookManager getInstance(){
        if(instance == null)
        {
            instance = new bookManager();
        }
        return instance;
    }
    
    
    bookManager(){
       try { 
            FileReader reader = new FileReader("books.txt");
            char[] chars = new char[1000000];
            reader.read(chars);
            reader.close();
            
            String str = "";
            String name = "";
            double cost = 0;
            book inpBook = new book();
            for(int i=0; i<chars.length;i++){
                if(chars[i] == '<'){
                    i++;
                    if(chars[i] == '/'){
                        str = readTag(chars,i+1);
                        i = i + str.length()+1;
                        if(str.equals("book")){
                            this.addBook(inpBook.getName(),inpBook.getCost());
                        }else if(str.equals("name")){
                            inpBook.setName(name);
                        }else if(str.equals("price")){
                            inpBook.setCost(cost);
                        }else{
                            System.out.println("Unknown closing tag");
                        }
                    }
                    else{
                        str = readTag(chars,i);
                        i = i + str.length()+1;
                        if(str.equals("book")){
                            inpBook = new book();
                        }else if(str.equals("name")){
                            name = readProp(chars,i);
                        }else if(str.equals("price")){
                            cost = Double.valueOf(readProp(chars,i));
                        }else{

                        }
                    }
                }
            }
            
        } catch (IOException e) {             
            System.out.println("An error occurred.");
            e.printStackTrace();         
        }    
    }
    
    private String readTag(char[] chars, int i ){
        Boolean done = false;
        String str = "";
        while(done == false && i< chars.length){
            if(chars[i] == '>'){
                done = true;
                i++;
                break;
            }
            str = str.concat(Character.toString(chars[i]));
            i++;
            if(i>=chars.length){
                System.out.println("Tag does not close");
                break;
            }
        }
        return str;
    }
    
    private String readProp(char[] chars, int i ){
        Boolean done = false;
        String str = "";
        while(done == false && i< chars.length){
            if(chars[i] == '<'){
                if(chars[i+1] == '/'){
                    done = true;
                    i++;
                    break;
                }
            }
            str = str.concat(Character.toString(chars[i]));
            i++;
            if(i>=chars.length){
                System.out.println("Tag does not close");
                break;
            }
        }
        return str;
    }
    

    boolean addBook(String name, double cost){
        
        if(name.contains("</")){
            return false;
        }
        if(cost<0){
            return false;
        }
        if(BigDecimal.valueOf(cost).scale() > 2){
            return false;
        }
        for(book b : books)
            if(b.getName().equals(name)){
                return false;
            }
        book newbook = new book(name,cost);
        return books.add(newbook);
    }
    
    boolean removeBook(String name, double cost){
        for(book b : books)
        {
            if(0==b.getName().compareTo(name))
            {
                if(b.getCost() == cost){
                    return books.remove(b);
                }
            }
        }
        return false;
    }
    
    ArrayList<book> getBooks(){
        ArrayList<book> bok = new ArrayList<book>();
        for(book b : books)
            bok.add(b);
        return bok;
    }
    
    void saveToFile(){
        try { 
            FileWriter writer = new FileWriter("books_xml.txt",false);
            String writeout = "";
            for(book b:books )
            {
                writeout = writeout.concat("<book>\n\t<name>"+b.getName()+"</name>\n\t<price>"+b.getCost()+"</price>\n</book>\n");
            }
            writer.append(writeout);
            writer.flush();
            writer.close();
            // Write the code here         
        } catch (IOException e) {             
            System.out.println("An error occurred.");
            e.printStackTrace();         
        }     
    }
    
    public static void main(String [] args){
        bookManager bokman= new bookManager();
        for(book b:bokman.books )
        {
            System.out.println("title: "+b.getName()+" cost: "+b.getCost());
        }
        double pirce = 33.33;
        //bokman.addBook("sup", pirce);
        bokman.removeBook("sup",pirce);
        bokman.saveToFile();
    }
}
