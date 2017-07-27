package com.weekendesk.anki.domain;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.weekendesk.anki.domain.Box.GREEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SessionTest {

    private Session session = new Session( mock(Deck.class) );

    @Test
    public void should_add_card_to_greenBox() {
        Card cardToPlace = Card.builder().build();

        session.place(cardToPlace, GREEN);

        List<Card> greenCards = session.getCardsFrom(GREEN);
        assertThat(greenCards).contains(cardToPlace);
    }

    @Test
    public void should_remove_card_from_deck() {
        Card cardToRemove = Card.builder().build();
        setupSession(cardToRemove);

        session.removeFromDeck(cardToRemove);

        assertThat(session.getCardsFromDeck()).doesNotContain(cardToRemove);
    }

    private void setupSession(Card card) {
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        Deck deck = new Deck(cards);
        session = new Session( deck );
    }

    @Test
    public void should_return_true_when_all_cards_from_deck_are_in_green_box() {
        Card card = Card.builder().build();
        setupSession(card);
        session.place(card, GREEN);

        boolean successful = session.isSuccessful();

        assertThat(successful).isTrue();
    }

    @Test
    public void should_return_false_when_green_box_has_not_all_deck_cards() {
        Card card = Card.builder().build();
        setupSession(card);

        boolean successful = session.isSuccessful();

        assertThat(successful).isFalse();
    }
}