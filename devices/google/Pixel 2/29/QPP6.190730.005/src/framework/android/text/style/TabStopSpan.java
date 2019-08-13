/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.text.style.ParagraphStyle;

public interface TabStopSpan
extends ParagraphStyle {
    public int getTabStop();

    public static class Standard
    implements TabStopSpan {
        private int mTabOffset;

        public Standard(int n) {
            this.mTabOffset = n;
        }

        @Override
        public int getTabStop() {
            return this.mTabOffset;
        }
    }

}

