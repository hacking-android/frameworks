/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.IOException;
import java.io.Serializable;
import java.security.CodeSigner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.Manifest;
import sun.security.jca.Providers;
import sun.security.util.Debug;

public class ManifestEntryVerifier {
    private static final Debug debug = Debug.getInstance("jar");
    private static final char[] hexc = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    HashMap<String, MessageDigest> createdDigests = new HashMap(11);
    ArrayList<MessageDigest> digests = new ArrayList();
    private JarEntry entry;
    private Manifest man;
    ArrayList<byte[]> manifestHashes = new ArrayList();
    private String name = null;
    private CodeSigner[] signers = null;
    private boolean skip = true;

    public ManifestEntryVerifier(Manifest manifest) {
        this.man = manifest;
    }

    static String toHex(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            stringBuffer.append(hexc[arrby[i] >> 4 & 15]);
            stringBuffer.append(hexc[arrby[i] & 15]);
        }
        return stringBuffer.toString();
    }

    public JarEntry getEntry() {
        return this.entry;
    }

    public void setEntry(String object, JarEntry object2) throws IOException {
        this.digests.clear();
        this.manifestHashes.clear();
        this.name = object;
        this.entry = object2;
        this.skip = true;
        this.signers = null;
        object2 = this.man;
        if (object2 != null && object != null) {
            Object object3;
            object2 = object3 = ((Manifest)object2).getAttributes((String)object);
            if (object3 == null) {
                object2 = this.man;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("./");
                ((StringBuilder)object3).append((String)object);
                object2 = object3 = ((Manifest)object2).getAttributes(((StringBuilder)object3).toString());
                if (object3 == null) {
                    object3 = this.man;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("/");
                    ((StringBuilder)object2).append((String)object);
                    object2 = object = ((Manifest)object3).getAttributes(((StringBuilder)object2).toString());
                    if (object == null) {
                        return;
                    }
                }
            }
            for (Map.Entry entry : ((Attributes)object2).entrySet()) {
                object = entry.getKey().toString();
                if (!((String)object).toUpperCase(Locale.ENGLISH).endsWith("-DIGEST")) continue;
                String string = ((String)object).substring(0, ((String)object).length() - 7);
                object = object2 = this.createdDigests.get(string);
                if (object2 == null) {
                    object = object2;
                    object = object2 = MessageDigest.getInstance(string, SunProviderHolder.instance);
                    try {
                        this.createdDigests.put(string, (MessageDigest)object2);
                        object = object2;
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        // empty catch block
                    }
                }
                if (object == null) continue;
                this.skip = false;
                ((MessageDigest)object).reset();
                this.digests.add((MessageDigest)object);
                this.manifestHashes.add(Base64.getMimeDecoder().decode((String)entry.getValue()));
            }
            return;
        }
    }

    public void update(byte by) {
        if (this.skip) {
            return;
        }
        for (int i = 0; i < this.digests.size(); ++i) {
            this.digests.get(i).update(by);
        }
    }

    public void update(byte[] arrby, int n, int n2) {
        if (this.skip) {
            return;
        }
        for (int i = 0; i < this.digests.size(); ++i) {
            this.digests.get(i).update(arrby, n, n2);
        }
    }

    public CodeSigner[] verify(Hashtable<String, CodeSigner[]> serializable, Hashtable<String, CodeSigner[]> arrcodeSigner) throws JarException {
        if (this.skip) {
            return null;
        }
        Object object = this.signers;
        if (object != null) {
            return object;
        }
        for (int i = 0; i < this.digests.size(); ++i) {
            object = this.digests.get(i);
            byte[] arrby = this.manifestHashes.get(i);
            byte[] arrby2 = ((MessageDigest)object).digest();
            Object object2 = debug;
            if (object2 != null) {
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append("Manifest Entry: ");
                ((StringBuilder)object3).append(this.name);
                ((StringBuilder)object3).append(" digest=");
                ((StringBuilder)object3).append(((MessageDigest)object).getAlgorithm());
                ((Debug)object2).println(((StringBuilder)object3).toString());
                object3 = debug;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("  manifest ");
                ((StringBuilder)object2).append(ManifestEntryVerifier.toHex(arrby));
                ((Debug)object3).println(((StringBuilder)object2).toString());
                object3 = debug;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("  computed ");
                ((StringBuilder)object2).append(ManifestEntryVerifier.toHex(arrby2));
                ((Debug)object3).println(((StringBuilder)object2).toString());
                debug.println();
            }
            if (MessageDigest.isEqual(arrby2, arrby)) {
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(((MessageDigest)object).getAlgorithm());
            ((StringBuilder)serializable).append(" digest error for ");
            ((StringBuilder)serializable).append(this.name);
            throw new SecurityException(((StringBuilder)serializable).toString());
        }
        this.signers = arrcodeSigner.remove(this.name);
        if ((arrcodeSigner = this.signers) != null) {
            ((Hashtable)serializable).put((String)this.name, (CodeSigner[])arrcodeSigner);
        }
        return this.signers;
    }

    private static class SunProviderHolder {
        private static final Provider instance = Providers.getSunProvider();

        private SunProviderHolder() {
        }
    }

}

