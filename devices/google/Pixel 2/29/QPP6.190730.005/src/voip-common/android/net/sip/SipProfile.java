/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  javax.sip.InvalidArgumentException
 *  javax.sip.PeerUnavailableException
 *  javax.sip.SipFactory
 *  javax.sip.address.Address
 *  javax.sip.address.AddressFactory
 *  javax.sip.address.SipURI
 *  javax.sip.address.URI
 */
package android.net.sip;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.PeerUnavailableException;
import javax.sip.SipFactory;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.URI;

public class SipProfile
implements Parcelable,
Serializable,
Cloneable {
    public static final Parcelable.Creator<SipProfile> CREATOR = new Parcelable.Creator<SipProfile>(){

        public SipProfile createFromParcel(Parcel parcel) {
            return new SipProfile(parcel);
        }

        public SipProfile[] newArray(int n) {
            return new SipProfile[n];
        }
    };
    private static final int DEFAULT_PORT = 5060;
    private static final String TCP = "TCP";
    private static final String UDP = "UDP";
    private static final long serialVersionUID = 1L;
    private Address mAddress;
    private String mAuthUserName;
    private boolean mAutoRegistration;
    private transient int mCallingUid;
    private String mDomain;
    private String mPassword;
    private int mPort = 5060;
    private String mProfileName;
    private String mProtocol = "UDP";
    private String mProxyAddress;
    private boolean mSendKeepAlive;

    private SipProfile() {
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
    }

    private SipProfile(Parcel parcel) {
        boolean bl = false;
        this.mSendKeepAlive = false;
        this.mAutoRegistration = true;
        this.mCallingUid = 0;
        this.mAddress = (Address)parcel.readSerializable();
        this.mProxyAddress = parcel.readString();
        this.mPassword = parcel.readString();
        this.mDomain = parcel.readString();
        this.mProtocol = parcel.readString();
        this.mProfileName = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.mSendKeepAlive = bl2;
        bl2 = parcel.readInt() == 0 ? bl : true;
        this.mAutoRegistration = bl2;
        this.mCallingUid = parcel.readInt();
        this.mPort = parcel.readInt();
        this.mAuthUserName = parcel.readString();
    }

    private Object readResolve() throws ObjectStreamException {
        if (this.mPort == 0) {
            this.mPort = 5060;
        }
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public String getAuthUserName() {
        return this.mAuthUserName;
    }

    public boolean getAutoRegistration() {
        return this.mAutoRegistration;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public String getDisplayName() {
        return this.mAddress.getDisplayName();
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public String getProtocol() {
        return this.mProtocol;
    }

    public String getProxyAddress() {
        return this.mProxyAddress;
    }

    public boolean getSendKeepAlive() {
        return this.mSendKeepAlive;
    }

    public Address getSipAddress() {
        return this.mAddress;
    }

    public String getSipDomain() {
        return this.mDomain;
    }

    public SipURI getUri() {
        return (SipURI)this.mAddress.getURI();
    }

    public String getUriString() {
        if (!TextUtils.isEmpty((CharSequence)this.mProxyAddress)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sip:");
            stringBuilder.append(this.getUserName());
            stringBuilder.append("@");
            stringBuilder.append(this.mDomain);
            return stringBuilder.toString();
        }
        return this.getUri().toString();
    }

    public String getUserName() {
        return this.getUri().getUser();
    }

    public void setCallingUid(int n) {
        this.mCallingUid = n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeSerializable((Serializable)this.mAddress);
        parcel.writeString(this.mProxyAddress);
        parcel.writeString(this.mPassword);
        parcel.writeString(this.mDomain);
        parcel.writeString(this.mProtocol);
        parcel.writeString(this.mProfileName);
        parcel.writeInt((int)this.mSendKeepAlive);
        parcel.writeInt((int)this.mAutoRegistration);
        parcel.writeInt(this.mCallingUid);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mAuthUserName);
    }

    public static class Builder {
        private AddressFactory mAddressFactory;
        private String mDisplayName;
        private SipProfile mProfile;
        private String mProxyAddress;
        private SipURI mUri;

        public Builder(SipProfile sipProfile) {
            block4 : {
                this.mProfile = new SipProfile();
                try {
                    this.mAddressFactory = SipFactory.getInstance().createAddressFactory();
                    if (sipProfile == null) break block4;
                }
                catch (PeerUnavailableException peerUnavailableException) {
                    throw new RuntimeException(peerUnavailableException);
                }
                try {
                    this.mProfile = (SipProfile)sipProfile.clone();
                }
                catch (CloneNotSupportedException cloneNotSupportedException) {
                    throw new RuntimeException("should not occur", cloneNotSupportedException);
                }
                this.mProfile.mAddress = null;
                this.mUri = sipProfile.getUri();
                this.mUri.setUserPassword(sipProfile.getPassword());
                this.mDisplayName = sipProfile.getDisplayName();
                this.mProxyAddress = sipProfile.getProxyAddress();
                this.mProfile.mPort = sipProfile.getPort();
                return;
            }
            throw new NullPointerException();
        }

        public Builder(String string) throws ParseException {
            block3 : {
                this.mProfile = new SipProfile();
                try {
                    this.mAddressFactory = SipFactory.getInstance().createAddressFactory();
                    if (string == null) break block3;
                }
                catch (PeerUnavailableException peerUnavailableException) {
                    throw new RuntimeException(peerUnavailableException);
                }
                Object object = this.mAddressFactory.createURI(this.fix(string));
                if (object instanceof SipURI) {
                    this.mUri = (SipURI)object;
                    this.mProfile.mDomain = this.mUri.getHost();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" is not a SIP URI");
                throw new ParseException(((StringBuilder)object).toString(), 0);
            }
            throw new NullPointerException("uriString cannot be null");
        }

        public Builder(String string, String string2) throws ParseException {
            block2 : {
                this.mProfile = new SipProfile();
                try {
                    this.mAddressFactory = SipFactory.getInstance().createAddressFactory();
                    if (string == null || string2 == null) break block2;
                }
                catch (PeerUnavailableException peerUnavailableException) {
                    throw new RuntimeException(peerUnavailableException);
                }
                this.mUri = this.mAddressFactory.createSipURI(string, string2);
                this.mProfile.mDomain = string2;
                return;
            }
            throw new NullPointerException("username and serverDomain cannot be null");
        }

        private String fix(String string) {
            if (!string.trim().toLowerCase().startsWith("sip:")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sip:");
                stringBuilder.append(string);
                string = stringBuilder.toString();
            }
            return string;
        }

        public SipProfile build() {
            this.mProfile.mPassword = this.mUri.getUserPassword();
            this.mUri.setUserPassword(null);
            try {
                if (!TextUtils.isEmpty((CharSequence)this.mProxyAddress)) {
                    SipURI sipURI = (SipURI)this.mAddressFactory.createURI(this.fix(this.mProxyAddress));
                    this.mProfile.mProxyAddress = sipURI.getHost();
                } else {
                    if (!this.mProfile.mProtocol.equals(SipProfile.UDP)) {
                        this.mUri.setTransportParam(this.mProfile.mProtocol);
                    }
                    if (this.mProfile.mPort != 5060) {
                        this.mUri.setPort(this.mProfile.mPort);
                    }
                }
                this.mProfile.mAddress = this.mAddressFactory.createAddress(this.mDisplayName, (URI)this.mUri);
                return this.mProfile;
            }
            catch (ParseException parseException) {
                throw new RuntimeException(parseException);
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw new RuntimeException(invalidArgumentException);
            }
        }

        public Builder setAuthUserName(String string) {
            this.mProfile.mAuthUserName = string;
            return this;
        }

        public Builder setAutoRegistration(boolean bl) {
            this.mProfile.mAutoRegistration = bl;
            return this;
        }

        public Builder setDisplayName(String string) {
            this.mDisplayName = string;
            return this;
        }

        public Builder setOutboundProxy(String string) {
            this.mProxyAddress = string;
            return this;
        }

        public Builder setPassword(String string) {
            this.mUri.setUserPassword(string);
            return this;
        }

        public Builder setPort(int n) throws IllegalArgumentException {
            if (n <= 65535 && n >= 1000) {
                this.mProfile.mPort = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("incorrect port arugment: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setProfileName(String string) {
            this.mProfile.mProfileName = string;
            return this;
        }

        public Builder setProtocol(String charSequence) throws IllegalArgumentException {
            if (charSequence != null) {
                String string = ((String)charSequence).toUpperCase();
                if (!string.equals(SipProfile.UDP) && !string.equals(SipProfile.TCP)) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("unsupported protocol: ");
                    ((StringBuilder)charSequence).append(string);
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                this.mProfile.mProtocol = string;
                return this;
            }
            throw new NullPointerException("protocol cannot be null");
        }

        public Builder setSendKeepAlive(boolean bl) {
            this.mProfile.mSendKeepAlive = bl;
            return this;
        }
    }

}

