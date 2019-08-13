/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGKEY", "The message key ''{0}'' is not in the message class ''{1}''"};
        Object[] arrobject2 = new Object[]{"BAD_MSGFORMAT", "The format of message ''{0}'' in message class ''{1}'' failed."};
        Object[] arrobject3 = new Object[]{"ER_INVALID_UTF16_SURROGATE", "Invalid UTF-16 surrogate detected: {0} ?"};
        Object[] arrobject4 = new Object[]{"ER_OIERROR", "IO error"};
        Object[] arrobject5 = new Object[]{"ER_COULD_NOT_LOAD_RESOURCE", "Could not load ''{0}'' (check CLASSPATH), now using just the defaults"};
        Object[] arrobject6 = new Object[]{"ER_ILLEGAL_CHARACTER", "Attempt to output character of integral value {0} that is not represented in specified output encoding of {1}."};
        Object[] arrobject7 = new Object[]{"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Could not load the propery file ''{0}'' for output method ''{1}'' (check CLASSPATH)"};
        Object[] arrobject8 = new Object[]{"ER_HOST_ADDRESS_NOT_WELLFORMED", "Host is not a well formed address"};
        Object[] arrobject9 = new Object[]{"ER_SCHEME_FROM_NULL_STRING", "Cannot set scheme from null string"};
        Object[] arrobject10 = new Object[]{"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Path contains invalid escape sequence"};
        Object[] arrobject11 = new Object[]{"ER_PATH_INVALID_CHAR", "Path contains invalid character: {0}"};
        Object[] arrobject12 = new Object[]{"ER_FRAG_INVALID_CHAR", "Fragment contains invalid character"};
        Object[] arrobject13 = new Object[]{"ER_FRAG_WHEN_PATH_NULL", "Fragment cannot be set when path is null"};
        Object[] arrobject14 = new Object[]{"ER_FRAG_FOR_GENERIC_URI", "Fragment can only be set for a generic URI"};
        Object[] arrobject15 = new Object[]{"ER_NO_SCHEME_IN_URI", "No scheme found in URI"};
        Object[] arrobject16 = new Object[]{"ER_CANNOT_INIT_URI_EMPTY_PARMS", "Cannot initialize URI with empty parameters"};
        Object[] arrobject17 = new Object[]{"ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment cannot be specified in both the path and fragment"};
        Object[] arrobject18 = new Object[]{"ER_NO_QUERY_STRING_IN_PATH", "Query string cannot be specified in path and query string"};
        Object[] arrobject19 = new Object[]{"ER_NO_PORT_IF_NO_HOST", "Port may not be specified if host is not specified"};
        Object[] arrobject20 = new Object[]{"ER_XML_VERSION_NOT_SUPPORTED", "Warning:  The version of the output document is requested to be ''{0}''.  This version of XML is not supported.  The version of the output document will be ''1.0''."};
        Object[] arrobject21 = new Object[]{"ER_FACTORY_PROPERTY_MISSING", "The Properties object passed to the SerializerFactory does not have a ''{0}'' property."};
        Object[] arrobject22 = new Object[]{"FEATURE_NOT_SUPPORTED", "The parameter ''{0}'' is recognized but the requested value cannot be set."};
        Object[] arrobject23 = new Object[]{"TYPE_MISMATCH_ERR", "The value type for this parameter name is incompatible with the expected value type."};
        Object[] arrobject24 = new Object[]{"unsupported-encoding", "An unsupported encoding is encountered."};
        Object[] arrobject25 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_COMMENT", "An invalid XML character (Unicode: 0x{0}) was found in the comment."};
        Object[] arrobject26 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "An invalid XML character (Unicode: 0x{0}) was found in the processing instructiondata."};
        Object[] arrobject27 = new Object[]{"wf-invalid-character-in-node-name", "An invalid XML character(s) was found in the {0} node named ''{1}''."};
        Object[] arrobject28 = new Object[]{"ER_WF_DASH_IN_COMMENT", "The string \"--\" is not permitted within comments."};
        Object[] arrobject29 = new Object[]{"ER_WF_LT_IN_ATTVAL", "The value of attribute \"{1}\" associated with an element type \"{0}\" must not contain the ''<'' character."};
        Object[] arrobject30 = new Object[]{"ER_WF_REF_TO_EXTERNAL_ENT", "The external entity reference \"&{0};\" is not permitted in an attribute value."};
        Object[] arrobject31 = new Object[]{"ER_NS_PREFIX_CANNOT_BE_BOUND", "The prefix \"{0}\" can not be bound to namespace \"{1}\"."};
        Object[] arrobject32 = new Object[]{"ER_NULL_LOCAL_ATTR_NAME", "The local name of attr \"{0}\" is null."};
        Object[] arrobject33 = new Object[]{"unbound-prefix-in-entity-reference", "The replacement text of the entity node \"{0}\" contains an element node \"{1}\" with an unbound prefix \"{2}\"."};
        Object[] arrobject34 = new Object[]{"ER_WRITING_INTERNAL_SUBSET", "An error occured while writing the internal subset."};
        return new Object[][]{arrobject, arrobject2, {"ER_SERIALIZER_NOT_CONTENTHANDLER", "The serializer class ''{0}'' does not implement org.xml.sax.ContentHandler."}, {"ER_RESOURCE_COULD_NOT_FIND", "The resource [ {0} ] could not be found.\n {1}"}, {"ER_RESOURCE_COULD_NOT_LOAD", "The resource [ {0} ] could not load: {1} \n {2} \t {3}"}, {"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Buffer size <=0"}, arrobject3, arrobject4, {"ER_ILLEGAL_ATTRIBUTE_POSITION", "Cannot add attribute {0} after child nodes or before an element is produced.  Attribute will be ignored."}, {"ER_NAMESPACE_PREFIX", "Namespace for prefix ''{0}'' has not been declared."}, {"ER_STRAY_ATTRIBUTE", "Attribute ''{0}'' outside of element."}, {"ER_STRAY_NAMESPACE", "Namespace declaration ''{0}''=''{1}'' outside of element."}, arrobject5, arrobject6, arrobject7, {"ER_INVALID_PORT", "Invalid port number"}, {"ER_PORT_WHEN_HOST_NULL", "Port cannot be set when host is null"}, arrobject8, {"ER_SCHEME_NOT_CONFORMANT", "The scheme is not conformant."}, arrobject9, arrobject10, arrobject11, arrobject12, arrobject13, arrobject14, arrobject15, arrobject16, arrobject17, arrobject18, arrobject19, {"ER_NO_USERINFO_IF_NO_HOST", "Userinfo may not be specified if host is not specified"}, arrobject20, {"ER_SCHEME_REQUIRED", "Scheme is required!"}, arrobject21, {"ER_ENCODING_NOT_SUPPORTED", "Warning:  The encoding ''{0}'' is not supported by the Java runtime."}, {"FEATURE_NOT_FOUND", "The parameter ''{0}'' is not recognized."}, arrobject22, {"DOMSTRING_SIZE_ERR", "The resulting string is too long to fit in a DOMString: ''{0}''."}, arrobject23, {"no-output-specified", "The output destination for data to be written to was null."}, arrobject24, {"ER_UNABLE_TO_SERIALIZE_NODE", "The node could not be serialized."}, {"cdata-sections-splitted", "The CDATA Section contains one or more termination markers ']]>'."}, {"ER_WARNING_WF_NOT_CHECKED", "An instance of the Well-Formedness checker could not be created.  The well-formed parameter was set to true but well-formedness checking can not be performed."}, {"wf-invalid-character", "The node ''{0}'' contains invalid XML characters."}, arrobject25, arrobject26, {"ER_WF_INVALID_CHARACTER_IN_CDATA", "An invalid XML character (Unicode: 0x{0}) was found in the contents of the CDATASection."}, {"ER_WF_INVALID_CHARACTER_IN_TEXT", "An invalid XML character (Unicode: 0x{0}) was found in the node''s character data content."}, arrobject27, arrobject28, arrobject29, {"ER_WF_REF_TO_UNPARSED_ENT", "The unparsed entity reference \"&{0};\" is not permitted."}, arrobject30, arrobject31, {"ER_NULL_LOCAL_ELEMENT_NAME", "The local name of element \"{0}\" is null."}, arrobject32, arrobject33, {"unbound-prefix-in-entity-reference", "The replacement text of the entity node \"{0}\" contains an attribute node \"{1}\" with an unbound prefix \"{2}\"."}, arrobject34};
    }
}

