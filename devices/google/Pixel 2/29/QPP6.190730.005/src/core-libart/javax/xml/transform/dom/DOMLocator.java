/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.dom;

import javax.xml.transform.SourceLocator;
import org.w3c.dom.Node;

public interface DOMLocator
extends SourceLocator {
    public Node getOriginatingNode();
}

