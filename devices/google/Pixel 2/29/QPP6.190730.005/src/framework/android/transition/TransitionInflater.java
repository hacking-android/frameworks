/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.transition.ArcMotion;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.PathMotion;
import android.transition.PatternPathMotion;
import android.transition.Recolor;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.ViewGroup;
import com.android.internal.R;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor> sConstructors = new ArrayMap();
    private Context mContext;

    private TransitionInflater(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Object createCustom(AttributeSet object, Class class_, String constructor) {
        block10 : {
            Class class_2;
            String string2 = object.getAttributeValue(null, "class");
            if (string2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)((Object)constructor));
                ((StringBuilder)object).append(" tag must have a 'class' attribute");
                throw new InflateException(((StringBuilder)object).toString());
            }
            try {
                ArrayMap<String, Constructor> arrayMap = sConstructors;
                // MONITORENTER : arrayMap
                Constructor constructor2 = sConstructors.get(string2);
                constructor = constructor2;
                if (constructor2 != null) break block10;
                class_2 = this.mContext.getClassLoader().loadClass(string2).asSubclass(class_);
                constructor = constructor2;
                if (class_2 == null) break block10;
            }
            catch (IllegalAccessException illegalAccessException) {
                constructor = new StringBuilder();
                ((StringBuilder)((Object)constructor)).append("Could not instantiate ");
                ((StringBuilder)((Object)constructor)).append(class_);
                ((StringBuilder)((Object)constructor)).append(" class ");
                ((StringBuilder)((Object)constructor)).append(string2);
                throw new InflateException(((StringBuilder)((Object)constructor)).toString(), illegalAccessException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not instantiate ");
                ((StringBuilder)object).append(class_);
                ((StringBuilder)object).append(" class ");
                ((StringBuilder)object).append(string2);
                throw new InflateException(((StringBuilder)object).toString(), noSuchMethodException);
            }
            catch (InvocationTargetException invocationTargetException) {
                constructor = new StringBuilder();
                ((StringBuilder)((Object)constructor)).append("Could not instantiate ");
                ((StringBuilder)((Object)constructor)).append(class_);
                ((StringBuilder)((Object)constructor)).append(" class ");
                ((StringBuilder)((Object)constructor)).append(string2);
                throw new InflateException(((StringBuilder)((Object)constructor)).toString(), invocationTargetException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                constructor = new StringBuilder();
                ((StringBuilder)((Object)constructor)).append("Could not instantiate ");
                ((StringBuilder)((Object)constructor)).append(class_);
                ((StringBuilder)((Object)constructor)).append(" class ");
                ((StringBuilder)((Object)constructor)).append(string2);
                throw new InflateException(((StringBuilder)((Object)constructor)).toString(), classNotFoundException);
            }
            catch (InstantiationException instantiationException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not instantiate ");
                ((StringBuilder)object).append(class_);
                ((StringBuilder)object).append(" class ");
                ((StringBuilder)object).append(string2);
                throw new InflateException(((StringBuilder)object).toString(), instantiationException);
            }
            constructor = class_2.getConstructor(sConstructorSignature);
            constructor.setAccessible(true);
            sConstructors.put(string2, constructor);
        }
        object = constructor.newInstance(this.mContext, object);
        // MONITOREXIT : arrayMap
        return object;
    }

    private Transition createTransitionFromXml(XmlPullParser xmlPullParser, AttributeSet object, Transition transition2) throws XmlPullParserException, IOException {
        int n;
        Object object2 = null;
        int n2 = xmlPullParser.getDepth();
        TransitionSet transitionSet = transition2 instanceof TransitionSet ? (TransitionSet)transition2 : null;
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            block26 : {
                Object object3;
                block11 : {
                    block25 : {
                        block24 : {
                            block23 : {
                                block22 : {
                                    block21 : {
                                        block20 : {
                                            block19 : {
                                                block18 : {
                                                    block17 : {
                                                        block16 : {
                                                            block15 : {
                                                                block14 : {
                                                                    block13 : {
                                                                        block12 : {
                                                                            block10 : {
                                                                                if (n != 2) continue;
                                                                                object3 = xmlPullParser.getName();
                                                                                if (!"fade".equals(object3)) break block10;
                                                                                object2 = new Fade(this.mContext, (AttributeSet)object);
                                                                                break block11;
                                                                            }
                                                                            if (!"changeBounds".equals(object3)) break block12;
                                                                            object2 = new ChangeBounds(this.mContext, (AttributeSet)object);
                                                                            break block11;
                                                                        }
                                                                        if (!"slide".equals(object3)) break block13;
                                                                        object2 = new Slide(this.mContext, (AttributeSet)object);
                                                                        break block11;
                                                                    }
                                                                    if (!"explode".equals(object3)) break block14;
                                                                    object2 = new Explode(this.mContext, (AttributeSet)object);
                                                                    break block11;
                                                                }
                                                                if (!"changeImageTransform".equals(object3)) break block15;
                                                                object2 = new ChangeImageTransform(this.mContext, (AttributeSet)object);
                                                                break block11;
                                                            }
                                                            if (!"changeTransform".equals(object3)) break block16;
                                                            object2 = new ChangeTransform(this.mContext, (AttributeSet)object);
                                                            break block11;
                                                        }
                                                        if (!"changeClipBounds".equals(object3)) break block17;
                                                        object2 = new ChangeClipBounds(this.mContext, (AttributeSet)object);
                                                        break block11;
                                                    }
                                                    if (!"autoTransition".equals(object3)) break block18;
                                                    object2 = new AutoTransition(this.mContext, (AttributeSet)object);
                                                    break block11;
                                                }
                                                if (!"recolor".equals(object3)) break block19;
                                                object2 = new Recolor(this.mContext, (AttributeSet)object);
                                                break block11;
                                            }
                                            if (!"changeScroll".equals(object3)) break block20;
                                            object2 = new ChangeScroll(this.mContext, (AttributeSet)object);
                                            break block11;
                                        }
                                        if (!"transitionSet".equals(object3)) break block21;
                                        object2 = new TransitionSet(this.mContext, (AttributeSet)object);
                                        break block11;
                                    }
                                    if (!"transition".equals(object3)) break block22;
                                    object2 = (Transition)this.createCustom((AttributeSet)object, Transition.class, "transition");
                                    break block11;
                                }
                                if (!"targets".equals(object3)) break block23;
                                this.getTargetIds(xmlPullParser, (AttributeSet)object, transition2);
                                break block11;
                            }
                            if (!"arcMotion".equals(object3)) break block24;
                            transition2.setPathMotion(new ArcMotion(this.mContext, (AttributeSet)object));
                            break block11;
                        }
                        if (!"pathMotion".equals(object3)) break block25;
                        transition2.setPathMotion((PathMotion)this.createCustom((AttributeSet)object, PathMotion.class, "pathMotion"));
                        break block11;
                    }
                    if (!"patternPathMotion".equals(object3)) break block26;
                    transition2.setPathMotion(new PatternPathMotion(this.mContext, (AttributeSet)object));
                }
                object3 = object2;
                if (object2 != null) {
                    if (!xmlPullParser.isEmptyElementTag()) {
                        this.createTransitionFromXml(xmlPullParser, (AttributeSet)object, (Transition)object2);
                    }
                    if (transitionSet != null) {
                        transitionSet.addTransition((Transition)object2);
                        object3 = null;
                    } else if (transition2 == null) {
                        object3 = object2;
                    } else {
                        throw new InflateException("Could not add transition to another transition.");
                    }
                }
                object2 = object3;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown scene name: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return object2;
    }

    private TransitionManager createTransitionManagerFromXml(XmlPullParser xmlPullParser, AttributeSet object, ViewGroup viewGroup) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        TransitionManager transitionManager = null;
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            String string2 = xmlPullParser.getName();
            if (string2.equals("transitionManager")) {
                transitionManager = new TransitionManager();
                continue;
            }
            if (string2.equals("transition") && transitionManager != null) {
                this.loadTransition((AttributeSet)object, viewGroup, transitionManager);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown scene name: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return transitionManager;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void getTargetIds(XmlPullParser xmlPullParser, AttributeSet object, Transition transition2) throws XmlPullParserException, IOException {
        n2 = xmlPullParser.getDepth();
        do {
            block5 : {
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                if ((n = xmlPullParser.next()) == 3) {
                                    if (xmlPullParser.getDepth() <= n2) return;
                                }
                                if (n == 1) return;
                                if (n != 2) continue;
                                if (!xmlPullParser.getName().equals("target")) {
                                    object = new StringBuilder();
                                    object.append("Unknown scene name: ");
                                    object.append(xmlPullParser.getName());
                                    throw new RuntimeException(object.toString());
                                }
                                typedArray = this.mContext.obtainStyledAttributes((AttributeSet)object, R.styleable.TransitionTarget);
                                n = typedArray.getResourceId(1, 0);
                                if (n == 0) break block6;
                                transition2.addTarget(n);
                                break block5;
                            }
                            n = typedArray.getResourceId(2, 0);
                            if (n == 0) break block7;
                            transition2.excludeTarget(n, true);
                            break block5;
                        }
                        string2 = typedArray.getString(4);
                        if (string2 == null) break block8;
                        transition2.addTarget(string2);
                        break block5;
                    }
                    string2 = typedArray.getString(5);
                    if (string2 == null) break block9;
                    transition2.excludeTarget(string2, true);
                    break block5;
                }
                string4 = typedArray.getString(3);
                if (string4 == null) ** GOTO lbl46
                string2 = string4;
                transition2.excludeTarget(Class.forName(string4), true);
                break block5;
lbl46: // 1 sources:
                string2 = string4;
                string4 = string3 = typedArray.getString(0);
                if (string3 == null) break block5;
                string2 = string4;
                transition2.addTarget(Class.forName(string4));
            }
            typedArray.recycle();
        } while (true);
        catch (ClassNotFoundException classNotFoundException) {
            typedArray.recycle();
            object = new StringBuilder();
            object.append("Could not create ");
            object.append(string2);
            throw new RuntimeException(object.toString(), classNotFoundException);
        }
    }

    private void loadTransition(AttributeSet object, ViewGroup object2, TransitionManager transitionManager) throws Resources.NotFoundException {
        TypedArray typedArray = this.mContext.obtainStyledAttributes((AttributeSet)object, R.styleable.TransitionManager);
        int n = typedArray.getResourceId(2, -1);
        int n2 = typedArray.getResourceId(0, -1);
        Transition transition2 = null;
        object = n2 < 0 ? null : Scene.getSceneForLayout((ViewGroup)object2, n2, this.mContext);
        n2 = typedArray.getResourceId(1, -1);
        object2 = n2 < 0 ? transition2 : Scene.getSceneForLayout((ViewGroup)object2, n2, this.mContext);
        if (n >= 0 && (transition2 = this.inflateTransition(n)) != null) {
            if (object2 != null) {
                if (object == null) {
                    transitionManager.setTransition((Scene)object2, transition2);
                } else {
                    transitionManager.setTransition((Scene)object, (Scene)object2, transition2);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("No toScene for transition ID ");
                ((StringBuilder)object).append(n);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
        }
        typedArray.recycle();
    }

    /*
     * Exception decompiling
     */
    public Transition inflateTransition(int var1_1) {
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

    /*
     * Exception decompiling
     */
    public TransitionManager inflateTransitionManager(int var1_1, ViewGroup var2_2) {
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
}

