/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.os.UserHandle;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.ProcessState;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class DumpUtils {
    public static final String[] ADJ_MEM_NAMES_CSV;
    static final int[] ADJ_MEM_PROTO_ENUMS;
    static final String[] ADJ_MEM_TAGS;
    public static final String[] ADJ_SCREEN_NAMES_CSV;
    static final int[] ADJ_SCREEN_PROTO_ENUMS;
    static final String[] ADJ_SCREEN_TAGS;
    static final String CSV_SEP = "\t";
    public static final String[] STATE_LABELS;
    public static final String STATE_LABEL_CACHED;
    public static final String STATE_LABEL_TOTAL;
    public static final String[] STATE_NAMES;
    public static final String[] STATE_NAMES_CSV;
    static final int[] STATE_PROTO_ENUMS;
    static final String[] STATE_TAGS;

    static {
        Object[] arrobject = STATE_NAMES = new String[14];
        arrobject[0] = "Persist";
        arrobject[1] = "Top";
        arrobject[2] = "ImpFg";
        arrobject[3] = "ImpBg";
        arrobject[4] = "Backup";
        arrobject[5] = "Service";
        arrobject[6] = "ServRst";
        arrobject[7] = "Receivr";
        arrobject[8] = "HeavyWt";
        arrobject[9] = "Home";
        arrobject[10] = "LastAct";
        arrobject[11] = "CchAct";
        arrobject[12] = "CchCAct";
        arrobject[13] = "CchEmty";
        STATE_LABELS = new String[14];
        arrobject = STATE_LABELS;
        arrobject[0] = "Persistent";
        arrobject[1] = "       Top";
        arrobject[2] = "    Imp Fg";
        arrobject[3] = "    Imp Bg";
        arrobject[4] = "    Backup";
        arrobject[5] = "   Service";
        arrobject[6] = "Service Rs";
        arrobject[7] = "  Receiver";
        arrobject[8] = " Heavy Wgt";
        arrobject[9] = "    (Home)";
        arrobject[10] = "(Last Act)";
        arrobject[11] = " (Cch Act)";
        arrobject[12] = "(Cch CAct)";
        arrobject[13] = "(Cch Emty)";
        STATE_LABEL_CACHED = "  (Cached)";
        STATE_LABEL_TOTAL = "     TOTAL";
        STATE_NAMES_CSV = new String[14];
        arrobject = STATE_NAMES_CSV;
        arrobject[0] = "pers";
        arrobject[1] = "top";
        arrobject[2] = "impfg";
        arrobject[3] = "impbg";
        arrobject[4] = "backup";
        arrobject[5] = "service";
        arrobject[6] = "service-rs";
        arrobject[7] = "receiver";
        arrobject[8] = "heavy";
        arrobject[9] = "home";
        arrobject[10] = "lastact";
        arrobject[11] = "cch-activity";
        arrobject[12] = "cch-aclient";
        arrobject[13] = "cch-empty";
        STATE_TAGS = new String[14];
        arrobject = STATE_TAGS;
        arrobject[0] = "p";
        arrobject[1] = "t";
        arrobject[2] = "f";
        arrobject[3] = "b";
        arrobject[4] = "u";
        arrobject[5] = "s";
        arrobject[6] = "x";
        arrobject[7] = "r";
        arrobject[8] = "w";
        arrobject[9] = "h";
        arrobject[10] = "l";
        arrobject[11] = "a";
        arrobject[12] = "c";
        arrobject[13] = "e";
        STATE_PROTO_ENUMS = new int[14];
        arrobject = STATE_PROTO_ENUMS;
        arrobject[0] = (String)true;
        arrobject[1] = (String)2;
        arrobject[2] = (String)3;
        arrobject[3] = (String)4;
        arrobject[4] = (String)5;
        arrobject[5] = (String)6;
        arrobject[6] = (String)7;
        arrobject[7] = (String)8;
        arrobject[8] = (String)9;
        arrobject[9] = (String)10;
        arrobject[10] = (String)11;
        arrobject[11] = (String)12;
        arrobject[12] = (String)13;
        arrobject[13] = (String)14;
        ADJ_SCREEN_NAMES_CSV = new String[]{"off", "on"};
        ADJ_MEM_NAMES_CSV = new String[]{"norm", "mod", "low", "crit"};
        ADJ_SCREEN_TAGS = new String[]{"0", "1"};
        ADJ_SCREEN_PROTO_ENUMS = new int[]{1, 2};
        ADJ_MEM_TAGS = new String[]{"n", "m", "l", "c"};
        ADJ_MEM_PROTO_ENUMS = new int[]{1, 2, 3, 4};
    }

    private DumpUtils() {
    }

    public static String collapseString(String string2, String string3) {
        if (string3.startsWith(string2)) {
            int n;
            int n2 = string3.length();
            if (n2 == (n = string2.length())) {
                return "";
            }
            if (n2 >= n && string3.charAt(n) == '.') {
                return string3.substring(n);
            }
        }
        return string3;
    }

    public static void dumpAdjTimesCheckin(PrintWriter printWriter, String string2, long[] arrl, int n, long l, long l2) {
        for (int i = 0; i < 8; i += 4) {
            for (int j = 0; j < 4; ++j) {
                long l3;
                int n2 = j + i;
                long l4 = l3 = arrl[n2];
                if (n == n2) {
                    l4 = l3 + (l2 - l);
                }
                if (l4 == 0L) continue;
                DumpUtils.printAdjTagAndValue(printWriter, n2, l4);
            }
        }
    }

    public static void dumpProcessListCsv(PrintWriter printWriter, ArrayList<ProcessState> arrayList, boolean bl, int[] arrn, boolean bl2, int[] arrn2, boolean bl3, int[] arrn3, long l) {
        printWriter.print("process");
        printWriter.print(CSV_SEP);
        printWriter.print("uid");
        printWriter.print(CSV_SEP);
        printWriter.print("vers");
        int[] arrn4 = null;
        Object object = bl ? arrn : null;
        Object object2 = bl2 ? arrn2 : null;
        if (bl3) {
            arrn4 = arrn3;
        }
        DumpUtils.dumpStateHeadersCsv(printWriter, CSV_SEP, (int[])object, object2, arrn4);
        printWriter.println();
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            object = arrayList.get(i);
            printWriter.print(((ProcessState)object).getName());
            printWriter.print(CSV_SEP);
            UserHandle.formatUid(printWriter, ((ProcessState)object).getUid());
            printWriter.print(CSV_SEP);
            printWriter.print(((ProcessState)object).getVersion());
            ((ProcessState)object).dumpCsv(printWriter, bl, arrn, bl2, arrn2, bl3, arrn3, l);
            printWriter.println();
        }
    }

    public static void dumpProcessSummaryLocked(PrintWriter printWriter, String string2, String string3, ArrayList<ProcessState> arrayList, int[] arrn, int[] arrn2, int[] arrn3, long l, long l2) {
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            arrayList.get(i).dumpSummary(printWriter, string2, string3, arrn, arrn2, arrn3, l, l2);
        }
    }

    public static long dumpSingleTime(PrintWriter printWriter, String string2, long[] arrl, int n, long l, long l2) {
        long l3 = 0L;
        int n2 = -1;
        for (int i = 0; i < 8; i += 4) {
            int n3 = -1;
            for (int j = 0; j < 4; ++j) {
                int n4 = j + i;
                long l4 = arrl[n4];
                String string3 = "";
                long l5 = l4;
                String string4 = string3;
                if (n == n4) {
                    l5 = l4 += l2 - l;
                    string4 = string3;
                    if (printWriter != null) {
                        string4 = " (running)";
                        l5 = l4;
                    }
                }
                l4 = l3;
                int n5 = n2;
                n4 = n3;
                if (l5 != 0L) {
                    n5 = n2;
                    n4 = n3;
                    if (printWriter != null) {
                        printWriter.print(string2);
                        n4 = -1;
                        n2 = n2 != i ? i : -1;
                        DumpUtils.printScreenLabel(printWriter, n2);
                        n5 = i;
                        n2 = n4;
                        if (n3 != j) {
                            n2 = j;
                        }
                        DumpUtils.printMemLabel(printWriter, n2, '\u0000');
                        n4 = j;
                        printWriter.print(": ");
                        TimeUtils.formatDuration(l5, printWriter);
                        printWriter.println(string4);
                    }
                    l4 = l3 + l5;
                }
                l3 = l4;
                n2 = n5;
                n3 = n4;
            }
        }
        if (l3 != 0L && printWriter != null) {
            printWriter.print(string2);
            printWriter.print("    TOTAL: ");
            TimeUtils.formatDuration(l3, printWriter);
            printWriter.println();
        }
        return l3;
    }

    private static void dumpStateHeadersCsv(PrintWriter printWriter, String string2, int[] arrn, int[] arrn2, int[] arrn3) {
        int n = arrn != null ? arrn.length : 1;
        int n2 = arrn2 != null ? arrn2.length : 1;
        int n3 = arrn3 != null ? arrn3.length : 1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n3; ++k) {
                    boolean bl;
                    printWriter.print(string2);
                    boolean bl2 = bl = false;
                    if (arrn != null) {
                        bl2 = bl;
                        if (arrn.length > 1) {
                            DumpUtils.printScreenLabelCsv(printWriter, arrn[i]);
                            bl2 = true;
                        }
                    }
                    bl = bl2;
                    if (arrn2 != null) {
                        bl = bl2;
                        if (arrn2.length > 1) {
                            if (bl2) {
                                printWriter.print("-");
                            }
                            DumpUtils.printMemLabelCsv(printWriter, arrn2[j]);
                            bl = true;
                        }
                    }
                    if (arrn3 == null || arrn3.length <= 1) continue;
                    if (bl) {
                        printWriter.print("-");
                    }
                    printWriter.print(STATE_NAMES_CSV[arrn3[k]]);
                }
            }
        }
    }

    public static void printAdjTag(PrintWriter printWriter, int n) {
        n = DumpUtils.printArrayEntry(printWriter, ADJ_SCREEN_TAGS, n, 4);
        DumpUtils.printArrayEntry(printWriter, ADJ_MEM_TAGS, n, 1);
    }

    public static void printAdjTagAndValue(PrintWriter printWriter, int n, long l) {
        printWriter.print(',');
        DumpUtils.printAdjTag(printWriter, n);
        printWriter.print(':');
        printWriter.print(l);
    }

    public static int printArrayEntry(PrintWriter printWriter, String[] arrstring, int n, int n2) {
        int n3 = n / n2;
        if (n3 >= 0 && n3 < arrstring.length) {
            printWriter.print(arrstring[n3]);
        } else {
            printWriter.print('?');
        }
        return n - n3 * n2;
    }

    public static void printMemLabel(PrintWriter printWriter, int n, char c) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            printWriter.print("????");
                            if (c != '\u0000') {
                                printWriter.print(c);
                            }
                        } else {
                            printWriter.print("Crit");
                            if (c != '\u0000') {
                                printWriter.print(c);
                            }
                        }
                    } else {
                        printWriter.print(" Low");
                        if (c != '\u0000') {
                            printWriter.print(c);
                        }
                    }
                } else {
                    printWriter.print(" Mod");
                    if (c != '\u0000') {
                        printWriter.print(c);
                    }
                }
            } else {
                printWriter.print("Norm");
                if (c != '\u0000') {
                    printWriter.print(c);
                }
            }
        } else {
            printWriter.print("    ");
            if (c != '\u0000') {
                printWriter.print(' ');
            }
        }
    }

    public static void printMemLabelCsv(PrintWriter printWriter, int n) {
        if (n >= 0) {
            if (n <= 3) {
                printWriter.print(ADJ_MEM_NAMES_CSV[n]);
            } else {
                printWriter.print("???");
            }
        }
    }

    public static void printPercent(PrintWriter printWriter, double d) {
        if ((d *= 100.0) < 1.0) {
            printWriter.print(String.format("%.2f", d));
        } else if (d < 10.0) {
            printWriter.print(String.format("%.1f", d));
        } else {
            printWriter.print(String.format("%.0f", d));
        }
        printWriter.print("%");
    }

    public static void printProcStateAdjTagProto(ProtoOutputStream protoOutputStream, long l, long l2, int n) {
        n = DumpUtils.printProto(protoOutputStream, l, ADJ_SCREEN_PROTO_ENUMS, n, 56);
        DumpUtils.printProto(protoOutputStream, l2, ADJ_MEM_PROTO_ENUMS, n, 14);
    }

    public static void printProcStateDurationProto(ProtoOutputStream protoOutputStream, long l, int n, long l2) {
        l = protoOutputStream.start(l);
        DumpUtils.printProto(protoOutputStream, 1159641169923L, STATE_PROTO_ENUMS, n, 1);
        protoOutputStream.write(1112396529668L, l2);
        protoOutputStream.end(l);
    }

    public static void printProcStateTag(PrintWriter printWriter, int n) {
        n = DumpUtils.printArrayEntry(printWriter, ADJ_SCREEN_TAGS, n, 56);
        n = DumpUtils.printArrayEntry(printWriter, ADJ_MEM_TAGS, n, 14);
        DumpUtils.printArrayEntry(printWriter, STATE_TAGS, n, 1);
    }

    public static void printProcStateTagAndValue(PrintWriter printWriter, int n, long l) {
        printWriter.print(',');
        DumpUtils.printProcStateTag(printWriter, n);
        printWriter.print(':');
        printWriter.print(l);
    }

    public static void printProcStateTagProto(ProtoOutputStream protoOutputStream, long l, long l2, long l3, int n) {
        n = DumpUtils.printProto(protoOutputStream, l, ADJ_SCREEN_PROTO_ENUMS, n, 56);
        n = DumpUtils.printProto(protoOutputStream, l2, ADJ_MEM_PROTO_ENUMS, n, 14);
        DumpUtils.printProto(protoOutputStream, l3, STATE_PROTO_ENUMS, n, 1);
    }

    public static int printProto(ProtoOutputStream protoOutputStream, long l, int[] arrn, int n, int n2) {
        int n3 = n / n2;
        if (n3 >= 0 && n3 < arrn.length) {
            protoOutputStream.write(l, arrn[n3]);
        }
        return n - n3 * n2;
    }

    public static void printScreenLabel(PrintWriter printWriter, int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 4) {
                    printWriter.print("????/");
                } else {
                    printWriter.print(" SOn/");
                }
            } else {
                printWriter.print("SOff/");
            }
        } else {
            printWriter.print("     ");
        }
    }

    public static void printScreenLabelCsv(PrintWriter printWriter, int n) {
        block4 : {
            if (n == -1) break block4;
            if (n != 0) {
                if (n != 4) {
                    printWriter.print("???");
                } else {
                    printWriter.print(ADJ_SCREEN_NAMES_CSV[1]);
                }
            } else {
                printWriter.print(ADJ_SCREEN_NAMES_CSV[0]);
            }
        }
    }
}

