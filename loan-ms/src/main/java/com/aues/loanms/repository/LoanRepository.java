package com.aues.loanms.repository;
import com.aues.loanms.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    // Find loans by user ID
    List<Loan> findByUserId(Long userId);

    // Find loans by book ID
    List<Loan> findByBookId(Long bookId);

    // Find loans that are overdue
    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDate currentDate);

    List<Loan> findByDueDateAfter(LocalDate currentDate);

    // Additional custom queries can be added here
}
