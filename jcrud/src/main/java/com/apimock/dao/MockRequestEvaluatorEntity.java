package com.apimock.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.apimock.model.MockRequestEvaluator;

@Entity
@Table(name = "mockrequestevaluator")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MockRequestEvaluatorEntity implements MockRequestEvaluator, Serializable {

	private static final long serialVersionUID = 1392494375037690020L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	public long getId() {
		return id;
	}
}
