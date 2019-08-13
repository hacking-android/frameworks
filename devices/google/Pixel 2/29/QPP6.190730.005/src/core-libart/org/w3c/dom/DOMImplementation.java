/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

public interface DOMImplementation {
    public Document createDocument(String var1, String var2, DocumentType var3) throws DOMException;

    public DocumentType createDocumentType(String var1, String var2, String var3) throws DOMException;

    public Object getFeature(String var1, String var2);

    public boolean hasFeature(String var1, String var2);
}

