/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;

public class StreamTokenizer {
    private static final byte CT_ALPHA = 4;
    private static final byte CT_COMMENT = 16;
    private static final byte CT_DIGIT = 2;
    private static final byte CT_QUOTE = 8;
    private static final byte CT_WHITESPACE = 1;
    private static final int NEED_CHAR = Integer.MAX_VALUE;
    private static final int SKIP_LF = 2147483646;
    public static final int TT_EOF = -1;
    public static final int TT_EOL = 10;
    private static final int TT_NOTHING = -4;
    public static final int TT_NUMBER = -2;
    public static final int TT_WORD = -3;
    private int LINENO = 1;
    private char[] buf = new char[20];
    private byte[] ctype = new byte[256];
    private boolean eolIsSignificantP = false;
    private boolean forceLower;
    private InputStream input = null;
    public double nval;
    private int peekc = Integer.MAX_VALUE;
    private boolean pushedBack;
    private Reader reader = null;
    private boolean slashSlashCommentsP = false;
    private boolean slashStarCommentsP = false;
    public String sval;
    public int ttype = -4;

    private StreamTokenizer() {
        this.wordChars(97, 122);
        this.wordChars(65, 90);
        this.wordChars(160, 255);
        this.whitespaceChars(0, 32);
        this.commentChar(47);
        this.quoteChar(34);
        this.quoteChar(39);
        this.parseNumbers();
    }

    @Deprecated
    public StreamTokenizer(InputStream inputStream) {
        this();
        if (inputStream != null) {
            this.input = inputStream;
            return;
        }
        throw new NullPointerException();
    }

    public StreamTokenizer(Reader reader) {
        this();
        if (reader != null) {
            this.reader = reader;
            return;
        }
        throw new NullPointerException();
    }

    private int read() throws IOException {
        Closeable closeable = this.reader;
        if (closeable != null) {
            return ((Reader)closeable).read();
        }
        closeable = this.input;
        if (closeable != null) {
            return ((InputStream)closeable).read();
        }
        throw new IllegalStateException();
    }

    public void commentChar(int n) {
        byte[] arrby;
        if (n >= 0 && n < (arrby = this.ctype).length) {
            arrby[n] = (byte)16;
        }
    }

    public void eolIsSignificant(boolean bl) {
        this.eolIsSignificantP = bl;
    }

    public int lineno() {
        return this.LINENO;
    }

    public void lowerCaseMode(boolean bl) {
        this.forceLower = bl;
    }

    public int nextToken() throws IOException {
        int n;
        int n2;
        if (this.pushedBack) {
            this.pushedBack = false;
            return this.ttype;
        }
        Object[] arrobject = this.ctype;
        this.sval = null;
        int n3 = n2 = this.peekc;
        if (n2 < 0) {
            n3 = Integer.MAX_VALUE;
        }
        n2 = n3;
        if (n3 == 2147483646) {
            n3 = this.read();
            if (n3 < 0) {
                this.ttype = -1;
                return -1;
            }
            n2 = n3;
            if (n3 == 10) {
                n2 = Integer.MAX_VALUE;
            }
        }
        int n4 = Integer.MAX_VALUE;
        n3 = n2;
        if (n2 == Integer.MAX_VALUE) {
            n3 = n2 = this.read();
            if (n2 < 0) {
                this.ttype = -1;
                return -1;
            }
        }
        this.ttype = n3;
        this.peekc = Integer.MAX_VALUE;
        if (n3 < 256) {
            n = arrobject[n3];
            n2 = n3;
        } else {
            n = 4;
            n2 = n3;
        }
        while ((n & 1) != 0) {
            if (n2 == 13) {
                ++this.LINENO;
                if (this.eolIsSignificantP) {
                    this.peekc = 2147483646;
                    this.ttype = 10;
                    return 10;
                }
                n2 = n3 = this.read();
                if (n3 == 10) {
                    n2 = this.read();
                }
            } else {
                if (n2 == 10) {
                    ++this.LINENO;
                    if (this.eolIsSignificantP) {
                        this.ttype = 10;
                        return 10;
                    }
                }
                n2 = this.read();
            }
            if (n2 < 0) {
                this.ttype = -1;
                return -1;
            }
            n3 = n2 < 256 ? arrobject[n2] : 4;
            n = n3;
        }
        if ((n & 2) != 0) {
            double d;
            n = 0;
            n3 = n2;
            if (n2 == 45) {
                n3 = this.read();
                if (n3 != 46 && (n3 < 48 || n3 > 57)) {
                    this.peekc = n3;
                    this.ttype = 45;
                    return 45;
                }
                n = 1;
            }
            double d2 = 0.0;
            n2 = 0;
            int n5 = 0;
            n4 = n3;
            do {
                if (n4 == 46 && n5 == 0) {
                    n3 = 1;
                } else {
                    if (48 > n4 || n4 > 57) break;
                    d = n4 - 48;
                    n2 += n5;
                    d2 = 10.0 * d2 + d;
                    n3 = n5;
                }
                n4 = this.read();
                n5 = n3;
            } while (true);
            this.peekc = n4;
            d = d2;
            if (n2 != 0) {
                d = 10.0;
                --n2;
                while (n2 > 0) {
                    d *= 10.0;
                    --n2;
                }
                d = d2 / d;
            }
            d2 = n != 0 ? -d : d;
            this.nval = d2;
            this.ttype = -2;
            return -2;
        }
        if ((n & 4) != 0) {
            n3 = 0;
            do {
                char[] arrc;
                if (n3 >= (arrc = this.buf).length) {
                    this.buf = Arrays.copyOf(arrc, arrc.length * 2);
                }
                arrc = this.buf;
                n = n3 + 1;
                arrc[n3] = (char)n2;
                int n6 = this.read();
                n2 = n6 < 0 ? 1 : (n6 < 256 ? arrobject[n6] : 4);
                if ((n2 & 6) == 0) {
                    this.peekc = n6;
                    this.sval = String.copyValueOf(this.buf, 0, n);
                    if (this.forceLower) {
                        this.sval = this.sval.toLowerCase();
                    }
                    this.ttype = -3;
                    return -3;
                }
                n3 = n;
                n2 = n6;
            } while (true);
        }
        if ((n & 8) != 0) {
            this.ttype = n2;
            int n7 = 0;
            n2 = this.read();
            while (n2 >= 0 && n2 != this.ttype && n2 != 10 && n2 != 13) {
                if (n2 == 92) {
                    n2 = this.read();
                    if (n2 >= 48 && n2 <= 55) {
                        n3 = n2 - 48;
                        n = this.read();
                        if (48 <= n && n <= 55) {
                            n3 = (n3 << 3) + (n - 48);
                            n = this.read();
                            if (48 <= n && n <= 55 && n2 <= 51) {
                                n3 = (n3 << 3) + (n - 48);
                                n2 = this.read();
                            } else {
                                n2 = n;
                            }
                        } else {
                            n2 = n;
                        }
                        n = n3;
                        n3 = n2;
                    } else {
                        if (n2 != 97) {
                            if (n2 != 98) {
                                if (n2 != 102) {
                                    if (n2 != 110) {
                                        if (n2 != 114) {
                                            if (n2 != 116) {
                                                if (n2 == 118) {
                                                    n2 = 11;
                                                }
                                            } else {
                                                n2 = 9;
                                            }
                                        } else {
                                            n2 = 13;
                                        }
                                    } else {
                                        n2 = 10;
                                    }
                                } else {
                                    n2 = 12;
                                }
                            } else {
                                n2 = 8;
                            }
                        } else {
                            n2 = 7;
                        }
                        n3 = this.read();
                        n = n2;
                    }
                    n2 = n3;
                } else {
                    n = n2;
                    n2 = this.read();
                }
                arrobject = this.buf;
                if (n7 >= arrobject.length) {
                    this.buf = Arrays.copyOf((char[])arrobject, arrobject.length * 2);
                }
                this.buf[n7] = (char)n;
                ++n7;
            }
            if (n2 == this.ttype) {
                n2 = n4;
            }
            this.peekc = n2;
            this.sval = String.copyValueOf(this.buf, 0, n7);
            return this.ttype;
        }
        if (n2 == 47 && (this.slashSlashCommentsP || this.slashStarCommentsP)) {
            n2 = this.read();
            if (n2 == 42 && this.slashStarCommentsP) {
                n2 = 0;
                do {
                    n3 = n = this.read();
                    if (n == 47 && n2 == 42) {
                        return this.nextToken();
                    }
                    if (n3 == 13) {
                        ++this.LINENO;
                        n2 = n3 = this.read();
                        if (n3 != 10) continue;
                        n2 = this.read();
                        continue;
                    }
                    n2 = n3;
                    if (n3 != 10) continue;
                    ++this.LINENO;
                    n2 = this.read();
                } while (n2 >= 0);
                this.ttype = -1;
                return -1;
            }
            if (n2 == 47 && this.slashSlashCommentsP) {
                while ((n2 = this.read()) != 10 && n2 != 13 && n2 >= 0) {
                }
                this.peekc = n2;
                return this.nextToken();
            }
            if ((arrobject[47] & 16) != 0) {
                while ((n2 = this.read()) != 10 && n2 != 13 && n2 >= 0) {
                }
                this.peekc = n2;
                return this.nextToken();
            }
            this.peekc = n2;
            this.ttype = 47;
            return 47;
        }
        if ((n & 16) != 0) {
            while ((n2 = this.read()) != 10 && n2 != 13 && n2 >= 0) {
            }
            this.peekc = n2;
            return this.nextToken();
        }
        this.ttype = n2;
        return n2;
    }

    public void ordinaryChar(int n) {
        byte[] arrby;
        if (n >= 0 && n < (arrby = this.ctype).length) {
            arrby[n] = (byte)(false ? 1 : 0);
        }
    }

    public void ordinaryChars(int n, int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        byte[] arrby = this.ctype;
        int n4 = n3;
        n = n2;
        if (n2 >= arrby.length) {
            n = arrby.length - 1;
            n4 = n3;
        }
        while (n4 <= n) {
            this.ctype[n4] = (byte)(false ? 1 : 0);
            ++n4;
        }
    }

    public void parseNumbers() {
        byte[] arrby;
        for (int i = 48; i <= 57; ++i) {
            arrby = this.ctype;
            arrby[i] = (byte)(arrby[i] | 2);
        }
        arrby = this.ctype;
        arrby[46] = (byte)(arrby[46] | 2);
        arrby[45] = (byte)(arrby[45] | 2);
    }

    public void pushBack() {
        if (this.ttype != -4) {
            this.pushedBack = true;
        }
    }

    public void quoteChar(int n) {
        byte[] arrby;
        if (n >= 0 && n < (arrby = this.ctype).length) {
            arrby[n] = (byte)8;
        }
    }

    public void resetSyntax() {
        int n = this.ctype.length;
        while (--n >= 0) {
            this.ctype[n] = (byte)(false ? 1 : 0);
        }
    }

    public void slashSlashComments(boolean bl) {
        this.slashSlashCommentsP = bl;
    }

    public void slashStarComments(boolean bl) {
        this.slashStarCommentsP = bl;
    }

    public String toString() {
        CharSequence charSequence;
        int n = this.ttype;
        if (n != -4) {
            if (n != -3) {
                if (n != -2) {
                    charSequence = n != -1 ? (n != 10 ? (n < 256 && (this.ctype[n] & 8) != 0 ? this.sval : new String(new char[]{'\'', (char)this.ttype, '\''})) : "EOL") : "EOF";
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("n=");
                    ((StringBuilder)charSequence).append(this.nval);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            } else {
                charSequence = this.sval;
            }
        } else {
            charSequence = "NOTHING";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Token[");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("], line ");
        stringBuilder.append(this.LINENO);
        return stringBuilder.toString();
    }

    public void whitespaceChars(int n, int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        byte[] arrby = this.ctype;
        int n4 = n3;
        n = n2;
        if (n2 >= arrby.length) {
            n = arrby.length - 1;
            n4 = n3;
        }
        while (n4 <= n) {
            this.ctype[n4] = (byte)(true ? 1 : 0);
            ++n4;
        }
    }

    public void wordChars(int n, int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        byte[] arrby = this.ctype;
        int n4 = n3;
        n = n2;
        if (n2 >= arrby.length) {
            n = arrby.length - 1;
            n4 = n3;
        }
        while (n4 <= n) {
            arrby = this.ctype;
            arrby[n4] = (byte)(arrby[n4] | 4);
            ++n4;
        }
    }
}

