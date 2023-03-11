package com.manish.moneytransfer;

import java.util.Objects;

public class MoneyTransferService {
    public static final int TRANSFER_AMOUNT_SHOULD_GREATER_THAN = 0;

    public boolean transferMoney(Account firstAccount,
                                 Account secondAccount,
                                 double amountToTransfer) {
        if (Objects.isNull(firstAccount) || Objects.isNull(secondAccount)) {
            throw new IllegalArgumentException("Accounts Shouldn't Be Null");
        }

        if (amountToTransfer <= TRANSFER_AMOUNT_SHOULD_GREATER_THAN) {
            throw new IllegalArgumentException("Transfer Amount Should Be Greater Than Zero");
        }

        firstAccount.setAmount(firstAccount.getAmount() - amountToTransfer);
        secondAccount.setAmount(secondAccount.getAmount() + amountToTransfer);
        return true;
    }
}
