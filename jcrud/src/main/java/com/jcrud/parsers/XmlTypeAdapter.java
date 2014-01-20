package com.jcrud.parsers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xmlpull.mxp1.MXParserFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;
import com.jcrud.model.HttpTypeAdapter;
import com.jcrud.model.impl.HttpResponseImpl;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;
import com.thoughtworks.xstream.XStream;

public class XmlTypeAdapter implements HttpTypeAdapter {

	private final GsonXml gsonXml;

	private final XStream xs;

	// TODO: set GsonXml by constructor
	public XmlTypeAdapter() {

		XmlParserCreator parserCreator = new XmlParserCreator() {
			@Override
			public XmlPullParser createParser() {

				try {
					final XmlPullParserFactory parserFactory = new MXParserFactory();
					final XmlPullParser parser = parserFactory.newPullParser();
					return parser;
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		};

		gsonXml = new GsonXmlBuilder().setXmlParserCreator(parserCreator).create();

		xs = new XStream();
	}

	@Override
	public <T> T getFromRequest(HttpRequest request, Class<T> resourceClass) {

		InputStream content = request.getContent();
		String stringContent = getStringBodyContent(content);
		T object = gsonXml.fromXml(stringContent, resourceClass);

		return object;
	}

	@Override
	public HttpResponse toHttpResponse(HttpRequest request, Object responseObject) {

		String xml = xs.toXML(responseObject);
		byte[] bytes = xml.getBytes();
		InputStream content = new ByteArrayInputStream(bytes);

		HttpResponseImpl response = new HttpResponseImpl(200, content);
		response.setHeader("Content-Type", "application/xml");

		return response;
	}

	private String getStringBodyContent(InputStream content) {

		StringBuilder data = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(content);
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				data.append(readData);
			}
			reader.close();
			return data.toString();

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
