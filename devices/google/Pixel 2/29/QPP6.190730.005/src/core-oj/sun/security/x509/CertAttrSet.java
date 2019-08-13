/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public interface CertAttrSet<T> {
    public void delete(String var1) throws CertificateException, IOException;

    public void encode(OutputStream var1) throws CertificateException, IOException;

    public Object get(String var1) throws CertificateException, IOException;

    public Enumeration<T> getElements();

    public String getName();

    public void set(String var1, Object var2) throws CertificateException, IOException;

    public String toString();
}

