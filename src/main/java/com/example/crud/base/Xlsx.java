package com.example.crud.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.crud.i18n.RuleException;

import lombok.Getter;

public class Xlsx {

	@Getter
	private XSSFWorkbook workbook;
	private String filename;

	public Xlsx(String filename) {
		super();
		this.workbook = new XSSFWorkbook();
		this.filename = filename;
	}

	public ResponseEntity<byte[]> export() {
		var bos = new ByteArrayOutputStream();
		try {
			this.workbook.write(bos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuleException("Erro ao gerar XLSX");
		} finally {
			try {
				this.workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Xlsx.toResponseEntity(this.filename, bos.toByteArray());
	}

	public XSSFCell createCell(XSSFRow row, Integer column, XSSFCellStyle style) {
		var cell = row.createCell(column++);
		cell.setCellStyle(style);
		return cell;
	}

	public static ResponseEntity<byte[]> toResponseEntity(String fileName, byte[] bytes) {
		if (bytes != null) {
			var headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			return ResponseEntity.ok().headers(headers).contentLength(bytes.length)
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(bytes);
		}
		return ResponseEntity.noContent().build();
	}
}
