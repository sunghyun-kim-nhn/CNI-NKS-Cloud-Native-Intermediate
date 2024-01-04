package com.nhncloud.edu.cont;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncloud.edu.dto.LocalFileListVo;

public class LocalFileListController {

	public String getObjectList(String containerName) {
		if (containerName == null) {
			containerName = "";
		}
		ClassPathResource cpr = new ClassPathResource("static/" + containerName);
		File dir2 = null;
		try {
			dir2 = cpr.getFile();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// File dir = new File("src/main/resources/static/" + containerName);
		// File files[] = dir.listFiles();
		File files2[] = dir2.listFiles();

//		File dir2 = new File("src/main/resources/static/" + containerName);
//		String[] directories = dir2.list(new FilenameFilter() {
//			@Override
//			public boolean accept(File dir, String name) {
//				return new File(dir, name).isDirectory();
//			}
//		});

//		System.out.println("dir : " + Arrays.toString(directories));

		for (int i = 0; i < files2.length; i++) {
			if (files2[i].isDirectory()) {
				System.out.println("dir: " + files2[i]);
			} else {
				Path source = Paths.get(files2[i].toString());
				BasicFileAttributes attr = null;
				try {
					attr = Files.readAttributes(source, BasicFileAttributes.class);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("File: " + files2[i]);

				System.out.println("File getName: " + files2[i].getName());
				System.out.println("File byte: " + files2[i].length());
				String ext = files2[i].getName().substring(files2[i].getName().lastIndexOf(".") + 1);
				System.out.println("File type: " + ext);
				try {
					System.out.println("File mime type:" + Files.probeContentType(source));
					System.out.println("File Hash:" + getHash(files2[i].toString()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("File Modified Date: " + attr.lastModifiedTime());

			}

		}
		List<LocalFileListVo> list = new ArrayList<LocalFileListVo>();

		for (int i = 0; i < files2.length; i++) {
			LocalFileListVo lflv = new LocalFileListVo();
			Path source = Paths.get(files2[i].toString());
			BasicFileAttributes attr = null;
			if (files2[i].isDirectory()) {
				System.out.println("dir: " + files2[i]);
			} else {
				try {
					attr = Files.readAttributes(source, BasicFileAttributes.class);
//					{"hash":"38e7f56505144e1393bda7a67e481e37","last_modified":"2023-04-21T12:55:58.033600","bytes":2717890,"name":"1670288701959.jpg","content_type":"image/jpeg"}

					lflv.setHash(getHash(files2[i].toString()));
					lflv.setLast_modified(attr.lastModifiedTime().toString());
					lflv.setBytes(Long.toString(files2[i].length()));
					lflv.setName(files2[i].getName());
					lflv.setContent_type(Files.probeContentType(source));

					list.add(lflv);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		// JSON 만들기
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		JSONArray jarr = new JSONArray();
		try {
			jsonString = mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jarr.put(jsonString);
		System.out.println("JSON List Test : " + jsonString);

		return jsonString;

	}

	public String getJsonFile() {
		String filePath = "/mnt/local_video.json";
		File file = new File(filePath);

		if (isFileExists(file)) {
			System.out.println("File exists!!");
			try {
				return new String(Files.readAllBytes(Paths.get(filePath)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "[{\"title\":\"JSON에 접근되지 않아 Default 자료를 출력합니다.\",\"type\":\"text/html\",\"youtube\":\"zGg1I7YePfE\"},{\"title\":\"웨비나 16\",\"type\":\"text/html\",\"youtube\":\"11MQNnRAOUo\"},{\"title\":\"티저 공개 2탄ㅣNHN Cloud make IT 2023ㅣ유연하게 안전하게 Empower your Business!\",\"type\":\"text/html\",\"youtube\":\"Fsrfg4C_ijA\"}]";
			}
		} else {
			System.out.println("File doesn't exist or program doesn't have access " + "to the file");
			
		}
		return "[{\"title\":\"JSON에 접근되지 않아 Default 자료를 출력합니다.\",\"type\":\"text/html\",\"youtube\":\"zGg1I7YePfE\"},{\"title\":\"웨비나 16\",\"type\":\"text/html\",\"youtube\":\"11MQNnRAOUo\"},{\"title\":\"티저 공개 2탄ㅣNHN Cloud make IT 2023ㅣ유연하게 안전하게 Empower your Business!\",\"type\":\"text/html\",\"youtube\":\"Fsrfg4C_ijA\"}]";
	}

	public static boolean isFileExists(File file) {
		return file.exists() && !file.isDirectory();
	}

//	public JSONArray getObjectFileListJson(List<LocalFileListVo> arr_lflvo) {
//
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString = "";
//		JSONArray jarr = new JSONArray();
//		try {
//			jsonString = mapper.writeValueAsString(arr_lflvo);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		jarr.put(jsonString);
//		System.out.println("JSON List Test : " + jsonString);
//
//		return jarr;
//	}

	private static String getHash(String path) throws IOException, NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			byte[] dataBytes = new byte[1024];
			Integer nRead = 0;
			while ((nRead = fileInputStream.read(dataBytes)) != -1) {
				messageDigest.update(dataBytes, 0, nRead);
			}
		}
		byte[] mdBytes = messageDigest.digest();
		StringBuffer stringBuffer = new StringBuffer();
		for (Integer i = 0; i < mdBytes.length; i++) {
			stringBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)).substring(1);
		}
		return stringBuffer.toString();
	}
}
