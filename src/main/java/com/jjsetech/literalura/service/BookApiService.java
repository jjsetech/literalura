package com.jjsetech.literalura.service;
import com.jjsetech.literalura.model.BookApiResponse;
import com.jjsetech.literalura.model.BookData;
import com.jjsetech.literalura.model.PersonData;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookApiService {

    private final String baseUrl = "https://gutendex.com/books/";

    public List<BookData> search(String text) {
        var json = ApiConsumer.getData(
                baseUrl + "?search="
                        + URLEncoder.encode(text.trim().toLowerCase(), StandardCharsets.UTF_8)
        );
        return new DataConverter().convert(json, BookApiResponse.class).livros();
    }

    public List<PersonData> getAuthorsAliveOnYear(Integer year) {
        var endpoint = baseUrl + "?author_year_start=" + year
                + "&author_year_end=" + year;
        var json = ApiConsumer.getData(endpoint);
        return new DataConverter()
                .convert(json, BookApiResponse.class)
                .livros()
                .stream()
                .flatMap(l -> l.autores().stream())
                .toList();
    }
}