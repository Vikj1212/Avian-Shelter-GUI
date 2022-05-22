package shelter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Animal{
    protected String name;
    protected Gender gender;
    protected int age;
    
    public Animal(String name, Gender gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    public Animal(BufferedReader br){
        String Line;
        //ArrayList<String> parsedLine = new ArrayList<String>();
        try{
            name = br.readLine();
            gender = Gender.valueOf(br.readLine());
            age = Integer.parseInt(br.readLine());
        }catch(Exception e){System.err.println("Failed Constructer! Unable to read file: "+ e);}
    }
    public void save(BufferedWriter bw){
        try{
            bw.write(name + '\n');
            bw.write(gender.name() + '\n');
            bw.write("" + age + '\n');
        }catch(Exception e){System.err.println("Unable to save Animal data: "+e);}
    }
    public abstract void create(Object breed, String name, Gender gender, int age);
    public abstract String family();
    public abstract String breed();
    public abstract String toString();

} 
