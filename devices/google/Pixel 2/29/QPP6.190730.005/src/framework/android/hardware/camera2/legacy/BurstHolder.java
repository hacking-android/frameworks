/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.legacy.RequestHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BurstHolder {
    private static final String TAG = "BurstHolder";
    private final boolean mRepeating;
    private final ArrayList<RequestHolder.Builder> mRequestBuilders = new ArrayList();
    private final int mRequestId;

    public BurstHolder(int n, boolean bl, CaptureRequest[] arrcaptureRequest, Collection<Long> collection) {
        int n2 = 0;
        for (CaptureRequest captureRequest : arrcaptureRequest) {
            this.mRequestBuilders.add(new RequestHolder.Builder(n, n2, captureRequest, bl, collection));
            ++n2;
        }
        this.mRepeating = bl;
        this.mRequestId = n;
    }

    public int getNumberOfRequests() {
        return this.mRequestBuilders.size();
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public boolean isRepeating() {
        return this.mRepeating;
    }

    public List<RequestHolder> produceRequestHolders(long l) {
        ArrayList<RequestHolder> arrayList = new ArrayList<RequestHolder>();
        int n = 0;
        Iterator<RequestHolder.Builder> iterator = this.mRequestBuilders.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().build((long)n + l));
            ++n;
        }
        return arrayList;
    }
}

