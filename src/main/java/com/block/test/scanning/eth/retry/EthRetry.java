package com.block.test.scanning.eth.retry;

import com.blockchain.scanning.chain.RetryStrategy;

import java.math.BigInteger;

public class EthRetry implements RetryStrategy {

    @Override
    public void retry(BigInteger bigInteger) {
        System.out.println("ETH 重试策略" + bigInteger);
    }
}
