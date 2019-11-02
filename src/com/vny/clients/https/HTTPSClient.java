package com.vny.clients.https;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSClient {

	public static void main(String[] args) throws Exception {
        //System.setProperty("javax.net.ssl.keyStore", "C:\\Program Files\\Java\\jdk1.8.0_152\\jre\\lib\\security\\cacerts");
		//System.setProperty("javax.net.ssl.keyStorePassword", "changeit");

		long start = System.currentTimeMillis();
		System.out.println("STATS START: " + (new Timestamp(new Date(System.currentTimeMillis()).getTime())));

		URL url;
		Writer writer = null;
		DataInputStream inStream = null;
		HttpURLConnection con = null;
		Reader reader = null;
		String p_url = "https://172.16.233.163:8443/jsp/tomcat.jsp";
		try {

			url = new URL(p_url);

			con = (HttpsURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			con.setUseCaches(false);
			con.setDoOutput(true);

			if (con.getResponseCode() < HttpURLConnection.HTTP_OK
					|| con.getResponseCode() > HttpURLConnection.HTTP_PARTIAL) {
				throw new Exception("Request to MCM failed...");
			}

			inStream = new DataInputStream(con.getInputStream());

			if (inStream != null) {
				writer = new StringWriter();
				char[] buffer = new char[1024];
				try {
					reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
					reader.close();
				} catch (Exception e) {
					System.out.println("Error message while reading the buffer: " + e.getMessage());
					throw e;
				}
			}
		} catch (UnknownHostException e) {
			System.out.println("Host: " + e.getMessage() + " is not reachable, URL: " + p_url);
			throw e;
		} catch (Exception e) {
			System.out.println("Error message in getHttp method: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (inStream != null)
					inStream.close();
				if (con != null)
					con.disconnect();
			} catch (Exception e) {
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(
				"STATS ELAPSED TIME:" + ((end - start) / 1000) + "s or " + (end - start) + "ms *** URL : " + p_url);
		System.out.println("STATS END: " + ((new Timestamp(new Date(end).getTime()))));

		System.out.println(writer.toString());
		writer.close();

	}

}
