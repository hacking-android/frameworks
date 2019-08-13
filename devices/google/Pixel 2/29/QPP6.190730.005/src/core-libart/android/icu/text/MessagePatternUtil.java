/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.MessagePattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MessagePatternUtil {
    private MessagePatternUtil() {
    }

    private static ArgNode buildArgNode(MessagePattern messagePattern, int n, int n2) {
        ArgNode argNode = ArgNode.createArgNode();
        MessagePattern.ArgType argType = argNode.argType = messagePattern.getPart(n).getArgType();
        MessagePattern.Part part = messagePattern.getPart(++n);
        argNode.name = messagePattern.getSubstring(part);
        if (part.getType() == MessagePattern.Part.Type.ARG_NUMBER) {
            argNode.number = part.getValue();
        }
        ++n;
        int n3 = 1.$SwitchMap$android$icu$text$MessagePattern$ArgType[argType.ordinal()];
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 == 5) {
                            argNode.typeName = "selectordinal";
                            argNode.complexStyle = MessagePatternUtil.buildPluralStyleNode(messagePattern, n, n2, argType);
                        }
                    } else {
                        argNode.typeName = "select";
                        argNode.complexStyle = MessagePatternUtil.buildSelectStyleNode(messagePattern, n, n2);
                    }
                } else {
                    argNode.typeName = "plural";
                    argNode.complexStyle = MessagePatternUtil.buildPluralStyleNode(messagePattern, n, n2, argType);
                }
            } else {
                argNode.typeName = "choice";
                argNode.complexStyle = MessagePatternUtil.buildChoiceStyleNode(messagePattern, n, n2);
            }
        } else {
            n3 = n + 1;
            argNode.typeName = messagePattern.getSubstring(messagePattern.getPart(n));
            if (n3 < n2) {
                argNode.style = messagePattern.getSubstring(messagePattern.getPart(n3));
            }
        }
        return argNode;
    }

    private static ComplexArgStyleNode buildChoiceStyleNode(MessagePattern messagePattern, int n, int n2) {
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.CHOICE);
        while (n < n2) {
            double d = messagePattern.getNumericValue(messagePattern.getPart(n));
            int n3 = n + 2;
            int n4 = messagePattern.getLimitPartIndex(n3);
            VariantNode variantNode = new VariantNode();
            variantNode.selector = messagePattern.getSubstring(messagePattern.getPart(n + 1));
            variantNode.numericValue = d;
            variantNode.msgNode = MessagePatternUtil.buildMessageNode(messagePattern, n3, n4);
            complexArgStyleNode.addVariant(variantNode);
            n = n4 + 1;
        }
        return complexArgStyleNode.freeze();
    }

    public static MessageNode buildMessageNode(MessagePattern messagePattern) {
        int n = messagePattern.countParts() - 1;
        if (n >= 0) {
            if (messagePattern.getPartType(0) == MessagePattern.Part.Type.MSG_START) {
                return MessagePatternUtil.buildMessageNode(messagePattern, 0, n);
            }
            throw new IllegalArgumentException("The MessagePattern does not represent a MessageFormat pattern");
        }
        throw new IllegalArgumentException("The MessagePattern is empty");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static MessageNode buildMessageNode(MessagePattern var0, int var1_1, int var2_2) {
        var3_3 = var0.getPart(var1_1).getLimit();
        var4_4 = new MessageNode();
        ++var1_1;
        do {
            block5 : {
                if (var3_3 < (var6_6 = (var5_5 = var0.getPart(var1_1)).getIndex())) {
                    MessageNode.access$500(var4_4, new TextNode(var0.getPatternString().substring(var3_3, var6_6)));
                }
                if (var1_1 == var2_2) {
                    return MessageNode.access$700(var4_4);
                }
                var7_7 = var5_5.getType();
                if (var7_7 != MessagePattern.Part.Type.ARG_START) break block5;
                var3_3 = var0.getLimitPartIndex(var1_1);
                MessageNode.access$500(var4_4, MessagePatternUtil.buildArgNode(var0, var1_1, var3_3));
                var8_8 = var0.getPart(var3_3);
                ** GOTO lbl-1000
            }
            var3_3 = var1_1;
            var8_8 = var5_5;
            if (var7_7 == MessagePattern.Part.Type.REPLACE_NUMBER) {
                MessageNode.access$500(var4_4, MessageContentsNode.access$600());
                var8_8 = var5_5;
            } else lbl-1000: // 2 sources:
            {
                var1_1 = var3_3;
            }
            var3_3 = var8_8.getLimit();
            ++var1_1;
        } while (true);
    }

    public static MessageNode buildMessageNode(String string) {
        return MessagePatternUtil.buildMessageNode(new MessagePattern(string));
    }

    private static ComplexArgStyleNode buildPluralStyleNode(MessagePattern messagePattern, int n, int n2, MessagePattern.ArgType object) {
        object = new ComplexArgStyleNode((MessagePattern.ArgType)((Object)object));
        MessagePattern.Part part = messagePattern.getPart(n);
        int n3 = n;
        if (part.getType().hasNumericValue()) {
            ((ComplexArgStyleNode)object).explicitOffset = true;
            ((ComplexArgStyleNode)object).offset = messagePattern.getNumericValue(part);
            n3 = n + 1;
        }
        while (n3 < n2) {
            int n4 = n3 + 1;
            part = messagePattern.getPart(n3);
            double d = -1.23456789E8;
            Object object2 = messagePattern.getPart(n4);
            n = n4;
            if (((MessagePattern.Part)object2).getType().hasNumericValue()) {
                d = messagePattern.getNumericValue((MessagePattern.Part)object2);
                n = n4 + 1;
            }
            n3 = messagePattern.getLimitPartIndex(n);
            object2 = new VariantNode();
            ((VariantNode)object2).selector = messagePattern.getSubstring(part);
            ((VariantNode)object2).numericValue = d;
            ((VariantNode)object2).msgNode = MessagePatternUtil.buildMessageNode(messagePattern, n, n3);
            ((ComplexArgStyleNode)object).addVariant((VariantNode)object2);
            ++n3;
        }
        return ((ComplexArgStyleNode)object).freeze();
    }

    private static ComplexArgStyleNode buildSelectStyleNode(MessagePattern messagePattern, int n, int n2) {
        ComplexArgStyleNode complexArgStyleNode = new ComplexArgStyleNode(MessagePattern.ArgType.SELECT);
        while (n < n2) {
            int n3 = n + 1;
            MessagePattern.Part part = messagePattern.getPart(n);
            n = messagePattern.getLimitPartIndex(n3);
            VariantNode variantNode = new VariantNode();
            variantNode.selector = messagePattern.getSubstring(part);
            variantNode.msgNode = MessagePatternUtil.buildMessageNode(messagePattern, n3, n);
            complexArgStyleNode.addVariant(variantNode);
            ++n;
        }
        return complexArgStyleNode.freeze();
    }

    public static class ArgNode
    extends MessageContentsNode {
        private MessagePattern.ArgType argType;
        private ComplexArgStyleNode complexStyle;
        private String name;
        private int number = -1;
        private String style;
        private String typeName;

        private ArgNode() {
            super(MessageContentsNode.Type.ARG);
        }

        private static ArgNode createArgNode() {
            return new ArgNode();
        }

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public ComplexArgStyleNode getComplexStyle() {
            return this.complexStyle;
        }

        public String getName() {
            return this.name;
        }

        public int getNumber() {
            return this.number;
        }

        public String getSimpleStyle() {
            return this.style;
        }

        public String getTypeName() {
            return this.typeName;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('{');
            stringBuilder.append(this.name);
            if (this.argType != MessagePattern.ArgType.NONE) {
                stringBuilder.append(',');
                stringBuilder.append(this.typeName);
                if (this.argType == MessagePattern.ArgType.SIMPLE) {
                    if (this.style != null) {
                        stringBuilder.append(',');
                        stringBuilder.append(this.style);
                    }
                } else {
                    stringBuilder.append(',');
                    stringBuilder.append(this.complexStyle.toString());
                }
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static class ComplexArgStyleNode
    extends Node {
        private MessagePattern.ArgType argType;
        private boolean explicitOffset;
        private volatile List<VariantNode> list = new ArrayList<VariantNode>();
        private double offset;

        private ComplexArgStyleNode(MessagePattern.ArgType argType) {
            this.argType = argType;
        }

        private void addVariant(VariantNode variantNode) {
            this.list.add(variantNode);
        }

        private ComplexArgStyleNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }

        public MessagePattern.ArgType getArgType() {
            return this.argType;
        }

        public double getOffset() {
            return this.offset;
        }

        public List<VariantNode> getVariants() {
            return this.list;
        }

        public VariantNode getVariantsByType(List<VariantNode> list, List<VariantNode> list2) {
            if (list != null) {
                list.clear();
            }
            list2.clear();
            VariantNode variantNode = null;
            for (VariantNode variantNode2 : this.list) {
                VariantNode variantNode3;
                if (variantNode2.isSelectorNumeric()) {
                    list.add(variantNode2);
                    variantNode3 = variantNode;
                } else if ("other".equals(variantNode2.getSelector())) {
                    variantNode3 = variantNode;
                    if (variantNode == null) {
                        variantNode3 = variantNode2;
                    }
                } else {
                    list2.add(variantNode2);
                    variantNode3 = variantNode;
                }
                variantNode = variantNode3;
            }
            return variantNode;
        }

        public boolean hasExplicitOffset() {
            return this.explicitOffset;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('(');
            stringBuilder.append(this.argType.toString());
            stringBuilder.append(" style) ");
            if (this.hasExplicitOffset()) {
                stringBuilder.append("offset:");
                stringBuilder.append(this.offset);
                stringBuilder.append(' ');
            }
            stringBuilder.append(this.list.toString());
            return stringBuilder.toString();
        }
    }

    public static class MessageContentsNode
    extends Node {
        private Type type;

        private MessageContentsNode(Type type) {
            this.type = type;
        }

        static /* synthetic */ MessageContentsNode access$600() {
            return MessageContentsNode.createReplaceNumberNode();
        }

        private static MessageContentsNode createReplaceNumberNode() {
            return new MessageContentsNode(Type.REPLACE_NUMBER);
        }

        public Type getType() {
            return this.type;
        }

        public String toString() {
            return "{REPLACE_NUMBER}";
        }

        public static enum Type {
            TEXT,
            ARG,
            REPLACE_NUMBER;
            
        }

    }

    public static class MessageNode
    extends Node {
        private volatile List<MessageContentsNode> list = new ArrayList<MessageContentsNode>();

        private MessageNode() {
        }

        static /* synthetic */ void access$500(MessageNode messageNode, MessageContentsNode messageContentsNode) {
            messageNode.addContentsNode(messageContentsNode);
        }

        static /* synthetic */ MessageNode access$700(MessageNode messageNode) {
            return messageNode.freeze();
        }

        private void addContentsNode(MessageContentsNode messageContentsNode) {
            Object object;
            if (messageContentsNode instanceof TextNode && !this.list.isEmpty() && (object = this.list.get(this.list.size() - 1)) instanceof TextNode) {
                TextNode textNode = (TextNode)object;
                object = new StringBuilder();
                ((StringBuilder)object).append(textNode.text);
                ((StringBuilder)object).append(((TextNode)messageContentsNode).text);
                textNode.text = ((StringBuilder)object).toString();
                return;
            }
            this.list.add(messageContentsNode);
        }

        private MessageNode freeze() {
            this.list = Collections.unmodifiableList(this.list);
            return this;
        }

        public List<MessageContentsNode> getContents() {
            return this.list;
        }

        public String toString() {
            return this.list.toString();
        }
    }

    public static class Node {
        private Node() {
        }
    }

    public static class TextNode
    extends MessageContentsNode {
        private String text;

        private TextNode(String string) {
            super(MessageContentsNode.Type.TEXT);
            this.text = string;
        }

        public String getText() {
            return this.text;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u00ab");
            stringBuilder.append(this.text);
            stringBuilder.append("\u00bb");
            return stringBuilder.toString();
        }
    }

    public static class VariantNode
    extends Node {
        private MessageNode msgNode;
        private double numericValue = -1.23456789E8;
        private String selector;

        private VariantNode() {
        }

        public MessageNode getMessage() {
            return this.msgNode;
        }

        public String getSelector() {
            return this.selector;
        }

        public double getSelectorValue() {
            return this.numericValue;
        }

        public boolean isSelectorNumeric() {
            boolean bl = this.numericValue != -1.23456789E8;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.isSelectorNumeric()) {
                stringBuilder.append(this.numericValue);
                stringBuilder.append(" (");
                stringBuilder.append(this.selector);
                stringBuilder.append(") {");
            } else {
                stringBuilder.append(this.selector);
                stringBuilder.append(" {");
            }
            stringBuilder.append(this.msgNode.toString());
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

