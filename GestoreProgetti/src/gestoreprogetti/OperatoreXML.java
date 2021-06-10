
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class OperatoreXML {
    
    public void valida(){
        try {   
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File("gestoreProgetti_config.xml")); //01
            Schema s = sf.newSchema(new StreamSource(new File("gestoreProgetti_config.xsd"))); //01
            s.newValidator().validate(new DOMSource(d));
            
          } catch (Exception e) {
            if (e instanceof SAXException) 
              System.out.println("Errore di validazione: " + e.getMessage());
            else
              System.out.println(e.getMessage());    
          }
    }
    
    public ParametriConfigurazione estrai(){
        ParametriConfigurazione contenutoFileXML;
        try{
            String x = new String(Files.readAllBytes(Paths.get("gestoreProgetti_config.xml")));
            XStream xs = new XStream();
            xs.useAttributeFor(ParametriConfigurazione.class, "mostraLegendaGrafico");
            contenutoFileXML = (ParametriConfigurazione)xs.fromXML(x); 

        } catch (IOException e) {
            System.out.println("Problema nell'estrazione del file di configurazione: " + e.getMessage());
            return null;
        }
        return contenutoFileXML;
    }
    
    public String formattaMessaggio(MessaggioLog messaggioDaInviare) {
        XStream xs = new XStream();
        xs.registerConverter(new DateConverter("yyyy:MM:dd_HH:mm:ss", null)); //03
        return xs.toXML(messaggioDaInviare);
    }
    
}
