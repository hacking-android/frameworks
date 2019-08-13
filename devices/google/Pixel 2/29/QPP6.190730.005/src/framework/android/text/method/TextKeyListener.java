/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.BaseKeyListener;
import android.text.method.KeyListener;
import android.text.method.MultiTapKeyListener;
import android.text.method.QwertyKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import java.lang.ref.WeakReference;

public class TextKeyListener
extends BaseKeyListener
implements SpanWatcher {
    static final Object ACTIVE;
    static final int AUTO_CAP = 1;
    static final int AUTO_PERIOD = 4;
    static final int AUTO_TEXT = 2;
    static final Object CAPPED;
    static final Object INHIBIT_REPLACEMENT;
    static final Object LAST_TYPED;
    static final int SHOW_PASSWORD = 8;
    private static TextKeyListener[] sInstance;
    private Capitalize mAutoCap;
    private boolean mAutoText;
    private SettingsObserver mObserver;
    private int mPrefs;
    private boolean mPrefsInited;
    private WeakReference<ContentResolver> mResolver;

    static {
        sInstance = new TextKeyListener[Capitalize.values().length * 2];
        ACTIVE = new NoCopySpan.Concrete();
        CAPPED = new NoCopySpan.Concrete();
        INHIBIT_REPLACEMENT = new NoCopySpan.Concrete();
        LAST_TYPED = new NoCopySpan.Concrete();
    }

    public TextKeyListener(Capitalize capitalize, boolean bl) {
        this.mAutoCap = capitalize;
        this.mAutoText = bl;
    }

    public static void clear(Editable editable) {
        editable.clear();
        editable.removeSpan(ACTIVE);
        editable.removeSpan(CAPPED);
        editable.removeSpan(INHIBIT_REPLACEMENT);
        editable.removeSpan(LAST_TYPED);
        QwertyKeyListener.Replaced[] arrreplaced = editable.getSpans(0, editable.length(), QwertyKeyListener.Replaced.class);
        int n = arrreplaced.length;
        for (int i = 0; i < n; ++i) {
            editable.removeSpan(arrreplaced[i]);
        }
    }

    public static TextKeyListener getInstance() {
        return TextKeyListener.getInstance(false, Capitalize.NONE);
    }

    public static TextKeyListener getInstance(boolean bl, Capitalize capitalize) {
        TextKeyListener[] arrtextKeyListener = sInstance;
        int n = capitalize.ordinal() * 2 + bl;
        if (arrtextKeyListener[n] == null) {
            arrtextKeyListener[n] = new TextKeyListener(capitalize, bl);
        }
        return sInstance[n];
    }

    private KeyListener getKeyListener(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCharacterMap().getKeyboardType();
        if (n == 3) {
            return QwertyKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (n == 1) {
            return MultiTapKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (n != 4 && n != 5) {
            return NullKeyListener.getInstance();
        }
        return QwertyKeyListener.getInstanceForFullKeyboard();
    }

    private void initPrefs(Context object) {
        object = ((Context)object).getContentResolver();
        this.mResolver = new WeakReference<Object>(object);
        if (this.mObserver == null) {
            this.mObserver = new SettingsObserver();
            ((ContentResolver)object).registerContentObserver(Settings.System.CONTENT_URI, true, this.mObserver);
        }
        this.updatePrefs((ContentResolver)object);
        this.mPrefsInited = true;
    }

    public static boolean shouldCap(Capitalize capitalize, CharSequence charSequence, int n) {
        Capitalize capitalize2 = Capitalize.NONE;
        boolean bl = false;
        if (capitalize == capitalize2) {
            return false;
        }
        if (capitalize == Capitalize.CHARACTERS) {
            return true;
        }
        int n2 = capitalize == Capitalize.WORDS ? 8192 : 16384;
        if (TextUtils.getCapsMode(charSequence, n, n2) != 0) {
            bl = true;
        }
        return bl;
    }

    private void updatePrefs(ContentResolver contentResolver) {
        int n = 1;
        int n2 = Settings.System.getInt(contentResolver, "auto_caps", 1);
        int n3 = 0;
        int n4 = n2 > 0 ? 1 : 0;
        int n5 = Settings.System.getInt(contentResolver, "auto_replace", 1) > 0 ? 1 : 0;
        int n6 = Settings.System.getInt(contentResolver, "auto_punctuate", 1) > 0 ? 1 : 0;
        n2 = Settings.System.getInt(contentResolver, "show_password", 1) > 0 ? 1 : 0;
        n4 = n4 != 0 ? n : 0;
        n5 = n5 != 0 ? 2 : 0;
        n6 = n6 != 0 ? 4 : 0;
        if (n2 != 0) {
            n3 = 8;
        }
        this.mPrefs = n4 | n5 | n6 | n3;
    }

    @Override
    public int getInputType() {
        return TextKeyListener.makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int getPrefs(Context context) {
        synchronized (this) {
            if (!this.mPrefsInited || this.mResolver.get() == null) {
                this.initPrefs(context);
            }
            return this.mPrefs;
        }
    }

    @Override
    public boolean onKeyDown(View view, Editable editable, int n, KeyEvent keyEvent) {
        return this.getKeyListener(keyEvent).onKeyDown(view, editable, n, keyEvent);
    }

    @Override
    public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        return this.getKeyListener(keyEvent).onKeyOther(view, editable, keyEvent);
    }

    @Override
    public boolean onKeyUp(View view, Editable editable, int n, KeyEvent keyEvent) {
        return this.getKeyListener(keyEvent).onKeyUp(view, editable, n, keyEvent);
    }

    @Override
    public void onSpanAdded(Spannable spannable, Object object, int n, int n2) {
    }

    @Override
    public void onSpanChanged(Spannable spannable, Object object, int n, int n2, int n3, int n4) {
        if (object == Selection.SELECTION_END) {
            spannable.removeSpan(ACTIVE);
        }
    }

    @Override
    public void onSpanRemoved(Spannable spannable, Object object, int n, int n2) {
    }

    public void release() {
        WeakReference<ContentResolver> weakReference = this.mResolver;
        if (weakReference != null) {
            if ((weakReference = (ContentResolver)weakReference.get()) != null) {
                ((ContentResolver)((Object)weakReference)).unregisterContentObserver(this.mObserver);
                this.mResolver.clear();
            }
            this.mObserver = null;
            this.mResolver = null;
            this.mPrefsInited = false;
        }
    }

    public static enum Capitalize {
        NONE,
        SENTENCES,
        WORDS,
        CHARACTERS;
        
    }

    private static class NullKeyListener
    implements KeyListener {
        private static NullKeyListener sInstance;

        private NullKeyListener() {
        }

        public static NullKeyListener getInstance() {
            NullKeyListener nullKeyListener = sInstance;
            if (nullKeyListener != null) {
                return nullKeyListener;
            }
            sInstance = new NullKeyListener();
            return sInstance;
        }

        @Override
        public void clearMetaKeyState(View view, Editable editable, int n) {
        }

        @Override
        public int getInputType() {
            return 0;
        }

        @Override
        public boolean onKeyDown(View view, Editable editable, int n, KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean onKeyUp(View view, Editable editable, int n, KeyEvent keyEvent) {
            return false;
        }
    }

    private class SettingsObserver
    extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override
        public void onChange(boolean bl) {
            if (TextKeyListener.this.mResolver != null) {
                ContentResolver contentResolver = (ContentResolver)TextKeyListener.this.mResolver.get();
                if (contentResolver == null) {
                    TextKeyListener.this.mPrefsInited = false;
                } else {
                    TextKeyListener.this.updatePrefs(contentResolver);
                }
            } else {
                TextKeyListener.this.mPrefsInited = false;
            }
        }
    }

}

