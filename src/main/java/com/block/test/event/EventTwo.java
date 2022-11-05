package com.block.test.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.token.ERC20;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;
import com.blockchain.web3.MagicianWeb3;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.List;

/**
 * Listens for authorization events within the specified contract
 */
public class EventTwo implements EthMonitorEvent {
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setToAddress("")
                .setFunctionCode(ERC20.APPROVE.getFunctionCode());
    }

    @Override
    public void call(TransactionModel transactionModel) {
        String template = "EventTwo 扫描到了, hash:{0}, from:{1}, to: {2}, input: {3}";
        template = template.replace("{0}", transactionModel.getEthTransactionModel().getBlockHash());
        template = template.replace("{1}", transactionModel.getEthTransactionModel().getFrom());
        template = template.replace("{2}", transactionModel.getEthTransactionModel().getTo());
        template = template.replace("{3}", transactionModel.getEthTransactionModel().getInput());

        List<Type> result = MagicianWeb3.getEthBuilder().getEthAbiCodec().decoderInputData(
                "0x" + transactionModel.getEthTransactionModel().getInput().substring(10),
                new TypeReference<Address>() {},
                new TypeReference<Uint256>() {}
        );

        System.out.println(template + "---" + result.get(0).getValue().toString() + "|" + result.get(1).getValue().toString());
    }
}
