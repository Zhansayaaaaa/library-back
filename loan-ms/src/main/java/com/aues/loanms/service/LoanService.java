package com.aues.loanms.service;
import com.aues.loanms.model.Loan;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {

    // Create a new loan
    Loan loanBook(Loan loan);

    // Update an existing loan
    Loan updateLoan(Long loanId, Loan updatedLoan);

    // Return a loaned book
    Loan returnBook(Long loanId, LocalDate returnDate);

    // Extend a loan
    Loan extendLoan(Long loanId, LocalDate newDueDate);

    // Get a loan by its ID
    Loan getLoanById(Long loanId);

    // Delete a loan by its ID
    void deleteLoan(Long loanId);

    // List all loans
    List<Loan> getAllLoans();

    // Find loans by user ID
    List<Loan> getLoansByUserId(Long userId);

    // Find loans by book ID
    List<Loan> getLoansByBookId(Long bookId);

    // Get overdue loans
    List<Loan> getOverdueLoans(LocalDate currentDate);

    // Optional: Search loans by criteria
    List<Loan> searchLoans(Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate);

    Loan createLoan(Loan loan);

    List<Loan> getActiveLoans();

    Loan returnBook(Long loanId);
}

