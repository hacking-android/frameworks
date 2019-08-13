/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import java.lang.ref.Reference;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> Iterable<T> dereferenceIterable(final Iterable<? extends Reference<T>> iterable, final boolean bl) {
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>(){
                    private final Iterator<? extends Reference<T>> delegate;
                    private T next;
                    private boolean removeIsOkay;
                    {
                        this.delegate = iterable.iterator();
                    }

                    private void computeNext() {
                        this.removeIsOkay = false;
                        while (this.next == null && this.delegate.hasNext()) {
                            this.next = this.delegate.next().get();
                            if (!bl || this.next != null) continue;
                            this.delegate.remove();
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        this.computeNext();
                        boolean bl = this.next != null;
                        return bl;
                    }

                    @Override
                    public T next() {
                        if (this.hasNext()) {
                            T t = this.next;
                            this.removeIsOkay = true;
                            this.next = null;
                            return t;
                        }
                        throw new IllegalStateException();
                    }

                    @Override
                    public void remove() {
                        if (this.removeIsOkay) {
                            this.delegate.remove();
                            return;
                        }
                        throw new IllegalStateException();
                    }
                };
            }

        };
    }

    public static <T> void removeDuplicates(List<T> list, Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
        int n = 1;
        for (int i = 1; i < list.size(); ++i) {
            int n2 = n;
            if (comparator.compare(list.get(n - 1), list.get(i)) != 0) {
                list.set(n, list.get(i));
                n2 = n + 1;
            }
            n = n2;
        }
        if (n < list.size()) {
            list.subList(n, list.size()).clear();
        }
    }

}

