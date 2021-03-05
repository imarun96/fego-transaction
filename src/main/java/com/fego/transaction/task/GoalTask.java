package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.Goal;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class GoalTask implements BaseTask<Goal> {

    @Override
    public void onCreate(Goal model) {
        // Method which executes after an Goal object is created.
    }

    @Override
    public void onUpdate(Goal model) {
        // Method which executes after an Goal object is updated.
    }

    @Override
    public void onDelete(Goal model) {
        // Method which executes after an Goal object is deleted.
    }
}