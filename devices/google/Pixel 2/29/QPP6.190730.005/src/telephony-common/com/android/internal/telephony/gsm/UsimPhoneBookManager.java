/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.gsm;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.telephony.gsm.SimTlv;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.AdnRecordCache;
import com.android.internal.telephony.uicc.IccConstants;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class UsimPhoneBookManager
extends Handler
implements IccConstants {
    private static final boolean DBG = true;
    private static final int EVENT_EMAIL_LOAD_DONE = 4;
    private static final int EVENT_IAP_LOAD_DONE = 3;
    private static final int EVENT_PBR_LOAD_DONE = 1;
    private static final int EVENT_USIM_ADN_LOAD_DONE = 2;
    private static final byte INVALID_BYTE = -1;
    private static final int INVALID_SFI = -1;
    private static final String LOG_TAG = "UsimPhoneBookManager";
    private static final int USIM_EFAAS_TAG = 199;
    private static final int USIM_EFADN_TAG = 192;
    private static final int USIM_EFANR_TAG = 196;
    private static final int USIM_EFCCP1_TAG = 203;
    private static final int USIM_EFEMAIL_TAG = 202;
    private static final int USIM_EFEXT1_TAG = 194;
    private static final int USIM_EFGRP_TAG = 198;
    private static final int USIM_EFGSD_TAG = 200;
    private static final int USIM_EFIAP_TAG = 193;
    private static final int USIM_EFPBC_TAG = 197;
    private static final int USIM_EFSNE_TAG = 195;
    private static final int USIM_EFUID_TAG = 201;
    private static final int USIM_TYPE1_TAG = 168;
    private static final int USIM_TYPE2_TAG = 169;
    private static final int USIM_TYPE3_TAG = 170;
    private AdnRecordCache mAdnCache;
    private ArrayList<byte[]> mEmailFileRecord;
    private SparseArray<ArrayList<String>> mEmailsForAdnRec;
    private IccFileHandler mFh;
    private ArrayList<byte[]> mIapFileRecord;
    private Boolean mIsPbrPresent;
    private Object mLock = new Object();
    private ArrayList<PbrRecord> mPbrRecords;
    private ArrayList<AdnRecord> mPhoneBookRecords;
    private boolean mRefreshCache = false;
    private SparseIntArray mSfiEfidTable;

    public UsimPhoneBookManager(IccFileHandler iccFileHandler, AdnRecordCache adnRecordCache) {
        this.mFh = iccFileHandler;
        this.mPhoneBookRecords = new ArrayList();
        this.mPbrRecords = null;
        this.mIsPbrPresent = true;
        this.mAdnCache = adnRecordCache;
        this.mEmailsForAdnRec = new SparseArray();
        this.mSfiEfidTable = new SparseIntArray();
    }

    private void buildType1EmailList(int n) {
        if (this.mPbrRecords.get(n) == null) {
            return;
        }
        int n2 = this.mPbrRecords.get(n).mMasterFileRecordNum;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Building type 1 email list. recId = ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", numRecs = ");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        for (int i = 0; i < n2; ++i) {
            try {
                object = this.mEmailFileRecord.get(i);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                Rlog.e((String)LOG_TAG, (String)"Error: Improper ICC card: No email record for ADN, continuing");
                break;
            }
            int n3 = object[((byte[])object).length - 2];
            byte by = object[((byte[])object).length - 1];
            String string = this.readEmailRecord(i);
            if (string == null || string.equals("")) continue;
            if (n3 != -1 && this.mSfiEfidTable.get(n3) != 0) {
                n3 = this.mSfiEfidTable.get(n3);
            } else {
                object = (File)this.mPbrRecords.get(n).mFileIds.get(192);
                if (object == null) continue;
                n3 = ((File)object).getEfid();
            }
            n3 = (65535 & n3) << 8 | by - 1 & 255;
            Serializable serializable = (ArrayList)this.mEmailsForAdnRec.get(n3);
            object = serializable;
            if (serializable == null) {
                object = new ArrayList();
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Adding email #");
            ((StringBuilder)serializable).append(i);
            ((StringBuilder)serializable).append(" list to index 0x");
            ((StringBuilder)serializable).append(Integer.toHexString(n3).toUpperCase());
            this.log(((StringBuilder)serializable).toString());
            ((ArrayList)object).add(string);
            this.mEmailsForAdnRec.put(n3, object);
            continue;
        }
    }

    private boolean buildType2EmailList(int n) {
        if (this.mPbrRecords.get(n) == null) {
            return false;
        }
        int n2 = this.mPbrRecords.get(n).mMasterFileRecordNum;
        ArrayList<String> arrayList = new StringBuilder();
        ((StringBuilder)((Object)arrayList)).append("Building type 2 email list. recId = ");
        ((StringBuilder)((Object)arrayList)).append(n);
        ((StringBuilder)((Object)arrayList)).append(", numRecs = ");
        ((StringBuilder)((Object)arrayList)).append(n2);
        this.log(((StringBuilder)((Object)arrayList)).toString());
        arrayList = (File)this.mPbrRecords.get(n).mFileIds.get(192);
        if (arrayList == null) {
            Rlog.e((String)LOG_TAG, (String)"Error: Improper ICC card: EF_ADN does not exist in PBR files");
            return false;
        }
        int n3 = ((File)((Object)arrayList)).getEfid();
        for (int i = 0; i < n2; ++i) {
            int n4;
            try {
                n4 = this.mIapFileRecord.get(i)[((File)this.mPbrRecords.get(n).mFileIds.get(202)).getIndex()];
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                Rlog.e((String)LOG_TAG, (String)"Error: Improper ICC card: Corrupted EF_IAP");
            }
            String string = this.readEmailRecord(n4 - 1);
            if (string == null || string.equals("")) continue;
            n4 = (65535 & n3) << 8 | i & 255;
            Serializable serializable = (ArrayList)this.mEmailsForAdnRec.get(n4);
            arrayList = serializable;
            if (serializable == null) {
                arrayList = new ArrayList<String>();
            }
            arrayList.add(string);
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Adding email list to index 0x");
            ((StringBuilder)serializable).append(Integer.toHexString(n4).toUpperCase());
            this.log(((StringBuilder)serializable).toString());
            this.mEmailsForAdnRec.put(n4, arrayList);
            continue;
        }
        return true;
    }

    private void createPbrFile(ArrayList<byte[]> object2) {
        int n;
        if (object2 == null) {
            this.mPbrRecords = null;
            this.mIsPbrPresent = false;
            return;
        }
        this.mPbrRecords = new ArrayList();
        for (n = 0; n < ((ArrayList)object2).size(); ++n) {
            if (((byte[])((ArrayList)object2).get(n))[0] == -1) continue;
            this.mPbrRecords.add(new PbrRecord((byte[])((ArrayList)object2).get(n)));
        }
        for (PbrRecord pbrRecord : this.mPbrRecords) {
            File file = (File)pbrRecord.mFileIds.get(192);
            if (file == null || (n = file.getSfi()) == -1) continue;
            this.mSfiEfidTable.put(n, ((File)pbrRecord.mFileIds.get(192)).getEfid());
        }
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void readAdnFileAndWait(int n) {
        SparseArray sparseArray = this.mPbrRecords.get(n).mFileIds;
        if (sparseArray != null && sparseArray.size() != 0) {
            int n2 = 0;
            if (sparseArray.get(194) != null) {
                n2 = ((File)sparseArray.get(194)).getEfid();
            }
            if (sparseArray.get(192) == null) {
                return;
            }
            int n3 = this.mPhoneBookRecords.size();
            this.mAdnCache.requestLoadAllAdnLike(((File)sparseArray.get(192)).getEfid(), n2, this.obtainMessage(2));
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                Rlog.e((String)LOG_TAG, (String)"Interrupted Exception in readAdnFileAndWait");
            }
            this.mPbrRecords.get(n).mMasterFileRecordNum = this.mPhoneBookRecords.size() - n3;
            return;
        }
    }

    private void readEmailFileAndWait(int n) {
        Object object = this.mPbrRecords.get(n).mFileIds;
        if (object == null) {
            return;
        }
        File file = (File)object.get(202);
        if (file != null) {
            if (file.getParentTag() == 169) {
                if (object.get(193) == null) {
                    Rlog.e((String)LOG_TAG, (String)"Can't locate EF_IAP in EF_PBR.");
                    return;
                }
                this.log("EF_IAP exists. Loading EF_IAP to retrieve the index.");
                this.readIapFileAndWait(((File)object.get(193)).getEfid());
                if (this.mIapFileRecord == null) {
                    Rlog.e((String)LOG_TAG, (String)"Error: IAP file is empty");
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("EF_EMAIL order in PBR record: ");
                ((StringBuilder)object).append(file.getIndex());
                this.log(((StringBuilder)object).toString());
            }
            int n2 = file.getEfid();
            object = new StringBuilder();
            ((StringBuilder)object).append("EF_EMAIL exists in PBR. efid = 0x");
            ((StringBuilder)object).append(Integer.toHexString(n2).toUpperCase());
            this.log(((StringBuilder)object).toString());
            for (int i = 0; i < n; ++i) {
                if (this.mPbrRecords.get(i) == null || (object = this.mPbrRecords.get(i).mFileIds) == null || (object = (File)object.get(202)) == null || ((File)object).getEfid() != n2) continue;
                this.log("Skipped this EF_EMAIL which was loaded earlier");
                return;
            }
            this.mFh.loadEFLinearFixedAll(n2, this.obtainMessage(4));
            try {
                this.mLock.wait();
            }
            catch (InterruptedException interruptedException) {
                Rlog.e((String)LOG_TAG, (String)"Interrupted Exception in readEmailFileAndWait");
            }
            if (this.mEmailFileRecord == null) {
                Rlog.e((String)LOG_TAG, (String)"Error: Email file is empty");
                return;
            }
            if (file.getParentTag() == 169 && this.mIapFileRecord != null) {
                this.buildType2EmailList(n);
            } else {
                this.buildType1EmailList(n);
            }
        }
    }

    private String readEmailRecord(int n) {
        byte[] arrby;
        try {
            arrby = this.mEmailFileRecord.get(n);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
        return IccUtils.adnStringFieldToString((byte[])arrby, (int)0, (int)(arrby.length - 2));
    }

    private void readIapFileAndWait(int n) {
        this.mFh.loadEFLinearFixedAll(n, this.obtainMessage(3));
        try {
            this.mLock.wait();
        }
        catch (InterruptedException interruptedException) {
            Rlog.e((String)LOG_TAG, (String)"Interrupted Exception in readIapFileAndWait");
        }
    }

    private void readPbrFileAndWait() {
        this.mFh.loadEFLinearFixedAll(20272, this.obtainMessage(1));
        try {
            this.mLock.wait();
        }
        catch (InterruptedException interruptedException) {
            Rlog.e((String)LOG_TAG, (String)"Interrupted Exception in readAdnFileAndWait");
        }
    }

    private void refreshCache() {
        if (this.mPbrRecords == null) {
            return;
        }
        this.mPhoneBookRecords.clear();
        int n = this.mPbrRecords.size();
        for (int i = 0; i < n; ++i) {
            this.readAdnFileAndWait(i);
        }
    }

    private void updatePhoneAdnRecord() {
        int n = this.mPhoneBookRecords.size();
        for (int i = 0; i < n; ++i) {
            ArrayList arrayList;
            AdnRecord adnRecord = this.mPhoneBookRecords.get(i);
            int n2 = adnRecord.getEfid();
            int n3 = adnRecord.getRecId();
            try {
                arrayList = (ArrayList)this.mEmailsForAdnRec.get((65535 & n2) << 8 | n3 - 1 & 255);
                if (arrayList == null) continue;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                // empty catch block
            }
            Object object = new String[arrayList.size()];
            System.arraycopy(arrayList.toArray(), 0, object, 0, arrayList.size());
            adnRecord.setEmails((String[])object);
            object = new StringBuilder();
            ((StringBuilder)object).append("Adding email list to ADN (0x");
            ((StringBuilder)object).append(Integer.toHexString(this.mPhoneBookRecords.get(i).getEfid()).toUpperCase());
            ((StringBuilder)object).append(") record #");
            ((StringBuilder)object).append(this.mPhoneBookRecords.get(i).getRecId());
            this.log(((StringBuilder)object).toString());
            this.mPhoneBookRecords.set(i, adnRecord);
            continue;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message object) {
        int n = object.what;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return;
                    }
                    this.log("Loading USIM Email records done");
                    object = (AsyncResult)object.obj;
                    if (object.exception == null) {
                        this.mEmailFileRecord = (ArrayList)object.result;
                    }
                    Object object2 = this.mLock;
                    synchronized (object2) {
                        this.mLock.notify();
                        return;
                    }
                }
                this.log("Loading USIM IAP records done");
                object = (AsyncResult)object.obj;
                if (object.exception == null) {
                    this.mIapFileRecord = (ArrayList)object.result;
                }
                Object object3 = this.mLock;
                synchronized (object3) {
                    this.mLock.notify();
                    return;
                }
            }
            this.log("Loading USIM ADN records done");
            object = (AsyncResult)object.obj;
            if (object.exception == null) {
                this.mPhoneBookRecords.addAll((ArrayList)object.result);
            }
            object = this.mLock;
            synchronized (object) {
                this.mLock.notify();
                return;
            }
        }
        this.log("Loading PBR records done");
        object = (AsyncResult)object.obj;
        if (object.exception == null) {
            this.createPbrFile((ArrayList)object.result);
        }
        object = this.mLock;
        synchronized (object) {
            this.mLock.notify();
            return;
        }
    }

    public void invalidateCache() {
        this.mRefreshCache = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<AdnRecord> loadEfFilesFromUsim() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mPhoneBookRecords.isEmpty()) {
                if (!this.mRefreshCache) return this.mPhoneBookRecords;
                this.mRefreshCache = false;
                this.refreshCache();
                return this.mPhoneBookRecords;
            }
            if (!this.mIsPbrPresent.booleanValue()) {
                return null;
            }
            if (this.mPbrRecords == null) {
                this.readPbrFileAndWait();
            }
            if (this.mPbrRecords == null) {
                return null;
            }
            int n = this.mPbrRecords.size();
            this.log("loadEfFilesFromUsim: Loading adn and emails");
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.updatePhoneAdnRecord();
                    return this.mPhoneBookRecords;
                }
                this.readAdnFileAndWait(n2);
                this.readEmailFileAndWait(n2);
                ++n2;
            } while (true);
        }
    }

    public void reset() {
        this.mPhoneBookRecords.clear();
        this.mIapFileRecord = null;
        this.mEmailFileRecord = null;
        this.mPbrRecords = null;
        this.mIsPbrPresent = true;
        this.mRefreshCache = false;
        this.mEmailsForAdnRec.clear();
        this.mSfiEfidTable.clear();
    }

    private class File {
        private final int mEfid;
        private final int mIndex;
        private final int mParentTag;
        private final int mSfi;

        File(int n, int n2, int n3, int n4) {
            this.mParentTag = n;
            this.mEfid = n2;
            this.mSfi = n3;
            this.mIndex = n4;
        }

        public int getEfid() {
            return this.mEfid;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getParentTag() {
            return this.mParentTag;
        }

        public int getSfi() {
            return this.mSfi;
        }
    }

    private class PbrRecord {
        private SparseArray<File> mFileIds = new SparseArray();
        private int mMasterFileRecordNum;

        PbrRecord(byte[] arrby) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PBR rec: ");
            stringBuilder.append(IccUtils.bytesToHexString((byte[])arrby));
            UsimPhoneBookManager.this.log(stringBuilder.toString());
            this.parseTag(new SimTlv(arrby, 0, arrby.length));
        }

        void parseEfAndSFI(SimTlv simTlv, int n) {
            int n2 = 0;
            do {
                int n3 = simTlv.getTag();
                switch (n3) {
                    default: {
                        break;
                    }
                    case 192: 
                    case 193: 
                    case 194: 
                    case 195: 
                    case 196: 
                    case 197: 
                    case 198: 
                    case 199: 
                    case 200: 
                    case 201: 
                    case 202: 
                    case 203: {
                        byte[] arrby = simTlv.getData();
                        if (arrby.length >= 2 && arrby.length <= 3) {
                            int n4 = arrby.length == 3 ? arrby[2] & 255 : -1;
                            byte by = arrby[0];
                            byte by2 = arrby[1];
                            this.mFileIds.put(n3, (Object)new File(n, (by & 255) << 8 | by2 & 255, n4, n2));
                            break;
                        }
                        UsimPhoneBookManager usimPhoneBookManager = UsimPhoneBookManager.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid TLV length: ");
                        stringBuilder.append(arrby.length);
                        usimPhoneBookManager.log(stringBuilder.toString());
                    }
                }
                ++n2;
            } while (simTlv.nextObject());
        }

        void parseTag(SimTlv simTlv) {
            do {
                int n = simTlv.getTag();
                switch (n) {
                    default: {
                        break;
                    }
                    case 168: 
                    case 169: 
                    case 170: {
                        byte[] arrby = simTlv.getData();
                        this.parseEfAndSFI(new SimTlv(arrby, 0, arrby.length), n);
                    }
                }
            } while (simTlv.nextObject());
        }
    }

}

