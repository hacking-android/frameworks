/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import javax.xml.transform.Transformer;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xml.serializer.TransformStateSetter;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface TransformState
extends TransformStateSetter {
    public NodeIterator getContextNodeList();

    public ElemTemplateElement getCurrentElement();

    public Node getCurrentNode();

    public ElemTemplate getCurrentTemplate();

    public Node getMatchedNode();

    public ElemTemplate getMatchedTemplate();

    public Transformer getTransformer();
}

