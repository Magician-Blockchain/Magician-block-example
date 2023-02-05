package com.block.test.scanning;

import com.block.test.scanning.eth.event.EventOne;
import com.block.test.scanning.eth.event.EventThree;
import com.block.test.scanning.eth.retry.EthRetry;
import com.block.test.scanning.tron.event.TronEventOne;
import com.block.test.scanning.tron.retry.TronRetry;
import com.blockchain.scanning.MagicianBlockchainScan;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.config.rpcinit.impl.EthRpcInit;
import com.blockchain.scanning.commons.config.rpcinit.impl.TronRpcInit;
import com.blockchain.scanning.commons.enums.BlockEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class Start {

    private static Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) throws InterruptedException {
        EventThreadPool.init(5);

        MagicianBlockchainScan magicianBlockchainScan1 = tron();
        MagicianBlockchainScan magicianBlockchainScan2 =  eth();

//        Thread.sleep(20000);
//        magicianBlockchainScan1.shutdown();
//        magicianBlockchainScan2.shutdown();
//        EventThreadPool.shutdown();
    }

    private static MagicianBlockchainScan tron(){
        try {
            MagicianBlockchainScan magicianBlockchainScan = MagicianBlockchainScan.create()
                    .setRpcUrl(
                            TronRpcInit.create()
                                    .addRpcUrl("https://api.shasta.trongrid.io/wallet")
                    )
                    .setScanPeriod(500)
//                    .setBeginBlockNumber(BigInteger.valueOf(31161000))
                    .setBeginBlockNumber(BlockEnums.LAST_BLOCK_NUMBER.getValue())
                    .addTronMonitorEvent(new TronEventOne())
                    .setRetryStrategy(new TronRetry());

            magicianBlockchainScan.start();

//            Thread.sleep(20000);
            logger.info("===========");
//            magicianBlockchainScan.shutdown();

            return magicianBlockchainScan;
        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
        return null;
    }

    private static MagicianBlockchainScan eth(){
        try {
            MagicianBlockchainScan magicianBlockchainScan = MagicianBlockchainScan.create()
                    .setRpcUrl(
                            EthRpcInit.create()
                                    .addRpcUrl("https://data-seed-prebsc-1-s1.binance.org:8545")
                                    .addRpcUrl("https://data-seed-prebsc-2-s1.binance.org:8545")
                                    .addRpcUrl("https://data-seed-prebsc-1-s2.binance.org:8545")
                    )
                    .setScanPeriod(500)
//                    .setBeginBlockNumber(BigInteger.valueOf(24833912))
                    .setBeginBlockNumber(BlockEnums.LAST_BLOCK_NUMBER.getValue())
                    .addEthMonitorEvent(new EventOne())
                    .addEthMonitorEvent(new EventThree())
                    .setRetryStrategy(new EthRetry());

            magicianBlockchainScan.start();

//            Thread.sleep(20000);
            logger.info("===========");
//            magicianBlockchainScan.shutdown();

            return magicianBlockchainScan;
        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
        return null;
    }
}
