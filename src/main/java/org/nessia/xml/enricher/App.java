package org.nessia.xml.enricher;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.FilenameUtils;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class App {

    public static final String OUTPUT_SUFIX = "-output";

    public static void main(String[] args) {
        if(args.length < 2){
            System.err.println("Número incorrecto de argumentos");
            System.exit(-1);
        }

        String originPath = args[0];
        File origin = new File(originPath);
        if(!origin.exists()){
            System.err.println("El fichero de origen no existe");
            System.exit(-1);
        }


        String transformationsPath = args[1];
        if(!new File(transformationsPath).exists()){
            System.err.println("El fichero de transformaciones no existe");
            System.exit(-1);
        }

        String outputFileName = null;
        if(args.length > 2){
            outputFileName = args[3];
        }else{
            String extension = FilenameUtils.getExtension(originPath);
            outputFileName = originPath.substring(0, originPath.length() - extension.length())
                    + OUTPUT_SUFIX + "." + extension;
        }

        String schemaPath = null;
        if(args.length > 3){
            schemaPath = args[2];
        }

        // parse an XML document into a DOM tree
        DocumentBuilder parser;
        try {
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document originDocument = parser.parse(new File(originPath));


            Document transformersDocument = parser.parse(new File(transformationsPath));

            // Optionally validate the original document
            if(schemaPath != null){
                validate(schemaPath, originDocument);
            }

            NodeTransformer transformer = new NodeTransformerMap();
            Document output = transformer.transform(originDocument, transformersDocument);



            //
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }

    private static void validate(String schemaPath, Document originDocument){
        try{
            // create a SchemaFactory capable of understanding WXS schemas
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // load a WXS schema, represented by a Schema instance .xsd
            Source schemaFile = new StreamSource(new File(schemaPath));
            Schema schema = factory.newSchema(schemaFile);

            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();

            // validate the DOM tree
            validator.validate(new DOMSource(originDocument));
        } catch (SAXException e) {
            // instance document is invalid!
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
