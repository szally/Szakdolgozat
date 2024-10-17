package com.assignment.app;

import com.assignment.service.TransactionHistoryServiceImpl;
import com.assignment.service.TransactionService;
import com.assignment.service.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class TransactionUpdater {
    @Autowired
    private TransactionServiceImpl transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionUpdater.class);

    @Scheduled(cron = "0 */2 * * * ?")
    public void updateTransactionBookingDate() {
        logger.info("Starting update of booking date");
        try {
            transactionService.setBookingDateForTransactions(Date.from(Instant.now()));
            logger.info("Booking date updated successfully");
        } catch (Exception e) {
            logger.error("Error updating booking date", e);
        }
    }
    @Scheduled(cron = "0 */5 * * * ?")
    public void updateTransactionStatuses() {
        logger.info("Starting update of transaction statuses");
        try {
            transactionService.updateBookedTransactions(Date.from(Instant.now()));
            logger.info("Transaction statuses updated successfully");
        } catch (Exception e) {
            logger.error("Error updating transaction statuses", e);
        }
    }
}
