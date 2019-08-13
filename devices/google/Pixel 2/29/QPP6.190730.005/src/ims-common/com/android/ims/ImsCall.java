/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Message
 *  android.os.Parcel
 *  android.telecom.ConferenceParticipant
 *  android.telephony.CallQuality
 *  android.telephony.Rlog
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsCallSession$Listener
 *  android.telephony.ims.ImsConferenceState
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.ImsStreamMediaProfile
 *  android.telephony.ims.ImsSuppServiceNotification
 *  android.util.Log
 *  com.android.ims.ImsException
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.ims;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.telecom.ConferenceParticipant;
import android.telephony.CallQuality;
import android.telephony.Rlog;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.util.Log;
import com.android.ims.ImsException;
import com.android.ims.internal.ICall;
import com.android.ims.internal.ImsStreamMediaSession;
import com.android.internal.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ImsCall
implements ICall {
    private static final boolean CONF_DBG = true;
    private static final boolean DBG = Log.isLoggable((String)"ImsCall", (int)3);
    private static final boolean FORCE_DEBUG = false;
    private static final String TAG = "ImsCall";
    private static final int UPDATE_EXTEND_TO_CONFERENCE = 5;
    private static final int UPDATE_HOLD = 1;
    private static final int UPDATE_HOLD_MERGE = 2;
    private static final int UPDATE_MERGE = 4;
    private static final int UPDATE_NONE = 0;
    private static final int UPDATE_RESUME = 3;
    private static final int UPDATE_UNSPECIFIED = 6;
    public static final int USSD_MODE_NOTIFY = 0;
    public static final int USSD_MODE_REQUEST = 1;
    private static final boolean VDBG = Log.isLoggable((String)"ImsCall", (int)2);
    private static final AtomicInteger sUniqueIdGenerator = new AtomicInteger();
    private boolean mAnswerWithRtt = false;
    private ImsCallProfile mCallProfile = null;
    private boolean mCallSessionMergePending = false;
    private List<ConferenceParticipant> mConferenceParticipants;
    private Context mContext;
    private boolean mHold = false;
    private ImsCallSessionListenerProxy mImsCallSessionListenerProxy;
    private boolean mInCall = false;
    private boolean mIsConferenceHost = false;
    private boolean mIsMerged = false;
    private ImsReasonInfo mLastReasonInfo = null;
    private Listener mListener = null;
    private Object mLockObj = new Object();
    private ImsStreamMediaSession mMediaSession = null;
    private ImsCall mMergeHost = null;
    private ImsCall mMergePeer = null;
    private boolean mMergeRequestedByConference = false;
    private boolean mMute = false;
    private int mOverrideReason = 0;
    private ImsCallProfile mProposedCallProfile = null;
    private ImsCallSession mSession = null;
    private boolean mSessionEndDuringMerge = false;
    private ImsReasonInfo mSessionEndDuringMergeReasonInfo = null;
    private boolean mTerminationRequestPending = false;
    private ImsCallSession mTransientConferenceSession = null;
    private int mUpdateRequest = 0;
    private boolean mWasVideoCall = false;
    public final int uniqueId;

    public ImsCall(Context context, ImsCallProfile imsCallProfile) {
        this.mContext = context;
        this.setCallProfile(imsCallProfile);
        this.uniqueId = sUniqueIdGenerator.getAndIncrement();
    }

    static /* synthetic */ ImsCallProfile access$300(ImsCall imsCall) {
        return imsCall.mCallProfile;
    }

    private String appendImsCallInfoToString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" ImsCall=");
        stringBuilder.append(this);
        return stringBuilder.toString();
    }

    private void clear(ImsReasonInfo imsReasonInfo) {
        this.mInCall = false;
        this.mHold = false;
        this.mUpdateRequest = 0;
        this.mLastReasonInfo = imsReasonInfo;
    }

    private void clearMergeInfo() {
        this.logi("clearMergeInfo :: clearing all merge info");
        ImsCall imsCall = this.mMergeHost;
        if (imsCall != null) {
            imsCall.mMergePeer = null;
            imsCall.mUpdateRequest = 0;
            imsCall.mCallSessionMergePending = false;
        }
        if ((imsCall = this.mMergePeer) != null) {
            imsCall.mMergeHost = null;
            imsCall.mUpdateRequest = 0;
            imsCall.mCallSessionMergePending = false;
        }
        this.mMergeHost = null;
        this.mMergePeer = null;
        this.mUpdateRequest = 0;
        this.mCallSessionMergePending = false;
    }

    private void clearSessionTerminationFlags() {
        this.mSessionEndDuringMerge = false;
        this.mSessionEndDuringMergeReasonInfo = null;
    }

    private ImsCallSession.Listener createCallSessionListener() {
        this.mImsCallSessionListenerProxy = new ImsCallSessionListenerProxy();
        return this.mImsCallSessionListenerProxy;
    }

    private ImsStreamMediaProfile createHoldMediaProfile() {
        ImsStreamMediaProfile imsStreamMediaProfile = new ImsStreamMediaProfile();
        ImsCallProfile imsCallProfile = this.mCallProfile;
        if (imsCallProfile == null) {
            return imsStreamMediaProfile;
        }
        imsStreamMediaProfile.mAudioQuality = imsCallProfile.mMediaProfile.mAudioQuality;
        imsStreamMediaProfile.mVideoQuality = this.mCallProfile.mMediaProfile.mVideoQuality;
        imsStreamMediaProfile.mAudioDirection = 2;
        if (imsStreamMediaProfile.mVideoQuality != 0) {
            imsStreamMediaProfile.mVideoDirection = 2;
        }
        return imsStreamMediaProfile;
    }

    private ImsCall createNewCall(ImsCallSession object, ImsCallProfile object2) {
        object2 = new ImsCall(this.mContext, (ImsCallProfile)object2);
        try {
            ((ImsCall)object2).attachSession((ImsCallSession)object);
            object = object2;
        }
        catch (ImsException imsException) {
            ((ImsCall)object2).close();
            object = null;
        }
        return object;
    }

    private ImsStreamMediaProfile createResumeMediaProfile() {
        ImsStreamMediaProfile imsStreamMediaProfile = new ImsStreamMediaProfile();
        ImsCallProfile imsCallProfile = this.mCallProfile;
        if (imsCallProfile == null) {
            return imsStreamMediaProfile;
        }
        imsStreamMediaProfile.mAudioQuality = imsCallProfile.mMediaProfile.mAudioQuality;
        imsStreamMediaProfile.mVideoQuality = this.mCallProfile.mMediaProfile.mVideoQuality;
        imsStreamMediaProfile.mAudioDirection = 3;
        if (imsStreamMediaProfile.mVideoQuality != 0) {
            imsStreamMediaProfile.mVideoDirection = 3;
        }
        return imsStreamMediaProfile;
    }

    private void enforceConversationMode() {
        if (this.mInCall) {
            this.mHold = false;
            this.mUpdateRequest = 0;
        }
    }

    private boolean isMergeHost() {
        boolean bl = this.mMergePeer != null && this.mMergeHost == null;
        return bl;
    }

    private boolean isMergePeer() {
        boolean bl = this.mMergePeer == null && this.mMergeHost != null;
        return bl;
    }

    private boolean isMerging() {
        boolean bl = this.mMergePeer != null || this.mMergeHost != null;
        return bl;
    }

    public static boolean isSessionAlive(ImsCallSession imsCallSession) {
        boolean bl = imsCallSession != null && imsCallSession.isAlive();
        return bl;
    }

    private boolean isTransientConferenceSession(ImsCallSession imsCallSession) {
        return imsCallSession != null && imsCallSession != this.mSession && imsCallSession == this.mTransientConferenceSession;
    }

    private boolean isUpdatePending(ImsCall object) {
        if (object != null && ((ImsCall)object).mUpdateRequest != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("merge :: update is in progress; request=");
            ((StringBuilder)object).append(this.updateRequestToString(this.mUpdateRequest));
            this.loge(((StringBuilder)object).toString());
            return true;
        }
        return false;
    }

    private void logd(String string) {
        Log.d((String)TAG, (String)this.appendImsCallInfoToString(string));
    }

    private void loge(String string) {
        Log.e((String)TAG, (String)this.appendImsCallInfoToString(string));
    }

    private void loge(String string, Throwable throwable) {
        Log.e((String)TAG, (String)this.appendImsCallInfoToString(string), (Throwable)throwable);
    }

    private void logi(String string) {
        Log.i((String)TAG, (String)this.appendImsCallInfoToString(string));
    }

    private void logv(String string) {
        Log.v((String)TAG, (String)this.appendImsCallInfoToString(string));
    }

    private void markCallAsMerged(boolean bl) {
        if (!ImsCall.isSessionAlive(this.mSession)) {
            String string;
            int n;
            this.logi("markCallAsMerged");
            this.setIsMerged(bl);
            this.mSessionEndDuringMerge = true;
            if (bl) {
                n = 510;
                string = "Call ended by network";
            } else {
                n = 108;
                string = "Call ended during conference merge process.";
            }
            this.mSessionEndDuringMergeReasonInfo = new ImsReasonInfo(n, 0, string);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void merge() throws ImsException {
        this.logi("merge :: ");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.isUpdatePending(this)) {
                this.setCallSessionMergePending(false);
                if (this.mMergePeer != null) {
                    this.mMergePeer.setCallSessionMergePending(false);
                }
                if (this.mMergeHost != null) {
                    this.mMergeHost.setCallSessionMergePending(false);
                }
                ImsException imsException = new ImsException("Call update is in progress", 102);
                throw imsException;
            }
            if (!this.isUpdatePending(this.mMergePeer) && !this.isUpdatePending(this.mMergeHost)) {
                if (this.mSession == null) {
                    this.loge("merge :: no call session");
                    ImsException imsException = new ImsException("No call session", 148);
                    throw imsException;
                }
                if (!this.mHold && !this.mContext.getResources().getBoolean(17891614)) {
                    this.mSession.hold(this.createHoldMediaProfile());
                    this.mHold = true;
                    this.mUpdateRequest = 2;
                } else {
                    if (this.mMergePeer != null && !this.mMergePeer.isMultiparty() && !this.isMultiparty()) {
                        this.mUpdateRequest = 4;
                        this.mMergePeer.mUpdateRequest = 4;
                    } else if (this.mMergeHost != null && !this.mMergeHost.isMultiparty() && !this.isMultiparty()) {
                        this.mUpdateRequest = 4;
                        this.mMergeHost.mUpdateRequest = 4;
                    }
                    this.mSession.merge();
                }
                return;
            }
            this.setCallSessionMergePending(false);
            if (this.mMergePeer != null) {
                this.mMergePeer.setCallSessionMergePending(false);
            }
            if (this.mMergeHost != null) {
                this.mMergeHost.setCallSessionMergePending(false);
            }
            ImsException imsException = new ImsException("Peer or host call update is in progress", 102);
            throw imsException;
        }
    }

    private void mergeInternal() {
        this.logi("mergeInternal :: ");
        this.mSession.merge();
        this.mUpdateRequest = 4;
    }

    private void notifyConferenceSessionTerminated(ImsReasonInfo imsReasonInfo) {
        Listener listener = this.mListener;
        this.clear(imsReasonInfo);
        if (listener != null) {
            try {
                listener.onCallTerminated(this, imsReasonInfo);
            }
            catch (Throwable throwable) {
                this.loge("notifyConferenceSessionTerminated :: ", throwable);
            }
        }
    }

    private void notifyConferenceStateUpdated(ImsConferenceState object) {
        if (object != null && ((ImsConferenceState)object).mParticipants != null) {
            object = ((ImsConferenceState)object).mParticipants.entrySet();
            if (object == null) {
                return;
            }
            List<ConferenceParticipant> list = object.iterator();
            this.mConferenceParticipants = new ArrayList<ConferenceParticipant>(object.size());
            while (list.hasNext()) {
                object = (Map.Entry)list.next();
                String string = (String)object.getKey();
                object = (Bundle)object.getValue();
                String string2 = object.getString("status");
                String string3 = object.getString("user");
                String string4 = object.getString("display-text");
                object = object.getString("endpoint");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyConferenceStateUpdated :: key=");
                stringBuilder.append(Rlog.pii((String)TAG, (Object)string));
                stringBuilder.append(", status=");
                stringBuilder.append(string2);
                stringBuilder.append(", user=");
                stringBuilder.append(Rlog.pii((String)TAG, (Object)string3));
                stringBuilder.append(", displayName= ");
                stringBuilder.append(Rlog.pii((String)TAG, (Object)string4));
                stringBuilder.append(", endpoint=");
                stringBuilder.append((String)object);
                this.logi(stringBuilder.toString());
                string = Uri.parse((String)string3);
                if (object == null) {
                    object = "";
                }
                object = Uri.parse((String)object);
                int n = ImsConferenceState.getConnectionStateForStatus((String)string2);
                if (n == 6) continue;
                object = new ConferenceParticipant((Uri)string, string4, (Uri)object, n, -1);
                this.mConferenceParticipants.add((ConferenceParticipant)object);
            }
            list = this.mConferenceParticipants;
            if (list != null && (object = this.mListener) != null) {
                try {
                    ((Listener)object).onConferenceParticipantsStateChanged(this, list);
                }
                catch (Throwable throwable) {
                    this.loge("notifyConferenceStateUpdated :: ", throwable);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifySessionTerminatedDuringMerge() {
        Listener listener;
        boolean bl = false;
        ImsReasonInfo imsReasonInfo = null;
        synchronized (this) {
            listener = this.mListener;
            if (this.mSessionEndDuringMerge) {
                this.logi("notifySessionTerminatedDuringMerge ::reporting terminate during merge");
                bl = true;
                imsReasonInfo = this.mSessionEndDuringMergeReasonInfo;
            }
            this.clearSessionTerminationFlags();
        }
        if (listener == null) return;
        if (!bl) return;
        try {
            this.processCallTerminated(imsReasonInfo);
            return;
        }
        catch (Throwable throwable) {
            this.loge("notifySessionTerminatedDuringMerge :: ", throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processCallTerminated(ImsReasonInfo imsReasonInfo) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("processCallTerminated :: reason=");
        ((StringBuilder)object).append((Object)imsReasonInfo);
        ((StringBuilder)object).append(" userInitiated = ");
        ((StringBuilder)object).append(this.mTerminationRequestPending);
        this.logi(((StringBuilder)object).toString());
        synchronized (this) {
            if (this.isCallSessionMergePending() && !this.mTerminationRequestPending) {
                this.logi("processCallTerminated :: burying termination during ongoing merge.");
                this.mSessionEndDuringMerge = true;
                this.mSessionEndDuringMergeReasonInfo = imsReasonInfo;
                return;
            }
            if (this.isMultiparty()) {
                this.notifyConferenceSessionTerminated(imsReasonInfo);
                return;
            }
            object = this.mListener;
            this.clear(imsReasonInfo);
        }
        if (object == null) return;
        try {
            ((Listener)object).onCallTerminated(this, imsReasonInfo);
            return;
        }
        catch (Throwable throwable) {
            this.loge("processCallTerminated :: ", throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processMergeComplete() {
        Object object;
        Object object2;
        Object object3;
        this.logi("processMergeComplete :: ");
        if (!this.isMergeHost()) {
            this.loge("processMergeComplete :: We are not the merge host!");
            return;
        }
        boolean bl = false;
        synchronized (this) {
            if (this.isMultiparty()) {
                this.setIsMerged(false);
                if (!this.mMergeRequestedByConference) {
                    this.mHold = false;
                    bl = true;
                }
                this.mMergePeer.markCallAsMerged(false);
                object2 = this;
                object3 = this.mMergePeer;
            } else {
                if (this.mTransientConferenceSession == null) {
                    this.loge("processMergeComplete :: No transient session!");
                    return;
                }
                if (this.mMergePeer == null) {
                    this.loge("processMergeComplete :: No merge peer!");
                    return;
                }
                object = this.mTransientConferenceSession;
                this.mTransientConferenceSession = null;
                object.setListener(null);
                if (ImsCall.isSessionAlive(this.mSession) && !ImsCall.isSessionAlive(this.mMergePeer.getCallSession())) {
                    this.mMergePeer.mHold = false;
                    this.mHold = true;
                    if (this.mConferenceParticipants != null && !this.mConferenceParticipants.isEmpty()) {
                        this.mMergePeer.mConferenceParticipants = this.mConferenceParticipants;
                    }
                    object3 = this.mMergePeer;
                    bl = true;
                    this.setIsMerged(false);
                    this.mMergePeer.setIsMerged(false);
                    this.logi("processMergeComplete :: transient will transfer to merge peer");
                    object2 = this;
                } else if (!ImsCall.isSessionAlive(this.mSession) && ImsCall.isSessionAlive(this.mMergePeer.getCallSession())) {
                    object3 = this;
                    object2 = this.mMergePeer;
                    bl = false;
                    this.setIsMerged(false);
                    this.mMergePeer.setIsMerged(false);
                    this.logi("processMergeComplete :: transient will stay with the merge host");
                } else {
                    object3 = this;
                    object2 = this.mMergePeer;
                    this.mMergePeer.markCallAsMerged(false);
                    bl = false;
                    this.setIsMerged(false);
                    this.mMergePeer.setIsMerged(true);
                    this.logi("processMergeComplete :: transient will stay with us (I'm the host).");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("processMergeComplete :: call=");
                stringBuilder.append(object3);
                stringBuilder.append(" is the final host");
                this.logi(stringBuilder.toString());
                ImsCall.super.setTransientSessionAsPrimary((ImsCallSession)object);
                object = object3;
                object3 = object2;
                object2 = object;
            }
            object = ((ImsCall)object2).mListener;
            ImsCall.updateCallProfile((ImsCall)object3);
            ImsCall.updateCallProfile((ImsCall)object2);
            this.clearMergeInfo();
            ((ImsCall)object3).notifySessionTerminatedDuringMerge();
            ((ImsCall)object2).clearSessionTerminationFlags();
            ((ImsCall)object2).mIsConferenceHost = true;
        }
        if (object == null) return;
        try {
            ((Listener)object).onCallMerged((ImsCall)object2, (ImsCall)object3, bl);
        }
        catch (Throwable throwable) {
            this.loge("processMergeComplete :: ", throwable);
        }
        object3 = this.mConferenceParticipants;
        if (object3 == null) return;
        if (object3.isEmpty()) return;
        try {
            ((Listener)object).onConferenceParticipantsStateChanged((ImsCall)object2, this.mConferenceParticipants);
            return;
        }
        catch (Throwable throwable) {
            this.loge("processMergeComplete :: ", throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processMergeFailed(ImsReasonInfo imsReasonInfo) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("processMergeFailed :: reason=");
        ((StringBuilder)object).append((Object)imsReasonInfo);
        this.logi(((StringBuilder)object).toString());
        synchronized (this) {
            if (!this.isMergeHost()) {
                this.loge("processMergeFailed :: We are not the merge host!");
                return;
            }
            if (this.mTransientConferenceSession != null) {
                this.mTransientConferenceSession.setListener(null);
                this.mTransientConferenceSession = null;
            }
            object = this.mListener;
            this.markCallAsMerged(true);
            this.setCallSessionMergePending(false);
            this.notifySessionTerminatedDuringMerge();
            if (this.mMergePeer != null) {
                this.mMergePeer.markCallAsMerged(true);
                this.mMergePeer.setCallSessionMergePending(false);
                this.mMergePeer.notifySessionTerminatedDuringMerge();
            } else {
                this.loge("processMergeFailed :: No merge peer!");
            }
            this.clearMergeInfo();
        }
        if (object == null) return;
        try {
            ((Listener)object).onCallMergeFailed(this, imsReasonInfo);
            return;
        }
        catch (Throwable throwable) {
            this.loge("processMergeFailed :: ", throwable);
        }
    }

    private void setCallSessionMergePending(boolean bl) {
        this.mCallSessionMergePending = bl;
    }

    private void setMergePeer(ImsCall imsCall) {
        this.mMergePeer = imsCall;
        this.mMergeHost = null;
        imsCall.mMergeHost = this;
        imsCall.mMergePeer = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setTransientSessionAsPrimary(ImsCallSession imsCallSession) {
        synchronized (this) {
            this.mSession.setListener(null);
            this.mSession = imsCallSession;
            this.mSession.setListener(this.createCallSessionListener());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean shouldProcessConferenceResult() {
        boolean bl = false;
        synchronized (this) {
            CharSequence charSequence;
            boolean bl2 = this.isMergeHost();
            boolean bl3 = false;
            boolean bl4 = false;
            if (!bl2 && !this.isMergePeer()) {
                this.loge("shouldProcessConferenceResult :: no merge in progress");
                return false;
            }
            if (this.isMergeHost()) {
                this.logi("shouldProcessConferenceResult :: We are a merge host");
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("shouldProcessConferenceResult :: Here is the merge peer=");
                ((StringBuilder)charSequence).append(this.mMergePeer);
                this.logi(((StringBuilder)charSequence).toString());
                if (!this.isCallSessionMergePending() && !this.mMergePeer.isCallSessionMergePending()) {
                    bl4 = true;
                }
                bl4 = bl = bl4;
                if (!this.isMultiparty()) {
                    bl4 = bl & ImsCall.isSessionAlive(this.mTransientConferenceSession);
                }
            } else if (this.isMergePeer()) {
                this.logi("shouldProcessConferenceResult :: We are a merge peer");
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("shouldProcessConferenceResult :: Here is the merge host=");
                ((StringBuilder)charSequence).append(this.mMergeHost);
                this.logi(((StringBuilder)charSequence).toString());
                if (!this.isCallSessionMergePending() && !this.mMergeHost.isCallSessionMergePending()) {
                    bl3 = true;
                }
                bl4 = !this.mMergeHost.isMultiparty() ? bl3 & ImsCall.isSessionAlive(this.mMergeHost.mTransientConferenceSession) : this.isCallSessionMergePending() ^ true;
            } else {
                this.loge("shouldProcessConferenceResult : merge in progress but call is neither host nor peer.");
                bl4 = bl;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("shouldProcessConferenceResult :: returning:");
            charSequence = bl4 ? "true" : "false";
            stringBuilder.append((String)charSequence);
            this.logi(stringBuilder.toString());
            return bl4;
        }
    }

    private void throwImsException(Throwable throwable, int n) throws ImsException {
        if (throwable instanceof ImsException) {
            throw (ImsException)throwable;
        }
        throw new ImsException(String.valueOf(n), throwable, n);
    }

    private void trackVideoStateHistory(ImsCallProfile imsCallProfile) {
        boolean bl = this.mWasVideoCall || imsCallProfile.isVideoCall();
        this.mWasVideoCall = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateCallProfile() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.setCallProfile(this.mSession.getCallProfile());
            }
            return;
        }
    }

    private static void updateCallProfile(ImsCall imsCall) {
        if (imsCall != null) {
            imsCall.updateCallProfile();
        }
    }

    private String updateRequestToString(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 6: {
                return "UNSPECIFIED";
            }
            case 5: {
                return "EXTEND_TO_CONFERENCE";
            }
            case 4: {
                return "MERGE";
            }
            case 3: {
                return "RESUME";
            }
            case 2: {
                return "HOLD_MERGE";
            }
            case 1: {
                return "HOLD";
            }
            case 0: 
        }
        return "NONE";
    }

    public void accept(int n) throws ImsException {
        this.accept(n, new ImsStreamMediaProfile());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void accept(int n, ImsStreamMediaProfile object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("accept :: callType=");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(", profile=");
        ((StringBuilder)object2).append(object);
        this.logi(((StringBuilder)object2).toString());
        if (this.mAnswerWithRtt) {
            object.mRttMode = 1;
            this.logi("accept :: changing media profile RTT mode to full");
        }
        object2 = this.mLockObj;
        synchronized (object2) {
            block9 : {
                Object object3 = this.mSession;
                if (object3 == null) {
                    object = new ImsException("No call to answer", 148);
                    throw object;
                }
                try {
                    this.mSession.accept(n, object);
                    if (this.mInCall && this.mProposedCallProfile != null) {
                        if (DBG) {
                            this.logi("accept :: call profile will be updated");
                        }
                        this.mCallProfile = this.mProposedCallProfile;
                        this.trackVideoStateHistory(this.mCallProfile);
                        this.mProposedCallProfile = null;
                    }
                    if (!this.mInCall || this.mUpdateRequest != 6) break block9;
                    this.mUpdateRequest = 0;
                }
                catch (Throwable throwable) {
                    this.loge("accept :: ", throwable);
                    object3 = new ImsException("accept()", throwable, 0);
                    throw object3;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachSession(ImsCallSession imsCallSession) throws ImsException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("attachSession :: session=");
        ((StringBuilder)object).append((Object)imsCallSession);
        this.logi(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            this.mSession = imsCallSession;
            try {
                this.mSession.setListener(this.createCallSessionListener());
            }
            catch (Throwable throwable) {
                this.loge("attachSession :: ", throwable);
                this.throwImsException(throwable, 0);
            }
            return;
        }
    }

    @Override
    public boolean checkIfRemoteUserIsSame(String string) {
        if (string == null) {
            return false;
        }
        return string.equals(this.mCallProfile.getCallExtra("remote_uri", ""));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.mSession.close();
                this.mSession = null;
            } else {
                this.logi("close :: Cannot close Null call session!");
            }
            this.mCallProfile = null;
            this.mProposedCallProfile = null;
            this.mLastReasonInfo = null;
            this.mMediaSession = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void conferenceStateUpdated(ImsConferenceState imsConferenceState) {
        Listener listener;
        synchronized (this) {
            this.notifyConferenceStateUpdated(imsConferenceState);
            listener = this.mListener;
        }
        if (listener == null) return;
        try {
            listener.onCallConferenceStateUpdated(this, imsConferenceState);
            return;
        }
        catch (Throwable throwable) {
            this.loge("callSessionConferenceStateUpdated :: ", throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deflect(String object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("deflect :: session=");
        ((StringBuilder)object2).append((Object)this.mSession);
        ((StringBuilder)object2).append(", number=");
        ((StringBuilder)object2).append(Rlog.pii((String)TAG, (Object)object));
        this.logi(((StringBuilder)object2).toString());
        object2 = this.mLockObj;
        synchronized (object2) {
            Object object3 = this.mSession;
            if (object3 == null) {
                object = new ImsException("No call to deflect", 148);
                throw object;
            }
            try {
                this.mSession.deflect(object);
                return;
            }
            catch (Throwable throwable) {
                this.loge("deflect :: ", throwable);
                object3 = new ImsException("deflect()", throwable, 0);
                throw object3;
            }
        }
    }

    @Override
    public boolean equalsTo(ICall iCall) {
        if (iCall == null) {
            return false;
        }
        if (iCall instanceof ImsCall) {
            return this.equals(iCall);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void extendToConference(String[] object) throws ImsException {
        this.logi("extendToConference ::");
        if (this.isOnHold()) {
            if (DBG) {
                this.logi("extendToConference :: call is on hold");
            }
            throw new ImsException("Not in a call to extend a call to conference", 102);
        }
        Object object2 = this.mLockObj;
        synchronized (object2) {
            if (this.mUpdateRequest != 0) {
                object = new StringBuilder();
                object.append("extendToConference :: update is in progress; request=");
                object.append(this.updateRequestToString(this.mUpdateRequest));
                this.logi(object.toString());
                object = new ImsException("Call update is in progress", 102);
                throw object;
            }
            if (this.mSession != null) {
                this.mSession.extendToConference(object);
                this.mUpdateRequest = 5;
                return;
            }
            this.loge("extendToConference :: ");
            object = new ImsException("No call session", 148);
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getCallExtra(String object) throws ImsException {
        Object object2 = this.mLockObj;
        synchronized (object2) {
            Object object3 = this.mSession;
            if (object3 == null) {
                object = new ImsException("No call session", 148);
                throw object;
            }
            try {
                return this.mSession.getProperty(object);
            }
            catch (Throwable throwable) {
                this.loge("getCallExtra :: ", throwable);
                object3 = new ImsException("getCallExtra()", throwable, 0);
                throw object3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallProfile getCallProfile() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mCallProfile;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallSession getCallSession() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ConferenceParticipant> getConferenceParticipants() {
        Object object = this.mLockObj;
        synchronized (object) {
            Serializable serializable = new StringBuilder();
            serializable.append("getConferenceParticipants :: mConferenceParticipants");
            serializable.append(this.mConferenceParticipants);
            this.logi(serializable.toString());
            if (this.mConferenceParticipants == null) {
                return null;
            }
            if (!this.mConferenceParticipants.isEmpty()) return new ArrayList(this.mConferenceParticipants);
            return new ArrayList(0);
        }
    }

    @VisibleForTesting
    public ImsCallSessionListenerProxy getImsCallSessionListenerProxy() {
        return this.mImsCallSessionListenerProxy;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsReasonInfo getLastReasonInfo() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mLastReasonInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallProfile getLocalCallProfile() throws ImsException {
        Object object = this.mLockObj;
        synchronized (object) {
            Object object2 = this.mSession;
            if (object2 == null) {
                object2 = new ImsException("No call session", 148);
                throw object2;
            }
            try {
                return this.mSession.getLocalCallProfile();
            }
            catch (Throwable throwable) {
                this.loge("getLocalCallProfile :: ", throwable);
                object2 = new ImsException("getLocalCallProfile()", throwable, 0);
                throw object2;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsStreamMediaSession getMediaSession() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mMediaSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallProfile getProposedCallProfile() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.isInCall()) return this.mProposedCallProfile;
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getRadioTechnology() {
        Object object = this.mLockObj;
        synchronized (object) {
            String string;
            block9 : {
                block8 : {
                    if (this.mCallProfile == null) {
                        return 0;
                    }
                    String string2 = this.mCallProfile.getCallExtra("CallRadioTech");
                    if (string2 == null) break block8;
                    string = string2;
                    if (!string2.isEmpty()) break block9;
                }
                string = this.mCallProfile.getCallExtra("callRadioTech");
            }
            try {
                return Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                return 0;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallProfile getRemoteCallProfile() throws ImsException {
        Object object = this.mLockObj;
        synchronized (object) {
            Object object2 = this.mSession;
            if (object2 == null) {
                object2 = new ImsException("No call session", 148);
                throw object2;
            }
            try {
                return this.mSession.getRemoteCallProfile();
            }
            catch (Throwable throwable) {
                this.loge("getRemoteCallProfile :: ", throwable);
                ImsException imsException = new ImsException("getRemoteCallProfile()", throwable, 0);
                throw imsException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImsCallSession getSession() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getState() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) return this.mSession.getState();
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasPendingUpdate() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mUpdateRequest == 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void hold() throws ImsException {
        this.logi("hold :: ");
        if (this.isOnHold()) {
            if (DBG) {
                this.logi("hold :: call is already on hold");
            }
            return;
        }
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mUpdateRequest != 0) {
                Object object2 = new StringBuilder();
                object2.append("hold :: update is in progress; request=");
                object2.append(this.updateRequestToString(this.mUpdateRequest));
                this.loge(object2.toString());
                object2 = new ImsException("Call update is in progress", 102);
                throw object2;
            }
            if (this.mSession != null) {
                this.mSession.hold(this.createHoldMediaProfile());
                this.mHold = true;
                this.mUpdateRequest = 1;
                return;
            }
            ImsException imsException = new ImsException("No call session", 148);
            throw imsException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void inviteParticipants(String[] object) throws ImsException {
        this.logi("inviteParticipants ::");
        Object object2 = this.mLockObj;
        synchronized (object2) {
            if (this.mSession != null) {
                this.mSession.inviteParticipants(object);
                return;
            }
            this.loge("inviteParticipants :: ");
            object = new ImsException("No call session", 148);
            throw object;
        }
    }

    public boolean isCallSessionMergePending() {
        return this.mCallSessionMergePending;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConferenceHost() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (!this.isMultiparty()) return false;
            if (!this.mIsConferenceHost) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isInCall() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mInCall;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isMergeRequestedByConf() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mMergeRequestedByConference;
        }
    }

    public boolean isMerged() {
        return this.mIsMerged;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isMultiparty() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) return this.mSession.isMultiparty();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isMuted() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mMute;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isOnHold() {
        Object object = this.mLockObj;
        synchronized (object) {
            return this.mHold;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isPendingHold() {
        Object object = this.mLockObj;
        synchronized (object) {
            int n = this.mUpdateRequest;
            boolean bl = true;
            if (n != 1) return false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isVideoCall() {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mCallProfile == null) return false;
            if (!this.mCallProfile.isVideoCall()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isWifiCall() {
        Object object = this.mLockObj;
        synchronized (object) {
            ImsCallProfile imsCallProfile = this.mCallProfile;
            boolean bl = false;
            if (imsCallProfile == null) {
                return false;
            }
            if (this.getRadioTechnology() != 18) return bl;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void merge(ImsCall imsCall) throws ImsException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("merge(1) :: bgImsCall=");
        ((StringBuilder)object).append(imsCall);
        this.logi(((StringBuilder)object).toString());
        if (imsCall == null) throw new ImsException("No background call", 101);
        object = this.mLockObj;
        // MONITORENTER : object
        this.setCallSessionMergePending(true);
        imsCall.setCallSessionMergePending(true);
        if (!this.isMultiparty() && !imsCall.isMultiparty() || this.isMultiparty()) {
            this.setMergePeer(imsCall);
        } else {
            this.setMergeHost(imsCall);
        }
        // MONITOREXIT : object
        if (this.isMultiparty()) {
            this.mMergeRequestedByConference = true;
        } else {
            this.logi("merge : mMergeRequestedByConference not set");
        }
        this.merge();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reject(int n) throws ImsException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("reject :: reason=");
        ((StringBuilder)object).append(n);
        this.logi(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.mSession.reject(n);
            }
            if (this.mInCall && this.mProposedCallProfile != null) {
                if (DBG) {
                    this.logi("reject :: call profile is not updated; destroy it...");
                }
                this.mProposedCallProfile = null;
            }
            if (this.mInCall && this.mUpdateRequest == 6) {
                this.mUpdateRequest = 0;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeParticipants(String[] object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("removeParticipants :: session=");
        ((StringBuilder)object2).append((Object)this.mSession);
        this.logi(((StringBuilder)object2).toString());
        object2 = this.mLockObj;
        synchronized (object2) {
            if (this.mSession != null) {
                this.mSession.removeParticipants(object);
                return;
            }
            this.loge("removeParticipants :: ");
            object = new ImsException("No call session", 148);
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resetIsMergeRequestedByConf(boolean bl) {
        Object object = this.mLockObj;
        synchronized (object) {
            this.mMergeRequestedByConference = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resume() throws ImsException {
        this.logi("resume :: ");
        if (!this.isOnHold()) {
            if (DBG) {
                this.logi("resume :: call is not being held");
            }
            return;
        }
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mUpdateRequest != 0) {
                Object object2 = new StringBuilder();
                object2.append("resume :: update is in progress; request=");
                object2.append(this.updateRequestToString(this.mUpdateRequest));
                this.loge(object2.toString());
                object2 = new ImsException("Call update is in progress", 102);
                throw object2;
            }
            if (this.mSession != null) {
                this.mUpdateRequest = 3;
                this.mSession.resume(this.createResumeMediaProfile());
                return;
            }
            this.loge("resume :: ");
            ImsException imsException = new ImsException("No call session", 148);
            throw imsException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendDtmf(char c, Message message) {
        this.logi("sendDtmf :: ");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.mSession.sendDtmf(c, message);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendRttMessage(String string) {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession == null) {
                this.loge("sendRttMessage::no session");
            }
            if (!this.mCallProfile.mMediaProfile.isRttCall()) {
                this.logi("sendRttMessage::Not an rtt call, ignoring");
                return;
            }
            this.mSession.sendRttMessage(string);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendRttModifyRequest(boolean bl) {
        this.logi("sendRttModifyRequest");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession == null) {
                this.loge("sendRttModifyRequest::no session");
            }
            if (bl && this.mCallProfile.mMediaProfile.isRttCall()) {
                this.logi("sendRttModifyRequest::Already RTT call, ignoring request to turn on.");
                return;
            }
            if (!bl && !this.mCallProfile.mMediaProfile.isRttCall()) {
                this.logi("sendRttModifyRequest::Not RTT call, ignoring request to turn off.");
                return;
            }
            Parcel parcel = Parcel.obtain();
            ImsCallProfile imsCallProfile = this.mCallProfile;
            int n = 0;
            imsCallProfile.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            imsCallProfile = new ImsCallProfile(parcel);
            parcel = imsCallProfile.mMediaProfile;
            if (bl) {
                n = 1;
            }
            parcel.setRttMode(n);
            this.mSession.sendRttModifyRequest(imsCallProfile);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendRttModifyResponse(boolean bl) {
        this.logi("sendRttModifyResponse");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession == null) {
                this.loge("sendRttModifyResponse::no session");
            }
            if (this.mCallProfile.mMediaProfile.isRttCall()) {
                this.logi("sendRttModifyResponse::Already RTT call, ignoring.");
                return;
            }
            this.mSession.sendRttModifyResponse(bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendUssd(String object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("sendUssd :: ussdMessage=");
        ((StringBuilder)object2).append((String)object);
        this.logi(((StringBuilder)object2).toString());
        object2 = this.mLockObj;
        synchronized (object2) {
            if (this.mSession != null) {
                this.mSession.sendUssd(object);
                return;
            }
            this.loge("sendUssd :: ");
            object = new ImsException("No call session", 148);
            throw object;
        }
    }

    public void setAnswerWithRtt() {
        this.mAnswerWithRtt = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void setCallProfile(ImsCallProfile imsCallProfile) {
        Object object = this.mLockObj;
        synchronized (object) {
            this.mCallProfile = imsCallProfile;
            this.trackVideoStateHistory(this.mCallProfile);
            return;
        }
    }

    public void setIsMerged(boolean bl) {
        this.mIsMerged = bl;
    }

    public void setListener(Listener listener) {
        this.setListener(listener, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setListener(Listener listener, boolean bl) {
        Throwable throwable2;
        Object object = this.mLockObj;
        // MONITORENTER : object
        this.mListener = listener;
        if (listener == null) {
            // MONITOREXIT : object
            return;
        }
        if (!bl) {
            return;
        }
        bl = this.mInCall;
        boolean bl2 = this.mHold;
        int n = this.getState();
        ImsReasonInfo imsReasonInfo = this.mLastReasonInfo;
        // MONITOREXIT : object
        if (imsReasonInfo != null) {
            try {
                listener.onCallError(this, imsReasonInfo);
                return;
            }
            catch (Throwable throwable2) {}
        } else {
            if (bl) {
                if (bl2) {
                    listener.onCallHeld(this);
                    return;
                }
                listener.onCallStarted(this);
                return;
            }
            if (n == 3) {
                listener.onCallProgressing(this);
                return;
            }
            if (n != 8) {
                return;
            }
            listener.onCallTerminated(this, imsReasonInfo);
            return;
        }
        this.loge("setListener() :: ", throwable2);
    }

    public void setMergeHost(ImsCall imsCall) {
        this.mMergeHost = imsCall;
        this.mMergePeer = null;
        imsCall.mMergeHost = null;
        imsCall.mMergePeer = this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMute(boolean bl) throws ImsException {
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mMute != bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setMute :: turning mute ");
                String string = bl ? "on" : "off";
                stringBuilder.append(string);
                this.logi(stringBuilder.toString());
                this.mMute = bl;
                try {
                    this.mSession.setMute(bl);
                }
                catch (Throwable throwable) {
                    this.loge("setMute :: ", throwable);
                    this.throwImsException(throwable, 0);
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start(ImsCallSession object, String string) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("start(1) :: session=");
        ((StringBuilder)object2).append(object);
        this.logi(((StringBuilder)object2).toString());
        object2 = this.mLockObj;
        synchronized (object2) {
            this.mSession = object;
            try {
                object.setListener(this.createCallSessionListener());
                object.start(string, this.mCallProfile);
                return;
            }
            catch (Throwable throwable) {
                this.loge("start(1) :: ", throwable);
                object = new ImsException("start(1)", throwable, 0);
                throw object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start(ImsCallSession imsCallSession, String[] object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("start(n) :: session=");
        ((StringBuilder)object2).append((Object)imsCallSession);
        this.logi(((StringBuilder)object2).toString());
        object2 = this.mLockObj;
        synchronized (object2) {
            this.mSession = imsCallSession;
            try {
                imsCallSession.setListener(this.createCallSessionListener());
                imsCallSession.start(object, this.mCallProfile);
                return;
            }
            catch (Throwable throwable) {
                this.loge("start(n) :: ", throwable);
                object = new ImsException("start(n)", throwable, 0);
                throw object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startDtmf(char c) {
        this.logi("startDtmf :: ");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.mSession.startDtmf(c);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopDtmf() {
        this.logi("stopDtmf :: ");
        Object object = this.mLockObj;
        synchronized (object) {
            if (this.mSession != null) {
                this.mSession.stopDtmf();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void terminate(int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("terminate :: reason=");
        ((StringBuilder)object).append(n);
        this.logi(((StringBuilder)object).toString());
        object = this.mLockObj;
        synchronized (object) {
            this.mHold = false;
            this.mInCall = false;
            this.mTerminationRequestPending = true;
            if (this.mSession != null) {
                this.mSession.terminate(n);
            }
            return;
        }
    }

    public void terminate(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("terminate :: reason=");
        stringBuilder.append(n);
        stringBuilder.append(" ; overrideReason=");
        stringBuilder.append(n2);
        this.logi(stringBuilder.toString());
        this.mOverrideReason = n2;
        this.terminate(n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsCall objId:");
        stringBuilder.append(System.identityHashCode(this));
        stringBuilder.append(" onHold:");
        boolean bl = this.isOnHold();
        String string = "Y";
        CharSequence charSequence = bl ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" mute:");
        charSequence = this.isMuted() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        if (this.mCallProfile != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" mCallProfile:");
            ((StringBuilder)charSequence).append((Object)this.mCallProfile);
            stringBuilder.append(((StringBuilder)charSequence).toString());
            stringBuilder.append(" tech:");
            stringBuilder.append(this.mCallProfile.getCallExtra("CallRadioTech"));
        }
        stringBuilder.append(" updateRequest:");
        stringBuilder.append(this.updateRequestToString(this.mUpdateRequest));
        stringBuilder.append(" merging:");
        charSequence = this.isMerging() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        if (this.isMerging()) {
            if (this.isMergePeer()) {
                stringBuilder.append("P");
            } else {
                stringBuilder.append("H");
            }
        }
        stringBuilder.append(" merge action pending:");
        charSequence = this.isCallSessionMergePending() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" merged:");
        charSequence = this.isMerged() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" multiParty:");
        charSequence = this.isMultiparty() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" confHost:");
        charSequence = this.isConferenceHost() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" buried term:");
        charSequence = this.mSessionEndDuringMerge ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" isVideo: ");
        charSequence = this.isVideoCall() ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" wasVideo: ");
        charSequence = this.mWasVideoCall ? "Y" : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" isWifi: ");
        charSequence = this.isWifiCall() ? string : "N";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" session:");
        stringBuilder.append((Object)this.mSession);
        stringBuilder.append(" transientSession:");
        stringBuilder.append((Object)this.mTransientConferenceSession);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(int n, ImsStreamMediaProfile object) throws ImsException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("update :: callType=");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(", mediaProfile=");
        ((StringBuilder)object2).append(object);
        this.logi(((StringBuilder)object2).toString());
        if (this.isOnHold()) {
            if (DBG) {
                this.logi("update :: call is on hold");
            }
            throw new ImsException("Not in a call to update call", 102);
        }
        object2 = this.mLockObj;
        synchronized (object2) {
            if (this.mUpdateRequest != 0) {
                if (DBG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("update :: update is in progress; request=");
                    ((StringBuilder)object).append(this.updateRequestToString(this.mUpdateRequest));
                    this.logi(((StringBuilder)object).toString());
                }
                object = new ImsException("Call update is in progress", 102);
                throw object;
            }
            if (this.mSession != null) {
                this.mSession.update(n, (ImsStreamMediaProfile)object);
                this.mUpdateRequest = 6;
                return;
            }
            this.loge("update :: ");
            object = new ImsException("No call session", 148);
            throw object;
        }
    }

    public boolean wasVideoCall() {
        return this.mWasVideoCall;
    }

    @VisibleForTesting
    public class ImsCallSessionListenerProxy
    extends ImsCallSession.Listener {
        private boolean doesCallSessionExistsInMerge(ImsCallSession object) {
            object = object.getCallId();
            boolean bl = ImsCall.this.isMergeHost() && Objects.equals(ImsCall.this.mMergePeer.mSession.getCallId(), object) || ImsCall.this.isMergePeer() && Objects.equals(ImsCall.this.mMergeHost.mSession.getCallId(), object) || Objects.equals(ImsCall.this.mSession.getCallId(), object);
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callQualityChanged(CallQuality callQuality) {
            Listener listener;
            ImsCall imsCall = ImsCall.this;
            synchronized (imsCall) {
                listener = ImsCall.this.mListener;
            }
            if (listener == null) return;
            try {
                listener.onCallQualityChanged(ImsCall.this, callQuality);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callQualityChanged:: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionConferenceExtendFailed(ImsCallSession object, ImsReasonInfo object2) {
            ImsCall imsCall = ImsCall.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("callSessionConferenceExtendFailed :: reasonInfo=");
            ((StringBuilder)object3).append(object2);
            imsCall.loge(((StringBuilder)object3).toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionConferenceExtendFailed :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mUpdateRequest = 0;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallConferenceExtendFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionConferenceExtendFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionConferenceExtendReceived(ImsCallSession object, ImsCallSession object2, ImsCallProfile object3) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionConferenceExtendReceived :: newSession=");
            stringBuilder.append(object2);
            stringBuilder.append(", profile=");
            stringBuilder.append(object3);
            imsCall.logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionConferenceExtendReceived :: not supported for transient conference session");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            if ((object2 = ImsCall.this.createNewCall((ImsCallSession)object2, (ImsCallProfile)object3)) == null) {
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallConferenceExtendReceived(ImsCall.this, (ImsCall)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionConferenceExtendReceived :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionConferenceExtended(ImsCallSession object, ImsCallSession object2, ImsCallProfile object3) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionConferenceExtended :: session=");
            stringBuilder.append(object);
            stringBuilder.append(" newSession=");
            stringBuilder.append(object2);
            stringBuilder.append(", profile=");
            stringBuilder.append(object3);
            imsCall.logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionConferenceExtended :: not supported for transient conference session=");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            if ((object2 = ImsCall.this.createNewCall((ImsCallSession)object2, (ImsCallProfile)object3)) == null) {
                this.callSessionConferenceExtendFailed((ImsCallSession)object, new ImsReasonInfo());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mUpdateRequest = 0;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallConferenceExtended(ImsCall.this, (ImsCall)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionConferenceExtended :: ", throwable);
            }
        }

        public void callSessionConferenceStateUpdated(ImsCallSession object, ImsConferenceState imsConferenceState) {
            object = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionConferenceStateUpdated :: state=");
            stringBuilder.append((Object)imsConferenceState);
            ((ImsCall)object).logi(stringBuilder.toString());
            ImsCall.this.conferenceStateUpdated(imsConferenceState);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionHandover(ImsCallSession object, int n, int n2, ImsReasonInfo imsReasonInfo) {
            ImsCall imsCall = ImsCall.this;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("callSessionHandover :: session=");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(", srcAccessTech=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(", targetAccessTech=");
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(", reasonInfo=");
            ((StringBuilder)object2).append((Object)imsReasonInfo);
            imsCall.logi(((StringBuilder)object2).toString());
            object = ImsCall.this;
            synchronized (object) {
                object2 = ImsCall.this.mListener;
            }
            if (object2 == null) return;
            try {
                ((Listener)object2).onCallHandover(ImsCall.this, n, n2, imsReasonInfo);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionHandover :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionHandoverFailed(ImsCallSession object, int n, int n2, ImsReasonInfo imsReasonInfo) {
            ImsCall imsCall = ImsCall.this;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("callSessionHandoverFailed :: session=");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(", srcAccessTech=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(", targetAccessTech=");
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(", reasonInfo=");
            ((StringBuilder)object2).append((Object)imsReasonInfo);
            imsCall.loge(((StringBuilder)object2).toString());
            object = ImsCall.this;
            synchronized (object) {
                object2 = ImsCall.this.mListener;
            }
            if (object2 == null) return;
            try {
                ((Listener)object2).onCallHandoverFailed(ImsCall.this, n, n2, imsReasonInfo);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionHandoverFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionHeld(ImsCallSession object, ImsCallProfile object2) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionHeld :: session=");
            stringBuilder.append(object);
            stringBuilder.append("profile=");
            stringBuilder.append(object2);
            imsCall.logi(stringBuilder.toString());
            object = ImsCall.this;
            synchronized (object) {
                ImsCall.this.setCallSessionMergePending(false);
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
                if (ImsCall.this.mUpdateRequest == 2) {
                    ImsCall.this.mergeInternal();
                    return;
                }
                ImsCall.this.mUpdateRequest = 0;
                object2 = ImsCall.this.mListener;
            }
            if (object2 == null) return;
            try {
                ((Listener)object2).onCallHeld(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionHeld :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionHoldFailed(ImsCallSession object, ImsReasonInfo object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionHoldFailed :: session");
            stringBuilder.append(object);
            stringBuilder.append("reasonInfo=");
            stringBuilder.append(object2);
            ((ImsCall)object3).loge(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionHoldFailed :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object3 = ImsCall.this;
            stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionHoldFailed :: session=");
            stringBuilder.append(object);
            stringBuilder.append(", reasonInfo=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            object = ImsCall.this.mLockObj;
            synchronized (object) {
                ImsCall.this.mHold = false;
            }
            object = ImsCall.this;
            synchronized (object) {
                if (ImsCall.this.mUpdateRequest == 2) {
                    // empty if block
                }
                ImsCall.this.mUpdateRequest = 0;
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallHoldFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionHoldFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionHoldReceived(ImsCallSession object, ImsCallProfile object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionHoldReceived :: session=");
            stringBuilder.append(object);
            stringBuilder.append("profile=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionHoldReceived :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallHoldReceived(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionHoldReceived :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionInviteParticipantsRequestDelivered(ImsCallSession object) {
            Listener listener;
            ImsCall.this.logi("callSessionInviteParticipantsRequestDelivered ::");
            if (ImsCall.this.isTransientConferenceSession(object)) {
                ImsCall imsCall = ImsCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("callSessionInviteParticipantsRequestDelivered :: not supported for conference session=");
                stringBuilder.append(object);
                imsCall.logi(stringBuilder.toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                listener = ImsCall.this.mListener;
            }
            if (listener == null) return;
            try {
                listener.onCallInviteParticipantsRequestDelivered(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionInviteParticipantsRequestDelivered :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionInviteParticipantsRequestFailed(ImsCallSession object, ImsReasonInfo object2) {
            ImsCall imsCall = ImsCall.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("callSessionInviteParticipantsRequestFailed :: reasonInfo=");
            ((StringBuilder)object3).append(object2);
            imsCall.loge(((StringBuilder)object3).toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionInviteParticipantsRequestFailed :: not supported for conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallInviteParticipantsRequestFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionInviteParticipantsRequestFailed :: ", throwable);
            }
        }

        public void callSessionMergeComplete(ImsCallSession imsCallSession) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionMergeComplete :: newSession =");
            stringBuilder.append((Object)imsCallSession);
            imsCall.logi(stringBuilder.toString());
            if (!ImsCall.this.isMergeHost()) {
                ImsCall.this.mMergeHost.processMergeComplete();
            } else {
                if (imsCallSession != null) {
                    imsCall = ImsCall.this;
                    if (this.doesCallSessionExistsInMerge(imsCallSession)) {
                        imsCallSession = null;
                    }
                    imsCall.mTransientConferenceSession = imsCallSession;
                }
                ImsCall.this.processMergeComplete();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionMergeFailed(ImsCallSession object, ImsReasonInfo imsReasonInfo) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionMergeFailed :: session=");
            stringBuilder.append(object);
            stringBuilder.append("reasonInfo=");
            stringBuilder.append((Object)imsReasonInfo);
            imsCall.loge(stringBuilder.toString());
            object = ImsCall.this;
            synchronized (object) {
                if (ImsCall.this.isMergeHost()) {
                    ImsCall.this.processMergeFailed(imsReasonInfo);
                } else if (ImsCall.this.mMergeHost != null) {
                    ImsCall.this.mMergeHost.processMergeFailed(imsReasonInfo);
                } else {
                    ImsCall.this.loge("callSessionMergeFailed :: No merge host for this conference!");
                }
                return;
            }
        }

        public void callSessionMergeStarted(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionMergeStarted :: session=");
            stringBuilder.append((Object)imsCallSession);
            stringBuilder.append(" newSession=");
            stringBuilder.append((Object)imsCallSession2);
            stringBuilder.append(", profile=");
            stringBuilder.append((Object)imsCallProfile);
            imsCall.logi(stringBuilder.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionMultipartyStateChanged(ImsCallSession object, boolean bl) {
            Object object2;
            if (VDBG) {
                object2 = ImsCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("callSessionMultipartyStateChanged isMultiParty: ");
                object = bl ? "Y" : "N";
                stringBuilder.append((String)object);
                ((ImsCall)object2).logi(stringBuilder.toString());
            }
            object = ImsCall.this;
            synchronized (object) {
                object2 = ImsCall.this.mListener;
            }
            if (object2 == null) return;
            try {
                ((Listener)object2).onMultipartyStateChanged(ImsCall.this, bl);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionMultipartyStateChanged :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionProgressing(ImsCallSession object, ImsStreamMediaProfile object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionProgressing :: session=");
            stringBuilder.append(object);
            stringBuilder.append(" profile=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionProgressing :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.access$300((ImsCall)ImsCall.this).mMediaProfile.copyFrom(object2);
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallProgressing(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionProgressing :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionRemoveParticipantsRequestDelivered(ImsCallSession object) {
            Listener listener;
            ImsCall.this.logi("callSessionRemoveParticipantsRequestDelivered ::");
            if (ImsCall.this.isTransientConferenceSession(object)) {
                ImsCall imsCall = ImsCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("callSessionRemoveParticipantsRequestDelivered :: not supported for conference session=");
                stringBuilder.append(object);
                imsCall.logi(stringBuilder.toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                listener = ImsCall.this.mListener;
            }
            if (listener == null) return;
            try {
                listener.onCallRemoveParticipantsRequestDelivered(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRemoveParticipantsRequestDelivered :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionRemoveParticipantsRequestFailed(ImsCallSession object, ImsReasonInfo object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionRemoveParticipantsRequestFailed :: reasonInfo=");
            stringBuilder.append(object2);
            ((ImsCall)object3).loge(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionRemoveParticipantsRequestFailed :: not supported for conference session=");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallRemoveParticipantsRequestFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRemoveParticipantsRequestFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionResumeFailed(ImsCallSession object, ImsReasonInfo object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionResumeFailed :: session=");
            stringBuilder.append(object);
            stringBuilder.append("reasonInfo=");
            stringBuilder.append(object2);
            ((ImsCall)object3).loge(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionResumeFailed :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this.mLockObj;
            synchronized (object) {
                ImsCall.this.mHold = true;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mUpdateRequest = 0;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallResumeFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionResumeFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionResumeReceived(ImsCallSession object, ImsCallProfile object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionResumeReceived :: session=");
            stringBuilder.append(object);
            stringBuilder.append("profile=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionResumeReceived :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallResumeReceived(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionResumeReceived :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionResumed(ImsCallSession object, ImsCallProfile object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionResumed :: session=");
            stringBuilder.append(object);
            stringBuilder.append("profile=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionResumed :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            ImsCall.this.setCallSessionMergePending(false);
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
                ImsCall.this.mUpdateRequest = 0;
                ImsCall.this.mHold = false;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallResumed(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionResumed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
            Listener listener;
            ImsCall imsCall = ImsCall.this;
            synchronized (imsCall) {
                listener = ImsCall.this.mListener;
            }
            if (listener == null) return;
            try {
                listener.onRttAudioIndicatorChanged(ImsCall.this, imsStreamMediaProfile);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRttAudioIndicatorChanged:: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionRttMessageReceived(String string) {
            Listener listener;
            ImsCall imsCall = ImsCall.this;
            synchronized (imsCall) {
                listener = ImsCall.this.mListener;
            }
            if (listener == null) return;
            try {
                listener.onRttMessageReceived(ImsCall.this, string);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRttMessageReceived:: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void callSessionRttModifyRequestReceived(ImsCallSession object, ImsCallProfile imsCallProfile) {
            ImsCall.this.logi("callSessionRttModifyRequestReceived");
            object = ImsCall.this;
            // MONITORENTER : object
            Listener listener = ImsCall.this.mListener;
            // MONITOREXIT : object
            if (!imsCallProfile.mMediaProfile.isRttCall()) {
                ImsCall.this.logi("callSessionRttModifyRequestReceived:: ignoring request, requested profile is not RTT.");
                return;
            }
            if (listener == null) return;
            try {
                listener.onRttModifyRequestReceived(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRttModifyRequestReceived:: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionRttModifyResponseReceived(int n) {
            ImsCall imsCall = ImsCall.this;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("callSessionRttModifyResponseReceived: ");
            ((StringBuilder)object).append(n);
            imsCall.logi(((StringBuilder)object).toString());
            imsCall = ImsCall.this;
            synchronized (imsCall) {
                object = ImsCall.this.mListener;
            }
            if (object == null) return;
            try {
                ((Listener)object).onRttModifyResponseReceived(ImsCall.this, n);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionRttModifyResponseReceived:: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionStartFailed(ImsCallSession object, ImsReasonInfo object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionStartFailed :: session=");
            stringBuilder.append(object);
            stringBuilder.append(" reasonInfo=");
            stringBuilder.append(object2);
            ((ImsCall)object3).loge(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionStartFailed :: not supported for transient conference session=");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mLastReasonInfo = (ImsReasonInfo)object2;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallStartFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionStarted :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionStarted(ImsCallSession object, ImsCallProfile object2) {
            ImsCall imsCall = ImsCall.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("callSessionStarted :: session=");
            ((StringBuilder)object3).append(object);
            ((StringBuilder)object3).append(" profile=");
            ((StringBuilder)object3).append(object2);
            imsCall.logi(((StringBuilder)object3).toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionStarted :: on transient session=");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            ImsCall.this.setCallSessionMergePending(false);
            if (ImsCall.this.isTransientConferenceSession(object)) {
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallStarted(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionStarted :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionSuppServiceReceived(ImsCallSession object, ImsSuppServiceNotification object2) {
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("callSessionSuppServiceReceived :: not supported for transient conference session=");
                stringBuilder.append(object);
                ((ImsCall)object2).logi(stringBuilder.toString());
                return;
            }
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionSuppServiceReceived :: session=");
            stringBuilder.append(object);
            stringBuilder.append(", suppServiceInfo");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallSuppServiceReceived(ImsCall.this, (ImsSuppServiceNotification)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionSuppServiceReceived :: ", throwable);
            }
        }

        public void callSessionTerminated(ImsCallSession object, ImsReasonInfo imsReasonInfo) {
            ImsCall imsCall = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionTerminated :: session=");
            stringBuilder.append(object);
            stringBuilder.append(" reasonInfo=");
            stringBuilder.append((Object)imsReasonInfo);
            imsCall.logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession((ImsCallSession)object)) {
                imsCall = ImsCall.this;
                stringBuilder = new StringBuilder();
                stringBuilder.append("callSessionTerminated :: on transient session=");
                stringBuilder.append(object);
                imsCall.logi(stringBuilder.toString());
                ImsCall.this.processMergeFailed(imsReasonInfo);
                return;
            }
            object = imsReasonInfo;
            if (ImsCall.this.mOverrideReason != 0) {
                imsCall = ImsCall.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("callSessionTerminated :: overrideReasonInfo=");
                ((StringBuilder)object).append(ImsCall.this.mOverrideReason);
                imsCall.logi(((StringBuilder)object).toString());
                object = new ImsReasonInfo(ImsCall.this.mOverrideReason, imsReasonInfo.getExtraCode(), imsReasonInfo.getExtraMessage());
            }
            ImsCall.this.processCallTerminated((ImsReasonInfo)object);
            ImsCall.this.setCallSessionMergePending(false);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionTtyModeReceived(ImsCallSession object, int n) {
            Object object2 = ImsCall.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("callSessionTtyModeReceived :: mode=");
            ((StringBuilder)object).append(n);
            ((ImsCall)object2).logi(((StringBuilder)object).toString());
            object = ImsCall.this;
            synchronized (object) {
                object2 = ImsCall.this.mListener;
            }
            if (object2 == null) return;
            try {
                ((Listener)object2).onCallSessionTtyModeReceived(ImsCall.this, n);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionTtyModeReceived :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionUpdateFailed(ImsCallSession object, ImsReasonInfo object2) {
            ImsCall imsCall = ImsCall.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("callSessionUpdateFailed :: session=");
            ((StringBuilder)object3).append(object);
            ((StringBuilder)object3).append(" reasonInfo=");
            ((StringBuilder)object3).append(object2);
            imsCall.loge(((StringBuilder)object3).toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionUpdateFailed :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mUpdateRequest = 0;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallUpdateFailed(ImsCall.this, (ImsReasonInfo)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionUpdateFailed :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionUpdateReceived(ImsCallSession object, ImsCallProfile object2) {
            ImsCall imsCall = ImsCall.this;
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("callSessionUpdateReceived :: session=");
            ((StringBuilder)object3).append(object);
            ((StringBuilder)object3).append(" profile=");
            ((StringBuilder)object3).append(object2);
            imsCall.logi(((StringBuilder)object3).toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionUpdateReceived :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.mProposedCallProfile = object2;
                ImsCall.this.mUpdateRequest = 6;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallUpdateReceived(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionUpdateReceived :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionUpdated(ImsCallSession object, ImsCallProfile object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionUpdated :: session=");
            stringBuilder.append(object);
            stringBuilder.append(" profile=");
            stringBuilder.append(object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object3 = ImsCall.this;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("callSessionUpdated :: not supported for transient conference session=");
                ((StringBuilder)object2).append(object);
                ((ImsCall)object3).logi(((StringBuilder)object2).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
                ImsCall.this.setCallProfile((ImsCallProfile)object2);
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallUpdated(ImsCall.this);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionUpdated :: ", throwable);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void callSessionUssdMessageReceived(ImsCallSession object, int n, String object2) {
            Object object3 = ImsCall.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("callSessionUssdMessageReceived :: mode=");
            stringBuilder.append(n);
            stringBuilder.append(", ussdMessage=");
            stringBuilder.append((String)object2);
            ((ImsCall)object3).logi(stringBuilder.toString());
            if (ImsCall.this.isTransientConferenceSession(object)) {
                object2 = ImsCall.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("callSessionUssdMessageReceived :: not supported for transient conference session=");
                ((StringBuilder)object3).append(object);
                ((ImsCall)object2).logi(((StringBuilder)object3).toString());
                return;
            }
            object = ImsCall.this;
            synchronized (object) {
                object3 = ImsCall.this.mListener;
            }
            if (object3 == null) return;
            try {
                ((Listener)object3).onCallUssdMessageReceived(ImsCall.this, n, (String)object2);
                return;
            }
            catch (Throwable throwable) {
                ImsCall.this.loge("callSessionUssdMessageReceived :: ", throwable);
            }
        }
    }

    public static class Listener {
        public void onCallConferenceExtendFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallConferenceExtendReceived(ImsCall imsCall, ImsCall imsCall2) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallConferenceExtended(ImsCall imsCall, ImsCall imsCall2) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallConferenceStateUpdated(ImsCall imsCall, ImsConferenceState imsConferenceState) {
        }

        public void onCallError(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
        }

        public void onCallHandover(ImsCall imsCall, int n, int n2, ImsReasonInfo imsReasonInfo) {
        }

        public void onCallHandoverFailed(ImsCall imsCall, int n, int n2, ImsReasonInfo imsReasonInfo) {
        }

        public void onCallHeld(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallHoldFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallHoldReceived(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallInviteParticipantsRequestDelivered(ImsCall imsCall) {
        }

        public void onCallInviteParticipantsRequestFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
        }

        public void onCallMergeFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallMerged(ImsCall imsCall, ImsCall imsCall2, boolean bl) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallProgressing(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallQualityChanged(ImsCall imsCall, CallQuality callQuality) {
        }

        public void onCallRemoveParticipantsRequestDelivered(ImsCall imsCall) {
        }

        public void onCallRemoveParticipantsRequestFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
        }

        public void onCallResumeFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallResumeReceived(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallResumed(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallSessionTtyModeReceived(ImsCall imsCall, int n) {
        }

        public void onCallStartFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallStarted(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallStateChanged(ImsCall imsCall) {
        }

        public void onCallStateChanged(ImsCall imsCall, int n) {
        }

        public void onCallSuppServiceReceived(ImsCall imsCall, ImsSuppServiceNotification imsSuppServiceNotification) {
        }

        public void onCallTerminated(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallUpdateFailed(ImsCall imsCall, ImsReasonInfo imsReasonInfo) {
            this.onCallError(imsCall, imsReasonInfo);
        }

        public void onCallUpdateReceived(ImsCall imsCall) {
        }

        public void onCallUpdated(ImsCall imsCall) {
            this.onCallStateChanged(imsCall);
        }

        public void onCallUssdMessageReceived(ImsCall imsCall, int n, String string) {
        }

        public void onConferenceParticipantsStateChanged(ImsCall imsCall, List<ConferenceParticipant> list) {
        }

        public void onMultipartyStateChanged(ImsCall imsCall, boolean bl) {
        }

        public void onRttAudioIndicatorChanged(ImsCall imsCall, ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void onRttMessageReceived(ImsCall imsCall, String string) {
        }

        public void onRttModifyRequestReceived(ImsCall imsCall) {
        }

        public void onRttModifyResponseReceived(ImsCall imsCall, int n) {
        }
    }

}

