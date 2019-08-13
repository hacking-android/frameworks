/*
 * Decompiled with CFR 0.145.
 */
package org.xmlpull.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface XmlSerializer {
    public XmlSerializer attribute(String var1, String var2, String var3) throws IOException, IllegalArgumentException, IllegalStateException;

    public void cdsect(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void comment(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void docdecl(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException;

    public XmlSerializer endTag(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException;

    public void entityRef(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void flush() throws IOException;

    public int getDepth();

    public boolean getFeature(String var1);

    public String getName();

    public String getNamespace();

    public String getPrefix(String var1, boolean var2) throws IllegalArgumentException;

    public Object getProperty(String var1);

    public void ignorableWhitespace(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void processingInstruction(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void setFeature(String var1, boolean var2) throws IllegalArgumentException, IllegalStateException;

    public void setOutput(OutputStream var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException;

    public void setOutput(Writer var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public void setPrefix(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException;

    public void setProperty(String var1, Object var2) throws IllegalArgumentException, IllegalStateException;

    public void startDocument(String var1, Boolean var2) throws IOException, IllegalArgumentException, IllegalStateException;

    public XmlSerializer startTag(String var1, String var2) throws IOException, IllegalArgumentException, IllegalStateException;

    public XmlSerializer text(String var1) throws IOException, IllegalArgumentException, IllegalStateException;

    public XmlSerializer text(char[] var1, int var2, int var3) throws IOException, IllegalArgumentException, IllegalStateException;
}

