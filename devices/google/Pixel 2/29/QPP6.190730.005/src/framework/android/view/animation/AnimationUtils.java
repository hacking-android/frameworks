/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view.animation;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.ClipRectAnimation;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationUtils {
    private static final int SEQUENTIALLY = 1;
    private static final int TOGETHER = 0;
    private static ThreadLocal<AnimationState> sAnimationState = new ThreadLocal<AnimationState>(){

        @Override
        protected AnimationState initialValue() {
            return new AnimationState();
        }
    };

    private static Animation createAnimationFromXml(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return AnimationUtils.createAnimationFromXml(context, xmlPullParser, null, Xml.asAttributeSet(xmlPullParser));
    }

    @UnsupportedAppUsage
    private static Animation createAnimationFromXml(Context object, XmlPullParser xmlPullParser, AnimationSet animationSet, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        Object object2 = null;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            block9 : {
                block4 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                block5 : {
                                    block3 : {
                                        if (n != 2) continue;
                                        object2 = xmlPullParser.getName();
                                        if (!((String)object2).equals("set")) break block3;
                                        object2 = new AnimationSet((Context)object, attributeSet);
                                        AnimationUtils.createAnimationFromXml((Context)object, xmlPullParser, (AnimationSet)object2, attributeSet);
                                        break block4;
                                    }
                                    if (!((String)object2).equals("alpha")) break block5;
                                    object2 = new AlphaAnimation((Context)object, attributeSet);
                                    break block4;
                                }
                                if (!((String)object2).equals("scale")) break block6;
                                object2 = new ScaleAnimation((Context)object, attributeSet);
                                break block4;
                            }
                            if (!((String)object2).equals("rotate")) break block7;
                            object2 = new RotateAnimation((Context)object, attributeSet);
                            break block4;
                        }
                        if (!((String)object2).equals("translate")) break block8;
                        object2 = new TranslateAnimation((Context)object, attributeSet);
                        break block4;
                    }
                    if (!((String)object2).equals("cliprect")) break block9;
                    object2 = new ClipRectAnimation((Context)object, attributeSet);
                }
                if (animationSet == null) continue;
                animationSet.addAnimation((Animation)object2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown animation name: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return object2;
    }

    private static Interpolator createInterpolatorFromXml(Resources object, Resources.Theme theme, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        Object object2 = null;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
            object2 = xmlPullParser.getName();
            if (((String)object2).equals("linearInterpolator")) {
                object2 = new LinearInterpolator();
                continue;
            }
            if (((String)object2).equals("accelerateInterpolator")) {
                object2 = new AccelerateInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("decelerateInterpolator")) {
                object2 = new DecelerateInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("accelerateDecelerateInterpolator")) {
                object2 = new AccelerateDecelerateInterpolator();
                continue;
            }
            if (((String)object2).equals("cycleInterpolator")) {
                object2 = new CycleInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("anticipateInterpolator")) {
                object2 = new AnticipateInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("overshootInterpolator")) {
                object2 = new OvershootInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("anticipateOvershootInterpolator")) {
                object2 = new AnticipateOvershootInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            if (((String)object2).equals("bounceInterpolator")) {
                object2 = new BounceInterpolator();
                continue;
            }
            if (((String)object2).equals("pathInterpolator")) {
                object2 = new PathInterpolator((Resources)object, theme, attributeSet);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown interpolator name: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return object2;
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return AnimationUtils.createLayoutAnimationFromXml(context, xmlPullParser, Xml.asAttributeSet(xmlPullParser));
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        Object object2 = null;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            object2 = xmlPullParser.getName();
            if ("layoutAnimation".equals(object2)) {
                object2 = new LayoutAnimationController((Context)object, attributeSet);
                continue;
            }
            if ("gridLayoutAnimation".equals(object2)) {
                object2 = new GridLayoutAnimationController((Context)object, attributeSet);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown layout animation name: ");
            ((StringBuilder)object).append((String)object2);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return object2;
    }

    public static long currentAnimationTimeMillis() {
        AnimationState animationState = sAnimationState.get();
        if (animationState.animationClockLocked) {
            return Math.max(animationState.currentVsyncTimeMillis, animationState.lastReportedTimeMillis);
        }
        animationState.lastReportedTimeMillis = SystemClock.uptimeMillis();
        return animationState.lastReportedTimeMillis;
    }

    /*
     * Exception decompiling
     */
    public static Animation loadAnimation(Context var0, int var1_3) throws Resources.NotFoundException {
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
    public static Interpolator loadInterpolator(Context var0, int var1_2) throws Resources.NotFoundException {
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
    public static Interpolator loadInterpolator(Resources var0, Resources.Theme var1_2, int var2_4) throws Resources.NotFoundException {
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
    public static LayoutAnimationController loadLayoutAnimation(Context var0, int var1_3) throws Resources.NotFoundException {
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

    public static void lockAnimationClock(long l) {
        AnimationState animationState = sAnimationState.get();
        animationState.animationClockLocked = true;
        animationState.currentVsyncTimeMillis = l;
    }

    public static Animation makeInAnimation(Context object, boolean bl) {
        object = bl ? AnimationUtils.loadAnimation((Context)object, 17432578) : AnimationUtils.loadAnimation((Context)object, 17432879);
        ((Animation)object).setInterpolator(new DecelerateInterpolator());
        ((Animation)object).setStartTime(AnimationUtils.currentAnimationTimeMillis());
        return object;
    }

    public static Animation makeInChildBottomAnimation(Context object) {
        object = AnimationUtils.loadAnimation((Context)object, 17432876);
        ((Animation)object).setInterpolator(new AccelerateInterpolator());
        ((Animation)object).setStartTime(AnimationUtils.currentAnimationTimeMillis());
        return object;
    }

    public static Animation makeOutAnimation(Context object, boolean bl) {
        object = bl ? AnimationUtils.loadAnimation((Context)object, 17432579) : AnimationUtils.loadAnimation((Context)object, 17432882);
        ((Animation)object).setInterpolator(new AccelerateInterpolator());
        ((Animation)object).setStartTime(AnimationUtils.currentAnimationTimeMillis());
        return object;
    }

    public static void unlockAnimationClock() {
        AnimationUtils.sAnimationState.get().animationClockLocked = false;
    }

    private static class AnimationState {
        boolean animationClockLocked;
        long currentVsyncTimeMillis;
        long lastReportedTimeMillis;

        private AnimationState() {
        }
    }

}

