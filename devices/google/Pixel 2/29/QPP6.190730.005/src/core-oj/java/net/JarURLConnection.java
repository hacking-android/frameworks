/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import sun.net.www.ParseUtil;

public abstract class JarURLConnection
extends URLConnection {
    private String entryName;
    private URL jarFileURL;
    protected URLConnection jarFileURLConnection;

    protected JarURLConnection(URL uRL) throws MalformedURLException {
        super(uRL);
        this.parseSpecs(uRL);
    }

    private void parseSpecs(URL serializable) throws MalformedURLException {
        String string = ((URL)serializable).getFile();
        int n = string.indexOf("!/");
        if (n != -1) {
            this.jarFileURL = new URL(string.substring(0, n));
            this.entryName = null;
            if ((n = n + 1 + 1) != string.length()) {
                this.entryName = string.substring(n, string.length());
                this.entryName = ParseUtil.decode(this.entryName);
            }
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("no !/ found in url spec:");
        ((StringBuilder)serializable).append(string);
        throw new MalformedURLException(((StringBuilder)serializable).toString());
    }

    public Attributes getAttributes() throws IOException {
        Cloneable cloneable = this.getJarEntry();
        cloneable = cloneable != null ? cloneable.getAttributes() : null;
        return cloneable;
    }

    public Certificate[] getCertificates() throws IOException {
        Object object = this.getJarEntry();
        object = object != null ? object.getCertificates() : null;
        return object;
    }

    public String getEntryName() {
        return this.entryName;
    }

    public JarEntry getJarEntry() throws IOException {
        return this.getJarFile().getJarEntry(this.entryName);
    }

    public abstract JarFile getJarFile() throws IOException;

    public URL getJarFileURL() {
        return this.jarFileURL;
    }

    public Attributes getMainAttributes() throws IOException {
        Cloneable cloneable = this.getManifest();
        cloneable = cloneable != null ? cloneable.getMainAttributes() : null;
        return cloneable;
    }

    public Manifest getManifest() throws IOException {
        return this.getJarFile().getManifest();
    }
}

