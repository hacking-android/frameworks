/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public final class OkHostnameVerifier
implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private OkHostnameVerifier() {
    }

    public static List<String> allSubjectAltNames(X509Certificate serializable) {
        List<String> list = OkHostnameVerifier.getSubjectAltNames((X509Certificate)serializable, 7);
        List<String> list2 = OkHostnameVerifier.getSubjectAltNames((X509Certificate)serializable, 2);
        serializable = new ArrayList(list.size() + list2.size());
        serializable.addAll(list);
        serializable.addAll(list2);
        return serializable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<String> getSubjectAltNames(X509Certificate iterator, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            iterator = ((X509Certificate)((Object)iterator)).getSubjectAlternativeNames();
            if (iterator == null) {
                return Collections.emptyList();
            }
            iterator = iterator.iterator();
            do {
                Object object;
                if (!iterator.hasNext()) {
                    return arrayList;
                }
                List list = (List)iterator.next();
                if (list == null || list.size() < 2 || (object = (Integer)list.get(0)) == null || (Integer)object != n || (object = (String)list.get(1)) == null) continue;
                arrayList.add((String)object);
            } while (true);
        }
        catch (CertificateParsingException certificateParsingException) {
            return Collections.emptyList();
        }
    }

    static boolean verifyAsIpAddress(String string) {
        return VERIFY_AS_IP_ADDRESS.matcher(string).matches();
    }

    private boolean verifyHostName(String charSequence, String string) {
        if (charSequence != null && ((String)charSequence).length() != 0 && !((String)charSequence).startsWith(".") && !((String)charSequence).endsWith("..")) {
            if (string != null && string.length() != 0 && !string.startsWith(".") && !string.endsWith("..")) {
                CharSequence charSequence2 = charSequence;
                if (!((String)charSequence).endsWith(".")) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append('.');
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = string;
                if (!string.endsWith(".")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append('.');
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                if (!((String)(charSequence = ((String)charSequence).toLowerCase(Locale.US))).contains("*")) {
                    return ((String)charSequence2).equals(charSequence);
                }
                if (((String)charSequence).startsWith("*.") && ((String)charSequence).indexOf(42, 1) == -1) {
                    if (((String)charSequence2).length() < ((String)charSequence).length()) {
                        return false;
                    }
                    if ("*.".equals(charSequence)) {
                        return false;
                    }
                    if (!((String)charSequence2).endsWith((String)(charSequence = ((String)charSequence).substring(1)))) {
                        return false;
                    }
                    int n = ((String)charSequence2).length() - ((String)charSequence).length();
                    return n <= 0 || ((String)charSequence2).lastIndexOf(46, n - 1) == -1;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean verifyHostName(String string, X509Certificate object) {
        string = string.toLowerCase(Locale.US);
        object = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 2);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            if (!this.verifyHostName(string, (String)object.get(i))) continue;
            return true;
        }
        return false;
    }

    private boolean verifyIpAddress(String string, X509Certificate object) {
        object = OkHostnameVerifier.getSubjectAltNames((X509Certificate)object, 7);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            if (!string.equalsIgnoreCase((String)object.get(i))) continue;
            return true;
        }
        return false;
    }

    public boolean verify(String string, X509Certificate x509Certificate) {
        boolean bl = OkHostnameVerifier.verifyAsIpAddress(string) ? this.verifyIpAddress(string, x509Certificate) : this.verifyHostName(string, x509Certificate);
        return bl;
    }

    @Override
    public boolean verify(String string, SSLSession sSLSession) {
        try {
            boolean bl = this.verify(string, (X509Certificate)sSLSession.getPeerCertificates()[0]);
            return bl;
        }
        catch (SSLException sSLException) {
            return false;
        }
    }
}

