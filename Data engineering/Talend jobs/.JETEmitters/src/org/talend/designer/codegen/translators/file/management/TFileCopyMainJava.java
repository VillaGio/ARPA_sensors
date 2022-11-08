package org.talend.designer.codegen.translators.file.management;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;

public class TFileCopyMainJava
{
  protected static String nl;
  public static synchronized TFileCopyMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TFileCopyMainJava result = new TFileCopyMainJava();
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
  protected final String TEXT_21 = NL + " " + NL;
  protected final String TEXT_22 = "     " + NL + "        String srcFileFolderPath_";
  protected final String TEXT_23 = " = ";
  protected final String TEXT_24 = ";" + NL + "" + NL + "        String desFileFolderPath_";
  protected final String TEXT_25 = ";" + NL + "\t\t" + NL + "\t\tjava.io.File srcFileFolder_";
  protected final String TEXT_26 = " = new java.io.File(srcFileFolderPath_";
  protected final String TEXT_27 = ");" + NL + "" + NL + "\t\tjava.io.File desFileFolder_";
  protected final String TEXT_28 = " = new java.io.File(desFileFolderPath_";
  protected final String TEXT_29 = ");" + NL + "" + NL + "\t\tint srcFilePathLength_";
  protected final String TEXT_30 = " = srcFileFolder_";
  protected final String TEXT_31 = ".getPath().length();" + NL + "" + NL + "\t\tString srcFolderName_";
  protected final String TEXT_32 = ".getName();" + NL + "" + NL + "\t\tjava.io.File newDesFileFolder_";
  protected final String TEXT_33 = " = new java.io.File(desFileFolder_";
  protected final String TEXT_34 = ", srcFolderName_";
  protected final String TEXT_35 = ");" + NL + "      " + NL + "\t\tclass CopyDirectory_";
  protected final String TEXT_36 = " {\t\t" + NL + "" + NL + "\t\t\tpublic void copyAllFiles(java.io.File rootFolder, int pathLength, java.io.File newDesFileFolder) {" + NL + "" + NL + "\t\t\t\tjava.io.File[] fileList = rootFolder.listFiles();" + NL + "\t\t\t\tif(fileList!=null){" + NL + "\t\t\t\t\tif (fileList.length > 0) {" + NL + "\t\t\t\t\t" + NL + "\t\t\t\t\t\tfor (java.io.File file : fileList) {" + NL + "\t" + NL + "\t\t\t\t\t\t\tif (file.isDirectory()) {" + NL + "\t\t\t\t\t\t\t\tcopyAllFiles(file, pathLength, newDesFileFolder);" + NL + "\t\t\t\t\t\t\t} else {" + NL + "\t\t\t\t\t\t\t" + NL + "\t\t\t\t\t\t\t\tString srcFilePath = file.getPath();" + NL + "\t" + NL + "\t\t\t\t\t\t\t\tString temFileName = srcFilePath.substring(pathLength);" + NL + "\t" + NL + "\t\t\t\t\t\t\t\tjava.io.File desFile = new java.io.File(newDesFileFolder, temFileName);" + NL + "\t" + NL + "\t\t\t\t\t\t\t\tString desFilePath = desFile.getPath();" + NL + "\t" + NL + "\t\t\t\t\t\t\t\tjava.io.File parentFile = desFile.getParentFile();" + NL + "\t" + NL + "\t\t\t\t\t\t\t\tif (!parentFile.exists()) {" + NL + "\t\t\t\t\t\t\t\t\tparentFile.mkdirs();" + NL + "\t\t\t\t\t\t\t\t}" + NL + "\t" + NL + "\t\t\t\t\t\t\t\ttry\t{\t\t\t\t" + NL + "\t\t\t\t\t\t\t\t\torg.talend.FileCopy.copyFile(srcFilePath, desFilePath, false, ";
  protected final String TEXT_37 = ");" + NL + "\t\t\t\t\t\t\t\t} catch (java.lang.Exception e){ " + NL + "                                   globalMap.put(\"";
  protected final String TEXT_38 = "_ERROR_MESSAGE\",e.getMessage());";
  protected final String TEXT_39 = NL + "\t\t\t\t\t\t\t\t\t\tthrow new RuntimeException(\"";
  protected final String TEXT_40 = " - \" + e.getMessage());";
  protected final String TEXT_41 = NL + "\t\t\t\t\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_42 = NL + "\t\t\t\t\t\t\t\t\t\tSystem.err.println(\"";
  protected final String TEXT_43 = NL + "\t\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t\t}" + NL + "\t" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t} else{ //it is an empty folder." + NL + "\t        " + NL + "\t\t\t\t\t\tString srcFolderPath = rootFolder.getPath();" + NL + "\t" + NL + "\t\t\t\t\t\tString temFolderName = srcFolderPath.substring(pathLength);" + NL + "\t" + NL + "\t\t\t\t\t\tjava.io.File desFolder = new java.io.File(newDesFileFolder, temFolderName);" + NL + "\t\t\t\t\t\tdesFolder.mkdirs();" + NL + "\t\t\t\t\t} " + NL + "        \t\t}else{" + NL + "\t\t\t\t\tString errorMessageInputOutputErrorOccurs_";
  protected final String TEXT_44 = " = String.format(\"Path name \\\"%s\\\" does not denote a directory, or if an I/O error occurs.\", rootFolder.getPath());";
  protected final String TEXT_45 = NL + "\t\t\t\t\t\tthrow new RuntimeException(errorMessageInputOutputErrorOccurs_";
  protected final String TEXT_46 = NL + "\t\t\t\t\t\tlog.error(\"";
  protected final String TEXT_47 = "\" +  errorMessageInputOutputErrorOccurs_";
  protected final String TEXT_48 = ");" + NL + "\t\t\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_49 = "_ERROR_MESSAGE\", errorMessageInputOutputErrorOccurs_";
  protected final String TEXT_50 = NL + "\t\t\t\t\t\tSystem.err.println(errorMessageInputOutputErrorOccurs_";
  protected final String TEXT_51 = NL + "        \t\t}" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "    " + NL + "\t\tCopyDirectory_";
  protected final String TEXT_52 = " copyDir_";
  protected final String TEXT_53 = " = new CopyDirectory_";
  protected final String TEXT_54 = "();\t  " + NL + "\t\tcopyDir_";
  protected final String TEXT_55 = ".copyAllFiles(srcFileFolder_";
  protected final String TEXT_56 = ", srcFilePathLength_";
  protected final String TEXT_57 = ",newDesFileFolder_";
  protected final String TEXT_58 = ");" + NL + "" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_59 = "_SOURCE_DIRECTORY\", srcFileFolderPath_";
  protected final String TEXT_60 = ");" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_61 = "_DESTINATION_DIRECTORY\", desFileFolderPath_";
  protected final String TEXT_62 = ");" + NL;
  protected final String TEXT_63 = NL + NL + "        String srcFileName_";
  protected final String TEXT_64 = ";" + NL + "" + NL + "\t\tjava.io.File srcFile_";
  protected final String TEXT_65 = " = new java.io.File(srcFileName_";
  protected final String TEXT_66 = ");" + NL + "" + NL + "\t\t// here need check first, before mkdirs()." + NL + "\t\tif (!srcFile_";
  protected final String TEXT_67 = ".exists() || !srcFile_";
  protected final String TEXT_68 = ".isFile()) {" + NL + "\t\t\tString errorMessageFileDoesnotExistsOrIsNotAFile_";
  protected final String TEXT_69 = " = String.format(\"The source File \\\"%s\\\" does not exist or is not a file.\", srcFileName_";
  protected final String TEXT_70 = NL + "\t\t\t\tthrow new RuntimeException(errorMessageFileDoesnotExistsOrIsNotAFile_";
  protected final String TEXT_71 = NL + "\t\t\t\tlog.error(errorMessageFileDoesnotExistsOrIsNotAFile_";
  protected final String TEXT_72 = ");" + NL + "\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_73 = "_ERROR_MESSAGE\", errorMessageFileDoesnotExistsOrIsNotAFile_";
  protected final String TEXT_74 = NL + "\t\t\t\tSystem.err.println(errorMessageFileDoesnotExistsOrIsNotAFile_";
  protected final String TEXT_75 = NL + "\t\t}" + NL + "        String desDirName_";
  protected final String TEXT_76 = ";" + NL + "" + NL + "\t\tString desFileName_";
  protected final String TEXT_77 = " ";
  protected final String TEXT_78 = " srcFile_";
  protected final String TEXT_79 = ".getName() ";
  protected final String TEXT_80 = ";" + NL + "" + NL + "\t\tif (desFileName_";
  protected final String TEXT_81 = " != null && (\"\").equals(desFileName_";
  protected final String TEXT_82 = ".trim())){" + NL + "\t\t\tdesFileName_";
  protected final String TEXT_83 = " = \"NewName.temp\";" + NL + "\t\t}" + NL + "" + NL + "\t\tjava.io.File desFile_";
  protected final String TEXT_84 = " = new java.io.File(desDirName_";
  protected final String TEXT_85 = ", desFileName_";
  protected final String TEXT_86 = ");" + NL + "" + NL + "\t\tif (!srcFile_";
  protected final String TEXT_87 = ".getPath().equals(desFile_";
  protected final String TEXT_88 = ".getPath()) ";
  protected final String TEXT_89 = " && !desFile_";
  protected final String TEXT_90 = ".exists() ";
  protected final String TEXT_91 = " ) {";
  protected final String TEXT_92 = NL + "\t\t\t\tjava.io.File parentFile_";
  protected final String TEXT_93 = " = desFile_";
  protected final String TEXT_94 = ".getParentFile();" + NL + "" + NL + "\t\t\t\tif (parentFile_";
  protected final String TEXT_95 = " != null && !parentFile_";
  protected final String TEXT_96 = ".exists()) {" + NL + "\t\t\t\t\tparentFile_";
  protected final String TEXT_97 = ".mkdirs();" + NL + "\t\t\t\t}";
  protected final String TEXT_98 = "           " + NL + "\t\t\t\ttry {";
  protected final String TEXT_99 = NL + "\t\t\t\t\torg.talend.FileCopy.copyFile(srcFile_";
  protected final String TEXT_100 = ".getPath(), desFile_";
  protected final String TEXT_101 = ".getPath(), ";
  protected final String TEXT_102 = ", ";
  protected final String TEXT_103 = NL + "\t\t\t\t\torg.talend.FileCopy.forceCopyAndDelete(srcFile_";
  protected final String TEXT_104 = ".getPath(),";
  protected final String TEXT_105 = NL + "\t\t\t\t} catch (Exception e) {" + NL + "globalMap.put(\"";
  protected final String TEXT_106 = " \" + e.getMessage());";
  protected final String TEXT_107 = NL + "\t\t\t\t\t\tSystem.err.println(\"";
  protected final String TEXT_108 = NL + "\t\t\t\t}";
  protected final String TEXT_109 = NL + "\t\t\t\tjava.io.File isRemoved_";
  protected final String TEXT_110 = " = new java.io.File(";
  protected final String TEXT_111 = ");" + NL + "\t\t\t\tif(isRemoved_";
  protected final String TEXT_112 = ".exists()) {" + NL + "\t\t\t\t\tString errorMessageCouldNotRemoveFile_";
  protected final String TEXT_113 = " = String.format(\"";
  protected final String TEXT_114 = " - The source file \\\"%s\\\" could not be removed from the folder because it is open or you only have read-only rights.\", srcFileName_";
  protected final String TEXT_115 = NL + "\t\t\t\t\t\tthrow new RuntimeException(errorMessageCouldNotRemoveFile_";
  protected final String TEXT_116 = NL + "\t\t\t\t\t\tlog.error(errorMessageCouldNotRemoveFile_";
  protected final String TEXT_117 = "_ERROR_MESSAGE\", errorMessageCouldNotRemoveFile_";
  protected final String TEXT_118 = NL + "\t\t\t\t\t\tSystem.err.println(errorMessageCouldNotRemoveFile_";
  protected final String TEXT_119 = " + \"\\n\");" + NL + "\t\t\t\t\t\tglobalMap.put(\"";
  protected final String TEXT_120 = NL + "\t\t\t\t} ";
  protected final String TEXT_121 = NL + "\t\t\t\telse {" + NL + "\t\t\t\t\tlog.info(\"";
  protected final String TEXT_122 = " - The source file \\\"\" + srcFileName_";
  protected final String TEXT_123 = " + \"\\\" is deleted.\");" + NL + "\t\t\t\t}";
  protected final String TEXT_124 = NL + NL + "\t\t}" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_125 = "_DESTINATION_FILEPATH\",desFile_";
  protected final String TEXT_126 = ".getPath()); " + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_127 = "_DESTINATION_FILENAME\",desFile_";
  protected final String TEXT_128 = ".getName()); " + NL + "" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_129 = "_SOURCE_DIRECTORY\", srcFile_";
  protected final String TEXT_130 = ".getParent());" + NL + "\t\tglobalMap.put(\"";
  protected final String TEXT_131 = "_DESTINATION_DIRECTORY\", desFile_";
  protected final String TEXT_132 = ".getParent());";
  protected final String TEXT_133 = NL;

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

	String fileName = ElementParameterParser.getValue(node, "__FILENAME__");	

	String destination  = ElementParameterParser.getValue(node, "__DESTINATION__");
	
	boolean rename = ("true").equals(ElementParameterParser.getValue(node, "__RENAME__"));
	
	String destination_filename  = ElementParameterParser.getValue(node, "__DESTINATION_RENAME__");

	boolean isCopyADir = ("true").equals(ElementParameterParser.getValue(node, "__ENABLE_COPY_DIRECTORY__"));
	
	boolean reFile = ("true").equals(ElementParameterParser.getValue(node, "__REMOVE_FILE__"));

	boolean rpFile = ("true").equals(ElementParameterParser.getValue(node,"__REPLACE_FILE__"));

	boolean creatDir = ("true").equals(ElementParameterParser.getValue(node,"__CREATE_DIRECTORY__"));
	
	boolean failOn = ("true").equals(ElementParameterParser.getValue(node,"__FAILON__"));
	
	boolean forceCopyAndDelete = ("true").equals(ElementParameterParser.getValue(node,"__FORCE_COPY_DELETE__")) && reFile;

	boolean preserveModifiedTime = ("true").equals(ElementParameterParser.getValue(node,"__PRESERVE_LAST_MODIFIED_TIME__"));
	
	final boolean isLog4jEnabled = ("true").equals(ElementParameterParser.getValue(node.getProcess(), "__LOG4J_ACTIVATE__"));
	
	log4jFileUtil.componentStartInfo(node);
	
	if (isCopyADir){ // copy a directory

    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(ElementParameterParser.getValue(node, "__SOURCE_DERECTORY__"));
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(ElementParameterParser.getValue(node, "__DESTINATION__"));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_32);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_33);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_34);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_35);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_36);
    stringBuffer.append(preserveModifiedTime);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
									if (failOn) {

    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    
									} else if(isLog4jEnabled) {

    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    
									} else {

    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_40);
    
									}

    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_44);
    
					if (failOn) {

    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
					} else if(isLog4jEnabled) {

    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
					} else {

    stringBuffer.append(TEXT_50);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
					}

    stringBuffer.append(TEXT_51);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid );
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
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_60);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_61);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_62);
     
	} else { //copy a file

    stringBuffer.append(TEXT_63);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(fileName );
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
			if (failOn) {

    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
			} else if (isLog4jEnabled) {

    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
			} else {

    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
			}

    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(destination );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    if(rename){
    stringBuffer.append(TEXT_77);
    stringBuffer.append(destination_filename );
    stringBuffer.append(TEXT_77);
    }else{
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_79);
    }
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_82);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_83);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_84);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_87);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_88);
    if (!rpFile){
    stringBuffer.append(TEXT_89);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_90);
    }
    stringBuffer.append(TEXT_91);
    
			if (creatDir){

    stringBuffer.append(TEXT_92);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_97);
    
			}
			if (!failOn) {

    stringBuffer.append(TEXT_98);
    
			}
			if(!forceCopyAndDelete) {

    stringBuffer.append(TEXT_99);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_101);
    stringBuffer.append(reFile );
    stringBuffer.append(TEXT_102);
    stringBuffer.append(preserveModifiedTime);
    stringBuffer.append(TEXT_19);
    
			} else {

    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_100);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_104);
    stringBuffer.append(preserveModifiedTime);
    stringBuffer.append(TEXT_19);
    
			}
			if (!failOn) {

    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_38);
    
					if (isLog4jEnabled) {

    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_106);
    
					} else {

    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_106);
    
					}

    stringBuffer.append(TEXT_108);
    
			}
			if(reFile) {

    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_110);
    stringBuffer.append(fileName);
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_19);
    
					if (failOn) {

    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
					} else if(isLog4jEnabled) {

    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
					} else {

    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_19);
    
					}

    stringBuffer.append(TEXT_120);
    
				if(isLog4jEnabled) {

    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_123);
    
				}
			}

    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_130);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_132);
    
	}
	

    stringBuffer.append(TEXT_133);
    return stringBuffer.toString();
  }
}
