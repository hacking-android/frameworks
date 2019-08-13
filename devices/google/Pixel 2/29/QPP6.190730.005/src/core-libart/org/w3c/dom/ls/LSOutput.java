/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

import java.io.OutputStream;
import java.io.Writer;

public interface LSOutput {
    public OutputStream getByteStream();

    public Writer getCharacterStream();

    public String getEncoding();

    public String getSystemId();

    public void setByteStream(OutputStream var1);

    public void setCharacterStream(Writer var1);

    public void setEncoding(String var1);

    public void setSystemId(String var1);
}

