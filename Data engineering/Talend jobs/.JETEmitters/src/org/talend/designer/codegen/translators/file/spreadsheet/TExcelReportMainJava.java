package org.talend.designer.codegen.translators.file.spreadsheet;

import org.talend.core.model.process.INode;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.designer.codegen.config.CodeGeneratorArgument;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.process.ElementParameterParser;
import java.util.*;

public class TExcelReportMainJava
{
  protected static String nl;
  public static synchronized TExcelReportMainJava create(String lineSeparator)
  {
    nl = lineSeparator;
    TExcelReportMainJava result = new TExcelReportMainJava();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\tnb_line_";
  protected final String TEXT_2 = "++;" + NL + "\t";
  protected final String TEXT_3 = NL + "\t\t\t\t\tjava.lang.Byte ";
  protected final String TEXT_4 = " =  ";
  protected final String TEXT_5 = ".";
  protected final String TEXT_6 = ".byteValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\t\t\tjava.lang.Short ";
  protected final String TEXT_8 = ".shortValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t\t\tjava.lang.Integer ";
  protected final String TEXT_10 = ";" + NL + "\t\t\t\t\t";
  protected final String TEXT_11 = NL + "\t\t\t\t\tjava.lang.Long ";
  protected final String TEXT_12 = ".longValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_13 = NL + "\t\t\t\t\tjava.lang.Float ";
  protected final String TEXT_14 = ".floatValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_15 = NL + "\t\t\t\t\tjava.lang.Double ";
  protected final String TEXT_16 = ".doubleValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\t\tjava.util.Boolean ";
  protected final String TEXT_18 = ".booleanValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_19 = NL + "\t\t\t\t\tjava.lang.Character ";
  protected final String TEXT_20 = ".charValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_21 = NL + "\t\t\t\t\tjava.math.BigDecimal ";
  protected final String TEXT_22 = NL + "\t\t\t\t\tjava.lang.Byte[] ";
  protected final String TEXT_23 = NL + "\t\t\t\t\tjava.util.Date ";
  protected final String TEXT_24 = ".intValue();" + NL + "\t\t\t\t\t";
  protected final String TEXT_25 = NL + "\t\t\t\t\tString ";
  protected final String TEXT_26 = NL + "\t\t\t\tString ";
  protected final String TEXT_27 = "JoinKey = ";
  protected final String TEXT_28 = ";" + NL + "\t\t\t\tString ";
  protected final String TEXT_29 = ";" + NL + "\t\t\t";
  protected final String TEXT_30 = NL + "\t\t\t\t//create a new bean and class if necessary" + NL + "\t\t\t\torg.apache.commons.beanutils.DynaBean ";
  protected final String TEXT_31 = " = null;" + NL + "\t" + NL + "\t\t\t\tif (hasNesting) {" + NL + "\t\t\t\t    //if there is nesting then the parent object may already exist" + NL + "\t\t\t\t    if (parentChildMap.containsKey(\"";
  protected final String TEXT_32 = "\")) {" + NL + "\t\t\t\t\t    //ok this is a parent " + NL + "\t\t\t\t\t    ";
  protected final String TEXT_33 = NL + NL + "\t\t\t\t\t\t\t    if (!  ";
  protected final String TEXT_34 = "Map.containsKey(";
  protected final String TEXT_35 = "JoinKey)) {" + NL + "\t\t\t\t\t\t\t\t// we don't already have the object so create a new one the nesting of the child object in the parent will be done later" + NL + "\t\t\t\t\t\t\t\t// when we know that all the necessary objects have been creates" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_36 = " = ";
  protected final String TEXT_37 = "DynaClass.newInstance();" + NL + "\t\t\t\t\t\t\t\t//";
  protected final String TEXT_38 = "Collection = new java.util.LinkedHashSet();" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_39 = "Collection.add(";
  protected final String TEXT_40 = ");" + NL + "\t\t\t\t\t\t\t    }" + NL + "\t\t\t\t\t\t    ";
  protected final String TEXT_41 = NL + "\t\t\t\t    } else {" + NL + "\t\t\t\t\t    //ok this is a child and is never a parent so alway create an instance" + NL + "\t\t\t\t\t    ";
  protected final String TEXT_42 = "DynaClass.newInstance();" + NL + "\t\t\t\t\t    ";
  protected final String TEXT_43 = ");" + NL + "\t\t\t\t    }" + NL + "\t\t\t\t} else {" + NL + "\t\t\t\t    if (! parentChildMap.containsKey(\"";
  protected final String TEXT_44 = "\")) {" + NL + "\t\t\t\t\t    //ok this is a child and is never a parent so alway create an instance" + NL + "\t\t\t\t\t    ";
  protected final String TEXT_45 = ");" + NL + "\t\t\t\t    }" + NL + "\t\t\t\t}" + NL + "\t\t\t\t" + NL + "\t\t\t    ";
  protected final String TEXT_46 = NL + "\t\t\t    \t\tif (";
  protected final String TEXT_47 = " != null) {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_48 = "Map.put(";
  protected final String TEXT_49 = "JoinKey, ";
  protected final String TEXT_50 = ");" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_51 = ".set(\"";
  protected final String TEXT_52 = "\" , ";
  protected final String TEXT_53 = "Collection);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t    ";
  protected final String TEXT_54 = NL + "\t\t\t    " + NL + "\t\t\t    " + NL + "\t\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_55 = NL + "\t\t\t\t\tif (";
  protected final String TEXT_56 = " != null) {" + NL + "\t\t\t\t";
  protected final String TEXT_57 = NL + "\t\t\t\t\t\t";
  protected final String TEXT_58 = ");" + NL + "\t\t\t\t\t";
  protected final String TEXT_59 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_60 = NL + "\t\t\t\t\t\t//get the parent object and add this child to the relevant Collection" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_61 = "Map.get(";
  protected final String TEXT_62 = "JoinKey);" + NL + "\t\t\t\t\t\tjava.util.Collection ";
  protected final String TEXT_63 = "Collection = (java.util.Collection) ";
  protected final String TEXT_64 = ".get(\"";
  protected final String TEXT_65 = "\");" + NL + "\t\t\t\t\t\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
CodeGeneratorArgument codeGenArgument = (CodeGeneratorArgument) argument;
INode node = (INode)codeGenArgument.getArgument();



List<IMetadataTable> metadatas = node.getMetadataList();
if ((metadatas!=null)&&(metadatas.size()>0)) {
    IMetadataTable metadata = metadatas.get(0);
    if (metadata!=null) {
        String cid = node.getUniqueName();
	
    stringBuffer.append(TEXT_1);
    stringBuffer.append(cid );
    stringBuffer.append(TEXT_2);
    
	
    	List< ? extends IConnection> conns = node.getIncomingConnections();
    	for (IConnection conn : conns) {
    		if (conn.getLineStyle().hasConnectionCategory(IConnectionCategory.DATA)) {
    			List<IMetadataColumn> columns = metadata.getListColumns();
    			int sizeColumns = columns.size();

		for (int i = 0; i < sizeColumns; i++) {
			int rowSetNo = i+1;

			IMetadataColumn column = columns.get(i);
			JavaType javaType = JavaTypesManager.getJavaTypeFromId(column.getTalendType());
			boolean isPrimitive = JavaTypesManager.isJavaPrimitiveType( javaType, column.isNullable());

			    if(isPrimitive) {		    
				if(javaType == JavaTypesManager.BYTE) {
					
    stringBuffer.append(TEXT_3);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_6);
    
				}
				else if(javaType == JavaTypesManager.SHORT) {
					
    stringBuffer.append(TEXT_7);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_8);
    
				}				
				else if(javaType == JavaTypesManager.INTEGER) {
					
    stringBuffer.append(TEXT_9);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}
				else if(javaType == JavaTypesManager.LONG) {
					
    stringBuffer.append(TEXT_11);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_12);
    
				}
				else if(javaType == JavaTypesManager.FLOAT) {
					
    stringBuffer.append(TEXT_13);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_14);
    
				}
				else if(javaType == JavaTypesManager.DOUBLE) {
					
    stringBuffer.append(TEXT_15);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_16);
    
				}
			    	if(javaType == JavaTypesManager.BOOLEAN) {
					
    stringBuffer.append(TEXT_17);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_18);
    
				}
				else if(javaType == JavaTypesManager.CHARACTER) {
					
    stringBuffer.append(TEXT_19);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_20);
    
				}				
			    }else {
			    	if(javaType == JavaTypesManager.BOOLEAN) {
					
    stringBuffer.append(TEXT_17);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_18);
    
				}
				else if(javaType == JavaTypesManager.BIGDECIMAL) {
					
    stringBuffer.append(TEXT_21);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}				
				else if(javaType == JavaTypesManager.BYTE) {
					
    stringBuffer.append(TEXT_3);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_6);
    
				}
				else if(javaType == JavaTypesManager.BYTE_ARRAY) {
					
    stringBuffer.append(TEXT_22);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}				
				else if(javaType == JavaTypesManager.CHARACTER) {
					
    stringBuffer.append(TEXT_19);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_20);
    
				}
				else if(javaType == JavaTypesManager.DATE) {
					
    stringBuffer.append(TEXT_23);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}
				else if(javaType == JavaTypesManager.DOUBLE) {
					
    stringBuffer.append(TEXT_15);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_16);
    
				}
				else if(javaType == JavaTypesManager.INTEGER) {
					
    stringBuffer.append(TEXT_9);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_24);
    
				}
				else if(javaType == JavaTypesManager.LONG) {
					
    stringBuffer.append(TEXT_11);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_12);
    
				}
				else if(javaType == JavaTypesManager.FLOAT) {
					
    stringBuffer.append(TEXT_13);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_14);
    
				}
				else if(javaType == JavaTypesManager.SHORT) {
					
    stringBuffer.append(TEXT_7);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_8);
    
				}
				else if(javaType == JavaTypesManager.STRING) {
					
    stringBuffer.append(TEXT_25);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}				
				else {
					
    stringBuffer.append(TEXT_25);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(conn.getName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(column.getLabel());
    stringBuffer.append(TEXT_10);
    
				}
			    }


		}

		List<Map<String, String>> objects =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNAMESTABLE__");
		List<Map<String, String>> groups = (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__GROUPBYS__");
		List<Map<String, String>> objectNesting =  (List<Map<String,String>>)ElementParameterParser.getObjectValue(node, "__OBJECTNESTING__");
		HashMap<String, ArrayList> parentChildMap = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> childParentMap = new HashMap<String, ArrayList>();
		ArrayList<String> allParentObjects = new ArrayList<String>();
		ArrayList<String> allChildObjects = new ArrayList<String>();
		HashMap<Integer, String> idToNameMap = new HashMap<Integer, String>();

		HashMap<String, ArrayList> objectRelationships = new HashMap<String, ArrayList>();
		for (int k=0; k < objectNesting.size() ; k++) {
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
				Integer id = (Integer) currentObjectID;
				idToNameMap.put(id, currentObjectName);
				String keyID = parentObjectID + "~|" + childObjectID;
				String fieldNames = parentField + "~|" + childField;
				
				if (currentObjectID == parentObjectID) {
					parentObjectName = currentObjectName;
					allParentObjects.add(parentObjectName);
				}
				else if(currentObjectID == childObjectID) {
					childObjectName = currentObjectName;
					allChildObjects.add(childObjectName);
				}
				
				if (! objectRelationships.containsKey(keyID)) {
					ArrayList ar = new ArrayList();
					ar.add(fieldNames);
					objectRelationships.put(keyID, ar);
				} else {
					ArrayList fieldData = objectRelationships.get(keyID);
					if (fieldData.indexOf(fieldNames) == -1) {
						//in theory should never get here - but this will prevent duplicates
						fieldData.add(fieldNames);
					}
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
		
		//OK now add the join fields to the main template
		for (Map.Entry entry : objectRelationships.entrySet()) {
			String joinObjectsStr = (String) entry.getKey();
			ArrayList joinFields = (ArrayList) entry.getValue();
			//ok what does this map contain ?
			String parentKey="";
			String childKey="";
			String child = "";
			String parent = "";
			Iterator<String> fieldsItr = joinFields.iterator();
			while (fieldsItr.hasNext()) {
				String fieldsDelim = fieldsItr.next();
				String[] fields =  fieldsDelim.split("~\\|");	
				String[] joinObjects = joinObjectsStr.split("~\\|");
				Integer parentId = Integer.parseInt(joinObjects[0]);
				Integer childId = Integer.parseInt(joinObjects[1]);
				parent = idToNameMap.get(parentId);
				child = idToNameMap.get(childId);
				//String joinStr="";
				for (int i=0 ; i<fields.length ; i+=2) {
					parentKey += fields[i] + ".toString() +";
					childKey += fields[i+1] + ".toString() +";
					//joinStr += s + ".toString() +";
				}
				

			}
			
			//remove any trailing concatenation symbols
			if (parentKey.endsWith("+")) {
				parentKey = parentKey.substring(0, parentKey.length()-1);
			}
			if (childKey.endsWith("+")) {
				childKey = childKey.substring(0, childKey.length()-1);
			}
			
			
    stringBuffer.append(TEXT_26);
    stringBuffer.append(parent);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(parentKey);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(child);
    stringBuffer.append(parent);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(childKey);
    stringBuffer.append(TEXT_29);
    
		}
		
		ArrayList<String> objectOrder = new ArrayList<String>();
		
		if (parentChildMap.size() == 0) {
			//we don't have any nesting so object order doesn't matter 
			//just put everything into the array so the code below can 
			//remain the same
			for (int i=0 ; i<objects.size() ; i++) {
				Map<String, String> currentObject = objects.get(i);
				int currentObjectID = Integer.parseInt(currentObject.get("OBJECT_ID"));
				String currentObjectName = currentObject.get("OBJECTNAME");
				objectOrder.add(currentObjectName);
			}
		}
		
		
		// This should contain a list of the object in the correct order
		for (Map.Entry entry : parentChildMap.entrySet()) {
			String parentName = (String) entry.getKey();
			ArrayList<String> children = (ArrayList<String>) entry.getValue();
			int indexParent = objectOrder.indexOf(parentName);
			for (String childName : children) {
				if (indexParent == -1) {
					objectOrder.add(parentName);
					if (! objectOrder.contains(childName)) {
						objectOrder.add(indexParent +1 , childName);
					}
					
				}
			}
		}
		

		Collections.reverse(objectOrder);
		
		
		
		for (String objectName : objectOrder) {
		
			
    stringBuffer.append(TEXT_30);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_32);
    
					   //Might end up creating a few extra objects this way - but since everything is in a row as input we
					   //should still get the correct result
					   	if (parentChildMap.containsKey(objectName)) {
							ArrayList <String> childObjects = parentChildMap.get(objectName);
							for (String child : childObjects) {
					    
    stringBuffer.append(TEXT_33);
    stringBuffer.append(objectName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(objectName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_40);
      
						    	}
						    }
						    
    stringBuffer.append(TEXT_41);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_43);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_44);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_45);
    
			   //Add the ojbects to the hashmaps
				if (parentChildMap.containsKey(objectName)) {
					ArrayList <String> childObjects = parentChildMap.get(objectName);
					for (String child : childObjects) {
			    
    stringBuffer.append(TEXT_46);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(objectName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(objectName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_49);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_50);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_53);
    
			    		}
				}
			    
    stringBuffer.append(TEXT_54);
    		
			}

			//ok now add the fields to the objects
			int objectID=0;
			for (int i=0 ; i<objects.size() ; i++) {
				Map<String, String> object = objects.get(i);
				objectID = Integer.parseInt(object.get("OBJECT_ID"));
				String objectName = object.get("OBJECTNAME");
				
    stringBuffer.append(TEXT_55);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_56);
    
				for (int j=0 ; j < groups.size(); j++) {
					Map<String, String> fields = groups.get(j);
					int fieldObjectNo = Integer.parseInt(fields.get("OBJECT_GROUP"));
					String fieldName = fields.get("SCHEMA_COLUMN");
					if (fieldObjectNo == objectID) {
					
    stringBuffer.append(TEXT_57);
    stringBuffer.append(objectName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_58);
    
					}
				}
				
    stringBuffer.append(TEXT_59);
    
			}
			
			//add the children to their parents
			
			for (Map.Entry entry : parentChildMap.entrySet()) {
				String parentName = (String) entry.getKey();
				ArrayList<String> children = (ArrayList<String>) entry.getValue();
				for (String child : children) {
					
    stringBuffer.append(TEXT_60);
    stringBuffer.append(parentName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(parentName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(child);
    stringBuffer.append(parentName);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(parentName);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(parentName);
    stringBuffer.append(TEXT_64);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_65);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(child);
    stringBuffer.append(TEXT_58);
    
				}
			}
			

		}
	}
   }
}


    return stringBuffer.toString();
  }
}
