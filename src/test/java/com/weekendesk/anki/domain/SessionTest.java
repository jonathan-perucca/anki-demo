package com.weekendesk.anki.domain;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    private Session session = new Session(new HashMap<>());

    /**
     * 4 following tests could have been done with Parameterized unit test
     */
    @Test
    public void should_add_card_to_greenBox() {
        Card cardToPlace = Card.builder().build();

        session.place(cardToPlace, Box.GREEN);

        List<Card> greenCards = session.getCardsFrom(Box.GREEN);
        assertThat(greenCards).contains(cardToPlace);
    }

    @Test
    public void should_add_card_to_redBox() {
        Card cardToPlace = Card.builder().build();

        session.place(cardToPlace, Box.RED);

        List<Card> redCards = session.getCardsFrom(Box.RED);
        assertThat(redCards).contains(cardToPlace);
    }

    @Test
    public void should_add_card_to_orangeBox() {
        Card cardToPlace = Card.builder().build();

        session.place(cardToPlace, Box.ORANGE);

        List<Card> orangeCards = session.getCardsFrom(Box.ORANGE);
        assertThat(orangeCards).contains(cardToPlace);
    }

    @Test
    public void should_add_card_to_notPlayedBox() {
        Card cardToPlace = Card.builder().build();

        session.place(cardToPlace, Box.NOT_PLAYED);

        List<Card> notPlayedCards = session.getCardsFrom(Box.NOT_PLAYED);
        assertThat(notPlayedCards).contains(cardToPlace);
    }
}