# Gestore di progetti

<p style="text-align: center;">Guillaume Quint</p>

---

## Documento di Analisi

### Vista statica dell'interfaccia (Mockup)

![Vista Statica](VistaStatica.png)

### Vista dinamica (scenario di utilizzo)

1. l'utente inserisce o modifica il nome del progetto
2. SE l'utente preme sul tasto `Aggiungi`
   1. SE almeno uno dei due campi `Titolo` o `Descrizione` è vuoto
      1. non succede niente
   2. ALTRIMENTI
      1. SE è stato raggiunto il numero massimo di compiti per la tabella `Da fare`
         1. non succede niente
      2. ALTRIMENTI
         1. viene aggiunto un nuovo compito nella tabella `Da fare`
3. l'utente può modificare il titolo o la descrizione di un compito in una della tre tabelle premendoci sopra e digitando il nuovo testo
4. l'utente può selezionare un compito premendolo
   1. SE l'utente preme sul tasto `Elimina`
      1. il compito selezionato viene eliminato dalla tabella in cui si trova
   2. SE l'utente preme su uno dei tasti `>` o `<`
      1. SE è stato raggiunto il numero massimo di compiti per la tabella di destinazione
         1. non succede niente
      2. ALTRIMENTI
         1. il compito selezionato viene spostato nella tabella di destinazione adiacente come indicato dalla freccia
         2. viene aggiornato il grafico a torta 

### File di configurazione in XML

All'avvio il Sistema legge dal file di configurazione in XML i seguenti dati:

- indirizzo IP del client, indirizzo IP e porta del server di log
- username e password del DBMS
- IP e porta del DBMS
- nome dei tre stati disponibili (di default `Da fare`, `In esecuzione`, `Eseguiti`)
- se mostrare la legenda nel grafico a torta
- colore di sfondo
- massimo numero di compiti inseribili per ogni stato

### Cache locale degli input

Alla chiusura il Sistema salva in un file binario locale i campi `Titolo` e `Descrizione`, se questi non sono vuoti

All'avvio il Sistema carica da file binario, se sono presenti, il `Titolo` e la `Descrizione` negli appositi campi

### Archivio

Il Sistema archivia i seguenti dati:

- il nome del progetto
- tutti i compiti che appartengono al progetto. Di ciascuno viene salvato:
  - il titolo
  - la descrizione
  - lo stato a cui appartiene (se `Da fare`, `In esecuzione` o `Eseguito`)

### File di Log remoto in XML

Il sistema invia una riga di log ad ogni evento di seguito:

- avvio dell'applicazione ("AVVIO")
- pressione dei pulsanti `Aggiungi`, `Elimina`, `>`, `<`
- modifica del nome del Progetto ("CAMBIO NOME")
- termine dell'applicazione ("TERMINE")

La riga di log contiene: nome del progetto, indirizzo IP del client, data-ora corrente, etichetta dell'evento

