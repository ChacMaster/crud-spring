package com.example.crud.web.users;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.crud.base.OptionDTO;
import com.example.crud.model.Post;
import com.example.crud.model.Post_;
import com.example.crud.model.User;
import com.example.crud.model.User_;
import com.example.crud.util.SpecUtil;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMapper {

	private final ModelMapper mapper;

	public Specification<User> toSpec(UserFilter filter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			predicates.add(SpecUtil.isEqual(builder, root.get(User_.ID), filter.getId()));
			predicates.add(SpecUtil.like(builder, root.get(User_.NAME), filter.getName()));
			if (StringUtils.isNotEmpty(filter.getPostTitle())) {
				var subQuery = query.subquery(User.class);
				var subRoot = subQuery.from(Post.class);
				subQuery.select(subRoot.get(Post_.USER));
				var subPredicates = new ArrayList<Predicate>();
				subPredicates.add(builder.equal(root, subRoot.get(Post_.USER)));
				subPredicates.add(SpecUtil.like(builder, subRoot.get(Post_.TITLE), filter.getPostTitle()));
				subQuery.where(SpecUtil.toAndArray(builder, subPredicates));
				predicates.add(builder.exists(subQuery));
			}
			return SpecUtil.toAndArray(builder, predicates);
		};
	}

	public UserListItemDTO toListItemDto(User entity) {
		var dto = this.mapper.map(entity, UserListItemDTO.class);
		if (CollectionUtils.isNotEmpty(entity.getPosts())) {
			dto.setPosts(entity.getPosts().stream().map(OptionDTO::of).toList());
		}
		return dto;
	}

	public UserFormDTO toFormDto(User entity) {
		var dto = this.mapper.map(entity, UserFormDTO.class);
		if (CollectionUtils.isNotEmpty(entity.getPosts())) {
			dto.setPosts(entity.getPosts().stream().map(OptionDTO::of).toList());
		}
		return dto;
	}

	public User toEntity(User entity, UserFormDTO dto) {
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDtNascimento(dto.getDtNascimento());
		entity.setHrCadastro(dto.getHrCadastro());
		return entity;
	}
}
