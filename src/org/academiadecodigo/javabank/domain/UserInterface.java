package org.academiadecodigo.javabank;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerInputScanner;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.javabank.domain.Bank;
import org.academiadecodigo.javabank.domain.Customer;
import org.academiadecodigo.javabank.domain.account.AccountType;

public class UserInterface {
    Bank bank;
    Prompt prompt = new Prompt(System.in, System.out);
    Customer customer = new Customer();

    public UserInterface(Bank bank) {
        this.bank = bank;
        this.customer.setAccountManager(bank.accountManager);
        menu();
    }

    public void menu() {
        String[] options = {"Open New Account", "View Balance", "Deposit", "Withdraw", "Quit"};

        // create a menu with those options and set the message
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Welcome to our Bank\n" +
                "What do you wish to do ?");

        // show the menu to the user and get the selected answer
        int answerIndex = prompt.getUserInput(scanner);
        System.out.println(answerIndex);
        switch (answerIndex) {
            case 1 -> customer.openAccount(chooseAccountType());
            case 2 ->
            case 3 -> deposit();
            ;
            case 4 -> System.out.println();
            case 5 -> System.out.println();
        }
    }

    public AccountType chooseAccountType() {
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

    public void deposit(){
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("Insert an amount to deposit");
        Integer response = prompt.getUserInput(scanner);

    }
    public void getBalance() {

        System.out.println("Your total balance is " + customer.getBalance());
        IntegerInputScanner scanner = new IntegerInputScanner();
        scanner.setMessage("Select an Account to view its balance");
        Integer response = prompt.getUserInput(scanner);

    }

    public void getAccount() {
        

    }
}
