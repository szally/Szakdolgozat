package com.assignment.transformer;


import com.assignment.domain.Card;
import com.assignment.model.CardModel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@NoArgsConstructor
public class CardTransformer {
    public List<CardModel> transformCardListToCardModelList(List<Card> cards){
        List<CardModel> cardModels = new ArrayList<>();
        Iterator iterator = cards.iterator();

        while (iterator.hasNext()){
            Card card= (Card) iterator.next();
            cardModels.add(transformCardToCardModel(card));
        }

        return cardModels;
    }

    public CardModel transformCardToCardModel(Card card) {
        CardModel cardModel = new CardModel();

        if(!cardModel.equals(null)){
            cardModel.setCustomerName(card.getCustomerName());
            cardModel.setExpiryDate(card.getExpiryDate());
            cardModel.setType(card.getType());
            cardModel.setStatus(card.getStatus());
            cardModel.setPinCode(card.getPinCode());
            cardModel.setCustomer(card.getCustomer());

            if(card.getId() != null){
                cardModel.setId(card.getId());
            }
        }

        return cardModel;
    }

    public Card transformCardModelToCard(CardModel cardModel){
        Card card = new Card();

        if(!card.equals(null)){
            card.setCustomerName(cardModel.getCustomerName());
            card.setExpiryDate(cardModel.getExpiryDate());
            card.setType(cardModel.getType());
            card.setStatus(cardModel.getStatus());
            card.setPinCode(cardModel.getPinCode());
            card.setCustomer(cardModel.getCustomer());

            if(cardModel.getId() != null){
                card.setId(cardModel.getId());
            }
        }

        return card;
    }
}
