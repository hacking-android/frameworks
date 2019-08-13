/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Color
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.ImageDescriptor;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.util.HashMap;

class IconLoader
extends Handler {
    private static final int CLUT_ENTRY_SIZE = 3;
    private static final int CLUT_LOCATION_OFFSET = 4;
    private static final int EVENT_READ_CLUT_DONE = 3;
    private static final int EVENT_READ_EF_IMG_RECOED_DONE = 1;
    private static final int EVENT_READ_ICON_DONE = 2;
    private static final int STATE_MULTI_ICONS = 2;
    private static final int STATE_SINGLE_ICON = 1;
    private static IconLoader sLoader = null;
    private static HandlerThread sThread = null;
    private Bitmap mCurrentIcon = null;
    private int mCurrentRecordIndex = 0;
    private Message mEndMsg = null;
    private byte[] mIconData = null;
    private Bitmap[] mIcons = null;
    private HashMap<Integer, Bitmap> mIconsCache = null;
    private ImageDescriptor mId = null;
    private int mRecordNumber;
    private int[] mRecordNumbers = null;
    private IccFileHandler mSimFH = null;
    private int mState = 1;

    private IconLoader(Looper looper, IccFileHandler iccFileHandler) {
        super(looper);
        this.mSimFH = iccFileHandler;
        this.mIconsCache = new HashMap(50);
    }

    private static int bitToBnW(int n) {
        if (n == 1) {
            return -1;
        }
        return -16777216;
    }

    static IconLoader getInstance(Handler handler, IccFileHandler iccFileHandler) {
        handler = sLoader;
        if (handler != null) {
            return handler;
        }
        if (iccFileHandler != null) {
            sThread = new HandlerThread("Cat Icon Loader");
            sThread.start();
            return new IconLoader(sThread.getLooper(), iccFileHandler);
        }
        return null;
    }

    private static int getMask(int n) {
        int n2 = 0;
        switch (n) {
            default: {
                n = n2;
                break;
            }
            case 8: {
                n = 255;
                break;
            }
            case 7: {
                n = 127;
                break;
            }
            case 6: {
                n = 63;
                break;
            }
            case 5: {
                n = 31;
                break;
            }
            case 4: {
                n = 15;
                break;
            }
            case 3: {
                n = 7;
                break;
            }
            case 2: {
                n = 3;
                break;
            }
            case 1: {
                n = 1;
            }
        }
        return n;
    }

    private boolean handleImageDescriptor(byte[] arrby) {
        this.mId = ImageDescriptor.parse(arrby, 1);
        return this.mId != null;
    }

    public static Bitmap parseToBnW(byte[] arrby, int n) {
        n = 0 + 1;
        int n2 = arrby[0] & 255;
        int n3 = n + 1;
        int n4 = arrby[n] & 255;
        int n5 = n2 * n4;
        int[] arrn = new int[n5];
        int n6 = 0;
        n = 7;
        byte by = 0;
        while (n6 < n5) {
            int n7 = n3;
            if (n6 % 8 == 0) {
                by = arrby[n3];
                n = 7;
                n7 = n3 + 1;
            }
            arrn[n6] = IconLoader.bitToBnW(by >> n & 1);
            ++n6;
            --n;
            n3 = n7;
        }
        if (n6 != n5) {
            CatLog.d("IconLoader", "parseToBnW; size error");
        }
        return Bitmap.createBitmap((int[])arrn, (int)n2, (int)n4, (Bitmap.Config)Bitmap.Config.ARGB_8888);
    }

    public static Bitmap parseToRGB(byte[] arrby, int n, boolean bl, byte[] arrby2) {
        int n2;
        int n3 = 0 + 1;
        int n4 = arrby[0] & 255;
        n = n3 + 1;
        int n5 = arrby[n3] & 255;
        n3 = n + 1;
        int n6 = arrby[n] & 255;
        int n7 = arrby[n3] & 255;
        n = 0;
        if (bl) {
            arrby2[n7 - 1] = (byte)(false ? 1 : 0);
        }
        int n8 = n4 * n5;
        int[] arrn = new int[n8];
        int n9 = 0;
        int n10 = n2 = 8 - n6;
        int n11 = 6 + 1;
        byte by = arrby[6];
        int n12 = IconLoader.getMask(n6);
        int n13 = n7;
        byte by2 = by;
        int n14 = n9;
        n3 = n10;
        int n15 = n11;
        if (8 % n6 == 0) {
            n = 1;
            n15 = n11;
            n3 = n10;
            n14 = n9;
            by2 = by;
            n13 = n7;
        }
        while (n14 < n8) {
            n7 = n3;
            n10 = n15;
            if (n3 < 0) {
                by2 = arrby[n15];
                n3 = n != 0 ? n2 : (n3 *= -1);
                n10 = n15 + 1;
                n7 = n3;
            }
            n3 = (by2 >> n7 & n12) * 3;
            arrn[n14] = Color.rgb((int)arrby2[n3], (int)arrby2[n3 + 1], (int)arrby2[n3 + 2]);
            n3 = n7 - n6;
            ++n14;
            n15 = n10;
        }
        return Bitmap.createBitmap((int[])arrn, (int)n4, (int)n5, (Bitmap.Config)Bitmap.Config.ARGB_8888);
    }

    private void postIcon() {
        int n = this.mState;
        if (n == 1) {
            Message message = this.mEndMsg;
            message.obj = this.mCurrentIcon;
            message.sendToTarget();
        } else if (n == 2) {
            Bitmap[] arrbitmap = this.mIcons;
            n = this.mCurrentRecordIndex;
            this.mCurrentRecordIndex = n + 1;
            arrbitmap[n] = this.mCurrentIcon;
            n = this.mCurrentRecordIndex;
            Message message = this.mRecordNumbers;
            if (n < ((int[])message).length) {
                this.startLoadingIcon((int)message[n]);
            } else {
                message = this.mEndMsg;
                message.obj = arrbitmap;
                message.sendToTarget();
            }
        }
    }

    private void readClut() {
        byte by = this.mIconData[3];
        Message message = this.obtainMessage(3);
        IccFileHandler iccFileHandler = this.mSimFH;
        int n = this.mId.mImageId;
        byte[] arrby = this.mIconData;
        iccFileHandler.loadEFImgTransparent(n, arrby[4], arrby[5], by * 3, message);
    }

    private void readIconData() {
        Message message = this.obtainMessage(2);
        this.mSimFH.loadEFImgTransparent(this.mId.mImageId, 0, 0, this.mId.mLength, message);
    }

    private void readId() {
        if (this.mRecordNumber < 0) {
            this.mCurrentIcon = null;
            this.postIcon();
            return;
        }
        Message message = this.obtainMessage(1);
        this.mSimFH.loadEFImgLinearFixed(this.mRecordNumber, message);
    }

    private void startLoadingIcon(int n) {
        this.mId = null;
        this.mIconData = null;
        this.mCurrentIcon = null;
        this.mRecordNumber = n;
        if (this.mIconsCache.containsKey(n)) {
            this.mCurrentIcon = this.mIconsCache.get(n);
            this.postIcon();
            return;
        }
        this.readId();
    }

    public void dispose() {
        this.mSimFH = null;
        HandlerThread handlerThread = sThread;
        if (handlerThread != null) {
            handlerThread.quit();
            sThread = null;
        }
        this.mIconsCache = null;
        sLoader = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleMessage(Message object) {
        try {
            int n = object.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return;
                    }
                    object = (byte[])((AsyncResult)object.obj).result;
                    this.mCurrentIcon = IconLoader.parseToRGB(this.mIconData, this.mIconData.length, false, object);
                    this.mIconsCache.put(this.mRecordNumber, this.mCurrentIcon);
                    this.postIcon();
                    return;
                }
                CatLog.d((Object)this, "load icon done");
                object = (byte[])((AsyncResult)object.obj).result;
                if (this.mId.mCodingScheme == 17) {
                    this.mCurrentIcon = IconLoader.parseToBnW(object, ((byte[])object).length);
                    this.mIconsCache.put(this.mRecordNumber, this.mCurrentIcon);
                    this.postIcon();
                    return;
                }
                if (this.mId.mCodingScheme == 33) {
                    this.mIconData = object;
                    this.readClut();
                    return;
                }
                CatLog.d((Object)this, "else  /postIcon ");
                this.postIcon();
                return;
            }
            if (this.handleImageDescriptor((byte[])((AsyncResult)object.obj).result)) {
                this.readIconData();
                return;
            }
            object = new Exception("Unable to parse image descriptor");
            throw object;
        }
        catch (Exception exception) {
            CatLog.d((Object)this, "Icon load failed");
            this.postIcon();
        }
    }

    @UnsupportedAppUsage
    void loadIcon(int n, Message message) {
        if (message == null) {
            return;
        }
        this.mEndMsg = message;
        this.mState = 1;
        this.startLoadingIcon(n);
    }

    void loadIcons(int[] arrn, Message message) {
        if (arrn != null && arrn.length != 0 && message != null) {
            this.mEndMsg = message;
            this.mIcons = new Bitmap[arrn.length];
            this.mRecordNumbers = arrn;
            this.mCurrentRecordIndex = 0;
            this.mState = 2;
            this.startLoadingIcon(arrn[0]);
            return;
        }
    }
}

