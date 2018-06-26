package org.nessia.xml.enricher;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class TransformationsContainer {

    private static final Logger LOGGER = Logger.getLogger(TransformationsContainer.class.getName());

    public static TransformationsContainer instanceFromDocument(Document t){
        int len = t.getChildNodes().getLength();
        Map<String,Node> nodesMap = new HashMap<String, Node>(len);
        for(int i=0; i<len; i++){
            Node node = t.getChildNodes().item(i);
            String nombre = node.getNodeName();
            LOGGER.info("Nodo de transformaciones leido: " + nombre);
            nodesMap.put(nombre, node);
        }

        return new TransformationsContainer(nodesMap);

    }

    public Map<String,Node> nodesMap;

    public TransformationsContainer(Map<String,Node> nodesMap){
        this.nodesMap = nodesMap;
    }

//    public Map<String, Node> getNodesMap() {
//        return nodesMap;
//    }
//
//    public void setNodesMap(Map<String, Node> nodesMap) {
//        this.nodesMap = nodesMap;
//    }


    public boolean contains(String value){
        return nodesMap.containsKey(value);
    }

    public Node getTransformationNode(String key){
        return nodesMap.get(key);
    }
}
