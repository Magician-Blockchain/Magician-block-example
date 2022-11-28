package com.block.test.contracts;

import com.blockchain.tools.eth.contract.template.ERC20Contract;
import com.blockchain.tools.eth.contract.util.model.SendModel;
import com.blockchain.tools.eth.contract.util.model.SendResultModel;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestERC20 {

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("1000000000000000000");

        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-2-s1.binance.org:8545"));

        ERC20Contract erc20Contract = ERC20Contract.builder(web3j, "0x428862f821b1A5eFff5B258583572451229eEeA6");

        try {

            BigInteger total = erc20Contract.totalSupply();
            System.out.println(new BigDecimal(total).divide(decimal, 2, BigDecimal.ROUND_UP));

            BigInteger amount = erc20Contract.balanceOf("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84");
            System.out.println(new BigDecimal(amount).divide(decimal, 2, BigDecimal.ROUND_UP));


            SendResultModel sendResultModel = erc20Contract.transfer(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1000000000000000000"),
                    SendModel.builder()
                            .setSenderAddress("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84")
                            .setPrivateKey("")
                            .setToAddress("0x428862f821b1A5eFff5B258583572451229eEeA6")
                            .setValue(new BigInteger("1000000000"))
                            .setGasPrice(new BigInteger("1000"))
                            .setGasLimit(new BigInteger("800000"))

                    );
            System.out.println(sendResultModel.getEthGetTransactionReceipt());


            sendResultModel = erc20Contract.approve(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1000000000000000000"),
                    SendModel.builder()
                            .setSenderAddress("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84")
                            .setPrivateKey("")
            );

            System.out.println(sendResultModel.getEthGetTransactionReceipt());

            sendResultModel = erc20Contract.transferFrom(
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1000000000000000000"),
                    SendModel.builder()
                            .setSenderAddress("0x552115849813d334C58f2757037F68E2963C4c5e")
                            .setPrivateKey("")
            );

            System.out.println(sendResultModel.getEthGetTransactionReceipt());


            amount = erc20Contract.balanceOf("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84");
            System.out.println(new BigDecimal(amount).divide(decimal, 2, BigDecimal.ROUND_UP));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
