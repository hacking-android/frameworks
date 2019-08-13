/*
 * Decompiled with CFR 0.145.
 */
package javax.security.cert;

import com.sun.security.cert.internal.x509.X509V1CertImpl;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Date;
import javax.security.cert.Certificate;
import javax.security.cert.CertificateException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.CertificateNotYetValidException;

public abstract class X509Certificate
extends Certificate {
    private static final String DEFAULT_X509_CERT_CLASS = X509V1CertImpl.class.getName();
    private static String X509Provider = AccessController.doPrivileged(new PrivilegedAction<String>(){

        @Override
        public String run() {
            return Security.getProperty(X509Certificate.X509_PROVIDER);
        }
    });
    private static final String X509_PROVIDER = "cert.provider.x509v1";

    private static final X509Certificate getInst(Object object) throws CertificateException {
        CharSequence charSequence;
        Class[] arrclass;
        block11 : {
            block10 : {
                arrclass = X509Provider;
                if (arrclass == null) break block10;
                charSequence = arrclass;
                if (arrclass.length() != 0) break block11;
            }
            charSequence = DEFAULT_X509_CERT_CLASS;
        }
        try {
            block14 : {
                block13 : {
                    block12 : {
                        if (!(object instanceof InputStream)) break block12;
                        arrclass = new Class[]{InputStream.class};
                        break block13;
                    }
                    if (!(object instanceof byte[])) break block14;
                    arrclass = new Class[]{object.getClass()};
                }
                return (X509Certificate)Class.forName((String)charSequence).getConstructor(arrclass).newInstance(object);
            }
            object = new CertificateException("Unsupported argument type");
            throw object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Could not find class method: ");
            ((StringBuilder)charSequence).append(noSuchMethodException.getMessage());
            throw new CertificateException(((StringBuilder)charSequence).toString());
        }
        catch (InvocationTargetException invocationTargetException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("InvocationTargetException: ");
            ((StringBuilder)object).append(invocationTargetException.getTargetException());
            throw new CertificateException(((StringBuilder)object).toString());
        }
        catch (InstantiationException instantiationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Problems instantiating: ");
            ((StringBuilder)object).append(instantiationException);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        catch (IllegalAccessException illegalAccessException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not access class: ");
            ((StringBuilder)object).append(illegalAccessException);
            throw new CertificateException(((StringBuilder)object).toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not find class: ");
            ((StringBuilder)object).append(classNotFoundException);
            throw new CertificateException(((StringBuilder)object).toString());
        }
    }

    public static final X509Certificate getInstance(InputStream inputStream) throws CertificateException {
        return X509Certificate.getInst(inputStream);
    }

    public static final X509Certificate getInstance(byte[] arrby) throws CertificateException {
        return X509Certificate.getInst(arrby);
    }

    public abstract void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;

    public abstract void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException;

    public abstract Principal getIssuerDN();

    public abstract Date getNotAfter();

    public abstract Date getNotBefore();

    public abstract BigInteger getSerialNumber();

    public abstract String getSigAlgName();

    public abstract String getSigAlgOID();

    public abstract byte[] getSigAlgParams();

    public abstract Principal getSubjectDN();

    public abstract int getVersion();

}

