/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.Vector;

interface XSLOutputAttributes {
    public String getDoctypePublic();

    public String getDoctypeSystem();

    public String getEncoding();

    public boolean getIndent();

    public int getIndentAmount();

    public String getMediaType();

    public boolean getOmitXMLDeclaration();

    public String getOutputProperty(String var1);

    public String getOutputPropertyDefault(String var1);

    public String getStandalone();

    public String getVersion();

    public void setCdataSectionElements(Vector var1);

    public void setDoctype(String var1, String var2);

    public void setDoctypePublic(String var1);

    public void setDoctypeSystem(String var1);

    public void setEncoding(String var1);

    public void setIndent(boolean var1);

    public void setMediaType(String var1);

    public void setOmitXMLDeclaration(boolean var1);

    public void setOutputProperty(String var1, String var2);

    public void setOutputPropertyDefault(String var1, String var2);

    public void setStandalone(String var1);

    public void setVersion(String var1);
}

