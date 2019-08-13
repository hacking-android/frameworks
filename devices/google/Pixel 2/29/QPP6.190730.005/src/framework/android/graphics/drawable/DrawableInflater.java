/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableInflater {
    private static final HashMap<String, Constructor<? extends Drawable>> CONSTRUCTOR_MAP = new HashMap();
    @UnsupportedAppUsage
    private final ClassLoader mClassLoader;
    private final Resources mRes;

    public DrawableInflater(Resources resources, ClassLoader classLoader) {
        this.mRes = resources;
        this.mClassLoader = classLoader;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Drawable inflateFromClass(String object) {
        Constructor<Drawable> constructor;
        HashMap<String, Constructor<? extends Drawable>> hashMap = CONSTRUCTOR_MAP;
        // MONITORENTER : hashMap
        Constructor<Drawable> constructor2 = constructor = CONSTRUCTOR_MAP.get(object);
        if (constructor == null) {
            constructor2 = this.mClassLoader.loadClass((String)object).asSubclass(Drawable.class).getConstructor(new Class[0]);
            CONSTRUCTOR_MAP.put((String)object, constructor2);
        }
        // MONITOREXIT : hashMap
        try {
            return (Drawable)constructor2.newInstance(new Object[0]);
        }
        catch (Exception exception) {
            constructor = new StringBuilder();
            ((StringBuilder)((Object)constructor)).append("Error inflating class ");
            ((StringBuilder)((Object)constructor)).append((String)object);
            object = new InflateException(((StringBuilder)((Object)constructor)).toString());
            ((Throwable)object).initCause(exception);
            throw object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            constructor = new StringBuilder();
            ((StringBuilder)((Object)constructor)).append("Class not found ");
            ((StringBuilder)((Object)constructor)).append((String)object);
            object = new InflateException(((StringBuilder)((Object)constructor)).toString());
            ((Throwable)object).initCause(classNotFoundException);
            throw object;
        }
        catch (ClassCastException classCastException) {
            constructor = new StringBuilder();
            ((StringBuilder)((Object)constructor)).append("Class is not a Drawable ");
            ((StringBuilder)((Object)constructor)).append((String)object);
            object = new InflateException(((StringBuilder)((Object)constructor)).toString());
            ((Throwable)object).initCause(classCastException);
            throw object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            constructor = new StringBuilder();
            ((StringBuilder)((Object)constructor)).append("Error inflating class ");
            ((StringBuilder)((Object)constructor)).append((String)object);
            object = new InflateException(((StringBuilder)((Object)constructor)).toString());
            ((Throwable)object).initCause(noSuchMethodException);
            throw object;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Drawable inflateFromTag(String var1_1) {
        block44 : {
            switch (var1_1.hashCode()) {
                case 2118620333: {
                    if (!var1_1.equals("animated-vector")) break;
                    var2_2 = 10;
                    break block44;
                }
                case 2013827269: {
                    if (!var1_1.equals("animated-rotate")) break;
                    var2_2 = 14;
                    break block44;
                }
                case 1442046129: {
                    if (!var1_1.equals("animated-image")) break;
                    var2_2 = 19;
                    break block44;
                }
                case 1191572447: {
                    if (!var1_1.equals("selector")) break;
                    var2_2 = 0;
                    break block44;
                }
                case 160680263: {
                    if (!var1_1.equals("level-list")) break;
                    var2_2 = 2;
                    break block44;
                }
                case 109399969: {
                    if (!var1_1.equals("shape")) break;
                    var2_2 = 8;
                    break block44;
                }
                case 109250890: {
                    if (!var1_1.equals("scale")) break;
                    var2_2 = 11;
                    break block44;
                }
                case 100360477: {
                    if (!var1_1.equals("inset")) break;
                    var2_2 = 16;
                    break block44;
                }
                case 94842723: {
                    if (!var1_1.equals("color")) break;
                    var2_2 = 7;
                    break block44;
                }
                case 3056464: {
                    if (!var1_1.equals("clip")) break;
                    var2_2 = 12;
                    break block44;
                }
                case -94197862: {
                    if (!var1_1.equals("layer-list")) break;
                    var2_2 = 3;
                    break block44;
                }
                case -510364471: {
                    if (!var1_1.equals("animated-selector")) break;
                    var2_2 = 1;
                    break block44;
                }
                case -820387517: {
                    if (!var1_1.equals("vector")) break;
                    var2_2 = 9;
                    break block44;
                }
                case -925180581: {
                    if (!var1_1.equals("rotate")) break;
                    var2_2 = 13;
                    break block44;
                }
                case -930826704: {
                    if (!var1_1.equals("ripple")) break;
                    var2_2 = 5;
                    break block44;
                }
                case -1388777169: {
                    if (!var1_1.equals("bitmap")) break;
                    var2_2 = 17;
                    break block44;
                }
                case -1493546681: {
                    if (!var1_1.equals("animation-list")) break;
                    var2_2 = 15;
                    break block44;
                }
                case -1671889043: {
                    if (!var1_1.equals("nine-patch")) break;
                    var2_2 = 18;
                    break block44;
                }
                case -1724158635: {
                    if (!var1_1.equals("transition")) break;
                    var2_2 = 4;
                    break block44;
                }
                case -2024464016: {
                    if (!var1_1.equals("adaptive-icon")) break;
                    var2_2 = 6;
                    break block44;
                }
            }
            ** break;
lbl83: // 1 sources:
            var2_2 = -1;
        }
        switch (var2_2) {
            default: {
                return null;
            }
            case 19: {
                return new AnimatedImageDrawable();
            }
            case 18: {
                return new NinePatchDrawable();
            }
            case 17: {
                return new BitmapDrawable();
            }
            case 16: {
                return new InsetDrawable();
            }
            case 15: {
                return new AnimationDrawable();
            }
            case 14: {
                return new AnimatedRotateDrawable();
            }
            case 13: {
                return new RotateDrawable();
            }
            case 12: {
                return new ClipDrawable();
            }
            case 11: {
                return new ScaleDrawable();
            }
            case 10: {
                return new AnimatedVectorDrawable();
            }
            case 9: {
                return new VectorDrawable();
            }
            case 8: {
                return new GradientDrawable();
            }
            case 7: {
                return new ColorDrawable();
            }
            case 6: {
                return new AdaptiveIconDrawable();
            }
            case 5: {
                return new RippleDrawable();
            }
            case 4: {
                return new TransitionDrawable();
            }
            case 3: {
                return new LayerDrawable();
            }
            case 2: {
                return new LevelListDrawable();
            }
            case 1: {
                return new AnimatedStateListDrawable();
            }
            case 0: 
        }
        return new StateListDrawable();
    }

    public static Drawable loadDrawable(Context context, int n) {
        return DrawableInflater.loadDrawable(context.getResources(), context.getTheme(), n);
    }

    public static Drawable loadDrawable(Resources resources, Resources.Theme theme, int n) {
        return resources.getDrawable(n, theme);
    }

    public Drawable inflateFromXml(String string2, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        return this.inflateFromXmlForDensity(string2, xmlPullParser, attributeSet, 0, theme);
    }

    Drawable inflateFromXmlForDensity(String object, XmlPullParser xmlPullParser, AttributeSet attributeSet, int n, Resources.Theme theme) throws XmlPullParserException, IOException {
        String string2 = object;
        if (((String)object).equals("drawable") && (string2 = attributeSet.getAttributeValue(null, "class")) == null) {
            throw new InflateException("<drawable> tag must specify class attribute");
        }
        Drawable drawable2 = this.inflateFromTag(string2);
        object = drawable2;
        if (drawable2 == null) {
            object = this.inflateFromClass(string2);
        }
        ((Drawable)object).setSrcDensityOverride(n);
        ((Drawable)object).inflate(this.mRes, xmlPullParser, attributeSet, theme);
        return object;
    }
}

