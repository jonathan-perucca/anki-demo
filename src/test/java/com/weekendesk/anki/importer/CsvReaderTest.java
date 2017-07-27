package com.weekendesk.anki.importer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvReaderTest {

    CsvReader csvReader;

    @Before
    public void readCsv() throws IOException {
        csvReader = new CsvReader(new InputStreamReader(new ClassPathResource("deck-sample.csv").getInputStream()));
    }

    @Test
    public void should_get_file_lines() {
        List<String> lineStream = csvReader.getLinesWithoutHeaders();

        assertThat(lineStream)
                .hasSize(3)
                .contains(
                        "What enzyme breaks down sugars mouth and digestive tract?|Amylase|NOT_PLAYED",
                        "How is dietary cholesterol transported to target tissues?|In chylomicrons|NOT_PLAYED",
                        "What is the glucose transporter in the brain and what are its properties?|GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity|NOT_PLAYED"
                )
                .doesNotContain("card question|card answer");
    }
}