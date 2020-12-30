package com.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.page.factory.Configurations;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Utilities {

	private static final Logger LOGGER = Logg.createLogger();

	public static String getCurrentThreadId() {
		return "Thread:" + Thread.currentThread().getId() + "	-";
	}

	public static int randomNumberGenerator() {
		LOGGER.info(Utilities.getCurrentThreadId() + "Random number generator function");
		Random rand = new Random();
		LOGGER.info(Utilities.getCurrentThreadId() + "Generating a random number between 0 and "
				+ Configurations.TEST_PROPERTIES.get("randomMaxInteger"));
		return rand.nextInt(1000);
	}

	public static String convertToString(int value) {
		LOGGER.info(Utilities.getCurrentThreadId() + "Integer to String Conversion function");
		LOGGER.info(Utilities.getCurrentThreadId() + "Converting the Integer value " + value
				+ " to String");
		return String.valueOf(value);
	}

	public static int convertToInteger(String value) {
		LOGGER.info(Utilities.getCurrentThreadId() + "String to Integer Conversion function");
		LOGGER.info(Utilities.getCurrentThreadId() + "Coverting the String value " + value
				+ " to Integer");
		return Integer.parseInt(value);
	}

	public static String[] convertListToStringArray(List<?> list) {
		LOGGER.info(Utilities.getCurrentThreadId() + "List to String Array Conversion function");
		LOGGER.info(Utilities.getCurrentThreadId() + "Size of the list obtained=" + list.size());
		Object[] obj = list.toArray();
		String[] str = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			str[i] = (String) obj[i];
		}
		return str;
	}

	public static List<String> covert2DArrayToList(String[][] array2D) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array2D.length; i++) {
			for (int j = 0; j < array2D[i].length; j++) {
				list.add(array2D[i][j]);
			}
		}
		return list;
	}


	public static String getBaseDirPath(){
		String path = System.getProperty("user.dir").replace("/", "\\");
		LOGGER.debug(Utilities.getCurrentThreadId() + "Base directory path : " + path );

		return path;

	}


	public static boolean createDirIfNotExists(String dirName) {
		File dir = new File(dirName);
		boolean result = false;
		// if the directory does not exist, create it
		if (!dir.exists()) {
			LOGGER.debug("Creating Logs directory : " + dir);
			result= false;
			try{
				dir.mkdir();
				result = true;
			} catch(SecurityException se){
				LOGGER.error(Utilities.getCurrentThreadId()+"SecurityException "+se.getStackTrace(),se);
			}        
			if(result) {    
				LOGGER.debug(dirName + " dir created");  
			}
		}
		else
			LOGGER.debug(dirName + "folder already exists");
		return result;
	}


	public static boolean createDirsIfNotExists(String dirName) {
		File dir = new File(dirName);
		boolean result = false;
		// if the directory does not exist, create it
		if (!dir.exists()) {
			LOGGER.debug("Creating Logs directory : " + dir);
			result= false;
			try{
				dir.mkdirs();
				result = true;
			} catch(SecurityException se){
				LOGGER.error(Utilities.getCurrentThreadId()+"SecurityException "+se.getStackTrace(),se);
			}        
			if(result) {    
				LOGGER.debug(dirName + " dir created");  
			}
		}
		else
			LOGGER.debug(dirName + "folder already exists");
		return result;
	}
	public boolean getTextFromImage(String filepath){
		boolean status=false;
		// D:\\download.jpg
		File imageFile = new File(filepath);
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:/Program Files (x86)/OCS Inventory Agent");
		try {
			String result = instance.doOCR(imageFile);
			LOGGER.info(result);

		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}

		return status;
	}

	public static boolean compareExactText(String actual, String expected) {

		Logg.createLogger().info(Utilities.getCurrentThreadId() + "Actual Value=" + actual + " Expected Value="
				+ expected);
		Logg.createLogger().info(Utilities.getCurrentThreadId() + "Result of exact comparison is "
				+ actual.equals(expected));
		return actual.equals(expected);
	}

	public static boolean comparePartialText(String actual, String expected) {
		Logg.createLogger().info(Utilities.getCurrentThreadId() + "Actual Value=" + actual + " Expected Value="
				+ expected);
		Logg.createLogger().info(Utilities.getCurrentThreadId() + "Result of partial comparison is "
				+ actual.contains(expected));
		return actual.contains(expected);
	}


	public static boolean extractZip(String inputFile, String outputFolder) {

		byte[] buffer = new byte[1024];
		FileOutputStream fos = null; ZipInputStream zis = null;
		boolean success = true;

		try{
			//create output directory if it doesn't exist
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}
			//get the zip file content
			zis = new ZipInputStream(new FileInputStream(inputFile));
			//get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while(ze!=null){
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				//create all non exists folders
				//else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();
				fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			LOGGER.info("Extracted to " + outputFolder);

		}catch(IOException ex){
			success = false;
			ex.printStackTrace();
		} finally {
			try {
				fos.close();
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}


	public static void clearDirectory(String directoryPath) throws IOException{
		File folder = new File(directoryPath);
		FileUtils.cleanDirectory(folder);
	}

	
	public static String[] getFileNamesFromDirectory(String directoryPath){
		File[] listOfFiles = null;
		String[] filenames = null;
		File folder = new File(directoryPath);
		//		try {
		listOfFiles = folder.listFiles();
		filenames = new String[listOfFiles.length];
		for(int i=0;i<listOfFiles.length;i++) {
			filenames[i] = listOfFiles[i].getName();
		}
		return filenames;
	}


	public static String getFileNameFromDirectory(String directoryPath) {
		File[] listOfFiles = null;
		String filename = "";
		File folder = new File(directoryPath);
		listOfFiles = folder.listFiles();
		filename = listOfFiles[0].getName();
		return filename;
	}

	
	public static void copyFileToDir(String inputFolder, String outputFolder) {
		File srcFolder = new File(inputFolder);
		File destFolder = new File(outputFolder);
		try {
			FileUtils.copyDirectory(srcFolder, destFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void renameFile(String dirPath, String testName) {

		String[] filenames = getFileNamesFromDirectory(dirPath);                
		for(int i=1;i<=filenames.length;i++) {
			File oldName = new File(dirPath+"/"+filenames[i-1]);
			File newName = new File(dirPath+"/"+testName+ "_" +i+".png");
			if(oldName.renameTo(newName)) {
				LOGGER.info("renamed" + newName.getName());
			} else {
				LOGGER.info("Error renaming file");
			}
		}
	}

	
	public static void uploadFile(String filePath) throws Exception {
		StringSelection stringSelection = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		try{
			Thread.sleep(4000);
		}catch(InterruptedException e){}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void copyFile(File f1, File f2) throws IOException{

		Files.copy(f1.toPath(), f2.toPath(),StandardCopyOption.REPLACE_EXISTING);
		LOGGER.info(Utilities.getCurrentThreadId() + "Method to copy file");
		LOGGER.info(Utilities.getCurrentThreadId() + "Copied file from:  "+f1+"  to  "+f2);
		LOGGER.info(Utilities.getCurrentThreadId() + "file copied successfully");

	}

	
	public static void copyBackupFile(String filename, String...destination) throws IOException{
		File dest = null;
		if(destination.length > 0) {
			dest = new File(Configurations.TEST_PROPERTIES.get(destination[0])+filename);
		} else {
			dest = new File(Configurations.TEST_PROPERTIES.get("destination")+filename);
		} 
		File temp = new File(Configurations.TEST_PROPERTIES.get("temp")+filename);
		Files.copy(temp.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
		LOGGER.info(Utilities.getCurrentThreadId() + "Method to copy file");
		LOGGER.info(Utilities.getCurrentThreadId() + "Copied file from:  "+temp+"  to  "+dest);
		LOGGER.info(Utilities.getCurrentThreadId() + "file copied successfully");

	}

	public static void copyFileFromOneFolderToOther(String filename, String sourceFileName, String...destination) throws IOException
	{
		File dest = null;
		File source = new File(Configurations.TEST_PROPERTIES.get("source")+sourceFileName);

		if(destination.length > 0) {
			dest = new File(Configurations.TEST_PROPERTIES.get(destination[0])+filename);
		} else {
			dest = new File(Configurations.TEST_PROPERTIES.get("destination")+filename);
		}
		File temp = new File(Configurations.TEST_PROPERTIES.get("temp")+filename);

		if(dest.exists())
		{
			copyFile(dest,temp);
		}
		copyFile(source,dest);
	}

	
	public static boolean checkIfDirectoryisEmpty(String folderPath) {
		if(FileUtils.sizeOfDirectory(new File(folderPath)) > 0) {
			return true;
		} else {
			return false;
		}
	}



	public int getRandomNumber() {
		Random randomObj = new Random();
		int randomNum = randomObj.nextInt((9999 - 100)) + 10;
		return randomNum;
	}

	
	
	
}
