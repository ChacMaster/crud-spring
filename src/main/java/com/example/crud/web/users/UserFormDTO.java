package com.example.crud.web.users;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.crud.base.OptionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFormDTO implements Serializable {

	private static final long serialVersionUID = -8191033949403830217L;
	private Long id;
	private LocalDateTime createdAt;
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hrCadastro;
	@NotBlank
	private String name;
	@NotNull
	private LocalDate dtNascimento;
	private List<OptionDTO<Long>> posts;
}
