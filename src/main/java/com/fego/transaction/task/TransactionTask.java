package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.Transaction;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class TransactionTask implements BaseTask<Transaction> {

    @Override
    public void onCreate(Transaction model) {
        // Method which executes after an Transaction object is created.
    }

    @Override
    public void onUpdate(Transaction model) {
        // Method which executes after an Transaction object is updated.
    }

    @Override
    public void onDelete(Transaction model) {
        // Method which executes after an Transaction object is deleted.
    }
}