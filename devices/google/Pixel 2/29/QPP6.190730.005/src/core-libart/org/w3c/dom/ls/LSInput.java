/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

import java.io.InputStream;
import java.io.Reader;

public interface LSInput {
    public String getBaseURI();

    public InputStream getByteStream();

    public boolean getCertifiedText();

    public Reader getCharacterStream();

    public String getEncoding();

    public String getPublicId();

    public String getStringData();

    public String getSystemId();

    public void setBaseURI(String var1);

    public void setByteStream(InputStream var1);

    public void setCertifiedText(boolean var1);

    public void setCharacterStream(Reader var1);

    public void setEncoding(String var1);

    public void setPublicId(String var1);

    public void setStringData(String var1);

    public void setSystemId(String var1);
}

