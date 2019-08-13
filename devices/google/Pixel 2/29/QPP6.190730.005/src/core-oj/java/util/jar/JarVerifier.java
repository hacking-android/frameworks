/*
 * Decompiled with CFR 0.145.
 */
package java.util.jar;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import sun.security.util.Debug;
import sun.security.util.ManifestDigester;
import sun.security.util.ManifestEntryVerifier;
import sun.security.util.SignatureFileVerifier;

class JarVerifier {
    static final Debug debug = Debug.getInstance("jar");
    private boolean anyToVerify = true;
    private ByteArrayOutputStream baos;
    private Object csdomain = new Object();
    boolean eagerValidation;
    private Enumeration<String> emptyEnumeration = new Enumeration<String>(){

        @Override
        public boolean hasMoreElements() {
            return false;
        }

        @Override
        public String nextElement() {
            throw new NoSuchElementException();
        }
    };
    private CodeSigner[] emptySigner = new CodeSigner[0];
    private List<CodeSigner[]> jarCodeSigners;
    private URL lastURL;
    private Map<CodeSigner[], CodeSource> lastURLMap;
    private volatile ManifestDigester manDig;
    private List<Object> manifestDigests;
    byte[] manifestRawBytes = null;
    private boolean parsingBlockOrSF = false;
    private boolean parsingMeta = true;
    private ArrayList<SignatureFileVerifier> pendingBlocks;
    private Hashtable<String, byte[]> sigFileData;
    private Hashtable<String, CodeSigner[]> sigFileSigners;
    private ArrayList<CodeSigner[]> signerCache;
    private Map<String, CodeSigner[]> signerMap;
    private Map<CodeSigner[], CodeSource> signerToCodeSource = new HashMap<CodeSigner[], CodeSource>();
    private Map<URL, Map<CodeSigner[], CodeSource>> urlToCodeSourceMap = new HashMap<URL, Map<CodeSigner[], CodeSource>>();
    private Hashtable<String, CodeSigner[]> verifiedSigners;

    public JarVerifier(byte[] arrby) {
        this.manifestRawBytes = arrby;
        this.sigFileSigners = new Hashtable();
        this.verifiedSigners = new Hashtable();
        this.sigFileData = new Hashtable(11);
        this.pendingBlocks = new ArrayList();
        this.baos = new ByteArrayOutputStream();
        this.manifestDigests = new ArrayList<Object>();
    }

    private CodeSigner[] findMatchingSigners(CodeSource arrcodeSigner) {
        int n;
        if (arrcodeSigner instanceof VerifierCodeSource && ((VerifierCodeSource)arrcodeSigner).isSameDomain(this.csdomain)) {
            return ((VerifierCodeSource)arrcodeSigner).getPrivateSigners();
        }
        CodeSource[] arrcodeSource = this.mapSignersToCodeSources(arrcodeSigner.getLocation(), this.getJarCodeSigners(), true);
        CodeSigner[] arrcodeSigner2 = new ArrayList();
        for (n = 0; n < arrcodeSource.length; ++n) {
            arrcodeSigner2.add(arrcodeSource[n]);
        }
        n = arrcodeSigner2.indexOf(arrcodeSigner);
        if (n != -1) {
            arrcodeSigner = arrcodeSigner2 = ((VerifierCodeSource)arrcodeSigner2.get(n)).getPrivateSigners();
            if (arrcodeSigner2 == null) {
                arrcodeSigner = this.emptySigner;
            }
            return arrcodeSigner;
        }
        return null;
    }

    private List<CodeSigner[]> getJarCodeSigners() {
        synchronized (this) {
            ArrayList<CodeSigner[]> arrayList;
            if (this.jarCodeSigners == null) {
                HashSet<CodeSigner[]> hashSet = new HashSet<CodeSigner[]>();
                hashSet.addAll(this.signerMap().values());
                arrayList = new ArrayList<CodeSigner[]>();
                this.jarCodeSigners = arrayList;
                this.jarCodeSigners.addAll(hashSet);
            }
            arrayList = this.jarCodeSigners;
            return arrayList;
        }
    }

    static CodeSource getUnsignedCS(URL uRL) {
        return new VerifierCodeSource(null, uRL, (Certificate[])null);
    }

    static boolean isSigningRelated(String string) {
        return SignatureFileVerifier.isSigningRelated(string);
    }

    private static Certificate[] mapSignersToCertArray(CodeSigner[] arrcodeSigner) {
        if (arrcodeSigner != null) {
            ArrayList<? extends Certificate> arrayList = new ArrayList<Certificate>();
            for (int i = 0; i < arrcodeSigner.length; ++i) {
                arrayList.addAll(arrcodeSigner[i].getSignerCertPath().getCertificates());
            }
            return arrayList.toArray(new Certificate[arrayList.size()]);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CodeSource mapSignersToCodeSource(URL uRL, CodeSigner[] arrcodeSigner) {
        synchronized (this) {
            Object object;
            void var2_2;
            void var3_7;
            void var3_10;
            if (uRL == this.lastURL) {
                Map<CodeSigner[], CodeSource> map = this.lastURLMap;
            } else {
                void var3_6;
                object = this.urlToCodeSourceMap.get(uRL);
                Map<CodeSigner[], CodeSource> map = object;
                if (object == null) {
                    HashMap hashMap = new HashMap();
                    this.urlToCodeSourceMap.put(uRL, hashMap);
                }
                this.lastURLMap = var3_6;
                this.lastURL = uRL;
            }
            Object object2 = object = (CodeSource)var3_7.get(var2_2);
            if (object == null) {
                VerifierCodeSource verifierCodeSource = new VerifierCodeSource(this.csdomain, uRL, (CodeSigner[])var2_2);
                this.signerToCodeSource.put((CodeSigner[])var2_2, verifierCodeSource);
            }
            return var3_10;
        }
    }

    private CodeSource[] mapSignersToCodeSources(URL uRL, List<CodeSigner[]> list, boolean bl) {
        ArrayList<CodeSource> arrayList = new ArrayList<CodeSource>();
        for (int i = 0; i < list.size(); ++i) {
            arrayList.add(this.mapSignersToCodeSource(uRL, list.get(i)));
        }
        if (bl) {
            arrayList.add(this.mapSignersToCodeSource(uRL, null));
        }
        return arrayList.toArray(new CodeSource[arrayList.size()]);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void processEntry(ManifestEntryVerifier var1_1) throws IOException {
        block17 : {
            if (!this.parsingBlockOrSF) {
                var2_5 = var1_1.getEntry();
                if (var2_5 == null) return;
                if (var2_5.signers != null) return;
                var2_5.signers = var1_1.verify(this.verifiedSigners, this.sigFileSigners);
                var2_5.certs = JarVerifier.mapSignersToCertArray(var2_5.signers);
                return;
            }
            try {
                this.parsingBlockOrSF = false;
                if (JarVerifier.debug != null) {
                    JarVerifier.debug.println("processEntry: processing block");
                }
                if ((var1_1 = var1_1.getEntry().getName().toUpperCase(Locale.ENGLISH)).endsWith(".SF")) {
                    var3_12 = var1_1.substring(0, var1_1.length() - 3);
                    var2_6 = this.baos.toByteArray();
                    this.sigFileData.put(var3_12, var2_6);
                    var4_18 = this.pendingBlocks.iterator();
                    while (var4_18.hasNext() != false) {
                        var1_1 = var4_18.next();
                        if (!var1_1.needSignatureFile(var3_12)) continue;
                        if (JarVerifier.debug != null) {
                            JarVerifier.debug.println("processEntry: processing pending block");
                        }
                        var1_1.setSignatureFile(var2_6);
                        var1_1.process(this.sigFileSigners, this.manifestDigests);
                    }
                    return;
                }
                var2_7 = var1_1.substring(0, var1_1.lastIndexOf("."));
                if (this.signerCache == null) {
                    this.signerCache = var3_13 = new ArrayList();
                }
                if (this.manDig != null) ** GOTO lbl-1000
                var3_13 = this.manifestRawBytes;
                // MONITORENTER : var3_13
                if (this.manDig != null) break block17;
                this.manDig = var4_19 = new ManifestDigester(this.manifestRawBytes);
                this.manifestRawBytes = null;
            }
            catch (CertificateException var1_2) {
                var3_14 = JarVerifier.debug;
                if (var3_14 == null) return;
                var2_8 = new StringBuilder();
                var2_8.append("processEntry caught: ");
                var2_8.append(var1_2);
                var3_14.println(var2_8.toString());
                return;
            }
            catch (NoSuchAlgorithmException var1_3) {
                var3_15 = JarVerifier.debug;
                if (var3_15 == null) return;
                var2_9 = new StringBuilder();
                var2_9.append("processEntry caught: ");
                var2_9.append(var1_3);
                var3_15.println(var2_9.toString());
                return;
            }
            catch (SignatureException var2_10) {
                var1_1 = JarVerifier.debug;
                if (var1_1 == null) return;
                var3_16 = new StringBuilder();
                var3_16.append("processEntry caught: ");
                var3_16.append(var2_10);
                var1_1.println(var3_16.toString());
                return;
            }
            catch (IOException var1_4) {
                var2_11 = JarVerifier.debug;
                if (var2_11 == null) return;
                var3_17 = new StringBuilder();
                var3_17.append("processEntry caught: ");
                var3_17.append(var1_4);
                var2_11.println(var3_17.toString());
            }
        }
        // MONITOREXIT : var3_13
lbl-1000: // 2 sources:
        {
            if ((var3_13 = new SignatureFileVerifier(this.signerCache, this.manDig, (String)var1_1, this.baos.toByteArray())).needSignatureFileBytes()) {
                var1_1 = this.sigFileData.get(var2_7);
                if (var1_1 == null) {
                    if (JarVerifier.debug != null) {
                        JarVerifier.debug.println("adding pending block");
                    }
                    this.pendingBlocks.add((SignatureFileVerifier)var3_13);
                    return;
                }
                var3_13.setSignatureFile((byte[])var1_1);
            }
            var3_13.process(this.sigFileSigners, this.manifestDigests);
            return;
        }
    }

    private Map<String, CodeSigner[]> signerMap() {
        synchronized (this) {
            Map<Object, Object> map;
            if (this.signerMap == null) {
                map = new Map<Object, Object>(this.verifiedSigners.size() + this.sigFileSigners.size());
                this.signerMap = map;
                this.signerMap.putAll(this.verifiedSigners);
                this.signerMap.putAll(this.sigFileSigners);
            }
            map = this.signerMap;
            return map;
        }
    }

    private Enumeration<String> unsignedEntryNames(JarFile jarFile) {
        Map<String, CodeSigner[]> map = this.signerMap();
        return new Enumeration<String>(jarFile.entries(), map){
            String name;
            final /* synthetic */ Enumeration val$entries;
            final /* synthetic */ Map val$map;
            {
                this.val$entries = enumeration;
                this.val$map = map;
            }

            @Override
            public boolean hasMoreElements() {
                if (this.name != null) {
                    return true;
                }
                while (this.val$entries.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry)this.val$entries.nextElement();
                    String string = zipEntry.getName();
                    if (zipEntry.isDirectory() || JarVerifier.isSigningRelated(string) || this.val$map.get(string) != null) continue;
                    this.name = string;
                    return true;
                }
                return false;
            }

            @Override
            public String nextElement() {
                if (this.hasMoreElements()) {
                    String string = this.name;
                    this.name = null;
                    return string;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public void beginEntry(JarEntry jarEntry, ManifestEntryVerifier manifestEntryVerifier) throws IOException {
        Object object;
        if (jarEntry == null) {
            return;
        }
        Object object2 = debug;
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("beginEntry ");
            ((StringBuilder)object).append(jarEntry.getName());
            ((Debug)object2).println(((StringBuilder)object).toString());
        }
        object2 = jarEntry.getName();
        if (this.parsingMeta && (((String)(object = ((String)object2).toUpperCase(Locale.ENGLISH))).startsWith("META-INF/") || ((String)object).startsWith("/META-INF/"))) {
            if (jarEntry.isDirectory()) {
                manifestEntryVerifier.setEntry(null, jarEntry);
                return;
            }
            if (!((String)object).equals("META-INF/MANIFEST.MF") && !((String)object).equals("META-INF/INDEX.LIST")) {
                if (SignatureFileVerifier.isBlockOrSF((String)object)) {
                    this.parsingBlockOrSF = true;
                    this.baos.reset();
                    manifestEntryVerifier.setEntry(null, jarEntry);
                    return;
                }
            } else {
                return;
            }
        }
        if (this.parsingMeta) {
            this.doneWithMeta();
        }
        if (jarEntry.isDirectory()) {
            manifestEntryVerifier.setEntry(null, jarEntry);
            return;
        }
        object = object2;
        if (((String)object2).startsWith("./")) {
            object = ((String)object2).substring(2);
        }
        object2 = object;
        if (((String)object).startsWith("/")) {
            object2 = ((String)object).substring(1);
        }
        if (this.sigFileSigners.get(object2) == null && this.verifiedSigners.get(object2) == null) {
            manifestEntryVerifier.setEntry(null, jarEntry);
            return;
        }
        manifestEntryVerifier.setEntry((String)object2, jarEntry);
    }

    void doneWithMeta() {
        this.parsingMeta = false;
        this.anyToVerify = this.sigFileSigners.isEmpty() ^ true;
        this.baos = null;
        this.sigFileData = null;
        this.pendingBlocks = null;
        this.signerCache = null;
        this.manDig = null;
        if (this.sigFileSigners.containsKey("META-INF/MANIFEST.MF")) {
            CodeSigner[] arrcodeSigner = this.sigFileSigners.remove("META-INF/MANIFEST.MF");
            this.verifiedSigners.put("META-INF/MANIFEST.MF", arrcodeSigner);
        }
    }

    public Enumeration<JarEntry> entries2(final JarFile jarFile, final Enumeration<? extends ZipEntry> enumeration) {
        final HashMap<String, CodeSigner[]> hashMap = new HashMap<String, CodeSigner[]>();
        hashMap.putAll(this.signerMap());
        return new Enumeration<JarEntry>(){
            JarEntry entry;
            Enumeration<String> signers = null;

            @Override
            public boolean hasMoreElements() {
                Object object;
                if (this.entry != null) {
                    return true;
                }
                while (enumeration.hasMoreElements()) {
                    object = (ZipEntry)enumeration.nextElement();
                    if (JarVerifier.isSigningRelated(((ZipEntry)object).getName())) continue;
                    this.entry = jarFile.newEntry((ZipEntry)object);
                    return true;
                }
                if (this.signers == null) {
                    this.signers = Collections.enumeration(hashMap.keySet());
                }
                if (this.signers.hasMoreElements()) {
                    object = this.signers.nextElement();
                    this.entry = jarFile.newEntry(new ZipEntry((String)object));
                    return true;
                }
                return false;
            }

            @Override
            public JarEntry nextElement() {
                if (this.hasMoreElements()) {
                    JarEntry jarEntry = this.entry;
                    hashMap.remove(jarEntry.getName());
                    this.entry = null;
                    return jarEntry;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Enumeration<String> entryNames(final JarFile enumeration, CodeSource[] arrcodeSource) {
        synchronized (this) {
            void var2_2;
            final Iterator<Map.Entry<String, CodeSigner[]>> iterator = this.signerMap().entrySet().iterator();
            boolean bl = false;
            final ArrayList<CodeSigner[]> arrayList = new ArrayList<CodeSigner[]>(((void)var2_2).length);
            for (int i = 0; i < ((void)var2_2).length; ++i) {
                CodeSigner[] arrcodeSigner = this.findMatchingSigners((CodeSource)var2_2[i]);
                if (arrcodeSigner != null) {
                    if (arrcodeSigner.length > 0) {
                        arrayList.add(arrcodeSigner);
                        continue;
                    }
                    bl = true;
                    continue;
                }
                bl = true;
            }
            enumeration = bl ? this.unsignedEntryNames((JarFile)((Object)enumeration)) : this.emptyEnumeration;
            return new Enumeration<String>(){
                String name;

                @Override
                public boolean hasMoreElements() {
                    if (this.name != null) {
                        return true;
                    }
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        if (!arrayList.contains(entry.getValue())) continue;
                        this.name = (String)entry.getKey();
                        return true;
                    }
                    if (enumeration.hasMoreElements()) {
                        this.name = (String)enumeration.nextElement();
                        return true;
                    }
                    return false;
                }

                @Override
                public String nextElement() {
                    if (this.hasMoreElements()) {
                        String string = this.name;
                        this.name = null;
                        return string;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
    }

    @Deprecated
    public Certificate[] getCerts(String string) {
        return JarVerifier.mapSignersToCertArray(this.getCodeSigners(string));
    }

    public Certificate[] getCerts(JarFile jarFile, JarEntry jarEntry) {
        return JarVerifier.mapSignersToCertArray(this.getCodeSigners(jarFile, jarEntry));
    }

    public CodeSigner[] getCodeSigners(String string) {
        return this.verifiedSigners.get(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CodeSigner[] getCodeSigners(JarFile closeable, JarEntry arrby) {
        String string = arrby.getName();
        if (!this.eagerValidation) return this.getCodeSigners(string);
        if (this.sigFileSigners.get(string) == null) return this.getCodeSigners(string);
        try {
            closeable = ((JarFile)closeable).getInputStream((ZipEntry)arrby);
            arrby = new byte[1024];
            int n = arrby.length;
            do {
                if (n == -1) {
                    ((InputStream)closeable).close();
                    return this.getCodeSigners(string);
                }
                n = ((InputStream)closeable).read(arrby, 0, arrby.length);
            } while (true);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return this.getCodeSigners(string);
    }

    public CodeSource getCodeSource(URL uRL, String string) {
        return this.mapSignersToCodeSource(uRL, this.signerMap().get(string));
    }

    public CodeSource getCodeSource(URL uRL, JarFile jarFile, JarEntry jarEntry) {
        return this.mapSignersToCodeSource(uRL, this.getCodeSigners(jarFile, jarEntry));
    }

    public CodeSource[] getCodeSources(JarFile arrcodeSource, URL uRL) {
        synchronized (this) {
            boolean bl = this.unsignedEntryNames((JarFile)arrcodeSource).hasMoreElements();
            arrcodeSource = this.mapSignersToCodeSources(uRL, this.getJarCodeSigners(), bl);
            return arrcodeSource;
        }
    }

    public List<Object> getManifestDigests() {
        synchronized (this) {
            List<Object> list = Collections.unmodifiableList(this.manifestDigests);
            return list;
        }
    }

    boolean nothingToVerify() {
        return this.anyToVerify ^ true;
    }

    public void setEagerValidation(boolean bl) {
        this.eagerValidation = bl;
    }

    public void update(int n, ManifestEntryVerifier manifestEntryVerifier) throws IOException {
        if (n != -1) {
            if (this.parsingBlockOrSF) {
                this.baos.write(n);
            } else {
                manifestEntryVerifier.update((byte)n);
            }
        } else {
            this.processEntry(manifestEntryVerifier);
        }
    }

    public void update(int n, byte[] arrby, int n2, int n3, ManifestEntryVerifier manifestEntryVerifier) throws IOException {
        if (n != -1) {
            if (this.parsingBlockOrSF) {
                this.baos.write(arrby, n2, n);
            } else {
                manifestEntryVerifier.update(arrby, n2, n);
            }
        } else {
            this.processEntry(manifestEntryVerifier);
        }
    }

    private static class VerifierCodeSource
    extends CodeSource {
        private static final long serialVersionUID = -9047366145967768825L;
        Object csdomain;
        Certificate[] vcerts;
        URL vlocation;
        CodeSigner[] vsigners;

        VerifierCodeSource(Object object, URL uRL, CodeSigner[] arrcodeSigner) {
            super(uRL, arrcodeSigner);
            this.csdomain = object;
            this.vlocation = uRL;
            this.vsigners = arrcodeSigner;
        }

        VerifierCodeSource(Object object, URL uRL, Certificate[] arrcertificate) {
            super(uRL, arrcertificate);
            this.csdomain = object;
            this.vlocation = uRL;
            this.vcerts = arrcertificate;
        }

        private Certificate[] getPrivateCertificates() {
            return this.vcerts;
        }

        private CodeSigner[] getPrivateSigners() {
            return this.vsigners;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof VerifierCodeSource) {
                Serializable serializable = (VerifierCodeSource)object;
                if (this.isSameDomain(((VerifierCodeSource)serializable).csdomain)) {
                    if (((VerifierCodeSource)serializable).vsigners == this.vsigners && ((VerifierCodeSource)serializable).vcerts == this.vcerts) {
                        object = ((VerifierCodeSource)serializable).vlocation;
                        if (object != null) {
                            return ((URL)object).equals(this.vlocation);
                        }
                        serializable = this.vlocation;
                        if (serializable != null) {
                            return ((URL)serializable).equals(object);
                        }
                        return true;
                    }
                    return false;
                }
            }
            return Object.super.equals(object);
        }

        boolean isSameDomain(Object object) {
            boolean bl = this.csdomain == object;
            return bl;
        }
    }

    static class VerifierStream
    extends InputStream {
        private InputStream is;
        private JarVerifier jv;
        private ManifestEntryVerifier mev;
        private long numLeft;

        VerifierStream(Manifest manifest, JarEntry jarEntry, InputStream inputStream, JarVerifier jarVerifier) throws IOException {
            if (inputStream != null) {
                this.is = inputStream;
                this.jv = jarVerifier;
                this.mev = new ManifestEntryVerifier(manifest);
                this.jv.beginEntry(jarEntry, this.mev);
                this.numLeft = jarEntry.getSize();
                if (this.numLeft == 0L) {
                    this.jv.update(-1, this.mev);
                }
                return;
            }
            throw new NullPointerException("is == null");
        }

        @Override
        public int available() throws IOException {
            InputStream inputStream = this.is;
            if (inputStream != null) {
                return inputStream.available();
            }
            throw new IOException("stream closed");
        }

        @Override
        public void close() throws IOException {
            InputStream inputStream = this.is;
            if (inputStream != null) {
                inputStream.close();
            }
            this.is = null;
            this.mev = null;
            this.jv = null;
        }

        @Override
        public int read() throws IOException {
            InputStream inputStream = this.is;
            if (inputStream != null) {
                if (this.numLeft > 0L) {
                    int n = inputStream.read();
                    this.jv.update(n, this.mev);
                    --this.numLeft;
                    if (this.numLeft == 0L) {
                        this.jv.update(-1, this.mev);
                    }
                    return n;
                }
                return -1;
            }
            throw new IOException("stream closed");
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if (this.is != null) {
                long l = this.numLeft;
                int n3 = n2;
                if (l > 0L) {
                    n3 = n2;
                    if (l < (long)n2) {
                        n3 = (int)l;
                    }
                }
                if (this.numLeft > 0L) {
                    n2 = this.is.read(arrby, n, n3);
                    this.jv.update(n2, arrby, n, n3, this.mev);
                    this.numLeft -= (long)n2;
                    if (this.numLeft == 0L) {
                        this.jv.update(-1, arrby, n, n3, this.mev);
                    }
                    return n2;
                }
                return -1;
            }
            throw new IOException("stream closed");
        }
    }

}

