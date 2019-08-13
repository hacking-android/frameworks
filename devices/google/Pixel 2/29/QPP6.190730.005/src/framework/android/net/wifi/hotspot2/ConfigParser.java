/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2;

import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.omadm.PpsMoParser;
import android.net.wifi.hotspot2.pps.Credential;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ConfigParser {
    private static final String BOUNDARY = "boundary=";
    private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ENCODING_BASE64 = "base64";
    private static final String TAG = "ConfigParser";
    private static final String TYPE_CA_CERT = "application/x-x509-ca-cert";
    private static final String TYPE_MULTIPART_MIXED = "multipart/mixed";
    private static final String TYPE_PASSPOINT_PROFILE = "application/x-passpoint-profile";
    private static final String TYPE_PKCS12 = "application/x-pkcs12";
    private static final String TYPE_WIFI_CONFIG = "application/x-wifi-config";

    private static PasspointConfiguration createPasspointConfig(Map<String, byte[]> object) throws IOException {
        Object object2 = object.get(TYPE_PASSPOINT_PROFILE);
        if (object2 != null) {
            if ((object2 = PpsMoParser.parseMoText(new String((byte[])object2))) != null) {
                if (((PasspointConfiguration)object2).getCredential() != null) {
                    byte[] arrby = object.get(TYPE_CA_CERT);
                    if (arrby != null) {
                        try {
                            ((PasspointConfiguration)object2).getCredential().setCaCertificate(ConfigParser.parseCACert(arrby));
                        }
                        catch (CertificateException certificateException) {
                            throw new IOException("Failed to parse CA Certificate");
                        }
                    }
                    if ((object = object.get(TYPE_PKCS12)) != null) {
                        try {
                            object = ConfigParser.parsePkcs12(object);
                            ((PasspointConfiguration)object2).getCredential().setClientPrivateKey((PrivateKey)object.first);
                            ((PasspointConfiguration)object2).getCredential().setClientCertificateChain(((List)object.second).toArray(new X509Certificate[((List)object.second).size()]));
                        }
                        catch (IOException | GeneralSecurityException exception) {
                            throw new IOException("Failed to parse PCKS12 string");
                        }
                    }
                    return object2;
                }
                throw new IOException("Passpoint profile missing credential");
            }
            throw new IOException("Failed to parse Passpoint profile");
        }
        throw new IOException("Missing Passpoint Profile");
    }

    private static X509Certificate parseCACert(byte[] arrby) throws CertificateException {
        return (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(arrby));
    }

    private static Pair<String, String> parseContentType(String charSequence) throws IOException {
        String[] arrstring = charSequence.split(";");
        CharSequence charSequence2 = null;
        if (arrstring.length >= 1) {
            String string2 = arrstring[0].trim();
            charSequence = charSequence2;
            for (int i = 1; i < arrstring.length; ++i) {
                charSequence2 = arrstring[i].trim();
                if (!((String)charSequence2).startsWith(BOUNDARY)) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Ignore Content-Type attribute: ");
                    ((StringBuilder)charSequence2).append(arrstring[i]);
                    Log.d(TAG, ((StringBuilder)charSequence2).toString());
                    continue;
                }
                charSequence2 = ((String)charSequence2).substring(BOUNDARY.length());
                charSequence = charSequence2;
                if (((String)charSequence2).length() <= 1) continue;
                charSequence = charSequence2;
                if (!((String)charSequence2).startsWith("\"")) continue;
                charSequence = charSequence2;
                if (!((String)charSequence2).endsWith("\"")) continue;
                charSequence = ((String)charSequence2).substring(1, ((String)charSequence2).length() - 1);
            }
            return new Pair<String, String>(string2, (String)charSequence);
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Invalid Content-Type: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IOException(((StringBuilder)charSequence2).toString());
    }

    private static MimeHeader parseHeaders(LineNumberReader object) throws IOException {
        MimeHeader mimeHeader = new MimeHeader();
        for (Map.Entry<String, String> entry : ConfigParser.readHeaders((LineNumberReader)((Object)object)).entrySet()) {
            CharSequence charSequence = entry.getKey();
            int n = -1;
            int n2 = ((String)charSequence).hashCode();
            if (n2 != 747297921) {
                if (n2 == 949037134 && ((String)charSequence).equals(CONTENT_TYPE)) {
                    n = 0;
                }
            } else if (((String)charSequence).equals(CONTENT_TRANSFER_ENCODING)) {
                n = 1;
            }
            if (n != 0) {
                if (n != 1) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Ignore header: ");
                    ((StringBuilder)charSequence).append(entry.getKey());
                    Log.d(TAG, ((StringBuilder)charSequence).toString());
                    continue;
                }
                mimeHeader.encodingType = entry.getValue();
                continue;
            }
            Pair<String, String> pair = ConfigParser.parseContentType(entry.getValue());
            mimeHeader.contentType = (String)pair.first;
            mimeHeader.boundary = (String)pair.second;
        }
        return mimeHeader;
    }

    private static Map<String, byte[]> parseMimeMultipartMessage(LineNumberReader object) throws IOException {
        Object object2 = ConfigParser.parseHeaders((LineNumberReader)object);
        if (TextUtils.equals(((MimeHeader)object2).contentType, TYPE_MULTIPART_MIXED)) {
            if (!TextUtils.isEmpty(((MimeHeader)object2).boundary)) {
                if (TextUtils.equals(((MimeHeader)object2).encodingType, ENCODING_BASE64)) {
                    Object object3;
                    while ((object3 = ((LineNumberReader)object).readLine()) != null) {
                        Serializable serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("--");
                        ((StringBuilder)serializable).append(((MimeHeader)object2).boundary);
                        if (!((String)object3).equals(((StringBuilder)serializable).toString())) continue;
                        serializable = new HashMap();
                        do {
                            object3 = ConfigParser.parseMimePart((LineNumberReader)object, ((MimeHeader)object2).boundary);
                            serializable.put(((MimePart)object3).type, ((MimePart)object3).data);
                        } while (!((MimePart)object3).isLast);
                        return serializable;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unexpected EOF before first boundary @ ");
                    ((StringBuilder)object2).append(((LineNumberReader)object).getLineNumber());
                    throw new IOException(((StringBuilder)object2).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected encoding: ");
                ((StringBuilder)object).append(((MimeHeader)object2).encodingType);
                throw new IOException(((StringBuilder)object).toString());
            }
            throw new IOException("Missing boundary string");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid content type: ");
        ((StringBuilder)object).append(((MimeHeader)object2).contentType);
        throw new IOException(((StringBuilder)object).toString());
    }

    private static MimePart parseMimePart(LineNumberReader object, String charSequence) throws IOException {
        MimeHeader mimeHeader = ConfigParser.parseHeaders((LineNumberReader)object);
        if (TextUtils.equals(mimeHeader.encodingType, ENCODING_BASE64)) {
            if (!(TextUtils.equals(mimeHeader.contentType, TYPE_PASSPOINT_PROFILE) || TextUtils.equals(mimeHeader.contentType, TYPE_CA_CERT) || TextUtils.equals(mimeHeader.contentType, TYPE_PKCS12))) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected content type: ");
                ((StringBuilder)object).append(mimeHeader.contentType);
                throw new IOException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = false;
            CharSequence charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("--");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence = ((StringBuilder)charSequence2).toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("--");
            String string2 = ((StringBuilder)charSequence2).toString();
            while ((charSequence2 = ((LineNumberReader)object).readLine()) != null) {
                if (((String)charSequence2).startsWith((String)charSequence)) {
                    if (((String)charSequence2).equals(string2)) {
                        bl = true;
                    }
                    object = new MimePart();
                    ((MimePart)object).type = mimeHeader.contentType;
                    ((MimePart)object).data = Base64.decode(stringBuilder.toString(), 0);
                    ((MimePart)object).isLast = bl;
                    return object;
                }
                stringBuilder.append((String)charSequence2);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unexpected EOF file in body @ ");
            ((StringBuilder)charSequence).append(((LineNumberReader)object).getLineNumber());
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected encoding type: ");
        ((StringBuilder)object).append(mimeHeader.encodingType);
        throw new IOException(((StringBuilder)object).toString());
    }

    public static PasspointConfiguration parsePasspointConfig(String object, byte[] object2) {
        if (!TextUtils.equals((CharSequence)object, TYPE_WIFI_CONFIG)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unexpected MIME type: ");
            ((StringBuilder)object2).append((String)object);
            Log.e(TAG, ((StringBuilder)object2).toString());
            return null;
        }
        try {
            object = new String((byte[])object2, StandardCharsets.ISO_8859_1);
            byte[] arrby = Base64.decode((String)object, 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
            object2 = new InputStreamReader((InputStream)byteArrayInputStream, StandardCharsets.ISO_8859_1);
            object = new LineNumberReader((Reader)object2);
            object = ConfigParser.createPasspointConfig(ConfigParser.parseMimeMultipartMessage((LineNumberReader)object));
            return object;
        }
        catch (IOException | IllegalArgumentException exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to parse installation file: ");
            ((StringBuilder)object).append(exception.getMessage());
            Log.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
    }

    private static Pair<PrivateKey, List<X509Certificate>> parsePkcs12(byte[] object) throws GeneralSecurityException, IOException {
        Object object2 = KeyStore.getInstance("PKCS12");
        object = new ByteArrayInputStream((byte[])object);
        int n = 0;
        ((KeyStore)object2).load((InputStream)object, new char[0]);
        ((ByteArrayInputStream)object).close();
        if (((KeyStore)object2).size() == 1) {
            Certificate[] arrcertificate = ((KeyStore)object2).aliases().nextElement();
            if (arrcertificate != null) {
                PrivateKey privateKey;
                block4 : {
                    privateKey = (PrivateKey)((KeyStore)object2).getKey((String)arrcertificate, null);
                    object = null;
                    if ((arrcertificate = ((KeyStore)object2).getCertificateChain((String)arrcertificate)) != null) {
                        object2 = new ArrayList();
                        int n2 = arrcertificate.length;
                        do {
                            object = object2;
                            if (n >= n2) break block4;
                            object = arrcertificate[n];
                            if (!(object instanceof X509Certificate)) break;
                            object2.add((X509Certificate)object);
                            ++n;
                        } while (true);
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unexpceted certificate type: ");
                        ((StringBuilder)object2).append(object.getClass());
                        throw new IOException(((StringBuilder)object2).toString());
                    }
                }
                return new Pair<PrivateKey, Object>(privateKey, object);
            }
            throw new IOException("No alias found");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected key size: ");
        ((StringBuilder)object).append(((KeyStore)object2).size());
        throw new IOException(((StringBuilder)object).toString());
    }

    private static Map<String, String> readHeaders(LineNumberReader lineNumberReader) throws IOException {
        String string2;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String string3 = null;
        StringBuilder stringBuilder = null;
        while ((string2 = lineNumberReader.readLine()) != null) {
            if (string2.length() != 0 && string2.trim().length() != 0) {
                int n = string2.indexOf(58);
                if (n < 0) {
                    if (stringBuilder != null) {
                        stringBuilder.append(' ');
                        stringBuilder.append(string2.trim());
                        continue;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Bad header line: '");
                    stringBuilder.append(string2);
                    stringBuilder.append("' @ ");
                    stringBuilder.append(lineNumberReader.getLineNumber());
                    throw new IOException(stringBuilder.toString());
                }
                if (!Character.isWhitespace(string2.charAt(0))) {
                    if (string3 != null) {
                        hashMap.put(string3, stringBuilder.toString());
                    }
                    string3 = string2.substring(0, n).trim();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string2.substring(n + 1).trim());
                    continue;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal blank prefix in header line '");
                stringBuilder.append(string2);
                stringBuilder.append("' @ ");
                stringBuilder.append(lineNumberReader.getLineNumber());
                throw new IOException(stringBuilder.toString());
            }
            if (string3 != null) {
                hashMap.put(string3, stringBuilder.toString());
            }
            return hashMap;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Missing line @ ");
        stringBuilder.append(lineNumberReader.getLineNumber());
        throw new IOException(stringBuilder.toString());
    }

    private static class MimeHeader {
        public String boundary = null;
        public String contentType = null;
        public String encodingType = null;

        private MimeHeader() {
        }
    }

    private static class MimePart {
        public byte[] data = null;
        public boolean isLast = false;
        public String type = null;

        private MimePart() {
        }
    }

}

