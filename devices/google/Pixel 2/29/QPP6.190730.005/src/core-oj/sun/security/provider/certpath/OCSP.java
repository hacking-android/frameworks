/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.cert.CRLReason;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.Extension;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.security.action.GetIntegerAction;
import sun.security.provider.certpath.CertId;
import sun.security.provider.certpath.OCSPRequest;
import sun.security.provider.certpath.OCSPResponse;
import sun.security.util.Debug;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.SerialNumber;
import sun.security.x509.URIName;
import sun.security.x509.X509CertImpl;

public final class OCSP {
    private static final int CONNECT_TIMEOUT;
    private static final int DEFAULT_CONNECT_TIMEOUT = 15000;
    static final ObjectIdentifier NONCE_EXTENSION_OID;
    private static final Debug debug;

    static {
        NONCE_EXTENSION_OID = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 1, 2});
        debug = Debug.getInstance("certpath");
        CONNECT_TIMEOUT = OCSP.initializeTimeout();
    }

    private OCSP() {
    }

    public static RevocationStatus check(X509Certificate serializable, X509Certificate x509Certificate) throws IOException, CertPathValidatorException {
        block4 : {
            Object object = X509CertImpl.toImpl(serializable);
            serializable = OCSP.getResponderURI((X509CertImpl)object);
            if (serializable == null) break block4;
            object = new CertId(x509Certificate, ((X509CertImpl)object).getSerialNumberObject());
            return OCSP.check(Collections.singletonList(object), (URI)serializable, x509Certificate, null, null, Collections.emptyList()).getSingleResponse((CertId)object);
        }
        try {
            serializable = new CertPathValidatorException("No OCSP Responder URI in certificate");
            throw serializable;
        }
        catch (IOException | CertificateException exception) {
            throw new CertPathValidatorException("Exception while encoding OCSPRequest", exception);
        }
    }

    public static RevocationStatus check(X509Certificate x509Certificate, X509Certificate x509Certificate2, URI uRI, X509Certificate x509Certificate3, Date date) throws IOException, CertPathValidatorException {
        return OCSP.check(x509Certificate, x509Certificate2, uRI, x509Certificate3, date, Collections.emptyList());
    }

    public static RevocationStatus check(X509Certificate object, X509Certificate x509Certificate, URI uRI, X509Certificate x509Certificate2, Date date, List<Extension> list) throws IOException, CertPathValidatorException {
        try {
            object = new CertId(x509Certificate, X509CertImpl.toImpl((X509Certificate)object).getSerialNumberObject());
            return OCSP.check(Collections.singletonList(object), uRI, x509Certificate, x509Certificate2, date, list).getSingleResponse((CertId)object);
        }
        catch (IOException | CertificateException exception) {
            throw new CertPathValidatorException("Exception while encoding OCSPRequest", exception);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static OCSPResponse check(List<CertId> list, URI object, X509Certificate arrby, X509Certificate x509Certificate, Date date, List<Extension> object2) throws IOException, CertPathValidatorException {
        void var0_14;
        block40 : {
            block41 : {
                byte[] arrby3;
                byte[] arrby2;
                OCSPRequest oCSPRequest;
                block39 : {
                    int n2;
                    int n;
                    block37 : {
                        oCSPRequest = new OCSPRequest(list, (List<Extension>)object2);
                        Object object3 = oCSPRequest.encodeBytes();
                        arrby3 = null;
                        object2 = ((URI)object).toURL();
                        if (debug != null) {
                            object = debug;
                            arrby2 = new StringBuilder();
                            arrby2.append("connecting to OCSP service at: ");
                            arrby2.append(object2);
                            ((Debug)object).println(arrby2.toString());
                        }
                        arrby2 = (HttpURLConnection)((URL)object2).openConnection();
                        arrby2.setConnectTimeout(CONNECT_TIMEOUT);
                        arrby2.setReadTimeout(CONNECT_TIMEOUT);
                        arrby2.setDoOutput(true);
                        arrby2.setDoInput(true);
                        arrby2.setRequestMethod("POST");
                        arrby2.setRequestProperty("Content-type", "application/ocsp-request");
                        arrby2.setRequestProperty("Content-length", String.valueOf(((byte[])object3).length));
                        object2 = arrby2.getOutputStream();
                        ((OutputStream)object2).write((byte[])object3);
                        ((OutputStream)object2).flush();
                        if (debug != null && arrby2.getResponseCode() != 200) {
                            object3 = debug;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Received HTTP error: ");
                            ((StringBuilder)object).append(arrby2.getResponseCode());
                            ((StringBuilder)object).append(" - ");
                            ((StringBuilder)object).append(arrby2.getResponseMessage());
                            ((Debug)object3).println(((StringBuilder)object).toString());
                        }
                        object = arrby2.getInputStream();
                        n = n2 = arrby2.getContentLength();
                        if (n2 == -1) {
                            n = Integer.MAX_VALUE;
                        }
                        n2 = 2048;
                        if (n > 2048) break block37;
                        n2 = n;
                    }
                    arrby3 = new byte[n2];
                    n2 = 0;
                    while (n2 < n) {
                        block38 : {
                            int n3 = ((InputStream)object).read(arrby3, n2, arrby3.length - n2);
                            if (n3 < 0) break;
                            n2 += n3;
                            arrby2 = arrby3;
                            if (n2 < arrby3.length) break block38;
                            arrby2 = arrby3;
                            if (n2 >= n) break block38;
                            arrby2 = Arrays.copyOf(arrby3, n2 * 2);
                        }
                        arrby3 = arrby2;
                    }
                    arrby2 = Arrays.copyOf(arrby3, n2);
                    if (object == null) break block39;
                    ((InputStream)object).close();
                }
                ((OutputStream)object2).close();
                try {
                    object = new OCSPResponse(arrby2);
                    ((OCSPResponse)object).verify(list, (X509Certificate)arrby, x509Certificate, date, oCSPRequest.getNonce());
                    return object;
                }
                catch (IOException iOException) {
                    throw new CertPathValidatorException(iOException);
                }
                catch (Throwable throwable) {
                    break block40;
                }
                catch (IOException iOException) {
                    arrby = arrby3;
                    break block41;
                }
                catch (Throwable throwable) {
                    break block40;
                }
                catch (IOException iOException) {
                    arrby = null;
                    break block41;
                }
                catch (Throwable throwable) {
                    object = arrby3;
                    break block40;
                }
                catch (IOException iOException) {
                    object = null;
                    arrby = null;
                    break block41;
                }
                catch (Throwable throwable) {
                    object2 = null;
                    object = arrby3;
                    break block40;
                }
                catch (IOException iOException) {
                    object = null;
                    object2 = null;
                    arrby = null;
                }
            }
            try {
                void var0_12;
                arrby = new CertPathValidatorException("Unable to determine revocation status due to network error", (Throwable)var0_12, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                throw arrby;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (object != null) {
            ((InputStream)object).close();
        }
        if (object2 == null) throw var0_14;
        ((OutputStream)object2).close();
        throw var0_14;
        catch (IOException iOException) {
            void var0_19;
            throw new CertPathValidatorException("Exception while encoding OCSPRequest", (Throwable)var0_19);
        }
    }

    public static URI getResponderURI(X509Certificate serializable) {
        try {
            serializable = OCSP.getResponderURI(X509CertImpl.toImpl(serializable));
            return serializable;
        }
        catch (CertificateException certificateException) {
            return null;
        }
    }

    static URI getResponderURI(X509CertImpl iterator) {
        if ((iterator = ((X509CertImpl)((Object)iterator)).getAuthorityInfoAccessExtension()) == null) {
            return null;
        }
        for (AccessDescription accessDescription : ((AuthorityInfoAccessExtension)((Object)iterator)).getAccessDescriptions()) {
            GeneralName generalName;
            if (!accessDescription.getAccessMethod().equals((Object)AccessDescription.Ad_OCSP_Id) || (generalName = accessDescription.getAccessLocation()).getType() != 6) continue;
            return ((URIName)generalName.getName()).getURI();
        }
        return null;
    }

    private static int initializeTimeout() {
        Integer n = AccessController.doPrivileged(new GetIntegerAction("com.sun.security.ocsp.timeout"));
        if (n != null && n >= 0) {
            return n * 1000;
        }
        return 15000;
    }

    public static interface RevocationStatus {
        public CertStatus getCertStatus();

        public CRLReason getRevocationReason();

        public Date getRevocationTime();

        public Map<String, Extension> getSingleExtensions();

        public static enum CertStatus {
            GOOD,
            REVOKED,
            UNKNOWN;
            
        }

    }

}

