package org.talend.designer.codegen.translators.file.input;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.Map;
import java.util.List;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.utils.NodeUtil;

public class TFileInputJSONBeginJava
{
  protected static String nl;
  public static synchronized TFileInputJSONBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TFileInputJSONBeginJava result = new TFileInputJSONBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t\t\tlog.debug(\"";
  protected final String TEXT_2 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
  protected final String TEXT_3 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_4 = " - Retrieved records count: \"+ nb_line_";
  protected final String TEXT_5 = " + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_6 = " - Retrieved records count: \"+ globalMap.get(\"";
  protected final String TEXT_7 = "_NB_LINE\") + \" .\");" + NL + "\t\t\t";
  protected final String TEXT_8 = " - Written records count: \" + nb_line_";
  protected final String TEXT_9 = NL + "\t\t\t\tfinal StringBuffer log4jSb_";
  protected final String TEXT_10 = " = new StringBuffer();" + NL + "\t\t\t";
  protected final String TEXT_11 = " - Retrieving the record \" + (nb_line_";
  protected final String TEXT_12 = ") + \".\");" + NL + "\t\t\t";
  protected final String TEXT_13 = " - Writing the record \" + nb_line_";
  protected final String TEXT_14 = " + \" to the file.\");" + NL + "\t\t\t";
  protected final String TEXT_15 = " - Processing the record \" + nb_line_";
  protected final String TEXT_16 = " + \".\");" + NL + "\t\t\t";
  protected final String TEXT_17 = " - Processed records count: \" + nb_line_";
  protected final String TEXT_18 = NL + "                log.error(message_";
  protected final String TEXT_19 = ");";
  protected final String TEXT_20 = NL + "                System.err.println(message_";
  protected final String TEXT_21 = NL + "\t";
  protected final String TEXT_22 = NL + NL + "    int nb_line_";
  protected final String TEXT_23 = " = 0;" + NL + "" + NL + "    class XML_API_";
  protected final String TEXT_24 = "{" + NL + "        public boolean isDefNull(org.dom4j.Node node) throws javax.xml.transform.TransformerException {" + NL + "            if (node != null && node instanceof org.dom4j.Element) {" + NL + "                org.dom4j.Attribute attri = ((org.dom4j.Element)node).attribute(\"nil\");" + NL + "                if(attri != null && (\"true\").equals(attri.getText())){" + NL + "                    return true;" + NL + "                }" + NL + "            }" + NL + "            return false;" + NL + "        }" + NL + "    " + NL + "        public boolean isMissing(org.dom4j.Node node) throws javax.xml.transform.TransformerException {" + NL + "            return node == null ? true : false;" + NL + "        }" + NL + "    " + NL + "        public boolean isEmpty(org.dom4j.Node node) throws javax.xml.transform.TransformerException {" + NL + "            if (node != null) {" + NL + "                return node.getText().length() == 0;" + NL + "            }" + NL + "            return false;" + NL + "        }" + NL + "    }" + NL + "" + NL + "    class ConvertJSONString_";
  protected final String TEXT_25 = "{" + NL + "        final static int Brace = 0 ; // {" + NL + "        final static int Bracket = 1; // [" + NL + "        private int barceType = -1 ;" + NL + "        private String originalJsonString = \"\" ;" + NL + "        private String originalLoopString = \"\" ;" + NL + "        private String jsonString4XML = null;" + NL + "        private String loopString4XML = null;" + NL + "        private String additionRoot = null;" + NL + "        " + NL + "        public void barceType(){" + NL + "" + NL + "            for (int c = 0; c < originalJsonString.length(); ++c) {" + NL + "                if (originalJsonString.charAt(c) == '{') {" + NL + "                    barceType = Brace;" + NL + "                    break;" + NL + "                } else if (originalJsonString.charAt(c) == '[') {" + NL + "                    barceType = Bracket;" + NL + "                    break;" + NL + "                }" + NL + "            }" + NL + "        }" + NL + "" + NL + "        public void setJsonString (String originalJsonString) {" + NL + "            this.originalJsonString = originalJsonString;" + NL + "        }" + NL + "        " + NL + "        public void setLoopString (String originalLoopString) {" + NL + "            this.originalLoopString = originalLoopString;" + NL + "        }" + NL + "        " + NL + "        public String getJsonString4XML(){" + NL + "            return jsonString4XML;" + NL + "        }" + NL + "        " + NL + "        public String getLoopString4XML(){" + NL + "            if(loopString4XML.length()>1 && loopString4XML.endsWith(\"/\")){" + NL + "                loopString4XML = loopString4XML.substring(0, loopString4XML.length()-1);" + NL + "            }" + NL + "            return loopString4XML;" + NL + "        }" + NL + "    " + NL + "        public void setAdditionRoot (String additionRoot) {" + NL + "            this.additionRoot = additionRoot;" + NL + "        }" + NL + "                    " + NL + "        public String getAdditionRoot (){" + NL + "            return additionRoot;" + NL + "        }" + NL + "                    " + NL + "        " + NL + "        public void generate() {" + NL + "            barceType();" + NL + "            jsonString4XML = originalJsonString;" + NL + "            loopString4XML = originalLoopString;" + NL + "            if (Brace == barceType) {" + NL + "                if (isNeedAddRoot(originalJsonString)) {" + NL + "                    jsonString4XML = \"{ \\\"root\\\": \" + originalJsonString + \" }\";" + NL + "                    loopString4XML = \"root\" + originalLoopString;" + NL + "                    setAdditionRoot(\"root\");" + NL + "                }" + NL + "            } else if (Bracket == barceType) {" + NL + "                jsonString4XML = \"{ \\\"root\\\" : { \\\"object\\\": \"" + NL + "                        + originalJsonString + \" } }\";" + NL + "                loopString4XML = \"root/object\" + originalLoopString;" + NL + "                setAdditionRoot(\"object\");" + NL + "            }" + NL + "        }" + NL + "" + NL + "        public boolean isNeedAddRoot(String originalJsonString) {" + NL + "            boolean isNeedAddRoot = false;" + NL + "            net.sf.json.JSONObject jso = net.sf.json.JSONObject" + NL + "                    .fromObject(originalJsonString);" + NL + "            String jsonKey = \"\";" + NL + "            Object firstObject = null;" + NL + "            if (jso.names().size() == 1) {" + NL + "                jsonKey = jso.names().get(0).toString();" + NL + "                firstObject = jso.get(jsonKey);" + NL + "            }" + NL + "            if (jso.size() > 1" + NL + "                    || (firstObject != null" + NL + "                            && firstObject instanceof net.sf.json.JSONArray && ((net.sf.json.JSONArray) firstObject)" + NL + "                            .size() > 1)) {" + NL + "                isNeedAddRoot = true;" + NL + "            }" + NL + "            return isNeedAddRoot;" + NL + "        }" + NL + "                " + NL + "    }" + NL + "" + NL + "        ConvertJSONString_";
  protected final String TEXT_26 = " cjs_";
  protected final String TEXT_27 = " = new ConvertJSONString_";
  protected final String TEXT_28 = "();" + NL + "        " + NL + "        de.odysseus.staxon.json.JsonXMLConfig config_";
  protected final String TEXT_29 = " = new de.odysseus.staxon.json.JsonXMLConfigBuilder().multiplePI(false).build();" + NL + "        de.odysseus.staxon.json.JsonXMLInputFactory jsonXMLInputFactory_";
  protected final String TEXT_30 = " = new de.odysseus.staxon.json.JsonXMLInputFactory(config_";
  protected final String TEXT_31 = ");" + NL + "        javax.xml.stream.XMLOutputFactory xmlOutputFactory_";
  protected final String TEXT_32 = " = javax.xml.stream.XMLOutputFactory.newInstance();" + NL + "        boolean isGetWholeJson_";
  protected final String TEXT_33 = " = false;" + NL;
  protected final String TEXT_34 = NL + "            class OriginalJSONString_";
  protected final String TEXT_35 = " {" + NL + "" + NL + "                String originalJSONString = null;" + NL + "                java.io.ByteArrayInputStream bais = null;" + NL + "                java.io.ByteArrayOutputStream baos = null;" + NL + "                de.odysseus.staxon.json.JsonXMLConfig config = null;" + NL + "                de.odysseus.staxon.json.JsonXMLOutputFactory jxof = null;" + NL + "                " + NL + "                public String getOriginalJSONString(String xmlString,String additionRoot,String encoding,boolean isGetWholeJson) throws Exception {" + NL + "" + NL + "                    try {" + NL + "                        bais = new ByteArrayInputStream(xmlString.getBytes(encoding));" + NL + "                        baos = new java.io.ByteArrayOutputStream();" + NL + "                        config = new de.odysseus.staxon.json.JsonXMLConfigBuilder().multiplePI(false).autoArray(true).build();" + NL + "                        jxof = new de.odysseus.staxon.json.JsonXMLOutputFactory(config);" + NL + "                        javax.xml.stream.XMLEventReader xmlEventReader = javax.xml.stream.XMLInputFactory.newInstance().createXMLEventReader(bais);" + NL + "                        javax.xml.stream.XMLEventWriter xmLEventWriter = jxof.createXMLEventWriter(baos,encoding);" + NL + "                        xmLEventWriter.add(xmlEventReader);" + NL + "                        xmlEventReader.close();" + NL + "                        xmLEventWriter.close();" + NL + "                        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(baos.toString());" + NL + "                        net.sf.json.JSONObject originalJsonObject = null;" + NL + "                        if (!json.isNullObject()) {" + NL + "                            if (additionRoot == null) {" + NL + "                                originalJSONString = json.toString();" + NL + "                            } else {" + NL + "                                if (isGetWholeJson) {" + NL + "                                    originalJsonObject = json.getJSONObject(additionRoot);" + NL + "                                    if (!originalJsonObject.isNullObject()) {" + NL + "                                        originalJSONString = originalJsonObject.toString();" + NL + "                                    } " + NL + "                                }else {" + NL + "                                        originalJSONString = json.toString();" + NL + "                                }" + NL + "                            }" + NL + "                        }" + NL + "                    } finally {" + NL + "                        baos.close();" + NL + "                        if(bais!=null){" + NL + "                            bais.close();" + NL + "                        }" + NL + "                    }" + NL + "                    " + NL + "                    return originalJSONString;" + NL + "                }" + NL + "            }" + NL + "            " + NL + "            OriginalJSONString_";
  protected final String TEXT_36 = " originalJSONString_";
  protected final String TEXT_37 = " = new OriginalJSONString_";
  protected final String TEXT_38 = "();";
  protected final String TEXT_39 = "        " + NL + "        " + NL + "        org.dom4j.io.SAXReader reader_";
  protected final String TEXT_40 = " = new org.dom4j.io.SAXReader();" + NL + "        Object filenameOrStream_";
  protected final String TEXT_41 = " = null;" + NL + "        try {" + NL + "            filenameOrStream_";
  protected final String TEXT_42 = " = ";
  protected final String TEXT_43 = ";" + NL + "        } catch(java.lang.Exception e_";
  protected final String TEXT_44 = ") {" + NL + "globalMap.put(\"";
  protected final String TEXT_45 = "_ERROR_MESSAGE\",e_";
  protected final String TEXT_46 = ".getMessage());";
  protected final String TEXT_47 = NL + "            \tthrow(e_";
  protected final String TEXT_48 = NL + "\t\t\t\t";
  protected final String TEXT_49 = NL + "\t\t\t\t\tlog.error(\"";
  protected final String TEXT_50 = " - \" + e_";
  protected final String TEXT_51 = ".getMessage());" + NL + "\t\t\t\t";
  protected final String TEXT_52 = NL + "\t            System.err.println(e_";
  protected final String TEXT_53 = NL + "        }" + NL + "                " + NL + "        boolean isValidFile_";
  protected final String TEXT_54 = " = true;" + NL + "        org.dom4j.Document doc_";
  protected final String TEXT_55 = " = null;" + NL + "        java.io.BufferedReader br_";
  protected final String TEXT_56 = " = null;" + NL + "        java.lang.StringBuilder jsonBuffer_";
  protected final String TEXT_57 = " =  new java.lang.StringBuilder(\"\");" + NL + "        String jsonStr_";
  protected final String TEXT_58 = " = null;" + NL + "        String xmlStr_";
  protected final String TEXT_59 = " = \"\";" + NL + "        String line_";
  protected final String TEXT_60 = " = null;" + NL + "        " + NL + "        String loopQuery_";
  protected final String TEXT_61 = " =  ";
  protected final String TEXT_62 = " ;" + NL + "        java.io.ByteArrayInputStream bais_";
  protected final String TEXT_63 = " = null;" + NL + "        java.io.ByteArrayOutputStream  baos_";
  protected final String TEXT_64 = " = new java.io.ByteArrayOutputStream();" + NL + "            " + NL + "        try{" + NL + "            if(filenameOrStream_";
  protected final String TEXT_65 = " instanceof java.io.InputStream){" + NL + "                br_";
  protected final String TEXT_66 = " = new java.io.BufferedReader(new java.io.InputStreamReader((java.io.InputStream)filenameOrStream_";
  protected final String TEXT_67 = ",";
  protected final String TEXT_68 = "));" + NL + "            }else{";
  protected final String TEXT_69 = NL + "            \t    br_";
  protected final String TEXT_70 = " = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(String.valueOf(filenameOrStream_";
  protected final String TEXT_71 = ")), ";
  protected final String TEXT_72 = "));";
  protected final String TEXT_73 = NL + "\t\t\t\t\tjava.net.URL url_";
  protected final String TEXT_74 = " = new java.net.URL(";
  protected final String TEXT_75 = ");" + NL + "\t\t\t\t\tjava.net.URLConnection urlConn_";
  protected final String TEXT_76 = " = url_";
  protected final String TEXT_77 = ".openConnection();" + NL + "\t\t\t\t\tbr_";
  protected final String TEXT_78 = " = new java.io.BufferedReader( new java.io.InputStreamReader(urlConn_";
  protected final String TEXT_79 = ".getInputStream(),";
  protected final String TEXT_80 = NL + "            }" + NL + "            while ((line_";
  protected final String TEXT_81 = " = br_";
  protected final String TEXT_82 = ".readLine()) != null){" + NL + "                jsonBuffer_";
  protected final String TEXT_83 = ".append(line_";
  protected final String TEXT_84 = ");" + NL + "            }" + NL + "            jsonStr_";
  protected final String TEXT_85 = " = jsonBuffer_";
  protected final String TEXT_86 = ".toString();" + NL + "            cjs_";
  protected final String TEXT_87 = ".setJsonString(jsonStr_";
  protected final String TEXT_88 = ");" + NL + "            cjs_";
  protected final String TEXT_89 = ".setLoopString(loopQuery_";
  protected final String TEXT_90 = ".generate();" + NL + "            jsonStr_";
  protected final String TEXT_91 = " = cjs_";
  protected final String TEXT_92 = ".getJsonString4XML();" + NL + "            loopQuery_";
  protected final String TEXT_93 = ".getLoopString4XML();" + NL + "            bais_";
  protected final String TEXT_94 = " = new ByteArrayInputStream(jsonStr_";
  protected final String TEXT_95 = ".getBytes(";
  protected final String TEXT_96 = "));" + NL + "            javax.xml.stream.XMLEventReader xmlEventReader_";
  protected final String TEXT_97 = " = jsonXMLInputFactory_";
  protected final String TEXT_98 = ".createXMLEventReader(bais_";
  protected final String TEXT_99 = ");" + NL + "            javax.xml.stream.XMLEventWriter xmLEventWriter_";
  protected final String TEXT_100 = " = xmlOutputFactory_";
  protected final String TEXT_101 = ".createXMLEventWriter(baos_";
  protected final String TEXT_102 = ");" + NL + "            xmLEventWriter_";
  protected final String TEXT_103 = ".add(xmlEventReader_";
  protected final String TEXT_104 = ");" + NL + "            //convert json string to xml" + NL + "            xmlStr_";
  protected final String TEXT_105 = " = baos_";
  protected final String TEXT_106 = ".toString();  " + NL + "            xmLEventWriter_";
  protected final String TEXT_107 = ".close();" + NL + "            xmlEventReader_";
  protected final String TEXT_108 = ".close();" + NL + "            doc_";
  protected final String TEXT_109 = "= reader_";
  protected final String TEXT_110 = ".read(new java.io.StringReader(xmlStr_";
  protected final String TEXT_111 = "));" + NL + "        }catch(java.lang.Exception e_";
  protected final String TEXT_112 = "){" + NL + "globalMap.put(\"";
  protected final String TEXT_113 = NL + "                throw(e_";
  protected final String TEXT_114 = NL + "\t\t\t\t\t";
  protected final String TEXT_115 = NL + "\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_116 = ".getMessage());" + NL + "\t\t\t\t\t";
  protected final String TEXT_117 = NL + "                    System.err.println( e_";
  protected final String TEXT_118 = ".getMessage());" + NL + "                    isValidFile_";
  protected final String TEXT_119 = " = false;";
  protected final String TEXT_120 = NL + "        } finally {" + NL + "            if(br_";
  protected final String TEXT_121 = "!=null) {" + NL + "                br_";
  protected final String TEXT_122 = ".close();" + NL + "            }" + NL + "            baos_";
  protected final String TEXT_123 = ".close();" + NL + "            if(bais_";
  protected final String TEXT_124 = "!=null){" + NL + "                bais_";
  protected final String TEXT_125 = ".close();" + NL + "            }" + NL + "        }" + NL + "        if(isValidFile_";
  protected final String TEXT_126 = "){" + NL + "    " + NL + "            org.dom4j.XPath x_";
  protected final String TEXT_127 = " = doc_";
  protected final String TEXT_128 = ".createXPath(loopQuery_";
  protected final String TEXT_129 = "); " + NL + "            java.util.HashMap<String,String> xmlNameSpaceMap_";
  protected final String TEXT_130 = " = new java.util.HashMap<String, String>();" + NL + "            x_";
  protected final String TEXT_131 = "); " + NL + "            x_";
  protected final String TEXT_132 = ".setNamespaceURIs(xmlNameSpaceMap_";
  protected final String TEXT_133 = "); " + NL + "            java.util.List<org.dom4j.Node> nodeList_";
  protected final String TEXT_134 = " = (java.util.List<org.dom4j.Node>)x_";
  protected final String TEXT_135 = ".selectNodes(doc_";
  protected final String TEXT_136 = ");  " + NL + "            XML_API_";
  protected final String TEXT_137 = " xml_api_";
  protected final String TEXT_138 = " = new XML_API_";
  protected final String TEXT_139 = "();" + NL + "            String str_";
  protected final String TEXT_140 = " = \"\";" + NL + "            org.dom4j.Node node_";
  protected final String TEXT_141 = " = null;" + NL + "    " + NL + "            //init all mapping xpaths";
  protected final String TEXT_142 = NL + "                org.dom4j.XPath xTmp";
  protected final String TEXT_143 = "_";
  protected final String TEXT_144 = " = org.dom4j.DocumentHelper.createXPath(";
  protected final String TEXT_145 = ");" + NL + "                xTmp";
  protected final String TEXT_146 = NL + NL + "            for (org.dom4j.Node temp_";
  protected final String TEXT_147 = ": nodeList_";
  protected final String TEXT_148 = ") {" + NL + "                nb_line_";
  protected final String TEXT_149 = "++;";
  protected final String TEXT_150 = NL + "    ";
  protected final String TEXT_151 = " = null;            ";
  protected final String TEXT_152 = NL + "                    boolean whetherReject_";
  protected final String TEXT_153 = NL + "                    ";
  protected final String TEXT_154 = " = new ";
  protected final String TEXT_155 = "Struct();" + NL + "                    try{";
  protected final String TEXT_156 = NL + "                                isGetWholeJson_";
  protected final String TEXT_157 = "  = true;";
  protected final String TEXT_158 = NL + "                        Object obj";
  protected final String TEXT_159 = " = xTmp";
  protected final String TEXT_160 = ".evaluate(temp_";
  protected final String TEXT_161 = ");" + NL + "                        if(obj";
  protected final String TEXT_162 = " == null) {" + NL + "                            node_";
  protected final String TEXT_163 = " = null;";
  protected final String TEXT_164 = NL + "                               str_";
  protected final String TEXT_165 = " = \"\";";
  protected final String TEXT_166 = NL + "                        } else if(obj";
  protected final String TEXT_167 = " instanceof org.dom4j.Node) {" + NL + "                            node_";
  protected final String TEXT_168 = " = (org.dom4j.Node)obj";
  protected final String TEXT_169 = ";";
  protected final String TEXT_170 = NL + "                                str_";
  protected final String TEXT_171 = " = originalJSONString_";
  protected final String TEXT_172 = ".getOriginalJSONString(node_";
  protected final String TEXT_173 = ".asXML(),cjs_";
  protected final String TEXT_174 = ".getAdditionRoot(),";
  protected final String TEXT_175 = ",isGetWholeJson_";
  protected final String TEXT_176 = ");" + NL + "                                isGetWholeJson_";
  protected final String TEXT_177 = " = org.jaxen.function.StringFunction.evaluate(node_";
  protected final String TEXT_178 = ",org.jaxen.dom4j.DocumentNavigator.getInstance());";
  protected final String TEXT_179 = " instanceof String || obj";
  protected final String TEXT_180 = " instanceof Number){" + NL + "                            node_";
  protected final String TEXT_181 = " = temp_";
  protected final String TEXT_182 = ";" + NL + "                            str_";
  protected final String TEXT_183 = " = String.valueOf(obj";
  protected final String TEXT_184 = ");" + NL + "                        } else if(obj";
  protected final String TEXT_185 = " instanceof java.util.List){" + NL + "                            java.util.List<org.dom4j.Node> nodes_";
  protected final String TEXT_186 = " = (java.util.List<org.dom4j.Node>)obj";
  protected final String TEXT_187 = ";" + NL + "                            node_";
  protected final String TEXT_188 = " = nodes_";
  protected final String TEXT_189 = ".size()>0 ? nodes_";
  protected final String TEXT_190 = ".get(0) : null;";
  protected final String TEXT_191 = NL + "                                if(node_";
  protected final String TEXT_192 = "==null){" + NL + "                                    str_";
  protected final String TEXT_193 = " = null;" + NL + "                                }else{" + NL + "                                    str_";
  protected final String TEXT_194 = ");" + NL + "                                }" + NL + "                                isGetWholeJson_";
  protected final String TEXT_195 = " = node_";
  protected final String TEXT_196 = "==null?\"\":org.jaxen.function.StringFunction.evaluate(node_";
  protected final String TEXT_197 = NL + "                        }";
  protected final String TEXT_198 = NL + "                                        ";
  protected final String TEXT_199 = ".";
  protected final String TEXT_200 = " = str_";
  protected final String TEXT_201 = NL + "                                            if(xml_api_";
  protected final String TEXT_202 = ".isDefNull(node_";
  protected final String TEXT_203 = ")){";
  protected final String TEXT_204 = NL + "                                                    ";
  protected final String TEXT_205 = " =null;" + NL + "                                            }else if(xml_api_";
  protected final String TEXT_206 = ".isEmpty(node_";
  protected final String TEXT_207 = NL + "                                                ";
  protected final String TEXT_208 = " =\"\";" + NL + "                                            }else if(xml_api_";
  protected final String TEXT_209 = ".isMissing(node_";
  protected final String TEXT_210 = " )){ ";
  protected final String TEXT_211 = " =";
  protected final String TEXT_212 = ";" + NL + "                                            }else{";
  protected final String TEXT_213 = "  " + NL + "                                            if(xml_api_";
  protected final String TEXT_214 = ") || xml_api_";
  protected final String TEXT_215 = "=";
  protected final String TEXT_216 = NL + "                                            ";
  protected final String TEXT_217 = " = ParserUtils.parseTo_Date(str_";
  protected final String TEXT_218 = ", ";
  protected final String TEXT_219 = ",false);";
  protected final String TEXT_220 = "                          ";
  protected final String TEXT_221 = " = ParserUtils.parseTo_";
  protected final String TEXT_222 = "(ParserUtils.parseTo_Number(str_";
  protected final String TEXT_223 = "(str_";
  protected final String TEXT_224 = NL + "                                            }";
  protected final String TEXT_225 = NL + "                        ";
  protected final String TEXT_226 = " = null; ";
  protected final String TEXT_227 = NL + "            " + NL + "                } catch (java.lang.Exception e_";
  protected final String TEXT_228 = ".getMessage());" + NL + "                    whetherReject_";
  protected final String TEXT_229 = " = true;";
  protected final String TEXT_230 = NL + "                        throw(e_";
  protected final String TEXT_231 = NL + "\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_232 = ".getMessage());" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_233 = NL + "                            ";
  protected final String TEXT_234 = "Struct();";
  protected final String TEXT_235 = NL + "                                ";
  protected final String TEXT_236 = ".errorMessage = e_";
  protected final String TEXT_237 = ".getMessage() + \" - Line: \" + tos_count_";
  protected final String TEXT_238 = NL + "                            System.err.println(e_";
  protected final String TEXT_239 = NL + "                }";
  protected final String TEXT_240 = "if(!whetherReject_";
  protected final String TEXT_241 = ") { ";
  protected final String TEXT_242 = "      " + NL + "                                 if(";
  protected final String TEXT_243 = " == null){ ";
  protected final String TEXT_244 = NL + "                                     ";
  protected final String TEXT_245 = "Struct();" + NL + "                                 }";
  protected final String TEXT_246 = NL + "                                    ";
  protected final String TEXT_247 = ";                 ";
  protected final String TEXT_248 = " } ";
  protected final String TEXT_249 = NL + "class JsonPathCache_";
  protected final String TEXT_250 = " {" + NL + "\tfinal java.util.Map<String,com.jayway.jsonpath.JsonPath> jsonPathString2compiledJsonPath = new java.util.HashMap<String,com.jayway.jsonpath.JsonPath>();" + NL + "\t" + NL + "\tpublic com.jayway.jsonpath.JsonPath getCompiledJsonPath(String jsonPath) {" + NL + "\t\tif(jsonPathString2compiledJsonPath.containsKey(jsonPath)) {" + NL + "\t\t\treturn jsonPathString2compiledJsonPath.get(jsonPath);" + NL + "\t\t} else {" + NL + "\t\t\tcom.jayway.jsonpath.JsonPath compiledLoopPath = com.jayway.jsonpath.JsonPath.compile(jsonPath);" + NL + "\t\t\tjsonPathString2compiledJsonPath.put(jsonPath,compiledLoopPath);" + NL + "\t\t\treturn compiledLoopPath;" + NL + "\t\t}" + NL + "\t}" + NL + "}" + NL + "" + NL + "int nb_line_";
  protected final String TEXT_251 = " = 0;" + NL + "" + NL + "JsonPathCache_";
  protected final String TEXT_252 = " jsonPathCache_";
  protected final String TEXT_253 = " = new JsonPathCache_";
  protected final String TEXT_254 = "();" + NL + "" + NL + "String loopPath_";
  protected final String TEXT_255 = ";" + NL + "java.util.List<Object> resultset_";
  protected final String TEXT_256 = " = new java.util.ArrayList<Object>();" + NL + "" + NL + "java.io.InputStream is_";
  protected final String TEXT_257 = " = null;" + NL + "com.jayway.jsonpath.ParseContext parseContext_";
  protected final String TEXT_258 = " = com.jayway.jsonpath.JsonPath.using(com.jayway.jsonpath.Configuration.defaultConfiguration());" + NL + "Object filenameOrStream_";
  protected final String TEXT_259 = " = null;" + NL + "        try {" + NL + "            filenameOrStream_";
  protected final String TEXT_260 = ";" + NL + "        } catch(java.lang.Exception e_";
  protected final String TEXT_261 = ") {" + NL + "globalMap.put(\"";
  protected final String TEXT_262 = NL + "            \tthrow(e_";
  protected final String TEXT_263 = NL + "\t\t\t\t";
  protected final String TEXT_264 = NL + "\t\t\t\t\tlog.error(\"";
  protected final String TEXT_265 = ".getMessage());" + NL + "\t\t\t\t";
  protected final String TEXT_266 = NL + "\t            globalMap.put(\"";
  protected final String TEXT_267 = "_ERROR_MESSAGE\", e_";
  protected final String TEXT_268 = ".getMessage());" + NL + "\t            System.err.println(e_";
  protected final String TEXT_269 = NL + "        }" + NL + "        " + NL + "com.jayway.jsonpath.ReadContext document_";
  protected final String TEXT_270 = " = null;" + NL + "try {" + NL + "     if(filenameOrStream_";
  protected final String TEXT_271 = " instanceof java.io.InputStream){" + NL + "         is_";
  protected final String TEXT_272 = " = (java.io.InputStream)filenameOrStream_";
  protected final String TEXT_273 = ";" + NL + "     }else{" + NL + "\t    ";
  protected final String TEXT_274 = NL + "\t        is_";
  protected final String TEXT_275 = " = new java.io.FileInputStream((String)filenameOrStream_";
  protected final String TEXT_276 = ");" + NL + "\t    ";
  protected final String TEXT_277 = NL + "\t        java.net.URL url_";
  protected final String TEXT_278 = ");" + NL + "\t        java.net.URLConnection urlConn_";
  protected final String TEXT_279 = ".openConnection();" + NL + "\t        is_";
  protected final String TEXT_280 = " = urlConn_";
  protected final String TEXT_281 = ".getInputStream();" + NL + "\t    ";
  protected final String TEXT_282 = NL + "\t }" + NL + "\t" + NL + "\t" + NL + "\tdocument_";
  protected final String TEXT_283 = " = parseContext_";
  protected final String TEXT_284 = ".parse(is_";
  protected final String TEXT_285 = ");" + NL + "\tcom.jayway.jsonpath.JsonPath compiledLoopPath_";
  protected final String TEXT_286 = " = jsonPathCache_";
  protected final String TEXT_287 = ".getCompiledJsonPath(loopPath_";
  protected final String TEXT_288 = ");" + NL + "\tObject result_";
  protected final String TEXT_289 = " = document_";
  protected final String TEXT_290 = ".read(compiledLoopPath_";
  protected final String TEXT_291 = ",net.minidev.json.JSONObject.class);" + NL + "\tif (result_";
  protected final String TEXT_292 = " instanceof net.minidev.json.JSONArray) {" + NL + "\t\tresultset_";
  protected final String TEXT_293 = " = (net.minidev.json.JSONArray) result_";
  protected final String TEXT_294 = ";" + NL + "\t} else {" + NL + "\t\tresultset_";
  protected final String TEXT_295 = ".add(result_";
  protected final String TEXT_296 = ");" + NL + "\t}" + NL + "} catch (java.lang.Exception e_";
  protected final String TEXT_297 = NL + "\tthrow(e_";
  protected final String TEXT_298 = NL + "\tlog.error(\"";
  protected final String TEXT_299 = ".getMessage());" + NL + "\t";
  protected final String TEXT_300 = NL + "\tglobalMap.put(\"";
  protected final String TEXT_301 = ".getMessage());" + NL + "\tSystem.err.println(e_";
  protected final String TEXT_302 = NL + "} finally {" + NL + "\tif(is_";
  protected final String TEXT_303 = " != null) {" + NL + "\t\tis_";
  protected final String TEXT_304 = ".close();" + NL + "\t}" + NL + "}" + NL + "" + NL + "String jsonPath_";
  protected final String TEXT_305 = " = null;" + NL + "com.jayway.jsonpath.JsonPath compiledJsonPath_";
  protected final String TEXT_306 = " = null;" + NL + "" + NL + "Object value_";
  protected final String TEXT_307 = NL + "\tlog.info(\"";
  protected final String TEXT_308 = " - Retrieving records from data.\");";
  protected final String TEXT_309 = NL + "Object root_";
  protected final String TEXT_310 = " = null;" + NL + "for(Object row_";
  protected final String TEXT_311 = " : resultset_";
  protected final String TEXT_312 = ") {" + NL + "\tnb_line_";
  protected final String TEXT_313 = NL + "\t\t\t";
  protected final String TEXT_314 = " = null;\t\t\t";
  protected final String TEXT_315 = NL + "\tboolean whetherReject_";
  protected final String TEXT_316 = " = false;" + NL + "\t";
  protected final String TEXT_317 = "Struct();" + NL + "\t" + NL + "\ttry{";
  protected final String TEXT_318 = NL + "\t\t\t\tjsonPath_";
  protected final String TEXT_319 = ";" + NL + "\t\t\t\tcompiledJsonPath_";
  protected final String TEXT_320 = ".getCompiledJsonPath(jsonPath_";
  protected final String TEXT_321 = ");" + NL + "\t\t\t\t" + NL + "\t\t\t\ttry {" + NL + "\t\t\t\t   ";
  protected final String TEXT_322 = NL + "\t\t\t\t       value_";
  protected final String TEXT_323 = " = compiledJsonPath_";
  protected final String TEXT_324 = ".read(row_";
  protected final String TEXT_325 = ");" + NL + "\t\t\t\t   ";
  protected final String TEXT_326 = NL + "\t\t\t\t\t    if(jsonPath_";
  protected final String TEXT_327 = ".startsWith(\"$\")){" + NL + "\t\t\t\t\t        if(root_";
  protected final String TEXT_328 = " == null){" + NL + "\t\t\t\t\t            root_";
  protected final String TEXT_329 = ".read(jsonPathCache_";
  protected final String TEXT_330 = ".getCompiledJsonPath(\"$\"));" + NL + "\t\t\t\t\t        }" + NL + "\t\t\t\t           value_";
  protected final String TEXT_331 = ".read(root_";
  protected final String TEXT_332 = ");" + NL + "\t\t\t\t       }else{" + NL + "\t\t\t\t           value_";
  protected final String TEXT_333 = ");" + NL + "\t\t\t\t       }";
  protected final String TEXT_334 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_335 = " = value_";
  protected final String TEXT_336 = " == null ? ";
  protected final String TEXT_337 = NL;
  protected final String TEXT_338 = NL + "\t\t";
  protected final String TEXT_339 = NL + " : value_";
  protected final String TEXT_340 = ".toString();";
  protected final String TEXT_341 = NL + "\t\t\t\t\t\tif(value_";
  protected final String TEXT_342 = " != null && !value_";
  protected final String TEXT_343 = ".toString().isEmpty()) {";
  protected final String TEXT_344 = " = ParserUtils.parseTo_Date(value_";
  protected final String TEXT_345 = ".toString(), ";
  protected final String TEXT_346 = "(ParserUtils.parseTo_Number(value_";
  protected final String TEXT_347 = ".toString().getBytes(";
  protected final String TEXT_348 = "(value_";
  protected final String TEXT_349 = ".toString());";
  protected final String TEXT_350 = NL + "\t\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_351 = NL + ";" + NL + "\t\t\t\t\t\t}";
  protected final String TEXT_352 = NL + "\t\t\t\t} catch (com.jayway.jsonpath.PathNotFoundException e_";
  protected final String TEXT_353 = ".getMessage());" + NL + "\t\t\t\t\t";
  protected final String TEXT_354 = NL + ";" + NL + "\t\t\t\t}\t\t\t\t";
  protected final String TEXT_355 = " ";
  protected final String TEXT_356 = NL + "        ";
  protected final String TEXT_357 = NL + "    } catch (java.lang.Exception e_";
  protected final String TEXT_358 = ".getMessage());" + NL + "        whetherReject_";
  protected final String TEXT_359 = NL + "        throw(e_";
  protected final String TEXT_360 = NL + "                ";
  protected final String TEXT_361 = NL + "               \t\t";
  protected final String TEXT_362 = NL + "                System.err.println(e_";
  protected final String TEXT_363 = NL + "            globalMap.put(\"";
  protected final String TEXT_364 = NL + "    }" + NL + "//}";
  protected final String TEXT_365 = NL + "  class JSONUtil_";
  protected final String TEXT_366 = "{" + NL + "      public int getData(String query,javax.script.Invocable invocableEngine,java.util.List<org.json.simple.JSONArray> jsonResultList,int recordMaxSize){" + NL + "          try{" + NL + "              //only 2 types: String/Boolean" + NL + "              String resultObj = invocableEngine.invokeFunction(\"jsonPath\",query).toString();" + NL + "              if(!\"false\".equals(resultObj)){" + NL + "\t              org.json.simple.JSONArray resultArray= (org.json.simple.JSONArray)org.json.simple.JSONValue.parse(resultObj);" + NL + "\t              jsonResultList.add(resultArray);" + NL + "\t              if(recordMaxSize != -1 && recordMaxSize != resultArray.size()){" + NL + "\t\t             //just give an error, don't stop" + NL + "\t\t\t\t\t";
  protected final String TEXT_367 = NL + "\t\t\t\t\t\tlog.warn(\"";
  protected final String TEXT_368 = " - The Json resource datas maybe have some problems, please make sure the data structure with the same fields.\");" + NL + "\t\t\t\t\t";
  protected final String TEXT_369 = NL + "\t\t              System.err.println(\"The Json resource datas maybe have some problems, please make sure the data structure with the same fields.\");" + NL + "\t               }" + NL + "\t               recordMaxSize = Math.max(recordMaxSize, resultArray.size());" + NL + "               }else{" + NL + "\t\t\t\t\t";
  protected final String TEXT_370 = " - Can't find any data with JSONPath \" + query);" + NL + "\t\t\t\t\t";
  protected final String TEXT_371 = NL + "\t               System.err.println(\"Can't find any data with JSONPath \" + query);" + NL + "\t               //add null to take a place in List(buffer)" + NL + "\t               jsonResultList.add(null); " + NL + "               }" + NL + "          }catch(java.lang.Exception e){" + NL + "globalMap.put(\"";
  protected final String TEXT_372 = "_ERROR_MESSAGE\",e.getMessage());" + NL + "\t\t\t\t";
  protected final String TEXT_373 = " - \" + e.getMessage());" + NL + "\t\t\t\t";
  protected final String TEXT_374 = NL + "              e.printStackTrace();" + NL + "          }" + NL + "          return recordMaxSize;" + NL + "      }" + NL;
  protected final String TEXT_375 = NL + "\t                   void setRowValue_";
  protected final String TEXT_376 = "(";
  protected final String TEXT_377 = "Struct ";
  protected final String TEXT_378 = ", java.util.List<org.json.simple.JSONArray> JSONResultList_";
  protected final String TEXT_379 = ", int nbResultArray_";
  protected final String TEXT_380 = ") throws java.io.UnsupportedEncodingException{";
  protected final String TEXT_381 = NL + "\t\t\t\t\t\t\t        if(JSONResultList_";
  protected final String TEXT_382 = ".get(";
  protected final String TEXT_383 = ") != null && nbResultArray_";
  protected final String TEXT_384 = "<JSONResultList_";
  protected final String TEXT_385 = ").size() && JSONResultList_";
  protected final String TEXT_386 = ").get(nbResultArray_";
  protected final String TEXT_387 = ")!=null){";
  protected final String TEXT_388 = "\t\t" + NL + "\t\t\t                                ";
  protected final String TEXT_389 = " = JSONResultList_";
  protected final String TEXT_390 = ").toString();";
  protected final String TEXT_391 = "\t\t\t\t\t" + NL + "\t\t\t                                ";
  protected final String TEXT_392 = " = ParserUtils.parseTo_Date(JSONResultList_";
  protected final String TEXT_393 = ").toString(), ";
  protected final String TEXT_394 = ");\t\t\t\t";
  protected final String TEXT_395 = NL + "\t\t\t                                ";
  protected final String TEXT_396 = "(ParserUtils.parseTo_Number(JSONResultList_";
  protected final String TEXT_397 = "\t\t\t\t\t\t\t" + NL + "\t\t\t                                ";
  protected final String TEXT_398 = ").toString().getBytes(";
  protected final String TEXT_399 = "(JSONResultList_";
  protected final String TEXT_400 = ").toString());";
  protected final String TEXT_401 = NL + "\t\t\t\t\t\t\t        }else{" + NL;
  protected final String TEXT_402 = NL + "\t\t\t                                throw new RuntimeException(\"Value is empty for column : '";
  protected final String TEXT_403 = "' in '";
  protected final String TEXT_404 = "' connection, value is invalid or this column should be nullable or have a default value.\");";
  protected final String TEXT_405 = NL + "\t\t                                    ";
  protected final String TEXT_406 = ";" + NL + "\t\t";
  protected final String TEXT_407 = NL + "\t\t                             }";
  protected final String TEXT_408 = NL + "\t\t\t             }";
  protected final String TEXT_409 = NL + "                    }";
  protected final String TEXT_410 = NL + "  }" + NL + "int nb_line_";
  protected final String TEXT_411 = " = 0;" + NL + "java.lang.Object jsonText_";
  protected final String TEXT_412 = " = null;" + NL + "JSONUtil_";
  protected final String TEXT_413 = " jsonUtil_";
  protected final String TEXT_414 = "=new JSONUtil_";
  protected final String TEXT_415 = "();" + NL + "java.util.List<org.json.simple.JSONArray> JSONResultList_";
  protected final String TEXT_416 = " = new java.util.ArrayList<org.json.simple.JSONArray>();" + NL + "int recordMaxSize_";
  protected final String TEXT_417 = " = -1;" + NL + "" + NL + "//init js json engine" + NL + "javax.script.ScriptEngineManager scriptEngineMgr_";
  protected final String TEXT_418 = " = new javax.script.ScriptEngineManager();" + NL + "javax.script.ScriptEngine jsEngine_";
  protected final String TEXT_419 = " = scriptEngineMgr_";
  protected final String TEXT_420 = ".getEngineByName(\"JavaScript\");" + NL + "if (jsEngine_";
  protected final String TEXT_421 = " == null) {" + NL + "\t";
  protected final String TEXT_422 = NL + "\t\tlog.warn(\"";
  protected final String TEXT_423 = " - No script engine found for JavaScript\");" + NL + "\t";
  protected final String TEXT_424 = NL + "    System.err.println(\"No script engine found for JavaScript\");" + NL + "} else {" + NL + "    java.net.URL jsonjsUrl_";
  protected final String TEXT_425 = " = com.jsonpath.test.ReadJar.class.getResource(\"json.js\");" + NL + "    if(jsonjsUrl_";
  protected final String TEXT_426 = "!=null) {" + NL + "    \tjsEngine_";
  protected final String TEXT_427 = ".eval(new java.io.BufferedReader(new java.io.InputStreamReader(jsonjsUrl_";
  protected final String TEXT_428 = ".openStream())));" + NL + "    }" + NL + "    " + NL + "    java.net.URL jsonpathjsUrl_";
  protected final String TEXT_429 = " = com.jsonpath.test.ReadJar.class.getResource(\"jsonpath.js\");" + NL + "    if(jsonpathjsUrl_";
  protected final String TEXT_430 = "!=null) {" + NL + "\t\tjsEngine_";
  protected final String TEXT_431 = ".eval(new java.io.BufferedReader(new java.io.InputStreamReader(jsonpathjsUrl_";
  protected final String TEXT_432 = ".openStream())));" + NL + "\t}" + NL + "\t    Object filenameOrStream_";
  protected final String TEXT_433 = NL + "\t            System.err.println(e_";
  protected final String TEXT_434 = ".getMessage());" + NL + "\t            globalMap.put(\"";
  protected final String TEXT_435 = NL + "        }" + NL;
  protected final String TEXT_436 = NL + "\tjava.io.BufferedReader fr_";
  protected final String TEXT_437 = " = null;" + NL + "\t\ttry{" + NL + "\t        if(filenameOrStream_";
  protected final String TEXT_438 = " instanceof java.io.InputStream){" + NL + "\t            fr_";
  protected final String TEXT_439 = "));" + NL + "\t        }else{" + NL + "\t            fr_";
  protected final String TEXT_440 = " = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream((String)filenameOrStream_";
  protected final String TEXT_441 = "),";
  protected final String TEXT_442 = "));" + NL + "\t        }";
  protected final String TEXT_443 = NL + "\tjava.io.InputStreamReader fr_";
  protected final String TEXT_444 = " = new java.io.InputStreamReader((java.io.InputStream)filenameOrStream_";
  protected final String TEXT_445 = ");" + NL + "\t        }else{" + NL + "\t        java.net.URL url_";
  protected final String TEXT_446 = ");" + NL + "\t\t               java.net.URLConnection urlConn_";
  protected final String TEXT_447 = ".openConnection();" + NL + "\t\t               fr_";
  protected final String TEXT_448 = " = new java.io.InputStreamReader(urlConn_";
  protected final String TEXT_449 = ");" + NL + "\t        }";
  protected final String TEXT_450 = NL + "\t       " + NL + "\t\tjsonText_";
  protected final String TEXT_451 = " = org.json.simple.JSONValue.parse(fr_";
  protected final String TEXT_452 = ");" + NL + "\t\tif(jsonText_";
  protected final String TEXT_453 = " == null) {" + NL + "\t\t\tthrow new RuntimeException(\"fail to parse the json file : \" + ";
  protected final String TEXT_454 = ");" + NL + "\t\t}" + NL + "\t} catch(java.lang.Exception e_";
  protected final String TEXT_455 = NL + "\t\tthrow e_";
  protected final String TEXT_456 = ";" + NL + "\t";
  protected final String TEXT_457 = NL + "\t\t";
  protected final String TEXT_458 = NL + "\t\t\tlog.error(\"";
  protected final String TEXT_459 = ".getMessage());" + NL + "\t\t";
  protected final String TEXT_460 = NL + " \t\tSystem.err.println(e_";
  protected final String TEXT_461 = ".getMessage());" + NL + " \t\tglobalMap.put(\"";
  protected final String TEXT_462 = NL + "\t} finally {" + NL + "\t\tif(fr_";
  protected final String TEXT_463 = " != null ) {" + NL + "\t\t\tfr_";
  protected final String TEXT_464 = ".close();" + NL + "\t\t}" + NL + "    }" + NL + "" + NL + "\tif(jsonText_";
  protected final String TEXT_465 = ".eval(\"var obj=\"+jsonText_";
  protected final String TEXT_466 = ".toString());" + NL + "" + NL + "\t\tjavax.script.Invocable invocableEngine_";
  protected final String TEXT_467 = " = (javax.script.Invocable)jsEngine_";
  protected final String TEXT_468 = ";" + NL;
  protected final String TEXT_469 = NL + NL + "recordMaxSize_";
  protected final String TEXT_470 = "=jsonUtil_";
  protected final String TEXT_471 = ".getData(";
  protected final String TEXT_472 = ",invocableEngine_";
  protected final String TEXT_473 = ",JSONResultList_";
  protected final String TEXT_474 = ",recordMaxSize_";
  protected final String TEXT_475 = ");" + NL;
  protected final String TEXT_476 = NL + "\t}" + NL + "}";
  protected final String TEXT_477 = NL + "    \tlog.info(\"";
  protected final String TEXT_478 = NL + "\tfor(int nbResultArray_";
  protected final String TEXT_479 = " = 0; nbResultArray_";
  protected final String TEXT_480 = " < recordMaxSize_";
  protected final String TEXT_481 = "; nbResultArray_";
  protected final String TEXT_482 = "++){" + NL + "\t" + NL + "\t\tnb_line_";
  protected final String TEXT_483 = NL + "\t\t\t\t\t";
  protected final String TEXT_484 = NL + "                \tboolean whetherReject_";
  protected final String TEXT_485 = " = false;" + NL + "                \t";
  protected final String TEXT_486 = "Struct();" + NL + "                \ttry{";
  protected final String TEXT_487 = NL + "                            jsonUtil_";
  protected final String TEXT_488 = ".setRowValue_";
  protected final String TEXT_489 = ",nbResultArray_";
  protected final String TEXT_490 = NL + "                            ";
  protected final String TEXT_491 = NL + "                    } catch (java.lang.Exception e) {" + NL + "globalMap.put(\"";
  protected final String TEXT_492 = "_ERROR_MESSAGE\",e.getMessage());" + NL + "                        whetherReject_";
  protected final String TEXT_493 = NL + "                            throw(e);";
  protected final String TEXT_494 = NL + "\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_495 = " - \" + e.getMessage());" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_496 = NL + "                                ";
  protected final String TEXT_497 = NL + "                                    ";
  protected final String TEXT_498 = ".errorMessage = e.getMessage() + \" - Line: \" + tos_count_";
  protected final String TEXT_499 = NL + "                                System.err.println(e.getMessage());";
  protected final String TEXT_500 = NL + "\t\t\t\t                ";
  protected final String TEXT_501 = NL + "                            globalMap.put(\"";
  protected final String TEXT_502 = "_ERROR_MESSAGE\", e.getMessage());";
  protected final String TEXT_503 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	//this util class use by set log4j debug paramters
	class DefaultLog4jFileUtil {
	
		INode node = null;
	    String cid = null;
 		boolean isLog4jEnabled = false;
 		String label = null;
 		
 		public DefaultLog4jFileUtil(){
 		}
 		public DefaultLog4jFileUtil(INode node) {
 			this.node = node;
 			this.cid = node.getUniqueName();
 			this.label = cid;
			this.isLog4jEnabled = ("true").equals(org.talend.core.model.process.ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
 		}
 		
 		public void setCid(String cid) {
 			this.cid = cid;
 		}
 		
		//for all tFileinput* components 
		public void startRetriveDataInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_1);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_2);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_7);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_5);
    
			}
		}
		
 		//TODO delete it and remove all log4jSb parameter from components
		public void componentStartInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node,boolean hasIncreased) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid);
    stringBuffer.append(hasIncreased?"":"+1");
    stringBuffer.append(TEXT_12);
    
			}
		}
		
		//TODO rename or delete it
		public void debugRetriveData(INode node) {
			debugRetriveData(node,true);
		}
		
		//TODO rename or delete it
		public void debugWriteData(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_16);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_3);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_5);
    
			}
		}

        public void logErrorMessage() {
            if(isLog4jEnabled){
            
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
            } else {
            
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
            }
        }
	}
	
	final DefaultLog4jFileUtil log4jFileUtil = new DefaultLog4jFileUtil((INode)(((org.talend.designer.codegen.config.CodeGeneratorArgument)argument).getArgument()));
	
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

String cid = node.getUniqueName();

String dieOnErrorStr = ElementParameterParser.getValue(node, "__DIE_ON_ERROR__");
boolean dieOnError = (dieOnErrorStr!=null&&!("").equals(dieOnErrorStr))?("true").equals(dieOnErrorStr):false;

String encoding = ElementParameterParser.getValue(node, "__ENCODING__");
String advancedSeparatorStr = ElementParameterParser.getValue(node, "__ADVANCED_SEPARATOR__");
boolean advancedSeparator = (advancedSeparatorStr!=null&&!("").equals(advancedSeparatorStr))?("true").equals(advancedSeparatorStr):false;
String thousandsSeparator = ElementParameterParser.getValueWithJavaType(node, "__THOUSANDS_SEPARATOR__", JavaTypesManager.CHARACTER);
String decimalSeparator = ElementParameterParser.getValueWithJavaType(node, "__DECIMAL_SEPARATOR__", JavaTypesManager.CHARACTER);

String filenameOrStream = ElementParameterParser.getValue(node, "__FILENAME__");
String useUrl = ElementParameterParser.getValue(node, "__USEURL__");
boolean isUseUrl = "true".equals(useUrl);
String urlpath = ElementParameterParser.getValue(node, "__URLPATH__");

String readBy = ElementParameterParser.getValue(node, "__READ_BY__");

if("XPATH".equals(readBy)){

    stringBuffer.append(TEXT_21);
    
List<Map<String, String>> mapping = (List<Map<String,String>>)ElementParameterParser.getObjectValueXML(node, "__MAPPINGXPATH__");
String loopQuery = ElementParameterParser.getValue(node, "__LOOP_QUERY__"); 

String checkDateStr = ElementParameterParser.getValue(node,"__CHECK_DATE__");
boolean checkDate = (checkDateStr!=null&&!("").equals(checkDateStr))?("true").equals(checkDateStr):false;

    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    
        String nodeChecked = null;
        boolean hasNodeCheck = false;
        for(int i = 0 ;i<mapping.size();i++){
            nodeChecked = mapping.get(i).get("NODECHECK");
            if("true".equals(nodeChecked)){
                hasNodeCheck = true;
                break;
            }
        }
        if(hasNodeCheck){

    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
        }

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(filenameOrStream );
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			if (dieOnError) {

    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    			
			}else{

    stringBuffer.append(TEXT_48);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_51);
    }
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			}

    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append(loopQuery );
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_68);
    
				if(!isUseUrl){//read from a file

    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_72);
    
				}else{ //read from internet

    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(urlpath );
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_72);
    
				}

    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_95);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_98);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
            if (dieOnError) {

    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
            }else{

    stringBuffer.append(TEXT_114);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    }
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_119);
     
            }

    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_134);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    
            for (int i=0;i<mapping.size();i++) {
                String query = mapping.get(i).get("QUERY");

    stringBuffer.append(TEXT_142);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(query);
    stringBuffer.append(TEXT_145);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
            }

    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
log4jFileUtil.debugRetriveData(node);

List< ? extends IConnection> conns = node.getOutgoingSortedConnections();
String firstConnName = "";
List<IMetadataTable> metadatas = node.getMetadataList();
if ((metadatas!=null)&&(metadatas.size()>0)) {
    IMetadataTable metadata = metadatas.get(0);
    if (metadata!=null) {
        List<IMetadataColumn> columns=metadata.getListColumns();
        if (conns!=null) {
            String rejectConnName = "";
            List<? extends IConnection> rejectConns = node.getOutgoingConnections("REJECT");
            if(rejectConns != null && rejectConns.size() > 0) {
                IConnection rejectConn = rejectConns.get(0);
                rejectConnName = rejectConn.getName();
            }
            List<IMetadataColumn> rejectColumnList = null;
            IMetadataTable metadataTable = node.getMetadataFromConnector("REJECT");
            if(metadataTable != null) {
                rejectColumnList = metadataTable.getListColumns();      
            }
            for (int i=0;i<conns.size();i++) {
                IConnection connTemp = conns.get(i);
                if (connTemp.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {

    stringBuffer.append(TEXT_150);
    stringBuffer.append(connTemp.getName() );
    stringBuffer.append(TEXT_151);
    
                }
            }
            if (conns.size()>0) { // S_if_a_0_0     
                IConnection conn = conns.get(0);
                firstConnName = conn.getName();
                if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) { // add for reject

    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_119);
    stringBuffer.append(TEXT_153);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_155);
    
                    for (int i=0;i<mapping.size();i++) { // S_for_a_1_0
                        String query = mapping.get(i).get("QUERY");
                        String nodeCheck = mapping.get(i).get("NODECHECK");
                        if("true".equals(nodeCheck) && query!=null){
                            if((".").equals(query.substring(1,query.length()-1)) && ("/").equals(loopQuery.substring(1,loopQuery.length()-1))){

    stringBuffer.append(TEXT_156);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_157);
    
                            }
                        }

    stringBuffer.append(TEXT_158);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_159);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_160);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_161);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_162);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    
                            if(("true").equals(nodeCheck)){

    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_163);
    
                            }else{

    stringBuffer.append(TEXT_164);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_165);
    
                            }

    stringBuffer.append(TEXT_166);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_167);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_168);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_169);
    
                            if(("true").equals(nodeCheck)){

    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_174);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_176);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    
                            }else{

    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_177);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_178);
    
                            }

    stringBuffer.append(TEXT_166);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_179);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_180);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_181);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_182);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_183);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_184);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_185);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_186);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_187);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_188);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_189);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_190);
    
                            if(("true").equals(nodeCheck)){

    stringBuffer.append(TEXT_191);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_192);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_193);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_171);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_172);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_173);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_174);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_175);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_194);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    
                            }else{

    stringBuffer.append(TEXT_170);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_195);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_196);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_178);
    
                            }

    stringBuffer.append(TEXT_197);
    
                        for(IMetadataColumn column:columns) {  // S_for_0_1
                            if (mapping.get(i).get("SCHEMA_COLUMN")!=null) { // S_if_0_2
                                if (column.getLabel().compareTo(mapping.get(i).get("SCHEMA_COLUMN"))==0) { // S_if_0_3
                                    String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
                                    JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
                                    String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
                                    
                                    boolean isNotSetDefault = false;
                                    String defaultValue=column.getDefault();
                                    if(defaultValue!=null){
                                        isNotSetDefault = defaultValue.length()==0;
                                    }else{
                                        isNotSetDefault=true;
                                    }
                                    
                                    if("true".equals(nodeCheck)){

    stringBuffer.append(TEXT_198);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_169);
    
                                        continue;
                                    }
                                    if(javaType == JavaTypesManager.STRING){
                                        if(column.isNullable()){

    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_204);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_211);
    stringBuffer.append(isNotSetDefault?null:column.getDefault());
    stringBuffer.append(TEXT_212);
    
                                        }else{ // column.isNullable()

    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_208);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_210);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_211);
    stringBuffer.append(isNotSetDefault?JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate):column.getDefault());
    stringBuffer.append(TEXT_212);
    
                                        }
                                    }else{ // other type
                                        if(column.isNullable()){

    stringBuffer.append(TEXT_213);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_202);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_205);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_215);
    stringBuffer.append(isNotSetDefault?null:column.getDefault());
    stringBuffer.append(TEXT_212);
    
                                        }else{ // column.isNullable()

    stringBuffer.append(TEXT_201);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_209);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_214);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_206);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_203);
    stringBuffer.append(TEXT_207);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_211);
    stringBuffer.append(isNotSetDefault?JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate):column.getDefault());
    stringBuffer.append(TEXT_212);
    
                                        }
                                    }
                                
                                    if (javaType == JavaTypesManager.STRING || javaType == JavaTypesManager.OBJECT) {

    stringBuffer.append(TEXT_198);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_169);
    
                                    } else if (javaType == JavaTypesManager.DATE) {
                                        if(checkDate) {

    stringBuffer.append(TEXT_216);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_219);
      
                                        } else {

    stringBuffer.append(TEXT_216);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_217);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_19);
                                          
                                        }
                                    } else if(javaType == JavaTypesManager.BYTE_ARRAY){ 

    stringBuffer.append(TEXT_220);
    stringBuffer.append(TEXT_198);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_200);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_95);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_19);
    
                                    } else if(advancedSeparator && JavaTypesManager.isNumberType(javaType)) { 

    stringBuffer.append(TEXT_198);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_222);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( thousandsSeparator );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( decimalSeparator );
    stringBuffer.append(TEXT_72);
    
                                    } else {

    stringBuffer.append(TEXT_198);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_223);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
                                    }

    stringBuffer.append(TEXT_224);
    
                                } //S_if_1_2
                            } // S_if_1_1
                        } // S_for_1_0
                    } // S_for_a_0_1

    
                    if(rejectConnName.equals(firstConnName)) {

    stringBuffer.append(TEXT_225);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_226);
    
                    }

    stringBuffer.append(TEXT_227);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_228);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    
                    if (dieOnError) {

    stringBuffer.append(TEXT_230);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
                    } else {
						if(isLog4jEnabled){
    stringBuffer.append(TEXT_231);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_232);
    }
                        if(!("").equals(rejectConnName)&&!rejectConnName.equals(firstConnName)&&rejectColumnList != null && rejectColumnList.size() > 0) {
    

    stringBuffer.append(TEXT_233);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_234);
    
                            for(IMetadataColumn column : metadata.getListColumns()) {

    stringBuffer.append(TEXT_235);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_169);
    
                            }

    stringBuffer.append(TEXT_233);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
                        } else if(("").equals(rejectConnName)){

    stringBuffer.append(TEXT_238);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(TEXT_233);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
                        } else if(rejectConnName.equals(firstConnName)){

    stringBuffer.append(TEXT_233);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    
                        } 
    
                    }

    stringBuffer.append(TEXT_239);
    
                } //if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA))
            } // S_if_a_1_1

                if (conns.size()>0) {   
                    boolean isFirstEnter = true;
                    for (int i=0;i<conns.size();i++) {
                        IConnection tmpconn = conns.get(i);
                        if ((tmpconn.getName().compareTo(firstConnName)!=0)&&(tmpconn.getName().compareTo(rejectConnName)!=0)&&(tmpconn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA))) {

     
                            if(isFirstEnter) {
    stringBuffer.append(TEXT_240);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_241);
     isFirstEnter = false; } 

    stringBuffer.append(TEXT_242);
    stringBuffer.append(tmpconn.getName() );
    stringBuffer.append(TEXT_243);
    stringBuffer.append(TEXT_244);
    stringBuffer.append(tmpconn.getName() );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(tmpconn.getName() );
    stringBuffer.append(TEXT_245);
    
                                for (IMetadataColumn column: metadata.getListColumns()) {

    stringBuffer.append(TEXT_246);
    stringBuffer.append(tmpconn.getName() );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_247);
    
                                }
                            }
                        }

     if(!isFirstEnter) {
    stringBuffer.append(TEXT_248);
     
                    }
                }

        }// if(conn!=null)
        
    }// if (metadata!=null)
}//if ((metadatas!=null)&&(metadatas.size()>0))

    
}else if("JSONPATH".equals(readBy)) {

    stringBuffer.append(TEXT_21);
    
List<Map<String, String>> mapping = (List<Map<String,String>>)ElementParameterParser.getObjectValueXML(node, "__MAPPING_JSONPATH__");
List< ? extends IConnection> conns = NodeUtil.getOutgoingConnections(node, IConnectionCategory.DATA);
String firstConnName = "";
List<IMetadataTable> metadatas = node.getMetadataList();

String loopPath = ElementParameterParser.getValue(node, "__JSON_LOOP_QUERY__");
boolean useLoopAsRoot = "true".equals(ElementParameterParser.getValue(node, "__USE_LOOP_AS_ROOT__"));

    stringBuffer.append(TEXT_249);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_250);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_251);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_252);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_253);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_254);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(loopPath);
    stringBuffer.append(TEXT_255);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_256);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_257);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_258);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(filenameOrStream );
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			if (dieOnError) {

    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    			
			}else{

    stringBuffer.append(TEXT_263);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    }
    stringBuffer.append(TEXT_266);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_268);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			}

    stringBuffer.append(TEXT_269);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_270);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_271);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_272);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_273);
    if(!isUseUrl){//read from a file
    stringBuffer.append(TEXT_274);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_275);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_276);
    }else{ //read from internet
    stringBuffer.append(TEXT_277);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(urlpath );
    stringBuffer.append(TEXT_278);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_279);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_280);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_281);
    }
    stringBuffer.append(TEXT_282);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_283);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_284);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_285);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_287);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_288);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_290);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_291);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_292);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_293);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_294);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_295);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_296);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
if(dieOnError){

    stringBuffer.append(TEXT_297);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
} else {
	if(isLog4jEnabled){
    stringBuffer.append(TEXT_298);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    }
    stringBuffer.append(TEXT_300);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_301);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
}

    stringBuffer.append(TEXT_302);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_303);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_304);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_305);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_306);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_163);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_307);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    }
    stringBuffer.append(TEXT_309);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_310);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_311);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_312);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
	log4jFileUtil.debugRetriveData(node);
	
	if ((metadatas==null) || (metadatas.isEmpty())) {
		return stringBuffer.toString();
	}
	
	IMetadataTable metadata = metadatas.get(0);
	
	if (metadata==null) {
		return stringBuffer.toString();
	}
	
	if (conns==null || conns.isEmpty()) {
		return stringBuffer.toString(); 
	}
	
	List<IMetadataColumn> columns=metadata.getListColumns();
	
	String rejectConnName = "";
    List<? extends IConnection> rejectConns = node.getOutgoingConnections("REJECT");
    if(rejectConns != null && rejectConns.size() > 0) {
        IConnection rejectConn = rejectConns.get(0);
        rejectConnName = rejectConn.getName();
    }
    List<IMetadataColumn> rejectColumnList = null;
    IMetadataTable metadataTable = node.getMetadataFromConnector("REJECT");
    if(metadataTable != null) {
    	rejectColumnList = metadataTable.getListColumns();
    }
    for (int i=0;i<conns.size();i++) {
		IConnection connTemp = conns.get(i);
		if (connTemp.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {

    stringBuffer.append(TEXT_313);
    stringBuffer.append(connTemp.getName() );
    stringBuffer.append(TEXT_314);
    
		}
	}
	
	IConnection conn = conns.get(0);
	firstConnName = conn.getName();
	
	if(!conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
		return stringBuffer.toString();
	}

    stringBuffer.append(TEXT_315);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_316);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_317);
    
		for (int i=0;i<mapping.size();i++) {
			for(IMetadataColumn column:columns) {
				String schemaColumn = mapping.get(i).get("SCHEMA_COLUMN");
				if(schemaColumn==null || !column.getLabel().equals(schemaColumn)) {
					continue;
				}
				
				String jsonPath = mapping.get(i).get("QUERY");
				
				String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
				JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
				String pattern = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();
				
			 	String defaultValue = column.getDefault();
        		boolean isNotSetDefault = (defaultValue == null || defaultValue.trim().length()==0);

    stringBuffer.append(TEXT_318);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(jsonPath);
    stringBuffer.append(TEXT_319);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_286);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_320);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_321);
    
				   if(useLoopAsRoot){
				   
    stringBuffer.append(TEXT_322);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_325);
    
				   }else{
				   
    stringBuffer.append(TEXT_326);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_327);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_328);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_289);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_329);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_330);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_331);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_332);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_323);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_324);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_333);
    
               }
        			if(javaType == JavaTypesManager.STRING){

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_336);
    stringBuffer.append(TEXT_337);
    
	if(column.isNullable()) {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault? null: defaultValue);
    
	} else {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault ? JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate) : defaultValue);
    
	}

    stringBuffer.append(TEXT_339);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    
					} else {

    stringBuffer.append(TEXT_341);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_342);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_343);
    
					if(javaType == JavaTypesManager.OBJECT) {

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_340);
    
					} else if(javaType == JavaTypesManager.DATE) {

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_344);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    stringBuffer.append(pattern);
    stringBuffer.append(TEXT_19);
    
        			} else if(advancedSeparator && JavaTypesManager.isNumberType(javaType)) {

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_346);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_345);
    stringBuffer.append( thousandsSeparator );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( decimalSeparator );
    stringBuffer.append(TEXT_72);
    
        			} else if(javaType == JavaTypesManager.BYTE_ARRAY) {

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_335);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_347);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_19);
    
        			} else {

    stringBuffer.append(TEXT_334);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append(typeToGenerate );
    stringBuffer.append(TEXT_348);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_349);
    
        			}

    stringBuffer.append(TEXT_350);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(TEXT_337);
    
	if(column.isNullable()) {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault? null: defaultValue);
    
	} else {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault ? JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate) : defaultValue);
    
	}

    stringBuffer.append(TEXT_351);
    
        			}

    stringBuffer.append(TEXT_352);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_353);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(TEXT_337);
    
	if(column.isNullable()) {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault? null: defaultValue);
    
	} else {

    stringBuffer.append(TEXT_338);
    stringBuffer.append(isNotSetDefault ? JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate) : defaultValue);
    
	}

    stringBuffer.append(TEXT_354);
    
			}
		}
		
		if(rejectConnName.equals(firstConnName)) {

    stringBuffer.append(TEXT_355);
    stringBuffer.append(TEXT_356);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_226);
    
    	}

    stringBuffer.append(TEXT_357);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_358);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    
    	if (dieOnError) {

    stringBuffer.append(TEXT_359);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
    	} else {
            if(!("").equals(rejectConnName)&&!rejectConnName.equals(firstConnName)&&rejectColumnList != null && rejectColumnList.size() > 0) {

    stringBuffer.append(TEXT_360);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_234);
    
                for(IMetadataColumn column : metadata.getListColumns()) {

    stringBuffer.append(TEXT_361);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_169);
    
            	}

    stringBuffer.append(TEXT_360);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
            } else if(("").equals(rejectConnName)){
            	if(isLog4jEnabled){
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    }

    stringBuffer.append(TEXT_362);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(TEXT_360);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
            }else if(rejectConnName.equals(firstConnName)){

    stringBuffer.append(TEXT_360);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_236);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_237);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    
            } 
    stringBuffer.append(TEXT_363);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
        }

    stringBuffer.append(TEXT_364);
    
} else {

    stringBuffer.append(TEXT_21);
    
List<Map<String, String>> mapping = (List<Map<String,String>>)ElementParameterParser.getObjectValueXML(node, "__MAPPING__");
List< ? extends IConnection> conns = NodeUtil.getOutgoingConnections(node, IConnectionCategory.DATA);
String firstConnName = "";
List<IMetadataTable> metadatas = node.getMetadataList();

    stringBuffer.append(TEXT_365);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_366);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_367);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_368);
    }
    stringBuffer.append(TEXT_369);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_367);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_370);
    }
    stringBuffer.append(TEXT_371);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_372);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_373);
    }
    stringBuffer.append(TEXT_374);
    
if ((metadatas!=null)&&(metadatas.size()>0)) {
	IMetadataTable metadata = metadatas.get(0);
	if (metadata!=null) {
		List<IMetadataColumn> columns=metadata.getListColumns();
		if (conns!=null) {
			if (conns.size()>0) {
				IConnection conn = conns.get(0);
				firstConnName = conn.getName();
				for (int i=0;i<mapping.size();i++) {
    				if(i % 100 == 0){

    stringBuffer.append(TEXT_375);
    stringBuffer.append((i/100) );
    stringBuffer.append(TEXT_376);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_377);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_378);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_379);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_380);
    
    				}

    
                        for(IMetadataColumn column:columns) {
                            if (mapping.get(i).get("SCHEMA_COLUMN")!=null) {
                                if (column.getLabel().compareTo(mapping.get(i).get("SCHEMA_COLUMN"))==0) {
    								String typeToGenerate = JavaTypesManager.getTypeToGenerate(column.getTalendType(), column.isNullable());
    								JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
    								String patternValue = column.getPattern() == null || column.getPattern().trim().length() == 0 ? null : column.getPattern();

    stringBuffer.append(TEXT_381);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_383);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_384);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_385);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_387);
    		
                                        if (javaType == JavaTypesManager.STRING || javaType == JavaTypesManager.OBJECT) {

    stringBuffer.append(TEXT_388);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_389);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_390);
    		
                                        } else if(javaType == JavaTypesManager.DATE) {						

    stringBuffer.append(TEXT_391);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_392);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_393);
    stringBuffer.append( patternValue );
    stringBuffer.append(TEXT_394);
    		
                                        }else if(advancedSeparator && JavaTypesManager.isNumberType(javaType)) { 

    stringBuffer.append(TEXT_395);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_396);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_393);
    stringBuffer.append( thousandsSeparator );
    stringBuffer.append(TEXT_218);
    stringBuffer.append( decimalSeparator );
    stringBuffer.append(TEXT_72);
    
                                        } else if(javaType == JavaTypesManager.BYTE_ARRAY) { 

    stringBuffer.append(TEXT_397);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_389);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_398);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_19);
    
                                        } else {

    stringBuffer.append(TEXT_395);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_221);
    stringBuffer.append( typeToGenerate );
    stringBuffer.append(TEXT_399);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_382);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_386);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_400);
    
                                        }

    stringBuffer.append(TEXT_401);
    
                                        String defaultValue = JavaTypesManager.getDefaultValueFromJavaType(typeToGenerate, column.getDefault());
                                        if(defaultValue == null) {

    stringBuffer.append(TEXT_402);
    stringBuffer.append( column.getLabel() );
    stringBuffer.append(TEXT_403);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_404);
    
                                        } else {

    stringBuffer.append(TEXT_405);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(defaultValue);
    stringBuffer.append(TEXT_406);
    
                                        }

    stringBuffer.append(TEXT_407);
    

                                }
                            }
					   }
    				if((i + 1) % 100 == 0){

    stringBuffer.append(TEXT_408);
    
    				}
				} // for (int i=0)
  				if(mapping.size() > 0 && mapping.size() % 100 > 0){

    stringBuffer.append(TEXT_409);
    
 				 }
			}

		}

	}
}

    stringBuffer.append(TEXT_410);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_411);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_412);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_413);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_414);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_415);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_416);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_417);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_418);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_419);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_420);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_421);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_422);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_423);
    }
    stringBuffer.append(TEXT_424);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_425);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_426);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_427);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_428);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_429);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_431);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_432);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_259);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(filenameOrStream );
    stringBuffer.append(TEXT_260);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			if (dieOnError) {

    stringBuffer.append(TEXT_262);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    			
			}else{

    stringBuffer.append(TEXT_263);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_264);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_265);
    }
    stringBuffer.append(TEXT_433);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_434);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    
			}

    stringBuffer.append(TEXT_435);
    if(!isUseUrl){//read from a file
    stringBuffer.append(TEXT_436);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_439);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_440);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_441);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_442);
    }else{ //read from internet
    stringBuffer.append(TEXT_443);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_437);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_438);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_444);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_445);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_74);
    stringBuffer.append(urlpath );
    stringBuffer.append(TEXT_446);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_447);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_448);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append(encoding );
    stringBuffer.append(TEXT_449);
    }
    stringBuffer.append(TEXT_450);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_451);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_452);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_453);
    if(!isUseUrl) {
    stringBuffer.append(TEXT_355);
    stringBuffer.append(filenameOrStream );
    stringBuffer.append(TEXT_355);
    } else {
    stringBuffer.append(TEXT_355);
    stringBuffer.append(urlpath );
    stringBuffer.append(TEXT_355);
    }
    stringBuffer.append(TEXT_454);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_261);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    if (dieOnError) {
    stringBuffer.append(TEXT_455);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_456);
    } else {
    stringBuffer.append(TEXT_457);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_458);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_459);
    }
    stringBuffer.append(TEXT_460);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_461);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_267);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_299);
    }
    stringBuffer.append(TEXT_462);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_463);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_464);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_430);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_465);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_466);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_467);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_468);
    
	for(Map<String,String> path:mapping){
		String column = path.get("SCHEMA_COLUMN");
		String query = path.get("QUERY");

    stringBuffer.append(TEXT_469);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_470);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_471);
    stringBuffer.append(query);
    stringBuffer.append(TEXT_472);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_473);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_474);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_475);
    
	}

    stringBuffer.append(TEXT_476);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_477);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_308);
    }
    stringBuffer.append(TEXT_478);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_479);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_480);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_481);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_482);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_149);
    
log4jFileUtil.debugRetriveData(node);

if ((metadatas!=null)&&(metadatas.size()>0)) {
	IMetadataTable metadata = metadatas.get(0);
	if (metadata!=null) {
		List<IMetadataColumn> columns=metadata.getListColumns();
		if (conns!=null) {
            String rejectConnName = "";
            List<? extends IConnection> rejectConns = node.getOutgoingConnections("REJECT");
            if(rejectConns != null && rejectConns.size() > 0) {
                IConnection rejectConn = rejectConns.get(0);
                rejectConnName = rejectConn.getName();
            }
            List<IMetadataColumn> rejectColumnList = null;
            IMetadataTable metadataTable = node.getMetadataFromConnector("REJECT");
            if(metadataTable != null) {
                rejectColumnList = metadataTable.getListColumns();      
            }
            for (int i=0;i<conns.size();i++) {
				IConnection connTemp = conns.get(i);
				if (connTemp.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {

    stringBuffer.append(TEXT_483);
    stringBuffer.append(connTemp.getName() );
    stringBuffer.append(TEXT_314);
    
				}
    		}
			if (conns.size()>0) { // conns.size()>0	
				IConnection conn = conns.get(0);
				firstConnName = conn.getName();
				if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) { // add for DATA

    stringBuffer.append(TEXT_484);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_485);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_486);
    
            			for (int i=0;i<mapping.size();i++) {
            				if(i % 100 == 0){

    stringBuffer.append(TEXT_487);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_488);
    stringBuffer.append( (i/100) );
    stringBuffer.append(TEXT_376);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_473);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_489);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
    				        }
            			}// for
 						if(rejectConnName.equals(firstConnName)) {

    stringBuffer.append(TEXT_355);
    stringBuffer.append(TEXT_490);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_226);
    
                        }

    stringBuffer.append(TEXT_491);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_492);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_229);
    
                        if (dieOnError) {

    stringBuffer.append(TEXT_493);
    
                        } else {
							if(isLog4jEnabled){
    stringBuffer.append(TEXT_494);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_495);
    }
                            if(!("").equals(rejectConnName)&&!rejectConnName.equals(firstConnName)&&rejectColumnList != null && rejectColumnList.size() > 0) {

    stringBuffer.append(TEXT_496);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_154);
    stringBuffer.append(rejectConnName );
    stringBuffer.append(TEXT_234);
    
                                for(IMetadataColumn column : metadata.getListColumns()) {

    stringBuffer.append(TEXT_497);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(firstConnName);
    stringBuffer.append(TEXT_199);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_169);
    
                            	}

    stringBuffer.append(TEXT_496);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_498);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    stringBuffer.append(TEXT_496);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
                            } else if(("").equals(rejectConnName)){

    stringBuffer.append(TEXT_499);
    stringBuffer.append(TEXT_496);
    stringBuffer.append(firstConnName );
    stringBuffer.append(TEXT_163);
    
                            }else if(rejectConnName.equals(firstConnName)){

    stringBuffer.append(TEXT_500);
    stringBuffer.append(rejectConnName);
    stringBuffer.append(TEXT_498);
    stringBuffer.append(node.getUniqueName() );
    stringBuffer.append(TEXT_169);
    
                            } 
    stringBuffer.append(TEXT_501);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_502);
    
                        }

    stringBuffer.append(TEXT_409);
    
                }// end for DATA
			}// conns.size()>0
		} // if(conns!=null)
	} // if (metadata!=null)
} // if ((metadatas!=null)&&(metadatas.size()>0))

    
}

    stringBuffer.append(TEXT_503);
    return stringBuffer.toString();
  }
}
