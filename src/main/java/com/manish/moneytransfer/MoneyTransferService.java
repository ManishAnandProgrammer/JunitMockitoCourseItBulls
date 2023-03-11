package com.manish.moneytransfer;

import com.manish.exceptions.NotEnoughAmountException;

import java.util.Objects;

public class MoneyTransferService {
    public static final int TRANSFER_AMOUNT_SHOULD_GREATER_THAN = 0;

    public boolean transferMoney(final Account fromAccount,
                                 final Account toAccount,
                                 final double amountToTransfer) {
        if (Objects.isNull(fromAccount) || Objects.isNull(toAccount)) {
            throw new IllegalArgumentException("Accounts Shouldn't Be Null");
        }

        if (amountToTransfer <= TRANSFER_AMOUNT_SHOULD_GREATER_THAN) {
            throw new IllegalArgumentException("Transfer Amount Should Be Greater Than Zero");
        }

        if (fromAccount.getAmount() < amountToTransfer) {
            throw new NotEnoughAmountException("Doesn't Have Enough Balance To Transfer");
        }

        fromAccount.setAmount(fromAccount.getAmount() - amountToTransfer);
        toAccount.setAmount(toAccount.getAmount() + amountToTransfer);
        return true;
    }
}
