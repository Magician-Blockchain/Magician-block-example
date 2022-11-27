package com.block.test.contracts;

import com.blockchain.scanning.commons.codec.EthAbiCodec;
import com.blockchain.tools.eth.codec.EthAbiCodecTool;
import com.blockchain.tools.eth.contract.template.ERC1155Contract;
import com.blockchain.tools.eth.contract.template.ERC721Contract;
import com.blockchain.tools.eth.contract.util.EthContractUtil;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;

public class TestERC721 {

    public static void main(String[] args) {
        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-2-s1.binance.org:8545"));
        String contractAddress = "";

        ERC721Contract erc721Contract = ERC721Contract.builder(web3j, contractAddress);
        try {
            BigInteger amount = erc721Contract.balanceOf("");

            String ownerAddress = erc721Contract.ownerOf(new BigInteger("1002"));

            erc721Contract.isApprovedForAll("","");

            String approvedAddress = erc721Contract.getApproved(new BigInteger("1002"));

            erc721Contract.approve(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1002"),
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "",
                    null,
                    null
            );

            erc721Contract.transferFrom(
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1002"),
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    "",
                    null,
                    null
            );

            erc721Contract.safeTransferFrom(
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    new BigInteger("1002"),
                    new byte[0],
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    "",
                    null,
                    null
            );

            erc721Contract.setApprovalForAll(
                    "0x552115849813d334C58f2757037F68E2963C4c5e", // 被授权人
                    true, // 是否授权全部
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84", // 调用者的地址
                    "", // 调用者的私钥
                    null, // gasPrice，如果传null，自动使用默认值
                    null // gasLimit，如果传null，自动使用默认值
            );
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
