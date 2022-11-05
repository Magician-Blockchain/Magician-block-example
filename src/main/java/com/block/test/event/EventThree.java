package com.block.test.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;

/**
 * Listens for transactions sent from the specified address
 */
public class EventThree implements EthMonitorEvent {
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setFromAddress("");
    }

    @Override
    public void call(TransactionModel transactionModel) {
        String template = "EventThree 扫描到了, hash:{0}, from:{1}, to: {2}, input: {3}";
        template = template.replace("{0}", transactionModel.getEthTransactionModel().getBlockHash());
        template = template.replace("{1}", transactionModel.getEthTransactionModel().getFrom());
        template = template.replace("{2}", transactionModel.getEthTransactionModel().getTo());
        template = template.replace("{3}", transactionModel.getEthTransactionModel().getInput());

        System.out.println(template);
    }
}
