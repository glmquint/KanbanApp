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
    
    public Label nome_tabella_da_fare; // new Label(parametri_configurazione.nomeStato1);
    public Label nome_tabella_in_esecuzione; // new Label(parametri_configurazione.nomeStato2);
    public Label nome_tabella_completati; // new Label(parametri_configurazione.nomeStato3);
    
    public Label id_progetto; // new Label("ID progetto: " + parametri_configurazione.ProjectID);  
    
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
    
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Applicazione Gestore Progetti");
        //FIXUP: disabilitare il *2 per gli schermi non 4K
        stage.setWidth(1000 * 2);
        stage.setHeight(550 * 2);
        
        parametri_configurazione = new ParametriConfigurazione();
        logger = new Logger(this);
        
        
        nome_tabella_da_fare = new Label(parametri_configurazione.nomeStato1);
        nome_tabella_in_esecuzione = new Label(parametri_configurazione.nomeStato2);
        nome_tabella_completati = new Label(parametri_configurazione.nomeStato3);
         
        nome_tabella_da_fare.setFont(new Font("Arial", 30));
        nome_tabella_in_esecuzione.setFont(new Font("Arial", 30));
        nome_tabella_completati.setFont(new Font("Arial", 30));
        
        id_progetto = new Label("ID progetto: " + parametri_configurazione.ProjectID);  


        tabella_da_fare.setEditable(true);
        /*
        tabella_in_esecuzione.setEditable(true);
        tabella_completati+.setEditable(true);
        */
        
        ponte_database = new PonteApplicazioneDB(this);
        
        compiti_da_fare = FXCollections.observableArrayList(ponte_database.QueryCompito(1));
        compiti_in_esecuzione = FXCollections.observableArrayList(ponte_database.QueryCompito(2));
        compiti_completati = FXCollections.observableArrayList(ponte_database.QueryCompito(3));
        
        

        Callback<TableColumn, TableCell> cellFactory =
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
        
        
        tasto_elimina.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("titolo is: " + compito_selezionato.getTitolo());
                lista_selezionata.remove(compito_selezionato);
                logger.invia("Rimuovi");
                if (lista_selezionata.isEmpty()) {
                    tasto_elimina.setDisable(true);
                }
            }
        });
        tasto_elimina.setDisable(true);
        
        
        /*1*/
        TableColumn titoloCol1 = new TableColumn("Titolo");
        titoloCol1.setMinWidth(100 * 2);
        titoloCol1.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("titolo"));
        titoloCol1.setCellFactory(cellFactory);
        titoloCol1.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitolo(t.getNewValue());
                    
                }
             }
        );
        TableColumn descrizioneCol1 = new TableColumn("Descrizione");
        descrizioneCol1.setMinWidth(200 * 2);
        descrizioneCol1.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));
        descrizioneCol1.setCellFactory(cellFactory);
        descrizioneCol1.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDescrizione(t.getNewValue());
                }
            }
        );
        tabella_da_fare.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = compiti_da_fare;
                tabella_in_esecuzione.getSelectionModel().clearSelection();
                tabella_completati.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) {
                    tasto_elimina.setDisable(false);
                }
            }
        });
        /*2*/
        TableColumn titoloCol2 = new TableColumn("Titolo");
        titoloCol2.setMinWidth(100*2);
        titoloCol2.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("titolo"));
        titoloCol2.setCellFactory(cellFactory);
        titoloCol2.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitolo(t.getNewValue());
                }
             }
        );
        TableColumn descrizioneCol2 = new TableColumn("Descrizione");
        descrizioneCol2.setMinWidth(200*2);
        descrizioneCol2.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));
        descrizioneCol2.setCellFactory(cellFactory);
        descrizioneCol2.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDescrizione(t.getNewValue());
                }
            }
        );
        tabella_in_esecuzione.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = compiti_in_esecuzione;
                tabella_da_fare.getSelectionModel().clearSelection();
                tabella_completati.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) {
                    tasto_elimina.setDisable(false);
                }
            }
        });

        /*3*/
        TableColumn titoloCol3 = new TableColumn("Titolo");
        titoloCol3.setMinWidth(100*2);
        titoloCol3.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("titolo"));
        titoloCol3.setCellFactory(cellFactory);
        titoloCol3.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTitolo(t.getNewValue());
                }
             }
        );
        TableColumn descrizioneCol3 = new TableColumn("Descrizione");
        descrizioneCol3.setMinWidth(200*2);
        descrizioneCol3.setCellValueFactory(
            new PropertyValueFactory<Compito, String>("descrizione"));
        descrizioneCol3.setCellFactory(cellFactory);
        descrizioneCol3.setOnEditCommit(
            new EventHandler<CellEditEvent<Compito, String>>() {
                @Override
                public void handle(CellEditEvent<Compito, String> t) {
                    ((Compito) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDescrizione(t.getNewValue());
                }
            }
        );
        tabella_completati.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = compiti_completati;
                tabella_in_esecuzione.getSelectionModel().clearSelection();
                tabella_da_fare.getSelectionModel().clearSelection();
                if (tasto_elimina.isDisabled()) {
                    tasto_elimina.setDisable(false);
                }
            }
        });

 
        tabella_da_fare.setItems(compiti_da_fare);
        tabella_in_esecuzione.setItems(compiti_in_esecuzione);
        tabella_completati.setItems(compiti_completati);
        tabella_da_fare.getColumns().addAll(titoloCol1, descrizioneCol1);
        tabella_in_esecuzione.getColumns().addAll(titoloCol2, descrizioneCol2);
        tabella_completati.getColumns().addAll(titoloCol3, descrizioneCol3);

 
        aggiungi_titolo = new TextField();
        aggiungi_titolo.setPromptText("Titolo");
        aggiungi_titolo.setMaxWidth(200*2);
        aggiungi_descrizione = new TextField();
        aggiungi_descrizione.setMaxWidth(200*2);
        aggiungi_descrizione.setPromptText("Descrizione");
        
        cache_gui = new CacheGUI(this);

 
        
        tasto_aggiungi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (compiti_da_fare.size() < parametri_configurazione.maxRigheTab1) {
                    compiti_da_fare.add(new Compito(
                            aggiungi_titolo.getText(),
                            aggiungi_descrizione.getText()));
                    aggiungi_titolo.clear();
                    aggiungi_descrizione.clear();
                    logger.invia("Aggiungi");
                }
                
            }
        });
        
        
        
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(aggiungi_titolo, aggiungi_descrizione, tasto_aggiungi, tasto_elimina);
        vb1.setAlignment(Pos.BASELINE_CENTER);
        //hb2.getChildren().addAll(aggiungi_titolo, aggiungi_descrizione, tasto_aggiungi);
        vb1.setSpacing(3);
        /*hb2.setSpacing(3);
        hb3.setSpacing(3);*/
 
        final HBox hbox = new HBox();
        final VBox vbox1 = new VBox();
        final VBox vbpulsanti1 = new VBox();
        final VBox vbox2 = new VBox();
        final VBox vbpulsanti2 = new VBox();
        final VBox vbox3 = new VBox();
        
        /*1*/
        
        sposta_da_fare_in_esecuzione.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("sposta_da_fare_in_esecuzione");
                Compito selectedCompito = tabella_da_fare.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (compiti_in_esecuzione.size() < parametri_configurazione.maxRigheTab2) {
                        compiti_in_esecuzione.add(selectedCompito);
                        compiti_da_fare.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("sposta_da_fare_in_esecuzione");
                    }
                }
            }
        });
        
        sposta_in_da_fare.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("sposta_in_da_fare");
                System.out.println(tabella_in_esecuzione.getSelectionModel().getSelectedItem());
                Compito selectedCompito = tabella_in_esecuzione.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (compiti_da_fare.size() < parametri_configurazione.maxRigheTab1) {
                        compiti_da_fare.add(selectedCompito);
                        compiti_in_esecuzione.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("sposta_in_da_fare");
                    }
                }            
            }
        });
        /*2*/
        
        sposta_in_completati.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("sposta_in_completati");
                System.out.println(tabella_in_esecuzione.getSelectionModel().getSelectedItem());
                Compito selectedCompito = tabella_in_esecuzione.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (compiti_completati.size() < parametri_configurazione.maxRigheTab3) {
                        compiti_completati.add(selectedCompito);
                        compiti_in_esecuzione.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("sposta_in_completati");
                    }
                }            
            }
        });
        
        sposta_da_completati_in_esecuzione.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("sposta_da_completati_in_esecuzione");
                System.out.println(tabella_completati.getSelectionModel().getSelectedItem());
                Compito selectedCompito = tabella_completati.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (compiti_in_esecuzione.size() < parametri_configurazione.maxRigheTab2) {
                        compiti_in_esecuzione.add(selectedCompito);
                        compiti_completati.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("sposta_da_completati_in_esecuzione");
                    }
                }            
            }
        });
        

       
        dati_grafico =
                FXCollections.observableArrayList(
                new PieChart.Data(parametri_configurazione.nomeStato1, 3),
                new PieChart.Data(parametri_configurazione.nomeStato2, 3),
                new PieChart.Data(parametri_configurazione.nomeStato3, 3));
        
        grafico = new PieChart(dati_grafico);
        grafico.setTitle("Andamento");

        grafico.setLegendVisible(parametri_configurazione.mostraLegendaGrafico);

        vbox1.setSpacing(5);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(nome_tabella_da_fare, tabella_da_fare, vb1);
        vbpulsanti1.setSpacing(5);
        vbpulsanti1.getChildren().addAll(sposta_da_fare_in_esecuzione, sposta_in_da_fare);
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().addAll(nome_tabella_in_esecuzione, tabella_in_esecuzione, grafico);
        vbpulsanti2.setSpacing(5);
        vbpulsanti2.getChildren().addAll(sposta_in_completati, sposta_da_completati_in_esecuzione);
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 10, 10, 10));
        vbox3.getChildren().addAll(nome_tabella_completati, tabella_completati);
        
                
        vbpulsanti1.setAlignment(Pos.CENTER);
        vbpulsanti2.setAlignment(Pos.CENTER);

        //vbox2.getChildren().addAll(grafico);

        
        hbox.getChildren().addAll(vbox1, vbpulsanti1, vbox2, vbpulsanti2, vbox3);
        
        final VBox MasterVbox = new VBox();
        MasterVbox.setAlignment(Pos.CENTER);
        nome_progetto = new TextField();
        nome_progetto.setPromptText("Nome Progetto");
        nome_progetto.setMaxWidth(200*2);
        nome_progetto.setText(ponte_database.QueryProgetto());
        // nome_progetto.setFocusTraversable(false);
              
        id_progetto.setFont(new Font("Arial", 25));
        stage.setOnCloseRequest((WindowEvent we) -> {
            cache_gui.salvaInCache();
            ponte_database.aggiornaCompiti();
            ponte_database.aggiornaProgetto();
            logger.invia("STOP");
            System.out.println("bye!");
        });
        MasterVbox.getChildren().addAll(id_progetto, nome_progetto, hbox);
        ((Group) scene.getRoot()).getChildren().addAll(MasterVbox);

 
        stage.setScene(scene);
        updateGraph();

        logger.invia("AVVIO");

        stage.show();
    }
    
    void updateGraph() {
        System.out.println("updateGraph ");
        this.dati_grafico.set(0, new PieChart.Data(parametri_configurazione.nomeStato1, this.tabella_da_fare.getItems().size()));
        this.dati_grafico.set(1, new PieChart.Data(parametri_configurazione.nomeStato2, this.tabella_in_esecuzione.getItems().size()));
        this.dati_grafico.set(2, new PieChart.Data(parametri_configurazione.nomeStato3, this.tabella_completati.getItems().size()));
    }
 
    
 
    class EditingCell extends TableCell<Compito, String> {
 
        private TextField textField;
 
        public EditingCell() {
        }
 
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
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}