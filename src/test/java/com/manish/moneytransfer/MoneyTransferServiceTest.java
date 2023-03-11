package com.manish.moneytransfer;

import com.manish.exceptions.NotEnoughAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
            moneyTransferService.transferMoney(null, secondAccount, 100)
        );
        String message = illegalArgumentException.getMessage();
        Assertions.assertEquals("Accounts Shouldn't Be Null", message);
    }

    @Test
    void whenSecondAccountIsNullItShouldThrowIllegalArgumentException() {
        Account firstAccount = new Account();

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                moneyTransferService.transferMoney(firstAccount, null, 100)
        );

        String message = illegalArgumentException.getMessage();
        Assertions.assertEquals("Accounts Shouldn't Be Null", message);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -323})
    void whenTransferValueIsLessThanOrEqualToZeroItShouldThrowIllegalArgumentException(double amountToTransfer) {
        Account firstAccount = new Account();
        firstAccount.setAmount(10);

        Account secondAccount = new Account();
        secondAccount.setAmount(10);

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
            moneyTransferService.transferMoney(firstAccount, secondAccount, amountToTransfer)
        );

        String message = illegalArgumentException.getMessage();
        Assertions.assertEquals("Transfer Amount Should Be Greater Than Zero", message);
    }

    @ParameterizedTest
    @MethodSource("moneyTransferNotEnoughAmountArguments")
    void ifNotEnoughBalanceInFirstAccountToTransferToSecondAccountThanShouldThrowNotEnoughAmountException(
            int balanceInFirstAccount, int balanceInSecondAccount, double amountOfMoneyTransfer
    ) {
        Account firstAccount = new Account();
        firstAccount.setAmount(balanceInFirstAccount);

        Account secondAccount = new Account();
        secondAccount.setAmount(balanceInSecondAccount);

        MoneyTransferService moneyTransferService = new MoneyTransferService();

        NotEnoughAmountException notEnoughAmountException = assertThrows(NotEnoughAmountException.class, () ->
            moneyTransferService.transferMoney(firstAccount, secondAccount, amountOfMoneyTransfer)
        );

        String message = notEnoughAmountException.getMessage();
        Assertions.assertEquals("Doesn't Have Enough Balance To Transfer", message);
    }

    private static Stream<Arguments> moneyTransferNotEnoughAmountArguments() {
        return Stream.of(
            Arguments.of(100, 100, 120),
            Arguments.of(200, 100, 201),
            Arguments.of(70, 20, 100)
        );
    }

}
