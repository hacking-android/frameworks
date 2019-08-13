/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.CursorWindow;
import android.database.IContentObserver;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;

public interface IBulkCursor
extends IInterface {
    public static final int CLOSE_TRANSACTION = 7;
    public static final int DEACTIVATE_TRANSACTION = 2;
    public static final int GET_CURSOR_WINDOW_TRANSACTION = 1;
    public static final int GET_EXTRAS_TRANSACTION = 5;
    public static final int ON_MOVE_TRANSACTION = 4;
    public static final int REQUERY_TRANSACTION = 3;
    public static final int RESPOND_TRANSACTION = 6;
    public static final String descriptor = "android.content.IBulkCursor";

    public void close() throws RemoteException;

    public void deactivate() throws RemoteException;

    public Bundle getExtras() throws RemoteException;

    public CursorWindow getWindow(int var1) throws RemoteException;

    public void onMove(int var1) throws RemoteException;

    public int requery(IContentObserver var1) throws RemoteException;

    public Bundle respond(Bundle var1) throws RemoteException;
}

