package com.jcrud.utils.json;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactoryBean implements FactoryBean<Gson> {

	private final GsonBuilder gs;

	private final List<CustomJsonDeserializer<?>> deserializers;

	public GsonFactoryBean(GsonBuilder gs, List<CustomJsonDeserializer<?>> deserializers) {
		this.gs = gs;
		this.deserializers = deserializers;
	}

	public GsonFactoryBean(GsonBuilder gs) {
		this(gs, null);
	}

	@Override
	public Gson getObject() throws Exception {

		if (deserializers != null) {
			for (CustomJsonDeserializer<?> deserializer : deserializers) {

				gs.registerTypeAdapter(deserializer.getDeserializingClass(), deserializer);
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
