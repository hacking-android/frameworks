/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessController;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;
import sun.security.action.GetPropertyAction;

public class PrintWriter
extends Writer {
    private final boolean autoFlush;
    private Formatter formatter;
    private final String lineSeparator;
    protected Writer out;
    private PrintStream psOut = null;
    private boolean trouble = false;

    public PrintWriter(File file) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))), false);
    }

    public PrintWriter(File file, String string) throws FileNotFoundException, UnsupportedEncodingException {
        this(PrintWriter.toCharset(string), file);
    }

    public PrintWriter(OutputStream outputStream) {
        this(outputStream, false);
    }

    public PrintWriter(OutputStream outputStream, boolean bl) {
        this(new BufferedWriter(new OutputStreamWriter(outputStream)), bl);
        if (outputStream instanceof PrintStream) {
            this.psOut = (PrintStream)outputStream;
        }
    }

    public PrintWriter(Writer writer) {
        this(writer, false);
    }

    public PrintWriter(Writer writer, boolean bl) {
        super(writer);
        this.out = writer;
        this.autoFlush = bl;
        this.lineSeparator = AccessController.doPrivileged(new GetPropertyAction("line.separator"));
    }

    public PrintWriter(String string) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(string))), false);
    }

    public PrintWriter(String string, String string2) throws FileNotFoundException, UnsupportedEncodingException {
        this(PrintWriter.toCharset(string2), new File(string));
    }

    private PrintWriter(Charset charset, File file) throws FileNotFoundException {
        this(new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset)), false);
    }

    private void ensureOpen() throws IOException {
        if (this.out != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void newLine() {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        this.out.write(this.lineSeparator);
        if (this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : object
        return;
    }

    private static Charset toCharset(String string) throws UnsupportedEncodingException {
        Objects.requireNonNull(string, "charsetName");
        try {
            Charset charset = Charset.forName(string);
            return charset;
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException illegalArgumentException) {
            throw new UnsupportedEncodingException(string);
        }
    }

    @Override
    public PrintWriter append(char c) {
        this.write(c);
        return this;
    }

    @Override
    public PrintWriter append(CharSequence charSequence) {
        if (charSequence == null) {
            this.write("null");
        } else {
            this.write(charSequence.toString());
        }
        return this;
    }

    @Override
    public PrintWriter append(CharSequence charSequence, int n, int n2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        this.write(charSequence.subSequence(n, n2).toString());
        return this;
    }

    public boolean checkError() {
        Closeable closeable;
        if (this.out != null) {
            this.flush();
        }
        if ((closeable = this.out) instanceof PrintWriter) {
            return ((PrintWriter)closeable).checkError();
        }
        closeable = this.psOut;
        if (closeable != null) {
            return ((PrintStream)closeable).checkError();
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
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void close() {
        block5 : {
            try {
                Object object = this.lock;
                // MONITORENTER : object
                if (this.out != null) break block5;
            }
            catch (IOException iOException) {
                this.trouble = true;
            }
            // MONITOREXIT : object
            return;
        }
        this.out.close();
        this.out = null;
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void flush() {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
        }
        this.ensureOpen();
        this.out.flush();
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PrintWriter format(String string, Object ... arrobject) {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return this;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        if (this.formatter == null || this.formatter.locale() != Locale.getDefault()) {
            Formatter formatter;
            this.formatter = formatter = new Formatter(this);
        }
        this.formatter.format(Locale.getDefault(), string, arrobject);
        if (this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : object
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PrintWriter format(Locale locale, String string, Object ... arrobject) {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return this;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        if (this.formatter == null || this.formatter.locale() != locale) {
            Formatter formatter;
            this.formatter = formatter = new Formatter(this, locale);
        }
        this.formatter.format(locale, string, arrobject);
        if (this.autoFlush) {
            this.out.flush();
        }
        // MONITOREXIT : object
        return this;
    }

    public void print(char c) {
        this.write(c);
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

    public PrintWriter printf(String string, Object ... arrobject) {
        return this.format(string, arrobject);
    }

    public PrintWriter printf(Locale locale, String string, Object ... arrobject) {
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
        Object object = this.lock;
        synchronized (object) {
            this.print(c);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(double d) {
        Object object = this.lock;
        synchronized (object) {
            this.print(d);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(float f) {
        Object object = this.lock;
        synchronized (object) {
            this.print(f);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(int n) {
        Object object = this.lock;
        synchronized (object) {
            this.print(n);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(long l) {
        Object object = this.lock;
        synchronized (object) {
            this.print(l);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(Object object) {
        String string = String.valueOf(object);
        object = this.lock;
        synchronized (object) {
            this.print(string);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(String string) {
        Object object = this.lock;
        synchronized (object) {
            this.print(string);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(boolean bl) {
        Object object = this.lock;
        synchronized (object) {
            this.print(bl);
            this.println();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void println(char[] arrc) {
        Object object = this.lock;
        synchronized (object) {
            this.print(arrc);
            this.println();
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
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        this.out.write(n);
        // MONITOREXIT : object
        return;
    }

    @Override
    public void write(String string) {
        this.write(string, 0, string.length());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void write(String string, int n, int n2) {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        this.out.write(string, n, n2);
        // MONITOREXIT : object
        return;
    }

    @Override
    public void write(char[] arrc) {
        this.write(arrc, 0, arrc.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void write(char[] arrc, int n, int n2) {
        try {
            Object object = this.lock;
            // MONITORENTER : object
        }
        catch (IOException iOException) {
            this.trouble = true;
            return;
        }
        catch (InterruptedIOException interruptedIOException) {
            Thread.currentThread().interrupt();
        }
        this.ensureOpen();
        this.out.write(arrc, n, n2);
        // MONITOREXIT : object
        return;
    }
}

