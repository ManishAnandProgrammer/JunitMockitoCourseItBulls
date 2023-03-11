package com.manish.moneytransfer;

public class MoneyTransferService {
    public boolean transferMoney(Account firstAccount,
                                 Account secondAccount,
                                 double amountToTransfer) {
        firstAccount.setAmount(firstAccount.getAmount() - amountToTransfer);
        secondAccount.setAmount(secondAccount.getAmount() + amountToTransfer);
        return true;
    }
}
