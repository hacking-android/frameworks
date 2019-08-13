/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

import com.android.org.bouncycastle.util.encoders.Base64;
import com.android.org.bouncycastle.util.io.pem.PemHeader;
import com.android.org.bouncycastle.util.io.pem.PemObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class PemReader
extends BufferedReader {
    private static final String BEGIN = "-----BEGIN ";
    private static final String END = "-----END ";

    public PemReader(Reader reader) {
        super(reader);
    }

    private PemObject loadObject(String charSequence) throws IOException {
        String string;
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(END);
        charSequence2.append((String)charSequence);
        charSequence2 = charSequence2.toString();
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<PemHeader> arrayList = new ArrayList<PemHeader>();
        while ((string = this.readLine()) != null) {
            if (string.indexOf(":") >= 0) {
                int n = string.indexOf(58);
                arrayList.add(new PemHeader(string.substring(0, n), string.substring(n + 1).trim()));
                continue;
            }
            if (string.indexOf((String)charSequence2) != -1) break;
            stringBuffer.append(string.trim());
        }
        if (string != null) {
            return new PemObject((String)charSequence, arrayList, Base64.decode(stringBuffer.toString()));
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(" not found");
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public PemObject readPemObject() throws IOException {
        String string = this.readLine();
        while (string != null && !string.startsWith(BEGIN)) {
            string = this.readLine();
        }
        if (string != null) {
            string = string.substring(BEGIN.length());
            int n = string.indexOf(45);
            string = string.substring(0, n);
            if (n > 0) {
                return this.loadObject(string);
            }
        }
        return null;
    }
}

