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
    
    /*
    private Compito compito_selezionato = null; 
    private ObservableList<Compito> lista_selezionata = null;
    
    public TramiteDB tdb;
    public LocalCacheHandler cache_gui;
    public ParametriConfigurazione config;
    public Logger logger;
    */
    
    
    public TextField nome_progetto;
    public TextField aggiungi_titolo;
    public TextField aggiungi_descrizione;
 
    public TableView<Compito> tabella_da_fare = new TableView<Compito>();
    public ObservableList<Compito> compiti_da_fare = FXCollections.observableArrayList();
 
    public TableView<Compito> tabella_in_esecuzione = new TableView<Compito>();
    public  ObservableList<Compito> compiti_in_esecuzione = FXCollections.observableArrayList();
 
    public TableView<Compito> tabella_completati = new TableView<Compito>();
    public  ObservableList<Compito> compiti_completati = FXCollections.observableArrayList();
    
    public Label nome_tabella_da_fare = new Label(config.nomeStato1);
    public Label nome_tabella_in_esecuzione = new Label(config.nomeStato2);
    public Label nome_tabella_completati = new Label(config.nomeStato3);
    
    public final Label id_progetto = new Label("ID progetto: " + config.ProjectID);  
    
    public final Button sposta_in_da_fare = new Button("<");
    public final Button sposta_da_fare_in_esecuzione = new Button(">");
    public final Button sposta_da_completati_in_esecuzione = new Button("<");
    public final Button sposta_incompletati = new Button(">");

    public final Button aggiungi = new Button("Aggiungi");
    public final Button elimina = new Button("Elimina");
    
    private Compito compito_selezionato = null; 
    private ObservableList<Compito> lista_selezionata = null;
    
    public ObservableList<PieChart.Data> dati_grafico;
    public PieChart grafico = new PieChart(dati_grafico);
    
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
        
        config = new ParametriConfigurazione();
        logger = new Logger(this);

        
        // FIXUP: delete this!!
        maxData1Length = config.maxRigheTab1;
        maxData2Length = config.maxRigheTab2;
        maxData3Length = config.maxRigheTab3;
         

        label1.setFont(new Font("Arial", 20));
        label2.setFont(new Font("Arial", 20));
        label3.setFont(new Font("Arial", 20));


        table1.setEditable(true);
        table2.setEditable(true);
        table3.setEditable(true);
        
        tdb = new TramiteDB(this);
        
        data1 = FXCollections.observableArrayList(tdb.QueryCompito(1));
        data2 = FXCollections.observableArrayList(tdb.QueryCompito(2));
        data3 = FXCollections.observableArrayList(tdb.QueryCompito(3));
        
        

        Callback<TableColumn, TableCell> cellFactory =
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
        
        
        remButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("titolo is: " + compito_selezionato.getTitolo());
                lista_selezionata.remove(compito_selezionato);
                logger.invia("Rimuovi");
                if (lista_selezionata.isEmpty()) {
                    remButton.setDisable(true);
                }
            }
        });
        remButton.setDisable(true);
        
        
        /*1*/
        TableColumn titoloCol1 = new TableColumn("Titolo");
        titoloCol1.setMinWidth(minTitleColWidth);
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
        descrizioneCol1.setMinWidth(minDescriptionColWidth);
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
        table1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = data1;
                table2.getSelectionModel().clearSelection();
                table3.getSelectionModel().clearSelection();
                if (remButton.isDisabled()) {
                    remButton.setDisable(false);
                }
            }
        });
        /*2*/
        TableColumn titoloCol2 = new TableColumn("Titolo");
        titoloCol2.setMinWidth(minTitleColWidth);
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
        descrizioneCol2.setMinWidth(minDescriptionColWidth);
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
        table2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = data2;
                table1.getSelectionModel().clearSelection();
                table3.getSelectionModel().clearSelection();
                if (remButton.isDisabled()) {
                    remButton.setDisable(false);
                }
            }
        });

        /*3*/
        TableColumn titoloCol3 = new TableColumn("Titolo");
        titoloCol3.setMinWidth(minTitleColWidth);
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
        descrizioneCol3.setMinWidth(minDescriptionColWidth);
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
        table3.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                compito_selezionato = newSelection;
                lista_selezionata = data3;
                table2.getSelectionModel().clearSelection();
                table1.getSelectionModel().clearSelection();
                if (remButton.isDisabled()) {
                    remButton.setDisable(false);
                }
            }
        });

 
        table1.setItems(data1);
        table2.setItems(data2);
        table3.setItems(data3);
        table1.getColumns().addAll(titoloCol1, descrizioneCol1);
        table2.getColumns().addAll(titoloCol2, descrizioneCol2);
        table3.getColumns().addAll(titoloCol3, descrizioneCol3);

 
        addTitolo = new TextField();
        addTitolo.setPromptText("Titolo");
        addTitolo.setMaxWidth(minDescriptionColWidth);
        addDescrizione = new TextField();
        addDescrizione.setMaxWidth(minDescriptionColWidth);
        addDescrizione.setPromptText("Descrizione");
        
        cache = new LocalCacheHandler(this);

 
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (data1.size() < maxData1Length) {
                    data1.add(new Compito(
                            addTitolo.getText(),
                            addDescrizione.getText()));
                    addTitolo.clear();
                    addDescrizione.clear();
                    logger.invia("Aggiungi");
                }
                
            }
        });
        
        
        
 
        vb1.getChildren().addAll(addTitolo, addDescrizione, addButton, remButton);
        vb1.setAlignment(Pos.BASELINE_CENTER);
        //hb2.getChildren().addAll(addTitolo, addDescrizione, addButton);
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
        
        moveRight1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("moveRight1");
                Compito selectedCompito = table1.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (data2.size() < maxData2Length) {
                        data2.add(selectedCompito);
                        data1.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("moveRight1");
                    }
                }
            }
        });
        
        moveLeft1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("moveLeft1");
                System.out.println(table2.getSelectionModel().getSelectedItem());
                Compito selectedCompito = table2.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (data1.size() < maxData1Length) {
                        data1.add(selectedCompito);
                        data2.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("moveLeft1");
                    }
                }            
            }
        });
        /*2*/
        
        moveRight2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("moveRight2");
                System.out.println(table2.getSelectionModel().getSelectedItem());
                Compito selectedCompito = table2.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (data3.size() < maxData3Length) {
                        data3.add(selectedCompito);
                        data2.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("moveRight2");
                    }
                }            
            }
        });
        
        moveLeft2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("moveLeft2");
                System.out.println(table3.getSelectionModel().getSelectedItem());
                Compito selectedCompito = table3.getSelectionModel().getSelectedItem();
                if (selectedCompito != null) {
                    if (data2.size() < maxData2Length) {
                        data2.add(selectedCompito);
                        data3.removeAll(selectedCompito);
                        updateGraph();
                        logger.invia("moveLeft2");
                    }
                }            
            }
        });
        


        pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data(config.nomeStato1, 3),
                new PieChart.Data(config.nomeStato2, 3),
                new PieChart.Data(config.nomeStato3, 3));
        
        chart.setTitle("Andamento");

        chart.setLegendVisible(config.mostraLegendaGrafico);

        vbox1.setSpacing(5);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().addAll(label1, table1, vb1);
        vbpulsanti1.setSpacing(5);
        vbpulsanti1.getChildren().addAll(moveRight1, moveLeft1);
        vbox2.setSpacing(5);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().addAll(label2, table2, chart);
        vbpulsanti2.setSpacing(5);
        vbpulsanti2.getChildren().addAll(moveRight2, moveLeft2);
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 10, 10, 10));
        vbox3.getChildren().addAll(label3, table3);
        
                
        vbpulsanti1.setAlignment(Pos.CENTER);
        vbpulsanti2.setAlignment(Pos.CENTER);

        //vbox2.getChildren().addAll(chart);

        
        hbox.getChildren().addAll(vbox1, vbpulsanti1, vbox2, vbpulsanti2, vbox3);
        
        final VBox MasterVbox = new VBox();
        MasterVbox.setAlignment(Pos.CENTER);
        ProjectName = new TextField();
        ProjectName.setPromptText("Nome Progetto");
        ProjectName.setMaxWidth(minDescriptionColWidth);
        ProjectName.setText(tdb.QueryProgetto(1));
        // ProjectName.setFocusTraversable(false);
        
              
        id_label.setFont(new Font("Arial", 25));
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            cache.salvaInCache();
            tdb.eseguiUpdate("DELETE FROM compito WHERE progetto_id = " + config.ProjectID);
            for (int i = 0; i < data1.size(); i++) {
                tdb.eseguiUpdate("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + data1.get(i).getTitolo() + "\", \"" + data1.get(i).getDescrizione() + "\", 1, " + config.ProjectID + ")");
            }
            for (int i = 0; i < data2.size(); i++) {
                tdb.eseguiUpdate("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + data2.get(i).getTitolo() + "\", \"" + data2.get(i).getDescrizione() + "\", 2, " + config.ProjectID + ")");
            }
            for (int i = 0; i < data3.size(); i++) {
                tdb.eseguiUpdate("INSERT INTO `compito` (`titolo`, `descrizione`, `stato`, `progetto_id`) VALUES (\"" + data3.get(i).getTitolo() + "\", \"" + data3.get(i).getDescrizione() + "\", 3, " + config.ProjectID + ")");
            }
            tdb.eseguiUpdate("UPDATE progetto SET nome = \"" + ProjectName.getText() + "\" WHERE id = " + config.ProjectID);
            logger.invia("STOP");
            System.out.println("bye!");
        });


        MasterVbox.getChildren().addAll(id_label, ProjectName, hbox);
        
        ((Group) scene.getRoot()).getChildren().addAll(MasterVbox);
        
 
        stage.setScene(scene);
        updateGraph();
        
        logger.invia("AVVIO");

        stage.show();
    }
    
    void updateGraph() {
        this.pieChartData.set(0, new PieChart.Data(config.nomeStato1, this.table1.getItems().size()));
        this.pieChartData.set(1, new PieChart.Data(config.nomeStato2, this.table2.getItems().size()));
        this.pieChartData.set(2, new PieChart.Data(config.nomeStato3, this.table3.getItems().size()));
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