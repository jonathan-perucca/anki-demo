package com.weekendesk.anki.importer;

import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.domain.Deck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class DeckImporter {

    public Deck buildFrom(File file) {
        Deck deck = Deck.emptyDeck();
        try {
            CsvReader csvReader = new CsvReader(new InputStreamReader(new FileInputStream(file)));

            csvReader.getLinesWithoutHeaders()
                    .stream()
                    .map(this::createCardFromLine)
                    .forEach(deck::addCard);

            return deck;
        } catch (FileNotFoundException e) {
            return Deck.emptyDeck();
        }
    }

    private Card createCardFromLine(String line) {
        String[] lineParts = line.split("\\|");
        return Card.builder()
                .question(lineParts[0])
                .answer(lineParts[1])
                .build();
    }

}
