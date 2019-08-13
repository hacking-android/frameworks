/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.io.OutputStream;

public interface Extension {
    public void encode(OutputStream var1) throws IOException;

    public String getId();

    public byte[] getValue();

    public boolean isCritical();
}

