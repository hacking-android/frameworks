/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.CollationElementIterator
 *  android.icu.text.CollationKey
 *  android.icu.text.Collator
 *  android.icu.text.RuleBasedCollator
 *  libcore.icu.CollationKeyICU
 */
package java.text;

import java.text.CharacterIterator;
import java.text.CollationElementIterator;
import java.text.CollationKey;
import java.text.Collator;
import java.text.ParseException;
import libcore.icu.CollationKeyICU;

public class RuleBasedCollator
extends Collator {
    RuleBasedCollator(android.icu.text.RuleBasedCollator ruleBasedCollator) {
        super((android.icu.text.Collator)ruleBasedCollator);
    }

    public RuleBasedCollator(String string) throws ParseException {
        if (string != null) {
            try {
                android.icu.text.RuleBasedCollator ruleBasedCollator = new android.icu.text.RuleBasedCollator(string);
                this.icuColl = ruleBasedCollator;
                return;
            }
            catch (Exception exception) {
                if (exception instanceof ParseException) {
                    throw (ParseException)exception;
                }
                throw new ParseException(exception.getMessage(), -1);
            }
        }
        throw new NullPointerException("rules == null");
    }

    private android.icu.text.RuleBasedCollator collAsICU() {
        return (android.icu.text.RuleBasedCollator)this.icuColl;
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public int compare(String object, String string) {
        synchronized (this) {
            if (object != null && string != null) {
                int n = this.icuColl.compare((String)object, string);
                return n;
            }
            object = new NullPointerException();
            throw object;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        return super.equals(object);
    }

    public CollationElementIterator getCollationElementIterator(String string) {
        if (string != null) {
            return new CollationElementIterator(this.collAsICU().getCollationElementIterator(string));
        }
        throw new NullPointerException("source == null");
    }

    public CollationElementIterator getCollationElementIterator(CharacterIterator characterIterator) {
        if (characterIterator != null) {
            return new CollationElementIterator(this.collAsICU().getCollationElementIterator(characterIterator));
        }
        throw new NullPointerException("source == null");
    }

    @Override
    public CollationKey getCollationKey(String string) {
        synchronized (this) {
            if (string == null) {
                return null;
            }
            string = new CollationKeyICU(string, this.icuColl.getCollationKey(string));
            return string;
        }
    }

    public String getRules() {
        return this.collAsICU().getRules();
    }

    @Override
    public int hashCode() {
        return this.icuColl.hashCode();
    }
}

