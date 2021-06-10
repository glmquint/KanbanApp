
import java.io.*;
import java.util.*;

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
