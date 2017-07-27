package com.weekendesk.anki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {

    private Map<Box, List<Card>> boxes;
    private final Deck deck;
    private int deckInitialCounter;

    public Session(Deck deck) {
        this.boxes = new HashMap<>();
        this.deck = deck;
        this.deckInitialCounter = deck.getCards().size();
    }

    public void place(Card card, Box toBox) {
        List<Card> cards = getCardsFrom(toBox);

        cards.add(card);

        boxes.put(toBox, cards);
    }

    public List<Card> getCardsFrom(Box box) {
        return new ArrayList<>( boxes.getOrDefault(box, new ArrayList<>()) );
    }

    public List<Card> getCardsFromDeck() {
        return new ArrayList<>( deck.getCards() );
    }

    public void removeFromDeck(Card card) {
        deck.remove(card);
    }

    public boolean isSuccessful() {
        return getCardsFrom(Box.GREEN).size() == deckInitialCounter;
    }
}
