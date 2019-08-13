/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.ParseException;

class PatternEntry {
    static final int RESET = -2;
    static final int UNSET = -1;
    String chars;
    String extension;
    int strength = -1;

    PatternEntry(int n, StringBuffer charSequence, StringBuffer stringBuffer) {
        String string = "";
        this.chars = "";
        this.extension = "";
        this.strength = n;
        this.chars = charSequence.toString();
        charSequence = stringBuffer.length() > 0 ? stringBuffer.toString() : string;
        this.extension = charSequence;
    }

    static void appendQuoted(String string, StringBuffer stringBuffer) {
        boolean bl;
        block3 : {
            block5 : {
                block6 : {
                    char c;
                    block4 : {
                        block2 : {
                            bl = false;
                            c = string.charAt(0);
                            if (!Character.isSpaceChar(c)) break block2;
                            bl = true;
                            stringBuffer.append('\'');
                            break block3;
                        }
                        if (!PatternEntry.isSpecialChar(c)) break block4;
                        bl = true;
                        stringBuffer.append('\'');
                        break block3;
                    }
                    if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == '\u0010') break block5;
                    if (c == '\'') break block6;
                    if (c == '@') break block5;
                    if (false) {
                        bl = false;
                        stringBuffer.append('\'');
                    }
                    break block3;
                }
                bl = true;
                stringBuffer.append('\'');
                break block3;
            }
            bl = true;
            stringBuffer.append('\'');
        }
        stringBuffer.append(string);
        if (bl) {
            stringBuffer.append('\'');
        }
    }

    static boolean isSpecialChar(char c) {
        boolean bl = c == ' ' || c <= '/' && c >= '\"' || c <= '?' && c >= ':' || c <= '`' && c >= '[' || c <= '~' && c >= '{';
        return bl;
    }

    void addToBuffer(StringBuffer stringBuffer, boolean bl, boolean bl2, PatternEntry patternEntry) {
        int n;
        if (bl2 && stringBuffer.length() > 0) {
            if (this.strength != 0 && patternEntry == null) {
                stringBuffer.append(' ');
            } else {
                stringBuffer.append('\n');
            }
        }
        if (patternEntry != null) {
            stringBuffer.append('&');
            if (bl2) {
                stringBuffer.append(' ');
            }
            patternEntry.appendQuotedChars(stringBuffer);
            this.appendQuotedExtension(stringBuffer);
            if (bl2) {
                stringBuffer.append(' ');
            }
        }
        if ((n = this.strength) != -2) {
            if (n != -1) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n == 3) {
                                stringBuffer.append('=');
                            }
                        } else {
                            stringBuffer.append(',');
                        }
                    } else {
                        stringBuffer.append(';');
                    }
                } else {
                    stringBuffer.append('<');
                }
            } else {
                stringBuffer.append('?');
            }
        } else {
            stringBuffer.append('&');
        }
        if (bl2) {
            stringBuffer.append(' ');
        }
        PatternEntry.appendQuoted(this.chars, stringBuffer);
        if (bl && this.extension.length() != 0) {
            stringBuffer.append('/');
            PatternEntry.appendQuoted(this.extension, stringBuffer);
        }
    }

    public void appendQuotedChars(StringBuffer stringBuffer) {
        PatternEntry.appendQuoted(this.chars, stringBuffer);
    }

    public void appendQuotedExtension(StringBuffer stringBuffer) {
        PatternEntry.appendQuoted(this.extension, stringBuffer);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        object = (PatternEntry)object;
        return this.chars.equals(((PatternEntry)object).chars);
    }

    final String getChars() {
        return this.chars;
    }

    final String getExtension() {
        return this.extension;
    }

    final int getStrength() {
        return this.strength;
    }

    public int hashCode() {
        return this.chars.hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        this.addToBuffer(stringBuffer, true, false, null);
        return stringBuffer.toString();
    }

    static class Parser {
        private int i;
        private StringBuffer newChars = new StringBuffer();
        private StringBuffer newExtension = new StringBuffer();
        private String pattern;

        public Parser(String string) {
            this.pattern = string;
            this.i = 0;
        }

        public PatternEntry next() throws ParseException {
            CharSequence charSequence;
            int n = -1;
            this.newChars.setLength(0);
            this.newExtension.setLength(0);
            boolean bl = true;
            int n2 = 0;
            block5 : while (this.i < this.pattern.length()) {
                char c = this.pattern.charAt(this.i);
                if (n2 != 0) {
                    if (c == '\'') {
                        n2 = 0;
                    } else if (this.newChars.length() == 0) {
                        this.newChars.append(c);
                    } else if (bl) {
                        this.newChars.append(c);
                    } else {
                        this.newExtension.append(c);
                    }
                } else if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                    if (c != ',') {
                        if (c != '/') {
                            if (c != '&') {
                                if (c != '\'') {
                                    switch (c) {
                                        default: {
                                            if (n == -1) {
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("missing char (=,;<&) : ");
                                                charSequence = this.pattern;
                                                n2 = this.i;
                                                n = n2 + 10 < ((String)charSequence).length() ? this.i + 10 : this.pattern.length();
                                                stringBuilder.append(((String)charSequence).substring(n2, n));
                                                throw new ParseException(stringBuilder.toString(), this.i);
                                            }
                                            if (PatternEntry.isSpecialChar(c) && n2 == 0) {
                                                charSequence = new StringBuilder();
                                                ((StringBuilder)charSequence).append("Unquoted punctuation character : ");
                                                ((StringBuilder)charSequence).append(Integer.toString(c, 16));
                                                throw new ParseException(((StringBuilder)charSequence).toString(), this.i);
                                            }
                                            if (bl) {
                                                this.newChars.append(c);
                                                break;
                                            }
                                            this.newExtension.append(c);
                                            break;
                                        }
                                        case '=': {
                                            if (n != -1) break block5;
                                            n = 3;
                                            break;
                                        }
                                        case '<': {
                                            if (n != -1) break block5;
                                            n = 0;
                                            break;
                                        }
                                        case ';': {
                                            if (n != -1) break block5;
                                            n = 1;
                                            break;
                                        }
                                    }
                                } else {
                                    int n3;
                                    n2 = 1;
                                    charSequence = this.pattern;
                                    this.i = n3 = this.i + 1;
                                    c = ((String)charSequence).charAt(n3);
                                    if (this.newChars.length() == 0) {
                                        this.newChars.append(c);
                                    } else if (bl) {
                                        this.newChars.append(c);
                                    } else {
                                        this.newExtension.append(c);
                                    }
                                }
                            } else {
                                if (n != -1) break;
                                n = -2;
                            }
                        } else {
                            bl = false;
                        }
                    } else {
                        if (n != -1) break;
                        n = 2;
                    }
                }
                ++this.i;
            }
            if (n == -1) {
                return null;
            }
            if (this.newChars.length() == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("missing chars (=,;<&): ");
                charSequence = this.pattern;
                n2 = this.i;
                n = n2 + 10 < ((String)charSequence).length() ? this.i + 10 : this.pattern.length();
                stringBuilder.append(((String)charSequence).substring(n2, n));
                throw new ParseException(stringBuilder.toString(), this.i);
            }
            return new PatternEntry(n, this.newChars, this.newExtension);
        }
    }

}

