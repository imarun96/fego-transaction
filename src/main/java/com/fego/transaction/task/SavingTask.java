package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.Saving;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class SavingTask implements BaseTask<Saving> {

    @Override
    public void onCreate(Saving model) {
        // Method which executes after an Saving object is created.
    }

    @Override
    public void onUpdate(Saving model) {
        // Method which executes after an Saving object is updated.
    }

    @Override
    public void onDelete(Saving model) {
        // Method which executes after an Saving object is deleted.
    }
}