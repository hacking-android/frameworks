/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioManager
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.net.wifi.WifiManager$WifiLock
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.text.TextUtils
 */
package android.net.sip;

import android.content.Context;
import android.media.AudioManager;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.AudioStream;
import android.net.sip.SimpleSessionDescription;
import android.net.sip.SipErrorCode;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipSession;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.telephony.Rlog;
import android.text.TextUtils;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SipAudioCall {
    private static final boolean DBG = true;
    private static final boolean DONT_RELEASE_SOCKET = false;
    private static final String LOG_TAG = SipAudioCall.class.getSimpleName();
    private static final boolean RELEASE_SOCKET = true;
    private static final int SESSION_TIMEOUT = 5;
    private static final int TRANSFER_TIMEOUT = 15;
    private AudioGroup mAudioGroup;
    private AudioStream mAudioStream;
    private Context mContext;
    private int mErrorCode = 0;
    private String mErrorMessage;
    private boolean mHold = false;
    private boolean mInCall = false;
    private Listener mListener;
    private SipProfile mLocalProfile;
    private boolean mMuted = false;
    private String mPeerSd;
    private long mSessionId = System.currentTimeMillis();
    private SipSession mSipSession;
    private SipSession mTransferringSession;
    private WifiManager.WifiLock mWifiHighPerfLock;
    private WifiManager mWm;

    public SipAudioCall(Context context, SipProfile sipProfile) {
        this.mContext = context;
        this.mLocalProfile = sipProfile;
        this.mWm = (WifiManager)context.getSystemService("wifi");
    }

    static /* synthetic */ SimpleSessionDescription access$1400(SipAudioCall sipAudioCall) {
        return sipAudioCall.createOffer();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void close(boolean bl) {
        synchronized (this) {
            if (bl) {
                this.stopCall(true);
            }
            this.mInCall = false;
            this.mHold = false;
            this.mSessionId = System.currentTimeMillis();
            this.mErrorCode = 0;
            this.mErrorMessage = null;
            if (this.mSipSession != null) {
                this.mSipSession.setListener(null);
                this.mSipSession = null;
            }
            return;
        }
    }

    private SimpleSessionDescription createAnswer(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return this.createOffer();
        }
        SimpleSessionDescription simpleSessionDescription = new SimpleSessionDescription((String)object);
        SimpleSessionDescription simpleSessionDescription2 = new SimpleSessionDescription(this.mSessionId, this.getLocalIp());
        SimpleSessionDescription.Media[] arrmedia = simpleSessionDescription.getMedia();
        int n = arrmedia.length;
        object = null;
        block0 : for (int i = 0; i < n; ++i) {
            SimpleSessionDescription.Media media;
            int n2;
            int n3;
            String[] arrstring = arrmedia[i];
            Object object2 = object;
            if (object == null) {
                object2 = object;
                if (arrstring.getPort() > 0) {
                    object2 = object;
                    if ("audio".equals(arrstring.getType())) {
                        object2 = object;
                        if ("RTP/AVP".equals(arrstring.getProtocol())) {
                            int n42;
                            object2 = arrstring.getRtpPayloadTypes();
                            n3 = ((int[])object2).length;
                            for (n2 = 0; n2 < n3 && (object = AudioCodec.getCodec(n42 = object2[n2], arrstring.getRtpmap(n42), arrstring.getFmtp(n42))) == null; ++n2) {
                            }
                            object2 = object;
                            if (object != null) {
                                media = simpleSessionDescription2.newMedia("audio", this.mAudioStream.getLocalPort(), 1, "RTP/AVP");
                                media.setRtpPayload(((AudioCodec)object).type, ((AudioCodec)object).rtpmap, ((AudioCodec)object).fmtp);
                                for (int n42 : arrstring.getRtpPayloadTypes()) {
                                    object2 = arrstring.getRtpmap(n42);
                                    if (n42 == ((AudioCodec)object).type || object2 == null || !((String)object2).startsWith("telephone-event")) continue;
                                    media.setRtpPayload(n42, (String)object2, arrstring.getFmtp(n42));
                                }
                                if (arrstring.getAttribute("recvonly") != null) {
                                    simpleSessionDescription2.setAttribute("sendonly", "");
                                    continue;
                                }
                                if (arrstring.getAttribute("sendonly") != null) {
                                    simpleSessionDescription2.setAttribute("recvonly", "");
                                    continue;
                                }
                                if (simpleSessionDescription.getAttribute("recvonly") != null) {
                                    simpleSessionDescription2.setAttribute("sendonly", "");
                                    continue;
                                }
                                if (simpleSessionDescription.getAttribute("sendonly") == null) continue;
                                simpleSessionDescription2.setAttribute("recvonly", "");
                                continue;
                            }
                        }
                    }
                }
            }
            media = simpleSessionDescription2.newMedia(arrstring.getType(), 0, 1, arrstring.getProtocol());
            arrstring = arrstring.getFormats();
            n3 = arrstring.length;
            n2 = 0;
            do {
                object = object2;
                if (n2 >= n3) continue block0;
                media.setFormat(arrstring[n2], null);
                ++n2;
            } while (true);
        }
        if (object != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createAnswer: answer=");
            ((StringBuilder)object).append(simpleSessionDescription2);
            this.log(((StringBuilder)object).toString());
            return simpleSessionDescription2;
        }
        this.loge("createAnswer: no suitable codes");
        throw new IllegalStateException("Reject SDP: no suitable codecs");
    }

    private SimpleSessionDescription createContinueOffer() {
        this.log("createContinueOffer");
        SimpleSessionDescription simpleSessionDescription = new SimpleSessionDescription(this.mSessionId, this.getLocalIp());
        SimpleSessionDescription.Media media = simpleSessionDescription.newMedia("audio", this.mAudioStream.getLocalPort(), 1, "RTP/AVP");
        AudioCodec audioCodec = this.mAudioStream.getCodec();
        media.setRtpPayload(audioCodec.type, audioCodec.rtpmap, audioCodec.fmtp);
        int n = this.mAudioStream.getDtmfType();
        if (n != -1) {
            media.setRtpPayload(n, "telephone-event/8000", "0-15");
        }
        return simpleSessionDescription;
    }

    private SimpleSessionDescription createHoldOffer() {
        SimpleSessionDescription simpleSessionDescription = this.createContinueOffer();
        simpleSessionDescription.setAttribute("sendonly", "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("createHoldOffer: offer=");
        stringBuilder.append(simpleSessionDescription);
        this.log(stringBuilder.toString());
        return simpleSessionDescription;
    }

    private SipSession.Listener createListener() {
        return new SipSession.Listener(){

            @Override
            public void onCallBusy(SipSession object) {
                SipAudioCall sipAudioCall = SipAudioCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCallBusy: ");
                stringBuilder.append(object);
                sipAudioCall.log(stringBuilder.toString());
                object = SipAudioCall.this.mListener;
                if (object != null) {
                    try {
                        ((Listener)object).onCallBusy(SipAudioCall.this);
                    }
                    catch (Throwable throwable) {
                        SipAudioCall.this.loge("onCallBusy(): ", throwable);
                    }
                }
                SipAudioCall.this.close(false);
            }

            @Override
            public void onCallChangeFailed(SipSession object, int n, String string) {
                object = SipAudioCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCallChangedFailed: ");
                stringBuilder.append(string);
                ((SipAudioCall)object).log(stringBuilder.toString());
                SipAudioCall.this.mErrorCode = n;
                SipAudioCall.this.mErrorMessage = string;
                object = SipAudioCall.this.mListener;
                if (object != null) {
                    try {
                        ((Listener)object).onError(SipAudioCall.this, SipAudioCall.this.mErrorCode, string);
                    }
                    catch (Throwable throwable) {
                        SipAudioCall.this.loge("onCallBusy():", throwable);
                    }
                }
            }

            @Override
            public void onCallEnded(SipSession object) {
                SipAudioCall sipAudioCall = SipAudioCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCallEnded: ");
                stringBuilder.append(object);
                stringBuilder.append(" mSipSession:");
                stringBuilder.append(SipAudioCall.this.mSipSession);
                sipAudioCall.log(stringBuilder.toString());
                if (object == SipAudioCall.this.mTransferringSession) {
                    SipAudioCall.this.mTransferringSession = null;
                    return;
                }
                if (SipAudioCall.this.mTransferringSession == null && object == SipAudioCall.this.mSipSession) {
                    object = SipAudioCall.this.mListener;
                    if (object != null) {
                        try {
                            ((Listener)object).onCallEnded(SipAudioCall.this);
                        }
                        catch (Throwable throwable) {
                            SipAudioCall.this.loge("onCallEnded(): ", throwable);
                        }
                    }
                    SipAudioCall.this.close();
                    return;
                }
            }

            @Override
            public void onCallEstablished(SipSession object, String charSequence) {
                SipAudioCall.this.mPeerSd = (String)charSequence;
                SipAudioCall sipAudioCall = SipAudioCall.this;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("onCallEstablished(): ");
                ((StringBuilder)charSequence).append(SipAudioCall.this.mPeerSd);
                sipAudioCall.log(((StringBuilder)charSequence).toString());
                if (SipAudioCall.this.mTransferringSession != null && object == SipAudioCall.this.mTransferringSession) {
                    SipAudioCall.this.transferToNewSession();
                    return;
                }
                object = SipAudioCall.this.mListener;
                if (object != null) {
                    try {
                        if (SipAudioCall.this.mHold) {
                            ((Listener)object).onCallHeld(SipAudioCall.this);
                        } else {
                            ((Listener)object).onCallEstablished(SipAudioCall.this);
                        }
                    }
                    catch (Throwable throwable) {
                        SipAudioCall.this.loge("onCallEstablished(): ", throwable);
                    }
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onCallTransferring(SipSession var1_1, String var2_2) {
                var3_4 = SipAudioCall.this;
                var4_5 = new StringBuilder();
                var4_5.append("onCallTransferring: mSipSession=");
                var4_5.append(SipAudioCall.access$300(SipAudioCall.this));
                var4_5.append(" newSession=");
                var4_5.append(var1_1);
                SipAudioCall.access$000(var3_4, var4_5.toString());
                SipAudioCall.access$702(SipAudioCall.this, var1_1);
                if (var2_2 != null) ** GOTO lbl18
                try {
                    var1_1.makeCall(var1_1.getPeerProfile(), SipAudioCall.access$1400(SipAudioCall.this).encode(), 15);
                    return;
lbl18: // 1 sources:
                    var1_1.answerCall(SipAudioCall.access$500(SipAudioCall.this, var2_2).encode(), 5);
                    return;
                }
                catch (Throwable var2_3) {
                    SipAudioCall.access$200(SipAudioCall.this, "onCallTransferring()", var2_3);
                    var1_1.endCall();
                }
            }

            @Override
            public void onCalling(SipSession object) {
                SipAudioCall sipAudioCall = SipAudioCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onCalling: session=");
                stringBuilder.append(object);
                sipAudioCall.log(stringBuilder.toString());
                object = SipAudioCall.this.mListener;
                if (object != null) {
                    try {
                        ((Listener)object).onCalling(SipAudioCall.this);
                    }
                    catch (Throwable throwable) {
                        SipAudioCall.this.loge("onCalling():", throwable);
                    }
                }
            }

            @Override
            public void onError(SipSession sipSession, int n, String string) {
                SipAudioCall.this.onError(n, string);
            }

            @Override
            public void onRegistering(SipSession sipSession) {
            }

            @Override
            public void onRegistrationDone(SipSession sipSession, int n) {
            }

            @Override
            public void onRegistrationFailed(SipSession sipSession, int n, String string) {
            }

            @Override
            public void onRegistrationTimeout(SipSession sipSession) {
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRinging(SipSession sipSession, SipProfile object, String string) {
                object = SipAudioCall.this;
                synchronized (object) {
                    boolean bl;
                    if (SipAudioCall.this.mSipSession != null && SipAudioCall.this.mInCall && (bl = sipSession.getCallId().equals(SipAudioCall.this.mSipSession.getCallId()))) {
                        try {
                            string = SipAudioCall.this.createAnswer(string).encode();
                            SipAudioCall.this.mSipSession.answerCall(string, 5);
                        }
                        catch (Throwable throwable) {
                            SipAudioCall.this.loge("onRinging():", throwable);
                            sipSession.endCall();
                        }
                        return;
                    }
                    sipSession.endCall();
                    return;
                }
            }

            @Override
            public void onRingingBack(SipSession object) {
                SipAudioCall sipAudioCall = SipAudioCall.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onRingingBackk: ");
                stringBuilder.append(object);
                sipAudioCall.log(stringBuilder.toString());
                object = SipAudioCall.this.mListener;
                if (object != null) {
                    try {
                        ((Listener)object).onRingingBack(SipAudioCall.this);
                    }
                    catch (Throwable throwable) {
                        SipAudioCall.this.loge("onRingingBack():", throwable);
                    }
                }
            }
        };
    }

    private SimpleSessionDescription createOffer() {
        SimpleSessionDescription simpleSessionDescription = new SimpleSessionDescription(this.mSessionId, this.getLocalIp());
        AudioCodec.getCodecs();
        SimpleSessionDescription.Media media = simpleSessionDescription.newMedia("audio", this.mAudioStream.getLocalPort(), 1, "RTP/AVP");
        for (AudioCodec object2 : AudioCodec.getCodecs()) {
            media.setRtpPayload(object2.type, object2.rtpmap, object2.fmtp);
        }
        media.setRtpPayload(127, "telephone-event/8000", "0-15");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("createOffer: offer=");
        stringBuilder.append(simpleSessionDescription);
        this.log(stringBuilder.toString());
        return simpleSessionDescription;
    }

    private String getLocalIp() {
        return this.mSipSession.getLocalIp();
    }

    private void grabWifiHighPerfLock() {
        if (this.mWifiHighPerfLock == null) {
            this.log("grabWifiHighPerfLock:");
            this.mWifiHighPerfLock = ((WifiManager)this.mContext.getSystemService("wifi")).createWifiLock(3, LOG_TAG);
            this.mWifiHighPerfLock.acquire();
        }
    }

    private boolean isSpeakerOn() {
        return ((AudioManager)this.mContext.getSystemService("audio")).isSpeakerphoneOn();
    }

    private boolean isWifiOn() {
        boolean bl = this.mWm.getConnectionInfo().getBSSID() != null;
        return bl;
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)LOG_TAG, (String)string, (Throwable)throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onError(int n, String string) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("onError: ");
        ((StringBuilder)object).append(SipErrorCode.toString(n));
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(string);
        this.log(((StringBuilder)object).toString());
        this.mErrorCode = n;
        this.mErrorMessage = string;
        object = this.mListener;
        if (object != null) {
            try {
                ((Listener)object).onError(this, n, string);
            }
            catch (Throwable throwable) {
                this.loge("onError():", throwable);
            }
        }
        synchronized (this) {
            if (n == -10 || !this.isInCall()) {
                this.close(true);
            }
            return;
        }
    }

    private void releaseWifiHighPerfLock() {
        if (this.mWifiHighPerfLock != null) {
            this.log("releaseWifiHighPerfLock:");
            this.mWifiHighPerfLock.release();
            this.mWifiHighPerfLock = null;
        }
    }

    private void setAudioGroupMode() {
        AudioGroup audioGroup = this.getAudioGroup();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setAudioGroupMode: audioGroup=");
        stringBuilder.append(audioGroup);
        this.log(stringBuilder.toString());
        if (audioGroup != null) {
            if (this.mHold) {
                audioGroup.setMode(0);
            } else if (this.mMuted) {
                audioGroup.setMode(1);
            } else if (this.isSpeakerOn()) {
                audioGroup.setMode(3);
            } else {
                audioGroup.setMode(2);
            }
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void startAudioInternal() throws UnknownHostException {
        synchronized (this) {
            void var7_25;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("startAudioInternal: mPeerSd=");
            ((StringBuilder)object).append(this.mPeerSd);
            this.loge(((StringBuilder)object).toString());
            if (this.mPeerSd == null) {
                object = new IllegalStateException("mPeerSd = null");
                throw object;
            }
            this.stopCall(false);
            this.mInCall = true;
            SimpleSessionDescription simpleSessionDescription = new SimpleSessionDescription(this.mPeerSd);
            AudioStream audioStream = this.mAudioStream;
            Object object2 = simpleSessionDescription.getMedia();
            int n5 = ((SimpleSessionDescription.Media[])object2).length;
            object = null;
            int n2 = 0;
            do {
                void var7_7;
                Serializable serializable = object;
                if (n2 >= n5) break;
                SimpleSessionDescription.Media media = object2[n2];
                Serializable serializable2 = object;
                if (object == null) {
                    Serializable serializable3 = object;
                    if (media.getPort() > 0) {
                        Object object3 = object;
                        if ("audio".equals(media.getType())) {
                            Object object4 = object;
                            if ("RTP/AVP".equals(media.getProtocol())) {
                                int n3;
                                int[] arrn = media.getRtpPayloadTypes();
                                int n4 = arrn.length;
                                for (int i = 0; i < n4 && (object = AudioCodec.getCodec(n3 = arrn[i], media.getRtpmap(n3), media.getFmtp(n3))) == null; ++i) {
                                }
                                Object object5 = object;
                                if (object != null) {
                                    void var7_17;
                                    object2 = media.getAddress();
                                    Object object6 = object2;
                                    if (object2 == null) {
                                        String string = simpleSessionDescription.getAddress();
                                    }
                                    audioStream.associate(InetAddress.getByName((String)var7_17), media.getPort());
                                    audioStream.setDtmfType(-1);
                                    audioStream.setCodec((AudioCodec)object);
                                    for (int n5 : media.getRtpPayloadTypes()) {
                                        object2 = media.getRtpmap(n5);
                                        if (n5 == ((AudioCodec)object).type || object2 == null || !((String)object2).startsWith("telephone-event")) continue;
                                        audioStream.setDtmfType(n5);
                                    }
                                    if (this.mHold) {
                                        audioStream.setMode(0);
                                        Object object7 = object;
                                        break;
                                    }
                                    if (media.getAttribute("recvonly") != null) {
                                        audioStream.setMode(1);
                                        Object object8 = object;
                                        break;
                                    }
                                    if (media.getAttribute("sendonly") != null) {
                                        audioStream.setMode(2);
                                        Object object9 = object;
                                        break;
                                    }
                                    if (simpleSessionDescription.getAttribute("recvonly") != null) {
                                        audioStream.setMode(1);
                                        Object object10 = object;
                                        break;
                                    }
                                    if (simpleSessionDescription.getAttribute("sendonly") != null) {
                                        audioStream.setMode(2);
                                        Object object11 = object;
                                        break;
                                    }
                                    audioStream.setMode(0);
                                    Object object12 = object;
                                    break;
                                }
                            }
                        }
                    }
                }
                ++n2;
                object = var7_7;
            } while (true);
            if (var7_25 == null) {
                object = new IllegalStateException("Reject SDP: no suitable codecs");
                throw object;
            }
            if (this.isWifiOn()) {
                this.grabWifiHighPerfLock();
            }
            AudioGroup audioGroup = this.getAudioGroup();
            if (!this.mHold) {
                object = audioGroup;
                if (audioGroup == null) {
                    object = new AudioGroup();
                }
                audioStream.join((AudioGroup)object);
            }
            this.setAudioGroupMode();
            return;
        }
    }

    private void stopCall(boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("stopCall: releaseSocket=");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        this.releaseWifiHighPerfLock();
        object = this.mAudioStream;
        if (object != null) {
            ((AudioStream)object).join(null);
            if (bl) {
                this.mAudioStream.release();
                this.mAudioStream = null;
            }
        }
    }

    private void throwSipException(Throwable throwable) throws SipException {
        if (throwable instanceof SipException) {
            throw (SipException)throwable;
        }
        throw new SipException("", throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void transferToNewSession() {
        synchronized (this) {
            SipSession sipSession = this.mTransferringSession;
            if (sipSession == null) {
                return;
            }
            sipSession = this.mSipSession;
            this.mSipSession = this.mTransferringSession;
            this.mTransferringSession = null;
            if (this.mAudioStream != null) {
                this.mAudioStream.join(null);
            } else {
                try {
                    AudioStream audioStream;
                    this.mAudioStream = audioStream = new AudioStream(InetAddress.getByName(this.getLocalIp()));
                }
                catch (Throwable throwable) {
                    this.loge("transferToNewSession():", throwable);
                }
            }
            if (sipSession != null) {
                sipSession.endCall();
            }
            this.startAudio();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void answerCall(int n) throws SipException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("answerCall: mSipSession");
        ((StringBuilder)object).append(this.mSipSession);
        ((StringBuilder)object).append(" timeout=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        synchronized (this) {
            object = this.mSipSession;
            if (object == null) {
                object = new SipException("No call to answer");
                throw object;
            }
            try {
                this.mAudioStream = object = new AudioStream(InetAddress.getByName(this.getLocalIp()));
                this.mSipSession.answerCall(this.createAnswer(this.mPeerSd).encode(), n);
                return;
            }
            catch (IOException iOException) {
                this.loge("answerCall:", iOException);
                SipException sipException = new SipException("answerCall()", iOException);
                throw sipException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachCall(SipSession sipSession, String charSequence) throws SipException {
        if (!SipManager.isVoipSupported(this.mContext)) {
            throw new SipException("VOIP API is not supported");
        }
        synchronized (this) {
            this.mSipSession = sipSession;
            this.mPeerSd = charSequence;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("attachCall(): ");
            ((StringBuilder)charSequence).append(this.mPeerSd);
            this.log(((StringBuilder)charSequence).toString());
            try {
                sipSession.setListener(this.createListener());
            }
            catch (Throwable throwable) {
                this.loge("attachCall()", throwable);
                this.throwSipException(throwable);
            }
            return;
        }
    }

    public void close() {
        this.close(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void continueCall(int n) throws SipException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("continueCall: mSipSession");
        stringBuilder.append(this.mSipSession);
        stringBuilder.append(" timeout=");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        synchronized (this) {
            if (!this.mHold) {
                return;
            }
            this.mSipSession.changeCall(this.createContinueOffer().encode(), n);
            this.mHold = false;
            this.setAudioGroupMode();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void endCall() throws SipException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("endCall: mSipSession");
        stringBuilder.append(this.mSipSession);
        this.log(stringBuilder.toString());
        synchronized (this) {
            this.stopCall(true);
            this.mInCall = false;
            if (this.mSipSession != null) {
                this.mSipSession.endCall();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AudioGroup getAudioGroup() {
        synchronized (this) {
            if (this.mAudioGroup != null) {
                return this.mAudioGroup;
            }
            if (this.mAudioStream != null) return this.mAudioStream.getGroup();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AudioStream getAudioStream() {
        synchronized (this) {
            return this.mAudioStream;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SipProfile getLocalProfile() {
        synchronized (this) {
            return this.mLocalProfile;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SipProfile getPeerProfile() {
        synchronized (this) {
            if (this.mSipSession != null) return this.mSipSession.getPeerProfile();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SipSession getSipSession() {
        synchronized (this) {
            return this.mSipSession;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getState() {
        synchronized (this) {
            if (this.mSipSession != null) return this.mSipSession.getState();
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void holdCall(int n) throws SipException {
        Serializable serializable = new StringBuilder();
        serializable.append("holdCall: mSipSession");
        serializable.append(this.mSipSession);
        serializable.append(" timeout=");
        serializable.append(n);
        this.log(serializable.toString());
        synchronized (this) {
            if (this.mHold) {
                return;
            }
            if (this.mSipSession != null) {
                this.mSipSession.changeCall(this.createHoldOffer().encode(), n);
                this.mHold = true;
                this.setAudioGroupMode();
                return;
            }
            this.loge("holdCall:");
            serializable = new SipException("Not in a call to hold call");
            throw serializable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isInCall() {
        synchronized (this) {
            return this.mInCall;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isMuted() {
        synchronized (this) {
            return this.mMuted;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isOnHold() {
        synchronized (this) {
            return this.mHold;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void makeCall(SipProfile serializable, SipSession sipSession, int n) throws SipException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("makeCall: ");
        ((StringBuilder)object).append(serializable);
        ((StringBuilder)object).append(" session=");
        ((StringBuilder)object).append(sipSession);
        ((StringBuilder)object).append(" timeout=");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        if (!SipManager.isVoipSupported(this.mContext)) {
            throw new SipException("VOIP API is not supported");
        }
        synchronized (this) {
            this.mSipSession = sipSession;
            try {
                this.mAudioStream = object = new AudioStream(InetAddress.getByName(this.getLocalIp()));
                sipSession.setListener(this.createListener());
                sipSession.makeCall((SipProfile)serializable, this.createOffer().encode(), n);
                return;
            }
            catch (IOException iOException) {
                this.loge("makeCall:", iOException);
                serializable = new SipException("makeCall()", iOException);
                throw serializable;
            }
        }
    }

    public void sendDtmf(int n) {
        this.sendDtmf(n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendDtmf(int n, Message message) {
        synchronized (this) {
            AudioGroup audioGroup = this.getAudioGroup();
            if (audioGroup != null && this.mSipSession != null && 8 == this.getState()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sendDtmf: code=");
                stringBuilder.append(n);
                stringBuilder.append(" result=");
                stringBuilder.append((Object)message);
                this.log(stringBuilder.toString());
                audioGroup.sendDtmf(n);
            }
            if (message != null) {
                message.sendToTarget();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAudioGroup(AudioGroup audioGroup) {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAudioGroup: group=");
            stringBuilder.append(audioGroup);
            this.log(stringBuilder.toString());
            if (this.mAudioStream != null && this.mAudioStream.getGroup() != null) {
                this.mAudioStream.join(audioGroup);
            }
            this.mAudioGroup = audioGroup;
            return;
        }
    }

    public void setListener(Listener listener) {
        this.setListener(listener, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setListener(Listener listener, boolean bl) {
        this.mListener = listener;
        if (listener == null) return;
        if (!bl) {
            return;
        }
        try {
            if (this.mErrorCode != 0) {
                listener.onError(this, this.mErrorCode, this.mErrorMessage);
                return;
            }
            if (this.mInCall) {
                if (this.mHold) {
                    listener.onCallHeld(this);
                    return;
                }
                listener.onCallEstablished(this);
                return;
            }
            int n = this.getState();
            if (n == 0) {
                listener.onReadyToCall(this);
                return;
            }
            if (n == 3) {
                listener.onRinging(this, this.getPeerProfile());
                return;
            }
            if (n == 5) {
                listener.onCalling(this);
                return;
            }
            if (n != 6) {
                return;
            }
            listener.onRingingBack(this);
            return;
        }
        catch (Throwable throwable) {
            this.loge("setListener()", throwable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSpeakerMode(boolean bl) {
        synchronized (this) {
            ((AudioManager)this.mContext.getSystemService("audio")).setSpeakerphoneOn(bl);
            this.setAudioGroupMode();
            return;
        }
    }

    public void startAudio() {
        try {
            this.startAudioInternal();
        }
        catch (Throwable throwable) {
            this.onError(-4, throwable.getMessage());
        }
        catch (UnknownHostException unknownHostException) {
            this.onError(-7, unknownHostException.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void toggleMute() {
        synchronized (this) {
            boolean bl = !this.mMuted;
            this.mMuted = bl;
            this.setAudioGroupMode();
            return;
        }
    }

    public static class Listener {
        public void onCallBusy(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onCallEnded(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onCallEstablished(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onCallHeld(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onCalling(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onChanged(SipAudioCall sipAudioCall) {
        }

        public void onError(SipAudioCall sipAudioCall, int n, String string) {
        }

        public void onReadyToCall(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }

        public void onRinging(SipAudioCall sipAudioCall, SipProfile sipProfile) {
            this.onChanged(sipAudioCall);
        }

        public void onRingingBack(SipAudioCall sipAudioCall) {
            this.onChanged(sipAudioCall);
        }
    }

}

