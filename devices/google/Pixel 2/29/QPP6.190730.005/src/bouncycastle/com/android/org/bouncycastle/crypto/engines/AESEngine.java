/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Pack;

public class AESEngine
implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final byte[] S = new byte[]{99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] Si = new byte[]{82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] T0;
    private static final int[] Tinv0;
    private static final int m1 = -2139062144;
    private static final int m2 = 2139062143;
    private static final int m3 = 27;
    private static final int m4 = -1061109568;
    private static final int m5 = 1061109567;
    private static final int[] rcon;
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int ROUNDS;
    private int[][] WorkingKey = null;
    private boolean forEncryption;
    private byte[] s;

    static {
        rcon = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
        T0 = new int[]{-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};
        Tinv0 = new int[]{1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};
    }

    private static int FFmulX(int n) {
        return (2139062143 & n) << 1 ^ ((-2139062144 & n) >>> 7) * 27;
    }

    private static int FFmulX2(int n) {
        int n2 = -1061109568 & n;
        n2 ^= n2 >>> 1;
        return n2 >>> 2 ^ (1061109567 & n) << 2 ^ n2 >>> 5;
    }

    private void decryptBlock(int[][] arrn) {
        int[] arrn2;
        int n;
        int n2;
        int n3 = this.C0;
        int n4 = this.ROUNDS;
        int n5 = n3 ^ arrn[n4][0];
        int n6 = this.C1 ^ arrn[n4][1];
        int n7 = this.C2 ^ arrn[n4][2];
        n3 = n4 - 1;
        int n8 = this.C3;
        n4 = arrn[n4][3] ^ n8;
        while (n3 > 1) {
            arrn2 = Tinv0;
            n8 = arrn2[n5 & 255];
            n8 = AESEngine.shift(arrn2[n4 >> 8 & 255], 24) ^ n8 ^ AESEngine.shift(Tinv0[n7 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n6 >> 24 & 255], 8) ^ arrn[n3][0];
            arrn2 = Tinv0;
            n = arrn2[n6 & 255];
            n = AESEngine.shift(arrn2[n5 >> 8 & 255], 24) ^ n ^ AESEngine.shift(Tinv0[n4 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n7 >> 24 & 255], 8) ^ arrn[n3][1];
            arrn2 = Tinv0;
            n2 = arrn2[n7 & 255];
            n2 = AESEngine.shift(arrn2[n6 >> 8 & 255], 24) ^ n2 ^ AESEngine.shift(Tinv0[n5 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n4 >> 24 & 255], 8) ^ arrn[n3][2];
            arrn2 = Tinv0;
            n4 = arrn2[n4 & 255];
            n7 = AESEngine.shift(arrn2[n7 >> 8 & 255], 24);
            n6 = AESEngine.shift(Tinv0[n6 >> 16 & 255], 16);
            n5 = AESEngine.shift(Tinv0[n5 >> 24 & 255], 8);
            int n9 = n3 - 1;
            n3 = n7 ^ n4 ^ n6 ^ n5 ^ arrn[n3][3];
            arrn2 = Tinv0;
            n5 = arrn2[n8 & 255];
            n5 = AESEngine.shift(arrn2[n3 >> 8 & 255], 24) ^ n5 ^ AESEngine.shift(Tinv0[n2 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n >> 24 & 255], 8) ^ arrn[n9][0];
            arrn2 = Tinv0;
            n6 = arrn2[n & 255];
            n6 = AESEngine.shift(arrn2[n8 >> 8 & 255], 24) ^ n6 ^ AESEngine.shift(Tinv0[n3 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n2 >> 24 & 255], 8) ^ arrn[n9][1];
            arrn2 = Tinv0;
            n7 = arrn2[n2 & 255];
            n7 = AESEngine.shift(arrn2[n >> 8 & 255], 24) ^ n7 ^ AESEngine.shift(Tinv0[n8 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n3 >> 24 & 255], 8) ^ arrn[n9][2];
            arrn2 = Tinv0;
            n3 = arrn2[n3 & 255];
            n4 = AESEngine.shift(arrn2[n2 >> 8 & 255], 24) ^ n3 ^ AESEngine.shift(Tinv0[n >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n8 >> 24 & 255], 8) ^ arrn[n9][3];
            n3 = n9 - 1;
        }
        arrn2 = Tinv0;
        n8 = arrn2[n5 & 255];
        n8 = AESEngine.shift(arrn2[n4 >> 8 & 255], 24) ^ n8 ^ AESEngine.shift(Tinv0[n7 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n6 >> 24 & 255], 8) ^ arrn[n3][0];
        arrn2 = Tinv0;
        n = arrn2[n6 & 255];
        n = AESEngine.shift(arrn2[n5 >> 8 & 255], 24) ^ n ^ AESEngine.shift(Tinv0[n4 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n7 >> 24 & 255], 8) ^ arrn[n3][1];
        arrn2 = Tinv0;
        n2 = arrn2[n7 & 255];
        n2 = AESEngine.shift(arrn2[n6 >> 8 & 255], 24) ^ n2 ^ AESEngine.shift(Tinv0[n5 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n4 >> 24 & 255], 8) ^ arrn[n3][2];
        arrn2 = Tinv0;
        n4 = arrn2[n4 & 255];
        n3 = AESEngine.shift(arrn2[n7 >> 8 & 255], 24) ^ n4 ^ AESEngine.shift(Tinv0[n6 >> 16 & 255], 16) ^ AESEngine.shift(Tinv0[n5 >> 24 & 255], 8) ^ arrn[n3][3];
        byte[] arrby = Si;
        n5 = arrby[n8 & 255];
        arrn2 = this.s;
        this.C0 = (arrn2[n3 >> 8 & 255] & 255) << 8 ^ n5 & 255 ^ (arrn2[n2 >> 16 & 255] & 255) << 16 ^ arrby[n >> 24 & 255] << 24 ^ arrn[0][0];
        this.C1 = arrn2[n & 255] & 255 ^ (arrn2[n8 >> 8 & 255] & 255) << 8 ^ (arrby[n3 >> 16 & 255] & 255) << 16 ^ arrn2[n2 >> 24 & 255] << 24 ^ arrn[0][1];
        this.C2 = arrn2[n2 & 255] & 255 ^ (arrby[n >> 8 & 255] & 255) << 8 ^ (arrby[n8 >> 16 & 255] & 255) << 16 ^ arrn2[n3 >> 24 & 255] << 24 ^ arrn[0][2];
        n3 = arrby[n3 & 255];
        n5 = arrn2[n2 >> 8 & 255];
        this.C3 = (arrn2[n >> 16 & 255] & 255) << 16 ^ (n3 & 255 ^ (n5 & 255) << 8) ^ arrn2[n8 >> 24 & 255] << 24 ^ arrn[0][3];
    }

    private void encryptBlock(int[][] arrn) {
        int n;
        int n2;
        int n3;
        int[] arrn2;
        int n4;
        int n5 = this.C0 ^ arrn[0][0];
        int n6 = this.C1 ^ arrn[0][1];
        int n7 = this.C2 ^ arrn[0][2];
        int n8 = 1;
        int n9 = this.C3 ^ arrn[0][3];
        while (n8 < this.ROUNDS - 1) {
            arrn2 = T0;
            n = arrn2[n5 & 255];
            n = AESEngine.shift(arrn2[n6 >> 8 & 255], 24) ^ n ^ AESEngine.shift(T0[n7 >> 16 & 255], 16) ^ AESEngine.shift(T0[n9 >> 24 & 255], 8) ^ arrn[n8][0];
            arrn2 = T0;
            n2 = arrn2[n6 & 255];
            n2 = AESEngine.shift(arrn2[n7 >> 8 & 255], 24) ^ n2 ^ AESEngine.shift(T0[n9 >> 16 & 255], 16) ^ AESEngine.shift(T0[n5 >> 24 & 255], 8) ^ arrn[n8][1];
            arrn2 = T0;
            n3 = arrn2[n7 & 255];
            n3 = AESEngine.shift(arrn2[n9 >> 8 & 255], 24) ^ n3 ^ AESEngine.shift(T0[n5 >> 16 & 255], 16) ^ AESEngine.shift(T0[n6 >> 24 & 255], 8) ^ arrn[n8][2];
            arrn2 = T0;
            n9 = arrn2[n9 & 255];
            n5 = AESEngine.shift(arrn2[n5 >> 8 & 255], 24);
            n6 = AESEngine.shift(T0[n6 >> 16 & 255], 16);
            n7 = AESEngine.shift(T0[n7 >> 24 & 255], 8);
            n4 = n8 + 1;
            n8 = arrn[n8][3] ^ (n5 ^ n9 ^ n6 ^ n7);
            arrn2 = T0;
            n7 = arrn2[n & 255];
            n5 = AESEngine.shift(arrn2[n2 >> 8 & 255], 24) ^ n7 ^ AESEngine.shift(T0[n3 >> 16 & 255], 16) ^ AESEngine.shift(T0[n8 >> 24 & 255], 8) ^ arrn[n4][0];
            arrn2 = T0;
            n7 = arrn2[n2 & 255];
            n6 = AESEngine.shift(arrn2[n3 >> 8 & 255], 24) ^ n7 ^ AESEngine.shift(T0[n8 >> 16 & 255], 16) ^ AESEngine.shift(T0[n >> 24 & 255], 8) ^ arrn[n4][1];
            arrn2 = T0;
            n7 = arrn2[n3 & 255];
            n7 = AESEngine.shift(arrn2[n8 >> 8 & 255], 24) ^ n7 ^ AESEngine.shift(T0[n >> 16 & 255], 16) ^ AESEngine.shift(T0[n2 >> 24 & 255], 8) ^ arrn[n4][2];
            arrn2 = T0;
            n8 = arrn2[n8 & 255];
            n9 = AESEngine.shift(arrn2[n >> 8 & 255], 24) ^ n8 ^ AESEngine.shift(T0[n2 >> 16 & 255], 16) ^ AESEngine.shift(T0[n3 >> 24 & 255], 8) ^ arrn[n4][3];
            n8 = n4 + 1;
        }
        arrn2 = T0;
        n = arrn2[n5 & 255];
        n3 = AESEngine.shift(arrn2[n6 >> 8 & 255], 24) ^ n ^ AESEngine.shift(T0[n7 >> 16 & 255], 16) ^ AESEngine.shift(T0[n9 >> 24 & 255], 8) ^ arrn[n8][0];
        arrn2 = T0;
        n = arrn2[n6 & 255];
        n2 = AESEngine.shift(arrn2[n7 >> 8 & 255], 24) ^ n ^ AESEngine.shift(T0[n9 >> 16 & 255], 16) ^ AESEngine.shift(T0[n5 >> 24 & 255], 8) ^ arrn[n8][1];
        arrn2 = T0;
        n = arrn2[n7 & 255];
        n = AESEngine.shift(arrn2[n9 >> 8 & 255], 24) ^ n ^ AESEngine.shift(T0[n5 >> 16 & 255], 16) ^ AESEngine.shift(T0[n6 >> 24 & 255], 8) ^ arrn[n8][2];
        arrn2 = T0;
        n9 = arrn2[n9 & 255];
        n5 = AESEngine.shift(arrn2[n5 >> 8 & 255], 24);
        n6 = AESEngine.shift(T0[n6 >> 16 & 255], 16);
        n4 = AESEngine.shift(T0[n7 >> 24 & 255], 8);
        n7 = n8 + 1;
        n5 = arrn[n8][3] ^ (n5 ^ n9 ^ n6 ^ n4);
        byte[] arrby = S;
        n8 = arrby[n3 & 255];
        n6 = arrby[n2 >> 8 & 255];
        arrn2 = this.s;
        this.C0 = n8 & 255 ^ (n6 & 255) << 8 ^ (arrn2[n >> 16 & 255] & 255) << 16 ^ arrn2[n5 >> 24 & 255] << 24 ^ arrn[n7][0];
        this.C1 = arrn2[n2 & 255] & 255 ^ (arrby[n >> 8 & 255] & 255) << 8 ^ (arrby[n5 >> 16 & 255] & 255) << 16 ^ arrn2[n3 >> 24 & 255] << 24 ^ arrn[n7][1];
        this.C2 = arrn2[n & 255] & 255 ^ (arrby[n5 >> 8 & 255] & 255) << 8 ^ (arrby[n3 >> 16 & 255] & 255) << 16 ^ arrby[n2 >> 24 & 255] << 24 ^ arrn[n7][2];
        n8 = arrn2[n5 & 255];
        n6 = arrn2[n3 >> 8 & 255];
        n5 = arrn2[n2 >> 16 & 255];
        this.C3 = arrby[n >> 24 & 255] << 24 ^ (n8 & 255 ^ (n6 & 255) << 8 ^ (n5 & 255) << 16) ^ arrn[n7][3];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int[][] generateWorkingKey(byte[] arrby, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = arrby.length;
        if (n5 < 16 || n5 > 32 || (n5 & 7) != 0) throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        this.ROUNDS = (n5 >>>= 2) + 6;
        int[][] arrn = new int[this.ROUNDS + 1][4];
        if (n5 != 4) {
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            if (n5 != 6) {
                int n11;
                if (n5 != 8) throw new IllegalStateException("Should never get here");
                arrn[0][0] = n10 = Pack.littleEndianToInt(arrby, 0);
                arrn[0][1] = n8 = Pack.littleEndianToInt(arrby, 4);
                arrn[0][2] = n = Pack.littleEndianToInt(arrby, 8);
                arrn[0][3] = n3 = Pack.littleEndianToInt(arrby, 12);
                arrn[1][0] = n6 = Pack.littleEndianToInt(arrby, 16);
                arrn[1][1] = n7 = Pack.littleEndianToInt(arrby, 20);
                arrn[1][2] = n2 = Pack.littleEndianToInt(arrby, 24);
                arrn[1][3] = n4 = Pack.littleEndianToInt(arrby, 28);
                n5 = 1;
                n9 = 2;
                do {
                    n11 = n5;
                    if (n9 >= 14) break;
                    int n12 = AESEngine.subWord(AESEngine.shift(n4, 8));
                    n5 = n11 << 1;
                    arrn[n9][0] = n10 ^= n12 ^ n11;
                    arrn[n9][1] = n8 ^= n10;
                    arrn[n9][2] = n ^= n8;
                    arrn[n9][3] = n3 ^= n;
                    arrn[n9 + 1][0] = n6 ^= AESEngine.subWord(n3);
                    arrn[n9 + 1][1] = n7 ^= n6;
                    arrn[n9 + 1][2] = n2 ^= n7;
                    arrn[n9 + 1][3] = n4 ^= n2;
                    n9 += 2;
                } while (true);
                arrn[14][0] = n5 = n10 ^ (AESEngine.subWord(AESEngine.shift(n4, 8)) ^ n11);
                arrn[14][1] = n5 = n8 ^ n5;
                arrn[14][2] = n5 = n ^ n5;
                arrn[14][3] = n3 ^ n5;
            } else {
                arrn[0][0] = n10 = Pack.littleEndianToInt(arrby, 0);
                arrn[0][1] = n4 = Pack.littleEndianToInt(arrby, 4);
                arrn[0][2] = n3 = Pack.littleEndianToInt(arrby, 8);
                arrn[0][3] = n2 = Pack.littleEndianToInt(arrby, 12);
                arrn[1][0] = n = Pack.littleEndianToInt(arrby, 16);
                arrn[1][1] = n5 = Pack.littleEndianToInt(arrby, 20);
                n7 = AESEngine.subWord(AESEngine.shift(n5, 8));
                n8 = 1 << 1;
                arrn[1][2] = n10 ^= n7 ^ 1;
                arrn[1][3] = n7 = n4 ^ n10;
                arrn[2][0] = n3 ^= n7;
                arrn[2][1] = n2 ^= n3;
                arrn[2][2] = n4 = n ^ n2;
                arrn[2][3] = n = n5 ^ n4;
                for (n5 = 3; n5 < 12; n5 += 3) {
                    n6 = AESEngine.subWord(AESEngine.shift(n, 8));
                    n9 = n8 << 1;
                    arrn[n5][0] = n10 ^= n6 ^ n8;
                    arrn[n5][1] = n7 ^= n10;
                    arrn[n5][2] = n3 ^= n7;
                    arrn[n5][3] = n2 ^= n3;
                    arrn[n5 + 1][0] = n4 ^= n2;
                    arrn[n5 + 1][1] = n ^= n4;
                    n6 = AESEngine.subWord(AESEngine.shift(n, 8));
                    n8 = n9 << 1;
                    arrn[n5 + 1][2] = n10 ^= n6 ^ n9;
                    arrn[n5 + 1][3] = n7 ^= n10;
                    arrn[n5 + 2][0] = n3 ^= n7;
                    arrn[n5 + 2][1] = n2 ^= n3;
                    arrn[n5 + 2][2] = n4 ^= n2;
                    arrn[n5 + 2][3] = n ^= n4;
                }
                arrn[12][0] = n5 = n10 ^ (AESEngine.subWord(AESEngine.shift(n, 8)) ^ n8);
                arrn[12][1] = n5 = n7 ^ n5;
                arrn[12][2] = n5 = n3 ^ n5;
                arrn[12][3] = n2 ^ n5;
            }
        } else {
            arrn[0][0] = n4 = Pack.littleEndianToInt(arrby, 0);
            arrn[0][1] = n3 = Pack.littleEndianToInt(arrby, 4);
            arrn[0][2] = n = Pack.littleEndianToInt(arrby, 8);
            arrn[0][3] = n2 = Pack.littleEndianToInt(arrby, 12);
            for (n5 = 1; n5 <= 10; ++n5) {
                arrn[n5][0] = n4 ^= AESEngine.subWord(AESEngine.shift(n2, 8)) ^ rcon[n5 - 1];
                arrn[n5][1] = n3 ^= n4;
                arrn[n5][2] = n ^= n3;
                arrn[n5][3] = n2 ^= n;
            }
        }
        if (bl) return arrn;
        for (n5 = 1; n5 < this.ROUNDS; ++n5) {
            for (n2 = 0; n2 < 4; ++n2) {
                arrn[n5][n2] = AESEngine.inv_mcol(arrn[n5][n2]);
            }
        }
        return arrn;
    }

    private static int inv_mcol(int n) {
        int n2 = AESEngine.shift(n, 8) ^ n;
        n ^= AESEngine.FFmulX(n2);
        return n ^ (AESEngine.shift(n2 ^= AESEngine.FFmulX2(n), 16) ^ n2);
    }

    private void packBlock(byte[] arrby, int n) {
        int n2 = n + 1;
        int n3 = this.C0;
        arrby[n] = (byte)n3;
        int n4 = n2 + 1;
        arrby[n2] = (byte)(n3 >> 8);
        n = n4 + 1;
        arrby[n4] = (byte)(n3 >> 16);
        n2 = n + 1;
        arrby[n] = (byte)(n3 >> 24);
        n3 = n2 + 1;
        n = this.C1;
        arrby[n2] = (byte)n;
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >> 8);
        n4 = n2 + 1;
        arrby[n2] = (byte)(n >> 16);
        n3 = n4 + 1;
        arrby[n4] = (byte)(n >> 24);
        n2 = n3 + 1;
        n = this.C2;
        arrby[n3] = (byte)n;
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 8);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >> 16);
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 24);
        n2 = n3 + 1;
        n = this.C3;
        arrby[n3] = (byte)n;
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 8);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >> 16);
        arrby[n2] = (byte)(n >> 24);
    }

    private static int shift(int n, int n2) {
        return n >>> n2 | n << -n2;
    }

    private static int subWord(int n) {
        byte[] arrby = S;
        byte by = arrby[n & 255];
        byte by2 = arrby[n >> 8 & 255];
        byte by3 = arrby[n >> 16 & 255];
        return arrby[n >> 24 & 255] << 24 | (by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16);
    }

    private void unpackBlock(byte[] arrby, int n) {
        int n2 = n + 1;
        int n3 = this.C0 = arrby[n] & 255;
        n = n2 + 1;
        n3 = this.C0 = n3 | (arrby[n2] & 255) << 8;
        n2 = n + 1;
        n3 = this.C0 = n3 | (arrby[n] & 255) << 16;
        n = n2 + 1;
        this.C0 = n3 | arrby[n2] << 24;
        n3 = n + 1;
        n = this.C1 = arrby[n] & 255;
        n2 = n3 + 1;
        n3 = this.C1 = (arrby[n3] & 255) << 8 | n;
        n = n2 + 1;
        n3 = this.C1 = n3 | (arrby[n2] & 255) << 16;
        n2 = n + 1;
        this.C1 = n3 | arrby[n] << 24;
        n = n2 + 1;
        n3 = this.C2 = arrby[n2] & 255;
        n2 = n + 1;
        n3 = this.C2 = (arrby[n] & 255) << 8 | n3;
        n = n2 + 1;
        n3 = this.C2 = n3 | (arrby[n2] & 255) << 16;
        n2 = n + 1;
        this.C2 = n3 | arrby[n] << 24;
        n3 = n2 + 1;
        n2 = this.C3 = arrby[n2] & 255;
        n = n3 + 1;
        n3 = this.C3 = (arrby[n3] & 255) << 8 | n2;
        n2 = n + 1;
        this.C3 = n3 | (arrby[n] & 255) << 16;
        this.C3 |= arrby[n2] << 24;
    }

    @Override
    public String getAlgorithmName() {
        return "AES";
    }

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.WorkingKey = this.generateWorkingKey(((KeyParameter)cipherParameters).getKey(), bl);
            this.forEncryption = bl;
            this.s = bl ? Arrays.clone(S) : Arrays.clone(Si);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to AES init - ");
        stringBuilder.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        if (this.WorkingKey != null) {
            if (n + 16 <= arrby.length) {
                if (n2 + 16 <= arrby2.length) {
                    if (this.forEncryption) {
                        this.unpackBlock(arrby, n);
                        this.encryptBlock(this.WorkingKey);
                        this.packBlock(arrby2, n2);
                    } else {
                        this.unpackBlock(arrby, n);
                        this.decryptBlock(this.WorkingKey);
                        this.packBlock(arrby2, n2);
                    }
                    return 16;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("AES engine not initialised");
    }

    @Override
    public void reset() {
    }
}

