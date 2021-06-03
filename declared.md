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