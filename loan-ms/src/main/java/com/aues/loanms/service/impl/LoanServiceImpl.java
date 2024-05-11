package com.aues.loanms.service.impl;
import com.aues.loanms.exception.LoanNotFoundException;
import com.aues.loanms.model.Loan;
import com.aues.loanms.model.enums.LoanStatus;
import com.aues.loanms.repository.LoanRepository;
import com.aues.loanms.service.BookServiceClient;
import com.aues.loanms.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookServiceClient bookServiceClient;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, BookServiceClient bookServiceClient) {
        this.loanRepository = loanRepository;
        this.bookServiceClient = bookServiceClient;
    }


    @Override
    @Transactional
    public Loan loanBook(Loan loan) {
        // Additional validation and business logic can be added here
        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public Loan updateLoan(Long loanId, Loan updatedLoan) {
        return loanRepository.findById(loanId).map(loan -> {
            if (updatedLoan.getBookId() != null) {
                loan.setBookId(updatedLoan.getBookId());
            }
            if (updatedLoan.getUserId() != null) {
                loan.setUserId(updatedLoan.getUserId());
            }
            if (updatedLoan.getLoanDate() != null) {
                loan.setLoanDate(updatedLoan.getLoanDate());
            }
            if (updatedLoan.getDueDate() != null) {
                loan.setDueDate(updatedLoan.getDueDate());
            }
            if (updatedLoan.getReturnDate() != null) {
                loan.setReturnDate(updatedLoan.getReturnDate());
            }
            return loanRepository.save(loan);
        }).orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
    }

    @Override
    @Transactional
    public Loan returnBook(Long loanId, LocalDate returnDate) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
        loan.setReturnDate(returnDate);
        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public Loan extendLoan(Long loanId, LocalDate newDueDate) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
        loan.setDueDate(newDueDate);
        return loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
    }

    @Override
    @Transactional
    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
        loanRepository.delete(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getOverdueLoans(LocalDate currentDate) {
        return loanRepository.findByDueDateBeforeAndReturnDateIsNull(currentDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> searchLoans(Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate) {
        Specification<Loan> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
            }

            if (bookId != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookId"), bookId));
            }

            if (loanDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("loanDate"), loanDate));
            }

            if (dueDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return loanRepository.findAll(spec);
    }

    // loan-service/src/main/java/com/yourcompany/loanservice/service/LoanService.java
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    // loan-service/src/main/java/com/yourcompany/loanservice/service/LoanService.java
    public List<Loan> getActiveLoans() {
        LocalDate today = LocalDate.now();
        return loanRepository.findByDueDateAfter(today);
    }

    // loan-service/src/main/java/com/yourcompany/loanservice/service/LoanService.java
    // loan-service/src/main/java/com/yourcompany/loanservice/service/LoanService.java
    // loan-service/src/main/java/com/yourcompany/loanservice/service/LoanService.java
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
        loan.setStatus(LoanStatus.RETURNED);
        Loan savedLoan = loanRepository.save(loan);
        bookServiceClient.incrementAvailableCopies(loan.getBookId()); // Assuming bookId is stored in Loan
        return savedLoan;
    }





}
