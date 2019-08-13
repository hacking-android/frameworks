/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import libcore.reflect.GenericArrayTypeImpl;
import libcore.reflect.ListOfTypes;
import libcore.reflect.ListOfVariables;
import libcore.reflect.ParameterizedTypeImpl;
import libcore.reflect.TypeVariableImpl;
import libcore.reflect.WildcardTypeImpl;
import libcore.util.EmptyArray;

public final class GenericSignatureParser {
    char[] buffer;
    private boolean eof;
    public ListOfTypes exceptionTypes;
    public Type fieldType;
    public TypeVariable[] formalTypeParameters;
    GenericDeclaration genericDecl;
    String identifier;
    public ListOfTypes interfaceTypes;
    public ClassLoader loader;
    public ListOfTypes parameterTypes;
    int pos;
    public Type returnType;
    public Type superclassType;
    char symbol;

    public GenericSignatureParser(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    static boolean isStopSymbol(char c) {
        if (c != '.' && c != '/') {
            switch (c) {
                default: {
                    return false;
                }
                case ':': 
                case ';': 
                case '<': 
            }
        }
        return true;
    }

    void expect(char c) {
        if (this.symbol == c) {
            this.scanSymbol();
            return;
        }
        throw new GenericSignatureFormatError();
    }

    void parseClassSignature() {
        this.parseOptFormalTypeParameters();
        this.superclassType = this.parseClassTypeSignature();
        this.interfaceTypes = new ListOfTypes(16);
        while (this.symbol > '\u0000') {
            this.interfaceTypes.add(this.parseClassTypeSignature());
        }
    }

    Type parseClassTypeSignature() {
        this.expect('L');
        StringBuilder stringBuilder = new StringBuilder();
        this.scanIdentifier();
        while (this.symbol == '/') {
            this.scanSymbol();
            stringBuilder.append(this.identifier);
            stringBuilder.append(".");
            this.scanIdentifier();
        }
        stringBuilder.append(this.identifier);
        Object object = this.parseOptTypeArguments();
        ParameterizedTypeImpl parameterizedTypeImpl = new ParameterizedTypeImpl(null, stringBuilder.toString(), (ListOfTypes)object, this.loader);
        object = parameterizedTypeImpl;
        while (this.symbol == '.') {
            this.scanSymbol();
            this.scanIdentifier();
            stringBuilder.append("$");
            stringBuilder.append(this.identifier);
            object = this.parseOptTypeArguments();
            object = new ParameterizedTypeImpl(parameterizedTypeImpl, stringBuilder.toString(), (ListOfTypes)object, this.loader);
        }
        this.expect(';');
        return object;
    }

    Type parseFieldTypeSignature() {
        char c = this.symbol;
        if (c != 'L') {
            if (c != 'T') {
                if (c == '[') {
                    this.scanSymbol();
                    return new GenericArrayTypeImpl(this.parseTypeSignature());
                }
                throw new GenericSignatureFormatError();
            }
            return this.parseTypeVariableSignature();
        }
        return this.parseClassTypeSignature();
    }

    public void parseForClass(GenericDeclaration arrtype, String string) {
        this.setInput((GenericDeclaration)arrtype, string);
        if (!this.eof) {
            this.parseClassSignature();
        } else if (arrtype instanceof Class) {
            arrtype = (Class)arrtype;
            this.formalTypeParameters = EmptyArray.TYPE_VARIABLE;
            this.superclassType = arrtype.getSuperclass();
            this.interfaceTypes = (arrtype = arrtype.getInterfaces()).length == 0 ? ListOfTypes.EMPTY : new ListOfTypes(arrtype);
        } else {
            this.formalTypeParameters = EmptyArray.TYPE_VARIABLE;
            this.superclassType = Object.class;
            this.interfaceTypes = ListOfTypes.EMPTY;
        }
    }

    public void parseForConstructor(GenericDeclaration arrtype, String object, Class<?>[] arrclass) {
        this.setInput((GenericDeclaration)arrtype, (String)object);
        if (!this.eof) {
            this.parseMethodTypeSignature(arrclass);
        } else {
            object = (Constructor)arrtype;
            this.formalTypeParameters = EmptyArray.TYPE_VARIABLE;
            arrtype = ((Constructor)object).getParameterTypes();
            this.parameterTypes = arrtype.length == 0 ? ListOfTypes.EMPTY : new ListOfTypes(arrtype);
            arrtype = ((Constructor)object).getExceptionTypes();
            this.exceptionTypes = arrtype.length == 0 ? ListOfTypes.EMPTY : new ListOfTypes(arrtype);
        }
    }

    public void parseForField(GenericDeclaration genericDeclaration, String string) {
        this.setInput(genericDeclaration, string);
        if (!this.eof) {
            this.fieldType = this.parseFieldTypeSignature();
        }
    }

    public void parseForMethod(GenericDeclaration genericDeclaration, String arrtype, Class<?>[] arrclass) {
        this.setInput(genericDeclaration, (String)arrtype);
        if (!this.eof) {
            this.parseMethodTypeSignature(arrclass);
        } else {
            genericDeclaration = (Method)genericDeclaration;
            this.formalTypeParameters = EmptyArray.TYPE_VARIABLE;
            arrtype = ((Method)genericDeclaration).getParameterTypes();
            this.parameterTypes = arrtype.length == 0 ? ListOfTypes.EMPTY : new ListOfTypes(arrtype);
            arrtype = ((Method)genericDeclaration).getExceptionTypes();
            this.exceptionTypes = arrtype.length == 0 ? ListOfTypes.EMPTY : new ListOfTypes(arrtype);
            this.returnType = ((Method)genericDeclaration).getReturnType();
        }
    }

    TypeVariableImpl<GenericDeclaration> parseFormalTypeParameter() {
        this.scanIdentifier();
        String string = this.identifier.intern();
        ListOfTypes listOfTypes = new ListOfTypes(8);
        this.expect(':');
        char c = this.symbol;
        if (c == 'L' || c == '[' || c == 'T') {
            listOfTypes.add(this.parseFieldTypeSignature());
        }
        while (this.symbol == ':') {
            this.scanSymbol();
            listOfTypes.add(this.parseFieldTypeSignature());
        }
        return new TypeVariableImpl<GenericDeclaration>(this.genericDecl, string, listOfTypes);
    }

    void parseMethodTypeSignature(Class<?>[] arrclass) {
        char c;
        this.parseOptFormalTypeParameters();
        this.parameterTypes = new ListOfTypes(16);
        this.expect('(');
        while ((c = this.symbol) != ')' && c > '\u0000') {
            this.parameterTypes.add(this.parseTypeSignature());
        }
        this.expect(')');
        this.returnType = this.parseReturnType();
        if (this.symbol == '^') {
            this.exceptionTypes = new ListOfTypes(8);
            do {
                this.scanSymbol();
                if (this.symbol == 'T') {
                    this.exceptionTypes.add(this.parseTypeVariableSignature());
                    continue;
                }
                this.exceptionTypes.add(this.parseClassTypeSignature());
            } while (this.symbol == '^');
        } else {
            this.exceptionTypes = arrclass != null ? new ListOfTypes(arrclass) : new ListOfTypes(0);
        }
    }

    void parseOptFormalTypeParameters() {
        ListOfVariables listOfVariables = new ListOfVariables();
        if (this.symbol == '<') {
            char c;
            this.scanSymbol();
            listOfVariables.add(this.parseFormalTypeParameter());
            while ((c = this.symbol) != '>' && c > '\u0000') {
                listOfVariables.add(this.parseFormalTypeParameter());
            }
            this.expect('>');
        }
        this.formalTypeParameters = listOfVariables.getArray();
    }

    ListOfTypes parseOptTypeArguments() {
        ListOfTypes listOfTypes = new ListOfTypes(8);
        if (this.symbol == '<') {
            char c;
            this.scanSymbol();
            listOfTypes.add(this.parseTypeArgument());
            while ((c = this.symbol) != '>' && c > '\u0000') {
                listOfTypes.add(this.parseTypeArgument());
            }
            this.expect('>');
        }
        return listOfTypes;
    }

    Type parseReturnType() {
        if (this.symbol != 'V') {
            return this.parseTypeSignature();
        }
        this.scanSymbol();
        return Void.TYPE;
    }

    Type parseTypeArgument() {
        ListOfTypes listOfTypes = new ListOfTypes(1);
        ListOfTypes listOfTypes2 = new ListOfTypes(1);
        char c = this.symbol;
        if (c == '*') {
            this.scanSymbol();
            listOfTypes.add((Type)((Object)Object.class));
            return new WildcardTypeImpl(listOfTypes, listOfTypes2);
        }
        if (c == '+') {
            this.scanSymbol();
            listOfTypes.add(this.parseFieldTypeSignature());
            return new WildcardTypeImpl(listOfTypes, listOfTypes2);
        }
        if (c == '-') {
            this.scanSymbol();
            listOfTypes2.add(this.parseFieldTypeSignature());
            listOfTypes.add((Type)((Object)Object.class));
            return new WildcardTypeImpl(listOfTypes, listOfTypes2);
        }
        return this.parseFieldTypeSignature();
    }

    Type parseTypeSignature() {
        char c = this.symbol;
        if (c != 'F') {
            if (c != 'S') {
                if (c != 'Z') {
                    if (c != 'I') {
                        if (c != 'J') {
                            switch (c) {
                                default: {
                                    return this.parseFieldTypeSignature();
                                }
                                case 'D': {
                                    this.scanSymbol();
                                    return Double.TYPE;
                                }
                                case 'C': {
                                    this.scanSymbol();
                                    return Character.TYPE;
                                }
                                case 'B': 
                            }
                            this.scanSymbol();
                            return Byte.TYPE;
                        }
                        this.scanSymbol();
                        return Long.TYPE;
                    }
                    this.scanSymbol();
                    return Integer.TYPE;
                }
                this.scanSymbol();
                return Boolean.TYPE;
            }
            this.scanSymbol();
            return Short.TYPE;
        }
        this.scanSymbol();
        return Float.TYPE;
    }

    TypeVariableImpl<GenericDeclaration> parseTypeVariableSignature() {
        this.expect('T');
        this.scanIdentifier();
        this.expect(';');
        return new TypeVariableImpl<GenericDeclaration>(this.genericDecl, this.identifier);
    }

    void scanIdentifier() {
        if (!this.eof) {
            StringBuilder stringBuilder = new StringBuilder(32);
            if (!GenericSignatureParser.isStopSymbol(this.symbol)) {
                char c;
                stringBuilder.append(this.symbol);
                while ((c = this.buffer[this.pos]) >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || !GenericSignatureParser.isStopSymbol(c)) {
                    stringBuilder.append(c);
                    ++this.pos;
                    if (this.pos != this.buffer.length) continue;
                    this.identifier = stringBuilder.toString();
                    this.symbol = (char)(false ? 1 : 0);
                    this.eof = true;
                    return;
                }
                this.identifier = stringBuilder.toString();
                this.scanSymbol();
                return;
            }
            this.symbol = (char)(false ? 1 : 0);
            this.eof = true;
            throw new GenericSignatureFormatError();
        }
        throw new GenericSignatureFormatError();
    }

    void scanSymbol() {
        if (!this.eof) {
            int n = this.pos;
            char[] arrc = this.buffer;
            if (n < arrc.length) {
                this.symbol = arrc[n];
                this.pos = n + 1;
            } else {
                this.symbol = (char)(false ? 1 : 0);
                this.eof = true;
            }
            return;
        }
        throw new GenericSignatureFormatError();
    }

    void setInput(GenericDeclaration genericDeclaration, String string) {
        if (string != null) {
            this.genericDecl = genericDeclaration;
            this.buffer = string.toCharArray();
            this.eof = false;
            this.scanSymbol();
        } else {
            this.eof = true;
        }
    }
}

