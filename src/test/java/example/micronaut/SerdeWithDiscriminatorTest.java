package example.micronaut;

import com.example.openapi.server.model.BookInfo;
import com.example.openapi.server.model.DetailedBookInfo;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

@MicronautTest
class SerdeWithDiscriminatorTest {

    private final ObjectMapper objectMapper;

    public SerdeWithDiscriminatorTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    void testSerializationForDetailedBookInfoWithoutDiscriminatorSet() throws IOException {

        String bookInfoString = objectMapper.writeValueAsString(
                new DetailedBookInfo(
                        "Michael Ende",
                        "SOME_ISBN",
                        "Never-ending Story"
                )
        );

        System.out.println(bookInfoString);

        Assertions.assertEquals(
                "{\"type\":\"DETAILED\",\"name\":\"Never-ending Story\",\"author\":\"Michael Ende\",\"ISBN\":\"SOME_ISBN\"}",
                bookInfoString
        );
    }

    @Test
    void testSerializationForDetailedBookInfoWithDiscriminatorSet() throws IOException {

        String bookInfoString = objectMapper.writeValueAsString(
                new DetailedBookInfo(
                        "Michael Ende",
                        "SOME_ISBN",
                        "Never-ending Story"
                )
        );

        System.out.println(bookInfoString);

        Assertions.assertEquals(
                "{\"type\":\"DETAILED\",\"name\":\"Never-ending Story\",\"author\":\"Michael Ende\",\"ISBN\":\"SOME_ISBN\"}",
                bookInfoString
        );
    }

    @Test
    void testDeserializationForDetailedBookInfo() throws IOException {

        var bookInfo = objectMapper.readValue(
                "{\"type\":\"DETAILED\",\"name\":\"Never-ending Story\",\"author\":\"Michael Ende\",\"ISBN\":\"SOME_ISBN\"}",
                BookInfo.class
        );

        System.out.println(bookInfo);

        Assertions.assertEquals(
                "DetailedBookInfo(name: Never-ending Story, type: null, author: Michael Ende, ISBN: SOME_ISBN)",
                bookInfo.toString()
        );
    }
}
