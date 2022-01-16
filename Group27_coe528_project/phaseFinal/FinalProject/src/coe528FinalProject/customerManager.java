package coe528FinalProject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class customerManager {
    ArrayList<customer> customers = new ArrayList<customer>();
    static customerManager instance;
    
    public static customerManager getInstance(){
        if(instance == null)
        {
            instance = new customerManager();
        }
        return instance;
    }
    
    customerManager(){
        try { 
            FileReader reader = new FileReader("customers.txt");
            char[] chars = new char[1000000];
            reader.read(chars);
            reader.close();
            
            String str = "";
            String name = "";
            int cost = 0;
            String passwd = "";
            customer readCustomer = new customer();
            for(int i=0; i<chars.length;i++){
                if(chars[i] == '<'){
                    i++;
                    if(chars[i] == '/'){
                        str = readTag(chars,i+1);
                        i = i + str.length()+1;
                        if(str.equals("customer")){
                            this.addCustomer(readCustomer.getUserName(),readCustomer.getPoints(), readCustomer.getPasswd());
                        }else if(str.equals("name")){
                            readCustomer.setName(name);
                        }else if(str.equals("points")){
                            readCustomer.setPoints(cost);
                        }else if(str.equals("password")){
                            readCustomer.setPasswd(passwd);
                        }else{
                            System.out.println("Unknown closing tag "+str);
                        }
                    }
                    else{
                        str = readTag(chars,i);
                        i = i + str.length()+1;
                        if(str.equals("customer")){
                            readCustomer = new customer();
                        }else if(str.equals("name")){
                            name = readProp(chars,i);
                        }else if(str.equals("points")){
                            cost = Integer.valueOf(readProp(chars,i));
                        }else if(str.equals("password")){
                            passwd = readProp(chars,i);
                        }else{

                        }
                    }
                }
            }
            
        } catch (FileNotFoundException e){
            try { 
                FileWriter writer = new FileWriter("customers.txt",false);
                writer.flush();
                writer.close();
            } catch (IOException e2) {             
                System.out.println("An error occurred.");
                e2.printStackTrace();         
            }
        }catch (IOException e) {             
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
    
    boolean addCustomer(String name, int points, String pass){
        
        if(points < 0){
            return false;
        }
        
        if(name.contains("</") || pass.contains("</")){
            return false;
        }
        
        for(customer b : customers)
            if(b.getUserName().equals(name)){
                return false;
            }
        customer newbook = new customer(name,points,pass);
        customers.add(newbook);
        return true;
    }
    
    boolean addCustomer(String name, String pass){

        if(name.contains("</") || pass.contains("</")){
            return false;
        }

        for(customer b : customers)
            if(b.getUserName().equals(name)){
                return false;
            }
        customer newbook = new customer(name,pass);
        customers.add(newbook);
        return true;
    }
    
    boolean removeCustomer(String name){
        for(customer b : customers)
        {
            if(0==b.getUserName().compareTo(name))
            {
                customers.remove(b);
                return true;
            }
        }
        return false;
    }
    
    ArrayList<customer> getCustomers(){
        ArrayList<customer> bok = new ArrayList<customer>();
        for(customer b : customers)
            bok.add(b);
        return bok;
    }
    
    int validateLogin(String name, String pass){
        for(customer c : customers)
        {
            if(0==c.getUserName().compareTo(name) && 0==c.getPasswd().compareTo(pass)){
                return 1;
            }
        }
        if(name.equals("admin")){
            if(pass.equals("admin")){
                return 2;
            }
        }
        return 0;
    }
    
    customer getCustomer(String name, String pass){
        for(customer c : customers)
        {
            if(0==c.getUserName().compareTo(name) && 0==c.getPasswd().compareTo(pass)){
                return c;
            }
        }
        return null;    
    }
    
    boolean modifyCustomer(String name, int points){
        for(customer c : customers)
        {
            if(0==c.getUserName().compareTo(name))
            {
                c.setPoints(points);
                return true;
            }
        }
        return false;
    }
    
    void saveToFile(){
        try { 
            FileWriter writer = new FileWriter("customers_xml.txt",false);
            String writeout = "";
            for(customer b:customers )
            {
                writeout = writeout.concat("<customer>\n\t<name>"+b.getUserName()+"</name>\n\t<points>"+b.getPoints()+"</points>\n\t<password>"+b.getPasswd()+"</password>\n</customer>\n");
            }
            writer.append(writeout);
            writer.flush();
            writer.close();
        } catch (IOException e) {             
            System.out.println("An error occurred.");
            e.printStackTrace();         
        }     
    }
    
    public static void main(String [] args){
        customerManager bokman= new customerManager();
        for(customer b:bokman.customers )
        {
            System.out.println("title: "+b.getUserName()+" cost: "+b.getPoints());
        }
        double pirce = 33.33;
        bokman.addCustomer("sup", "abcd gang shit");
        bokman.removeCustomer("sup");
        bokman.saveToFile();
    }
    
}
