package com.weekendesk.anki.importer;

import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.domain.Deck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class DeckImporterIntegrationTest {

    DeckImporter deckImporter;
    File deckFile;
    Set<Card> cardsExpected;

    @Test
    public void should_build_deck_from_csv(){
        Deck deck = deckImporter.buildFrom(deckFile);

        assertThat(deck).isNotNull();
        assertThat(deck.getCards())
                .hasSize(3)
                .containsAll(cardsExpected);
    }

    @Test
    public void should_return_empty_deck_when_fileNotFound() {
        File unknownFile = new File("unknown");

        Deck deck = deckImporter.buildFrom(unknownFile);

        assertThat(deck).isNotNull();
        assertThat(deck.getCards()).hasSize(0);
    }

    @Before
    public void setup() throws IOException {
        setupCardsExpected();

        deckFile = new ClassPathResource("deck-sample.csv").getFile();
        deckImporter = new DeckImporter();
    }

    private void setupCardsExpected() {
        cardsExpected = new HashSet<>();
        cardsExpected.addAll(asList(
                Card.builder()
                        .question("What enzyme breaks down sugars mouth and digestive tract?")
                        .answer("Amylase")
                        .build(),
                Card.builder()
                        .question("How is dietary cholesterol transported to target tissues?")
                        .answer("In chylomicrons")
                        .build(),
                Card.builder()
                        .question("What is the glucose transporter in the brain and what are its properties?")
                        .answer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity")
                        .build()
        ));
    }
}
