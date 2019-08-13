/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkMisc;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.internal.util.AsyncChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class NetworkAgent
extends Handler {
    private static final int BASE = 528384;
    private static final long BW_REFRESH_MIN_WIN_MS = 500L;
    public static final int CMD_ADD_KEEPALIVE_PACKET_FILTER = 528400;
    public static final int CMD_PREVENT_AUTOMATIC_RECONNECT = 528399;
    public static final int CMD_REMOVE_KEEPALIVE_PACKET_FILTER = 528401;
    public static final int CMD_REPORT_NETWORK_STATUS = 528391;
    public static final int CMD_REQUEST_BANDWIDTH_UPDATE = 528394;
    public static final int CMD_SAVE_ACCEPT_UNVALIDATED = 528393;
    public static final int CMD_SET_SIGNAL_STRENGTH_THRESHOLDS = 528398;
    public static final int CMD_START_SOCKET_KEEPALIVE = 528395;
    public static final int CMD_STOP_SOCKET_KEEPALIVE = 528396;
    public static final int CMD_SUSPECT_BAD = 528384;
    private static final boolean DBG = true;
    public static final int EVENT_NETWORK_CAPABILITIES_CHANGED = 528386;
    public static final int EVENT_NETWORK_INFO_CHANGED = 528385;
    public static final int EVENT_NETWORK_PROPERTIES_CHANGED = 528387;
    public static final int EVENT_NETWORK_SCORE_CHANGED = 528388;
    public static final int EVENT_SET_EXPLICITLY_SELECTED = 528392;
    public static final int EVENT_SOCKET_KEEPALIVE = 528397;
    public static final int INVALID_NETWORK = 2;
    public static String REDIRECT_URL_KEY = "redirect URL";
    public static final int VALID_NETWORK = 1;
    private static final boolean VDBG = false;
    public static final int WIFI_BASE_SCORE = 60;
    private final String LOG_TAG;
    private volatile AsyncChannel mAsyncChannel;
    private final Context mContext;
    public final int mFactorySerialNumber;
    private volatile long mLastBwRefreshTime = 0L;
    private AtomicBoolean mPollLcePending = new AtomicBoolean(false);
    private boolean mPollLceScheduled = false;
    private final ArrayList<Message> mPreConnectedQueue = new ArrayList();
    public final int netId;

    public NetworkAgent(Looper looper, Context context, String string2, NetworkInfo networkInfo, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, int n) {
        this(looper, context, string2, networkInfo, networkCapabilities, linkProperties, n, null, -1);
    }

    public NetworkAgent(Looper looper, Context context, String string2, NetworkInfo networkInfo, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, int n, int n2) {
        this(looper, context, string2, networkInfo, networkCapabilities, linkProperties, n, null, n2);
    }

    public NetworkAgent(Looper looper, Context context, String string2, NetworkInfo networkInfo, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, int n, NetworkMisc networkMisc) {
        this(looper, context, string2, networkInfo, networkCapabilities, linkProperties, n, networkMisc, -1);
    }

    public NetworkAgent(Looper looper, Context context, String string2, NetworkInfo networkInfo, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, int n, NetworkMisc networkMisc, int n2) {
        super(looper);
        this.LOG_TAG = string2;
        this.mContext = context;
        this.mFactorySerialNumber = n2;
        if (networkInfo != null && networkCapabilities != null && linkProperties != null) {
            this.netId = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).registerNetworkAgent(new Messenger(this), new NetworkInfo(networkInfo), new LinkProperties(linkProperties), new NetworkCapabilities(networkCapabilities), n, networkMisc, n2);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void queueOrSendMessage(int n, int n2, int n3) {
        this.queueOrSendMessage(n, n2, n3, null);
    }

    private void queueOrSendMessage(int n, int n2, int n3, Object object) {
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = n3;
        message.obj = object;
        this.queueOrSendMessage(message);
    }

    private void queueOrSendMessage(int n, Object object) {
        this.queueOrSendMessage(n, 0, 0, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void queueOrSendMessage(Message message) {
        ArrayList<Message> arrayList = this.mPreConnectedQueue;
        synchronized (arrayList) {
            if (this.mAsyncChannel != null) {
                this.mAsyncChannel.sendMessage(message);
            } else {
                this.mPreConnectedQueue.add(message);
            }
            return;
        }
    }

    protected void addKeepalivePacketFilter(Message message) {
    }

    public void explicitlySelected(boolean bl) {
        this.explicitlySelected(true, bl);
    }

    public void explicitlySelected(boolean bl, boolean bl2) {
        this.queueOrSendMessage(528392, (int)bl, (int)bl2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleMessage(Message object) {
        int n = object.what;
        boolean bl = true;
        int n2 = 0;
        switch (n) {
            default: {
                return;
            }
            case 528401: {
                this.removeKeepalivePacketFilter((Message)object);
                return;
            }
            case 528400: {
                this.addKeepalivePacketFilter((Message)object);
                return;
            }
            case 528399: {
                this.preventAutomaticReconnect();
                return;
            }
            case 528398: {
                ArrayList<Integer> arrayList = ((Bundle)object.obj).getIntegerArrayList("thresholds");
                if (arrayList != null) {
                    n2 = arrayList.size();
                }
                object = new int[n2];
                n2 = 0;
                do {
                    if (n2 >= ((int[])object).length) {
                        this.setSignalStrengthThresholds((int[])object);
                        return;
                    }
                    object[n2] = arrayList.get(n2);
                    ++n2;
                } while (true);
            }
            case 528396: {
                this.stopSocketKeepalive((Message)object);
                return;
            }
            case 528395: {
                this.startSocketKeepalive((Message)object);
                return;
            }
            case 528394: {
                long l = System.currentTimeMillis();
                if (l >= this.mLastBwRefreshTime + 500L) {
                    this.mPollLceScheduled = false;
                    if (this.mPollLcePending.getAndSet(true)) return;
                    this.pollLceData();
                    return;
                }
                if (this.mPollLceScheduled) return;
                this.mPollLceScheduled = this.sendEmptyMessageDelayed(528394, this.mLastBwRefreshTime + 500L - l + 1L);
                return;
            }
            case 528393: {
                if (object.arg1 == 0) {
                    bl = false;
                }
                this.saveAcceptUnvalidated(bl);
                return;
            }
            case 528391: {
                String string2 = ((Bundle)object.obj).getString(REDIRECT_URL_KEY);
                this.networkStatus(object.arg1, string2);
                return;
            }
            case 528384: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled Message ");
                stringBuilder.append(object);
                this.log(stringBuilder.toString());
                return;
            }
            case 69636: {
                this.log("NetworkAgent channel lost");
                this.unwanted();
                object = this.mPreConnectedQueue;
                synchronized (object) {
                    this.mAsyncChannel = null;
                    return;
                }
            }
            case 69635: {
                if (this.mAsyncChannel == null) return;
                this.mAsyncChannel.disconnect();
                return;
            }
            case 69633: 
        }
        if (this.mAsyncChannel != null) {
            this.log("Received new connection while already connected!");
            return;
        }
        AsyncChannel asyncChannel = new AsyncChannel();
        asyncChannel.connected(null, this, object.replyTo);
        asyncChannel.replyToMessage((Message)object, 69634, 0);
        object = this.mPreConnectedQueue;
        synchronized (object) {
            this.mAsyncChannel = asyncChannel;
            Iterator<Message> iterator = this.mPreConnectedQueue.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mPreConnectedQueue.clear();
                    return;
                }
                asyncChannel.sendMessage(iterator.next());
            } while (true);
        }
    }

    protected void log(String string2) {
        String string3 = this.LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetworkAgent: ");
        stringBuilder.append(string2);
        Log.d(string3, stringBuilder.toString());
    }

    protected void networkStatus(int n, String string2) {
    }

    public void onSocketKeepaliveEvent(int n, int n2) {
        this.queueOrSendMessage(528397, n, n2);
    }

    protected void pollLceData() {
    }

    protected void preventAutomaticReconnect() {
    }

    protected void removeKeepalivePacketFilter(Message message) {
    }

    protected void saveAcceptUnvalidated(boolean bl) {
    }

    public void sendLinkProperties(LinkProperties linkProperties) {
        this.queueOrSendMessage(528387, new LinkProperties(linkProperties));
    }

    public void sendNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        this.mPollLcePending.set(false);
        this.mLastBwRefreshTime = System.currentTimeMillis();
        this.queueOrSendMessage(528386, new NetworkCapabilities(networkCapabilities));
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void sendNetworkInfo(NetworkInfo networkInfo) {
        this.queueOrSendMessage(528385, new NetworkInfo(networkInfo));
    }

    public void sendNetworkScore(int n) {
        if (n >= 0) {
            this.queueOrSendMessage(528388, n, 0);
            return;
        }
        throw new IllegalArgumentException("Score must be >= 0");
    }

    protected void setSignalStrengthThresholds(int[] arrn) {
    }

    protected void startSocketKeepalive(Message message) {
        this.onSocketKeepaliveEvent(message.arg1, -30);
    }

    protected void stopSocketKeepalive(Message message) {
        this.onSocketKeepaliveEvent(message.arg1, -30);
    }

    protected abstract void unwanted();
}

