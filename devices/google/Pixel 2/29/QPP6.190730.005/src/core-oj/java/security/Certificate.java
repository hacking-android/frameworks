/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyException;
import java.security.Principal;
import java.security.PublicKey;

@Deprecated
public interface Certificate {
    public void decode(InputStream var1) throws KeyException, IOException;

    public void encode(OutputStream var1) throws KeyException, IOException;

    public String getFormat();

    public Principal getGuarantor();

    public Principal getPrincipal();

    public PublicKey getPublicKey();

    public String toString(boolean var1);
}

