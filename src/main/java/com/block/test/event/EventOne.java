package com.block.test.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;

import java.math.BigInteger;

/**
 * Listening for transactions received at the specified address
 *
 * Sending coins to this address will trigger this event, and if this address is a contract address, then any changes to the data in this contract will trigger this event
 */
public class EventOne implements EthMonitorEvent {
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setFromAddress("")
                .setToAddress("")
                .setMinValue(BigInteger.valueOf(1))
                .setMaxValue(BigInteger.valueOf(10))
                .setFunctionCode("0xasdas123");
    }

    @Override
    public void call(TransactionModel transactionModel) {
        String template = "EventOne 扫描到了, hash:{0}, from:{1}, to: {2}, input: {3}";
        template = template.replace("{0}", transactionModel.getEthTransactionModel().getBlockHash());
        template = template.replace("{1}", transactionModel.getEthTransactionModel().getFrom());
        template = template.replace("{2}", transactionModel.getEthTransactionModel().getTo());
        template = template.replace("{3}", transactionModel.getEthTransactionModel().getInput());

        System.out.println(template);
    }
}
