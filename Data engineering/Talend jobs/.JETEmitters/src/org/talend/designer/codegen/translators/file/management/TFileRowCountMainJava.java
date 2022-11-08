package org.talend.designer.codegen.translators.file.management;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

public class TFileRowCountMainJava
{
  protected static String nl;
  public static synchronized TFileRowCountMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TFileRowCountMainJava result = new TFileRowCountMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\t\t\t\tlog.debug(\"";
  protected final String TEXT_3 = " - Retrieving records from the datasource.\");" + NL + "\t\t\t";
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
  protected final String TEXT_21 = NL;
  protected final String TEXT_22 = NL + "String tmp_";
  protected final String TEXT_23 = " = ";
  protected final String TEXT_24 = ";" + NL + "int emptyLineCount_";
  protected final String TEXT_25 = " = 0;" + NL + "if (tmp_";
  protected final String TEXT_26 = ".toLowerCase().endsWith(\".xlsx\")) throw new RuntimeException(\"not support excel 2007\");  " + NL + "int lineCount_";
  protected final String TEXT_27 = ".toLowerCase().endsWith(\".xls\")){" + NL + "  final jxl.WorkbookSettings wbs_";
  protected final String TEXT_28 = " = new jxl.WorkbookSettings();" + NL + "  wbs_";
  protected final String TEXT_29 = ".setEncoding(";
  protected final String TEXT_30 = ");" + NL + "  final jxl.Workbook wb_";
  protected final String TEXT_31 = " = jxl.Workbook.getWorkbook(" + NL + "      new java.io.BufferedInputStream(" + NL + "          new java.io.FileInputStream(";
  protected final String TEXT_32 = ")), wbs_";
  protected final String TEXT_33 = ");" + NL + "" + NL + "  java.util.List<jxl.Sheet> sheets_";
  protected final String TEXT_34 = " = new java.util.ArrayList<jxl.Sheet>();;" + NL + "  sheets_";
  protected final String TEXT_35 = " = java.util.Arrays.<jxl.Sheet> asList(wb_";
  protected final String TEXT_36 = ".getSheets());" + NL + "  " + NL + "  if (sheets_";
  protected final String TEXT_37 = ".size() <= 0){" + NL + "    wb_";
  protected final String TEXT_38 = ".close();" + NL + "    throw new RuntimeException(\"Special sheets not exist!\");" + NL + "  } " + NL + " " + NL + "  for (jxl.Sheet sheet_";
  protected final String TEXT_39 = " : sheets_";
  protected final String TEXT_40 = ") {" + NL + "    int one_sheet_rows = sheet_";
  protected final String TEXT_41 = ".getRows();" + NL + "    lineCount_";
  protected final String TEXT_42 = " += one_sheet_rows;" + NL + "  " + NL + "    for (int i_";
  protected final String TEXT_43 = " = 0; i_";
  protected final String TEXT_44 = " < one_sheet_rows; i_";
  protected final String TEXT_45 = "++){" + NL + "      jxl.Cell[] sheet_row = sheet_";
  protected final String TEXT_46 = ".getRow(i_";
  protected final String TEXT_47 = ");" + NL + "    " + NL + "      boolean bIsEmptyRow = true;" + NL + "      for (jxl.Cell cell: sheet_row){" + NL + "        if (cell.getContents() != null){" + NL + "          bIsEmptyRow = false;" + NL + "          break;" + NL + "        }" + NL + "      }" + NL + "      emptyLineCount_";
  protected final String TEXT_48 = " += bIsEmptyRow ? 1 : 0;" + NL + "    }" + NL + "  }  " + NL + "  wb_";
  protected final String TEXT_49 = ".close();" + NL + "  " + NL + "} else{        \t\t\t\t\t" + NL + "  java.io.BufferedReader br_";
  protected final String TEXT_50 = " = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(";
  protected final String TEXT_51 = "), ";
  protected final String TEXT_52 = "));\t\t\t" + NL + "  String rowSeparator_";
  protected final String TEXT_53 = ";" + NL + "  byte[] bytes_";
  protected final String TEXT_54 = " = rowSeparator_";
  protected final String TEXT_55 = ".getBytes();" + NL + "  int index_";
  protected final String TEXT_56 = " = 0, oneChar_";
  protected final String TEXT_57 = " = 0, tipEmptyLineCount_";
  protected final String TEXT_58 = " = 0; " + NL + "  boolean bTipEmptyFlagOpen_";
  protected final String TEXT_59 = " = true, bReadyEOF_";
  protected final String TEXT_60 = " = false;" + NL + "\t\t" + NL + "  if(bytes_";
  protected final String TEXT_61 = ".length > 0) {" + NL + "    while ((oneChar_";
  protected final String TEXT_62 = " = br_";
  protected final String TEXT_63 = ".read()) != -1) {" + NL + "      if (oneChar_";
  protected final String TEXT_64 = " == bytes_";
  protected final String TEXT_65 = "[index_";
  protected final String TEXT_66 = "]) {" + NL + "      " + NL + "        if (index_";
  protected final String TEXT_67 = " < bytes_";
  protected final String TEXT_68 = ".length - 1){" + NL + "          index_";
  protected final String TEXT_69 = " ++ ;" + NL + "          continue; // match next char" + NL + "        }" + NL + "        " + NL + "        if (index_";
  protected final String TEXT_70 = ".length - 1) {                  " + NL + "          lineCount_";
  protected final String TEXT_71 = "++;" + NL + "          if(bTipEmptyFlagOpen_";
  protected final String TEXT_72 = ") {" + NL + "            tipEmptyLineCount_";
  protected final String TEXT_73 = " ++;" + NL + "            emptyLineCount_";
  protected final String TEXT_74 = " ++;" + NL + "          }" + NL + "          bReadyEOF_";
  protected final String TEXT_75 = " = false; // next row must be have char(or EOF flag)" + NL + "          bTipEmptyFlagOpen_";
  protected final String TEXT_76 = " = true; " + NL + "          index_";
  protected final String TEXT_77 = " = 0;" + NL + "        }" + NL + "        " + NL + "      }else{      " + NL + "        bReadyEOF_";
  protected final String TEXT_78 = " = true;" + NL + "        bTipEmptyFlagOpen_";
  protected final String TEXT_79 = " = false;" + NL + "        tipEmptyLineCount_";
  protected final String TEXT_80 = " = 0;" + NL + "        index_";
  protected final String TEXT_81 = " = 0;        " + NL + "      }" + NL + "    }" + NL + "    " + NL + "    if (bReadyEOF_";
  protected final String TEXT_82 = ") lineCount_";
  protected final String TEXT_83 = " ++ ; // add last row if not end by row separator" + NL + "    " + NL + "    if (bTipEmptyFlagOpen_";
  protected final String TEXT_84 = ") {" + NL + "      lineCount_";
  protected final String TEXT_85 = " -= tipEmptyLineCount_";
  protected final String TEXT_86 = ";" + NL + "      emptyLineCount_";
  protected final String TEXT_87 = ";" + NL + "    }          " + NL + "  }" + NL + "  br_";
  protected final String TEXT_88 = ".close();" + NL + "} ";
  protected final String TEXT_89 = NL + "  lineCount_";
  protected final String TEXT_90 = " -= emptyLineCount_";
  protected final String TEXT_91 = ";";
  protected final String TEXT_92 = "   " + NL + "" + NL + "  globalMap.put(\"";
  protected final String TEXT_93 = "_COUNT\",lineCount_";
  protected final String TEXT_94 = ");" + NL + "  ";
  protected final String TEXT_95 = NL + "    log.info(\"";
  protected final String TEXT_96 = " - File : \" + ";
  protected final String TEXT_97 = " + \" row count is \" + lineCount_";
  protected final String TEXT_98 = " " + NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    
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
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_3);
    
			}
		}
		
		public void retrievedDataNumberInfo() {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void retrievedDataNumberInfoFromGlobalMap(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_7);
    
			}
		}
		
		//for all tFileinput* components 
		public void retrievedDataNumberInfo(INode node) {
			if (isLog4jEnabled) {
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_5);
    
			}
		}
		
		public void writeDataFinishInfo(INode node) {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_2);
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
			
    stringBuffer.append(TEXT_2);
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
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_14);
    
			}
		}
		
		public void logCurrentRowNumberInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_2);
    stringBuffer.append(label);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_16);
    
			}
		}
		
		public void logDataCountInfo() {
			if(isLog4jEnabled){
			
    stringBuffer.append(TEXT_2);
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
	
    stringBuffer.append(TEXT_21);
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();
String cid = node.getUniqueName();

String filename = ElementParameterParser.getValue(
    node,
    "__FILENAME__"
);

String rowSeparator = ElementParameterParser.getValue(
    node,
    "__ROWSEPARATOR__"
);
String encoding = ElementParameterParser.getValue(node,"__ENCODING__");

final boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));

log4jFileUtil.componentStartInfo(node);

    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(filename);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(filename);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_50);
    stringBuffer.append(filename );
    stringBuffer.append(TEXT_51);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(rowSeparator );
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_55);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid );
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
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    
if ("true".equals(ElementParameterParser.getValue(node, "__IGNORE_EMPTY_ROW__")) ){

    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_91);
    }
    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    
if(isLog4jEnabled) {

    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_96);
    stringBuffer.append(filename);
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
}

    stringBuffer.append(TEXT_98);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}
