package com.example.BFF1;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LogRepository extends MongoRepository<Log, String> {

    @Query(value = "{$group:{_id:{method:\"$method\", url:\"$url\"}, count:{$sum:1}}},{$sort:{count:-1}},{$limit:1}")
    List<Object[]> findMostCalledEndpoint();

    Optional<Log> findTopByOrderByTimestampDesc();
}
