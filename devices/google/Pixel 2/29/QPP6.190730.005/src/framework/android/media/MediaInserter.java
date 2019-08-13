/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.net.Uri;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MediaInserter {
    private final int mBufferSizePerUri;
    private final HashMap<Uri, List<ContentValues>> mPriorityRowMap = new HashMap();
    private final ContentProviderClient mProvider;
    private final HashMap<Uri, List<ContentValues>> mRowMap = new HashMap();

    public MediaInserter(ContentProviderClient contentProviderClient, int n) {
        this.mProvider = contentProviderClient;
        this.mBufferSizePerUri = n;
    }

    private void flush(Uri uri, List<ContentValues> list) throws RemoteException {
        if (!list.isEmpty()) {
            ContentValues[] arrcontentValues = list.toArray(new ContentValues[list.size()]);
            this.mProvider.bulkInsert(uri, arrcontentValues);
            list.clear();
        }
    }

    private void flushAllPriority() throws RemoteException {
        for (Uri uri : this.mPriorityRowMap.keySet()) {
            this.flush(uri, this.mPriorityRowMap.get(uri));
        }
        this.mPriorityRowMap.clear();
    }

    private void insert(Uri uri, ContentValues contentValues, boolean bl) throws RemoteException {
        List<ContentValues> list;
        HashMap<Uri, List<ContentValues>> hashMap = bl ? this.mPriorityRowMap : this.mRowMap;
        List<ContentValues> list2 = list = hashMap.get(uri);
        if (list == null) {
            list2 = new ArrayList<ContentValues>();
            hashMap.put(uri, list2);
        }
        list2.add(new ContentValues(contentValues));
        if (list2.size() >= this.mBufferSizePerUri) {
            this.flushAllPriority();
            this.flush(uri, list2);
        }
    }

    @UnsupportedAppUsage
    public void flushAll() throws RemoteException {
        this.flushAllPriority();
        for (Uri uri : this.mRowMap.keySet()) {
            this.flush(uri, this.mRowMap.get(uri));
        }
        this.mRowMap.clear();
    }

    public void insert(Uri uri, ContentValues contentValues) throws RemoteException {
        this.insert(uri, contentValues, false);
    }

    public void insertwithPriority(Uri uri, ContentValues contentValues) throws RemoteException {
        this.insert(uri, contentValues, true);
    }
}

