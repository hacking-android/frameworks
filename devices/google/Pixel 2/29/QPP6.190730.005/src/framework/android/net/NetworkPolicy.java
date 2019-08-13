/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.NetworkTemplate;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.BackupUtils;
import android.util.Range;
import android.util.RecurrenceRule;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Objects;

public class NetworkPolicy
implements Parcelable,
Comparable<NetworkPolicy> {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkPolicy> CREATOR = new Parcelable.Creator<NetworkPolicy>(){

        @Override
        public NetworkPolicy createFromParcel(Parcel parcel) {
            return new NetworkPolicy(parcel);
        }

        public NetworkPolicy[] newArray(int n) {
            return new NetworkPolicy[n];
        }
    };
    public static final int CYCLE_NONE = -1;
    private static final long DEFAULT_MTU = 1500L;
    public static final long LIMIT_DISABLED = -1L;
    public static final long SNOOZE_NEVER = -1L;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_RAPID = 3;
    private static final int VERSION_RULE = 2;
    public static final long WARNING_DISABLED = -1L;
    public RecurrenceRule cycleRule;
    @UnsupportedAppUsage
    public boolean inferred;
    public long lastLimitSnooze = -1L;
    public long lastRapidSnooze = -1L;
    public long lastWarningSnooze = -1L;
    @UnsupportedAppUsage
    public long limitBytes = -1L;
    @Deprecated
    @UnsupportedAppUsage
    public boolean metered;
    @UnsupportedAppUsage
    public NetworkTemplate template;
    @UnsupportedAppUsage
    public long warningBytes = -1L;

    @Deprecated
    @UnsupportedAppUsage
    public NetworkPolicy(NetworkTemplate networkTemplate, int n, String string2, long l, long l2, long l3, long l4, boolean bl, boolean bl2) {
        this(networkTemplate, NetworkPolicy.buildRule(n, ZoneId.of(string2)), l, l2, l3, l4, bl, bl2);
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate networkTemplate, int n, String string2, long l, long l2, boolean bl) {
        this(networkTemplate, n, string2, l, l2, -1L, -1L, bl, false);
    }

    public NetworkPolicy(NetworkTemplate networkTemplate, RecurrenceRule recurrenceRule, long l, long l2, long l3, long l4, long l5, boolean bl, boolean bl2) {
        this.metered = true;
        this.inferred = false;
        this.template = Preconditions.checkNotNull(networkTemplate, "missing NetworkTemplate");
        this.cycleRule = Preconditions.checkNotNull(recurrenceRule, "missing RecurrenceRule");
        this.warningBytes = l;
        this.limitBytes = l2;
        this.lastWarningSnooze = l3;
        this.lastLimitSnooze = l4;
        this.lastRapidSnooze = l5;
        this.metered = bl;
        this.inferred = bl2;
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate networkTemplate, RecurrenceRule recurrenceRule, long l, long l2, long l3, long l4, boolean bl, boolean bl2) {
        this(networkTemplate, recurrenceRule, l, l2, l3, l4, -1L, bl, bl2);
    }

    private NetworkPolicy(Parcel parcel) {
        boolean bl = true;
        this.metered = true;
        this.inferred = false;
        this.template = (NetworkTemplate)parcel.readParcelable(null);
        this.cycleRule = (RecurrenceRule)parcel.readParcelable(null);
        this.warningBytes = parcel.readLong();
        this.limitBytes = parcel.readLong();
        this.lastWarningSnooze = parcel.readLong();
        this.lastLimitSnooze = parcel.readLong();
        this.lastRapidSnooze = parcel.readLong();
        boolean bl2 = parcel.readInt() != 0;
        this.metered = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.inferred = bl2;
    }

    public static RecurrenceRule buildRule(int n, ZoneId zoneId) {
        if (n != -1) {
            return RecurrenceRule.buildRecurringMonthly(n, zoneId);
        }
        return RecurrenceRule.buildNever();
    }

    public static NetworkPolicy getNetworkPolicyFromBackup(DataInputStream object) throws IOException, BackupUtils.BadVersionException {
        int n = ((DataInputStream)object).readInt();
        if (n >= 1 && n <= 3) {
            NetworkTemplate networkTemplate = NetworkTemplate.getNetworkTemplateFromBackup((DataInputStream)object);
            RecurrenceRule recurrenceRule = n >= 2 ? new RecurrenceRule((DataInputStream)object) : NetworkPolicy.buildRule(((DataInputStream)object).readInt(), ZoneId.of(BackupUtils.readString((DataInputStream)object)));
            long l = ((DataInputStream)object).readLong();
            long l2 = ((DataInputStream)object).readLong();
            long l3 = ((DataInputStream)object).readLong();
            long l4 = ((DataInputStream)object).readLong();
            long l5 = n >= 3 ? ((DataInputStream)object).readLong() : -1L;
            boolean bl = ((DataInputStream)object).readInt() == 1;
            boolean bl2 = ((DataInputStream)object).readInt() == 1;
            return new NetworkPolicy(networkTemplate, recurrenceRule, l, l2, l3, l4, l5, bl, bl2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown backup version: ");
        ((StringBuilder)object).append(n);
        throw new BackupUtils.BadVersionException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public void clearSnooze() {
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
    }

    @UnsupportedAppUsage
    @Override
    public int compareTo(NetworkPolicy networkPolicy) {
        long l;
        if (networkPolicy != null && (l = networkPolicy.limitBytes) != -1L) {
            long l2 = this.limitBytes;
            return l2 == -1L || l < l2;
            {
            }
        }
        return -1;
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof NetworkPolicy;
        boolean bl2 = false;
        if (bl) {
            object = (NetworkPolicy)object;
            if (this.warningBytes == ((NetworkPolicy)object).warningBytes && this.limitBytes == ((NetworkPolicy)object).limitBytes && this.lastWarningSnooze == ((NetworkPolicy)object).lastWarningSnooze && this.lastLimitSnooze == ((NetworkPolicy)object).lastLimitSnooze && this.lastRapidSnooze == ((NetworkPolicy)object).lastRapidSnooze && this.metered == ((NetworkPolicy)object).metered && this.inferred == ((NetworkPolicy)object).inferred && Objects.equals(this.template, ((NetworkPolicy)object).template) && Objects.equals(this.cycleRule, ((NetworkPolicy)object).cycleRule)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public byte[] getBytesForBackup() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.write(this.template.getBytesForBackup());
        this.cycleRule.writeToStream(dataOutputStream);
        dataOutputStream.writeLong(this.warningBytes);
        dataOutputStream.writeLong(this.limitBytes);
        dataOutputStream.writeLong(this.lastWarningSnooze);
        dataOutputStream.writeLong(this.lastLimitSnooze);
        dataOutputStream.writeLong(this.lastRapidSnooze);
        dataOutputStream.writeInt((int)this.metered);
        dataOutputStream.writeInt((int)this.inferred);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean hasCycle() {
        return this.cycleRule.cycleIterator().hasNext();
    }

    public int hashCode() {
        return Objects.hash(this.template, this.cycleRule, this.warningBytes, this.limitBytes, this.lastWarningSnooze, this.lastLimitSnooze, this.lastRapidSnooze, this.metered, this.inferred);
    }

    @UnsupportedAppUsage
    public boolean isOverLimit(long l) {
        long l2 = this.limitBytes;
        boolean bl = l2 != -1L && l + 3000L >= l2;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isOverWarning(long l) {
        long l2 = this.warningBytes;
        boolean bl = l2 != -1L && l >= l2;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkPolicy{");
        stringBuilder.append("template=");
        stringBuilder.append(this.template);
        stringBuilder.append(" cycleRule=");
        stringBuilder.append(this.cycleRule);
        stringBuilder.append(" warningBytes=");
        stringBuilder.append(this.warningBytes);
        stringBuilder.append(" limitBytes=");
        stringBuilder.append(this.limitBytes);
        stringBuilder.append(" lastWarningSnooze=");
        stringBuilder.append(this.lastWarningSnooze);
        stringBuilder.append(" lastLimitSnooze=");
        stringBuilder.append(this.lastLimitSnooze);
        stringBuilder.append(" lastRapidSnooze=");
        stringBuilder.append(this.lastRapidSnooze);
        stringBuilder.append(" metered=");
        stringBuilder.append(this.metered);
        stringBuilder.append(" inferred=");
        stringBuilder.append(this.inferred);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.template, n);
        parcel.writeParcelable(this.cycleRule, n);
        parcel.writeLong(this.warningBytes);
        parcel.writeLong(this.limitBytes);
        parcel.writeLong(this.lastWarningSnooze);
        parcel.writeLong(this.lastLimitSnooze);
        parcel.writeLong(this.lastRapidSnooze);
        parcel.writeInt((int)this.metered);
        parcel.writeInt((int)this.inferred);
    }

}

