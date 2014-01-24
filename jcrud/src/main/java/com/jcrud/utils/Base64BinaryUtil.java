package com.jcrud.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class Base64BinaryUtil {

	public static void main(String[] args) throws Exception {

	}

	public static String getBinaryFileBase64Encoded(String fileName) {

		try {
			InputStream fileInputStream = new FileInputStream(fileName);

			byte[] bytes = IOUtils.toByteArray(fileInputStream);

			String encodeFileBase64 = Base64.encodeBase64String(bytes);

			return encodeFileBase64;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
