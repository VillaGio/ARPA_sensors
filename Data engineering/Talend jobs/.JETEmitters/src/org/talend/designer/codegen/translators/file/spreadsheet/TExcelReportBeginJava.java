package org.talend.designer.codegen.translators.file.spreadsheet;

import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.*;
import java.util.jar.*;

public class TExcelReportBeginJava
{
  protected static String nl;
  public static synchronized TExcelReportBeginJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TExcelReportBeginJava result = new TExcelReportBeginJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "\tString templateFileName = ";
  protected final String TEXT_3 = ";" + NL + "\tString outputFilename = ";
  protected final String TEXT_4 = ";" + NL + "\t";
  protected final String TEXT_5 = NL + "\t\t\t\t\tjava.lang.Integer rowCount = new java.lang.Integer(0);" + NL + "\t\t\t\t\tjava.util.HashMap parentObjects = new java.util.LinkedHashMap();" + NL + "\t\t\t\t\tjava.util.Collection objects = new java.util.LinkedHashSet();" + NL + "\t\t\t\t";
  protected final String TEXT_6 = NL + "\t\t\t\t\tjava.util.Collection ";
  protected final String TEXT_7 = "Collection = new java.util.LinkedHashSet();" + NL + "\t\t\t\t\t";
  protected final String TEXT_8 = NL + "\t\tjava.util.HashMap<String, java.util.ArrayList> parentChildMap = new java.util.HashMap<String, java.util.ArrayList>();" + NL + "\t\tjava.util.HashMap<String, java.util.ArrayList> childParentMap = new java.util.HashMap<String, java.util.ArrayList>();" + NL + "\t\tBoolean hasNesting = false;" + NL + "\t";
  protected final String TEXT_9 = NL + "\t\t\thasNesting = true;" + NL + "\t\t";
  protected final String TEXT_10 = NL + "\t\t\tjava.util.ArrayList<String> ";
  protected final String TEXT_11 = "ChildObjects = new java.util.ArrayList<String>();" + NL + "\t\t";
  protected final String TEXT_12 = NL + "\t\t\t\t";
  protected final String TEXT_13 = "ChildObjects.add(\"";
  protected final String TEXT_14 = "\");" + NL + "\t\t\t\tjava.util.HashMap<String, org.apache.commons.beanutils.DynaBean> ";
  protected final String TEXT_15 = "Map = " + NL + "\t\t\t\tnew java.util.HashMap<String, org.apache.commons.beanutils.DynaBean>();\t\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_16 = NL + "\t\t\tparentChildMap.put(\"";
  protected final String TEXT_17 = "\", ";
  protected final String TEXT_18 = "ChildObjects);\t\t\t" + NL + "\t\t";
  protected final String TEXT_19 = NL + "\t\t\torg.apache.commons.beanutils.DynaProperty[] ";
  protected final String TEXT_20 = "Properties =" + NL + "\t\t\t{" + NL + "\t\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t\t\tnew org.apache.commons.beanutils.DynaProperty(";
  protected final String TEXT_22 = ")," + NL + "\t\t\t\t\t";
  protected final String TEXT_23 = NL + "\t\t\t\t\t\tnew org.apache.commons.beanutils.DynaProperty(\"";
  protected final String TEXT_24 = "\" , java.util.Collection.class)," + NL + "\t\t\t\t\t\t";
  protected final String TEXT_25 = "\t" + NL + "\t\t\t};" + NL + "\t\t";
  protected final String TEXT_26 = NL + "\t\t\torg.apache.commons.beanutils.DynaClass ";
  protected final String TEXT_27 = "DynaClass =" + NL + "\t\t\t\tnew org.apache.commons.beanutils.BasicDynaClass(\"";
  protected final String TEXT_28 = "\", null, ";
  protected final String TEXT_29 = "Properties);" + NL + "\t\t";
  protected final String TEXT_30 = NL + NL + "int nb_line_";
  protected final String TEXT_31 = " = 0;";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    

//YUK THESE TEMPLATES ARE NASTY - FIND OUT IF YOU CAN CREATE FUNCTION IN THEM TO REUSE LOGIC

	CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
	INode node = (INode)codeGenArgument.getArgument();

	String templateFilename = ElementParameterParser.getValue(node, "__TEMPLATEFILENAME__");
	String outputFilename = ElementParameterParser.getValue(node, "__OUTPUTFILENAME__");
	String sheetname = ElementParameterParser.getValue(node, "__SHEETNAME__");
	String cid = "";

	
    stringBuffer.append(TEXT_2);
    stringBuffer.append(templateFilename );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(outputFilename );
    stringBuffer.append(TEXT_4);
    
		HashMap<String, ArrayList> objectMap = new HashMap<String, ArrayList>();
	try {
		cid = node.getUniqueName();
		List<Map<String, String>> groups = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__GROUPBYS__");
		List<Map<String, String>> objects =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNAMESTABLE__");

		//setup the objects we require to establish relationships

		//TOO avoid many nested loops and code which would be a nightmare to read later on (it's already too deeply nested)
		//but I can't use methods and so have to write it in rediculously large blocks, so here we only deal with defining
		//the objects and it's fields (eg Emp -> String Name, Double Salary, Double bonus) we will deal with the nesting of
		//objects in the block after this

		List<IMetadataTable> metadatas = node.getMetadataList();
		if ((metadatas!=null)&&(metadatas.size()>0)) {
			IMetadataTable metadata = metadatas.get(0);
			if (metadata!=null) {
				//aWriter.write("HELLO\n");
				List<IMetadataColumn> columns = metadata.getListColumns();
				int sizeColumns = columns.size();
				
    stringBuffer.append(TEXT_5);
    

				for (int i=0 ; i<objects.size(); i++) {
					Map<String, String> object = objects.get(i);
					int objectID = Integer.parseInt(object.get("OBJECT_ID"));
					String objectName = object.get("OBJECTNAME");

					
    stringBuffer.append(TEXT_6);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_7);
    

					//aWriter.write("ObjectID is " + objectID + "object name is " + objectName +"\n");
					//This will be used to define the variable names and type in the class we generate and jar up
					ArrayList<String> classVariables = new ArrayList<String>();
					for (int j=0 ; j<groups.size(); j++) {
						//get the columns associdated with this object
						Map<String, String> fields = groups.get(j);
						int fieldObjectNo = Integer.parseInt(fields.get("OBJECT_GROUP"));
						String fieldName = fields.get("SCHEMA_COLUMN");
						if (fieldObjectNo == objectID) {
							for (int k=0; k < sizeColumns; k++) {
							//get the meta data associated with the column
								IMetadataColumn column = columns.get(k);
								JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
								boolean isPrimitive = JavaTypesManager.isJavaPrimitiveType( javaType, column.isNullable());
								String columnName = column.getLabel();
								if (columnName.compareTo(fieldName) == 0) {
									//ok this is the correct column - determine the data type
									String dataType = "";
									String variableDetails;

									if(isPrimitive) {
										if(javaType == JavaTypesManager.BYTE) {
											dataType = "java.lang.Byte.class";
										}
										else if(javaType == JavaTypesManager.SHORT) {
											dataType = "java.lang.Short.class";
										}										
										else if(javaType == JavaTypesManager.INTEGER) {
											dataType = "java.lang.Integer.class";
										}
										else if(javaType == JavaTypesManager.LONG) {
											dataType = "java.lang.Long.class";
										}										
										else if(javaType == JavaTypesManager.FLOAT) {
											dataType = "java.lang.Float.class";
										}
										else if(javaType == JavaTypesManager.DOUBLE) {
											dataType = "java.lang.Double.class";
										}
										else if(javaType == JavaTypesManager.BOOLEAN) {
											dataType = "java.util.Boolean.class";
										}
										else if(javaType == JavaTypesManager.CHARACTER) {
											dataType = "java.lang.Character.class";
										} else {
											dataType = "java.lang.Double.class";
										}										
									}else {
										if(javaType == JavaTypesManager.BOOLEAN) {
											dataType = "java.util.Boolean.class";
										}
										else if(javaType == JavaTypesManager.BIGDECIMAL) {
											dataType = "java.math.BigDecimal.class";
						
										}				
										else if(javaType == JavaTypesManager.BYTE) {
											dataType = "java.lang.Byte.class";
										}
										else if(javaType == JavaTypesManager.BYTE_ARRAY) {
											dataType = "java.lang.Byte.class";
										}				
										else if(javaType == JavaTypesManager.CHARACTER) {
											dataType = "java.lang.Character.class";
										}
										else if(javaType == JavaTypesManager.DATE) {
											dataType = "java.util.Date.class";
										}
										else if(javaType == JavaTypesManager.DOUBLE) {
											dataType = "java.lang.Double.class";
										}
										else if(javaType == JavaTypesManager.INTEGER) {
											dataType = "java.lang.Integer.class";
										}
										else if(javaType == JavaTypesManager.LONG) {
											dataType = "java.lang.Long.class";
										}
										else if(javaType == JavaTypesManager.FLOAT) {
											dataType = "java.lang.Float.class";
										}
										else if(javaType == JavaTypesManager.SHORT) {
											dataType = "java.lang.Short.class";
										}
										else if(javaType == JavaTypesManager.STRING) {
											dataType = "java.lang.String.class";
										}				
										else {
											dataType = "java.lang.String.class";
										}	
									}

									variableDetails =   "\"" +  columnName + "\"," + dataType;
									classVariables.add(variableDetails);
								}
							}
						objectMap.put(objectName,classVariables);
						}
					}
				}
			}

		}

	}catch (Exception z) {
		z.printStackTrace();
		System.exit(1);
	}

	//Now deal with nested objects
	//eg Dept -> int DeptID, String DeptName, int NoEmployess, Employee[] Employees
	//In other words one department can contain many employee objects
	//Therefore we will create an array of the child objects within the parent object

	HashMap<String, HashMap<String, String>> nestedObjectDetails = new HashMap<String, HashMap<String, String>>();
	List<Map<String, String>> objectNesting =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNESTING__");
	List<Map<String, String>> objects =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNAMESTABLE__");
	HashMap<String, ArrayList> parentChildMap = new HashMap<String, ArrayList>();
	HashMap<String, ArrayList> childParentMap = new HashMap<String, ArrayList>();
	Boolean hasNesting = false;
	
    stringBuffer.append(TEXT_8);
    
	
	for (int k=0; k < objectNesting.size() ; k++) {
	        hasNesting = true;
		
		Map<String, String> fields =objectNesting.get(k);
		int parentObjectID = Integer.parseInt(fields.get("PARENT_OBJECT"));
		String parentField = fields.get("PARENT_FIELD");
		int childObjectID = Integer.parseInt(fields.get("CHILD_OBJECT"));
		String childField = fields.get("CHILD_FIELD");
		String childObjectName = "";
		String parentObjectName = "";
		for (int m=0 ; m<objects.size() ; m++) {
			Map<String, String> currentObject = objects.get(m);
			int currentObjectID = Integer.parseInt(currentObject.get("OBJECT_ID"));
			String currentObjectName = currentObject.get("OBJECTNAME");
			if (currentObjectID == parentObjectID) {
				parentObjectName = currentObjectName;
			}
			else if(currentObjectID == childObjectID) {
				childObjectName = currentObjectName;
			}
		}
		
		ArrayList<String> childObjects = null;
		
		//An object could have multiple children of different object types
		//The blow block caters for this
		
		if (! parentChildMap.containsKey(parentObjectName)) {
			childObjects = new ArrayList<String>();
			childObjects.add(childObjectName);
			parentChildMap.put(parentObjectName, childObjects);
		} else {
			childObjects = parentChildMap.get(parentObjectName);
			//Have we come across this child before ?
			if (childObjects.indexOf(childObjectName) == -1) {
				childObjects.add(childObjectName);
			}
		}
	}

	if (hasNesting) {
		//inject the information into the generated code
		
    stringBuffer.append(TEXT_9);
    
	}
	
	// Add the necessary collections and populate the maps 
	
	for(Map.Entry<String, ArrayList> e : parentChildMap.entrySet()) {
		String parentObjectName = e.getKey();
		ArrayList<String> childObjects = e.getValue();
		
		
    stringBuffer.append(TEXT_10);
    stringBuffer.append(parentObjectName);
    stringBuffer.append(TEXT_11);
    

		
		for (String childObjectName : childObjects) {
			
    stringBuffer.append(TEXT_12);
    stringBuffer.append(parentObjectName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(childObjectName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(parentObjectName);
    stringBuffer.append(childObjectName);
    stringBuffer.append(TEXT_15);
    
		}
		
		
    stringBuffer.append(TEXT_16);
    stringBuffer.append(parentObjectName);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(parentObjectName);
    stringBuffer.append(TEXT_18);
    
		
	}
	
	
	//OK Define the dynabeans properties and dynaClass

	Iterator keyValuePairs = objectMap.entrySet().iterator();

	for (int i=0 ; i < objectMap.size() ; i++) {
		Map.Entry mapentry = (Map.Entry) keyValuePairs.next();
		String objectName = (String) mapentry.getKey();
		ArrayList<String> fieldData = (ArrayList<String>) mapentry.getValue();

		//Add the properties and dynaClasses to the generated code
		
    stringBuffer.append(TEXT_19);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_20);
    
				Iterator it = fieldData.iterator();
				while(it.hasNext()) {
					String classType = it.next().toString();
					
    stringBuffer.append(TEXT_21);
    stringBuffer.append(classType);
    stringBuffer.append(TEXT_22);
    
				}
				
    stringBuffer.append(TEXT_12);
    
				if (hasNesting && parentChildMap.containsKey(objectName)) {
					ArrayList<String> children =  parentChildMap.get(objectName);
					for (String child : children) {
						
    stringBuffer.append(TEXT_23);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_24);
    
					}
				}
				
    stringBuffer.append(TEXT_25);
    
		
		
    stringBuffer.append(TEXT_26);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_29);
    
				
	}

    stringBuffer.append(TEXT_30);
    stringBuffer.append(cid);
    stringBuffer.append(TEXT_31);
    return stringBuffer.toString();
  }
}
