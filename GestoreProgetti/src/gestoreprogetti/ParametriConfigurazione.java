
import java.io.*;

public class ParametriConfigurazione implements Serializable{
    
    public OperatoreXML operatore_xml = new OperatoreXML();
    
    public int limite_prima_tabella;
    public int limite_seconda_tabella;
    public int limite_terza_tabella;
    public String nome_prima_tabella;
    public String nome_seconda_tabella;
    public String nome_terza_tabella;
    public String colore_background;
    public int id_progetto;
    public Boolean mostraLegendaGrafico;
    public String ip_dbms;
    public int porta_dbms;
    public String username_dbms;
    public String password_dbms;
    public String ip_client;
    public String ip_log_server;
    public int porta_log_server;
    
    public ParametriConfigurazione(){
        operatore_xml.valida();
        ParametriConfigurazione tmp = operatore_xml.estrai(); /*(1)*/
        
        this.limite_prima_tabella = tmp.limite_prima_tabella;
        this.limite_seconda_tabella = tmp.limite_seconda_tabella;
        this.limite_terza_tabella = tmp.limite_terza_tabella;
        this.nome_prima_tabella = tmp.nome_prima_tabella;
        this.nome_seconda_tabella = tmp.nome_seconda_tabella;
        this.nome_terza_tabella = tmp.nome_terza_tabella;
        this.colore_background = tmp.colore_background;
        this.id_progetto = tmp.id_progetto;
        this.mostraLegendaGrafico = tmp.mostraLegendaGrafico;
        this.ip_dbms = tmp.ip_dbms;
        this.porta_dbms = tmp.porta_dbms;
        this.username_dbms = tmp.username_dbms;
        this.password_dbms = tmp.password_dbms;
        this.ip_client = tmp.ip_client;
        this.ip_log_server = tmp.ip_log_server;
        this.porta_log_server = tmp.porta_log_server;
    }
    
    
}

/*
Commenti:
    (1): l'estrai della classe operatore_xml ritornerà un oggetto di tipo 
        ParametriConfigurazione direttamente dal file xml tarmite casting
        esplicito: così è possibile accederci ai campi e usarli per impostare
        i valori di quest'oggetto usato dalla classe GestoreProgettiGUI
*/