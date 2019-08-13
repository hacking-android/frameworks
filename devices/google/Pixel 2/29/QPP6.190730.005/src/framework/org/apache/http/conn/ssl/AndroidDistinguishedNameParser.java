/*
 * Decompiled with CFR 0.145.
 */
package org.apache.http.conn.ssl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;

@Deprecated
final class AndroidDistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    public AndroidDistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String escapedAV() {
        char[] arrc;
        int n;
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
            Object object;
            int n2;
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
            if ((n2 = (n = this.end) - (n3 = this.beg)) >= 5 && (n2 & 1) != 0) {
                object = new byte[n2 / 2];
                ++n3;
                for (n = 0; n < ((Object)object).length; ++n) {
                    object[n] = (byte)this.getByte(n3);
                    n3 += 2;
                }
                return new String(this.chars, this.beg, n2);
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
        Object object;
        int n;
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
            int n2 = this.end;
            n = this.beg;
            if (!(n2 - n <= 4 || (object = this.chars)[n + 3] != 46 || object[n] != 79 && object[n] != 111 || (object = this.chars)[(n = this.beg) + 1] != 73 && object[n + 1] != 105 || (object = this.chars)[(n = this.beg) + 2] != 68 && object[n + 2] != 100)) {
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
        Object object;
        int n;
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

    public List<String> getAllMostSpecificFirst(String charSequence) {
        char[] arrc;
        block10 : {
            this.pos = 0;
            this.beg = 0;
            this.end = 0;
            this.cur = 0;
            this.chars = this.dn.toCharArray();
            arrc = Collections.emptyList();
            String string2 = this.nextAT();
            char[] arrc2 = arrc;
            String string3 = string2;
            if (string2 == null) {
                return arrc;
            }
            do {
                int n = this.pos;
                arrc = arrc2;
                if (n >= this.length) break block10;
                string2 = "";
                if ((n = this.chars[n]) != 34) {
                    if (n != 35) {
                        if (n != 43 && n != 44 && n != 59) {
                            string2 = this.escapedAV();
                        }
                    } else {
                        string2 = this.hexAV();
                    }
                } else {
                    string2 = this.quotedAV();
                }
                arrc = arrc2;
                if (((String)charSequence).equalsIgnoreCase(string3)) {
                    arrc = arrc2;
                    if (arrc2.isEmpty()) {
                        arrc = new ArrayList<String>();
                    }
                    arrc.add((String)string2);
                }
                if ((n = this.pos) >= this.length) break block10;
                arrc2 = this.chars;
                if (arrc2[n] != ',' && arrc2[n] != ';' && arrc2[n] != '+') {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Malformed DN: ");
                    ((StringBuilder)charSequence).append(this.dn);
                    throw new IllegalStateException(((StringBuilder)charSequence).toString());
                }
                ++this.pos;
                string3 = this.nextAT();
                if (string3 == null) break;
                arrc2 = arrc;
            } while (true);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Malformed DN: ");
            ((StringBuilder)charSequence).append(this.dn);
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
        return arrc;
    }
}

