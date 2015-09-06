package com.example.volley.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.idea.xbox.component.logger.Logger;

public class FileUtils {
	public static final String RIFF_HEADER = "52494646";
	public static final String FILE_SCHEM = "file://";

	public static final String BASE_DIR = Environment
			.getExternalStorageDirectory() + "/xiaoliu";
	public static final String CACHE_DIR = BASE_DIR + "/cache";
	public static final String CACHE_IMAGE_DIR = CACHE_DIR + "/image";
	public static final String CACHE_FILE_DIR = CACHE_DIR + "/file";
	public static final String APK_NAME = "xiaoliu_fruits.apk";

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	static {
		getAllFileType();
	}

	public static boolean isSDCARDMounted() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public static String getFilenameWithoutSuffix(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

	public static String getFilenameFromPath(String path) {
		int separatorIndex = path.lastIndexOf(File.separator);
		separatorIndex = (separatorIndex == -1) ? 0 : separatorIndex + 1;
		int dotIndex = path.lastIndexOf(".");
		dotIndex = (dotIndex == -1) ? path.length() : dotIndex;
		return path.substring(separatorIndex, dotIndex);
	}

	public static void writeStringToFile(String content, String path) {
		File file = new File(path);
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter(file, true));
			fw.write(content);
			fw.newLine();
		} catch (IOException e) {
			Logger.e("", e.getMessage(), e);
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				Logger.e("", e.getMessage(), e);
			}
		}
	}

	public static String deserializeString(File file) {
		int len;
		char[] chr = new char[4096];
		final StringBuffer buffer = new StringBuffer();
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			while ((len = reader.read(chr)) > 0) {
				buffer.append(chr, 0, len);
			}
		} catch (IOException e) {
			Logger.e("", e.getMessage(), e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				Logger.e("", e.getMessage(), e);
			}
		}
		return buffer.toString();
	}

	/**
	 * Format file URI with file path.
	 * 
	 * @param path
	 * @return
	 */
	public static String fileUriFromPath(String path) {
		return FILE_SCHEM + path;
	}

	/**
	 * Create directory.
	 * 
	 * @param path
	 *            Directory path.
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * Create a file.
	 * 
	 * @param path
	 *            File path.
	 * @return New file.
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return file;
	}

	/**
	 * Delete a folder.
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * Delete all files.
	 * 
	 * @param path
	 *            File path.
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	public static long getAllFileSize(String path) {
		long size = 0;
		File file = new File(path);
		if (!file.exists()) {
			return 0;
		}
		if (!file.isDirectory()) {
			return 0;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				size = size + temp.length();
			}
			if (temp.isDirectory()) {
				size = size + getAllFileSize(path + "/" + tempList[i]);
			}
		}

		return size;
	}

	/**
	 * Get file MD5 string.
	 * 
	 * @param path
	 *            File path.
	 * @return File MD5 string
	 */
	public static String getFileMD5(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * Get file MD5 string encoded with base64.
	 * 
	 * @param path
	 *            File path.
	 * @return File MD5 string
	 */
	public static String getBase64MD5(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return Base64.encodeToString(digest.digest(), Base64.DEFAULT);
	}

	/**
	 * Check the file is exist.
	 * 
	 * @param pathString
	 * @return
	 */
	public static boolean fileExist(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Get file extension.
	 * 
	 * @param path
	 * @return
	 */
	public static String getExtensionFromPath(String path) {
		int dotIndex = path.lastIndexOf(".");
		if (dotIndex == -1) {
			return null;
		}
		return path.substring(dotIndex, path.length());
	}

	/**
	 * Read file header and get it's MIME type.
	 * 
	 * @param path
	 * @return
	 */
	public final static String getFileMIMEType(String path) {
		String type = "*/*";
		if (TextUtils.isEmpty(path)) {
			return type;
		}
		File file = new File(path);
		String fileType;
		byte[] header = new byte[50];
		try {
			InputStream is = new FileInputStream(file);
			is.read(header);
			fileType = getFileTypeByStream(header);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return type;
		} catch (IOException e) {
			e.printStackTrace();
			return type;
		}
		String extension = "." + fileType;
		for (int i = 0; i < MIME_MAP.length; i++) {
			if (extension.equals(MIME_MAP[i][0])) {
				type = MIME_MAP[i][1];
				break;
			}
		}
		return type;
	}

	public final static String getFileTypeByStream(byte[] b) {
		String filetypeHex = String.valueOf(getFileHexString(b));
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP
				.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				return entry.getKey();
			} else if (filetypeHex.toUpperCase().startsWith(RIFF_HEADER)
					&& filetypeHex.toUpperCase().startsWith(fileTypeHexValue,
							16)) { // RIFF File
									// Header
				return entry.getKey();
			}
		}
		return null;
	}

	public final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	private static void getAllFileType() {
		FILE_TYPE_MAP.put("webp", "57454250");
		FILE_TYPE_MAP.put("jpg", "FFD8FF");
		FILE_TYPE_MAP.put("png", "89504E47");
		FILE_TYPE_MAP.put("gif", "47494638");
		FILE_TYPE_MAP.put("tif", "49492A00");
		FILE_TYPE_MAP.put("bmp", "424D");
		FILE_TYPE_MAP.put("dwg", "41433130");
		FILE_TYPE_MAP.put("html", "68746D6C3E");
		FILE_TYPE_MAP.put("rtf", "7B5C727466");
		FILE_TYPE_MAP.put("xml", "3C3F786D6C");
		FILE_TYPE_MAP.put("zip", "504B0304");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("psd", "38425053");
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");
		FILE_TYPE_MAP.put("pst", "2142444E");
		FILE_TYPE_MAP.put("xls", "D0CF11E0");
		FILE_TYPE_MAP.put("doc", "D0CF11E0");
		FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");
		FILE_TYPE_MAP.put("wpd", "FF575043");
		FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("pdf", "255044462D312E");
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F");
		FILE_TYPE_MAP.put("pwl", "E3828596");
		FILE_TYPE_MAP.put("wav", "57415645");
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD");
		FILE_TYPE_MAP.put("rm", "2E524D46");
		FILE_TYPE_MAP.put("mpg", "000001BA");
		FILE_TYPE_MAP.put("mov", "6D6F6F76");
		FILE_TYPE_MAP.put("asf", "3026B2758E66CF11");
		FILE_TYPE_MAP.put("mid", "4D546864");
		FILE_TYPE_MAP.put("amr", "2321414D52");
	}

	public static boolean saveBitmap2File(Bitmap bmp, String filename) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			File file = new File(CACHE_IMAGE_DIR + "/" + filename);
			if (file.exists()) {
				file.delete();
			}
			stream = new FileOutputStream(CACHE_IMAGE_DIR + "/" + filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bmp.compress(format, quality, stream);
	}

	private static final String[][] MIME_MAP = {
			// {extension, MIME type}
			{ ".webp", "image/webp" },
			{ ".amr", "audio/amr" },
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };
}
