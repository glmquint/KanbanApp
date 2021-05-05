# Gestore di progetti

<p style="text-align: center;">Guillaume Quint</p>

Documento di analisi per il progetto di Programmazione Avanzata 20/21 ad Ing. Informatica UNIPI

---

### Vista statica dell'interfaccia (Mockup)

![Vista Statica](VistaStatica.png)

### Vista dinamica (scenario di utilizzo)

1. l'utente inserisce o modifica il nome del progetto
2. l'utente inserisce un nuovo compito
   1. inserisce il titolo del compito
   2. inserisce la descrizione
   3. inserisce almeno un sotto-compito
   4. SE preme `Cancella` 
      1. vengono svuotati i campi
   5. SE preme `Aggiorna`
      1. non succede niente (in quanto il nuovo compito non è ancora presente nel progetto)
   6. SE preme `Aggiungi`
      1. viene aggiunto il nuovo compito nella sezione `Da fare`
3. l'utente preme su un compito esistente (lo seleziona)
   1. i campi `titolo`, `descrizione`, `sotto-compiti` vengono riempiti con i valori del compito selezionato
   2. appare un `cestino` a fianco del compito
   3. SE l'utente preme il `cestino` a fianco del compito
      1. il compito viene eliminato
   4. l'utente può modificare i campi `titolo`, `descrizione`, `nuovo-sotto-compito`
   5. SE l'utente preme il `segno di spunta` a fianco di un sotto-compito
      1. viene aggiornata la barra di progresso del compito
   6. SE l'utente preme il `cestino` a fianco di un sotto-compito
      1. SE è l'unico sotto-compito rimasto
         1. non succede nulla (è necessario almeno un sotto-compito)
      2. ALTRIMENTI
         1. il sotto-compito viene eliminato
   7. SE l'utente preme il `+` a fianco di `nuovo sotto-compito`
      1. SE il campo `nuovo sotto-compito` è vuoto
         1. non succede nulla
      2. ALTRIMENTI
         1. viene aggiunto un nuovo sotto compito non completato alla lista dei sotto-compiti
   8. SE l'utente preme `Cancella`
      1. vengono svuotati i campi
   9. SE l'utente preme `Aggiorna`
      1. vengono aggiornati i valori del compito
   10. SE l'utente preme `Aggiungi`
      1. viene a aggiunto un nuovo compito nella sezione `Da fare`
4. PER OGNI compito in `Da fare`
   1. SE almeno un sotto-compito è stato eseguito
      1. il compito viene spostato nella sezione `In esecuzione`
      2. viene aggiornata la barra di progresso del compito
5. PER OGNI compito in `Eseguiti`
   1. SE almeno un sotto-compito **non** è stato eseguito
      1. il compito viene spostato nella sezione `In esecuzione`
      2. viene aggiornata la barra di progresso del compito 
6. PER OGNI compito in `In esecuzione`
   1. SE tutti i sotto-compiti sono stati eseguiti
      1. il compito viene spostato nella sezione `Eseguiti`
   2. SE tutti i sotto-compiti **non** sono stati eseguiti
      1. il compito viene spostato nella sezione `Da fare`

### File di configurazione in XML

All'avvio il Sistema legge dal file di configurazione il nome del file binario nel quale cercare il progetto da caricare

### Cache locale degli input

Alla chiusura il Sistema, se è presente almeno un un compito, salva sul file binario tutte le informazioni relative al progetto, ossia il nome del progetto, tutte le informazioni relative ai compiti che lo compongono e lo stato di questi (se `Da fare`, `In esecuzioni` o `Eseguiti`)

All'avvio il Sistema carica da file binario tutte le informazioni relative al progetto che deve essere caricato.

#### Archivio

Il Sistema archivia i seguenti dati:

- il nome del progetto
- tutti i compiti che appartengono al progetto. Di ciascuno viene salvato:
  - il titolo
  - la descrizione
  - la lista dei sotto-compiti e per ciascuno
    - se è stato completato

### File di Log remoto in XML

Il sistema invia una riga di log ad ogni evento di seguito:

- avvio dell'applicazione ("AVVIO")
- pressione dei pulsanti `Aggiorna`, `Aggiungi`, `+`, `cestino`, `segno di spunta`
- modifica del nome del Progetto ("CAMBIO NOME")
- termine dell'applicazione ("TERMINE")

La riga di log contiene: nome del progetto, indirizzo IP del client, data-ora corrente, etichetta dell'evento, SE l'etichetta è `Aggiorna`, `Aggiungi`, o `cestino` invia anche il nome del compito in questione e SE l'etichetta è `segno di spunta` invia anche il nome del sotto-compito completato.

