package godsang.backend.entity.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RequestIIntakeHistory {
    Long memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

}
