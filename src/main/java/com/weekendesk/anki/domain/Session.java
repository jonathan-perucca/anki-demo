package com.weekendesk.anki.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Session {

    private Map<Box, List<Card>> boxes;

    public Session(Map<Box, List<Card>> boxes) {
        this.boxes = boxes;
    }

    public void place(Card card, Box box) {
        List<Card> cards = getCardsFrom(box);

        cards.add(card);

        boxes.put(box, cards);
    }

    public List<Card> getCardsFrom(Box box) {
        return boxes.getOrDefault(box, new ArrayList<>());
    }
}
