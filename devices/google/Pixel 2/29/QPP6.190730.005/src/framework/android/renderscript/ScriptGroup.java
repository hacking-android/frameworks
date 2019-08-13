/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.FieldPacker;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.Type;
import android.util.Log;
import android.util.Pair;
import dalvik.system.CloseGuard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ScriptGroup
extends BaseObj {
    private static final String TAG = "ScriptGroup";
    private List<Closure> mClosures;
    IO[] mInputs;
    private List<Input> mInputs2;
    private String mName;
    IO[] mOutputs;
    private Future[] mOutputs2;

    ScriptGroup(long l, RenderScript renderScript) {
        super(l, renderScript);
        this.guard.open("destroy");
    }

    ScriptGroup(RenderScript renderScript, String string2, List<Closure> list, List<Input> arrl, Future[] arrfuture) {
        super(0L, renderScript);
        this.mName = string2;
        this.mClosures = list;
        this.mInputs2 = arrl;
        this.mOutputs2 = arrfuture;
        arrl = new long[list.size()];
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = list.get(i).getID(renderScript);
        }
        this.setID(renderScript.nScriptGroup2Create(string2, RenderScript.getCachePath(), arrl));
        this.guard.open("destroy");
    }

    @Override
    public void destroy() {
        super.destroy();
        Object object = this.mClosures;
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ((Closure)object.next()).destroy();
            }
        }
    }

    public void execute() {
        this.mRS.nScriptGroupExecute(this.getID(this.mRS));
    }

    public Object[] execute(Object ... object) {
        Object object2;
        int n;
        if (((Object[])object).length < this.mInputs2.size()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.toString());
            stringBuilder.append(" receives ");
            stringBuilder.append(((Object[])object).length);
            stringBuilder.append(" inputs, less than expected ");
            stringBuilder.append(this.mInputs2.size());
            Log.e(TAG, stringBuilder.toString());
            return null;
        }
        if (((Object[])object).length > this.mInputs2.size()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.toString());
            ((StringBuilder)object2).append(" receives ");
            ((StringBuilder)object2).append(((Object[])object).length);
            ((StringBuilder)object2).append(" inputs, more than expected ");
            ((StringBuilder)object2).append(this.mInputs2.size());
            Log.i(TAG, ((StringBuilder)object2).toString());
        }
        for (n = 0; n < this.mInputs2.size(); ++n) {
            object2 = object[n];
            if (!(object2 instanceof Future) && !(object2 instanceof Input)) {
                this.mInputs2.get(n).set(object2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.toString());
            ((StringBuilder)object).append(": input ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is a future or unbound value");
            Log.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
        this.mRS.nScriptGroup2Execute(this.getID(this.mRS));
        Future[] arrfuture = this.mOutputs2;
        Object[] arrobject = new Object[arrfuture.length];
        n = 0;
        int n2 = arrfuture.length;
        int n3 = 0;
        while (n3 < n2) {
            object = object2 = arrfuture[n3].getValue();
            if (object2 instanceof Input) {
                object = ((Input)object2).get();
            }
            arrobject[n] = object;
            ++n3;
            ++n;
        }
        return arrobject;
    }

    public void setInput(Script.KernelID kernelID, Allocation allocation) {
        IO[] arriO;
        for (int i = 0; i < (arriO = this.mInputs).length; ++i) {
            if (arriO[i].mKID != kernelID) continue;
            this.mInputs[i].mAllocation = allocation;
            this.mRS.nScriptGroupSetInput(this.getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
            return;
        }
        throw new RSIllegalArgumentException("Script not found");
    }

    public void setOutput(Script.KernelID kernelID, Allocation allocation) {
        IO[] arriO;
        for (int i = 0; i < (arriO = this.mOutputs).length; ++i) {
            if (arriO[i].mKID != kernelID) continue;
            this.mOutputs[i].mAllocation = allocation;
            this.mRS.nScriptGroupSetOutput(this.getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
            return;
        }
        throw new RSIllegalArgumentException("Script not found");
    }

    public static final class Binding {
        private final Script.FieldID mField;
        private final Object mValue;

        public Binding(Script.FieldID fieldID, Object object) {
            this.mField = fieldID;
            this.mValue = object;
        }

        Script.FieldID getField() {
            return this.mField;
        }

        Object getValue() {
            return this.mValue;
        }
    }

    public static final class Builder {
        private int mKernelCount;
        private ArrayList<ConnectLine> mLines = new ArrayList();
        private ArrayList<Node> mNodes = new ArrayList();
        private RenderScript mRS;

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private Node findNode(Script.KernelID kernelID) {
            for (int i = 0; i < this.mNodes.size(); ++i) {
                Node node = this.mNodes.get(i);
                for (int j = 0; j < node.mKernels.size(); ++j) {
                    if (kernelID != node.mKernels.get(j)) continue;
                    return node;
                }
            }
            return null;
        }

        private Node findNode(Script script) {
            for (int i = 0; i < this.mNodes.size(); ++i) {
                if (script != this.mNodes.get((int)i).mScript) continue;
                return this.mNodes.get(i);
            }
            return null;
        }

        private void mergeDAGs(int n, int n2) {
            for (int i = 0; i < this.mNodes.size(); ++i) {
                if (this.mNodes.get((int)i).dagNumber != n2) continue;
                this.mNodes.get((int)i).dagNumber = n;
            }
        }

        private void validateCycle(Node node, Node node2) {
            for (int i = 0; i < node.mOutputs.size(); ++i) {
                Object object = node.mOutputs.get(i);
                if (((ConnectLine)object).mToK != null) {
                    Node node3 = this.findNode(object.mToK.mScript);
                    if (!node3.equals(node2)) {
                        this.validateCycle(node3, node2);
                    } else {
                        throw new RSInvalidStateException("Loops in group not allowed.");
                    }
                }
                if (((ConnectLine)object).mToF == null) continue;
                object = this.findNode(object.mToF.mScript);
                if (!object.equals(node2)) {
                    this.validateCycle((Node)object, node2);
                    continue;
                }
                throw new RSInvalidStateException("Loops in group not allowed.");
            }
        }

        private void validateDAG() {
            int n;
            for (n = 0; n < this.mNodes.size(); ++n) {
                Node node = this.mNodes.get(n);
                if (node.mInputs.size() != 0) continue;
                if (node.mOutputs.size() == 0 && this.mNodes.size() > 1) {
                    throw new RSInvalidStateException("Groups cannot contain unconnected scripts");
                }
                this.validateDAGRecurse(node, n + 1);
            }
            int n2 = this.mNodes.get((int)0).dagNumber;
            for (n = 0; n < this.mNodes.size(); ++n) {
                if (this.mNodes.get((int)n).dagNumber == n2) {
                    continue;
                }
                throw new RSInvalidStateException("Multiple DAGs in group not allowed.");
            }
        }

        private void validateDAGRecurse(Node node, int n) {
            if (node.dagNumber != 0 && node.dagNumber != n) {
                this.mergeDAGs(node.dagNumber, n);
                return;
            }
            node.dagNumber = n;
            for (int i = 0; i < node.mOutputs.size(); ++i) {
                ConnectLine connectLine = node.mOutputs.get(i);
                if (connectLine.mToK != null) {
                    this.validateDAGRecurse(this.findNode(connectLine.mToK.mScript), n);
                }
                if (connectLine.mToF == null) continue;
                this.validateDAGRecurse(this.findNode(connectLine.mToF.mScript), n);
            }
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            Node node = this.findNode(kernelID);
            if (node != null) {
                Node node2 = this.findNode(fieldID.mScript);
                if (node2 != null) {
                    ConnectLine connectLine = new ConnectLine(type, kernelID, fieldID);
                    this.mLines.add(new ConnectLine(type, kernelID, fieldID));
                    node.mOutputs.add(connectLine);
                    node2.mInputs.add(connectLine);
                    this.validateCycle(node, node);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        public Builder addConnection(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            Node node = this.findNode(kernelID);
            if (node != null) {
                Node node2 = this.findNode(kernelID2);
                if (node2 != null) {
                    ConnectLine connectLine = new ConnectLine(type, kernelID, kernelID2);
                    this.mLines.add(new ConnectLine(type, kernelID, kernelID2));
                    node.mOutputs.add(connectLine);
                    node2.mInputs.add(connectLine);
                    this.validateCycle(node, node);
                    return this;
                }
                throw new RSInvalidStateException("To script not found.");
            }
            throw new RSInvalidStateException("From script not found.");
        }

        public Builder addKernel(Script.KernelID kernelID) {
            if (this.mLines.size() == 0) {
                Node node;
                if (this.findNode(kernelID) != null) {
                    return this;
                }
                ++this.mKernelCount;
                Node node2 = node = this.findNode(kernelID.mScript);
                if (node == null) {
                    node2 = new Node(kernelID.mScript);
                    this.mNodes.add(node2);
                }
                node2.mKernels.add(kernelID);
                return this;
            }
            throw new RSInvalidStateException("Kernels may not be added once connections exist.");
        }

        public ScriptGroup create() {
            if (this.mNodes.size() != 0) {
                long[] arrl;
                long[] arrl2;
                int n;
                for (n = 0; n < this.mNodes.size(); ++n) {
                    this.mNodes.get((int)n).dagNumber = 0;
                }
                this.validateDAG();
                ArrayList<IO> arrayList = new ArrayList<IO>();
                ArrayList<IO> arrayList2 = new ArrayList<IO>();
                Object object = new long[this.mKernelCount];
                int n2 = 0;
                for (n = 0; n < this.mNodes.size(); ++n) {
                    arrl2 = this.mNodes.get(n);
                    int n3 = 0;
                    while (n3 < arrl2.mKernels.size()) {
                        int n4;
                        arrl = arrl2.mKernels.get(n3);
                        object[n2] = arrl.getID(this.mRS);
                        boolean bl = false;
                        boolean bl2 = false;
                        for (n4 = 0; n4 < arrl2.mInputs.size(); ++n4) {
                            if (arrl2.mInputs.get((int)n4).mToK != arrl) continue;
                            bl = true;
                        }
                        for (n4 = 0; n4 < arrl2.mOutputs.size(); ++n4) {
                            if (arrl2.mOutputs.get((int)n4).mFrom != arrl) continue;
                            bl2 = true;
                        }
                        if (!bl) {
                            arrayList.add(new IO((Script.KernelID)arrl));
                        }
                        if (!bl2) {
                            arrayList2.add(new IO((Script.KernelID)arrl));
                        }
                        ++n3;
                        ++n2;
                    }
                }
                if (n2 == this.mKernelCount) {
                    long[] arrl3 = new long[this.mLines.size()];
                    arrl2 = new long[this.mLines.size()];
                    long[] arrl4 = new long[this.mLines.size()];
                    arrl = new long[this.mLines.size()];
                    for (n = 0; n < this.mLines.size(); ++n) {
                        ConnectLine connectLine = this.mLines.get(n);
                        arrl3[n] = connectLine.mFrom.getID(this.mRS);
                        if (connectLine.mToK != null) {
                            arrl2[n] = connectLine.mToK.getID(this.mRS);
                        }
                        if (connectLine.mToF != null) {
                            arrl4[n] = connectLine.mToF.getID(this.mRS);
                        }
                        arrl[n] = connectLine.mAllocationType.getID(this.mRS);
                    }
                    long l = this.mRS.nScriptGroupCreate((long[])object, arrl3, arrl2, arrl4, arrl);
                    if (l != 0L) {
                        object = new ScriptGroup(l, this.mRS);
                        object.mOutputs = new IO[arrayList2.size()];
                        for (n = 0; n < arrayList2.size(); ++n) {
                            object.mOutputs[n] = (IO)arrayList2.get(n);
                        }
                        object.mInputs = new IO[arrayList.size()];
                        for (n = 0; n < arrayList.size(); ++n) {
                            object.mInputs[n] = (IO)arrayList.get(n);
                        }
                        return object;
                    }
                    throw new RSRuntimeException("Object creation error, should not happen.");
                }
                throw new RSRuntimeException("Count mismatch, should not happen.");
            }
            throw new RSInvalidStateException("Empty script groups are not allowed");
        }
    }

    public static final class Builder2 {
        private static final String TAG = "ScriptGroup.Builder2";
        List<Closure> mClosures;
        List<Input> mInputs;
        RenderScript mRS;

        public Builder2(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mClosures = new ArrayList<Closure>();
            this.mInputs = new ArrayList<Input>();
        }

        private Closure addInvokeInternal(Script.InvokeID baseObj, Object[] arrobject, Map<Script.FieldID, Object> map) {
            baseObj = new Closure(this.mRS, (Script.InvokeID)baseObj, arrobject, map);
            this.mClosures.add((Closure)baseObj);
            return baseObj;
        }

        private Closure addKernelInternal(Script.KernelID baseObj, Type type, Object[] arrobject, Map<Script.FieldID, Object> map) {
            baseObj = new Closure(this.mRS, (Script.KernelID)baseObj, type, arrobject, map);
            this.mClosures.add((Closure)baseObj);
            return baseObj;
        }

        private boolean seperateArgsAndBindings(Object[] arrobject, ArrayList<Object> object, Map<Script.FieldID, Object> map) {
            int n;
            int n2 = 0;
            do {
                n = ++n2;
                if (n2 >= arrobject.length) break;
                if (arrobject[n2] instanceof Binding) {
                    n = n2;
                    break;
                }
                ((ArrayList)object).add((Object)arrobject[n2]);
            } while (true);
            while (n < arrobject.length) {
                if (!(arrobject[n] instanceof Binding)) {
                    return false;
                }
                object = (Binding)arrobject[n];
                map.put(((Binding)object).getField(), ((Binding)object).getValue());
                ++n;
            }
            return true;
        }

        public Input addInput() {
            Input input = new Input();
            this.mInputs.add(input);
            return input;
        }

        public Closure addInvoke(Script.InvokeID invokeID, Object ... arrobject) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            HashMap<Script.FieldID, Object> hashMap = new HashMap<Script.FieldID, Object>();
            if (!this.seperateArgsAndBindings(arrobject, arrayList, hashMap)) {
                return null;
            }
            return this.addInvokeInternal(invokeID, arrayList.toArray(), hashMap);
        }

        public Closure addKernel(Script.KernelID kernelID, Type type, Object ... arrobject) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            HashMap<Script.FieldID, Object> hashMap = new HashMap<Script.FieldID, Object>();
            if (!this.seperateArgsAndBindings(arrobject, arrayList, hashMap)) {
                return null;
            }
            return this.addKernelInternal(kernelID, type, arrayList.toArray(), hashMap);
        }

        public ScriptGroup create(String object, Future ... arrfuture) {
            if (object != null && !((String)object).isEmpty() && ((String)object).length() <= 100 && ((String)object).equals(((String)object).replaceAll("[^a-zA-Z0-9-]", "_"))) {
                object = new ScriptGroup(this.mRS, (String)object, this.mClosures, this.mInputs, arrfuture);
                this.mClosures = new ArrayList<Closure>();
                this.mInputs = new ArrayList<Input>();
                return object;
            }
            throw new RSIllegalArgumentException("invalid script group name");
        }
    }

    public static final class Closure
    extends BaseObj {
        private static final String TAG = "Closure";
        private Object[] mArgs;
        private Map<Script.FieldID, Object> mBindings;
        private FieldPacker mFP;
        private Map<Script.FieldID, Future> mGlobalFuture;
        private Future mReturnFuture;
        private Allocation mReturnValue;

        Closure(long l, RenderScript renderScript) {
            super(l, renderScript);
        }

        Closure(RenderScript renderScript, Script.InvokeID invokeID, Object[] arrobject, Map<Script.FieldID, Object> arrl) {
            super(0L, renderScript);
            this.mFP = FieldPacker.createFromArray(arrobject);
            this.mArgs = arrobject;
            this.mBindings = arrl;
            this.mGlobalFuture = new HashMap<Script.FieldID, Future>();
            int n = arrl.size();
            long[] arrl2 = new long[n];
            long[] arrl3 = new long[n];
            arrobject = new int[n];
            long[] arrl4 = new long[n];
            Object object = new long[n];
            Iterator<Map.Entry<Script.FieldID, Object>> iterator = arrl.entrySet().iterator();
            n = 0;
            arrl = object;
            while (iterator.hasNext()) {
                Object object2 = iterator.next();
                object = object2.getValue();
                object2 = object2.getKey();
                arrl2[n] = ((BaseObj)object2).getID(renderScript);
                this.retrieveValueAndDependenceInfo(renderScript, n, (Script.FieldID)object2, object, arrl3, (int[])arrobject, arrl4, arrl);
                ++n;
            }
            this.setID(renderScript.nInvokeClosureCreate(invokeID.getID(renderScript), this.mFP.getData(), arrl2, arrl3, (int[])arrobject));
            this.guard.open("destroy");
        }

        Closure(RenderScript renderScript, Script.KernelID kernelID, Type arrl, Object[] object, Map<Script.FieldID, Object> map) {
            super(0L, renderScript);
            int n;
            this.mArgs = object;
            this.mReturnValue = Allocation.createTyped(renderScript, (Type)arrl);
            this.mBindings = map;
            this.mGlobalFuture = new HashMap<Script.FieldID, Future>();
            int n2 = ((Object)object).length + map.size();
            arrl = new long[n2];
            long[] arrl2 = new long[n2];
            int[] arrn = new int[n2];
            long[] arrl3 = new long[n2];
            long[] arrl4 = new long[n2];
            for (n = 0; n < ((Object)object).length; ++n) {
                arrl[n] = 0L;
                this.retrieveValueAndDependenceInfo(renderScript, n, null, object[n], arrl2, arrn, arrl3, arrl4);
            }
            for (Map.Entry entry : map.entrySet()) {
                map = entry.getValue();
                Script.FieldID object2 = (Script.FieldID)entry.getKey();
                arrl[n] = object2.getID(renderScript);
                this.retrieveValueAndDependenceInfo(renderScript, n, object2, map, arrl2, arrn, arrl3, arrl4);
                ++n;
            }
            this.setID(renderScript.nClosureCreate(kernelID.getID(renderScript), this.mReturnValue.getID(renderScript), arrl, arrl2, arrn, arrl3, arrl4));
            this.guard.open("destroy");
        }

        private void retrieveValueAndDependenceInfo(RenderScript object, int n, Script.FieldID fieldID, Object object2, long[] arrl, int[] arrn, long[] object3, long[] arrl2) {
            if (object2 instanceof Future) {
                Future future = (Future)object2;
                object2 = future.getValue();
                object3[n] = future.getClosure().getID((RenderScript)object);
                object3 = future.getFieldID();
                long l = object3 != null ? ((BaseObj)object3).getID((RenderScript)object) : 0L;
                arrl2[n] = l;
            } else {
                object3[n] = 0L;
                arrl2[n] = 0L;
            }
            if (object2 instanceof Input) {
                object = (Input)object2;
                if (n < this.mArgs.length) {
                    ((Input)object).addReference(this, n);
                } else {
                    ((Input)object).addReference(this, fieldID);
                }
                arrl[n] = 0L;
                arrn[n] = 0;
            } else {
                object = new ValueAndSize((RenderScript)object, object2);
                arrl[n] = ((ValueAndSize)object).value;
                arrn[n] = ((ValueAndSize)object).size;
            }
        }

        @Override
        public void destroy() {
            super.destroy();
            Allocation allocation = this.mReturnValue;
            if (allocation != null) {
                allocation.destroy();
            }
        }

        @Override
        protected void finalize() throws Throwable {
            this.mReturnValue = null;
            super.finalize();
        }

        public Future getGlobal(Script.FieldID fieldID) {
            Object object;
            Object object2 = object = this.mGlobalFuture.get(fieldID);
            if (object == null) {
                object2 = object = this.mBindings.get(fieldID);
                if (object instanceof Future) {
                    object2 = ((Future)object).getValue();
                }
                object2 = new Future(this, fieldID, object2);
                this.mGlobalFuture.put(fieldID, (Future)object2);
            }
            return object2;
        }

        public Future getReturn() {
            if (this.mReturnFuture == null) {
                this.mReturnFuture = new Future(this, null, this.mReturnValue);
            }
            return this.mReturnFuture;
        }

        void setArg(int n, Object object) {
            Object object2 = object;
            if (object instanceof Future) {
                object2 = ((Future)object).getValue();
            }
            this.mArgs[n] = object2;
            object = new ValueAndSize(this.mRS, object2);
            this.mRS.nClosureSetArg(this.getID(this.mRS), n, ((ValueAndSize)object).value, ((ValueAndSize)object).size);
        }

        void setGlobal(Script.FieldID fieldID, Object object) {
            Object object2 = object;
            if (object instanceof Future) {
                object2 = ((Future)object).getValue();
            }
            this.mBindings.put(fieldID, object2);
            object = new ValueAndSize(this.mRS, object2);
            this.mRS.nClosureSetGlobal(this.getID(this.mRS), fieldID.getID(this.mRS), ((ValueAndSize)object).value, ((ValueAndSize)object).size);
        }

        private static final class ValueAndSize {
            public int size;
            public long value;

            public ValueAndSize(RenderScript renderScript, Object object) {
                if (object instanceof Allocation) {
                    this.value = ((Allocation)object).getID(renderScript);
                    this.size = -1;
                } else if (object instanceof Boolean) {
                    long l = (Boolean)object != false ? 1L : 0L;
                    this.value = l;
                    this.size = 4;
                } else if (object instanceof Integer) {
                    this.value = ((Integer)object).longValue();
                    this.size = 4;
                } else if (object instanceof Long) {
                    this.value = (Long)object;
                    this.size = 8;
                } else if (object instanceof Float) {
                    this.value = Float.floatToRawIntBits(((Float)object).floatValue());
                    this.size = 4;
                } else if (object instanceof Double) {
                    this.value = Double.doubleToRawLongBits((Double)object);
                    this.size = 8;
                }
            }
        }

    }

    static class ConnectLine {
        Type mAllocationType;
        Script.KernelID mFrom;
        Script.FieldID mToF;
        Script.KernelID mToK;

        ConnectLine(Type type, Script.KernelID kernelID, Script.FieldID fieldID) {
            this.mFrom = kernelID;
            this.mToF = fieldID;
            this.mAllocationType = type;
        }

        ConnectLine(Type type, Script.KernelID kernelID, Script.KernelID kernelID2) {
            this.mFrom = kernelID;
            this.mToK = kernelID2;
            this.mAllocationType = type;
        }
    }

    public static final class Future {
        Closure mClosure;
        Script.FieldID mFieldID;
        Object mValue;

        Future(Closure closure, Script.FieldID fieldID, Object object) {
            this.mClosure = closure;
            this.mFieldID = fieldID;
            this.mValue = object;
        }

        Closure getClosure() {
            return this.mClosure;
        }

        Script.FieldID getFieldID() {
            return this.mFieldID;
        }

        Object getValue() {
            return this.mValue;
        }
    }

    static class IO {
        Allocation mAllocation;
        Script.KernelID mKID;

        IO(Script.KernelID kernelID) {
            this.mKID = kernelID;
        }
    }

    public static final class Input {
        List<Pair<Closure, Integer>> mArgIndex = new ArrayList<Pair<Closure, Integer>>();
        List<Pair<Closure, Script.FieldID>> mFieldID = new ArrayList<Pair<Closure, Script.FieldID>>();
        Object mValue;

        Input() {
        }

        void addReference(Closure closure, int n) {
            this.mArgIndex.add(Pair.create(closure, n));
        }

        void addReference(Closure closure, Script.FieldID fieldID) {
            this.mFieldID.add(Pair.create(closure, fieldID));
        }

        Object get() {
            return this.mValue;
        }

        void set(Object object) {
            this.mValue = object;
            for (Pair<Closure, Integer> pair : this.mArgIndex) {
                ((Closure)pair.first).setArg((Integer)pair.second, object);
            }
            for (Pair<Closure, Object> pair : this.mFieldID) {
                ((Closure)pair.first).setGlobal((Script.FieldID)pair.second, object);
            }
        }
    }

    static class Node {
        int dagNumber;
        ArrayList<ConnectLine> mInputs = new ArrayList();
        ArrayList<Script.KernelID> mKernels = new ArrayList();
        Node mNext;
        ArrayList<ConnectLine> mOutputs = new ArrayList();
        Script mScript;

        Node(Script script) {
            this.mScript = script;
        }
    }

}

