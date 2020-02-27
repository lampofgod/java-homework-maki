package com.wongnai.interview.movie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InvertedIndexRepository extends CrudRepository<InvertedIndex, Long> {
    /**
     * Find movies ids from name using a given keyword.
     *
     * @param keyword
     * 		a user query keyword
     * @return list of inverted index , should be of size 1, (inverted index = word string + list of id containing the word)
     */
    @Query("SELECT i FROM InvertedIndex i where i.word = :keyword")
    List<InvertedIndex> findByWord(@Param("keyword") String keyword);

}