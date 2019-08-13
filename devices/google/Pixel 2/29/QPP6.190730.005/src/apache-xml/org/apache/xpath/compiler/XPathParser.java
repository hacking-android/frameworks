/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.compiler;

import java.io.PrintStream;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.ObjectVector;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathProcessorException;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.FunctionTable;
import org.apache.xpath.compiler.Keywords;
import org.apache.xpath.compiler.Lexer;
import org.apache.xpath.compiler.OpMap;
import org.apache.xpath.domapi.XPathStylesheetDOM3Exception;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XString;

public class XPathParser {
    public static final String CONTINUE_AFTER_FATAL_ERROR = "CONTINUE_AFTER_FATAL_ERROR";
    protected static final int FILTER_MATCH_FAILED = 0;
    protected static final int FILTER_MATCH_PREDICATES = 2;
    protected static final int FILTER_MATCH_PRIMARY = 1;
    private ErrorListener m_errorListener;
    private FunctionTable m_functionTable;
    PrefixResolver m_namespaceContext;
    private OpMap m_ops;
    int m_queueMark = 0;
    SourceLocator m_sourceLocator;
    transient String m_token;
    transient char m_tokenChar = (char)(false ? 1 : 0);

    public XPathParser(ErrorListener errorListener, SourceLocator sourceLocator) {
        this.m_errorListener = errorListener;
        this.m_sourceLocator = sourceLocator;
    }

    private void assertion(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{string}));
    }

    private final void consumeExpected(char c) throws TransformerException {
        if (this.tokenIs(c)) {
            this.nextToken();
            return;
        }
        this.error("ER_EXPECTED_BUT_FOUND", new Object[]{String.valueOf(c), this.m_token});
        throw new XPathProcessorException(CONTINUE_AFTER_FATAL_ERROR);
    }

    private final void consumeExpected(String string) throws TransformerException {
        if (this.tokenIs(string)) {
            this.nextToken();
            return;
        }
        this.error("ER_EXPECTED_BUT_FOUND", new Object[]{string, this.m_token});
        throw new XPathProcessorException(CONTINUE_AFTER_FATAL_ERROR);
    }

    private final String getTokenRelative(int n) {
        String string = (n = this.m_queueMark + n) > 0 && n < this.m_ops.getTokenQueueSize() ? (String)this.m_ops.m_tokenQueue.elementAt(n) : null;
        return string;
    }

    private final boolean lookahead(String string, int n) {
        int n2 = this.m_queueMark;
        int n3 = this.m_ops.getTokenQueueSize();
        boolean bl = false;
        boolean bl2 = false;
        if (n2 + n <= n3) {
            String string2 = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark + (n - 1));
            if (string2 != null) {
                bl2 = string2.equals(string);
            } else if (string == null) {
                bl2 = true;
            }
        } else {
            bl2 = bl;
            if (string == null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private final boolean lookbehind(char c, int n) {
        boolean bl;
        if ((n = this.m_queueMark - (n + 1)) >= 0) {
            String string = (String)this.m_ops.m_tokenQueue.elementAt(n);
            if (string.length() == 1) {
                bl = false;
                n = string.charAt(0);
                if (n != 124 && n == c) {
                    bl = true;
                }
            } else {
                bl = false;
            }
        } else {
            bl = false;
        }
        return bl;
    }

    private final boolean lookbehindHasToken(int n) {
        boolean bl;
        if (this.m_queueMark - n > 0) {
            String string = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark - (n - 1));
            bl = false;
            n = string == null ? 124 : (int)string.charAt(0);
            if (n != 124) {
                bl = true;
            }
        } else {
            bl = false;
        }
        return bl;
    }

    private final void nextToken() {
        if (this.m_queueMark < this.m_ops.getTokenQueueSize()) {
            ObjectVector objectVector = this.m_ops.m_tokenQueue;
            int n = this.m_queueMark;
            this.m_queueMark = n + 1;
            this.m_token = (String)objectVector.elementAt(n);
            this.m_tokenChar = this.m_token.charAt(0);
        } else {
            this.m_token = null;
            this.m_tokenChar = (char)(false ? 1 : 0);
        }
    }

    private final void prevToken() {
        int n = this.m_queueMark;
        if (n > 0) {
            this.m_queueMark = n - 1;
            this.m_token = (String)this.m_ops.m_tokenQueue.elementAt(this.m_queueMark);
            this.m_tokenChar = this.m_token.charAt(0);
        } else {
            this.m_token = null;
            this.m_tokenChar = (char)(false ? 1 : 0);
        }
    }

    protected boolean AbbreviatedNodeTestStep(boolean bl) throws TransformerException {
        int n;
        int n2 = this.m_ops.getOp(1);
        int n3 = -1;
        if (this.tokenIs('@')) {
            n = 51;
            this.appendOp(2, 51);
            this.nextToken();
        } else if (this.lookahead("::", 1)) {
            if (this.tokenIs("attribute")) {
                n = 51;
                this.appendOp(2, 51);
            } else if (this.tokenIs("child")) {
                n3 = this.m_ops.getOp(1);
                n = 53;
                this.appendOp(2, 53);
            } else {
                n = -1;
                this.error("ER_AXES_NOT_ALLOWED", new Object[]{this.m_token});
            }
            this.nextToken();
            this.nextToken();
        } else if (this.tokenIs('/')) {
            if (!bl) {
                this.error("ER_EXPECTED_STEP_PATTERN", null);
            }
            n = 52;
            this.appendOp(2, 52);
            this.nextToken();
        } else {
            n3 = this.m_ops.getOp(1);
            n = 53;
            this.appendOp(2, 53);
        }
        OpMap opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        this.NodeTest(n);
        opMap = this.m_ops;
        opMap.setOp(n2 + 1 + 1, opMap.getOp(1) - n2);
        while (this.tokenIs('[')) {
            this.Predicate();
        }
        if (n3 > -1 && this.tokenIs('/') && this.lookahead('/', 1)) {
            this.m_ops.setOp(n3, 52);
            this.nextToken();
            bl = true;
        } else {
            bl = false;
        }
        opMap = this.m_ops;
        opMap.setOp(n2 + 1, opMap.getOp(1) - n2);
        return bl;
    }

    protected int AdditiveExpr(int n) throws TransformerException {
        int n2 = this.m_ops.getOp(1);
        int n3 = n;
        if (-1 == n) {
            n3 = n2;
        }
        this.MultiplicativeExpr(-1);
        n = n3;
        if (this.m_token != null) {
            if (this.tokenIs('+')) {
                this.nextToken();
                this.insertOp(n3, 2, 10);
                n = this.m_ops.getOp(1) - n3;
                n3 = this.AdditiveExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else {
                n = n3;
                if (this.tokenIs('-')) {
                    this.nextToken();
                    this.insertOp(n3, 2, 11);
                    n = this.m_ops.getOp(1) - n3;
                    n3 = this.AdditiveExpr(n3);
                    OpMap opMap = this.m_ops;
                    opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                    n = n3 + 2;
                }
            }
        }
        return n;
    }

    protected void AndExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.EqualityExpr(-1);
        if (this.m_token != null && this.tokenIs("and")) {
            this.nextToken();
            this.insertOp(n, 2, 3);
            this.AndExpr();
            OpMap opMap = this.m_ops;
            opMap.setOp(n + 1, opMap.getOp(1) - n);
        }
    }

    protected void Argument() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 26);
        this.Expr();
        OpMap opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected int AxisName() throws TransformerException {
        Object object = Keywords.getAxisName(this.m_token);
        if (object == null) {
            this.error("ER_ILLEGAL_AXIS_NAME", new Object[]{this.m_token});
        }
        int n = (Integer)object;
        this.appendOp(2, n);
        return n;
    }

    protected void Basis() throws TransformerException {
        int n;
        int n2 = this.m_ops.getOp(1);
        if (this.lookahead("::", 1)) {
            n = this.AxisName();
            this.nextToken();
            this.nextToken();
        } else if (this.tokenIs('@')) {
            n = 39;
            this.appendOp(2, 39);
            this.nextToken();
        } else {
            n = 40;
            this.appendOp(2, 40);
        }
        OpMap opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        this.NodeTest(n);
        opMap = this.m_ops;
        opMap.setOp(n2 + 1 + 1, opMap.getOp(1) - n2);
    }

    protected void BooleanExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 18);
        this.Expr();
        int n2 = this.m_ops.getOp(1) - n;
        if (n2 == 2) {
            this.error("ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL", null);
        }
        this.m_ops.setOp(n + 1, n2);
    }

    protected int EqualityExpr(int n) throws TransformerException {
        int n2 = this.m_ops.getOp(1);
        int n3 = n;
        if (-1 == n) {
            n3 = n2;
        }
        this.RelationalExpr(-1);
        n = n3;
        if (this.m_token != null) {
            if (this.tokenIs('!') && this.lookahead('=', 1)) {
                this.nextToken();
                this.nextToken();
                this.insertOp(n3, 2, 4);
                n = this.m_ops.getOp(1) - n3;
                n3 = this.EqualityExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else {
                n = n3;
                if (this.tokenIs('=')) {
                    this.nextToken();
                    this.insertOp(n3, 2, 5);
                    n = this.m_ops.getOp(1) - n3;
                    n3 = this.EqualityExpr(n3);
                    OpMap opMap = this.m_ops;
                    opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                    n = n3 + 2;
                }
            }
        }
        return n;
    }

    protected void Expr() throws TransformerException {
        this.OrExpr();
    }

    protected int FilterExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        if (this.PrimaryExpr()) {
            if (this.tokenIs('[')) {
                this.insertOp(n, 2, 28);
                while (this.tokenIs('[')) {
                    this.Predicate();
                }
                n = 2;
            } else {
                n = 1;
            }
        } else {
            n = 0;
        }
        return n;
    }

    protected boolean FunctionCall() throws TransformerException {
        int n = this.m_ops.getOp(1);
        if (this.lookahead(':', 1)) {
            this.appendOp(4, 24);
            this.m_ops.setOp(n + 1 + 1, this.m_queueMark - 1);
            this.nextToken();
            this.consumeExpected(':');
            this.m_ops.setOp(n + 1 + 2, this.m_queueMark - 1);
            this.nextToken();
        } else {
            int n2 = this.getFunctionToken(this.m_token);
            if (-1 == n2) {
                this.error("ER_COULDNOT_FIND_FUNCTION", new Object[]{this.m_token});
            }
            switch (n2) {
                default: {
                    this.appendOp(3, 25);
                    this.m_ops.setOp(n + 1 + 1, n2);
                    this.nextToken();
                    break;
                }
                case 1030: 
                case 1031: 
                case 1032: 
                case 1033: {
                    return false;
                }
            }
        }
        this.consumeExpected('(');
        while (!this.tokenIs(')') && this.m_token != null) {
            if (this.tokenIs(',')) {
                this.error("ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG", null);
            }
            this.Argument();
            if (this.tokenIs(')')) continue;
            this.consumeExpected(',');
            if (!this.tokenIs(')')) continue;
            this.error("ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG", null);
        }
        this.consumeExpected(')');
        OpMap opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), -1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
        return true;
    }

    protected void IdKeyPattern() throws TransformerException {
        this.FunctionCall();
    }

    protected void Literal() throws TransformerException {
        int n = this.m_token.length() - 1;
        char c = this.m_tokenChar;
        int n2 = this.m_token.charAt(n);
        if (c == '\"' && n2 == 34 || c == '\'' && n2 == 39) {
            n2 = this.m_queueMark - 1;
            this.m_ops.m_tokenQueue.setElementAt(null, n2);
            Object object = new XString(this.m_token.substring(1, n));
            this.m_ops.m_tokenQueue.setElementAt(object, n2);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1), n2);
            object = this.m_ops;
            ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
            this.nextToken();
        } else {
            this.error("ER_PATTERN_LITERAL_NEEDS_BE_QUOTED", new Object[]{this.m_token});
        }
    }

    protected void LocationPath() throws TransformerException {
        OpMap opMap;
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 28);
        boolean bl = this.tokenIs('/');
        if (bl) {
            this.appendOp(4, 50);
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1) - 2, 4);
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1) - 1, 35);
            this.nextToken();
        } else if (this.m_token == null) {
            this.error("ER_EXPECTED_LOC_PATH_AT_END_EXPR", null);
        }
        if (this.m_token != null && !this.RelativeLocationPath() && !bl) {
            this.error("ER_EXPECTED_LOC_PATH", new Object[]{this.m_token});
        }
        opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), -1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected void LocationPathPattern() throws TransformerException {
        OpMap opMap;
        int n = this.m_ops.getOp(1);
        int n2 = 0;
        this.appendOp(2, 31);
        if (this.lookahead('(', 1) && (this.tokenIs("id") || this.tokenIs("key"))) {
            this.IdKeyPattern();
            if (this.tokenIs('/')) {
                this.nextToken();
                if (this.tokenIs('/')) {
                    this.appendOp(4, 52);
                    this.nextToken();
                } else {
                    this.appendOp(4, 53);
                }
                opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1) - 2, 4);
                opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1) - 1, 1034);
                n2 = 2;
            }
        } else if (this.tokenIs('/')) {
            if (this.lookahead('/', 1)) {
                this.appendOp(4, 52);
                this.nextToken();
                n2 = 2;
            } else {
                this.appendOp(4, 50);
                n2 = 1;
            }
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1) - 2, 4);
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1) - 1, 35);
            this.nextToken();
        } else {
            n2 = 2;
        }
        if (n2 != 0) {
            if (!this.tokenIs('|') && this.m_token != null) {
                this.RelativePathPattern();
            } else if (n2 == 2) {
                this.error("ER_EXPECTED_REL_PATH_PATTERN", null);
            }
        }
        opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), -1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected int MultiplicativeExpr(int n) throws TransformerException {
        int n2 = this.m_ops.getOp(1);
        int n3 = n;
        if (-1 == n) {
            n3 = n2;
        }
        this.UnaryExpr();
        n = n3;
        if (this.m_token != null) {
            if (this.tokenIs('*')) {
                this.nextToken();
                this.insertOp(n3, 2, 12);
                n = this.m_ops.getOp(1) - n3;
                n3 = this.MultiplicativeExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else if (this.tokenIs("div")) {
                this.nextToken();
                this.insertOp(n3, 2, 13);
                n = this.m_ops.getOp(1) - n3;
                n3 = this.MultiplicativeExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else if (this.tokenIs("mod")) {
                this.nextToken();
                this.insertOp(n3, 2, 14);
                n = this.m_ops.getOp(1) - n3;
                n3 = this.MultiplicativeExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else {
                n = n3;
                if (this.tokenIs("quo")) {
                    this.nextToken();
                    this.insertOp(n3, 2, 15);
                    n = this.m_ops.getOp(1) - n3;
                    n3 = this.MultiplicativeExpr(n3);
                    OpMap opMap = this.m_ops;
                    opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                    n = n3 + 2;
                }
            }
        }
        return n;
    }

    protected void NCName() {
        OpMap opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), this.m_queueMark - 1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        this.nextToken();
    }

    protected void NodeTest(int n) throws TransformerException {
        if (this.lookahead('(', 1)) {
            Object object = Keywords.getNodeType(this.m_token);
            if (object == null) {
                this.error("ER_UNKNOWN_NODETYPE", new Object[]{this.m_token});
            } else {
                this.nextToken();
                n = (Integer)object;
                object = this.m_ops;
                ((OpMap)object).setOp(((OpMap)object).getOp(1), n);
                object = this.m_ops;
                ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
                this.consumeExpected('(');
                if (1032 == n && !this.tokenIs(')')) {
                    this.Literal();
                }
                this.consumeExpected(')');
            }
        } else {
            OpMap opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1), 34);
            opMap = this.m_ops;
            opMap.setOp(1, opMap.getOp(1) + 1);
            if (this.lookahead(':', 1)) {
                if (this.tokenIs('*')) {
                    opMap = this.m_ops;
                    opMap.setOp(opMap.getOp(1), -3);
                } else {
                    opMap = this.m_ops;
                    opMap.setOp(opMap.getOp(1), this.m_queueMark - 1);
                    if (!Character.isLetter(this.m_tokenChar) && !this.tokenIs('_')) {
                        this.error("ER_EXPECTED_NODE_TEST", null);
                    }
                }
                this.nextToken();
                this.consumeExpected(':');
            } else {
                opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1), -2);
            }
            opMap = this.m_ops;
            opMap.setOp(1, opMap.getOp(1) + 1);
            if (this.tokenIs('*')) {
                opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1), -3);
            } else {
                opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1), this.m_queueMark - 1);
                if (!Character.isLetter(this.m_tokenChar) && !this.tokenIs('_')) {
                    this.error("ER_EXPECTED_NODE_TEST", null);
                }
            }
            opMap = this.m_ops;
            opMap.setOp(1, opMap.getOp(1) + 1);
            this.nextToken();
        }
    }

    protected void Number() throws TransformerException {
        Object object = this.m_token;
        if (object != null) {
            double d;
            try {
                if (((String)object).indexOf(101) > -1 || this.m_token.indexOf(69) > -1) {
                    object = new NumberFormatException();
                    throw object;
                }
                d = Double.valueOf(this.m_token);
            }
            catch (NumberFormatException numberFormatException) {
                this.error("ER_COULDNOT_BE_FORMATTED_TO_NUMBER", new Object[]{this.m_token});
                d = 0.0;
            }
            this.m_ops.m_tokenQueue.setElementAt(new XNumber(d), this.m_queueMark - 1);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1), this.m_queueMark - 1);
            object = this.m_ops;
            ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
            this.nextToken();
        }
    }

    protected void NumberExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 19);
        this.Expr();
        OpMap opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected void OrExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.AndExpr();
        if (this.m_token != null && this.tokenIs("or")) {
            this.nextToken();
            this.insertOp(n, 2, 2);
            this.OrExpr();
            OpMap opMap = this.m_ops;
            opMap.setOp(n + 1, opMap.getOp(1) - n);
        }
    }

    protected void PathExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        int n2 = this.FilterExpr();
        if (n2 != 0) {
            n2 = n2 == 2 ? 1 : 0;
            int n3 = n2;
            if (this.tokenIs('/')) {
                this.nextToken();
                int n4 = n2;
                if (n2 == 0) {
                    this.insertOp(n, 2, 28);
                    n4 = 1;
                }
                n3 = n4;
                if (!this.RelativeLocationPath()) {
                    this.error("ER_EXPECTED_REL_LOC_PATH", null);
                    n3 = n4;
                }
            }
            if (n3 != 0) {
                OpMap opMap = this.m_ops;
                opMap.setOp(opMap.getOp(1), -1);
                opMap = this.m_ops;
                opMap.setOp(1, opMap.getOp(1) + 1);
                opMap = this.m_ops;
                opMap.setOp(n + 1, opMap.getOp(1) - n);
            }
        } else {
            this.LocationPath();
        }
    }

    protected void Pattern() throws TransformerException {
        do {
            this.LocationPathPattern();
            if (!this.tokenIs('|')) break;
            this.nextToken();
        } while (true);
    }

    protected void Predicate() throws TransformerException {
        if (this.tokenIs('[')) {
            this.nextToken();
            this.PredicateExpr();
            this.consumeExpected(']');
        }
    }

    protected void PredicateExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 29);
        this.Expr();
        OpMap opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), -1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected boolean PrimaryExpr() throws TransformerException {
        boolean bl;
        int n = this.m_ops.getOp(1);
        char c = this.m_tokenChar;
        if (c != '\'' && c != '\"') {
            if (c == '$') {
                this.nextToken();
                this.appendOp(2, 22);
                this.QName();
                OpMap opMap = this.m_ops;
                opMap.setOp(n + 1, opMap.getOp(1) - n);
                bl = true;
            } else if (c == '(') {
                this.nextToken();
                this.appendOp(2, 23);
                this.Expr();
                this.consumeExpected(')');
                OpMap opMap = this.m_ops;
                opMap.setOp(n + 1, opMap.getOp(1) - n);
                bl = true;
            } else {
                Object object = this.m_token;
                if (object != null && ('.' == c && ((String)object).length() > 1 && Character.isDigit(this.m_token.charAt(1)) || Character.isDigit(this.m_tokenChar))) {
                    this.appendOp(2, 27);
                    this.Number();
                    object = this.m_ops;
                    ((OpMap)object).setOp(n + 1, ((OpMap)object).getOp(1) - n);
                    bl = true;
                } else {
                    bl = !(this.lookahead('(', 1) || this.lookahead(':', 1) && this.lookahead('(', 3)) ? false : this.FunctionCall();
                }
            }
        } else {
            this.appendOp(2, 21);
            this.Literal();
            OpMap opMap = this.m_ops;
            opMap.setOp(n + 1, opMap.getOp(1) - n);
            bl = true;
        }
        return bl;
    }

    protected void QName() throws TransformerException {
        OpMap opMap;
        if (this.lookahead(':', 1)) {
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1), this.m_queueMark - 1);
            opMap = this.m_ops;
            opMap.setOp(1, opMap.getOp(1) + 1);
            this.nextToken();
            this.consumeExpected(':');
        } else {
            opMap = this.m_ops;
            opMap.setOp(opMap.getOp(1), -2);
            opMap = this.m_ops;
            opMap.setOp(1, opMap.getOp(1) + 1);
        }
        opMap = this.m_ops;
        opMap.setOp(opMap.getOp(1), this.m_queueMark - 1);
        opMap = this.m_ops;
        opMap.setOp(1, opMap.getOp(1) + 1);
        this.nextToken();
    }

    protected int RelationalExpr(int n) throws TransformerException {
        int n2 = this.m_ops.getOp(1);
        int n3 = n;
        if (-1 == n) {
            n3 = n2;
        }
        this.AdditiveExpr(-1);
        n = n3;
        if (this.m_token != null) {
            if (this.tokenIs('<')) {
                this.nextToken();
                if (this.tokenIs('=')) {
                    this.nextToken();
                    this.insertOp(n3, 2, 6);
                } else {
                    this.insertOp(n3, 2, 7);
                }
                n = this.m_ops.getOp(1) - n3;
                n3 = this.RelationalExpr(n3);
                OpMap opMap = this.m_ops;
                opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                n = n3 + 2;
            } else {
                n = n3;
                if (this.tokenIs('>')) {
                    this.nextToken();
                    if (this.tokenIs('=')) {
                        this.nextToken();
                        this.insertOp(n3, 2, 8);
                    } else {
                        this.insertOp(n3, 2, 9);
                    }
                    n = this.m_ops.getOp(1) - n3;
                    n3 = this.RelationalExpr(n3);
                    OpMap opMap = this.m_ops;
                    opMap.setOp(n3 + 1, opMap.getOp(n3 + n + 1) + n);
                    n = n3 + 2;
                }
            }
        }
        return n;
    }

    protected boolean RelativeLocationPath() throws TransformerException {
        if (!this.Step()) {
            return false;
        }
        while (this.tokenIs('/')) {
            this.nextToken();
            if (this.Step()) continue;
            this.error("ER_EXPECTED_LOC_STEP", null);
        }
        return true;
    }

    protected void RelativePathPattern() throws TransformerException {
        boolean bl = this.StepPattern(false);
        while (this.tokenIs('/')) {
            this.nextToken();
            bl = !bl;
            bl = this.StepPattern(bl);
        }
    }

    protected boolean Step() throws TransformerException {
        Object object;
        int n = this.m_ops.getOp(1);
        boolean bl = this.tokenIs('/');
        int n2 = n;
        if (bl) {
            this.nextToken();
            this.appendOp(2, 42);
            object = this.m_ops;
            ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1), 1033);
            object = this.m_ops;
            ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
            object = this.m_ops;
            ((OpMap)object).setOp(n + 1 + 1, ((OpMap)object).getOp(1) - n);
            object = this.m_ops;
            ((OpMap)object).setOp(n + 1, ((OpMap)object).getOp(1) - n);
            n2 = this.m_ops.getOp(1);
        }
        if (this.tokenIs(".")) {
            this.nextToken();
            if (this.tokenIs('[')) {
                this.error("ER_PREDICATE_ILLEGAL_SYNTAX", null);
            }
            this.appendOp(4, 48);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1) - 2, 4);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1) - 1, 1033);
        } else if (this.tokenIs("..")) {
            this.nextToken();
            this.appendOp(4, 45);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1) - 2, 4);
            object = this.m_ops;
            ((OpMap)object).setOp(((OpMap)object).getOp(1) - 1, 1033);
        } else {
            if (!(this.tokenIs('*') || this.tokenIs('@') || this.tokenIs('_') || (object = this.m_token) != null && Character.isLetter(((String)object).charAt(0)))) {
                if (bl) {
                    this.error("ER_EXPECTED_LOC_STEP", null);
                }
                return false;
            }
            this.Basis();
            while (this.tokenIs('[')) {
                this.Predicate();
            }
            object = this.m_ops;
            ((OpMap)object).setOp(n2 + 1, ((OpMap)object).getOp(1) - n2);
        }
        return true;
    }

    protected boolean StepPattern(boolean bl) throws TransformerException {
        return this.AbbreviatedNodeTestStep(bl);
    }

    protected void StringExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        this.appendOp(2, 17);
        this.Expr();
        OpMap opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    protected void UnaryExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        boolean bl = false;
        if (this.m_tokenChar == '-') {
            this.nextToken();
            this.appendOp(2, 16);
            bl = true;
        }
        this.UnionExpr();
        if (bl) {
            OpMap opMap = this.m_ops;
            opMap.setOp(n + 1, opMap.getOp(1) - n);
        }
    }

    protected void UnionExpr() throws TransformerException {
        int n = this.m_ops.getOp(1);
        boolean bl = false;
        do {
            this.PathExpr();
            if (!this.tokenIs('|')) break;
            boolean bl2 = bl;
            if (!bl) {
                bl2 = true;
                this.insertOp(n, 2, 20);
            }
            this.nextToken();
            bl = bl2;
        } while (true);
        OpMap opMap = this.m_ops;
        opMap.setOp(n + 1, opMap.getOp(1) - n);
    }

    void appendOp(int n, int n2) {
        int n3 = this.m_ops.getOp(1);
        this.m_ops.setOp(n3, n2);
        this.m_ops.setOp(n3 + 1, n);
        this.m_ops.setOp(1, n3 + n);
    }

    protected String dumpRemainingTokenQueue() {
        String string;
        int n = this.m_queueMark;
        if (n < this.m_ops.getTokenQueueSize()) {
            StringBuilder stringBuilder;
            string = "\n Remaining tokens: (";
            while (n < this.m_ops.getTokenQueueSize()) {
                String string2 = (String)this.m_ops.m_tokenQueue.elementAt(n);
                stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(" '");
                stringBuilder.append(string2);
                stringBuilder.append("'");
                string = stringBuilder.toString();
                ++n;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(")");
            string = stringBuilder.toString();
        } else {
            string = "";
        }
        return string;
    }

    void error(String object, Object[] object2) throws TransformerException {
        object2 = XSLMessages.createXPATHMessage((String)object, object2);
        object = this.getErrorListener();
        object2 = new TransformerException((String)object2, this.m_sourceLocator);
        if (object != null) {
            object.fatalError((TransformerException)object2);
            return;
        }
        throw object2;
    }

    void errorForDOM3(String object, Object[] object2) throws TransformerException {
        object2 = XSLMessages.createXPATHMessage((String)object, object2);
        object = this.getErrorListener();
        object2 = new XPathStylesheetDOM3Exception((String)object2, this.m_sourceLocator);
        if (object != null) {
            object.fatalError((TransformerException)object2);
            return;
        }
        throw object2;
    }

    public ErrorListener getErrorListener() {
        return this.m_errorListener;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final int getFunctionToken(String string) {
        Object object;
        Object object2 = object = Keywords.lookupNodeTest(string);
        if (object != null) return (Integer)object2;
        try {
            object2 = this.m_functionTable.getFunctionID(string);
            return (Integer)object2;
        }
        catch (ClassCastException classCastException) {
            return -1;
        }
        catch (NullPointerException nullPointerException) {
            return -1;
        }
    }

    public void initMatchPattern(Compiler object, String charSequence, PrefixResolver prefixResolver) throws TransformerException {
        this.m_ops = object;
        this.m_namespaceContext = prefixResolver;
        this.m_functionTable = ((Compiler)object).getFunctionTable();
        new Lexer((Compiler)object, prefixResolver, this).tokenize((String)charSequence);
        this.m_ops.setOp(0, 30);
        this.m_ops.setOp(1, 2);
        this.nextToken();
        this.Pattern();
        if (this.m_token != null) {
            object = "";
            while (this.m_token != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append("'");
                ((StringBuilder)charSequence).append(this.m_token);
                ((StringBuilder)charSequence).append("'");
                charSequence = ((StringBuilder)charSequence).toString();
                this.nextToken();
                object = charSequence;
                if (this.m_token == null) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(", ");
                object = ((StringBuilder)object).toString();
            }
            this.error("ER_EXTRA_ILLEGAL_TOKENS", new Object[]{object});
        }
        object = this.m_ops;
        ((OpMap)object).setOp(((OpMap)object).getOp(1), -1);
        object = this.m_ops;
        ((OpMap)object).setOp(1, ((OpMap)object).getOp(1) + 1);
        this.m_ops.shrink();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void initXPath(Compiler compiler, String charSequence, PrefixResolver prefixResolver) throws TransformerException {
        XPathProcessorException xPathProcessorException2;
        block4 : {
            this.m_ops = compiler;
            this.m_namespaceContext = prefixResolver;
            this.m_functionTable = compiler.getFunctionTable();
            new Lexer(compiler, prefixResolver, this).tokenize((String)charSequence);
            this.m_ops.setOp(0, 1);
            this.m_ops.setOp(1, 2);
            try {
                this.nextToken();
                this.Expr();
                if (this.m_token != null) {
                    charSequence = "";
                    while (this.m_token != null) {
                        CharSequence charSequence2 = new StringBuilder();
                        charSequence2.append((String)charSequence);
                        charSequence2.append("'");
                        charSequence2.append(this.m_token);
                        charSequence2.append("'");
                        charSequence2 = charSequence2.toString();
                        this.nextToken();
                        charSequence = charSequence2;
                        if (this.m_token == null) continue;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        ((StringBuilder)charSequence).append(", ");
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    this.error("ER_EXTRA_ILLEGAL_TOKENS", new Object[]{charSequence});
                }
            }
            catch (XPathProcessorException xPathProcessorException2) {
                if (!CONTINUE_AFTER_FATAL_ERROR.equals(xPathProcessorException2.getMessage())) break block4;
                this.initXPath(compiler, "/..", prefixResolver);
            }
            compiler.shrink();
            return;
        }
        throw xPathProcessorException2;
    }

    void insertOp(int n, int n2, int n3) {
        int n4 = this.m_ops.getOp(1);
        for (int i = n4 - 1; i >= n; --i) {
            OpMap opMap = this.m_ops;
            opMap.setOp(i + n2, opMap.getOp(i));
        }
        this.m_ops.setOp(n, n3);
        this.m_ops.setOp(1, n4 + n2);
    }

    final boolean lookahead(char c, int n) {
        boolean bl;
        if ((n = this.m_queueMark + n) <= this.m_ops.getTokenQueueSize() && n > 0 && this.m_ops.getTokenQueueSize() != 0) {
            boolean bl2;
            String string = (String)this.m_ops.m_tokenQueue.elementAt(n - 1);
            n = string.length();
            bl = bl2 = false;
            if (n == 1) {
                bl = bl2;
                if (string.charAt(0) == c) {
                    bl = true;
                }
            }
        } else {
            bl = false;
        }
        return bl;
    }

    public void setErrorHandler(ErrorListener errorListener) {
        this.m_errorListener = errorListener;
    }

    final boolean tokenIs(char c) {
        boolean bl;
        String string = this.m_token;
        boolean bl2 = bl = false;
        if (string != null) {
            bl2 = bl;
            if (this.m_tokenChar == c) {
                bl2 = true;
            }
        }
        return bl2;
    }

    final boolean tokenIs(String string) {
        String string2 = this.m_token;
        boolean bl = string2 != null ? string2.equals(string) : string == null;
        return bl;
    }

    void warn(String string, Object[] object) throws TransformerException {
        string = XSLMessages.createXPATHWarning(string, (Object[])object);
        object = this.getErrorListener();
        if (object != null) {
            object.warning(new TransformerException(string, this.m_sourceLocator));
        } else {
            System.err.println(string);
        }
    }
}

