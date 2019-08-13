/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.io;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.ProtocolException;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.GraphReader;
import android.filterfw.io.PatternScanner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class TextGraphReader
extends GraphReader {
    private KeyValueMap mBoundReferences;
    private ArrayList<Command> mCommands = new ArrayList();
    private Filter mCurrentFilter;
    private FilterGraph mCurrentGraph;
    private FilterFactory mFactory;
    private KeyValueMap mSettings;

    private void applySettings() throws GraphIOException {
        for (CharSequence charSequence : this.mSettings.keySet()) {
            Object object = this.mSettings.get(charSequence);
            if (((String)charSequence).equals("autoBranch")) {
                this.expectSettingClass((String)charSequence, object, String.class);
                if (object.equals("synced")) {
                    this.mCurrentGraph.setAutoBranchMode(1);
                    continue;
                }
                if (object.equals("unsynced")) {
                    this.mCurrentGraph.setAutoBranchMode(2);
                    continue;
                }
                if (object.equals("off")) {
                    this.mCurrentGraph.setAutoBranchMode(0);
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown autobranch setting: ");
                ((StringBuilder)charSequence).append(object);
                ((StringBuilder)charSequence).append("!");
                throw new GraphIOException(((StringBuilder)charSequence).toString());
            }
            if (((String)charSequence).equals("discardUnconnectedOutputs")) {
                this.expectSettingClass((String)charSequence, object, Boolean.class);
                this.mCurrentGraph.setDiscardUnconnectedOutputs((Boolean)object);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown @setting '");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("'!");
            throw new GraphIOException(((StringBuilder)object).toString());
        }
    }

    private void bindExternal(String string2) throws GraphIOException {
        if (this.mReferences.containsKey(string2)) {
            Object v = this.mReferences.get(string2);
            this.mBoundReferences.put(string2, v);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown external variable '");
        stringBuilder.append(string2);
        stringBuilder.append("'! You must add a reference to this external in the host program using addReference(...)!");
        throw new GraphIOException(stringBuilder.toString());
    }

    private void checkReferences() throws GraphIOException {
        Object object = this.mReferences.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (this.mBoundReferences.containsKey(string2)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Host program specifies reference to '");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("', which is not declared @external in graph file!");
            throw new GraphIOException(((StringBuilder)object).toString());
        }
    }

    private void executeCommands() throws GraphIOException {
        Iterator<Command> iterator = this.mCommands.iterator();
        while (iterator.hasNext()) {
            iterator.next().execute(this);
        }
    }

    private void expectSettingClass(String string2, Object object, Class class_) throws GraphIOException {
        if (object.getClass() == class_) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Setting '");
        stringBuilder.append(string2);
        stringBuilder.append("' must have a value of type ");
        stringBuilder.append(class_.getSimpleName());
        stringBuilder.append(", but found a value of type ");
        stringBuilder.append(object.getClass().getSimpleName());
        stringBuilder.append("!");
        throw new GraphIOException(stringBuilder.toString());
    }

    private void parseString(String charSequence) throws GraphIOException {
        Pattern pattern = Pattern.compile("@[a-zA-Z]+");
        Pattern pattern2 = Pattern.compile("\\}");
        Pattern pattern3 = Pattern.compile("\\{");
        Object object = Pattern.compile("(\\s+|//[^\\n]*\\n)+");
        Pattern pattern4 = Pattern.compile("[a-zA-Z\\.]+");
        Pattern pattern5 = Pattern.compile("[a-zA-Z\\./:]+");
        Pattern pattern6 = Pattern.compile("\\[[a-zA-Z0-9\\-_]+\\]");
        Pattern pattern7 = Pattern.compile("=>");
        String string2 = ";";
        Pattern pattern8 = Pattern.compile(";");
        Pattern pattern9 = Pattern.compile("[a-zA-Z0-9\\-_]+");
        int n = 0;
        object = new PatternScanner((String)charSequence, (Pattern)object);
        String string3 = null;
        String string4 = null;
        String string5 = null;
        charSequence = null;
        block19 : while (!((PatternScanner)object).atEnd()) {
            Object object2;
            switch (n) {
                default: {
                    continue block19;
                }
                case 16: {
                    ((PatternScanner)object).eat(pattern8, string2);
                    n = 0;
                    continue block19;
                }
                case 15: {
                    object2 = this.readKeyValueAssignments((PatternScanner)object, pattern8);
                    this.mSettings.putAll(object2);
                    n = 16;
                    continue block19;
                }
                case 14: {
                    this.bindExternal(((PatternScanner)object).eat(pattern9, "<external-identifier>"));
                    n = 16;
                    continue block19;
                }
                case 13: {
                    object2 = this.readKeyValueAssignments((PatternScanner)object, pattern8);
                    this.mBoundReferences.putAll(object2);
                    n = 16;
                    continue block19;
                }
                case 12: {
                    object2 = ((PatternScanner)object).eat(pattern6, "[<target-port-name>]");
                    object2 = ((String)object2).substring(1, ((String)object2).length() - 1);
                    this.mCommands.add(new ConnectCommand(string3, string4, string5, (String)object2));
                    n = 16;
                    continue block19;
                }
                case 11: {
                    string5 = ((PatternScanner)object).eat(pattern9, "<target-filter-name>");
                    n = 12;
                    continue block19;
                }
                case 10: {
                    ((PatternScanner)object).eat(pattern7, "=>");
                    n = 11;
                    continue block19;
                }
                case 9: {
                    string4 = ((PatternScanner)object).eat(pattern6, "[<source-port-name>]");
                    string4 = string4.substring(1, string4.length() - 1);
                    n = 10;
                    continue block19;
                }
                case 8: {
                    string3 = ((PatternScanner)object).eat(pattern9, "<source-filter-name>");
                    n = 9;
                    continue block19;
                }
                case 7: {
                    ((PatternScanner)object).eat(pattern2, "}");
                    n = 0;
                    continue block19;
                }
                case 6: {
                    object2 = this.readKeyValueAssignments((PatternScanner)object, pattern2);
                    this.mCommands.add(new InitFilterCommand((KeyValueMap)object2));
                    n = 7;
                    continue block19;
                }
                case 5: {
                    ((PatternScanner)object).eat(pattern3, "{");
                    n = 6;
                    continue block19;
                }
                case 4: {
                    String string6 = ((PatternScanner)object).eat(pattern9, "<filter-name>");
                    object2 = this.mCommands;
                    ((ArrayList)object2).add((Command)new AllocateFilterCommand((String)charSequence, string6));
                    n = 5;
                    continue block19;
                }
                case 3: {
                    charSequence = ((PatternScanner)object).eat(pattern9, "<class-name>");
                    n = 4;
                    continue block19;
                }
                case 2: {
                    object2 = ((PatternScanner)object).eat(pattern5, "<library-name>");
                    this.mCommands.add(new AddLibraryCommand((String)object2));
                    n = 16;
                    continue block19;
                }
                case 1: {
                    object2 = ((PatternScanner)object).eat(pattern4, "<package-name>");
                    this.mCommands.add(new ImportPackageCommand((String)object2));
                    n = 16;
                    continue block19;
                }
                case 0: 
            }
            object2 = ((PatternScanner)object).eat(pattern, "<command>");
            if (((String)object2).equals("@import")) {
                n = 1;
                continue;
            }
            if (((String)object2).equals("@library")) {
                n = 2;
                continue;
            }
            if (((String)object2).equals("@filter")) {
                n = 3;
                continue;
            }
            if (((String)object2).equals("@connect")) {
                n = 8;
                continue;
            }
            if (((String)object2).equals("@set")) {
                n = 13;
                continue;
            }
            if (((String)object2).equals("@external")) {
                n = 14;
                continue;
            }
            if (((String)object2).equals("@setting")) {
                n = 15;
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unknown command '");
            ((StringBuilder)charSequence).append((String)object2);
            ((StringBuilder)charSequence).append("'!");
            throw new GraphIOException(((StringBuilder)charSequence).toString());
        }
        if (n != 16 && n != 0) {
            throw new GraphIOException("Unexpected end of input!");
        }
    }

    private KeyValueMap readKeyValueAssignments(PatternScanner object, Pattern serializable) throws GraphIOException {
        boolean bl = true;
        int n = 2;
        int n2 = 3;
        Pattern pattern = Pattern.compile("=");
        String string2 = ";";
        Pattern pattern2 = Pattern.compile(";");
        Pattern pattern3 = Pattern.compile("[a-zA-Z]+[a-zA-Z0-9]*");
        Pattern pattern4 = Pattern.compile("'[^']*'|\\\"[^\\\"]*\\\"");
        Pattern pattern5 = Pattern.compile("[0-9]+");
        Pattern pattern6 = Pattern.compile("[0-9]*\\.[0-9]+f?");
        Pattern pattern7 = Pattern.compile("\\$[a-zA-Z]+[a-zA-Z0-9]");
        Pattern pattern8 = Pattern.compile("true|false");
        int n3 = 0;
        KeyValueMap keyValueMap = new KeyValueMap();
        String string3 = null;
        while (!(((PatternScanner)object).atEnd() || serializable != null && ((PatternScanner)object).peek((Pattern)serializable))) {
            block6 : {
                block7 : {
                    block14 : {
                        block9 : {
                            Object object2;
                            KeyValueMap keyValueMap2;
                            block13 : {
                                block12 : {
                                    block10 : {
                                        String string4;
                                        block11 : {
                                            block8 : {
                                                if (n3 == 0) break block6;
                                                if (n3 == 1) break block7;
                                                if (n3 != 2) {
                                                    if (n3 != 3) continue;
                                                    ((PatternScanner)object).eat(pattern2, string2);
                                                    n3 = 0;
                                                    continue;
                                                }
                                                object2 = ((PatternScanner)object).tryEat(pattern4);
                                                if (object2 == null) break block8;
                                                keyValueMap.put(string3, ((String)object2).substring(1, ((String)object2).length() - 1));
                                                break block9;
                                            }
                                            keyValueMap2 = keyValueMap;
                                            object2 = ((PatternScanner)object).tryEat(pattern7);
                                            if (object2 == null) break block10;
                                            string4 = ((String)object2).substring(1, ((String)object2).length());
                                            object2 = this.mBoundReferences;
                                            object2 = object2 != null ? ((HashMap)object2).get(string4) : null;
                                            if (object2 == null) break block11;
                                            keyValueMap2.put(string3, object2);
                                            break block9;
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Unknown object reference to '");
                                        ((StringBuilder)object).append(string4);
                                        ((StringBuilder)object).append("'!");
                                        throw new GraphIOException(((StringBuilder)object).toString());
                                    }
                                    object2 = ((PatternScanner)object).tryEat(pattern8);
                                    if (object2 == null) break block12;
                                    keyValueMap2.put(string3, Boolean.parseBoolean((String)object2));
                                    break block9;
                                }
                                object2 = ((PatternScanner)object).tryEat(pattern6);
                                if (object2 == null) break block13;
                                keyValueMap2.put(string3, Float.valueOf(Float.parseFloat((String)object2)));
                                break block9;
                            }
                            object2 = ((PatternScanner)object).tryEat(pattern5);
                            if (object2 == null) break block14;
                            keyValueMap2.put(string3, Integer.parseInt((String)object2));
                        }
                        n3 = 3;
                        continue;
                    }
                    throw new GraphIOException(((PatternScanner)object).unexpectedTokenMessage("<value>"));
                }
                ((PatternScanner)object).eat(pattern, "=");
                n3 = 2;
                continue;
            }
            string3 = ((PatternScanner)object).eat(pattern3, "<identifier>");
            n3 = 1;
        }
        if (n3 != 0 && n3 != 3) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unexpected end of assignments on line ");
            ((StringBuilder)serializable).append(((PatternScanner)object).lineNo());
            ((StringBuilder)serializable).append("!");
            throw new GraphIOException(((StringBuilder)serializable).toString());
        }
        return keyValueMap;
    }

    private void reset() {
        this.mCurrentGraph = null;
        this.mCurrentFilter = null;
        this.mCommands.clear();
        this.mBoundReferences = new KeyValueMap();
        this.mSettings = new KeyValueMap();
        this.mFactory = new FilterFactory();
    }

    @Override
    public FilterGraph readGraphString(String string2) throws GraphIOException {
        FilterGraph filterGraph = new FilterGraph();
        this.reset();
        this.mCurrentGraph = filterGraph;
        this.parseString(string2);
        this.applySettings();
        this.executeCommands();
        this.reset();
        return filterGraph;
    }

    @Override
    public KeyValueMap readKeyValueAssignments(String string2) throws GraphIOException {
        return this.readKeyValueAssignments(new PatternScanner(string2, Pattern.compile("\\s+")), null);
    }

    private class AddLibraryCommand
    implements Command {
        private String mLibraryName;

        public AddLibraryCommand(String string2) {
            this.mLibraryName = string2;
        }

        @Override
        public void execute(TextGraphReader textGraphReader) {
            textGraphReader.mFactory;
            FilterFactory.addFilterLibrary(this.mLibraryName);
        }
    }

    private class AllocateFilterCommand
    implements Command {
        private String mClassName;
        private String mFilterName;

        public AllocateFilterCommand(String string2, String string3) {
            this.mClassName = string2;
            this.mFilterName = string3;
        }

        @Override
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            Filter filter;
            try {
                filter = textGraphReader.mFactory.createFilterByClassName(this.mClassName, this.mFilterName);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new GraphIOException(illegalArgumentException.getMessage());
            }
            textGraphReader.mCurrentFilter = filter;
        }
    }

    private static interface Command {
        public void execute(TextGraphReader var1) throws GraphIOException;
    }

    private class ConnectCommand
    implements Command {
        private String mSourceFilter;
        private String mSourcePort;
        private String mTargetFilter;
        private String mTargetName;

        public ConnectCommand(String string2, String string3, String string4, String string5) {
            this.mSourceFilter = string2;
            this.mSourcePort = string3;
            this.mTargetFilter = string4;
            this.mTargetName = string5;
        }

        @Override
        public void execute(TextGraphReader textGraphReader) {
            textGraphReader.mCurrentGraph.connect(this.mSourceFilter, this.mSourcePort, this.mTargetFilter, this.mTargetName);
        }
    }

    private class ImportPackageCommand
    implements Command {
        private String mPackageName;

        public ImportPackageCommand(String string2) {
            this.mPackageName = string2;
        }

        @Override
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            try {
                textGraphReader.mFactory.addPackage(this.mPackageName);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new GraphIOException(illegalArgumentException.getMessage());
            }
        }
    }

    private class InitFilterCommand
    implements Command {
        private KeyValueMap mParams;

        public InitFilterCommand(KeyValueMap keyValueMap) {
            this.mParams = keyValueMap;
        }

        @Override
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            Filter filter = textGraphReader.mCurrentFilter;
            try {
                filter.initWithValueMap(this.mParams);
            }
            catch (ProtocolException protocolException) {
                throw new GraphIOException(protocolException.getMessage());
            }
            textGraphReader.mCurrentGraph.addFilter(TextGraphReader.this.mCurrentFilter);
        }
    }

}

