package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.SavingsMeta;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class SavingsMetaTask implements BaseTask<SavingsMeta> {

    @Override
    public void onCreate(SavingsMeta model) {
        // Method which executes after an SavingsMeta object is created.
    }

    @Override
    public void onUpdate(SavingsMeta model) {
        // Method which executes after an SavingsMeta object is updated.
    }

    @Override
    public void onDelete(SavingsMeta model) {
        // Method which executes after an SavingsMeta object is deleted.
    }
}