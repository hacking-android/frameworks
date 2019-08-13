/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages_pt_BR
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGKEY", "A chave de mensagem ''{0}'' n\u00e3o est\u00e1 na classe de mensagem ''{1}''"};
        Object[] arrobject2 = new Object[]{"BAD_MSGFORMAT", "O formato da mensagem ''{0}'' na classe de mensagem ''{1}'' falhou."};
        Object[] arrobject3 = new Object[]{"ER_SERIALIZER_NOT_CONTENTHANDLER", "A classe de serializador ''{0}'' n\u00e3o implementa org.xml.sax.ContentHandler."};
        Object[] arrobject4 = new Object[]{"ER_RESOURCE_COULD_NOT_FIND", "O recurso [ {0} ] n\u00e3o p\u00f4de ser encontrado.\n{1}"};
        Object[] arrobject5 = new Object[]{"ER_RESOURCE_COULD_NOT_LOAD", "O recurso [ {0} ] n\u00e3o p\u00f4de carregar: {1} \n {2} \t {3}"};
        Object[] arrobject6 = new Object[]{"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Tamanho do buffer <=0"};
        Object[] arrobject7 = new Object[]{"ER_INVALID_UTF16_SURROGATE", "Detectado substituto UTF-16 inv\u00e1lido: {0} ?"};
        Object[] arrobject8 = new Object[]{"ER_OIERROR", "Erro de E/S"};
        Object[] arrobject9 = new Object[]{"ER_NAMESPACE_PREFIX", "O espa\u00e7o de nomes do prefixo ''{0}'' n\u00e3o foi declarado. "};
        Object[] arrobject10 = new Object[]{"ER_STRAY_ATTRIBUTE", "Atributo ''{0}'' fora do elemento. "};
        Object[] arrobject11 = new Object[]{"ER_STRAY_NAMESPACE", "Declara\u00e7\u00e3o de espa\u00e7o de nomes ''{0}''=''{1}'' fora do elemento. "};
        Object[] arrobject12 = new Object[]{"ER_ILLEGAL_CHARACTER", "Tentativa de processar o caractere de um valor integral {0} que n\u00e3o \u00e9 representado na codifica\u00e7\u00e3o de sa\u00edda especificada de {1}."};
        Object[] arrobject13 = new Object[]{"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "N\u00e3o foi poss\u00edvel carregar o arquivo de propriedade ''{0}'' para o m\u00e9todo de sa\u00edda ''{1}'' (verifique CLASSPATH)"};
        Object[] arrobject14 = new Object[]{"ER_INVALID_PORT", "N\u00famero de porta inv\u00e1lido"};
        Object[] arrobject15 = new Object[]{"ER_HOST_ADDRESS_NOT_WELLFORMED", "O host n\u00e3o \u00e9 um endere\u00e7o formado corretamente"};
        Object[] arrobject16 = new Object[]{"ER_SCHEME_FROM_NULL_STRING", "Imposs\u00edvel definir esquema a partir da cadeia nula"};
        Object[] arrobject17 = new Object[]{"ER_PATH_INVALID_CHAR", "O caminho cont\u00e9m caractere inv\u00e1lido: {0}"};
        Object[] arrobject18 = new Object[]{"ER_FRAG_WHEN_PATH_NULL", "O fragmento n\u00e3o pode ser definido quando o caminho \u00e9 nulo"};
        Object[] arrobject19 = new Object[]{"ER_FRAG_FOR_GENERIC_URI", "O fragmento s\u00f3 pode ser definido para um URI gen\u00e9rico"};
        Object[] arrobject20 = new Object[]{"ER_NO_SCHEME_IN_URI", "Nenhum esquema encontrado no URI"};
        Object[] arrobject21 = new Object[]{"ER_NO_FRAGMENT_STRING_IN_PATH", "O fragmento n\u00e3o pode ser especificado no caminho e fragmento"};
        Object[] arrobject22 = new Object[]{"ER_NO_QUERY_STRING_IN_PATH", "A cadeia de consulta n\u00e3o pode ser especificada na cadeia de consulta e caminho"};
        Object[] arrobject23 = new Object[]{"ER_NO_PORT_IF_NO_HOST", "Port n\u00e3o pode ser especificado se host n\u00e3o for especificado"};
        Object[] arrobject24 = new Object[]{"ER_FACTORY_PROPERTY_MISSING", "O objeto Properties transmitido para SerializerFactory n\u00e3o tem uma propriedade ''{0}''."};
        Object[] arrobject25 = new Object[]{"FEATURE_NOT_SUPPORTED", "O par\u00e2metro ''{0}'' \u00e9 reconhecido, mas o valor pedido n\u00e3o pode ser definido. "};
        Object[] arrobject26 = new Object[]{"TYPE_MISMATCH_ERR", "O tipo de valor para este nome de par\u00e2metro \u00e9 incompat\u00edvel com o tipo de valor esperado. "};
        Object[] arrobject27 = new Object[]{"unsupported-encoding", "Uma codifica\u00e7\u00e3o n\u00e3o suportada foi encontrada. "};
        Object[] arrobject28 = new Object[]{"cdata-sections-splitted", "A Se\u00e7\u00e3o CDATA cont\u00e9m um ou mais marcadores de t\u00e9rmino ']]>'."};
        Object[] arrobject29 = new Object[]{"wf-invalid-character", "O n\u00f3 ''{0}'' cont\u00e9m caracteres XML inv\u00e1lidos. "};
        Object[] arrobject30 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_COMMENT", "Um caractere XML inv\u00e1lido (Unicode: 0x{0}) foi encontrado no coment\u00e1rio. "};
        Object[] arrobject31 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "Um caractere XML inv\u00e1lido (Unicode: 0x{0}) foi encontrado no processo instructiondata."};
        Object[] arrobject32 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_CDATA", "Um caractere XML inv\u00e1lido (Unicode: 0x{0}) foi encontrado nos conte\u00fados do CDATASection. "};
        Object[] arrobject33 = new Object[]{"ER_WF_REF_TO_UNPARSED_ENT", "A refer\u00eancia de entidade n\u00e3o analisada \"&{0};\" n\u00e3o \u00e9 permitida. "};
        return new Object[][]{arrobject, arrobject2, arrobject3, arrobject4, arrobject5, arrobject6, arrobject7, arrobject8, {"ER_ILLEGAL_ATTRIBUTE_POSITION", "Imposs\u00edvel incluir atributo {0} depois de n\u00f3s filhos ou antes da gera\u00e7\u00e3o de um elemento. O atributo ser\u00e1 ignorado."}, arrobject9, arrobject10, arrobject11, {"ER_COULD_NOT_LOAD_RESOURCE", "N\u00e3o foi poss\u00edvel carregar ''{0}'' (verifique CLASSPATH) agora , utilizando somente os padr\u00f5es"}, arrobject12, arrobject13, arrobject14, {"ER_PORT_WHEN_HOST_NULL", "A porta n\u00e3o pode ser definida quando o host \u00e9 nulo"}, arrobject15, {"ER_SCHEME_NOT_CONFORMANT", "O esquema n\u00e3o est\u00e1 em conformidade."}, arrobject16, {"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "O caminho cont\u00e9m seq\u00fc\u00eancia de escape inv\u00e1lida"}, arrobject17, {"ER_FRAG_INVALID_CHAR", "O fragmento cont\u00e9m caractere inv\u00e1lido"}, arrobject18, arrobject19, arrobject20, {"ER_CANNOT_INIT_URI_EMPTY_PARMS", "Imposs\u00edvel inicializar URI com par\u00e2metros vazios"}, arrobject21, arrobject22, arrobject23, {"ER_NO_USERINFO_IF_NO_HOST", "Userinfo n\u00e3o pode ser especificado se host n\u00e3o for especificado"}, {"ER_XML_VERSION_NOT_SUPPORTED", "Aviso:  A vers\u00e3o do documento de sa\u00edda precisa ser ''{0}''.  Essa vers\u00e3o do XML n\u00e3o \u00e9 suportada. A vers\u00e3o do documento de sa\u00edda ser\u00e1 ''1.0''."}, {"ER_SCHEME_REQUIRED", "O esquema \u00e9 obrigat\u00f3rio!"}, arrobject24, {"ER_ENCODING_NOT_SUPPORTED", "Aviso:  A codifica\u00e7\u00e3o ''{0}'' n\u00e3o \u00e9 suportada pelo Java Runtime."}, {"FEATURE_NOT_FOUND", "O par\u00e2metro ''{0}'' n\u00e3o \u00e9 reconhecido."}, arrobject25, {"DOMSTRING_SIZE_ERR", "A cadeia resultante \u00e9 muito longa para caber em uma DOMString: ''{0}''. "}, arrobject26, {"no-output-specified", "O destino de sa\u00edda para os dados a serem gravados era nulo. "}, arrobject27, {"ER_UNABLE_TO_SERIALIZE_NODE", "O n\u00f3 n\u00e3o p\u00f4de ser serializado."}, arrobject28, {"ER_WARNING_WF_NOT_CHECKED", "Uma inst\u00e2ncia do verificador Well-Formedness n\u00e3o p\u00f4de ser criada. O par\u00e2metro well-formed foi definido como true, mas a verifica\u00e7\u00e3o well-formedness n\u00e3o pode ser executada."}, arrobject29, arrobject30, arrobject31, arrobject32, {"ER_WF_INVALID_CHARACTER_IN_TEXT", "Um caractere XML inv\u00e1lido (Unicode: 0x{0}) foi encontrado no conte\u00fado dos dados de caractere dos n\u00f3s. "}, {"wf-invalid-character-in-node-name", "Um caractere inv\u00e1lido foi encontrado no {0} do n\u00f3 denominado ''{1}''."}, {"ER_WF_DASH_IN_COMMENT", "A cadeia \"--\" n\u00e3o \u00e9 permitida dentro dos coment\u00e1rios. "}, {"ER_WF_LT_IN_ATTVAL", "O valor do atributo \"{1}\" associado a um tipo de elemento \"{0}\" n\u00e3o deve conter o caractere ''<''. "}, arrobject33, {"ER_WF_REF_TO_EXTERNAL_ENT", "A refer\u00eancia de entidade externa \"&{0};\" n\u00e3o \u00e9 permitida em um valor de atributo. "}, {"ER_NS_PREFIX_CANNOT_BE_BOUND", "O prefixo \"{0}\" n\u00e3o pode ser vinculado ao espa\u00e7o de nomes \"{1}\"."}, {"ER_NULL_LOCAL_ELEMENT_NAME", "O nome local do elemento \"{0}\" \u00e9 nulo."}, {"ER_NULL_LOCAL_ATTR_NAME", "O nome local do atributo \"{0}\" \u00e9 nulo."}, {"unbound-prefix-in-entity-reference", "O texto de substitui\u00e7\u00e3o do n\u00f3 de entidade \"{0}\" cont\u00e9m um n\u00f3 de elemento \"{1}\" com um prefixo n\u00e3o vinculado \"{2}\"."}, {"unbound-prefix-in-entity-reference", "O texto de substitui\u00e7\u00e3o do n\u00f3 de entidade \"{0}\" cont\u00e9m um n\u00f3 de atributo \"{1}\" com um prefixo n\u00e3o vinculado \"{2}\"."}};
    }
}

