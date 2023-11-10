package org.academiadecodigo.javabank;

import org.academiadecodigo.javabank.domain.Bank;
import org.academiadecodigo.javabank.domain.UserInterface;
import org.academiadecodigo.javabank.managers.AccountManager;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(new AccountManager());

        UserInterface UI = new UserInterface(bank);

    }


}
