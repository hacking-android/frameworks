/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.NioUtils
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.media.AudioPresentation;
import android.media.AudioSystem;
import android.media.Image;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaDescrambler;
import android.media.MediaFormat;
import android.media.Utils;
import android.os.Bundle;
import android.os.Handler;
import android.os.IHwBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.Surface;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.NioUtils;
import java.nio.ReadOnlyBufferException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class MediaCodec {
    public static final int BUFFER_FLAG_CODEC_CONFIG = 2;
    public static final int BUFFER_FLAG_END_OF_STREAM = 4;
    public static final int BUFFER_FLAG_KEY_FRAME = 1;
    public static final int BUFFER_FLAG_MUXER_DATA = 16;
    public static final int BUFFER_FLAG_PARTIAL_FRAME = 8;
    public static final int BUFFER_FLAG_SYNC_FRAME = 1;
    private static final int CB_ERROR = 3;
    private static final int CB_INPUT_AVAILABLE = 1;
    private static final int CB_OUTPUT_AVAILABLE = 2;
    private static final int CB_OUTPUT_FORMAT_CHANGE = 4;
    public static final int CONFIGURE_FLAG_ENCODE = 1;
    public static final int CRYPTO_MODE_AES_CBC = 2;
    public static final int CRYPTO_MODE_AES_CTR = 1;
    public static final int CRYPTO_MODE_UNENCRYPTED = 0;
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_FRAME_RENDERED = 3;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int INFO_OUTPUT_BUFFERS_CHANGED = -3;
    public static final int INFO_OUTPUT_FORMAT_CHANGED = -2;
    public static final int INFO_TRY_AGAIN_LATER = -1;
    public static final String PARAMETER_KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
    public static final String PARAMETER_KEY_OFFSET_TIME = "time-offset-us";
    public static final String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
    public static final String PARAMETER_KEY_SUSPEND = "drop-input-frames";
    public static final String PARAMETER_KEY_SUSPEND_TIME = "drop-start-time-us";
    public static final String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private final Object mBufferLock;
    private ByteBuffer[] mCachedInputBuffers;
    private ByteBuffer[] mCachedOutputBuffers;
    private Callback mCallback;
    private EventHandler mCallbackHandler;
    private MediaCodecInfo mCodecInfo;
    private final Object mCodecInfoLock = new Object();
    private MediaCrypto mCrypto;
    private final BufferMap mDequeuedInputBuffers;
    private final BufferMap mDequeuedOutputBuffers;
    private final Map<Integer, BufferInfo> mDequeuedOutputInfos;
    private EventHandler mEventHandler;
    private boolean mHasSurface = false;
    private final Object mListenerLock = new Object();
    private String mNameAtCreation;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private long mNativeContext;
    private final Lock mNativeContextLock;
    private EventHandler mOnFrameRenderedHandler;
    private OnFrameRenderedListener mOnFrameRenderedListener;

    static {
        System.loadLibrary("media_jni");
        MediaCodec.native_init();
    }

    private MediaCodec(String string2, boolean bl, boolean bl2) {
        String string3 = null;
        this.mDequeuedInputBuffers = new BufferMap();
        this.mDequeuedOutputBuffers = new BufferMap();
        this.mDequeuedOutputInfos = new HashMap<Integer, BufferInfo>();
        this.mNativeContext = 0L;
        this.mNativeContextLock = new ReentrantLock();
        Object object = Looper.myLooper();
        this.mEventHandler = object != null ? new EventHandler(this, (Looper)object) : ((object = Looper.getMainLooper()) != null ? new EventHandler(this, (Looper)object) : null);
        this.mCallbackHandler = object = this.mEventHandler;
        this.mOnFrameRenderedHandler = object;
        this.mBufferLock = new Object();
        if (!bl) {
            string3 = string2;
        }
        this.mNameAtCreation = string3;
        this.native_setup(string2, bl, bl2);
    }

    private final void cacheBuffers(boolean bl) {
        ByteBuffer[] arrbyteBuffer;
        ByteBuffer[] arrbyteBuffer2 = null;
        arrbyteBuffer2 = arrbyteBuffer = this.getBuffers(bl);
        try {
            this.invalidateByteBuffers(arrbyteBuffer);
            arrbyteBuffer2 = arrbyteBuffer;
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        if (bl) {
            this.mCachedInputBuffers = arrbyteBuffer2;
        } else {
            this.mCachedOutputBuffers = arrbyteBuffer2;
        }
    }

    private void configure(MediaFormat arrobject, Surface surface, MediaCrypto mediaCrypto, IHwBinder iHwBinder, int n) {
        String[] arrstring;
        block7 : {
            block4 : {
                if (mediaCrypto != null && iHwBinder != null) {
                    throw new IllegalArgumentException("Can't use crypto and descrambler together!");
                }
                arrstring = null;
                if (arrobject == null) break block4;
                Map.Entry<String, Object> entry = arrobject.getMap();
                arrstring = new String[entry.size()];
                arrobject = new Object[entry.size()];
                Iterator<Map.Entry<String, Object>> iterator = entry.entrySet().iterator();
                int n2 = 0;
                while (iterator.hasNext()) {
                    block6 : {
                        block5 : {
                            int n3;
                            entry = iterator.next();
                            if (!((String)entry.getKey()).equals("audio-session-id")) break block5;
                            try {
                                n3 = (Integer)entry.getValue();
                                arrstring[n2] = "audio-hw-sync";
                            }
                            catch (Exception exception) {
                                throw new IllegalArgumentException("Wrong Session ID Parameter!");
                            }
                            arrobject[n2] = AudioSystem.getAudioHwSyncForSession(n3);
                            break block6;
                        }
                        arrstring[n2] = (String)entry.getKey();
                        arrobject[n2] = entry.getValue();
                    }
                    ++n2;
                }
                break block7;
            }
            arrobject = null;
        }
        boolean bl = surface != null;
        this.mHasSurface = bl;
        this.mCrypto = mediaCrypto;
        this.native_configure(arrstring, arrobject, surface, mediaCrypto, iHwBinder, n);
    }

    public static MediaCodec createByCodecName(String string2) throws IOException {
        return new MediaCodec(string2, false, false);
    }

    public static MediaCodec createDecoderByType(String string2) throws IOException {
        return new MediaCodec(string2, true, false);
    }

    public static MediaCodec createEncoderByType(String string2) throws IOException {
        return new MediaCodec(string2, true, true);
    }

    public static Surface createPersistentInputSurface() {
        return MediaCodec.native_createPersistentInputSurface();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void freeAllTrackedBuffers() {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.freeByteBuffers(this.mCachedInputBuffers);
            this.freeByteBuffers(this.mCachedOutputBuffers);
            this.mCachedInputBuffers = null;
            this.mCachedOutputBuffers = null;
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
            return;
        }
    }

    private final void freeByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            NioUtils.freeDirectBuffer((ByteBuffer)byteBuffer);
        }
    }

    private final void freeByteBuffers(ByteBuffer[] arrbyteBuffer) {
        if (arrbyteBuffer != null) {
            int n = arrbyteBuffer.length;
            for (int i = 0; i < n; ++i) {
                this.freeByteBuffer(arrbyteBuffer[i]);
            }
        }
    }

    private final native ByteBuffer getBuffer(boolean var1, int var2);

    @UnsupportedAppUsage
    private final native ByteBuffer[] getBuffers(boolean var1);

    private EventHandler getEventHandlerOn(Handler object, EventHandler eventHandler) {
        if (object == null) {
            return this.mEventHandler;
        }
        object = ((Handler)object).getLooper();
        if (eventHandler.getLooper() == object) {
            return eventHandler;
        }
        return new EventHandler(this, (Looper)object);
    }

    private final native Map<String, Object> getFormatNative(boolean var1);

    private final native Image getImage(boolean var1, int var2);

    private final native Map<String, Object> getOutputFormatNative(int var1);

    private final native MediaCodecInfo getOwnCodecInfo();

    private final void invalidateByteBuffer(ByteBuffer[] object, int n) {
        if (object != null && n >= 0 && n < ((ByteBuffer[])object).length && (object = object[n]) != null) {
            object.setAccessible(false);
        }
    }

    private final void invalidateByteBuffers(ByteBuffer[] arrbyteBuffer) {
        if (arrbyteBuffer != null) {
            for (ByteBuffer byteBuffer : arrbyteBuffer) {
                if (byteBuffer == null) continue;
                byteBuffer.setAccessible(false);
            }
        }
    }

    private final long lockAndGetContext() {
        this.mNativeContextLock.lock();
        return this.mNativeContext;
    }

    private final native void native_configure(String[] var1, Object[] var2, Surface var3, MediaCrypto var4, IHwBinder var5, int var6);

    private static final native PersistentSurface native_createPersistentInputSurface();

    private final native int native_dequeueInputBuffer(long var1);

    private final native int native_dequeueOutputBuffer(BufferInfo var1, long var2);

    private native void native_enableOnFrameRenderedListener(boolean var1);

    private final native void native_finalize();

    private final native void native_flush();

    private native PersistableBundle native_getMetrics();

    private static final native void native_init();

    private final native void native_queueInputBuffer(int var1, int var2, int var3, long var4, int var6) throws CryptoException;

    private final native void native_queueSecureInputBuffer(int var1, int var2, CryptoInfo var3, long var4, int var6) throws CryptoException;

    private final native void native_release();

    private static final native void native_releasePersistentInputSurface(Surface var0);

    private final native void native_reset();

    private native void native_setAudioPresentation(int var1, int var2);

    private final native void native_setCallback(Callback var1);

    private final native void native_setInputSurface(Surface var1);

    private native void native_setSurface(Surface var1);

    private final native void native_setup(String var1, boolean var2, boolean var3);

    private final native void native_start();

    private final native void native_stop();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void postEventFromNative(int n, int n2, int n3, Object object) {
        Object object2 = this.mListenerLock;
        synchronized (object2) {
            EventHandler eventHandler = this.mEventHandler;
            if (n == 1) {
                eventHandler = this.mCallbackHandler;
            } else if (n == 3) {
                eventHandler = this.mOnFrameRenderedHandler;
            }
            if (eventHandler != null) {
                eventHandler.sendMessage(eventHandler.obtainMessage(n, n2, n3, object));
            }
            return;
        }
    }

    @UnsupportedAppUsage
    private final native void releaseOutputBuffer(int var1, boolean var2, boolean var3, long var4);

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void revalidateByteBuffer(ByteBuffer[] object, int n) {
        Object object2 = this.mBufferLock;
        synchronized (object2) {
            if (object != null && n >= 0 && n < ((ByteBuffer[])object).length && (object = object[n]) != null) {
                object.setAccessible(true);
            }
            return;
        }
    }

    private final void setAndUnlockContext(long l) {
        this.mNativeContext = l;
        this.mNativeContextLock.unlock();
    }

    @UnsupportedAppUsage
    private final native void setParameters(String[] var1, Object[] var2);

    private final void validateInputByteBuffer(ByteBuffer[] object, int n) {
        if (object != null && n >= 0 && n < ((ByteBuffer[])object).length && (object = object[n]) != null) {
            object.setAccessible(true);
            ((Buffer)object).clear();
        }
    }

    private final void validateOutputByteBuffer(ByteBuffer[] object, int n, BufferInfo bufferInfo) {
        if (object != null && n >= 0 && n < ((ByteBuffer[])object).length && (object = object[n]) != null) {
            object.setAccessible(true);
            ((Buffer)object).limit(bufferInfo.offset + bufferInfo.size).position(bufferInfo.offset);
        }
    }

    public void configure(MediaFormat mediaFormat, Surface surface, int n, MediaDescrambler object) {
        object = object != null ? ((MediaDescrambler)object).getBinder() : null;
        this.configure(mediaFormat, surface, null, (IHwBinder)object, n);
    }

    public void configure(MediaFormat mediaFormat, Surface surface, MediaCrypto mediaCrypto, int n) {
        this.configure(mediaFormat, surface, mediaCrypto, null, n);
    }

    public final native Surface createInputSurface();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int dequeueInputBuffer(long l) {
        int n = this.native_dequeueInputBuffer(l);
        if (n < 0) return n;
        Object object = this.mBufferLock;
        synchronized (object) {
            this.validateInputByteBuffer(this.mCachedInputBuffers, n);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int dequeueOutputBuffer(BufferInfo bufferInfo, long l) {
        int n = this.native_dequeueOutputBuffer(bufferInfo, l);
        Object object = this.mBufferLock;
        synchronized (object) {
            Throwable throwable2;
            block7 : {
                block6 : {
                    if (n == -3) {
                        try {
                            this.cacheBuffers(false);
                            break block6;
                        }
                        catch (Throwable throwable2) {
                            break block7;
                        }
                    }
                    if (n >= 0) {
                        this.validateOutputByteBuffer(this.mCachedOutputBuffers, n, bufferInfo);
                        if (this.mHasSurface) {
                            this.mDequeuedOutputInfos.put(n, bufferInfo.dup());
                        }
                    }
                }
                return n;
            }
            throw throwable2;
        }
    }

    protected void finalize() {
        this.native_finalize();
        this.mCrypto = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void flush() {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffers(this.mCachedInputBuffers);
            this.invalidateByteBuffers(this.mCachedOutputBuffers);
            this.mDequeuedInputBuffers.clear();
            this.mDequeuedOutputBuffers.clear();
        }
        this.native_flush();
    }

    public final native String getCanonicalName();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MediaCodecInfo getCodecInfo() {
        String string2 = this.getName();
        Object object = this.mCodecInfoLock;
        synchronized (object) {
            if (this.mCodecInfo != null) return this.mCodecInfo;
            this.mCodecInfo = this.getOwnCodecInfo();
            if (this.mCodecInfo != null) return this.mCodecInfo;
            this.mCodecInfo = MediaCodecList.getInfoFor(string2);
            return this.mCodecInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ByteBuffer getInputBuffer(int n) {
        ByteBuffer byteBuffer = this.getBuffer(true, n);
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedInputBuffers, n);
            this.mDequeuedInputBuffers.put(n, byteBuffer);
            return byteBuffer;
        }
    }

    public ByteBuffer[] getInputBuffers() {
        ByteBuffer[] arrbyteBuffer = this.mCachedInputBuffers;
        if (arrbyteBuffer != null) {
            return arrbyteBuffer;
        }
        throw new IllegalStateException();
    }

    public final MediaFormat getInputFormat() {
        return new MediaFormat(this.getFormatNative(true));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Image getInputImage(int n) {
        Image image = this.getImage(true, n);
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedInputBuffers, n);
            this.mDequeuedInputBuffers.put(n, image);
            return image;
        }
    }

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    public final String getName() {
        String string2;
        block0 : {
            string2 = this.getCanonicalName();
            String string3 = this.mNameAtCreation;
            if (string3 == null) break block0;
            string2 = string3;
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ByteBuffer getOutputBuffer(int n) {
        ByteBuffer byteBuffer = this.getBuffer(false, n);
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedOutputBuffers, n);
            this.mDequeuedOutputBuffers.put(n, byteBuffer);
            return byteBuffer;
        }
    }

    public ByteBuffer[] getOutputBuffers() {
        ByteBuffer[] arrbyteBuffer = this.mCachedOutputBuffers;
        if (arrbyteBuffer != null) {
            return arrbyteBuffer;
        }
        throw new IllegalStateException();
    }

    public final MediaFormat getOutputFormat() {
        return new MediaFormat(this.getFormatNative(false));
    }

    public final MediaFormat getOutputFormat(int n) {
        return new MediaFormat(this.getOutputFormatNative(n));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Image getOutputImage(int n) {
        Image image = this.getImage(false, n);
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedOutputBuffers, n);
            this.mDequeuedOutputBuffers.put(n, image);
            return image;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void queueInputBuffer(int n, int n2, int n3, long l, int n4) throws CryptoException {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedInputBuffers, n);
            this.mDequeuedInputBuffers.remove(n);
        }
        try {
            this.native_queueInputBuffer(n, n2, n3, l, n4);
            return;
        }
        catch (CryptoException | IllegalStateException runtimeException) {
            this.revalidateByteBuffer(this.mCachedInputBuffers, n);
            throw runtimeException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void queueSecureInputBuffer(int n, int n2, CryptoInfo cryptoInfo, long l, int n3) throws CryptoException {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedInputBuffers, n);
            this.mDequeuedInputBuffers.remove(n);
        }
        try {
            this.native_queueSecureInputBuffer(n, n2, cryptoInfo, l, n3);
            return;
        }
        catch (CryptoException | IllegalStateException runtimeException) {
            this.revalidateByteBuffer(this.mCachedInputBuffers, n);
            throw runtimeException;
        }
    }

    public final void release() {
        this.freeAllTrackedBuffers();
        this.native_release();
        this.mCrypto = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void releaseOutputBuffer(int n, long l) {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedOutputBuffers, n);
            this.mDequeuedOutputBuffers.remove(n);
            if (this.mHasSurface) {
                BufferInfo bufferInfo = this.mDequeuedOutputInfos.remove(n);
            }
        }
        this.releaseOutputBuffer(n, true, true, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void releaseOutputBuffer(int n, boolean bl) {
        Object object = this.mBufferLock;
        synchronized (object) {
            this.invalidateByteBuffer(this.mCachedOutputBuffers, n);
            this.mDequeuedOutputBuffers.remove(n);
            if (this.mHasSurface) {
                BufferInfo bufferInfo = this.mDequeuedOutputInfos.remove(n);
            }
        }
        this.releaseOutputBuffer(n, bl, false, 0L);
    }

    public final void reset() {
        this.freeAllTrackedBuffers();
        this.native_reset();
        this.mCrypto = null;
    }

    public void setAudioPresentation(AudioPresentation audioPresentation) {
        if (audioPresentation != null) {
            this.native_setAudioPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
            return;
        }
        throw new NullPointerException("audio presentation is null");
    }

    public void setCallback(Callback callback) {
        this.setCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCallback(Callback callback, Handler object) {
        if (callback != null) {
            Object object2 = this.mListenerLock;
            synchronized (object2) {
                object = this.getEventHandlerOn((Handler)object, this.mCallbackHandler);
                if (object != this.mCallbackHandler) {
                    this.mCallbackHandler.removeMessages(2);
                    this.mCallbackHandler.removeMessages(1);
                    this.mCallbackHandler = object;
                }
            }
        } else {
            object = this.mCallbackHandler;
            if (object != null) {
                ((Handler)object).removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
        }
        object = this.mCallbackHandler;
        if (object != null) {
            object = ((Handler)object).obtainMessage(2, 0, 0, callback);
            this.mCallbackHandler.sendMessage((Message)object);
            this.native_setCallback(callback);
        }
    }

    public void setInputSurface(Surface surface) {
        if (surface instanceof PersistentSurface) {
            this.native_setInputSurface(surface);
            return;
        }
        throw new IllegalArgumentException("not a PersistentSurface");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnFrameRenderedListener(OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        Object object = this.mListenerLock;
        synchronized (object) {
            this.mOnFrameRenderedListener = onFrameRenderedListener;
            if (onFrameRenderedListener != null) {
                if ((handler = this.getEventHandlerOn(handler, this.mOnFrameRenderedHandler)) != this.mOnFrameRenderedHandler) {
                    this.mOnFrameRenderedHandler.removeMessages(3);
                }
                this.mOnFrameRenderedHandler = handler;
            } else if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
            boolean bl = onFrameRenderedListener != null;
            this.native_enableOnFrameRenderedListener(bl);
            return;
        }
    }

    public void setOutputSurface(Surface surface) {
        if (this.mHasSurface) {
            this.native_setSurface(surface);
            return;
        }
        throw new IllegalStateException("codec was not configured for an output surface");
    }

    public final void setParameters(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String[] arrstring = new String[bundle.size()];
        Object[] arrobject = new Object[bundle.size()];
        int n = 0;
        Iterator<String> iterator = bundle.keySet().iterator();
        while (iterator.hasNext()) {
            String string2;
            arrstring[n] = string2 = iterator.next();
            Object object = bundle.get(string2);
            arrobject[n] = object instanceof byte[] ? ByteBuffer.wrap((byte[])object) : object;
            ++n;
        }
        this.setParameters(arrstring, arrobject);
    }

    public final native void setVideoScalingMode(int var1);

    public final native void signalEndOfInputStream();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void start() {
        this.native_start();
        Object object = this.mBufferLock;
        synchronized (object) {
            this.cacheBuffers(true);
            this.cacheBuffers(false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void stop() {
        this.native_stop();
        this.freeAllTrackedBuffers();
        Object object = this.mListenerLock;
        synchronized (object) {
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.removeMessages(2);
                this.mCallbackHandler.removeMessages(1);
            }
            if (this.mOnFrameRenderedHandler != null) {
                this.mOnFrameRenderedHandler.removeMessages(3);
            }
            return;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BufferFlag {
    }

    public static final class BufferInfo {
        public int flags;
        public int offset;
        public long presentationTimeUs;
        public int size;

        public BufferInfo dup() {
            BufferInfo bufferInfo = new BufferInfo();
            bufferInfo.set(this.offset, this.size, this.presentationTimeUs, this.flags);
            return bufferInfo;
        }

        public void set(int n, int n2, long l, int n3) {
            this.offset = n;
            this.size = n2;
            this.presentationTimeUs = l;
            this.flags = n3;
        }
    }

    private static class BufferMap {
        private final Map<Integer, CodecBuffer> mMap = new HashMap<Integer, CodecBuffer>();

        private BufferMap() {
        }

        public void clear() {
            Iterator<CodecBuffer> iterator = this.mMap.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().free();
            }
            this.mMap.clear();
        }

        public void put(int n, Image image) {
            CodecBuffer codecBuffer;
            CodecBuffer codecBuffer2 = codecBuffer = this.mMap.get(n);
            if (codecBuffer == null) {
                codecBuffer2 = new CodecBuffer();
                this.mMap.put(n, codecBuffer2);
            }
            codecBuffer2.setImage(image);
        }

        public void put(int n, ByteBuffer byteBuffer) {
            CodecBuffer codecBuffer;
            CodecBuffer codecBuffer2 = codecBuffer = this.mMap.get(n);
            if (codecBuffer == null) {
                codecBuffer2 = new CodecBuffer();
                this.mMap.put(n, codecBuffer2);
            }
            codecBuffer2.setByteBuffer(byteBuffer);
        }

        public void remove(int n) {
            CodecBuffer codecBuffer = this.mMap.get(n);
            if (codecBuffer != null) {
                codecBuffer.free();
                this.mMap.remove(n);
            }
        }

        private static class CodecBuffer {
            private ByteBuffer mByteBuffer;
            private Image mImage;

            private CodecBuffer() {
            }

            public void free() {
                Object object = this.mByteBuffer;
                if (object != null) {
                    NioUtils.freeDirectBuffer((ByteBuffer)object);
                    this.mByteBuffer = null;
                }
                if ((object = this.mImage) != null) {
                    ((Image)object).close();
                    this.mImage = null;
                }
            }

            public void setByteBuffer(ByteBuffer byteBuffer) {
                this.free();
                this.mByteBuffer = byteBuffer;
            }

            public void setImage(Image image) {
                this.free();
                this.mImage = image;
            }
        }

    }

    public static abstract class Callback {
        public abstract void onError(MediaCodec var1, CodecException var2);

        public abstract void onInputBufferAvailable(MediaCodec var1, int var2);

        public abstract void onOutputBufferAvailable(MediaCodec var1, int var2, BufferInfo var3);

        public abstract void onOutputFormatChanged(MediaCodec var1, MediaFormat var2);
    }

    public static final class CodecException
    extends IllegalStateException {
        private static final int ACTION_RECOVERABLE = 2;
        private static final int ACTION_TRANSIENT = 1;
        public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
        public static final int ERROR_RECLAIMED = 1101;
        private final int mActionCode;
        private final String mDiagnosticInfo;
        private final int mErrorCode;

        @UnsupportedAppUsage
        CodecException(int n, int n2, String string2) {
            super(string2);
            this.mErrorCode = n;
            this.mActionCode = n2;
            string2 = n < 0 ? "neg_" : "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android.media.MediaCodec.error_");
            stringBuilder.append(string2);
            stringBuilder.append(Math.abs(n));
            this.mDiagnosticInfo = stringBuilder.toString();
        }

        public String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public boolean isRecoverable() {
            boolean bl = this.mActionCode == 2;
            return bl;
        }

        public boolean isTransient() {
            int n = this.mActionCode;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            return bl;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ReasonCode {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ConfigureFlag {
    }

    public static final class CryptoException
    extends RuntimeException {
        public static final int ERROR_FRAME_TOO_LARGE = 8;
        public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
        public static final int ERROR_INSUFFICIENT_SECURITY = 7;
        public static final int ERROR_KEY_EXPIRED = 2;
        public static final int ERROR_LOST_STATE = 9;
        public static final int ERROR_NO_KEY = 1;
        public static final int ERROR_RESOURCE_BUSY = 3;
        public static final int ERROR_SESSION_NOT_OPENED = 5;
        public static final int ERROR_UNSUPPORTED_OPERATION = 6;
        private int mErrorCode;

        public CryptoException(int n, String string2) {
            super(string2);
            this.mErrorCode = n;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface CryptoErrorCode {
        }

    }

    public static final class CryptoInfo {
        public byte[] iv;
        public byte[] key;
        public int mode;
        public int[] numBytesOfClearData;
        public int[] numBytesOfEncryptedData;
        public int numSubSamples;
        private Pattern pattern;
        private final Pattern zeroPattern = new Pattern(0, 0);

        private void setPattern(int n, int n2) {
            this.pattern = new Pattern(n, n2);
        }

        public void set(int n, int[] arrn, int[] arrn2, byte[] arrby, byte[] arrby2, int n2) {
            this.numSubSamples = n;
            this.numBytesOfClearData = arrn;
            this.numBytesOfEncryptedData = arrn2;
            this.key = arrby;
            this.iv = arrby2;
            this.mode = n2;
            this.pattern = this.zeroPattern;
        }

        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        public String toString() {
            int n;
            StringBuilder stringBuilder = new StringBuilder();
            byte[] arrby = new StringBuilder();
            arrby.append(this.numSubSamples);
            arrby.append(" subsamples, key [");
            stringBuilder.append(arrby.toString());
            for (n = 0; n < (arrby = this.key).length; ++n) {
                stringBuilder.append("0123456789abcdef".charAt((arrby[n] & 240) >> 4));
                stringBuilder.append("0123456789abcdef".charAt(this.key[n] & 15));
            }
            stringBuilder.append("], iv [");
            for (n = 0; n < this.key.length; ++n) {
                stringBuilder.append("0123456789abcdef".charAt((this.iv[n] & 240) >> 4));
                stringBuilder.append("0123456789abcdef".charAt(this.iv[n] & 15));
            }
            stringBuilder.append("], clear ");
            stringBuilder.append(Arrays.toString(this.numBytesOfClearData));
            stringBuilder.append(", encrypted ");
            stringBuilder.append(Arrays.toString(this.numBytesOfEncryptedData));
            return stringBuilder.toString();
        }

        public static final class Pattern {
            private int mEncryptBlocks;
            private int mSkipBlocks;

            public Pattern(int n, int n2) {
                this.set(n, n2);
            }

            public int getEncryptBlocks() {
                return this.mEncryptBlocks;
            }

            public int getSkipBlocks() {
                return this.mSkipBlocks;
            }

            public void set(int n, int n2) {
                this.mEncryptBlocks = n;
                this.mSkipBlocks = n2;
            }
        }

    }

    private class EventHandler
    extends Handler {
        private MediaCodec mCodec;

        public EventHandler(MediaCodec mediaCodec2, Looper looper) {
            super(looper);
            this.mCodec = mediaCodec2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void handleCallback(Message object) {
            if (MediaCodec.this.mCallback == null) {
                return;
            }
            int n = ((Message)object).arg1;
            if (n == 1) {
                n = ((Message)object).arg2;
                object = MediaCodec.this.mBufferLock;
                synchronized (object) {
                    MediaCodec.this.validateInputByteBuffer(MediaCodec.this.mCachedInputBuffers, n);
                }
                MediaCodec.this.mCallback.onInputBufferAvailable(this.mCodec, n);
                return;
            }
            if (n == 2) {
                n = ((Message)object).arg2;
                BufferInfo bufferInfo = (BufferInfo)((Message)object).obj;
                object = MediaCodec.this.mBufferLock;
                synchronized (object) {
                    MediaCodec.this.validateOutputByteBuffer(MediaCodec.this.mCachedOutputBuffers, n, bufferInfo);
                }
                MediaCodec.this.mCallback.onOutputBufferAvailable(this.mCodec, n, bufferInfo);
                return;
            }
            if (n == 3) {
                MediaCodec.this.mCallback.onError(this.mCodec, (CodecException)((Message)object).obj);
                return;
            }
            if (n != 4) {
                return;
            }
            MediaCodec.this.mCallback.onOutputFormatChanged(this.mCodec, new MediaFormat((Map)((Message)object).obj));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n == 1) {
                this.handleCallback((Message)object);
                return;
            }
            if (n == 2) {
                MediaCodec.this.mCallback = (Callback)((Message)object).obj;
                return;
            }
            if (n != 3) {
                return;
            }
            Object object2 = MediaCodec.this.mListenerLock;
            synchronized (object2) {
                object = (Map)((Message)object).obj;
                n = 0;
                do {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(n);
                    stringBuilder.append("-media-time-us");
                    stringBuilder = object.get(stringBuilder.toString());
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(n);
                    stringBuilder2.append("-system-nano");
                    stringBuilder2 = object.get(stringBuilder2.toString());
                    if (stringBuilder == null) return;
                    if (stringBuilder2 == null) return;
                    if (MediaCodec.this.mOnFrameRenderedListener == null) {
                        return;
                    }
                    MediaCodec.this.mOnFrameRenderedListener.onFrameRendered(this.mCodec, (Long)((Object)stringBuilder), (Long)((Object)stringBuilder2));
                    ++n;
                } while (true);
            }
        }
    }

    public static class MediaImage
    extends Image {
        private static final int TYPE_YUV = 1;
        private final ByteBuffer mBuffer;
        private final int mFormat;
        private final int mHeight;
        private final ByteBuffer mInfo;
        private final boolean mIsReadOnly;
        private final Image.Plane[] mPlanes;
        private final int mScalingMode;
        private long mTimestamp;
        private final int mTransform;
        private final int mWidth;
        private final int mXOffset;
        private final int mYOffset;

        public MediaImage(ByteBuffer object, ByteBuffer byteBuffer, boolean bl, long l, int n, int n2, Rect rect) {
            this.mTransform = 0;
            this.mScalingMode = 0;
            this.mFormat = 35;
            this.mTimestamp = l;
            this.mIsImageValid = true;
            this.mIsReadOnly = ((Buffer)object).isReadOnly();
            this.mBuffer = ((ByteBuffer)object).duplicate();
            this.mXOffset = n;
            this.mYOffset = n2;
            this.mInfo = byteBuffer;
            if (byteBuffer.remaining() == 104) {
                int n3 = byteBuffer.getInt();
                if (n3 == 1) {
                    int n4 = byteBuffer.getInt();
                    if (n4 == 3) {
                        this.mWidth = byteBuffer.getInt();
                        this.mHeight = byteBuffer.getInt();
                        if (this.mWidth >= 1 && this.mHeight >= 1) {
                            int n5 = byteBuffer.getInt();
                            if (n5 == 8) {
                                n3 = byteBuffer.getInt();
                                if (n3 == 8) {
                                    block7 : {
                                        int n6;
                                        int n7;
                                        block8 : {
                                            int n8;
                                            int n9;
                                            this.mPlanes = new MediaPlane[n4];
                                            n3 = 0;
                                            do {
                                                int n10;
                                                Object object2 = object;
                                                if (n3 >= n4) break block7;
                                                int n11 = byteBuffer.getInt();
                                                n9 = byteBuffer.getInt();
                                                n8 = byteBuffer.getInt();
                                                n7 = byteBuffer.getInt();
                                                if (n7 != (n6 = byteBuffer.getInt()) || n7 != (n10 = n3 == 0 ? 1 : 2)) break block8;
                                                if (n9 < 1 || n8 < 1) break;
                                                ((Buffer)object).clear();
                                                ((Buffer)object2).position(this.mBuffer.position() + n11 + n / n7 * n9 + n2 / n6 * n8);
                                                ((Buffer)object2).limit(((Buffer)object).position() + Utils.divUp(n5, 8) + (this.mHeight / n6 - 1) * n8 + (this.mWidth / n7 - 1) * n9);
                                                this.mPlanes[n3] = new MediaPlane(((ByteBuffer)object).slice(), n8, n9);
                                                ++n3;
                                            } while (true);
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("unexpected strides: ");
                                            ((StringBuilder)object).append(n9);
                                            ((StringBuilder)object).append(" pixel, ");
                                            ((StringBuilder)object).append(n8);
                                            ((StringBuilder)object).append(" row on plane ");
                                            ((StringBuilder)object).append(n3);
                                            throw new UnsupportedOperationException(((StringBuilder)object).toString());
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("unexpected subsampling: ");
                                        ((StringBuilder)object).append(n7);
                                        ((StringBuilder)object).append("x");
                                        ((StringBuilder)object).append(n6);
                                        ((StringBuilder)object).append(" on plane ");
                                        ((StringBuilder)object).append(n3);
                                        throw new UnsupportedOperationException(((StringBuilder)object).toString());
                                    }
                                    object = rect == null ? new Rect(0, 0, this.mWidth, this.mHeight) : rect;
                                    ((Rect)object).offset(-n, -n2);
                                    super.setCropRect((Rect)object);
                                    return;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("unsupported allocated bit depth: ");
                                ((StringBuilder)object).append(n3);
                                throw new UnsupportedOperationException(((StringBuilder)object).toString());
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("unsupported bit depth: ");
                            ((StringBuilder)object).append(n5);
                            throw new UnsupportedOperationException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unsupported size: ");
                        ((StringBuilder)object).append(this.mWidth);
                        ((StringBuilder)object).append("x");
                        ((StringBuilder)object).append(this.mHeight);
                        throw new UnsupportedOperationException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unexpected number of planes: ");
                    ((StringBuilder)object).append(n4);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("unsupported type: ");
                ((StringBuilder)object).append(n3);
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unsupported info length: ");
            ((StringBuilder)object).append(byteBuffer.remaining());
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }

        @Override
        public void close() {
            if (this.mIsImageValid) {
                NioUtils.freeDirectBuffer((ByteBuffer)this.mBuffer);
                this.mIsImageValid = false;
            }
        }

        @Override
        public int getFormat() {
            this.throwISEIfImageIsInvalid();
            return this.mFormat;
        }

        @Override
        public int getHeight() {
            this.throwISEIfImageIsInvalid();
            return this.mHeight;
        }

        @Override
        public Image.Plane[] getPlanes() {
            this.throwISEIfImageIsInvalid();
            Image.Plane[] arrplane = this.mPlanes;
            return Arrays.copyOf(arrplane, arrplane.length);
        }

        @Override
        public int getScalingMode() {
            this.throwISEIfImageIsInvalid();
            return 0;
        }

        @Override
        public long getTimestamp() {
            this.throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override
        public int getTransform() {
            this.throwISEIfImageIsInvalid();
            return 0;
        }

        @Override
        public int getWidth() {
            this.throwISEIfImageIsInvalid();
            return this.mWidth;
        }

        @Override
        public void setCropRect(Rect rect) {
            if (!this.mIsReadOnly) {
                super.setCropRect(rect);
                return;
            }
            throw new ReadOnlyBufferException();
        }

        private class MediaPlane
        extends Image.Plane {
            private final int mColInc;
            private final ByteBuffer mData;
            private final int mRowInc;

            public MediaPlane(ByteBuffer byteBuffer, int n, int n2) {
                this.mData = byteBuffer;
                this.mRowInc = n;
                this.mColInc = n2;
            }

            @Override
            public ByteBuffer getBuffer() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mData;
            }

            @Override
            public int getPixelStride() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mColInc;
            }

            @Override
            public int getRowStride() {
                MediaImage.this.throwISEIfImageIsInvalid();
                return this.mRowInc;
            }
        }

    }

    public static final class MetricsConstants {
        public static final String CODEC = "android.media.mediacodec.codec";
        public static final String ENCODER = "android.media.mediacodec.encoder";
        public static final String HEIGHT = "android.media.mediacodec.height";
        public static final String MIME_TYPE = "android.media.mediacodec.mime";
        public static final String MODE = "android.media.mediacodec.mode";
        public static final String MODE_AUDIO = "audio";
        public static final String MODE_VIDEO = "video";
        public static final String ROTATION = "android.media.mediacodec.rotation";
        public static final String SECURE = "android.media.mediacodec.secure";
        public static final String WIDTH = "android.media.mediacodec.width";

        private MetricsConstants() {
        }
    }

    public static interface OnFrameRenderedListener {
        public void onFrameRendered(MediaCodec var1, long var2, long var4);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OutputBufferInfo {
    }

    static class PersistentSurface
    extends Surface {
        private long mPersistentObject;

        PersistentSurface() {
        }

        @Override
        public void release() {
            MediaCodec.native_releasePersistentInputSurface(this);
            super.release();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VideoScalingMode {
    }

}

