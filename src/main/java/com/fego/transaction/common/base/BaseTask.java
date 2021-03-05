package com.fego.transaction.common.base;

public interface BaseTask<M extends BaseModel> {
    void onCreate(M model);

    void onUpdate(M model);

    void onDelete(M model);
}