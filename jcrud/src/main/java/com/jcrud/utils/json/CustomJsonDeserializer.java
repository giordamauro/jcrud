package com.jcrud.utils.json;

import com.google.gson.JsonDeserializer;

public interface CustomJsonDeserializer<T> extends JsonDeserializer<T> {

	Class<T> getDeserializingClass();

}
