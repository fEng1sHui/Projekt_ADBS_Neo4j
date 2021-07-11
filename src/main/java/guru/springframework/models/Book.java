package guru.springframework.models;


import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.UNDIRECTED;

@NodeEntity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer year_of_production;

    @Relationship(type = "BOOK_AUTHOR", direction = UNDIRECTED)
    private List<Author> author = new ArrayList<>();

    @Relationship(type = "READED", direction = UNDIRECTED)
    private List<Person> people = new ArrayList<>();
}
