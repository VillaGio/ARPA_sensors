package org.talend.designer.codegen.translators.file.output;

import org.talend.core.model.process.INode;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import java.util.List;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.process.ElementParameterParser;

public class TFileOutputJSONMainJava
{
  protected static String nl;
  public static synchronized TFileOutputJSONMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TFileOutputJSONMainJava result = new TFileOutputJSONMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "org.json.simple.JSONObject jsonRow";
  protected final String TEXT_3 = " = new org.json.simple.JSONObject();";
  protected final String TEXT_4 = NL + "\t\t\t    \tif(";
  protected final String TEXT_5 = ".";
  protected final String TEXT_6 = " != null){" + NL + "\t\t\t    ";
  protected final String TEXT_7 = NL + "\t\t\t\t\tjsonRow";
  protected final String TEXT_8 = ".put(\"";
  protected final String TEXT_9 = "\",FormatterUtils.format_Date(";
  protected final String TEXT_10 = ", ";
  protected final String TEXT_11 = "));" + NL + "\t\t\t\t";
  protected final String TEXT_12 = "\", String.valueOf(";
  protected final String TEXT_13 = "\", ";
  protected final String TEXT_14 = ");" + NL + "\t\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\t\t}else{" + NL + "\t\t\t\t\t\tjsonRow";
  protected final String TEXT_16 = "\", null);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_17 = NL + NL + "if(!isFirst_";
  protected final String TEXT_18 = "){" + NL + "\tout";
  protected final String TEXT_19 = ".append(\",\");" + NL + "}" + NL + "isFirst_";
  protected final String TEXT_20 = " = false;" + NL + "out";
  protected final String TEXT_21 = ".append(jsonRow";
  protected final String TEXT_22 = ".toJSONString());" + NL + "nb_line_";
  protected final String TEXT_23 = "++;";
  protected final String TEXT_24 = NL + "\tlog.debug(\"";
  protected final String TEXT_25 = " - Writing the record \" + nb_line_";
  protected final String TEXT_26 = " + \".\");";
  protected final String TEXT_27 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();
List<IMetadataTable> metadatas = node.getMetadataList();
boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));


    stringBuffer.append(TEXT_2);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_3);
    
List< ? extends IConnection> conns = node.getIncomingConnections();
for (IConnection conn : conns) {
	if ((metadatas!=null)&&(metadatas.size()>0)) {
		IMetadataTable metadata = metadatas.get(0);
		if (metadata!=null) {
			List<IMetadataColumn> columns = metadata.getListColumns();
			int sizeColumns = columns.size();
			for (int i = 0; i < sizeColumns; i++) {
						
				IMetadataColumn column = columns.get(i);
				JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
				boolean isPrimitive = JavaTypesManager.isJavaPrimitiveType(javaType, column.isNullable());
			    if(!isPrimitive){
			    
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_6);
    
			    }
				if (javaType == JavaTypesManager.DATE && column.getPattern() != null && column.getPattern().trim().length() != 0) {
					String pattern = column.getPattern();
					
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(pattern);
    stringBuffer.append(TEXT_11);
    
				}else if(javaType == JavaTypesManager.CHARACTER){
				
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_11);
    
				}else{
				
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(conn.getName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_14);
    
				}
				if(!isPrimitive){
				
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_16);
    
				}
			}
		}
	}
}

    stringBuffer.append(TEXT_17);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    if(isLog4jEnabled){
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    }
    stringBuffer.append(TEXT_27);
    return stringBuffer.toString();
  }
}
