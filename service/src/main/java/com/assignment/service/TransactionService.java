package com.assignment.service;

import java.time.LocalDate;
import java.util.Date;

public interface TransactionService {

    void setBookingDateForTransactions(Date now);
    void updateBookedTransactions(Date now);
}
