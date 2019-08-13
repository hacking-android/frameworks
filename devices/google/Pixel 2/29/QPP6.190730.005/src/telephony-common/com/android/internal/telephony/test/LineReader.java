/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

class LineReader {
    static final int BUFFER_SIZE = 4096;
    byte[] mBuffer = new byte[4096];
    InputStream mInStream;

    LineReader(InputStream inputStream) {
        this.mInStream = inputStream;
    }

    String getNextLine() {
        return this.getNextLine(false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    String getNextLine(boolean bl) {
        int n;
        block8 : {
            n = 0;
            do {
                block9 : {
                    int n2 = this.mInStream.read();
                    if (n2 < 0) {
                        return null;
                    }
                    if (bl && n2 == 26) break block8;
                    if (n2 == 13 || n2 == 10) break block9;
                    byte[] arrby = this.mBuffer;
                    n2 = (byte)n2;
                    arrby[n] = (byte)n2;
                    ++n;
                    continue;
                }
                if (n == 0) {
                    continue;
                }
                break block8;
                break;
            } while (true);
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.err.println("ATChannel: buffer overflow");
            }
        }
        try {
            return new String(this.mBuffer, 0, n, "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            System.err.println("ATChannel: implausable UnsupportedEncodingException");
            return null;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    String getNextLineCtrlZ() {
        return this.getNextLine(true);
    }
}

