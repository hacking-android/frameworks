/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String escapedAV() {
        int n;
        char[] arrc;
        this.beg = n = this.pos;
        this.end = n;
        do {
            if ((n = this.pos) >= this.length) {
                arrc = this.chars;
                n = this.beg;
                return new String(arrc, n, this.end - n);
            }
            arrc = this.chars;
            int n2 = arrc[n];
            if (n2 != 32) {
                if (n2 != 59) {
                    if (n2 != 92) {
                        if (n2 != 43 && n2 != 44) {
                            n2 = this.end;
                            this.end = n2 + 1;
                            arrc[n2] = arrc[n];
                            this.pos = n + 1;
                            continue;
                        }
                    } else {
                        n = this.end;
                        this.end = n + 1;
                        arrc[n] = this.getEscaped();
                        ++this.pos;
                        continue;
                    }
                }
                arrc = this.chars;
                n = this.beg;
                return new String(arrc, n, this.end - n);
            }
            this.cur = n2 = this.end;
            this.pos = n + 1;
            this.end = n2 + 1;
            arrc[n2] = (char)32;
            while ((n = this.pos) < this.length && (arrc = this.chars)[n] == ' ') {
                n2 = this.end;
                this.end = n2 + 1;
                arrc[n2] = (char)32;
                this.pos = n + 1;
            }
            n = this.pos;
            if (n == this.length || (arrc = this.chars)[n] == ',' || arrc[n] == '+' || arrc[n] == ';') break;
        } while (true);
        arrc = this.chars;
        n = this.beg;
        return new String(arrc, n, this.cur - n);
    }

    private int getByte(int n) {
        block2 : {
            block6 : {
                block10 : {
                    int n2;
                    block8 : {
                        block9 : {
                            block7 : {
                                block4 : {
                                    block5 : {
                                        block3 : {
                                            if (n + 1 >= this.length) break block2;
                                            n2 = this.chars[n];
                                            if (n2 < 48 || n2 > 57) break block3;
                                            n2 -= 48;
                                            break block4;
                                        }
                                        if (n2 < 97 || n2 > 102) break block5;
                                        n2 -= 87;
                                        break block4;
                                    }
                                    if (n2 < 65 || n2 > 70) break block6;
                                    n2 -= 55;
                                }
                                n = this.chars[n + 1];
                                if (n < 48 || n > 57) break block7;
                                n -= 48;
                                break block8;
                            }
                            if (n < 97 || n > 102) break block9;
                            n -= 87;
                            break block8;
                        }
                        if (n < 65 || n > 70) break block10;
                        n -= 55;
                    }
                    return (n2 << 4) + n;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Malformed DN: ");
                stringBuilder.append(this.dn);
                throw new IllegalStateException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed DN: ");
            stringBuilder.append(this.dn);
            throw new IllegalStateException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Malformed DN: ");
        stringBuilder.append(this.dn);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private char getEscaped() {
        ++this.pos;
        int n = this.pos;
        if (n != this.length) {
            if ((n = this.chars[n]) != 32 && n != 37 && n != 92 && n != 95 && n != 34 && n != 35) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return this.getUTF8();
                            }
                            case 59: 
                            case 60: 
                            case 61: 
                            case 62: 
                        }
                    }
                    case 42: 
                    case 43: 
                    case 44: 
                }
            }
            return this.chars[this.pos];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected end of DN: ");
        stringBuilder.append(this.dn);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private char getUTF8() {
        int n = this.getByte(this.pos);
        ++this.pos;
        if (n < 128) {
            return (char)n;
        }
        if (n >= 192 && n <= 247) {
            int n2;
            if (n <= 223) {
                n2 = 1;
                n &= 31;
            } else if (n <= 239) {
                n2 = 2;
                n &= 15;
            } else {
                n2 = 3;
                n &= 7;
            }
            for (int i = 0; i < n2; ++i) {
                ++this.pos;
                int n3 = this.pos;
                if (n3 != this.length && this.chars[n3] == '\\') {
                    this.pos = n3 + 1;
                    n3 = this.getByte(this.pos);
                    ++this.pos;
                    if ((n3 & 192) != 128) {
                        return '?';
                    }
                    n = (n << 6) + (n3 & 63);
                    continue;
                }
                return '?';
            }
            return (char)n;
        }
        return '?';
    }

    private String hexAV() {
        int n = this.pos;
        if (n + 4 < this.length) {
            int n2;
            Object object;
            int n3;
            block6 : {
                this.beg = n;
                this.pos = n + 1;
                while ((n = ++this.pos) != this.length && (object = this.chars)[n] != '+' && object[n] != ',' && object[n] != ';') {
                    if (object[n] == ' ') {
                        this.end = n;
                        this.pos = n + 1;
                        while ((n = this.pos) < this.length && this.chars[n] == ' ') {
                            this.pos = n + 1;
                        }
                        break block6;
                    }
                    if (object[n] < 65 || object[n] > 70) continue;
                    object[n] = (char)(object[n] + 32);
                }
                this.end = this.pos;
            }
            if ((n3 = (n = this.end) - (n2 = this.beg)) >= 5 && (n3 & 1) != 0) {
                object = new byte[n3 / 2];
                ++n2;
                for (n = 0; n < ((Object)object).length; ++n) {
                    object[n] = (byte)this.getByte(n2);
                    n2 += 2;
                }
                return new String(this.chars, this.beg, n3);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected end of DN: ");
            ((StringBuilder)object).append(this.dn);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected end of DN: ");
        stringBuilder.append(this.dn);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private String nextAT() {
        int n;
        Object object;
        while ((n = this.pos) < this.length && this.chars[n] == ' ') {
            this.pos = n + 1;
        }
        n = this.pos;
        if (n == this.length) {
            return null;
        }
        this.beg = n;
        this.pos = n + 1;
        while ((n = this.pos) < this.length && (object = this.chars)[n] != '=' && object[n] != ' ') {
            this.pos = n + 1;
        }
        n = this.pos;
        if (n < this.length) {
            this.end = n;
            if (this.chars[n] == ' ') {
                while ((n = this.pos) < this.length && (object = this.chars)[n] != 61 && object[n] == 32) {
                    this.pos = n + 1;
                }
                object = this.chars;
                n = this.pos;
                if (object[n] != 61 || n == this.length) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected end of DN: ");
                    ((StringBuilder)object).append(this.dn);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            }
            ++this.pos;
            while ((n = this.pos) < this.length && this.chars[n] == ' ') {
                this.pos = n + 1;
            }
            n = this.end;
            int n2 = this.beg;
            if (!(n - n2 <= 4 || (object = this.chars)[n2 + 3] != 46 || object[n2] != 79 && object[n2] != 111 || (object = this.chars)[(n = this.beg) + 1] != 73 && object[n + 1] != 105 || (object = this.chars)[(n = this.beg) + 2] != 68 && object[n + 2] != 100)) {
                this.beg += 4;
            }
            object = this.chars;
            n = this.beg;
            return new String((char[])object, n, this.end - n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected end of DN: ");
        ((StringBuilder)object).append(this.dn);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private String quotedAV() {
        int n;
        Object object;
        ++this.pos;
        this.end = this.beg = this.pos;
        while ((n = ++this.pos) != this.length) {
            object = this.chars;
            if (object[n] == '\"') {
                this.pos = n + 1;
                while ((n = this.pos) < this.length && this.chars[n] == ' ') {
                    this.pos = n + 1;
                }
                object = this.chars;
                n = this.beg;
                return new String((char[])object, n, this.end - n);
            }
            object[this.end] = object[n] == 92 ? (Object)this.getEscaped() : (Object)((char)object[n]);
            ++this.end;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected end of DN: ");
        ((StringBuilder)object).append(this.dn);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public String findMostSpecific(String charSequence) {
        char[] arrc;
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        Object object = arrc = this.nextAT();
        if (arrc == null) {
            return null;
        }
        do {
            arrc = "";
            int n = this.pos;
            if (n == this.length) {
                return null;
            }
            if ((n = this.chars[n]) != 34) {
                if (n != 35) {
                    if (n != 43 && n != 44 && n != 59) {
                        arrc = this.escapedAV();
                    }
                } else {
                    arrc = this.hexAV();
                }
            } else {
                arrc = this.quotedAV();
            }
            if (((String)charSequence).equalsIgnoreCase((String)object)) {
                return arrc;
            }
            n = this.pos;
            if (n >= this.length) {
                return null;
            }
            arrc = this.chars;
            if (arrc[n] != ',' && arrc[n] != ';' && arrc[n] != '+') {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Malformed DN: ");
                ((StringBuilder)charSequence).append(this.dn);
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            ++this.pos;
        } while ((object = this.nextAT()) != null);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Malformed DN: ");
        ((StringBuilder)charSequence).append(this.dn);
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }
}

