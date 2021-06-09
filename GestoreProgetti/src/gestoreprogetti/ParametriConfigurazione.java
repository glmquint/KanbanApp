
import java.io.*;

public class ParametriConfigurazione implements Serializable{
    
    public GestoreXML gestore_xml = new GestoreXML();
    
    public int limite_prima_tabella;
    public int limite_seconda_tabella;
    public int limite_terza_tabella;
    public String nome_prima_tabella;
    public String nome_seconda_tabella;
    public String nome_terza_tabella;
    public String colore_background;
    public int id_progetto;
    public Boolean mostra_legenda_grafico;
    public String ip_dbms;
    public int porta_dbms;
    public String username_dbms;
    public String password_dbms;
    public String ip_client;
    public String ip_log_server;
    public int porta_log_server;
    
    public ParametriConfigurazione(){
        gestore_xml.valida();
        ParametriConfigurazione tmp = gestore_xml.estrai(); //02
        if (tmp == null)
            System.out.println("========================================================");
        
        this.limite_prima_tabella = tmp.limite_prima_tabella;
        this.limite_seconda_tabella = tmp.limite_seconda_tabella;
        this.limite_terza_tabella = tmp.limite_terza_tabella;
        this.nome_prima_tabella = tmp.nome_prima_tabella;
        this.nome_seconda_tabella = tmp.nome_seconda_tabella;
        this.nome_terza_tabella = tmp.nome_terza_tabella;
        this.colore_background = tmp.colore_background;
        this.id_progetto = tmp.id_progetto;
        this.mostra_legenda_grafico = tmp.mostra_legenda_grafico;
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
Note:
(01) I file skillshare_this.xml e skillshare_this.xsd si trovano nella
     directory radice
(02) Si estrae il contenuto dal file di thisurazione
(03) Una volta ottenuto il valore di ciascun parametro thisurabile, si
     assegna ciascun valore al campo corrispondente nell'istanza della classe
     ParametriConfigurazione che sarà utilizzata dalle altre classi dell'app
*/