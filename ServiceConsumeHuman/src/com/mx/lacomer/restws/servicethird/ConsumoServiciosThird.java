package com.mx.lacomer.restws.servicethird;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ConsumoServiciosThird {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String consumeService(String strFile, String url, String code, Integer folderId,
			String signatureCoordinates, Boolean sendNotification, String signatureStatus, Boolean allowDisagreement)
			throws Exception {
		turnOffSslChecking();
		ResponseEntity<?> response = null;
		try {
			System.clearProperty("http.proxyHost");
			System.clearProperty("http.proxyPort");
			LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
			linkedMultiValueMap.add("signatureCoordinates", signatureCoordinates);
			File fi = new File(strFile);
			if (fi.exists()) {
				if (fi.isFile()) {
					if (fi.length() > 1000000) 
						throw new Exception("Archivo *" + fi.getName() + "* mayor a 1 MB");
				} else
					throw new Exception("La ruta *" + strFile + "* especificada es de un directorio");

			} else
				throw new Exception("Archivo *" + fi.getName() + "* no existe");
			FileSystemResource fileSystemResource = new FileSystemResource(new File(strFile));
			linkedMultiValueMap.add("file", fileSystemResource);
			linkedMultiValueMap.add("folderId", folderId);
			linkedMultiValueMap.add("sendNotification", sendNotification);
			linkedMultiValueMap.add("signatureStatus", signatureStatus);
			linkedMultiValueMap.add("name", fileSystemResource.getFilename());
			linkedMultiValueMap.add("allowDisagreement", allowDisagreement);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.add("Authorization", "Basic " + code);
			HttpEntity<MultiValueMap<String, Object>> request1 = new HttpEntity(linkedMultiValueMap,
					(MultiValueMap) headers);
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			System.out.println("Request to: " + url);
			response = restTemplate.exchange(url, HttpMethod.POST, request1, String.class, new Object[0]);
			System.out.println("Response to: ");
			System.out.println(toJson(response.getBody()));
			return (String) response.getBody();
		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	public static String consumeService(String msj) {
		return msj;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String consumeService(String strFile, String url, String code, String folderId,
			String signatureCoordinates, String sendNotification, String signatureStatus, String allowDisagreement)
			throws Exception {
		turnOffSslChecking();
		ResponseEntity<?> response = null;
		try {
			System.clearProperty("http.proxyHost");
			System.clearProperty("http.proxyPort");
			LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
			linkedMultiValueMap.add("signatureCoordinates", signatureCoordinates);
			File fi = new File(strFile);
			if (fi.exists()) {
				if (fi.isFile()) {
					if (fi.length() > 1000000) 
						throw new Exception("Archivo *" + fi.getName() + "* mayor a 1 MB");
				} else
					throw new Exception("La ruta *" + strFile + "* especificada es de un directorio");

			} else
				throw new Exception("Archivo *" + fi.getName() + "* no existe");

			FileSystemResource fileSystemResource = new FileSystemResource(new File(strFile));
			linkedMultiValueMap.add("file", fileSystemResource);
			linkedMultiValueMap.add("folderId", Integer.valueOf(folderId));
			linkedMultiValueMap.add("sendNotification", Boolean.valueOf(sendNotification));
			linkedMultiValueMap.add("signatureStatus", signatureStatus);
			linkedMultiValueMap.add("name", fileSystemResource.getFilename());
			linkedMultiValueMap.add("allowDisagreement", Boolean.valueOf(allowDisagreement));
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.add("Authorization", "Basic " + code);
			HttpEntity<MultiValueMap<String, Object>> request1 = new HttpEntity(linkedMultiValueMap,
					(MultiValueMap) headers);
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			System.out.println("Request to: " + url);
			response = restTemplate.exchange(url, HttpMethod.POST, request1, String.class, new Object[0]);
			System.out.println("Response to: ");
			System.out.println(toJson(response.getBody()));
			return (String) response.getBody();
		} catch (Exception ex) {

			throw new Exception(ex.getMessage());
		}
	}

	public static String toJson(Object object) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	}

	private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	} };

	public static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, UNQUESTIONING_TRUST_MANAGER, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	public static void turnOnSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext.getInstance("SSL").init(null, null, null);
	}

	public static void main(String[] args) {
		try {
			String signatureCoordinates = "[{  \"page\": 1,  \"x\": 5,  \"y\": 5,  \"width\": 5,  \"height\": 5}]";
			consumeService("test-4.pdf",
					"https://api-prod.humand.co/public/api/v1/users/742506043/documents/files",
					"c4dpS-0u-L-e9TCa04DYlT6NfSagKQYr", Integer.valueOf(9221), signatureCoordinates,
					Boolean.valueOf(false), "SIGNATURE_NOT_NEEDED", Boolean.valueOf(false));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}
}
