package org.academiadecodigo.javabank.domain;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.javabank.domain.account.Account;
import org.academiadecodigo.javabank.domain.account.AccountType;

import java.util.Map;

public class UserInterface {
    Bank bank;
    Prompt prompt = new Prompt(System.in, System.out);
    Customer customer = new Customer();


    public UserInterface(Bank bank) {
        this.bank = bank;
        bank.addCustomer(customer);
        System.out.println("|--------------------------|");
        System.out.println("| Welcome to our Bank!     |");
        menu();
    }

    /**
     * Presents 6 options for our costumer
     */
    private void menu() {
        boolean quit = false;

        String[] options = {"Open New Account", "View Balance", "Deposit", "Withdraw", "Check Account Info", "Quit"};

        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("| What do you wish to do ? |\n|--------------------------|");


        int answerIndex = prompt.getUserInput(scanner);
        System.out.println(answerIndex);
        switch (answerIndex) {
            case 1 -> customer.openAccount(chooseAccountType());
            case 2 -> getBalance();
            case 3 -> deposit();
            case 4 -> withdraw();
            case 5 -> seeAllAccounts();
            case 6 -> quit = true;
        }
        if (!quit) menu();
        System.out.println("Bye have a great time");
    }

    private AccountType chooseAccountType() {
        String[] options = {"Checking", "Savings"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Enter Account Type to open");

        int answerIndex = prompt.getUserInput(scanner);
        AccountType accountType = switch (answerIndex) {
            case 1 -> AccountType.CHECKING;
            case 2 -> AccountType.SAVINGS;
            default -> AccountType.CHECKING;
        };
        return accountType;
    }

    private void seeAllAccounts() {
        System.out.println("\n--------------------------------");
        for (int i = 0; i < customer.accounts.size(); i++) {
            Account account = customer.accounts.get(i + 1);
            if(account.getAccountType()==AccountType.CHECKING)
            System.out.println(" |ID: " + account.getId() + "  |Type: " + account.getAccountType() + "  | " + account.getBalance() + "$");
            else System.out.println(" |ID: " + account.getId() + "  |Type: " + account.getAccountType() + "   | " + account.getBalance() + "$");
        }
        System.out.println("--------------------------------");
    }

    private void noAccount_backToMenu() {
        if (customer.accounts.isEmpty()) {
            System.out.println("\nYou have no account associated to your Costumer account ");
            menu();
        }
    }

    public int selectAccountById(String string) {
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage(string);
        boolean found = false;
        Integer accountId = 0;
        while (!found) {
            accountId = prompt.getUserInput(scanner);
            for (int i = 0; i < customer.accounts.size(); i++) {
                Account account = customer.accounts.get(i + 1);
                if (account.getId() == accountId) {
                    found = true;
                }
            }
        }
        return accountId;
    }

    private void withdraw() {
        noAccount_backToMenu();
        seeAllAccounts();

        int index = selectAccountById("Select an account id to withdraw\n");
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("Insert an amount to withdraw\n");
        boolean validAmount = false;
        Integer amount = 0;
        while (!validAmount) {
            amount = prompt.getUserInput(scanner);
            if (amount > 0) validAmount = true;
        }
        bank.accountManager.withdraw(index, amount);
    }


    public void deposit() {
        noAccount_backToMenu();
        seeAllAccounts();

        int index = selectAccountById("Select an account id to deposit\n");
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("Insert an amount to deposit\n");

        boolean validAmount = false;
        Integer amount = 0;
        while (!validAmount) {
            amount = prompt.getUserInput(scanner);
            if (amount > 0) validAmount = true;
        }
        bank.accountManager.deposit(index, amount);
    }

    public void getBalance() {
        noAccount_backToMenu();

        System.out.println("You have " + customer.accounts.size() + " accounts associated with your costumer account\n" +
                "with " + getTotalCostumerBalance(customer.accounts) + "$ in total");

        int index = selectAccountById("Select an account id to see its balance\n");
        Account account = customer.accounts.get(index);
        System.out.println("You have " + account.getBalance() + "$ in your" + account.getAccountType() + " account number " + account.getId());
        waitUntilKeyPressed();
        menu();
    }



    public double getTotalCostumerBalance(Map<Integer, Account> accounts) {
        double sum = 0;
        for (int i = 0; i < accounts.size(); i++) {
            sum += accounts.get(i + 1).getBalance();
        }
        return sum;
    }

    public void waitUntilKeyPressed(){
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("MENU to go back\n");
        prompt.getUserInput(scanner);
    }
}
