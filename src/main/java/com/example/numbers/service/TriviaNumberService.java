package com.example.numbers.service;

import com.example.numbers.dao.TriviaNumberRepository;
import com.example.numbers.entity.TriviaNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TriviaNumberService {

    @Autowired
    private TriviaNumberRepository triviaNumberRepository;

    public TriviaNumber save(TriviaNumber triviaNumber) {
        return triviaNumberRepository.save(triviaNumber);
    }

    public List<TriviaNumber> findAll() {
        return triviaNumberRepository.findAll();
    }

    public Optional<TriviaNumber> findByTrivialNumber(long triviaNumber) {
        if (triviaNumberRepository.findByTriviaNumber(triviaNumber).isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://numbersapi.com";
            ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/" + triviaNumber + "/trivia", String.class);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode number = mapper.readTree(response.getBody());
                JsonNode text = number.path("text");
                String fact = text.asText();
                if (!fact.isEmpty()) {
                    TriviaNumber num = new TriviaNumber(triviaNumber, fact);
                    return Optional.of(triviaNumberRepository.save(num));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return triviaNumberRepository.findByTriviaNumber(triviaNumber);
    }

    @Transactional
    public void deleteTriviaNumberByTriviaNumber(long triviaNumber) {
        triviaNumberRepository.deleteTriviaNumberByTriviaNumber(triviaNumber);
    }
}
