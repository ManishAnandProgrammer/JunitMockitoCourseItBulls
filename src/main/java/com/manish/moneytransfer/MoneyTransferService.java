package com.manish.moneytransfer;

import java.util.Objects;

public class MoneyTransferService {
    public boolean transferMoney(Account firstAccount,
                                 Account secondAccount,
                                 double amountToTransfer) {
        if (Objects.isNull(firstAccount) || Objects.isNull(secondAccount)) {
            throw new IllegalArgumentException("Accounts Can't Be Null");
        }
        firstAccount.setAmount(firstAccount.getAmount() - amountToTransfer);
        secondAccount.setAmount(secondAccount.getAmount() + amountToTransfer);
        return true;
    }
}
