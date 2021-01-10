package org.example.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileUtil {

	public static void delete(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("--> Cannot delete dir");
				// for (File f : file.listFiles())
				// if (f.isFile()) {
				// f.delete();
				// }
			} else if (file.isFile()) {
				file.delete();
				if (file.exists()) {
					file.deleteOnExit();
				}
			}
		}
	}

	public static void record(String data, String filename) {
		try {
			if (new File(filename).exists()) {
				StringBuffer sb = new StringBuffer();
				sb.append(FileUtil.read(filename)).append("\r\n").append(data);
				data = sb.toString();
			}
			write(data, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String genName(String filepath, String endfix) {
		filepath = filepath.trim().replace("\\", "/");
		filepath = filepath.endsWith("/") ? filepath : filepath + "/";
		File path = new File(filepath);
		if (!path.exists()) {
			path.mkdirs();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		String filenameprefix = sdf.format(new Date());
//		Date data1 = new Date();
//		String filenameprefix = data1.toString();
		List<Integer> indexlist = new ArrayList<Integer>();
		for (String s : path.list()) {
			if (s.endsWith(endfix) && s.indexOf(filenameprefix) > -1) {
				int index = s.lastIndexOf("_");
				if (index == -1) {
					indexlist.add(0);
				} else {
					indexlist.add(Integer.valueOf(s.substring(index + 1).replace(endfix, "")));
				}
			}
		}
		int size = indexlist.size();
		if (size > 0) {
			Collections.sort(indexlist);
			filenameprefix += "_" + String.valueOf(indexlist.get(size - 1) + 1);
		}
		return filepath + filenameprefix + endfix;

	}

	public static String readFileToString(File filename) {
		StringBuilder sb = new StringBuilder();
		if (filename != null && filename.exists())
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
				// BufferedReader br = new BufferedReader(new FileReader(filename));
				for (String data; (data = br.readLine()) != null;) {
					sb.append("\r\n").append(data);
				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		sb.delete(0, 2);
		return sb.toString();
	}

	public static String read(String filename) {
		filename = filename.replace("\\", "/");
		filename = filename.contains("/") ? filename : filename;
		StringBuilder sb = new StringBuilder();
		if (filename != null && new File(filename).exists())
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(new File(filename)), "gbk"));
				// BufferedReader br = new BufferedReader(new FileReader(filename));
				for (String data; (data = br.readLine()) != null;) {
					sb.append("\r\n").append(data);
				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		sb.delete(0, 2);
		return sb.toString();
	}

	public static void write(String data, String filename) {

		try {
			FileOutputStream fos = new FileOutputStream(new File(filename));
			Writer os = new OutputStreamWriter(fos, "UTF-8");
			os.write(data);
			os.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File newFile(String filename) {
		File dir = new File(filename).getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(filename);
	}

	public static String firstLine(String filename) {
		String firstLine = null;
		String[] arr = FileUtil.read(filename).split("\r\n");
		int len = arr.length;
		for (int i = 0; i < len && (firstLine = arr[i]).isEmpty(); i++)
			;
		return firstLine;
	}

	public static String lastLine(String filename) {
		String lastLine = null;
		String[] arr = FileUtil.read(filename).split("\r\n");
		for (int i = arr.length - 1; i >= 0 && (lastLine = arr[i]).isEmpty(); i++)
			;
		return lastLine;
	}

	/**
	 * @param sourceDir
	 * @param destDir
	 * @throws IOException
	 */
	public void javaDecompileDeal(String sourceDir, String destDir) throws IOException {
		File file = new File(sourceDir);

		File[] fileList = file.listFiles();

		File path = new File(destDir);
		if (!path.exists()) {
			path.mkdirs();
		}

		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				String fileName = fileList[i].getName();
				System.out.println("文件：" + fileName);

				CsvUtil.sortJava(sourceDir + fileName, destDir + fileName);
			}

			if (fileList[i].isDirectory()) {
				String fileName = fileList[i].getName();
				System.out.println("目录：" + fileName);
				javaDecompileDeal(sourceDir + fileName + "\\", destDir + fileName + "\\");
			}
		}
	}

	public static void main(String args[]) throws IOException {

		FileUtil rf = new FileUtil();
		rf.javaDecompileDeal("C:\\Users\\188040364\\Desktop\\qq\\qwqw\\security-klcrypto-4.0.0.jar.src\\kl\\crypto\\",
				"C:\\Users\\188040364\\Desktop\\qq\\qwqw\\security-klcrypto-4.0.0.jar.src\\kl\\crypto1\\");

//		String ss = read("d:\\test\\qqq.txt");
//	String ss1 = read("d:\\test\\02_70204108_YBFX_20180621_001.csv");
//		System.out.println(ss);
//	System.out.println(ss1);

	}
}
