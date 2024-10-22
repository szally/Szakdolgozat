package com.assignment;

import com.assignment.service.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class Updater {
    @Autowired
    private TransactionServiceImpl transactionService;

    private static final Logger logger = LoggerFactory.getLogger(Updater.class);

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
