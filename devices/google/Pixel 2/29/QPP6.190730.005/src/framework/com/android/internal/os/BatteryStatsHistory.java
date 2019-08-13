/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.BatteryStats;
import android.os.Parcel;
import android.os.StatFs;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.AtomicFile;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.util.ParseUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class BatteryStatsHistory {
    private static final boolean DEBUG = false;
    public static final String FILE_SUFFIX = ".bin";
    public static final String HISTORY_DIR = "battery-history";
    private static final int MIN_FREE_SPACE = 104857600;
    private static final String TAG = "BatteryStatsHistory";
    private AtomicFile mActiveFile;
    private int mCurrentFileIndex;
    private Parcel mCurrentParcel;
    private int mCurrentParcelEnd;
    private final List<Integer> mFileNumbers = new ArrayList<Integer>();
    private final Parcel mHistoryBuffer;
    private final File mHistoryDir;
    private List<Parcel> mHistoryParcels = null;
    private int mParcelIndex = 0;
    private int mRecordCount = 0;
    private final BatteryStatsImpl mStats;

    public BatteryStatsHistory(BatteryStatsImpl batteryStatsImpl, Parcel parcel) {
        this.mStats = batteryStatsImpl;
        this.mHistoryDir = null;
        this.mHistoryBuffer = parcel;
    }

    public BatteryStatsHistory(BatteryStatsImpl collection, File file, Parcel parcel) {
        this.mStats = collection;
        this.mHistoryBuffer = parcel;
        this.mHistoryDir = new File(file, HISTORY_DIR);
        this.mHistoryDir.mkdirs();
        if (!this.mHistoryDir.exists()) {
            collection = new StringBuilder();
            ((StringBuilder)((Object)collection)).append("HistoryDir does not exist:");
            ((StringBuilder)((Object)collection)).append(this.mHistoryDir.getPath());
            Slog.wtf(TAG, ((StringBuilder)((Object)collection)).toString());
        }
        collection = new ArraySet();
        this.mHistoryDir.listFiles(new FilenameFilter((Set)collection){
            final /* synthetic */ Set val$dedup;
            {
                this.val$dedup = set;
            }

            @Override
            public boolean accept(File comparable, String string2) {
                int n = string2.lastIndexOf(BatteryStatsHistory.FILE_SUFFIX);
                if (n <= 0) {
                    return false;
                }
                comparable = ParseUtils.parseInt(string2.substring(0, n), -1);
                if ((Integer)comparable != -1) {
                    this.val$dedup.add(comparable);
                    return true;
                }
                return false;
            }
        });
        if (!collection.isEmpty()) {
            this.mFileNumbers.addAll(collection);
            Collections.sort(this.mFileNumbers);
            collection = this.mFileNumbers;
            this.setActiveFile((Integer)collection.get(collection.size() - 1));
        } else {
            this.mFileNumbers.add(0);
            this.setActiveFile(0);
        }
    }

    private AtomicFile getFile(int n) {
        File file = this.mHistoryDir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(FILE_SUFFIX);
        return new AtomicFile(new File(file, stringBuilder.toString()));
    }

    private boolean hasFreeDiskSpace() {
        boolean bl = new StatFs(this.mHistoryDir.getAbsolutePath()).getAvailableBytes() > 104857600L;
        return bl;
    }

    private void setActiveFile(int n) {
        this.mActiveFile = this.getFile(n);
    }

    private boolean skipHead(Parcel parcel) {
        parcel.setDataPosition(0);
        int n = parcel.readInt();
        BatteryStatsImpl batteryStatsImpl = this.mStats;
        if (n != 186) {
            return false;
        }
        parcel.readLong();
        return true;
    }

    public void finishIteratingHistory() {
        Parcel parcel = this.mHistoryBuffer;
        parcel.setDataPosition(parcel.dataSize());
    }

    public AtomicFile getActiveFile() {
        return this.mActiveFile;
    }

    public List<Integer> getFilesNumbers() {
        return this.mFileNumbers;
    }

    public int getHistoryUsedSize() {
        int n;
        int n2 = 0;
        for (n = 0; n < this.mFileNumbers.size() - 1; ++n) {
            n2 = (int)((long)n2 + this.getFile(this.mFileNumbers.get(n)).getBaseFile().length());
        }
        int n3 = n = n2 + this.mHistoryBuffer.dataSize();
        if (this.mHistoryParcels != null) {
            n2 = 0;
            do {
                n3 = n;
                if (n2 >= this.mHistoryParcels.size()) break;
                n += this.mHistoryParcels.get(n2).dataSize();
                ++n2;
            } while (true);
        }
        return n3;
    }

    public Parcel getNextParcel(BatteryStats.HistoryItem object) {
        int n;
        List<Parcel> list;
        int n2;
        if (this.mRecordCount == 0) {
            ((BatteryStats.HistoryItem)object).clear();
        }
        ++this.mRecordCount;
        object = this.mCurrentParcel;
        if (object != null) {
            if (((Parcel)object).dataPosition() < this.mCurrentParcelEnd) {
                return this.mCurrentParcel;
            }
            list = this.mHistoryBuffer;
            object = this.mCurrentParcel;
            if (list == object) {
                return null;
            }
            list = this.mHistoryParcels;
            if (list == null || !list.contains(object)) {
                this.mCurrentParcel.recycle();
            }
        }
        while (this.mCurrentFileIndex < this.mFileNumbers.size() - 1) {
            this.mCurrentParcel = null;
            this.mCurrentParcelEnd = 0;
            object = Parcel.obtain();
            list = this.mFileNumbers;
            n = this.mCurrentFileIndex;
            this.mCurrentFileIndex = n + 1;
            if (this.readFileToParcel((Parcel)object, this.getFile(list.get(n)))) {
                n = ((Parcel)object).readInt();
                n2 = ((Parcel)object).dataPosition();
                this.mCurrentParcelEnd = n2 + n;
                this.mCurrentParcel = object;
                if (n2 >= this.mCurrentParcelEnd) continue;
                return this.mCurrentParcel;
            }
            ((Parcel)object).recycle();
        }
        if (this.mHistoryParcels != null) {
            while (this.mParcelIndex < this.mHistoryParcels.size()) {
                object = this.mHistoryParcels;
                n = this.mParcelIndex;
                this.mParcelIndex = n + 1;
                if (!this.skipHead((Parcel)(object = object.get(n)))) continue;
                n2 = ((Parcel)object).readInt();
                n = ((Parcel)object).dataPosition();
                this.mCurrentParcelEnd = n + n2;
                this.mCurrentParcel = object;
                if (n >= this.mCurrentParcelEnd) continue;
                return this.mCurrentParcel;
            }
        }
        if (this.mHistoryBuffer.dataSize() <= 0) {
            return null;
        }
        this.mHistoryBuffer.setDataPosition(0);
        this.mCurrentParcel = this.mHistoryBuffer;
        this.mCurrentParcelEnd = this.mCurrentParcel.dataSize();
        return this.mCurrentParcel;
    }

    public boolean readFileToParcel(Parcel object, AtomicFile atomicFile) {
        try {
            SystemClock.uptimeMillis();
            byte[] arrby = atomicFile.readFully();
            ((Parcel)object).unmarshall(arrby, 0, arrby.length);
            ((Parcel)object).setDataPosition(0);
            return this.skipHead((Parcel)object);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error reading file ");
            ((StringBuilder)object).append(atomicFile.getBaseFile().getPath());
            Slog.e(TAG, ((StringBuilder)object).toString(), exception);
            return false;
        }
    }

    public void readFromParcel(Parcel parcel) {
        SystemClock.uptimeMillis();
        this.mHistoryParcels = new ArrayList<Parcel>();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            byte[] arrby = parcel.createByteArray();
            if (arrby.length == 0) continue;
            Parcel parcel2 = Parcel.obtain();
            parcel2.unmarshall(arrby, 0, arrby.length);
            parcel2.setDataPosition(0);
            this.mHistoryParcels.add(parcel2);
        }
    }

    public void resetAllFiles() {
        Iterator<Integer> iterator = this.mFileNumbers.iterator();
        while (iterator.hasNext()) {
            this.getFile(iterator.next()).delete();
        }
        this.mFileNumbers.clear();
        this.mFileNumbers.add(0);
        this.setActiveFile(0);
    }

    public boolean startIteratingHistory() {
        this.mRecordCount = 0;
        this.mCurrentFileIndex = 0;
        this.mCurrentParcel = null;
        this.mCurrentParcelEnd = 0;
        this.mParcelIndex = 0;
        return true;
    }

    public void startNextFile() {
        if (this.mFileNumbers.isEmpty()) {
            Slog.wtf(TAG, "mFileNumbers should never be empty");
            return;
        }
        List<Integer> list = this.mFileNumbers;
        int n = list.get(list.size() - 1) + 1;
        this.mFileNumbers.add(n);
        this.setActiveFile(n);
        if (!this.hasFreeDiskSpace()) {
            this.getFile(this.mFileNumbers.remove(0)).delete();
        }
        while (this.mFileNumbers.size() > this.mStats.mConstants.MAX_HISTORY_FILES) {
            this.getFile(this.mFileNumbers.get(0)).delete();
            this.mFileNumbers.remove(0);
        }
    }

    public void writeToParcel(Parcel parcel) {
        SystemClock.uptimeMillis();
        parcel.writeInt(this.mFileNumbers.size() - 1);
        for (int i = 0; i < this.mFileNumbers.size() - 1; ++i) {
            Object object;
            AtomicFile atomicFile = this.getFile(this.mFileNumbers.get(i));
            byte[] arrby = new byte[]{};
            try {
                object = atomicFile.readFully();
                arrby = object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error reading file ");
                ((StringBuilder)object).append(atomicFile.getBaseFile().getPath());
                Slog.e(TAG, ((StringBuilder)object).toString(), exception);
            }
            parcel.writeByteArray(arrby);
        }
    }

}

