/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.GenericInflater;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
class PreferenceInflater
extends GenericInflater<Preference, PreferenceGroup> {
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String INTENT_TAG_NAME = "intent";
    private static final String TAG = "PreferenceInflater";
    private PreferenceManager mPreferenceManager;

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        super(context);
        this.init(preferenceManager);
    }

    PreferenceInflater(GenericInflater<Preference, PreferenceGroup> genericInflater, PreferenceManager preferenceManager, Context context) {
        super(genericInflater, context);
        this.init(preferenceManager);
    }

    private void init(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.setDefaultPackage("android.preference.");
    }

    @Override
    public GenericInflater<Preference, PreferenceGroup> cloneInContext(Context context) {
        return new PreferenceInflater(this, this.mPreferenceManager, context);
    }

    @Override
    protected boolean onCreateCustomFromTag(XmlPullParser object, Preference object2, AttributeSet attributeSet) throws XmlPullParserException {
        String string2 = object.getName();
        if (string2.equals(INTENT_TAG_NAME)) {
            try {
                object = Intent.parseIntent(this.getContext().getResources(), object, attributeSet);
                if (object != null) {
                    object2.setIntent((Intent)object);
                }
                return true;
            }
            catch (IOException iOException) {
                object = new XmlPullParserException("Error parsing preference");
                object.initCause((Throwable)iOException);
                throw object;
            }
        }
        if (string2.equals(EXTRA_TAG_NAME)) {
            this.getContext().getResources().parseBundleExtra(EXTRA_TAG_NAME, attributeSet, object2.getExtras());
            try {
                XmlUtils.skipCurrentTag(object);
                return true;
            }
            catch (IOException iOException) {
                object2 = new XmlPullParserException("Error parsing preference");
                object2.initCause((Throwable)iOException);
                throw object2;
            }
        }
        return false;
    }

    @Override
    protected PreferenceGroup onMergeRoots(PreferenceGroup preferenceGroup, boolean bl, PreferenceGroup preferenceGroup2) {
        if (preferenceGroup == null) {
            preferenceGroup2.onAttachedToHierarchy(this.mPreferenceManager);
            return preferenceGroup2;
        }
        return preferenceGroup;
    }
}

