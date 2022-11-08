package org.talend.designer.codegen.translators.file.spreadsheet;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import java.util.List;
import java.util.Map;

public class TExcelReportEndJava
{
  protected static String nl;
  public static synchronized TExcelReportEndJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TExcelReportEndJava result = new TExcelReportEndJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t  \tjava.util.HashMap ";
  protected final String TEXT_2 = "beans = new java.util.HashMap();" + NL + "\t";
  protected final String TEXT_3 = NL + "\t\t \t//for now add the parents - TODO - amend to include toplevel parents" + NL + "\t\t \tif (parentChildMap.containsKey(\"";
  protected final String TEXT_4 = "\")) {" + NL + "\t\t\t\t";
  protected final String TEXT_5 = "beans.put(\"";
  protected final String TEXT_6 = "\", ";
  protected final String TEXT_7 = "Collection);" + NL + "\t\t\t} else {" + NL + "\t\t\t\t";
  protected final String TEXT_8 = "Collection);" + NL + "\t\t\t}" + NL + "\t\t";
  protected final String TEXT_9 = NL + NL + "        net.sf.jxls.transformer.XLSTransformer transformer = new net.sf.jxls.transformer.XLSTransformer();" + NL + "        try {" + NL + "\t\ttransformer.transformXLS(templateFileName, ";
  protected final String TEXT_10 = "beans, outputFilename);" + NL + "\t} catch (Exception e) {" + NL + "\t\te.printStackTrace();" + NL + "\t}" + NL + "" + NL + "" + NL + "\tglobalMap.put(\"";
  protected final String TEXT_11 = "_NB_LINE\",nb_line_";
  protected final String TEXT_12 = ");";
  protected final String TEXT_13 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();
	String cid = node.getUniqueName();



	List<Map<String, String>> objects =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNAMESTABLE__");

	
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_2);
    

	for (int i=0 ; i<objects.size(); i++) {
		Map<String, String> object = objects.get(i);
		int objectID = Integer.parseInt(object.get("OBJECT_ID"));
		String objectName = object.get("OBJECTNAME");
		 
    stringBuffer.append(TEXT_3);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_8);
    

	}


    stringBuffer.append(TEXT_9);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_11);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    return stringBuffer.toString();
  }
}
