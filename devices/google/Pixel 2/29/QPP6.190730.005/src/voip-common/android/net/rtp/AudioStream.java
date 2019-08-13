/*
 * Decompiled with CFR 0.145.
 */
package android.net.rtp;

import android.net.rtp.AudioCodec;
import android.net.rtp.AudioGroup;
import android.net.rtp.RtpStream;
import java.net.InetAddress;
import java.net.SocketException;

public class AudioStream
extends RtpStream {
    private AudioCodec mCodec;
    private int mDtmfType = -1;
    private AudioGroup mGroup;

    public AudioStream(InetAddress inetAddress) throws SocketException {
        super(inetAddress);
    }

    public AudioCodec getCodec() {
        return this.mCodec;
    }

    public int getDtmfType() {
        return this.mDtmfType;
    }

    public AudioGroup getGroup() {
        return this.mGroup;
    }

    @Override
    public final boolean isBusy() {
        boolean bl = this.mGroup != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void join(AudioGroup audioGroup) {
        synchronized (this) {
            if (this.mGroup == audioGroup) {
                return;
            }
            if (this.mGroup != null) {
                this.mGroup.remove(this);
                this.mGroup = null;
            }
            if (audioGroup != null) {
                audioGroup.add(this);
                this.mGroup = audioGroup;
            }
            return;
        }
    }

    public void setCodec(AudioCodec audioCodec) {
        if (!this.isBusy()) {
            if (audioCodec.type != this.mDtmfType) {
                this.mCodec = audioCodec;
                return;
            }
            throw new IllegalArgumentException("The type is used by DTMF");
        }
        throw new IllegalStateException("Busy");
    }

    public void setDtmfType(int n) {
        if (!this.isBusy()) {
            if (n != -1) {
                if (n >= 96 && n <= 127) {
                    AudioCodec audioCodec = this.mCodec;
                    if (audioCodec != null && n == audioCodec.type) {
                        throw new IllegalArgumentException("The type is used by codec");
                    }
                } else {
                    throw new IllegalArgumentException("Invalid type");
                }
            }
            this.mDtmfType = n;
            return;
        }
        throw new IllegalStateException("Busy");
    }
}

