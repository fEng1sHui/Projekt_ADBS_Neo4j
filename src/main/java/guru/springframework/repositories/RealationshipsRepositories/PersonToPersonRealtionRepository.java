package guru.springframework.repositories.RealationshipsRepositories;

import guru.springframework.models.Person;
import guru.springframework.models.Relationships.PersonToPerson;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface PersonToPersonRealtionRepository extends Neo4jRepository<PersonToPerson, Long> {


    //tworzenie relacji osoba - osoba
    @Query("MATCH (person), (friend) " +
            "WHERE id(person)= $id_person AND id(friend)= $id_friend " +
            "CREATE (person)-[:FRIEND{}]->(friend)")
    void saveFriendship(@Param("id_person") Long person, @Param("id_friend") Long friend);






}
