package com.weekendesk.anki.service;

import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Session;
import com.weekendesk.anki.importer.DeckImporter;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionServiceTest {

    SessionService sessionService = new SessionService(new DeckImporter());

    @Test
    public void should_start_session() throws IOException {
        File deckFile = new ClassPathResource("deck-sample.csv").getFile();

        Session session = sessionService.start(deckFile);

        assertThat(session).isNotNull();
        assertThat(session.getCardsFrom(Box.GREEN)).isEmpty();
        assertThat(session.getCardsFrom(Box.ORANGE)).isEmpty();
        assertThat(session.getCardsFrom(Box.RED)).isEmpty();
        assertThat(session.getCardsFrom(Box.NOT_PLAYED)).hasSize(3);
    }

    @Test
    public void should_restart_session_from_history() throws IOException {
        File deckFile = new ClassPathResource("deck-sample-history.csv").getFile();

        Session session = sessionService.start(deckFile);

        assertThat(session).isNotNull();
        assertThat(session.getCardsFrom(Box.GREEN)).hasSize(1);
        assertThat(session.getCardsFrom(Box.ORANGE)).hasSize(2);
        assertThat(session.getCardsFrom(Box.RED)).hasSize(0);
        assertThat(session.getCardsFrom(Box.NOT_PLAYED)).isEmpty();
    }
}