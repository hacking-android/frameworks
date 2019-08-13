/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSessionListener;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsVideoCallProvider;
import android.telephony.ims.aidl.IImsCallSessionListener;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsVideoCallProvider;

@SystemApi
public class ImsCallSessionImplBase
implements AutoCloseable {
    public static final int USSD_MODE_NOTIFY = 0;
    public static final int USSD_MODE_REQUEST = 1;
    private IImsCallSession mServiceImpl = new IImsCallSession.Stub(){

        @Override
        public void accept(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.accept(n, imsStreamMediaProfile);
        }

        @Override
        public void close() {
            ImsCallSessionImplBase.this.close();
        }

        @Override
        public void deflect(String string2) {
            ImsCallSessionImplBase.this.deflect(string2);
        }

        @Override
        public void extendToConference(String[] arrstring) {
            ImsCallSessionImplBase.this.extendToConference(arrstring);
        }

        @Override
        public String getCallId() {
            return ImsCallSessionImplBase.this.getCallId();
        }

        @Override
        public ImsCallProfile getCallProfile() {
            return ImsCallSessionImplBase.this.getCallProfile();
        }

        @Override
        public ImsCallProfile getLocalCallProfile() {
            return ImsCallSessionImplBase.this.getLocalCallProfile();
        }

        @Override
        public String getProperty(String string2) {
            return ImsCallSessionImplBase.this.getProperty(string2);
        }

        @Override
        public ImsCallProfile getRemoteCallProfile() {
            return ImsCallSessionImplBase.this.getRemoteCallProfile();
        }

        @Override
        public int getState() {
            return ImsCallSessionImplBase.this.getState();
        }

        @Override
        public IImsVideoCallProvider getVideoCallProvider() {
            return ImsCallSessionImplBase.this.getVideoCallProvider();
        }

        @Override
        public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.hold(imsStreamMediaProfile);
        }

        @Override
        public void inviteParticipants(String[] arrstring) {
            ImsCallSessionImplBase.this.inviteParticipants(arrstring);
        }

        @Override
        public boolean isInCall() {
            return ImsCallSessionImplBase.this.isInCall();
        }

        @Override
        public boolean isMultiparty() {
            return ImsCallSessionImplBase.this.isMultiparty();
        }

        @Override
        public void merge() {
            ImsCallSessionImplBase.this.merge();
        }

        @Override
        public void reject(int n) {
            ImsCallSessionImplBase.this.reject(n);
        }

        @Override
        public void removeParticipants(String[] arrstring) {
            ImsCallSessionImplBase.this.removeParticipants(arrstring);
        }

        @Override
        public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.resume(imsStreamMediaProfile);
        }

        @Override
        public void sendDtmf(char c, Message message) {
            ImsCallSessionImplBase.this.sendDtmf(c, message);
        }

        @Override
        public void sendRttMessage(String string2) {
            ImsCallSessionImplBase.this.sendRttMessage(string2);
        }

        @Override
        public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
            ImsCallSessionImplBase.this.sendRttModifyRequest(imsCallProfile);
        }

        @Override
        public void sendRttModifyResponse(boolean bl) {
            ImsCallSessionImplBase.this.sendRttModifyResponse(bl);
        }

        @Override
        public void sendUssd(String string2) {
            ImsCallSessionImplBase.this.sendUssd(string2);
        }

        @Override
        public void setListener(IImsCallSessionListener iImsCallSessionListener) {
            ImsCallSessionImplBase.this.setListener(new ImsCallSessionListener(iImsCallSessionListener));
        }

        @Override
        public void setMute(boolean bl) {
            ImsCallSessionImplBase.this.setMute(bl);
        }

        @Override
        public void start(String string2, ImsCallProfile imsCallProfile) {
            ImsCallSessionImplBase.this.start(string2, imsCallProfile);
        }

        @Override
        public void startConference(String[] arrstring, ImsCallProfile imsCallProfile) throws RemoteException {
            ImsCallSessionImplBase.this.startConference(arrstring, imsCallProfile);
        }

        @Override
        public void startDtmf(char c) {
            ImsCallSessionImplBase.this.startDtmf(c);
        }

        @Override
        public void stopDtmf() {
            ImsCallSessionImplBase.this.stopDtmf();
        }

        @Override
        public void terminate(int n) {
            ImsCallSessionImplBase.this.terminate(n);
        }

        @Override
        public void update(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.update(n, imsStreamMediaProfile);
        }
    };

    public void accept(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override
    public void close() {
    }

    public void deflect(String string2) {
    }

    public void extendToConference(String[] arrstring) {
    }

    public String getCallId() {
        return null;
    }

    public ImsCallProfile getCallProfile() {
        return null;
    }

    public ImsVideoCallProvider getImsVideoCallProvider() {
        return null;
    }

    public ImsCallProfile getLocalCallProfile() {
        return null;
    }

    public String getProperty(String string2) {
        return null;
    }

    public ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    public IImsCallSession getServiceImpl() {
        return this.mServiceImpl;
    }

    public int getState() {
        return -1;
    }

    public IImsVideoCallProvider getVideoCallProvider() {
        Object object = this.getImsVideoCallProvider();
        object = object != null ? ((ImsVideoCallProvider)object).getInterface() : null;
        return object;
    }

    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void inviteParticipants(String[] arrstring) {
    }

    public boolean isInCall() {
        return false;
    }

    public boolean isMultiparty() {
        return false;
    }

    public void merge() {
    }

    public void reject(int n) {
    }

    public void removeParticipants(String[] arrstring) {
    }

    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void sendDtmf(char c, Message message) {
    }

    public void sendRttMessage(String string2) {
    }

    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
    }

    public void sendRttModifyResponse(boolean bl) {
    }

    public void sendUssd(String string2) {
    }

    public void setListener(ImsCallSessionListener imsCallSessionListener) {
    }

    public final void setListener(IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
        this.setListener(new ImsCallSessionListener(iImsCallSessionListener));
    }

    public void setMute(boolean bl) {
    }

    public void setServiceImpl(IImsCallSession iImsCallSession) {
        this.mServiceImpl = iImsCallSession;
    }

    public void start(String string2, ImsCallProfile imsCallProfile) {
    }

    public void startConference(String[] arrstring, ImsCallProfile imsCallProfile) {
    }

    public void startDtmf(char c) {
    }

    public void stopDtmf() {
    }

    public void terminate(int n) {
    }

    public void update(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public static class State {
        public static final int ESTABLISHED = 4;
        public static final int ESTABLISHING = 3;
        public static final int IDLE = 0;
        public static final int INITIATED = 1;
        public static final int INVALID = -1;
        public static final int NEGOTIATING = 2;
        public static final int REESTABLISHING = 6;
        public static final int RENEGOTIATING = 5;
        public static final int TERMINATED = 8;
        public static final int TERMINATING = 7;

        private State() {
        }

        public static String toString(int n) {
            switch (n) {
                default: {
                    return "UNKNOWN";
                }
                case 8: {
                    return "TERMINATED";
                }
                case 7: {
                    return "TERMINATING";
                }
                case 6: {
                    return "REESTABLISHING";
                }
                case 5: {
                    return "RENEGOTIATING";
                }
                case 4: {
                    return "ESTABLISHED";
                }
                case 3: {
                    return "ESTABLISHING";
                }
                case 2: {
                    return "NEGOTIATING";
                }
                case 1: {
                    return "INITIATED";
                }
                case 0: 
            }
            return "IDLE";
        }
    }

}

