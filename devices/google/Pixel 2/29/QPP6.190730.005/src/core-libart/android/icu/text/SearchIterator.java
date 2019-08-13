/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.BreakIterator;
import java.text.CharacterIterator;

public abstract class SearchIterator {
    public static final int DONE = -1;
    protected BreakIterator breakIterator;
    protected int matchLength;
    Search search_ = new Search();
    protected CharacterIterator targetText;

    protected SearchIterator(CharacterIterator object, BreakIterator breakIterator) {
        if (object != null && object.getEndIndex() - object.getBeginIndex() != 0) {
            this.search_.setTarget((CharacterIterator)object);
            this.search_.setBreakIter(breakIterator);
            if (this.search_.breakIter() != null) {
                this.search_.breakIter().setText((CharacterIterator)object.clone());
            }
            object = this.search_;
            ((Search)object).isOverlap_ = false;
            ((Search)object).isCanonicalMatch_ = false;
            ((Search)object).elementComparisonType_ = ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
            object = this.search_;
            ((Search)object).isForwardSearching_ = true;
            ((Search)object).reset_ = true;
            ((Search)object).matchedIndex_ = -1;
            ((Search)object).setMatchedLength(0);
            return;
        }
        throw new IllegalArgumentException("Illegal argument target.  Argument can not be null or of length 0");
    }

    public final int first() {
        int n = this.search_.beginIndex();
        this.setIndex(n);
        return this.handleNext(n);
    }

    public final int following(int n) {
        this.setIndex(n);
        return this.handleNext(n);
    }

    public BreakIterator getBreakIterator() {
        return this.search_.breakIter();
    }

    public ElementComparisonType getElementComparisonType() {
        return this.search_.elementComparisonType_;
    }

    public abstract int getIndex();

    public int getMatchLength() {
        return this.search_.matchedLength();
    }

    public int getMatchStart() {
        return this.search_.matchedIndex_;
    }

    public String getMatchedText() {
        if (this.search_.matchedLength() > 0) {
            int n = this.search_.matchedIndex_;
            int n2 = this.search_.matchedLength();
            StringBuilder stringBuilder = new StringBuilder(this.search_.matchedLength());
            CharacterIterator characterIterator = this.search_.text();
            characterIterator.setIndex(this.search_.matchedIndex_);
            while (characterIterator.getIndex() < n + n2) {
                stringBuilder.append(characterIterator.current());
                characterIterator.next();
            }
            characterIterator.setIndex(this.search_.matchedIndex_);
            return stringBuilder.toString();
        }
        return null;
    }

    public CharacterIterator getTarget() {
        return this.search_.text();
    }

    protected abstract int handleNext(int var1);

    protected abstract int handlePrevious(int var1);

    public boolean isOverlapping() {
        return this.search_.isOverlap_;
    }

    public final int last() {
        int n = this.search_.endIndex();
        this.setIndex(n);
        return this.handlePrevious(n);
    }

    public int next() {
        int n;
        int n2 = this.getIndex();
        int n3 = this.search_.matchedIndex_;
        int n4 = this.search_.matchedLength();
        Search search = this.search_;
        search.reset_ = false;
        if (search.isForwardSearching_) {
            n = this.search_.endIndex();
            if (n2 == n || n3 == n || n3 != -1 && n3 + n4 >= n) {
                this.setMatchNotFound();
                return -1;
            }
        } else {
            search = this.search_;
            search.isForwardSearching_ = true;
            if (search.matchedIndex_ != -1) {
                return n3;
            }
        }
        n = n2;
        if (n4 > 0) {
            n = this.search_.isOverlap_ ? n2 + 1 : n2 + n4;
        }
        return this.handleNext(n);
    }

    public final int preceding(int n) {
        this.setIndex(n);
        return this.handlePrevious(n);
    }

    public int previous() {
        block11 : {
            int n;
            int n2;
            block10 : {
                block9 : {
                    if (this.search_.reset_) {
                        n = this.search_.endIndex();
                        Search search = this.search_;
                        search.isForwardSearching_ = false;
                        search.reset_ = false;
                        this.setIndex(n);
                    } else {
                        n = this.getIndex();
                    }
                    n2 = this.search_.matchedIndex_;
                    if (!this.search_.isForwardSearching_) break block9;
                    this.search_.isForwardSearching_ = false;
                    if (n2 != -1) {
                        return n2;
                    }
                    break block10;
                }
                int n3 = this.search_.beginIndex();
                if (n == n3 || n2 == n3) break block11;
            }
            if (n2 != -1) {
                n = n2;
                if (this.search_.isOverlap_) {
                    n = n2 + (this.search_.matchedLength() - 2);
                }
                return this.handlePrevious(n);
            }
            return this.handlePrevious(n);
        }
        this.setMatchNotFound();
        return -1;
    }

    public void reset() {
        this.setMatchNotFound();
        this.setIndex(this.search_.beginIndex());
        Search search = this.search_;
        search.isOverlap_ = false;
        search.isCanonicalMatch_ = false;
        search.elementComparisonType_ = ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        search = this.search_;
        search.isForwardSearching_ = true;
        search.reset_ = true;
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.search_.setBreakIter(breakIterator);
        if (this.search_.breakIter() != null && this.search_.text() != null) {
            this.search_.breakIter().setText((CharacterIterator)this.search_.text().clone());
        }
    }

    public void setElementComparisonType(ElementComparisonType elementComparisonType) {
        this.search_.elementComparisonType_ = elementComparisonType;
    }

    public void setIndex(int n) {
        if (n >= this.search_.beginIndex() && n <= this.search_.endIndex()) {
            Search search = this.search_;
            search.reset_ = false;
            search.setMatchedLength(0);
            this.search_.matchedIndex_ = -1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setIndex(int) expected position to be between ");
        stringBuilder.append(this.search_.beginIndex());
        stringBuilder.append(" and ");
        stringBuilder.append(this.search_.endIndex());
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    protected void setMatchLength(int n) {
        this.search_.setMatchedLength(n);
    }

    @Deprecated
    protected void setMatchNotFound() {
        Search search = this.search_;
        search.matchedIndex_ = -1;
        search.setMatchedLength(0);
    }

    public void setOverlapping(boolean bl) {
        this.search_.isOverlap_ = bl;
    }

    public void setTarget(CharacterIterator characterIterator) {
        if (characterIterator != null && characterIterator.getEndIndex() != characterIterator.getIndex()) {
            characterIterator.setIndex(characterIterator.getBeginIndex());
            this.search_.setTarget(characterIterator);
            Search search = this.search_;
            search.matchedIndex_ = -1;
            search.setMatchedLength(0);
            search = this.search_;
            search.reset_ = true;
            search.isForwardSearching_ = true;
            if (search.breakIter() != null) {
                this.search_.breakIter().setText((CharacterIterator)characterIterator.clone());
            }
            if (this.search_.internalBreakIter_ != null) {
                this.search_.internalBreakIter_.setText((CharacterIterator)characterIterator.clone());
            }
            return;
        }
        throw new IllegalArgumentException("Illegal null or empty text");
    }

    public static enum ElementComparisonType {
        STANDARD_ELEMENT_COMPARISON,
        PATTERN_BASE_WEIGHT_IS_WILDCARD,
        ANY_BASE_WEIGHT_IS_WILDCARD;
        
    }

    final class Search {
        ElementComparisonType elementComparisonType_;
        BreakIterator internalBreakIter_;
        boolean isCanonicalMatch_;
        boolean isForwardSearching_;
        boolean isOverlap_;
        int matchedIndex_;
        boolean reset_;

        Search() {
        }

        int beginIndex() {
            if (SearchIterator.this.targetText == null) {
                return 0;
            }
            return SearchIterator.this.targetText.getBeginIndex();
        }

        BreakIterator breakIter() {
            return SearchIterator.this.breakIterator;
        }

        int endIndex() {
            if (SearchIterator.this.targetText == null) {
                return 0;
            }
            return SearchIterator.this.targetText.getEndIndex();
        }

        int matchedLength() {
            return SearchIterator.this.matchLength;
        }

        void setBreakIter(BreakIterator breakIterator) {
            SearchIterator.this.breakIterator = breakIterator;
        }

        void setMatchedLength(int n) {
            SearchIterator.this.matchLength = n;
        }

        void setTarget(CharacterIterator characterIterator) {
            SearchIterator.this.targetText = characterIterator;
        }

        CharacterIterator text() {
            return SearchIterator.this.targetText;
        }
    }

}

