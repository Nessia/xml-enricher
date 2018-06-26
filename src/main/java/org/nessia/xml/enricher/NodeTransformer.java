package org.nessia.xml.enricher;

import org.jdom.Document;
import org.jdom.Element;

public interface NodeTransformer {

    Element transform(Element n);
    Document transform(Document origin, Document transformations);
}
