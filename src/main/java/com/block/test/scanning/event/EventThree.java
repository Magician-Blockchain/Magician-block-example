package com.block.test.scanning.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;

/**
 * Listens for transactions sent from the specified address
 */
public class EventThree implements EthMonitorEvent {

    /**
     * 监控：地址[0x552115849813d334C58f2757037F68E2963C4c5e]收到了币的 交易事件
     * @return
     */
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setToAddress("0x552115849813d334C58f2757037F68E2963C4c5e");
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
