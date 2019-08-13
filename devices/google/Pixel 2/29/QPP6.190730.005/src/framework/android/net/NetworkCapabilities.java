/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.-$
 *  android.net.-$$Lambda
 *  android.net.-$$Lambda$FpGXkd3pLxeXY58eJ_84mi1PLWQ
 *  android.net.-$$Lambda$p1_56lwnt1xBuY1muPblbN1Dtkw
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.-$;
import android.net.MatchAllNetworkSpecifier;
import android.net.NetworkSpecifier;
import android.net.TransportInfo;
import android.net.UidRange;
import android.net._$$Lambda$FpGXkd3pLxeXY58eJ_84mi1PLWQ;
import android.net._$$Lambda$p1_56lwnt1xBuY1muPblbN1Dtkw;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public final class NetworkCapabilities
implements Parcelable {
    private static final long CONNECTIVITY_MANAGED_CAPABILITIES = 17498112L;
    public static final Parcelable.Creator<NetworkCapabilities> CREATOR;
    private static final long DEFAULT_CAPABILITIES = 57344L;
    private static final long FORCE_RESTRICTED_CAPABILITIES = 0x400000L;
    private static final int INVALID_UID = -1;
    public static final int LINK_BANDWIDTH_UNSPECIFIED = 0;
    private static final int MAX_NET_CAPABILITY = 24;
    public static final int MAX_TRANSPORT = 7;
    private static final int MIN_NET_CAPABILITY = 0;
    public static final int MIN_TRANSPORT = 0;
    private static final long MUTABLE_CAPABILITIES = 20922368L;
    public static final int NET_CAPABILITY_CAPTIVE_PORTAL = 17;
    public static final int NET_CAPABILITY_CBS = 5;
    public static final int NET_CAPABILITY_DUN = 2;
    public static final int NET_CAPABILITY_EIMS = 10;
    public static final int NET_CAPABILITY_FOREGROUND = 19;
    public static final int NET_CAPABILITY_FOTA = 3;
    public static final int NET_CAPABILITY_IA = 7;
    public static final int NET_CAPABILITY_IMS = 4;
    public static final int NET_CAPABILITY_INTERNET = 12;
    public static final int NET_CAPABILITY_MCX = 23;
    public static final int NET_CAPABILITY_MMS = 0;
    public static final int NET_CAPABILITY_NOT_CONGESTED = 20;
    public static final int NET_CAPABILITY_NOT_METERED = 11;
    public static final int NET_CAPABILITY_NOT_RESTRICTED = 13;
    public static final int NET_CAPABILITY_NOT_ROAMING = 18;
    public static final int NET_CAPABILITY_NOT_SUSPENDED = 21;
    public static final int NET_CAPABILITY_NOT_VPN = 15;
    @SystemApi
    public static final int NET_CAPABILITY_OEM_PAID = 22;
    @SystemApi
    public static final int NET_CAPABILITY_PARTIAL_CONNECTIVITY = 24;
    public static final int NET_CAPABILITY_RCS = 8;
    public static final int NET_CAPABILITY_SUPL = 1;
    public static final int NET_CAPABILITY_TRUSTED = 14;
    public static final int NET_CAPABILITY_VALIDATED = 16;
    public static final int NET_CAPABILITY_WIFI_P2P = 6;
    public static final int NET_CAPABILITY_XCAP = 9;
    private static final long NON_REQUESTABLE_CAPABILITIES = 20905984L;
    @VisibleForTesting
    static final long RESTRICTED_CAPABILITIES = 8390588L;
    public static final int SIGNAL_STRENGTH_UNSPECIFIED = Integer.MIN_VALUE;
    private static final String TAG = "NetworkCapabilities";
    public static final int TRANSPORT_BLUETOOTH = 2;
    public static final int TRANSPORT_CELLULAR = 0;
    public static final int TRANSPORT_ETHERNET = 3;
    public static final int TRANSPORT_LOWPAN = 6;
    private static final String[] TRANSPORT_NAMES;
    public static final int TRANSPORT_TEST = 7;
    public static final int TRANSPORT_VPN = 4;
    public static final int TRANSPORT_WIFI = 1;
    public static final int TRANSPORT_WIFI_AWARE = 5;
    @VisibleForTesting
    static final long UNRESTRICTED_CAPABILITIES = 4163L;
    private int mEstablishingVpnAppUid = -1;
    private int mLinkDownBandwidthKbps = 0;
    private int mLinkUpBandwidthKbps = 0;
    @UnsupportedAppUsage
    private long mNetworkCapabilities;
    private NetworkSpecifier mNetworkSpecifier = null;
    private String mSSID;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSignalStrength = Integer.MIN_VALUE;
    private TransportInfo mTransportInfo = null;
    private long mTransportTypes;
    private ArraySet<UidRange> mUids = null;
    private long mUnwantedNetworkCapabilities;

    static {
        TRANSPORT_NAMES = new String[]{"CELLULAR", "WIFI", "BLUETOOTH", "ETHERNET", "VPN", "WIFI_AWARE", "LOWPAN", "TEST"};
        CREATOR = new Parcelable.Creator<NetworkCapabilities>(){

            @Override
            public NetworkCapabilities createFromParcel(Parcel parcel) {
                NetworkCapabilities networkCapabilities = new NetworkCapabilities();
                networkCapabilities.mNetworkCapabilities = parcel.readLong();
                networkCapabilities.mUnwantedNetworkCapabilities = parcel.readLong();
                networkCapabilities.mTransportTypes = parcel.readLong();
                networkCapabilities.mLinkUpBandwidthKbps = parcel.readInt();
                networkCapabilities.mLinkDownBandwidthKbps = parcel.readInt();
                networkCapabilities.mNetworkSpecifier = (NetworkSpecifier)parcel.readParcelable(null);
                networkCapabilities.mTransportInfo = (TransportInfo)parcel.readParcelable(null);
                networkCapabilities.mSignalStrength = parcel.readInt();
                networkCapabilities.mUids = parcel.readArraySet(null);
                networkCapabilities.mSSID = parcel.readString();
                return networkCapabilities;
            }

            public NetworkCapabilities[] newArray(int n) {
                return new NetworkCapabilities[n];
            }
        };
    }

    @UnsupportedAppUsage
    public NetworkCapabilities() {
        this.clearAll();
        this.mNetworkCapabilities = 57344L;
    }

    public NetworkCapabilities(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities != null) {
            this.set(networkCapabilities);
        }
    }

    public static void appendStringRepresentationOfBitMaskToStringBuilder(StringBuilder stringBuilder, long l, NameOf nameOf, String string2) {
        int n = 0;
        boolean bl = false;
        while (l != 0L) {
            boolean bl2 = bl;
            if ((1L & l) != 0L) {
                if (bl) {
                    stringBuilder.append(string2);
                } else {
                    bl = true;
                }
                stringBuilder.append(nameOf.nameOf(n));
                bl2 = bl;
            }
            l >>= 1;
            ++n;
            bl = bl2;
        }
    }

    public static String capabilityNameOf(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 24: {
                return "PARTIAL_CONNECTIVITY";
            }
            case 23: {
                return "MCX";
            }
            case 22: {
                return "OEM_PAID";
            }
            case 21: {
                return "NOT_SUSPENDED";
            }
            case 20: {
                return "NOT_CONGESTED";
            }
            case 19: {
                return "FOREGROUND";
            }
            case 18: {
                return "NOT_ROAMING";
            }
            case 17: {
                return "CAPTIVE_PORTAL";
            }
            case 16: {
                return "VALIDATED";
            }
            case 15: {
                return "NOT_VPN";
            }
            case 14: {
                return "TRUSTED";
            }
            case 13: {
                return "NOT_RESTRICTED";
            }
            case 12: {
                return "INTERNET";
            }
            case 11: {
                return "NOT_METERED";
            }
            case 10: {
                return "EIMS";
            }
            case 9: {
                return "XCAP";
            }
            case 8: {
                return "RCS";
            }
            case 7: {
                return "IA";
            }
            case 6: {
                return "WIFI_P2P";
            }
            case 5: {
                return "CBS";
            }
            case 4: {
                return "IMS";
            }
            case 3: {
                return "FOTA";
            }
            case 2: {
                return "DUN";
            }
            case 1: {
                return "SUPL";
            }
            case 0: 
        }
        return "MMS";
    }

    public static String capabilityNamesOf(int[] arrn) {
        StringJoiner stringJoiner = new StringJoiner("|");
        if (arrn != null) {
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                stringJoiner.add(NetworkCapabilities.capabilityNameOf(arrn[i]));
            }
        }
        return stringJoiner.toString();
    }

    private static void checkValidCapability(int n) {
        boolean bl = NetworkCapabilities.isValidCapability(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetworkCapability ");
        stringBuilder.append(n);
        stringBuilder.append("out of range");
        Preconditions.checkArgument(bl, stringBuilder.toString());
    }

    private static void checkValidTransportType(int n) {
        boolean bl = NetworkCapabilities.isValidTransport(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid TransportType ");
        stringBuilder.append(n);
        Preconditions.checkArgument(bl, stringBuilder.toString());
    }

    private void combineLinkBandwidths(NetworkCapabilities networkCapabilities) {
        this.mLinkUpBandwidthKbps = Math.max(this.mLinkUpBandwidthKbps, networkCapabilities.mLinkUpBandwidthKbps);
        this.mLinkDownBandwidthKbps = Math.max(this.mLinkDownBandwidthKbps, networkCapabilities.mLinkDownBandwidthKbps);
    }

    private void combineNetCapabilities(NetworkCapabilities networkCapabilities) {
        this.mNetworkCapabilities |= networkCapabilities.mNetworkCapabilities;
        this.mUnwantedNetworkCapabilities |= networkCapabilities.mUnwantedNetworkCapabilities;
    }

    private void combineSSIDs(NetworkCapabilities networkCapabilities) {
        String string2 = this.mSSID;
        if (string2 != null && !string2.equals(networkCapabilities.mSSID)) {
            throw new IllegalStateException("Can't combine two SSIDs");
        }
        this.setSSID(networkCapabilities.mSSID);
    }

    private void combineSignalStrength(NetworkCapabilities networkCapabilities) {
        this.mSignalStrength = Math.max(this.mSignalStrength, networkCapabilities.mSignalStrength);
    }

    private void combineSpecifiers(NetworkCapabilities networkCapabilities) {
        NetworkSpecifier networkSpecifier = this.mNetworkSpecifier;
        if (networkSpecifier != null && !networkSpecifier.equals(networkCapabilities.mNetworkSpecifier)) {
            throw new IllegalStateException("Can't combine two networkSpecifiers");
        }
        this.setNetworkSpecifier(networkCapabilities.mNetworkSpecifier);
    }

    private void combineTransportInfos(NetworkCapabilities networkCapabilities) {
        TransportInfo transportInfo = this.mTransportInfo;
        if (transportInfo != null && !transportInfo.equals(networkCapabilities.mTransportInfo)) {
            throw new IllegalStateException("Can't combine two TransportInfos");
        }
        this.setTransportInfo(networkCapabilities.mTransportInfo);
    }

    private void combineTransportTypes(NetworkCapabilities networkCapabilities) {
        this.mTransportTypes |= networkCapabilities.mTransportTypes;
    }

    private void combineUids(NetworkCapabilities arraySet) {
        ArraySet<UidRange> arraySet2;
        arraySet = ((NetworkCapabilities)arraySet).mUids;
        if (arraySet != null && (arraySet2 = this.mUids) != null) {
            arraySet2.addAll(arraySet);
            return;
        }
        this.mUids = null;
    }

    private boolean equalsLinkBandwidths(NetworkCapabilities networkCapabilities) {
        boolean bl = this.mLinkUpBandwidthKbps == networkCapabilities.mLinkUpBandwidthKbps && this.mLinkDownBandwidthKbps == networkCapabilities.mLinkDownBandwidthKbps;
        return bl;
    }

    private boolean equalsNetCapabilitiesRequestable(NetworkCapabilities networkCapabilities) {
        boolean bl = (this.mNetworkCapabilities & -20905985L) == (networkCapabilities.mNetworkCapabilities & -20905985L) && (this.mUnwantedNetworkCapabilities & -20905985L) == (-20905985L & networkCapabilities.mUnwantedNetworkCapabilities);
        return bl;
    }

    private boolean equalsSignalStrength(NetworkCapabilities networkCapabilities) {
        boolean bl = this.mSignalStrength == networkCapabilities.mSignalStrength;
        return bl;
    }

    private boolean equalsSpecifier(NetworkCapabilities networkCapabilities) {
        return Objects.equals(this.mNetworkSpecifier, networkCapabilities.mNetworkSpecifier);
    }

    private boolean equalsTransportInfo(NetworkCapabilities networkCapabilities) {
        return Objects.equals(this.mTransportInfo, networkCapabilities.mTransportInfo);
    }

    private static boolean isValidCapability(int n) {
        boolean bl = n >= 0 && n <= 24;
        return bl;
    }

    public static boolean isValidTransport(int n) {
        boolean bl = n >= 0 && n <= 7;
        return bl;
    }

    public static int maxBandwidth(int n, int n2) {
        return Math.max(n, n2);
    }

    public static int minBandwidth(int n, int n2) {
        if (n == 0) {
            return n2;
        }
        if (n2 == 0) {
            return n;
        }
        return Math.min(n, n2);
    }

    private boolean satisfiedByLinkBandwidths(NetworkCapabilities networkCapabilities) {
        boolean bl = this.mLinkUpBandwidthKbps <= networkCapabilities.mLinkUpBandwidthKbps && this.mLinkDownBandwidthKbps <= networkCapabilities.mLinkDownBandwidthKbps;
        return bl;
    }

    private boolean satisfiedByNetCapabilities(NetworkCapabilities networkCapabilities, boolean bl) {
        long l = this.mNetworkCapabilities;
        long l2 = this.mUnwantedNetworkCapabilities;
        long l3 = networkCapabilities.mNetworkCapabilities;
        long l4 = l;
        long l5 = l2;
        if (bl) {
            l4 = l & -20922369L;
            l5 = l2 & -20922369L;
        }
        bl = (l3 & l4) == l4 && (l5 & l3) == 0L;
        return bl;
    }

    private boolean satisfiedByNetworkCapabilities(NetworkCapabilities networkCapabilities, boolean bl) {
        bl = !(networkCapabilities == null || !this.satisfiedByNetCapabilities(networkCapabilities, bl) || !this.satisfiedByTransportTypes(networkCapabilities) || !bl && !this.satisfiedByLinkBandwidths(networkCapabilities) || !this.satisfiedBySpecifier(networkCapabilities) || !bl && !this.satisfiedBySignalStrength(networkCapabilities) || !bl && !this.satisfiedByUids(networkCapabilities) || !bl && !this.satisfiedBySSID(networkCapabilities));
        return bl;
    }

    private boolean satisfiedBySignalStrength(NetworkCapabilities networkCapabilities) {
        boolean bl = this.mSignalStrength <= networkCapabilities.mSignalStrength;
        return bl;
    }

    private boolean satisfiedBySpecifier(NetworkCapabilities networkCapabilities) {
        NetworkSpecifier networkSpecifier = this.mNetworkSpecifier;
        boolean bl = networkSpecifier == null || networkSpecifier.satisfiedBy(networkCapabilities.mNetworkSpecifier) || networkCapabilities.mNetworkSpecifier instanceof MatchAllNetworkSpecifier;
        return bl;
    }

    private boolean satisfiedByTransportTypes(NetworkCapabilities networkCapabilities) {
        long l = this.mTransportTypes;
        boolean bl = l == 0L || (l & networkCapabilities.mTransportTypes) != 0L;
        return bl;
    }

    public static String transportNameOf(int n) {
        if (!NetworkCapabilities.isValidTransport(n)) {
            return "UNKNOWN";
        }
        return TRANSPORT_NAMES[n];
    }

    @UnsupportedAppUsage
    public static String transportNamesOf(int[] arrn) {
        StringJoiner stringJoiner = new StringJoiner("|");
        if (arrn != null) {
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                stringJoiner.add(NetworkCapabilities.transportNameOf(arrn[i]));
            }
        }
        return stringJoiner.toString();
    }

    @UnsupportedAppUsage
    public NetworkCapabilities addCapability(int n) {
        NetworkCapabilities.checkValidCapability(n);
        this.mNetworkCapabilities |= (long)(1 << n);
        this.mUnwantedNetworkCapabilities &= (long)(1 << n);
        return this;
    }

    @UnsupportedAppUsage
    public NetworkCapabilities addTransportType(int n) {
        NetworkCapabilities.checkValidTransportType(n);
        this.mTransportTypes |= (long)(1 << n);
        this.setNetworkSpecifier(this.mNetworkSpecifier);
        return this;
    }

    public void addUnwantedCapability(int n) {
        NetworkCapabilities.checkValidCapability(n);
        this.mUnwantedNetworkCapabilities |= (long)(1 << n);
        this.mNetworkCapabilities &= (long)(1 << n);
    }

    public boolean appliesToUid(int n) {
        ArraySet<UidRange> arraySet = this.mUids;
        if (arraySet == null) {
            return true;
        }
        arraySet = arraySet.iterator();
        while (arraySet.hasNext()) {
            if (!((UidRange)arraySet.next()).contains(n)) continue;
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean appliesToUidRange(UidRange uidRange) {
        ArraySet<UidRange> arraySet = this.mUids;
        if (arraySet == null) {
            return true;
        }
        arraySet = arraySet.iterator();
        while (arraySet.hasNext()) {
            if (!((UidRange)arraySet.next()).containsRange(uidRange)) continue;
            return true;
        }
        return false;
    }

    public void clearAll() {
        this.mUnwantedNetworkCapabilities = 0L;
        this.mTransportTypes = 0L;
        this.mNetworkCapabilities = 0L;
        this.mLinkDownBandwidthKbps = 0;
        this.mLinkUpBandwidthKbps = 0;
        this.mNetworkSpecifier = null;
        this.mTransportInfo = null;
        this.mSignalStrength = Integer.MIN_VALUE;
        this.mUids = null;
        this.mEstablishingVpnAppUid = -1;
        this.mSSID = null;
    }

    public void combineCapabilities(NetworkCapabilities networkCapabilities) {
        this.combineNetCapabilities(networkCapabilities);
        this.combineTransportTypes(networkCapabilities);
        this.combineLinkBandwidths(networkCapabilities);
        this.combineSpecifiers(networkCapabilities);
        this.combineTransportInfos(networkCapabilities);
        this.combineSignalStrength(networkCapabilities);
        this.combineUids(networkCapabilities);
        this.combineSSIDs(networkCapabilities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String describeFirstNonRequestableCapability() {
        long l = (this.mNetworkCapabilities | this.mUnwantedNetworkCapabilities) & 20905984L;
        if (l != 0L) {
            return NetworkCapabilities.capabilityNameOf(BitUtils.unpackBits(l)[0]);
        }
        if (this.mLinkUpBandwidthKbps == 0 && this.mLinkDownBandwidthKbps == 0) {
            if (this.hasSignalStrength()) {
                return "signalStrength";
            }
            return null;
        }
        return "link bandwidth";
    }

    public String describeImmutableDifferences(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return "other NetworkCapabilities was null";
        }
        StringJoiner stringJoiner = new StringJoiner(", ");
        long l = this.mNetworkCapabilities & -20924417L;
        long l2 = -20924417L & networkCapabilities.mNetworkCapabilities;
        if (l != l2) {
            stringJoiner.add(String.format("immutable capabilities changed: %s -> %s", NetworkCapabilities.capabilityNamesOf(BitUtils.unpackBits(l)), NetworkCapabilities.capabilityNamesOf(BitUtils.unpackBits(l2))));
        }
        if (!this.equalsSpecifier(networkCapabilities)) {
            stringJoiner.add(String.format("specifier changed: %s -> %s", this.getNetworkSpecifier(), networkCapabilities.getNetworkSpecifier()));
        }
        if (!this.equalsTransportTypes(networkCapabilities)) {
            stringJoiner.add(String.format("transports changed: %s -> %s", NetworkCapabilities.transportNamesOf(this.getTransportTypes()), NetworkCapabilities.transportNamesOf(networkCapabilities.getTransportTypes())));
        }
        return stringJoiner.toString();
    }

    public boolean equalRequestableCapabilities(NetworkCapabilities networkCapabilities) {
        boolean bl;
        block1 : {
            bl = false;
            if (networkCapabilities == null) {
                return false;
            }
            if (!this.equalsNetCapabilitiesRequestable(networkCapabilities) || !this.equalsTransportTypes(networkCapabilities) || !this.equalsSpecifier(networkCapabilities)) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof NetworkCapabilities) {
            if (this.equalsNetCapabilities((NetworkCapabilities)(object = (NetworkCapabilities)object)) && this.equalsTransportTypes((NetworkCapabilities)object) && this.equalsLinkBandwidths((NetworkCapabilities)object) && this.equalsSignalStrength((NetworkCapabilities)object) && this.equalsSpecifier((NetworkCapabilities)object) && this.equalsTransportInfo((NetworkCapabilities)object) && this.equalsUids((NetworkCapabilities)object) && this.equalsSSID((NetworkCapabilities)object)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean equalsNetCapabilities(NetworkCapabilities networkCapabilities) {
        boolean bl = networkCapabilities.mNetworkCapabilities == this.mNetworkCapabilities && networkCapabilities.mUnwantedNetworkCapabilities == this.mUnwantedNetworkCapabilities;
        return bl;
    }

    public boolean equalsSSID(NetworkCapabilities networkCapabilities) {
        return Objects.equals(this.mSSID, networkCapabilities.mSSID);
    }

    public boolean equalsTransportTypes(NetworkCapabilities networkCapabilities) {
        boolean bl = networkCapabilities.mTransportTypes == this.mTransportTypes;
        return bl;
    }

    @VisibleForTesting
    public boolean equalsUids(NetworkCapabilities arraySet) {
        Object object = ((NetworkCapabilities)arraySet).mUids;
        boolean bl = false;
        if (object == null) {
            if (this.mUids == null) {
                bl = true;
            }
            return bl;
        }
        arraySet = this.mUids;
        if (arraySet == null) {
            return false;
        }
        arraySet = new ArraySet(arraySet);
        object = object.iterator();
        while (object.hasNext()) {
            UidRange uidRange = (UidRange)object.next();
            if (!arraySet.contains(uidRange)) {
                return false;
            }
            arraySet.remove(uidRange);
        }
        return arraySet.isEmpty();
    }

    public int[] getCapabilities() {
        return BitUtils.unpackBits(this.mNetworkCapabilities);
    }

    public int getEstablishingVpnAppUid() {
        return this.mEstablishingVpnAppUid;
    }

    public int getLinkDownstreamBandwidthKbps() {
        return this.mLinkDownBandwidthKbps;
    }

    public int getLinkUpstreamBandwidthKbps() {
        return this.mLinkUpBandwidthKbps;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public NetworkSpecifier getNetworkSpecifier() {
        return this.mNetworkSpecifier;
    }

    public String getSSID() {
        return this.mSSID;
    }

    public int getSignalStrength() {
        return this.mSignalStrength;
    }

    public TransportInfo getTransportInfo() {
        return this.mTransportInfo;
    }

    @SystemApi
    public int[] getTransportTypes() {
        return BitUtils.unpackBits(this.mTransportTypes);
    }

    public Set<UidRange> getUids() {
        ArraySet<UidRange> arraySet = this.mUids;
        arraySet = arraySet == null ? null : new ArraySet<UidRange>(arraySet);
        return arraySet;
    }

    public int[] getUnwantedCapabilities() {
        return BitUtils.unpackBits(this.mUnwantedNetworkCapabilities);
    }

    public boolean hasCapability(int n) {
        boolean bl = NetworkCapabilities.isValidCapability(n);
        boolean bl2 = true;
        if (!bl || (this.mNetworkCapabilities & (long)(1 << n)) == 0L) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean hasConnectivityManagedCapability() {
        boolean bl = (this.mNetworkCapabilities & 17498112L) != 0L;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean hasSignalStrength() {
        boolean bl = this.mSignalStrength > Integer.MIN_VALUE;
        return bl;
    }

    public boolean hasTransport(int n) {
        boolean bl = NetworkCapabilities.isValidTransport(n);
        boolean bl2 = true;
        if (!bl || (this.mTransportTypes & (long)(1 << n)) == 0L) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean hasUnwantedCapability(int n) {
        boolean bl = NetworkCapabilities.isValidCapability(n);
        boolean bl2 = true;
        if (!bl || (this.mUnwantedNetworkCapabilities & (long)(1 << n)) == 0L) {
            bl2 = false;
        }
        return bl2;
    }

    public int hashCode() {
        long l = this.mNetworkCapabilities;
        int n = (int)(l & -1L);
        int n2 = (int)(l >> 32);
        l = this.mUnwantedNetworkCapabilities;
        int n3 = (int)(l & -1L);
        int n4 = (int)(l >> 32);
        l = this.mTransportTypes;
        return n + n2 * 3 + n3 * 5 + n4 * 7 + (int)(-1L & l) * 11 + (int)(l >> 32) * 13 + this.mLinkUpBandwidthKbps * 17 + this.mLinkDownBandwidthKbps * 19 + Objects.hashCode(this.mNetworkSpecifier) * 23 + this.mSignalStrength * 29 + Objects.hashCode(this.mUids) * 31 + Objects.hashCode(this.mSSID) * 37 + Objects.hashCode(this.mTransportInfo) * 41;
    }

    public boolean isMetered() {
        return this.hasCapability(11) ^ true;
    }

    public void maybeMarkCapabilitiesRestricted() {
        long l = this.mNetworkCapabilities;
        boolean bl = true;
        boolean bl2 = (l & 0x400000L) != 0L;
        boolean bl3 = (this.mNetworkCapabilities & 4163L) != 0L;
        if ((this.mNetworkCapabilities & 8390588L) == 0L) {
            bl = false;
        }
        if (bl2 || bl && !bl3) {
            this.removeCapability(13);
        }
    }

    @UnsupportedAppUsage
    public NetworkCapabilities removeCapability(int n) {
        NetworkCapabilities.checkValidCapability(n);
        long l = 1 << n;
        this.mNetworkCapabilities &= l;
        this.mUnwantedNetworkCapabilities &= l;
        return this;
    }

    public NetworkCapabilities removeTransportType(int n) {
        NetworkCapabilities.checkValidTransportType(n);
        this.mTransportTypes &= (long)(1 << n);
        this.setNetworkSpecifier(this.mNetworkSpecifier);
        return this;
    }

    public boolean satisfiedByImmutableNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        return this.satisfiedByNetworkCapabilities(networkCapabilities, true);
    }

    @SystemApi
    public boolean satisfiedByNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        return this.satisfiedByNetworkCapabilities(networkCapabilities, false);
    }

    public boolean satisfiedBySSID(NetworkCapabilities networkCapabilities) {
        String string2 = this.mSSID;
        boolean bl = string2 == null || string2.equals(networkCapabilities.mSSID);
        return bl;
    }

    public boolean satisfiedByUids(NetworkCapabilities networkCapabilities) {
        Object object;
        if (networkCapabilities.mUids != null && (object = this.mUids) != null) {
            Iterator<UidRange> iterator = ((ArraySet)object).iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (((UidRange)object).contains(networkCapabilities.mEstablishingVpnAppUid)) {
                    return true;
                }
                if (networkCapabilities.appliesToUidRange((UidRange)object)) continue;
                return false;
            }
            return true;
        }
        return true;
    }

    public void set(NetworkCapabilities networkCapabilities) {
        this.mNetworkCapabilities = networkCapabilities.mNetworkCapabilities;
        this.mTransportTypes = networkCapabilities.mTransportTypes;
        this.mLinkUpBandwidthKbps = networkCapabilities.mLinkUpBandwidthKbps;
        this.mLinkDownBandwidthKbps = networkCapabilities.mLinkDownBandwidthKbps;
        this.mNetworkSpecifier = networkCapabilities.mNetworkSpecifier;
        this.mTransportInfo = networkCapabilities.mTransportInfo;
        this.mSignalStrength = networkCapabilities.mSignalStrength;
        this.setUids(networkCapabilities.mUids);
        this.mEstablishingVpnAppUid = networkCapabilities.mEstablishingVpnAppUid;
        this.mUnwantedNetworkCapabilities = networkCapabilities.mUnwantedNetworkCapabilities;
        this.mSSID = networkCapabilities.mSSID;
    }

    @Deprecated
    public void setCapabilities(int[] arrn) {
        this.setCapabilities(arrn, new int[0]);
    }

    public void setCapabilities(int[] arrn, int[] arrn2) {
        this.mNetworkCapabilities = BitUtils.packBits(arrn);
        this.mUnwantedNetworkCapabilities = BitUtils.packBits(arrn2);
    }

    public NetworkCapabilities setCapability(int n, boolean bl) {
        if (bl) {
            this.addCapability(n);
        } else {
            this.removeCapability(n);
        }
        return this;
    }

    public void setEstablishingVpnAppUid(int n) {
        this.mEstablishingVpnAppUid = n;
    }

    public NetworkCapabilities setLinkDownstreamBandwidthKbps(int n) {
        this.mLinkDownBandwidthKbps = n;
        return this;
    }

    public NetworkCapabilities setLinkUpstreamBandwidthKbps(int n) {
        this.mLinkUpBandwidthKbps = n;
        return this;
    }

    public NetworkCapabilities setNetworkSpecifier(NetworkSpecifier networkSpecifier) {
        if (networkSpecifier != null && Long.bitCount(this.mTransportTypes) != 1) {
            throw new IllegalStateException("Must have a single transport specified to use setNetworkSpecifier");
        }
        this.mNetworkSpecifier = networkSpecifier;
        return this;
    }

    public NetworkCapabilities setSSID(String string2) {
        this.mSSID = string2;
        return this;
    }

    @UnsupportedAppUsage
    public NetworkCapabilities setSignalStrength(int n) {
        this.mSignalStrength = n;
        return this;
    }

    public NetworkCapabilities setSingleUid(int n) {
        ArraySet<UidRange> arraySet = new ArraySet<UidRange>(1);
        arraySet.add(new UidRange(n, n));
        this.setUids(arraySet);
        return this;
    }

    public NetworkCapabilities setTransportInfo(TransportInfo transportInfo) {
        this.mTransportInfo = transportInfo;
        return this;
    }

    public NetworkCapabilities setTransportType(int n, boolean bl) {
        if (bl) {
            this.addTransportType(n);
        } else {
            this.removeTransportType(n);
        }
        return this;
    }

    public void setTransportTypes(int[] arrn) {
        this.mTransportTypes = BitUtils.packBits(arrn);
    }

    public NetworkCapabilities setUids(Set<UidRange> set) {
        this.mUids = set == null ? null : new ArraySet<UidRange>(set);
        return this;
    }

    public String toString() {
        ArraySet<UidRange> arraySet;
        StringBuilder stringBuilder = new StringBuilder("[");
        if (0L != this.mTransportTypes) {
            stringBuilder.append(" Transports: ");
            NetworkCapabilities.appendStringRepresentationOfBitMaskToStringBuilder(stringBuilder, this.mTransportTypes, (NameOf)_$$Lambda$FpGXkd3pLxeXY58eJ_84mi1PLWQ.INSTANCE, "|");
        }
        if (0L != this.mNetworkCapabilities) {
            stringBuilder.append(" Capabilities: ");
            NetworkCapabilities.appendStringRepresentationOfBitMaskToStringBuilder(stringBuilder, this.mNetworkCapabilities, (NameOf)_$$Lambda$p1_56lwnt1xBuY1muPblbN1Dtkw.INSTANCE, "&");
        }
        if (0L != this.mUnwantedNetworkCapabilities) {
            stringBuilder.append(" Unwanted: ");
            NetworkCapabilities.appendStringRepresentationOfBitMaskToStringBuilder(stringBuilder, this.mUnwantedNetworkCapabilities, (NameOf)_$$Lambda$p1_56lwnt1xBuY1muPblbN1Dtkw.INSTANCE, "&");
        }
        if (this.mLinkUpBandwidthKbps > 0) {
            stringBuilder.append(" LinkUpBandwidth>=");
            stringBuilder.append(this.mLinkUpBandwidthKbps);
            stringBuilder.append("Kbps");
        }
        if (this.mLinkDownBandwidthKbps > 0) {
            stringBuilder.append(" LinkDnBandwidth>=");
            stringBuilder.append(this.mLinkDownBandwidthKbps);
            stringBuilder.append("Kbps");
        }
        if (this.mNetworkSpecifier != null) {
            stringBuilder.append(" Specifier: <");
            stringBuilder.append(this.mNetworkSpecifier);
            stringBuilder.append(">");
        }
        if (this.mTransportInfo != null) {
            stringBuilder.append(" TransportInfo: <");
            stringBuilder.append(this.mTransportInfo);
            stringBuilder.append(">");
        }
        if (this.hasSignalStrength()) {
            stringBuilder.append(" SignalStrength: ");
            stringBuilder.append(this.mSignalStrength);
        }
        if ((arraySet = this.mUids) != null) {
            if (1 == arraySet.size() && this.mUids.valueAt(0).count() == 1) {
                stringBuilder.append(" Uid: ");
                stringBuilder.append(this.mUids.valueAt((int)0).start);
            } else {
                stringBuilder.append(" Uids: <");
                stringBuilder.append(this.mUids);
                stringBuilder.append(">");
            }
        }
        if (this.mEstablishingVpnAppUid != -1) {
            stringBuilder.append(" EstablishingAppUid: ");
            stringBuilder.append(this.mEstablishingVpnAppUid);
        }
        if (this.mSSID != null) {
            stringBuilder.append(" SSID: ");
            stringBuilder.append(this.mSSID);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mNetworkCapabilities);
        parcel.writeLong(this.mUnwantedNetworkCapabilities);
        parcel.writeLong(this.mTransportTypes);
        parcel.writeInt(this.mLinkUpBandwidthKbps);
        parcel.writeInt(this.mLinkDownBandwidthKbps);
        parcel.writeParcelable((Parcelable)((Object)this.mNetworkSpecifier), n);
        parcel.writeParcelable((Parcelable)((Object)this.mTransportInfo), n);
        parcel.writeInt(this.mSignalStrength);
        parcel.writeArraySet(this.mUids);
        parcel.writeString(this.mSSID);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        int n;
        l = protoOutputStream.start(l);
        Object object = this.getTransportTypes();
        int n2 = ((int[])object).length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            protoOutputStream.write(2259152797697L, object[n]);
        }
        object = this.getCapabilities();
        n2 = ((int[])object).length;
        for (n = n3; n < n2; ++n) {
            protoOutputStream.write(2259152797698L, object[n]);
        }
        protoOutputStream.write(1120986464259L, this.mLinkUpBandwidthKbps);
        protoOutputStream.write(1120986464260L, this.mLinkDownBandwidthKbps);
        object = this.mNetworkSpecifier;
        if (object != null) {
            protoOutputStream.write(1138166333445L, object.toString());
        }
        object = this.mTransportInfo;
        protoOutputStream.write(1133871366150L, this.hasSignalStrength());
        protoOutputStream.write(1172526071815L, this.mSignalStrength);
        protoOutputStream.end(l);
    }

    private static interface NameOf {
        public String nameOf(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetCapability {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Transport {
    }

}

