/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net._$$Lambda$NetworkFactory$HfslgqyaKc_n0wXX5_qRYVZoGfI;
import android.net._$$Lambda$NetworkFactory$quULWy1SjqmEQiqq5nzlBuGzTss;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkFactory
extends Handler {
    private static final int BASE = 536576;
    public static final int CMD_CANCEL_REQUEST = 536577;
    public static final int CMD_REQUEST_NETWORK = 536576;
    private static final int CMD_SET_FILTER = 536579;
    private static final int CMD_SET_SCORE = 536578;
    private static final boolean DBG = true;
    public static final int EVENT_UNFULFILLABLE_REQUEST = 536580;
    private static final boolean VDBG = false;
    private final String LOG_TAG;
    private AsyncChannel mAsyncChannel;
    private NetworkCapabilities mCapabilityFilter;
    private final Context mContext;
    private Messenger mMessenger = null;
    private final SparseArray<NetworkRequestInfo> mNetworkRequests = new SparseArray();
    private final ArrayList<Message> mPreConnectedQueue = new ArrayList();
    private int mRefCount = 0;
    private int mScore;
    private int mSerialNumber;

    @UnsupportedAppUsage
    public NetworkFactory(Looper looper, Context context, String string2, NetworkCapabilities networkCapabilities) {
        super(looper);
        this.LOG_TAG = string2;
        this.mContext = context;
        this.mCapabilityFilter = networkCapabilities;
    }

    private void evalRequest(NetworkRequestInfo networkRequestInfo) {
        if (this.shouldNeedNetworkFor(networkRequestInfo)) {
            this.needNetworkFor(networkRequestInfo.request, networkRequestInfo.score);
            networkRequestInfo.requested = true;
        } else if (this.shouldReleaseNetworkFor(networkRequestInfo)) {
            this.releaseNetworkFor(networkRequestInfo.request);
            networkRequestInfo.requested = false;
        }
    }

    private void evalRequests() {
        for (int i = 0; i < this.mNetworkRequests.size(); ++i) {
            this.evalRequest(this.mNetworkRequests.valueAt(i));
        }
    }

    private void handleSetFilter(NetworkCapabilities networkCapabilities) {
        this.mCapabilityFilter = networkCapabilities;
        this.evalRequests();
    }

    private void handleSetScore(int n) {
        this.mScore = n;
        this.evalRequests();
    }

    private boolean shouldNeedNetworkFor(NetworkRequestInfo networkRequestInfo) {
        boolean bl = !networkRequestInfo.requested && (networkRequestInfo.score < this.mScore || networkRequestInfo.factorySerialNumber == this.mSerialNumber) && networkRequestInfo.request.networkCapabilities.satisfiedByNetworkCapabilities(this.mCapabilityFilter) && this.acceptRequest(networkRequestInfo.request, networkRequestInfo.score);
        return bl;
    }

    private boolean shouldReleaseNetworkFor(NetworkRequestInfo networkRequestInfo) {
        boolean bl = networkRequestInfo.requested && (networkRequestInfo.score > this.mScore && networkRequestInfo.factorySerialNumber != this.mSerialNumber || !networkRequestInfo.request.networkCapabilities.satisfiedByNetworkCapabilities(this.mCapabilityFilter) || !this.acceptRequest(networkRequestInfo.request, networkRequestInfo.score));
        return bl;
    }

    public boolean acceptRequest(NetworkRequest networkRequest, int n) {
        return true;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        object = new IndentingPrintWriter(printWriter, "  ");
        ((PrintWriter)object).println(this.toString());
        ((IndentingPrintWriter)object).increaseIndent();
        for (int i = 0; i < this.mNetworkRequests.size(); ++i) {
            ((PrintWriter)object).println(this.mNetworkRequests.valueAt(i));
        }
        ((IndentingPrintWriter)object).decreaseIndent();
    }

    @VisibleForTesting
    protected int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    @VisibleForTesting
    protected void handleAddRequest(NetworkRequest networkRequest, int n) {
        this.handleAddRequest(networkRequest, n, -1);
    }

    @VisibleForTesting
    protected void handleAddRequest(NetworkRequest object, int n, int n2) {
        Object object2 = this.mNetworkRequests.get(((NetworkRequest)object).requestId);
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("got request ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" with score ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" and serial ");
            ((StringBuilder)object2).append(n2);
            this.log(((StringBuilder)object2).toString());
            object = new NetworkRequestInfo((NetworkRequest)object, n, n2);
            this.mNetworkRequests.put(object.request.requestId, (NetworkRequestInfo)object);
        } else {
            ((NetworkRequestInfo)object2).score = n;
            ((NetworkRequestInfo)object2).factorySerialNumber = n2;
            object = object2;
        }
        this.evalRequest((NetworkRequestInfo)object);
    }

    @Override
    public void handleMessage(Message iterator) {
        int n = ((Message)iterator).what;
        if (n != 69633) {
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break block0;
                        }
                        case 536579: {
                            this.handleSetFilter((NetworkCapabilities)((Message)iterator).obj);
                            break block0;
                        }
                        case 536578: {
                            this.handleSetScore(((Message)iterator).arg1);
                            break block0;
                        }
                        case 536577: {
                            this.handleRemoveRequest((NetworkRequest)((Message)iterator).obj);
                            break block0;
                        }
                        case 536576: 
                    }
                    this.handleAddRequest((NetworkRequest)((Message)iterator).obj, ((Message)iterator).arg1, ((Message)iterator).arg2);
                    break;
                }
                case 69636: {
                    this.log("NetworkFactory channel lost");
                    this.mAsyncChannel = null;
                    break;
                }
                case 69635: {
                    iterator = this.mAsyncChannel;
                    if (iterator != null) {
                        ((AsyncChannel)((Object)iterator)).disconnect();
                        this.mAsyncChannel = null;
                        break;
                    } else {
                        break;
                    }
                }
            }
        } else if (this.mAsyncChannel != null) {
            this.log("Received new connection while already connected!");
        } else {
            AsyncChannel asyncChannel = new AsyncChannel();
            asyncChannel.connected(null, this, ((Message)iterator).replyTo);
            asyncChannel.replyToMessage((Message)((Object)iterator), 69634, 0);
            this.mAsyncChannel = asyncChannel;
            iterator = this.mPreConnectedQueue.iterator();
            while (iterator.hasNext()) {
                asyncChannel.sendMessage(iterator.next());
            }
            this.mPreConnectedQueue.clear();
        }
    }

    @VisibleForTesting
    protected void handleRemoveRequest(NetworkRequest networkRequest) {
        NetworkRequestInfo networkRequestInfo = this.mNetworkRequests.get(networkRequest.requestId);
        if (networkRequestInfo != null) {
            this.mNetworkRequests.remove(networkRequest.requestId);
            if (networkRequestInfo.requested) {
                this.releaseNetworkFor(networkRequestInfo.request);
            }
        }
    }

    public /* synthetic */ void lambda$reevaluateAllRequests$0$NetworkFactory() {
        this.evalRequests();
    }

    public /* synthetic */ void lambda$releaseRequestAsUnfulfillableByAnyFactory$1$NetworkFactory(NetworkRequest parcelable) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("releaseRequestAsUnfulfillableByAnyFactory: ");
        ((StringBuilder)object).append(parcelable);
        this.log(((StringBuilder)object).toString());
        parcelable = this.obtainMessage(536580, parcelable);
        object = this.mAsyncChannel;
        if (object != null) {
            ((AsyncChannel)object).sendMessage((Message)parcelable);
        } else {
            this.mPreConnectedQueue.add((Message)parcelable);
        }
    }

    protected void log(String string2) {
        Log.d(this.LOG_TAG, string2);
    }

    protected void needNetworkFor(NetworkRequest networkRequest, int n) {
        this.mRefCount = n = this.mRefCount + 1;
        if (n == 1) {
            this.startNetwork();
        }
    }

    protected void reevaluateAllRequests() {
        this.post(new _$$Lambda$NetworkFactory$HfslgqyaKc_n0wXX5_qRYVZoGfI(this));
    }

    public void register() {
        this.log("Registering NetworkFactory");
        if (this.mMessenger == null) {
            this.mMessenger = new Messenger(this);
            this.mSerialNumber = ConnectivityManager.from(this.mContext).registerNetworkFactory(this.mMessenger, this.LOG_TAG);
        }
    }

    protected void releaseNetworkFor(NetworkRequest networkRequest) {
        int n;
        this.mRefCount = n = this.mRefCount - 1;
        if (n == 0) {
            this.stopNetwork();
        }
    }

    protected void releaseRequestAsUnfulfillableByAnyFactory(NetworkRequest networkRequest) {
        this.post(new _$$Lambda$NetworkFactory$quULWy1SjqmEQiqq5nzlBuGzTss(this, networkRequest));
    }

    public void setCapabilityFilter(NetworkCapabilities networkCapabilities) {
        this.sendMessage(this.obtainMessage(536579, new NetworkCapabilities(networkCapabilities)));
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setScoreFilter(int n) {
        this.sendMessage(this.obtainMessage(536578, n, 0));
    }

    protected void startNetwork() {
    }

    protected void stopNetwork() {
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append(this.LOG_TAG);
        stringBuilder.append(" - mSerialNumber=");
        stringBuilder.append(this.mSerialNumber);
        stringBuilder.append(", ScoreFilter=");
        stringBuilder.append(this.mScore);
        stringBuilder.append(", Filter=");
        stringBuilder.append(this.mCapabilityFilter);
        stringBuilder.append(", requests=");
        stringBuilder.append(this.mNetworkRequests.size());
        stringBuilder.append(", refCount=");
        stringBuilder.append(this.mRefCount);
        return stringBuilder.append("}").toString();
    }

    public void unregister() {
        this.log("Unregistering NetworkFactory");
        if (this.mMessenger != null) {
            ConnectivityManager.from(this.mContext).unregisterNetworkFactory(this.mMessenger);
            this.mMessenger = null;
        }
    }

    private class NetworkRequestInfo {
        public int factorySerialNumber;
        public final NetworkRequest request;
        public boolean requested;
        public int score;

        NetworkRequestInfo(NetworkRequest networkRequest, int n, int n2) {
            this.request = networkRequest;
            this.score = n;
            this.requested = false;
            this.factorySerialNumber = n2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(this.request);
            stringBuilder.append(", score=");
            stringBuilder.append(this.score);
            stringBuilder.append(", requested=");
            stringBuilder.append(this.requested);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static class SerialNumber {
        public static final int NONE = -1;
        public static final int VPN = -2;
        private static final AtomicInteger sNetworkFactorySerialNumber = new AtomicInteger(1);

        public static final int nextSerialNumber() {
            return sNetworkFactorySerialNumber.getAndIncrement();
        }
    }

}

