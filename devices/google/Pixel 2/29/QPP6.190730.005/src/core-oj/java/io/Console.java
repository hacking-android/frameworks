/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Formatter;
import sun.nio.cs.StreamDecoder;
import sun.nio.cs.StreamEncoder;

public final class Console
implements Flushable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Console cons;
    private static boolean echoOff;
    private Charset cs;
    private Formatter formatter;
    private Writer out;
    private PrintWriter pw;
    private char[] rcb;
    private Object readLock = new Object();
    private Reader reader;
    private Object writeLock = new Object();

    private Console() {
        this(new FileInputStream(FileDescriptor.in), new FileOutputStream(FileDescriptor.out));
    }

    private Console(InputStream inputStream, OutputStream outputStream) {
        String string = Console.encoding();
        if (string != null) {
            try {
                this.cs = Charset.forName(string);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.cs == null) {
            this.cs = Charset.defaultCharset();
        }
        this.out = StreamEncoder.forOutputStreamWriter(outputStream, this.writeLock, this.cs);
        this.pw = new PrintWriter(this.out, true){

            @Override
            public void close() {
            }
        };
        this.formatter = new Formatter(this.out);
        this.reader = new LineReader(StreamDecoder.forInputStreamReader(inputStream, this.readLock, this.cs));
        this.rcb = new char[1024];
    }

    public static Console console() {
        if (Console.istty()) {
            if (cons == null) {
                cons = new Console();
            }
            return cons;
        }
        return null;
    }

    private static native boolean echo(boolean var0) throws IOException;

    private static native String encoding();

    private char[] grow() {
        char[] arrc = this.rcb;
        char[] arrc2 = new char[arrc.length * 2];
        System.arraycopy((Object)arrc, 0, (Object)arrc2, 0, arrc.length);
        this.rcb = arrc2;
        return this.rcb;
    }

    private static native boolean istty();

    private char[] readline(boolean bl) throws IOException {
        int n;
        char[] arrc = this.reader;
        char[] arrc2 = this.rcb;
        int n2 = arrc.read(arrc2, 0, arrc2.length);
        if (n2 < 0) {
            return null;
        }
        arrc = this.rcb;
        if (arrc[n2 - 1] == '\r') {
            n = n2 - 1;
        } else {
            n = n2;
            if (arrc[n2 - 1] == '\n') {
                n = --n2;
                if (n2 > 0) {
                    n = n2;
                    if (arrc[n2 - 1] == '\r') {
                        n = n2 - 1;
                    }
                }
            }
        }
        arrc = new char[n];
        if (n > 0) {
            System.arraycopy((Object)this.rcb, 0, (Object)arrc, 0, n);
            if (bl) {
                Arrays.fill(this.rcb, 0, n, ' ');
            }
        }
        return arrc;
    }

    @Override
    public void flush() {
        this.pw.flush();
    }

    public Console format(String string, Object ... arrobject) {
        this.formatter.format(string, arrobject).flush();
        return this;
    }

    public Console printf(String string, Object ... arrobject) {
        return this.format(string, arrobject);
    }

    public String readLine() {
        return this.readLine("", new Object[0]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String readLine(String object, Object ... arrobject) {
        Object var3_4 = null;
        Object object2 = this.writeLock;
        synchronized (object2) {
            Object object3 = this.readLock;
            synchronized (object3) {
                if (((String)object).length() != 0) {
                    this.pw.format((String)object, arrobject);
                }
                try {
                    arrobject = this.readline(false);
                    object = var3_4;
                    if (arrobject == null) return object;
                    return new String((char[])arrobject);
                }
                catch (IOException iOException) {
                    object = new IOError(iOException);
                    throw object;
                }
            }
        }
    }

    public char[] readPassword() {
        return this.readPassword("", new Object[0]);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public char[] readPassword(String var1_1, Object ... var2_5) {
        var3_10 = this.writeLock;
        // MONITORENTER : var3_10
        var4_11 = this.readLock;
        // MONITORENTER : var4_11
        Console.echoOff = Console.echo(false);
        var5_12 = null;
        var6_13 = null;
        if (var1_1.length() != 0) {
            this.pw.format((String)var1_1, var2_5 /* !! */ );
        }
        var7_14 = this.readline(true);
        try {
            Console.echoOff = Console.echo(true);
            var2_5 /* !! */  = var6_13;
        }
        catch (IOException var1_2) {
            if (false != false) throw new NullPointerException();
            var2_5 /* !! */  = new IOError(var1_2);
        }
        var1_1 = var2_5 /* !! */ ;
        if (var2_5 /* !! */  != null) throw var1_1;
        this.pw.println();
        // MONITOREXIT : var4_11
        // MONITOREXIT : var3_10
        return var7_14;
        {
            catch (Throwable var1_4) {
                throw var1_4;
            }
            catch (Throwable var2_6) {
                ** GOTO lbl41
            }
            catch (IOException var2_7) {}
            {
                var1_1 = new IOError(var2_7);
            }
            try {
                Console.echoOff = Console.echo(true);
                throw var1_1;
            }
            catch (IOException var2_8) {
                var1_1.addSuppressed(var2_8);
                throw var1_1;
            }
lbl41: // 1 sources:
            try {
                Console.echoOff = Console.echo(true);
                var1_1 = var5_12;
            }
            catch (IOException var1_3) {
                if (false != false) throw new NullPointerException();
                var1_1 = new IOError(var1_3);
            }
            if (var1_1 == null) throw var2_6;
            throw var1_1;
        }
        catch (IOException var2_9) {
            var1_1 = new IOError(var2_9);
            throw var1_1;
        }
    }

    public Reader reader() {
        return this.reader;
    }

    public PrintWriter writer() {
        return this.pw;
    }

    class LineReader
    extends Reader {
        private char[] cb;
        private Reader in;
        boolean leftoverLF;
        private int nChars;
        private int nextChar;

        LineReader(Reader reader) {
            this.in = reader;
            this.cb = new char[1024];
            this.nChars = 0;
            this.nextChar = 0;
            this.leftoverLF = false;
        }

        @Override
        public void close() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public int read(char[] arrc, int n, int n2) throws IOException {
            int n3;
            int n4 = n;
            int n5 = n + n2;
            if (n < 0) throw new IndexOutOfBoundsException();
            if (n > arrc.length) throw new IndexOutOfBoundsException();
            if (n2 < 0) throw new IndexOutOfBoundsException();
            if (n5 < 0) throw new IndexOutOfBoundsException();
            if (n5 > arrc.length) throw new IndexOutOfBoundsException();
            Object object = Console.this.readLock;
            // MONITORENTER : object
            int n6 = 0;
            do {
                n3 = n6;
                if (this.nextChar >= this.nChars) {
                    while ((n2 = this.in.read(this.cb, 0, this.cb.length)) == 0) {
                    }
                    if (n2 > 0) {
                        this.nChars = n2;
                        this.nextChar = 0;
                        n3 = n6;
                        if (n2 < this.cb.length) {
                            n3 = n6;
                            if (this.cb[n2 - 1] != '\n') {
                                n3 = n6;
                                if (this.cb[n2 - 1] != '\r') {
                                    n3 = 1;
                                }
                            }
                        }
                    } else {
                        if (n4 - n == 0) {
                            // MONITOREXIT : object
                            return -1;
                        }
                        // MONITOREXIT : object
                        return n4 - n;
                    }
                }
                if (this.leftoverLF && arrc == Console.this.rcb && this.cb[this.nextChar] == '\n') {
                    ++this.nextChar;
                }
                this.leftoverLF = false;
                n2 = n4;
                while ((n4 = this.nextChar) < (n6 = this.nChars)) {
                    n6 = n2 + 1;
                    n4 = this.cb[this.nextChar];
                    arrc[n2] = (char)n4;
                    char[] arrc2 = this.cb;
                    n2 = this.nextChar;
                    this.nextChar = n2 + 1;
                    arrc2[n2] = (char)(false ? 1 : 0);
                    if (n4 == 10) {
                        return n6 - n;
                    }
                    if (n4 == 13) {
                        block33 : {
                            block32 : {
                                arrc2 = arrc;
                                if (n6 == n5) {
                                    if (arrc == Console.this.rcb) {
                                        arrc2 = Console.this.grow();
                                        n2 = arrc2.length;
                                        break block32;
                                    }
                                    this.leftoverLF = true;
                                    // MONITOREXIT : object
                                    return n6 - n;
                                }
                            }
                            if (this.nextChar == this.nChars && this.in.ready()) {
                                this.nChars = this.in.read(this.cb, 0, this.cb.length);
                                this.nextChar = 0;
                            }
                            if (this.nextChar >= this.nChars || (n2 = this.cb[this.nextChar]) != 10) break block33;
                            n2 = n6 + 1;
                            arrc2[n6] = (char)10;
                            ++this.nextChar;
                            n6 = n2;
                        }
                        // MONITOREXIT : object
                        return n6 - n;
                    }
                    if (n6 == n5) {
                        if (arrc == Console.this.rcb) {
                            arrc = Console.this.grow();
                            n5 = arrc.length;
                            n2 = n6;
                            continue;
                        }
                        // MONITOREXIT : object
                        return n6 - n;
                    }
                    n2 = n6;
                }
                n4 = n2;
                n6 = n3;
            } while (n3 == 0);
            // MONITOREXIT : object
            return n2 - n;
        }

        @Override
        public boolean ready() throws IOException {
            return this.in.ready();
        }
    }

}

