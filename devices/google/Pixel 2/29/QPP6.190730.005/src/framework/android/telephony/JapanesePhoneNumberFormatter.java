/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.text.Editable;

class JapanesePhoneNumberFormatter {
    private static short[] FORMAT_MAP = new short[]{-100, 10, 220, -15, 410, 530, 1200, 670, 780, 1060, -100, -25, 20, 40, 70, 100, 150, 190, 200, 210, -36, -100, -100, -35, -35, -35, 30, -100, -100, -100, -35, -35, -35, -35, -35, -35, -35, -45, -35, -35, -100, -100, -100, -35, -35, -35, -35, 50, -35, 60, -35, -35, -45, -35, -45, -35, -35, -45, -35, -35, -35, -35, -45, -35, -35, -35, -35, -45, -45, -35, -100, -100, -35, -35, -35, 80, 90, -100, -100, -100, -35, -35, -35, -35, -35, -35, -45, -45, -35, -35, -35, -35, -35, -35, -35, -35, -45, -35, -35, -35, -25, -25, -35, -35, 110, 120, 130, -35, 140, -25, -35, -25, -35, -35, -35, -35, -35, -45, -25, -35, -35, -25, -35, -35, -35, -35, -35, -25, -45, -35, -35, -35, -35, -35, -45, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -45, -35, -35, -100, -100, -35, 160, 170, 180, -35, -35, -100, -100, -35, -35, -45, -35, -45, -45, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -35, -35, -35, -35, -35, -45, -45, -45, -35, -45, -35, -25, -25, -35, -35, -35, -35, -35, -25, -35, -35, -25, -25, -35, -35, -35, -35, -35, -35, -25, -25, -25, -35, -35, -35, -35, -35, -25, -35, -35, -25, -100, -100, 230, 250, 260, 270, 320, 340, 360, 390, -35, -25, -25, 240, -35, -35, -35, -25, -35, -35, -25, -35, -35, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -35, -35, -25, -35, -35, -25, -35, -35, -35, -35, -35, -25, -35, -35, -35, -25, -35, -25, -25, -25, -35, 280, 290, 300, 310, -35, -25, -25, -25, -25, -25, -25, -25, -35, -35, -25, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -35, -25, -25, -25, -25, -25, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -25, -35, 330, -35, -35, -35, -35, -35, -25, -35, -35, -35, -35, -35, -25, -25, -25, -25, -35, -25, -25, -25, -35, -25, -35, -35, 350, -35, -25, -35, -35, -35, -35, -35, -35, -35, -25, -25, -35, -25, -35, 370, -35, -35, -25, -35, -35, 380, -25, -35, -35, -25, -25, -35, -35, -35, -35, -35, -25, -35, -25, -25, -25, -25, -35, -35, -35, -35, -25, -35, -25, 400, -35, -35, -35, -35, -25, -35, -25, -35, -35, -35, -35, -25, -25, -25, -25, -25, -15, -15, 420, 460, -25, -25, 470, 480, 500, 510, -15, -25, 430, -25, -25, -25, -25, -25, 440, 450, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -25, -25, -25, -35, -35, -35, -15, -25, -15, -15, -15, -15, -15, -25, -25, -15, -25, -25, -25, -25, -25, -25, -35, -25, -35, -35, -35, -25, -25, -35, -25, -35, -35, -35, -25, -25, 490, -15, -25, -25, -25, -35, -35, -25, -35, -35, -15, -35, -35, -35, -35, -35, -35, -35, -35, -15, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -35, -35, -25, -25, -25, 520, -100, -100, -45, -100, -45, -100, -45, -100, -45, -100, -26, -100, -25, 540, 580, 590, 600, 610, 630, 640, -25, -35, -35, -35, -25, -25, -35, -35, -35, 550, -35, -35, -25, -25, -25, -25, 560, 570, -25, -35, -35, -35, -35, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -25, -25, -35, -25, -25, -25, -25, -25, -25, -35, -35, -25, -35, -35, -25, -35, -35, -25, -35, -35, -35, -35, -35, -35, -25, -100, -35, -35, -35, -35, -35, -35, -35, -35, -35, -36, -100, -35, -35, -35, -35, 620, -35, -35, -100, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -25, -35, -25, -25, -35, -35, -35, -35, -25, -25, -25, -25, -25, -25, -35, -35, -35, 650, -35, 660, -35, -35, -35, -35, -45, -35, -35, -35, -35, -45, -35, -35, -35, -35, -35, -35, -35, -35, -35, -25, -26, -100, 680, 690, 700, -25, 720, 730, -25, 740, -25, -35, -25, -25, -25, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -35, -35, -35, -35, -35, -100, -35, -35, -35, -35, 710, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -35, -25, -35, -25, -35, -25, -35, -35, -35, -35, -25, -35, -35, -35, -35, -35, -25, -35, -25, -35, -35, -35, -35, -25, -25, 750, 760, 770, -35, -35, -35, -25, -35, -25, -25, -25, -25, -35, -35, -35, -25, -25, -35, -35, -35, -35, -25, -25, -35, -35, -25, -25, -35, -35, -35, -35, -35, -25, -25, -35, -35, 790, -100, 800, 850, 900, 920, 940, 1030, 1040, 1050, -36, -26, -26, -26, -26, -26, -26, -26, -26, -26, -35, -25, -25, -35, 810, -25, -35, -35, -25, 820, -25, -35, -25, -25, -35, -35, -35, -35, -35, -25, -25, -35, 830, -35, 840, -35, -25, -35, -35, -25, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -100, -25, -25, -25, -100, -100, -100, -100, -100, -100, -25, -25, -35, -35, -35, -35, 860, -35, 870, 880, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -45, -35, -100, -100, -100, -100, -100, -100, 890, -100, -100, -100, -25, -45, -45, -25, -45, -45, -25, -45, -45, -45, -25, -25, -25, -25, -25, -35, -35, 910, -35, -25, -35, -35, -35, -35, -35, -35, -35, -45, -35, -35, -100, 930, -35, -35, -35, -35, -35, -35, -35, -35, -100, -100, -45, -100, -45, -100, -100, -100, -100, -100, -25, -25, -25, 950, -25, 970, 990, -35, 1000, 1010, -35, -35, -35, -35, -35, -35, 960, -35, -35, -35, -45, -45, -45, -45, -45, -45, -35, -45, -45, -45, -35, -35, -25, -35, -35, 980, -35, -35, -35, -35, -100, -100, -25, -25, -100, -100, -100, -100, -100, -100, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -25, -35, -35, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -35, -25, -25, -35, -35, -35, 1020, -45, -45, -35, -35, -45, -45, -45, -45, -45, -45, -25, -25, -25, -25, -25, -35, -25, -35, -25, -35, -35, -25, -25, -35, -35, -35, -25, -35, -25, -35, -25, -25, -35, -35, -35, -35, -35, -35, -35, -25, -26, -100, 1070, 1080, 1090, 1110, 1120, 1130, 1140, 1160, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -25, -25, -25, -25, -25, -25, -25, -25, -25, -35, -100, -35, -35, -35, -100, -35, -35, -35, 1100, -35, -35, -35, -35, -35, -35, -45, -35, -35, -35, -35, -25, -35, -25, -35, -35, -35, -35, -25, -35, -25, -25, -25, -25, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -35, -25, -25, -35, -35, -35, 1150, -25, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -45, -35, -35, -35, -35, -35, -35, -35, -35, 1170, -25, -35, 1180, -35, 1190, -35, -25, -25, -100, -100, -45, -45, -100, -100, -100, -100, -100, -100, -25, -35, -35, -35, -35, -35, -35, -25, -25, -35, -35, -35, -35, -35, -35, -35, -35, -35, -35, -45, -26, -15, -15, -15, -15, -15, -15, -15, -15, -15};

    JapanesePhoneNumberFormatter() {
    }

    @UnsupportedAppUsage
    public static void format(Editable editable) {
        block12 : {
            int n;
            int n2;
            block11 : {
                block10 : {
                    n2 = 1;
                    n = editable.length();
                    if (n <= 3 || !editable.subSequence(0, 3).toString().equals("+81")) break block10;
                    n2 = 3;
                    break block11;
                }
                if (n < 1 || editable.charAt(0) != '0') break block12;
            }
            CharSequence charSequence = editable.subSequence(0, n);
            n = 0;
            while (n < editable.length()) {
                if (editable.charAt(n) == '-') {
                    editable.delete(n, n + 1);
                    continue;
                }
                ++n;
            }
            int n3 = editable.length();
            short s = 0;
            for (n = n2; n < n3; ++n) {
                char c = editable.charAt(n);
                if (!Character.isDigit(c)) {
                    editable.replace(0, n3, charSequence);
                    return;
                }
                if ((s = FORMAT_MAP[s + c - 48]) >= 0) continue;
                if (s <= -100) {
                    editable.replace(0, n3, charSequence);
                    return;
                }
                n = Math.abs(s) % 10 + n2;
                if (n3 > n) {
                    editable.insert(n, "-");
                }
                if (n3 <= (n = Math.abs(s) / 10 + n2)) break;
                editable.insert(n, "-");
                break;
            }
            if (n3 > 3 && n2 == 3) {
                editable.insert(n2, "-");
            }
            return;
        }
    }
}

