
import java.io.*;
import java.net.*;
import java.util.*;


public class Logger {
    public GestoreProgettiGUI interfaccia;
    public GestoreXML gxml = new GestoreXML();
    private MessaggioLog messaggioDaInviare;
    
    public Logger(GestoreProgettiGUI interfaccia){
        this.interfaccia = interfaccia;
    }
    
    public void invia(String azione){
        try( Socket s = new Socket(interfaccia.parametri_configurazione.IPServer, interfaccia.parametri_configurazione.portaServer);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            ){
            messaggioDaInviare = new MessaggioLog(interfaccia.nome_progetto.getText(), interfaccia.parametri_configurazione.IPClient, new Date(), azione); //02
            
            dout.writeUTF(gxml.formattaMessaggio(messaggioDaInviare)); //04
            } catch (IOException ex){
                ex.printStackTrace();
            }
    }
}
