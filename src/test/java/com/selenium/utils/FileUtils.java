/**
 * @author 
 *
 */
package com.selenium.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FileUtils {

	public static final String JSON_FILE_EXTENSION = ".json";

	// A class to store the search data while searching a file within
	// directories...
	private static class FileSearch {

		File file;
		private String fileName = null;

		public FileSearch(String fileTobeSearch) {
			this.fileName = fileTobeSearch;
		}

		public File getFileLocation() {
			return file;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileLocation(File fileLocation) {
			this.file = fileLocation;
		}
	}

	private static int DIRECTORY = 2;
	private static int FILE = 1;

	private static int UNIVERSAL = 3;

	public static void copyDirectory(File moveDirectoryFrom, File moveDirectoryTo) {
		if (!moveDirectoryFrom.exists())
			return;
		if (moveDirectoryFrom.isDirectory() && moveDirectoryTo.isDirectory())
			moveDirectory(moveDirectoryFrom, moveDirectoryTo, true, true);
	}

	public static void moveFiles(String sourceLocation, String targetLocation, boolean willCopySubFoldersAsWell)
			throws IOException {
		File sourceDirectory = new File(sourceLocation);
		File destinationDirectory = new File(targetLocation);
		if (sourceDirectory.isDirectory()) {
			if (!destinationDirectory.exists()) {
				destinationDirectory.mkdir();
			}
			File[] files = sourceDirectory.listFiles();
			for (File file : files) {

				if (!willCopySubFoldersAsWell) {
					if (file.isDirectory())
						continue;
				}
				InputStream in = new FileInputStream(file);
				OutputStream out = new FileOutputStream(targetLocation + "/" + file.getName());

				// Copy the bits from input stream to output stream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				file.delete();
			}
		}
	}

	public static void copyFileToDirectory(File moveFileFrom, File aDirectory) {
		AssertUtils.isNull(moveFileFrom, "The moveFileFrom cannot be null");
		AssertUtils.isNull(aDirectory, "The aDirectory cannot be null");
		if (!moveFileFrom.exists()) {
			PrintData.logInfo("The file '" + moveFileFrom + "' does not exists");
			return;
		}
		if (moveFileFrom.isFile() && aDirectory.isDirectory()) {
			moveFile(moveFileFrom, new File(aDirectory.getAbsoluteFile(), moveFileFrom.getName()), true, true);
		}
	}

	public static File createDirectory(File aDirectory, String directoryName) {
		if (aDirectory == null) {
			return null;
		}
		File file = new File(aDirectory, directoryName);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static void deleteAllFilesWithinDirectory(File directoryFilename, boolean isDeleteSourceDir) {
		if (!directoryFilename.exists())
			PrintData.logInfo("No Directory " + FDConstants.STARTEXP + directoryFilename.getName()
					+ "'exists at the path" + FDConstants.DELIMITER + directoryFilename.getAbsolutePath() + "'");
		else {
			File[] allFiles = directoryFilename.listFiles();
			for (File file : allFiles) {
				if (file.isFile())
					deleteFileWithLogs(file);
				else {
					deleteAllFilesWithinDirectory(file, isDeleteSourceDir);
					// To delete the folder
					deleteFileWithLogs(file);
				}
				// To delete the Parent Directory
				if (isDeleteSourceDir)
					deleteFileWithLogs(directoryFilename);
			}
		}
	}

	public static void deleteAllFilesWithAnExtensionWithinDirectory(File directoryFile, String extension) {
		if (!directoryFile.exists())
			PrintData.logInfo("No Directory " + FDConstants.STARTEXP + directoryFile.getName() + "'exists at the path"
					+ FDConstants.DELIMITER + directoryFile.getAbsolutePath() + "'");
		else {
			File[] allFiles = directoryFile.listFiles();
			for (File file : allFiles) {
				if (file.getName().endsWith(extension)) {
					deleteFileWithLogs(file);
				}
			}
		}
	}

	public static void deleteDirectory(File aDirectory) {
		if (!aDirectory.exists())
			return;
		if (aDirectory.isDirectory()) {
			File[] files = aDirectory.listFiles();
			if (files == null)
				return;
			for (File aFile : files) {
				if (aFile.isFile())
					deleteFileWithLogs(aFile);
				else
					deleteDirectory(aFile);
			}
		}
	}

	public static void deleteFileIfExists(File aFile) {
		if (aFile.exists())
			deleteFileWithLogs(aFile);
		else
			PrintData.logInfo(
					"The file " + FDConstants.STARTEXP + aFile.getAbsolutePath() + "' does not exists in the directory "
							+ FDConstants.STARTEXP + aFile.getParentFile().getName() + "'");
	}

	public static void deleteFileWithLogs(File aFile) {
		try {
			if (aFile.delete())
				PrintData.logInfo("The file " + FDConstants.STARTEXP + aFile.getAbsolutePath()
						+ "' has been deleted from the directory" + FDConstants.STARTEXP
						+ aFile.getParentFile().getName() + "'");
			else
				PrintData.logInfo("The file " + FDConstants.STARTEXP + aFile.getAbsolutePath()
						+ "' has not been deleted from the directory" + FDConstants.STARTEXP
						+ aFile.getParentFile().getName() + "'");
		} catch (Exception e) {
			PrintData.logInfo("The file " + FDConstants.STARTEXP + aFile.getAbsolutePath()
					+ "' cannot be deleted because it's being used by another process.'");
		}
	}

	public static void deleteFileWithoutLogs(File aFile) {
		try {
			aFile.delete();
		} catch (Exception e) {
			PrintData.logInfo("The file " + FDConstants.STARTEXP + aFile.getAbsolutePath()
					+ "' cannot be deleted because it's being used by another process.'");
		}
	}

	public static boolean deleteSpecificDirectory(File aDirectory, String directoryName) {
		File file = getAbsoluteDirectoryFile(aDirectory, directoryName);
		return file != null ? file.delete() : false;
	}

	// Do it for the first occurrence of the file
	public static boolean deleteSpecificFile(File aDirectory, String fileName) {
		File file = getAbsoluteFile(aDirectory, fileName);
		return file != null ? file.delete() : false;
	}

	public static File getAbsoluteDirectoryFile(File aDirectory, String directoryName) {
		return searchDirectory(aDirectory, directoryName, DIRECTORY);
	}

	public static String getAbsoluteDirectoryPath(File aDirectory, String directoryName) {
		File file = getAbsoluteDirectoryFile(aDirectory, directoryName);
		return file != null ? file.getAbsolutePath() : null;
	}

	public static File getAbsoluteDirectoryWithinSingleDirectory(File aDirectory, String fileName) {
		if (aDirectory.isDirectory()) {
			File[] files = aDirectory.listFiles();
			for (File file : files)
				if (file.isDirectory())
					if (file.getName().equals(fileName))
						return file;
		}
		return null;
	}

	public static File getAbsoluteFile(File aDirectory, String fileName) {
		return searchDirectory(aDirectory, fileName, FILE);
	}

	public static String getAbsoluteFilePath(File aDirectory, String fileName) {
		File file = getAbsoluteFile(aDirectory, fileName);
		return file != null ? file.getAbsolutePath() : null;
	}

	public static File getAbsoluteFileWithinSingleDirectory(File aDirectory, String fileName) {
		if (aDirectory.isDirectory()) {
			File[] files = aDirectory.listFiles();
			for (File file : files)
				if (file.isFile())
					if (file.getName().equals(fileName))
						return file;
		}
		return null;
	}

	public static ArrayList<File> getFilesWithinDirectoryofAnyExtension(File directoryPath, String fileExtension) {
		ArrayList<File> filesArray = new ArrayList<File>();
		if (directoryPath == null || directoryPath.isFile()) {
			return filesArray;
		}
		for (File file : directoryPath.listFiles()) {
			if (file.isFile() && file.getName().endsWith(fileExtension)) {
				filesArray.add(file.getAbsoluteFile());
			} else {
				filesArray.addAll(getFilesWithinDirectoryofAnyExtension(file, fileExtension));
			}
		}
		return filesArray;
	}

	public static ArrayList<String> getFilePathWithinDirectoryofAnyExtension(File directoryPath, String fileExtension) {
		ArrayList<String> filesArray = new ArrayList<String>();
		if (directoryPath == null || directoryPath.isFile()) {
			return filesArray;
		}
		for (File file : directoryPath.listFiles()) {
			if (file.isFile() && file.getName().endsWith(fileExtension)) {
				filesArray.add(file.getAbsolutePath());
			} else {
				filesArray.addAll(getFilePathWithinDirectoryofAnyExtension(file, fileExtension));
			}
		}
		return filesArray;
	}

	public static boolean isApkFile(String aFilePath) {
		return aFilePath != null ? (aFilePath.endsWith(".apk") ? true : false) : false;
	}

	public static boolean isDirectoryExists(File aDirectory, String directoryName) {
		File file = getAbsoluteDirectoryFile(aDirectory, directoryName);
		return file != null ? file.exists() : false;
	}

	public static boolean isFileExists(File aDirectory, String fileName) {
		File file = getAbsoluteFile(aDirectory, fileName);
		return file != null ? file.exists() : false;
	}

	public static boolean isFileExists(String pathname) {
		return isFileExists(new File(pathname));
	}

	public static boolean isFileExists(File file) {
		if (file == null) {
			return false;
		}
		try {
			if (file.isFile()) {
				return file.exists();
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isFileOfZeroKB(File aFile) {
		if (aFile.length() != 0)
			return false;
		return true;
	}

	public static boolean isIPAFile(String aFilePath) {
		return aFilePath != null ? (aFilePath.endsWith(".ipa") ? true : false) : false;
	}

	public static boolean isXlsFile(String aFilePath) {
		return aFilePath != null ? (aFilePath.endsWith(".xls") ? true : false) : false;
	}

	public static boolean isXlsxFile(String aFilePath) {
		return aFilePath != null ? (aFilePath.endsWith(".xlsx") ? true : false) : false;
	}

	public static boolean isZipFile(String aFilePath) {
		return aFilePath != null ? (aFilePath.endsWith(".zip") ? true : false) : false;
	}

	public static void moveDirectory(File moveDirectoryFrom, File moveDirectoryTo, boolean retainCopy,
			boolean preserveDate) {
		// if(moveDirectoryFrom.isDirectory())
		if (!moveDirectoryFrom.exists())
			return;
		if (moveDirectoryFrom.isFile())
			return;
		else {
			File files[] = moveDirectoryFrom.listFiles();
			for (File moveFileFrom : files) {
				if (moveFileFrom.isFile())
					moveFile(moveFileFrom, new File(moveDirectoryTo.getAbsoluteFile(), moveFileFrom.getName()),
							retainCopy, preserveDate);
				else {
					File aDirectory = new File(moveDirectoryTo.getAbsoluteFile(), moveFileFrom.getName());
					aDirectory.mkdir();
					if (preserveDate)
						aDirectory.setLastModified(moveDirectoryFrom.lastModified());
					moveDirectory(moveFileFrom, aDirectory, retainCopy, preserveDate);
					if (!retainCopy)
						deleteFileWithLogs(moveFileFrom);
				}
			}
		}
	}

	public static void moveFile(File moveFileFrom, File moveFileToDir, boolean preserveDate) {
		AssertUtils.isNull(moveFileToDir, "The moveFileToDir cannot be null");
		moveFile(moveFileFrom, new File(moveFileToDir, moveFileFrom.getName()), false, preserveDate);
	}

	public static void moveFile(File moveFileFrom, File moveFileTo, boolean retainCopy, boolean preserveDate) {
		try {
			FileInputStream instream;
			FileOutputStream ostream;
			if (moveFileFrom.isFile()) {
				instream = new FileInputStream(moveFileFrom);
				ostream = new FileOutputStream(moveFileTo);
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = instream.read(buffer)) > 0) {
					ostream.write(buffer, 0, length);
				}
				instream.close();
				ostream.close();
				if (preserveDate) {
					moveFileFrom.setLastModified(moveFileFrom.lastModified());
				}
				if (!retainCopy) {
					deleteFileWithoutLogs(moveFileFrom);
				}
			}
		} catch (Exception e) {
		}
	}

	public static java.util.ArrayList<String> readAllLines(File aFile) {
		BufferedReader buff;
		java.util.ArrayList<String> fileData;
		try {
			fileData = new ArrayList<String>();
			buff = new BufferedReader(new FileReader(aFile));
			for (String aString = buff.readLine(); aString != null; aString = buff.readLine())
				fileData.add(aString);
			buff.close();
			return fileData;
		} catch (Exception e) {
		}
		return null;
	}

	public static java.util.ArrayList<String> readAllLines(File aFile, Charset aCharsetName) {
		BufferedReader buff;
		java.util.ArrayList<String> fileData;
		try {
			fileData = new ArrayList<String>();
			buff = new BufferedReader(new FileReader(aFile));
			for (String aString = buff.readLine(); aString != null; aString = buff.readLine())
				fileData.add(new String(aString.getBytes(), aCharsetName));
			buff.close();
			return fileData;
		} catch (IOException e) {
		}
		return null;
	}

	public static String readFile(File aFile) {
		if (!aFile.exists())
			return null;
		if (aFile.isFile()) {
			BufferedReader buff;
			try {
				String fileData = "";
				buff = new BufferedReader(new FileReader(aFile));
				for (String aString = buff.readLine(); aString != null; aString = buff.readLine())
					fileData += aString + "\n";
				buff.close();
				return fileData;
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static String readFile(File aFile, Charset aCharsetName) {
		if (!aFile.exists())
			return null;
		BufferedReader buff;
		try {
			String fileData = "";
			buff = new BufferedReader(new FileReader(aFile));
			for (String aString = buff.readLine(); aString != null; aString = buff.readLine())
				fileData += new String(aString.getBytes(), aCharsetName) + "\n";
			buff.close();
			return fileData;
		} catch (Exception e) {
		}
		return null;
	}

	public static byte[] readFile(String fileToBeRead) {
		String fileData = readFile(new File(fileToBeRead));
		return fileData != null ? fileData.getBytes() : null;
	}

	@SuppressWarnings("resource")
	public static String readFirstLine(File aFile) throws FileNotFoundException, IOException {
		return new BufferedReader(new FileReader(aFile)).readLine();
	}

	private static void searchDirectory(File aDirectory, FileSearch fsObject, int fileType) {
		if (aDirectory.isDirectory()) {
			File[] flist = aDirectory.listFiles();
			for (File file : flist) {
				if (fileType == DIRECTORY) {
					if (file.isDirectory())
						if (searchFile(file, fsObject))
							return;
						else
							searchDirectory(file, fsObject, fileType);
				} else if (fileType == FILE) {
					if (searchFile(file, fsObject))
						return;
					else
						searchDirectory(file, fsObject, fileType);
				} else if (fileType == UNIVERSAL) {
					if (file.isDirectory())
						if (searchFile(file, fsObject))
							return;
						else
							searchDirectory(file, fsObject, fileType);
					else if (searchFile(file, fsObject))
						return;
				} else
					throw new IllegalArgumentException("Illegal Argumnent " + FDConstants.STARTEXP + fileType
							+ "' passed as an argument. Please choose 1, 2 OR 3");
			}
		}
	}

	private static File searchDirectory(File aDirectory, String fileName, int fileType) {
		FileSearch fsObject = new FileSearch(fileName);
		if (aDirectory.isDirectory()) {
			searchDirectory(aDirectory, fsObject, fileType);
		}
		return fsObject.getFileLocation();
	}

	private static boolean searchFile(File aFile, FileSearch search) {
		if (search.getFileName().equals(aFile.getName())) {
			search.setFileLocation(aFile);
			return true;
		}
		return false;
	}

	public static ArrayList<File> splitFiles(File sourceFile, String fileBaseName, int chunkSize) {
		AssertUtils.isNull(sourceFile);

		if (sourceFile == null || sourceFile.isDirectory())
			return null;

		if (fileBaseName == null || fileBaseName.isEmpty())
			throw new IllegalArgumentException("The BaseName cannot be empty or null");

		if (chunkSize <= 0)
			throw new IllegalArgumentException(chunkSize + " < " + 0);

		long fileSize = sourceFile.length();

		if (fileSize == 0)
			return null;

		if (chunkSize > fileSize)
			throw new IllegalArgumentException(chunkSize + " > " + fileSize);

		String fileExtension = sourceFile.getName().substring(sourceFile.getName().indexOf('.'));
		ArrayList<File> splitFilesArray = null;
		byte[] byteArray = null;
		try {
			FileInputStream fin = new FileInputStream(sourceFile);
			splitFilesArray = new ArrayList<File>();
			int count = 0;
			for (int i = 0; i < fileSize; i += chunkSize) {
				chunkSize = (int) (fileSize - i >= chunkSize ? chunkSize : fileSize - i);
				byteArray = new byte[chunkSize];
				fin.read(byteArray, 0, chunkSize);
				File splitFile = new File(sourceFile.getParentFile(), fileBaseName + "_" + (count++) + fileExtension);
				writeFile(splitFile, byteArray);
				splitFilesArray.add(splitFile);
			}
			fin.close();
			return splitFilesArray;
		} catch (IOException e) {
			return null;
		}
	}

	public static void writeFile(File aFile, byte[] textContentInBytes) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(aFile);
			fos.write(textContentInBytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public static void writeFile(File aFile, String aString) {
		writeFile(aFile, aString.getBytes(FDConstants.UTF8));
	}

	public static void writeFile(File fileName, String aString, String charsetName) {
		writeFile(fileName, aString.getBytes(Charset.forName(charsetName)));
	}

	public static void writeFile(String filename, byte[] filebytearray) throws IOException {
		writeFile(new File(filename), filebytearray);
	}

	public static File createDirectory(File paramFile) {
		if (paramFile == null) {
			return null;
		}
		if (paramFile.isFile()) {
			throw new IllegalArgumentException("The passed argument '" + paramFile + "' is of file type");
		}
		if (!paramFile.exists()) {
			paramFile.mkdir();
		}
		return paramFile;
	}

	public static boolean isBeingWritten(String filePath) {
		File file = new File(filePath);
		RandomAccessFile stream = null;
		try {
			stream = new RandomAccessFile(file, "rw");
			return false;
		} catch (Exception e) {
			System.err
					.println("Skipping file " + file.getName() + " for this iteration due it's not completely written");
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					System.err.println("Exception during closing file " + file.getName());
				}
			}
		}
		return true;
	}

	public static String getCanonicalPath(File file) {
		String path = "";
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			PrintData.logInfo(e.getMessage());
		}
		return path;
	}

	@SuppressWarnings("deprecation")

	/**
	 * Writes the entire text to a file
	 * 
	 * @param text
	 * @param outputFile
	 */
	public static void writeToFile(String text, String outputFile, boolean isAppend) {
		FileOutputStream fout;
		if (text != null) {
			try {
				fout = new FileOutputStream(new File(outputFile), isAppend);
				fout.write(text.getBytes());
				fout.flush();
			} catch (FileNotFoundException fe) {
				System.err.println("File not found.");
			} catch (IOException ioe) {
			} finally {
				fout = null;
			}
		}
	}

}