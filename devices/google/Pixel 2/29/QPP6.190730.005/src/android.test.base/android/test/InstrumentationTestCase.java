/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Instrumentation
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.test.FlakyTest;
import android.test.RepetitiveTest;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.TestCase;

@Deprecated
public class InstrumentationTestCase
extends TestCase {
    private Instrumentation mInstrumentation;

    private void runMethod(Method method, int n) throws Throwable {
        this.runMethod(method, n, false);
    }

    /*
     * Exception decompiling
     */
    private void runMethod(Method var1_1, int var2_3, boolean var3_4) throws Throwable {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    @Deprecated
    public void injectInsrumentation(Instrumentation instrumentation) {
        this.injectInstrumentation(instrumentation);
    }

    public void injectInstrumentation(Instrumentation instrumentation) {
        this.mInstrumentation = instrumentation;
    }

    public final <T extends Activity> T launchActivity(String string, Class<T> class_, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.MAIN");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return this.launchActivityWithIntent(string, class_, intent);
    }

    public final <T extends Activity> T launchActivityWithIntent(String string, Class<T> class_, Intent intent) {
        intent.setClassName(string, class_.getName());
        intent.addFlags(268435456);
        string = this.getInstrumentation().startActivitySync(intent);
        this.getInstrumentation().waitForIdleSync();
        return (T)string;
    }

    @Override
    protected void runTest() throws Throwable {
        Throwable[] arrthrowable;
        String string = this.getName();
        InstrumentationTestCase.assertNotNull(string);
        Throwable[] arrthrowable2 = null;
        try {
            arrthrowable2 = arrthrowable = this.getClass().getMethod(string, null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            arrthrowable = new StringBuilder();
            arrthrowable.append("Method \"");
            arrthrowable.append(string);
            arrthrowable.append("\" not found");
            InstrumentationTestCase.fail(arrthrowable.toString());
        }
        if (!Modifier.isPublic(arrthrowable2.getModifiers())) {
            arrthrowable = new StringBuilder();
            arrthrowable.append("Method \"");
            arrthrowable.append(string);
            arrthrowable.append("\" should be public");
            InstrumentationTestCase.fail(arrthrowable.toString());
        }
        int n = 1;
        boolean bl = false;
        if (arrthrowable2.isAnnotationPresent(FlakyTest.class)) {
            n = arrthrowable2.getAnnotation(FlakyTest.class).tolerance();
        } else if (arrthrowable2.isAnnotationPresent(RepetitiveTest.class)) {
            n = arrthrowable2.getAnnotation(RepetitiveTest.class).numIterations();
            bl = true;
        }
        if (arrthrowable2.isAnnotationPresent(UiThreadTest.class)) {
            arrthrowable = new Throwable[1];
            this.getInstrumentation().runOnMainSync(new Runnable((Method)arrthrowable2, n, bl, arrthrowable){
                final /* synthetic */ Throwable[] val$exceptions;
                final /* synthetic */ boolean val$repetitive;
                final /* synthetic */ Method val$testMethod;
                final /* synthetic */ int val$tolerance;
                {
                    this.val$testMethod = method;
                    this.val$tolerance = n;
                    this.val$repetitive = bl;
                    this.val$exceptions = arrthrowable;
                }

                @Override
                public void run() {
                    try {
                        InstrumentationTestCase.this.runMethod(this.val$testMethod, this.val$tolerance, this.val$repetitive);
                    }
                    catch (Throwable throwable) {
                        this.val$exceptions[0] = throwable;
                    }
                }
            });
            if (arrthrowable[0] != null) {
                throw arrthrowable[0];
            }
        } else {
            this.runMethod((Method)arrthrowable2, n, bl);
        }
    }

    public void runTestOnUiThread(final Runnable runnable) throws Throwable {
        final Throwable[] arrthrowable = new Throwable[1];
        this.getInstrumentation().runOnMainSync(new Runnable(){

            @Override
            public void run() {
                try {
                    runnable.run();
                }
                catch (Throwable throwable) {
                    arrthrowable[0] = throwable;
                }
            }
        });
        if (arrthrowable[0] == null) {
            return;
        }
        throw arrthrowable[0];
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void sendKeys(String charSequence) {
        String[] arrstring = ((String)charSequence).split(" ");
        int n = arrstring.length;
        Instrumentation instrumentation = this.getInstrumentation();
        int i = 0;
        do {
            block12 : {
                CharSequence charSequence2;
                int n3;
                int n2;
                block13 : {
                    if (i >= n) {
                        instrumentation.waitForIdleSync();
                        return;
                    }
                    charSequence2 = arrstring[i];
                    n3 = ((String)charSequence2).indexOf(42);
                    if (n3 == -1) {
                        n2 = 1;
                    } else {
                        n2 = Integer.parseInt(((String)charSequence2).substring(0, n3));
                    }
                    charSequence = charSequence2;
                    if (n3 == -1) break block13;
                    charSequence = ((String)charSequence2).substring(n3 + 1);
                    break block13;
                    catch (NumberFormatException numberFormatException) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Invalid repeat count: ");
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        Log.w((String)"ActivityTestCase", (String)((StringBuilder)charSequence).toString());
                        break block12;
                    }
                }
                for (n3 = 0; n3 < n2; ++n3) {
                    try {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("KEYCODE_");
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        int n4 = KeyEvent.class.getField(((StringBuilder)charSequence2).toString()).getInt(null);
                        try {
                            instrumentation.sendKeyDownUpSync(n4);
                        }
                        catch (SecurityException securityException) {
                            // empty catch block
                        }
                        continue;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("Unknown keycode: KEYCODE_");
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        Log.w((String)"ActivityTestCase", (String)((StringBuilder)charSequence2).toString());
                        break;
                    }
                    catch (NoSuchFieldException noSuchFieldException) {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append("Unknown keycode: KEYCODE_");
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        Log.w((String)"ActivityTestCase", (String)((StringBuilder)charSequence2).toString());
                        break;
                    }
                }
            }
            ++i;
        } while (true);
    }

    public void sendKeys(int ... arrn) {
        int n = arrn.length;
        Instrumentation instrumentation = this.getInstrumentation();
        for (int i = 0; i < n; ++i) {
            try {
                instrumentation.sendKeyDownUpSync(arrn[i]);
                continue;
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
        instrumentation.waitForIdleSync();
    }

    public void sendRepeatedKeys(int ... arrn) {
        int n = arrn.length;
        if ((n & 1) != 1) {
            Instrumentation instrumentation = this.getInstrumentation();
            for (int i = 0; i < n; i += 2) {
                int n2 = arrn[i];
                int n3 = arrn[i + 1];
                for (int j = 0; j < n2; ++j) {
                    try {
                        instrumentation.sendKeyDownUpSync(n3);
                        continue;
                    }
                    catch (SecurityException securityException) {
                        // empty catch block
                    }
                }
            }
            instrumentation.waitForIdleSync();
            return;
        }
        throw new IllegalArgumentException("The size of the keys array must be a multiple of 2");
    }

    @Override
    protected void tearDown() throws Exception {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        super.tearDown();
    }

}

