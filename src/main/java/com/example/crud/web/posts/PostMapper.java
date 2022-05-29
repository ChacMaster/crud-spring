package com.example.crud.web.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.crud.base.OptionDTO;
import com.example.crud.base.Xlsx;
import com.example.crud.i18n.MessageFactory;
import com.example.crud.model.Post;
import com.example.crud.model.Post_;
import com.example.crud.model.User;
import com.example.crud.util.SpecUtil;

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
		dto.setUser(OptionDTO.of(entity.getUser()));
		return dto;
	}

	public PostFormDTO toFormDto(Post entity) {
		return this.mapper.map(entity, PostFormDTO.class);
	}

	public Post toEntity(Post entity, PostFormDTO dto) {
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setBody(dto.getBody());
		entity.setUser(Optional.ofNullable(dto.getUser()).map(OptionDTO::getKey).map(User::new).orElse(null));
		return entity;
	}

	public Xlsx toExcel(List<Post> data) {
		var xlsx = new Xlsx("posts");
		var wb = xlsx.getWorkbook();
		var cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		var dtTimeStyle = wb.createCellStyle();
		dtTimeStyle.setVerticalAlignment(VerticalAlignment.TOP);
		dtTimeStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy hh:mm"));

		var sheet = wb.createSheet("Posts");
		sheet.createFreezePane(0, 1, 0, 1);
		var line = sheet.getLastRowNum() + 1;
		var row = sheet.createRow(line++);
		var column = 0;

		var labelPrefix = "post.";
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Post_.ID));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Post_.TITLE));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Post_.BODY));
		xlsx.createCell(row, column++, cellStyle).setCellValue(MessageFactory.getLabel(labelPrefix + Post_.CREATED_AT));
		for (var item : data) {
			column = 0;
			row = sheet.createRow(line++);
			xlsx.createCell(row, column++, cellStyle).setCellValue(item.getId());
			xlsx.createCell(row, column++, cellStyle).setCellValue(item.getTitle());
			xlsx.createCell(row, column++, cellStyle).setCellValue(item.getBody());
			xlsx.createCell(row, column++, dtTimeStyle).setCellValue(item.getCreatedAt());
		}
		sheet.autoSizeColumn(1);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.autoSizeColumn(3);
		return xlsx;
	}
}
