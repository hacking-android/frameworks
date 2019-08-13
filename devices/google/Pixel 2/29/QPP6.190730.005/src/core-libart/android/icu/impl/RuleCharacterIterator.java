/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.PatternProps;
import android.icu.impl.Utility;
import android.icu.text.SymbolTable;
import android.icu.text.UTF16;
import java.text.ParsePosition;

public class RuleCharacterIterator {
    public static final int DONE = -1;
    public static final int PARSE_ESCAPES = 2;
    public static final int PARSE_VARIABLES = 1;
    public static final int SKIP_WHITESPACE = 4;
    private char[] buf;
    private int bufPos;
    private boolean isEscaped;
    private ParsePosition pos;
    private SymbolTable sym;
    private String text;

    public RuleCharacterIterator(String string, SymbolTable symbolTable, ParsePosition parsePosition) {
        if (string != null && parsePosition.getIndex() <= string.length()) {
            this.text = string;
            this.sym = symbolTable;
            this.pos = parsePosition;
            this.buf = null;
            return;
        }
        throw new IllegalArgumentException();
    }

    private void _advance(int n) {
        Object object = this.buf;
        if (object != null) {
            this.bufPos += n;
            if (this.bufPos == ((Object)object).length) {
                this.buf = null;
            }
        } else {
            object = this.pos;
            ((ParsePosition)object).setIndex(((ParsePosition)object).getIndex() + n);
            if (this.pos.getIndex() > this.text.length()) {
                this.pos.setIndex(this.text.length());
            }
        }
    }

    private int _current() {
        char[] arrc = this.buf;
        if (arrc != null) {
            return UTF16.charAt(arrc, 0, arrc.length, this.bufPos);
        }
        int n = this.pos.getIndex();
        n = n < this.text.length() ? UTF16.charAt(this.text, n) : -1;
        return n;
    }

    public boolean atEnd() {
        boolean bl = this.buf == null && this.pos.getIndex() == this.text.length();
        return bl;
    }

    public Object getPos(Object object) {
        if (object == null) {
            return new Object[]{this.buf, new int[]{this.pos.getIndex(), this.bufPos}};
        }
        Object[] arrobject = (Object[])object;
        arrobject[0] = this.buf;
        arrobject = (int[])arrobject[1];
        arrobject[0] = this.pos.getIndex();
        arrobject[1] = this.bufPos;
        return object;
    }

    public boolean inVariable() {
        boolean bl = this.buf != null;
        return bl;
    }

    public boolean isEscaped() {
        return this.isEscaped;
    }

    public void jumpahead(int n) {
        block5 : {
            block9 : {
                block8 : {
                    block6 : {
                        block7 : {
                            if (n < 0) break block5;
                            char[] arrc = this.buf;
                            if (arrc == null) break block6;
                            this.bufPos += n;
                            n = this.bufPos;
                            if (n > arrc.length) break block7;
                            if (n == arrc.length) {
                                this.buf = null;
                            }
                            break block8;
                        }
                        throw new IllegalArgumentException();
                    }
                    n = this.pos.getIndex() + n;
                    this.pos.setIndex(n);
                    if (n > this.text.length()) break block9;
                }
                return;
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    public String lookahead() {
        char[] arrc = this.buf;
        if (arrc != null) {
            int n = this.bufPos;
            return new String(arrc, n, arrc.length - n);
        }
        return this.text.substring(this.pos.getIndex());
    }

    public int next(int n) {
        int n2;
        block7 : {
            int n3;
            Object object;
            this.isEscaped = false;
            do {
                n3 = this._current();
                this._advance(UTF16.getCharCount(n3));
                if (n3 == 36 && this.buf == null && (n & 1) != 0 && (object = this.sym) != null) {
                    Object object2 = this.text;
                    if ((object = object.parseReference((String)object2, this.pos, ((String)object2).length())) == null) {
                        n2 = n3;
                        break block7;
                    }
                    this.bufPos = 0;
                    this.buf = this.sym.lookup((String)object);
                    object2 = this.buf;
                    if (object2 != null) {
                        if (((char[])object2).length != 0) continue;
                        this.buf = null;
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Undefined variable: ");
                    ((StringBuilder)object2).append((String)object);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
                if ((n & 4) == 0 || !PatternProps.isWhiteSpace(n3)) break;
            } while (true);
            n2 = n3;
            if (n3 == 92) {
                n2 = n3;
                if ((n & 2) != 0) {
                    object = new int[]{0};
                    n2 = Utility.unescapeAt(this.lookahead(), object);
                    this.jumpahead(object[0]);
                    this.isEscaped = true;
                    if (n2 < 0) {
                        throw new IllegalArgumentException("Invalid escape");
                    }
                }
            }
        }
        return n2;
    }

    public void setPos(Object arrobject) {
        arrobject = arrobject;
        this.buf = (char[])arrobject[0];
        arrobject = (int[])arrobject[1];
        this.pos.setIndex((int)arrobject[0]);
        this.bufPos = (int)arrobject[1];
    }

    public void skipIgnored(int n) {
        if ((n & 4) != 0) {
            while (PatternProps.isWhiteSpace(n = this._current())) {
                this._advance(UTF16.getCharCount(n));
            }
        }
    }

    public String toString() {
        int n = this.pos.getIndex();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.text.substring(0, n));
        stringBuilder.append('|');
        stringBuilder.append(this.text.substring(n));
        return stringBuilder.toString();
    }
}

