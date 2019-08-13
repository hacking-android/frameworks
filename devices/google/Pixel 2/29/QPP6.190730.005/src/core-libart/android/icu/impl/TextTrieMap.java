/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TextTrieMap<V> {
    boolean _ignoreCase;
    private TextTrieMap<V> _root = new Node();

    public TextTrieMap(boolean bl) {
        this._ignoreCase = bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void find(TextTrieMap<V> object, CharIterator charIterator, ResultHandler<V> resultHandler, Output output) {
        synchronized (this) {
            void var3_3;
            boolean bl;
            void var2_2;
            void var4_4;
            Iterator iterator = ((Node)object).values();
            if (iterator != null && !(bl = var3_3.handlePrefixMatch(var2_2.processedLength(), iterator))) {
                return;
            }
            if ((object = object.findMatch((CharIterator)var2_2, (Output)var4_4)) != null) {
                this.find((Node)object, (CharIterator)var2_2, (ResultHandler)var3_3, (Output)var4_4);
            }
            return;
        }
    }

    private void find(CharSequence object, int n, ResultHandler<V> resultHandler, Output output) {
        object = new CharIterator((CharSequence)object, n, this._ignoreCase);
        this.find(this._root, (CharIterator)object, resultHandler, output);
    }

    private static char[] subArray(char[] arrc, int n) {
        if (n == 0) {
            return arrc;
        }
        char[] arrc2 = new char[arrc.length - n];
        System.arraycopy(arrc, n, arrc2, 0, arrc2.length);
        return arrc2;
    }

    private static char[] subArray(char[] arrc, int n, int n2) {
        if (n == 0 && n2 == arrc.length) {
            return arrc;
        }
        char[] arrc2 = new char[n2 - n];
        System.arraycopy(arrc, n, arrc2, 0, n2 - n);
        return arrc2;
    }

    private static char[] toCharArray(CharSequence charSequence) {
        char[] arrc = new char[charSequence.length()];
        for (int i = 0; i < arrc.length; ++i) {
            arrc[i] = charSequence.charAt(i);
        }
        return arrc;
    }

    public void find(CharSequence charSequence, int n, ResultHandler<V> resultHandler) {
        this.find(charSequence, n, resultHandler, null);
    }

    public void find(CharSequence charSequence, ResultHandler<V> resultHandler) {
        this.find(charSequence, 0, resultHandler, null);
    }

    public Iterator<V> get(CharSequence charSequence, int n) {
        return this.get(charSequence, n, null);
    }

    public Iterator<V> get(CharSequence charSequence, int n, Output output) {
        LongestMatchHandler longestMatchHandler = new LongestMatchHandler();
        this.find(charSequence, n, longestMatchHandler, output);
        if (output != null) {
            output.matchLength = longestMatchHandler.getMatchLength();
        }
        return longestMatchHandler.getMatches();
    }

    public Iterator<V> get(String string) {
        return this.get(string, 0);
    }

    public TextTrieMap<V> put(CharSequence object, V v) {
        object = new CharIterator((CharSequence)object, 0, this._ignoreCase);
        ((Node)((Object)this._root)).add((CharIterator)object, v);
        return this;
    }

    public void putLeadCodePoints(UnicodeSet unicodeSet) {
        ((Node)((Object)this._root)).putLeadCodePoints(unicodeSet);
    }

    public static class CharIterator
    implements Iterator<Character> {
        private boolean _ignoreCase;
        private int _nextIdx;
        private Character _remainingChar;
        private int _startIdx;
        private CharSequence _text;

        CharIterator(CharSequence charSequence, int n, boolean bl) {
            this._text = charSequence;
            this._startIdx = n;
            this._nextIdx = n;
            this._ignoreCase = bl;
        }

        @Override
        public boolean hasNext() {
            return this._nextIdx != this._text.length() || this._remainingChar != null;
        }

        @Override
        public Character next() {
            Object object;
            if (this._nextIdx == this._text.length() && this._remainingChar == null) {
                return null;
            }
            if (this._remainingChar != null) {
                object = this._remainingChar;
                this._remainingChar = null;
            } else if (this._ignoreCase) {
                int n = UCharacter.foldCase(Character.codePointAt(this._text, this._nextIdx), true);
                this._nextIdx += Character.charCount(n);
                object = Character.toChars(n);
                char c = object[0];
                if (((char[])object).length == 2) {
                    this._remainingChar = Character.valueOf(object[1]);
                }
                object = Character.valueOf(c);
            } else {
                object = Character.valueOf(this._text.charAt(this._nextIdx));
                ++this._nextIdx;
            }
            return object;
        }

        public int nextIndex() {
            return this._nextIdx;
        }

        public int processedLength() {
            if (this._remainingChar == null) {
                return this._nextIdx - this._startIdx;
            }
            throw new IllegalStateException("In the middle of surrogate pair");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supproted");
        }
    }

    private static class LongestMatchHandler<V>
    implements ResultHandler<V> {
        private int length = 0;
        private Iterator<V> matches = null;

        private LongestMatchHandler() {
        }

        public int getMatchLength() {
            return this.length;
        }

        public Iterator<V> getMatches() {
            return this.matches;
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<V> iterator) {
            if (n > this.length) {
                this.length = n;
                this.matches = iterator;
            }
            return true;
        }
    }

    private class Node {
        private List<TextTrieMap<V>> _children;
        private char[] _text;
        private List<V> _values;

        private Node() {
        }

        private Node(char[] arrc, List<V> list, List<TextTrieMap<V>> list2) {
            this._text = arrc;
            this._values = list;
            this._children = list2;
        }

        private void add(char[] object, int n, V v) {
            if (((char[])object).length == n) {
                this._values = this.addValue(this._values, v);
                return;
            }
            List<TextTrieMap<V>> list = this._children;
            if (list == null) {
                this._children = new LinkedList<TextTrieMap<V>>();
                object = new Node(TextTrieMap.subArray(object, n), this.addValue(null, v), null);
                this._children.add((TextTrieMap<V>)object);
                return;
            }
            ListIterator<TextTrieMap<V>> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                list = (Node)((Object)listIterator.next());
                int n2 = object[n];
                char[] arrc = ((Node)list)._text;
                if (n2 < arrc[0]) {
                    listIterator.previous();
                    break;
                }
                if (object[n] != arrc[0]) continue;
                n2 = Node.super.lenMatches(object, n);
                if (n2 == ((Node)list)._text.length) {
                    Node.super.add(object, n + n2, v);
                } else {
                    Node.super.split(n2);
                    Node.super.add(object, n + n2, v);
                }
                return;
            }
            listIterator.add((TextTrieMap<V>)((Object)new Node(TextTrieMap.subArray(object, n), this.addValue(null, v), null)));
        }

        private List<V> addValue(List<V> list, V v) {
            List<V> list2 = list;
            if (list == null) {
                list2 = new LinkedList<V>();
            }
            list2.add(v);
            return list2;
        }

        private int lenMatches(char[] arrc, int n) {
            int n2;
            char[] arrc2 = this._text;
            int n3 = arrc.length - n;
            if (arrc2.length < n3) {
                n3 = arrc2.length;
            }
            for (n2 = 0; n2 < n3 && this._text[n2] == arrc[n + n2]; ++n2) {
            }
            return n2;
        }

        private boolean matchFollowing(CharIterator charIterator, Output output) {
            boolean bl;
            boolean bl2 = true;
            int n = 1;
            do {
                bl = bl2;
                if (n >= this._text.length) break;
                if (!charIterator.hasNext()) {
                    if (output != null) {
                        output.partialMatch = true;
                    }
                    bl = false;
                    break;
                }
                if (charIterator.next().charValue() != this._text[n]) {
                    bl = false;
                    break;
                }
                ++n;
            } while (true);
            return bl;
        }

        private void split(int n) {
            Object object = TextTrieMap.subArray(this._text, n);
            this._text = TextTrieMap.subArray(this._text, 0, n);
            object = new Node((char[])object, this._values, this._children);
            this._values = null;
            this._children = new LinkedList<TextTrieMap<V>>();
            this._children.add((TextTrieMap<V>)object);
        }

        public void add(CharIterator charIterator, V v) {
            StringBuilder stringBuilder = new StringBuilder();
            while (charIterator.hasNext()) {
                stringBuilder.append(charIterator.next());
            }
            this.add(TextTrieMap.toCharArray(stringBuilder), 0, v);
        }

        public int charCount() {
            char[] arrc = this._text;
            int n = arrc == null ? 0 : arrc.length;
            return n;
        }

        public TextTrieMap<V> findMatch(CharIterator charIterator, Output output) {
            Node node;
            block4 : {
                Node node2;
                if (this._children == null) {
                    return null;
                }
                if (!charIterator.hasNext()) {
                    if (output != null) {
                        output.partialMatch = true;
                    }
                    return null;
                }
                Node node3 = null;
                Character c = charIterator.next();
                Iterator<TextTrieMap<V>> iterator = this._children.iterator();
                do {
                    node = node3;
                    if (!iterator.hasNext()) break block4;
                    node2 = (Node)((Object)iterator.next());
                    if (c.charValue() >= node2._text[0]) continue;
                    node = node3;
                    break block4;
                } while (c.charValue() != node2._text[0]);
                node = node3;
                if (!node2.matchFollowing(charIterator, output)) break block4;
                node = node2;
            }
            return node;
        }

        public void putLeadCodePoints(UnicodeSet unicodeSet) {
            List<TextTrieMap<V>> list = this._children;
            if (list == null) {
                return;
            }
            for (Node node : list) {
                char c = node._text[0];
                if (!UCharacter.isHighSurrogate(c)) {
                    unicodeSet.add(c);
                    continue;
                }
                if (node.charCount() >= 2) {
                    unicodeSet.add(Character.codePointAt(node._text, 0));
                    continue;
                }
                List<TextTrieMap<V>> list2 = node._children;
                if (list2 == null) continue;
                Iterator<TextTrieMap<V>> iterator = list2.iterator();
                while (iterator.hasNext()) {
                    unicodeSet.add(Character.toCodePoint(c, ((Node)iterator.next())._text[0]));
                }
            }
        }

        public Iterator<V> values() {
            List<V> list = this._values;
            if (list == null) {
                return null;
            }
            return list.iterator();
        }
    }

    public static class Output {
        public int matchLength;
        public boolean partialMatch;
    }

    public static interface ResultHandler<V> {
        public boolean handlePrefixMatch(int var1, Iterator<V> var2);
    }

}

