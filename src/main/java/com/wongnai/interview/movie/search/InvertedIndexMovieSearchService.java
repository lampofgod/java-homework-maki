package com.wongnai.interview.movie.search;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	private final static Logger LOGGER =
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	private MovieRepository movieRepository;

	private Map<String, Set<Long>> invertedIndexLookupTable;

	private void constructInvertedIndexLookupTable() {
		invertedIndexLookupTable = new HashMap<>();
		movieRepository.findAll().forEach(movie -> {
			String[] keywords = movie.getName().split(" ");
			Long movieId = movie.getId();

			for (String keyword: keywords) {
				String keywordLowerCase = keyword.toLowerCase();
				if(invertedIndexLookupTable.containsKey(keywordLowerCase)) {
					invertedIndexLookupTable.get(keywordLowerCase).add(movieId);
				}
				else {
					Set<Long> s = new HashSet<>();
					s.add(movieId);
					invertedIndexLookupTable.put(keywordLowerCase, s);
				}
			}
		});
	}

	@Override
	public List<Movie> search(String queryText) {

		//TODO: Step 4 => Please implement in-memory inverted index to search movie by keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// |  Term      | Movie Ids      |
		// -------------------------------
		// |  Star      |  5, 8, 1       |
		// |  War       |  5, 2          |
		// |  Trek      |  1, 8          |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms, Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for just movie id 5.

		if(invertedIndexLookupTable == null) {
			constructInvertedIndexLookupTable();
		}

		String[] keywords = queryText.split(" ");
		Set<Long> ids = Arrays.stream(keywords)
				.map(String::toLowerCase)
				.map(keyword -> invertedIndexLookupTable.getOrDefault(keyword, new HashSet<>()))
				.reduce(Sets::intersection)
				.orElse(new HashSet<>());

		return StreamSupport.stream(movieRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}
}
