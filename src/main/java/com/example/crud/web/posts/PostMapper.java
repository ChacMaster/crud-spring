package com.example.crud.web.posts;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.crud.base.ComboDTO;
import com.example.crud.base.SpecUtil;
import com.example.crud.model.Post;
import com.example.crud.model.Post_;
import com.example.crud.model.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostMapper {

	private final ModelMapper mapper;

	public Specification<Post> toSpec(PostFilter filter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			predicates.add(SpecUtil.isEqual(builder, root.get(Post_.ID), filter.getId()));
			predicates.add(SpecUtil.like(builder, root.get(Post_.TITLE), filter.getTitle()));
			return SpecUtil.toAndArray(builder, predicates);
		};
	}

	public PostListItemDTO toListItemDto(Post entity) {
		var dto = this.mapper.map(entity, PostListItemDTO.class);
		dto.setUser(ComboDTO.of(entity.getUser()));
		return dto;
	}

	public PostFormDTO toFormDto(Post entity) {
		return this.mapper.map(entity, PostFormDTO.class);
	}

	public Post toEntity(Post entity, PostFormDTO dto) {
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setBody(dto.getBody());
		entity.setUser(Optional.ofNullable(dto.getUser()).map(ComboDTO::getId).map(User::new).orElse(null));
		return entity;
	}
}
