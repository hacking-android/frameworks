/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.ProxyInfo;
import android.net.RouteInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public final class LinkProperties
implements Parcelable {
    public static final Parcelable.Creator<LinkProperties> CREATOR = new Parcelable.Creator<LinkProperties>(){

        @Override
        public LinkProperties createFromParcel(Parcel object) {
            int n;
            LinkProperties linkProperties = new LinkProperties();
            Object object2 = ((Parcel)object).readString();
            if (object2 != null) {
                linkProperties.setInterfaceName((String)object2);
            }
            int n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                linkProperties.addLinkAddress((LinkAddress)((Parcel)object).readParcelable(null));
            }
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                try {
                    linkProperties.addDnsServer(InetAddress.getByAddress(((Parcel)object).createByteArray()));
                    continue;
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                try {
                    linkProperties.addValidatedPrivateDnsServer(InetAddress.getByAddress(((Parcel)object).createByteArray()));
                    continue;
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            linkProperties.setUsePrivateDns(((Parcel)object).readBoolean());
            linkProperties.setPrivateDnsServerName(((Parcel)object).readString());
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                try {
                    linkProperties.addPcscfServer(InetAddress.getByAddress(((Parcel)object).createByteArray()));
                    continue;
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            linkProperties.setDomains(((Parcel)object).readString());
            linkProperties.setMtu(((Parcel)object).readInt());
            linkProperties.setTcpBufferSizes(((Parcel)object).readString());
            n2 = ((Parcel)object).readInt();
            for (n = 0; n < n2; ++n) {
                linkProperties.addRoute((RouteInfo)((Parcel)object).readParcelable(null));
            }
            if (((Parcel)object).readByte() == 1) {
                linkProperties.setHttpProxy((ProxyInfo)((Parcel)object).readParcelable(null));
            }
            linkProperties.setNat64Prefix((IpPrefix)((Parcel)object).readParcelable(null));
            object2 = new ArrayList();
            ((Parcel)object).readList((List)object2, LinkProperties.class.getClassLoader());
            object = ((ArrayList)object2).iterator();
            while (object.hasNext()) {
                linkProperties.addStackedLink((LinkProperties)object.next());
            }
            return linkProperties;
        }

        public LinkProperties[] newArray(int n) {
            return new LinkProperties[n];
        }
    };
    private static final int MAX_MTU = 10000;
    private static final int MIN_MTU = 68;
    private static final int MIN_MTU_V6 = 1280;
    private final ArrayList<InetAddress> mDnses = new ArrayList();
    private String mDomains;
    private ProxyInfo mHttpProxy;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mIfaceName;
    private final ArrayList<LinkAddress> mLinkAddresses = new ArrayList();
    private int mMtu;
    private IpPrefix mNat64Prefix;
    private final ArrayList<InetAddress> mPcscfs = new ArrayList();
    private String mPrivateDnsServerName;
    private ArrayList<RouteInfo> mRoutes = new ArrayList();
    private Hashtable<String, LinkProperties> mStackedLinks = new Hashtable();
    private String mTcpBufferSizes;
    private boolean mUsePrivateDns;
    private final ArrayList<InetAddress> mValidatedPrivateDnses = new ArrayList();

    public LinkProperties() {
    }

    @SystemApi
    public LinkProperties(LinkProperties linkProperties) {
        if (linkProperties != null) {
            this.mIfaceName = linkProperties.mIfaceName;
            this.mLinkAddresses.addAll(linkProperties.mLinkAddresses);
            this.mDnses.addAll(linkProperties.mDnses);
            this.mValidatedPrivateDnses.addAll(linkProperties.mValidatedPrivateDnses);
            this.mUsePrivateDns = linkProperties.mUsePrivateDns;
            this.mPrivateDnsServerName = linkProperties.mPrivateDnsServerName;
            this.mPcscfs.addAll(linkProperties.mPcscfs);
            this.mDomains = linkProperties.mDomains;
            this.mRoutes.addAll(linkProperties.mRoutes);
            Object object = linkProperties.mHttpProxy;
            object = object == null ? null : new ProxyInfo((ProxyInfo)object);
            this.mHttpProxy = object;
            object = linkProperties.mStackedLinks.values().iterator();
            while (object.hasNext()) {
                this.addStackedLink((LinkProperties)object.next());
            }
            this.setMtu(linkProperties.mMtu);
            this.mTcpBufferSizes = linkProperties.mTcpBufferSizes;
            this.mNat64Prefix = linkProperties.mNat64Prefix;
        }
    }

    @UnsupportedAppUsage
    public static ProvisioningChange compareProvisioning(LinkProperties linkProperties, LinkProperties linkProperties2) {
        if (linkProperties.isProvisioned() && linkProperties2.isProvisioned()) {
            if (linkProperties.isIpv4Provisioned() && !linkProperties2.isIpv4Provisioned() || linkProperties.isIpv6Provisioned() && !linkProperties2.isIpv6Provisioned()) {
                return ProvisioningChange.LOST_PROVISIONING;
            }
            return ProvisioningChange.STILL_PROVISIONED;
        }
        if (linkProperties.isProvisioned() && !linkProperties2.isProvisioned()) {
            return ProvisioningChange.LOST_PROVISIONING;
        }
        if (!linkProperties.isProvisioned() && linkProperties2.isProvisioned()) {
            return ProvisioningChange.GAINED_PROVISIONING;
        }
        return ProvisioningChange.STILL_NOT_PROVISIONED;
    }

    private int findLinkAddressIndex(LinkAddress linkAddress) {
        for (int i = 0; i < this.mLinkAddresses.size(); ++i) {
            if (!this.mLinkAddresses.get(i).isSameAddressAs(linkAddress)) continue;
            return i;
        }
        return -1;
    }

    private boolean hasIpv4AddressOnInterface(String string2) {
        boolean bl = Objects.equals(string2, this.mIfaceName) && this.hasIpv4Address() || string2 != null && this.mStackedLinks.containsKey(string2) && this.mStackedLinks.get(string2).hasIpv4Address();
        return bl;
    }

    public static boolean isValidMtu(int n, boolean bl) {
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl = n >= 1280 && n <= 10000 ? bl3 : false;
            return bl;
        }
        bl = n >= 68 && n <= 10000 ? bl2 : false;
        return bl;
    }

    private RouteInfo routeWithInterface(RouteInfo routeInfo) {
        return new RouteInfo(routeInfo.getDestination(), routeInfo.getGateway(), this.mIfaceName, routeInfo.getType());
    }

    @SystemApi
    public boolean addDnsServer(InetAddress inetAddress) {
        if (inetAddress != null && !this.mDnses.contains(inetAddress)) {
            this.mDnses.add(inetAddress);
            return true;
        }
        return false;
    }

    @SystemApi
    public boolean addLinkAddress(LinkAddress linkAddress) {
        if (linkAddress == null) {
            return false;
        }
        int n = this.findLinkAddressIndex(linkAddress);
        if (n < 0) {
            this.mLinkAddresses.add(linkAddress);
            return true;
        }
        if (this.mLinkAddresses.get(n).equals(linkAddress)) {
            return false;
        }
        this.mLinkAddresses.set(n, linkAddress);
        return true;
    }

    public boolean addPcscfServer(InetAddress inetAddress) {
        if (inetAddress != null && !this.mPcscfs.contains(inetAddress)) {
            this.mPcscfs.add(inetAddress);
            return true;
        }
        return false;
    }

    public boolean addRoute(RouteInfo object) {
        String string2 = ((RouteInfo)object).getInterface();
        if (string2 != null && !string2.equals(this.mIfaceName)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Route added with non-matching interface: ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" vs. ");
            ((StringBuilder)object).append(this.mIfaceName);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (!this.mRoutes.contains(object = this.routeWithInterface((RouteInfo)object))) {
            this.mRoutes.add((RouteInfo)object);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean addStackedLink(LinkProperties linkProperties) {
        if (linkProperties.getInterfaceName() != null) {
            this.mStackedLinks.put(linkProperties.getInterfaceName(), linkProperties);
            return true;
        }
        return false;
    }

    public boolean addValidatedPrivateDnsServer(InetAddress inetAddress) {
        if (inetAddress != null && !this.mValidatedPrivateDnses.contains(inetAddress)) {
            this.mValidatedPrivateDnses.add(inetAddress);
            return true;
        }
        return false;
    }

    public void clear() {
        this.mIfaceName = null;
        this.mLinkAddresses.clear();
        this.mDnses.clear();
        this.mUsePrivateDns = false;
        this.mPrivateDnsServerName = null;
        this.mPcscfs.clear();
        this.mDomains = null;
        this.mRoutes.clear();
        this.mHttpProxy = null;
        this.mStackedLinks.clear();
        this.mMtu = 0;
        this.mTcpBufferSizes = null;
        this.mNat64Prefix = null;
    }

    public CompareResult<LinkAddress> compareAddresses(LinkProperties list) {
        ArrayList<LinkAddress> arrayList = this.mLinkAddresses;
        list = list != null ? ((LinkProperties)((Object)list)).getLinkAddresses() : null;
        return new CompareResult<LinkAddress>(arrayList, list);
    }

    public CompareResult<String> compareAllInterfaceNames(LinkProperties list) {
        List<String> list2 = this.getAllInterfaceNames();
        list = list != null ? ((LinkProperties)((Object)list)).getAllInterfaceNames() : null;
        return new CompareResult<String>(list2, list);
    }

    public CompareResult<RouteInfo> compareAllRoutes(LinkProperties list) {
        List<RouteInfo> list2 = this.getAllRoutes();
        list = list != null ? ((LinkProperties)((Object)list)).getAllRoutes() : null;
        return new CompareResult<RouteInfo>(list2, list);
    }

    public CompareResult<InetAddress> compareDnses(LinkProperties list) {
        ArrayList<InetAddress> arrayList = this.mDnses;
        list = list != null ? ((LinkProperties)((Object)list)).getDnsServers() : null;
        return new CompareResult<InetAddress>(arrayList, list);
    }

    public CompareResult<InetAddress> compareValidatedPrivateDnses(LinkProperties list) {
        ArrayList<InetAddress> arrayList = this.mValidatedPrivateDnses;
        list = list != null ? ((LinkProperties)((Object)list)).getValidatedPrivateDnsServers() : null;
        return new CompareResult<InetAddress>(arrayList, list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void ensureDirectlyConnectedRoutes() {
        Iterator<LinkAddress> iterator = this.mLinkAddresses.iterator();
        while (iterator.hasNext()) {
            this.addRoute(new RouteInfo(iterator.next(), null, this.mIfaceName));
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof LinkProperties)) {
            return false;
        }
        if (!(this.isIdenticalInterfaceName((LinkProperties)(object = (LinkProperties)object)) && this.isIdenticalAddresses((LinkProperties)object) && this.isIdenticalDnses((LinkProperties)object) && this.isIdenticalPrivateDns((LinkProperties)object) && this.isIdenticalValidatedPrivateDnses((LinkProperties)object) && this.isIdenticalPcscfs((LinkProperties)object) && this.isIdenticalRoutes((LinkProperties)object) && this.isIdenticalHttpProxy((LinkProperties)object) && this.isIdenticalStackedLinks((LinkProperties)object) && this.isIdenticalMtu((LinkProperties)object) && this.isIdenticalTcpBufferSizes((LinkProperties)object) && this.isIdenticalNat64Prefix((LinkProperties)object))) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public List<InetAddress> getAddresses() {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        Iterator<LinkAddress> iterator = this.mLinkAddresses.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getAddress());
        }
        return Collections.unmodifiableList(arrayList);
    }

    @UnsupportedAppUsage
    public List<InetAddress> getAllAddresses() {
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        Iterator<Parcelable> iterator = this.mLinkAddresses.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getAddress());
        }
        iterator = this.mStackedLinks.values().iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(((LinkProperties)iterator.next()).getAllAddresses());
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public List<String> getAllInterfaceNames() {
        ArrayList<String> arrayList = new ArrayList<String>(this.mStackedLinks.size() + 1);
        Object object = this.mIfaceName;
        if (object != null) {
            arrayList.add((String)object);
        }
        object = this.mStackedLinks.values().iterator();
        while (object.hasNext()) {
            arrayList.addAll(((LinkProperties)object.next()).getAllInterfaceNames());
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public List<LinkAddress> getAllLinkAddresses() {
        ArrayList<LinkAddress> arrayList = new ArrayList<LinkAddress>(this.mLinkAddresses);
        Iterator<LinkProperties> iterator = this.mStackedLinks.values().iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().getAllLinkAddresses());
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public List<RouteInfo> getAllRoutes() {
        ArrayList<RouteInfo> arrayList = new ArrayList<RouteInfo>(this.mRoutes);
        Iterator<LinkProperties> iterator = this.mStackedLinks.values().iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().getAllRoutes());
        }
        return arrayList;
    }

    public List<InetAddress> getDnsServers() {
        return Collections.unmodifiableList(this.mDnses);
    }

    public String getDomains() {
        return this.mDomains;
    }

    public ProxyInfo getHttpProxy() {
        return this.mHttpProxy;
    }

    public String getInterfaceName() {
        return this.mIfaceName;
    }

    public List<LinkAddress> getLinkAddresses() {
        return Collections.unmodifiableList(this.mLinkAddresses);
    }

    public int getMtu() {
        return this.mMtu;
    }

    @SystemApi
    public IpPrefix getNat64Prefix() {
        return this.mNat64Prefix;
    }

    @SystemApi
    public List<InetAddress> getPcscfServers() {
        return Collections.unmodifiableList(this.mPcscfs);
    }

    public String getPrivateDnsServerName() {
        return this.mPrivateDnsServerName;
    }

    public List<RouteInfo> getRoutes() {
        return Collections.unmodifiableList(this.mRoutes);
    }

    @UnsupportedAppUsage
    public List<LinkProperties> getStackedLinks() {
        if (this.mStackedLinks.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<LinkProperties> arrayList = new ArrayList<LinkProperties>();
        Iterator<LinkProperties> iterator = this.mStackedLinks.values().iterator();
        while (iterator.hasNext()) {
            arrayList.add(new LinkProperties(iterator.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    @SystemApi
    public String getTcpBufferSizes() {
        return this.mTcpBufferSizes;
    }

    @SystemApi
    public List<InetAddress> getValidatedPrivateDnsServers() {
        return Collections.unmodifiableList(this.mValidatedPrivateDnses);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasGlobalIPv6Address() {
        return this.hasGlobalIpv6Address();
    }

    @SystemApi
    public boolean hasGlobalIpv6Address() {
        for (LinkAddress linkAddress : this.mLinkAddresses) {
            if (!(linkAddress.getAddress() instanceof Inet6Address) || !linkAddress.isGlobalPreferred()) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasIPv4Address() {
        return this.hasIpv4Address();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasIPv4DefaultRoute() {
        return this.hasIpv4DefaultRoute();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasIPv4DnsServer() {
        return this.hasIpv4DnsServer();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasIPv6DefaultRoute() {
        return this.hasIpv6DefaultRoute();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean hasIPv6DnsServer() {
        return this.hasIpv6DnsServer();
    }

    @SystemApi
    public boolean hasIpv4Address() {
        Iterator<LinkAddress> iterator = this.mLinkAddresses.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next().getAddress() instanceof Inet4Address)) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean hasIpv4DefaultRoute() {
        Iterator<RouteInfo> iterator = this.mRoutes.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isIPv4Default()) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean hasIpv4DnsServer() {
        Iterator<InetAddress> iterator = this.mDnses.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof Inet4Address)) continue;
            return true;
        }
        return false;
    }

    public boolean hasIpv4PcscfServer() {
        Iterator<InetAddress> iterator = this.mPcscfs.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof Inet4Address)) continue;
            return true;
        }
        return false;
    }

    @SystemApi
    public boolean hasIpv6DefaultRoute() {
        Iterator<RouteInfo> iterator = this.mRoutes.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isIPv6Default()) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean hasIpv6DnsServer() {
        Iterator<InetAddress> iterator = this.mDnses.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof Inet6Address)) continue;
            return true;
        }
        return false;
    }

    public boolean hasIpv6PcscfServer() {
        Iterator<InetAddress> iterator = this.mPcscfs.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof Inet6Address)) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        Object object = this.mIfaceName;
        if (object == null) {
            n = 0;
        } else {
            n4 = ((String)object).hashCode();
            n6 = this.mLinkAddresses.size();
            n3 = this.mDnses.size();
            n5 = this.mValidatedPrivateDnses.size();
            object = this.mDomains;
            n = object == null ? 0 : ((String)object).hashCode();
            int n7 = this.mRoutes.size();
            object = this.mHttpProxy;
            n2 = object == null ? 0 : ((ProxyInfo)object).hashCode();
            n = n4 + n6 * 31 + n3 * 37 + n5 * 61 + n + n7 * 41 + n2 + this.mStackedLinks.hashCode() * 47;
        }
        n4 = this.mMtu;
        object = this.mTcpBufferSizes;
        n2 = object == null ? 0 : ((String)object).hashCode();
        n3 = this.mUsePrivateDns ? 57 : 0;
        n5 = this.mPcscfs.size();
        object = this.mPrivateDnsServerName;
        n6 = object == null ? 0 : ((String)object).hashCode();
        return n + n4 * 51 + n2 + n3 + n5 * 67 + n6 + Objects.hash(this.mNat64Prefix);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean isIPv6Provisioned() {
        return this.isIpv6Provisioned();
    }

    @UnsupportedAppUsage
    public boolean isIdenticalAddresses(LinkProperties list) {
        List<InetAddress> list2 = ((LinkProperties)((Object)list)).getAddresses();
        list = this.getAddresses();
        boolean bl = list.size() == list2.size() ? list.containsAll(list2) : false;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalDnses(LinkProperties object) {
        boolean bl;
        block1 : {
            List<InetAddress> list = ((LinkProperties)object).getDnsServers();
            object = ((LinkProperties)object).getDomains();
            String string2 = this.mDomains;
            bl = false;
            if (string2 == null ? object != null : !string2.equals(object)) {
                return false;
            }
            if (this.mDnses.size() != list.size()) break block1;
            bl = this.mDnses.containsAll(list);
        }
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean isIdenticalHttpProxy(LinkProperties linkProperties) {
        boolean bl = this.getHttpProxy() == null ? linkProperties.getHttpProxy() == null : this.getHttpProxy().equals(linkProperties.getHttpProxy());
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalInterfaceName(LinkProperties linkProperties) {
        return TextUtils.equals(this.getInterfaceName(), linkProperties.getInterfaceName());
    }

    public boolean isIdenticalMtu(LinkProperties linkProperties) {
        boolean bl = this.getMtu() == linkProperties.getMtu();
        return bl;
    }

    public boolean isIdenticalNat64Prefix(LinkProperties linkProperties) {
        return Objects.equals(this.mNat64Prefix, linkProperties.mNat64Prefix);
    }

    public boolean isIdenticalPcscfs(LinkProperties object) {
        object = ((LinkProperties)object).getPcscfServers();
        boolean bl = this.mPcscfs.size() == object.size() ? this.mPcscfs.containsAll((Collection<?>)object) : false;
        return bl;
    }

    public boolean isIdenticalPrivateDns(LinkProperties linkProperties) {
        boolean bl = this.isPrivateDnsActive() == linkProperties.isPrivateDnsActive() && TextUtils.equals(this.getPrivateDnsServerName(), linkProperties.getPrivateDnsServerName());
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalRoutes(LinkProperties object) {
        object = ((LinkProperties)object).getRoutes();
        boolean bl = this.mRoutes.size() == object.size() ? this.mRoutes.containsAll((Collection<?>)object) : false;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isIdenticalStackedLinks(LinkProperties linkProperties) {
        if (!this.mStackedLinks.keySet().equals(linkProperties.mStackedLinks.keySet())) {
            return false;
        }
        for (LinkProperties linkProperties2 : this.mStackedLinks.values()) {
            String string2;
            if (linkProperties2.equals(linkProperties.mStackedLinks.get(string2 = linkProperties2.getInterfaceName()))) continue;
            return false;
        }
        return true;
    }

    public boolean isIdenticalTcpBufferSizes(LinkProperties linkProperties) {
        return Objects.equals(this.mTcpBufferSizes, linkProperties.mTcpBufferSizes);
    }

    public boolean isIdenticalValidatedPrivateDnses(LinkProperties object) {
        object = ((LinkProperties)object).getValidatedPrivateDnsServers();
        boolean bl = this.mValidatedPrivateDnses.size() == object.size() ? this.mValidatedPrivateDnses.containsAll((Collection<?>)object) : false;
        return bl;
    }

    @SystemApi
    public boolean isIpv4Provisioned() {
        boolean bl = this.hasIpv4Address() && this.hasIpv4DefaultRoute() && this.hasIpv4DnsServer();
        return bl;
    }

    @SystemApi
    public boolean isIpv6Provisioned() {
        boolean bl = this.hasGlobalIpv6Address() && this.hasIpv6DefaultRoute() && this.hasIpv6DnsServer();
        return bl;
    }

    public boolean isPrivateDnsActive() {
        return this.mUsePrivateDns;
    }

    @SystemApi
    public boolean isProvisioned() {
        boolean bl = this.isIpv4Provisioned() || this.isIpv6Provisioned();
        return bl;
    }

    @SystemApi
    public boolean isReachable(InetAddress inetAddress) {
        block7 : {
            boolean bl;
            block9 : {
                block8 : {
                    RouteInfo routeInfo = RouteInfo.selectBestRoute(this.getAllRoutes(), inetAddress);
                    boolean bl2 = false;
                    bl = false;
                    if (routeInfo == null) {
                        return false;
                    }
                    if (inetAddress instanceof Inet4Address) {
                        return this.hasIpv4AddressOnInterface(routeInfo.getInterface());
                    }
                    if (!(inetAddress instanceof Inet6Address)) break block7;
                    if (inetAddress.isLinkLocalAddress()) {
                        if (((Inet6Address)inetAddress).getScopeId() != 0) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (!routeInfo.hasGateway()) break block8;
                    bl = bl2;
                    if (!this.hasGlobalIpv6Address()) break block9;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @SystemApi
    public boolean removeDnsServer(InetAddress inetAddress) {
        return this.mDnses.remove(inetAddress);
    }

    @SystemApi
    public boolean removeLinkAddress(LinkAddress linkAddress) {
        int n = this.findLinkAddressIndex(linkAddress);
        if (n >= 0) {
            this.mLinkAddresses.remove(n);
            return true;
        }
        return false;
    }

    public boolean removePcscfServer(InetAddress inetAddress) {
        return this.mPcscfs.remove(inetAddress);
    }

    @SystemApi
    public boolean removeRoute(RouteInfo routeInfo) {
        boolean bl = Objects.equals(this.mIfaceName, routeInfo.getInterface()) && this.mRoutes.remove(routeInfo);
        return bl;
    }

    public boolean removeStackedLink(String string2) {
        boolean bl = this.mStackedLinks.remove(string2) != null;
        return bl;
    }

    public boolean removeValidatedPrivateDnsServer(InetAddress inetAddress) {
        return this.mValidatedPrivateDnses.remove(inetAddress);
    }

    public void setDnsServers(Collection<InetAddress> object) {
        this.mDnses.clear();
        object = object.iterator();
        while (object.hasNext()) {
            this.addDnsServer((InetAddress)object.next());
        }
    }

    public void setDomains(String string2) {
        this.mDomains = string2;
    }

    public void setHttpProxy(ProxyInfo proxyInfo) {
        this.mHttpProxy = proxyInfo;
    }

    public void setInterfaceName(String object) {
        this.mIfaceName = object;
        object = new ArrayList(this.mRoutes.size());
        Iterator<RouteInfo> iterator = this.mRoutes.iterator();
        while (iterator.hasNext()) {
            ((ArrayList)object).add(this.routeWithInterface(iterator.next()));
        }
        this.mRoutes = object;
    }

    public void setLinkAddresses(Collection<LinkAddress> object) {
        this.mLinkAddresses.clear();
        object = object.iterator();
        while (object.hasNext()) {
            this.addLinkAddress((LinkAddress)object.next());
        }
    }

    public void setMtu(int n) {
        this.mMtu = n;
    }

    @SystemApi
    public void setNat64Prefix(IpPrefix ipPrefix) {
        if (ipPrefix != null && ipPrefix.getPrefixLength() != 96) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Only 96-bit prefixes are supported: ");
            stringBuilder.append(ipPrefix);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mNat64Prefix = ipPrefix;
    }

    @SystemApi
    public void setPcscfServers(Collection<InetAddress> object) {
        this.mPcscfs.clear();
        object = object.iterator();
        while (object.hasNext()) {
            this.addPcscfServer((InetAddress)object.next());
        }
    }

    @SystemApi
    public void setPrivateDnsServerName(String string2) {
        this.mPrivateDnsServerName = string2;
    }

    @SystemApi
    public void setTcpBufferSizes(String string2) {
        this.mTcpBufferSizes = string2;
    }

    @SystemApi
    public void setUsePrivateDns(boolean bl) {
        this.mUsePrivateDns = bl;
    }

    @SystemApi
    public void setValidatedPrivateDnsServers(Collection<InetAddress> object) {
        this.mValidatedPrivateDnses.clear();
        object = object.iterator();
        while (object.hasNext()) {
            this.addValidatedPrivateDnsServer((InetAddress)object.next());
        }
    }

    public String toString() {
        Object object;
        Iterator iterator;
        StringJoiner stringJoiner = new StringJoiner(" ", "{", "}");
        if (this.mIfaceName != null) {
            stringJoiner.add("InterfaceName:");
            stringJoiner.add(this.mIfaceName);
        }
        stringJoiner.add("LinkAddresses: [");
        if (!this.mLinkAddresses.isEmpty()) {
            stringJoiner.add(TextUtils.join((CharSequence)",", this.mLinkAddresses));
        }
        stringJoiner.add("]");
        stringJoiner.add("DnsAddresses: [");
        if (!this.mDnses.isEmpty()) {
            stringJoiner.add(TextUtils.join((CharSequence)",", this.mDnses));
        }
        stringJoiner.add("]");
        if (this.mUsePrivateDns) {
            stringJoiner.add("UsePrivateDns: true");
        }
        if (this.mPrivateDnsServerName != null) {
            stringJoiner.add("PrivateDnsServerName:");
            stringJoiner.add(this.mPrivateDnsServerName);
        }
        if (!this.mPcscfs.isEmpty()) {
            stringJoiner.add("PcscfAddresses: [");
            stringJoiner.add(TextUtils.join((CharSequence)",", this.mPcscfs));
            stringJoiner.add("]");
        }
        if (!this.mValidatedPrivateDnses.isEmpty()) {
            iterator = new StringJoiner(",", "ValidatedPrivateDnsAddresses: [", "]");
            object = this.mValidatedPrivateDnses.iterator();
            while (object.hasNext()) {
                ((StringJoiner)((Object)iterator)).add(object.next().getHostAddress());
            }
            stringJoiner.add(((StringJoiner)((Object)iterator)).toString());
        }
        stringJoiner.add("Domains:");
        stringJoiner.add(this.mDomains);
        stringJoiner.add("MTU:");
        stringJoiner.add(Integer.toString(this.mMtu));
        if (this.mTcpBufferSizes != null) {
            stringJoiner.add("TcpBufferSizes:");
            stringJoiner.add(this.mTcpBufferSizes);
        }
        stringJoiner.add("Routes: [");
        if (!this.mRoutes.isEmpty()) {
            stringJoiner.add(TextUtils.join((CharSequence)",", this.mRoutes));
        }
        stringJoiner.add("]");
        if (this.mHttpProxy != null) {
            stringJoiner.add("HttpProxy:");
            stringJoiner.add(this.mHttpProxy.toString());
        }
        if (this.mNat64Prefix != null) {
            stringJoiner.add("Nat64Prefix:");
            stringJoiner.add(this.mNat64Prefix.toString());
        }
        if (!(iterator = this.mStackedLinks.values()).isEmpty()) {
            object = new StringJoiner(",", "Stacked: [", "]");
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                LinkProperties linkProperties = (LinkProperties)iterator.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[ ");
                stringBuilder.append(linkProperties);
                stringBuilder.append(" ]");
                ((StringJoiner)object).add(stringBuilder.toString());
            }
            stringJoiner.add(((StringJoiner)object).toString());
        }
        return stringJoiner.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.getInterfaceName());
        parcel.writeInt(this.mLinkAddresses.size());
        Iterator<Object> iterator = this.mLinkAddresses.iterator();
        while (iterator.hasNext()) {
            parcel.writeParcelable(iterator.next(), n);
        }
        parcel.writeInt(this.mDnses.size());
        iterator = this.mDnses.iterator();
        while (iterator.hasNext()) {
            parcel.writeByteArray(((InetAddress)iterator.next()).getAddress());
        }
        parcel.writeInt(this.mValidatedPrivateDnses.size());
        iterator = this.mValidatedPrivateDnses.iterator();
        while (iterator.hasNext()) {
            parcel.writeByteArray(((InetAddress)iterator.next()).getAddress());
        }
        parcel.writeBoolean(this.mUsePrivateDns);
        parcel.writeString(this.mPrivateDnsServerName);
        parcel.writeInt(this.mPcscfs.size());
        iterator = this.mPcscfs.iterator();
        while (iterator.hasNext()) {
            parcel.writeByteArray(((InetAddress)iterator.next()).getAddress());
        }
        parcel.writeString(this.mDomains);
        parcel.writeInt(this.mMtu);
        parcel.writeString(this.mTcpBufferSizes);
        parcel.writeInt(this.mRoutes.size());
        iterator = this.mRoutes.iterator();
        while (iterator.hasNext()) {
            parcel.writeParcelable((RouteInfo)iterator.next(), n);
        }
        if (this.mHttpProxy != null) {
            parcel.writeByte((byte)1);
            parcel.writeParcelable(this.mHttpProxy, n);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeParcelable(this.mNat64Prefix, 0);
        parcel.writeList(new ArrayList<LinkProperties>(this.mStackedLinks.values()));
    }

    public static class CompareResult<T> {
        public final List<T> added = new ArrayList<T>();
        public final List<T> removed = new ArrayList<T>();

        public CompareResult() {
        }

        public CompareResult(Collection<T> collection, Collection<T> object) {
            if (collection != null) {
                this.removed.addAll(collection);
            }
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    collection = object.next();
                    if (this.removed.remove(collection)) continue;
                    this.added.add(collection);
                }
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removed=[");
            stringBuilder.append(TextUtils.join((CharSequence)",", this.removed));
            stringBuilder.append("] added=[");
            stringBuilder.append(TextUtils.join((CharSequence)",", this.added));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static enum ProvisioningChange {
        STILL_NOT_PROVISIONED,
        LOST_PROVISIONING,
        GAINED_PROVISIONING,
        STILL_PROVISIONED;
        
    }

}

