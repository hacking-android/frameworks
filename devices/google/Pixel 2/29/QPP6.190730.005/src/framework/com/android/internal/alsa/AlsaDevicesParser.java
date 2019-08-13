/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.alsa;

import android.util.Slog;
import com.android.internal.alsa.LineTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class AlsaDevicesParser {
    protected static final boolean DEBUG = false;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final String TAG = "AlsaDevicesParser";
    private static final String kDevicesFilePath = "/proc/asound/devices";
    private static final int kEndIndex_CardNum = 8;
    private static final int kEndIndex_DeviceNum = 11;
    private static final int kIndex_CardDeviceField = 5;
    private static final int kStartIndex_CardNum = 6;
    private static final int kStartIndex_DeviceNum = 9;
    private static final int kStartIndex_Type = 14;
    private static LineTokenizer mTokenizer = new LineTokenizer(" :[]-");
    private final ArrayList<AlsaDeviceRecord> mDeviceRecords = new ArrayList();
    private boolean mHasCaptureDevices = false;
    private boolean mHasMIDIDevices = false;
    private boolean mHasPlaybackDevices = false;
    private int mScanStatus = -1;

    private void Log(String string2) {
    }

    static /* synthetic */ LineTokenizer access$000() {
        return mTokenizer;
    }

    static /* synthetic */ boolean access$102(AlsaDevicesParser alsaDevicesParser, boolean bl) {
        alsaDevicesParser.mHasMIDIDevices = bl;
        return bl;
    }

    static /* synthetic */ boolean access$202(AlsaDevicesParser alsaDevicesParser, boolean bl) {
        alsaDevicesParser.mHasCaptureDevices = bl;
        return bl;
    }

    static /* synthetic */ boolean access$302(AlsaDevicesParser alsaDevicesParser, boolean bl) {
        alsaDevicesParser.mHasPlaybackDevices = bl;
        return bl;
    }

    private boolean isLineDeviceRecord(String string2) {
        boolean bl = string2.charAt(5) == '[';
        return bl;
    }

    public int getDefaultDeviceNum(int n) {
        return 0;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }

    public boolean hasCaptureDevices(int n) {
        for (AlsaDeviceRecord alsaDeviceRecord : this.mDeviceRecords) {
            if (alsaDeviceRecord.mCardNum != n || alsaDeviceRecord.mDeviceType != 0 || alsaDeviceRecord.mDeviceDir != 0) continue;
            return true;
        }
        return false;
    }

    public boolean hasMIDIDevices(int n) {
        for (AlsaDeviceRecord alsaDeviceRecord : this.mDeviceRecords) {
            if (alsaDeviceRecord.mCardNum != n || alsaDeviceRecord.mDeviceType != 2) continue;
            return true;
        }
        return false;
    }

    public boolean hasPlaybackDevices(int n) {
        for (AlsaDeviceRecord alsaDeviceRecord : this.mDeviceRecords) {
            if (alsaDeviceRecord.mCardNum != n || alsaDeviceRecord.mDeviceType != 0 || alsaDeviceRecord.mDeviceDir != 1) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int scan() {
        this.mDeviceRecords.clear();
        Object object = new File(kDevicesFilePath);
        try {
            String string2;
            FileReader fileReader = new FileReader((File)object);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((string2 = bufferedReader.readLine()) != null) {
                if (!this.isLineDeviceRecord(string2)) continue;
                object = new AlsaDeviceRecord();
                ((AlsaDeviceRecord)object).parse(string2);
                Slog.i(TAG, ((AlsaDeviceRecord)object).textFormat());
                this.mDeviceRecords.add((AlsaDeviceRecord)object);
            }
            fileReader.close();
            if (this.mDeviceRecords.size() > 0) {
                this.mScanStatus = 0;
                return this.mScanStatus;
            }
            this.mScanStatus = 2;
            return this.mScanStatus;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            this.mScanStatus = 1;
            return this.mScanStatus;
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            this.mScanStatus = 1;
        }
        return this.mScanStatus;
    }

    public class AlsaDeviceRecord {
        public static final int kDeviceDir_Capture = 0;
        public static final int kDeviceDir_Playback = 1;
        public static final int kDeviceDir_Unknown = -1;
        public static final int kDeviceType_Audio = 0;
        public static final int kDeviceType_Control = 1;
        public static final int kDeviceType_MIDI = 2;
        public static final int kDeviceType_Unknown = -1;
        int mCardNum = -1;
        int mDeviceDir = -1;
        int mDeviceNum = -1;
        int mDeviceType = -1;

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public boolean parse(String var1_1) {
            var2_4 = 0;
            var3_5 = 0;
            do {
                block11 : {
                    if ((var4_6 = AlsaDevicesParser.access$000().nextToken(var1_1, var2_4)) == -1) {
                        return true;
                    }
                    var2_4 = var5_7 = AlsaDevicesParser.access$000().nextDelimiter(var1_1, var4_6);
                    if (var5_7 == -1) {
                        var2_4 = var1_1.length();
                    }
                    var6_8 = var1_1.substring(var4_6, var2_4);
                    if (var3_5 == 0) break block11;
                    if (var3_5 == 1) ** GOTO lbl57
                    if (var3_5 == 2) ** GOTO lbl54
                    if (var3_5 == 3) ** GOTO lbl43
                    if (var3_5 == 4) ** GOTO lbl32
                    if (var3_5 != 5) {
                        var5_7 = var3_5;
                    } else {
                        try {
                            if (var6_8.equals("capture")) {
                                this.mDeviceDir = 0;
                                AlsaDevicesParser.access$202(AlsaDevicesParser.this, true);
                                var5_7 = var3_5;
                            }
                            var5_7 = var3_5;
                            if (!var6_8.equals("playback")) ** GOTO lbl82
                            this.mDeviceDir = 1;
                            AlsaDevicesParser.access$302(AlsaDevicesParser.this, true);
                            var5_7 = var3_5;
lbl32: // 1 sources:
                            if (var6_8.equals("audio")) {
                                this.mDeviceType = 0;
                                var5_7 = var3_5;
                            }
                            var5_7 = var3_5;
                            if (!var6_8.equals("midi")) ** GOTO lbl82
                            this.mDeviceType = 2;
                            AlsaDevicesParser.access$102(AlsaDevicesParser.this, true);
                            var5_7 = var3_5;
lbl43: // 1 sources:
                            if (var6_8.equals("digital")) {
                                var5_7 = var3_5;
                            }
                            if (var6_8.equals("control")) {
                                this.mDeviceType = 1;
                                var5_7 = var3_5;
                            }
                            var6_8.equals("raw");
                            var5_7 = var3_5;
lbl54: // 1 sources:
                            this.mDeviceNum = Integer.parseInt(var6_8);
                            var5_7 = var3_5;
lbl57: // 1 sources:
                            this.mCardNum = Integer.parseInt(var6_8);
                            var4_6 = var1_1.charAt(var2_4);
                            var5_7 = var3_5;
                            if (var4_6 == 45) ** GOTO lbl82
                            var5_7 = var3_5 + 1;
                        }
                        catch (NumberFormatException var1_2) {
                            var1_3 = new StringBuilder();
                            var1_3.append("Failed to parse token ");
                            var1_3.append(var3_5);
                            var1_3.append(" of ");
                            var1_3.append("/proc/asound/devices");
                            var1_3.append(" token: ");
                            var1_3.append(var6_8);
                            Slog.e("AlsaDevicesParser", var1_3.toString());
                            return false;
                        }
                    }
                }
                var5_7 = var3_5;
lbl82: // 14 sources:
                var3_5 = var5_7 + 1;
            } while (true);
        }

        public String textFormat() {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("[");
            stringBuilder2.append(this.mCardNum);
            stringBuilder2.append(":");
            stringBuilder2.append(this.mDeviceNum);
            stringBuilder2.append("]");
            stringBuilder.append(stringBuilder2.toString());
            int n = this.mDeviceType;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        stringBuilder.append(" N/A");
                    } else {
                        stringBuilder.append(" MIDI");
                    }
                } else {
                    stringBuilder.append(" Control");
                }
            } else {
                stringBuilder.append(" Audio");
            }
            n = this.mDeviceDir;
            if (n != 0) {
                if (n != 1) {
                    stringBuilder.append(" N/A");
                } else {
                    stringBuilder.append(" Playback");
                }
            } else {
                stringBuilder.append(" Capture");
            }
            return stringBuilder.toString();
        }
    }

}

