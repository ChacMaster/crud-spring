package com.example.crud.web.posts;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFilter implements Serializable {

	private static final long serialVersionUID = -4990951590205890859L;
	private Long id;
	private String title;
}
