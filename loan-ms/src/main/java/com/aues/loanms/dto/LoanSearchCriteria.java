package com.aues.loanms.dto;
import lombok.Data;

import java.time.LocalDate;
@Data
public class LoanSearchCriteria {

    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
}
