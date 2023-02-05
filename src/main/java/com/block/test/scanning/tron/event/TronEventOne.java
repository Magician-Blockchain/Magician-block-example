package com.block.test.scanning.tron.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.TronMonitorEvent;

public class TronEventOne implements TronMonitorEvent {
    @Override
    public void call(TransactionModel transactionModel) {
        System.out.println("TRON 成功了！！！");
        System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
    }
}
