/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.IIpSecService;
import android.net.IpSecAlgorithm;
import android.net.IpSecConfig;
import android.net.IpSecManager;
import android.net.IpSecTransformResponse;
import android.net.Network;
import android.net.NetworkUtils;
import android.net._$$Lambda$IpSecTransform$1$Rc3lbWP51o1kJRHwkpVUEV1G_d8;
import android.net._$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE;
import android.net._$$Lambda$IpSecTransform$1$zl9bpxiE2uj_QuCOkuJ091wPuwo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;

public final class IpSecTransform
implements AutoCloseable {
    public static final int ENCAP_ESPINUDP = 2;
    public static final int ENCAP_ESPINUDP_NON_IKE = 1;
    public static final int ENCAP_NONE = 0;
    public static final int MODE_TRANSPORT = 0;
    public static final int MODE_TUNNEL = 1;
    private static final String TAG = "IpSecTransform";
    private Handler mCallbackHandler;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final IpSecConfig mConfig;
    private final Context mContext;
    private ConnectivityManager.PacketKeepalive mKeepalive;
    private final ConnectivityManager.PacketKeepaliveCallback mKeepaliveCallback = new ConnectivityManager.PacketKeepaliveCallback(){

        public /* synthetic */ void lambda$onError$2$IpSecTransform$1(int n) {
            IpSecTransform.this.mUserKeepaliveCallback.onError(n);
        }

        public /* synthetic */ void lambda$onStarted$0$IpSecTransform$1() {
            IpSecTransform.this.mUserKeepaliveCallback.onStarted();
        }

        public /* synthetic */ void lambda$onStopped$1$IpSecTransform$1() {
            IpSecTransform.this.mUserKeepaliveCallback.onStopped();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onError(int n) {
            synchronized (this) {
                IpSecTransform.this.mKeepalive = null;
                Handler handler = IpSecTransform.this.mCallbackHandler;
                _$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE _$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE = new _$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE(this, n);
                handler.post(_$$Lambda$IpSecTransform$1$_ae2VrMToKvertNlEIezU0bdvXE);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onStarted() {
            synchronized (this) {
                Handler handler = IpSecTransform.this.mCallbackHandler;
                _$$Lambda$IpSecTransform$1$zl9bpxiE2uj_QuCOkuJ091wPuwo _$$Lambda$IpSecTransform$1$zl9bpxiE2uj_QuCOkuJ091wPuwo = new _$$Lambda$IpSecTransform$1$zl9bpxiE2uj_QuCOkuJ091wPuwo(this);
                handler.post(_$$Lambda$IpSecTransform$1$zl9bpxiE2uj_QuCOkuJ091wPuwo);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onStopped() {
            synchronized (this) {
                IpSecTransform.this.mKeepalive = null;
                Handler handler = IpSecTransform.this.mCallbackHandler;
                _$$Lambda$IpSecTransform$1$Rc3lbWP51o1kJRHwkpVUEV1G_d8 _$$Lambda$IpSecTransform$1$Rc3lbWP51o1kJRHwkpVUEV1G_d8 = new _$$Lambda$IpSecTransform$1$Rc3lbWP51o1kJRHwkpVUEV1G_d8(this);
                handler.post(_$$Lambda$IpSecTransform$1$Rc3lbWP51o1kJRHwkpVUEV1G_d8);
                return;
            }
        }
    };
    private int mResourceId;
    private NattKeepaliveCallback mUserKeepaliveCallback;

    @VisibleForTesting
    public IpSecTransform(Context context, IpSecConfig ipSecConfig) {
        this.mContext = context;
        this.mConfig = new IpSecConfig(ipSecConfig);
        this.mResourceId = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IpSecTransform activate() throws IOException, IpSecManager.ResourceUnavailableException, IpSecManager.SpiUnavailableException {
        synchronized (this) {
            try {
                try {
                    Object object = this.getIpSecService();
                    IpSecConfig ipSecConfig = this.mConfig;
                    Binder binder = new Binder();
                    object = object.createTransform(ipSecConfig, binder, this.mContext.getOpPackageName());
                    this.checkResultStatus(((IpSecTransformResponse)object).status);
                    this.mResourceId = ((IpSecTransformResponse)object).resourceId;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Added Transform with Id ");
                    ((StringBuilder)object).append(this.mResourceId);
                    Log.d(TAG, ((StringBuilder)object).toString());
                    this.mCloseGuard.open("build");
                    return this;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowAsRuntimeException();
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    throw IpSecManager.rethrowUncheckedExceptionFromServiceSpecificException(serviceSpecificException);
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private void checkResultStatus(int n) throws IOException, IpSecManager.ResourceUnavailableException, IpSecManager.SpiUnavailableException {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    Log.wtf(TAG, "Attempting to use an SPI that was somehow not reserved");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to Create a Transform with status code ");
                stringBuilder.append(n);
                throw new IllegalStateException(stringBuilder.toString());
            }
            throw new IpSecManager.ResourceUnavailableException("Failed to allocate a new IpSecTransform");
        }
    }

    @VisibleForTesting
    public static boolean equals(IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2) {
        boolean bl = true;
        boolean bl2 = true;
        if (ipSecTransform != null && ipSecTransform2 != null) {
            if (!IpSecConfig.equals(ipSecTransform.getConfig(), ipSecTransform2.getConfig()) || ipSecTransform.mResourceId != ipSecTransform2.mResourceId) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = ipSecTransform == ipSecTransform2 ? bl : false;
        return bl2;
    }

    private IIpSecService getIpSecService() {
        IBinder iBinder = ServiceManager.getService("ipsec");
        if (iBinder != null) {
            return IIpSecService.Stub.asInterface(iBinder);
        }
        throw new RemoteException("Failed to connect to IpSecService").rethrowAsRuntimeException();
    }

    /*
     * Exception decompiling
     */
    @Override
    public void close() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    protected void finalize() throws Throwable {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    IpSecConfig getConfig() {
        return this.mConfig;
    }

    @VisibleForTesting
    public int getResourceId() {
        return this.mResourceId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startNattKeepalive(NattKeepaliveCallback object, int n, Handler handler) throws IOException {
        Preconditions.checkNotNull(object);
        if (n >= 20 && n <= 3600) {
            Preconditions.checkNotNull(handler);
            if (this.mResourceId == -1) {
                throw new IllegalStateException("Packet keepalive cannot be started for an inactive transform");
            }
            ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback = this.mKeepaliveCallback;
            synchronized (packetKeepaliveCallback) {
                if (this.mKeepaliveCallback == null) {
                    this.mUserKeepaliveCallback = object;
                    this.mKeepalive = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).startNattKeepalive(this.mConfig.getNetwork(), n, this.mKeepaliveCallback, NetworkUtils.numericToInetAddress(this.mConfig.getSourceAddress()), 4500, NetworkUtils.numericToInetAddress(this.mConfig.getDestinationAddress()));
                    this.mCallbackHandler = handler;
                    return;
                }
                object = new IllegalStateException("Keepalive already active");
                throw object;
            }
        }
        throw new IllegalArgumentException("Invalid NAT-T keepalive interval");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopNattKeepalive() {
        ConnectivityManager.PacketKeepaliveCallback packetKeepaliveCallback = this.mKeepaliveCallback;
        synchronized (packetKeepaliveCallback) {
            if (this.mKeepalive == null) {
                Log.e(TAG, "No active keepalive to stop");
                return;
            }
            this.mKeepalive.stop();
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IpSecTransform{resourceId=");
        stringBuilder.append(this.mResourceId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static class Builder {
        private IpSecConfig mConfig;
        private Context mContext;

        public Builder(Context context) {
            Preconditions.checkNotNull(context);
            this.mContext = context;
            this.mConfig = new IpSecConfig();
        }

        public IpSecTransform buildTransportModeTransform(InetAddress inetAddress, IpSecManager.SecurityParameterIndex securityParameterIndex) throws IpSecManager.ResourceUnavailableException, IpSecManager.SpiUnavailableException, IOException {
            Preconditions.checkNotNull(inetAddress);
            Preconditions.checkNotNull(securityParameterIndex);
            if (securityParameterIndex.getResourceId() != -1) {
                this.mConfig.setMode(0);
                this.mConfig.setSourceAddress(inetAddress.getHostAddress());
                this.mConfig.setSpiResourceId(securityParameterIndex.getResourceId());
                return new IpSecTransform(this.mContext, this.mConfig).activate();
            }
            throw new IllegalArgumentException("Invalid SecurityParameterIndex");
        }

        @SystemApi
        public IpSecTransform buildTunnelModeTransform(InetAddress inetAddress, IpSecManager.SecurityParameterIndex securityParameterIndex) throws IpSecManager.ResourceUnavailableException, IpSecManager.SpiUnavailableException, IOException {
            Preconditions.checkNotNull(inetAddress);
            Preconditions.checkNotNull(securityParameterIndex);
            if (securityParameterIndex.getResourceId() != -1) {
                this.mConfig.setMode(1);
                this.mConfig.setSourceAddress(inetAddress.getHostAddress());
                this.mConfig.setSpiResourceId(securityParameterIndex.getResourceId());
                return new IpSecTransform(this.mContext, this.mConfig).activate();
            }
            throw new IllegalArgumentException("Invalid SecurityParameterIndex");
        }

        public Builder setAuthenticatedEncryption(IpSecAlgorithm ipSecAlgorithm) {
            Preconditions.checkNotNull(ipSecAlgorithm);
            this.mConfig.setAuthenticatedEncryption(ipSecAlgorithm);
            return this;
        }

        public Builder setAuthentication(IpSecAlgorithm ipSecAlgorithm) {
            Preconditions.checkNotNull(ipSecAlgorithm);
            this.mConfig.setAuthentication(ipSecAlgorithm);
            return this;
        }

        public Builder setEncryption(IpSecAlgorithm ipSecAlgorithm) {
            Preconditions.checkNotNull(ipSecAlgorithm);
            this.mConfig.setEncryption(ipSecAlgorithm);
            return this;
        }

        public Builder setIpv4Encapsulation(IpSecManager.UdpEncapsulationSocket udpEncapsulationSocket, int n) {
            Preconditions.checkNotNull(udpEncapsulationSocket);
            this.mConfig.setEncapType(2);
            if (udpEncapsulationSocket.getResourceId() != -1) {
                this.mConfig.setEncapSocketResourceId(udpEncapsulationSocket.getResourceId());
                this.mConfig.setEncapRemotePort(n);
                return this;
            }
            throw new IllegalArgumentException("Invalid UdpEncapsulationSocket");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EncapType {
    }

    public static class NattKeepaliveCallback {
        public static final int ERROR_HARDWARE_ERROR = 3;
        public static final int ERROR_HARDWARE_UNSUPPORTED = 2;
        public static final int ERROR_INVALID_NETWORK = 1;

        public void onError(int n) {
        }

        public void onStarted() {
        }

        public void onStopped() {
        }
    }

}

