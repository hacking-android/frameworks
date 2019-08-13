/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.util.encoders.Base64;
import java.io.IOException;
import java.io.InputStream;

public class PEMUtil {
    private final String _footer1;
    private final String _footer2;
    private final String _header1;
    private final String _header2;

    PEMUtil(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----BEGIN ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this._header1 = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("-----BEGIN X509 ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this._header2 = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("-----END ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this._footer1 = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("-----END X509 ");
        stringBuilder.append(string);
        stringBuilder.append("-----");
        this._footer2 = stringBuilder.toString();
    }

    private String readLine(InputStream inputStream) throws IOException {
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        do {
            if ((n = inputStream.read()) != 13 && n != 10 && n >= 0) {
                if (n == 13) continue;
                stringBuffer.append((char)n);
                continue;
            }
            if (n < 0 || stringBuffer.length() != 0) break;
        } while (true);
        if (n < 0) {
            return null;
        }
        return stringBuffer.toString();
    }

    ASN1Sequence readPEMObject(InputStream object) throws IOException {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        while ((string = this.readLine((InputStream)object)) != null && !string.startsWith(this._header1) && !string.startsWith(this._header2)) {
        }
        while ((string = this.readLine((InputStream)object)) != null && !string.startsWith(this._footer1) && !string.startsWith(this._footer2)) {
            stringBuffer.append(string);
        }
        if (stringBuffer.length() != 0) {
            object = new ASN1InputStream(Base64.decode(stringBuffer.toString())).readObject();
            if (object instanceof ASN1Sequence) {
                return (ASN1Sequence)object;
            }
            throw new IOException("malformed PEM data encountered");
        }
        return null;
    }
}

