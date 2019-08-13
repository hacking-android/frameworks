/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BufferedReader
extends Reader {
    private static final int INVALIDATED = -2;
    private static final int UNMARKED = -1;
    private static int defaultCharBufferSize = 8192;
    private static int defaultExpectedLineLength = 80;
    private char[] cb;
    private Reader in;
    private int markedChar = -1;
    private boolean markedSkipLF = false;
    private int nChars;
    private int nextChar;
    private int readAheadLimit = 0;
    private boolean skipLF = false;

    public BufferedReader(Reader reader) {
        this(reader, defaultCharBufferSize);
    }

    public BufferedReader(Reader reader, int n) {
        super(reader);
        if (n > 0) {
            this.in = reader;
            this.cb = new char[n];
            this.nChars = 0;
            this.nextChar = 0;
            return;
        }
        throw new IllegalArgumentException("Buffer size <= 0");
    }

    private void ensureOpen() throws IOException {
        if (this.in != null) {
            return;
        }
        throw new IOException("Stream closed");
    }

    private void fill() throws IOException {
        char[] arrc;
        char[] arrc2;
        int n;
        int n2 = this.markedChar;
        if (n2 <= -1) {
            n2 = 0;
        } else {
            n = this.nextChar - n2;
            int n3 = this.readAheadLimit;
            if (n >= n3) {
                this.markedChar = -2;
                this.readAheadLimit = 0;
                n2 = 0;
            } else {
                arrc2 = this.cb;
                if (n3 <= arrc2.length) {
                    System.arraycopy((Object)arrc2, n2, (Object)arrc2, 0, n);
                    this.markedChar = 0;
                } else {
                    int n4;
                    n2 = n4 = arrc2.length * 2;
                    if (n4 > n3) {
                        n2 = this.readAheadLimit;
                    }
                    arrc2 = new char[n2];
                    System.arraycopy((Object)this.cb, this.markedChar, (Object)arrc2, 0, n);
                    this.cb = arrc2;
                    this.markedChar = 0;
                }
                n2 = n;
                this.nChars = n;
                this.nextChar = n;
            }
        }
        while ((n = (arrc2 = this.in).read(arrc = this.cb, n2, arrc.length - n2)) == 0) {
        }
        if (n > 0) {
            this.nChars = n2 + n;
            this.nextChar = n2;
        }
    }

    private int read1(char[] arrc, int n, int n2) throws IOException {
        int n3;
        int n4;
        if (this.nextChar >= this.nChars) {
            if (n2 >= this.cb.length && this.markedChar <= -1 && !this.skipLF) {
                return this.in.read(arrc, n, n2);
            }
            this.fill();
        }
        if ((n4 = this.nextChar) >= (n3 = this.nChars)) {
            return -1;
        }
        if (this.skipLF) {
            this.skipLF = false;
            if (this.cb[n4] == '\n') {
                this.nextChar = n4 + 1;
                if (this.nextChar >= n3) {
                    this.fill();
                }
                if (this.nextChar >= this.nChars) {
                    return -1;
                }
            }
        }
        n2 = Math.min(n2, this.nChars - this.nextChar);
        System.arraycopy((Object)this.cb, this.nextChar, (Object)arrc, n, n2);
        this.nextChar += n2;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.in == null) {
                return;
            }
            try {
                this.in.close();
                return;
            }
            finally {
                this.in = null;
                this.cb = null;
            }
        }
    }

    public Stream<String> lines() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<String>(){
            String nextLine = null;

            @Override
            public boolean hasNext() {
                String string = this.nextLine;
                boolean bl = true;
                if (string != null) {
                    return true;
                }
                try {
                    string = this.nextLine = BufferedReader.this.readLine();
                    if (string == null) {
                        bl = false;
                    }
                    return bl;
                }
                catch (IOException iOException) {
                    throw new UncheckedIOException(iOException);
                }
            }

            @Override
            public String next() {
                if (this.nextLine == null && !this.hasNext()) {
                    throw new NoSuchElementException();
                }
                String string = this.nextLine;
                this.nextLine = null;
                return string;
            }
        }, 272), false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void mark(int n) throws IOException {
        if (n >= 0) {
            Object object = this.lock;
            synchronized (object) {
                this.ensureOpen();
                this.readAheadLimit = n;
                this.markedChar = this.nextChar;
                this.markedSkipLF = this.skipLF;
                return;
            }
        }
        throw new IllegalArgumentException("Read-ahead limit < 0");
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            do {
                if (this.nextChar >= this.nChars) {
                    this.fill();
                    if (this.nextChar >= this.nChars) {
                        return -1;
                    }
                }
                if (!this.skipLF) break;
                this.skipLF = false;
                if (this.cb[this.nextChar] != '\n') break;
                ++this.nextChar;
            } while (true);
            char[] arrc = this.cb;
            int n = this.nextChar;
            this.nextChar = n + 1;
            return arrc[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(char[] object, int n, int n2) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            int n3;
            int n4;
            this.ensureOpen();
            if (n >= 0 && n <= ((char[])object).length && n2 >= 0 && n + n2 <= ((char[])object).length && n + n2 >= 0) {
                if (n2 == 0) {
                    return 0;
                }
                n4 = n3 = this.read1((char[])object, n, n2);
                if (n3 <= 0) {
                    return n3;
                }
            } else {
                object = new IndexOutOfBoundsException();
                throw object;
            }
            while (n4 < n2 && this.in.ready() && (n3 = this.read1((char[])object, n + n4, n2 - n4)) > 0) {
                n4 += n3;
            }
            return n4;
        }
    }

    public String readLine() throws IOException {
        return this.readLine(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String readLine(boolean bl) throws IOException {
        CharSequence charSequence = null;
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            int n = !bl && !this.skipLF ? 0 : 1;
            do {
                int n2;
                CharSequence charSequence2;
                int n3;
                int n4;
                int n5;
                block12 : {
                    if (this.nextChar >= this.nChars) {
                        this.fill();
                    }
                    if (this.nextChar >= this.nChars) {
                        if (charSequence == null) return null;
                        if (charSequence.length() <= 0) return null;
                        return charSequence.toString();
                    }
                    n3 = 0;
                    n5 = 0;
                    if (n == 0 || this.cb[this.nextChar] == '\n') {
                        // empty if block
                    }
                    this.skipLF = false;
                    n2 = 0;
                    n4 = ++this.nextChar;
                    n = n5;
                    do {
                        n5 = n3;
                        if (n4 >= this.nChars) break block12;
                        n = this.cb[n4];
                        if (n == 10 || n == 13) break;
                        ++n4;
                    } while (true);
                    n5 = 1;
                }
                n3 = this.nextChar;
                this.nextChar = n4;
                if (n5 != 0) {
                    if (charSequence == null) {
                        charSequence2 = new String(this.cb, n3, n4 - n3);
                    } else {
                        charSequence.append(this.cb, n3, n4 - n3);
                        charSequence2 = charSequence.toString();
                    }
                    ++this.nextChar;
                    if (n != 13) return charSequence2;
                    this.skipLF = true;
                    return charSequence2;
                }
                charSequence2 = charSequence;
                if (charSequence == null) {
                    charSequence2 = new StringBuffer(defaultExpectedLineLength);
                }
                ((StringBuffer)charSequence2).append(this.cb, n3, n4 - n3);
                charSequence = charSequence2;
                n = n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean ready() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            boolean bl = this.skipLF;
            boolean bl2 = false;
            if (bl) {
                if (this.nextChar >= this.nChars && this.in.ready()) {
                    this.fill();
                }
                if (this.nextChar < this.nChars) {
                    if (this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                    this.skipLF = false;
                }
            }
            if (this.nextChar < this.nChars) return true;
            if (!this.in.ready()) return bl2;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void reset() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.markedChar >= 0) {
                this.nextChar = this.markedChar;
                this.skipLF = this.markedSkipLF;
                return;
            }
            String string = this.markedChar == -2 ? "Mark invalid" : "Stream not marked";
            IOException iOException = new IOException(string);
            throw iOException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long skip(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        Object object = this.lock;
        synchronized (object) {
            long l2;
            this.ensureOpen();
            long l3 = l;
            do {
                l2 = l3;
                if (l3 <= 0L) break;
                if (this.nextChar >= this.nChars) {
                    this.fill();
                }
                if (this.nextChar >= this.nChars) {
                    l2 = l3;
                    break;
                }
                if (this.skipLF) {
                    this.skipLF = false;
                    if (this.cb[this.nextChar] == '\n') {
                        ++this.nextChar;
                    }
                }
                if (l3 <= (l2 = (long)(this.nChars - this.nextChar))) {
                    this.nextChar = (int)((long)this.nextChar + l3);
                    l2 = 0L;
                    break;
                }
                l3 -= l2;
                this.nextChar = this.nChars;
            } while (true);
            return l - l2;
        }
    }

}

