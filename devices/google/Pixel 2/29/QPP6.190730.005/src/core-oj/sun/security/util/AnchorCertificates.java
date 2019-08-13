/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import sun.security.util.Debug;
import sun.security.x509.X509CertImpl;

public class AnchorCertificates {
    private static final String HASH = "SHA-256";
    private static HashSet<String> certs;
    private static final Debug debug;

    static {
        debug = Debug.getInstance("certpath");
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public Void run() {
                Object object = new File(System.getProperty("java.home"), "lib/security/cacerts");
                KeyStore keyStore = KeyStore.getInstance("JKS");
                FileInputStream fileInputStream = new FileInputStream((File)object);
                keyStore.load(fileInputStream, null);
                object = new HashSet();
                certs = (HashSet)object;
                object = keyStore.aliases();
                while (object.hasMoreElements()) {
                    Object object2 = (String)object.nextElement();
                    if (!((String)object2).contains(" [jdk")) continue;
                    object2 = (X509Certificate)keyStore.getCertificate((String)object2);
                    certs.add(X509CertImpl.getFingerprint(AnchorCertificates.HASH, (X509Certificate)object2));
                }
                fileInputStream.close();
                return null;
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        try {
                            fileInputStream.close();
                            throw throwable2;
                        }
                        catch (Throwable throwable3) {
                            try {
                                throwable.addSuppressed(throwable3);
                                throw throwable2;
                            }
                            catch (Exception exception) {
                                if (debug != null) {
                                    debug.println("Error parsing cacerts");
                                }
                                exception.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            }
        });
    }

    private AnchorCertificates() {
    }

    public static boolean contains(X509Certificate x509Certificate) {
        Object object = X509CertImpl.getFingerprint(HASH, x509Certificate);
        boolean bl = certs.contains(object);
        if (bl && (object = debug) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AnchorCertificate.contains: matched ");
            stringBuilder.append(x509Certificate.getSubjectDN());
            ((Debug)object).println(stringBuilder.toString());
        }
        return bl;
    }

}

