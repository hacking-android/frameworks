/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.Arrays;

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
        byte[] arrby = CHARS = new byte[65536];
        arrby[9] = (byte)35;
        arrby[10] = (byte)19;
        arrby[13] = (byte)19;
        arrby[32] = (byte)51;
        arrby[33] = (byte)49;
        arrby[34] = (byte)33;
        Arrays.fill(arrby, 35, 38, (byte)49);
        arrby = CHARS;
        arrby[38] = (byte)(true ? 1 : 0);
        Arrays.fill(arrby, 39, 45, (byte)49);
        Arrays.fill(CHARS, 45, 47, (byte)-71);
        arrby = CHARS;
        arrby[47] = (byte)49;
        Arrays.fill(arrby, 48, 58, (byte)-71);
        arrby = CHARS;
        arrby[58] = (byte)61;
        arrby[59] = (byte)49;
        arrby[60] = (byte)(true ? 1 : 0);
        arrby[61] = (byte)49;
        arrby[62] = (byte)33;
        Arrays.fill(arrby, 63, 65, (byte)49);
        Arrays.fill(CHARS, 65, 91, (byte)-3);
        Arrays.fill(CHARS, 91, 93, (byte)33);
        arrby = CHARS;
        arrby[93] = (byte)(true ? 1 : 0);
        arrby[94] = (byte)33;
        arrby[95] = (byte)-3;
        arrby[96] = (byte)33;
        Arrays.fill(arrby, 97, 123, (byte)-3);
        Arrays.fill(CHARS, 123, 183, (byte)33);
        arrby = CHARS;
        arrby[183] = (byte)-87;
        Arrays.fill(arrby, 184, 192, (byte)33);
        Arrays.fill(CHARS, 192, 215, (byte)-19);
        arrby = CHARS;
        arrby[215] = (byte)33;
        Arrays.fill(arrby, 216, 247, (byte)-19);
        arrby = CHARS;
        arrby[247] = (byte)33;
        Arrays.fill(arrby, 248, 306, (byte)-19);
        Arrays.fill(CHARS, 306, 308, (byte)33);
        Arrays.fill(CHARS, 308, 319, (byte)-19);
        Arrays.fill(CHARS, 319, 321, (byte)33);
        Arrays.fill(CHARS, 321, 329, (byte)-19);
        arrby = CHARS;
        arrby[329] = (byte)33;
        Arrays.fill(arrby, 330, 383, (byte)-19);
        arrby = CHARS;
        arrby[383] = (byte)33;
        Arrays.fill(arrby, 384, 452, (byte)-19);
        Arrays.fill(CHARS, 452, 461, (byte)33);
        Arrays.fill(CHARS, 461, 497, (byte)-19);
        Arrays.fill(CHARS, 497, 500, (byte)33);
        Arrays.fill(CHARS, 500, 502, (byte)-19);
        Arrays.fill(CHARS, 502, 506, (byte)33);
        Arrays.fill(CHARS, 506, 536, (byte)-19);
        Arrays.fill(CHARS, 536, 592, (byte)33);
        Arrays.fill(CHARS, 592, 681, (byte)-19);
        Arrays.fill(CHARS, 681, 699, (byte)33);
        Arrays.fill(CHARS, 699, 706, (byte)-19);
        Arrays.fill(CHARS, 706, 720, (byte)33);
        Arrays.fill(CHARS, 720, 722, (byte)-87);
        Arrays.fill(CHARS, 722, 768, (byte)33);
        Arrays.fill(CHARS, 768, 838, (byte)-87);
        Arrays.fill(CHARS, 838, 864, (byte)33);
        Arrays.fill(CHARS, 864, 866, (byte)-87);
        Arrays.fill(CHARS, 866, 902, (byte)33);
        arrby = CHARS;
        arrby[902] = (byte)-19;
        arrby[903] = (byte)-87;
        Arrays.fill(arrby, 904, 907, (byte)-19);
        arrby = CHARS;
        arrby[907] = (byte)33;
        arrby[908] = (byte)-19;
        arrby[909] = (byte)33;
        Arrays.fill(arrby, 910, 930, (byte)-19);
        arrby = CHARS;
        arrby[930] = (byte)33;
        Arrays.fill(arrby, 931, 975, (byte)-19);
        arrby = CHARS;
        arrby[975] = (byte)33;
        Arrays.fill(arrby, 976, 983, (byte)-19);
        Arrays.fill(CHARS, 983, 986, (byte)33);
        arrby = CHARS;
        arrby[986] = (byte)-19;
        arrby[987] = (byte)33;
        arrby[988] = (byte)-19;
        arrby[989] = (byte)33;
        arrby[990] = (byte)-19;
        arrby[991] = (byte)33;
        arrby[992] = (byte)-19;
        arrby[993] = (byte)33;
        Arrays.fill(arrby, 994, 1012, (byte)-19);
        Arrays.fill(CHARS, 1012, 1025, (byte)33);
        Arrays.fill(CHARS, 1025, 1037, (byte)-19);
        arrby = CHARS;
        arrby[1037] = (byte)33;
        Arrays.fill(arrby, 1038, 1104, (byte)-19);
        arrby = CHARS;
        arrby[1104] = (byte)33;
        Arrays.fill(arrby, 1105, 1117, (byte)-19);
        arrby = CHARS;
        arrby[1117] = (byte)33;
        Arrays.fill(arrby, 1118, 1154, (byte)-19);
        arrby = CHARS;
        arrby[1154] = (byte)33;
        Arrays.fill(arrby, 1155, 1159, (byte)-87);
        Arrays.fill(CHARS, 1159, 1168, (byte)33);
        Arrays.fill(CHARS, 1168, 1221, (byte)-19);
        Arrays.fill(CHARS, 1221, 1223, (byte)33);
        Arrays.fill(CHARS, 1223, 1225, (byte)-19);
        Arrays.fill(CHARS, 1225, 1227, (byte)33);
        Arrays.fill(CHARS, 1227, 1229, (byte)-19);
        Arrays.fill(CHARS, 1229, 1232, (byte)33);
        Arrays.fill(CHARS, 1232, 1260, (byte)-19);
        Arrays.fill(CHARS, 1260, 1262, (byte)33);
        Arrays.fill(CHARS, 1262, 1270, (byte)-19);
        Arrays.fill(CHARS, 1270, 1272, (byte)33);
        Arrays.fill(CHARS, 1272, 1274, (byte)-19);
        Arrays.fill(CHARS, 1274, 1329, (byte)33);
        Arrays.fill(CHARS, 1329, 1367, (byte)-19);
        Arrays.fill(CHARS, 1367, 1369, (byte)33);
        arrby = CHARS;
        arrby[1369] = (byte)-19;
        Arrays.fill(arrby, 1370, 1377, (byte)33);
        Arrays.fill(CHARS, 1377, 1415, (byte)-19);
        Arrays.fill(CHARS, 1415, 1425, (byte)33);
        Arrays.fill(CHARS, 1425, 1442, (byte)-87);
        arrby = CHARS;
        arrby[1442] = (byte)33;
        Arrays.fill(arrby, 1443, 1466, (byte)-87);
        arrby = CHARS;
        arrby[1466] = (byte)33;
        Arrays.fill(arrby, 1467, 1470, (byte)-87);
        arrby = CHARS;
        arrby[1470] = (byte)33;
        arrby[1471] = (byte)-87;
        arrby[1472] = (byte)33;
        Arrays.fill(arrby, 1473, 1475, (byte)-87);
        arrby = CHARS;
        arrby[1475] = (byte)33;
        arrby[1476] = (byte)-87;
        Arrays.fill(arrby, 1477, 1488, (byte)33);
        Arrays.fill(CHARS, 1488, 1515, (byte)-19);
        Arrays.fill(CHARS, 1515, 1520, (byte)33);
        Arrays.fill(CHARS, 1520, 1523, (byte)-19);
        Arrays.fill(CHARS, 1523, 1569, (byte)33);
        Arrays.fill(CHARS, 1569, 1595, (byte)-19);
        Arrays.fill(CHARS, 1595, 1600, (byte)33);
        arrby = CHARS;
        arrby[1600] = (byte)-87;
        Arrays.fill(arrby, 1601, 1611, (byte)-19);
        Arrays.fill(CHARS, 1611, 1619, (byte)-87);
        Arrays.fill(CHARS, 1619, 1632, (byte)33);
        Arrays.fill(CHARS, 1632, 1642, (byte)-87);
        Arrays.fill(CHARS, 1642, 1648, (byte)33);
        arrby = CHARS;
        arrby[1648] = (byte)-87;
        Arrays.fill(arrby, 1649, 1720, (byte)-19);
        Arrays.fill(CHARS, 1720, 1722, (byte)33);
        Arrays.fill(CHARS, 1722, 1727, (byte)-19);
        arrby = CHARS;
        arrby[1727] = (byte)33;
        Arrays.fill(arrby, 1728, 1743, (byte)-19);
        arrby = CHARS;
        arrby[1743] = (byte)33;
        Arrays.fill(arrby, 1744, 1748, (byte)-19);
        arrby = CHARS;
        arrby[1748] = (byte)33;
        arrby[1749] = (byte)-19;
        Arrays.fill(arrby, 1750, 1765, (byte)-87);
        Arrays.fill(CHARS, 1765, 1767, (byte)-19);
        Arrays.fill(CHARS, 1767, 1769, (byte)-87);
        arrby = CHARS;
        arrby[1769] = (byte)33;
        Arrays.fill(arrby, 1770, 1774, (byte)-87);
        Arrays.fill(CHARS, 1774, 1776, (byte)33);
        Arrays.fill(CHARS, 1776, 1786, (byte)-87);
        Arrays.fill(CHARS, 1786, 2305, (byte)33);
        Arrays.fill(CHARS, 2305, 2308, (byte)-87);
        arrby = CHARS;
        arrby[2308] = (byte)33;
        Arrays.fill(arrby, 2309, 2362, (byte)-19);
        Arrays.fill(CHARS, 2362, 2364, (byte)33);
        arrby = CHARS;
        arrby[2364] = (byte)-87;
        arrby[2365] = (byte)-19;
        Arrays.fill(arrby, 2366, 2382, (byte)-87);
        Arrays.fill(CHARS, 2382, 2385, (byte)33);
        Arrays.fill(CHARS, 2385, 2389, (byte)-87);
        Arrays.fill(CHARS, 2389, 2392, (byte)33);
        Arrays.fill(CHARS, 2392, 2402, (byte)-19);
        Arrays.fill(CHARS, 2402, 2404, (byte)-87);
        Arrays.fill(CHARS, 2404, 2406, (byte)33);
        Arrays.fill(CHARS, 2406, 2416, (byte)-87);
        Arrays.fill(CHARS, 2416, 2433, (byte)33);
        Arrays.fill(CHARS, 2433, 2436, (byte)-87);
        arrby = CHARS;
        arrby[2436] = (byte)33;
        Arrays.fill(arrby, 2437, 2445, (byte)-19);
        Arrays.fill(CHARS, 2445, 2447, (byte)33);
        Arrays.fill(CHARS, 2447, 2449, (byte)-19);
        Arrays.fill(CHARS, 2449, 2451, (byte)33);
        Arrays.fill(CHARS, 2451, 2473, (byte)-19);
        arrby = CHARS;
        arrby[2473] = (byte)33;
        Arrays.fill(arrby, 2474, 2481, (byte)-19);
        arrby = CHARS;
        arrby[2481] = (byte)33;
        arrby[2482] = (byte)-19;
        Arrays.fill(arrby, 2483, 2486, (byte)33);
        Arrays.fill(CHARS, 2486, 2490, (byte)-19);
        Arrays.fill(CHARS, 2490, 2492, (byte)33);
        arrby = CHARS;
        arrby[2492] = (byte)-87;
        arrby[2493] = (byte)33;
        Arrays.fill(arrby, 2494, 2501, (byte)-87);
        Arrays.fill(CHARS, 2501, 2503, (byte)33);
        Arrays.fill(CHARS, 2503, 2505, (byte)-87);
        Arrays.fill(CHARS, 2505, 2507, (byte)33);
        Arrays.fill(CHARS, 2507, 2510, (byte)-87);
        Arrays.fill(CHARS, 2510, 2519, (byte)33);
        arrby = CHARS;
        arrby[2519] = (byte)-87;
        Arrays.fill(arrby, 2520, 2524, (byte)33);
        Arrays.fill(CHARS, 2524, 2526, (byte)-19);
        arrby = CHARS;
        arrby[2526] = (byte)33;
        Arrays.fill(arrby, 2527, 2530, (byte)-19);
        Arrays.fill(CHARS, 2530, 2532, (byte)-87);
        Arrays.fill(CHARS, 2532, 2534, (byte)33);
        Arrays.fill(CHARS, 2534, 2544, (byte)-87);
        Arrays.fill(CHARS, 2544, 2546, (byte)-19);
        Arrays.fill(CHARS, 2546, 2562, (byte)33);
        arrby = CHARS;
        arrby[2562] = (byte)-87;
        Arrays.fill(arrby, 2563, 2565, (byte)33);
        Arrays.fill(CHARS, 2565, 2571, (byte)-19);
        Arrays.fill(CHARS, 2571, 2575, (byte)33);
        Arrays.fill(CHARS, 2575, 2577, (byte)-19);
        Arrays.fill(CHARS, 2577, 2579, (byte)33);
        Arrays.fill(CHARS, 2579, 2601, (byte)-19);
        arrby = CHARS;
        arrby[2601] = (byte)33;
        Arrays.fill(arrby, 2602, 2609, (byte)-19);
        arrby = CHARS;
        arrby[2609] = (byte)33;
        Arrays.fill(arrby, 2610, 2612, (byte)-19);
        arrby = CHARS;
        arrby[2612] = (byte)33;
        Arrays.fill(arrby, 2613, 2615, (byte)-19);
        arrby = CHARS;
        arrby[2615] = (byte)33;
        Arrays.fill(arrby, 2616, 2618, (byte)-19);
        Arrays.fill(CHARS, 2618, 2620, (byte)33);
        arrby = CHARS;
        arrby[2620] = (byte)-87;
        arrby[2621] = (byte)33;
        Arrays.fill(arrby, 2622, 2627, (byte)-87);
        Arrays.fill(CHARS, 2627, 2631, (byte)33);
        Arrays.fill(CHARS, 2631, 2633, (byte)-87);
        Arrays.fill(CHARS, 2633, 2635, (byte)33);
        Arrays.fill(CHARS, 2635, 2638, (byte)-87);
        Arrays.fill(CHARS, 2638, 2649, (byte)33);
        Arrays.fill(CHARS, 2649, 2653, (byte)-19);
        arrby = CHARS;
        arrby[2653] = (byte)33;
        arrby[2654] = (byte)-19;
        Arrays.fill(arrby, 2655, 2662, (byte)33);
        Arrays.fill(CHARS, 2662, 2674, (byte)-87);
        Arrays.fill(CHARS, 2674, 2677, (byte)-19);
        Arrays.fill(CHARS, 2677, 2689, (byte)33);
        Arrays.fill(CHARS, 2689, 2692, (byte)-87);
        arrby = CHARS;
        arrby[2692] = (byte)33;
        Arrays.fill(arrby, 2693, 2700, (byte)-19);
        arrby = CHARS;
        arrby[2700] = (byte)33;
        arrby[2701] = (byte)-19;
        arrby[2702] = (byte)33;
        Arrays.fill(arrby, 2703, 2706, (byte)-19);
        arrby = CHARS;
        arrby[2706] = (byte)33;
        Arrays.fill(arrby, 2707, 2729, (byte)-19);
        arrby = CHARS;
        arrby[2729] = (byte)33;
        Arrays.fill(arrby, 2730, 2737, (byte)-19);
        arrby = CHARS;
        arrby[2737] = (byte)33;
        Arrays.fill(arrby, 2738, 2740, (byte)-19);
        arrby = CHARS;
        arrby[2740] = (byte)33;
        Arrays.fill(arrby, 2741, 2746, (byte)-19);
        Arrays.fill(CHARS, 2746, 2748, (byte)33);
        arrby = CHARS;
        arrby[2748] = (byte)-87;
        arrby[2749] = (byte)-19;
        Arrays.fill(arrby, 2750, 2758, (byte)-87);
        arrby = CHARS;
        arrby[2758] = (byte)33;
        Arrays.fill(arrby, 2759, 2762, (byte)-87);
        arrby = CHARS;
        arrby[2762] = (byte)33;
        Arrays.fill(arrby, 2763, 2766, (byte)-87);
        Arrays.fill(CHARS, 2766, 2784, (byte)33);
        arrby = CHARS;
        arrby[2784] = (byte)-19;
        Arrays.fill(arrby, 2785, 2790, (byte)33);
        Arrays.fill(CHARS, 2790, 2800, (byte)-87);
        Arrays.fill(CHARS, 2800, 2817, (byte)33);
        Arrays.fill(CHARS, 2817, 2820, (byte)-87);
        arrby = CHARS;
        arrby[2820] = (byte)33;
        Arrays.fill(arrby, 2821, 2829, (byte)-19);
        Arrays.fill(CHARS, 2829, 2831, (byte)33);
        Arrays.fill(CHARS, 2831, 2833, (byte)-19);
        Arrays.fill(CHARS, 2833, 2835, (byte)33);
        Arrays.fill(CHARS, 2835, 2857, (byte)-19);
        arrby = CHARS;
        arrby[2857] = (byte)33;
        Arrays.fill(arrby, 2858, 2865, (byte)-19);
        arrby = CHARS;
        arrby[2865] = (byte)33;
        Arrays.fill(arrby, 2866, 2868, (byte)-19);
        Arrays.fill(CHARS, 2868, 2870, (byte)33);
        Arrays.fill(CHARS, 2870, 2874, (byte)-19);
        Arrays.fill(CHARS, 2874, 2876, (byte)33);
        arrby = CHARS;
        arrby[2876] = (byte)-87;
        arrby[2877] = (byte)-19;
        Arrays.fill(arrby, 2878, 2884, (byte)-87);
        Arrays.fill(CHARS, 2884, 2887, (byte)33);
        Arrays.fill(CHARS, 2887, 2889, (byte)-87);
        Arrays.fill(CHARS, 2889, 2891, (byte)33);
        Arrays.fill(CHARS, 2891, 2894, (byte)-87);
        Arrays.fill(CHARS, 2894, 2902, (byte)33);
        Arrays.fill(CHARS, 2902, 2904, (byte)-87);
        Arrays.fill(CHARS, 2904, 2908, (byte)33);
        Arrays.fill(CHARS, 2908, 2910, (byte)-19);
        arrby = CHARS;
        arrby[2910] = (byte)33;
        Arrays.fill(arrby, 2911, 2914, (byte)-19);
        Arrays.fill(CHARS, 2914, 2918, (byte)33);
        Arrays.fill(CHARS, 2918, 2928, (byte)-87);
        Arrays.fill(CHARS, 2928, 2946, (byte)33);
        Arrays.fill(CHARS, 2946, 2948, (byte)-87);
        arrby = CHARS;
        arrby[2948] = (byte)33;
        Arrays.fill(arrby, 2949, 2955, (byte)-19);
        Arrays.fill(CHARS, 2955, 2958, (byte)33);
        Arrays.fill(CHARS, 2958, 2961, (byte)-19);
        arrby = CHARS;
        arrby[2961] = (byte)33;
        Arrays.fill(arrby, 2962, 2966, (byte)-19);
        Arrays.fill(CHARS, 2966, 2969, (byte)33);
        Arrays.fill(CHARS, 2969, 2971, (byte)-19);
        arrby = CHARS;
        arrby[2971] = (byte)33;
        arrby[2972] = (byte)-19;
        arrby[2973] = (byte)33;
        Arrays.fill(arrby, 2974, 2976, (byte)-19);
        Arrays.fill(CHARS, 2976, 2979, (byte)33);
        Arrays.fill(CHARS, 2979, 2981, (byte)-19);
        Arrays.fill(CHARS, 2981, 2984, (byte)33);
        Arrays.fill(CHARS, 2984, 2987, (byte)-19);
        Arrays.fill(CHARS, 2987, 2990, (byte)33);
        Arrays.fill(CHARS, 2990, 2998, (byte)-19);
        arrby = CHARS;
        arrby[2998] = (byte)33;
        Arrays.fill(arrby, 2999, 3002, (byte)-19);
        Arrays.fill(CHARS, 3002, 3006, (byte)33);
        Arrays.fill(CHARS, 3006, 3011, (byte)-87);
        Arrays.fill(CHARS, 3011, 3014, (byte)33);
        Arrays.fill(CHARS, 3014, 3017, (byte)-87);
        arrby = CHARS;
        arrby[3017] = (byte)33;
        Arrays.fill(arrby, 3018, 3022, (byte)-87);
        Arrays.fill(CHARS, 3022, 3031, (byte)33);
        arrby = CHARS;
        arrby[3031] = (byte)-87;
        Arrays.fill(arrby, 3032, 3047, (byte)33);
        Arrays.fill(CHARS, 3047, 3056, (byte)-87);
        Arrays.fill(CHARS, 3056, 3073, (byte)33);
        Arrays.fill(CHARS, 3073, 3076, (byte)-87);
        arrby = CHARS;
        arrby[3076] = (byte)33;
        Arrays.fill(arrby, 3077, 3085, (byte)-19);
        arrby = CHARS;
        arrby[3085] = (byte)33;
        Arrays.fill(arrby, 3086, 3089, (byte)-19);
        arrby = CHARS;
        arrby[3089] = (byte)33;
        Arrays.fill(arrby, 3090, 3113, (byte)-19);
        arrby = CHARS;
        arrby[3113] = (byte)33;
        Arrays.fill(arrby, 3114, 3124, (byte)-19);
        arrby = CHARS;
        arrby[3124] = (byte)33;
        Arrays.fill(arrby, 3125, 3130, (byte)-19);
        Arrays.fill(CHARS, 3130, 3134, (byte)33);
        Arrays.fill(CHARS, 3134, 3141, (byte)-87);
        arrby = CHARS;
        arrby[3141] = (byte)33;
        Arrays.fill(arrby, 3142, 3145, (byte)-87);
        arrby = CHARS;
        arrby[3145] = (byte)33;
        Arrays.fill(arrby, 3146, 3150, (byte)-87);
        Arrays.fill(CHARS, 3150, 3157, (byte)33);
        Arrays.fill(CHARS, 3157, 3159, (byte)-87);
        Arrays.fill(CHARS, 3159, 3168, (byte)33);
        Arrays.fill(CHARS, 3168, 3170, (byte)-19);
        Arrays.fill(CHARS, 3170, 3174, (byte)33);
        Arrays.fill(CHARS, 3174, 3184, (byte)-87);
        Arrays.fill(CHARS, 3184, 3202, (byte)33);
        Arrays.fill(CHARS, 3202, 3204, (byte)-87);
        arrby = CHARS;
        arrby[3204] = (byte)33;
        Arrays.fill(arrby, 3205, 3213, (byte)-19);
        arrby = CHARS;
        arrby[3213] = (byte)33;
        Arrays.fill(arrby, 3214, 3217, (byte)-19);
        arrby = CHARS;
        arrby[3217] = (byte)33;
        Arrays.fill(arrby, 3218, 3241, (byte)-19);
        arrby = CHARS;
        arrby[3241] = (byte)33;
        Arrays.fill(arrby, 3242, 3252, (byte)-19);
        arrby = CHARS;
        arrby[3252] = (byte)33;
        Arrays.fill(arrby, 3253, 3258, (byte)-19);
        Arrays.fill(CHARS, 3258, 3262, (byte)33);
        Arrays.fill(CHARS, 3262, 3269, (byte)-87);
        arrby = CHARS;
        arrby[3269] = (byte)33;
        Arrays.fill(arrby, 3270, 3273, (byte)-87);
        arrby = CHARS;
        arrby[3273] = (byte)33;
        Arrays.fill(arrby, 3274, 3278, (byte)-87);
        Arrays.fill(CHARS, 3278, 3285, (byte)33);
        Arrays.fill(CHARS, 3285, 3287, (byte)-87);
        Arrays.fill(CHARS, 3287, 3294, (byte)33);
        arrby = CHARS;
        arrby[3294] = (byte)-19;
        arrby[3295] = (byte)33;
        Arrays.fill(arrby, 3296, 3298, (byte)-19);
        Arrays.fill(CHARS, 3298, 3302, (byte)33);
        Arrays.fill(CHARS, 3302, 3312, (byte)-87);
        Arrays.fill(CHARS, 3312, 3330, (byte)33);
        Arrays.fill(CHARS, 3330, 3332, (byte)-87);
        arrby = CHARS;
        arrby[3332] = (byte)33;
        Arrays.fill(arrby, 3333, 3341, (byte)-19);
        arrby = CHARS;
        arrby[3341] = (byte)33;
        Arrays.fill(arrby, 3342, 3345, (byte)-19);
        arrby = CHARS;
        arrby[3345] = (byte)33;
        Arrays.fill(arrby, 3346, 3369, (byte)-19);
        arrby = CHARS;
        arrby[3369] = (byte)33;
        Arrays.fill(arrby, 3370, 3386, (byte)-19);
        Arrays.fill(CHARS, 3386, 3390, (byte)33);
        Arrays.fill(CHARS, 3390, 3396, (byte)-87);
        Arrays.fill(CHARS, 3396, 3398, (byte)33);
        Arrays.fill(CHARS, 3398, 3401, (byte)-87);
        arrby = CHARS;
        arrby[3401] = (byte)33;
        Arrays.fill(arrby, 3402, 3406, (byte)-87);
        Arrays.fill(CHARS, 3406, 3415, (byte)33);
        arrby = CHARS;
        arrby[3415] = (byte)-87;
        Arrays.fill(arrby, 3416, 3424, (byte)33);
        Arrays.fill(CHARS, 3424, 3426, (byte)-19);
        Arrays.fill(CHARS, 3426, 3430, (byte)33);
        Arrays.fill(CHARS, 3430, 3440, (byte)-87);
        Arrays.fill(CHARS, 3440, 3585, (byte)33);
        Arrays.fill(CHARS, 3585, 3631, (byte)-19);
        arrby = CHARS;
        arrby[3631] = (byte)33;
        arrby[3632] = (byte)-19;
        arrby[3633] = (byte)-87;
        Arrays.fill(arrby, 3634, 3636, (byte)-19);
        Arrays.fill(CHARS, 3636, 3643, (byte)-87);
        Arrays.fill(CHARS, 3643, 3648, (byte)33);
        Arrays.fill(CHARS, 3648, 3654, (byte)-19);
        Arrays.fill(CHARS, 3654, 3663, (byte)-87);
        arrby = CHARS;
        arrby[3663] = (byte)33;
        Arrays.fill(arrby, 3664, 3674, (byte)-87);
        Arrays.fill(CHARS, 3674, 3713, (byte)33);
        Arrays.fill(CHARS, 3713, 3715, (byte)-19);
        arrby = CHARS;
        arrby[3715] = (byte)33;
        arrby[3716] = (byte)-19;
        Arrays.fill(arrby, 3717, 3719, (byte)33);
        Arrays.fill(CHARS, 3719, 3721, (byte)-19);
        arrby = CHARS;
        arrby[3721] = (byte)33;
        arrby[3722] = (byte)-19;
        Arrays.fill(arrby, 3723, 3725, (byte)33);
        arrby = CHARS;
        arrby[3725] = (byte)-19;
        Arrays.fill(arrby, 3726, 3732, (byte)33);
        Arrays.fill(CHARS, 3732, 3736, (byte)-19);
        arrby = CHARS;
        arrby[3736] = (byte)33;
        Arrays.fill(arrby, 3737, 3744, (byte)-19);
        arrby = CHARS;
        arrby[3744] = (byte)33;
        Arrays.fill(arrby, 3745, 3748, (byte)-19);
        arrby = CHARS;
        arrby[3748] = (byte)33;
        arrby[3749] = (byte)-19;
        arrby[3750] = (byte)33;
        arrby[3751] = (byte)-19;
        Arrays.fill(arrby, 3752, 3754, (byte)33);
        Arrays.fill(CHARS, 3754, 3756, (byte)-19);
        arrby = CHARS;
        arrby[3756] = (byte)33;
        Arrays.fill(arrby, 3757, 3759, (byte)-19);
        arrby = CHARS;
        arrby[3759] = (byte)33;
        arrby[3760] = (byte)-19;
        arrby[3761] = (byte)-87;
        Arrays.fill(arrby, 3762, 3764, (byte)-19);
        Arrays.fill(CHARS, 3764, 3770, (byte)-87);
        arrby = CHARS;
        arrby[3770] = (byte)33;
        Arrays.fill(arrby, 3771, 3773, (byte)-87);
        arrby = CHARS;
        arrby[3773] = (byte)-19;
        Arrays.fill(arrby, 3774, 3776, (byte)33);
        Arrays.fill(CHARS, 3776, 3781, (byte)-19);
        arrby = CHARS;
        arrby[3781] = (byte)33;
        arrby[3782] = (byte)-87;
        arrby[3783] = (byte)33;
        Arrays.fill(arrby, 3784, 3790, (byte)-87);
        Arrays.fill(CHARS, 3790, 3792, (byte)33);
        Arrays.fill(CHARS, 3792, 3802, (byte)-87);
        Arrays.fill(CHARS, 3802, 3864, (byte)33);
        Arrays.fill(CHARS, 3864, 3866, (byte)-87);
        Arrays.fill(CHARS, 3866, 3872, (byte)33);
        Arrays.fill(CHARS, 3872, 3882, (byte)-87);
        Arrays.fill(CHARS, 3882, 3893, (byte)33);
        arrby = CHARS;
        arrby[3893] = (byte)-87;
        arrby[3894] = (byte)33;
        arrby[3895] = (byte)-87;
        arrby[3896] = (byte)33;
        arrby[3897] = (byte)-87;
        Arrays.fill(arrby, 3898, 3902, (byte)33);
        Arrays.fill(CHARS, 3902, 3904, (byte)-87);
        Arrays.fill(CHARS, 3904, 3912, (byte)-19);
        arrby = CHARS;
        arrby[3912] = (byte)33;
        Arrays.fill(arrby, 3913, 3946, (byte)-19);
        Arrays.fill(CHARS, 3946, 3953, (byte)33);
        Arrays.fill(CHARS, 3953, 3973, (byte)-87);
        arrby = CHARS;
        arrby[3973] = (byte)33;
        Arrays.fill(arrby, 3974, 3980, (byte)-87);
        Arrays.fill(CHARS, 3980, 3984, (byte)33);
        Arrays.fill(CHARS, 3984, 3990, (byte)-87);
        arrby = CHARS;
        arrby[3990] = (byte)33;
        arrby[3991] = (byte)-87;
        arrby[3992] = (byte)33;
        Arrays.fill(arrby, 3993, 4014, (byte)-87);
        Arrays.fill(CHARS, 4014, 4017, (byte)33);
        Arrays.fill(CHARS, 4017, 4024, (byte)-87);
        arrby = CHARS;
        arrby[4024] = (byte)33;
        arrby[4025] = (byte)-87;
        Arrays.fill(arrby, 4026, 4256, (byte)33);
        Arrays.fill(CHARS, 4256, 4294, (byte)-19);
        Arrays.fill(CHARS, 4294, 4304, (byte)33);
        Arrays.fill(CHARS, 4304, 4343, (byte)-19);
        Arrays.fill(CHARS, 4343, 4352, (byte)33);
        arrby = CHARS;
        arrby[4352] = (byte)-19;
        arrby[4353] = (byte)33;
        Arrays.fill(arrby, 4354, 4356, (byte)-19);
        arrby = CHARS;
        arrby[4356] = (byte)33;
        Arrays.fill(arrby, 4357, 4360, (byte)-19);
        arrby = CHARS;
        arrby[4360] = (byte)33;
        arrby[4361] = (byte)-19;
        arrby[4362] = (byte)33;
        Arrays.fill(arrby, 4363, 4365, (byte)-19);
        arrby = CHARS;
        arrby[4365] = (byte)33;
        Arrays.fill(arrby, 4366, 4371, (byte)-19);
        Arrays.fill(CHARS, 4371, 4412, (byte)33);
        arrby = CHARS;
        arrby[4412] = (byte)-19;
        arrby[4413] = (byte)33;
        arrby[4414] = (byte)-19;
        arrby[4415] = (byte)33;
        arrby[4416] = (byte)-19;
        Arrays.fill(arrby, 4417, 4428, (byte)33);
        arrby = CHARS;
        arrby[4428] = (byte)-19;
        arrby[4429] = (byte)33;
        arrby[4430] = (byte)-19;
        arrby[4431] = (byte)33;
        arrby[4432] = (byte)-19;
        Arrays.fill(arrby, 4433, 4436, (byte)33);
        Arrays.fill(CHARS, 4436, 4438, (byte)-19);
        Arrays.fill(CHARS, 4438, 4441, (byte)33);
        arrby = CHARS;
        arrby[4441] = (byte)-19;
        Arrays.fill(arrby, 4442, 4447, (byte)33);
        Arrays.fill(CHARS, 4447, 4450, (byte)-19);
        arrby = CHARS;
        arrby[4450] = (byte)33;
        arrby[4451] = (byte)-19;
        arrby[4452] = (byte)33;
        arrby[4453] = (byte)-19;
        arrby[4454] = (byte)33;
        arrby[4455] = (byte)-19;
        arrby[4456] = (byte)33;
        arrby[4457] = (byte)-19;
        Arrays.fill(arrby, 4458, 4461, (byte)33);
        Arrays.fill(CHARS, 4461, 4463, (byte)-19);
        Arrays.fill(CHARS, 4463, 4466, (byte)33);
        Arrays.fill(CHARS, 4466, 4468, (byte)-19);
        arrby = CHARS;
        arrby[4468] = (byte)33;
        arrby[4469] = (byte)-19;
        Arrays.fill(arrby, 4470, 4510, (byte)33);
        arrby = CHARS;
        arrby[4510] = (byte)-19;
        Arrays.fill(arrby, 4511, 4520, (byte)33);
        arrby = CHARS;
        arrby[4520] = (byte)-19;
        Arrays.fill(arrby, 4521, 4523, (byte)33);
        arrby = CHARS;
        arrby[4523] = (byte)-19;
        Arrays.fill(arrby, 4524, 4526, (byte)33);
        Arrays.fill(CHARS, 4526, 4528, (byte)-19);
        Arrays.fill(CHARS, 4528, 4535, (byte)33);
        Arrays.fill(CHARS, 4535, 4537, (byte)-19);
        arrby = CHARS;
        arrby[4537] = (byte)33;
        arrby[4538] = (byte)-19;
        arrby[4539] = (byte)33;
        Arrays.fill(arrby, 4540, 4547, (byte)-19);
        Arrays.fill(CHARS, 4547, 4587, (byte)33);
        arrby = CHARS;
        arrby[4587] = (byte)-19;
        Arrays.fill(arrby, 4588, 4592, (byte)33);
        arrby = CHARS;
        arrby[4592] = (byte)-19;
        Arrays.fill(arrby, 4593, 4601, (byte)33);
        arrby = CHARS;
        arrby[4601] = (byte)-19;
        Arrays.fill(arrby, 4602, 7680, (byte)33);
        Arrays.fill(CHARS, 7680, 7836, (byte)-19);
        Arrays.fill(CHARS, 7836, 7840, (byte)33);
        Arrays.fill(CHARS, 7840, 7930, (byte)-19);
        Arrays.fill(CHARS, 7930, 7936, (byte)33);
        Arrays.fill(CHARS, 7936, 7958, (byte)-19);
        Arrays.fill(CHARS, 7958, 7960, (byte)33);
        Arrays.fill(CHARS, 7960, 7966, (byte)-19);
        Arrays.fill(CHARS, 7966, 7968, (byte)33);
        Arrays.fill(CHARS, 7968, 8006, (byte)-19);
        Arrays.fill(CHARS, 8006, 8008, (byte)33);
        Arrays.fill(CHARS, 8008, 8014, (byte)-19);
        Arrays.fill(CHARS, 8014, 8016, (byte)33);
        Arrays.fill(CHARS, 8016, 8024, (byte)-19);
        arrby = CHARS;
        arrby[8024] = (byte)33;
        arrby[8025] = (byte)-19;
        arrby[8026] = (byte)33;
        arrby[8027] = (byte)-19;
        arrby[8028] = (byte)33;
        arrby[8029] = (byte)-19;
        arrby[8030] = (byte)33;
        Arrays.fill(arrby, 8031, 8062, (byte)-19);
        Arrays.fill(CHARS, 8062, 8064, (byte)33);
        Arrays.fill(CHARS, 8064, 8117, (byte)-19);
        arrby = CHARS;
        arrby[8117] = (byte)33;
        Arrays.fill(arrby, 8118, 8125, (byte)-19);
        arrby = CHARS;
        arrby[8125] = (byte)33;
        arrby[8126] = (byte)-19;
        Arrays.fill(arrby, 8127, 8130, (byte)33);
        Arrays.fill(CHARS, 8130, 8133, (byte)-19);
        arrby = CHARS;
        arrby[8133] = (byte)33;
        Arrays.fill(arrby, 8134, 8141, (byte)-19);
        Arrays.fill(CHARS, 8141, 8144, (byte)33);
        Arrays.fill(CHARS, 8144, 8148, (byte)-19);
        Arrays.fill(CHARS, 8148, 8150, (byte)33);
        Arrays.fill(CHARS, 8150, 8156, (byte)-19);
        Arrays.fill(CHARS, 8156, 8160, (byte)33);
        Arrays.fill(CHARS, 8160, 8173, (byte)-19);
        Arrays.fill(CHARS, 8173, 8178, (byte)33);
        Arrays.fill(CHARS, 8178, 8181, (byte)-19);
        arrby = CHARS;
        arrby[8181] = (byte)33;
        Arrays.fill(arrby, 8182, 8189, (byte)-19);
        Arrays.fill(CHARS, 8189, 8400, (byte)33);
        Arrays.fill(CHARS, 8400, 8413, (byte)-87);
        Arrays.fill(CHARS, 8413, 8417, (byte)33);
        arrby = CHARS;
        arrby[8417] = (byte)-87;
        Arrays.fill(arrby, 8418, 8486, (byte)33);
        arrby = CHARS;
        arrby[8486] = (byte)-19;
        Arrays.fill(arrby, 8487, 8490, (byte)33);
        Arrays.fill(CHARS, 8490, 8492, (byte)-19);
        Arrays.fill(CHARS, 8492, 8494, (byte)33);
        arrby = CHARS;
        arrby[8494] = (byte)-19;
        Arrays.fill(arrby, 8495, 8576, (byte)33);
        Arrays.fill(CHARS, 8576, 8579, (byte)-19);
        Arrays.fill(CHARS, 8579, 12293, (byte)33);
        arrby = CHARS;
        arrby[12293] = (byte)-87;
        arrby[12294] = (byte)33;
        arrby[12295] = (byte)-19;
        Arrays.fill(arrby, 12296, 12321, (byte)33);
        Arrays.fill(CHARS, 12321, 12330, (byte)-19);
        Arrays.fill(CHARS, 12330, 12336, (byte)-87);
        arrby = CHARS;
        arrby[12336] = (byte)33;
        Arrays.fill(arrby, 12337, 12342, (byte)-87);
        Arrays.fill(CHARS, 12342, 12353, (byte)33);
        Arrays.fill(CHARS, 12353, 12437, (byte)-19);
        Arrays.fill(CHARS, 12437, 12441, (byte)33);
        Arrays.fill(CHARS, 12441, 12443, (byte)-87);
        Arrays.fill(CHARS, 12443, 12445, (byte)33);
        Arrays.fill(CHARS, 12445, 12447, (byte)-87);
        Arrays.fill(CHARS, 12447, 12449, (byte)33);
        Arrays.fill(CHARS, 12449, 12539, (byte)-19);
        arrby = CHARS;
        arrby[12539] = (byte)33;
        Arrays.fill(arrby, 12540, 12543, (byte)-87);
        Arrays.fill(CHARS, 12543, 12549, (byte)33);
        Arrays.fill(CHARS, 12549, 12589, (byte)-19);
        Arrays.fill(CHARS, 12589, 19968, (byte)33);
        Arrays.fill(CHARS, 19968, 40870, (byte)-19);
        Arrays.fill(CHARS, 40870, 44032, (byte)33);
        Arrays.fill(CHARS, 44032, 55204, (byte)-19);
        Arrays.fill(CHARS, 55204, 55296, (byte)33);
        Arrays.fill(CHARS, 57344, 65534, (byte)33);
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
        boolean bl = n <= 32 && (CHARS[n] & 2) != 0;
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

    public static char lowSurrogate(int n) {
        return (char)((n - 65536 & 1023) + 56320);
    }

    public static int supplemental(char c, char c2) {
        return (c - 55296) * 1024 + (c2 - 56320) + 65536;
    }
}

