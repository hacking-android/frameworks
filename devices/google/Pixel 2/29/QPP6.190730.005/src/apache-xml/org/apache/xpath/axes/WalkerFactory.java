/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.PrintStream;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.axes.AttributeIterator;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.ChildIterator;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.axes.DescendantIterator;
import org.apache.xpath.axes.FilterExprWalker;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.MatchPatternIterator;
import org.apache.xpath.axes.OneStepIterator;
import org.apache.xpath.axes.OneStepIteratorForward;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.ReverseAxesWalker;
import org.apache.xpath.axes.SelfIteratorNoPredicate;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.axes.WalkingIteratorSorted;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.patterns.ContextMatchStepPattern;
import org.apache.xpath.patterns.FunctionPattern;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;

public class WalkerFactory {
    public static final int BITMASK_TRAVERSES_OUTSIDE_SUBTREE = 234381312;
    public static final int BITS_COUNT = 255;
    public static final int BITS_RESERVED = 3840;
    public static final int BIT_ANCESTOR = 8192;
    public static final int BIT_ANCESTOR_OR_SELF = 16384;
    public static final int BIT_ANY_DESCENDANT_FROM_ROOT = 536870912;
    public static final int BIT_ATTRIBUTE = 32768;
    public static final int BIT_BACKWARDS_SELF = 268435456;
    public static final int BIT_CHILD = 65536;
    public static final int BIT_DESCENDANT = 131072;
    public static final int BIT_DESCENDANT_OR_SELF = 262144;
    public static final int BIT_FILTER = 67108864;
    public static final int BIT_FOLLOWING = 524288;
    public static final int BIT_FOLLOWING_SIBLING = 1048576;
    public static final int BIT_MATCH_PATTERN = Integer.MIN_VALUE;
    public static final int BIT_NAMESPACE = 2097152;
    public static final int BIT_NODETEST_ANY = 1073741824;
    public static final int BIT_PARENT = 4194304;
    public static final int BIT_PRECEDING = 8388608;
    public static final int BIT_PRECEDING_SIBLING = 16777216;
    public static final int BIT_PREDICATE = 4096;
    public static final int BIT_ROOT = 134217728;
    public static final int BIT_SELF = 33554432;
    static final boolean DEBUG_ITERATOR_CREATION = false;
    static final boolean DEBUG_PATTERN_CREATION = false;
    static final boolean DEBUG_WALKER_CREATION = false;

    private static int analyze(Compiler compiler, int n, int n2) throws TransformerException {
        int n3;
        int n4 = 0;
        n2 = 0;
        int n5 = n;
        n = n2;
        do {
            int n6 = compiler.getOp(n5);
            n3 = n4++;
            n2 = n;
            if (-1 == n6) break;
            n2 = n;
            if (WalkerFactory.analyzePredicate(compiler, n5, n6)) {
                n2 = n | 4096;
            }
            block0 : switch (n6) {
                default: {
                    switch (n6) {
                        default: {
                            throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[]{Integer.toString(n6)}));
                        }
                        case 53: {
                            n = n2 | -2143289344;
                            break block0;
                        }
                        case 52: {
                            n = n2 | -2147475456;
                            break block0;
                        }
                        case 51: {
                            n = n2 | -2147450880;
                            break block0;
                        }
                        case 50: {
                            n = n2 | 134217728;
                            break block0;
                        }
                        case 49: {
                            n = n2 | 2097152;
                            break block0;
                        }
                        case 48: {
                            n = n2 | 33554432;
                            break block0;
                        }
                        case 47: {
                            n = n2 | 16777216;
                            break block0;
                        }
                        case 46: {
                            n = n2 | 8388608;
                            break block0;
                        }
                        case 45: {
                            n = n2 | 4194304;
                            break block0;
                        }
                        case 44: {
                            n = n2 | 1048576;
                            break block0;
                        }
                        case 43: {
                            n = n2 | 524288;
                            break block0;
                        }
                        case 42: {
                            n = n2;
                            if (2 == n4) {
                                n = n2;
                                if (134217728 == n2) {
                                    n = n2 | 536870912;
                                }
                            }
                            n |= 262144;
                            break block0;
                        }
                        case 41: {
                            n = n2 | 131072;
                            break block0;
                        }
                        case 40: {
                            n = n2 | 65536;
                            break block0;
                        }
                        case 39: {
                            n = n2 | 32768;
                            break block0;
                        }
                        case 38: {
                            n = n2 | 16384;
                            break block0;
                        }
                        case 37: 
                    }
                    n = n2 | 8192;
                    break;
                }
                case 22: 
                case 23: 
                case 24: 
                case 25: {
                    n = n2 | 67108864;
                }
            }
            n2 = n;
            if (1033 == compiler.getOp(n5 + 3)) {
                n2 = n | 1073741824;
            }
            if ((n5 = compiler.getNextStepPos(n5)) < 0) {
                n3 = n4;
                break;
            }
            n = n2;
        } while (true);
        return n2 | n3 & 255;
    }

    static boolean analyzePredicate(Compiler compiler, int n, int n2) throws TransformerException {
        switch (n2) {
            default: {
                compiler.getArgLengthOfStep(n);
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                compiler.getArgLength(n);
            }
        }
        boolean bl = compiler.countPredicates(compiler.getFirstPredicateOpPos(n)) > 0;
        return bl;
    }

    public static boolean canCrissCross(int n) {
        if (WalkerFactory.walksSelfOnly(n)) {
            return false;
        }
        if (WalkerFactory.walksDownOnly(n) && !WalkerFactory.canSkipSubtrees(n)) {
            return false;
        }
        if (WalkerFactory.walksChildrenAndExtraAndSelfOnly(n)) {
            return false;
        }
        if (WalkerFactory.walksDescendantsAndExtraAndSelfOnly(n)) {
            return false;
        }
        if (WalkerFactory.walksUpOnly(n)) {
            return false;
        }
        if (WalkerFactory.walksExtraNodesOnly(n)) {
            return false;
        }
        return WalkerFactory.walksSubtree(n) && (WalkerFactory.walksSideways(n) || WalkerFactory.walksUp(n) || WalkerFactory.canSkipSubtrees(n));
    }

    public static boolean canSkipSubtrees(int n) {
        return WalkerFactory.isSet(n, 65536) | WalkerFactory.walksSideways(n);
    }

    private static StepPattern createDefaultStepPattern(Compiler compiler, int n, MatchPatternIterator expression, int n2, StepPattern expression2, StepPattern stepPattern) throws TransformerException {
        int n3;
        n2 = compiler.getOp(n);
        compiler.getWhatToShow(n);
        expression = null;
        block0 : switch (n2) {
            default: {
                switch (n2) {
                    default: {
                        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[]{Integer.toString(n2)}));
                    }
                    case 50: {
                        expression = new StepPattern(1280, 19, 19);
                        n2 = 19;
                        n3 = 19;
                        break block0;
                    }
                    case 49: {
                        n2 = 10;
                        n3 = 9;
                        break block0;
                    }
                    case 48: {
                        n2 = 13;
                        n3 = 13;
                        break block0;
                    }
                    case 47: {
                        n2 = 7;
                        n3 = 12;
                        break block0;
                    }
                    case 46: {
                        n2 = 6;
                        n3 = 11;
                        break block0;
                    }
                    case 45: {
                        n2 = 3;
                        n3 = 10;
                        break block0;
                    }
                    case 44: {
                        n2 = 12;
                        n3 = 7;
                        break block0;
                    }
                    case 43: {
                        n2 = 11;
                        n3 = 6;
                        break block0;
                    }
                    case 42: {
                        n2 = 1;
                        n3 = 5;
                        break block0;
                    }
                    case 41: {
                        n2 = 0;
                        n3 = 4;
                        break block0;
                    }
                    case 40: {
                        n2 = 10;
                        n3 = 3;
                        break block0;
                    }
                    case 39: {
                        n2 = 10;
                        n3 = 2;
                        break block0;
                    }
                    case 38: {
                        n2 = 5;
                        n3 = 1;
                        break block0;
                    }
                    case 37: 
                }
                n2 = 4;
                n3 = 0;
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                switch (n2) {
                    default: {
                        expression = compiler.compile(n + 2);
                        break;
                    }
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: {
                        expression = compiler.compile(n);
                    }
                }
                expression = new FunctionPattern(expression, 20, 20);
                n2 = 20;
                n3 = 20;
            }
        }
        expression2 = expression;
        if (expression == null) {
            expression2 = new StepPattern(compiler.getWhatToShow(n), compiler.getStepNS(n), compiler.getStepLocalName(n), n2, n3);
        }
        ((StepPattern)expression2).setPredicates(compiler.getCompiledPredicates(compiler.getFirstPredicateOpPos(n)));
        return expression2;
    }

    private static AxesWalker createDefaultWalker(Compiler compiler, int n, WalkingIterator predicatedNodeTest, int n2) {
        int n3 = compiler.getOp(n);
        n2 = 0;
        block0 : switch (n3) {
            default: {
                switch (n3) {
                    default: {
                        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[]{Integer.toString(n3)}));
                    }
                    case 50: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 19);
                        break block0;
                    }
                    case 49: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 9);
                        break block0;
                    }
                    case 48: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 13);
                        break block0;
                    }
                    case 47: {
                        predicatedNodeTest = new ReverseAxesWalker((LocPathIterator)predicatedNodeTest, 12);
                        break block0;
                    }
                    case 46: {
                        predicatedNodeTest = new ReverseAxesWalker((LocPathIterator)predicatedNodeTest, 11);
                        break block0;
                    }
                    case 45: {
                        predicatedNodeTest = new ReverseAxesWalker((LocPathIterator)predicatedNodeTest, 10);
                        break block0;
                    }
                    case 44: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 7);
                        break block0;
                    }
                    case 43: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 6);
                        break block0;
                    }
                    case 42: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 5);
                        break block0;
                    }
                    case 41: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 4);
                        break block0;
                    }
                    case 40: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 3);
                        break block0;
                    }
                    case 39: {
                        predicatedNodeTest = new AxesWalker((LocPathIterator)predicatedNodeTest, 2);
                        break block0;
                    }
                    case 38: {
                        predicatedNodeTest = new ReverseAxesWalker((LocPathIterator)predicatedNodeTest, 1);
                        break block0;
                    }
                    case 37: 
                }
                predicatedNodeTest = new ReverseAxesWalker((LocPathIterator)predicatedNodeTest, 0);
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                predicatedNodeTest = new FilterExprWalker((WalkingIterator)predicatedNodeTest);
                n2 = 1;
            }
        }
        if (n2 != 0) {
            predicatedNodeTest.initNodeTest(-1);
        } else {
            n2 = compiler.getWhatToShow(n);
            if ((n2 & 4163) != 0 && n2 != -1) {
                predicatedNodeTest.initNodeTest(n2, compiler.getStepNS(n), compiler.getStepLocalName(n));
            } else {
                predicatedNodeTest.initNodeTest(n2);
            }
        }
        return predicatedNodeTest;
    }

    public static void diagnoseIterator(String string, int n, Compiler compiler) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(compiler.toString());
        stringBuilder.append(", ");
        stringBuilder.append(string);
        stringBuilder.append(", ");
        stringBuilder.append(Integer.toBinaryString(n));
        stringBuilder.append(", ");
        stringBuilder.append(WalkerFactory.getAnalysisString(n));
        printStream.println(stringBuilder.toString());
    }

    static boolean functionProximateOrContainsProximate(Compiler compiler, int n) {
        int n2 = compiler.getOp(n + 1);
        int n3 = OpMap.getFirstChildPos(n);
        int n4 = compiler.getOp(n3);
        if (n4 != 1 && n4 != 2) {
            n4 = 0;
            ++n3;
            while (n3 < n2 + n - 1) {
                int n5 = n3 + 2;
                compiler.getOp(n5);
                if (WalkerFactory.isProximateInnerExpr(compiler, n5)) {
                    return true;
                }
                n3 = compiler.getNextOpPos(n3);
                ++n4;
            }
            return false;
        }
        return true;
    }

    public static int getAnalysisBitFromAxes(int n) {
        switch (n) {
            default: {
                return 67108864;
            }
            case 20: {
                return 67108864;
            }
            case 19: {
                return 134217728;
            }
            case 16: 
            case 17: 
            case 18: {
                return 536870912;
            }
            case 14: {
                return 262144;
            }
            case 13: {
                return 33554432;
            }
            case 12: {
                return 16777216;
            }
            case 11: {
                return 8388608;
            }
            case 10: {
                return 4194304;
            }
            case 8: 
            case 9: {
                return 2097152;
            }
            case 7: {
                return 1048576;
            }
            case 6: {
                return 524288;
            }
            case 5: {
                return 262144;
            }
            case 4: {
                return 131072;
            }
            case 3: {
                return 65536;
            }
            case 2: {
                return 32768;
            }
            case 1: {
                return 16384;
            }
            case 0: 
        }
        return 8192;
    }

    public static String getAnalysisString(int n) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("count: ");
        stringBuilder.append(WalkerFactory.getStepCount(n));
        stringBuilder.append(" ");
        stringBuffer.append(stringBuilder.toString());
        if ((1073741824 & n) != 0) {
            stringBuffer.append("NTANY|");
        }
        if ((n & 4096) != 0) {
            stringBuffer.append("PRED|");
        }
        if ((n & 8192) != 0) {
            stringBuffer.append("ANC|");
        }
        if ((n & 16384) != 0) {
            stringBuffer.append("ANCOS|");
        }
        if ((32768 & n) != 0) {
            stringBuffer.append("ATTR|");
        }
        if ((65536 & n) != 0) {
            stringBuffer.append("CH|");
        }
        if ((131072 & n) != 0) {
            stringBuffer.append("DESC|");
        }
        if ((262144 & n) != 0) {
            stringBuffer.append("DESCOS|");
        }
        if ((524288 & n) != 0) {
            stringBuffer.append("FOL|");
        }
        if ((1048576 & n) != 0) {
            stringBuffer.append("FOLS|");
        }
        if ((2097152 & n) != 0) {
            stringBuffer.append("NS|");
        }
        if ((4194304 & n) != 0) {
            stringBuffer.append("P|");
        }
        if ((8388608 & n) != 0) {
            stringBuffer.append("PREC|");
        }
        if ((16777216 & n) != 0) {
            stringBuffer.append("PRECS|");
        }
        if ((33554432 & n) != 0) {
            stringBuffer.append(".|");
        }
        if ((67108864 & n) != 0) {
            stringBuffer.append("FLT|");
        }
        if ((134217728 & n) != 0) {
            stringBuffer.append("R|");
        }
        return stringBuffer.toString();
    }

    public static int getAxisFromStep(Compiler compiler, int n) throws TransformerException {
        n = compiler.getOp(n);
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[]{Integer.toString(n)}));
                    }
                    case 50: {
                        return 19;
                    }
                    case 49: {
                        return 9;
                    }
                    case 48: {
                        return 13;
                    }
                    case 47: {
                        return 12;
                    }
                    case 46: {
                        return 11;
                    }
                    case 45: {
                        return 10;
                    }
                    case 44: {
                        return 7;
                    }
                    case 43: {
                        return 6;
                    }
                    case 42: {
                        return 5;
                    }
                    case 41: {
                        return 4;
                    }
                    case 40: {
                        return 3;
                    }
                    case 39: {
                        return 2;
                    }
                    case 38: {
                        return 1;
                    }
                    case 37: 
                }
                return 0;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
        }
        return 20;
    }

    public static int getStepCount(int n) {
        return n & 255;
    }

    public static boolean hasPredicate(int n) {
        boolean bl = (n & 4096) != 0;
        return bl;
    }

    public static boolean isAbsolute(int n) {
        return WalkerFactory.isSet(n, 201326592);
    }

    public static boolean isDownwardAxisOfMany(int n) {
        boolean bl = 5 == n || 4 == n || 6 == n || 11 == n;
        return bl;
    }

    public static boolean isNaturalDocOrder(int n) {
        if (!(WalkerFactory.canCrissCross(n) || WalkerFactory.isSet(n, 2097152) || WalkerFactory.walksFilteredList(n))) {
            return WalkerFactory.walksInDocOrder(n);
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    private static boolean isNaturalDocOrder(Compiler var0, int var1_1, int var2_2, int var3_3) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[CASE]], but top level block is 3[SWITCH]
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

    public static boolean isOneStep(int n) {
        boolean bl = true;
        if ((n & 255) != 1) {
            bl = false;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isOptimizableForDescendantIterator(Compiler var0, int var1_1, int var2_2) throws TransformerException {
        var3_3 = 0;
        var4_4 = 0;
        var5_5 = false;
        var6_6 = 0;
        var2_2 = 1033;
        var7_7 = var1_1;
        var1_1 = var6_6;
        block11 : while (-1 != (var6_6 = var0.getOp(var7_7))) {
            if (var2_2 != 1033 && var2_2 != 35) {
                return false;
            }
            if (++var3_3 > 3) {
                return false;
            }
            if (WalkerFactory.mightBeProximate(var0, var7_7, var6_6)) {
                return false;
            }
            switch (var6_6) {
                default: {
                    var2_2 = var1_1;
                    switch (var6_6) {
                        default: {
                            throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[]{Integer.toString(var6_6)}));
                        }
                        case 50: {
                            var6_6 = var4_4;
                            var8_8 = var5_5;
                            var2_2 = var1_1;
                            if (1 != var3_3) {
                                return false;
                            }
                            ** GOTO lbl54
                        }
                        case 48: {
                            if (1 != var3_3) {
                                return false;
                            }
                            var8_8 = true;
                            var6_6 = var4_4;
                            var2_2 = var1_1;
                            ** GOTO lbl54
                        }
                        case 42: {
                            var2_2 = 1;
                        }
                        case 41: {
                            if (3 == var3_3) {
                                return false;
                            }
                            var6_6 = 1;
                            var8_8 = var5_5;
                            ** GOTO lbl54
                        }
                        case 40: {
                            var6_6 = var4_4;
                            var8_8 = var5_5;
                            var2_2 = var1_1;
                            if (var1_1 == 0) {
                                if (var4_4 == 0) return false;
                                var6_6 = var4_4;
                                var8_8 = var5_5;
                                var2_2 = var1_1;
                                if (!var5_5) {
                                    return false;
                                }
                            }
lbl54: // 7 sources:
                            var9_9 = var0.getStepTestType(var7_7);
                            var1_1 = var0.getNextStepPos(var7_7);
                            if (var1_1 < 0) return true;
                            if (-1 != var0.getOp(var1_1) && var0.countPredicates(var7_7) > 0) {
                                return false;
                            }
                            var7_7 = var1_1;
                            var4_4 = var6_6;
                            var5_5 = var8_8;
                            var1_1 = var2_2;
                            var2_2 = var9_9;
                            continue block11;
                        }
                        case 37: 
                        case 38: 
                        case 39: 
                        case 43: 
                        case 44: 
                        case 45: 
                        case 46: 
                        case 47: 
                        case 49: 
                        case 51: 
                        case 52: 
                        case 53: 
                    }
                }
                case 22: 
                case 23: 
                case 24: 
                case 25: {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Exception decompiling
     */
    static boolean isProximateInnerExpr(Compiler var0, int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    public static boolean isSet(int n, int n2) {
        boolean bl = (n & n2) != 0;
        return bl;
    }

    public static boolean isWild(int n) {
        boolean bl = (1073741824 & n) != 0;
        return bl;
    }

    static AxesWalker loadOneWalker(WalkingIterator walkingIterator, Compiler compiler, int n) throws TransformerException {
        AxesWalker axesWalker = null;
        int n2 = compiler.getOp(n);
        if (n2 != -1) {
            axesWalker = WalkerFactory.createDefaultWalker(compiler, n2, walkingIterator, 0);
            axesWalker.init(compiler, n, n2);
        }
        return axesWalker;
    }

    static StepPattern loadSteps(MatchPatternIterator object, Compiler object2, int n, int n2) throws TransformerException {
        StepPattern stepPattern;
        int n3 = WalkerFactory.analyze((Compiler)object2, n, n2);
        StepPattern stepPattern2 = null;
        Expression expression = null;
        StepPattern stepPattern3 = null;
        do {
            stepPattern = stepPattern2;
            if (-1 == ((OpMap)object2).getOp(n)) break;
            stepPattern = WalkerFactory.createDefaultStepPattern((Compiler)object2, n, (MatchPatternIterator)object, n3, expression, stepPattern3);
            if (expression == null) {
                expression = stepPattern;
            } else {
                stepPattern.setRelativePathPattern(stepPattern3);
            }
            stepPattern3 = stepPattern;
            n = n2 = ((OpMap)object2).getNextStepPos(n);
            stepPattern2 = stepPattern;
        } while (n2 >= 0);
        n = 13;
        object2 = stepPattern;
        object = stepPattern;
        while (object != null) {
            n3 = ((StepPattern)object).getAxis();
            ((StepPattern)object).setAxis(n);
            int n4 = ((NodeTest)object).getWhatToShow();
            if (n4 != 2 && n4 != 4096) {
                object2 = object;
            } else {
                n2 = n4 == 2 ? 2 : 9;
                if (WalkerFactory.isDownwardAxisOfMany(n)) {
                    object2 = new StepPattern(n4, ((NodeTest)object).getNamespace(), ((NodeTest)object).getLocalName(), n2, 0);
                    expression = ((NodeTest)object).getStaticScore();
                    ((NodeTest)object).setNamespace(null);
                    ((NodeTest)object).setLocalName("*");
                    ((StepPattern)object2).setPredicates(((StepPattern)object).getPredicates());
                    ((StepPattern)object).setPredicates(null);
                    ((NodeTest)object).setWhatToShow(1);
                    stepPattern2 = ((StepPattern)object).getRelativePathPattern();
                    ((StepPattern)object).setRelativePathPattern((StepPattern)object2);
                    ((StepPattern)object2).setRelativePathPattern(stepPattern2);
                    ((NodeTest)object2).setStaticScore((XNumber)expression);
                    if (11 == ((StepPattern)object).getAxis()) {
                        ((StepPattern)object).setAxis(15);
                    } else if (4 == ((StepPattern)object).getAxis()) {
                        ((StepPattern)object).setAxis(5);
                    }
                } else {
                    object2 = object;
                    if (3 == ((StepPattern)object).getAxis()) {
                        ((StepPattern)object).setAxis(2);
                        object2 = object;
                    }
                }
            }
            n = n3;
            object = object2;
            stepPattern2 = ((StepPattern)object2).getRelativePathPattern();
            object2 = object;
            object = stepPattern2;
        }
        if (n < 16) {
            stepPattern2 = new ContextMatchStepPattern(n, 13);
            object = ((NodeTest)object2).getStaticScore();
            ((StepPattern)object2).setRelativePathPattern(stepPattern2);
            ((NodeTest)object2).setStaticScore((XNumber)object);
            stepPattern2.setStaticScore((XNumber)object);
        }
        return stepPattern;
    }

    static AxesWalker loadWalkers(WalkingIterator walkingIterator, Compiler compiler, int n, int n2) throws TransformerException {
        AxesWalker axesWalker;
        AxesWalker axesWalker2 = null;
        AxesWalker axesWalker3 = null;
        int n3 = WalkerFactory.analyze(compiler, n, n2);
        do {
            n2 = compiler.getOp(n);
            axesWalker = axesWalker2;
            if (-1 == n2) break;
            AxesWalker axesWalker4 = WalkerFactory.createDefaultWalker(compiler, n, walkingIterator, n3);
            axesWalker4.init(compiler, n, n2);
            axesWalker4.exprSetParent(walkingIterator);
            if (axesWalker2 == null) {
                axesWalker = axesWalker4;
            } else {
                axesWalker3.setNextWalker(axesWalker4);
                axesWalker4.setPrevWalker(axesWalker3);
                axesWalker = axesWalker2;
            }
            axesWalker3 = axesWalker4;
            n2 = compiler.getNextStepPos(n);
            axesWalker2 = axesWalker;
            n = n2;
        } while (n2 >= 0);
        return axesWalker;
    }

    public static boolean mightBeProximate(Compiler compiler, int n, int n2) throws TransformerException {
        switch (n2) {
            default: {
                compiler.getArgLengthOfStep(n);
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                compiler.getArgLength(n);
            }
        }
        n = compiler.getFirstPredicateOpPos(n);
        n2 = 0;
        while (29 == compiler.getOp(n)) {
            block11 : {
                block12 : {
                    block14 : {
                        int n3;
                        block13 : {
                            ++n2;
                            n3 = n + 2;
                            int n4 = compiler.getOp(n3);
                            if (n4 == 19) break block11;
                            if (n4 == 22) break block12;
                            if (n4 == 25) break block13;
                            if (n4 == 27) break block11;
                            if (n4 != 28) {
                                switch (n4) {
                                    default: {
                                        return true;
                                    }
                                    case 5: 
                                    case 6: 
                                    case 7: 
                                    case 8: 
                                    case 9: 
                                }
                                n4 = OpMap.getFirstChildPos(n3);
                                n3 = compiler.getNextOpPos(n4);
                                if (WalkerFactory.isProximateInnerExpr(compiler, n4)) {
                                    return true;
                                }
                                if (WalkerFactory.isProximateInnerExpr(compiler, n3)) {
                                    return true;
                                }
                            }
                            break block14;
                        }
                        if (WalkerFactory.functionProximateOrContainsProximate(compiler, n3)) {
                            return true;
                        }
                    }
                    n = compiler.getNextOpPos(n);
                    continue;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    public static DTMIterator newDTMIterator(Compiler object, int n, boolean bl) throws TransformerException {
        int n2 = OpMap.getFirstChildPos(n);
        int n3 = WalkerFactory.analyze((Compiler)object, n2, 0);
        boolean bl2 = WalkerFactory.isOneStep(n3);
        object = bl2 && WalkerFactory.walksSelfOnly(n3) && WalkerFactory.isWild(n3) && !WalkerFactory.hasPredicate(n3) ? new SelfIteratorNoPredicate((Compiler)object, n, n3) : (WalkerFactory.walksChildrenOnly(n3) && bl2 ? (WalkerFactory.isWild(n3) && !WalkerFactory.hasPredicate(n3) ? new ChildIterator((Compiler)object, n, n3) : new ChildTestIterator((Compiler)object, n, n3)) : (bl2 && WalkerFactory.walksAttributes(n3) ? new AttributeIterator((Compiler)object, n, n3) : (bl2 && !WalkerFactory.walksFilteredList(n3) ? (!WalkerFactory.walksNamespaces(n3) && (WalkerFactory.walksInDocOrder(n3) || WalkerFactory.isSet(n3, 4194304)) ? new OneStepIteratorForward((Compiler)object, n, n3) : new OneStepIterator((Compiler)object, n, n3)) : (WalkerFactory.isOptimizableForDescendantIterator((Compiler)object, n2, 0) ? new DescendantIterator((Compiler)object, n, n3) : (WalkerFactory.isNaturalDocOrder((Compiler)object, n2, 0, n3) ? new WalkingIterator((Compiler)object, n, n3, true) : new WalkingIteratorSorted((Compiler)object, n, n3, true))))));
        if (object instanceof LocPathIterator) {
            ((LocPathIterator)object).setIsTopLevel(bl);
        }
        return object;
    }

    public static boolean walksAncestors(int n) {
        return WalkerFactory.isSet(n, 24576);
    }

    public static boolean walksAttributes(int n) {
        boolean bl = (32768 & n) != 0;
        return bl;
    }

    public static boolean walksChildren(int n) {
        boolean bl = (65536 & n) != 0;
        return bl;
    }

    public static boolean walksChildrenAndExtraAndSelfOnly(int n) {
        boolean bl = WalkerFactory.walksChildren(n) && !WalkerFactory.walksDescendants(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && (!WalkerFactory.isAbsolute(n) || WalkerFactory.isSet(n, 134217728));
        return bl;
    }

    public static boolean walksChildrenOnly(int n) {
        boolean bl = WalkerFactory.walksChildren(n) && !WalkerFactory.isSet(n, 33554432) && !WalkerFactory.walksExtraNodes(n) && !WalkerFactory.walksDescendants(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && (!WalkerFactory.isAbsolute(n) || WalkerFactory.isSet(n, 134217728));
        return bl;
    }

    public static boolean walksDescendants(int n) {
        return WalkerFactory.isSet(n, 393216);
    }

    public static boolean walksDescendantsAndExtraAndSelfOnly(int n) {
        boolean bl = !WalkerFactory.walksChildren(n) && WalkerFactory.walksDescendants(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && (!WalkerFactory.isAbsolute(n) || WalkerFactory.isSet(n, 134217728));
        return bl;
    }

    public static boolean walksDownExtraOnly(int n) {
        boolean bl = WalkerFactory.walksSubtree(n) && WalkerFactory.walksExtraNodes(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }

    public static boolean walksDownOnly(int n) {
        boolean bl = WalkerFactory.walksSubtree(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }

    public static boolean walksExtraNodes(int n) {
        return WalkerFactory.isSet(n, 2129920);
    }

    public static boolean walksExtraNodesOnly(int n) {
        boolean bl = WalkerFactory.walksExtraNodes(n) && !WalkerFactory.isSet(n, 33554432) && !WalkerFactory.walksSubtree(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }

    public static boolean walksFilteredList(int n) {
        return WalkerFactory.isSet(n, 67108864);
    }

    public static boolean walksFollowingOnlyMaybeAbsolute(int n) {
        boolean bl = WalkerFactory.isSet(n, 35127296) && !WalkerFactory.walksSubtree(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n);
        return bl;
    }

    public static boolean walksInDocOrder(int n) {
        boolean bl = (WalkerFactory.walksSubtreeOnlyMaybeAbsolute(n) || WalkerFactory.walksExtraNodesOnly(n) || WalkerFactory.walksFollowingOnlyMaybeAbsolute(n)) && !WalkerFactory.isSet(n, 67108864);
        return bl;
    }

    public static boolean walksNamespaces(int n) {
        boolean bl = (2097152 & n) != 0;
        return bl;
    }

    public static boolean walksSelfOnly(int n) {
        boolean bl = WalkerFactory.isSet(n, 33554432) && !WalkerFactory.walksSubtree(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }

    public static boolean walksSideways(int n) {
        return WalkerFactory.isSet(n, 26738688);
    }

    public static boolean walksSubtree(int n) {
        return WalkerFactory.isSet(n, 458752);
    }

    public static boolean walksSubtreeOnly(int n) {
        boolean bl = WalkerFactory.walksSubtreeOnlyMaybeAbsolute(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }

    public static boolean walksSubtreeOnlyFromRootOrContext(int n) {
        boolean bl = WalkerFactory.walksSubtree(n) && !WalkerFactory.walksExtraNodes(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isSet(n, 67108864);
        return bl;
    }

    public static boolean walksSubtreeOnlyMaybeAbsolute(int n) {
        boolean bl = WalkerFactory.walksSubtree(n) && !WalkerFactory.walksExtraNodes(n) && !WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n);
        return bl;
    }

    public static boolean walksUp(int n) {
        return WalkerFactory.isSet(n, 4218880);
    }

    public static boolean walksUpOnly(int n) {
        boolean bl = !WalkerFactory.walksSubtree(n) && WalkerFactory.walksUp(n) && !WalkerFactory.walksSideways(n) && !WalkerFactory.isAbsolute(n);
        return bl;
    }
}

