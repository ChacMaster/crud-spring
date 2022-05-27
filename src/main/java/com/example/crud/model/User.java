package com.example.crud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import com.example.crud.base.BaseEntity;
import com.example.crud.util.AuthUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USERS")
public class User extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8927339884166830542L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Formula("name")
	private String desc;

	private boolean deleted;

	private String createdBy;

	private String updatedBy;

	@NotNull
	private LocalDateTime createdAt;

	@NotNull
	private LocalDateTime updatedAt;

	@NotBlank
	private String name;

	@NotNull
	private LocalDate dtNascimento;

	private LocalTime hrCadastro;

	@OneToMany(mappedBy = "user")
	@BatchSize(size = 10)
	private List<Post> posts;

	public User(Long id) {
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
