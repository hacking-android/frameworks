/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class FieldClassification {
    private final ArrayList<Match> mMatches;

    public FieldClassification(ArrayList<Match> arrayList) {
        this.mMatches = Preconditions.checkNotNull(arrayList);
        Collections.sort(this.mMatches, new Comparator<Match>(){

            @Override
            public int compare(Match match, Match match2) {
                if (match.mScore > match2.mScore) {
                    return -1;
                }
                return match.mScore < match2.mScore;
            }
        });
    }

    static FieldClassification[] readArrayFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        FieldClassification[] arrfieldClassification = new FieldClassification[n];
        for (int i = 0; i < n; ++i) {
            arrfieldClassification[i] = FieldClassification.readFromParcel(parcel);
        }
        return arrfieldClassification;
    }

    private static FieldClassification readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        ArrayList<Match> arrayList = new ArrayList<Match>();
        for (int i = 0; i < n; ++i) {
            arrayList.add(i, Match.readFromParcel(parcel));
        }
        return new FieldClassification(arrayList);
    }

    static void writeArrayToParcel(Parcel parcel, FieldClassification[] arrfieldClassification) {
        parcel.writeInt(arrfieldClassification.length);
        for (int i = 0; i < arrfieldClassification.length; ++i) {
            arrfieldClassification[i].writeToParcel(parcel);
        }
    }

    private void writeToParcel(Parcel parcel) {
        parcel.writeInt(this.mMatches.size());
        for (int i = 0; i < this.mMatches.size(); ++i) {
            this.mMatches.get(i).writeToParcel(parcel);
        }
    }

    public List<Match> getMatches() {
        return this.mMatches;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldClassification: ");
        stringBuilder.append(this.mMatches);
        return stringBuilder.toString();
    }

    public static final class Match {
        private final String mCategoryId;
        private final float mScore;

        public Match(String string2, float f) {
            this.mCategoryId = Preconditions.checkNotNull(string2);
            this.mScore = f;
        }

        private static Match readFromParcel(Parcel parcel) {
            return new Match(parcel.readString(), parcel.readFloat());
        }

        private void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mCategoryId);
            parcel.writeFloat(this.mScore);
        }

        public String getCategoryId() {
            return this.mCategoryId;
        }

        public float getScore() {
            return this.mScore;
        }

        public String toString() {
            if (!Helper.sDebug) {
                return super.toString();
            }
            StringBuilder stringBuilder = new StringBuilder("Match: categoryId=");
            Helper.appendRedacted(stringBuilder, this.mCategoryId);
            stringBuilder.append(", score=");
            stringBuilder.append(this.mScore);
            return stringBuilder.toString();
        }
    }

}

