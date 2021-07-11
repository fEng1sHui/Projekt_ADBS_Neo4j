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
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String Surname;
    private Long age;


    @Relationship(type = "FRIEND", direction = UNDIRECTED)
    private List<Person> friends = new ArrayList<>();

    @Relationship(type = "READED", direction = UNDIRECTED)
    private List<Book> books = new ArrayList<>();

}
