/*
 * Decompiled with CFR 0.145.
 */
package android.util.proto;

import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoStream;
import java.io.IOException;

public class ProtoUtils {
    public static String currentFieldToString(ProtoInputStream protoInputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int n = protoInputStream.getFieldNumber();
        int n2 = protoInputStream.getWireType();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Offset : 0x");
        stringBuilder2.append(Integer.toHexString(protoInputStream.getOffset()));
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("\nField Number : 0x");
        stringBuilder2.append(Integer.toHexString(protoInputStream.getFieldNumber()));
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\nWire Type : ");
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 != 5) {
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("unknown(");
                                stringBuilder2.append(protoInputStream.getWireType());
                                stringBuilder2.append(")");
                                stringBuilder.append(stringBuilder2.toString());
                            } else {
                                stringBuilder.append("fixed32");
                                long l = ProtoStream.makeFieldId(n, 1129576398848L);
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("\nField Value : 0x");
                                stringBuilder2.append(Integer.toHexString(protoInputStream.readInt(l)));
                                stringBuilder.append(stringBuilder2.toString());
                            }
                        } else {
                            stringBuilder.append("end group");
                        }
                    } else {
                        stringBuilder.append("start group");
                    }
                } else {
                    stringBuilder.append("length delimited");
                    long l = ProtoStream.makeFieldId(n, 1151051235328L);
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("\nField Bytes : ");
                    stringBuilder2.append(protoInputStream.readBytes(l));
                    stringBuilder.append(stringBuilder2.toString());
                }
            } else {
                stringBuilder.append("fixed64");
                long l = ProtoStream.makeFieldId(n, 1125281431552L);
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("\nField Value : 0x");
                stringBuilder2.append(Long.toHexString(protoInputStream.readLong(l)));
                stringBuilder.append(stringBuilder2.toString());
            }
        } else {
            stringBuilder.append("varint");
            long l = ProtoStream.makeFieldId(n, 1112396529664L);
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("\nField Value : 0x");
            stringBuilder2.append(Long.toHexString(protoInputStream.readLong(l)));
            stringBuilder.append(stringBuilder2.toString());
        }
        return stringBuilder.toString();
    }

    public static void toAggStatsProto(ProtoOutputStream protoOutputStream, long l, long l2, long l3, long l4) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1112396529665L, l2);
        protoOutputStream.write(1112396529666L, l3);
        protoOutputStream.write(1112396529667L, l4);
        protoOutputStream.end(l);
    }

    public static void toDuration(ProtoOutputStream protoOutputStream, long l, long l2, long l3) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1112396529665L, l2);
        protoOutputStream.write(1112396529666L, l3);
        protoOutputStream.end(l);
    }

    public static void writeBitWiseFlagsToProtoEnum(ProtoOutputStream protoOutputStream, long l, int n, int[] arrn, int[] arrn2) {
        if (arrn2.length == arrn.length) {
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                if (arrn[i] == 0 && n == 0) {
                    protoOutputStream.write(l, arrn2[i]);
                    return;
                }
                if ((arrn[i] & n) == 0) continue;
                protoOutputStream.write(l, arrn2[i]);
            }
            return;
        }
        throw new IllegalArgumentException("The length of origEnums must match protoEnums");
    }
}

