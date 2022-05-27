package com.example.crud.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<I> implements Serializable {

	private static final long serialVersionUID = -9067577225280177408L;

	public abstract I getId();

	public abstract void setId(I id);

	public abstract String getDesc();
}
