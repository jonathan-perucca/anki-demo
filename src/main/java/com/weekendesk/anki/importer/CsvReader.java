package com.weekendesk.anki.importer;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CsvReader {

    private final Reader source;

    public List<String> getLinesWithoutHeaders() {
        try (BufferedReader reader = new BufferedReader(source)) {

            return getLinesWithoutHeaders(reader).collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Stream<String> getLinesWithoutHeaders(BufferedReader reader) {
        int headerLine = 1;

        return reader.lines().skip(headerLine);
    }
}