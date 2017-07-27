package com.weekendesk.anki.service;

import com.weekendesk.anki.domain.*;
import com.weekendesk.anki.importer.DeckImporter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static com.weekendesk.anki.domain.AnswerType.*;
import static com.weekendesk.anki.domain.Box.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    private SessionService sessionService = new SessionService(new DeckImporter());

    @Mock
    private Student mockStudent;

    private Card CARD_A = Card.builder().question("one_question").answer("one_answer").build();
    private Card CARD_B = Card.builder().question("two_question").answer("two_answer").build();
    private Card CARD_C = Card.builder().question("three_question").answer("three_answer").build();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void teardown() {
        System.setOut(null);
    }

    @Test
    public void should_start_session() throws IOException {
        File deckFile = new ClassPathResource("deck-sample.csv").getFile();

        Session session = sessionService.start(deckFile);

        assertThat(session).isNotNull();
        assertThat(session.getCardsFromDeck()).hasSize(3);
    }

    @Test
    public void should_simulate_a_session_play_for_first_day() {
        Session session = new Session( setupFirstDayDeck() );
        given(mockStudent.study(CARD_A)).willReturn(COMPLETE);
        given(mockStudent.study(CARD_B)).willReturn(PARTIAL);
        given(mockStudent.study(CARD_C)).willReturn(PARTIAL);

        sessionService.studies(session, mockStudent);

        assertThat(session.getCardsFrom(NOT_PLAYED)).isEmpty();
        assertThat(session.getCardsFrom(ORANGE)).hasSize(2);
        assertThat(session.getCardsFrom(GREEN)).hasSize(1);
    }

    private Deck setupFirstDayDeck() {
        CARD_A.setLastBox(NOT_PLAYED);
        CARD_B.setLastBox(NOT_PLAYED);
        CARD_C.setLastBox(NOT_PLAYED);
        return new Deck(new HashSet<>(asList(CARD_A, CARD_B, CARD_C)));
    }

    @Test
    public void should_simulate_a_session_play_for_second_days() {
        Session session = new Session( setupSecondDayDeck() );
        given(mockStudent.study(CARD_A)).willReturn(COMPLETE);
        given(mockStudent.study(CARD_B)).willReturn(DID_NOT_KNOW).willReturn(COMPLETE);
        given(mockStudent.study(CARD_C)).willReturn(COMPLETE);

        sessionService.studies(session, mockStudent);

        assertThat(session.getCardsFrom(NOT_PLAYED)).isEmpty();
        assertThat(session.getCardsFrom(ORANGE)).hasSize(0);
        assertThat(session.getCardsFrom(GREEN)).hasSize(3);
        // just for demo
        assertThat(this.outContent.toString()).contains("Congratulations");
    }

    private Deck setupSecondDayDeck() {
        CARD_A.setLastBox(GREEN);
        CARD_B.setLastBox(ORANGE);
        CARD_C.setLastBox(ORANGE);
        return new Deck(new HashSet<>(asList(CARD_A, CARD_B, CARD_C)));
    }
}