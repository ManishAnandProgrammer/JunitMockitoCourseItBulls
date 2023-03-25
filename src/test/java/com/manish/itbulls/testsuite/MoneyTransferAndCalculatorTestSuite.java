package com.manish.itbulls.testsuite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.manish.calculator", "com.manish.moneytransfer"})
public class MoneyTransferAndCalculatorTestSuite {
}
