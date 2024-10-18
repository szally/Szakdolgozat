package com.assignment.transformer;

import com.assignment.domain.Card;
import com.assignment.domain.Transactions;
import com.assignment.model.CardModel;
import com.assignment.model.TransactionsModel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@NoArgsConstructor
public class TransactionTransformer {
    public List<TransactionsModel> transformTransactionsListToTransactionsModelList(List<Transactions> transactions){
        List<TransactionsModel> transactionsModelList = new ArrayList<>();
        Iterator iterator = transactions.iterator();

        while (iterator.hasNext()){
            Transactions transaction= (Transactions) iterator.next();
            transactionsModelList.add(transformTransactionsToTransactionsModel(transaction));
        }

        return transactionsModelList;
    }

    public TransactionsModel transformTransactionsToTransactionsModel(Transactions transactions) {
        TransactionsModel transactionsModel = new TransactionsModel();

        if(!transactionsModel.equals(null)){
            transactionsModel.setAmount(transactions.getAmount());
            transactionsModel.setCurrency(transactions.getCurrency());
            transactionsModel.setCreationDate(transactions.getCreationDate());
            transactionsModel.setBookingDate(transactions.getBookingDate());
            transactionsModel.setPartnerName(transactions.getPartnerName());
            transactionsModel.setPartnerAccountNumb(transactions.getPartnerAccountNumb());
            transactionsModel.setDescription(transactions.getDescription());
            transactionsModel.setStatus(transactions.getStatus());
            transactionsModel.setAccount(transactions.getAccount());
            transactionsModel.setCustomer(transactions.getCustomer());

            if(transactions.getId() != null){
                transactionsModel.setId(transactions.getId());
            }
        }

        return transactionsModel;
    }

    public Transactions transformTransactionsModelToTransactions(TransactionsModel transactionsModel){
        Transactions transactions = new Transactions();

        if(!transactions.equals(null)){
            transactions.setAmount(transactionsModel.getAmount());
            transactions.setCurrency(transactionsModel.getCurrency());
            transactions.setCreationDate(transactionsModel.getCreationDate());
            transactions.setBookingDate(transactionsModel.getBookingDate());
            transactions.setPartnerName(transactionsModel.getPartnerName());
            transactions.setPartnerAccountNumb(transactionsModel.getPartnerAccountNumb());
            transactions.setDescription(transactionsModel.getDescription());
            transactions.setStatus(transactionsModel.getStatus());
            transactions.setAccount(transactionsModel.getAccount());
            transactions.setCustomer(transactionsModel.getCustomer());

            if(transactionsModel.getId() != null){
                transactions.setId(transactionsModel.getId());
            }
        }

        return transactions;
    }
}
