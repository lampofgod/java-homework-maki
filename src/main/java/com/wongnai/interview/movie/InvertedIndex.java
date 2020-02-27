package com.wongnai.interview.movie;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class InvertedIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> index = new HashSet<>();

    /**
     * Required by JPA.
     */
    protected InvertedIndex() {
    }

    public InvertedIndex(String text) {
        this.word = text;
    }

    public String getWord() {
        return word;
    }

    public Long getId() {
        return id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Long> getIndex() {
        return index;
    }

    public void setIndex(Set<Long> index) {
        this.index = index;
    }
}