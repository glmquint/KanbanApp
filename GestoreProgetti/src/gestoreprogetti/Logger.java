
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
    
    public void invia(String azione){/*(1)*/
        try( Socket s = new Socket(interfaccia.parametri_configurazione.ip_log_server, interfaccia.parametri_configurazione.porta_log_server);
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            ){
            messaggioDaInviare = new MessaggioLog(interfaccia.nome_progetto.getText(), interfaccia.parametri_configurazione.ip_client, new Date(), azione); 
            
            dout.writeUTF(operatore_xml.formattaMessaggio(messaggioDaInviare)); 
            } catch (IOException ex){
                ex.printStackTrace();
            }
    }
}

/*Commenti:
    (1): la classe Logger si occupa soltanto dell'invio dell'evento al server di
        log tramite socket e in formato xml. Il messaggio di log viene 
        costruito a partire da un oggeatto MessaggioLog che avrà come etichetta
        quella passata alla funzione e come altri parametri quelli ricavati
        dall'interfaccia e dal file di configurazione
*/
