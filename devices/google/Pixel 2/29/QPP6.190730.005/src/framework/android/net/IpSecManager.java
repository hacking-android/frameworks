/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 */
package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.IIpSecService;
import android.net.IpSecSpiResponse;
import android.net.IpSecTransform;
import android.net.IpSecTunnelInterfaceResponse;
import android.net.IpSecUdpEncapResponse;
import android.net.LinkAddress;
import android.net.Network;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.AndroidException;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public final class IpSecManager {
    public static final int DIRECTION_IN = 0;
    public static final int DIRECTION_OUT = 1;
    public static final int INVALID_RESOURCE_ID = -1;
    public static final int INVALID_SECURITY_PARAMETER_INDEX = 0;
    private static final String TAG = "IpSecManager";
    private final Context mContext;
    private final IIpSecService mService;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public IpSecManager(Context context, IIpSecService iIpSecService) {
        this.mContext = context;
        this.mService = Preconditions.checkNotNull(iIpSecService, "missing service");
    }

    private static void maybeHandleServiceSpecificException(ServiceSpecificException serviceSpecificException) {
        if (serviceSpecificException.errorCode != OsConstants.EINVAL) {
            if (serviceSpecificException.errorCode != OsConstants.EAGAIN) {
                if (serviceSpecificException.errorCode != OsConstants.EOPNOTSUPP && serviceSpecificException.errorCode != OsConstants.EPROTONOSUPPORT) {
                    return;
                }
                throw new UnsupportedOperationException(serviceSpecificException);
            }
            throw new IllegalStateException(serviceSpecificException);
        }
        throw new IllegalArgumentException(serviceSpecificException);
    }

    static IOException rethrowCheckedExceptionFromServiceSpecificException(ServiceSpecificException serviceSpecificException) throws IOException {
        IpSecManager.maybeHandleServiceSpecificException(serviceSpecificException);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IpSec encountered errno=");
        stringBuilder.append(serviceSpecificException.errorCode);
        throw new ErrnoException(stringBuilder.toString(), serviceSpecificException.errorCode).rethrowAsIOException();
    }

    static RuntimeException rethrowUncheckedExceptionFromServiceSpecificException(ServiceSpecificException serviceSpecificException) {
        IpSecManager.maybeHandleServiceSpecificException(serviceSpecificException);
        throw new RuntimeException(serviceSpecificException);
    }

    public SecurityParameterIndex allocateSecurityParameterIndex(InetAddress object) throws ResourceUnavailableException {
        try {
            object = new SecurityParameterIndex(this.mService, (InetAddress)object, 0);
            return object;
        }
        catch (SpiUnavailableException spiUnavailableException) {
            throw new ResourceUnavailableException("No SPIs available");
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw IpSecManager.rethrowUncheckedExceptionFromServiceSpecificException(serviceSpecificException);
        }
    }

    public SecurityParameterIndex allocateSecurityParameterIndex(InetAddress object, int n) throws SpiUnavailableException, ResourceUnavailableException {
        if (n != 0) {
            try {
                object = new SecurityParameterIndex(this.mService, (InetAddress)object, n);
                return object;
            }
            catch (ServiceSpecificException serviceSpecificException) {
                throw IpSecManager.rethrowUncheckedExceptionFromServiceSpecificException(serviceSpecificException);
            }
        }
        throw new IllegalArgumentException("Requested SPI must be a valid (non-zero) SPI");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void applyTransportModeTransform(FileDescriptor object, int n, IpSecTransform ipSecTransform) throws IOException {
        object = ParcelFileDescriptor.dup((FileDescriptor)object);
        try {
            this.mService.applyTransportModeTransform((ParcelFileDescriptor)object, n, ipSecTransform.getResourceId());
            if (object == null) return;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object == null) throw throwable2;
                try {
                    IpSecManager.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
                }
            }
        }
        IpSecManager.$closeResource(null, (AutoCloseable)object);
    }

    public void applyTransportModeTransform(DatagramSocket datagramSocket, int n, IpSecTransform ipSecTransform) throws IOException {
        this.applyTransportModeTransform(datagramSocket.getFileDescriptor$(), n, ipSecTransform);
    }

    public void applyTransportModeTransform(Socket socket, int n, IpSecTransform ipSecTransform) throws IOException {
        socket.getSoLinger();
        this.applyTransportModeTransform(socket.getFileDescriptor$(), n, ipSecTransform);
    }

    @SystemApi
    public void applyTunnelModeTransform(IpSecTunnelInterface ipSecTunnelInterface, int n, IpSecTransform ipSecTransform) throws IOException {
        try {
            this.mService.applyTunnelModeTransform(ipSecTunnelInterface.getResourceId(), n, ipSecTransform.getResourceId(), this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
        }
    }

    @SystemApi
    public IpSecTunnelInterface createIpSecTunnelInterface(InetAddress object, InetAddress inetAddress, Network network) throws ResourceUnavailableException, IOException {
        try {
            object = new IpSecTunnelInterface(this.mContext, this.mService, (InetAddress)object, inetAddress, network);
            return object;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
        }
    }

    public UdpEncapsulationSocket openUdpEncapsulationSocket() throws IOException, ResourceUnavailableException {
        try {
            UdpEncapsulationSocket udpEncapsulationSocket = new UdpEncapsulationSocket(this.mService, 0);
            return udpEncapsulationSocket;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
        }
    }

    public UdpEncapsulationSocket openUdpEncapsulationSocket(int n) throws IOException, ResourceUnavailableException {
        if (n != 0) {
            try {
                UdpEncapsulationSocket udpEncapsulationSocket = new UdpEncapsulationSocket(this.mService, n);
                return udpEncapsulationSocket;
            }
            catch (ServiceSpecificException serviceSpecificException) {
                throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
            }
        }
        throw new IllegalArgumentException("Specified port must be a valid port number!");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void removeTransportModeTransforms(FileDescriptor fileDescriptor) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.dup(fileDescriptor);
        try {
            this.mService.removeTransportModeTransforms(parcelFileDescriptor);
            if (parcelFileDescriptor == null) return;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (parcelFileDescriptor == null) throw throwable2;
                try {
                    IpSecManager.$closeResource(throwable, parcelFileDescriptor);
                    throw throwable2;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
                }
            }
        }
        IpSecManager.$closeResource(null, parcelFileDescriptor);
    }

    public void removeTransportModeTransforms(DatagramSocket datagramSocket) throws IOException {
        this.removeTransportModeTransforms(datagramSocket.getFileDescriptor$());
    }

    public void removeTransportModeTransforms(Socket socket) throws IOException {
        socket.getSoLinger();
        this.removeTransportModeTransforms(socket.getFileDescriptor$());
    }

    public void removeTunnelModeTransform(Network network, IpSecTransform ipSecTransform) {
    }

    @SystemApi
    public static final class IpSecTunnelInterface
    implements AutoCloseable {
        private final CloseGuard mCloseGuard = CloseGuard.get();
        private String mInterfaceName;
        private final InetAddress mLocalAddress;
        private final String mOpPackageName;
        private final InetAddress mRemoteAddress;
        private int mResourceId = -1;
        private final IIpSecService mService;
        private final Network mUnderlyingNetwork;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private IpSecTunnelInterface(Context object, IIpSecService object2, InetAddress object3, InetAddress object4, Network network) throws ResourceUnavailableException, IOException {
            this.mOpPackageName = ((Context)object).getOpPackageName();
            this.mService = object2;
            this.mLocalAddress = object3;
            this.mRemoteAddress = object4;
            this.mUnderlyingNetwork = network;
            try {
                object = this.mService;
                object2 = ((InetAddress)object3).getHostAddress();
                object3 = ((InetAddress)object4).getHostAddress();
                object4 = new Binder();
                object = object.createTunnelInterface((String)object2, (String)object3, network, (IBinder)object4, this.mOpPackageName);
                int n = ((IpSecTunnelInterfaceResponse)object).status;
                if (n != 0) {
                    if (n != 1) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Unknown status returned by IpSecService: ");
                        ((StringBuilder)object3).append(((IpSecTunnelInterfaceResponse)object).status);
                        object2 = new RuntimeException(((StringBuilder)object3).toString());
                        throw object2;
                    }
                    object = new ResourceUnavailableException("No more tunnel interfaces may be allocated by this requester.");
                    throw object;
                }
                this.mResourceId = ((IpSecTunnelInterfaceResponse)object).resourceId;
                this.mInterfaceName = ((IpSecTunnelInterfaceResponse)object).interfaceName;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mCloseGuard.open("constructor");
        }

        @SystemApi
        public void addAddress(InetAddress inetAddress, int n) throws IOException {
            try {
                IIpSecService iIpSecService = this.mService;
                int n2 = this.mResourceId;
                LinkAddress linkAddress = new LinkAddress(inetAddress, n);
                iIpSecService.addAddressToTunnelInterface(n2, linkAddress, this.mOpPackageName);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (ServiceSpecificException serviceSpecificException) {
                throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
            }
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
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
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

        public String getInterfaceName() {
            return this.mInterfaceName;
        }

        @VisibleForTesting
        public int getResourceId() {
            return this.mResourceId;
        }

        @SystemApi
        public void removeAddress(InetAddress inetAddress, int n) throws IOException {
            try {
                IIpSecService iIpSecService = this.mService;
                int n2 = this.mResourceId;
                LinkAddress linkAddress = new LinkAddress(inetAddress, n);
                iIpSecService.removeAddressFromTunnelInterface(n2, linkAddress, this.mOpPackageName);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (ServiceSpecificException serviceSpecificException) {
                throw IpSecManager.rethrowCheckedExceptionFromServiceSpecificException(serviceSpecificException);
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IpSecTunnelInterface{ifname=");
            stringBuilder.append(this.mInterfaceName);
            stringBuilder.append(",resourceId=");
            stringBuilder.append(this.mResourceId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PolicyDirection {
    }

    public static final class ResourceUnavailableException
    extends AndroidException {
        ResourceUnavailableException(String string2) {
            super(string2);
        }
    }

    public static final class SecurityParameterIndex
    implements AutoCloseable {
        private final CloseGuard mCloseGuard;
        private final InetAddress mDestinationAddress;
        private int mResourceId;
        private final IIpSecService mService;
        private int mSpi;

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private SecurityParameterIndex(IIpSecService var1_1, InetAddress var2_3, int var3_4) throws ResourceUnavailableException, SpiUnavailableException {
            block6 : {
                super();
                this.mCloseGuard = CloseGuard.get();
                this.mSpi = 0;
                this.mResourceId = -1;
                this.mService = var1_1;
                this.mDestinationAddress = var2_3;
                try {
                    var1_1 = this.mService;
                    var2_3 = var2_3.getHostAddress();
                    var4_5 = new Binder();
                    var1_1 = var1_1.allocateSecurityParameterIndex((String)var2_3, var3_4, var4_5);
                    if (var1_1 == null) ** GOTO lbl53
                    var5_6 = var1_1.status;
                    if (var5_6 != 0) {
                        if (var5_6 == 1) {
                            var1_1 = new ResourceUnavailableException("No more SPIs may be allocated by this requester.");
                            throw var1_1;
                        }
                        if (var5_6 != 2) {
                            var1_1 = new StringBuilder();
                            var1_1.append("Unknown status returned by IpSecService: ");
                            var1_1.append(var5_6);
                            var2_3 = new RuntimeException(var1_1.toString());
                            throw var2_3;
                        }
                        var1_1 = new SpiUnavailableException("Requested SPI is unavailable", var3_4);
                        throw var1_1;
                    }
                    this.mSpi = var1_1.spi;
                    this.mResourceId = var1_1.resourceId;
                    if (this.mSpi == 0) ** GOTO lbl46
                    var3_4 = this.mResourceId;
                    if (var3_4 == -1) break block6;
                }
                catch (RemoteException var1_2) {
                    throw var1_2.rethrowFromSystemServer();
                }
                this.mCloseGuard.open("open");
                return;
            }
            var2_3 = new StringBuilder();
            var2_3.append("Invalid Resource ID returned by IpSecService: ");
            var2_3.append(var5_6);
            var1_1 = new RuntimeException(var2_3.toString());
            throw var1_1;
lbl46: // 1 sources:
            var1_1 = new StringBuilder();
            var1_1.append("Invalid SPI returned by IpSecService: ");
            var1_1.append(var5_6);
            var2_3 = new RuntimeException(var1_1.toString());
            throw var2_3;
lbl53: // 1 sources:
            var1_1 = new NullPointerException("Received null response from IpSecService");
            throw var1_1;
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
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
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

        @VisibleForTesting
        public int getResourceId() {
            return this.mResourceId;
        }

        public int getSpi() {
            return this.mSpi;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SecurityParameterIndex{spi=");
            stringBuilder.append(this.mSpi);
            stringBuilder.append(",resourceId=");
            stringBuilder.append(this.mResourceId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static final class SpiUnavailableException
    extends AndroidException {
        private final int mSpi;

        SpiUnavailableException(String string2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" (spi: ");
            stringBuilder.append(n);
            stringBuilder.append(")");
            super(stringBuilder.toString());
            this.mSpi = n;
        }

        public int getSpi() {
            return this.mSpi;
        }
    }

    public static interface Status {
        public static final int OK = 0;
        public static final int RESOURCE_UNAVAILABLE = 1;
        public static final int SPI_UNAVAILABLE = 2;
    }

    public static final class UdpEncapsulationSocket
    implements AutoCloseable {
        private final CloseGuard mCloseGuard = CloseGuard.get();
        private final ParcelFileDescriptor mPfd;
        private final int mPort;
        private int mResourceId = -1;
        private final IIpSecService mService;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private UdpEncapsulationSocket(IIpSecService object, int n) throws ResourceUnavailableException, IOException {
            this.mService = object;
            try {
                Object object2 = this.mService;
                object = new Binder();
                object = object2.openUdpEncapsulationSocket(n, (IBinder)object);
                n = ((IpSecUdpEncapResponse)object).status;
                if (n != 0) {
                    if (n != 1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown status returned by IpSecService: ");
                        stringBuilder.append(((IpSecUdpEncapResponse)object).status);
                        object2 = new RuntimeException(stringBuilder.toString());
                        throw object2;
                    }
                    object = new ResourceUnavailableException("No more Sockets may be allocated by this requester.");
                    throw object;
                }
                this.mResourceId = ((IpSecUdpEncapResponse)object).resourceId;
                this.mPort = ((IpSecUdpEncapResponse)object).port;
                this.mPfd = ((IpSecUdpEncapResponse)object).fileDescriptor;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mCloseGuard.open("constructor");
        }

        /*
         * Exception decompiling
         */
        @Override
        public void close() throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 8[UNCONDITIONALDOLOOP]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
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

        public FileDescriptor getFileDescriptor() {
            ParcelFileDescriptor parcelFileDescriptor = this.mPfd;
            if (parcelFileDescriptor == null) {
                return null;
            }
            return parcelFileDescriptor.getFileDescriptor();
        }

        public int getPort() {
            return this.mPort;
        }

        @VisibleForTesting
        public int getResourceId() {
            return this.mResourceId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UdpEncapsulationSocket{port=");
            stringBuilder.append(this.mPort);
            stringBuilder.append(",resourceId=");
            stringBuilder.append(this.mResourceId);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

