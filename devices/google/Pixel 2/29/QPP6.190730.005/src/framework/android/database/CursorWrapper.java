/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;

public class CursorWrapper
implements Cursor {
    @UnsupportedAppUsage
    protected final Cursor mCursor;

    public CursorWrapper(Cursor cursor) {
        this.mCursor = cursor;
    }

    @Override
    public void close() {
        this.mCursor.close();
    }

    @Override
    public void copyStringToBuffer(int n, CharArrayBuffer charArrayBuffer) {
        this.mCursor.copyStringToBuffer(n, charArrayBuffer);
    }

    @Deprecated
    @Override
    public void deactivate() {
        this.mCursor.deactivate();
    }

    @Override
    public byte[] getBlob(int n) {
        return this.mCursor.getBlob(n);
    }

    @Override
    public int getColumnCount() {
        return this.mCursor.getColumnCount();
    }

    @Override
    public int getColumnIndex(String string2) {
        return this.mCursor.getColumnIndex(string2);
    }

    @Override
    public int getColumnIndexOrThrow(String string2) throws IllegalArgumentException {
        return this.mCursor.getColumnIndexOrThrow(string2);
    }

    @Override
    public String getColumnName(int n) {
        return this.mCursor.getColumnName(n);
    }

    @Override
    public String[] getColumnNames() {
        return this.mCursor.getColumnNames();
    }

    @Override
    public int getCount() {
        return this.mCursor.getCount();
    }

    @Override
    public double getDouble(int n) {
        return this.mCursor.getDouble(n);
    }

    @Override
    public Bundle getExtras() {
        return this.mCursor.getExtras();
    }

    @Override
    public float getFloat(int n) {
        return this.mCursor.getFloat(n);
    }

    @Override
    public int getInt(int n) {
        return this.mCursor.getInt(n);
    }

    @Override
    public long getLong(int n) {
        return this.mCursor.getLong(n);
    }

    @Override
    public Uri getNotificationUri() {
        return this.mCursor.getNotificationUri();
    }

    @Override
    public List<Uri> getNotificationUris() {
        return this.mCursor.getNotificationUris();
    }

    @Override
    public int getPosition() {
        return this.mCursor.getPosition();
    }

    @Override
    public short getShort(int n) {
        return this.mCursor.getShort(n);
    }

    @Override
    public String getString(int n) {
        return this.mCursor.getString(n);
    }

    @Override
    public int getType(int n) {
        return this.mCursor.getType(n);
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return this.mCursor.getWantsAllOnMoveCalls();
    }

    public Cursor getWrappedCursor() {
        return this.mCursor;
    }

    @Override
    public boolean isAfterLast() {
        return this.mCursor.isAfterLast();
    }

    @Override
    public boolean isBeforeFirst() {
        return this.mCursor.isBeforeFirst();
    }

    @Override
    public boolean isClosed() {
        return this.mCursor.isClosed();
    }

    @Override
    public boolean isFirst() {
        return this.mCursor.isFirst();
    }

    @Override
    public boolean isLast() {
        return this.mCursor.isLast();
    }

    @Override
    public boolean isNull(int n) {
        return this.mCursor.isNull(n);
    }

    @Override
    public boolean move(int n) {
        return this.mCursor.move(n);
    }

    @Override
    public boolean moveToFirst() {
        return this.mCursor.moveToFirst();
    }

    @Override
    public boolean moveToLast() {
        return this.mCursor.moveToLast();
    }

    @Override
    public boolean moveToNext() {
        return this.mCursor.moveToNext();
    }

    @Override
    public boolean moveToPosition(int n) {
        return this.mCursor.moveToPosition(n);
    }

    @Override
    public boolean moveToPrevious() {
        return this.mCursor.moveToPrevious();
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver) {
        this.mCursor.registerContentObserver(contentObserver);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.registerDataSetObserver(dataSetObserver);
    }

    @Deprecated
    @Override
    public boolean requery() {
        return this.mCursor.requery();
    }

    @Override
    public Bundle respond(Bundle bundle) {
        return this.mCursor.respond(bundle);
    }

    @Override
    public void setExtras(Bundle bundle) {
        this.mCursor.setExtras(bundle);
    }

    @Override
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        this.mCursor.setNotificationUri(contentResolver, uri);
    }

    @Override
    public void setNotificationUris(ContentResolver contentResolver, List<Uri> list) {
        this.mCursor.setNotificationUris(contentResolver, list);
    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver) {
        this.mCursor.unregisterContentObserver(contentObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.unregisterDataSetObserver(dataSetObserver);
    }
}

