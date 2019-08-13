/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.util;

import com.google.android.util.AbstractMessageParser;
import java.util.HashMap;
import java.util.Set;

public class SmileyResources
implements AbstractMessageParser.Resources {
    private HashMap<String, Integer> mSmileyToRes = new HashMap();
    private final AbstractMessageParser.TrieNode smileys = new AbstractMessageParser.TrieNode();

    public SmileyResources(String[] arrstring, int[] arrn) {
        for (int i = 0; i < arrstring.length; ++i) {
            AbstractMessageParser.TrieNode.addToTrie(this.smileys, arrstring[i], "");
            this.mSmileyToRes.put(arrstring[i], arrn[i]);
        }
    }

    @Override
    public AbstractMessageParser.TrieNode getAcronyms() {
        return null;
    }

    @Override
    public AbstractMessageParser.TrieNode getDomainSuffixes() {
        return null;
    }

    @Override
    public Set<String> getSchemes() {
        return null;
    }

    public int getSmileyRes(String object) {
        if ((object = this.mSmileyToRes.get(object)) == null) {
            return -1;
        }
        return (Integer)object;
    }

    @Override
    public AbstractMessageParser.TrieNode getSmileys() {
        return this.smileys;
    }
}

