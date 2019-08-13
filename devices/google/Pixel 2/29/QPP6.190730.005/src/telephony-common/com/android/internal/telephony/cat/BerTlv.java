/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.ComprehensionTlv;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ResultCode;
import com.android.internal.telephony.cat.ResultException;
import java.util.Iterator;
import java.util.List;

class BerTlv {
    public static final int BER_EVENT_DOWNLOAD_TAG = 214;
    public static final int BER_MENU_SELECTION_TAG = 211;
    public static final int BER_PROACTIVE_COMMAND_TAG = 208;
    public static final int BER_UNKNOWN_TAG = 0;
    private List<ComprehensionTlv> mCompTlvs = null;
    private boolean mLengthValid = true;
    private int mTag = 0;

    private BerTlv(int n, List<ComprehensionTlv> list, boolean bl) {
        this.mTag = n;
        this.mCompTlvs = list;
        this.mLengthValid = bl;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static BerTlv decode(byte[] object) throws ResultException {
        Object object3;
        Object object2;
        int n;
        boolean bl;
        block13 : {
            boolean bl2;
            int n2;
            block21 : {
                int n3;
                block20 : {
                    void var0_6;
                    block19 : {
                        int n4;
                        boolean bl3;
                        block14 : {
                            block17 : {
                                block18 : {
                                    block16 : {
                                        block15 : {
                                            n3 = ((Object)object).length;
                                            bl3 = false;
                                            bl = true;
                                            bl2 = true;
                                            object2 = 0 + 1;
                                            object3 = object[0];
                                            n2 = object3 & 255;
                                            if (n2 != 208) break block14;
                                            object3 = object2 + 1;
                                            n = (object2 = (Object)object[object2]) & 255;
                                            if (n >= 128) break block15;
                                            object2 = object3;
                                            object3 = n;
                                            break block16;
                                        }
                                        if (n != 129) break block17;
                                        object2 = object3 + true;
                                        if ((object3 = (Object)(object[object3] & 255)) < 128) break block18;
                                    }
                                    n = n2;
                                    break block19;
                                }
                                try {
                                    object = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("length < 0x80 length=");
                                    stringBuilder.append(Integer.toHexString(0));
                                    stringBuilder.append(" curIndex=");
                                    stringBuilder.append((int)object2);
                                    stringBuilder.append(" endIndex=");
                                    stringBuilder.append(n3);
                                    ResultException resultException = new ResultException((ResultCode)((Object)object), stringBuilder.toString());
                                    throw resultException;
                                }
                                catch (ResultException resultException) {
                                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD, var0_6.explanation());
                                }
                                catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
                                break block20;
                            }
                            try {
                                object = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Expected first byte to be length or a length tag and < 0x81 byte= ");
                                stringBuilder.append(Integer.toHexString(n));
                                stringBuilder.append(" curIndex=");
                                stringBuilder.append((int)object3);
                                stringBuilder.append(" endIndex=");
                                stringBuilder.append(n3);
                                ResultException resultException = new ResultException((ResultCode)((Object)object), stringBuilder.toString());
                                throw resultException;
                            }
                            catch (ResultException resultException) {
                                throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD, var0_6.explanation());
                            }
                            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                object2 = object3;
                            }
                            break block20;
                        }
                        try {
                            n4 = ComprehensionTlvTag.COMMAND_DETAILS.value();
                            n = n2;
                        }
                        catch (ResultException resultException) {
                            // empty catch block
                        }
                        object3 = bl3;
                        if (n4 == (n2 & -129)) {
                            n = 0;
                            object2 = 0;
                            object3 = bl3;
                        }
                    }
                    if (n3 - object2 < object3) {
                        ResultCode resultCode = ResultCode.CMD_DATA_NOT_UNDERSTOOD;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Command had extra data endIndex=");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" curIndex=");
                        ((StringBuilder)object).append((int)object2);
                        ((StringBuilder)object).append(" length=");
                        ((StringBuilder)object).append((int)object3);
                        throw new ResultException(resultCode, ((StringBuilder)object).toString());
                    }
                    break block21;
                    throw new ResultException(ResultCode.CMD_DATA_NOT_UNDERSTOOD, var0_6.explanation());
                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        // empty catch block
                    }
                }
                ResultCode resultCode = ResultCode.REQUIRED_VALUES_MISSING;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IndexOutOfBoundsException  curIndex=");
                stringBuilder.append((int)object2);
                stringBuilder.append(" endIndex=");
                stringBuilder.append(n3);
                throw new ResultException(resultCode, stringBuilder.toString());
            }
            object = ComprehensionTlv.decodeMany((byte[])object, object2);
            if (n != 208) return new BerTlv(n, (List<ComprehensionTlv>)object, bl);
            object2 = 0;
            Iterator iterator = object.iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break block13;
                n2 = ((ComprehensionTlv)iterator.next()).getLength();
                if (n2 >= 128 && n2 <= 255) {
                    object2 += n2 + 3;
                    continue;
                }
                if (n2 < 0 || n2 >= 128) break;
                object2 += n2 + 2;
            } while (true);
            bl = false;
        }
        if (object3 == object2) return new BerTlv(n, (List<ComprehensionTlv>)object, bl);
        bl = false;
        return new BerTlv(n, (List<ComprehensionTlv>)object, bl);
    }

    public List<ComprehensionTlv> getComprehensionTlvs() {
        return this.mCompTlvs;
    }

    public int getTag() {
        return this.mTag;
    }

    public boolean isLengthValid() {
        return this.mLengthValid;
    }
}

