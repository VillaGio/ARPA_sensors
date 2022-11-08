package org.talend.designer.codegen.translators.file.management;

import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.process.ElementParameterParser;

public class TFileUnarchiveBeginJava
{
  protected static String nl;
  public static synchronized TFileUnarchiveBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TFileUnarchiveBeginJava result = new TFileUnarchiveBeginJava();
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
  protected final String TEXT_22 = NL + "        com.talend.compress.zip.Util util_";
  protected final String TEXT_23 = " = new com.talend.compress.zip.Util(";
  protected final String TEXT_24 = ");" + NL + "" + NL + "        String zipFileURL_";
  protected final String TEXT_25 = " = ";
  protected final String TEXT_26 = ";" + NL + "        String tmpFileURL_";
  protected final String TEXT_27 = " = zipFileURL_";
  protected final String TEXT_28 = ".toLowerCase();" + NL + "        String outputPath_";
  protected final String TEXT_29 = ";";
  protected final String TEXT_30 = NL + "        java.io.File file_";
  protected final String TEXT_31 = " = new java.io.File(zipFileURL_";
  protected final String TEXT_32 = ");" + NL + "        String name_";
  protected final String TEXT_33 = " = file_";
  protected final String TEXT_34 = ".getName();" + NL + "        int i_";
  protected final String TEXT_35 = " = 0;" + NL + "        if (tmpFileURL_";
  protected final String TEXT_36 = ".endsWith(\".tar.gz\"))  {" + NL + "            i_";
  protected final String TEXT_37 = " = name_";
  protected final String TEXT_38 = ".length()-7;" + NL + "        } else {" + NL + "            i_";
  protected final String TEXT_39 = ".lastIndexOf('.');" + NL + "            i_";
  protected final String TEXT_40 = " = i_";
  protected final String TEXT_41 = "!=-1? i_";
  protected final String TEXT_42 = " : name_";
  protected final String TEXT_43 = ".length();" + NL + "        }" + NL + "        String root_";
  protected final String TEXT_44 = ".substring(0, i_";
  protected final String TEXT_45 = ");" + NL + "        new java.io.File(outputPath_";
  protected final String TEXT_46 = ", root_";
  protected final String TEXT_47 = ").mkdir();" + NL + "        outputPath_";
  protected final String TEXT_48 = " = outputPath_";
  protected final String TEXT_49 = " +\"/\" + root_";
  protected final String TEXT_50 = NL;
  protected final String TEXT_51 = NL + "       boolean isValidArchive_";
  protected final String TEXT_52 = " = true;" + NL + "       if(" + NL + "          tmpFileURL_";
  protected final String TEXT_53 = ".endsWith(\".tar.gz\")" + NL + "       || tmpFileURL_";
  protected final String TEXT_54 = ".endsWith(\".tgz\")" + NL + "       || tmpFileURL_";
  protected final String TEXT_55 = ".endsWith(\".gz\")" + NL + "       ){" + NL + "           isValidArchive_";
  protected final String TEXT_56 = " = org.talend.archive.IntegrityUtil.isGZIPValid(zipFileURL_";
  protected final String TEXT_57 = ");" + NL + "" + NL + "       }else if(tmpFileURL_";
  protected final String TEXT_58 = ".endsWith(\".tar\")){" + NL + "       \t\tisValidArchive_";
  protected final String TEXT_59 = " = org.talend.archive.IntegrityUtil.isTarValid(zipFileURL_";
  protected final String TEXT_60 = ");" + NL + "       }";
  protected final String TEXT_61 = NL + "       if(!isValidArchive_";
  protected final String TEXT_62 = "){" + NL + "            throw new RuntimeException (\"The file \" + zipFileURL_";
  protected final String TEXT_63 = " + \" is corrupted, process terminated...\" );" + NL + "              }";
  protected final String TEXT_64 = NL + NL + "    if (tmpFileURL_";
  protected final String TEXT_65 = ".endsWith(\".tar.gz\") || tmpFileURL_";
  protected final String TEXT_66 = ".endsWith(\".tgz\")){" + NL + "        org.apache.tools.tar.TarInputStream zip_";
  protected final String TEXT_67 = " = null;" + NL + "        java.io.InputStream inputStream_";
  protected final String TEXT_68 = " = null;" + NL + "        try {" + NL + "            inputStream_";
  protected final String TEXT_69 = " = new java.io.FileInputStream(zipFileURL_";
  protected final String TEXT_70 = ");" + NL + "            inputStream_";
  protected final String TEXT_71 = " = new java.util.zip.GZIPInputStream(inputStream_";
  protected final String TEXT_72 = ");" + NL + "            zip_";
  protected final String TEXT_73 = " = new org.apache.tools.tar.TarInputStream(inputStream_";
  protected final String TEXT_74 = ");" + NL + "" + NL + "            org.apache.tools.tar.TarEntry entry_";
  protected final String TEXT_75 = " = null;" + NL + "            java.io.InputStream is_";
  protected final String TEXT_76 = " = null;" + NL + "            while ((entry_";
  protected final String TEXT_77 = " = zip_";
  protected final String TEXT_78 = ".getNextEntry()) != null) {" + NL + "                boolean isDirectory_";
  protected final String TEXT_79 = " = entry_";
  protected final String TEXT_80 = ".isDirectory();" + NL + "                if (!isDirectory_";
  protected final String TEXT_81 = ") {" + NL + "                    is_";
  protected final String TEXT_82 = ";" + NL + "                }" + NL + "                String filename_";
  protected final String TEXT_83 = " =  entry_";
  protected final String TEXT_84 = ".getName();" + NL + "                util_";
  protected final String TEXT_85 = ".output(outputPath_";
  protected final String TEXT_86 = ", filename_";
  protected final String TEXT_87 = ", isDirectory_";
  protected final String TEXT_88 = ", is_";
  protected final String TEXT_89 = ");" + NL;
  protected final String TEXT_90 = NL + "                java.io.File f = new java.io.File(outputPath_";
  protected final String TEXT_91 = ");" + NL + "                f.setLastModified(entry_";
  protected final String TEXT_92 = ".getModTime().getTime());";
  protected final String TEXT_93 = NL + "                java.io.File unzippedFile = new java.io.File(outputPath_";
  protected final String TEXT_94 = ", util_";
  protected final String TEXT_95 = ".getEntryName(filename_";
  protected final String TEXT_96 = "));" + NL + "                unzippedFile.setLastModified(entry_";
  protected final String TEXT_97 = NL + "            }" + NL + "        }catch(Exception e){" + NL + "globalMap.put(\"";
  protected final String TEXT_98 = "_ERROR_MESSAGE\",e.getMessage());";
  protected final String TEXT_99 = NL + "           throw e;";
  protected final String TEXT_100 = NL + "           System.err.println(e.getMessage());";
  protected final String TEXT_101 = NL + "        }finally {" + NL + "            if(zip_";
  protected final String TEXT_102 = " != null) {" + NL + "                zip_";
  protected final String TEXT_103 = ".close();" + NL + "            } else if(inputStream_";
  protected final String TEXT_104 = " != null) {" + NL + "                inputStream_";
  protected final String TEXT_105 = ".close();" + NL + "            }" + NL + "        }" + NL + "    } else if (tmpFileURL_";
  protected final String TEXT_106 = ".endsWith(\".tar\")){" + NL + "        org.apache.tools.tar.TarInputStream zip_";
  protected final String TEXT_107 = NL + "                       java.io.File f = new java.io.File(outputPath_";
  protected final String TEXT_108 = ");" + NL + "                       f.setLastModified(entry_";
  protected final String TEXT_109 = NL + "                       java.io.File unzippedFile = new java.io.File(outputPath_";
  protected final String TEXT_110 = "));" + NL + "                       unzippedFile.setLastModified(entry_";
  protected final String TEXT_111 = NL + NL + "            }" + NL + "        }catch(Exception e){" + NL + "globalMap.put(\"";
  protected final String TEXT_112 = NL + "        } finally {" + NL + "            if(zip_";
  protected final String TEXT_113 = "!=null) {" + NL + "                zip_";
  protected final String TEXT_114 = ".close();" + NL + "            }" + NL + "        }" + NL + "    }else if (tmpFileURL_";
  protected final String TEXT_115 = ".endsWith(\".gz\")){" + NL + "        java.util.zip.GZIPInputStream zip_";
  protected final String TEXT_116 = " = new java.io.FileInputStream(new java.io.File(zipFileURL_";
  protected final String TEXT_117 = "));" + NL + "            zip_";
  protected final String TEXT_118 = ");" + NL + "" + NL + "            java.io.InputStream is_";
  protected final String TEXT_119 = ";" + NL + "            String fullName_";
  protected final String TEXT_120 = ").getName();" + NL + "            String filename_";
  protected final String TEXT_121 = " =  fullName_";
  protected final String TEXT_122 = ".substring(0, fullName_";
  protected final String TEXT_123 = ".length()-3);" + NL + "            util_";
  protected final String TEXT_124 = ",is_";
  protected final String TEXT_125 = ");" + NL + "        }catch(Exception e){" + NL + "globalMap.put(\"";
  protected final String TEXT_126 = ".close();" + NL + "            }" + NL + "        }" + NL + "    }else {" + NL + "        //the others all use the ZIP to decompression" + NL + "        com.talend.compress.zip.Unzip unzip_";
  protected final String TEXT_127 = " = new com.talend.compress.zip.Unzip(zipFileURL_";
  protected final String TEXT_128 = ", outputPath_";
  protected final String TEXT_129 = ");" + NL + "        unzip_";
  protected final String TEXT_130 = ".setNeedPassword(";
  protected final String TEXT_131 = " " + NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_132 = " = routines.system.PasswordEncryptUtil.decryptPassword(";
  protected final String TEXT_133 = NL + "\tfinal String decryptedPassword_";
  protected final String TEXT_134 = "; ";
  protected final String TEXT_135 = NL + NL + "        unzip_";
  protected final String TEXT_136 = ".setPassword(decryptedPassword_";
  protected final String TEXT_137 = ".setCheckArchive(";
  protected final String TEXT_138 = ".setVerbose(";
  protected final String TEXT_139 = ".setExtractPath(";
  protected final String TEXT_140 = ".setUtil(util_";
  protected final String TEXT_141 = ".setUseZip4jDecryption(";
  protected final String TEXT_142 = ");" + NL + "\t\t";
  protected final String TEXT_143 = NL + "\t\tunzip_";
  protected final String TEXT_144 = ".setEncording(";
  protected final String TEXT_145 = NL + NL + "        try{" + NL + "        unzip_";
  protected final String TEXT_146 = ".doUnzip();" + NL + "        }catch(Exception e){" + NL + "globalMap.put(\"";
  protected final String TEXT_147 = NL + "        }" + NL + "    }" + NL + "" + NL + "" + NL + "    for (com.talend.compress.zip.UnzippedFile uf";
  protected final String TEXT_148 = " : util_";
  protected final String TEXT_149 = ".unzippedFiles) {" + NL + "        globalMap.put(\"";
  protected final String TEXT_150 = "_CURRENT_FILE\", uf";
  protected final String TEXT_151 = ".fileName);" + NL + "        globalMap.put(\"";
  protected final String TEXT_152 = "_CURRENT_FILEPATH\", uf";
  protected final String TEXT_153 = ".filePath);";

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

    String directory = ElementParameterParser.getValue(node, "__DIRECTORY__");
    String zipFile = ElementParameterParser.getValue(node, "__ZIPFILE__");
    boolean rootName = "true".equals(ElementParameterParser.getValue(node, "__ROOTNAME__"));
    boolean extractPath = "true".equals(ElementParameterParser.getValue(node, "__EXTRACTPATH__"));

    boolean checkArchiveIntegrity = "true".equals(ElementParameterParser.getValue(node, "__INTEGRITY__"));
    boolean dieWhenArchiveCorrupted = "true".equals(ElementParameterParser.getValue(node, "__DIE_ON_ERROR__"));
    boolean isPasswordNeeded = "true".equals(ElementParameterParser.getValue(node, "__CHECKPASSWORD__"));
    boolean needPrintout = "true".equals(ElementParameterParser.getValue(node, "__PRINTOUT__"));
	boolean UseEncoding = "true".equals(ElementParameterParser.getValue(node, "__USE_ENCODING__"));

    String decryptMethod = ElementParameterParser.getValue(node, "__DECRYPT_METHOD__");
	String encoding = ElementParameterParser.getValue(node, "__ENCORDING__");

    log4jFileUtil.componentStartInfo(node);

    stringBuffer.append(TEXT_22);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(extractPath);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(zipFile );
    stringBuffer.append(TEXT_26);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_28);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_25);
    stringBuffer.append(directory );
    stringBuffer.append(TEXT_29);
    
    if (rootName) {

    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
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
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_38);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_39);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_40);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_43);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_37);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_44);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_45);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_46);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_47);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_48);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_49);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_29);
    
  }

    stringBuffer.append(TEXT_50);
    
    if(checkArchiveIntegrity){
    
    stringBuffer.append(TEXT_51);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_52);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_53);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_54);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_56);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_57);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_59);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_60);
    
    if(dieWhenArchiveCorrupted){
    
    stringBuffer.append(TEXT_61);
    stringBuffer.append( cid );
    stringBuffer.append(TEXT_62);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_63);
    
           }
    }
     
    stringBuffer.append(TEXT_64);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_65);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_66);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_70);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
     if (extractPath == true) {
    stringBuffer.append(TEXT_90);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_91);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
    } else {
    stringBuffer.append(TEXT_93);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_96);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
     }
    stringBuffer.append(TEXT_97);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
        if(dieWhenArchiveCorrupted){
        
    stringBuffer.append(TEXT_99);
    
        }else{
        
    stringBuffer.append(TEXT_100);
    
        }
        
    stringBuffer.append(TEXT_101);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_105);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_106);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_69);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_72);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_73);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_74);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_75);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_76);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_78);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_79);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_80);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
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
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_88);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_89);
     if (extractPath == true) {
    stringBuffer.append(TEXT_107);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_108);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
    } else {
    stringBuffer.append(TEXT_109);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_94);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_95);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_110);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_92);
     }
    stringBuffer.append(TEXT_111);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
        if(dieWhenArchiveCorrupted){
        
    stringBuffer.append(TEXT_99);
    
        }else{
        
    stringBuffer.append(TEXT_100);
    
        }
        
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_113);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_114);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_115);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_67);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_68);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_116);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_117);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_71);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_118);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_77);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_119);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_31);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_120);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_121);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_122);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_123);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_85);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_86);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_124);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
        if(dieWhenArchiveCorrupted){
        
    stringBuffer.append(TEXT_99);
    
        }else{
        
    stringBuffer.append(TEXT_100);
    
        }
        
    stringBuffer.append(TEXT_112);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_102);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_103);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_104);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_127);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_128);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_130);
    stringBuffer.append(isPasswordNeeded);
    stringBuffer.append(TEXT_89);
    
        String passwordFieldName = "__PASSWORD__";
        
    stringBuffer.append(TEXT_50);
    if (ElementParameterParser.canEncrypt(node, passwordFieldName)) {
    stringBuffer.append(TEXT_131);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_132);
    stringBuffer.append(ElementParameterParser.getEncryptedValue(node, passwordFieldName));
    stringBuffer.append(TEXT_19);
    } else {
    stringBuffer.append(TEXT_133);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_25);
    stringBuffer.append( ElementParameterParser.getValue(node, passwordFieldName));
    stringBuffer.append(TEXT_134);
    }
    stringBuffer.append(TEXT_135);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_136);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_137);
    stringBuffer.append(checkArchiveIntegrity);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_138);
    stringBuffer.append(needPrintout);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_139);
    stringBuffer.append(extractPath);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_140);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_129);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_141);
    stringBuffer.append("ZIP4J_DECRYPT".equals(decryptMethod));
    stringBuffer.append(TEXT_142);
    if(UseEncoding){
    stringBuffer.append(TEXT_143);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(encoding);
    stringBuffer.append(TEXT_142);
    }
    stringBuffer.append(TEXT_145);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_146);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_98);
    
        if(dieWhenArchiveCorrupted){
        
    stringBuffer.append(TEXT_99);
    
        }else{
        
    stringBuffer.append(TEXT_100);
    
        }
        
    stringBuffer.append(TEXT_147);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_148);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_149);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_150);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_151);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_152);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_153);
    stringBuffer.append(TEXT_50);
    return stringBuffer.toString();
  }
}
