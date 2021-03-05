package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.Rule;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class RuleTask implements BaseTask<Rule> {

    @Override
    public void onCreate(Rule model) {
        // Method which executes after an Rule object is created.
    }

    @Override
    public void onUpdate(Rule model) {
        // Method which executes after an Rule object is updated.
    }

    @Override
    public void onDelete(Rule model) {
        // Method which executes after an Rule object is deleted.
    }
}