/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.HeaderFactoryImpl;
import gov.nist.javax.sip.message.Content;
import gov.nist.javax.sip.message.ContentImpl;
import gov.nist.javax.sip.message.MultipartMimeContent;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.sip.header.ContentDispositionHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.Header;

public class MultipartMimeContentImpl
implements MultipartMimeContent {
    public static String BOUNDARY = "boundary";
    private String boundary;
    private List<Content> contentList = new LinkedList<Content>();
    private ContentTypeHeader multipartMimeContentTypeHeader;

    public MultipartMimeContentImpl(ContentTypeHeader contentTypeHeader) {
        this.multipartMimeContentTypeHeader = contentTypeHeader;
        this.boundary = contentTypeHeader.getParameter(BOUNDARY);
    }

    @Override
    public boolean add(Content content) {
        return this.contentList.add((ContentImpl)content);
    }

    @Override
    public void addContent(Content content) {
        this.add(content);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void createContentList(String object) throws ParseException {
        Object object2 = "\r\n";
        try {
            Object object3 = new HeaderFactoryImpl();
            String[] arrstring = this.getContentTypeHeader().getParameter(BOUNDARY);
            if (arrstring == null) {
                object2 = new LinkedList();
                this.contentList = object2;
                object2 = new ContentImpl((String)object, (String)arrstring);
                ((ContentImpl)object2).setContentTypeHeader(this.getContentTypeHeader());
                this.contentList.add((Content)object2);
                return;
            }
            Object object4 = new StringBuilder();
            ((StringBuilder)object4).append("--");
            ((StringBuilder)object4).append((String)arrstring);
            ((StringBuilder)object4).append("\r\n");
            arrstring = ((String)object).split(((StringBuilder)object4).toString());
            int n = arrstring.length;
            object = object3;
            for (int i = 0; i < n; ++i) {
                block13 : {
                    block14 : {
                        object4 = arrstring[i];
                        if (object4 == null) {
                            return;
                        }
                        object3 = new StringBuffer((String)object4);
                        while (((StringBuffer)object3).length() > 0 && (((StringBuffer)object3).charAt(0) == '\r' || ((StringBuffer)object3).charAt(0) == '\n')) {
                            ((StringBuffer)object3).deleteCharAt(0);
                        }
                        if (((StringBuffer)object3).length() == 0) continue;
                        object3 = ((StringBuffer)object3).toString();
                        int n2 = ((String)object3).indexOf("\r\n\r\n");
                        int n3 = 4;
                        int n4 = n2;
                        if (n2 == -1) {
                            n4 = ((String)object3).indexOf("\n");
                            n3 = 2;
                        }
                        if (n4 == -1) break block13;
                        object4 = ((String)object3).substring(n4 + n3);
                        if (object4 == null) break block14;
                        String[] arrstring2 = ((String)object3).substring(0, n4);
                        object3 = new ContentImpl((String)object4, this.boundary);
                        arrstring2 = arrstring2.split((String)object2);
                        n4 = arrstring2.length;
                        for (n3 = 0; n3 < n4; ++n3) {
                            block17 : {
                                block16 : {
                                    block15 : {
                                        object4 = object.createHeader(arrstring2[n3]);
                                        if (!(object4 instanceof ContentTypeHeader)) break block15;
                                        ((ContentImpl)object3).setContentTypeHeader((ContentTypeHeader)object4);
                                        break block16;
                                    }
                                    if (!(object4 instanceof ContentDispositionHeader)) break block17;
                                    ((ContentImpl)object3).setContentDispositionHeader((ContentDispositionHeader)object4);
                                }
                                this.contentList.add((Content)object3);
                                continue;
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Unexpected header type ");
                            ((StringBuilder)object2).append(object4.getName());
                            object = new ParseException(((StringBuilder)object2).toString(), 0);
                            throw object;
                        }
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("No content [");
                    ((StringBuilder)object2).append((String)object3);
                    ((StringBuilder)object2).append("]");
                    object = new ParseException(((StringBuilder)object2).toString(), 0);
                    throw object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("no content type header found in ");
                ((StringBuilder)object).append((String)object3);
                object2 = new ParseException(((StringBuilder)object).toString(), 0);
                throw object2;
            }
            return;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            throw new ParseException("Invalid Multipart mime format", 0);
        }
    }

    public Content getContentByType(String string, String string2) {
        Object var3_3 = null;
        List<Content> list = this.contentList;
        if (list == null) {
            return null;
        }
        Iterator<Content> iterator = list.iterator();
        do {
            list = var3_3;
        } while (iterator.hasNext() && (!(list = iterator.next()).getContentTypeHeader().getContentType().equalsIgnoreCase(string) || !list.getContentTypeHeader().getContentSubType().equalsIgnoreCase(string2)));
        return list;
    }

    @Override
    public int getContentCount() {
        return this.contentList.size();
    }

    @Override
    public ContentTypeHeader getContentTypeHeader() {
        return this.multipartMimeContentTypeHeader;
    }

    @Override
    public Iterator<Content> getContents() {
        return this.contentList.iterator();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Content> iterator = this.contentList.iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next().toString());
        }
        return stringBuffer.toString();
    }
}

