package com.example.crud.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import com.example.crud.base.BaseEntity;
import com.example.crud.util.AuthUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "POSTS")
public class Post extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Formula("title")
	private String desc;

	private boolean deleted;

	private String createdBy;

	private String updatedBy;

	@NotNull
	private LocalDateTime createdAt;

	@NotNull
	private LocalDateTime updatedAt;

	@NotBlank
	@Column(length = 100)
	private String title;

	@NotBlank
	@Column(length = 500)
	private String body;

	@ManyToOne
	private User user;

	public Post(Long id) {
		this.id = id;
	}

	@PrePersist
	private void beforeInsert() {
		this.deleted = false;
		this.createdBy = AuthUtil.getUserName();
		this.updatedBy = this.createdBy;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	private void beforeUpdate() {
		this.updatedBy = AuthUtil.getUserName();
		this.updatedAt = LocalDateTime.now();
	}
}
