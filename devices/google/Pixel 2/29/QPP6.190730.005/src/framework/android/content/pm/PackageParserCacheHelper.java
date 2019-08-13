/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PackageParserCacheHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "PackageParserCacheHelper";

    private PackageParserCacheHelper() {
    }

    public static class ReadHelper
    extends Parcel.ReadWriteHelper {
        private final Parcel mParcel;
        private final ArrayList<String> mStrings = new ArrayList();

        public ReadHelper(Parcel parcel) {
            this.mParcel = parcel;
        }

        @Override
        public String readString(Parcel parcel) {
            return this.mStrings.get(parcel.readInt());
        }

        public void startAndInstall() {
            this.mStrings.clear();
            int n = this.mParcel.readInt();
            int n2 = this.mParcel.dataPosition();
            this.mParcel.setDataPosition(n);
            this.mParcel.readStringList(this.mStrings);
            this.mParcel.setDataPosition(n2);
            this.mParcel.setReadWriteHelper(this);
        }
    }

    public static class WriteHelper
    extends Parcel.ReadWriteHelper {
        private final HashMap<String, Integer> mIndexes = new HashMap();
        private final Parcel mParcel;
        private final int mStartPos;
        private final ArrayList<String> mStrings = new ArrayList();

        public WriteHelper(Parcel parcel) {
            this.mParcel = parcel;
            this.mStartPos = parcel.dataPosition();
            this.mParcel.writeInt(0);
            this.mParcel.setReadWriteHelper(this);
        }

        public void finishAndUninstall() {
            this.mParcel.setReadWriteHelper(null);
            int n = this.mParcel.dataPosition();
            this.mParcel.writeStringList(this.mStrings);
            this.mParcel.setDataPosition(this.mStartPos);
            this.mParcel.writeInt(n);
            Parcel parcel = this.mParcel;
            parcel.setDataPosition(parcel.dataSize());
        }

        @Override
        public void writeString(Parcel parcel, String string2) {
            Integer n = this.mIndexes.get(string2);
            if (n != null) {
                parcel.writeInt(n);
            } else {
                int n2 = this.mStrings.size();
                this.mIndexes.put(string2, n2);
                this.mStrings.add(string2);
                parcel.writeInt(n2);
            }
        }
    }

}

