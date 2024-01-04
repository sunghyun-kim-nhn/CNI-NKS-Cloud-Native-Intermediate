package com.nhncloud.edu.cont;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncloud.edu.dto.LocalFileListVo;
import com.nhncloud.edu.dto.ObsFileVo;
import com.nhncloud.edu.service.ObjectService;

public class FileUploadController {

	private String uploadDir;

	public String upload(MultipartFile file) {

		ClassPathResource cpr = new ClassPathResource("static/" + "upload");
		// C:/Users/NHN/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/NHNCloud_OBS_Control/WEB-INF/classes/static/upload/
		String osType = System.getProperty("os.name").toLowerCase();
		String path = "";
		System.out.println("OS Type : " + osType);
		try {
			if (osType.contains("win")) {
				path = cpr.getURL().getPath().toString().substring(1); // Windows 용
			} else
				path = cpr.getURL().getPath().toString(); // Linux용
		} catch (IOException e) {
			e.printStackTrace();
		}
		// path = classFile.getAbsolutePath();
		// System.out.println(path);

		uploadDir = path + file.getOriginalFilename();
		System.out.println("Test uploadDir Properties : " + uploadDir);

		// Path copyOfLocation = Paths.get(uploadDir + File.separator +
		// StringUtils.cleanPath(file.getOriginalFilename()));
		Path copyOfLocation = Paths.get(uploadDir);
		String jsonString = "";

		try {

			// inputStream을 가져와서
			// copyOfLocation (저장위치)로 파일을 쓴다.
			// copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다
			Files.copy(file.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);

			LocalFileListVo lflv = new LocalFileListVo();

			BasicFileAttributes attr = null;
			attr = Files.readAttributes(copyOfLocation, BasicFileAttributes.class);

			lflv.setContent_type(file.getContentType());
			lflv.setBytes(Long.toString(file.getSize()));
			lflv.setLast_modified(attr.lastModifiedTime().toString());
			lflv.setName(file.getOriginalFilename());
			lflv.setHash(getHash(uploadDir));
			// lflv.setHash(file.getName());

			ObjectMapper mapper = new ObjectMapper();

			// String jsonString1 = "[{test: 'test'}]";
			JSONArray jarr = new JSONArray();
			try {
				jsonString = mapper.writeValueAsString(lflv);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}
			jarr.put(jsonString);
			System.out.println("JSON List Test : " + jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		return jsonString;
	}

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

	public String obsupload(ObsFileVo ofv) {
		final String storageUrl = ofv.getObs();
		final String tokenId = ofv.getToken();
		final String containerName = ofv.getC_name();
		// final String objectPath = "C:/Users/NHN/Pictures";
		final String objectName = ofv.getFile().getOriginalFilename();
		System.out.println("FUL object value : " + objectName);

		ObjectService objectService = new ObjectService(storageUrl, tokenId);

		// File objFile = new File(objectPath + "/" + objectName);
		String jsonString = "";
		// File objFile = new File(objectName);
		// int fileSize = (int) objFile.length();

		// final int defaultChunkSize = 10000 * 1024; // 100 KB 단위로 분할
		// int chunkSize = defaultChunkSize;
		// int chunkNo = 0; // 분할 오브젝트의 이름을 만들기 위한 청크 번호
		// int totalBytesRead = 0;
		try {
			// File objFile = new File(objectPath + "/" + objectName);
			InputStream inputStream = ofv.getFile().getInputStream();
			// InputStream inputStream = new FileInputStream(objFile);

// 멀티파트(파일분할 업로드) 방식 소스
//		try {
//			// 파일로부터 InputStream 생성
//			InputStream inputStream = new BufferedInputStream(new FileInputStream(objFile));
//			while (totalBytesRead < fileSize) {
//
//				// 남은 데이터 크기 계산
//				int remainedBytes = fileSize - totalBytesRead;
//				if (remainedBytes < chunkSize) {
//					chunkSize = remainedBytes;
//				}
//
//				// 바이트 버퍼에 청크 크기만큼 데이터를 읽음
//				byte[] chunkBuffer = new byte[chunkSize];
//				int bytesRead = inputStream.read(chunkBuffer, 0, chunkSize);
//
//				if (bytesRead > 0) {
//					// 버퍼의 데이터를 InputStream으로 만들어 업로드, 오브젝트 업로드 예제의 uploadObject() 메서드 사용
//					String objPartName = String.format("%s/%03d", objectName, ++chunkNo);
//					InputStream chunkInputStream = new ByteArrayInputStream(chunkBuffer);
//					objectService.uploadObject(containerName, objPartName, chunkInputStream);
//
//					totalBytesRead += bytesRead;
//				}
//			}

			// 매니페스트 파일을 업로드
			// objectService.uploadManifestObject(containerName, objectName);

			// 일반 파일 업로드
			objectService.uploadObject(containerName, objectName, inputStream);

			System.out.println("Upload OK");
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		// String jsonString1 = "[{test: 'test'}]";
		JSONArray jarr = new JSONArray();
		try {
			LocalFileListVo lflv = new LocalFileListVo();

			// BasicFileAttributes attr = null;
			// attr = Files.readAttributes(copyOfLocation, BasicFileAttributes.class);

			lflv.setContent_type(ofv.getFile().getContentType());
			lflv.setBytes(Long.toString(ofv.getFile().getSize()));
			// lflv.setLast_modified(attr.lastModifiedTime().toString());
			lflv.setName(ofv.getFile().getOriginalFilename());
			lflv.setHash(Integer.toString(ofv.getFile().hashCode()));
			jsonString = mapper.writeValueAsString(lflv);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		jarr.put(jsonString);
		System.out.println("JSON List Test : " + jsonString);

		return jsonString;
	}

}