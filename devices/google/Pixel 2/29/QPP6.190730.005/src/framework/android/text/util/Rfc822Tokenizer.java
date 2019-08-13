/*
 * Decompiled with CFR 0.145.
 */
package android.text.util;

import android.text.util.Rfc822Token;
import android.widget.MultiAutoCompleteTextView;
import java.util.ArrayList;
import java.util.Collection;

public class Rfc822Tokenizer
implements MultiAutoCompleteTextView.Tokenizer {
    private static void crunch(StringBuilder stringBuilder) {
        int n = 0;
        int n2 = stringBuilder.length();
        while (n < n2) {
            if (stringBuilder.charAt(n) == '\u0000') {
                if (n != 0 && n != n2 - 1 && stringBuilder.charAt(n - 1) != ' ' && stringBuilder.charAt(n - 1) != '\u0000' && stringBuilder.charAt(n + 1) != ' ' && stringBuilder.charAt(n + 1) != '\u0000') {
                    ++n;
                    continue;
                }
                stringBuilder.deleteCharAt(n);
                --n2;
                continue;
            }
            ++n;
        }
        for (n = 0; n < n2; ++n) {
            if (stringBuilder.charAt(n) != '\u0000') continue;
            stringBuilder.setCharAt(n, ' ');
        }
    }

    public static void tokenize(CharSequence charSequence, Collection<Rfc822Token> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        int n = 0;
        int n2 = charSequence.length();
        block0 : while (n < n2) {
            char c = charSequence.charAt(n);
            if (c != ',' && c != ';') {
                int n3;
                if (c == '\"') {
                    n3 = n + 1;
                    do {
                        n = ++n3;
                        if (n3 >= n2) continue block0;
                        c = charSequence.charAt(n3);
                        if (c == '\"') {
                            n = n3 + 1;
                            continue block0;
                        }
                        if (c == '\\') {
                            if (n3 + 1 < n2) {
                                stringBuilder.append(charSequence.charAt(n3 + 1));
                            }
                            n3 += 2;
                            continue;
                        }
                        stringBuilder.append(c);
                    } while (true);
                }
                if (c == '(') {
                    n3 = 1;
                    ++n;
                    while (n < n2 && n3 > 0) {
                        c = charSequence.charAt(n);
                        if (c == ')') {
                            if (n3 > 1) {
                                stringBuilder3.append(c);
                            }
                            --n3;
                            ++n;
                            continue;
                        }
                        if (c == '(') {
                            stringBuilder3.append(c);
                            ++n3;
                            ++n;
                            continue;
                        }
                        if (c == '\\') {
                            if (n + 1 < n2) {
                                stringBuilder3.append(charSequence.charAt(n + 1));
                            }
                            n += 2;
                            continue;
                        }
                        stringBuilder3.append(c);
                        ++n;
                    }
                    continue;
                }
                if (c == '<') {
                    n3 = n + 1;
                    do {
                        n = ++n3;
                        if (n3 >= n2) continue block0;
                        c = charSequence.charAt(n3);
                        if (c == '>') {
                            n = n3 + 1;
                            continue block0;
                        }
                        stringBuilder2.append(c);
                    } while (true);
                }
                if (c == ' ') {
                    stringBuilder.append('\u0000');
                    ++n;
                    continue;
                }
                stringBuilder.append(c);
                ++n;
                continue;
            }
            ++n;
            while (n < n2 && charSequence.charAt(n) == ' ') {
                ++n;
            }
            Rfc822Tokenizer.crunch(stringBuilder);
            if (stringBuilder2.length() > 0) {
                collection.add(new Rfc822Token(stringBuilder.toString(), stringBuilder2.toString(), stringBuilder3.toString()));
            } else if (stringBuilder.length() > 0) {
                collection.add(new Rfc822Token(null, stringBuilder.toString(), stringBuilder3.toString()));
            }
            stringBuilder.setLength(0);
            stringBuilder2.setLength(0);
            stringBuilder3.setLength(0);
        }
        Rfc822Tokenizer.crunch(stringBuilder);
        if (stringBuilder2.length() > 0) {
            collection.add(new Rfc822Token(stringBuilder.toString(), stringBuilder2.toString(), stringBuilder3.toString()));
        } else if (stringBuilder.length() > 0) {
            collection.add(new Rfc822Token(null, stringBuilder.toString(), stringBuilder3.toString()));
        }
    }

    public static Rfc822Token[] tokenize(CharSequence charSequence) {
        ArrayList<Rfc822Token> arrayList = new ArrayList<Rfc822Token>();
        Rfc822Tokenizer.tokenize(charSequence, arrayList);
        return arrayList.toArray(new Rfc822Token[arrayList.size()]);
    }

    @Override
    public int findTokenEnd(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        block0 : while (n < n2) {
            int n3 = charSequence.charAt(n);
            if (n3 != 44 && n3 != 59) {
                if (n3 == 34) {
                    n3 = n + 1;
                    do {
                        n = n3;
                        if (n3 >= n2) continue block0;
                        n = charSequence.charAt(n3);
                        if (n == 34) {
                            n = n3 + 1;
                            continue block0;
                        }
                        if (n == 92 && n3 + 1 < n2) {
                            n3 += 2;
                            continue;
                        }
                        ++n3;
                    } while (true);
                }
                if (n3 == 40) {
                    n3 = 1;
                    ++n;
                    while (n < n2 && n3 > 0) {
                        char c = charSequence.charAt(n);
                        if (c == ')') {
                            --n3;
                            ++n;
                            continue;
                        }
                        if (c == '(') {
                            ++n3;
                            ++n;
                            continue;
                        }
                        if (c == '\\' && n + 1 < n2) {
                            n += 2;
                            continue;
                        }
                        ++n;
                    }
                    continue;
                }
                if (n3 == 60) {
                    n3 = n + 1;
                    do {
                        n = ++n3;
                        if (n3 >= n2) continue block0;
                        if (charSequence.charAt(n3) != '>') continue;
                        n = n3 + 1;
                        continue block0;
                    } while (true);
                }
                ++n;
                continue;
            }
            return n;
        }
        return n;
    }

    @Override
    public int findTokenStart(CharSequence charSequence, int n) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            int n4;
            n3 = n4 = this.findTokenEnd(charSequence, n3);
            if (n4 >= n) continue;
            ++n4;
            while (n4 < n && charSequence.charAt(n4) == ' ') {
                ++n4;
            }
            n3 = n4;
            if (n4 >= n) continue;
            n2 = n4;
            n3 = n4;
        }
        return n2;
    }

    @Override
    public CharSequence terminateToken(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)charSequence);
        stringBuilder.append(", ");
        return stringBuilder.toString();
    }
}

