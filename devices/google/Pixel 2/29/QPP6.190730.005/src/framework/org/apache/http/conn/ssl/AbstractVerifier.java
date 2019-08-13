/*
 * Decompiled with CFR 0.145.
 */
package org.apache.http.conn.ssl;

import android.annotation.UnsupportedAppUsage;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.AbstractCollection;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;
import org.apache.http.conn.ssl.AndroidDistinguishedNameParser;
import org.apache.http.conn.ssl.X509HostnameVerifier;

@Deprecated
public abstract class AbstractVerifier
implements X509HostnameVerifier {
    @UnsupportedAppUsage
    private static final String[] BAD_COUNTRY_2LDS;
    private static final Pattern IPV4_PATTERN;

    static {
        IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
        BAD_COUNTRY_2LDS = new String[]{"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
        Arrays.sort(BAD_COUNTRY_2LDS);
    }

    public static boolean acceptableCountryWildcard(String string2) {
        int n = string2.length();
        boolean bl = true;
        if (n >= 7 && n <= 9 && string2.charAt(n - 3) == '.') {
            if (Arrays.binarySearch(BAD_COUNTRY_2LDS, string2 = string2.substring(2, n - 3)) >= 0) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public static int countDots(String string2) {
        int n = 0;
        for (int i = 0; i < string2.length(); ++i) {
            int n2 = n;
            if (string2.charAt(i) == '.') {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    public static String[] getCNs(X509Certificate arrstring) {
        List<String> list = new AndroidDistinguishedNameParser(arrstring.getSubjectX500Principal()).getAllMostSpecificFirst("cn");
        if (!list.isEmpty()) {
            arrstring = new String[list.size()];
            list.toArray(arrstring);
            return arrstring;
        }
        return null;
    }

    public static String[] getDNSSubjectAlts(X509Certificate object) {
        LinkedList<String> linkedList = new LinkedList<String>();
        String[] arrstring = null;
        try {
            object = ((X509Certificate)object).getSubjectAlternativeNames();
        }
        catch (CertificateParsingException certificateParsingException) {
            Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", certificateParsingException);
            object = arrstring;
        }
        if (object != null) {
            arrstring = object.iterator();
            while (arrstring.hasNext()) {
                object = (List)arrstring.next();
                if ((Integer)object.get(0) != 2) continue;
                linkedList.add((String)object.get(1));
            }
        }
        if (!linkedList.isEmpty()) {
            object = new String[linkedList.size()];
            linkedList.toArray((T[])object);
            return object;
        }
        return null;
    }

    private static boolean isIPv4Address(String string2) {
        return IPV4_PATTERN.matcher(string2).matches();
    }

    @Override
    public final void verify(String string2, X509Certificate x509Certificate) throws SSLException {
        this.verify(string2, AbstractVerifier.getCNs(x509Certificate), AbstractVerifier.getDNSSubjectAlts(x509Certificate));
    }

    @Override
    public final void verify(String string2, SSLSocket sSLSocket) throws IOException {
        if (string2 != null) {
            this.verify(string2, (X509Certificate)sSLSocket.getSession().getPeerCertificates()[0]);
            return;
        }
        throw new NullPointerException("host to verify is null");
    }

    public final void verify(String string2, String[] object4, String[] object2, boolean bl) throws SSLException {
        Object object3 = new LinkedList<Object>();
        if (object4 != null && ((String[])object4).length > 0 && object4[0] != null) {
            ((LinkedList)object3).add(object4[0]);
        }
        if (object2 != null) {
            for (Object object4 : object2) {
                if (object4 == null) continue;
                ((LinkedList)object3).add(object4);
            }
        }
        if (!((AbstractCollection)object3).isEmpty()) {
            object4 = new StringBuffer();
            object2 = string2.trim().toLowerCase(Locale.ENGLISH);
            boolean bl2 = false;
            Iterator iterator = ((AbstractSequentialList)object3).iterator();
            while (iterator.hasNext()) {
                object3 = ((String)iterator.next()).toLowerCase(Locale.ENGLISH);
                ((StringBuffer)object4).append(" <");
                ((StringBuffer)object4).append((String)object3);
                ((StringBuffer)object4).append('>');
                if (iterator.hasNext()) {
                    ((StringBuffer)object4).append(" OR");
                }
                bl2 = ((String)object3).startsWith("*.");
                boolean bl3 = true;
                int n = bl2 && ((String)object3).indexOf(46, 2) != -1 && AbstractVerifier.acceptableCountryWildcard((String)object3) && !AbstractVerifier.isIPv4Address(string2) ? 1 : 0;
                if (n != 0) {
                    boolean bl4;
                    bl2 = bl4 = ((String)object2).endsWith(((String)object3).substring(1));
                    if (bl4) {
                        bl2 = bl4;
                        if (bl) {
                            bl2 = AbstractVerifier.countDots((String)object2) == AbstractVerifier.countDots((String)object3) ? bl3 : false;
                        }
                    }
                } else {
                    bl2 = ((String)object2).equals(object3);
                }
                if (!bl2) continue;
                break;
            }
            if (bl2) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("hostname in certificate didn't match: <");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("> !=");
            ((StringBuilder)object2).append(object4);
            throw new SSLException(((StringBuilder)object2).toString());
        }
        object4 = new StringBuilder();
        ((StringBuilder)object4).append("Certificate for <");
        ((StringBuilder)object4).append(string2);
        ((StringBuilder)object4).append("> doesn't contain CN or DNS subjectAlt");
        throw new SSLException(((StringBuilder)object4).toString());
    }

    @Override
    public final boolean verify(String string2, SSLSession sSLSession) {
        try {
            this.verify(string2, (X509Certificate)sSLSession.getPeerCertificates()[0]);
            return true;
        }
        catch (SSLException sSLException) {
            return false;
        }
    }
}

