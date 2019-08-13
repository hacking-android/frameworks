/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;
import java.util.Locale;

public class PrintStream
extends FilterOutputStream
implements Appendable,
Closeable {
    private final boolean autoFlush;
    private OutputStreamWriter charOut;
    private Charset charset;
    private boolean closing = false;
    private Formatter formatter;
    private BufferedWriter textOut;
    private boolean trouble = false;

    public PrintStream(File file) throws FileNotFoundException {
        this(false, new FileOutputStream(file));
    }

    public PrintStream(File file, String string) throws FileNotFoundException, UnsupportedEncodingException {
        this(false, PrintStream.toCharset(string), new FileOutputStream(file));
    }

    public PrintStream(OutputStream outputStream) {
        this(outputStream, false);
    }

    public PrintStream(OutputStream outputStream, boolean bl) {
        this(bl, PrintStream.requireNonNull(outputStream, "Null output stream"));
    }

    public PrintStream(OutputStream outputStream, boolean bl, String string) throws UnsupportedEncodingException {
        this(bl, PrintStream.requireNonNull(outputStream, "Null output stream"), PrintStream.toCharset(string));
    }

    public PrintStream(String string) throws FileNotFoundException {
        this(false, new FileOutputStream(string));
    }

    public PrintStream(String string, String string2) throws FileNotFoundException, UnsupportedEncodingException {
        this(false, PrintStream.toCharset(string2), new FileOutputStream(string));
    }

    private PrintStream(boolean bl, OutputStream outputStream) {
        super(outputStream);
        this.autoFlush = bl;
    }

    private PrintStream(boolean bl, OutputStream outputStream, Charset charset) {
        super(outputStream);
        this.autoFlush = bl;
        this.charset = charset;
    }

    private PrintStream(boolean bl, Charset charset, OutputStream outputStream) throws UnsupportedEncodingException {
        this(bl, outputStream, charset);
    }

    private void ensureOpen() throws IOException {
        if (this.out != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private BufferedWriter getTextOut() {
        if (this.textOut == null) {
            Object object = this.charset;
            object = object != null ? new OutputStreamWriter((OutputStream)this, (Charset)object) : new OutputStreamWriter(this);
            this.charOut = object;
            this.textOut = new BufferedWriter(this.charOut);
        }
        return this.textOut;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void newLine() {
        this.ensureOpen();
        BufferedWriter bufferedWriter = this.getTextOut();
        bufferedWriter.newLine();
        bufferedWriter.flushBuffer();
        this.charOut.flushBuffer();
        if (this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : this
    }

    private static <T> T requireNonNull(T t, String string) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(string);
    }

    private static Charset toCharset(String string) throws UnsupportedEncodingException {
        PrintStream.requireNonNull(string, "charsetName");
        try {
            Charset charset = Charset.forName(string);
            return charset;
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException illegalArgumentException) {
            throw new UnsupportedEncodingException(string);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void write(String string) {
        this.ensureOpen();
        BufferedWriter bufferedWriter = this.getTextOut();
        bufferedWriter.write(string);
        bufferedWriter.flushBuffer();
        this.charOut.flushBuffer();
        if (this.autoFlush && string.indexOf(10) >= 0) {
            this.out.flush();
        }
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void write(char[] arrc) {
        this.ensureOpen();
        BufferedWriter bufferedWriter = this.getTextOut();
        bufferedWriter.write(arrc);
        bufferedWriter.flushBuffer();
        this.charOut.flushBuffer();
        if (this.autoFlush) {
            for (int i = 0; i < arrc.length; ++i) {
                if (arrc[i] != '\n') continue;
                this.out.flush();
            }
        }
        // MONITOREXIT : this
    }

    @Override
    public PrintStream append(char c) {
        this.print(c);
        return this;
    }

    @Override
    public PrintStream append(CharSequence charSequence) {
        if (charSequence == null) {
            this.print("null");
        } else {
            this.print(charSequence.toString());
        }
        return this;
    }

    @Override
    public PrintStream append(CharSequence charSequence, int n, int n2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        this.write(charSequence.subSequence(n, n2).toString());
        return this;
    }

    public boolean checkError() {
        if (this.out != null) {
            this.flush();
        }
        if (this.out instanceof PrintStream) {
            return ((PrintStream)this.out).checkError();
        }
        return this.trouble;
    }

    protected void clearError() {
        this.trouble = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        synchronized (this) {
            if (!this.closing) {
                this.closing = true;
                try {
                    if (this.textOut != null) {
                        this.textOut.close();
                    }
                    this.out.close();
                }
                catch (IOException iOException) {
                    this.trouble = true;
                }
                this.textOut = null;
                this.charOut = null;
                this.out = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() {
        synchronized (this) {
            try {
                try {
                    this.ensureOpen();
                    this.out.flush();
                }
                catch (IOException iOException) {
                    this.trouble = true;
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
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PrintStream format(String string, Object ... arrobject) {
        this.ensureOpen();
        if (this.formatter == null || this.formatter.locale() != Locale.getDefault()) {
            Formatter formatter;
            this.formatter = formatter = new Formatter((Appendable)this);
        }
        this.formatter.format(Locale.getDefault(), string, arrobject);
        // MONITOREXIT : this
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PrintStream format(Locale locale, String string, Object ... arrobject) {
        this.ensureOpen();
        if (this.formatter == null || this.formatter.locale() != locale) {
            Formatter formatter;
            this.formatter = formatter = new Formatter(this, locale);
        }
        this.formatter.format(locale, string, arrobject);
        // MONITOREXIT : this
        return this;
    }

    public void print(char c) {
        this.write(String.valueOf(c));
    }

    public void print(double d) {
        this.write(String.valueOf(d));
    }

    public void print(float f) {
        this.write(String.valueOf(f));
    }

    public void print(int n) {
        this.write(String.valueOf(n));
    }

    public void print(long l) {
        this.write(String.valueOf(l));
    }

    public void print(Object object) {
        this.write(String.valueOf(object));
    }

    public void print(String string) {
        String string2 = string;
        if (string == null) {
            string2 = "null";
        }
        this.write(string2);
    }

    public void print(boolean bl) {
        String string = bl ? "true" : "false";
        this.write(string);
    }

    public void print(char[] arrc) {
        this.write(arrc);
    }

    public PrintStream printf(String string, Object ... arrobject) {
        return this.format(string, arrobject);
    }

    public PrintStream printf(Locale locale, String string, Object ... arrobject) {
        return this.format(locale, string, arrobject);
    }

    public void println() {
        this.newLine();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(char c) {
        synchronized (this) {
            this.print(c);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(double d) {
        synchronized (this) {
            this.print(d);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(float f) {
        synchronized (this) {
            this.print(f);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(int n) {
        synchronized (this) {
            this.print(n);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(long l) {
        synchronized (this) {
            this.print(l);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(Object object) {
        object = String.valueOf(object);
        synchronized (this) {
            this.print((String)object);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(String string) {
        synchronized (this) {
            this.print(string);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(boolean bl) {
        synchronized (this) {
            this.print(bl);
            this.newLine();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(char[] arrc) {
        synchronized (this) {
            this.print(arrc);
            this.newLine();
            return;
        }
    }

    protected void setError() {
        this.trouble = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void write(int n) {
        this.ensureOpen();
        this.out.write(n);
        if (n == 10 && this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void write(byte[] arrby, int n, int n2) {
        this.ensureOpen();
        this.out.write(arrby, n, n2);
        if (this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : this
    }
}

