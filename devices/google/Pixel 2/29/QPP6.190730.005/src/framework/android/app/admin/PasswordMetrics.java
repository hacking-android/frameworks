/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PasswordMetrics
implements Parcelable {
    private static final int CHAR_DIGIT = 2;
    private static final int CHAR_LOWER_CASE = 0;
    private static final int CHAR_SYMBOL = 3;
    private static final int CHAR_UPPER_CASE = 1;
    public static final Parcelable.Creator<PasswordMetrics> CREATOR = new Parcelable.Creator<PasswordMetrics>(){

        @Override
        public PasswordMetrics createFromParcel(Parcel parcel) {
            return new PasswordMetrics(parcel);
        }

        public PasswordMetrics[] newArray(int n) {
            return new PasswordMetrics[n];
        }
    };
    public static final int MAX_ALLOWED_SEQUENCE = 3;
    public int length = 0;
    public int letters = 0;
    public int lowerCase = 0;
    public int nonLetter = 0;
    public int numeric = 0;
    public int quality = 0;
    public int symbols = 0;
    public int upperCase = 0;

    public PasswordMetrics() {
    }

    public PasswordMetrics(int n) {
        this.quality = n;
    }

    public PasswordMetrics(int n, int n2) {
        this.quality = n;
        this.length = n2;
    }

    public PasswordMetrics(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this(n, n2);
        this.letters = n3;
        this.upperCase = n4;
        this.lowerCase = n5;
        this.numeric = n6;
        this.symbols = n7;
        this.nonLetter = n8;
    }

    private PasswordMetrics(Parcel parcel) {
        this.quality = parcel.readInt();
        this.length = parcel.readInt();
        this.letters = parcel.readInt();
        this.upperCase = parcel.readInt();
        this.lowerCase = parcel.readInt();
        this.numeric = parcel.readInt();
        this.symbols = parcel.readInt();
        this.nonLetter = parcel.readInt();
    }

    private static int categoryChar(char c) {
        if ('a' <= c && c <= 'z') {
            return 0;
        }
        if ('A' <= c && c <= 'Z') {
            return 1;
        }
        if ('0' <= c && c <= '9') {
            return 2;
        }
        return 3;
    }

    public static int complexityLevelToMinQuality(int n) {
        return PasswordComplexityBucket.access$100((PasswordComplexityBucket)PasswordComplexityBucket.access$000((int)n))[0].quality;
    }

    public static PasswordMetrics computeForCredential(int n, byte[] arrby) {
        if (n == 2) {
            Preconditions.checkNotNull(arrby, "credential cannot be null");
            return PasswordMetrics.computeForPassword(arrby);
        }
        if (n == 1) {
            return new PasswordMetrics(65536);
        }
        return new PasswordMetrics(0);
    }

    public static PasswordMetrics computeForPassword(byte[] arrby) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = arrby.length;
        int n7 = arrby.length;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        for (n2 = 0; n2 < n7; ++n2) {
            n = PasswordMetrics.categoryChar((char)arrby[n2]);
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            n = n11;
                        } else {
                            ++n9;
                            ++n5;
                            n = n11;
                        }
                    } else {
                        ++n4;
                        ++n5;
                        n = n11;
                    }
                } else {
                    n = n11 + 1;
                    ++n10;
                }
            } else {
                n = n11 + 1;
                ++n3;
            }
            n11 = n;
        }
        n = n4 > 0 ? 1 : 0;
        n2 = n8;
        if (n11 + n9 > 0) {
            n2 = 1;
        }
        n = n2 != 0 && n != 0 ? 327680 : (n2 != 0 ? 262144 : (n != 0 ? (PasswordMetrics.maxLengthSequence(arrby) > 3 ? 131072 : 196608) : 0));
        return new PasswordMetrics(n, n6, n11, n10, n3, n4, n9, n5);
    }

    @VisibleForTesting
    public static int getActualRequiredQuality(int n, boolean bl, boolean bl2) {
        if (n != 393216) {
            return n;
        }
        if (bl && bl2) {
            return 327680;
        }
        if (bl2) {
            return 262144;
        }
        if (bl) {
            return 131072;
        }
        return 0;
    }

    public static PasswordMetrics getMinimumMetrics(int n, int n2, int n3, boolean bl, boolean bl2) {
        return PasswordMetrics.getTargetQualityMetrics(n, Math.max(n2, PasswordMetrics.getActualRequiredQuality(n3, bl, bl2)));
    }

    @VisibleForTesting
    public static PasswordMetrics getTargetQualityMetrics(int n, int n2) {
        PasswordComplexityBucket passwordComplexityBucket = PasswordComplexityBucket.complexityLevelToBucket(n);
        for (PasswordMetrics passwordMetrics : passwordComplexityBucket.mMetrics) {
            if (n2 != passwordMetrics.quality) continue;
            return passwordMetrics;
        }
        return passwordComplexityBucket.mMetrics[0];
    }

    private static int maxDiffCategory(int n) {
        if (n != 0 && n != 1) {
            if (n != 2) {
                return 0;
            }
            return 10;
        }
        return 1;
    }

    public static int maxLengthSequence(byte[] arrby) {
        if (arrby.length == 0) {
            return 0;
        }
        int n = arrby[0];
        int n2 = PasswordMetrics.categoryChar((char)n);
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = n;
        for (int i = 1; i < arrby.length; ++i) {
            int n8;
            n = (char)arrby[i];
            int n9 = PasswordMetrics.categoryChar((char)n);
            int n10 = n - n7;
            if (n9 == n2 && Math.abs(n10) <= PasswordMetrics.maxDiffCategory(n2)) {
                n7 = n5;
                n8 = n6;
                if (n4 != 0) {
                    n7 = n5;
                    n8 = n6;
                    if (n10 != n3) {
                        n7 = Math.max(n5, i - n6);
                        n8 = i - 1;
                    }
                }
                n3 = n10;
                n6 = 1;
                n5 = n7;
            } else {
                n5 = Math.max(n5, i - n6);
                n8 = i;
                n6 = 0;
                n2 = n9;
            }
            n7 = n;
            n4 = n6;
            n6 = n8;
        }
        return Math.max(n5, arrby.length - n6);
    }

    public static int sanitizeComplexityLevel(int n) {
        return PasswordComplexityBucket.complexityLevelToBucket(n).mComplexityLevel;
    }

    private boolean satisfiesBucket(PasswordMetrics ... arrpasswordMetrics) {
        int n = arrpasswordMetrics.length;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            PasswordMetrics passwordMetrics = arrpasswordMetrics[i];
            if (this.quality != passwordMetrics.quality) continue;
            if (this.length >= passwordMetrics.length) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int determineComplexity() {
        for (PasswordComplexityBucket passwordComplexityBucket : PasswordComplexityBucket.BUCKETS) {
            if (!this.satisfiesBucket(passwordComplexityBucket.mMetrics)) continue;
            return passwordComplexityBucket.mComplexityLevel;
        }
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof PasswordMetrics;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (PasswordMetrics)object;
        bl = bl2;
        if (this.quality == ((PasswordMetrics)object).quality) {
            bl = bl2;
            if (this.length == ((PasswordMetrics)object).length) {
                bl = bl2;
                if (this.letters == ((PasswordMetrics)object).letters) {
                    bl = bl2;
                    if (this.upperCase == ((PasswordMetrics)object).upperCase) {
                        bl = bl2;
                        if (this.lowerCase == ((PasswordMetrics)object).lowerCase) {
                            bl = bl2;
                            if (this.numeric == ((PasswordMetrics)object).numeric) {
                                bl = bl2;
                                if (this.symbols == ((PasswordMetrics)object).symbols) {
                                    bl = bl2;
                                    if (this.nonLetter == ((PasswordMetrics)object).nonLetter) {
                                        bl = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    public boolean isDefault() {
        boolean bl = this.quality == 0 && this.length == 0 && this.letters == 0 && this.upperCase == 0 && this.lowerCase == 0 && this.numeric == 0 && this.symbols == 0 && this.nonLetter == 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.quality);
        parcel.writeInt(this.length);
        parcel.writeInt(this.letters);
        parcel.writeInt(this.upperCase);
        parcel.writeInt(this.lowerCase);
        parcel.writeInt(this.numeric);
        parcel.writeInt(this.symbols);
        parcel.writeInt(this.nonLetter);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface CharacterCatagory {
    }

    private static class PasswordComplexityBucket {
        private static final PasswordComplexityBucket[] BUCKETS;
        private static final PasswordComplexityBucket HIGH;
        private static final PasswordComplexityBucket LOW;
        private static final PasswordComplexityBucket MEDIUM;
        private static final PasswordComplexityBucket NONE;
        private final int mComplexityLevel;
        private final PasswordMetrics[] mMetrics;

        static {
            HIGH = new PasswordComplexityBucket(327680, new PasswordMetrics(196608, 8), new PasswordMetrics(262144, 6), new PasswordMetrics(327680, 6));
            MEDIUM = new PasswordComplexityBucket(196608, new PasswordMetrics(196608, 4), new PasswordMetrics(262144, 4), new PasswordMetrics(327680, 4));
            LOW = new PasswordComplexityBucket(65536, new PasswordMetrics(65536), new PasswordMetrics(131072), new PasswordMetrics(196608), new PasswordMetrics(262144), new PasswordMetrics(327680));
            NONE = new PasswordComplexityBucket(0, new PasswordMetrics());
            BUCKETS = new PasswordComplexityBucket[]{HIGH, MEDIUM, LOW};
        }

        private PasswordComplexityBucket(int n, PasswordMetrics ... arrpasswordMetrics) {
            int n2 = 0;
            int n3 = arrpasswordMetrics.length;
            for (int i = 0; i < n3; ++i) {
                PasswordMetrics passwordMetrics = arrpasswordMetrics[i];
                if (passwordMetrics.quality >= n2) {
                    n2 = passwordMetrics.quality;
                    continue;
                }
                throw new IllegalArgumentException("metricsArray must be sorted in ascending order of quality");
            }
            this.mMetrics = arrpasswordMetrics;
            this.mComplexityLevel = n;
        }

        private static PasswordComplexityBucket complexityLevelToBucket(int n) {
            for (PasswordComplexityBucket passwordComplexityBucket : BUCKETS) {
                if (passwordComplexityBucket.mComplexityLevel != n) continue;
                return passwordComplexityBucket;
            }
            return NONE;
        }
    }

}

