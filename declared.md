4/5 16:00->18:34 2h30

svolta analisi dei requisiti richiesti per il progetto, con studio di fattibilità di varie idee possibili. Scelta l'idea del "gestore di progetti" e strutturato un mockup dell'interfaccia con gli elementi fondamentali quali i pulsanti, le tabelle e il form di inserimento e modifica dei compiti e dei relativi sotto-compiti. Stilato anche il documento di analisi con schematizzazione dello scenario d'uso e delle funzionalità dell'applicazione lato server, della cache e del file di configurazione.



5/5 18:30->20:03 1h30

apportate le modifiche richieste, come da suggerimento del professore, al mockup e al documento di analisi. In particolare è stato semplificato lo stile e le funzionalità complessive dell'applicazione e, di conseguenza, ha subito modifiche anche lo schema di scenario d'uso. Sono inoltre state corrette alcune imprecisioni sul formato e il contenuto reale dei file di configurazione e della cache.



27/5 14:30->18:31 4h

lavorato sullo schema UML delle classi con definizione degli attributi e dei metodi necessari. In particolare sono state definite le componenti fondamentali dell'interfaccia, della cache, dei parametri di config, del ponte applicazione-database e dei compiti che compongono il progetto. Di questi è stata definita anche la rappresentazione con Schema E-R che avranno all'interno del database. Infine sono state definite, per ogni classe, le librerie in uso e le relazioni di dipendenza tra di esse.



28/5 15:00->18:03 3h

prototipazione degli elementi fondamentali dell'interfaccia. Tramite la documentazione online, sono state provate varie implementazioni possibili per tabelle, grafici e comunicazione di messaggi con un server tramite socket. Con queste informazioni è stata realizzata una prima soluzione possibile di interfaccia secondo lo schema del mockup con alcuni pulsanti, campi di testo, un grafico a torta e le tre tabelle che conterranno i compiti, il tutto con il minor numero di modifiche necessarie



29/5 14:30->18:32 4h

introdotto il progetto vero e proprio contenente la classe principale dell'interfaccia. Seppur ancora con uno stile grezzo, sono presenti le tre tabelle dei compiti ed un semplice form con due campi di testo per poter aggiungere un nuovo compito alla lista dei da-fare. Sono presenti le frecce per spostare un compito da una tabella ad un'altra, il grafico a torta che viene aggiornato ad ogni cambiamento del contenuto di una delle tre tabelle, oltre ai label hard-coded con il nome dei tre stati.

3/6 15:00->20:00 5h

sviluppata la funzionalità di configurazione dell'applicazione tramite file xml. In particolare, è stata introdotta la classe ParametriConfig che si appoggia su GestoreXML per validare (tramite il file xsd) ed estrarre i valori contenuti nel file xml come specificato nel documento di analisi. Questi valori, inizializzati all' avvio dell'applicazione, sono così disponibili alla classe dell'interfaccia. Attualmente gli unici valori inutilizzati riguardano la connettività con archivio e log server

4/6 15:00->20:00 5h

implementato il database e la comunicazione con questo per poter salvare lo stato attuale dei compiti. La classe PonteDB permette di recuperare il nome del progetto, dato l'id nel file di configurazione, e i compiti appartenenti ad un determinato stato. E' inoltre possibile aggiornare i compiti presenti in un progetto ed il nome del progetto su cui si sta lavorando. L'unica comunicazione con il db avviene alla chiusura dell'applicazione, nel quale viene sovrascritto lo stato completo del progetto

5/6 15:00->20:00 5h

implementato il server di log e l'invio da parte dell'applicazione di eventi rilevanti secondo il documento di analisi. La classe LogServer si appoggia su GestoreXMLog per validare ed aggiungere in un file txt i messaggi ricevuti tramite socket in formato xml. La classe Logger viene chiamata dall'interfaccia ad ogni evento rilevante (accensione/chiusura dell'applicazione e inserimento/modifica/rimozione di compiti) per inviare un oggetto messaggio serializzato in xml dalla classe GestoreXML

6/6 15:00->20:00 5h

sviluppata funzionalità di salvataggio su e da file di cache per i campi titolo e descrizione del form di inserimento di un compito. bugfix: colore di sfondo non veniva correttamente modificato dal file config. bugfix: non veniva inviato l'evento di aggiornamento nome del progetto. Introdotto documento di collaudo con descrizione dello scenario d'uso lato utente, corredato da alcuni screenshot. Iniziata prototipazione su un progetto a parte per studiare gli esempi online sull'editing di tabelle

7/6 15:00->20:00 5h

completato collaudo di tutte le funzionalità inizialmente previste per l'applicazione, che sono ora riportate nel manuale d'uso utente con relativi screenshot. Sviluppato editing delle righe delle tabelle, secondo consiglio del professore, con lo scopo di rimuovere completamente i textinput: per adesso non verranno rimossi in quanto sono gli unici elementi toccati dal salvataggio in cache. Nuove etichette eventi "cambio titolo" e "cambio descrizione" inviati come log alla modifica di un compito

8/6 15:00->20:00 5h

completato documento di collaudo per contenere la descrizione dell'utilizzo delle tabelle editabili. Pulitura della documentazione con nuovi nomi di classi più descrittivi e, di conseguenza, ricontrollate tutte le loro occorrenze. Pulitura del codice per rispettare criteri di qualità: lunghezza dei metodi, migliorati i commenti del codice, migliorata descrittività dei nomi usati per le classi, per i metodi e per le proprietà. Refactoring del codice per evitarne la duplicazione, dove possibile.

