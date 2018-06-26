package org.nessia.xml.enricher;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;



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
        List<Element> els = transf.getChildren();
        Namespace parentNamespace = origin.getNamespace();
        for(Element e : els){
            Element clone = (Element) e.clone();
            clone.setNamespace(parentNamespace);
            origin.addContent(0,clone);
        }
        return origin;
    }

    private Element append(Element origin, Element transf){
        List<Element> els = transf.getChildren();
        Namespace parentNamespace = origin.getNamespace();
        for(Element e : els){
            Element clone = (Element) e.clone();
            clone.setNamespace(parentNamespace);
            origin.addContent(clone);
        }
        return origin;
    }

    private Element addAttrs(Element origin, Element transf){
        List<Attribute> attrs = transf.getAttributes();
        for(Attribute attr : attrs){
            origin.setAttribute((Attribute)attr.clone());
        }
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
