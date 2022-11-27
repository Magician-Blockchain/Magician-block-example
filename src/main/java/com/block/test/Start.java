package com.block.test;

import com.block.test.event.EventOne;
import com.block.test.event.EventThree;
import com.block.test.retry.EthRetry;
import com.blockchain.scanning.MagicianBlockchainScan;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.config.rpcinit.impl.EthRpcInit;
import com.blockchain.scanning.commons.enums.BlockEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class Start {

    private static Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
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
//                    .setBeginBlockNumber(BigInteger.valueOf(24833912))
                    .setBeginBlockNumber(BlockEnums.LAST_BLOCK_NUMBER.getValue())
                    .addEthMonitorEvent(new EventOne())
                    .addEthMonitorEvent(new EventThree())
                    .setRetryStrategy(new EthRetry())
                    .start();

        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
    }
}
