import java.sql.*;
import java.util.*;

public class PonteApplicazioneDB {
    public GestoreProgettiGUI interfaccia;
    
    public PonteApplicazioneDB(GestoreProgettiGUI interfaccia){
        this.interfaccia = interfaccia;
    }
    
    public List<Compito> QueryCompito(int stato){
        String statement = "SELECT titolo, descrizione FROM compito WHERE progetto_id = " + interfaccia.parametri_configurazione.id_progetto + " AND stato = '" + stato + "';";
        System.out.println("è stata chiamata la perform Query con statement: " + statement);
        List<Compito> listaCompiti = new ArrayList<>();        
        String connessione = "jdbc:mysql://" + interfaccia.parametri_configurazione.ip_dbms + ":" + interfaccia.parametri_configurazione.porta_dbms + "/gestoreprogettidb";
        try
        (Connection co = DriverManager.getConnection(connessione, interfaccia.parametri_configurazione.username_dbms, interfaccia.parametri_configurazione.password_dbms);
         Statement st = co.createStatement();
        )
        {
            ResultSet rs = st.executeQuery(statement); //01
            while(rs.next()){
                //02
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                listaCompiti.add(new Compito(titolo, descrizione)); //03    
                System.out.println("titolo: " + titolo);
                System.out.println("descrizione: " + descrizione);
            }
        } catch (SQLException e){ System.err.println("something went wrong: " + e.getMessage());}
        
        return listaCompiti; //04
    }
    
    public String QueryProgetto(){
        String statement = "SELECT nome FROM progetto WHERE id = " + interfaccia.parametri_configurazione.id_progetto;
        System.out.println("è stata chiamata la perform Query con statement: " + statement);
        String nome_progetto = "";
        String connessione = "jdbc:mysql://" + interfaccia.parametri_configurazione.ip_dbms + ":" + interfaccia.parametri_configurazione.porta_dbms + "/gestoreprogettidb";
        try
        (Connection co = DriverManager.getConnection(connessione, interfaccia.parametri_configurazione.username_dbms, interfaccia.parametri_configurazione.password_dbms);
         Statement st = co.createStatement();
        )
        {
            ResultSet rs = st.executeQuery(statement); //01
            while(rs.next()){
                //02
                nome_progetto = rs.getString("nome");
            }
        } catch (SQLException e){ System.err.println("something went wrong: " + e.getMessage());}
        
        return nome_progetto; //04
    }
    
    public Boolean eseguiAggiornamento(String statement){
        Boolean operazioneEseguita = false;
        String connessione = "jdbc:mysql://" + interfaccia.parametri_configurazione.ip_dbms + ":" + interfaccia.parametri_configurazione.porta_dbms + "/gestoreprogettidb";
        try
        (Connection co = DriverManager.getConnection(connessione, interfaccia.parametri_configurazione.username_dbms, interfaccia.parametri_configurazione.password_dbms);
         Statement st = co.createStatement();
        )
        {
            operazioneEseguita = st.executeUpdate(statement) != 0; //05
        } catch (SQLException e){ System.err.println(e.getMessage());}
        
        return operazioneEseguita;
    }
    
    public Boolean aggiornaCompiti(){
        Boolean retval = true;
        this.eseguiAggiornamento("DELETE FROM compito WHERE progetto_id = " + interfaccia.parametri_configurazione.id_progetto);
        for (int i = 0; i < interfaccia.compiti_da_fare.size(); i++) {
            retval &= eseguiAggiornamento("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + interfaccia.compiti_da_fare.get(i).getTitolo() + "\", \"" + interfaccia.compiti_da_fare.get(i).getDescrizione() + "\", 1, " + interfaccia.parametri_configurazione.id_progetto + ")");
        }
        for (int i = 0; i < interfaccia.compiti_in_esecuzione.size(); i++) {
            retval &= eseguiAggiornamento("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + interfaccia.compiti_in_esecuzione.get(i).getTitolo() + "\", \"" + interfaccia.compiti_in_esecuzione.get(i).getDescrizione() + "\", 2, " + interfaccia.parametri_configurazione.id_progetto + ")");
        }
        for (int i = 0; i < interfaccia.compiti_completati.size(); i++) {
            retval &= eseguiAggiornamento("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + interfaccia.compiti_completati.get(i).getTitolo() + "\", \"" + interfaccia.compiti_completati.get(i).getDescrizione() + "\", 3, " + interfaccia.parametri_configurazione.id_progetto + ")");
        }
        return retval;
    }
    
    public Boolean aggiornaProgetto(){
        return eseguiAggiornamento("UPDATE progetto SET nome = \"" + interfaccia.nome_progetto.getText() + "\" WHERE id = " + interfaccia.parametri_configurazione.id_progetto);
    }
}

/*
Note:
(01) Il metodo executeQuery prende come parametro la stringa inviata da
     EseguiRichiestaUtente che corrisponde allo statement mySQL da eseguire.
(02) Il result set che ritorna dal metodo executeQuery è processato per
     ottenere una lista di corsi
(03) Il costruttore di Corso prende in ingresso come parametro Posti una
     stringa formattata come postiDisponibili/postiTotali
(04) Si ritorna ad EseguiRichiestaUtente la lista dei corsi ottenuti come
     result set dall'esecuzione della query
(05) Come prima, anche il metodo executeStatement prende in ingresso la stringa
     proveniente da EseguiRichiestaUtente che contiene il codice mySQL. Il
     valore di ritorno è confrontato con zero (è il numero di righe
     "influenzate" dall'update, in ogni caso maggiore di 1 nel caso tutto sia
     andato a buon fine) e, se zero, viene ritornato il valore false.
*/
