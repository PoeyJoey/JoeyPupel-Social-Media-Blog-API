package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        //username is not blank
        //password is at least 4 characters
        if (account.getUsername() != "" && account.getPassword().length() >= 4){
            //account with that username does not exist
            if (accountDAO.getAccountByUsername(account.getUsername()) == null){
                return accountDAO.registerAccount(account);
            }
        }
        return null;
        
    }

    public Account loginAccount (Account account) {
        return accountDAO.loginAccount(account);
    }

    public Account checkAccountExists(int userID) {
        return accountDAO.getAccountByID(userID);
    }
}
