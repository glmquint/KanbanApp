
import java.io.*;
import java.util.*;

class MessaggioLog implements Serializable{
    public String nome_progetto;
    public String ip_client;
    public Date istante;
    public String azione;
    
    public MessaggioLog(String nome_progetto, String ip_client, Date istante, String azione){
        this.nome_progetto = nome_progetto;
        this.ip_client = ip_client;
        this.istante = istante;
        this.azione = azione;
    }
}
