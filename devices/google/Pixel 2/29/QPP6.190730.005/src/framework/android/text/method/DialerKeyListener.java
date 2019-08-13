/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.Spannable;
import android.text.method.NumberKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

public class DialerKeyListener
extends NumberKeyListener {
    public static final char[] CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '#', '*', '+', '-', '(', ')', ',', '/', 'N', '.', ' ', ';'};
    private static DialerKeyListener sInstance;

    public static DialerKeyListener getInstance() {
        DialerKeyListener dialerKeyListener = sInstance;
        if (dialerKeyListener != null) {
            return dialerKeyListener;
        }
        sInstance = new DialerKeyListener();
        return sInstance;
    }

    @Override
    protected char[] getAcceptedChars() {
        return CHARACTERS;
    }

    @Override
    public int getInputType() {
        return 3;
    }

    @Override
    protected int lookup(KeyEvent keyEvent, Spannable arrc) {
        int n = DialerKeyListener.getMetaState((CharSequence)arrc, keyEvent);
        char c = keyEvent.getNumber();
        if ((n & 3) == 0 && c != '\u0000') {
            return c;
        }
        int n2 = super.lookup(keyEvent, (Spannable)arrc);
        if (n2 != 0) {
            return n2;
        }
        if (n != 0) {
            KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
            arrc = this.getAcceptedChars();
            if (keyEvent.getKeyData(keyData)) {
                for (n = 1; n < keyData.meta.length; ++n) {
                    if (!DialerKeyListener.ok(arrc, keyData.meta[n])) continue;
                    return keyData.meta[n];
                }
            }
        }
        return c;
    }
}

