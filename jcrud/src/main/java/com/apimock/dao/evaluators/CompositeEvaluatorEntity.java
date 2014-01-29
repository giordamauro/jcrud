package com.apimock.dao.evaluators;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apimock.dao.MockRequestEvaluatorEntity;

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

	public MockRequestEvaluatorEntity getEvaluator1() {
		return evaluator1;
	}

	public void setEvaluator1(MockRequestEvaluatorEntity evaluator1) {
		this.evaluator1 = evaluator1;
	}

	public MockRequestEvaluatorEntity getEvaluator2() {
		return evaluator2;
	}

	public void setEvaluator2(MockRequestEvaluatorEntity evaluator2) {
		this.evaluator2 = evaluator2;
	}
}
