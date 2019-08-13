/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.database.AbstractCursor;
import android.database.CharArrayBuffer;
import android.database.CursorWindow;
import android.database.StaleDataException;

public abstract class AbstractWindowedCursor
extends AbstractCursor {
    protected CursorWindow mWindow;

    @Override
    protected void checkPosition() {
        super.checkPosition();
        if (this.mWindow != null) {
            return;
        }
        throw new StaleDataException("Attempting to access a closed CursorWindow.Most probable cause: cursor is deactivated prior to calling this method.");
    }

    @UnsupportedAppUsage
    protected void clearOrCreateWindow(String string2) {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow == null) {
            this.mWindow = new CursorWindow(string2);
        } else {
            cursorWindow.clear();
        }
    }

    @UnsupportedAppUsage
    protected void closeWindow() {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
            this.mWindow = null;
        }
    }

    @Override
    public void copyStringToBuffer(int n, CharArrayBuffer charArrayBuffer) {
        this.checkPosition();
        this.mWindow.copyStringToBuffer(this.mPos, n, charArrayBuffer);
    }

    @Override
    public byte[] getBlob(int n) {
        this.checkPosition();
        return this.mWindow.getBlob(this.mPos, n);
    }

    @Override
    public double getDouble(int n) {
        this.checkPosition();
        return this.mWindow.getDouble(this.mPos, n);
    }

    @Override
    public float getFloat(int n) {
        this.checkPosition();
        return this.mWindow.getFloat(this.mPos, n);
    }

    @Override
    public int getInt(int n) {
        this.checkPosition();
        return this.mWindow.getInt(this.mPos, n);
    }

    @Override
    public long getLong(int n) {
        this.checkPosition();
        return this.mWindow.getLong(this.mPos, n);
    }

    @Override
    public short getShort(int n) {
        this.checkPosition();
        return this.mWindow.getShort(this.mPos, n);
    }

    @Override
    public String getString(int n) {
        this.checkPosition();
        return this.mWindow.getString(this.mPos, n);
    }

    @Override
    public int getType(int n) {
        this.checkPosition();
        return this.mWindow.getType(this.mPos, n);
    }

    @Override
    public CursorWindow getWindow() {
        return this.mWindow;
    }

    public boolean hasWindow() {
        boolean bl = this.mWindow != null;
        return bl;
    }

    @Deprecated
    public boolean isBlob(int n) {
        boolean bl = this.getType(n) == 4;
        return bl;
    }

    @Deprecated
    public boolean isFloat(int n) {
        boolean bl = this.getType(n) == 2;
        return bl;
    }

    @Deprecated
    public boolean isLong(int n) {
        n = this.getType(n);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isNull(int n) {
        this.checkPosition();
        boolean bl = this.mWindow.getType(this.mPos, n) == 0;
        return bl;
    }

    @Deprecated
    public boolean isString(int n) {
        boolean bl = this.getType(n) == 3;
        return bl;
    }

    @UnsupportedAppUsage
    @Override
    protected void onDeactivateOrClose() {
        super.onDeactivateOrClose();
        this.closeWindow();
    }

    public void setWindow(CursorWindow cursorWindow) {
        if (cursorWindow != this.mWindow) {
            this.closeWindow();
            this.mWindow = cursorWindow;
        }
    }
}

