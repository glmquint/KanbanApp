
import java.io.*;
import java.net.*;
import java.util.*;


public class Logger {
    public GestoreProgettiGUI interfaccia;
    public OperatoreXML operatore_xml = new OperatoreXML();
    private MessaggioLog messaggioDaInviare;
    
    public Logger(GestoreProgettiGUI interfaccia){
        this.interfaccia = interfaccia;
    }
    
    public void invia(String azione){
        try( Socket s = new Socket(interfaccia.parametri_configurazione.ip_log_server, interfaccia.parametri_configurazione.porta_log_server);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            ){
            messaggioDaInviare = new MessaggioLog(interfaccia.nome_progetto.getText(), interfaccia.parametri_configurazione.ip_client, new Date(), azione); //02
            
            dout.writeUTF(operatore_xml.formattaMessaggio(messaggioDaInviare)); //04
            } catch (IOException ex){
                ex.printStackTrace();
            }
    }
}
