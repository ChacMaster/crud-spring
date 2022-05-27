package com.example.crud.web.users;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserFilter implements Serializable {

	private static final long serialVersionUID = -1688928902613551826L;
	private Long id;
	private String name;
	private String postTitle;
}
