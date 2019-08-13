/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import sun.security.provider.certpath.BuildStep;
import sun.security.provider.certpath.Vertex;

public class AdjacencyList {
    private List<List<Vertex>> mOrigList;
    private ArrayList<BuildStep> mStepList = new ArrayList();

    public AdjacencyList(List<List<Vertex>> list) {
        this.mOrigList = list;
        this.buildList(list, 0, null);
    }

    private boolean buildList(List<List<Vertex>> list, int n, BuildStep object) {
        Object object2;
        Object object3 = list.get(n);
        boolean bl = true;
        n = 1;
        Object object4 = object3.iterator();
        while (object4.hasNext()) {
            boolean bl2;
            int n2;
            object2 = object4.next();
            if (((Vertex)object2).getIndex() != -1) {
                bl2 = bl;
                n2 = n;
                if (list.get(((Vertex)object2).getIndex()).size() != 0) {
                    bl2 = false;
                    n2 = n;
                }
            } else {
                bl2 = bl;
                n2 = n;
                if (((Vertex)object2).getThrowable() == null) {
                    n2 = 0;
                    bl2 = bl;
                }
            }
            this.mStepList.add(new BuildStep((Vertex)object2, 1));
            bl = bl2;
            n = n2;
        }
        if (bl) {
            if (n != 0) {
                if (object == null) {
                    this.mStepList.add(new BuildStep(null, 4));
                } else {
                    this.mStepList.add(new BuildStep(((BuildStep)object).getVertex(), 2));
                }
                return false;
            }
            list = new ArrayList<List<Vertex>>();
            object = object3.iterator();
            while (object.hasNext()) {
                object3 = (Vertex)object.next();
                if (((Vertex)object3).getThrowable() != null) continue;
                list.add((List<Vertex>)object3);
            }
            if (list.size() == 1) {
                this.mStepList.add(new BuildStep((Vertex)((Object)list.get(0)), 5));
            } else {
                this.mStepList.add(new BuildStep((Vertex)((Object)list.get(0)), 5));
            }
            return true;
        }
        boolean bl3 = false;
        object3 = object3.iterator();
        while (object3.hasNext()) {
            object4 = (Vertex)object3.next();
            boolean bl4 = bl3;
            if (((Vertex)object4).getIndex() != -1) {
                bl4 = bl3;
                if (list.get(((Vertex)object4).getIndex()).size() != 0) {
                    object2 = new BuildStep((Vertex)object4, 3);
                    this.mStepList.add((BuildStep)object2);
                    bl4 = this.buildList(list, ((Vertex)object4).getIndex(), (BuildStep)object2);
                }
            }
            bl3 = bl4;
        }
        if (bl3) {
            return true;
        }
        if (object == null) {
            this.mStepList.add(new BuildStep(null, 4));
        } else {
            this.mStepList.add(new BuildStep(((BuildStep)object).getVertex(), 2));
        }
        return false;
    }

    public Iterator<BuildStep> iterator() {
        return Collections.unmodifiableList(this.mStepList).iterator();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[\n");
        int n = 0;
        for (List<Vertex> list : this.mOrigList) {
            stringBuilder.append("LinkedList[");
            stringBuilder.append(n);
            stringBuilder.append("]:\n");
            Iterator<Vertex> object = list.iterator();
            while (object.hasNext()) {
                stringBuilder.append(object.next().toString());
                stringBuilder.append("\n");
            }
            ++n;
        }
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

