package shelter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Shelter{
    private String name;
    private ArrayList<Animal> animals;
    private String filename;
    private ArrayList<Client> clients;
    private HashMap<Animal, Client> adoptions;
    
    private static final String parrot = (new Parrot()).family();
    private static final String hawk = (new Hawk()).family();
    private static final String owl = (new Owl()).family();
    
    
    public Shelter(String name){
        this.name = name;
        filename = "/Users/vikram/Documents/Untitled.shelter";
        animals = new ArrayList<>();
        clients = new ArrayList<>();
        adoptions = new HashMap<>();
    }
    public Shelter(BufferedReader br) throws IOException{
        try{
            this.name = br.readLine();
            animals = new ArrayList<>();
            clients = new ArrayList<>();
            adoptions = new HashMap<>();
            int numAnimals = Integer.parseInt(br.readLine());
            while(numAnimals-- > 0){
                String fam = br.readLine();
                if(fam.equals(parrot)) animals.add(new Parrot(br));
                else if(fam.equals(hawk)) animals.add(new Hawk(br));
                else if(fam.equals(owl)) animals.add(new Owl(br));
                else throw new IOException("Inavlid Family: "+fam);
            }
            int numClients = Integer.parseInt(br.readLine());
            while(numClients-- > 0){
                clients.add(new Client(br));
            }
        }catch(Exception e){System.err.println("Failed to read shelter data from file: " + e);}
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }
    public ListIterator<Animal> animalListIterator() {return animals.listIterator();}
    public void addClient(Client client){
        clients.add(client);
    }
    public ListIterator<Client> clientListIterator() {return clients.listIterator();}
    public void setFilename(String filename){
        this.filename = filename;
    }
    public String getFilename(){return filename;}
    public void save(BufferedWriter bw) throws IOException{
        try{
            bw.write(name + '\n');
            bw.write("" + animals.size()+ '\n');
            for(Animal a : animals){
                bw.write(a.family() + '\n');
                a.save(bw);
            }
            bw.write("" + clients.size() + '\n');
            for(Client c: clients){
                c.save(bw);
            }
            bw.write("" + adoptions.size() + '\n');
            for(Animal a : adoptions.keySet()){
                bw.write(a.family() + '\n');
                a.save(bw);
                int index = clients.indexOf(adoptions.get(a));
                if(index < 0 || index > clients.size()){
                throw new ArrayIndexOutOfBoundsException("Adoptive Client Not Registered!");
                }
                bw.write(index + '\n');
            }
        }catch (Exception e){System.err.println("Failed to write shelter data to file: " + e);}
        bw.close();
    }
    public String clientsToString(){
        StringBuilder result = new StringBuilder("");
        for(Client c: clients){
            result.append(c.toString() + '\n');
        }
        return result.toString();
    }
    @Override
    public String toString(){
        String header = name + "\n";
        StringBuilder result = new StringBuilder(header);
        for(Animal x: animals){
            result.append(x + "\n");
        }
        return result.toString();   
    }
    
    public void adopt(Animal animal, Client client){
        adoptions.put(animal, client);
    }
    public Iterator<Animal> adoptionListIterator(){return adoptions.keySet().iterator();}
    public void getAdoptedClient(Animal animal){
        Iterator<Animal> adoptionsIt = adoptionListIterator();
        while(adoptionsIt != animal & adoptionsIt.hasNext()){
            adoptionsIt.next();
        }
        System.out.println("The Client is " + adoptions.get(adoptionsIt));
    }
    public String adoptionsToString(){
        StringBuilder result = new StringBuilder("");
        for(var a: adoptions.keySet()){
            result.append(a.toString() + " to " + adoptions.get(a).toString());
        }
        return result.toString();
    }
} 
