/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.CharArrayBuffer;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;

public class TranslatingCursor
extends CrossProcessCursorWrapper {
    private final int mAuxiliaryColumnIndex;
    private final Config mConfig;
    private final boolean mDropLast;
    private final ArraySet<Integer> mTranslateColumnIndices;
    private final Translator mTranslator;

    public TranslatingCursor(Cursor cursor, Config config, Translator object, boolean bl) {
        super(cursor);
        this.mConfig = Objects.requireNonNull(config);
        this.mTranslator = Objects.requireNonNull(object);
        this.mDropLast = bl;
        this.mAuxiliaryColumnIndex = cursor.getColumnIndexOrThrow(config.auxiliaryColumn);
        this.mTranslateColumnIndices = new ArraySet();
        for (int i = 0; i < cursor.getColumnCount(); ++i) {
            object = cursor.getColumnName(i);
            if (!ArrayUtils.contains(config.translateColumns, object)) continue;
            this.mTranslateColumnIndices.add(i);
        }
    }

    public static Cursor query(Config config, Translator translator, SQLiteQueryBuilder object, SQLiteDatabase sQLiteDatabase, String[] arrstring, String string2, String[] arrstring2, String string3, String string4, String string5, String string6, CancellationSignal cancellationSignal) {
        String[] arrstring3 = arrstring;
        boolean bl = ArrayUtils.isEmpty(arrstring);
        boolean bl2 = true;
        boolean bl3 = bl || ArrayUtils.contains(arrstring3, config.auxiliaryColumn);
        boolean bl4 = ArrayUtils.isEmpty(arrstring) || ArrayUtils.containsAny(arrstring3, config.translateColumns);
        if (!bl4) {
            return ((SQLiteQueryBuilder)object).query(sQLiteDatabase, arrstring, string2, arrstring2, string3, string4, string5, string6, cancellationSignal);
        }
        arrstring = arrstring3;
        if (!bl3) {
            arrstring = ArrayUtils.appendElement(String.class, arrstring3, config.auxiliaryColumn);
        }
        object = ((SQLiteQueryBuilder)object).query(sQLiteDatabase, arrstring, string2, arrstring2, string3, string4, string5);
        if (bl3) {
            bl2 = false;
        }
        return new TranslatingCursor((Cursor)object, config, translator, bl2);
    }

    @Override
    public void copyStringToBuffer(int n, CharArrayBuffer charArrayBuffer) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            super.copyStringToBuffer(n, charArrayBuffer);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void fillWindow(int n, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, n, cursorWindow);
    }

    @Override
    public byte[] getBlob(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getBlob(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getColumnCount() {
        if (this.mDropLast) {
            return super.getColumnCount() - 1;
        }
        return super.getColumnCount();
    }

    @Override
    public String[] getColumnNames() {
        if (this.mDropLast) {
            return Arrays.copyOfRange(super.getColumnNames(), 0, super.getColumnCount() - 1);
        }
        return super.getColumnNames();
    }

    @Override
    public double getDouble(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getDouble(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public float getFloat(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getFloat(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getInt(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getInt(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public long getLong(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getLong(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public short getShort(int n) {
        if (!ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return super.getShort(n);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getString(int n) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return this.mTranslator.translate(super.getString(n), this.mAuxiliaryColumnIndex, this.getColumnName(n), this);
        }
        return super.getString(n);
    }

    @Override
    public int getType(int n) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            return 3;
        }
        return super.getType(n);
    }

    @Override
    public CursorWindow getWindow() {
        return null;
    }

    @Override
    public Cursor getWrappedCursor() {
        throw new UnsupportedOperationException("Returning underlying cursor risks leaking data");
    }

    @Override
    public boolean isNull(int n) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(n))) {
            boolean bl = this.getString(n) == null;
            return bl;
        }
        return super.isNull(n);
    }

    public static class Config {
        public final String auxiliaryColumn;
        public final Uri baseUri;
        public final String[] translateColumns;

        public Config(Uri uri, String string2, String ... arrstring) {
            this.baseUri = uri;
            this.auxiliaryColumn = string2;
            this.translateColumns = arrstring;
        }
    }

    public static interface Translator {
        public String translate(String var1, int var2, String var3, Cursor var4);
    }

}

