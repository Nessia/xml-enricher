package org.nessia.xml.enricher;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class NodeTransformerMap implements NodeTransformer{

    private static final Logger LOGGER = Logger.getLogger(NodeTransformerMap.class.getName());

    private TransformationsContainer container;

    public NodeTransformerMap() {

    }

    //@Override
    public Node transform(Node n) {
        String nodeName = n.getNodeName();
        if(nodeName == null){
            return n;
        }
        for(TransformationTypes t : TransformationTypes.values()){
            String key = nodeName + "-" + t.toString();
            if(container.contains(key)){
                Node transformations = container.getTransformationNode(key);
                switch(t){
                case APPEND:
                    append(n, transformations);
                    break;
                case PREPEND:
                    prepend(n, transformations);
                    break;
                case ATTRIBUTES:
                    addAttrs(n, transformations);
                    break;
                default:
                    LOGGER.log(Level.WARNING, "Transformation type unknown: " + t.name());
                }
            }
        }

        return n;
    }

    private Node prepend(Node origin, Node transf){
        //TODO
        return origin;
    }

    private Node append(Node origin, Node transf){
        //TODO
        return origin;
    }

    private Node addAttrs(Node origin, Node transf){
        //TODO
        return origin;
    }

    public Document transform(Document origin, Document transformations) {
        this.container = TransformationsContainer.instanceFromDocument(transformations);
        // Go through every node in origin
        origin.getDocumentElement();
        recursiveTransform();

        return origin;
    }

    public Node recursiveTransform(){

    }

}
