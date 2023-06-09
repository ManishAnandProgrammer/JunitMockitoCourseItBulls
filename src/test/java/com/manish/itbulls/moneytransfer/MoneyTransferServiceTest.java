package com.manish.itbulls.moneytransfer;

import com.manish.itbulls.exceptions.NotEnoughAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money Transfer Service Tests")
class MoneyTransferServiceTest {

    @Test
    @DisplayName("Test If We Are Able To Create Object Of MoneyTransferService")
    void testObjectCreation() {
        MoneyTransferService moneyTransferService = new MoneyTransferService();
        Assertions.assertNotNull(moneyTransferService);
    }

    @Test
    @DisplayName("On Successfully Money Transfer transferMoney Method Should Return True")
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
    @DisplayName("Transferred Money Should Add In To-Account And Subtract From From-Account")
    void whenSendingMoneyFromOneAccountToAnotherItShouldChangeBalanceInBothAccounts(final int balanceInFromAccount,
                                                                                    final int balanceInToAccount,
                                                                                    final double amountOfMoneyTransfer) {
        final Account fromAccount = new Account();
        fromAccount.setAmount(balanceInFromAccount);

        final Account toAccount = new Account();
        toAccount.setAmount(balanceInToAccount);

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        moneyTransferService.transferMoney(fromAccount, toAccount, amountOfMoneyTransfer);

        final double afterTransferAmountInFromAccount = fromAccount.getAmount();
        final double afterTransferAmountInFromAccountMustBe = balanceInFromAccount - amountOfMoneyTransfer;

        final double afterTransferAmountInToAccount = toAccount.getAmount();
        final double afterTransferAmountInToAccountMustBe = balanceInToAccount + amountOfMoneyTransfer;

        assertAll("Money Transfer",
            () -> assertEquals(afterTransferAmountInFromAccountMustBe, afterTransferAmountInFromAccount),
            () -> assertEquals(afterTransferAmountInToAccountMustBe, afterTransferAmountInToAccount)
        );
    }

    private static Stream<Arguments> moneyTransferArguments() {
        return Stream.of(
            Arguments.of(100, 100, 50),
            Arguments.of(200, 100, 70),
            Arguments.of(70, 20, 20)
        );
    }

    @Test
    @DisplayName("If From-Account Is Null IllegalArgumentException Should Thrown")
    void whenFromAccountIsNullItShouldThrowIllegalArgumentException() {
        final Account secondAccount = new Account();

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
            moneyTransferService.transferMoney(null, secondAccount, 100)
        );
        final String actualExceptionMessage = illegalArgumentException.getMessage();
        final String expectedExceptionMessage = "Accounts Shouldn't Be Null";
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    @DisplayName("If To-Account Is Null IllegalArgumentException Should Thrown")
    void whenToAccountIsNullItShouldThrowIllegalArgumentException() {
        final Account fromAccount = new Account();

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
                moneyTransferService.transferMoney(fromAccount, null, 100)
        );

        final String actualExceptionMessage = illegalArgumentException.getMessage();
        final String expectedExceptionMessage = "Accounts Shouldn't Be Null";
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -323})
    @DisplayName("On Zero Or Negative Transfer Value IllegalArgumentException Should Thrown")
    void whenTransferValueIsLessThanOrEqualToZeroItShouldThrowIllegalArgumentException(final double amountToTransfer) {
        final Account fromAccount = new Account();
        fromAccount.setAmount(10);

        final Account toAccount = new Account();
        toAccount.setAmount(10);

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
            moneyTransferService.transferMoney(fromAccount, toAccount, amountToTransfer)
        );

        final String actualExceptionMessage = illegalArgumentException.getMessage();
        final String expectedExceptionMessage = "Transfer Amount Should Be Greater Than Zero";
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @ParameterizedTest
    @MethodSource("moneyTransferNotEnoughAmountArguments")
    @DisplayName("If Amount To Transfer Is Greater Than Balance In From-Account Should Throw NotEnoughAmountException")
    void ifNotEnoughBalanceInFromAccountToTransferThanShouldThrowNotEnoughAmountException(
            final int balanceInFromAccount,
            final int balanceInToAccount,
            final double amountOfMoneyTransfer
    ) {
        final Account fromAccount = new Account();
        fromAccount.setAmount(balanceInFromAccount);

        final Account toAccount = new Account();
        toAccount.setAmount(balanceInToAccount);

        final MoneyTransferService moneyTransferService = new MoneyTransferService();
        final NotEnoughAmountException notEnoughAmountException = assertThrows(NotEnoughAmountException.class, () ->
            moneyTransferService.transferMoney(fromAccount, toAccount, amountOfMoneyTransfer)
        );

        final String actualExceptionMessage = notEnoughAmountException.getMessage();
        final String expectedExceptionMessage = "Doesn't Have Enough Balance To Transfer";
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    private static Stream<Arguments> moneyTransferNotEnoughAmountArguments() {
        return Stream.of(
            Arguments.of(100, 100, 120),
            Arguments.of(200, 100, 201),
            Arguments.of(70, 20, 100)
        );
    }

}
