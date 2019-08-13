/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStoreException;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$1I5TANd1JGzUvxVPbWbmYgYHgZg;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$4bTF8UNuphmPWGI1zJtDN0vEMKQ;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$5nq0jbEkQm3ys2NrT291eV7NXn8;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$A_4O6faLVs6mpaPsKJIA9HefwvU;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$B5FCShigB8L98Le8jQF4kRDSfhk;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$B5UxN0BhElRx_FWpAZgbz41DxuY;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$Js49W5j_aEL3sBPRKR3zwBZEwQc;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$Ju03J4o5Gnha0Ynbq35sw9HL5nU;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$KCwtK0S_DWMMpZpRsslXFJ_BwLM;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$NeUx42_gy02_DXOOj3iF2Y92GoU;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$RUTTVEFxx0RPDq0oORm2TF6GoJ8;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$X3yfwvMihWzA9VZLnUyeAlq_rVc;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$_U_JpxTv_8vqlG8zHOxxNMMBqjQ;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$cbwg3i9EtuBNKXI5md4IWJQ_GDo;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$dlGXDrIqL_9NsNgH4LIS6Yg7j6k;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$eRysznIV0Pr9U0YPttLhvYxp2JE;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$gHrYiSj4B912GPuzgw6v3qjIwX4;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$iFRtCc6m4Iup_st7fFqTiBlhq4o;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$kXXTp4pKFNyBztnIElEJdJrz8F8;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$kvkf6ASdU_q8pR3hQ4h9sWdIiOQ;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$m0Uztiu9azOAnoxBEWLsT8Br_HE;
import android.telephony.ims._$$Lambda$RcsFileTransferPart$pZ6z6R9RPQvoiIFOh_auV7YAePw;
import android.telephony.ims.aidl.IRcs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RcsFileTransferPart {
    public static final int DOWNLOADING = 6;
    public static final int DOWNLOADING_CANCELLED = 9;
    public static final int DOWNLOADING_FAILED = 8;
    public static final int DOWNLOADING_PAUSED = 7;
    public static final int DRAFT = 1;
    public static final int NOT_SET = 0;
    public static final int SENDING = 2;
    public static final int SENDING_CANCELLED = 5;
    public static final int SENDING_FAILED = 4;
    public static final int SENDING_PAUSED = 3;
    public static final int SUCCEEDED = 10;
    private int mId;
    private final RcsControllerCall mRcsControllerCall;

    RcsFileTransferPart(RcsControllerCall rcsControllerCall, int n) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = n;
    }

    public String getContentMimeType() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$X3yfwvMihWzA9VZLnUyeAlq_rVc(this));
    }

    public Uri getContentUri() throws RcsMessageStoreException {
        return (Uri)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$kvkf6ASdU_q8pR3hQ4h9sWdIiOQ(this));
    }

    public long getFileSize() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$RUTTVEFxx0RPDq0oORm2TF6GoJ8(this));
    }

    public String getFileTransferSessionId() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$KCwtK0S_DWMMpZpRsslXFJ_BwLM(this));
    }

    public int getFileTransferStatus() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$5nq0jbEkQm3ys2NrT291eV7NXn8(this));
    }

    public int getHeight() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$A_4O6faLVs6mpaPsKJIA9HefwvU(this));
    }

    public int getId() {
        return this.mId;
    }

    public long getLength() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$B5UxN0BhElRx_FWpAZgbz41DxuY(this));
    }

    public String getPreviewMimeType() throws RcsMessageStoreException {
        return (String)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$B5FCShigB8L98Le8jQF4kRDSfhk(this));
    }

    public Uri getPreviewUri() throws RcsMessageStoreException {
        return (Uri)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$pZ6z6R9RPQvoiIFOh_auV7YAePw(this));
    }

    public long getTransferOffset() throws RcsMessageStoreException {
        return (Long)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$m0Uztiu9azOAnoxBEWLsT8Br_HE(this));
    }

    public int getWidth() throws RcsMessageStoreException {
        return (Integer)this.mRcsControllerCall.call(new _$$Lambda$RcsFileTransferPart$cbwg3i9EtuBNKXI5md4IWJQ_GDo(this));
    }

    public /* synthetic */ String lambda$getContentMimeType$5$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferContentType(this.mId, string2);
    }

    public /* synthetic */ Uri lambda$getContentUri$3$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferContentUri(this.mId, string2);
    }

    public /* synthetic */ Long lambda$getFileSize$7$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferFileSize(this.mId, string2);
    }

    public /* synthetic */ String lambda$getFileTransferSessionId$1$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferSessionId(this.mId, string2);
    }

    public /* synthetic */ Integer lambda$getFileTransferStatus$11$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferStatus(this.mId, string2);
    }

    public /* synthetic */ Integer lambda$getHeight$14$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferHeight(this.mId, string2);
    }

    public /* synthetic */ Long lambda$getLength$16$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferLength(this.mId, string2);
    }

    public /* synthetic */ String lambda$getPreviewMimeType$20$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferPreviewType(this.mId, string2);
    }

    public /* synthetic */ Uri lambda$getPreviewUri$18$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferPreviewUri(this.mId, string2);
    }

    public /* synthetic */ Long lambda$getTransferOffset$9$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferTransferOffset(this.mId, string2);
    }

    public /* synthetic */ Integer lambda$getWidth$12$RcsFileTransferPart(IRcs iRcs, String string2) throws RemoteException {
        return iRcs.getFileTransferWidth(this.mId, string2);
    }

    public /* synthetic */ void lambda$setContentMimeType$4$RcsFileTransferPart(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setFileTransferContentType(this.mId, string2, string3);
    }

    public /* synthetic */ void lambda$setContentUri$2$RcsFileTransferPart(Uri uri, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferContentUri(this.mId, uri, string2);
    }

    public /* synthetic */ void lambda$setFileSize$6$RcsFileTransferPart(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferFileSize(this.mId, l, string2);
    }

    public /* synthetic */ void lambda$setFileTransferSessionId$0$RcsFileTransferPart(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setFileTransferSessionId(this.mId, string2, string3);
    }

    public /* synthetic */ void lambda$setFileTransferStatus$10$RcsFileTransferPart(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferStatus(this.mId, n, string2);
    }

    public /* synthetic */ void lambda$setHeight$15$RcsFileTransferPart(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferHeight(this.mId, n, string2);
    }

    public /* synthetic */ void lambda$setLength$17$RcsFileTransferPart(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferLength(this.mId, l, string2);
    }

    public /* synthetic */ void lambda$setPreviewMimeType$21$RcsFileTransferPart(String string2, IRcs iRcs, String string3) throws RemoteException {
        iRcs.setFileTransferPreviewType(this.mId, string2, string3);
    }

    public /* synthetic */ void lambda$setPreviewUri$19$RcsFileTransferPart(Uri uri, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferPreviewUri(this.mId, uri, string2);
    }

    public /* synthetic */ void lambda$setTransferOffset$8$RcsFileTransferPart(long l, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferTransferOffset(this.mId, l, string2);
    }

    public /* synthetic */ void lambda$setWidth$13$RcsFileTransferPart(int n, IRcs iRcs, String string2) throws RemoteException {
        iRcs.setFileTransferWidth(this.mId, n, string2);
    }

    public void setContentMimeType(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$_U_JpxTv_8vqlG8zHOxxNMMBqjQ(this, string2));
    }

    public void setContentUri(Uri uri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$gHrYiSj4B912GPuzgw6v3qjIwX4(this, uri));
    }

    public void setFileSize(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$iFRtCc6m4Iup_st7fFqTiBlhq4o(this, l));
    }

    public void setFileTransferSessionId(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$eRysznIV0Pr9U0YPttLhvYxp2JE(this, string2));
    }

    public void setFileTransferStatus(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$1I5TANd1JGzUvxVPbWbmYgYHgZg(this, n));
    }

    public void setHeight(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$Ju03J4o5Gnha0Ynbq35sw9HL5nU(this, n));
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setLength(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$kXXTp4pKFNyBztnIElEJdJrz8F8(this, l));
    }

    public void setPreviewMimeType(String string2) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$Js49W5j_aEL3sBPRKR3zwBZEwQc(this, string2));
    }

    public void setPreviewUri(Uri uri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$4bTF8UNuphmPWGI1zJtDN0vEMKQ(this, uri));
    }

    public void setTransferOffset(long l) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$NeUx42_gy02_DXOOj3iF2Y92GoU(this, l));
    }

    public void setWidth(int n) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new _$$Lambda$RcsFileTransferPart$dlGXDrIqL_9NsNgH4LIS6Yg7j6k(this, n));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RcsFileTransferStatus {
    }

}

