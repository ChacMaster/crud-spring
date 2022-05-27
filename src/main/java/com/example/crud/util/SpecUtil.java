package com.example.crud.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class SpecUtil {

	private static final String TRANSLATE_FROM = "'áàâãäéèêëíìïóòôõöúùûüÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ'";
	private static final String TRANSLATE_TO = "'aaaaaeeeeiiiooooouuuuAAAAAEEEEIIIOOOOOUUUUcC'";

	private SpecUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Expression<String> removeAccents(CriteriaBuilder builder, Expression<String> expression) {
		return builder.function("translate", String.class, expression, builder.literal(TRANSLATE_FROM),
				builder.literal(TRANSLATE_TO));
	}

	public static Pageable validateAndReplaceSorts(Map<String, String> fields, Pageable pageable) {
		var sort = pageable.getSort();
		if (sort.isSorted()) {
			var orders = new ArrayList<Order>();
			sort.stream().iterator().forEachRemaining(order -> {
				if (fields.containsKey(order.getProperty())) {
					orders.add(new Sort.Order(order.getDirection(), fields.get(order.getProperty())));
				} else {
					orders.add(order);
				}
			});
			sort = Sort.by(orders);
		}
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
	}

	public static Predicate toAndArray(CriteriaBuilder builder, Collection<Predicate> predicates) {
		predicates.removeIf(Objects::isNull);
		if (predicates.isEmpty()) {
			return null;
		}
		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

	public static Predicate toOrArray(CriteriaBuilder builder, Collection<Predicate> predicates) {
		predicates.removeIf(Objects::isNull);
		if (predicates.isEmpty()) {
			return null;
		}
		return builder.or(predicates.toArray(new Predicate[predicates.size()]));
	}

	public static Expression<String> concatAll(CriteriaBuilder builder, Expression<?>... x) {
		var result = x[0].as(String.class);
		for (var i = 1; i < x.length; i++) {
			result = builder.concat(result, x[i].as(String.class));
		}
		return result;
	}

	public static Predicate isEqual(CriteriaBuilder builder, Expression<?> path, Optional<?> filterOpt) {
		if (filterOpt.isEmpty()) {
			return null;
		}
		return builder.equal(path, filterOpt.get());
	}

	public static Predicate like(CriteriaBuilder builder, Expression<?> path, Optional<String> filterOpt) {
		if (filterOpt.isEmpty() || filterOpt.get().isBlank()) {
			return null;
		}
		var filterValue = filterOpt.get().replace("*", "%").trim().toUpperCase();
		return builder.like(builder.upper(builder.trim(path.as(String.class))), filterValue);
	}

	public static Predicate contains(CriteriaBuilder builder, Expression<?> path, Optional<String> filterOpt) {
		return like(builder, path, filterOpt.map(val -> val.isBlank() ? val : "%" + val.trim() + "%"));
	}

	public static <F> Predicate isEqual(CriteriaBuilder builder, Expression<?> path, F filter) {
		return isEqual(builder, path, Optional.ofNullable(filter));
	}

	public static Predicate like(CriteriaBuilder builder, Expression<?> path, String filter) {
		return like(builder, path, Optional.ofNullable(filter));
	}

	public static Predicate contains(CriteriaBuilder builder, Expression<?> path, String filter) {
		return contains(builder, path, Optional.ofNullable(filter));
	}
}
