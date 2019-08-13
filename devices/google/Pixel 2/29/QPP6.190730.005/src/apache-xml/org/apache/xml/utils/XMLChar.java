/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class XMLChar {
    private static final byte[] CHARS;
    public static final int MASK_CONTENT = 32;
    public static final int MASK_NAME = 8;
    public static final int MASK_NAME_START = 4;
    public static final int MASK_NCNAME = 128;
    public static final int MASK_NCNAME_START = 64;
    public static final int MASK_PUBID = 16;
    public static final int MASK_SPACE = 2;
    public static final int MASK_VALID = 1;

    static {
        int[] arrn;
        int[] arrn2;
        int n;
        int[] arrn3;
        int[] arrn4;
        int n2;
        int[] arrn5;
        int[] arrn6;
        int[] arrn7;
        int[] arrn8;
        int[] arrn9;
        int[] arrn10;
        int[] arrn11;
        int[] arrn12;
        int[] arrn13;
        int[] arrn14;
        CHARS = new byte[65536];
        int[] arrn15 = arrn8 = new int[8];
        arrn15[0] = 9;
        arrn15[1] = 10;
        arrn15[2] = 13;
        arrn15[3] = 13;
        arrn15[4] = 32;
        arrn15[5] = 55295;
        arrn15[6] = 57344;
        arrn15[7] = 65533;
        int[] arrn16 = arrn2 = new int[4];
        arrn16[0] = 32;
        arrn16[1] = 9;
        arrn16[2] = 13;
        arrn16[3] = 10;
        int[] arrn17 = arrn5 = new int[2];
        arrn17[0] = 45;
        arrn17[1] = 46;
        int[] arrn18 = arrn10 = new int[2];
        arrn18[0] = 58;
        arrn18[1] = 95;
        int[] arrn19 = arrn3 = new int[9];
        arrn19[0] = 10;
        arrn19[1] = 13;
        arrn19[2] = 32;
        arrn19[3] = 33;
        arrn19[4] = 35;
        arrn19[5] = 36;
        arrn19[6] = 37;
        arrn19[7] = 61;
        arrn19[8] = 95;
        int[] arrn20 = arrn6 = new int[6];
        arrn20[0] = 39;
        arrn20[1] = 59;
        arrn20[2] = 63;
        arrn20[3] = 90;
        arrn20[4] = 97;
        arrn20[5] = 122;
        int[] arrn21 = arrn14 = new int[302];
        arrn21[0] = 65;
        arrn21[1] = 90;
        arrn21[2] = 97;
        arrn21[3] = 122;
        arrn21[4] = 192;
        arrn21[5] = 214;
        arrn21[6] = 216;
        arrn21[7] = 246;
        arrn21[8] = 248;
        arrn21[9] = 305;
        arrn21[10] = 308;
        arrn21[11] = 318;
        arrn21[12] = 321;
        arrn21[13] = 328;
        arrn21[14] = 330;
        arrn21[15] = 382;
        arrn21[16] = 384;
        arrn21[17] = 451;
        arrn21[18] = 461;
        arrn21[19] = 496;
        arrn21[20] = 500;
        arrn21[21] = 501;
        arrn21[22] = 506;
        arrn21[23] = 535;
        arrn21[24] = 592;
        arrn21[25] = 680;
        arrn21[26] = 699;
        arrn21[27] = 705;
        arrn21[28] = 904;
        arrn21[29] = 906;
        arrn21[30] = 910;
        arrn21[31] = 929;
        arrn21[32] = 931;
        arrn21[33] = 974;
        arrn21[34] = 976;
        arrn21[35] = 982;
        arrn21[36] = 994;
        arrn21[37] = 1011;
        arrn21[38] = 1025;
        arrn21[39] = 1036;
        arrn21[40] = 1038;
        arrn21[41] = 1103;
        arrn21[42] = 1105;
        arrn21[43] = 1116;
        arrn21[44] = 1118;
        arrn21[45] = 1153;
        arrn21[46] = 1168;
        arrn21[47] = 1220;
        arrn21[48] = 1223;
        arrn21[49] = 1224;
        arrn21[50] = 1227;
        arrn21[51] = 1228;
        arrn21[52] = 1232;
        arrn21[53] = 1259;
        arrn21[54] = 1262;
        arrn21[55] = 1269;
        arrn21[56] = 1272;
        arrn21[57] = 1273;
        arrn21[58] = 1329;
        arrn21[59] = 1366;
        arrn21[60] = 1377;
        arrn21[61] = 1414;
        arrn21[62] = 1488;
        arrn21[63] = 1514;
        arrn21[64] = 1520;
        arrn21[65] = 1522;
        arrn21[66] = 1569;
        arrn21[67] = 1594;
        arrn21[68] = 1601;
        arrn21[69] = 1610;
        arrn21[70] = 1649;
        arrn21[71] = 1719;
        arrn21[72] = 1722;
        arrn21[73] = 1726;
        arrn21[74] = 1728;
        arrn21[75] = 1742;
        arrn21[76] = 1744;
        arrn21[77] = 1747;
        arrn21[78] = 1765;
        arrn21[79] = 1766;
        arrn21[80] = 2309;
        arrn21[81] = 2361;
        arrn21[82] = 2392;
        arrn21[83] = 2401;
        arrn21[84] = 2437;
        arrn21[85] = 2444;
        arrn21[86] = 2447;
        arrn21[87] = 2448;
        arrn21[88] = 2451;
        arrn21[89] = 2472;
        arrn21[90] = 2474;
        arrn21[91] = 2480;
        arrn21[92] = 2486;
        arrn21[93] = 2489;
        arrn21[94] = 2524;
        arrn21[95] = 2525;
        arrn21[96] = 2527;
        arrn21[97] = 2529;
        arrn21[98] = 2544;
        arrn21[99] = 2545;
        arrn21[100] = 2565;
        arrn21[101] = 2570;
        arrn21[102] = 2575;
        arrn21[103] = 2576;
        arrn21[104] = 2579;
        arrn21[105] = 2600;
        arrn21[106] = 2602;
        arrn21[107] = 2608;
        arrn21[108] = 2610;
        arrn21[109] = 2611;
        arrn21[110] = 2613;
        arrn21[111] = 2614;
        arrn21[112] = 2616;
        arrn21[113] = 2617;
        arrn21[114] = 2649;
        arrn21[115] = 2652;
        arrn21[116] = 2674;
        arrn21[117] = 2676;
        arrn21[118] = 2693;
        arrn21[119] = 2699;
        arrn21[120] = 2703;
        arrn21[121] = 2705;
        arrn21[122] = 2707;
        arrn21[123] = 2728;
        arrn21[124] = 2730;
        arrn21[125] = 2736;
        arrn21[126] = 2738;
        arrn21[127] = 2739;
        arrn21[128] = 2741;
        arrn21[129] = 2745;
        arrn21[130] = 2821;
        arrn21[131] = 2828;
        arrn21[132] = 2831;
        arrn21[133] = 2832;
        arrn21[134] = 2835;
        arrn21[135] = 2856;
        arrn21[136] = 2858;
        arrn21[137] = 2864;
        arrn21[138] = 2866;
        arrn21[139] = 2867;
        arrn21[140] = 2870;
        arrn21[141] = 2873;
        arrn21[142] = 2908;
        arrn21[143] = 2909;
        arrn21[144] = 2911;
        arrn21[145] = 2913;
        arrn21[146] = 2949;
        arrn21[147] = 2954;
        arrn21[148] = 2958;
        arrn21[149] = 2960;
        arrn21[150] = 2962;
        arrn21[151] = 2965;
        arrn21[152] = 2969;
        arrn21[153] = 2970;
        arrn21[154] = 2974;
        arrn21[155] = 2975;
        arrn21[156] = 2979;
        arrn21[157] = 2980;
        arrn21[158] = 2984;
        arrn21[159] = 2986;
        arrn21[160] = 2990;
        arrn21[161] = 2997;
        arrn21[162] = 2999;
        arrn21[163] = 3001;
        arrn21[164] = 3077;
        arrn21[165] = 3084;
        arrn21[166] = 3086;
        arrn21[167] = 3088;
        arrn21[168] = 3090;
        arrn21[169] = 3112;
        arrn21[170] = 3114;
        arrn21[171] = 3123;
        arrn21[172] = 3125;
        arrn21[173] = 3129;
        arrn21[174] = 3168;
        arrn21[175] = 3169;
        arrn21[176] = 3205;
        arrn21[177] = 3212;
        arrn21[178] = 3214;
        arrn21[179] = 3216;
        arrn21[180] = 3218;
        arrn21[181] = 3240;
        arrn21[182] = 3242;
        arrn21[183] = 3251;
        arrn21[184] = 3253;
        arrn21[185] = 3257;
        arrn21[186] = 3296;
        arrn21[187] = 3297;
        arrn21[188] = 3333;
        arrn21[189] = 3340;
        arrn21[190] = 3342;
        arrn21[191] = 3344;
        arrn21[192] = 3346;
        arrn21[193] = 3368;
        arrn21[194] = 3370;
        arrn21[195] = 3385;
        arrn21[196] = 3424;
        arrn21[197] = 3425;
        arrn21[198] = 3585;
        arrn21[199] = 3630;
        arrn21[200] = 3634;
        arrn21[201] = 3635;
        arrn21[202] = 3648;
        arrn21[203] = 3653;
        arrn21[204] = 3713;
        arrn21[205] = 3714;
        arrn21[206] = 3719;
        arrn21[207] = 3720;
        arrn21[208] = 3732;
        arrn21[209] = 3735;
        arrn21[210] = 3737;
        arrn21[211] = 3743;
        arrn21[212] = 3745;
        arrn21[213] = 3747;
        arrn21[214] = 3754;
        arrn21[215] = 3755;
        arrn21[216] = 3757;
        arrn21[217] = 3758;
        arrn21[218] = 3762;
        arrn21[219] = 3763;
        arrn21[220] = 3776;
        arrn21[221] = 3780;
        arrn21[222] = 3904;
        arrn21[223] = 3911;
        arrn21[224] = 3913;
        arrn21[225] = 3945;
        arrn21[226] = 4256;
        arrn21[227] = 4293;
        arrn21[228] = 4304;
        arrn21[229] = 4342;
        arrn21[230] = 4354;
        arrn21[231] = 4355;
        arrn21[232] = 4357;
        arrn21[233] = 4359;
        arrn21[234] = 4363;
        arrn21[235] = 4364;
        arrn21[236] = 4366;
        arrn21[237] = 4370;
        arrn21[238] = 4436;
        arrn21[239] = 4437;
        arrn21[240] = 4447;
        arrn21[241] = 4449;
        arrn21[242] = 4461;
        arrn21[243] = 4462;
        arrn21[244] = 4466;
        arrn21[245] = 4467;
        arrn21[246] = 4526;
        arrn21[247] = 4527;
        arrn21[248] = 4535;
        arrn21[249] = 4536;
        arrn21[250] = 4540;
        arrn21[251] = 4546;
        arrn21[252] = 7680;
        arrn21[253] = 7835;
        arrn21[254] = 7840;
        arrn21[255] = 7929;
        arrn21[256] = 7936;
        arrn21[257] = 7957;
        arrn21[258] = 7960;
        arrn21[259] = 7965;
        arrn21[260] = 7968;
        arrn21[261] = 8005;
        arrn21[262] = 8008;
        arrn21[263] = 8013;
        arrn21[264] = 8016;
        arrn21[265] = 8023;
        arrn21[266] = 8031;
        arrn21[267] = 8061;
        arrn21[268] = 8064;
        arrn21[269] = 8116;
        arrn21[270] = 8118;
        arrn21[271] = 8124;
        arrn21[272] = 8130;
        arrn21[273] = 8132;
        arrn21[274] = 8134;
        arrn21[275] = 8140;
        arrn21[276] = 8144;
        arrn21[277] = 8147;
        arrn21[278] = 8150;
        arrn21[279] = 8155;
        arrn21[280] = 8160;
        arrn21[281] = 8172;
        arrn21[282] = 8178;
        arrn21[283] = 8180;
        arrn21[284] = 8182;
        arrn21[285] = 8188;
        arrn21[286] = 8490;
        arrn21[287] = 8491;
        arrn21[288] = 8576;
        arrn21[289] = 8578;
        arrn21[290] = 12353;
        arrn21[291] = 12436;
        arrn21[292] = 12449;
        arrn21[293] = 12538;
        arrn21[294] = 12549;
        arrn21[295] = 12588;
        arrn21[296] = 44032;
        arrn21[297] = 55203;
        arrn21[298] = 12321;
        arrn21[299] = 12329;
        arrn21[300] = 19968;
        arrn21[301] = 40869;
        int[] arrn22 = arrn = new int[53];
        arrn22[0] = 902;
        arrn22[1] = 908;
        arrn22[2] = 986;
        arrn22[3] = 988;
        arrn22[4] = 990;
        arrn22[5] = 992;
        arrn22[6] = 1369;
        arrn22[7] = 1749;
        arrn22[8] = 2365;
        arrn22[9] = 2482;
        arrn22[10] = 2654;
        arrn22[11] = 2701;
        arrn22[12] = 2749;
        arrn22[13] = 2784;
        arrn22[14] = 2877;
        arrn22[15] = 2972;
        arrn22[16] = 3294;
        arrn22[17] = 3632;
        arrn22[18] = 3716;
        arrn22[19] = 3722;
        arrn22[20] = 3725;
        arrn22[21] = 3749;
        arrn22[22] = 3751;
        arrn22[23] = 3760;
        arrn22[24] = 3773;
        arrn22[25] = 4352;
        arrn22[26] = 4361;
        arrn22[27] = 4412;
        arrn22[28] = 4414;
        arrn22[29] = 4416;
        arrn22[30] = 4428;
        arrn22[31] = 4430;
        arrn22[32] = 4432;
        arrn22[33] = 4441;
        arrn22[34] = 4451;
        arrn22[35] = 4453;
        arrn22[36] = 4455;
        arrn22[37] = 4457;
        arrn22[38] = 4469;
        arrn22[39] = 4510;
        arrn22[40] = 4520;
        arrn22[41] = 4523;
        arrn22[42] = 4538;
        arrn22[43] = 4587;
        arrn22[44] = 4592;
        arrn22[45] = 4601;
        arrn22[46] = 8025;
        arrn22[47] = 8027;
        arrn22[48] = 8029;
        arrn22[49] = 8126;
        arrn22[50] = 8486;
        arrn22[51] = 8494;
        arrn22[52] = 12295;
        int[] arrn23 = arrn7 = new int[132];
        arrn23[0] = 768;
        arrn23[1] = 837;
        arrn23[2] = 864;
        arrn23[3] = 865;
        arrn23[4] = 1155;
        arrn23[5] = 1158;
        arrn23[6] = 1425;
        arrn23[7] = 1441;
        arrn23[8] = 1443;
        arrn23[9] = 1465;
        arrn23[10] = 1467;
        arrn23[11] = 1469;
        arrn23[12] = 1473;
        arrn23[13] = 1474;
        arrn23[14] = 1611;
        arrn23[15] = 1618;
        arrn23[16] = 1750;
        arrn23[17] = 1756;
        arrn23[18] = 1757;
        arrn23[19] = 1759;
        arrn23[20] = 1760;
        arrn23[21] = 1764;
        arrn23[22] = 1767;
        arrn23[23] = 1768;
        arrn23[24] = 1770;
        arrn23[25] = 1773;
        arrn23[26] = 2305;
        arrn23[27] = 2307;
        arrn23[28] = 2366;
        arrn23[29] = 2380;
        arrn23[30] = 2385;
        arrn23[31] = 2388;
        arrn23[32] = 2402;
        arrn23[33] = 2403;
        arrn23[34] = 2433;
        arrn23[35] = 2435;
        arrn23[36] = 2496;
        arrn23[37] = 2500;
        arrn23[38] = 2503;
        arrn23[39] = 2504;
        arrn23[40] = 2507;
        arrn23[41] = 2509;
        arrn23[42] = 2530;
        arrn23[43] = 2531;
        arrn23[44] = 2624;
        arrn23[45] = 2626;
        arrn23[46] = 2631;
        arrn23[47] = 2632;
        arrn23[48] = 2635;
        arrn23[49] = 2637;
        arrn23[50] = 2672;
        arrn23[51] = 2673;
        arrn23[52] = 2689;
        arrn23[53] = 2691;
        arrn23[54] = 2750;
        arrn23[55] = 2757;
        arrn23[56] = 2759;
        arrn23[57] = 2761;
        arrn23[58] = 2763;
        arrn23[59] = 2765;
        arrn23[60] = 2817;
        arrn23[61] = 2819;
        arrn23[62] = 2878;
        arrn23[63] = 2883;
        arrn23[64] = 2887;
        arrn23[65] = 2888;
        arrn23[66] = 2891;
        arrn23[67] = 2893;
        arrn23[68] = 2902;
        arrn23[69] = 2903;
        arrn23[70] = 2946;
        arrn23[71] = 2947;
        arrn23[72] = 3006;
        arrn23[73] = 3010;
        arrn23[74] = 3014;
        arrn23[75] = 3016;
        arrn23[76] = 3018;
        arrn23[77] = 3021;
        arrn23[78] = 3073;
        arrn23[79] = 3075;
        arrn23[80] = 3134;
        arrn23[81] = 3140;
        arrn23[82] = 3142;
        arrn23[83] = 3144;
        arrn23[84] = 3146;
        arrn23[85] = 3149;
        arrn23[86] = 3157;
        arrn23[87] = 3158;
        arrn23[88] = 3202;
        arrn23[89] = 3203;
        arrn23[90] = 3262;
        arrn23[91] = 3268;
        arrn23[92] = 3270;
        arrn23[93] = 3272;
        arrn23[94] = 3274;
        arrn23[95] = 3277;
        arrn23[96] = 3285;
        arrn23[97] = 3286;
        arrn23[98] = 3330;
        arrn23[99] = 3331;
        arrn23[100] = 3390;
        arrn23[101] = 3395;
        arrn23[102] = 3398;
        arrn23[103] = 3400;
        arrn23[104] = 3402;
        arrn23[105] = 3405;
        arrn23[106] = 3636;
        arrn23[107] = 3642;
        arrn23[108] = 3655;
        arrn23[109] = 3662;
        arrn23[110] = 3764;
        arrn23[111] = 3769;
        arrn23[112] = 3771;
        arrn23[113] = 3772;
        arrn23[114] = 3784;
        arrn23[115] = 3789;
        arrn23[116] = 3864;
        arrn23[117] = 3865;
        arrn23[118] = 3953;
        arrn23[119] = 3972;
        arrn23[120] = 3974;
        arrn23[121] = 3979;
        arrn23[122] = 3984;
        arrn23[123] = 3989;
        arrn23[124] = 3993;
        arrn23[125] = 4013;
        arrn23[126] = 4017;
        arrn23[127] = 4023;
        arrn23[128] = 8400;
        arrn23[129] = 8412;
        arrn23[130] = 12330;
        arrn23[131] = 12335;
        int[] arrn24 = arrn13 = new int[29];
        arrn24[0] = 1471;
        arrn24[1] = 1476;
        arrn24[2] = 1648;
        arrn24[3] = 2364;
        arrn24[4] = 2381;
        arrn24[5] = 2492;
        arrn24[6] = 2494;
        arrn24[7] = 2495;
        arrn24[8] = 2519;
        arrn24[9] = 2562;
        arrn24[10] = 2620;
        arrn24[11] = 2622;
        arrn24[12] = 2623;
        arrn24[13] = 2748;
        arrn24[14] = 2876;
        arrn24[15] = 3031;
        arrn24[16] = 3415;
        arrn24[17] = 3633;
        arrn24[18] = 3761;
        arrn24[19] = 3893;
        arrn24[20] = 3895;
        arrn24[21] = 3897;
        arrn24[22] = 3902;
        arrn24[23] = 3903;
        arrn24[24] = 3991;
        arrn24[25] = 4025;
        arrn24[26] = 8417;
        arrn24[27] = 12441;
        arrn24[28] = 12442;
        int[] arrn25 = arrn9 = new int[30];
        arrn25[0] = 48;
        arrn25[1] = 57;
        arrn25[2] = 1632;
        arrn25[3] = 1641;
        arrn25[4] = 1776;
        arrn25[5] = 1785;
        arrn25[6] = 2406;
        arrn25[7] = 2415;
        arrn25[8] = 2534;
        arrn25[9] = 2543;
        arrn25[10] = 2662;
        arrn25[11] = 2671;
        arrn25[12] = 2790;
        arrn25[13] = 2799;
        arrn25[14] = 2918;
        arrn25[15] = 2927;
        arrn25[16] = 3047;
        arrn25[17] = 3055;
        arrn25[18] = 3174;
        arrn25[19] = 3183;
        arrn25[20] = 3302;
        arrn25[21] = 3311;
        arrn25[22] = 3430;
        arrn25[23] = 3439;
        arrn25[24] = 3664;
        arrn25[25] = 3673;
        arrn25[26] = 3792;
        arrn25[27] = 3801;
        arrn25[28] = 3872;
        arrn25[29] = 3881;
        int[] arrn26 = arrn11 = new int[6];
        arrn26[0] = 12337;
        arrn26[1] = 12341;
        arrn26[2] = 12445;
        arrn26[3] = 12446;
        arrn26[4] = 12540;
        arrn26[5] = 12542;
        int[] arrn27 = arrn4 = new int[8];
        arrn27[0] = 183;
        arrn27[1] = 720;
        arrn27[2] = 721;
        arrn27[3] = 903;
        arrn27[4] = 1600;
        arrn27[5] = 3654;
        arrn27[6] = 3782;
        arrn27[7] = 12293;
        int[] arrn28 = arrn12 = new int[5];
        arrn28[0] = 60;
        arrn28[1] = 38;
        arrn28[2] = 10;
        arrn28[3] = 13;
        arrn28[4] = 93;
        for (n2 = 0; n2 < arrn8.length; n2 += 2) {
            for (n = arrn8[n2]; n <= arrn8[n2 + 1]; ++n) {
                byte[] arrby = CHARS;
                arrby[n] = (byte)(arrby[n] | 33);
            }
        }
        for (n2 = 0; n2 < arrn12.length; ++n2) {
            arrn8 = CHARS;
            arrn8[arrn12[n2]] = (byte)(arrn8[arrn12[n2]] & -33);
        }
        for (n2 = 0; n2 < arrn2.length; ++n2) {
            arrn8 = CHARS;
            n = arrn2[n2];
            arrn8[n] = (byte)(arrn8[n] | 2);
        }
        for (n2 = 0; n2 < arrn10.length; ++n2) {
            arrn8 = CHARS;
            n = arrn10[n2];
            arrn8[n] = (byte)(arrn8[n] | 204);
        }
        for (n2 = 0; n2 < arrn14.length; n2 += 2) {
            for (n = arrn14[n2]; n <= arrn14[n2 + 1]; ++n) {
                arrn8 = CHARS;
                arrn8[n] = (byte)(arrn8[n] | 204);
            }
        }
        for (n2 = 0; n2 < arrn.length; ++n2) {
            arrn8 = CHARS;
            n = arrn[n2];
            arrn8[n] = (byte)(arrn8[n] | 204);
        }
        for (n2 = 0; n2 < arrn5.length; ++n2) {
            arrn8 = CHARS;
            n = arrn5[n2];
            arrn8[n] = (byte)(arrn8[n] | 136);
        }
        for (n2 = 0; n2 < arrn9.length; n2 += 2) {
            for (n = arrn9[n2]; n <= arrn9[n2 + 1]; ++n) {
                arrn8 = CHARS;
                arrn8[n] = (byte)(arrn8[n] | 136);
            }
        }
        for (n2 = 0; n2 < arrn7.length; n2 += 2) {
            for (n = arrn7[n2]; n <= arrn7[n2 + 1]; ++n) {
                arrn8 = CHARS;
                arrn8[n] = (byte)(arrn8[n] | 136);
            }
        }
        for (n2 = 0; n2 < arrn13.length; ++n2) {
            arrn8 = CHARS;
            n = arrn13[n2];
            arrn8[n] = (byte)(arrn8[n] | 136);
        }
        for (n2 = 0; n2 < arrn11.length; n2 += 2) {
            for (n = arrn11[n2]; n <= arrn11[n2 + 1]; ++n) {
                arrn8 = CHARS;
                arrn8[n] = (byte)(arrn8[n] | 136);
            }
        }
        for (n2 = 0; n2 < arrn4.length; ++n2) {
            arrn8 = CHARS;
            n = arrn4[n2];
            arrn8[n] = (byte)(arrn8[n] | 136);
        }
        arrn8 = CHARS;
        arrn8[58] = (byte)(arrn8[58] & -193);
        for (n2 = 0; n2 < arrn3.length; ++n2) {
            arrn8 = CHARS;
            n = arrn3[n2];
            arrn8[n] = (byte)(arrn8[n] | 16);
        }
        arrn8 = arrn4;
        for (n2 = 0; n2 < arrn6.length; n2 += 2) {
            for (n = arrn6[n2]; n <= arrn6[n2 + 1]; ++n) {
                arrn4 = CHARS;
                arrn4[n] = (byte)(arrn4[n] | 16);
            }
        }
    }

    public static char highSurrogate(int n) {
        return (char)((n - 65536 >> 10) + 55296);
    }

    public static boolean isContent(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 32) != 0 || 65536 <= n && n <= 1114111;
        return bl;
    }

    public static boolean isHighSurrogate(int n) {
        boolean bl = 55296 <= n && n <= 56319;
        return bl;
    }

    public static boolean isInvalid(int n) {
        return XMLChar.isValid(n) ^ true;
    }

    public static boolean isLowSurrogate(int n) {
        boolean bl = 56320 <= n && n <= 57343;
        return bl;
    }

    public static boolean isMarkup(int n) {
        boolean bl = n == 60 || n == 38 || n == 37;
        return bl;
    }

    public static boolean isNCName(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 128) != 0;
        return bl;
    }

    public static boolean isNCNameStart(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 64) != 0;
        return bl;
    }

    public static boolean isName(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 8) != 0;
        return bl;
    }

    public static boolean isNameStart(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 4) != 0;
        return bl;
    }

    public static boolean isPubid(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 16) != 0;
        return bl;
    }

    public static boolean isSpace(int n) {
        boolean bl = n < 65536 && (CHARS[n] & 2) != 0;
        return bl;
    }

    public static boolean isSupplemental(int n) {
        boolean bl = n >= 65536 && n <= 1114111;
        return bl;
    }

    public static boolean isValid(int n) {
        boolean bl;
        block3 : {
            boolean bl2;
            block2 : {
                bl2 = true;
                if (n >= 65536) break block2;
                bl = bl2;
                if ((CHARS[n] & 1) != 0) break block3;
            }
            bl = 65536 <= n && n <= 1114111 ? bl2 : false;
        }
        return bl;
    }

    public static boolean isValidIANAEncoding(String string) {
        int n;
        int n2;
        if (string != null && (n2 = string.length()) > 0 && ((n = string.charAt(0)) >= 65 && n <= 90 || n >= 97 && n <= 122)) {
            for (n = 1; n < n2; ++n) {
                char c = string.charAt(n);
                if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.' || c == '_' || c == '-') continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidJavaEncoding(String string) {
        int n;
        if (string != null && (n = string.length()) > 0) {
            for (int i = 1; i < n; ++i) {
                char c = string.charAt(i);
                if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.' || c == '_' || c == '-') continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidNCName(String string) {
        if (string.length() == 0) {
            return false;
        }
        if (!XMLChar.isNCNameStart(string.charAt(0))) {
            return false;
        }
        for (int i = 1; i < string.length(); ++i) {
            if (XMLChar.isNCName(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isValidName(String string) {
        if (string.length() == 0) {
            return false;
        }
        if (!XMLChar.isNameStart(string.charAt(0))) {
            return false;
        }
        for (int i = 1; i < string.length(); ++i) {
            if (XMLChar.isName(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isValidNmtoken(String string) {
        if (string.length() == 0) {
            return false;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (XMLChar.isName(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isValidQName(String string) {
        int n = string.indexOf(58);
        boolean bl = false;
        if (n != 0 && n != string.length() - 1) {
            if (n > 0) {
                String string2 = string.substring(0, n);
                string = string.substring(n + 1);
                boolean bl2 = bl;
                if (XMLChar.isValidNCName(string2)) {
                    bl2 = bl;
                    if (XMLChar.isValidNCName(string)) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            return XMLChar.isValidNCName(string);
        }
        return false;
    }

    public static char lowSurrogate(int n) {
        return (char)((n - 65536 & 1023) + 56320);
    }

    public static int supplemental(char c, char c2) {
        return (c - 55296) * 1024 + (c2 - 56320) + 65536;
    }
}

