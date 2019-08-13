/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.view.ViewRootImpl;
import java.text.BreakIterator;
import java.util.Locale;

public final class AccessibilityIterators {

    public static abstract class AbstractTextSegmentIterator
    implements TextSegmentIterator {
        private final int[] mSegment = new int[2];
        @UnsupportedAppUsage
        protected String mText;

        protected int[] getRange(int n, int n2) {
            if (n >= 0 && n2 >= 0 && n != n2) {
                int[] arrn = this.mSegment;
                arrn[0] = n;
                arrn[1] = n2;
                return arrn;
            }
            return null;
        }

        public void initialize(String string2) {
            this.mText = string2;
        }
    }

    static class CharacterTextSegmentIterator
    extends AbstractTextSegmentIterator
    implements ViewRootImpl.ConfigChangedCallback {
        private static CharacterTextSegmentIterator sInstance;
        protected BreakIterator mImpl;
        private Locale mLocale;

        private CharacterTextSegmentIterator(Locale locale) {
            this.mLocale = locale;
            this.onLocaleChanged(locale);
            ViewRootImpl.addConfigCallback(this);
        }

        public static CharacterTextSegmentIterator getInstance(Locale locale) {
            if (sInstance == null) {
                sInstance = new CharacterTextSegmentIterator(locale);
            }
            return sInstance;
        }

        @Override
        public int[] following(int n) {
            int n2 = this.mText.length();
            if (n2 <= 0) {
                return null;
            }
            if (n >= n2) {
                return null;
            }
            n = n2 = n;
            if (n2 < 0) {
                n = 0;
            }
            while (!this.mImpl.isBoundary(n)) {
                n = n2 = this.mImpl.following(n);
                if (n2 != -1) continue;
                return null;
            }
            n2 = this.mImpl.following(n);
            if (n2 == -1) {
                return null;
            }
            return this.getRange(n, n2);
        }

        @Override
        public void initialize(String string2) {
            super.initialize(string2);
            this.mImpl.setText(string2);
        }

        @Override
        public void onConfigurationChanged(Configuration object) {
            if ((object = ((Configuration)object).getLocales().get(0)) == null) {
                return;
            }
            if (!this.mLocale.equals(object)) {
                this.mLocale = object;
                this.onLocaleChanged((Locale)object);
            }
        }

        protected void onLocaleChanged(Locale locale) {
            this.mImpl = BreakIterator.getCharacterInstance(locale);
        }

        @Override
        public int[] preceding(int n) {
            int n2;
            int n3 = this.mText.length();
            if (n3 <= 0) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            n = n2 = n;
            if (n2 > n3) {
                n = n3;
            }
            while (!this.mImpl.isBoundary(n)) {
                n = n2 = this.mImpl.preceding(n);
                if (n2 != -1) continue;
                return null;
            }
            n2 = this.mImpl.preceding(n);
            if (n2 == -1) {
                return null;
            }
            return this.getRange(n2, n);
        }
    }

    static class ParagraphTextSegmentIterator
    extends AbstractTextSegmentIterator {
        private static ParagraphTextSegmentIterator sInstance;

        ParagraphTextSegmentIterator() {
        }

        public static ParagraphTextSegmentIterator getInstance() {
            if (sInstance == null) {
                sInstance = new ParagraphTextSegmentIterator();
            }
            return sInstance;
        }

        private boolean isEndBoundary(int n) {
            boolean bl = n > 0 && this.mText.charAt(n - 1) != '\n' && (n == this.mText.length() || this.mText.charAt(n) == '\n');
            return bl;
        }

        private boolean isStartBoundary(int n) {
            boolean bl = this.mText.charAt(n) != '\n' && (n == 0 || this.mText.charAt(n - 1) == '\n');
            return bl;
        }

        @Override
        public int[] following(int n) {
            int n2;
            int n3 = this.mText.length();
            if (n3 <= 0) {
                return null;
            }
            if (n >= n3) {
                return null;
            }
            n = n2 = n;
            if (n2 < 0) {
                n = 0;
            }
            while (n < n3 && this.mText.charAt(n) == '\n' && !this.isStartBoundary(n)) {
                ++n;
            }
            if (n >= n3) {
                return null;
            }
            for (n2 = n + 1; n2 < n3 && !this.isEndBoundary(n2); ++n2) {
            }
            return this.getRange(n, n2);
        }

        @Override
        public int[] preceding(int n) {
            int n2;
            int n3 = this.mText.length();
            if (n3 <= 0) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            n = n2 = n;
            if (n2 > n3) {
                n = n3;
            }
            while (n > 0 && this.mText.charAt(n - 1) == '\n' && !this.isEndBoundary(n)) {
                --n;
            }
            if (n <= 0) {
                return null;
            }
            for (n2 = n - 1; n2 > 0 && !this.isStartBoundary(n2); --n2) {
            }
            return this.getRange(n2, n);
        }
    }

    public static interface TextSegmentIterator {
        public int[] following(int var1);

        public int[] preceding(int var1);
    }

    static class WordTextSegmentIterator
    extends CharacterTextSegmentIterator {
        private static WordTextSegmentIterator sInstance;

        private WordTextSegmentIterator(Locale locale) {
            super(locale);
        }

        public static WordTextSegmentIterator getInstance(Locale locale) {
            if (sInstance == null) {
                sInstance = new WordTextSegmentIterator(locale);
            }
            return sInstance;
        }

        private boolean isEndBoundary(int n) {
            boolean bl = n > 0 && this.isLetterOrDigit(n - 1) && (n == this.mText.length() || !this.isLetterOrDigit(n));
            return bl;
        }

        private boolean isLetterOrDigit(int n) {
            if (n >= 0 && n < this.mText.length()) {
                return Character.isLetterOrDigit(this.mText.codePointAt(n));
            }
            return false;
        }

        private boolean isStartBoundary(int n) {
            boolean bl = this.isLetterOrDigit(n) && (n == 0 || !this.isLetterOrDigit(n - 1));
            return bl;
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
            n = n2 = n;
            if (n2 < 0) {
                n = 0;
            }
            while (!this.isLetterOrDigit(n) && !this.isStartBoundary(n)) {
                n = n2 = this.mImpl.following(n);
                if (n2 != -1) continue;
                return null;
            }
            n2 = this.mImpl.following(n);
            if (n2 != -1 && this.isEndBoundary(n2)) {
                return this.getRange(n, n2);
            }
            return null;
        }

        @Override
        protected void onLocaleChanged(Locale locale) {
            this.mImpl = BreakIterator.getWordInstance(locale);
        }

        @Override
        public int[] preceding(int n) {
            int n2;
            int n3 = this.mText.length();
            if (n3 <= 0) {
                return null;
            }
            if (n <= 0) {
                return null;
            }
            n = n2 = n;
            if (n2 > n3) {
                n = n3;
            }
            while (n > 0 && !this.isLetterOrDigit(n - 1) && !this.isEndBoundary(n)) {
                n = n2 = this.mImpl.preceding(n);
                if (n2 != -1) continue;
                return null;
            }
            n2 = this.mImpl.preceding(n);
            if (n2 != -1 && this.isStartBoundary(n2)) {
                return this.getRange(n2, n);
            }
            return null;
        }
    }

}

