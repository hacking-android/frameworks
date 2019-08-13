/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Process;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProcTimeInStateReader {
    private static final String TAG = "ProcTimeInStateReader";
    private static final List<Integer> TIME_IN_STATE_HEADER_LINE_FORMAT;
    private static final List<Integer> TIME_IN_STATE_LINE_FREQUENCY_FORMAT;
    private static final List<Integer> TIME_IN_STATE_LINE_TIME_FORMAT;
    private long[] mFrequenciesKhz;
    private int[] mTimeInStateTimeFormat;

    static {
        Integer n = 10;
        TIME_IN_STATE_LINE_FREQUENCY_FORMAT = Arrays.asList(8224, n);
        TIME_IN_STATE_LINE_TIME_FORMAT = Arrays.asList(32, 8202);
        TIME_IN_STATE_HEADER_LINE_FORMAT = Collections.singletonList(n);
    }

    public ProcTimeInStateReader(Path path) throws IOException {
        this.initializeTimeInStateFormat(path);
    }

    private void initializeTimeInStateFormat(Path iterable) throws IOException {
        byte[] arrby = Files.readAllBytes((Path)iterable);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        iterable = new ArrayList();
        int n = 0;
        for (int i = 0; i < arrby.length; ++i) {
            if (!Character.isDigit(arrby[i])) {
                arrayList.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
                ((ArrayList)iterable).addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
            } else {
                arrayList.addAll(TIME_IN_STATE_LINE_FREQUENCY_FORMAT);
                ((ArrayList)iterable).addAll(TIME_IN_STATE_LINE_TIME_FORMAT);
                ++n;
            }
            while (i < arrby.length && arrby[i] != 10) {
                ++i;
            }
        }
        if (n != 0) {
            long[] arrl = new long[n];
            if (Process.parseProcLine(arrby, 0, arrby.length, ArrayUtils.convertToIntArray(arrayList), null, arrl, null)) {
                this.mTimeInStateTimeFormat = ArrayUtils.convertToIntArray(iterable);
                this.mFrequenciesKhz = arrl;
                return;
            }
            throw new IOException("Failed to parse time_in_state file");
        }
        throw new IOException("Empty time_in_state file");
    }

    public long[] getFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    public long[] getUsageTimesMillis(Path path) {
        long[] arrl = new long[this.mFrequenciesKhz.length];
        if (!Process.readProcFile(path.toString(), this.mTimeInStateTimeFormat, null, arrl, null)) {
            return null;
        }
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = arrl[i] * 10L;
        }
        return arrl;
    }
}

