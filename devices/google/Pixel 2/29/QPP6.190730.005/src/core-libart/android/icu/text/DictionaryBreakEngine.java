/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CharacterIteration;
import android.icu.text.DictionaryMatcher;
import android.icu.text.LanguageBreakEngine;
import android.icu.text.UnicodeSet;
import java.text.CharacterIterator;

abstract class DictionaryBreakEngine
implements LanguageBreakEngine {
    UnicodeSet fSet = new UnicodeSet();

    abstract int divideUpDictionaryRange(CharacterIterator var1, int var2, int var3, DequeI var4);

    @Override
    public int findBreaks(CharacterIterator characterIterator, int n, int n2, DequeI dequeI) {
        int n3;
        int n4 = characterIterator.getIndex();
        n = CharacterIteration.current32(characterIterator);
        while ((n3 = characterIterator.getIndex()) < n2 && this.fSet.contains(n)) {
            CharacterIteration.next32(characterIterator);
            n = CharacterIteration.current32(characterIterator);
        }
        n = this.divideUpDictionaryRange(characterIterator, n4, n3, dequeI);
        characterIterator.setIndex(n3);
        return n;
    }

    @Override
    public boolean handles(int n) {
        return this.fSet.contains(n);
    }

    void setCharacters(UnicodeSet unicodeSet) {
        this.fSet = new UnicodeSet(unicodeSet);
        this.fSet.compact();
    }

    static class DequeI
    implements Cloneable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int[] data = new int[50];
        private int firstIdx = 4;
        private int lastIdx = 4;

        DequeI() {
        }

        private void grow() {
            int[] arrn = this.data;
            int[] arrn2 = new int[arrn.length * 2];
            System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
            this.data = arrn2;
        }

        public Object clone() throws CloneNotSupportedException {
            DequeI dequeI = (DequeI)super.clone();
            dequeI.data = (int[])this.data.clone();
            return dequeI;
        }

        boolean contains(int n) {
            for (int i = this.lastIdx; i < this.firstIdx; ++i) {
                if (this.data[i] != n) continue;
                return true;
            }
            return false;
        }

        int elementAt(int n) {
            return this.data[this.lastIdx + n];
        }

        boolean isEmpty() {
            boolean bl = this.size() == 0;
            return bl;
        }

        void offer(int n) {
            int n2;
            int[] arrn = this.data;
            this.lastIdx = n2 = this.lastIdx - 1;
            arrn[n2] = n;
        }

        int peek() {
            return this.data[this.firstIdx - 1];
        }

        int peekLast() {
            return this.data[this.lastIdx];
        }

        int pollLast() {
            int[] arrn = this.data;
            int n = this.lastIdx;
            this.lastIdx = n + 1;
            return arrn[n];
        }

        int pop() {
            int n;
            int[] arrn = this.data;
            this.firstIdx = n = this.firstIdx - 1;
            return arrn[n];
        }

        void push(int n) {
            if (this.firstIdx >= this.data.length) {
                this.grow();
            }
            int[] arrn = this.data;
            int n2 = this.firstIdx;
            this.firstIdx = n2 + 1;
            arrn[n2] = n;
        }

        void removeAllElements() {
            this.firstIdx = 4;
            this.lastIdx = 4;
        }

        int size() {
            return this.firstIdx - this.lastIdx;
        }
    }

    static class PossibleWord {
        private static final int POSSIBLE_WORD_LIST_MAX = 20;
        private int[] count = new int[1];
        private int current;
        private int[] lengths = new int[20];
        private int mark;
        private int offset = -1;
        private int prefix;

        public int acceptMarked(CharacterIterator characterIterator) {
            characterIterator.setIndex(this.offset + this.lengths[this.mark]);
            return this.lengths[this.mark];
        }

        public boolean backUp(CharacterIterator characterIterator) {
            int n = this.current;
            if (n > 0) {
                int n2 = this.offset;
                int[] arrn = this.lengths;
                this.current = --n;
                characterIterator.setIndex(n2 + arrn[n]);
                return true;
            }
            return false;
        }

        public int candidates(CharacterIterator arrn, DictionaryMatcher arrn2, int n) {
            int n2 = arrn.getIndex();
            if (n2 != this.offset) {
                this.offset = n2;
                int[] arrn3 = this.lengths;
                this.prefix = arrn2.matches((CharacterIterator)arrn, n - n2, arrn3, this.count, arrn3.length);
                if (this.count[0] <= 0) {
                    arrn.setIndex(n2);
                }
            }
            if ((arrn2 = this.count)[0] > 0) {
                arrn.setIndex(this.lengths[arrn2[0] - 1] + n2);
            }
            arrn = this.count;
            this.mark = this.current = arrn[0] - 1;
            return arrn[0];
        }

        public int longestPrefix() {
            return this.prefix;
        }

        public void markCurrent() {
            this.mark = this.current;
        }
    }

}

