package shelter;

import java.io.BufferedWriter;
import java.io.BufferedReader;

public class Parrot extends Animal{
    private Parrotbreed breed;
    
    public Parrot(Parrotbreed breed, String name, Gender gender, int age){
        super(name, gender, age);
        this.breed = breed;
    }
    public Parrot(){
        this(Parrotbreed.Macaw, "Unknown", Gender.Female, 1);
    }
    public Parrot(BufferedReader br){
        super(br);
        try{
            breed = Parrotbreed.valueOf(br.readLine());
          
        }catch(Exception e){System.err.println("Failed Constructer! Unable to read file: "+ e);}
    }
    @Override
    public void save(BufferedWriter bw){
        try{
            super.save(bw);
            bw.write(breed()+'\n');
            //bw.newLine();
        }catch(Exception e){System.err.println("Unable to write Parrot to file: " +e);}
    }
    @Override
    public void create(Object breed, String name, Gender gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
        Parrotbreed breedDC = (Parrotbreed)breed;
        this.breed = breedDC;
    }
    @Override
    public String family(){
        return "Parrot";
    }
    @Override
    public String breed(){
        return breed.name();
    }
    @Override
    public String toString(){
        return "Parrot -> "+name +"(" + age + " year old " + gender + " " + breed() + ")";
    }
} 
