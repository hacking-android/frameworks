/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.OIDMap;
import sun.security.x509.X509AttributeName;

public class CRLExtensions {
    private static final Class[] PARAMS = new Class[]{Boolean.class, Object.class};
    private Map<String, Extension> map = Collections.synchronizedMap(new TreeMap());
    private boolean unsupportedCritExt = false;

    public CRLExtensions() {
    }

    public CRLExtensions(DerInputStream derInputStream) throws CRLException {
        this.init(derInputStream);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init(DerInputStream object) throws CRLException {
        Object object2 = object;
        try {
            int n = ((DerInputStream)object).peekByte();
            object = object2;
            if ((n & 192) == 128) {
                object = object2;
                if ((n & 31) == 0) {
                    object = object2.getDerValue().data;
                }
            }
            object = ((DerInputStream)object).getSequence(5);
            n = 0;
            do {
                if (n >= ((Object)object).length) {
                    return;
                }
                object2 = new Extension((DerValue)object[n]);
                this.parseExtension((Extension)object2);
                ++n;
            } while (true);
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Parsing error: ");
            ((StringBuilder)object2).append(iOException.toString());
            throw new CRLException(((StringBuilder)object2).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseExtension(Extension object) throws CRLException {
        try {
            Class<?> class_ = OIDMap.getClass(((Extension)object).getExtensionId());
            if (class_ == null) {
                if (((Extension)object).isCritical()) {
                    this.unsupportedCritExt = true;
                }
                if (this.map.put(((Extension)object).getExtensionId().toString(), (Extension)object) == null) {
                    return;
                }
                object = new CRLException("Duplicate extensions not allowed");
                throw object;
            }
            Object[] arrobject = new Object[]{((Extension)object).isCritical(), ((Extension)object).getExtensionValue()};
            object = (CertAttrSet)class_.getConstructor(PARAMS).newInstance(arrobject);
            if (this.map.put(object.getName(), (Extension)object) == null) {
                return;
            }
            object = new CRLException("Duplicate extensions not allowed");
            throw object;
        }
        catch (Exception exception) {
            throw new CRLException(exception.toString());
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new CRLException(invocationTargetException.getTargetException().getMessage());
        }
    }

    public void delete(String string) {
        this.map.remove(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void encode(OutputStream object, boolean bl) throws CRLException {
        Object object2;
        try {
            DerOutputStream derOutputStream = new DerOutputStream();
            object2 = this.map.values().toArray();
            for (int i = 0; i < ((Object[])object2).length; ++i) {
                if (object2[i] instanceof CertAttrSet) {
                    ((CertAttrSet)object2[i]).encode(derOutputStream);
                    continue;
                }
                if (object2[i] instanceof Extension) {
                    ((Extension)object2[i]).encode(derOutputStream);
                    continue;
                }
                object = new CRLException("Illegal extension object");
                throw object;
            }
            object2 = new DerOutputStream();
            ((DerOutputStream)object2).write((byte)48, derOutputStream);
            derOutputStream = new DerOutputStream();
            if (bl) {
                derOutputStream.write(DerValue.createTag((byte)-128, true, (byte)0), (DerOutputStream)object2);
                object2 = derOutputStream;
            }
            ((OutputStream)object).write(((ByteArrayOutputStream)object2).toByteArray());
            return;
        }
        catch (CertificateException certificateException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Encoding error: ");
            ((StringBuilder)object2).append(certificateException.toString());
            throw new CRLException(((StringBuilder)object2).toString());
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Encoding error: ");
            ((StringBuilder)object).append(iOException.toString());
            throw new CRLException(((StringBuilder)object).toString());
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CRLExtensions)) {
            return false;
        }
        Object[] arrobject = ((CRLExtensions)object).getAllExtensions().toArray();
        int n = arrobject.length;
        if (n != this.map.size()) {
            return false;
        }
        object = null;
        for (int i = 0; i < n; ++i) {
            Object object2 = object;
            if (arrobject[i] instanceof CertAttrSet) {
                object2 = ((CertAttrSet)arrobject[i]).getName();
            }
            Extension extension = (Extension)arrobject[i];
            object = object2;
            if (object2 == null) {
                object = extension.getExtensionId().toString();
            }
            if ((object2 = this.map.get(object)) == null) {
                return false;
            }
            if (((Extension)object2).equals(extension)) continue;
            return false;
        }
        return true;
    }

    public Extension get(String string) {
        block0 : {
            if (!new X509AttributeName(string).getPrefix().equalsIgnoreCase("x509")) break block0;
            string = string.substring(string.lastIndexOf(".") + 1);
        }
        return this.map.get(string);
    }

    public Collection<Extension> getAllExtensions() {
        return this.map.values();
    }

    public Enumeration<Extension> getElements() {
        return Collections.enumeration(this.map.values());
    }

    public boolean hasUnsupportedCriticalExtension() {
        return this.unsupportedCritExt;
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public void set(String string, Object object) {
        this.map.put(string, (Extension)object);
    }

    public String toString() {
        return this.map.toString();
    }
}

