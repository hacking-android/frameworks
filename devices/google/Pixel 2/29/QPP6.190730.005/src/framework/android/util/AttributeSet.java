/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public interface AttributeSet {
    public boolean getAttributeBooleanValue(int var1, boolean var2);

    public boolean getAttributeBooleanValue(String var1, String var2, boolean var3);

    public int getAttributeCount();

    public float getAttributeFloatValue(int var1, float var2);

    public float getAttributeFloatValue(String var1, String var2, float var3);

    public int getAttributeIntValue(int var1, int var2);

    public int getAttributeIntValue(String var1, String var2, int var3);

    public int getAttributeListValue(int var1, String[] var2, int var3);

    public int getAttributeListValue(String var1, String var2, String[] var3, int var4);

    public String getAttributeName(int var1);

    public int getAttributeNameResource(int var1);

    default public String getAttributeNamespace(int n) {
        return null;
    }

    public int getAttributeResourceValue(int var1, int var2);

    public int getAttributeResourceValue(String var1, String var2, int var3);

    public int getAttributeUnsignedIntValue(int var1, int var2);

    public int getAttributeUnsignedIntValue(String var1, String var2, int var3);

    public String getAttributeValue(int var1);

    public String getAttributeValue(String var1, String var2);

    public String getClassAttribute();

    public String getIdAttribute();

    public int getIdAttributeResourceValue(int var1);

    public String getPositionDescription();

    public int getStyleAttribute();
}

