package com.example.crud.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The interface Sort as query param.
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
		, description = "Sorting criteria in the format: property,(asc|desc). "
		+ "Default sort order is ascending. " + "Multiple sort criteria are supported."
		, name = "sort"
		, array = @ArraySchema(schema = @Schema(type = "string", example = "id,asc")))
public @interface
SortAsQueryParam {

}