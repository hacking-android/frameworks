/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Formatter;
import java.util.Locale;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public abstract class LoggingPrintStream
extends PrintStream {
    private final StringBuilder builder = new StringBuilder();
    private CharBuffer decodedChars;
    private CharsetDecoder decoder;
    private ByteBuffer encodedBytes;
    private final Formatter formatter = new Formatter(this.builder, null);

    protected LoggingPrintStream() {
        super(new OutputStream(){

            @Override
            public void write(int n) throws IOException {
                throw new AssertionError();
            }
        });
    }

    private void flush(boolean bl) {
        int n;
        int n2 = this.builder.length();
        int n3 = 0;
        while (n3 < n2 && (n = this.builder.indexOf("\n", n3)) != -1) {
            this.log(this.builder.substring(n3, n));
            n3 = n + 1;
        }
        if (bl) {
            if (n3 < n2) {
                this.log(this.builder.substring(n3));
            }
            this.builder.setLength(0);
        } else {
            this.builder.delete(0, n3);
        }
    }

    @Override
    public PrintStream append(char c) {
        synchronized (this) {
            this.print(c);
            return this;
        }
    }

    @Override
    public PrintStream append(CharSequence charSequence) {
        synchronized (this) {
            this.builder.append(charSequence);
            this.flush(false);
            return this;
        }
    }

    @Override
    public PrintStream append(CharSequence charSequence, int n, int n2) {
        synchronized (this) {
            this.builder.append(charSequence, n, n2);
            this.flush(false);
            return this;
        }
    }

    @Override
    public boolean checkError() {
        return false;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
        synchronized (this) {
            this.flush(true);
            return;
        }
    }

    @Override
    public PrintStream format(String string2, Object ... arrobject) {
        return this.format(Locale.getDefault(), string2, arrobject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PrintStream format(Locale serializable, String string2, Object ... arrobject) {
        synchronized (this) {
            Throwable throwable2;
            if (string2 != null) {
                try {
                    this.formatter.format((Locale)serializable, string2, arrobject);
                    this.flush(false);
                    return this;
                }
                catch (Throwable throwable2) {}
            } else {
                serializable = new NullPointerException("format");
                throw serializable;
            }
            throw throwable2;
        }
    }

    protected abstract void log(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void print(char c) {
        synchronized (this) {
            this.builder.append(c);
            if (c == '\n') {
                this.flush(false);
            }
            return;
        }
    }

    @Override
    public void print(double d) {
        synchronized (this) {
            this.builder.append(d);
            return;
        }
    }

    @Override
    public void print(float f) {
        synchronized (this) {
            this.builder.append(f);
            return;
        }
    }

    @Override
    public void print(int n) {
        synchronized (this) {
            this.builder.append(n);
            return;
        }
    }

    @Override
    public void print(long l) {
        synchronized (this) {
            this.builder.append(l);
            return;
        }
    }

    @Override
    public void print(Object object) {
        synchronized (this) {
            this.builder.append(object);
            this.flush(false);
            return;
        }
    }

    @Override
    public void print(String string2) {
        synchronized (this) {
            this.builder.append(string2);
            this.flush(false);
            return;
        }
    }

    @Override
    public void print(boolean bl) {
        synchronized (this) {
            this.builder.append(bl);
            return;
        }
    }

    @Override
    public void print(char[] arrc) {
        synchronized (this) {
            this.builder.append(arrc);
            this.flush(false);
            return;
        }
    }

    @Override
    public PrintStream printf(String string2, Object ... arrobject) {
        return this.format(string2, arrobject);
    }

    @Override
    public PrintStream printf(Locale locale, String string2, Object ... arrobject) {
        return this.format(locale, string2, arrobject);
    }

    @Override
    public void println() {
        synchronized (this) {
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(char c) {
        synchronized (this) {
            this.builder.append(c);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(double d) {
        synchronized (this) {
            this.builder.append(d);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(float f) {
        synchronized (this) {
            this.builder.append(f);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(int n) {
        synchronized (this) {
            this.builder.append(n);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(long l) {
        synchronized (this) {
            this.builder.append(l);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(Object object) {
        synchronized (this) {
            this.builder.append(object);
            this.flush(true);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void println(String string2) {
        synchronized (this) {
            if (this.builder.length() == 0 && string2 != null) {
                int n;
                int n2 = string2.length();
                int n3 = 0;
                while (n3 < n2 && (n = string2.indexOf(10, n3)) != -1) {
                    this.log(string2.substring(n3, n));
                    n3 = n + 1;
                }
                if (n3 < n2) {
                    this.log(string2.substring(n3));
                }
            } else {
                this.builder.append(string2);
                this.flush(true);
            }
            return;
        }
    }

    @Override
    public void println(boolean bl) {
        synchronized (this) {
            this.builder.append(bl);
            this.flush(true);
            return;
        }
    }

    @Override
    public void println(char[] arrc) {
        synchronized (this) {
            this.builder.append(arrc);
            this.flush(true);
            return;
        }
    }

    @Override
    protected void setError() {
    }

    @Override
    public void write(int n) {
        this.write(new byte[]{(byte)n}, 0, 1);
    }

    @Override
    public void write(byte[] arrby) {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(byte[] arrby, int n, int n2) {
        synchronized (this) {
            void var2_2;
            void var3_3;
            if (this.decoder == null) {
                this.encodedBytes = ByteBuffer.allocate(80);
                this.decodedChars = CharBuffer.allocate(80);
                this.decoder = Charset.defaultCharset().newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            }
            var3_3 = var2_2 + var3_3;
            do {
                CoderResult coderResult;
                if (var2_2 >= var3_3) {
                    this.flush(false);
                    return;
                }
                int n3 = Math.min(this.encodedBytes.remaining(), (int)(var3_3 - var2_2));
                this.encodedBytes.put(arrby, (int)var2_2, n3);
                var2_2 += n3;
                this.encodedBytes.flip();
                do {
                    coderResult = this.decoder.decode(this.encodedBytes, this.decodedChars, false);
                    this.decodedChars.flip();
                    this.builder.append(this.decodedChars);
                    this.decodedChars.clear();
                } while (coderResult.isOverflow());
                this.encodedBytes.compact();
            } while (true);
        }
    }

}

