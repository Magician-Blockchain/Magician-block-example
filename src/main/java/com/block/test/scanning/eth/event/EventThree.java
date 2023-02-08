package com.block.test.scanning.eth.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;
import org.web3j.protocol.core.methods.response.EthBlock;

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

        // 符合条件的交易记录
        EthBlock.TransactionObject transactionObject = transactionModel.getEthTransactionModel().getTransactionObject();

        // 本条交易记录所在的块信息
        EthBlock ethBlock = transactionModel.getEthTransactionModel().getEthBlock();


        String template = "EventThree 扫描到了, hash:{0}, hash:{1}, from:{2}, to: {3}, input: {4}";
        template = template.replace("{0}", transactionModel.getEthTransactionModel().getEthBlock().getBlock().getHash());
        template = template.replace("{1}", transactionObject.getHash());
        template = template.replace("{2}", transactionObject.getFrom());
        template = template.replace("{3}", transactionObject.getTo());
        template = template.replace("{4}", transactionObject.getInput());

        System.out.println(template);
    }
}
