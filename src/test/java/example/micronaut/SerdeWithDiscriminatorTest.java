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
                        "SOME_ISBN",
                        "Never-ending Story",
                        null,
                        "Michael Ende"
                )
        );

        System.out.println(bookInfoString);

        Assertions.assertEquals(
                // For testing with the 4.0.3 version of the OpenAPI plugin, "DETAILED" should be changed to
                // "DetailedBookInfo" due to a separate bug, that was fixed in
                // https://github.com/micronaut-projects/micronaut-openapi/issues/1163
                "{\"type\":\"DETAILED\",\"name\":\"Never-ending Story\",\"author\":\"Michael Ende\",\"isbn\":\"SOME_ISBN\"}",
                bookInfoString
        );
    }

    @Test
    void testDeserializationForDetailedBookInfo() throws IOException {

        var bookInfo = objectMapper.readValue(
                // For testing with the 4.0.3 version of the OpenAPI plugin, "DETAILED" should be changed to
                // "DetailedBookInfo" due to a separate bug, that was fixed in
                // https://github.com/micronaut-projects/micronaut-openapi/issues/1163
                "{\"type\":\"DETAILED\",\"name\":\"Never-ending Story\",\"author\":\"Michael Ende\",\"isbn\":\"SOME_ISBN\"}",
                BookInfo.class
        );

        System.out.println(bookInfo);

        Assertions.assertEquals(
                "DetailedBookInfo(name: Never-ending Story, type: null, author: Michael Ende, isbn: SOME_ISBN)",
                bookInfo.toString()
        );
    }
}
