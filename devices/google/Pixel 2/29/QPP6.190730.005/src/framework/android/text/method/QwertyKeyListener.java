/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.content.Context;
import android.text.AutoText;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.BaseKeyListener;
import android.text.method.CharacterPickerDialog;
import android.text.method.TextKeyListener;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;

public class QwertyKeyListener
extends BaseKeyListener {
    private static SparseArray<String> PICKER_SETS;
    private static QwertyKeyListener sFullKeyboardInstance;
    private static QwertyKeyListener[] sInstance;
    private TextKeyListener.Capitalize mAutoCap;
    private boolean mAutoText;
    private boolean mFullKeyboard;

    static {
        sInstance = new QwertyKeyListener[TextKeyListener.Capitalize.values().length * 2];
        PICKER_SETS = new SparseArray();
        PICKER_SETS.put(65, "\u00c0\u00c1\u00c2\u00c4\u00c6\u00c3\u00c5\u0104\u0100");
        PICKER_SETS.put(67, "\u00c7\u0106\u010c");
        PICKER_SETS.put(68, "\u010e");
        PICKER_SETS.put(69, "\u00c8\u00c9\u00ca\u00cb\u0118\u011a\u0112");
        PICKER_SETS.put(71, "\u011e");
        PICKER_SETS.put(76, "\u0141");
        PICKER_SETS.put(73, "\u00cc\u00cd\u00ce\u00cf\u012a\u0130");
        PICKER_SETS.put(78, "\u00d1\u0143\u0147");
        PICKER_SETS.put(79, "\u00d8\u0152\u00d5\u00d2\u00d3\u00d4\u00d6\u014c");
        PICKER_SETS.put(82, "\u0158");
        PICKER_SETS.put(83, "\u015a\u0160\u015e");
        PICKER_SETS.put(84, "\u0164");
        PICKER_SETS.put(85, "\u00d9\u00da\u00db\u00dc\u016e\u016a");
        PICKER_SETS.put(89, "\u00dd\u0178");
        PICKER_SETS.put(90, "\u0179\u017b\u017d");
        PICKER_SETS.put(97, "\u00e0\u00e1\u00e2\u00e4\u00e6\u00e3\u00e5\u0105\u0101");
        PICKER_SETS.put(99, "\u00e7\u0107\u010d");
        PICKER_SETS.put(100, "\u010f");
        PICKER_SETS.put(101, "\u00e8\u00e9\u00ea\u00eb\u0119\u011b\u0113");
        PICKER_SETS.put(103, "\u011f");
        PICKER_SETS.put(105, "\u00ec\u00ed\u00ee\u00ef\u012b\u0131");
        PICKER_SETS.put(108, "\u0142");
        PICKER_SETS.put(110, "\u00f1\u0144\u0148");
        PICKER_SETS.put(111, "\u00f8\u0153\u00f5\u00f2\u00f3\u00f4\u00f6\u014d");
        PICKER_SETS.put(114, "\u0159");
        PICKER_SETS.put(115, "\u00a7\u00df\u015b\u0161\u015f");
        PICKER_SETS.put(116, "\u0165");
        PICKER_SETS.put(117, "\u00f9\u00fa\u00fb\u00fc\u016f\u016b");
        PICKER_SETS.put(121, "\u00fd\u00ff");
        PICKER_SETS.put(122, "\u017a\u017c\u017e");
        PICKER_SETS.put(61185, "\u2026\u00a5\u2022\u00ae\u00a9\u00b1[]{}\\|");
        PICKER_SETS.put(47, "\\");
        PICKER_SETS.put(49, "\u00b9\u00bd\u2153\u00bc\u215b");
        PICKER_SETS.put(50, "\u00b2\u2154");
        PICKER_SETS.put(51, "\u00b3\u00be\u215c");
        PICKER_SETS.put(52, "\u2074");
        PICKER_SETS.put(53, "\u215d");
        PICKER_SETS.put(55, "\u215e");
        PICKER_SETS.put(48, "\u207f\u2205");
        PICKER_SETS.put(36, "\u00a2\u00a3\u20ac\u00a5\u20a3\u20a4\u20b1");
        PICKER_SETS.put(37, "\u2030");
        PICKER_SETS.put(42, "\u2020\u2021");
        PICKER_SETS.put(45, "\u2013\u2014");
        PICKER_SETS.put(43, "\u00b1");
        PICKER_SETS.put(40, "[{<");
        PICKER_SETS.put(41, "]}>");
        PICKER_SETS.put(33, "\u00a1");
        PICKER_SETS.put(34, "\u201c\u201d\u00ab\u00bb\u02dd");
        PICKER_SETS.put(63, "\u00bf");
        PICKER_SETS.put(44, "\u201a\u201e");
        PICKER_SETS.put(61, "\u2260\u2248\u221e");
        PICKER_SETS.put(60, "\u2264\u00ab\u2039");
        PICKER_SETS.put(62, "\u2265\u00bb\u203a");
    }

    public QwertyKeyListener(TextKeyListener.Capitalize capitalize, boolean bl) {
        this(capitalize, bl, false);
    }

    private QwertyKeyListener(TextKeyListener.Capitalize capitalize, boolean bl, boolean bl2) {
        this.mAutoCap = capitalize;
        this.mAutoText = bl;
        this.mFullKeyboard = bl2;
    }

    public static QwertyKeyListener getInstance(boolean bl, TextKeyListener.Capitalize capitalize) {
        QwertyKeyListener[] arrqwertyKeyListener = sInstance;
        int n = capitalize.ordinal() * 2 + bl;
        if (arrqwertyKeyListener[n] == null) {
            arrqwertyKeyListener[n] = new QwertyKeyListener(capitalize, bl);
        }
        return sInstance[n];
    }

    public static QwertyKeyListener getInstanceForFullKeyboard() {
        if (sFullKeyboardInstance == null) {
            sFullKeyboardInstance = new QwertyKeyListener(TextKeyListener.Capitalize.NONE, false, true);
        }
        return sFullKeyboardInstance;
    }

    private String getReplacement(CharSequence charSequence, int n, int n2, View object) {
        int n3 = n2 - n;
        int n4 = 0;
        String string2 = AutoText.get(charSequence, n, n2, (View)object);
        Object object2 = string2;
        if (string2 == null) {
            object = AutoText.get(TextUtils.substring(charSequence, n, n2).toLowerCase(), 0, n2 - n, (View)object);
            n4 = 1;
            object2 = object;
            if (object == null) {
                return null;
            }
        }
        int n5 = 0;
        int n6 = 0;
        if (n4 != 0) {
            int n7 = n;
            n4 = n6;
            do {
                n5 = n4;
                if (n7 >= n2) break;
                n5 = n4;
                if (Character.isUpperCase(charSequence.charAt(n7))) {
                    n5 = n4 + 1;
                }
                ++n7;
                n4 = n5;
            } while (true);
        }
        if (((String)(object = n5 == 0 ? object2 : (n5 == 1 ? QwertyKeyListener.toTitleCase((String)object2) : (n5 == n3 ? ((String)object2).toUpperCase() : QwertyKeyListener.toTitleCase((String)object2))))).length() == n3 && TextUtils.regionMatches(charSequence, n, (CharSequence)object, 0, n3)) {
            return null;
        }
        return object;
    }

    public static void markAsReplaced(Spannable spannable, int n, int n2, String string2) {
        int n3;
        Object[] arrobject = spannable.getSpans(0, spannable.length(), Replaced.class);
        for (n3 = 0; n3 < arrobject.length; ++n3) {
            spannable.removeSpan(arrobject[n3]);
        }
        n3 = string2.length();
        arrobject = new char[n3];
        string2.getChars(0, n3, (char[])arrobject, 0);
        spannable.setSpan(new Replaced((char[])arrobject), n, n2, 33);
    }

    private boolean showCharacterPicker(View view, Editable editable, char c, boolean bl, int n) {
        String string2 = PICKER_SETS.get(c);
        if (string2 == null) {
            return false;
        }
        if (n == 1) {
            new CharacterPickerDialog(view.getContext(), view, editable, string2, bl).show();
        }
        return true;
    }

    private static String toTitleCase(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(string2.charAt(0)));
        stringBuilder.append(string2.substring(1));
        return stringBuilder.toString();
    }

    @Override
    public int getInputType() {
        return QwertyKeyListener.makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    @Override
    public boolean onKeyDown(View object, Editable editable, int n, KeyEvent arrobject) {
        int n2;
        int n3;
        char c;
        int n4 = object != null ? TextKeyListener.getInstance().getPrefs(((View)object).getContext()) : 0;
        int n5 = Selection.getSelectionStart(editable);
        int n6 = Selection.getSelectionEnd(editable);
        char c2 = Math.min(n5, n6);
        n5 = Math.max(n5, n6);
        if (c2 < '\u0000' || n5 < 0) {
            Selection.setSelection(editable, 0, 0);
            c2 = '\u0000';
            n5 = 0;
        }
        int n7 = editable.getSpanStart(TextKeyListener.ACTIVE);
        int n8 = editable.getSpanEnd(TextKeyListener.ACTIVE);
        n6 = arrobject.getUnicodeChar(QwertyKeyListener.getMetaState((CharSequence)editable, (KeyEvent)arrobject));
        if (!(this.mFullKeyboard || (n3 = arrobject.getRepeatCount()) <= 0 || c2 != n5 || c2 <= '\u0000' || (c = editable.charAt(c2 - 1)) != n6 && c != Character.toUpperCase(n6))) {
            if (object != null && this.showCharacterPicker((View)object, editable, c, false, n3)) {
                QwertyKeyListener.resetMetaState(editable);
                return true;
            }
        }
        if (n6 == 61185) {
            if (object != null) {
                this.showCharacterPicker((View)object, editable, '\uef01', true, 1);
            }
            QwertyKeyListener.resetMetaState(editable);
            return true;
        }
        if (n6 == 61184) {
            if (c2 == n5) {
                n3 = n5;
                do {
                    n6 = n3;
                    if (n3 > 0) {
                        n6 = n3;
                        if (n5 - n3 < 4) {
                            n6 = n3;
                            if (Character.digit(editable.charAt(n3 - 1), 16) >= 0) {
                                --n3;
                                continue;
                            }
                        }
                    }
                    break;
                } while (true);
            } else {
                n6 = c2;
            }
            n2 = -1;
            try {
                n3 = Integer.parseInt(TextUtils.substring(editable, n6, n5), 16);
            }
            catch (NumberFormatException numberFormatException) {
                n3 = n2;
            }
            if (n3 >= 0) {
                c2 = n6;
                Selection.setSelection(editable, c2, n5);
                n6 = n3;
                n3 = c2;
            } else {
                n6 = 0;
                n3 = c2;
            }
        } else {
            n3 = c2;
        }
        if (n6 != 0) {
            c2 = '\u0000';
            n = n6;
            if ((Integer.MIN_VALUE & n6) != 0) {
                c2 = '\u0001';
                n = n6 & Integer.MAX_VALUE;
            }
            char c3 = c2;
            n6 = n;
            int n9 = n3;
            if (n7 == n3) {
                c3 = c2;
                n6 = n;
                n9 = n3;
                if (n8 == n5) {
                    c3 = '\u0000';
                    n7 = c2;
                    n8 = c3;
                    n2 = n;
                    if (n5 - n3 - 1 == 0) {
                        n6 = KeyEvent.getDeadChar(editable.charAt(n3), n);
                        n7 = c2;
                        n8 = c3;
                        n2 = n;
                        if (n6 != 0) {
                            n2 = n6;
                            n8 = 1;
                            n7 = 0;
                        }
                    }
                    c3 = n7;
                    n6 = n2;
                    n9 = n3;
                    if (n8 == 0) {
                        Selection.setSelection(editable, n5);
                        editable.removeSpan(TextKeyListener.ACTIVE);
                        n9 = n5;
                        n6 = n2;
                        c3 = n7;
                    }
                }
            }
            n = n6;
            if ((n4 & 1) != 0) {
                n = n6;
                if (Character.isLowerCase(n6)) {
                    n = n6;
                    if (TextKeyListener.shouldCap(this.mAutoCap, editable, n9)) {
                        n = editable.getSpanEnd(TextKeyListener.CAPPED);
                        c2 = editable.getSpanFlags(TextKeyListener.CAPPED);
                        if (n == n9 && (c2 >> 16 & 65535) == n6) {
                            editable.removeSpan(TextKeyListener.CAPPED);
                            n = n6;
                        } else {
                            c2 = n6 << 16;
                            n = Character.toUpperCase(n6);
                            if (n9 == 0) {
                                editable.setSpan(TextKeyListener.CAPPED, 0, 0, c2 | 17);
                            } else {
                                editable.setSpan(TextKeyListener.CAPPED, n9 - 1, n9, c2 | 33);
                            }
                        }
                    }
                }
            }
            if (n9 != n5) {
                Selection.setSelection(editable, n5);
            }
            editable.setSpan(OLD_SEL_START, n9, n9, 17);
            editable.replace(n9, n5, String.valueOf((char)n));
            c2 = editable.getSpanStart(OLD_SEL_START);
            n5 = Selection.getSelectionEnd(editable);
            if (c2 < n5) {
                editable.setSpan(TextKeyListener.LAST_TYPED, c2, n5, 33);
                if (c3 != '\u0000') {
                    Selection.setSelection(editable, c2, n5);
                    editable.setSpan(TextKeyListener.ACTIVE, c2, n5, 33);
                }
            }
            QwertyKeyListener.adjustMetaAfterKeypress(editable);
            if ((n4 & 2) != 0 && this.mAutoText && (n == 32 || n == 9 || n == 10 || n == 44 || n == 46 || n == 33 || n == 63 || n == 34 || Character.getType(n) == 22)) {
                if (editable.getSpanEnd(TextKeyListener.INHIBIT_REPLACEMENT) != c2) {
                    for (n = c2; n > 0 && ((c = editable.charAt(n - 1)) == '\'' || Character.isLetter(c)); --n) {
                    }
                    if ((object = this.getReplacement(editable, n, c2, (View)object)) != null) {
                        arrobject = editable.getSpans(0, editable.length(), Replaced.class);
                        for (n5 = 0; n5 < arrobject.length; ++n5) {
                            editable.removeSpan(arrobject[n5]);
                        }
                        arrobject = new char[c2 - n];
                        TextUtils.getChars(editable, n, c2, (char[])arrobject, 0);
                        editable.setSpan(new Replaced((char[])arrobject), n, c2, 33);
                        editable.replace(n, c2, (CharSequence)object);
                    }
                }
            }
            if ((n4 & 4) != 0 && this.mAutoText && (n5 = Selection.getSelectionEnd(editable)) - 3 >= 0 && editable.charAt(n5 - 1) == ' ' && editable.charAt(n5 - 2) == ' ') {
                c2 = editable.charAt(n5 - 3);
                c = c2;
                for (n = n5 - 3; n > 0 && (c == '\"' || Character.getType(c) == 22); --n) {
                    c2 = editable.charAt(n - 1);
                    c = c2;
                }
                if (Character.isLetter(c) || Character.isDigit(c)) {
                    editable.replace(n5 - 2, n5 - 1, ".");
                }
            }
            return true;
        }
        if (n == 67 && (arrobject.hasNoModifiers() || arrobject.hasModifiers(2)) && n3 == n5) {
            Replaced[] arrreplaced;
            c2 = '\u0001';
            n5 = c2;
            if (editable.getSpanEnd(TextKeyListener.LAST_TYPED) == n3) {
                n5 = c2;
                if (editable.charAt(n3 - 1) != '\n') {
                    n5 = 2;
                }
            }
            if ((arrreplaced = editable.getSpans(n3 - n5, n3, Replaced.class)).length > 0) {
                n5 = editable.getSpanStart(arrreplaced[0]);
                c2 = editable.getSpanEnd(arrreplaced[0]);
                String string2 = new String(arrreplaced[0].mText);
                editable.removeSpan(arrreplaced[0]);
                if (n3 >= c2) {
                    editable.setSpan(TextKeyListener.INHIBIT_REPLACEMENT, c2, c2, 34);
                    editable.replace(n5, c2, string2);
                    n = editable.getSpanStart(TextKeyListener.INHIBIT_REPLACEMENT);
                    if (n - 1 >= 0) {
                        editable.setSpan(TextKeyListener.INHIBIT_REPLACEMENT, n - 1, n, 33);
                    } else {
                        editable.removeSpan(TextKeyListener.INHIBIT_REPLACEMENT);
                    }
                    QwertyKeyListener.adjustMetaAfterKeypress(editable);
                    return true;
                }
                QwertyKeyListener.adjustMetaAfterKeypress(editable);
                return super.onKeyDown((View)object, editable, n, (KeyEvent)arrobject);
            }
        }
        return super.onKeyDown((View)object, editable, n, (KeyEvent)arrobject);
    }

    static class Replaced
    implements NoCopySpan {
        private char[] mText;

        public Replaced(char[] arrc) {
            this.mText = arrc;
        }
    }

}

