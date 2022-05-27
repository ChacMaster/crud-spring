package com.example.crud.web.posts;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.crud.base.OptionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostListItemDTO implements Serializable {

	private static final long serialVersionUID = -1010531517710015436L;
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String title;
	private String body;
	private OptionDTO<Long> user;
}
