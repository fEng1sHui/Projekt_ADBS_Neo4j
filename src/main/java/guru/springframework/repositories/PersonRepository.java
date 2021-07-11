package guru.springframework.repositories;

import guru.springframework.models.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PersonRepository extends Neo4jRepository<Person, Long> {

    //znajdz osoby ktore przeczytaly dana ksiazke - nie dziala
    @Query (value = "MATCH (person:Person)-[:READED]-(book:BOOK) " +
            "WHERE id(book)= $book_id RETURN person ")
    List<Person> findByBookId(@Param("book_id") Long bookId);

    //znajdz osobe po id
    @Query ("MATCH (person:Person) " +
            "WHERE id(person)= $person_id " +
            "RETURN person  ")
    Person findPersonById(@Param("person_id") Long personId);

    //znajdz znajomych danej osoby
    @Query ("MATCH (friendsList:Person)-[:FRIEND]-(person:Person) " +
            "WHERE id(person)= $person_id RETURN friendsList  ")
    List<Person> findFriendsByPersonId(@Param("person_id") Long id);

    //znajdz proponowanych znajomych
    @Query ("MATCH (searchingList:Person), (personFirst:Person) " +
            "WHERE NOT (searchingList)-[:FRIEND]-(personFirst) AND  id(personFirst)= $person_id AND NOT id(searchingList)= $person_id " +
            "RETURN searchingList")
    List<Person> findPeopleForFriendship(@Param("person_id") Long id);

    @Query("MATCH (listPeople:Person)" +
            "RETURN listPeople ORDER BY listPeople.name ")
    List<Person> findAllOrOrderByName();


    @Query("CREATE (p:Person {name: $name, surname:$surname,age:$age })")
    void savePerson(@Param("name") String name,
                    @Param("surname") String surname,
                    @Param("age") Long age);



    @Query("CALL apoc.import.json($path)")
    void LoadCSVFile(@Param("path") String path);

    @Query("match (b), (a)-[r] -> () delete a, r, b")
    void deleteAllRelationshipsAndNodes();


    //znajdz osoby ktore moze znac dana osoba - czyli proponowani znajomi
    @Query ("MATCH (otherPeople:Person)-[:FRIEND]-(friendsPerson:Person)-[:FRIEND]-(person) "+
            "WHERE NOT id(otherPeople)= $person_id AND id(person)=$person_id AND NOT (otherPeople:Person)-[:FRIEND]-(person:Person)" +
            "WITH otherPeople as newFriend, COUNT(*) as number " +
            "RETURN newFriend ORDER BY number DESC LIMIT 5  ")
    List<Person> findProposedPeopleForFriendship(@Param("person_id") Long id);

}