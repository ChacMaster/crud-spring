package com.example.crud.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboDTO<I> implements Serializable {

	private static final long serialVersionUID = -914698292833381449L;
	private I id;
	private String descricao;

	public static <I> ComboDTO<I> of(BaseEntity<I> entity) {
		if (entity == null || entity.getId() == null) {
			return null;
		}
		return new ComboDTO<>(entity.getId(), entity.getDesc());
	}
}
