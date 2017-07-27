package com.weekendesk.anki.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Deck {

    private Set<Card> cards;

    public Deck(Set<Card> cards) {
        this.cards = cards;
    }

    public static Deck emptyDeck() {
        return new Deck(new HashSet<>());
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void remove(Card card) {
        this.cards.remove(card);
    }
}
