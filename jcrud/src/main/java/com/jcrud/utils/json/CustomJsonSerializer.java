package com.jcrud.utils.json;

import com.google.gson.JsonSerializer;

public interface CustomJsonSerializer<T> extends JsonSerializer<T> {

	Class<T> getSerializingClass();

}
