package com.block.test.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.token.ERC20;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;
import com.blockchain.scanning.monitor.filter.InputDataFilter;
import com.blockchain.web3.MagicianWeb3;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.List;

/**
 * Listening for transactions received at the specified address
 *
 * Sending coins to this address will trigger this event, and if this address is a contract address, then any changes to the data in this contract will trigger this event
 */
public class EventOne implements EthMonitorEvent {

    /**
     * 监控：合约[0x428862f821b1A5eFff5B258583572451229eEeA6]的transfer函数被调用了，
     * 并且函数的第一个参数是[0x552115849813d334C58f2757037F68E2963C4c5e] 的交易事件
     * @return
     */
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setToAddress("0x428862f821b1A5eFff5B258583572451229eEeA6")
                .setInputDataFilter(
                        InputDataFilter.create()
                                .setFunctionCode(ERC20.TRANSFER.getFunctionCode())
                                .setTypeReferences(
                                        new TypeReference<Address>(){},
                                        new TypeReference<Uint256>(){}
                                )
                                .setValue("0x552115849813d334C58f2757037F68E2963C4c5e", null)
                );
    }

    @Override
    public void call(TransactionModel transactionModel) {
        String template = "EventOne 扫描到了, hash:{0}, from:{1}, to: {2}, input: {3}";
        template = template.replace("{0}", transactionModel.getEthTransactionModel().getBlockHash());
        template = template.replace("{1}", transactionModel.getEthTransactionModel().getFrom());
        template = template.replace("{2}", transactionModel.getEthTransactionModel().getTo());
        template = template.replace("{3}", transactionModel.getEthTransactionModel().getInput());

        System.out.println(template);

        List<Type> result =  MagicianWeb3.getEthBuilder().getEthAbiCodec().decoderInputData(
                "0x" + transactionModel.getEthTransactionModel().getInput().substring(10),
                new TypeReference<Address>(){},
                new TypeReference<Uint256>(){});

        for(Type ty : result){
            System.out.println(ty.getValue());
        }
    }
}
