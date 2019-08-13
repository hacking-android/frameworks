/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.nfc.INfcTag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcBarcode;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public final class Tag
implements Parcelable {
    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>(){

        @Override
        public Tag createFromParcel(Parcel object) {
            byte[] arrby = Tag.readBytesWithNull((Parcel)object);
            int[] arrn = new int[((Parcel)object).readInt()];
            ((Parcel)object).readIntArray(arrn);
            Bundle[] arrbundle = ((Parcel)object).createTypedArray(Bundle.CREATOR);
            int n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() == 0 ? INfcTag.Stub.asInterface(((Parcel)object).readStrongBinder()) : null;
            return new Tag(arrby, arrn, arrbundle, n, (INfcTag)object);
        }

        public Tag[] newArray(int n) {
            return new Tag[n];
        }
    };
    int mConnectedTechnology;
    @UnsupportedAppUsage
    final byte[] mId;
    final int mServiceHandle;
    final INfcTag mTagService;
    final Bundle[] mTechExtras;
    final int[] mTechList;
    final String[] mTechStringList;

    public Tag(byte[] arrby, int[] arrn, Bundle[] arrbundle, int n, INfcTag iNfcTag) {
        if (arrn != null) {
            this.mId = arrby;
            this.mTechList = Arrays.copyOf(arrn, arrn.length);
            this.mTechStringList = this.generateTechStringList(arrn);
            this.mTechExtras = Arrays.copyOf(arrbundle, arrn.length);
            this.mServiceHandle = n;
            this.mTagService = iNfcTag;
            this.mConnectedTechnology = -1;
            return;
        }
        throw new IllegalArgumentException("rawTargets cannot be null");
    }

    public static Tag createMockTag(byte[] arrby, int[] arrn, Bundle[] arrbundle) {
        return new Tag(arrby, arrn, arrbundle, 0, null);
    }

    private String[] generateTechStringList(int[] arrn) {
        int n = arrn.length;
        Object object = new String[n];
        block12 : for (int i = 0; i < n; ++i) {
            switch (arrn[i]) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown tech type ");
                    ((StringBuilder)object).append(arrn[i]);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 10: {
                    object[i] = NfcBarcode.class.getName();
                    continue block12;
                }
                case 9: {
                    object[i] = MifareUltralight.class.getName();
                    continue block12;
                }
                case 8: {
                    object[i] = MifareClassic.class.getName();
                    continue block12;
                }
                case 7: {
                    object[i] = NdefFormatable.class.getName();
                    continue block12;
                }
                case 6: {
                    object[i] = Ndef.class.getName();
                    continue block12;
                }
                case 5: {
                    object[i] = NfcV.class.getName();
                    continue block12;
                }
                case 4: {
                    object[i] = NfcF.class.getName();
                    continue block12;
                }
                case 3: {
                    object[i] = IsoDep.class.getName();
                    continue block12;
                }
                case 2: {
                    object[i] = NfcB.class.getName();
                    continue block12;
                }
                case 1: {
                    object[i] = NfcA.class.getName();
                }
            }
        }
        return object;
    }

    static int[] getTechCodesFromStrings(String[] arrstring) throws IllegalArgumentException {
        if (arrstring != null) {
            int[] arrn = new int[arrstring.length];
            Serializable serializable = Tag.getTechStringToCodeMap();
            for (int i = 0; i < arrstring.length; ++i) {
                Integer n = ((HashMap)serializable).get(arrstring[i]);
                if (n != null) {
                    arrn[i] = n;
                    continue;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unknown tech type ");
                ((StringBuilder)serializable).append(arrstring[i]);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
            return arrn;
        }
        throw new IllegalArgumentException("List cannot be null");
    }

    private static HashMap<String, Integer> getTechStringToCodeMap() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put(IsoDep.class.getName(), 3);
        hashMap.put(MifareClassic.class.getName(), 8);
        hashMap.put(MifareUltralight.class.getName(), 9);
        hashMap.put(Ndef.class.getName(), 6);
        hashMap.put(NdefFormatable.class.getName(), 7);
        hashMap.put(NfcA.class.getName(), 1);
        hashMap.put(NfcB.class.getName(), 2);
        hashMap.put(NfcF.class.getName(), 4);
        hashMap.put(NfcV.class.getName(), 5);
        hashMap.put(NfcBarcode.class.getName(), 10);
        return hashMap;
    }

    static byte[] readBytesWithNull(Parcel parcel) {
        int n = parcel.readInt();
        byte[] arrby = null;
        if (n >= 0) {
            arrby = new byte[n];
            parcel.readByteArray(arrby);
        }
        return arrby;
    }

    static void writeBytesWithNull(Parcel parcel, byte[] arrby) {
        if (arrby == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(arrby.length);
        parcel.writeByteArray(arrby);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getConnectedTechnology() {
        return this.mConnectedTechnology;
    }

    public byte[] getId() {
        return this.mId;
    }

    @UnsupportedAppUsage
    public int getServiceHandle() {
        return this.mServiceHandle;
    }

    @UnsupportedAppUsage
    public INfcTag getTagService() {
        return this.mTagService;
    }

    public int[] getTechCodeList() {
        return this.mTechList;
    }

    public Bundle getTechExtras(int n) {
        int n2;
        int n3 = -1;
        int n4 = 0;
        do {
            int[] arrn = this.mTechList;
            n2 = n3;
            if (n4 >= arrn.length) break;
            if (arrn[n4] == n) {
                n2 = n4;
                break;
            }
            ++n4;
        } while (true);
        if (n2 < 0) {
            return null;
        }
        return this.mTechExtras[n2];
    }

    public String[] getTechList() {
        return this.mTechStringList;
    }

    public boolean hasTech(int n) {
        int[] arrn = this.mTechList;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    public Tag rediscover() throws IOException {
        if (this.getConnectedTechnology() == -1) {
            Object object = this.mTagService;
            if (object != null) {
                block5 : {
                    try {
                        object = object.rediscover(this.getServiceHandle());
                        if (object == null) break block5;
                        return object;
                    }
                    catch (RemoteException remoteException) {
                        throw new IOException("NFC service dead");
                    }
                }
                object = new IOException("Failed to rediscover tag");
                throw object;
            }
            throw new IOException("Mock tags don't support this operation.");
        }
        throw new IllegalStateException("Close connection to the technology first!");
    }

    public void setConnectedTechnology(int n) {
        synchronized (this) {
            if (this.mConnectedTechnology == -1) {
                this.mConnectedTechnology = n;
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Close other technology first!");
            throw illegalStateException;
        }
    }

    public void setTechnologyDisconnected() {
        this.mConnectedTechnology = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("TAG: Tech [");
        String[] arrstring = this.getTechList();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrstring[i]);
            if (i >= n - 1) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        n = this.mTagService == null ? 1 : 0;
        Tag.writeBytesWithNull(parcel, this.mId);
        parcel.writeInt(this.mTechList.length);
        parcel.writeIntArray(this.mTechList);
        parcel.writeTypedArray((Parcelable[])this.mTechExtras, 0);
        parcel.writeInt(this.mServiceHandle);
        parcel.writeInt(n);
        if (n == 0) {
            parcel.writeStrongBinder(this.mTagService.asBinder());
        }
    }

}

