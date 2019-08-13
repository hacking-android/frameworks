/*
 * Decompiled with CFR 0.145.
 */
package android.printservice.recommendation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
public final class RecommendationInfo
implements Parcelable {
    public static final Parcelable.Creator<RecommendationInfo> CREATOR = new Parcelable.Creator<RecommendationInfo>(){

        @Override
        public RecommendationInfo createFromParcel(Parcel parcel) {
            return new RecommendationInfo(parcel);
        }

        public RecommendationInfo[] newArray(int n) {
            return new RecommendationInfo[n];
        }
    };
    private final List<InetAddress> mDiscoveredPrinters;
    private final CharSequence mName;
    private final CharSequence mPackageName;
    private final boolean mRecommendsMultiVendorService;

    private RecommendationInfo(Parcel parcel) {
        CharSequence charSequence = parcel.readCharSequence();
        CharSequence charSequence2 = parcel.readCharSequence();
        ArrayList<InetAddress> arrayList = RecommendationInfo.readDiscoveredPrinters(parcel);
        boolean bl = parcel.readByte() != 0;
        this(charSequence, charSequence2, arrayList, bl);
    }

    @Deprecated
    public RecommendationInfo(CharSequence charSequence, CharSequence charSequence2, int n, boolean bl) {
        throw new IllegalArgumentException("This constructor has been deprecated");
    }

    public RecommendationInfo(CharSequence charSequence, CharSequence charSequence2, List<InetAddress> list, boolean bl) {
        this.mPackageName = Preconditions.checkStringNotEmpty(charSequence);
        this.mName = Preconditions.checkStringNotEmpty(charSequence2);
        this.mDiscoveredPrinters = Preconditions.checkCollectionElementsNotNull(list, "discoveredPrinters");
        this.mRecommendsMultiVendorService = bl;
    }

    private static ArrayList<InetAddress> readDiscoveredPrinters(Parcel parcel) {
        int n = parcel.readInt();
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>(n);
        for (int i = 0; i < n; ++i) {
            try {
                arrayList.add(InetAddress.getByAddress(parcel.readBlob()));
                continue;
            }
            catch (UnknownHostException unknownHostException) {
                throw new IllegalArgumentException(unknownHostException);
            }
        }
        return arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<InetAddress> getDiscoveredPrinters() {
        return this.mDiscoveredPrinters;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public int getNumDiscoveredPrinters() {
        return this.mDiscoveredPrinters.size();
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public boolean recommendsMultiVendorService() {
        return this.mRecommendsMultiVendorService;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mPackageName);
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mDiscoveredPrinters.size());
        Iterator<InetAddress> iterator = this.mDiscoveredPrinters.iterator();
        while (iterator.hasNext()) {
            parcel.writeBlob(iterator.next().getAddress());
        }
        parcel.writeByte((byte)(this.mRecommendsMultiVendorService ? 1 : 0));
    }

}

