package com.example.numbers.dao;

import com.example.numbers.entity.TriviaNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TriviaNumberRepository extends JpaRepository<TriviaNumber, Long> {

    Optional<TriviaNumber> findByTriviaNumber(long triviaNumber);

    void deleteTriviaNumberByTriviaNumber(long triviaNumber);
}
