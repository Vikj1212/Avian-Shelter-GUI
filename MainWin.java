import shelter.Shelter;
import shelter.Client;
import shelter.Animal;
import shelter.Gender;
import shelter.Hawkbreed;
import shelter.Hawk;
import shelter.Parrotbreed;
import shelter.Parrot;
import shelter.Owl;
import shelter.Owlbreed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JDialog;
import java.awt.FlowLayout;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class MainWin extends JFrame{
    private Shelter shelter;
    private JLabel data;
    private File filename;
    private enum DataView{ANIMALS, CLIENTS, ADOPTIONS};
    private boolean hasSaved = false;
    
    public MainWin(){
        super("Exotic Avian Shelter");
        shelter = new Shelter("Euless Exotic Avian Shelter");
        data = new JLabel(shelter.toString());
        super.setLayout(new BorderLayout());
        super.add(data, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                String buttons[] = {"Exit", "Go Back"};
                int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exiting Shelter", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[1]);
                System.out.println(opt);
                if(opt == 0){
                    System.exit(0);
                }
                System.out.println(opt);
            }
        });
        
        setSize(600,420);
        //setVisible(true);
        
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu client = new JMenu("Client");
        JMenuItem newClient = new JMenuItem("New Client");
        JMenuItem listClient = new JMenuItem("List Client");
        JMenuItem adoptAnimal = new JMenuItem("Adopt Animal");
        JMenuItem newShelter = new JMenuItem("New Shelter");
        JMenuItem openShelter = new JMenuItem("Open Shelter");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveas = new JMenuItem("Save as");
        JMenu addAnimal = new JMenu("Animal");
        JMenuItem addParrot = new JMenuItem("Add Parrot");
        JMenuItem addHawk = new JMenuItem("Add Hawk");
        JMenuItem addOwl = new JMenuItem("Add Owl");
        JMenuItem listAnimal = new JMenuItem("List Animal");
        JMenuItem listAdopted = new JMenuItem("List Adopted");
        JMenuItem quit = new JMenuItem("Quit");
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        
        JToolBar toolbar = new JToolBar("Controls");
        JButton pbutton = new JButton(new ImageIcon(new ImageIcon("shelter/IMG_4132.JPG").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT)));
        pbutton.setActionCommand("Add a Parrot");
        pbutton.setToolTipText("Add a Parrot");
        pbutton.addActionListener(event -> onAddParrot());
        JButton hbutton = new JButton(new ImageIcon(new ImageIcon("shelter/IMG_4131.PNG").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT)));
        hbutton.setActionCommand("Add a Hawk");
        hbutton.setToolTipText("Add a Hawk");
        hbutton.addActionListener(event -> onAddHawk());
        JButton oButton = new JButton(new ImageIcon(new ImageIcon("shelter/OwlLogo.JPG").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        oButton.setActionCommand("Add an Owl");
        oButton.setToolTipText("Add an Owl");
        oButton.addActionListener(event -> onAddOwl());
        JButton clientButton = new JButton(new ImageIcon(new ImageIcon("shelter/ClientLogo.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        clientButton.setActionCommand("Add a Client");
        clientButton.setToolTipText("Add a Client");
        clientButton.addActionListener(event -> onNewClientClick());
        toolbar.add(pbutton);
        toolbar.add(hbutton);
        toolbar.add(oButton);
        toolbar.add(clientButton);
        
        file.add(newShelter);
        file.add(openShelter);
        file.add((save));
        file.add(saveas);
        addAnimal.add(addParrot);
        addAnimal.add(addHawk);
        addAnimal.add(addOwl);
        addAnimal.add(listAnimal);
        addAnimal.add(listAdopted);
        file.add(addAnimal);
        file.add(quit);
        client.add(newClient);
        client.add(listClient);
        client.add(adoptAnimal);
        help.add(about);
        newShelter.addActionListener(event ->onNewShelterClick());
        openShelter.addActionListener(event -> onOpenShelterClick());
        save.addActionListener(event -> onSaveClick());
        saveas.addActionListener(event -> onSaveasProcess());
        addParrot.addActionListener(event -> onAddParrot());
        addHawk.addActionListener(event -> onAddHawk());
        addOwl.addActionListener(event -> onAddOwl());
        quit.addActionListener(event -> onQuitClick());
        about.addActionListener(event -> onAboutClick());
        newClient.addActionListener(event -> onNewClientClick());
        listAnimal.addActionListener(event -> onListAnimal());
        listClient.addActionListener(event -> onListClient());
        adoptAnimal.addActionListener(event -> onAdoptClick());
        listAdopted.addActionListener(event -> updateDisplay(DataView.ADOPTIONS));
       
        
        menubar.add(file);
        menubar.add(addAnimal);
        menubar.add(client);
        menubar.add(help);
        setJMenuBar(menubar);
        add(toolbar,BorderLayout.NORTH);
        setVisible(true);
        
    }

    
    private <T extends Animal> void newAnimal(T animal, JComboBox breeds){
        JDialog newAnimalDialog = new JDialog();
        newAnimalDialog.setSize(220, 220);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JLabel name = new JLabel("<HTML><br/>Name</HTML>");
        JTextField names = new JTextField(20);
                
                JLabel gender = new JLabel("<HTML><br/>Gender</HTML>");
                String[] gender_text = {"Male", "Female"};
                JComboBox genders = new JComboBox<String>(gender_text);
                
                JLabel breed = new JLabel("<HTML><br/>Breed</HTML>");
                
                
                JLabel age = new JLabel("<HTML><br/>Age</HTML>");
                SpinnerModel range = new SpinnerNumberModel(1,1,100,1);
                JSpinner ages = new JSpinner(range);
                
                Object[] objects = {name, names, gender, genders, breed, breeds, age, ages};
                int button = JOptionPane.showConfirmDialog(this, objects, "New Animal", JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
                if(button == JOptionPane.OK_OPTION){
                    String pbreed = String.valueOf(breeds.getSelectedItem());
                    String pgender = String.valueOf(genders.getSelectedItem());
                    int umar = (int)ages.getValue();
                    animal.create(breeds.getSelectedItem(), names.getText(), Gender.valueOf(String.valueOf(genders.getSelectedItem())), umar);
                }
                shelter.addAnimal(animal);
                updateDisplay(DataView.ANIMALS);
    }
    
    public void onAddParrot(){
        newAnimal(new Parrot(), new JComboBox(Parrotbreed.values()));
    }//End of onAddParrot() method
    public void onAddHawk(){
        newAnimal(new Hawk(), new JComboBox(Hawkbreed.values()));
    }//End of onAddHawk() method
    public void onAddOwl(){
        newAnimal(new Owl(), new JComboBox(Owlbreed.values()));
    }
    public void onNewClientClick(){
        JDialog newClient = new JDialog();
        newClient.setSize(220, 220);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JLabel name = new JLabel("<HTML><br/>Name</HTML>");
        JTextField clientName = new JTextField(25);
        JLabel number = new JLabel ("<HTML><br/>Phone #</HTML>");
        JTextField phNumber = new JTextField(15);
        
        Object[] objects = {name, clientName, number, phNumber};
        int button = JOptionPane.showConfirmDialog(this, objects, "New Client", JOptionPane.OK_CANCEL_OPTION);
        if(button == JOptionPane.OK_OPTION){
            shelter.addClient(new Client(clientName.getText(), phNumber.getText()));
        }
        updateDisplay(DataView.CLIENTS);
    }
    public void removeAdopted(JComboBox a){
        Object[] adoptedAnimals = shelter.adopted();
        if(adoptedAnimals != null){
            for(Object ani : adoptedAnimals){
                a.removeItem(ani);
            }
        }
    }
    public void onAdoptClick(){
        JDialog adopt = new JDialog();
        adopt.setSize(300, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JLabel animal = new JLabel("<HTML><br/>Animal</HTML>");
        //Object[] ani = new Object(shelter.animals.toArray());
//        String[] aniArray = new String[shelter.animals.size()];
//        for(int i = 0; i < shelter.animals.size(); i++){aniArray[i] = shelter.animals.get(i).toString();}
        JComboBox animals = new JComboBox();
        ListIterator<Animal> animalIt = shelter.animalListIterator();
        Object[] adoptedAnimal = shelter.adopted();
        while(animalIt.hasNext()){
            animals.addItem(animalIt.next());
            removeAdopted(animals);
        }
        JLabel client = new JLabel("<html><br/>Client</html>");
//        String[] clientArray = new String[shelter.clients.size()];
//        for(int i = 0; i < shelter.clients.size(); i++){clientArray[i] = shelter.clients.get(i).toString();}
        JComboBox clients = new JComboBox();
        ListIterator<Client> clientIt = shelter.clientListIterator();
        while(clientIt.hasNext()){
            clients.addItem(clientIt.next());
        }
        
        Object[] objects = {animal, animals, client, clients};
        int button = JOptionPane.showConfirmDialog(this, objects, "Adoption", JOptionPane.OK_CANCEL_OPTION);
        if(button == JOptionPane.OK_OPTION){
            shelter.adopt((Animal)animals.getSelectedItem(), (Client)clients.getSelectedItem());
        }
        updateDisplay(DataView.ADOPTIONS);
    }
    private void updateDisplay(DataView view){
    if(view == DataView.ANIMALS){data.setText("<html>" +
                  shelter.toString().replaceAll("<","&lt;").replaceAll(">", "&gt;")
                 .replaceAll("\n", "<br/>") + "</html>");}
     if(view == DataView.CLIENTS){data.setText("<html>" +
                  shelter.clientsToString().replaceAll("<","&lt;").replaceAll(">", "&gt;")
                 .replaceAll("\n", "<br/>") + "</html>");}
     if(view == DataView.ADOPTIONS){data.setText("<html>" +
                  shelter.adoptionsToString().replaceAll("<","&lt;").replaceAll(">", "&gt;")
                 .replaceAll("\n", "<br/>") + "</html>");}
    }
    public void onListAnimal(){updateDisplay(DataView.ANIMALS);}
    public void onListClient(){updateDisplay(DataView.CLIENTS);}
    public void onNewShelterClick(){
        shelter = new Shelter("Euless Exotic Avian Shelter");
        //data = new JLabel(this.toString());
        updateDisplay(DataView.ANIMALS);
    }
    public void onAboutClick(){
        JDialog about = new JDialog();
        about.setLayout(new FlowLayout());
        about.setTitle("Exotic Avian Shelter Software");
        JLabel mycredit = new JLabel("<html>" +
                                     "</br>" +
                                     "<p>Version 1.0</p>" +
                                     "<p>Copyright 2022-2023 by Vikramjeet Singh Brar</p>" +
                                     "<p>License pending...</p>" +
                                     "</html>");
        about.add(mycredit);
        JLabel piccredit = new JLabel("<html> </br> <p>Parrot Icon by zfmbek, Copyright by Adobe Stock</p>" +
                                      "<p>Hawk Icon by Flaticon, Flaticon License");
        about.add(piccredit);
        about.setSize(400,400);
        about.setVisible(true);
    }
    public void onQuitClick(){System.exit(0);}
    public void onOpenShelterClick(){
        JFileChooser fc = new JFileChooser();
        FileFilter shelterFilter = new FileNameExtensionFilter("Shelter Files", "shelter");
        fc.addChoosableFileFilter(shelterFilter);
        fc.setFileFilter(shelterFilter);
        int openVal = fc.showOpenDialog(this);
        if(openVal == JFileChooser.APPROVE_OPTION){
            filename = fc.getSelectedFile();
            try(BufferedReader br = new BufferedReader(new FileReader(filename));){
                //shelter.setFilename(filename.getAbsolutePath());
                shelter = new Shelter(br);
            }catch(Exception e){System.err.println("Failed to open from file: " + e);
                JOptionPane.showMessageDialog(this, "Failed to open file: " + e, "File Open Error!", JOptionPane.ERROR_MESSAGE);}
            updateDisplay(DataView.ANIMALS);
        }
    }
    public void onSaveClick(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(shelter.getFilename())))) {
            shelter.save(bw);
            hasSaved = true;
        }catch(Exception e){System.err.println("Failed to save to file: " + e);
            JOptionPane.showMessageDialog(this, e, "Failed to save to file!", JOptionPane.ERROR_MESSAGE);
            }
    }
    public void onSaveasProcess(){
        JFileChooser fc = new JFileChooser(filename);
        FileFilter shelterFilter = new FileNameExtensionFilter("Shelter Files", "shelter");
        fc.addChoosableFileFilter(shelterFilter);
        fc.setFileFilter(shelterFilter);
        
        int saveVal = fc.showSaveDialog(this);
        if(saveVal  == JFileChooser.APPROVE_OPTION){
            filename = fc.getSelectedFile();
            if(!filename.getAbsolutePath().endsWith(".shelter")){
                filename = new File(filename.getAbsolutePath() + ".shelter");
            }
                //BufferedWriter bw = null;
            try{
                //System.out.println("[Debug]: Filename: "+ filename.getAbsolutePath());
                shelter.setFilename(filename.getAbsolutePath());
                onSaveClick(); 
            }catch(Exception e){System.err.println("Failed to save to file: " + e);
                JOptionPane.showMessageDialog(this, "Faile to Save File: " + e, "Save Error!", JOptionPane.ERROR_MESSAGE);}         
             
        }
    }
}

 
