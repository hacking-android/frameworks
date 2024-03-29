/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.transition.TransitionPropagation;
import android.transition.TransitionValues;
import android.view.View;
import java.util.Map;

public abstract class VisibilityPropagation
extends TransitionPropagation {
    private static final String PROPNAME_VIEW_CENTER = "android:visibilityPropagation:center";
    private static final String PROPNAME_VISIBILITY = "android:visibilityPropagation:visibility";
    private static final String[] VISIBILITY_PROPAGATION_VALUES = new String[]{"android:visibilityPropagation:visibility", "android:visibilityPropagation:center"};

    private static int getViewCoordinate(TransitionValues arrn, int n) {
        if (arrn == null) {
            return -1;
        }
        arrn = (int[])arrn.values.get(PROPNAME_VIEW_CENTER);
        if (arrn == null) {
            return -1;
        }
        return arrn[n];
    }

    @Override
    public void captureValues(TransitionValues transitionValues) {
        int[] arrn;
        View view = transitionValues.view;
        int[] arrn2 = arrn = (int[])transitionValues.values.get("android:visibility:visibility");
        if (arrn == null) {
            arrn2 = view.getVisibility();
        }
        transitionValues.values.put(PROPNAME_VISIBILITY, arrn2);
        arrn2 = new int[2];
        view.getLocationOnScreen(arrn2);
        arrn2[0] = arrn2[0] + Math.round(view.getTranslationX());
        arrn2[0] = arrn2[0] + view.getWidth() / 2;
        arrn2[1] = arrn2[1] + Math.round(view.getTranslationY());
        arrn2[1] = arrn2[1] + view.getHeight() / 2;
        transitionValues.values.put(PROPNAME_VIEW_CENTER, arrn2);
    }

    @Override
    public String[] getPropagationProperties() {
        return VISIBILITY_PROPAGATION_VALUES;
    }

    public int getViewVisibility(TransitionValues object) {
        if (object == null) {
            return 8;
        }
        object = (Integer)((TransitionValues)object).values.get(PROPNAME_VISIBILITY);
        if (object == null) {
            return 8;
        }
        return (Integer)object;
    }

    public int getViewX(TransitionValues transitionValues) {
        return VisibilityPropagation.getViewCoordinate(transitionValues, 0);
    }

    public int getViewY(TransitionValues transitionValues) {
        return VisibilityPropagation.getViewCoordinate(transitionValues, 1);
    }
}

