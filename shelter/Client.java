package shelter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;

public class Client{
    private String name;
    private String phone;
    private String street;
    private String city;
    private String state;
    
    public Client(String name, String phone, String street, String city, String state){
        try{
            this.name = name;
            this.phone = phone;
            if(this.phone.length()<10){
                throw new Exception("Phone # is invalid(must be 10 digits)!");
            }
            this.street = street;
            this.city = city;
            this.state = state;
        }catch(Exception e){System.out.println(e);}
    }
    public Client(BufferedReader br){
        try{    
            this.name = br.readLine();
            this.phone = br.readLine();
            if(this.phone.length()<10){
                throw new Exception("Invalid Phone # for "+ name + " from file!");
            }
            this.street = br.readLine();
            this.city = br.readLine();
            this.state = br.readLine();
        }catch(Exception e){System.out.println(e);}
    }
    public void save(BufferedWriter bw){
        try{
            bw.write(name + '\n');
            bw.write("" + phone + '\n');
            bw.write(street + '\n');
            bw.write(city +'\n');
            bw.write(state + '\n');
        }catch(Exception e){System.out.println("Could not save client data!");}
    }
    public String toString(){
        StringBuilder cInfo = new StringBuilder(name +"(" + phone + ") " + city + ", " + state + '\n');
        return cInfo.toString(); 
    }
} 
