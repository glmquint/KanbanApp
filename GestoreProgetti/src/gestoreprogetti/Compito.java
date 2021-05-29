
import javafx.beans.property.SimpleStringProperty;

public class Compito {
 
    private final SimpleStringProperty titolo;
    private final SimpleStringProperty descrizione;



    public Compito(String _titolo, String _descrizione) {
        this.titolo = new SimpleStringProperty(_titolo);
        this.descrizione = new SimpleStringProperty(_descrizione);
    }

    public String getTitolo() {
        return titolo.get();
    }

    public void setTitolo(String _titolo) {
        
        if (_titolo != "" && _titolo != null && !_titolo.isEmpty()) //FIXUP ?????
            titolo.set(_titolo);
    }

    public String getDescrizione() {
        return descrizione.get();
    }

    public void setDescrizione(String _descrizione) {
        descrizione.set(_descrizione);
    }
}