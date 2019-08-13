/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class RaEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<RaEvent> CREATOR = new Parcelable.Creator<RaEvent>(){

        @Override
        public RaEvent createFromParcel(Parcel parcel) {
            return new RaEvent(parcel);
        }

        public RaEvent[] newArray(int n) {
            return new RaEvent[n];
        }
    };
    private static final long NO_LIFETIME = -1L;
    public final long dnsslLifetime;
    public final long prefixPreferredLifetime;
    public final long prefixValidLifetime;
    public final long rdnssLifetime;
    public final long routeInfoLifetime;
    public final long routerLifetime;

    public RaEvent(long l, long l2, long l3, long l4, long l5, long l6) {
        this.routerLifetime = l;
        this.prefixValidLifetime = l2;
        this.prefixPreferredLifetime = l3;
        this.routeInfoLifetime = l4;
        this.rdnssLifetime = l5;
        this.dnsslLifetime = l6;
    }

    private RaEvent(Parcel parcel) {
        this.routerLifetime = parcel.readLong();
        this.prefixValidLifetime = parcel.readLong();
        this.prefixPreferredLifetime = parcel.readLong();
        this.routeInfoLifetime = parcel.readLong();
        this.rdnssLifetime = parcel.readLong();
        this.dnsslLifetime = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(RaEvent.class)) {
            object = (RaEvent)object;
            boolean bl2 = bl;
            if (this.routerLifetime == ((RaEvent)object).routerLifetime) {
                bl2 = bl;
                if (this.prefixValidLifetime == ((RaEvent)object).prefixValidLifetime) {
                    bl2 = bl;
                    if (this.prefixPreferredLifetime == ((RaEvent)object).prefixPreferredLifetime) {
                        bl2 = bl;
                        if (this.routeInfoLifetime == ((RaEvent)object).routeInfoLifetime) {
                            bl2 = bl;
                            if (this.rdnssLifetime == ((RaEvent)object).rdnssLifetime) {
                                bl2 = bl;
                                if (this.dnsslLifetime == ((RaEvent)object).dnsslLifetime) {
                                    bl2 = true;
                                }
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RaEvent(lifetimes: ");
        stringBuilder.append(String.format("router=%ds, ", this.routerLifetime));
        stringBuilder.append(String.format("prefix_valid=%ds, ", this.prefixValidLifetime));
        stringBuilder.append(String.format("prefix_preferred=%ds, ", this.prefixPreferredLifetime));
        stringBuilder.append(String.format("route_info=%ds, ", this.routeInfoLifetime));
        stringBuilder.append(String.format("rdnss=%ds, ", this.rdnssLifetime));
        stringBuilder.append(String.format("dnssl=%ds)", this.dnsslLifetime));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.routerLifetime);
        parcel.writeLong(this.prefixValidLifetime);
        parcel.writeLong(this.prefixPreferredLifetime);
        parcel.writeLong(this.routeInfoLifetime);
        parcel.writeLong(this.rdnssLifetime);
        parcel.writeLong(this.dnsslLifetime);
    }

    public static final class Builder {
        long dnsslLifetime = -1L;
        long prefixPreferredLifetime = -1L;
        long prefixValidLifetime = -1L;
        long rdnssLifetime = -1L;
        long routeInfoLifetime = -1L;
        long routerLifetime = -1L;

        private long updateLifetime(long l, long l2) {
            if (l == -1L) {
                return l2;
            }
            return Math.min(l, l2);
        }

        public RaEvent build() {
            return new RaEvent(this.routerLifetime, this.prefixValidLifetime, this.prefixPreferredLifetime, this.routeInfoLifetime, this.rdnssLifetime, this.dnsslLifetime);
        }

        public Builder updateDnsslLifetime(long l) {
            this.dnsslLifetime = this.updateLifetime(this.dnsslLifetime, l);
            return this;
        }

        public Builder updatePrefixPreferredLifetime(long l) {
            this.prefixPreferredLifetime = this.updateLifetime(this.prefixPreferredLifetime, l);
            return this;
        }

        public Builder updatePrefixValidLifetime(long l) {
            this.prefixValidLifetime = this.updateLifetime(this.prefixValidLifetime, l);
            return this;
        }

        public Builder updateRdnssLifetime(long l) {
            this.rdnssLifetime = this.updateLifetime(this.rdnssLifetime, l);
            return this;
        }

        public Builder updateRouteInfoLifetime(long l) {
            this.routeInfoLifetime = this.updateLifetime(this.routeInfoLifetime, l);
            return this;
        }

        public Builder updateRouterLifetime(long l) {
            this.routerLifetime = this.updateLifetime(this.routerLifetime, l);
            return this;
        }
    }

}

