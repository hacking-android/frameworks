/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Bundle;
import android.util.ArrayMap;

public interface FieldClassificationUserData {
    public String[] getCategoryIds();

    public Bundle getDefaultFieldClassificationArgs();

    public String getFieldClassificationAlgorithm();

    public String getFieldClassificationAlgorithmForCategory(String var1);

    public ArrayMap<String, String> getFieldClassificationAlgorithms();

    public ArrayMap<String, Bundle> getFieldClassificationArgs();

    public String[] getValues();
}

