/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.KeyframeSet;
import android.animation.Keyframes;
import android.animation.ObjectAnimator;
import android.animation.PathKeyframes;
import android.animation.PropertyValuesHolder;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PathParser;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflater {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int SEQUENTIALLY = 1;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;
    private static final TypedValue sTmpTypedValue = new TypedValue();

    private static Animator createAnimatorFromXml(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return AnimatorInflater.createAnimatorFromXml(resources, theme, xmlPullParser, Xml.asAttributeSet(xmlPullParser), null, 0, f);
    }

    private static Animator createAnimatorFromXml(Resources object, Resources.Theme object2, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int n, float f) throws XmlPullParserException, IOException {
        int n2;
        Object object3 = null;
        int n3 = xmlPullParser.getDepth();
        Object object4 = null;
        while ((n2 = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n3) {
            block16 : {
                PropertyValuesHolder[] arrpropertyValuesHolder;
                block13 : {
                    block15 : {
                        block14 : {
                            block12 : {
                                if (n2 == 1) break;
                                if (n2 != 2) continue;
                                arrpropertyValuesHolder = xmlPullParser.getName();
                                n2 = 0;
                                if (!arrpropertyValuesHolder.equals("objectAnimator")) break block12;
                                object3 = AnimatorInflater.loadObjectAnimator((Resources)object, (Resources.Theme)object2, attributeSet, f);
                                break block13;
                            }
                            if (!arrpropertyValuesHolder.equals("animator")) break block14;
                            object3 = AnimatorInflater.loadAnimator((Resources)object, (Resources.Theme)object2, attributeSet, null, f);
                            break block13;
                        }
                        if (!arrpropertyValuesHolder.equals("set")) break block15;
                        arrpropertyValuesHolder = new AnimatorSet();
                        object3 = object2 != null ? ((Resources.Theme)object2).obtainStyledAttributes(attributeSet, R.styleable.AnimatorSet, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.AnimatorSet);
                        arrpropertyValuesHolder.appendChangingConfigurations(object3.getChangingConfigurations());
                        int n4 = object3.getInt(0, 0);
                        AnimatorInflater.createAnimatorFromXml((Resources)object, (Resources.Theme)object2, xmlPullParser, attributeSet, (AnimatorSet)arrpropertyValuesHolder, n4, f);
                        object3.recycle();
                        object3 = arrpropertyValuesHolder;
                        break block13;
                    }
                    if (!arrpropertyValuesHolder.equals("propertyValuesHolder")) break block16;
                    arrpropertyValuesHolder = AnimatorInflater.loadValues((Resources)object, (Resources.Theme)object2, xmlPullParser, Xml.asAttributeSet(xmlPullParser));
                    if (arrpropertyValuesHolder != null && object3 != null && object3 instanceof ValueAnimator) {
                        ((ValueAnimator)object3).setValues(arrpropertyValuesHolder);
                    }
                    n2 = 1;
                }
                arrpropertyValuesHolder = object4;
                if (animatorSet != null) {
                    arrpropertyValuesHolder = object4;
                    if (n2 == 0) {
                        arrpropertyValuesHolder = object4;
                        if (object4 == null) {
                            arrpropertyValuesHolder = new ArrayList();
                        }
                        arrpropertyValuesHolder.add(object3);
                    }
                }
                object4 = arrpropertyValuesHolder;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown animator name: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (animatorSet != null && object4 != null) {
            object = new Animator[((ArrayList)object4).size()];
            n2 = 0;
            object2 = ((ArrayList)object4).iterator();
            while (object2.hasNext()) {
                object[n2] = (Animator)object2.next();
                ++n2;
            }
            if (n == 0) {
                animatorSet.playTogether((Animator[])object);
            } else {
                animatorSet.playSequentially((Animator[])object);
            }
        }
        return object3;
    }

    private static Keyframe createNewKeyframe(Keyframe keyframe, float f) {
        keyframe = keyframe.getType() == Float.TYPE ? Keyframe.ofFloat(f) : (keyframe.getType() == Integer.TYPE ? Keyframe.ofInt(f) : Keyframe.ofObject(f));
        return keyframe;
    }

    private static StateListAnimator createStateListAnimatorFromXml(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws IOException, XmlPullParserException {
        int n;
        StateListAnimator stateListAnimator = new StateListAnimator();
        while ((n = xmlPullParser.next()) != 1) {
            if (n != 2) {
                if (n == 3) break;
                continue;
            }
            Animator animator2 = null;
            if (!"item".equals(xmlPullParser.getName())) continue;
            int n2 = xmlPullParser.getAttributeCount();
            int[] arrn = new int[n2];
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                int n4 = attributeSet.getAttributeNameResource(n);
                if (n4 == 16843213) {
                    animator2 = AnimatorInflater.loadAnimator(context, attributeSet.getAttributeResourceValue(n, 0));
                    continue;
                }
                if (!attributeSet.getAttributeBooleanValue(n, false)) {
                    n4 = -n4;
                }
                arrn[n3] = n4;
                ++n3;
            }
            Animator animator3 = animator2;
            if (animator2 == null) {
                animator3 = AnimatorInflater.createAnimatorFromXml(context.getResources(), context.getTheme(), xmlPullParser, 1.0f);
            }
            if (animator3 != null) {
                stateListAnimator.addState(StateSet.trimStateSet(arrn, n3), animator3);
                continue;
            }
            throw new Resources.NotFoundException("animation state item must have a valid animation");
        }
        return stateListAnimator;
    }

    private static void distributeKeyframes(Keyframe[] arrkeyframe, float f, int n, int n2) {
        f /= (float)(n2 - n + 2);
        while (n <= n2) {
            arrkeyframe[n].setFraction(arrkeyframe[n - 1].getFraction() + f);
            ++n;
        }
    }

    private static void dumpKeyframes(Object[] arrobject, String object) {
        if (arrobject != null && arrobject.length != 0) {
            Log.d(TAG, (String)object);
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                Keyframe keyframe = (Keyframe)arrobject[i];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Keyframe ");
                stringBuilder.append(i);
                stringBuilder.append(": fraction ");
                float f = keyframe.getFraction();
                String string2 = "null";
                object = f < 0.0f ? "null" : Float.valueOf(keyframe.getFraction());
                stringBuilder.append(object);
                stringBuilder.append(", , value : ");
                object = string2;
                if (keyframe.hasValue()) {
                    object = keyframe.getValue();
                }
                stringBuilder.append(object);
                Log.d(TAG, stringBuilder.toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int getChangingConfigs(Resources resources, int n) {
        TypedValue typedValue = sTmpTypedValue;
        synchronized (typedValue) {
            resources.getValue(n, sTmpTypedValue, true);
            return AnimatorInflater.sTmpTypedValue.changingConfigurations;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static PropertyValuesHolder getPVH(TypedArray object, int n, int n2, int n3, String object2) {
        Object object3 = ((TypedArray)object).peekValue(n2);
        boolean bl = object3 != null;
        int n4 = bl ? ((TypedValue)object3).type : 0;
        object3 = ((TypedArray)object).peekValue(n3);
        boolean bl2 = object3 != null;
        int n5 = bl2 ? ((TypedValue)object3).type : 0;
        if (n == 4) {
            n = bl && AnimatorInflater.isColorType(n4) || bl2 && AnimatorInflater.isColorType(n5) ? 3 : 0;
        }
        boolean bl3 = n == 0;
        if (n == 2) {
            String string2 = ((TypedArray)object).getString(n2);
            String string3 = ((TypedArray)object).getString(n3);
            object = string2 == null ? null : new PathParser.PathData(string2);
            object3 = string3 == null ? null : new PathParser.PathData(string3);
            if (object == null) {
                if (object3 == null) return null;
            }
            if (object != null) {
                PathDataEvaluator pathDataEvaluator = new PathDataEvaluator();
                if (object3 != null) {
                    if (!PathParser.canMorph((PathParser.PathData)object, (PathParser.PathData)object3)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(" Can't morph from ");
                        ((StringBuilder)object).append(string2);
                        ((StringBuilder)object).append(" to ");
                        ((StringBuilder)object).append(string3);
                        throw new InflateException(((StringBuilder)object).toString());
                    }
                    object = PropertyValuesHolder.ofObject((String)object2, (TypeEvaluator)pathDataEvaluator, object, object3);
                    return object;
                } else {
                    object = PropertyValuesHolder.ofObject((String)object2, (TypeEvaluator)pathDataEvaluator, object);
                }
                return object;
            } else {
                if (object3 == null) return null;
                object = PropertyValuesHolder.ofObject((String)object2, (TypeEvaluator)new PathDataEvaluator(), object3);
            }
            return object;
        }
        object3 = null;
        if (n == 3) {
            object3 = ArgbEvaluator.getInstance();
        }
        if (bl3) {
            if (bl) {
                float f = n4 == 5 ? ((TypedArray)object).getDimension(n2, 0.0f) : ((TypedArray)object).getFloat(n2, 0.0f);
                if (bl2) {
                    float f2 = n5 == 5 ? ((TypedArray)object).getDimension(n3, 0.0f) : ((TypedArray)object).getFloat(n3, 0.0f);
                    object = PropertyValuesHolder.ofFloat((String)object2, f, f2);
                } else {
                    object = PropertyValuesHolder.ofFloat((String)object2, f);
                }
            } else {
                float f = n5 == 5 ? ((TypedArray)object).getDimension(n3, 0.0f) : ((TypedArray)object).getFloat(n3, 0.0f);
                object = PropertyValuesHolder.ofFloat((String)object2, f);
            }
        } else if (bl) {
            n = n4 == 5 ? (int)((TypedArray)object).getDimension(n2, 0.0f) : (AnimatorInflater.isColorType(n4) ? ((TypedArray)object).getColor(n2, 0) : ((TypedArray)object).getInt(n2, 0));
            if (bl2) {
                n2 = n5 == 5 ? (int)((TypedArray)object).getDimension(n3, 0.0f) : (AnimatorInflater.isColorType(n5) ? ((TypedArray)object).getColor(n3, 0) : ((TypedArray)object).getInt(n3, 0));
                object = PropertyValuesHolder.ofInt((String)object2, n, n2);
            } else {
                object = PropertyValuesHolder.ofInt((String)object2, n);
            }
        } else if (bl2) {
            n = n5 == 5 ? (int)((TypedArray)object).getDimension(n3, 0.0f) : (AnimatorInflater.isColorType(n5) ? ((TypedArray)object).getColor(n3, 0) : ((TypedArray)object).getInt(n3, 0));
            object = PropertyValuesHolder.ofInt((String)object2, n);
        } else {
            object = null;
        }
        object2 = object;
        if (object == null) return object2;
        object2 = object;
        if (object3 == null) return object2;
        ((PropertyValuesHolder)object).setEvaluator((TypeEvaluator)object3);
        return object;
    }

    private static int inferValueTypeFromValues(TypedArray object, int n, int n2) {
        TypedValue typedValue = ((TypedArray)object).peekValue(n);
        int n3 = 1;
        int n4 = 0;
        n = typedValue != null ? 1 : 0;
        int n5 = n != 0 ? typedValue.type : 0;
        object = ((TypedArray)object).peekValue(n2);
        n2 = object != null ? n3 : 0;
        if (n2 != 0) {
            n4 = ((TypedValue)object).type;
        }
        n = n != 0 && AnimatorInflater.isColorType(n5) || n2 != 0 && AnimatorInflater.isColorType(n4) ? 3 : 0;
        return n;
    }

    private static int inferValueTypeOfKeyframe(Resources object, Resources.Theme object2, AttributeSet attributeSet) {
        int n = 0;
        object = object2 != null ? ((Resources.Theme)object2).obtainStyledAttributes(attributeSet, R.styleable.Keyframe, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.Keyframe);
        object2 = ((TypedArray)object).peekValue(0);
        if (object2 != null) {
            n = 1;
        }
        n = n != 0 && AnimatorInflater.isColorType(((TypedValue)object2).type) ? 3 : 0;
        ((TypedArray)object).recycle();
        return n;
    }

    private static boolean isColorType(int n) {
        boolean bl = n >= 28 && n <= 31;
        return bl;
    }

    public static Animator loadAnimator(Context context, int n) throws Resources.NotFoundException {
        return AnimatorInflater.loadAnimator(context.getResources(), context.getTheme(), n);
    }

    public static Animator loadAnimator(Resources resources, Resources.Theme theme, int n) throws Resources.NotFoundException {
        return AnimatorInflater.loadAnimator(resources, theme, n, 1.0f);
    }

    /*
     * Exception decompiling
     */
    public static Animator loadAnimator(Resources var0, Resources.Theme var1_3, int var2_5, float var3_6) throws Resources.NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static ValueAnimator loadAnimator(Resources object, Resources.Theme theme, AttributeSet object2, ValueAnimator valueAnimator, float f) throws Resources.NotFoundException {
        Object object3 = null;
        TypedArray typedArray = theme != null ? theme.obtainStyledAttributes((AttributeSet)object2, R.styleable.Animator, 0, 0) : ((Resources)object).obtainAttributes((AttributeSet)object2, R.styleable.Animator);
        if (valueAnimator != null) {
            object2 = theme != null ? theme.obtainStyledAttributes((AttributeSet)object2, R.styleable.PropertyAnimator, 0, 0) : ((Resources)object).obtainAttributes((AttributeSet)object2, R.styleable.PropertyAnimator);
            valueAnimator.appendChangingConfigurations(((TypedArray)object2).getChangingConfigurations());
            object3 = object2;
        }
        object2 = valueAnimator;
        if (valueAnimator == null) {
            object2 = new ValueAnimator();
        }
        ((Animator)object2).appendChangingConfigurations(typedArray.getChangingConfigurations());
        AnimatorInflater.parseAnimatorFromTypeArray((ValueAnimator)object2, typedArray, (TypedArray)object3, f);
        int n = typedArray.getResourceId(0, 0);
        if (n > 0) {
            if ((object = AnimationUtils.loadInterpolator((Resources)object, theme, n)) instanceof BaseInterpolator) {
                ((Animator)object2).appendChangingConfigurations(((BaseInterpolator)object).getChangingConfiguration());
            }
            ((ValueAnimator)object2).setInterpolator((TimeInterpolator)object);
        }
        typedArray.recycle();
        if (object3 != null) {
            ((TypedArray)object3).recycle();
        }
        return object2;
    }

    private static Keyframe loadKeyframe(Resources resources, Resources.Theme theme, AttributeSet object, int n) throws XmlPullParserException, IOException {
        TypedArray typedArray = theme != null ? theme.obtainStyledAttributes((AttributeSet)object, R.styleable.Keyframe, 0, 0) : resources.obtainAttributes((AttributeSet)object, R.styleable.Keyframe);
        object = null;
        float f = typedArray.getFloat(3, -1.0f);
        TypedValue typedValue = typedArray.peekValue(0);
        boolean bl = typedValue != null;
        int n2 = n;
        if (n == 4) {
            n2 = bl && AnimatorInflater.isColorType(typedValue.type) ? 3 : 0;
        }
        if (bl) {
            if (n2 != 0) {
                if (n2 == 1 || n2 == 3) {
                    object = Keyframe.ofInt(f, typedArray.getInt(0, 0));
                }
            } else {
                object = Keyframe.ofFloat(f, typedArray.getFloat(0, 0.0f));
            }
        } else {
            object = n2 == 0 ? Keyframe.ofFloat(f) : Keyframe.ofInt(f);
        }
        n = typedArray.getResourceId(1, 0);
        if (n > 0) {
            ((Keyframe)object).setInterpolator(AnimationUtils.loadInterpolator(resources, theme, n));
        }
        typedArray.recycle();
        return object;
    }

    private static ObjectAnimator loadObjectAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        AnimatorInflater.loadAnimator(resources, theme, attributeSet, objectAnimator, f);
        return objectAnimator;
    }

    private static PropertyValuesHolder loadPvh(Resources arrkeyframe, Resources.Theme object, XmlPullParser xmlPullParser, String string2, int n) throws XmlPullParserException, IOException {
        int n2;
        Object var5_5 = null;
        ArrayList<Keyframe> arrayList = null;
        while ((n2 = xmlPullParser.next()) != 3 && n2 != 1) {
            ArrayList<Keyframe> arrayList2 = arrayList;
            n2 = n;
            if (xmlPullParser.getName().equals("keyframe")) {
                n2 = n;
                if (n == 4) {
                    n2 = AnimatorInflater.inferValueTypeOfKeyframe((Resources)arrkeyframe, (Resources.Theme)object, Xml.asAttributeSet(xmlPullParser));
                }
                Keyframe keyframe = AnimatorInflater.loadKeyframe((Resources)arrkeyframe, (Resources.Theme)object, Xml.asAttributeSet(xmlPullParser), n2);
                arrayList2 = arrayList;
                if (keyframe != null) {
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList<Keyframe>();
                    }
                    arrayList2.add(keyframe);
                }
                xmlPullParser.next();
            }
            arrayList = arrayList2;
            n = n2;
        }
        if (arrayList != null) {
            int n3 = n2 = arrayList.size();
            if (n2 > 0) {
                arrkeyframe = (Keyframe)arrayList.get(0);
                object = (Keyframe)arrayList.get(n3 - 1);
                float f = ((Keyframe)object).getFraction();
                float f2 = 0.0f;
                n2 = n3;
                if (f < 1.0f) {
                    if (f < 0.0f) {
                        ((Keyframe)object).setFraction(1.0f);
                        n2 = n3;
                    } else {
                        arrayList.add(arrayList.size(), AnimatorInflater.createNewKeyframe((Keyframe)object, 1.0f));
                        n2 = n3 + 1;
                    }
                }
                f = arrkeyframe.getFraction();
                int n4 = n2;
                if (f != 0.0f) {
                    if (f < 0.0f) {
                        arrkeyframe.setFraction(0.0f);
                        n4 = n2;
                    } else {
                        arrayList.add(0, AnimatorInflater.createNewKeyframe((Keyframe)arrkeyframe, 0.0f));
                        n4 = n2 + 1;
                    }
                }
                arrkeyframe = new Keyframe[n4];
                arrayList.toArray(arrkeyframe);
                for (n2 = 0; n2 < n4; ++n2) {
                    object = arrkeyframe[n2];
                    if (!(((Keyframe)object).getFraction() < f2)) continue;
                    if (n2 == 0) {
                        ((Keyframe)object).setFraction(f2);
                        continue;
                    }
                    if (n2 == n4 - 1) {
                        ((Keyframe)object).setFraction(1.0f);
                        f2 = 0.0f;
                        continue;
                    }
                    int n5 = n2;
                    n3 = n2 + 1;
                    while (n3 < n4 - 1 && !(arrkeyframe[n3].getFraction() >= 0.0f)) {
                        n5 = n3++;
                    }
                    f2 = 0.0f;
                    AnimatorInflater.distributeKeyframes(arrkeyframe, arrkeyframe[n5 + 1].getFraction() - arrkeyframe[n2 - 1].getFraction(), n2, n5);
                }
                object = PropertyValuesHolder.ofKeyframe(string2, arrkeyframe);
                arrkeyframe = object;
                if (n == 3) {
                    ((PropertyValuesHolder)object).setEvaluator(ArgbEvaluator.getInstance());
                    arrkeyframe = object;
                }
            } else {
                arrkeyframe = var5_5;
            }
        } else {
            arrkeyframe = var5_5;
        }
        return arrkeyframe;
    }

    /*
     * Exception decompiling
     */
    public static StateListAnimator loadStateListAnimator(Context var0, int var1_2) throws Resources.NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static PropertyValuesHolder[] loadValues(Resources arrpropertyValuesHolder, Resources.Theme arrpropertyValuesHolder2, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        ArrayList arrayList = null;
        while ((n = xmlPullParser.getEventType()) != 3 && n != 1) {
            if (n != 2) {
                xmlPullParser.next();
                continue;
            }
            Cloneable cloneable = arrayList;
            if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                TypedArray typedArray = arrpropertyValuesHolder2 != null ? arrpropertyValuesHolder2.obtainStyledAttributes(attributeSet, R.styleable.PropertyValuesHolder, 0, 0) : arrpropertyValuesHolder.obtainAttributes(attributeSet, R.styleable.PropertyValuesHolder);
                String string2 = typedArray.getString(3);
                n = typedArray.getInt(2, 4);
                cloneable = AnimatorInflater.loadPvh((Resources)arrpropertyValuesHolder, (Resources.Theme)arrpropertyValuesHolder2, xmlPullParser, string2, n);
                Cloneable cloneable2 = cloneable;
                if (cloneable == null) {
                    cloneable2 = AnimatorInflater.getPVH(typedArray, n, 0, 1, string2);
                }
                cloneable = arrayList;
                if (cloneable2 != null) {
                    cloneable = arrayList;
                    if (arrayList == null) {
                        cloneable = new ArrayList();
                    }
                    ((ArrayList)cloneable).add(cloneable2);
                }
                typedArray.recycle();
            }
            xmlPullParser.next();
            arrayList = cloneable;
        }
        arrpropertyValuesHolder = null;
        if (arrayList != null) {
            int n2 = arrayList.size();
            arrpropertyValuesHolder2 = new PropertyValuesHolder[n2];
            n = 0;
            do {
                arrpropertyValuesHolder = arrpropertyValuesHolder2;
                if (n >= n2) break;
                arrpropertyValuesHolder2[n] = (PropertyValuesHolder)arrayList.get(n);
                ++n;
            } while (true);
        }
        return arrpropertyValuesHolder;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f) {
        int n;
        PropertyValuesHolder propertyValuesHolder;
        long l = typedArray.getInt(1, 300);
        long l2 = typedArray.getInt(2, 0);
        int n2 = n = typedArray.getInt(7, 4);
        if (n == 4) {
            n2 = AnimatorInflater.inferValueTypeFromValues(typedArray, 5, 6);
        }
        if ((propertyValuesHolder = AnimatorInflater.getPVH(typedArray, n2, 5, 6, "")) != null) {
            valueAnimator.setValues(propertyValuesHolder);
        }
        valueAnimator.setDuration(l);
        valueAnimator.setStartDelay(l2);
        if (typedArray.hasValue(3)) {
            valueAnimator.setRepeatCount(typedArray.getInt(3, 0));
        }
        if (typedArray.hasValue(4)) {
            valueAnimator.setRepeatMode(typedArray.getInt(4, 1));
        }
        if (typedArray2 != null) {
            AnimatorInflater.setupObjectAnimator(valueAnimator, typedArray2, n2, f);
        }
    }

    private static TypeEvaluator setupAnimatorForPath(ValueAnimator object, TypedArray object2) {
        Object var2_2 = null;
        String string2 = ((TypedArray)object2).getString(5);
        String string3 = ((TypedArray)object2).getString(6);
        PathParser.PathData pathData = string2 == null ? null : new PathParser.PathData(string2);
        PathParser.PathData pathData2 = string3 == null ? null : new PathParser.PathData(string3);
        if (pathData != null) {
            if (pathData2 != null) {
                ((ValueAnimator)object).setObjectValues(pathData, pathData2);
                if (!PathParser.canMorph(pathData, pathData2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((TypedArray)object2).getPositionDescription());
                    ((StringBuilder)object).append(" Can't morph from ");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(" to ");
                    ((StringBuilder)object).append(string3);
                    throw new InflateException(((StringBuilder)object).toString());
                }
            } else {
                ((ValueAnimator)object).setObjectValues(pathData);
            }
            object2 = new PathDataEvaluator();
        } else {
            object2 = var2_2;
            if (pathData2 != null) {
                ((ValueAnimator)object).setObjectValues(pathData2);
                object2 = new PathDataEvaluator();
            }
        }
        return object2;
    }

    private static void setupObjectAnimator(ValueAnimator object, TypedArray object2, int n, float f) {
        block15 : {
            ObjectAnimator objectAnimator;
            block12 : {
                String string2;
                Cloneable cloneable;
                String string3;
                block14 : {
                    block13 : {
                        int n2 = n;
                        objectAnimator = (ObjectAnimator)object;
                        object = ((TypedArray)object2).getString(1);
                        if (object == null) break block12;
                        string2 = ((TypedArray)object2).getString(2);
                        string3 = ((TypedArray)object2).getString(3);
                        if (n2 == 2) break block13;
                        n = n2;
                        if (n2 != 4) break block14;
                    }
                    n = 0;
                }
                if (string2 == null && string3 == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((TypedArray)object2).getPositionDescription());
                    ((StringBuilder)object).append(" propertyXName or propertyYName is needed for PathData");
                    throw new InflateException(((StringBuilder)object).toString());
                }
                object = KeyframeSet.ofPath(PathParser.createPathFromPathData((String)object), 0.5f * f);
                if (n == 0) {
                    cloneable = ((PathKeyframes)object).createXFloatKeyframes();
                    object = ((PathKeyframes)object).createYFloatKeyframes();
                } else {
                    cloneable = ((PathKeyframes)object).createXIntKeyframes();
                    object = ((PathKeyframes)object).createYIntKeyframes();
                }
                object2 = null;
                Object var9_9 = null;
                if (string2 != null) {
                    object2 = PropertyValuesHolder.ofKeyframes(string2, (Keyframes)cloneable);
                }
                cloneable = var9_9;
                if (string3 != null) {
                    cloneable = PropertyValuesHolder.ofKeyframes(string3, (Keyframes)object);
                }
                if (object2 == null) {
                    objectAnimator.setValues(new PropertyValuesHolder[]{cloneable});
                } else if (cloneable == null) {
                    objectAnimator.setValues(new PropertyValuesHolder[]{object2});
                } else {
                    objectAnimator.setValues(new PropertyValuesHolder[]{object2, cloneable});
                }
                break block15;
            }
            objectAnimator.setPropertyName(((TypedArray)object2).getString(0));
        }
    }

    private static void setupValues(ValueAnimator valueAnimator, TypedArray typedArray, boolean bl, boolean bl2, int n, boolean bl3, int n2) {
        if (bl) {
            if (bl2) {
                float f = n == 5 ? typedArray.getDimension(5, 0.0f) : typedArray.getFloat(5, 0.0f);
                if (bl3) {
                    float f2 = n2 == 5 ? typedArray.getDimension(6, 0.0f) : typedArray.getFloat(6, 0.0f);
                    valueAnimator.setFloatValues(f, f2);
                } else {
                    valueAnimator.setFloatValues(f);
                }
            } else {
                float f = n2 == 5 ? typedArray.getDimension(6, 0.0f) : typedArray.getFloat(6, 0.0f);
                valueAnimator.setFloatValues(f);
            }
        } else if (bl2) {
            n = n == 5 ? (int)typedArray.getDimension(5, 0.0f) : (AnimatorInflater.isColorType(n) ? typedArray.getColor(5, 0) : typedArray.getInt(5, 0));
            if (bl3) {
                n2 = n2 == 5 ? (int)typedArray.getDimension(6, 0.0f) : (AnimatorInflater.isColorType(n2) ? typedArray.getColor(6, 0) : typedArray.getInt(6, 0));
                valueAnimator.setIntValues(n, n2);
            } else {
                valueAnimator.setIntValues(n);
            }
        } else if (bl3) {
            n = n2 == 5 ? (int)typedArray.getDimension(6, 0.0f) : (AnimatorInflater.isColorType(n2) ? typedArray.getColor(6, 0) : typedArray.getInt(6, 0));
            valueAnimator.setIntValues(n);
        }
    }

    private static class PathDataEvaluator
    implements TypeEvaluator<PathParser.PathData> {
        private final PathParser.PathData mPathData = new PathParser.PathData();

        private PathDataEvaluator() {
        }

        @Override
        public PathParser.PathData evaluate(float f, PathParser.PathData pathData, PathParser.PathData pathData2) {
            if (PathParser.interpolatePathData(this.mPathData, pathData, pathData2, f)) {
                return this.mPathData;
            }
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

}

