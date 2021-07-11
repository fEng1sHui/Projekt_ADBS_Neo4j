package guru.springframework.services;

import guru.springframework.repositories.PersonRepository;
import guru.springframework.repositories.RealationshipsRepositories.PersonToPersonRealtionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@AllArgsConstructor
public class MainService {

    PersonToPersonRealtionRepository personToPersonRealtionRepository;
    PersonRepository personRepository;

    public void saveRelationshipPersonToPerson(Long id_1, Long id_2){
        personToPersonRealtionRepository.saveFriendship(id_1, id_2);
//        personToPersonRealtionRepository.saveFriendship(id_2, id_1);
    }

    public void LoadDataFromJSONFile(String path){
        personRepository.LoadCSVFile(path);
    }



}
