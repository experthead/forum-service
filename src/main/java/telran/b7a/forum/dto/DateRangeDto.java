package telran.b7a.forum.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DateRangeDto {
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateFrom;
	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate dateTo;

	public LocalDateTime dateTimeFrom() {
		return dateFrom.atStartOfDay();

	}

	public LocalDateTime dateTimeTo() {
		return dateTo.atStartOfDay();

	}
}
