package com.example.crud.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO<I> implements Serializable {

	private static final long serialVersionUID = -914698292833381449L;
	private I key;
	private String value;

	public static <I> OptionDTO<I> of(BaseEntity<I> entity) {
		if (entity == null || entity.getId() == null) {
			return null;
		}
		return new OptionDTO<>(entity.getId(), entity.getDesc());
	}
}
