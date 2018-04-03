package com.example.demo;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import com.asgard.base.Transaction;
import com.asgard.base.content.fact.Fact;
import com.asgard.base.content.fact.FactCategory;
import com.asgard.crypto.Certificate;
import com.asgard.crypto.CertificationRequest;
import com.asgard.crypto.ECKey;
import com.asgard.info.BlockInfo;
import com.asgard.info.FactCategoryInfo;
import com.asgard.info.Message;
import com.asgard.info.TransactionInfo;
import com.asgard.sdk.exception.BuilderException;
import com.asgard.sdk.exception.RemoteServiceException;
import com.asgard.sdk.exception.VerifyException;
import com.asgard.sdk.keybox.KeyBox;
import com.asgard.sdk.service.BlockChainService;
import com.asgard.sdk.service.bc.BlockchainRemoteService;
import com.asgard.sdk.service.ra.RARemoteService;
import com.asgard.sdk.txbuilder.CertificateRequestBuilder;
import com.asgard.sdk.txbuilder.FactBuilder;
import com.asgard.sdk.txbuilder.FactCategoryBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.security.cert.CertificateException;



@RestController
public class Hellocontroller {
	//java dictionary https://stackoverflow.com/questions/13543457/how-do-you-create-a-dictionary-in-java
	Map<String, ArrayList<Goodlist>> vdbsystem = new HashMap<String, ArrayList<Goodlist>>();
	String nowreceivefile = "";
     
	
	
	
	
	
@Value("$(cup)")
	private String cup;






@GetMapping("/")
public String index() throws Exception {
  	String applicationID = "2d9829ab72864707872b486d8ec12dce";
	  String access_token = "f5c5ef3e-4683-4e18-87b1-988d402b25cc";
	  MyResult info = null;
	
	   
	 
	  
	  try {
	  		getCertificateState(applicationID,access_token);
	  	} catch (IOException | RemoteServiceException  e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}

	  	//download certificate
	  	  try {
	  		downLoadCertificate(applicationID,access_token);
	  	} catch (IOException | RemoteServiceException | CertificateException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}


	 /*
	  		//add category
	  	  try {
	  		addFactCategory("货物清单");
	  	} catch (RemoteServiceException | VerifyException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
	
	*/
	
	  		//add category
	  	  try {
	  		//info = getFactCategoryByName("检疫冷链业务");
	  		info = getFactCategoryByName("货物清单");
	  	} catch (RemoteServiceException |IOException  e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}

	  	  try {
	  	
	  	 /*addFact(info.hashforblock, info.Category,assetid,assetfrom,assetto,agent,temprature,weather,
	  			people,storeplace,checkstate);
       */
	  		  //goolist checkid number
	  		 ArrayList<Goodlist> listfor =	getxlsx(nowreceivefile);
	  		 
	  		System.out.println("yangming");
	  		//https://stackoverflow.com/questions/6846841/how-do-i-get-length-of-list-of-lists-in-java
	  		int length = listfor.size();
	  		//length of goodlist
			for (int i = 0; i < listfor.size(); i++) {
				Goodlist vb =listfor.get(i);
				addFact(info.hashforblock, info.Category,vb.brandname,vb.checkid,vb.ingredient,vb.note,vb.numberandweight,vb.numberid,
			  			vb.type,vb.typeofsafe,"ye");
			}
	  		//get checkid
	  		Goodlist vb =listfor.get(0);
	  		vdbsystem.put(vb.checkid, listfor);
	  		
	  		
	  		//addFact(info.hashforblock, info.Category,vb.brandname,vb.checkid,vb.ingredient,vb.note,vb.numberandweight,vb.numberid,
		  	//		vb.type,vb.typeofsafe,"ye");
	  		//long blockHeight = 295;
//         
	  		/*
    BlockInfo blockInfo = ServiceManager.INSTANCE.getService().getBCRemoteService().getBlockService() .getBlockByHeight(blockHeight); 
     System.out.println(blockInfo);
     printFields(blockInfo);
     System.out.println("========");
     String txHash = "c63df4aed5d5351e6f55ad97b1fe27bc22302b3735682aa57974f7f4f88f11cf";
     TransactionInfo tx = ServiceManager.INSTANCE.getService().getBCRemoteService().getTransactionService().getTransactionByHash(txHash);
     tx.toString();
    Transaction t = tx.getTransaction();
    Fact fact=(Fact)(t.content);
    TreeMap<String,String> map=fact.getFactContent();
    String text=map.get("备注");
    System.out.println(text);
	 */ 		 
	  	} catch (RemoteServiceException |VerifyException  e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}

	  	//Message<Goodlist> result= getBlockByHeight(Long.valueOf(789));
	  	  
	  	  

	 
	 
	  
    return "allok";
}



public void printFields(Object obj) throws Exception {
    Class<?> objClass = obj.getClass();

    Field[] fields = objClass.getFields();
    for(Field field : fields) {
        String name = field.getName();
        Object value = field.get(obj);

        System.out.println(name + ": " + value.toString());
    }
    
}





public class MyResult {

	   String hashforblock;
	   FactCategory Category;
	   // etc
	}


public class Goodlist {

	   String  checkid;
	  String note;
      String ingredient;
	   String numberid;
	   String type;
	   String numberandweight;
	   String brandname;
	   String typeofsafe;
	   // etc

	
	}





//https://stackoverflow.com/questions/27101931/required-multipartfile-parameter-file-is-not-present-in-spring-mvc/27101988
//bug for Required MultipartFile parameter 'file' is not present in spring mvc

@RequestMapping(value = "/posts") // //new annotation since 4.3
public String singleFileUpload(@RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) {

    if (file.isEmpty()) {
        redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
        return "file is empty";
    }

    try {

        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        System.out.println(file.getOriginalFilename());
        nowreceivefile = file.getOriginalFilename();
        System.out.println("2333");
        Path path = Paths.get(file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded 222'" + file.getOriginalFilename() + "'");
        return file.getOriginalFilename();

    } catch (IOException e) {
        e.printStackTrace();
    }

    return "i donot know";
}

@GetMapping("/uploadStatus")
public String uploadStatus() {
    return "uploadStatus";
}


@GetMapping("/search")
//get url parameter https://stackoverflow.com/questions/32201441/how-do-i-retrieve-query-parameters-in-spring-boot
//return json https://stackoverflow.com/questions/44839753/returning-json-object-as-response-in-spring-boot
//https://stackoverflow.com/questions/18857884/how-to-convert-arraylist-of-custom-class-to-jsonarray-in-java
public Map<String, String>   uploadStats(@RequestParam("checkid") String checkid) {
	HashMap<String, String> map = new HashMap<>();
	ArrayList<Goodlist> List1 = vdbsystem.get(checkid);
	String jsonText =  new Gson().toJson(List1);
	map.put(List1.get(0).checkid,jsonText);
	return map;
}




@RequestMapping(value = "/block", method = RequestMethod.POST)
public ResponseEntity<Business> update(@RequestBody  Business  b) {


    // TODO: call persistence layer to update
    return new ResponseEntity<Business>(b, HttpStatus.OK);
}












@RequestMapping(value = "/hello",method = RequestMethod.GET)
public String say() throws IOException {
	Excel ex = new Excel();
	ReadWriteExcelFile ex1 = new ReadWriteExcelFile();

	String fileName="order.xlsx"; 
	//Read an Excel File from C:\\test.xls and Store in a Vector
    Vector dataHolder=ex.readExcelFile(fileName);
    //Print the data read
    //ex.printCellDataToConsole(dataHolder);
    ex1.readXLSFile();
	return  cup;
	
}






//cretae csr base 64 string
public void createSCR() throws VerifyException, BuilderException,
IOException {
//
ECKey key = ECKey.generateKeyPair();
//DN
String dn = "CN=example";
//

//String role = RoleEnum.CONNECTION.name();
//String subrole = SubRoleEnum.NVP.name();

String role = "TRANSACTION";
String subrole = "FACT_PRODUCERs";
String memberID = "88759d9e-55f2-48af-8665-0eb57b31ac9e"; //   CSR
CertificateRequestBuilder builder = new CertificateRequestBuilder(key,dn, role, subrole, memberID);
CertificationRequest csr = builder.build();
String scrBase64Str = csr.getBase64String();
//CSR Base64
System.out.print(csr.getBase64String());
}


//aply certificate

public String applyCertificate() throws IOException,
RemoteServiceException,VerifyException, BuilderException {
// KeyBox
	ECKey key = KeyBoxManager.INSTANCE.getKeybox().getKey("MyKey");
	//    DN
	String dn = "CN=example";
	//
	//String role = RoleEnum.CONNECTION.name();
	//String subrole = SubRoleEnum.NVP.name();

	 String role = "TRANSACTION";
	 String subrole = "FACT_PRODUCER";


	String memberID = "88759d9e-55f2-48af-8665-0eb57b31ac9e";
	String access_token = "f5c5ef3e-4683-4e18-87b1-988d402b25cc";
	CertificateRequestBuilder builder = new CertificateRequestBuilder(key,dn, role, subrole, memberID);
	CertificationRequest csr = builder.build(); // SCR Base64
	String csr_base64 = csr.getBase64String(); //
	System.out.println("================apply certification=============");
	System.out.println(csr_base64);
	String applicationID =ServiceManager.INSTANCE.getService().getRARemoteServie().getRAService().applyCertificate(csr_base64, access_token);
	System.out.println("applied id= "+applicationID);
	System.out.println("================apply certification=============");
	return applicationID;
}



public enum KeyBoxManager {
    INSTANCE;
    private KeyBox keybox = null;
    private KeyBoxManager() {
        char[] passwordStr = "PASSWORD".toCharArray();
        String keyboxFilePath = "keybox.xml";
        keybox = new KeyBox();
        try {
// KeyBox
            keybox.loadFromFile(new File(keyboxFilePath), passwordStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
}
// KyeBox
public KeyBox getKeybox() {
        return keybox;
    }
}




//get certification state
public void getCertificateState(String applicationID,String access_token) throws IOException,
RemoteServiceException {
//String applicationID = "f5d5f68bdef044bd8106509a7e861e04";
//String access_token = "e31da4d6-72f4-46ae-a4f7-5f17eda7f557";
int state =ServiceManager.INSTANCE.getService().getRARemoteServie().getRAService().getApplicationState(applicationID, access_token);
System.out.println("apply state is = "+state); }




//download certification
public void downLoadCertificate(String applicationID,String access_token) throws IOException,RemoteServiceException, CertificateException {
//String applicationID = "401622aef1be4ab7b1f1f05cb1e5a5d6";
//String access_token = "e31da4d6-72f4-46ae-a4f7-5f17eda7f557";
String base64Str = ServiceManager.INSTANCE.getService().getRARemoteServie().getRAService().getCertificate(applicationID, access_token);
Certificate cert = new Certificate(base64Str);
System.out.println("certification is = "+cert.toString());
KeyBoxManager.INSTANCE.getKeybox().addCertificate("MyCert", cert,KeyBoxManager.INSTANCE.getKeybox().getKey("MyKey").getPrivateKey());
}


public ArrayList<Goodlist> getxlsx(String path) throws IOException {
	//http://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
	//File myFile = new File("C://temp/Employee.xlsx");
	File myFile = new File(path);
	FileInputStream fis = new FileInputStream(myFile);
	XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
	XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	Iterator<Row> rowIterator = mySheet.iterator();
	Goodlist good = new Goodlist();
	ArrayList<Goodlist> al=new ArrayList<Goodlist>();
	int id = 0;
	while (rowIterator.hasNext()) {
	  Row row = rowIterator.next();
	  Iterator<Cell> cellIterator = row.cellIterator();
	 /* if(cellIterator.next().getStringCellValue() == ""){
		  break;
	  };
	  */
	  //int id = 0;
	  //String[] cells;
	  //String[] myStringArray = new String[3];
	 // Map<String, String> map = new HashMap<String, String>();
	  
	  ArrayList<String> rowforgood =new ArrayList<String>(); 
	  String mainloopstop ="no";
	  int idforsecondloop =0;
	  while (cellIterator.hasNext()) {
	     Cell cell = cellIterator.next();
	     if (cell.getCellTypeEnum() == CellType.STRING) {
             System.out.print(cell.getStringCellValue() + "--");
            if(cell.getStringCellValue() == ""&& idforsecondloop ==0){
            	mainloopstop ="yes"; 
       		  //break;
            	System.out.println("++++++++++++++++");																																																																																																																																																																																																									
       	  }
       	  
             rowforgood.add(cell.getStringCellValue());
         } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
             System.out.print(cell.getNumericCellValue() + "--");
             rowforgood.add(String.valueOf(cell.getNumericCellValue()));
           /*  if(String.valueOf(cell.getNumericCellValue())=="") {
            	 mainloopstop ="yes"; 
             }
             */
         }


	     
	     //System.out.print(cell.getStringCellValue() +"x" +"\t");
	     //rowforgood.add(cell.getStringCellValue());
	    idforsecondloop++;  
	  }
	  if(mainloopstop=="yes") {break;};
	  
	  System.out.println(""); 
	  if (id>0) {
	  Goodlist a = new Goodlist();
	  a.checkid = rowforgood.get(0);
	  a.numberid = rowforgood.get(1);
	  a.type = rowforgood.get(2);
	  a.numberandweight = rowforgood.get(3);
	  a.typeofsafe = rowforgood.get(4);
	  a.brandname = rowforgood.get(5);
	  a.ingredient = rowforgood.get(6);
	  a.note = rowforgood.get(7);
	  al.add(a);
	  }
	  id++;
	}  
	
	return al;
	
	
}





//remote connection
public enum ServiceManager {
  INSTANCE;
  private BlockChainService bcs = null;
  private ServiceManager() {
//
ArrayList<String>  bcUrlList = new ArrayList<String>();
ArrayList<String> raUrlList = new ArrayList<String>();
//ArrayList a1 = new ArrayList();
//ArrayList<String> bcUrlList2 = new ArrayList<String>();
bcUrlList.add("http://161.202.22.92:8080/blockchain-rpc/RPCServlet");
raUrlList.add("http://161.202.22.83:8080/blockchain-ra/rpc/certificateService");
bcs = new BlockChainService(); //
for (String url : bcUrlList)
		bcs.addBCRemoteService(new BlockchainRemoteService(url)); //
for (String url : raUrlList)
	    bcs.addRARemoteService(new RARemoteService(url));
}

public BlockChainService getService()
{
return bcs;
}
}

/*
public void addFactCategory(String factCategoryName) throws VerifyException,
RemoteServiceException {
//String factCategoryName = "example1"; //    FactCategoryBuilder
FactCategoryBuilder builder = new FactCategoryBuilder(factCategoryName); //  Field      Field   Field
builder.addField(new FactCategory.Field("资产标记码", 100, false,false));
builder.addField(new FactCategory.Field("执行机构", 100, false,false));
builder.addField(new FactCategory.Field("资产来源", 100, false,false));
builder.addField(new FactCategory.Field("资产去向", 100, false,false));
builder.addField(new FactCategory.Field("温度数据", 100, false,false));
builder.addField(new FactCategory.Field("天气", 100, false,false));
builder.addField(new FactCategory.Field("货站地点", 100, false,false));
builder.addField(new FactCategory.Field("责任人", 100, false,false));
builder.addField(new FactCategory.Field("检疫状态", 100, false,false));
Certificate cert = KeyBoxManager.INSTANCE.getKeybox().getCertificate("MyCert");
builder.setOwnerCertificate(cert.getMemberID(),cert.getSN().toString());
ECKey key = KeyBoxManager.INSTANCE.getKeybox().getKey("MyKey"); //
builder.setPrivateKey(key.getPrivateKey());
builder.setPublicKey(key.getPublicKey());
Transaction tx = builder.build(); //
String txHash =ServiceManager.INSTANCE.getService().getBCRemoteService().getTransactionService().broadcastTransaction(tx);
}
*/


public void addFactCategory(String factCategoryName) throws VerifyException,
RemoteServiceException {
//String factCategoryName = "example1"; //    FactCategoryBuilder
FactCategoryBuilder builder = new FactCategoryBuilder(factCategoryName); //  Field      Field   Field
builder.addField(new FactCategory.Field("报检号", 100, false,false));
builder.addField(new FactCategory.Field("序号", 100, false,false));
builder.addField(new FactCategory.Field("型号", 100, false,false));
builder.addField(new FactCategory.Field("品名", 100, false,false));
builder.addField(new FactCategory.Field("数重量", 100, false,false));
builder.addField(new FactCategory.Field("安全类别", 100, false,false));
builder.addField(new FactCategory.Field("品牌", 100, false,false));
builder.addField(new FactCategory.Field("成份", 100, false,false));
builder.addField(new FactCategory.Field("备注", 100, false,false));
Certificate cert = KeyBoxManager.INSTANCE.getKeybox().getCertificate("MyCert");
builder.setOwnerCertificate(cert.getMemberID(),cert.getSN().toString());
ECKey key = KeyBoxManager.INSTANCE.getKeybox().getKey("MyKey"); //
builder.setPrivateKey(key.getPrivateKey());
builder.setPublicKey(key.getPublicKey());
Transaction tx = builder.build(); //
String txHash =ServiceManager.INSTANCE.getService().getBCRemoteService().getTransactionService().broadcastTransaction(tx);

}






public MyResult getFactCategoryByName(String factCategoryName) throws RemoteServiceException,
IOException {
 //String factCategoryName = "example";
 FactCategoryInfo factCategoryInfo =ServiceManager.INSTANCE.getService().getBCRemoteService().getFactService().
getFactCategoryByName(factCategoryName);
//
String factCategoryHash = factCategoryInfo.getTransactionHash(); //
FactCategory factCategory = factCategoryInfo.getFactCategory();
MyResult c = new MyResult();
c.hashforblock = factCategoryHash;
c.Category = factCategory;
return c;
}




//broadcast to blockchain
public void addFact(String factCategoryHash, FactCategory factCategory,String assetid,
		String assetfrom,String assetto,String agent,String temprature,String weather,
		String people,String storeplace,String checkstate

		) throws VerifyException, RemoteServiceException {
//		    FactBuilder
		FactBuilder builder = new FactBuilder(factCategoryHash, factCategory); //
		/*
		builder.addItem("资产标记码",assetid);
		builder.addItem("资产来源", assetfrom);
		builder.addItem("资产去向", assetto);
		builder.addItem("执行机构", agent);
		builder.addItem("温度数据", temprature);
		builder.addItem("天气", weather);
		builder.addItem("责任人", people);
		builder.addItem("货站地点", storeplace);
		builder.addItem("检疫状态", checkstate);
        */

		
		builder.addItem("报检号",assetid);
		builder.addItem("序号", assetfrom);
		builder.addItem("型号", assetto);
		builder.addItem("品名", agent);
		builder.addItem("备注", temprature);
		builder.addItem("成份", weather);
		builder.addItem("品牌", people);
		builder.addItem("数重量", storeplace);
		builder.addItem("安全类别", checkstate);
		
		
		
		//builder.addItem("field2_Name", "yangmingsdhsdsdjksdkjkdsajkjkdkajs");
		Certificate cert =KeyBoxManager.INSTANCE.getKeybox().getCertificate("MyCert");
		//    Member SN builder.setOwnerCertificate(cert.getMemberID(),
		builder.setOwnerCertificate(cert.getMemberID(),cert.getSN().toString());
		ECKey key = KeyBoxManager.INSTANCE.getKeybox().getKey("MyKey"); //
		builder.setOwnerPrivateKey(key.getPrivateKey());
//
		      builder.setOwnerPublicKey(key.getPublicKey());
//
		String user1_publicKey = ECKey.generateKeyPair().getPublicKeyAsHex(); String user2_publicKey = ECKey.generateKeyPair().getPublicKeyAsHex();; String user3_publicKey = ECKey.generateKeyPair().getPublicKeyAsHex();; //                            builder.addPermission(user1_publicKey, PermissionType.DECRYPT); builder.addPermission(user2_publicKey, PermissionType.RELAY); builder.addPermission(user3_publicKey, PermissionType.UPDATE); //  Transaction
		Transaction tx = builder.build(); //
		String txHash =ServiceManager.INSTANCE.getService().getBCRemoteService().getTransactionService().broadcastTransaction(tx);
	    	
     
}











}
