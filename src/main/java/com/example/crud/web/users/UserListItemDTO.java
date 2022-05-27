package com.example.crud.web.users;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.example.crud.base.OptionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListItemDTO implements Serializable {

	private static final long serialVersionUID = -7318987152735757968L;
	private Long id;
	private LocalDateTime createdAt;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hrCadastro;
	private String name;
	private LocalDate dtNascimento;
	private List<OptionDTO<Long>> posts;
}
