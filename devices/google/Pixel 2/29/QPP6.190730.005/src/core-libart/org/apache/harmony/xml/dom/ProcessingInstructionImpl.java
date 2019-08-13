/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.ProcessingInstruction;

public final class ProcessingInstructionImpl
extends LeafNodeImpl
implements ProcessingInstruction {
    private String data;
    private String target;

    ProcessingInstructionImpl(DocumentImpl documentImpl, String string, String string2) {
        super(documentImpl);
        this.target = string;
        this.data = string2;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public String getNodeName() {
        return this.target;
    }

    @Override
    public short getNodeType() {
        return 7;
    }

    @Override
    public String getNodeValue() {
        return this.data;
    }

    @Override
    public String getTarget() {
        return this.target;
    }

    @Override
    public void setData(String string) throws DOMException {
        this.data = string;
    }
}

