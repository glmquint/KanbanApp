
import java.io.*;

public class ParametriConfigurazione implements Serializable{
    
    public GestoreXML gxml = new GestoreXML();
    
    public int maxRigheTab1;
    public int maxRigheTab2;
    public int maxRigheTab3;
    public String nomeStato1;
    public String nomeStato2;
    public String nomeStato3;
    public String coloreBackground;
    public int ProjectID;
    public Boolean mostraLegendaGrafico;
    public String IPDBMS;
    public int portaDBMS;
    public String usernameDBMS;
    public String passwordDBMS;
    public String IPClient;
    public String IPServer;
    public int portaServer;
    
    public ParametriConfigurazione(){
        gxml.valida();
        ParametriConfigurazione tmp = gxml.estrai(); //02
        
        this.maxRigheTab1 = tmp.maxRigheTab1;
        this.maxRigheTab2 = tmp.maxRigheTab2;
        this.maxRigheTab3 = tmp.maxRigheTab3;
        this.nomeStato1 = tmp.nomeStato1;
        this.nomeStato2 = tmp.nomeStato2;
        this.nomeStato3 = tmp.nomeStato3;
        this.coloreBackground = tmp.coloreBackground;
        this.ProjectID = tmp.ProjectID;
        this.mostraLegendaGrafico = tmp.mostraLegendaGrafico;
        this.IPDBMS = tmp.IPDBMS;
        this.portaDBMS = tmp.portaDBMS;
        this.usernameDBMS = tmp.usernameDBMS;
        this.passwordDBMS = tmp.passwordDBMS;
        this.IPClient = tmp.IPClient;
        this.IPServer = tmp.IPServer;
        this.portaServer = tmp.portaServer;
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