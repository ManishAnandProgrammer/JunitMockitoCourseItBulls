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
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTransferServiceTest {

    @Test
    void testObjectCreation() {
        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Assertions.assertNotNull(moneyTransferService);
    }

    @Test
    void whenSendingMoneyFromOneAccountToAnotherItShouldReturnTrue() {
        final Account fromAccount = new Account();
        final int balanceInFirstAccount = 100;
        fromAccount.setAmount(balanceInFirstAccount);

        final Account toAccount = new Account();
        final int balanceInSecondAccount = 100;
        toAccount.setAmount(balanceInSecondAccount);

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        final double amountOfMoneyTransfer = 50.0;
        final boolean actualResult =
                moneyTransferService.transferMoney(fromAccount, toAccount, amountOfMoneyTransfer);

        assertTrue(actualResult);
    }

    @ParameterizedTest
    @MethodSource("moneyTransferArguments")
    void whenSendingMoneyFromOneAccountToAnotherItShouldChangeBalanceInBothAccounts(final int balanceInFirstAccount,
                                                                                    final int balanceInSecondAccount,
                                                                                    final double amountOfMoneyTransfer) {
        final Account fromAccount = new Account();
        fromAccount.setAmount(balanceInFirstAccount);

        final Account toAccount = new Account();
        toAccount.setAmount(balanceInSecondAccount);

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        moneyTransferService.transferMoney(fromAccount, toAccount, amountOfMoneyTransfer);

        final double afterTransferAmountInFromAccount = fromAccount.getAmount();
        final double afterTransferAmountInFromAccountMustBe = balanceInFirstAccount - amountOfMoneyTransfer;

        Assertions.assertEquals(afterTransferAmountInFromAccountMustBe, afterTransferAmountInFromAccount);

        final double afterTransferAmountInToAccount = toAccount.getAmount();
        final double afterTransferAmountInToAccountMustBe = balanceInSecondAccount + amountOfMoneyTransfer;

        Assertions.assertEquals(afterTransferAmountInToAccountMustBe, afterTransferAmountInToAccount);
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
        final Account secondAccount = new Account();

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
            moneyTransferService.transferMoney(null, secondAccount, 100)
        );
        final String actualExceptionMessage = illegalArgumentException.getMessage();
        final String expectedExceptionMessage = "Accounts Shouldn't Be Null";
        Assertions.assertEquals(expectedExceptionMessage, actualExceptionMessage);
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
