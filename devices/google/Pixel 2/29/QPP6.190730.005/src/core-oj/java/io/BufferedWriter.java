/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Writer;
import java.security.AccessController;
import sun.security.action.GetPropertyAction;

public class BufferedWriter
extends Writer {
    private static int defaultCharBufferSize = 8192;
    private char[] cb;
    private String lineSeparator;
    private int nChars;
    private int nextChar;
    private Writer out;

    public BufferedWriter(Writer writer) {
        this(writer, defaultCharBufferSize);
    }

    public BufferedWriter(Writer writer, int n) {
        super(writer);
        if (n > 0) {
            this.out = writer;
            this.cb = new char[n];
            this.nChars = n;
            this.nextChar = 0;
            this.lineSeparator = AccessController.doPrivileged(new GetPropertyAction("line.separator"));
            return;
        }
        throw new IllegalArgumentException("Buffer size <= 0");
    }

    private void ensureOpen() throws IOException {
        if (this.out != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private int min(int n, int n2) {
        if (n < n2) {
            return n;
        }
        return n2;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void close() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.flushBuffer();
            this.out.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void flushBuffer() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.nextChar == 0) {
                return;
            }
            this.out.write(this.cb, 0, this.nextChar);
            this.nextChar = 0;
            return;
        }
    }

    public void newLine() throws IOException {
        this.write(this.lineSeparator);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(int n) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.nextChar >= this.nChars) {
                this.flushBuffer();
            }
            char[] arrc = this.cb;
            int n2 = this.nextChar;
            this.nextChar = n2 + 1;
            arrc[n2] = (char)n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(String string, int n, int n2) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            int n3 = n;
            n += n2;
            while (n3 < n) {
                n2 = this.min(this.nChars - this.nextChar, n - n3);
                string.getChars(n3, n3 + n2, this.cb, this.nextChar);
                n3 += n2;
                this.nextChar += n2;
                if (this.nextChar < this.nChars) continue;
                this.flushBuffer();
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
    public void write(char[] object, int n, int n2) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            this.ensureOpen();
            if (n >= 0 && n <= ((char[])object).length && n2 >= 0 && n + n2 <= ((char[])object).length && n + n2 >= 0) {
                if (n2 == 0) {
                    return;
                }
                if (n2 >= this.nChars) {
                    this.flushBuffer();
                    this.out.write((char[])object, n, n2);
                    return;
                }
                int n3 = n;
                n += n2;
                do {
                    if (n3 >= n) {
                        return;
                    }
                    n2 = this.min(this.nChars - this.nextChar, n - n3);
                    System.arraycopy(object, n3, (Object)this.cb, this.nextChar, n2);
                    n3 += n2;
                    this.nextChar += n2;
                    if (this.nextChar < this.nChars) continue;
                    this.flushBuffer();
                } while (true);
            }
            object = new IndexOutOfBoundsException();
            throw object;
        }
    }
}

