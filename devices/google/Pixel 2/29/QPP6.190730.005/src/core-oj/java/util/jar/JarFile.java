/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Spliterators;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarVerifier;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import sun.misc.IOUtils;
import sun.security.util.Debug;
import sun.security.util.ManifestEntryVerifier;
import sun.security.util.SignatureFileVerifier;

public class JarFile
extends ZipFile {
    private static final char[] CLASSPATH_CHARS = new char[]{'c', 'l', 'a', 's', 's', '-', 'p', 'a', 't', 'h'};
    private static final int[] CLASSPATH_LASTOCC = new int[128];
    private static final int[] CLASSPATH_OPTOSFT = new int[10];
    public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
    private volatile boolean hasCheckedSpecialAttributes;
    private boolean hasClassPathAttribute;
    private JarVerifier jv;
    private boolean jvInitialized;
    private JarEntry manEntry;
    private Manifest manifest;
    private boolean verify;

    static {
        int[] arrn = CLASSPATH_LASTOCC;
        arrn[99] = 1;
        arrn[108] = 2;
        arrn[115] = 5;
        arrn[45] = 6;
        arrn[112] = 7;
        arrn[97] = 8;
        arrn[116] = 9;
        arrn[104] = 10;
        for (int i = 0; i < 9; ++i) {
            JarFile.CLASSPATH_OPTOSFT[i] = 10;
        }
        JarFile.CLASSPATH_OPTOSFT[9] = 1;
    }

    public JarFile(File file) throws IOException {
        this(file, true, 1);
    }

    public JarFile(File file, boolean bl) throws IOException {
        this(file, bl, 1);
    }

    public JarFile(File file, boolean bl, int n) throws IOException {
        super(file, n);
        this.verify = bl;
    }

    public JarFile(String string) throws IOException {
        this(new File(string), true, 1);
    }

    public JarFile(String string, boolean bl) throws IOException {
        this(new File(string), bl, 1);
    }

    private void checkForSpecialAttributes() throws IOException {
        if (this.hasCheckedSpecialAttributes) {
            return;
        }
        byte[] arrby = this.getManEntry();
        if (arrby != null && this.match(CLASSPATH_CHARS, arrby = this.getBytes((ZipEntry)arrby), CLASSPATH_LASTOCC, CLASSPATH_OPTOSFT)) {
            this.hasClassPathAttribute = true;
        }
        this.hasCheckedSpecialAttributes = true;
    }

    private byte[] getBytes(ZipEntry arrby) throws IOException {
        block7 : {
            InputStream inputStream = super.getInputStream((ZipEntry)arrby);
            try {
                arrby = IOUtils.readFully(inputStream, (int)arrby.getSize(), true);
                if (inputStream == null) break block7;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    }
                    throw throwable2;
                }
            }
            inputStream.close();
        }
        return arrby;
    }

    private JarEntry getManEntry() {
        synchronized (this) {
            String[] arrstring;
            block6 : {
                if (this.manEntry != null) break block6;
                this.manEntry = this.getJarEntry(MANIFEST_NAME);
                if (this.manEntry != null || (arrstring = this.getMetaInfEntryNames()) == null) break block6;
                int n = 0;
                do {
                    block7 : {
                        if (n >= arrstring.length) break;
                        if (!MANIFEST_NAME.equals(arrstring[n].toUpperCase(Locale.ENGLISH))) break block7;
                        this.manEntry = this.getJarEntry(arrstring[n]);
                        break;
                    }
                    ++n;
                } while (true);
            }
            arrstring = this.manEntry;
            return arrstring;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Manifest getManifestFromReference() throws IOException {
        synchronized (this) {
            byte[] arrby;
            void var2_7;
            byte[] arrby2 = arrby = this.manifest;
            if (arrby == null) {
                Object object = this.getManEntry();
                byte[] arrby3 = arrby;
                if (object != null) {
                    void var2_6;
                    if (this.verify) {
                        arrby = this.getBytes((ZipEntry)object);
                        object = new ByteArrayInputStream(arrby);
                        Manifest manifest = new Manifest((InputStream)object);
                        if (!this.jvInitialized) {
                            this.jv = object = new JarVerifier(arrby);
                        }
                    } else {
                        Manifest manifest = new Manifest(super.getInputStream((ZipEntry)object));
                    }
                    this.manifest = var2_6;
                }
            }
            return var2_7;
        }
    }

    private native String[] getMetaInfEntryNames();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initializeVerifier() {
        byte[] arrby;
        block14 : {
            arrby = null;
            try {
                String[] arrstring = this.getMetaInfEntryNames();
                if (arrstring == null) break block14;
                for (int i = 0; i < arrstring.length; ++i) {
                    block17 : {
                        Object object;
                        block16 : {
                            JarEntry jarEntry;
                            Object object2;
                            block15 : {
                                object2 = arrstring[i].toUpperCase(Locale.ENGLISH);
                                if ("META-INF/MANIFEST.MF".equals(object2)) break block15;
                                object = arrby;
                                if (!SignatureFileVerifier.isBlockOrSF((String)object2)) break block16;
                            }
                            if ((jarEntry = this.getJarEntry(arrstring[i])) == null) break block17;
                            object2 = arrby;
                            if (arrby == null) {
                                object2 = new ManifestEntryVerifier(this.getManifestFromReference());
                            }
                            arrby = this.getBytes(jarEntry);
                            object = object2;
                            if (arrby != null) {
                                object = object2;
                                if (arrby.length > 0) {
                                    this.jv.beginEntry(jarEntry, (ManifestEntryVerifier)object2);
                                    this.jv.update(arrby.length, arrby, 0, arrby.length, (ManifestEntryVerifier)object2);
                                    this.jv.update(-1, null, 0, 0, (ManifestEntryVerifier)object2);
                                    object = object2;
                                }
                            }
                        }
                        arrby = object;
                        continue;
                    }
                    arrby = new JarException("corrupted jar file");
                    throw arrby;
                }
            }
            catch (IOException iOException) {
                this.jv = null;
                this.verify = false;
                if (JarVerifier.debug == null) break block14;
                JarVerifier.debug.println("jarfile parsing error!");
                iOException.printStackTrace();
            }
        }
        if ((arrby = this.jv) != null) {
            arrby.doneWithMeta();
            if (JarVerifier.debug != null) {
                JarVerifier.debug.println("done with meta!");
            }
            if (this.jv.nothingToVerify()) {
                if (JarVerifier.debug != null) {
                    JarVerifier.debug.println("nothing to verify!");
                }
                this.jv = null;
                this.verify = false;
            }
        }
    }

    private boolean match(char[] arrc, byte[] arrby, int[] arrn, int[] arrn2) {
        int n;
        char c;
        int n2 = arrc.length;
        int n3 = arrby.length;
        block0 : for (int i = 0; i <= n3 - n2; i += Math.max((int)(n + 1 - arrn[c & 127]), (int)arrn2[n])) {
            for (n = n2 - 1; n >= 0; --n) {
                c = (char)arrby[i + n];
                if ((c - 65 | 90 - c) >= 0) {
                    c = (char)(c + 32);
                }
                if (c == arrc[n]) continue;
                continue block0;
            }
            return true;
        }
        return false;
    }

    private void maybeInstantiateVerifier() throws IOException {
        if (this.jv != null) {
            return;
        }
        if (this.verify) {
            String[] arrstring = this.getMetaInfEntryNames();
            if (arrstring != null) {
                for (int i = 0; i < arrstring.length; ++i) {
                    String string = arrstring[i].toUpperCase(Locale.ENGLISH);
                    if (!(string.endsWith(".DSA") || string.endsWith(".RSA") || string.endsWith(".EC") || string.endsWith(".SF"))) {
                        continue;
                    }
                    this.getManifest();
                    return;
                }
            }
            this.verify = false;
        }
    }

    public Enumeration<JarEntry> entries() {
        return new JarEntryIterator();
    }

    @Override
    public ZipEntry getEntry(String object) {
        if ((object = super.getEntry((String)object)) != null) {
            return new JarFileEntry((ZipEntry)object);
        }
        return null;
    }

    @Override
    public InputStream getInputStream(ZipEntry object) throws IOException {
        synchronized (this) {
            block6 : {
                block5 : {
                    this.maybeInstantiateVerifier();
                    if (this.jv != null) break block5;
                    object = super.getInputStream((ZipEntry)object);
                    return object;
                }
                if (this.jvInitialized) break block6;
                this.initializeVerifier();
                this.jvInitialized = true;
                if (this.jv != null) break block6;
                object = super.getInputStream((ZipEntry)object);
                return object;
            }
            Manifest manifest = this.getManifestFromReference();
            JarEntry jarEntry = object instanceof JarFileEntry ? (JarEntry)object : this.getJarEntry(((ZipEntry)object).getName());
            object = new JarVerifier.VerifierStream(manifest, jarEntry, super.getInputStream((ZipEntry)object), this.jv);
            return object;
        }
    }

    public JarEntry getJarEntry(String string) {
        return (JarEntry)this.getEntry(string);
    }

    public Manifest getManifest() throws IOException {
        return this.getManifestFromReference();
    }

    public boolean hasClassPathAttribute() throws IOException {
        this.checkForSpecialAttributes();
        return this.hasClassPathAttribute;
    }

    JarEntry newEntry(ZipEntry zipEntry) {
        return new JarFileEntry(zipEntry);
    }

    public Stream<JarEntry> stream() {
        return StreamSupport.stream(Spliterators.spliterator(new JarEntryIterator(), (long)this.size(), 1297), false);
    }

    private class JarEntryIterator
    implements Enumeration<JarEntry>,
    Iterator<JarEntry> {
        final Enumeration<? extends ZipEntry> e;

        private JarEntryIterator() {
            this.e = JarFile.super.entries();
        }

        @Override
        public boolean hasMoreElements() {
            return this.hasNext();
        }

        @Override
        public boolean hasNext() {
            return this.e.hasMoreElements();
        }

        @Override
        public JarEntry next() {
            ZipEntry zipEntry = this.e.nextElement();
            return new JarFileEntry(zipEntry);
        }

        @Override
        public JarEntry nextElement() {
            return this.next();
        }
    }

    private class JarFileEntry
    extends JarEntry {
        JarFileEntry(ZipEntry zipEntry) {
            super(zipEntry);
        }

        @Override
        public Attributes getAttributes() throws IOException {
            Manifest manifest = JarFile.this.getManifest();
            if (manifest != null) {
                return manifest.getAttributes(this.getName());
            }
            return null;
        }

        @Override
        public Certificate[] getCertificates() {
            try {
                JarFile.this.maybeInstantiateVerifier();
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.certs == null && JarFile.this.jv != null) {
                this.certs = JarFile.this.jv.getCerts(JarFile.this, this);
            }
            Certificate[] arrcertificate = this.certs == null ? null : (Certificate[])this.certs.clone();
            return arrcertificate;
        }

        @Override
        public CodeSigner[] getCodeSigners() {
            try {
                JarFile.this.maybeInstantiateVerifier();
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
            if (this.signers == null && JarFile.this.jv != null) {
                this.signers = JarFile.this.jv.getCodeSigners(JarFile.this, this);
            }
            CodeSigner[] arrcodeSigner = this.signers == null ? null : (CodeSigner[])this.signers.clone();
            return arrcodeSigner;
        }
    }

}

