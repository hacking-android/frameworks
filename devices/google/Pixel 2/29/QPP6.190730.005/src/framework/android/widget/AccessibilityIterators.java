/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.Rect;
import android.text.Layout;
import android.text.Spannable;
import android.view.AccessibilityIterators;
import android.widget.TextView;

final class AccessibilityIterators {
    AccessibilityIterators() {
    }

    static class LineTextSegmentIterator
    extends AccessibilityIterators.AbstractTextSegmentIterator {
        protected static final int DIRECTION_END = 1;
        protected static final int DIRECTION_START = -1;
        private static LineTextSegmentIterator sLineInstance;
        protected Layout mLayout;

        LineTextSegmentIterator() {
        }

        public static LineTextSegmentIterator getInstance() {
            if (sLineInstance == null) {
                sLineInstance = new LineTextSegmentIterator();
            }
            return sLineInstance;
        }

        @Override
        public int[] following(int n) {
            int n2;
            if (this.mText.length() <= 0) {
                return null;
            }
            if (n >= this.mText.length()) {
                return null;
            }
            if ((n = n < 0 ? this.mLayout.getLineForOffset(0) : (this.getLineEdgeIndex(n2 = this.mLayout.getLineForOffset(n), -1) == n ? n2 : n2 + 1)) >= this.mLayout.getLineCount()) {
                return null;
            }
            return this.getRange(this.getLineEdgeIndex(n, -1), this.getLineEdgeIndex(n, 1) + 1);
        }

        protected int getLineEdgeIndex(int n, int n2) {
            if (n2 * this.mLayout.getParagraphDirection(n) < 0) {
                return this.mLayout.getLineStart(n);
            }
            return this.mLayout.getLineEnd(n) - 1;
        }

        public void initialize(Spannable spannable, Layout layout2) {
            this.mText = spannable.toString();
            this.mLayout = layout2;
        }

        @Override
        public int[] preceding(int n) {
            int n2;
            if (this.mText.length() <= 0) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            if ((n = n > this.mText.length() ? this.mLayout.getLineForOffset(this.mText.length()) : (this.getLineEdgeIndex(n2 = this.mLayout.getLineForOffset(n), 1) + 1 == n ? n2 : n2 - 1)) < 0) {
                return null;
            }
            return this.getRange(this.getLineEdgeIndex(n, -1), this.getLineEdgeIndex(n, 1) + 1);
        }
    }

    static class PageTextSegmentIterator
    extends LineTextSegmentIterator {
        private static PageTextSegmentIterator sPageInstance;
        private final Rect mTempRect = new Rect();
        private TextView mView;

        PageTextSegmentIterator() {
        }

        public static PageTextSegmentIterator getInstance() {
            if (sPageInstance == null) {
                sPageInstance = new PageTextSegmentIterator();
            }
            return sPageInstance;
        }

        @Override
        public int[] following(int n) {
            if (this.mText.length() <= 0) {
                return null;
            }
            if (n >= this.mText.length()) {
                return null;
            }
            if (!this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int n2 = Math.max(0, n);
            n = this.mLayout.getLineForOffset(n2);
            n = (n = this.mLayout.getLineTop(n) + (this.mTempRect.height() - this.mView.getTotalPaddingTop() - this.mView.getTotalPaddingBottom())) < this.mLayout.getLineTop(this.mLayout.getLineCount() - 1) ? this.mLayout.getLineForVertical(n) : this.mLayout.getLineCount();
            return this.getRange(n2, this.getLineEdgeIndex(n - 1, 1) + 1);
        }

        public void initialize(TextView textView) {
            super.initialize((Spannable)textView.getIterableTextForAccessibility(), textView.getLayout());
            this.mView = textView;
        }

        @Override
        public int[] preceding(int n) {
            if (this.mText.length() <= 0) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            if (!this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int n2 = Math.min(this.mText.length(), n);
            int n3 = this.mLayout.getLineForOffset(n2);
            n = this.mLayout.getLineTop(n3) - (this.mTempRect.height() - this.mView.getTotalPaddingTop() - this.mView.getTotalPaddingBottom());
            n = n > 0 ? this.mLayout.getLineForVertical(n) : 0;
            int n4 = n;
            if (n2 == this.mText.length()) {
                n4 = n;
                if (n < n3) {
                    n4 = n + 1;
                }
            }
            return this.getRange(this.getLineEdgeIndex(n4, -1), n2);
        }
    }

}

