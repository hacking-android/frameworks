/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.IConnectivityManager;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkUtils;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.system.OsConstants;
import com.android.internal.net.VpnConfig;
import java.io.FileDescriptor;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VpnService
extends Service {
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final String SERVICE_META_DATA_SUPPORTS_ALWAYS_ON = "android.net.VpnService.SUPPORTS_ALWAYS_ON";

    private static void check(InetAddress inetAddress, int n) {
        block5 : {
            block8 : {
                block9 : {
                    block7 : {
                        block6 : {
                            if (inetAddress.isLoopbackAddress()) break block5;
                            if (!(inetAddress instanceof Inet4Address)) break block6;
                            if (n < 0 || n > 32) {
                                throw new IllegalArgumentException("Bad prefixLength");
                            }
                            break block7;
                        }
                        if (!(inetAddress instanceof Inet6Address)) break block8;
                        if (n < 0 || n > 128) break block9;
                    }
                    return;
                }
                throw new IllegalArgumentException("Bad prefixLength");
            }
            throw new IllegalArgumentException("Unsupported family");
        }
        throw new IllegalArgumentException("Bad address");
    }

    private static IConnectivityManager getService() {
        return IConnectivityManager.Stub.asInterface(ServiceManager.getService("connectivity"));
    }

    public static Intent prepare(Context context) {
        try {
            boolean bl = VpnService.getService().prepareVpn(context.getPackageName(), null, context.getUserId());
            if (bl) {
                return null;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return VpnConfig.getIntentForConfirmation();
    }

    @SystemApi
    public static void prepareAndAuthorize(Context context) {
        IConnectivityManager iConnectivityManager = VpnService.getService();
        String string2 = context.getPackageName();
        try {
            int n = context.getUserId();
            if (!iConnectivityManager.prepareVpn(string2, null, n)) {
                iConnectivityManager.prepareVpn(null, string2, n);
            }
            iConnectivityManager.setVpnPackageAuthorization(string2, n, true);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public boolean addAddress(InetAddress inetAddress, int n) {
        VpnService.check(inetAddress, n);
        try {
            boolean bl = VpnService.getService().addVpnAddress(inetAddress.getHostAddress(), n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(remoteException);
        }
    }

    public final boolean isAlwaysOn() {
        try {
            boolean bl = VpnService.getService().isCallerCurrentAlwaysOnVpnApp();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final boolean isLockdownEnabled() {
        try {
            boolean bl = VpnService.getService().isCallerCurrentAlwaysOnVpnLockdownApp();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null && SERVICE_INTERFACE.equals(intent.getAction())) {
            return new Callback();
        }
        return null;
    }

    public void onRevoke() {
        this.stopSelf();
    }

    public boolean protect(int n) {
        return NetworkUtils.protectFromVpn(n);
    }

    public boolean protect(DatagramSocket datagramSocket) {
        return this.protect(datagramSocket.getFileDescriptor$().getInt$());
    }

    public boolean protect(Socket socket) {
        return this.protect(socket.getFileDescriptor$().getInt$());
    }

    public boolean removeAddress(InetAddress inetAddress, int n) {
        VpnService.check(inetAddress, n);
        try {
            boolean bl = VpnService.getService().removeVpnAddress(inetAddress.getHostAddress(), n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(remoteException);
        }
    }

    public boolean setUnderlyingNetworks(Network[] arrnetwork) {
        try {
            boolean bl = VpnService.getService().setUnderlyingNetworksForVpn(arrnetwork);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(remoteException);
        }
    }

    public class Builder {
        @UnsupportedAppUsage
        private final List<LinkAddress> mAddresses = new ArrayList<LinkAddress>();
        private final VpnConfig mConfig = new VpnConfig();
        @UnsupportedAppUsage
        private final List<RouteInfo> mRoutes = new ArrayList<RouteInfo>();

        public Builder() {
            this.mConfig.user = VpnService.this.getClass().getName();
        }

        private void verifyApp(String string2) throws PackageManager.NameNotFoundException {
            IPackageManager iPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            try {
                iPackageManager.getApplicationInfo(string2, 0, UserHandle.getCallingUserId());
                return;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(remoteException);
            }
        }

        public Builder addAddress(String string2, int n) {
            return this.addAddress(InetAddress.parseNumericAddress((String)string2), n);
        }

        public Builder addAddress(InetAddress inetAddress, int n) {
            VpnService.check(inetAddress, n);
            if (!inetAddress.isAnyLocalAddress()) {
                this.mAddresses.add(new LinkAddress(inetAddress, n));
                this.mConfig.updateAllowedFamilies(inetAddress);
                return this;
            }
            throw new IllegalArgumentException("Bad address");
        }

        public Builder addAllowedApplication(String string2) throws PackageManager.NameNotFoundException {
            if (this.mConfig.disallowedApplications == null) {
                this.verifyApp(string2);
                if (this.mConfig.allowedApplications == null) {
                    this.mConfig.allowedApplications = new ArrayList<String>();
                }
                this.mConfig.allowedApplications.add(string2);
                return this;
            }
            throw new UnsupportedOperationException("addDisallowedApplication already called");
        }

        public Builder addDisallowedApplication(String string2) throws PackageManager.NameNotFoundException {
            if (this.mConfig.allowedApplications == null) {
                this.verifyApp(string2);
                if (this.mConfig.disallowedApplications == null) {
                    this.mConfig.disallowedApplications = new ArrayList<String>();
                }
                this.mConfig.disallowedApplications.add(string2);
                return this;
            }
            throw new UnsupportedOperationException("addAllowedApplication already called");
        }

        public Builder addDnsServer(String string2) {
            return this.addDnsServer(InetAddress.parseNumericAddress((String)string2));
        }

        public Builder addDnsServer(InetAddress inetAddress) {
            if (!inetAddress.isLoopbackAddress() && !inetAddress.isAnyLocalAddress()) {
                if (this.mConfig.dnsServers == null) {
                    this.mConfig.dnsServers = new ArrayList<String>();
                }
                this.mConfig.dnsServers.add(inetAddress.getHostAddress());
                return this;
            }
            throw new IllegalArgumentException("Bad address");
        }

        public Builder addRoute(String string2, int n) {
            return this.addRoute(InetAddress.parseNumericAddress((String)string2), n);
        }

        public Builder addRoute(InetAddress inetAddress, int n) {
            int n2;
            VpnService.check(inetAddress, n);
            byte[] arrby = inetAddress.getAddress();
            if (n2 < arrby.length) {
                arrby[n2] = (byte)(arrby[n2] << n % 8);
                for (n2 = n / 8; n2 < arrby.length; ++n2) {
                    if (arrby[n2] == 0) {
                        continue;
                    }
                    throw new IllegalArgumentException("Bad address");
                }
            }
            this.mRoutes.add(new RouteInfo(new IpPrefix(inetAddress, n), null));
            this.mConfig.updateAllowedFamilies(inetAddress);
            return this;
        }

        public Builder addSearchDomain(String string2) {
            if (this.mConfig.searchDomains == null) {
                this.mConfig.searchDomains = new ArrayList<String>();
            }
            this.mConfig.searchDomains.add(string2);
            return this;
        }

        public Builder allowBypass() {
            this.mConfig.allowBypass = true;
            return this;
        }

        public Builder allowFamily(int n) {
            block4 : {
                block3 : {
                    block2 : {
                        if (n != OsConstants.AF_INET) break block2;
                        this.mConfig.allowIPv4 = true;
                        break block3;
                    }
                    if (n != OsConstants.AF_INET6) break block4;
                    this.mConfig.allowIPv6 = true;
                }
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append(" is neither ");
            stringBuilder.append(OsConstants.AF_INET);
            stringBuilder.append(" nor ");
            stringBuilder.append(OsConstants.AF_INET6);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public ParcelFileDescriptor establish() {
            Parcelable parcelable = this.mConfig;
            parcelable.addresses = this.mAddresses;
            parcelable.routes = this.mRoutes;
            try {
                parcelable = VpnService.getService().establishVpn(this.mConfig);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(remoteException);
            }
        }

        public Builder setBlocking(boolean bl) {
            this.mConfig.blocking = bl;
            return this;
        }

        public Builder setConfigureIntent(PendingIntent pendingIntent) {
            this.mConfig.configureIntent = pendingIntent;
            return this;
        }

        public Builder setHttpProxy(ProxyInfo proxyInfo) {
            this.mConfig.proxyInfo = proxyInfo;
            return this;
        }

        public Builder setMetered(boolean bl) {
            this.mConfig.isMetered = bl;
            return this;
        }

        public Builder setMtu(int n) {
            if (n > 0) {
                this.mConfig.mtu = n;
                return this;
            }
            throw new IllegalArgumentException("Bad mtu");
        }

        public Builder setSession(String string2) {
            this.mConfig.session = string2;
            return this;
        }

        public Builder setUnderlyingNetworks(Network[] object) {
            VpnConfig vpnConfig = this.mConfig;
            object = object != null ? (Network[])object.clone() : null;
            vpnConfig.underlyingNetworks = object;
            return this;
        }
    }

    private class Callback
    extends Binder {
        private Callback() {
        }

        @Override
        protected boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) {
            if (n == 16777215) {
                VpnService.this.onRevoke();
                return true;
            }
            return false;
        }
    }

}

