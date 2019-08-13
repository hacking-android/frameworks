/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.UnionPathIterator;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XString;
import org.apache.xpath.operations.Operation;
import org.apache.xpath.operations.UnaryOperation;
import org.apache.xpath.operations.Variable;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;
import org.apache.xpath.patterns.UnionPattern;

public class XPathVisitor {
    public boolean visitBinaryOperation(ExpressionOwner expressionOwner, Operation operation) {
        return true;
    }

    public boolean visitFunction(ExpressionOwner expressionOwner, Function function) {
        return true;
    }

    public boolean visitLocationPath(ExpressionOwner expressionOwner, LocPathIterator locPathIterator) {
        return true;
    }

    public boolean visitMatchPattern(ExpressionOwner expressionOwner, StepPattern stepPattern) {
        return true;
    }

    public boolean visitNumberLiteral(ExpressionOwner expressionOwner, XNumber xNumber) {
        return true;
    }

    public boolean visitPredicate(ExpressionOwner expressionOwner, Expression expression) {
        return true;
    }

    public boolean visitStep(ExpressionOwner expressionOwner, NodeTest nodeTest) {
        return true;
    }

    public boolean visitStringLiteral(ExpressionOwner expressionOwner, XString xString) {
        return true;
    }

    public boolean visitUnaryOperation(ExpressionOwner expressionOwner, UnaryOperation unaryOperation) {
        return true;
    }

    public boolean visitUnionPath(ExpressionOwner expressionOwner, UnionPathIterator unionPathIterator) {
        return true;
    }

    public boolean visitUnionPattern(ExpressionOwner expressionOwner, UnionPattern unionPattern) {
        return true;
    }

    public boolean visitVariableRef(ExpressionOwner expressionOwner, Variable variable) {
        return true;
    }
}

