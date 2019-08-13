/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.UBiDiProps;
import android.icu.impl.UCaseProps;
import android.icu.impl.UCharacterProperty;
import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;

public final class CharacterPropertiesImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int NUM_INCLUSIONS = 40;
    private static final UnicodeSet[] inclusions = new UnicodeSet[40];

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void clear() {
        synchronized (CharacterPropertiesImpl.class) {
            int n = 0;
            while (n < inclusions.length) {
                CharacterPropertiesImpl.inclusions[n] = null;
                ++n;
            }
            return;
        }
    }

    public static UnicodeSet getInclusionsForProperty(int n) {
        synchronized (CharacterPropertiesImpl.class) {
            if (4096 <= n && n < 4121) {
                UnicodeSet unicodeSet = CharacterPropertiesImpl.getIntPropInclusions(n);
                return unicodeSet;
            }
            UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForSource(UCharacterProperty.INSTANCE.getSource(n));
            return unicodeSet;
            finally {
            }
        }
    }

    private static UnicodeSet getInclusionsForSource(int n) {
        if (inclusions[n] == null) {
            Object object = new UnicodeSet();
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getInclusions(unknown src ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(")");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                case 12: 
                case 13: 
                case 14: {
                    UCharacterProperty.INSTANCE.ulayout_addPropertyStarts(n, (UnicodeSet)object);
                    break;
                }
                case 11: {
                    Norm2AllModes.getNFCInstance().impl.addCanonIterPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 10: {
                    Norm2AllModes.getNFKC_CFInstance().impl.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 9: {
                    Norm2AllModes.getNFKCInstance().impl.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 8: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 7: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts((UnicodeSet)object);
                    UCaseProps.INSTANCE.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 6: {
                    UCharacterProperty.INSTANCE.addPropertyStarts((UnicodeSet)object);
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 5: {
                    UBiDiProps.INSTANCE.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 4: {
                    UCaseProps.INSTANCE.addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 2: {
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts((UnicodeSet)object);
                    break;
                }
                case 1: {
                    UCharacterProperty.INSTANCE.addPropertyStarts((UnicodeSet)object);
                }
            }
            CharacterPropertiesImpl.inclusions[n] = ((UnicodeSet)object).compact();
        }
        return inclusions[n];
    }

    private static UnicodeSet getIntPropInclusions(int n) {
        Object object = inclusions;
        int n2 = n + 15 - 4096;
        if (object[n2] != null) {
            return object[n2];
        }
        object = CharacterPropertiesImpl.getInclusionsForSource(UCharacterProperty.INSTANCE.getSource(n));
        UnicodeSet unicodeSet = new UnicodeSet(0, 0);
        int n3 = ((UnicodeSet)object).getRangeCount();
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            int n5 = ((UnicodeSet)object).getRangeEnd(i);
            for (int j = object.getRangeStart((int)i); j <= n5; ++j) {
                int n6 = UCharacter.getIntPropertyValue(j, n);
                int n7 = n4;
                if (n6 != n4) {
                    unicodeSet.add(j);
                    n7 = n6;
                }
                n4 = n7;
            }
        }
        object = inclusions;
        unicodeSet = unicodeSet.compact();
        object[n2] = unicodeSet;
        return unicodeSet;
    }
}

