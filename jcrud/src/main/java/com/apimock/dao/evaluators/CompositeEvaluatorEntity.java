package com.apimock.dao.evaluators;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apimock.dao.MockRequestEvaluatorEntity;
import com.apimock.model.MockRequest;
import com.jcrud.model.HttpRequest;

@Entity
@Table(name = "compositeevaluator")
@PrimaryKeyJoinColumn(name = "id")
public class CompositeEvaluatorEntity extends MockRequestEvaluatorEntity {

	private static final long serialVersionUID = 928191933593617079L;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "evaluator1_id")
	private MockRequestEvaluatorEntity evaluator1;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "evaluator2_id")
	private MockRequestEvaluatorEntity evaluator2;

	public CompositeEvaluatorEntity() {

	}

	public CompositeEvaluatorEntity(MockRequestEvaluatorEntity evaluator1, MockRequestEvaluatorEntity evaluator2) {
		setEvaluator1(evaluator1);
		setEvaluator2(evaluator2);
	}

	public void setEvaluator1(MockRequestEvaluatorEntity evaluator1) {
		this.evaluator1 = evaluator1;
	}

	public void setEvaluator2(MockRequestEvaluatorEntity evaluator2) {
		this.evaluator2 = evaluator2;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
		hashCodeBuilder.append(evaluator1);
		hashCodeBuilder.append(evaluator2);

		int hashCode = hashCodeBuilder.toHashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof CompositeEvaluatorEntity))
			return false;

		CompositeEvaluatorEntity serviceMatcher = (CompositeEvaluatorEntity) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(evaluator1, serviceMatcher.evaluator1);
		equalsBuilder.append(evaluator2, serviceMatcher.evaluator2);

		return equalsBuilder.isEquals();
	}

	@Override
	public boolean evaluate(MockRequest mockRequest, HttpRequest request) {
		return evaluator1.evaluate(mockRequest, request) && evaluator2.evaluate(mockRequest, request);
	}
}
