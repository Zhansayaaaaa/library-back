package com.aues.loanms.controller;
import com.aues.loanms.dto.LoanSearchCriteria;
import com.aues.loanms.exception.LoanNotFoundException;
import com.aues.loanms.model.Loan;
import com.aues.loanms.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/loans")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // loan-service/src/main/java/com/yourcompany/loanservice/controller/LoanController.java
    @PostMapping("/loans")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan newLoan = loanService.createLoan(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }



    @PutMapping("/{loanId}")
    public ResponseEntity<?> updateLoan(@PathVariable Long loanId, @RequestBody Loan updatedLoan) {
        try {
            Loan loan = loanService.updateLoan(loanId, updatedLoan);
            return ResponseEntity.ok(loan);
        } catch (LoanNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        try {
            Loan loan = loanService.getLoanById(loanId);
            return ResponseEntity.ok(loan);
        } catch (LoanNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long loanId) {
        try {
            loanService.deleteLoan(loanId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LoanNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        try {
            Loan loan = loanService.returnBook(loanId, returnDate);
            return ResponseEntity.ok(loan);
        } catch (LoanNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{loanId}/extend")
    public ResponseEntity<?> extendLoan(@PathVariable Long loanId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newDueDate) {
        try {
            Loan loan = loanService.extendLoan(loanId, newDueDate);
            return ResponseEntity.ok(loan);
        } catch (LoanNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLoansByUserId(@PathVariable Long userId) {
        try {
            List<Loan> loans = loanService.getLoansByUserId(userId);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getLoansByBookId(@PathVariable Long bookId) {
        try {
            List<Loan> loans = loanService.getLoansByBookId(bookId);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueLoans() {
        try {
            List<Loan> loans = loanService.getOverdueLoans(LocalDate.now()); // Consider your application's time zone settings
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchLoans(@RequestBody LoanSearchCriteria criteria) {
        try {
            List<Loan> loans = loanService.searchLoans(criteria.getUserId(), criteria.getBookId(), criteria.getLoanDate(), criteria.getDueDate());
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        List<Loan> activeLoans = loanService.getActiveLoans();
        return ResponseEntity.ok(activeLoans);
    }

    // loan-service/src/main/java/com/yourcompany/loanservice/controller/LoanController.java
    @PostMapping("/loans/{loanId}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long loanId) {
        Loan returnedLoan = loanService.returnBook(loanId);
        return ResponseEntity.ok(returnedLoan);
    }



}
