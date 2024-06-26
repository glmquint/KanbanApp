import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


 
public class GestoreProgettiGUI extends Application {    
    
    public TextField nome_progetto;
    public TextField aggiungi_titolo;
    public TextField aggiungi_descrizione;
 
    public TableView<Compito> tabella_da_fare = new TableView<Compito>();
    public ObservableList<Compito> compiti_da_fare = FXCollections.observableArrayList();
 
    public TableView<Compito> tabella_in_esecuzione = new TableView<Compito>();
    public  ObservableList<Compito> compiti_in_esecuzione = FXCollections.observableArrayList();
 
    public TableView<Compito> tabella_completati = new TableView<Compito>();
    public  ObservableList<Compito> compiti_completati = FXCollections.observableArrayList();
    
    public Label nome_tabella_da_fare; 
    public Label nome_tabella_in_esecuzione; 
    public Label nome_tabella_completati; 
    
    public Label id_progetto; 
    
    public final Button sposta_in_da_fare = new Button("<");
    public final Button sposta_da_fare_in_esecuzione = new Button(">");
    public final Button sposta_da_completati_in_esecuzione = new Button("<");
    public final Button sposta_in_completati = new Button(">");

    public final Button tasto_aggiungi = new Button("Aggiungi");
    public final Button tasto_elimina = new Button("Elimina");
    
    private Compito compito_selezionato; 
    private ObservableList<Compito> lista_selezionata;
    
    public ObservableList<PieChart.Data> dati_grafico;
    public PieChart grafico;
    
    public CacheGUI cache_gui;
    public ParametriConfigurazione parametri_configurazione;
    public Logger logger;
    public PonteApplicazioneDB ponte_database;
    
             
    final HBox hbox = new HBox();
    final VBox vbox1 = new VBox();
    final VBox vbpulsanti1 = new VBox();
    final VBox vbox2 = new VBox();
    final VBox vbpulsanti2 = new VBox();
    final VBox vbox3 = new VBox();
    final VBox MasterVbox = new VBox();
    
    public void impostaTabella3() {/* (1) */
        nome_tabella_completati = new Label(parametri_configurazione.nome_terza_tabella);
        nome_tabella_completati.setFont(new Font("Arial", 30));
        compiti_completati = FXCollections.observableArrayList(ponte_database.QueryCompito(3)); /* (2) */
        TableColumn titoloCol3 = new TableColumn("Titolo");
        titoloCol3.setMinWidth(100*2);
        titoloCol3.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("titolo")); /*(3)*/
        TableColumn descrizioneCol3 = new TableColumn("Descrizione");
        descrizioneCol3.setMinWidth(200*2);
        descrizioneCol3.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));/*(3)*/
        
        tabella_completati.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {/*(4) */
                compito_selezionato = newSelection;
                lista_selezionata = compiti_completati;
                tabella_in_esecuzione.getSelectionModel().clearSelection();
                tabella_da_fare.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) { /*(5) */
                    tasto_elimina.setDisable(false);
                }
            }
        });
        tabella_completati.setItems(compiti_completati);
        tabella_completati.getColumns().addAll(titoloCol3, descrizioneCol3);
    }
    
    public void impostaTabella2() {/* (6) */
        nome_tabella_in_esecuzione = new Label(parametri_configurazione.nome_seconda_tabella);
        nome_tabella_in_esecuzione.setFont(new Font("Arial", 30));
        compiti_in_esecuzione = FXCollections.observableArrayList(ponte_database.QueryCompito(2)); /*(2)*/
        TableColumn titoloCol2 = new TableColumn("Titolo");
        titoloCol2.setMinWidth(100*2);
        titoloCol2.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("titolo"));/*(3)*/
        TableColumn descrizioneCol2 = new TableColumn("Descrizione");
        descrizioneCol2.setMinWidth(200*2);
        descrizioneCol2.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));/*(3)*/

        tabella_in_esecuzione.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {/*(4)*/
                compito_selezionato = newSelection;
                lista_selezionata = compiti_in_esecuzione;
                tabella_da_fare.getSelectionModel().clearSelection();
                tabella_completati.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) {/*(5)*/
                    tasto_elimina.setDisable(false);
                }
            }
        });
        tabella_in_esecuzione.setItems(compiti_in_esecuzione);
        tabella_in_esecuzione.getColumns().addAll(titoloCol2, descrizioneCol2);
    }
    
    public void impostaTabella1() {/* (7) */
        
        Callback<TableColumn, TableCell> cellFactory = /* (8) */
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new CellaEditabile();
                 }
             };
        nome_tabella_da_fare = new Label(parametri_configurazione.nome_prima_tabella);
        nome_tabella_da_fare.setFont(new Font("Arial", 30));
        tabella_da_fare.setEditable(true); /* (9) */
        compiti_da_fare = FXCollections.observableArrayList(ponte_database.QueryCompito(1));/*(2)*/
        TableColumn titoloCol1 = new TableColumn("Titolo");
        titoloCol1.setMinWidth(100 * 2);
        titoloCol1.setCellValueFactory(
            new PropertyValueFactory<>("titolo"));/*(3)*/
        titoloCol1.setCellFactory(cellFactory);
        titoloCol1.setOnEditCommit( /* (10) */
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitolo(t.getNewValue());
                    logger.invia("MODIFICA TITOLO");
                    
                }
             }
        );
        TableColumn descrizioneCol1 = new TableColumn("Descrizione");
        descrizioneCol1.setMinWidth(200 * 2);
        descrizioneCol1.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));/*(3)*/
        descrizioneCol1.setCellFactory(cellFactory);
        descrizioneCol1.setOnEditCommit(/*(10)*/
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDescrizione(t.getNewValue());
                    logger.invia("MODIFICA DESCRIZIONE");
                }
            }
        );
        tabella_da_fare.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {/*(4)*/
                compito_selezionato = newSelection;
                lista_selezionata = compiti_da_fare;
                tabella_in_esecuzione.getSelectionModel().clearSelection();
                tabella_completati.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) {/*(5)*/
                    tasto_elimina.setDisable(false);
                }
            }
        });
        tabella_da_fare.setItems(compiti_da_fare);
        tabella_da_fare.getColumns().addAll(titoloCol1, descrizioneCol1);
    }
    
    public void impostaFrecce(){/* (11) */
        sposta_da_fare_in_esecuzione.setOnAction((ActionEvent e) -> {
            System.out.println("sposta_da_fare_in_esecuzione");
            Compito selectedCompito = tabella_da_fare.getSelectionModel().getSelectedItem();
            if (selectedCompito != null) {
                if (compiti_in_esecuzione.size() < parametri_configurazione.limite_seconda_tabella) {/* (12) */
                    compiti_in_esecuzione.add(selectedCompito);
                    compiti_da_fare.removeAll(selectedCompito);
                    aggiornaGrafico();
                    logger.invia("sposta_da_fare_in_esecuzione");
                }
            }
        });
        sposta_in_da_fare.setOnAction((ActionEvent e) -> {
            System.out.println("sposta_in_da_fare");
            System.out.println(tabella_in_esecuzione.getSelectionModel().getSelectedItem());
            Compito selectedCompito = tabella_in_esecuzione.getSelectionModel().getSelectedItem();
            if (selectedCompito != null) {
                if (compiti_da_fare.size() < parametri_configurazione.limite_prima_tabella) {/*(12)*/
                    compiti_da_fare.add(selectedCompito);
                    compiti_in_esecuzione.removeAll(selectedCompito);
                    aggiornaGrafico();
                    logger.invia("sposta_in_da_fare");
                }            
            }
        });
        sposta_in_completati.setOnAction((ActionEvent e) -> {
            System.out.println("sposta_in_completati");
            System.out.println(tabella_in_esecuzione.getSelectionModel().getSelectedItem());
            Compito selectedCompito = tabella_in_esecuzione.getSelectionModel().getSelectedItem();
            if (selectedCompito != null) {
                if (compiti_completati.size() < parametri_configurazione.limite_terza_tabella) {/*(12)*/
                    compiti_completati.add(selectedCompito);
                    compiti_in_esecuzione.removeAll(selectedCompito);
                    aggiornaGrafico();
                    logger.invia("sposta_in_completati");
                }            
            }
        });
        sposta_da_completati_in_esecuzione.setOnAction( (ActionEvent e) -> {
            System.out.println("sposta_da_completati_in_esecuzione");
            System.out.println(tabella_completati.getSelectionModel().getSelectedItem());
            Compito selectedCompito = tabella_completati.getSelectionModel().getSelectedItem();
            if (selectedCompito != null) {
                if (compiti_in_esecuzione.size() < parametri_configurazione.limite_seconda_tabella) {/*(12)*/
                    compiti_in_esecuzione.add(selectedCompito);
                    compiti_completati.removeAll(selectedCompito);
                    aggiornaGrafico();
                    logger.invia("sposta_da_completati_in_esecuzione");
                }            
            }
        });
    }
    
    public void impostaStyleBoxes(){/* (13) */
        vbox1.setSpacing(5);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(nome_tabella_da_fare, tabella_da_fare, aggiungi_titolo, aggiungi_descrizione, tasto_aggiungi, tasto_elimina);
        vbox1.setAlignment(Pos.TOP_CENTER);
        
        vbpulsanti1.setSpacing(5);
        vbpulsanti1.getChildren().addAll(sposta_da_fare_in_esecuzione, sposta_in_da_fare);
        vbpulsanti1.setAlignment(Pos.CENTER);
        
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().addAll(nome_tabella_in_esecuzione, tabella_in_esecuzione, grafico);
        vbox2.setAlignment(Pos.TOP_CENTER);
        
        vbpulsanti2.setSpacing(5);
        vbpulsanti2.getChildren().addAll(sposta_in_completati, sposta_da_completati_in_esecuzione);
        vbpulsanti2.setAlignment(Pos.CENTER);
        
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 10, 10, 10));
        vbox3.getChildren().addAll(nome_tabella_completati, tabella_completati);
        vbox3.setAlignment(Pos.TOP_CENTER);

        
        hbox.getChildren().addAll(vbox1, vbpulsanti1, vbox2, vbpulsanti2, vbox3);
        
        
        MasterVbox.setAlignment(Pos.CENTER);
        MasterVbox.setStyle("-fx-background-color: " + parametri_configurazione.colore_background);/*(14)*/
        
    }

    public void impostaGrafico(){/*(15)*/
        dati_grafico =
        FXCollections.observableArrayList(
        new PieChart.Data(parametri_configurazione.nome_prima_tabella, 3),
        new PieChart.Data(parametri_configurazione.nome_seconda_tabella, 3),
        new PieChart.Data(parametri_configurazione.nome_terza_tabella, 3));
        
        grafico = new PieChart(dati_grafico);
        grafico.setTitle("Andamento");

        grafico.setLegendVisible(parametri_configurazione.mostraLegendaGrafico);/* (16)*/
    }
    
    public void impostaTasti(){/*(17)*/
        tasto_elimina.setOnAction((ActionEvent e) -> {
            System.out.println("titolo is: " + compito_selezionato.getTitolo());
            lista_selezionata.remove(compito_selezionato);
            logger.invia("RIMUOVI");
            aggiornaGrafico();/* (18)*/
            if (lista_selezionata.isEmpty()) {
                tasto_elimina.setDisable(true);
            }
        });
        tasto_elimina.setDisable(true);
        
        tasto_aggiungi.setOnAction((ActionEvent e) -> {
            if (compiti_da_fare.size() < parametri_configurazione.limite_prima_tabella) {/*(12)*/
                compiti_da_fare.add(new Compito(
                        aggiungi_titolo.getText(),
                        aggiungi_descrizione.getText()));
                aggiungi_titolo.clear();
                aggiungi_descrizione.clear();
                logger.invia("AGGIUNGI");
                aggiornaGrafico();
            }
        });
    }
    
    public void impostaTextInputs(){/*(19)*/
        aggiungi_titolo = new TextField();
        aggiungi_titolo.setPromptText("Titolo");
        aggiungi_titolo.setMaxWidth(200*2);
        aggiungi_descrizione = new TextField();
        aggiungi_descrizione.setMaxWidth(200*2);
        aggiungi_descrizione.setPromptText("Descrizione");
        
        cache_gui = new CacheGUI(this);/*(20)*/
    }
    
    public void aggiornaGrafico() {/*(21)*/
        this.dati_grafico.set(0, new PieChart.Data(parametri_configurazione.nome_prima_tabella, this.tabella_da_fare.getItems().size()));
        this.dati_grafico.set(1, new PieChart.Data(parametri_configurazione.nome_seconda_tabella, this.tabella_in_esecuzione.getItems().size()));
        this.dati_grafico.set(2, new PieChart.Data(parametri_configurazione.nome_terza_tabella, this.tabella_completati.getItems().size()));
    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Applicazione Gestore Progetti");
        //FIXUP: disabilitare il *2 per gli schermi non 4K
        stage.setWidth(1000 * 2);
        stage.setHeight(550 * 2);
        parametri_configurazione = new ParametriConfigurazione();
        logger = new Logger(this);
        ponte_database = new PonteApplicazioneDB(this);
        impostaTabella1();
        impostaTabella2();
        impostaTabella3();
        id_progetto = new Label("ID progetto: " + parametri_configurazione.id_progetto);  
        id_progetto.setFont(new Font("Arial", 25));
        impostaTextInputs();
        impostaTasti();
        impostaFrecce();
        impostaGrafico();
        impostaStyleBoxes();
        nome_progetto = new TextField();
        nome_progetto.setPromptText("Nome Progetto");
        nome_progetto.setMaxWidth(200*2);
        nome_progetto.setText(ponte_database.QueryProgetto());
        nome_progetto.textProperty().addListener(new ChangeListener<String>() {/* (22) */
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                logger.invia("CAMBIO NOME PROGETTO");
            }    
        });
        MasterVbox.getChildren().addAll(id_progetto, nome_progetto, hbox);
        stage.setOnCloseRequest((WindowEvent we) -> {
            cache_gui.salvaInCache();
            ponte_database.aggiornaCompiti();
            ponte_database.aggiornaProgetto();
            logger.invia("STOP");
            System.out.println("bye!");
        });
        ((Group) scene.getRoot()).getChildren().addAll(MasterVbox);
        stage.setScene(scene);
        aggiornaGrafico();
        logger.invia("AVVIO");
        stage.show();
    }
    

    class CellaEditabile extends TableCell<Compito, String> {/*(23)*/
 
        private TextField textField;
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}

/* Commenti:
    (1): setup della terza tabella (compiti completati), simile alla seconda, 
        non permette la modifica dei compiti che contiene
    (2): il popolamento della tabella viene fatto tramite una richiesta al 
        database relativamente a questo progetto e a questo stato
    (3): viene usata una PropertyValueFactory per occuparsi del contenuto della 
        cella sia per il titolo che per la descrizione dei compiti
    (4): alla selezione di una nuova tabella si cambia il valore di 
        compito_selezionato e tabella_selezionata, utili per l'eliminazione 
        di un compito
    (5): attivazione del tasto elimina se è stato selezionato un nuovo compito
    (6): setup della seconda tabella (compiti in esecuzione), simile alla terza, 
        non permette la modifica dei compiti che contiene
    (7): setup della prima tabella (compiti da fare), l'unica che permette di 
        editare i compiti che contiene
    (8): la cellFactory verrà usata nelle colonne titolo e descrizione e sfrutta 
        la classe CellaEditabile per gestire il comportamento di una riga della 
        tabella quando questa viene selezionata
    (9): è necessatio ricordarsi di settare la tabella come editabile o il 
        comportamento sarà uguale a quello delle altre due tabelle
    (10): si specifica la funzionalità di commit della cella editabile, in 
        particolare viene settato il valore della cella con ciò che è contenuto 
        nel textfield
    (11): setup dei tasti a frecce che permettono di passare un compito da una 
        tabella all'altra
    (12): deve essere effettuato un check sul limite di compiti della tabella di 
        destinazione, come definito nel file config
    (13): setup delle vertical boxes e horizontal boxes usate per la 
        disposizione degli elementi nell'interfaccia. Queste permettono di non 
        usare setX e setY per ogni elemento dell'interfaccia e facilitano 
        l'allineamento centrale di tutti gli elementi contenuti in una box
    (14): il colore di sfondi viene definito dal file di configurazione
    (15): inizializzazione del grafico a torta con le tre categorie possibili di 
        stato per i compiti
    (16): la visibilità della legenda è scelta nel file di configurazione
    (17): setup dei tasti di aggiunta e rimozione di un compito da una tabella
    (18): viene richiesto di ricalcolare il grafico a torta ad ogni pressione 
        dei tasti
    (19): setup dei textinputs per il titolo e la descrizione di un nuovo compito
    (20): i textinput vengono inizializzati con il contenuto salvato nella cache
    (21): l'aggiornamento del grafico consiste semplicemente nel contare il 
        numero di compiti per ogni stato
    (22): al cambio di valore del textfield per il nome del progetto deve essere 
        inviato il relativo evento al log server
    (23): classe interna necessaria per la funzionalità di editing della prima 
        tabella. Definisce il comportamento che deve avere una cella della 
        tabella per potersi trasformare in un textfield e trasfferire il proprio 
        contenuto nella cella al momento del commit
*/