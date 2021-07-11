package guru.springframework.repositories;

import guru.springframework.models.Book;

import org.springframework.data.neo4j.annotation. Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends Neo4jRepository<Book, Long> {


    @Query("MATCH (book:Book)-[:READED]-(person:Person) " +
            "WHERE id(person)= $person_id RETURN book  ")
    List<Book> findViewedBooksByPersonId(@Param("person_id") Long personId);

    @Query("MATCH (book:Book)-[:INTERESTED]-(person:Person) " +
            "WHERE id(person)= $person_id RETURN book  ")
    List<Book> findnteresingBooksByPersonId(@Param("person_id") Long personId);

    @Query("MATCH (book:Book)" +
            "RETURN book ORDER BY book.title ")
    List<Book> findAllBooksOrderByTitle();


    @Query ("MATCH (book:Book) " +
            "WHERE id(book)= $book_id " +
            "RETURN book  ")
    Book findBookById(@Param("book_id") Long bookId);


    @Query("CALL apoc.load.json($path) " +
            " YIELD value " +
            "CREATE (:Book {title:value.title, year_of_production:apoc.convert.toInteger(value.year_of_production)})")
    void importBooksFromJSONFile(@Param("path") String path);



// filmy ogladniete przez innych plus te ktore sa nimi zainteresowane
//    @Query("MATCH (author_of_both_books:Author)-[:BOOK_AUTHOR]-(friend_book:BOOK)-[x:READED]-(friend:Person)-[:FRIEND]-(person:Person)-[:READED]-(book:BOOK)-[:BOOK_AUTHOR]-(author_of_both_books:Author)" +
//            " WHERE NOT (person)-[:READED]-(friend_book) AND  id(person)= $person_id" +
//            " WITH friend_book as book, count(*)*x.weight  as number1"+
//            "  RETURN book"+
//            " UNION"+
//            " MATCH (author_of_both_books:Author)-[:BOOK_AUTHOR]-(readed_book:BOOK)-[z:INTERESTED]-(friend:Person)-[:FRIEND]-(person:Person)-[:READED]-(book:BOOK)-[:BOOK_AUTHOR]-(author_of_both_books:Author)"+
//            " WHERE NOT (person)-[:INTERESTED]-(readed_book) AND  id(person)= $person_id"+
//            " WITH readed_book as book, count(*)*z.weight as number2"+
//            " RETURN book")

    // filmy ktore ogladnely inne osoby - znajomi
//    @Query("MATCH (author_of_both_books:Author)-[:BOOK_AUTHOR]-(friend_book:BOOK)-[x:READED]-(friend:Person)-[:FRIEND]-(person:Person)-[:READED]-(book:BOOK)-[:BOOK_AUTHOR]-(author_of_both_books:Author)" +
//    " WHERE id(person)= $person_id AND NOT (person)-[:READED]-(friend_book)" +
//   " WITH friend_book as book, count(*)*x.weight  as number1"+
//  "  RETURN book")

    @Query("MATCH (c2:Author)-[:BOOK_AUTHOR]-(n2:Book)-[x:READED]-(p2:Person)-[:FRIEND]-(p1:Person)-[:READED]-(n1:Book)-[:BOOK_AUTHOR]-(c2:Author)" +
            " WHERE NOT (p1)-[:READED]-(n2) AND  id(p1)= $person_id" +
            " WITH n2 as movie, count(*)*x.weight  as number1"+
            "  RETURN movie")
    List<Book> findSuggestedBooks(@Param("person_id") Long id);




    @Query("CREATE (book:Book {title: $title ,year_of_production: $year_of_production})")
    void saveBook(@Param("title") String title,
                  @Param("year_of_production") Long year_of_production);




}
