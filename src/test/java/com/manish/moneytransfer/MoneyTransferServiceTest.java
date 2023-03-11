package com.manish.moneytransfer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MoneyTransferServiceTest {

    @Test
    void testObjectCreation() {
        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Assertions.assertNotNull(moneyTransferService);
    }

    @Test
    void whenSendingMoneyFromOneAccountToAnotherItShouldReturnTrue() {
        Account firstAccount = new Account();
        int balanceInFirstAccount = 100;
        firstAccount.setAmount(balanceInFirstAccount);

        Account secondAccount = new Account();
        int balanceInSecondAccount = 100;
        secondAccount.setAmount(balanceInSecondAccount);

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        double amountOfMoneyTransfer = 50.0;
        boolean actualResult = moneyTransferService.transferMoney(firstAccount, secondAccount, amountOfMoneyTransfer);

        Assertions.assertTrue(actualResult);
    }

    @ParameterizedTest
    @MethodSource("moneyTransferArguments")
    void whenSendingMoneyFromOneAccountToAnotherItShouldChangeBalanceInBothAccounts(int balanceInFirstAccount,
                                                                                    int balanceInSecondAccount,
                                                                                    double amountOfMoneyTransfer) {
        Account firstAccount = new Account();
        firstAccount.setAmount(balanceInFirstAccount);

        Account secondAccount = new Account();
        secondAccount.setAmount(balanceInSecondAccount);

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        moneyTransferService.transferMoney(firstAccount, secondAccount, amountOfMoneyTransfer);

        double afterTransferAmountInFirstAccount = firstAccount.getAmount();
        double afterTransferAmountInFirstAccountMustBe = balanceInFirstAccount - amountOfMoneyTransfer;

        Assertions.assertEquals(afterTransferAmountInFirstAccountMustBe, afterTransferAmountInFirstAccount);

        double afterTransferAmountInSecondAccount = secondAccount.getAmount();
        double afterTransferAmountInSecondAccountMustBe = balanceInSecondAccount + amountOfMoneyTransfer;

        Assertions.assertEquals(afterTransferAmountInSecondAccountMustBe, afterTransferAmountInSecondAccount);
    }

    private static Stream<Arguments> moneyTransferArguments() {
        return Stream.of(
            Arguments.of(100, 100, 50),
            Arguments.of(200, 100, 70),
            Arguments.of(70, 20, 20)
        );
    }

    @Test
    void whenFirstAccountIsNullItShouldThrowIllegalArgumentException() {
        Account secondAccount = new Account();

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            moneyTransferService.transferMoney(null, secondAccount, 100);
        });
    }
}
