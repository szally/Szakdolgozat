package com.assignment.transformer;

import com.assignment.domain.Account;
import com.assignment.domain.PartnerBank;
import com.assignment.model.AccountModel;
import com.assignment.model.PartnerBankModel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PartnerBankTransformer {
    public PartnerBankModel transformPartnerBankPartnerBankModel(PartnerBank partnerBank) {
        PartnerBankModel partnerBankModel = new PartnerBankModel();

        if(!partnerBank.equals(null)){
            partnerBankModel.setName(partnerBank.getName());
            partnerBankModel.setSwiftCode(partnerBank.getSwiftCode());
            partnerBankModel.setTransferFeeInEUR(partnerBank.getTransferFeeInEUR());

            if(partnerBank.getId() != null){
                partnerBankModel.setId(partnerBank.getId());
            }
        }

        return partnerBankModel;
    }

    public PartnerBank transformPartnerBankModelToPartnerBank(PartnerBankModel partnerBankModel){
        PartnerBank partnerBank = new PartnerBank();

        if(!partnerBankModel.equals(null)){
            partnerBank.setName(partnerBankModel.getName());
            partnerBank.setSwiftCode(partnerBankModel.getSwiftCode());
            partnerBank.setTransferFeeInEUR(partnerBankModel.getTransferFeeInEUR());

            if(partnerBankModel.getId() != null){
                partnerBank.setId(partnerBankModel.getId());
            }
        }

        return partnerBank;
    }
}
