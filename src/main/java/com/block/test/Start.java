package com.block.test;

import com.block.test.event.EventOne;
import com.block.test.event.EventThree;
import com.block.test.retry.EthRetry;
import com.blockchain.scanning.MagicianBlockchainScan;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.rpcinit.impl.EthRpcInit;
import com.blockchain.web3.MagicianWeb3;
import com.blockchain.web3.eth.codec.EthAbiCodec;
import com.blockchain.web3.eth.contract.EthContract;
import com.blockchain.web3.eth.contract.model.SendResultModel;
import com.blockchain.web3.eth.helper.EthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Start {

    private static Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        scanningExample();

//        web3Example();
    }

    /**
     *
     */
    private static void scanningExample() {
        try {
            EventThreadPool.init(2);

            MagicianBlockchainScan.create()
                    .setRpcUrl(
                            EthRpcInit.create()
                                    .addRpcUrl("https://data-seed-prebsc-1-s1.binance.org:8545")
                                    .addRpcUrl("https://data-seed-prebsc-2-s1.binance.org:8545")
                                    .addRpcUrl("https://data-seed-prebsc-1-s2.binance.org:8545")
                    )
                    .setScanPeriod(500)
//                    .setBeginBlockNumber(BigInteger.valueOf(24836912))
                    .setBeginBlockNumber(BlockEnums.LAST_BLOCK_NUMBER.getValue())
                    .addEthMonitorEvent(new EventOne())
                    .addEthMonitorEvent(new EventThree())
                    .setRetryStrategy(new EthRetry())
                    .start();

        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
    }

    private static void web3Example(){
        try {
            String fromAddress = "";
            String toAddress = "";
            String contractAddress = "";
            String fromAddressPrivateKey = "";
            Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-1-s1.binance.org:8545/"));


            EthHelper ethHelper =  MagicianWeb3.getEthBuilder().getEth(web3j);

            System.out.println(ethHelper.balanceOf(fromAddress));

            ethHelper.transfer(
                    toAddress,
                    fromAddressPrivateKey,
                    BigDecimal.valueOf(1),
                    Convert.Unit.ETHER);

            System.out.println(ethHelper.balanceOf(fromAddress));

            // -----------------------------------------------------------------------------------------------

            EthContract ethContract = MagicianWeb3.getEthBuilder().getEthContract(web3j);
            EthAbiCodec ethAbiCodec = MagicianWeb3.getEthBuilder().getEthAbiCodec();

            List<Type> result =  ethContract.select(
                    contractAddress,
                    ethAbiCodec.getInputData(
                            "balanceOf",
                            new Address(toAddress)
                    ),
                    new TypeReference<Uint256>() {}
            );

            System.out.println(result.get(0).getValue());

            SendResultModel sendResultModel = ethContract.sendRawTransaction(
                    fromAddress,
                    contractAddress,
                    fromAddressPrivateKey,
                    ethAbiCodec.getInputData(
                            "transfer",
                            new Address(toAddress),
                            new Uint256(new BigInteger("1000000000000000000"))
                    )
            );

            sendResultModel.getEthSendTransaction();
            sendResultModel.getEthGetTransactionReceipt();

            result =  ethContract.select(
                    contractAddress,
                    ethAbiCodec.getInputData(
                            "balanceOf",
                            new Address(toAddress)
                    ),
                    new TypeReference<Uint256>() {}
            );

            System.out.println(result.get(0).getValue());
        } catch (Exception e){
            logger.error("Web3 Exception", e);
        }
    }
}
