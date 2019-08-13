/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarVerifier;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import sun.security.util.ManifestEntryVerifier;

public class JarInputStream
extends ZipInputStream {
    private final boolean doVerify;
    private JarEntry first;
    private JarVerifier jv;
    private Manifest man;
    private ManifestEntryVerifier mev;
    private boolean tryManifest;

    public JarInputStream(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public JarInputStream(InputStream object, boolean bl) throws IOException {
        super((InputStream)object);
        this.doVerify = bl;
        JarEntry jarEntry = (JarEntry)super.getNextEntry();
        object = jarEntry;
        if (jarEntry != null) {
            object = jarEntry;
            if (jarEntry.getName().equalsIgnoreCase("META-INF/")) {
                object = (JarEntry)super.getNextEntry();
            }
        }
        this.first = this.checkManifest((JarEntry)object);
    }

    private JarEntry checkManifest(JarEntry arrby) throws IOException {
        if (arrby != null && "META-INF/MANIFEST.MF".equalsIgnoreCase(arrby.getName())) {
            this.man = new Manifest();
            arrby = this.getBytes(new BufferedInputStream(this));
            this.man.read(new ByteArrayInputStream(arrby));
            this.closeEntry();
            if (this.doVerify) {
                this.jv = new JarVerifier(arrby);
                this.mev = new ManifestEntryVerifier(this.man);
            }
            return (JarEntry)super.getNextEntry();
        }
        return arrby;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        int n;
        byte[] arrby = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        while ((n = inputStream.read(arrby, 0, arrby.length)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    protected ZipEntry createZipEntry(String string) {
        JarEntry jarEntry = new JarEntry(string);
        Manifest manifest = this.man;
        if (manifest != null) {
            jarEntry.attr = manifest.getAttributes(string);
        }
        return jarEntry;
    }

    public Manifest getManifest() {
        return this.man;
    }

    @Override
    public ZipEntry getNextEntry() throws IOException {
        JarEntry jarEntry;
        Object object = this.first;
        if (object == null) {
            object = (JarEntry)super.getNextEntry();
            jarEntry = object;
            if (this.tryManifest) {
                jarEntry = this.checkManifest((JarEntry)object);
                this.tryManifest = false;
            }
        } else {
            jarEntry = this.first;
            if (((ZipEntry)object).getName().equalsIgnoreCase("META-INF/INDEX.LIST")) {
                this.tryManifest = true;
            }
            this.first = null;
        }
        if ((object = this.jv) != null && jarEntry != null) {
            if (((JarVerifier)object).nothingToVerify()) {
                this.jv = null;
                this.mev = null;
            } else {
                this.jv.beginEntry(jarEntry, this.mev);
            }
        }
        return jarEntry;
    }

    public JarEntry getNextJarEntry() throws IOException {
        return (JarEntry)this.getNextEntry();
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.first == null ? super.read(arrby, n, n2) : -1;
        JarVerifier jarVerifier = this.jv;
        if (jarVerifier != null) {
            jarVerifier.update(n3, arrby, n, n2, this.mev);
        }
        return n3;
    }
}

