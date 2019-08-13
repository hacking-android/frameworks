/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.ContentObserver;
import android.database.Observable;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Iterator;

public class ContentObservable
extends Observable<ContentObserver> {
    @Deprecated
    public void dispatchChange(boolean bl) {
        this.dispatchChange(bl, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispatchChange(boolean bl, Uri uri) {
        ArrayList arrayList = this.mObservers;
        synchronized (arrayList) {
            Iterator iterator = this.mObservers.iterator();
            while (iterator.hasNext()) {
                ContentObserver contentObserver = (ContentObserver)iterator.next();
                if (bl && !contentObserver.deliverSelfNotifications()) continue;
                contentObserver.dispatchChange(bl, uri);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void notifyChange(boolean bl) {
        ArrayList arrayList = this.mObservers;
        synchronized (arrayList) {
            Iterator iterator = this.mObservers.iterator();
            while (iterator.hasNext()) {
                ((ContentObserver)iterator.next()).onChange(bl, null);
            }
            return;
        }
    }

    @Override
    public void registerObserver(ContentObserver contentObserver) {
        super.registerObserver(contentObserver);
    }
}

