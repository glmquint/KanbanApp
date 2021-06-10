
import java.io.*;
import java.util.*;

/*(1)*/
class MessaggioLog implements Serializable{ 
    public String nomeProgetto;
    public String IPClient;
    public Date istante;
    public String azione;
    
    public MessaggioLog(String nomeProgetto, String IPClient, Date istante, String azione){
        this.nomeProgetto = nomeProgetto;
        this.IPClient = IPClient;
        this.istante = istante;
        this.azione = azione;
    }
}

/*Commenti:
    (1): definisce le caratteristiche che deve avere un messaggio di log per 
        essere inviato al server di log. Queste comprendono l'etichetta 
        distintiva dell'evento che deve essere registrato, l'istante temporale 
        di registrazione, l'ip del clien e il nome del progetto su cui si sta 
        lavorando. La classe, ovviamente serializzabile, viene trasmessa 
        tramite socket in formato xml dalla classe Logger
*/
