/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.SimpleTimeZone;
import java.io.Serializable;

final class STZInfo
implements Serializable {
    private static final long serialVersionUID = -7849612037842370168L;
    boolean ea;
    int edm;
    int edw;
    int edwm;
    int em = -1;
    int et;
    boolean sa;
    int sdm;
    int sdw;
    int sdwm;
    int sm = -1;
    int st;
    int sy = -1;

    STZInfo() {
    }

    void applyTo(SimpleTimeZone simpleTimeZone) {
        int n;
        int n2;
        int n3 = this.sy;
        if (n3 != -1) {
            simpleTimeZone.setStartYear(n3);
        }
        if ((n2 = this.sm) != -1) {
            n = this.sdm;
            if (n == -1) {
                simpleTimeZone.setStartRule(n2, this.sdwm, this.sdw, this.st);
            } else {
                n3 = this.sdw;
                if (n3 == -1) {
                    simpleTimeZone.setStartRule(n2, n, this.st);
                } else {
                    simpleTimeZone.setStartRule(n2, n, n3, this.st, this.sa);
                }
            }
        }
        if ((n = this.em) != -1) {
            n2 = this.edm;
            if (n2 == -1) {
                simpleTimeZone.setEndRule(n, this.edwm, this.edw, this.et);
            } else {
                n3 = this.edw;
                if (n3 == -1) {
                    simpleTimeZone.setEndRule(n, n2, this.et);
                } else {
                    simpleTimeZone.setEndRule(n, n2, n3, this.et, this.ea);
                }
            }
        }
    }

    void setEnd(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.em = n;
        this.edwm = n2;
        this.edw = n3;
        this.et = n4;
        this.edm = n5;
        this.ea = bl;
    }

    void setStart(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.sm = n;
        this.sdwm = n2;
        this.sdw = n3;
        this.st = n4;
        this.sdm = n5;
        this.sa = bl;
    }
}

