package com.block.test.retry;

import com.blockchain.scanning.chain.RetryStrategy;

import java.math.BigInteger;

public class EthRetry implements RetryStrategy {

    @Override
    public void retry(BigInteger bigInteger) {
        System.out.println("哈哈哈哈哈哈哈哈" + bigInteger);
    }
}
