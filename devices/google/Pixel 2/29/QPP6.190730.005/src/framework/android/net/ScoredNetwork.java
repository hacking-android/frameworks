/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.net.NetworkKey;
import android.net.RssiCurve;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.Set;

@SystemApi
public class ScoredNetwork
implements Parcelable {
    public static final String ATTRIBUTES_KEY_BADGING_CURVE = "android.net.attributes.key.BADGING_CURVE";
    public static final String ATTRIBUTES_KEY_HAS_CAPTIVE_PORTAL = "android.net.attributes.key.HAS_CAPTIVE_PORTAL";
    public static final String ATTRIBUTES_KEY_RANKING_SCORE_OFFSET = "android.net.attributes.key.RANKING_SCORE_OFFSET";
    public static final Parcelable.Creator<ScoredNetwork> CREATOR = new Parcelable.Creator<ScoredNetwork>(){

        @Override
        public ScoredNetwork createFromParcel(Parcel parcel) {
            return new ScoredNetwork(parcel);
        }

        public ScoredNetwork[] newArray(int n) {
            return new ScoredNetwork[n];
        }
    };
    public final Bundle attributes;
    public final boolean meteredHint;
    public final NetworkKey networkKey;
    public final RssiCurve rssiCurve;

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve) {
        this(networkKey, rssiCurve, false);
    }

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve, boolean bl) {
        this(networkKey, rssiCurve, bl, null);
    }

    public ScoredNetwork(NetworkKey networkKey, RssiCurve rssiCurve, boolean bl, Bundle bundle) {
        this.networkKey = networkKey;
        this.rssiCurve = rssiCurve;
        this.meteredHint = bl;
        this.attributes = bundle;
    }

    private ScoredNetwork(Parcel parcel) {
        this.networkKey = NetworkKey.CREATOR.createFromParcel(parcel);
        byte by = parcel.readByte();
        boolean bl = true;
        this.rssiCurve = by == 1 ? RssiCurve.CREATOR.createFromParcel(parcel) : null;
        if (parcel.readByte() != 1) {
            bl = false;
        }
        this.meteredHint = bl;
        this.attributes = parcel.readBundle();
    }

    private boolean bundleEquals(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (bundle != null && bundle2 != null) {
            if (bundle.size() != bundle2.size()) {
                return false;
            }
            for (String string2 : bundle.keySet()) {
                if (Objects.equals(bundle.get(string2), bundle2.get(string2))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int calculateBadge(int n) {
        Bundle bundle = this.attributes;
        if (bundle != null && bundle.containsKey(ATTRIBUTES_KEY_BADGING_CURVE)) {
            return ((RssiCurve)this.attributes.getParcelable(ATTRIBUTES_KEY_BADGING_CURVE)).lookupScore(n);
        }
        return 0;
    }

    public int calculateRankingScore(int n) throws UnsupportedOperationException {
        if (this.hasRankingScore()) {
            int n2 = 0;
            Parcelable parcelable = this.attributes;
            int n3 = 0;
            if (parcelable != null) {
                n2 = 0 + ((BaseBundle)((Object)parcelable)).getInt(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET, 0);
            }
            n = (parcelable = this.rssiCurve) == null ? n3 : ((RssiCurve)parcelable).lookupScore(n) << 8;
            try {
                n2 = Math.addExact(n, n2);
                return n2;
            }
            catch (ArithmeticException arithmeticException) {
                n = n < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                return n;
            }
        }
        throw new UnsupportedOperationException("Either rssiCurve or rankingScoreOffset is required to calculate the ranking score");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ScoredNetwork)object;
            if (!(Objects.equals(this.networkKey, ((ScoredNetwork)object).networkKey) && Objects.equals(this.rssiCurve, ((ScoredNetwork)object).rssiCurve) && Objects.equals(this.meteredHint, ((ScoredNetwork)object).meteredHint) && this.bundleEquals(this.attributes, ((ScoredNetwork)object).attributes))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public boolean hasRankingScore() {
        Bundle bundle;
        boolean bl = this.rssiCurve != null || (bundle = this.attributes) != null && bundle.containsKey(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET);
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.networkKey, this.rssiCurve, this.meteredHint, this.attributes);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScoredNetwork{networkKey=");
        stringBuilder.append(this.networkKey);
        stringBuilder.append(", rssiCurve=");
        stringBuilder.append(this.rssiCurve);
        stringBuilder.append(", meteredHint=");
        stringBuilder.append(this.meteredHint);
        stringBuilder = new StringBuilder(stringBuilder.toString());
        Object object = this.attributes;
        if (object != null && !((BaseBundle)object).isEmpty()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(", attributes=");
            ((StringBuilder)object).append(this.attributes);
            stringBuilder.append(((StringBuilder)object).toString());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.networkKey.writeToParcel(parcel, n);
        if (this.rssiCurve != null) {
            parcel.writeByte((byte)1);
            this.rssiCurve.writeToParcel(parcel, n);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeByte((byte)(this.meteredHint ? 1 : 0));
        parcel.writeBundle(this.attributes);
    }

}

