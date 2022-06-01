package com.example.crud.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.example.crud.i18n.Messages;
import com.example.crud.i18n.RuleException;

public abstract class BaseService<I extends Serializable, E extends BaseEntity<I>, R extends BaseRepository<E, I>> {

	public final R repository;

	protected BaseService(R repository) {
		this.repository = repository;
	}

	public E save(E entity) {
		return this.repository.save(entity);
	}

	public void deleteById(I id) {
		if (!this.existsById(id)) {
			throw new RuleException(Messages.RECORD_NOT_FOUND);
		}
		this.repository.deleteById(id);
	}

	public Page<E> findPage(Specification<E> spec, Pageable pageable) {
		return this.repository.findAll(spec, pageable);
	}

	public List<E> findAll(Specification<E> spec, Sort sort) {
		return this.repository.findAll(spec, sort);
	}

	public Optional<E> findById(I id) {
		return this.repository.findById(id);
	}

	public I store(E entity) {
		return this.save(entity).getId();
	}

	public void update(I id, E entity) {
		if (!this.existsById(id)) {
			throw new RuleException(Messages.RECORD_NOT_FOUND);
		}
		entity.setId(id);
		this.save(entity);
	}

	public boolean existsById(I id) {
		return this.repository.existsById(id);
	}
}
