package com.example.crud.web.posts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.config.SortAsQueryParam;
import com.example.crud.i18n.Messages;
import com.example.crud.i18n.RuleException;
import com.example.crud.model.Post;
import com.example.crud.service.PostService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService service;
	private final PostMapper mapper;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	@PageableAsQueryParam
	public Page<PostListItemDTO> index(Optional<PostFilter> filter,
			@Parameter(hidden = true) @PageableDefault Pageable pageable) {
		var spec = filter.map(mapper::toSpec).orElse(null);
		var page = this.service.findPage(spec, pageable);
		return page.map(mapper::toListItemDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/list")
	@SortAsQueryParam
	public List<PostListItemDTO> listAll(Optional<PostFilter> filter,
			@Parameter(hidden = true) @SortDefault(sort = "id", direction = Direction.ASC) Sort sort) {
		var spec = filter.map(mapper::toSpec).orElse(null);
		return this.service.findAll(spec, sort).stream().map(mapper::toListItemDto).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Optional<PostFormDTO> show(@PathVariable Long id) {
		return this.service.findById(id).map(mapper::toFormDto);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Long store(@Valid @RequestBody PostFormDTO dto) {
		return this.service.store(this.mapper.toEntity(new Post(), dto));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @Valid @RequestBody PostFormDTO dto) {
		var entity = this.service.findById(id).orElseThrow(() -> new RuleException(Messages.RECORD_NOT_FOUND));
		this.service.update(id, this.mapper.toEntity(entity, dto));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void destroy(@PathVariable Long id) {
		this.service.deleteById(id);
	}

	@GetMapping(value = "/list.xlsx")
	@SortAsQueryParam
	public ResponseEntity<byte[]> exportExcel(Optional<PostFilter> filter,
			@Parameter(hidden = true) @SortDefault(sort = "id", direction = Direction.ASC) Sort sort) {
		var spec = filter.map(mapper::toSpec).orElse(null);
		var data = this.service.findAll(spec, sort);
		return this.mapper.toExcel(data).export();
	}
}
