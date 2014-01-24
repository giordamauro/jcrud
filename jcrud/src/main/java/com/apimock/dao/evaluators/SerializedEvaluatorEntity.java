package com.apimock.dao.evaluators;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.apimock.dao.MockRequestEvaluatorEntity;
import com.apimock.model.MockRequest;
import com.apimock.model.MockRequestEvaluator;
import com.jcrud.jpa.Serializer;
import com.jcrud.model.HttpRequest;

@Entity
@Table(name = "serializedevaluator")
@PrimaryKeyJoinColumn(name = "id")
public class SerializedEvaluatorEntity extends MockRequestEvaluatorEntity {

	private static final long serialVersionUID = 928191933593617079L;

	@Column
	private byte[] bytes;

	@Column
	private String className;

	public SerializedEvaluatorEntity() {

	}

	public SerializedEvaluatorEntity(MockRequestEvaluator evaluator) {
		setEvaluator(evaluator);
	}

	public <T extends Serializable> MockRequestEvaluator getEvaluator() {

		T object = Serializer.getObject(bytes, className);
		MockRequestEvaluator evaluator = (MockRequestEvaluator) object;

		return evaluator;
	}

	public void setEvaluator(MockRequestEvaluator object) {

		this.bytes = Serializer.getBytes(object);
		this.className = object.getClass().getName();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
		hashCodeBuilder.append(bytes);
		hashCodeBuilder.append(className);

		int hashCode = hashCodeBuilder.toHashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof SerializedEvaluatorEntity))
			return false;

		SerializedEvaluatorEntity serviceMatcher = (SerializedEvaluatorEntity) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(bytes, serviceMatcher.bytes);
		equalsBuilder.append(className, serviceMatcher.className);

		return equalsBuilder.isEquals();
	}

	@Override
	public boolean evaluate(MockRequest mockRequest, HttpRequest request) {
		MockRequestEvaluator evaluator = getEvaluator();

		return evaluator.evaluate(mockRequest, request);
	}

}
