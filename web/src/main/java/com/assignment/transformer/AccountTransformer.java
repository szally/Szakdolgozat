package com.assignment.transformer;

import com.assignment.domain.Account;
import com.assignment.model.AccountModel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@NoArgsConstructor
public class AccountTransformer {

    public List<AccountModel> transformAccountListToAccountModelList(List<Account> accounts){
        List<AccountModel> accountModels = new ArrayList<>();
        Iterator iterator = accounts.iterator();

        while (iterator.hasNext()){
            Account account= (Account) iterator.next();
            accountModels.add(transformAccountToAccountModel(account));
        }

        return accountModels;
    }

    public AccountModel transformAccountToAccountModel(Account account) {
        AccountModel accountModel = new AccountModel();

        if(!account.equals(null)){
            accountModel.setBalance(account.getBalance());
            accountModel.setCurrency(account.getCurrency());
            accountModel.setOpeningDate(account.getOpeningDate());
            accountModel.setStatus(account.getStatus());
            accountModel.setType(account.getType());
            accountModel.setIban(account.getIban());
            accountModel.setCustomer(account.getCustomer());
            accountModel.setCard(account.getCard());
            accountModel.setTransactionsList(account.getTransactionsList());

            if(account.getId() != null){
                accountModel.setId(account.getId());
            }
        }

        return accountModel;
    }

    public Account transformAccountModelToAccount(AccountModel accountModel){
        Account account = new Account();

        if(!accountModel.equals(null)){

            account.setBalance(accountModel.getBalance());
            account.setCurrency(accountModel.getCurrency());
            account.setOpeningDate(accountModel.getOpeningDate());
            account.setStatus(accountModel.getStatus());
            account.setType(accountModel.getType());
            account.setIban(accountModel.getIban());
            account.setCustomer(accountModel.getCustomer());
            account.setCard(accountModel.getCard());
            account.setTransactionsList(accountModel.getTransactionsList());

            if(accountModel.getId() != null){
                account.setId(accountModel.getId());
            }
        }

        return account;
    }
}
