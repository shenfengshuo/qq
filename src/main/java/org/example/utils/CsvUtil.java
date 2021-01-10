package org.example.utils;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {
	public static List<Map<String, String>> readCsvWithfirstLineAsKey(String csvFile, String charset) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		CSVReader reader = null;
		List<String[]> list = new ArrayList<String[]>();

		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(new File(csvFile)), charset));
			list = reader.readAll();

			List<String> keys = new ArrayList<String>();

			for (int k = 0; k < list.get(0).length; k++) {
				keys.add(list.get(0)[k]);
			}

			for (int i = 1; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < list.get(1).length; j++) {
					map.put(keys.get(j), list.get(i)[j]);
				}
				result.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, String>> readCsvWithfirstLineAsKey0(String csvFile, String charset) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (csvFile != null && new File(csvFile).exists())
			try {
				BufferedReader br0 = new BufferedReader(
						new InputStreamReader(new FileInputStream(new File(csvFile)), charset));

				List<String> keys = new ArrayList<String>();
				StringBuilder br = new StringBuilder();

				String data0 = br0.readLine();

				for (int k = 0; k < data0.split(",").length; k++) {
					keys.add(data0.split(",")[k]);
				}

				for (String data; (data = br0.readLine()) != null;) {
					br.append(data).append("\r\n");
				}

				for (String r : br.toString().split("\r\n")) {
					String[] arr = r.split(",");
					String[] arrNew = new String[keys.size()];// 定义新数组
					if (arr.length == keys.size()) {
						for (int i = 0; i < keys.size(); i++) {
							arrNew[i] = arr[i];// 把旧数组中的元素拷贝到新数组中
						}
					} else {
						for (int i = 0; i < arr.length; i++) {
							arrNew[i] = arr[i];// 把旧数组中的元素拷贝到新数组中
						}
						for (int i = 0; i < arrNew.length; i++) {
							if (arrNew[i] == null) {
								arrNew[i] = "";
							}
						}
					}

					Map<String, String> map = new HashMap<String, String>();
					for (int j = 0; j < keys.size(); j++) {
						map.put(keys.get(j), arrNew[j]);
					}
					result.add(map);
				}
				br0.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}

	public static void sortJava(String sourceFile, String destFile) throws IOException {
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(sourceFile)), "UTF-8"));
		List<String> list = new ArrayList<String>();

		File datafile = new File(destFile);
		datafile.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));

		String line = null;
		while ((line = bufferedreader.readLine()) != null) {
			list.add(line);
		}

		for (int i = 0; i < list.size() - 6; i++) {
			if (list.get(0).subSequence(0, 2).equals("/*")) {
				if (list.get(0).subSequence(0, 2).equals("/*") && list.get(0).subSequence(7, 9).equals("*/")) {
					if (list.get(i).length() > 10) {
						if (list.get(0).subSequence(10, 17).equals("package") || list.get(0).subSequence(10, 16).equals("import")) {
							bw.write(list.get(i).replace("kl", "shen").substring(10) + "\n");
						} else {
							bw.write(list.get(i).substring(10) + "\n");
						}
					} else {
						bw.write(list.get(i).substring(10) + "\n");
					}
				} else if (list.get(0).subSequence(0, 2).equals("/*") && list.get(0).subSequence(6, 8).equals("*/")) {
					if (list.get(i).length() > 9) {
						if (list.get(0).subSequence(9, 16).equals("package") || list.get(0).subSequence(9, 15).equals("import")) {
							bw.write(list.get(i).replace("kl", "shen").substring(9) + "\n");
						} else {
							bw.write(list.get(i).substring(9) + "\n");
						}
					} else {
						bw.write(list.get(i).substring(9) + "\n");
					}
				} else if (list.get(0).subSequence(0, 2).equals("/*") && list.get(0).subSequence(5, 7).equals("*/")) {
					if (list.get(i).length() > 8) {
						if (list.get(0).subSequence(8, 15).equals("package") || list.get(0).subSequence(8, 14).equals("import")) {
							bw.write(list.get(i).replace("kl", "shen").substring(8) + "\n");
						} else {
							bw.write(list.get(i).substring(8) + "\n");
						}
					} else {
						bw.write(list.get(i).substring(8) + "\n");
					}
				}
			} else {
				if (list.get(0).subSequence(0, 7).equals("package") || list.get(0).subSequence(0, 6).equals("import")) {
					bw.write(list.get(i).replace("kl", "shen") + "\n");
				} else {
					bw.write(list.get(i) + "\n");
				}
			}
		}

		bw.flush();
		bw.close();

	}

}