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
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class AlsaCardsParser {
    protected static final boolean DEBUG = false;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final String TAG = "AlsaCardsParser";
    private static final String kAlsaFolderPath = "/proc/asound";
    private static final String kCardsFilePath = "/proc/asound/cards";
    private static final String kDeviceAddressPrefix = "/dev/bus/usb/";
    private static LineTokenizer mTokenizer = new LineTokenizer(" :[]");
    private ArrayList<AlsaCardRecord> mCardRecords = new ArrayList();
    private int mScanStatus = -1;

    private void Log(String string2) {
    }

    public AlsaCardRecord findCardNumFor(String string2) {
        for (AlsaCardRecord alsaCardRecord : this.mCardRecords) {
            if (!alsaCardRecord.isUsb() || !alsaCardRecord.mUsbDeviceAddress.equals(string2)) continue;
            return alsaCardRecord;
        }
        return null;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int scan() {
        this.mCardRecords = new ArrayList();
        Object object = new File(kCardsFilePath);
        try {
            Object object2;
            FileReader fileReader = new FileReader((File)object);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((object2 = bufferedReader.readLine()) != null) {
                object = new AlsaCardRecord();
                ((AlsaCardRecord)object).parse((String)object2, 0);
                object2 = bufferedReader.readLine();
                if (object2 == null) break;
                ((AlsaCardRecord)object).parse((String)object2, 1);
                int n = ((AlsaCardRecord)object).mCardNum;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("/proc/asound/card");
                ((StringBuilder)object2).append(n);
                CharSequence charSequence = ((StringBuilder)object2).toString();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append("/usbbus");
                Object object3 = new File(((StringBuilder)object2).toString());
                if (((File)object3).exists()) {
                    object2 = new FileReader((File)object3);
                    object3 = new BufferedReader((Reader)object2);
                    if ((object3 = ((BufferedReader)object3).readLine()) != null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(kDeviceAddressPrefix);
                        ((StringBuilder)charSequence).append((String)object3);
                        ((AlsaCardRecord)object).setDeviceAddress(((StringBuilder)charSequence).toString());
                    }
                    ((InputStreamReader)object2).close();
                }
                this.mCardRecords.add((AlsaCardRecord)object);
            }
            fileReader.close();
            if (this.mCardRecords.size() > 0) {
                this.mScanStatus = 0;
                return this.mScanStatus;
            }
            this.mScanStatus = 2;
            return this.mScanStatus;
        }
        catch (IOException iOException) {
            this.mScanStatus = 1;
            return this.mScanStatus;
        }
        catch (FileNotFoundException fileNotFoundException) {
            this.mScanStatus = 1;
        }
        return this.mScanStatus;
    }

    public class AlsaCardRecord {
        private static final String TAG = "AlsaCardRecord";
        private static final String kUsbCardKeyStr = "at usb-";
        String mCardDescription = "";
        String mCardName = "";
        int mCardNum = -1;
        String mField1 = "";
        private String mUsbDeviceAddress = null;

        private boolean parse(String string2, int n) {
            block6 : {
                int n2;
                int n3;
                block5 : {
                    n3 = 0;
                    if (n != 0) break block5;
                    int n4 = mTokenizer.nextToken(string2, 0);
                    n3 = mTokenizer.nextDelimiter(string2, n4);
                    try {
                        this.mCardNum = Integer.parseInt(string2.substring(n4, n3));
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to parse line ");
                        stringBuilder.append(n);
                        stringBuilder.append(" of ");
                        stringBuilder.append(AlsaCardsParser.kCardsFilePath);
                        stringBuilder.append(": ");
                        stringBuilder.append(string2.substring(n4, n3));
                        Slog.e(TAG, stringBuilder.toString());
                        return false;
                    }
                    n = mTokenizer.nextToken(string2, n3);
                    n3 = mTokenizer.nextDelimiter(string2, n);
                    this.mField1 = string2.substring(n, n3);
                    this.mCardName = string2.substring(mTokenizer.nextToken(string2, n3));
                    break block6;
                }
                if (n == 1 && (n2 = mTokenizer.nextToken(string2, 0)) != -1) {
                    int n5 = string2.indexOf(kUsbCardKeyStr);
                    n = n3;
                    if (n5 != -1) {
                        n = 1;
                    }
                    if (n != 0) {
                        this.mCardDescription = string2.substring(n2, n5 - 1);
                    }
                }
            }
            return true;
        }

        public String getCardDescription() {
            return this.mCardDescription;
        }

        public String getCardName() {
            return this.mCardName;
        }

        public int getCardNum() {
            return this.mCardNum;
        }

        boolean isUsb() {
            boolean bl = this.mUsbDeviceAddress != null;
            return bl;
        }

        public void log(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            stringBuilder.append(" [");
            stringBuilder.append(this.mCardNum);
            stringBuilder.append(" ");
            stringBuilder.append(this.mCardName);
            stringBuilder.append(" : ");
            stringBuilder.append(this.mCardDescription);
            stringBuilder.append(" usb:");
            stringBuilder.append(this.isUsb());
            Slog.d(TAG, stringBuilder.toString());
        }

        public void setDeviceAddress(String string2) {
            this.mUsbDeviceAddress = string2;
        }

        public String textFormat() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mCardName);
            stringBuilder.append(" : ");
            stringBuilder.append(this.mCardDescription);
            stringBuilder.append(" [addr:");
            stringBuilder.append(this.mUsbDeviceAddress);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

