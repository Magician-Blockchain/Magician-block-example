package com.block.test.contracts;

import com.blockchain.tools.eth.contract.template.ERC1155Contract;
import com.blockchain.tools.eth.contract.template.ERC20Contract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestERC1155 {

    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("1000000000000000000");

        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-2-s1.binance.org:8545"));

        ERC1155Contract erc1155Contract = ERC1155Contract.builder(web3j, "0xE36b2f499E90ba37b881F4f52043BCb309D2604f");

        try {
            List<String> address = new ArrayList<>();
            address.add("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84");
            address.add("0x552115849813d334C58f2757037F68E2963C4c5e");

            List<BigInteger> tokenId = new ArrayList<>();
            tokenId.add(new BigInteger("0"));
            tokenId.add(new BigInteger("0"));

            List<BigInteger> amounts = erc1155Contract.balanceOfBatch(address, tokenId);


            for(BigInteger amount : amounts){
                System.out.println(amount.toString());
            }

            BigInteger amount = erc1155Contract.balanceOf("0x552115849813d334C58f2757037F68E2963C4c5e", new BigInteger("0"));
            System.out.println(amount.toString());



            Boolean result = erc1155Contract.isApprovedForAll("0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84", "0x552115849813d334C58f2757037F68E2963C4c5e");


            erc1155Contract.setApprovalForAll(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    true,
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "",
                    null,
                    null
            );

            erc1155Contract.safeTransferFrom(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    new BigInteger("1002"),
                    new BigInteger("1"),
                    new byte[0],
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "",
                    null,
                    null
            );

            List<BigInteger> tokenIds = new ArrayList<>();
            tokenIds.add(new BigInteger("1002"));
            tokenIds.add(new BigInteger("1003"));

            List<BigInteger> amounts2 = new ArrayList<>();
            amounts2.add(new BigInteger("1"));
            amounts2.add(new BigInteger("10"));

            erc1155Contract.safeBatchTransferFrom(
                    "0x552115849813d334C58f2757037F68E2963C4c5e",
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    tokenIds,
                    amounts2,
                    new byte[0],
                    "0xb4e32492E9725c3215F1662Cf28Db1862ed1EE84",
                    "",
                    null,
                    null
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
