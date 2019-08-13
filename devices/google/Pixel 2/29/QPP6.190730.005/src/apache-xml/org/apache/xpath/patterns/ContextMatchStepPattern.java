/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.patterns;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;

public class ContextMatchStepPattern
extends StepPattern {
    static final long serialVersionUID = -1888092779313211942L;

    public ContextMatchStepPattern(int n, int n2) {
        super(-1, n, n2);
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        if (xPathContext.getIteratorRoot() == xPathContext.getCurrentNode()) {
            return this.getStaticScore();
        }
        return SCORE_NONE;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public XObject executeRelativePathPattern(XPathContext xPathContext, StepPattern expression) throws TransformerException {
        expression = NodeTest.SCORE_NONE;
        n = xPathContext.getCurrentNode();
        dTM = xPathContext.getDTM(n);
        if (dTM == null) {
            return expression;
        }
        xPathContext.getCurrentNode();
        n2 = this.m_axis;
        bl = WalkerFactory.isDownwardAxisOfMany(n2);
        bl2 = dTM.getNodeType(xPathContext.getIteratorRoot()) == 2;
        n3 = n2;
        if (11 == n2) {
            n3 = n2;
            if (bl2) {
                n3 = 15;
            }
        }
        dTMAxisTraverser = dTM.getAxisTraverser(n3);
        n3 = dTMAxisTraverser.first(n);
        expression2 = expression;
        block14 : do {
            if (-1 == n3) return expression2;
            try {
                xPathContext.pushCurrentNode(n3);
                expression = expression2 = this.execute(xPathContext);
                if (expression2 != NodeTest.SCORE_NONE) {
                    bl3 = this.executePredicates(xPathContext, dTM, n);
                    if (bl3) {
                        xPathContext.popCurrentNode();
                        return expression2;
                    }
                    expression = NodeTest.SCORE_NONE;
                }
                expression2 = expression;
                if (!bl) ** GOTO lbl-1000
                expression2 = expression;
                if (!bl2) ** GOTO lbl-1000
                expression2 = expression;
                if (1 == dTM.getNodeType(n3)) {
                    n4 = 2;
                    n2 = 0;
                }
                ** GOTO lbl-1000
            }
            catch (Throwable throwable) {
                // empty catch block
                break;
            }
            do {
                expression2 = expression;
                if (n2 < 2) {
                    dTMAxisTraverser2 = dTM.getAxisTraverser(n4);
                    n4 = dTMAxisTraverser2.first(n3);
                } else lbl-1000: // 4 sources:
                {
                    xPathContext.popCurrentNode();
                    n3 = dTMAxisTraverser.next(n, n3);
                    continue block14;
                }
                while (-1 != n4) {
                    block26 : {
                        block25 : {
                            xPathContext.pushCurrentNode(n4);
                            expression = expression2 = this.execute(xPathContext);
                            expression2 = NodeTest.SCORE_NONE;
                            if (expression == expression2) break block25;
                            expression2 = NodeTest.SCORE_NONE;
                            if (expression == expression2) break block25;
                            try {
                                xPathContext.popCurrentNode();
                                xPathContext.popCurrentNode();
                                return expression;
                            }
                            catch (Throwable throwable) {
                                break block14;
                            }
                            catch (Throwable throwable) {
                                break block26;
                            }
                        }
                        xPathContext.popCurrentNode();
                        n4 = dTMAxisTraverser2.next(n3, n4);
                        continue;
                        catch (Throwable throwable) {}
                        break block26;
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                    }
                    try {
                        xPathContext.popCurrentNode();
                        throw var10_18;
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                }
                n4 = 9;
                ++n2;
            } while (true);
            break;
        } while (true);
        xPathContext.popCurrentNode();
        throw var2_6;
    }
}

