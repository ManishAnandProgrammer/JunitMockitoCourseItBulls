package com.manish.itbulls.moneytransfer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void checkObjectCreation() {
        Account account = new Account();
        assertNotNull(account);
    }
}
