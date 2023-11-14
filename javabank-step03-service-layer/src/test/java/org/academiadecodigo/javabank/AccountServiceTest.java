package org.academiadecodigo.javabank;

import org.academiadecodigo.javabank.model.account.Account;
import org.academiadecodigo.javabank.model.account.CheckingAccount;
import org.academiadecodigo.javabank.model.account.SavingsAccount;
import org.academiadecodigo.javabank.services.AccountService;
import org.academiadecodigo.javabank.services.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import static org.junit.Assert.assertFalse;

public class AccountServiceTest {
    private AccountServiceImpl accountService = new AccountServiceImpl();
    private Account ac;
    private Account as;
    //private Map<Integer, Account> accountMap;

    @Before
    public void setUp() {
        ac = Mockito.mock(Account.class);
        System.out.println("acid" + ac.getId());
        as = Mockito.mock(Account.class);
        //  this.accountMap = Mockito.mock(Map.class);
    }

    @Test
    public void add() {
        accountService.add(ac);
        accountService.add(as);
        assertEquals(1, (int) accountService.getNextId());
        assertEquals(2, (int) accountService.getNextId());

    }

    @Test
    public void deposit() {
        accountService.add(ac);
        accountService.deposit(0, 10);
        assertEquals(10, (int) ac.getBalance());

    }

    public boolean test() {

        AccountService accountService = new AccountServiceImpl();
        Account ac = new CheckingAccount();
        Account as = new SavingsAccount();
        accountService.add(ac);
        accountService.add(as);

        // should add ids
        if (ac.getId() == null || as.getId() == null) {
            return false;
        }

        // should be able to deposit
        accountService.deposit(ac.getId(), 10);
        accountService.deposit(as.getId(), SavingsAccount.MIN_BALANCE + 10);
        if (ac.getBalance() != 10 || as.getBalance() != SavingsAccount.MIN_BALANCE + 10) {
            return false;
        }

        // should be able to withdraw
        accountService.withdraw(ac.getId(), 1);
        if (ac.getBalance() != 9) {
            return false;
        }

        // should not be able to withdraw
        accountService.withdraw(as.getId(), 30);
        if (as.getBalance() != 110) {
            return false;
        }


        // should be able to transfer if sufficient funds are available
        accountService.transfer(as.getId(), ac.getId(), 1);
        if (ac.getBalance() != 10 || as.getBalance() != SavingsAccount.MIN_BALANCE + 9) {
            return false;
        }

        // should not be able to transfer if available funds are not sufficient in savings
        accountService.transfer(as.getId(), ac.getId(), 10);
        if (ac.getBalance() != 10 || as.getBalance() != SavingsAccount.MIN_BALANCE + 9) {
            return false;
        }

        // should not be able to transfer if available funds are not sufficient in checking
        accountService.transfer(ac.getId(), as.getId(), 11);
        if (ac.getBalance() != 10 || as.getBalance() != SavingsAccount.MIN_BALANCE + 9) {
            return false;
        }

        return true;
    }
}
