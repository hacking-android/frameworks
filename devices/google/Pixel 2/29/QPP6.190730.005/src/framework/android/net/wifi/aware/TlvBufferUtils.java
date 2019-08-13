/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Memory
 */
package android.net.wifi.aware;

import java.nio.BufferOverflowException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import libcore.io.Memory;

public class TlvBufferUtils {
    private TlvBufferUtils() {
    }

    public static boolean isValid(byte[] arrby, int n, int n2) {
        return TlvBufferUtils.isValidEndian(arrby, n, n2, ByteOrder.BIG_ENDIAN);
    }

    public static boolean isValidEndian(byte[] object, int n, int n2, ByteOrder byteOrder) {
        if (n >= 0 && n <= 2) {
            if (n2 > 0 && n2 <= 2) {
                boolean bl = true;
                if (object == null) {
                    return true;
                }
                int n3 = 0;
                while (n3 + n + n2 <= ((Object)object).length) {
                    n3 += n;
                    if (n2 == 1) {
                        n3 += object[n3] + n2;
                        continue;
                    }
                    n3 += Memory.peekShort((byte[])object, (int)n3, (ByteOrder)byteOrder) + n2;
                }
                if (n3 != ((Object)object).length) {
                    bl = false;
                }
                return bl;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid arguments - lengthSize must be 1 or 2: lengthSize=");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid arguments - typeSize must be 0, 1, or 2: typeSize=");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static class TlvConstructor {
        private byte[] mArray;
        private int mArrayLength;
        private ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
        private int mLengthSize;
        private int mPosition;
        private int mTypeSize;

        public TlvConstructor(int n, int n2) {
            if (n >= 0 && n <= 2 && n2 > 0 && n2 <= 2) {
                this.mTypeSize = n;
                this.mLengthSize = n2;
                this.mPosition = 0;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid sizes - typeSize=");
            stringBuilder.append(n);
            stringBuilder.append(", lengthSize=");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        private void addHeader(int n, int n2) {
            int n3 = this.mTypeSize;
            if (n3 == 1) {
                this.mArray[this.mPosition] = (byte)n;
            } else if (n3 == 2) {
                Memory.pokeShort((byte[])this.mArray, (int)this.mPosition, (short)((short)n), (ByteOrder)this.mByteOrder);
            }
            this.mPosition += this.mTypeSize;
            n = this.mLengthSize;
            if (n == 1) {
                this.mArray[this.mPosition] = (byte)n2;
            } else if (n == 2) {
                Memory.pokeShort((byte[])this.mArray, (int)this.mPosition, (short)((short)n2), (ByteOrder)this.mByteOrder);
            }
            this.mPosition += this.mLengthSize;
        }

        private void checkLength(int n) {
            if (this.mPosition + this.mTypeSize + this.mLengthSize + n <= this.mArrayLength) {
                return;
            }
            throw new BufferOverflowException();
        }

        private void checkRawLength(int n) {
            if (this.mPosition + n <= this.mArrayLength) {
                return;
            }
            throw new BufferOverflowException();
        }

        private int getActualLength() {
            return this.mPosition;
        }

        public TlvConstructor allocate(int n) {
            this.mArray = new byte[n];
            this.mArrayLength = n;
            this.mPosition = 0;
            return this;
        }

        public TlvConstructor allocateAndPut(List<byte[]> object) {
            if (object != null) {
                int n = 0;
                Iterator<byte[]> iterator = object.iterator();
                while (iterator.hasNext()) {
                    int n2;
                    byte[] arrby = iterator.next();
                    n = n2 = n + (this.mTypeSize + this.mLengthSize);
                    if (arrby == null) continue;
                    n = n2 + arrby.length;
                }
                this.allocate(n);
                object = object.iterator();
                while (object.hasNext()) {
                    this.putByteArray(0, (byte[])object.next());
                }
            }
            return this;
        }

        public byte[] getArray() {
            return Arrays.copyOf(this.mArray, this.getActualLength());
        }

        public TlvConstructor putByte(int n, byte by) {
            this.checkLength(1);
            this.addHeader(n, 1);
            byte[] arrby = this.mArray;
            n = this.mPosition;
            this.mPosition = n + 1;
            arrby[n] = by;
            return this;
        }

        public TlvConstructor putByteArray(int n, byte[] arrby) {
            int n2 = arrby == null ? 0 : arrby.length;
            return this.putByteArray(n, arrby, 0, n2);
        }

        public TlvConstructor putByteArray(int n, byte[] arrby, int n2, int n3) {
            this.checkLength(n3);
            this.addHeader(n, n3);
            if (n3 != 0) {
                System.arraycopy(arrby, n2, this.mArray, this.mPosition, n3);
            }
            this.mPosition += n3;
            return this;
        }

        public TlvConstructor putInt(int n, int n2) {
            this.checkLength(4);
            this.addHeader(n, 4);
            Memory.pokeInt((byte[])this.mArray, (int)this.mPosition, (int)n2, (ByteOrder)this.mByteOrder);
            this.mPosition += 4;
            return this;
        }

        public TlvConstructor putRawByte(byte by) {
            this.checkRawLength(1);
            byte[] arrby = this.mArray;
            int n = this.mPosition;
            this.mPosition = n + 1;
            arrby[n] = by;
            return this;
        }

        public TlvConstructor putRawByteArray(byte[] arrby) {
            if (arrby == null) {
                return this;
            }
            this.checkRawLength(arrby.length);
            System.arraycopy(arrby, 0, this.mArray, this.mPosition, arrby.length);
            this.mPosition += arrby.length;
            return this;
        }

        public TlvConstructor putShort(int n, short s) {
            this.checkLength(2);
            this.addHeader(n, 2);
            Memory.pokeShort((byte[])this.mArray, (int)this.mPosition, (short)s, (ByteOrder)this.mByteOrder);
            this.mPosition += 2;
            return this;
        }

        public TlvConstructor putString(int n, String string2) {
            byte[] arrby = null;
            int n2 = 0;
            if (string2 != null) {
                arrby = string2.getBytes();
                n2 = arrby.length;
            }
            return this.putByteArray(n, arrby, 0, n2);
        }

        public TlvConstructor putZeroLengthElement(int n) {
            this.checkLength(0);
            this.addHeader(n, 0);
            return this;
        }

        public TlvConstructor setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
            return this;
        }

        public TlvConstructor wrap(byte[] arrby) {
            this.mArray = arrby;
            int n = arrby == null ? 0 : arrby.length;
            this.mArrayLength = n;
            this.mPosition = 0;
            return this;
        }
    }

    public static class TlvElement {
        public ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        public int length;
        private byte[] mRefArray;
        public int offset;
        public int type;

        private TlvElement(int n, int n2, byte[] arrby, int n3) {
            this.type = n;
            this.length = n2;
            this.mRefArray = arrby;
            this.offset = n3;
            if (n3 + n2 <= arrby.length) {
                return;
            }
            throw new BufferOverflowException();
        }

        public byte getByte() {
            if (this.length == 1) {
                return this.mRefArray[this.offset];
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Accesing a byte from a TLV element of length ");
            stringBuilder.append(this.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public int getInt() {
            if (this.length == 4) {
                return Memory.peekInt((byte[])this.mRefArray, (int)this.offset, (ByteOrder)this.byteOrder);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Accesing an int from a TLV element of length ");
            stringBuilder.append(this.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public byte[] getRawData() {
            byte[] arrby = this.mRefArray;
            int n = this.offset;
            return Arrays.copyOfRange(arrby, n, this.length + n);
        }

        public short getShort() {
            if (this.length == 2) {
                return Memory.peekShort((byte[])this.mRefArray, (int)this.offset, (ByteOrder)this.byteOrder);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Accesing a short from a TLV element of length ");
            stringBuilder.append(this.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public String getString() {
            return new String(this.mRefArray, this.offset, this.length);
        }
    }

    public static class TlvIterable
    implements Iterable<TlvElement> {
        private byte[] mArray;
        private int mArrayLength;
        private ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
        private int mLengthSize;
        private int mTypeSize;

        public TlvIterable(int n, int n2, byte[] object) {
            if (n >= 0 && n <= 2 && n2 > 0 && n2 <= 2) {
                this.mTypeSize = n;
                this.mLengthSize = n2;
                this.mArray = object;
                n = object == null ? 0 : ((Object)object).length;
                this.mArrayLength = n;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid sizes - typeSize=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", lengthSize=");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public Iterator<TlvElement> iterator() {
            return new Iterator<TlvElement>(){
                private int mOffset = 0;

                @Override
                public boolean hasNext() {
                    boolean bl = this.mOffset < TlvIterable.this.mArrayLength;
                    return bl;
                }

                @Override
                public TlvElement next() {
                    if (this.hasNext()) {
                        short s = 0;
                        if (TlvIterable.this.mTypeSize == 1) {
                            s = TlvIterable.this.mArray[this.mOffset];
                        } else if (TlvIterable.this.mTypeSize == 2) {
                            s = Memory.peekShort((byte[])TlvIterable.this.mArray, (int)this.mOffset, (ByteOrder)TlvIterable.this.mByteOrder);
                        }
                        this.mOffset += TlvIterable.this.mTypeSize;
                        short s2 = 0;
                        if (TlvIterable.this.mLengthSize == 1) {
                            s2 = TlvIterable.this.mArray[this.mOffset];
                        } else if (TlvIterable.this.mLengthSize == 2) {
                            s2 = Memory.peekShort((byte[])TlvIterable.this.mArray, (int)this.mOffset, (ByteOrder)TlvIterable.this.mByteOrder);
                        }
                        this.mOffset += TlvIterable.this.mLengthSize;
                        TlvElement tlvElement = new TlvElement(s, s2, TlvIterable.this.mArray, this.mOffset);
                        tlvElement.byteOrder = TlvIterable.this.mByteOrder;
                        this.mOffset += s2;
                        return tlvElement;
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public List<byte[]> toList() {
            ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
            for (TlvElement tlvElement : this) {
                arrayList.add(Arrays.copyOfRange(tlvElement.mRefArray, tlvElement.offset, tlvElement.offset + tlvElement.length));
            }
            return arrayList;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            boolean bl = true;
            for (TlvElement tlvElement : this) {
                StringBuilder stringBuilder2;
                if (!bl) {
                    stringBuilder.append(",");
                }
                bl = false;
                stringBuilder.append(" (");
                if (this.mTypeSize != 0) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("T=");
                    stringBuilder2.append(tlvElement.type);
                    stringBuilder2.append(",");
                    stringBuilder.append(stringBuilder2.toString());
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("L=");
                stringBuilder2.append(tlvElement.length);
                stringBuilder2.append(") ");
                stringBuilder.append(stringBuilder2.toString());
                if (tlvElement.length == 0) {
                    stringBuilder.append("<null>");
                } else if (tlvElement.length == 1) {
                    stringBuilder.append(tlvElement.getByte());
                } else if (tlvElement.length == 2) {
                    stringBuilder.append(tlvElement.getShort());
                } else if (tlvElement.length == 4) {
                    stringBuilder.append(tlvElement.getInt());
                } else {
                    stringBuilder.append("<bytes>");
                }
                if (tlvElement.length == 0) continue;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" (S='");
                stringBuilder2.append(tlvElement.getString());
                stringBuilder2.append("')");
                stringBuilder.append(stringBuilder2.toString());
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

    }

}

