/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.util.Log;
import android.util.Printer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class FastPrintWriter
extends PrintWriter {
    private final boolean mAutoFlush;
    private final int mBufferLen;
    private final ByteBuffer mBytes;
    private CharsetEncoder mCharset;
    private boolean mIoError;
    private final OutputStream mOutputStream;
    private int mPos;
    private final Printer mPrinter;
    private final String mSeparator;
    private final char[] mText;
    private final Writer mWriter;

    public FastPrintWriter(Printer printer) {
        this(printer, 512);
    }

    public FastPrintWriter(Printer printer, int n) {
        super(new DummyWriter(), true);
        if (printer != null) {
            this.mBufferLen = n;
            this.mText = new char[n];
            this.mBytes = null;
            this.mOutputStream = null;
            this.mWriter = null;
            this.mPrinter = printer;
            this.mAutoFlush = true;
            this.mSeparator = System.lineSeparator();
            this.initDefaultEncoder();
            return;
        }
        throw new NullPointerException("pr is null");
    }

    @UnsupportedAppUsage
    public FastPrintWriter(OutputStream outputStream) {
        this(outputStream, false, 8192);
    }

    public FastPrintWriter(OutputStream outputStream, boolean bl) {
        this(outputStream, bl, 8192);
    }

    public FastPrintWriter(OutputStream outputStream, boolean bl, int n) {
        super(new DummyWriter(), bl);
        if (outputStream != null) {
            this.mBufferLen = n;
            this.mText = new char[n];
            this.mBytes = ByteBuffer.allocate(this.mBufferLen);
            this.mOutputStream = outputStream;
            this.mWriter = null;
            this.mPrinter = null;
            this.mAutoFlush = bl;
            this.mSeparator = System.lineSeparator();
            this.initDefaultEncoder();
            return;
        }
        throw new NullPointerException("out is null");
    }

    public FastPrintWriter(Writer writer) {
        this(writer, false, 8192);
    }

    public FastPrintWriter(Writer writer, boolean bl) {
        this(writer, bl, 8192);
    }

    public FastPrintWriter(Writer writer, boolean bl, int n) {
        super(new DummyWriter(), bl);
        if (writer != null) {
            this.mBufferLen = n;
            this.mText = new char[n];
            this.mBytes = null;
            this.mOutputStream = null;
            this.mWriter = writer;
            this.mPrinter = null;
            this.mAutoFlush = bl;
            this.mSeparator = System.lineSeparator();
            this.initDefaultEncoder();
            return;
        }
        throw new NullPointerException("wr is null");
    }

    private void appendLocked(char c) throws IOException {
        int n;
        int n2 = n = this.mPos;
        if (n >= this.mBufferLen - 1) {
            this.flushLocked();
            n2 = this.mPos;
        }
        this.mText[n2] = c;
        this.mPos = n2 + 1;
    }

    private void appendLocked(String string2, int n, int n2) throws IOException {
        int n3;
        int n4 = this.mBufferLen;
        if (n2 > n4) {
            int n5 = n + n2;
            while (n < n5) {
                int n6 = n + n4;
                n2 = n6 < n5 ? n4 : n5 - n;
                this.appendLocked(string2, n, n2);
                n = n6;
            }
            return;
        }
        int n7 = n3 = this.mPos;
        if (n3 + n2 > n4) {
            this.flushLocked();
            n7 = this.mPos;
        }
        string2.getChars(n, n + n2, this.mText, n7);
        this.mPos = n7 + n2;
    }

    private void appendLocked(char[] arrc, int n, int n2) throws IOException {
        int n3;
        int n4 = this.mBufferLen;
        if (n2 > n4) {
            int n5 = n + n2;
            while (n < n5) {
                int n6 = n + n4;
                n2 = n6 < n5 ? n4 : n5 - n;
                this.appendLocked(arrc, n, n2);
                n = n6;
            }
            return;
        }
        int n7 = n3 = this.mPos;
        if (n3 + n2 > n4) {
            this.flushLocked();
            n7 = this.mPos;
        }
        System.arraycopy(arrc, n, this.mText, n7, n2);
        this.mPos = n7 + n2;
    }

    private void flushBytesLocked() throws IOException {
        int n;
        if (!this.mIoError && (n = this.mBytes.position()) > 0) {
            this.mBytes.flip();
            this.mOutputStream.write(this.mBytes.array(), 0, n);
            this.mBytes.clear();
        }
    }

    private void flushLocked() throws IOException {
        int n = this.mPos;
        if (n > 0) {
            if (this.mOutputStream != null) {
                CharBuffer charBuffer = CharBuffer.wrap(this.mText, 0, n);
                CoderResult coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                while (!this.mIoError) {
                    if (!coderResult.isError()) {
                        if (!coderResult.isOverflow()) break;
                        this.flushBytesLocked();
                        coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                        continue;
                    }
                    throw new IOException(coderResult.toString());
                }
                if (!this.mIoError) {
                    this.flushBytesLocked();
                    this.mOutputStream.flush();
                }
            } else {
                Object object = this.mWriter;
                if (object != null) {
                    if (!this.mIoError) {
                        ((Writer)object).write(this.mText, 0, n);
                        this.mWriter.flush();
                    }
                } else {
                    int n2 = 0;
                    int n3 = this.mSeparator.length();
                    int n4 = this.mPos;
                    int n5 = n2;
                    n = n4;
                    if (n3 < n4) {
                        n = n3;
                        n5 = n2;
                    }
                    while (n5 < n && (n2 = this.mText[this.mPos - 1 - n5]) == ((String)(object = this.mSeparator)).charAt(((String)object).length() - 1 - n5)) {
                        ++n5;
                    }
                    n = this.mPos;
                    if (n5 >= n) {
                        this.mPrinter.println("");
                    } else {
                        this.mPrinter.println(new String(this.mText, 0, n - n5));
                    }
                }
            }
            this.mPos = 0;
        }
    }

    private final void initDefaultEncoder() {
        this.mCharset = Charset.defaultCharset().newEncoder();
        this.mCharset.onMalformedInput(CodingErrorAction.REPLACE);
        this.mCharset.onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    private final void initEncoder(String string2) throws UnsupportedEncodingException {
        try {
            this.mCharset = Charset.forName(string2).newEncoder();
            this.mCharset.onMalformedInput(CodingErrorAction.REPLACE);
        }
        catch (Exception exception) {
            throw new UnsupportedEncodingException(string2);
        }
        this.mCharset.onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    @Override
    public PrintWriter append(CharSequence charSequence, int n, int n2) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "null";
        }
        charSequence = charSequence2.subSequence(n, n2).toString();
        this.write((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean checkError() {
        this.flush();
        Object object = this.lock;
        synchronized (object) {
            return this.mIoError;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void clearError() {
        Object object = this.lock;
        synchronized (object) {
            this.mIoError = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.flushLocked();
                    if (this.mOutputStream != null) {
                        this.mOutputStream.close();
                    } else if (this.mWriter != null) {
                        this.mWriter.close();
                    }
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.flushLocked();
                    if (!this.mIoError) {
                        if (this.mOutputStream != null) {
                            this.mOutputStream.flush();
                        } else if (this.mWriter != null) {
                            this.mWriter.flush();
                        }
                    }
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void print(char c) {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(c);
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public void print(int n) {
        if (n == 0) {
            this.print("0");
        } else {
            super.print(n);
        }
    }

    @Override
    public void print(long l) {
        if (l == 0L) {
            this.print("0");
        } else {
            super.print(l);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void print(String object) {
        Object string2 = object;
        if (object == null) {
            string2 = String.valueOf(null);
        }
        object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked((String)string2, 0, ((String)string2).length());
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void print(char[] arrc) {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(arrc, 0, arrc.length);
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void println() {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(this.mSeparator, 0, this.mSeparator.length());
                    if (this.mAutoFlush) {
                        this.flushLocked();
                    }
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public void println(char c) {
        this.print(c);
        this.println();
    }

    @Override
    public void println(int n) {
        if (n == 0) {
            this.println("0");
        } else {
            super.println(n);
        }
    }

    @Override
    public void println(long l) {
        if (l == 0L) {
            this.println("0");
        } else {
            super.println(l);
        }
    }

    @Override
    public void println(char[] arrc) {
        this.print(arrc);
        this.println();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void setError() {
        Object object = this.lock;
        synchronized (object) {
            this.mIoError = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(int n) {
        Object object = this.lock;
        synchronized (object) {
            char c = (char)n;
            try {
                try {
                    this.appendLocked(c);
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(String string2) {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(string2, 0, string2.length());
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(String string2, int n, int n2) {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(string2, n, n2);
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(char[] arrc, int n, int n2) {
        Object object = this.lock;
        synchronized (object) {
            try {
                try {
                    this.appendLocked(arrc, n, n2);
                }
                catch (IOException iOException) {
                    Log.w("FastPrintWriter", "Write failure", iOException);
                    this.setError();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private static class DummyWriter
    extends Writer {
        private DummyWriter() {
        }

        @Override
        public void close() throws IOException {
            throw new UnsupportedOperationException("Shouldn't be here");
        }

        @Override
        public void flush() throws IOException {
            this.close();
        }

        @Override
        public void write(char[] arrc, int n, int n2) throws IOException {
            this.close();
        }
    }

}

