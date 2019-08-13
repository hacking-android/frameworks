/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferCreationParams;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims._$$Lambda$RcsMessage$0kBwAJ2w_8hy0pyzXvF4qM9OTJY;
import android.telephony.ims._$$Lambda$RcsMessage$ArUQB5LoWlQIN8Wq6WO2D83_1MM;
import android.telephony.ims._$$Lambda$RcsMessage$ENpJTtPeUTVSc1EYo7vY4el8CTs;
import android.telephony.ims._$$Lambda$RcsMessage$HOpRnAgYuj5X_zRrkxcAiJKt3Yc;
import android.telephony.ims._$$Lambda$RcsMessage$LUddD5B3is0XmrdznFFrh7_BWBA;
import android.telephony.ims._$$Lambda$RcsMessage$OAV9C_4ygCWHuq6dzQZ6ryQxcng;
import android.telephony.ims._$$Lambda$RcsMessage$OWkNB5jXq4SHPk_hN01pKQSg5Z0;
import android.telephony.ims._$$Lambda$RcsMessage$Q3LSjskzCcY_LjdyGsUpqO_r8VY;
import android.telephony.ims._$$Lambda$RcsMessage$_LY9H__5LQIoU4Xq6_Om0qdYMVI;
import android.telephony.ims._$$Lambda$RcsMessage$aRPnqqKzd_0_r7d0L_yxEGwwqhc;
import android.telephony.ims._$$Lambda$RcsMessage$b6noI0B_AJvyHWAuKOL2fM_kHI4;
import android.telephony.ims._$$Lambda$RcsMessage$g8Us4wB8C4Z6FrAxP2EuVIs7uxg;
import android.telephony.ims._$$Lambda$RcsMessage$g_U1Cuc_BEv4JwISu6moBuf_gk0;
import android.telephony.ims._$$Lambda$RcsMessage$jYDUGwQFl9jl0oYVhZlCKVq8rao;
import android.telephony.ims._$$Lambda$RcsMessage$kreYSW19iRp_OhyMXMbvQXAxPUo;
import android.telephony.ims._$$Lambda$RcsMessage$tq1Iu9i2c3B7IAVANp7f9nz6BQI;
import android.telephony.ims._$$Lambda$RcsMessage$x3G08QqJukFKk5K0JbtI4g5JW5o;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class RcsMessage {
    public static final int DRAFT = 1;
    public static final int FAILED = 6;
    public static final double LOCATION_NOT_SET = Double.MIN_VALUE;
    public static final int NOT_SET = 0;
    public static final int QUEUED = 2;
    public static final int RECEIVED = 7;
    public static final int RETRYING = 5;
    public static final int SEEN = 9;
    public static final int SENDING = 3;
    public static final int SENT = 4;
    protected final int mId;
    protected final RcsControllerCall mRcsControllerCall;

    RcsMessage(RcsControllerCall rcsControllerCall, int n) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = n;
    }

    static /* synthetic */ void lambda$removeFileTransferPart$16(RcsFileTransferPart rcsFileTransferPart, IRcs iRcs, String string2) throws RemoteException {
        iRcs.deleteFileTransfer(rcsFileTransferPart.getId(), string2);
    }

    public Set<RcsFileTransferPart> getFileTransferParts() throws RcsMessageStoreException {
        HashSet<RcsFileTransferPart> hashSet = new HashSet<RcsFileTransferPart>();
        for (int n : (int[])this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$0kBwAJ2w_8hy0pyzXvF4qM9OTJY(this))) {
            hashSet.add(new RcsFileTransferPart(this.mRcsControllerCall, n));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public int getId() {
        return this.mId;
    }

    public double getLatitude() throws RcsMessageStoreException {
        return (Double)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$kreYSW19iRp_OhyMXMbvQXAxPUo(this));
    }

    public double getLongitude() throws RcsMessageStoreException {
        return (Double)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$x3G08QqJukFKk5K0JbtI4g5JW5o(this));
    }

    public long getOriginationTimestamp() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$g_U1Cuc_BEv4JwISu6moBuf_gk0(this));
    }

    public String getRcsMessageId() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$Q3LSjskzCcY_LjdyGsUpqO_r8VY(this));
    }

    public int getStatus() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$ENpJTtPeUTVSc1EYo7vY4el8CTs(this));
    }

    public int getSubscriptionId() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$aRPnqqKzd_0_r7d0L_yxEGwwqhc(this));
    }

    public String getText() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsMessage$jYDUGwQFl9jl0oYVhZlCKVq8rao(this));
    }

    public RcsFileTransferPart insertFileTransfer(RcsFileTransferCreationParams rcsFileTransferCreationParams) throws RcsMessageStoreException {
        RcsControllerCall rcsControllerCall = this.mRcsControllerCall;
        return new RcsFileTransferPart(rcsControllerCall, (Integer)rcsControllerCall.call(new _$$Lambda$RcsMessage$b6noI0B_AJvyHWAuKOL2fM_kHI4(this, rcsFileTransferCreationParams)));
    }

    public abstract boolean isIncoming();

    public /* synthetic */ int[] lambda$getFileTransferParts$15$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransfersAttachedToMessage(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Double lambda$getLatitude$10$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getLatitudeForMessage(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Double lambda$getLongitude$12$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getLongitudeForMessage(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Long lambda$getOriginationTimestamp$5$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageOriginationTimestamp(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ String lambda$getRcsMessageId$7$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getGlobalMessageIdForMessage(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Integer lambda$getStatus$3$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageStatus(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Integer lambda$getSubscriptionId$0$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getMessageSubId(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ String lambda$getText$8$RcsMessage(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getTextForMessage(this.mId, this.isIncoming(), string2);
    }

    public /* synthetic */ Integer lambda$insertFileTransfer$14$RcsMessage(RcsFileTransferCreationParams rcsFileTransferCreationParams, IRcs iRcs, String string2) throws RemoteException {
        return iRcs.storeFileTransfer(this.mId, this.isIncoming(), rcsFileTransferCreationParams, string2);
    }

    public /* synthetic */ void lambda$setLatitude$11$RcsMessage(double d, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setLatitudeForMessage(this.mId, this.isIncoming(), d, string2);
    }

    public /* synthetic */ void lambda$setLongitude$13$RcsMessage(double d, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setLongitudeForMessage(this.mId, this.isIncoming(), d, string2);
    }

    public /* synthetic */ void lambda$setOriginationTimestamp$4$RcsMessage(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setMessageOriginationTimestamp(this.mId, this.isIncoming(), l, string2);
    }

    public /* synthetic */ void lambda$setRcsMessageId$6$RcsMessage(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setGlobalMessageIdForMessage(this.mId, this.isIncoming(), string2, string3);
    }

    public /* synthetic */ void lambda$setStatus$2$RcsMessage(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setMessageStatus(this.mId, this.isIncoming(), n, string2);
    }

    public /* synthetic */ void lambda$setSubscriptionId$1$RcsMessage(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setMessageSubId(this.mId, this.isIncoming(), n, string2);
    }

    public /* synthetic */ void lambda$setText$9$RcsMessage(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setTextForMessage(this.mId, this.isIncoming(), string2, string3);
    }

    public void removeFileTransferPart(RcsFileTransferPart rcsFileTransferPart) throws RcsMessageStoreException {
        if (rcsFileTransferPart == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$ArUQB5LoWlQIN8Wq6WO2D83_1MM(rcsFileTransferPart));
    }

    public void setLatitude(double d) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$OWkNB5jXq4SHPk_hN01pKQSg5Z0(this, d));
    }

    public void setLongitude(double d) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$LUddD5B3is0XmrdznFFrh7_BWBA(this, d));
    }

    public void setOriginationTimestamp(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$tq1Iu9i2c3B7IAVANp7f9nz6BQI(this, l));
    }

    public void setRcsMessageId(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$g8Us4wB8C4Z6FrAxP2EuVIs7uxg(this, string2));
    }

    public void setStatus(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$HOpRnAgYuj5X_zRrkxcAiJKt3Yc(this, n));
    }

    public void setSubscriptionId(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$_LY9H__5LQIoU4Xq6_Om0qdYMVI(this, n));
    }

    public void setText(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsMessage$OAV9C_4ygCWHuq6dzQZ6ryQxcng(this, string2));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RcsMessageStatus {
    }

}

