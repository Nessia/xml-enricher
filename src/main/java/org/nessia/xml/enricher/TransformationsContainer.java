package org.nessia.xml.enricher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;


public class TransformationsContainer {

    private static final Logger LOGGER = Logger.getLogger(TransformationsContainer.class.getName());

    public static TransformationsContainer instanceFromDocument(Document t){
        Element root = t.getRootElement();
        List<Element> nodes = root.getChildren();
        Map<String,Element> nodesMap = new HashMap<String, Element>(nodes.size());
        for(Element node : nodes){
            String nombre = node.getName();
            LOGGER.info("Nodo de transformaciones leido: " + nombre);
            nodesMap.put(nombre, node);
        }

        return new TransformationsContainer(nodesMap);

    }

    public Map<String,Element> nodesMap;

    public TransformationsContainer(Map<String,Element> nodesMap){
        this.nodesMap = nodesMap;
    }

//    public Map<String, Element> getNodesMap() {
//        return nodesMap;
//    }
//
//    public void setNodesMap(Map<String, Element> nodesMap) {
//        this.nodesMap = nodesMap;
//    }


    public boolean contains(String value){
        return nodesMap.containsKey(value);
    }

    public Element getTransformationNode(String key){
        return nodesMap.get(key);
    }
}
