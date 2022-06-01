package com.example.crud.web.users;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.i18n.Messages;
import com.example.crud.i18n.RuleException;
import com.example.crud.model.User;
import com.example.crud.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService service;
	private final UserMapper mapper;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	@PageableAsQueryParam
	public Page<UserListItemDTO> index(Optional<UserFilter> filter,
			@Parameter(hidden = true) @PageableDefault Pageable pageable) {
		var spec = filter.map(mapper::toSpec).orElse(null);
		var page = this.service.findPage(spec, pageable);
		return page.map(mapper::toListItemDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/list")
	@Parameter(in = ParameterIn.QUERY, description = "Sorting criteria in the format: property(,asc|desc). "
			+ "Default sort order is ascending. "
			+ "Multiple sort criteria are supported.", name = "sort", array = @ArraySchema(schema = @Schema(type = "string", example = "id,asc")))
	public List<UserListItemDTO> listAll(Optional<UserFilter> filter,
			@Parameter(hidden = true) @SortDefault(sort = "id", direction = Direction.ASC) Sort sort) {
		var spec = filter.map(mapper::toSpec).orElse(null);
		return this.service.findAll(spec, sort).stream().map(mapper::toListItemDto).toList();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Optional<UserFormDTO> show(@PathVariable Long id) {
		return this.service.findById(id).map(mapper::toFormDto);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long store(@Valid @RequestBody UserFormDTO dto) {
		return this.service.store(this.mapper.toEntity(new User(), dto));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @Valid @RequestBody UserFormDTO dto) {
		var entity = this.service.findById(id).orElseThrow(() -> new RuleException(Messages.RECORD_NOT_FOUND));
		this.service.update(id, this.mapper.toEntity(entity, dto));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void destroy(@PathVariable Long id) {
		this.service.deleteById(id);
	}
}
