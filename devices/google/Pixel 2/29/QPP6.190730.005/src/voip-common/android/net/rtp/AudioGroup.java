/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.ActivityThread
 */
package android.net.rtp;

import android.app.ActivityThread;
import android.net.rtp.AudioCodec;
import android.net.rtp.AudioStream;
import android.net.rtp.RtpStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AudioGroup {
    public static final int MODE_ECHO_SUPPRESSION = 3;
    private static final int MODE_LAST = 3;
    public static final int MODE_MUTED = 1;
    public static final int MODE_NORMAL = 2;
    public static final int MODE_ON_HOLD = 0;
    private int mMode = 0;
    private long mNative;
    private final Map<AudioStream, Long> mStreams = new HashMap<AudioStream, Long>();

    static {
        System.loadLibrary("rtp_jni");
    }

    private native long nativeAdd(int var1, int var2, String var3, int var4, String var5, int var6, String var7);

    private native void nativeRemove(long var1);

    private native void nativeSendDtmf(int var1);

    private native void nativeSetMode(int var1);

    void add(AudioStream object) {
        synchronized (this) {
            boolean bl = this.mStreams.containsKey(object);
            if (!bl) {
                Object object2 = ((AudioStream)object).getCodec();
                object2 = String.format(Locale.US, "%d %s %s", ((AudioCodec)object2).type, ((AudioCodec)object2).rtpmap, ((AudioCodec)object2).fmtp);
                long l = this.nativeAdd(((RtpStream)object).getMode(), ((RtpStream)object).getSocket(), ((RtpStream)object).getRemoteAddress().getHostAddress(), ((RtpStream)object).getRemotePort(), (String)object2, ((AudioStream)object).getDtmfType(), ActivityThread.currentOpPackageName());
                this.mStreams.put((AudioStream)object, l);
            }
            return;
        }
    }

    public void clear() {
        AudioStream[] arraudioStream = this.getStreams();
        int n = arraudioStream.length;
        for (int i = 0; i < n; ++i) {
            arraudioStream[i].join(null);
        }
    }

    protected void finalize() throws Throwable {
        this.nativeRemove(0L);
        super.finalize();
    }

    public int getMode() {
        return this.mMode;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AudioStream[] getStreams() {
        synchronized (this) {
            return this.mStreams.keySet().toArray(new AudioStream[this.mStreams.size()]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void remove(AudioStream object) {
        synchronized (this) {
            object = this.mStreams.remove(object);
            if (object != null) {
                this.nativeRemove((Long)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendDtmf(int n) {
        if (n >= 0 && n <= 15) {
            synchronized (this) {
                this.nativeSendDtmf(n);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid event");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMode(int n) {
        if (n >= 0 && n <= 3) {
            synchronized (this) {
                this.nativeSetMode(n);
                this.mMode = n;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid mode");
    }
}

