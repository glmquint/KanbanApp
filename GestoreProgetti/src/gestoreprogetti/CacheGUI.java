
import java.io.*;


public class CacheGUI {
    public GestoreProgettiGUI interfaccia;
    
    public CacheGUI(GestoreProgettiGUI interfaccia){
        this.interfaccia = interfaccia;
        caricaDaCache(); 
    }
    
    public void salvaInCache(){
        try(FileOutputStream fout = new FileOutputStream("./cacheGestoreProgetti.bin");
            ObjectOutputStream oout = new ObjectOutputStream(fout);){
            oout.writeObject(interfaccia.aggiungi_titolo.getText());
            oout.writeObject(interfaccia.aggiungi_descrizione.getText());
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void caricaDaCache(){
        try(FileInputStream fin = new FileInputStream("./cacheGestoreProgetti.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);){
            interfaccia.aggiungi_titolo.setText((String)oin.readObject());
            interfaccia.aggiungi_descrizione.setText((String)oin.readObject());
            
        }   catch(FileNotFoundException fnfe){
            System.out.println("Cache non trovata"); //03
            System.out.println(fnfe.getMessage());
        }   catch (IOException | ClassNotFoundException e){
            System.out.println("Errore: impossibile prelevare da cache");
            System.out.println(e.getMessage());
        }
    }
    
}
