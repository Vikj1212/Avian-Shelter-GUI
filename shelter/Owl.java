package shelter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
//import java.io.IOException;

public class Owl extends Animal{
    private Owlbreed breed;
    
    public Owl(Owlbreed breed, String name, Gender gender, int age){
        super(name, gender, age);
        this.breed = breed;
    }
    public Owl(){
        this(Owlbreed.Snowy_Owl, "Unknown", Gender.Male, 1);
    }
    public Owl(BufferedReader br){
        super(br);
        //ArrayList<String> parsedLine = new ArrayList<String>();
        try{
            breed = Owlbreed.valueOf(br.readLine());
        }catch(Exception e){System.err.println("Failed Constructer! Unable to read file: "+ e);}
    }
    @Override
    public void save(BufferedWriter bw){
        try{
            super.save(bw);
            bw.write(breed());
            bw.newLine();
        }catch(Exception e){System.err.println("Unble to write Owl: " +e);}
    }
    @Override
    public void create(Object breed, String name, Gender gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
        Owlbreed breedDC = (Owlbreed)breed;
        this.breed = breedDC;
    }
    @Override
    public String family(){
        return "Owl";
    }
    @Override
    public String breed(){
        return breed.name();
    }
    @Override
    public String toString(){
        return "Owl -> "+name +"(" + age + " year old " + gender + " " + breed() + ")";
    }
} 

