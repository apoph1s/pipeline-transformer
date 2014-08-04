/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fusepoolp3.p3pipelinetrasformer;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import javax.activation.MimeType;
import eu.fusepool.p3.vocab.TRANSFORMER;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Gabor
 */
public class Transformer {

    private URI uri;
    private Set<MimeType> supportedInputFormats;
    private Set<MimeType> supportedOutputFormats;

    public Transformer(URI uri) {
        this.uri = uri;
        SetMimeTypes();
    }

    public Transformer(String uriString) {
        try {
            uri = new URI(uriString);
            SetMimeTypes();
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI syntax error!", e);
        }
    }

    private void SetMimeTypes() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String output;
            while ((output = in.readLine()) != null) {
                result.append(output);
                result.append("\n");
            }
            StringReader stringReader = new StringReader(result.toString());
            Model model = ModelFactory.createDefaultModel();
            model.read(stringReader,null,"TTL");
            
            // save supported formats
            for (Statement s : model.listStatements().toList()) {
                System.out.println(s.asTriple().getPredicate().toString());
                if(s.asTriple().getPredicate().getLocalName().equals("supportedInputFormat")){
                    //System.out.println(s.asTriple().getObject().getLiteralValue().toString());
                }
                if(s.asTriple().getPredicate().getLocalName().equals("supportedOutputFormat")){
                    //System.out.println(s.asTriple().getObject().getLiteralValue().toString());
                }
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Cannot establish connection to <" + uri.toString() + "> !", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
