package com.fego.transaction.task;

import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.entity.PeerUser;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class PeerUserTask implements BaseTask<PeerUser> {

    @Override
    public void onCreate(PeerUser model) {
        // Method which executes after an PeerUser object is created.
    }

    @Override
    public void onUpdate(PeerUser model) {
        // Method which executes after an PeerUser object is updated.
    }

    @Override
    public void onDelete(PeerUser model) {
        // Method which executes after an PeerUser object is deleted.
    }
}