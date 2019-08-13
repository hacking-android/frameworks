/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.OIDMap;
import sun.security.x509.UnparseableExtension;

public class CertificateExtensions
implements CertAttrSet<Extension> {
    public static final String IDENT = "x509.info.extensions";
    public static final String NAME = "extensions";
    private static Class[] PARAMS;
    private static final Debug debug;
    private Map<String, Extension> map = Collections.synchronizedMap(new TreeMap());
    private Map<String, Extension> unparseableExtensions;
    private boolean unsupportedCritExt = false;

    static {
        debug = Debug.getInstance("x509");
        PARAMS = new Class[]{Boolean.class, Object.class};
    }

    public CertificateExtensions() {
    }

    public CertificateExtensions(DerInputStream derInputStream) throws IOException {
        this.init(derInputStream);
    }

    private void init(DerInputStream arrderValue) throws IOException {
        arrderValue = arrderValue.getSequence(5);
        for (int i = 0; i < arrderValue.length; ++i) {
            this.parseExtension(new Extension(arrderValue[i]));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseExtension(Extension extension) throws IOException {
        try {
            Object object = OIDMap.getClass(extension.getExtensionId());
            if (object == null) {
                if (extension.isCritical()) {
                    this.unsupportedCritExt = true;
                }
                if (this.map.put(extension.getExtensionId().toString(), extension) == null) {
                    return;
                }
                object = new IOException("Duplicate extensions not allowed");
                throw object;
            }
            object = ((Class)object).getConstructor(PARAMS);
            boolean bl = extension.isCritical();
            byte[] arrby = extension.getExtensionValue();
            if (this.map.put((object = (CertAttrSet)((Constructor)object).newInstance(bl, arrby)).getName(), (Extension)object) == null) {
                return;
            }
            object = new IOException("Duplicate extensions not allowed");
            throw object;
        }
        catch (Exception exception) {
            throw new IOException(exception);
        }
        catch (IOException iOException) {
            throw iOException;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            if (!extension.isCritical()) {
                if (this.unparseableExtensions == null) {
                    this.unparseableExtensions = new TreeMap<String, Extension>();
                }
                this.unparseableExtensions.put(extension.getExtensionId().toString(), new UnparseableExtension(extension, throwable));
                Object object = debug;
                if (object != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error parsing extension: ");
                    stringBuilder.append(extension);
                    ((Debug)object).println(stringBuilder.toString());
                    throwable.printStackTrace();
                    object = new HexDumpEncoder();
                    System.err.println(((CharacterEncoder)object).encodeBuffer(extension.getExtensionValue()));
                }
                return;
            }
            if (throwable instanceof IOException) {
                throw (IOException)throwable;
            }
            throw new IOException(throwable);
        }
    }

    @Override
    public void delete(String string) throws IOException {
        if (this.map.get(string) != null) {
            this.map.remove(string);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No extension found with name ");
        stringBuilder.append(string);
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws CertificateException, IOException {
        this.encode(outputStream, false);
    }

    public void encode(OutputStream outputStream, boolean bl) throws CertificateException, IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        Object object = this.map.values().toArray();
        for (int i = 0; i < ((Object[])object).length; ++i) {
            if (object[i] instanceof CertAttrSet) {
                ((CertAttrSet)object[i]).encode(derOutputStream);
                continue;
            }
            if (object[i] instanceof Extension) {
                ((Extension)object[i]).encode(derOutputStream);
                continue;
            }
            throw new CertificateException("Illegal extension object");
        }
        object = new DerOutputStream();
        ((DerOutputStream)object).write((byte)48, derOutputStream);
        if (!bl) {
            derOutputStream = new DerOutputStream();
            derOutputStream.write(DerValue.createTag((byte)-128, true, (byte)3), (DerOutputStream)object);
            object = derOutputStream;
        }
        outputStream.write(((ByteArrayOutputStream)object).toByteArray());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CertificateExtensions)) {
            return false;
        }
        Object[] arrobject = ((CertificateExtensions)object).getAllExtensions().toArray();
        int n = arrobject.length;
        if (n != this.map.size()) {
            return false;
        }
        String string = null;
        for (int i = 0; i < n; ++i) {
            Object object2 = string;
            if (arrobject[i] instanceof CertAttrSet) {
                object2 = ((CertAttrSet)arrobject[i]).getName();
            }
            Extension extension = (Extension)arrobject[i];
            string = object2;
            if (object2 == null) {
                string = extension.getExtensionId().toString();
            }
            if ((object2 = this.map.get(string)) == null) {
                return false;
            }
            if (((Extension)object2).equals(extension)) continue;
            return false;
        }
        return this.getUnparseableExtensions().equals(((CertificateExtensions)object).getUnparseableExtensions());
    }

    @Override
    public Extension get(String string) throws IOException {
        Object object = this.map.get(string);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No extension found with name ");
        ((StringBuilder)object).append(string);
        throw new IOException(((StringBuilder)object).toString());
    }

    public Collection<Extension> getAllExtensions() {
        return this.map.values();
    }

    @Override
    public Enumeration<Extension> getElements() {
        return Collections.enumeration(this.map.values());
    }

    Extension getExtension(String string) {
        return this.map.get(string);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getNameByOid(ObjectIdentifier objectIdentifier) throws IOException {
        for (String string : this.map.keySet()) {
            if (!this.map.get(string).getExtensionId().equals((Object)objectIdentifier)) continue;
            return string;
        }
        return null;
    }

    public Map<String, Extension> getUnparseableExtensions() {
        Map<String, Extension> map = this.unparseableExtensions;
        if (map == null) {
            return Collections.emptyMap();
        }
        return map;
    }

    public boolean hasUnsupportedCriticalExtension() {
        return this.unsupportedCritExt;
    }

    public int hashCode() {
        return this.map.hashCode() + this.getUnparseableExtensions().hashCode();
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof Extension) {
            this.map.put(string, (Extension)object);
            return;
        }
        throw new IOException("Unknown extension type.");
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}

