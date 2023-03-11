package com.manish.moneytransfer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoneyTransferServiceTest {

    @Test
    void testObjectCreation() {
        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Assertions.assertNotNull(moneyTransferService);
    }

    @Test
    void whenSendingMoneyFromOneAccountToAnotherItShouldReturnTrue() {
        Account firstAccount = new Account();
        firstAccount.setAmount(100);

        Account secondAccount = new Account();
        secondAccount.setAmount(100);

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        boolean actualResult = moneyTransferService.transferMoney(firstAccount, secondAccount, 50.0);

        Assertions.assertTrue(actualResult);
    }

}
