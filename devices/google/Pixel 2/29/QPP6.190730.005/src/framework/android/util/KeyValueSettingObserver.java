/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.KeyValueListParser;
import android.util.Slog;

public abstract class KeyValueSettingObserver {
    private static final String TAG = "KeyValueSettingObserver";
    private final ContentObserver mObserver;
    private final KeyValueListParser mParser = new KeyValueListParser(',');
    private final ContentResolver mResolver;
    private final Uri mSettingUri;

    public KeyValueSettingObserver(Handler handler, ContentResolver contentResolver, Uri uri) {
        this.mObserver = new SettingObserver(handler);
        this.mResolver = contentResolver;
        this.mSettingUri = uri;
    }

    private void setParserValue() {
        String string2 = this.getSettingValue(this.mResolver);
        try {
            this.mParser.setString(string2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed setting: ");
            stringBuilder.append(string2);
            Slog.e(TAG, stringBuilder.toString());
        }
    }

    public abstract String getSettingValue(ContentResolver var1);

    public void start() {
        this.mResolver.registerContentObserver(this.mSettingUri, false, this.mObserver);
        this.setParserValue();
        this.update(this.mParser);
    }

    public void stop() {
        this.mResolver.unregisterContentObserver(this.mObserver);
    }

    public abstract void update(KeyValueListParser var1);

    private class SettingObserver
    extends ContentObserver {
        private SettingObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean bl) {
            KeyValueSettingObserver.this.setParserValue();
            KeyValueSettingObserver keyValueSettingObserver = KeyValueSettingObserver.this;
            keyValueSettingObserver.update(keyValueSettingObserver.mParser);
        }
    }

}

