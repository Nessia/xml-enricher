package org.nessia.xml.enricher;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface NodeTransformer {

    Node transform(Node n);
    Document transform(Document origin, Document transformations);
}
