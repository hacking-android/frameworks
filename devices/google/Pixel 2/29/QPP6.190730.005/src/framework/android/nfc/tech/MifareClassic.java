/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.tech;

import android.nfc.INfcTag;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.BasicTagTechnology;
import android.nfc.tech.NfcA;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class MifareClassic
extends BasicTagTechnology {
    public static final int BLOCK_SIZE = 16;
    public static final byte[] KEY_DEFAULT = new byte[]{-1, -1, -1, -1, -1, -1};
    public static final byte[] KEY_MIFARE_APPLICATION_DIRECTORY = new byte[]{-96, -95, -94, -93, -92, -91};
    public static final byte[] KEY_NFC_FORUM = new byte[]{-45, -9, -45, -9, -45, -9};
    private static final int MAX_BLOCK_COUNT = 256;
    private static final int MAX_SECTOR_COUNT = 40;
    public static final int SIZE_1K = 1024;
    public static final int SIZE_2K = 2048;
    public static final int SIZE_4K = 4096;
    public static final int SIZE_MINI = 320;
    private static final String TAG = "NFC";
    public static final int TYPE_CLASSIC = 0;
    public static final int TYPE_PLUS = 1;
    public static final int TYPE_PRO = 2;
    public static final int TYPE_UNKNOWN = -1;
    private boolean mIsEmulated;
    private int mSize;
    private int mType;

    /*
     * Enabled aggressive block sorting
     */
    public MifareClassic(Tag object) throws RemoteException {
        super((Tag)object, 8);
        NfcA nfcA = NfcA.get((Tag)object);
        this.mIsEmulated = false;
        short s = nfcA.getSak();
        if (s != 1) {
            if (s == 24) {
                this.mType = 0;
                this.mSize = 4096;
                return;
            }
            if (s == 40) {
                this.mType = 0;
                this.mSize = 1024;
                this.mIsEmulated = true;
                return;
            }
            if (s == 56) {
                this.mType = 0;
                this.mSize = 4096;
                this.mIsEmulated = true;
                return;
            }
            if (s == 136) {
                this.mType = 0;
                this.mSize = 1024;
                return;
            }
            if (s != 152 && s != 184) {
                if (s != 8) {
                    if (s == 9) {
                        this.mType = 0;
                        this.mSize = 320;
                        return;
                    }
                    if (s == 16) {
                        this.mType = 1;
                        this.mSize = 2048;
                        return;
                    }
                    if (s == 17) {
                        this.mType = 1;
                        this.mSize = 4096;
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Tag incorrectly enumerated as MIFARE Classic, SAK = ");
                    ((StringBuilder)object).append(nfcA.getSak());
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
            } else {
                this.mType = 2;
                this.mSize = 4096;
                return;
            }
        }
        this.mType = 0;
        this.mSize = 1024;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean authenticate(int n, byte[] arrby, boolean bl) throws IOException {
        MifareClassic.validateSector(n);
        this.checkConnected();
        byte[] arrby2 = new byte[12];
        arrby2[0] = bl ? (byte)96 : (byte)97;
        arrby2[1] = (byte)this.sectorToBlock(n);
        byte[] arrby3 = this.getTag().getId();
        System.arraycopy(arrby3, arrby3.length - 4, arrby2, 2, 4);
        System.arraycopy(arrby, 0, arrby2, 6, 6);
        try {
            arrby = this.transceive(arrby2, false);
            if (arrby == null) return false;
            return true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return false;
        catch (TagLostException tagLostException) {
            throw tagLostException;
        }
    }

    public static MifareClassic get(Tag object) {
        if (!((Tag)object).hasTech(8)) {
            return null;
        }
        try {
            object = new MifareClassic((Tag)object);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    private static void validateBlock(int n) {
        if (n >= 0 && n < 256) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("block out of bounds: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static void validateSector(int n) {
        if (n >= 0 && n < 40) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sector out of bounds: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static void validateValueOperand(int n) {
        if (n >= 0) {
            return;
        }
        throw new IllegalArgumentException("value operand negative");
    }

    public boolean authenticateSectorWithKeyA(int n, byte[] arrby) throws IOException {
        return this.authenticate(n, arrby, true);
    }

    public boolean authenticateSectorWithKeyB(int n, byte[] arrby) throws IOException {
        return this.authenticate(n, arrby, false);
    }

    public int blockToSector(int n) {
        MifareClassic.validateBlock(n);
        if (n < 128) {
            return n / 4;
        }
        return (n - 128) / 16 + 32;
    }

    public void decrement(int n, int n2) throws IOException {
        MifareClassic.validateBlock(n);
        MifareClassic.validateValueOperand(n2);
        this.checkConnected();
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte)-64);
        byteBuffer.put((byte)n);
        byteBuffer.putInt(n2);
        this.transceive(byteBuffer.array(), false);
    }

    public int getBlockCount() {
        return this.mSize / 16;
    }

    public int getBlockCountInSector(int n) {
        MifareClassic.validateSector(n);
        if (n < 32) {
            return 4;
        }
        return 16;
    }

    public int getMaxTransceiveLength() {
        return this.getMaxTransceiveLengthInternal();
    }

    public int getSectorCount() {
        int n = this.mSize;
        if (n != 320) {
            if (n != 1024) {
                if (n != 2048) {
                    if (n != 4096) {
                        return 0;
                    }
                    return 40;
                }
                return 32;
            }
            return 16;
        }
        return 5;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getTimeout() {
        try {
            int n = this.mTag.getTagService().getTimeout(8);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
            return 0;
        }
    }

    public int getType() {
        return this.mType;
    }

    public void increment(int n, int n2) throws IOException {
        MifareClassic.validateBlock(n);
        MifareClassic.validateValueOperand(n2);
        this.checkConnected();
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put((byte)-63);
        byteBuffer.put((byte)n);
        byteBuffer.putInt(n2);
        this.transceive(byteBuffer.array(), false);
    }

    public boolean isEmulated() {
        return this.mIsEmulated;
    }

    public byte[] readBlock(int n) throws IOException {
        MifareClassic.validateBlock(n);
        this.checkConnected();
        return this.transceive(new byte[]{48, (byte)n}, false);
    }

    public void restore(int n) throws IOException {
        MifareClassic.validateBlock(n);
        this.checkConnected();
        this.transceive(new byte[]{-62, (byte)n}, false);
    }

    public int sectorToBlock(int n) {
        if (n < 32) {
            return n * 4;
        }
        return (n - 32) * 16 + 128;
    }

    public void setTimeout(int n) {
        try {
            if (this.mTag.getTagService().setTimeout(8, n) != 0) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("The supplied timeout is not valid");
                throw illegalArgumentException;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "NFC service dead", remoteException);
        }
    }

    public byte[] transceive(byte[] arrby) throws IOException {
        return this.transceive(arrby, true);
    }

    public void transfer(int n) throws IOException {
        MifareClassic.validateBlock(n);
        this.checkConnected();
        this.transceive(new byte[]{-80, (byte)n}, false);
    }

    public void writeBlock(int n, byte[] arrby) throws IOException {
        MifareClassic.validateBlock(n);
        this.checkConnected();
        if (arrby.length == 16) {
            byte[] arrby2 = new byte[arrby.length + 2];
            arrby2[0] = (byte)-96;
            arrby2[1] = (byte)n;
            System.arraycopy(arrby, 0, arrby2, 2, arrby.length);
            this.transceive(arrby2, false);
            return;
        }
        throw new IllegalArgumentException("must write 16-bytes");
    }
}

