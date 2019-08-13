/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.util.ArrayList;

public class SelectionBuilder {
    private StringBuilder mSelection = new StringBuilder();
    private ArrayList<String> mSelectionArgs = new ArrayList();

    public SelectionBuilder append(String object2, Object ... arrobject) {
        if (TextUtils.isEmpty((CharSequence)object2)) {
            if (arrobject != null && arrobject.length > 0) {
                throw new IllegalArgumentException("Valid selection required when including arguments");
            }
            return this;
        }
        if (this.mSelection.length() > 0) {
            this.mSelection.append(" AND ");
        }
        StringBuilder stringBuilder = this.mSelection;
        stringBuilder.append("(");
        stringBuilder.append((String)object2);
        stringBuilder.append(")");
        if (arrobject != null) {
            for (Object object2 : arrobject) {
                this.mSelectionArgs.add(String.valueOf(object2));
            }
        }
        return this;
    }

    public int delete(SQLiteDatabase sQLiteDatabase, String string2) {
        return sQLiteDatabase.delete(string2, this.getSelection(), this.getSelectionArgs());
    }

    public String getSelection() {
        return this.mSelection.toString();
    }

    public String[] getSelectionArgs() {
        ArrayList<String> arrayList = this.mSelectionArgs;
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String string2, String[] arrstring, String string3) {
        return this.query(sQLiteDatabase, string2, arrstring, null, null, string3, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String string2, String[] arrstring, String string3, String string4, String string5, String string6) {
        return sQLiteDatabase.query(string2, arrstring, this.getSelection(), this.getSelectionArgs(), string3, string4, string5, string6);
    }

    public SelectionBuilder reset() {
        this.mSelection.setLength(0);
        this.mSelectionArgs.clear();
        return this;
    }

    public int update(SQLiteDatabase sQLiteDatabase, String string2, ContentValues contentValues) {
        return sQLiteDatabase.update(string2, contentValues, this.getSelection(), this.getSelectionArgs());
    }
}

