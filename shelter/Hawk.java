
package shelter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
//import java.io.IOException;

public class Hawk extends Animal{
    private Hawkbreed breed;
    
    public Hawk(Hawkbreed breed, String name, Gender gender, int age){
        super(name, gender, age);
        this.breed = breed;
    }
    public Hawk(){
        this(Hawkbreed.Northern_Goshawk_Hawk, "Unknown", Gender.Male, 1);
    }
    public Hawk(BufferedReader br){
        super(br);
        //ArrayList<String> parsedLine = new ArrayList<String>();
        try{
            breed = Hawkbreed.valueOf(br.readLine());
        }catch(Exception e){System.err.println("Failed Constructer! Unable to read file: "+ e);}
    }
    @Override
    public void save(BufferedWriter bw){
        try{
            super.save(bw);
            bw.write(breed());
            bw.newLine();
        }catch(Exception e){System.err.println("Unble to write Hawk: " +e);}
    }
    @Override
    public void create(Object breed, String name, Gender gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
        Hawkbreed breedDC = (Hawkbreed)breed;
        this.breed = breedDC;
    }
    @Override
    public String family(){
        return "Hawk";
    }
    @Override
    public String breed(){
        return breed.name();
    }
    @Override
    public String toString(){
        return "Hawk -> "+name +"(" + age + " year old " + gender + " " + breed() + ")";
    }
} 

