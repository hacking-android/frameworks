/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform.sax;

import javax.xml.transform.Templates;
import org.xml.sax.ContentHandler;

public interface TemplatesHandler
extends ContentHandler {
    public String getSystemId();

    public Templates getTemplates();

    public void setSystemId(String var1);
}

