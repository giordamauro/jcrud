package com.apimock.dao.evaluators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.apimock.dao.MockRequestEvaluatorEntity;

@Entity
@Table(name = "serializedevaluator")
@PrimaryKeyJoinColumn(name = "id")
public class SerializedEvaluatorEntity extends MockRequestEvaluatorEntity {

	private static final long serialVersionUID = 928191933593617079L;

	@Column
	private byte[] bytes;

	@Column
	private String className;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
