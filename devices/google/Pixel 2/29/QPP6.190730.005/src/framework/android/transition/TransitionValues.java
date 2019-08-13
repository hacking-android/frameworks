/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.transition.Transition;
import android.util.ArrayMap;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class TransitionValues {
    final ArrayList<Transition> targetedTransitions = new ArrayList();
    public final Map<String, Object> values = new ArrayMap<String, Object>();
    public View view;

    @Deprecated
    public TransitionValues() {
    }

    public TransitionValues(View view) {
        this.view = view;
    }

    public boolean equals(Object object) {
        return object instanceof TransitionValues && this.view == ((TransitionValues)object).view && this.values.equals(((TransitionValues)object).values);
    }

    public int hashCode() {
        return this.view.hashCode() * 31 + this.values.hashCode();
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("TransitionValues@");
        charSequence.append(Integer.toHexString(this.hashCode()));
        charSequence.append(":\n");
        charSequence = charSequence.toString();
        Object object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("    view = ");
        ((StringBuilder)object).append(this.view);
        ((StringBuilder)object).append("\n");
        charSequence = ((StringBuilder)object).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("    values:");
        charSequence = ((StringBuilder)object).toString();
        for (String string2 : this.values.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("    ");
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append(this.values.get(string2));
            stringBuilder.append("\n");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }
}

