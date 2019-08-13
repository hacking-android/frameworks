/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetStringParser;
import com.android.org.bouncycastle.asn1.ASN1StreamParser;
import java.io.IOException;
import java.io.InputStream;

class ConstructedOctetStream
extends InputStream {
    private InputStream _currentStream;
    private boolean _first = true;
    private final ASN1StreamParser _parser;

    ConstructedOctetStream(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override
    public int read() throws IOException {
        ASN1OctetStringParser aSN1OctetStringParser;
        if (this._currentStream == null) {
            if (!this._first) {
                return -1;
            }
            aSN1OctetStringParser = (ASN1OctetStringParser)this._parser.readObject();
            if (aSN1OctetStringParser == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = aSN1OctetStringParser.getOctetStream();
        }
        int n;
        while ((n = this._currentStream.read()) < 0) {
            aSN1OctetStringParser = (ASN1OctetStringParser)this._parser.readObject();
            if (aSN1OctetStringParser == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = aSN1OctetStringParser.getOctetStream();
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        Object object = this._currentStream;
        int n3 = -1;
        if (object == null) {
            if (!this._first) {
                return -1;
            }
            object = (ASN1OctetStringParser)this._parser.readObject();
            if (object == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = object.getOctetStream();
        }
        int n4 = 0;
        do {
            int n5;
            if ((n5 = this._currentStream.read(arrby, n + n4, n2 - n4)) >= 0) {
                n4 = n5 = n4 + n5;
                if (n5 != n2) continue;
                return n5;
            }
            object = (ASN1OctetStringParser)this._parser.readObject();
            if (object == null) {
                this._currentStream = null;
                n = n4 < 1 ? n3 : n4;
                return n;
            }
            this._currentStream = object.getOctetStream();
        } while (true);
    }
}

