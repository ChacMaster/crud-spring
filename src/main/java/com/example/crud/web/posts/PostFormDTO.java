package com.example.crud.web.posts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.crud.base.OptionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFormDTO implements Serializable {

	private static final long serialVersionUID = -34687441674558399L;
	private Long id;
	private OptionDTO<Long> user;
	private LocalDateTime createdAt;
	@NotBlank
	private String title;
	@NotBlank
	private String body;
	@NotNull
	private LocalDate dtNascimento;
}
