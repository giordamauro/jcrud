package com.jcrud.jpa;

import java.io.Serializable;

import javax.persistence.Column;

public abstract class DBSerializer {

	@Column
	private byte[] bytes;

	@Column
	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public <T extends Serializable> T getObject() {

		T object = Serializer.getObject(bytes, className);

		return object;
	}

	public void setObject(Object object) {

		this.bytes = Serializer.getBytes(object);
		this.className = object.getClass().getName();
	}

}
