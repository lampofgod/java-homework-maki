package com.wongnai.interview.movie.search;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.wongnai.interview.movie.external.MovieData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		List<MovieData> movies = movieDataService.fetchAll();

		return 	movies.stream()
				.filter(movieData -> {
					List<String> movieFilter = Arrays.asList(movieData.getTitle().toLowerCase().split(" "));
					return movieFilter.contains(queryText.toLowerCase());
				})
				.map(movieData -> {
					Movie m = new Movie(movieData.getTitle());
					m.getActors().addAll(movieData.getCast());
					return m;
				})
				.collect(Collectors.toList());
	}
}
