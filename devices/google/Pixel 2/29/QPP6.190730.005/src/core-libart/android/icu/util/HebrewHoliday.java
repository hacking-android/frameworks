/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Calendar;
import android.icu.util.DateRule;
import android.icu.util.HebrewCalendar;
import android.icu.util.Holiday;
import android.icu.util.SimpleDateRule;

public class HebrewHoliday
extends Holiday {
    public static HebrewHoliday ESTHER;
    public static HebrewHoliday GEDALIAH;
    public static HebrewHoliday HANUKKAH;
    public static HebrewHoliday HOSHANAH_RABBAH;
    public static HebrewHoliday LAG_BOMER;
    public static HebrewHoliday PASSOVER;
    public static HebrewHoliday PESACH_SHEINI;
    public static HebrewHoliday PURIM;
    public static HebrewHoliday ROSH_HASHANAH;
    public static HebrewHoliday SELIHOT;
    public static HebrewHoliday SHAVUOT;
    public static HebrewHoliday SHEMINI_ATZERET;
    public static HebrewHoliday SHUSHAN_PURIM;
    public static HebrewHoliday SIMCHAT_TORAH;
    public static HebrewHoliday SUKKOT;
    public static HebrewHoliday TAMMUZ_17;
    public static HebrewHoliday TEVET_10;
    public static HebrewHoliday TISHA_BAV;
    public static HebrewHoliday TU_BSHEVAT;
    public static HebrewHoliday YOM_HAATZMAUT;
    public static HebrewHoliday YOM_HASHOAH;
    public static HebrewHoliday YOM_HAZIKARON;
    public static HebrewHoliday YOM_KIPPUR;
    public static HebrewHoliday YOM_YERUSHALAYIM;
    private static final HebrewCalendar gCalendar;

    static {
        gCalendar = new HebrewCalendar();
        ROSH_HASHANAH = new HebrewHoliday(0, 1, 2, "Rosh Hashanah");
        GEDALIAH = new HebrewHoliday(0, 3, "Fast of Gedaliah");
        YOM_KIPPUR = new HebrewHoliday(0, 10, "Yom Kippur");
        SUKKOT = new HebrewHoliday(0, 15, 6, "Sukkot");
        HOSHANAH_RABBAH = new HebrewHoliday(0, 21, "Hoshanah Rabbah");
        SHEMINI_ATZERET = new HebrewHoliday(0, 22, "Shemini Atzeret");
        SIMCHAT_TORAH = new HebrewHoliday(0, 23, "Simchat Torah");
        HANUKKAH = new HebrewHoliday(2, 25, "Hanukkah");
        TEVET_10 = new HebrewHoliday(3, 10, "Fast of Tevet 10");
        TU_BSHEVAT = new HebrewHoliday(4, 15, "Tu B'Shevat");
        ESTHER = new HebrewHoliday(6, 13, "Fast of Esther");
        PURIM = new HebrewHoliday(6, 14, "Purim");
        SHUSHAN_PURIM = new HebrewHoliday(6, 15, "Shushan Purim");
        PASSOVER = new HebrewHoliday(7, 15, 8, "Passover");
        YOM_HASHOAH = new HebrewHoliday(7, 27, "Yom Hashoah");
        YOM_HAZIKARON = new HebrewHoliday(8, 4, "Yom Hazikaron");
        YOM_HAATZMAUT = new HebrewHoliday(8, 5, "Yom Ha'Atzmaut");
        PESACH_SHEINI = new HebrewHoliday(8, 14, "Pesach Sheini");
        LAG_BOMER = new HebrewHoliday(8, 18, "Lab B'Omer");
        YOM_YERUSHALAYIM = new HebrewHoliday(8, 28, "Yom Yerushalayim");
        SHAVUOT = new HebrewHoliday(9, 6, 2, "Shavuot");
        TAMMUZ_17 = new HebrewHoliday(10, 17, "Fast of Tammuz 17");
        TISHA_BAV = new HebrewHoliday(11, 9, "Fast of Tisha B'Av");
        SELIHOT = new HebrewHoliday(12, 21, "Selihot");
    }

    public HebrewHoliday(int n, int n2, int n3, String string) {
        super(string, new SimpleDateRule(n, n2, gCalendar));
    }

    public HebrewHoliday(int n, int n2, String string) {
        this(n, n2, 1, string);
    }
}

