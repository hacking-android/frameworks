/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.pm.InstantAppIntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@SystemApi
public final class InstantAppResolveInfo
implements Parcelable {
    public static final Parcelable.Creator<InstantAppResolveInfo> CREATOR;
    private static final byte[] EMPTY_DIGEST;
    private static final String SHA_ALGORITHM = "SHA-256";
    private final InstantAppDigest mDigest;
    private final Bundle mExtras;
    private final List<InstantAppIntentFilter> mFilters;
    private final String mPackageName;
    private final boolean mShouldLetInstallerDecide;
    private final long mVersionCode;

    static {
        EMPTY_DIGEST = new byte[0];
        CREATOR = new Parcelable.Creator<InstantAppResolveInfo>(){

            @Override
            public InstantAppResolveInfo createFromParcel(Parcel parcel) {
                return new InstantAppResolveInfo(parcel);
            }

            public InstantAppResolveInfo[] newArray(int n) {
                return new InstantAppResolveInfo[n];
            }
        };
    }

    public InstantAppResolveInfo(InstantAppDigest instantAppDigest, String string2, List<InstantAppIntentFilter> list, int n) {
        this(instantAppDigest, string2, list, n, null);
    }

    public InstantAppResolveInfo(InstantAppDigest instantAppDigest, String string2, List<InstantAppIntentFilter> list, long l, Bundle bundle) {
        this(instantAppDigest, string2, list, l, bundle, false);
    }

    private InstantAppResolveInfo(InstantAppDigest instantAppDigest, String string2, List<InstantAppIntentFilter> list, long l, Bundle bundle, boolean bl) {
        if (string2 == null && list != null && list.size() != 0 || string2 != null && (list == null || list.size() == 0)) {
            throw new IllegalArgumentException();
        }
        this.mDigest = instantAppDigest;
        if (list != null) {
            this.mFilters = new ArrayList<InstantAppIntentFilter>(list.size());
            this.mFilters.addAll(list);
        } else {
            this.mFilters = null;
        }
        this.mPackageName = string2;
        this.mVersionCode = l;
        this.mExtras = bundle;
        this.mShouldLetInstallerDecide = bl;
    }

    public InstantAppResolveInfo(Bundle bundle) {
        this(InstantAppDigest.UNDEFINED, null, null, -1L, bundle, true);
    }

    InstantAppResolveInfo(Parcel parcel) {
        this.mShouldLetInstallerDecide = parcel.readBoolean();
        this.mExtras = parcel.readBundle();
        if (this.mShouldLetInstallerDecide) {
            this.mDigest = InstantAppDigest.UNDEFINED;
            this.mPackageName = null;
            this.mFilters = Collections.emptyList();
            this.mVersionCode = -1L;
        } else {
            this.mDigest = (InstantAppDigest)parcel.readParcelable(null);
            this.mPackageName = parcel.readString();
            this.mFilters = new ArrayList<InstantAppIntentFilter>();
            parcel.readList(this.mFilters, null);
            this.mVersionCode = parcel.readLong();
        }
    }

    public InstantAppResolveInfo(String string2, String string3, List<InstantAppIntentFilter> list) {
        this(new InstantAppDigest(string2), string3, list, -1L, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getDigestBytes() {
        byte[] arrby = this.mDigest.mDigestBytes.length > 0 ? this.mDigest.getDigestBytes()[0] : EMPTY_DIGEST;
        return arrby;
    }

    public int getDigestPrefix() {
        return this.mDigest.getDigestPrefix()[0];
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public List<InstantAppIntentFilter> getIntentFilters() {
        return this.mFilters;
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @Deprecated
    public int getVersionCode() {
        return (int)(this.mVersionCode & -1L);
    }

    public boolean shouldLetInstallerDecide() {
        return this.mShouldLetInstallerDecide;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.mShouldLetInstallerDecide);
        parcel.writeBundle(this.mExtras);
        if (this.mShouldLetInstallerDecide) {
            return;
        }
        parcel.writeParcelable(this.mDigest, n);
        parcel.writeString(this.mPackageName);
        parcel.writeList(this.mFilters);
        parcel.writeLong(this.mVersionCode);
    }

    @SystemApi
    public static final class InstantAppDigest
    implements Parcelable {
        public static final Parcelable.Creator<InstantAppDigest> CREATOR;
        static final int DIGEST_MASK = -4096;
        public static final InstantAppDigest UNDEFINED;
        private static Random sRandom;
        private final byte[][] mDigestBytes;
        private final int[] mDigestPrefix;
        private int[] mDigestPrefixSecure;

        static {
            UNDEFINED = new InstantAppDigest(new byte[0][], new int[0]);
            sRandom = null;
            try {
                sRandom = SecureRandom.getInstance("SHA1PRNG");
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                sRandom = new Random();
            }
            CREATOR = new Parcelable.Creator<InstantAppDigest>(){

                @Override
                public InstantAppDigest createFromParcel(Parcel parcel) {
                    if (parcel.readBoolean()) {
                        return UNDEFINED;
                    }
                    return new InstantAppDigest(parcel);
                }

                public InstantAppDigest[] newArray(int n) {
                    return new InstantAppDigest[n];
                }
            };
        }

        InstantAppDigest(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                this.mDigestBytes = null;
            } else {
                this.mDigestBytes = new byte[n][];
                for (int i = 0; i < n; ++i) {
                    this.mDigestBytes[i] = parcel.createByteArray();
                }
            }
            this.mDigestPrefix = parcel.createIntArray();
            this.mDigestPrefixSecure = parcel.createIntArray();
        }

        public InstantAppDigest(String string2) {
            this(string2, -1);
        }

        public InstantAppDigest(String arrby, int n) {
            if (arrby != null) {
                this.mDigestBytes = InstantAppDigest.generateDigest(arrby.toLowerCase(Locale.ENGLISH), n);
                this.mDigestPrefix = new int[this.mDigestBytes.length];
                for (n = 0; n < (arrby = this.mDigestBytes).length; ++n) {
                    int[] arrn = this.mDigestPrefix;
                    byte by = arrby[n][0];
                    byte by2 = arrby[n][1];
                    byte by3 = arrby[n][2];
                    arrn[n] = ((arrby[n][3] & 255) << 0 | ((by & 255) << 24 | (by2 & 255) << 16 | (by3 & 255) << 8)) & -4096;
                }
                return;
            }
            throw new IllegalArgumentException();
        }

        private InstantAppDigest(byte[][] arrby, int[] arrn) {
            this.mDigestPrefix = arrn;
            this.mDigestBytes = arrby;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static byte[][] generateDigest(String string2, int n) {
            ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(InstantAppResolveInfo.SHA_ALGORITHM);
                if (n <= 0) {
                    arrayList.add(messageDigest.digest(string2.getBytes()));
                    return (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
                }
                int n2 = string2.lastIndexOf(46, string2.lastIndexOf(46) - 1);
                if (n2 < 0) {
                    arrayList.add(messageDigest.digest(string2.getBytes()));
                    return (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
                }
                arrayList.add(messageDigest.digest(string2.substring(n2 + 1, string2.length()).getBytes()));
                int n3 = 1;
                while (n2 >= 0) {
                    if (n3 >= n) return (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
                    n2 = string2.lastIndexOf(46, n2 - 1);
                    arrayList.add(messageDigest.digest(string2.substring(n2 + 1, string2.length()).getBytes()));
                    ++n3;
                }
                return (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new IllegalStateException("could not find digest algorithm");
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public byte[][] getDigestBytes() {
            return this.mDigestBytes;
        }

        public int[] getDigestPrefix() {
            return this.mDigestPrefix;
        }

        public int[] getDigestPrefixSecure() {
            if (this == UNDEFINED) {
                return this.getDigestPrefix();
            }
            if (this.mDigestPrefixSecure == null) {
                int n;
                int n2 = n + 10 + sRandom.nextInt(10);
                this.mDigestPrefixSecure = Arrays.copyOf(this.getDigestPrefix(), n2);
                for (n = this.getDigestPrefix().length; n < n2; ++n) {
                    this.mDigestPrefixSecure[n] = sRandom.nextInt() & -4096;
                }
                Arrays.sort(this.mDigestPrefixSecure);
            }
            return this.mDigestPrefixSecure;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            boolean bl = this == UNDEFINED;
            parcel.writeBoolean(bl);
            if (bl) {
                return;
            }
            byte[][] arrby = this.mDigestBytes;
            if (arrby == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(arrby.length);
                for (n = 0; n < (arrby = this.mDigestBytes).length; ++n) {
                    parcel.writeByteArray(arrby[n]);
                }
            }
            parcel.writeIntArray(this.mDigestPrefix);
            parcel.writeIntArray(this.mDigestPrefixSecure);
        }

    }

}

