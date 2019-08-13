/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;
import com.android.internal.telephony.cat.CatLog;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.ResultException;
import java.util.ArrayList;
import java.util.List;

public class ComprehensionTlv {
    private static final String LOG_TAG = "ComprehensionTlv";
    private boolean mCr;
    private int mLength;
    private byte[] mRawValue;
    private int mTag;
    private int mValueIndex;

    protected ComprehensionTlv(int n, boolean bl, int n2, byte[] arrby, int n3) {
        this.mTag = n;
        this.mCr = bl;
        this.mLength = n2;
        this.mValueIndex = n3;
        this.mRawValue = arrby;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ComprehensionTlv decode(byte[] var0, int var1_6) throws ResultException {
        block22 : {
            block14 : {
                block21 : {
                    block19 : {
                        block20 : {
                            block17 : {
                                block18 : {
                                    block16 : {
                                        block15 : {
                                            var2_7 = ((Object)var0).length;
                                            var3_8 /* !! */  = var1_6 + 1;
                                            var4_9 /* !! */  = var0[var1_6] & 255;
                                            if (var4_9 /* !! */  == 0 || var4_9 /* !! */  == 255) break block14;
                                            var5_10 = false;
                                            var6_11 = false;
                                            if (var4_9 /* !! */  == 127) break block15;
                                            if (var4_9 /* !! */  == 128) break block14;
                                            if ((var4_9 /* !! */  & 128) != 0) {
                                                var6_11 = true;
                                            }
                                            var4_9 /* !! */  &= -129;
                                            break block16;
                                        }
                                        var4_9 /* !! */  = (int)var0[var3_8 /* !! */ ];
                                        var7_12 /* !! */  = (int)var0[var3_8 /* !! */  + 1];
                                        var7_12 /* !! */  = (var4_9 /* !! */  & 255) << 8 | var7_12 /* !! */  & 255;
                                        var6_11 = var5_10;
                                        if ((32768 & var7_12 /* !! */ ) != 0) {
                                            var6_11 = true;
                                        }
                                        var3_8 /* !! */  += 2;
                                        var4_9 /* !! */  = var7_12 /* !! */  & -32769;
                                    }
                                    var7_12 /* !! */  = var3_8 /* !! */  + 1;
                                    var3_8 /* !! */  = (int)var0[var3_8 /* !! */ ];
                                    if ((var3_8 /* !! */  &= 255) < 128) ** GOTO lbl128
                                    if (var3_8 /* !! */  != 129) break block17;
                                    var3_8 /* !! */  = var7_12 /* !! */  + 1;
                                    var8_13 = 255 & var0[var7_12 /* !! */ ];
                                    if (var8_13 < 128) break block18;
                                    var7_12 /* !! */  = var3_8 /* !! */ ;
                                    var3_8 /* !! */  = var8_13;
                                    ** GOTO lbl128
                                }
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var0 = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21 = new StringBuilder();
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append("length < 0x80 length=");
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(Integer.toHexString(var8_13));
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(" startIndex=");
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(var1_6);
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(" curIndex=");
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(var3_8 /* !! */ );
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(" endIndex=");
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var10_21.append(var2_7);
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                var9_16 = new ResultException((ResultCode)var0, var10_21.toString());
                                var7_12 /* !! */  = var3_8 /* !! */ ;
                                throw var9_16;
                            }
                            if (var3_8 /* !! */  != 130) break block19;
                            var3_8 /* !! */  = (int)var0[var7_12 /* !! */ ];
                            var8_14 /* !! */  = var0[var7_12 /* !! */  + 1];
                            var8_14 /* !! */  = (var3_8 /* !! */  & 255) << 8 | 255 & var8_14 /* !! */ ;
                            var3_8 /* !! */  = var7_12 /* !! */  + 2;
                            if (var8_14 /* !! */  < 256) break block20;
                            var7_12 /* !! */  = (int)var8_14 /* !! */ ;
                            var8_14 /* !! */  = var3_8 /* !! */ ;
                            var3_8 /* !! */  = var7_12 /* !! */ ;
                            var7_12 /* !! */  = (int)var8_14 /* !! */ ;
                            ** GOTO lbl128
                        }
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0 = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22 = new StringBuilder();
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append("two byte length < 0x100 length=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(Integer.toHexString((int)var8_14 /* !! */ ));
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(" startIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(var1_6);
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(" curIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(var3_8 /* !! */ );
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(" endIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_22.append(var2_7);
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var9_17 = new ResultException((ResultCode)var0, var10_22.toString());
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        throw var9_17;
                        {
                            catch (IndexOutOfBoundsException var0_1) {}
                        }
                    }
                    if (var3_8 /* !! */  != 131) break block21;
                    var11_25 = var0[var7_12 /* !! */ ];
                    var3_8 /* !! */  = (int)var0[var7_12 /* !! */  + 1];
                    var8_15 /* !! */  = var0[var7_12 /* !! */  + 2];
                    var8_15 /* !! */  = (var11_25 & 255) << 16 | (var3_8 /* !! */  & 255) << 8 | 255 & var8_15 /* !! */ ;
                    var3_8 /* !! */  = var7_12 /* !! */  + 3;
                    if (var8_15 /* !! */  >= 65536) {
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var3_8 /* !! */  = (int)var8_15 /* !! */ ;
lbl128: // 4 sources:
                        try {
                            return new ComprehensionTlv(var4_9 /* !! */ , var6_11, var3_8 /* !! */ , (byte[])var0, var7_12 /* !! */ );
                        }
                        catch (IndexOutOfBoundsException var0_2) {
                            ** GOTO lbl227
                        }
                    } else {
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var9_18 = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0 = new StringBuilder();
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append("three byte length < 0x10000 length=0x");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(Integer.toHexString((int)var8_15 /* !! */ ));
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(" startIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(var1_6);
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(" curIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(var3_8 /* !! */ );
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(" endIndex=");
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var0.append(var2_7);
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        var10_23 = new ResultException(var9_18, var0.toString());
                        var7_12 /* !! */  = var3_8 /* !! */ ;
                        throw var10_23;
                    }
                    break block22;
                }
                try {
                    var0 = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                    var10_24 = new StringBuilder();
                    var10_24.append("Bad length modifer=");
                    var10_24.append(var3_8 /* !! */ );
                    var10_24.append(" startIndex=");
                    var10_24.append(var1_6);
                    var10_24.append(" curIndex=");
                    var10_24.append(var7_12 /* !! */ );
                    var10_24.append(" endIndex=");
                    var10_24.append(var2_7);
                    var9_19 = new ResultException((ResultCode)var0, var10_24.toString());
                    throw var9_19;
                }
                catch (IndexOutOfBoundsException var0_3) {}
                break block22;
            }
            var7_12 /* !! */  = var3_8 /* !! */ ;
            try {
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0 = new StringBuilder();
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append("decode: unexpected first tag byte=");
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(Integer.toHexString(var4_9 /* !! */ ));
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(", startIndex=");
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(var1_6);
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(" curIndex=");
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(var3_8 /* !! */ );
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(" endIndex=");
                var7_12 /* !! */  = var3_8 /* !! */ ;
                var0.append(var2_7);
                var7_12 /* !! */  = var3_8 /* !! */ ;
                Rlog.d((String)"CAT     ", (String)var0.toString());
                return null;
            }
            catch (IndexOutOfBoundsException var0_4) {
                // empty catch block
            }
        }
        var9_20 = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
        var0_5 = new StringBuilder();
        var0_5.append("IndexOutOfBoundsException startIndex=");
        var0_5.append(var1_6);
        var0_5.append(" curIndex=");
        var0_5.append(var7_12 /* !! */ );
        var0_5.append(" endIndex=");
        var0_5.append(var2_7);
        throw new ResultException(var9_20, var0_5.toString());
    }

    public static List<ComprehensionTlv> decodeMany(byte[] arrby, int n) throws ResultException {
        ArrayList<ComprehensionTlv> arrayList = new ArrayList<ComprehensionTlv>();
        int n2 = arrby.length;
        while (n < n2) {
            ComprehensionTlv comprehensionTlv = ComprehensionTlv.decode(arrby, n);
            if (comprehensionTlv != null) {
                arrayList.add(comprehensionTlv);
                n = comprehensionTlv.mValueIndex + comprehensionTlv.mLength;
                continue;
            }
            CatLog.d(LOG_TAG, "decodeMany: ctlv is null, stop decoding");
            break;
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public int getLength() {
        return this.mLength;
    }

    @UnsupportedAppUsage
    public byte[] getRawValue() {
        return this.mRawValue;
    }

    @UnsupportedAppUsage
    public int getTag() {
        return this.mTag;
    }

    @UnsupportedAppUsage
    public int getValueIndex() {
        return this.mValueIndex;
    }

    public boolean isComprehensionRequired() {
        return this.mCr;
    }
}

