package org.nessia.xml.enricher;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;



public class NodeTransformerMap implements NodeTransformer{

    private static final Logger LOGGER = Logger.getLogger(NodeTransformerMap.class.getName());

    private TransformationsContainer container;

    public NodeTransformerMap() {

    }

    //@Override
    public Element transform(Element n) {
        String nodeName = n.getName();
        if(nodeName == null){
            return n;
        }
        for(TransformationTypes t : TransformationTypes.values()){
            String key = nodeName + "-" + t.toString();
            if(container.contains(key)){
                Element transformations = container.getTransformationNode(key);
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

    private Element prepend(Element origin, Element transf){
        //TODO
        return origin;
    }

    private Element append(Element origin, Element transf){
        //TODO
        return origin;
    }

    private Element addAttrs(Element origin, Element transf){
        //TODO
        return origin;
    }

    public Document transform(Document origin, Document transformations) {
        this.container = TransformationsContainer.instanceFromDocument(transformations);
        // Go through every node in origin
        Element root = origin.getRootElement();

        recursiveTransform(root);

        return origin;
    }

    public void recursiveTransform(Element node){
        transform(node);
        List<Element> children = node.getChildren();
        for(Element child : children){
            recursiveTransform(child);
        }
    }

}
