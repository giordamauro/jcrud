package com.jcrud.utils.json;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactoryBean implements FactoryBean<Gson> {

	private final GsonBuilder gs;

	private List<CustomJsonDeserializer<?>> deserializers;

	private List<CustomJsonSerializer<?>> serializers;

	public GsonFactoryBean(GsonBuilder gs, List<CustomJsonDeserializer<?>> deserializers, List<CustomJsonSerializer<?>> serializers) {
		this.gs = gs;
		this.deserializers = deserializers;
		this.serializers = serializers;
	}

	public GsonFactoryBean(GsonBuilder gs) {
		this(gs, null, null);
	}

	public void setDeserializers(List<CustomJsonDeserializer<?>> deserializers) {
		this.deserializers = deserializers;
	}

	public void setSerializers(List<CustomJsonSerializer<?>> serializers) {
		this.serializers = serializers;
	}

	@Override
	public Gson getObject() throws Exception {

		if (deserializers != null) {

			for (CustomJsonDeserializer<?> deserializer : deserializers) {
				gs.registerTypeAdapter(deserializer.getDeserializingClass(), deserializer);
			}
		}

		if (serializers != null) {

			for (CustomJsonSerializer<?> serializer : serializers) {
				gs.registerTypeAdapter(serializer.getSerializingClass(), serializer);
			}
		}
		return gs.create();
	}

	@Override
	public Class<?> getObjectType() {
		return Gson.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
