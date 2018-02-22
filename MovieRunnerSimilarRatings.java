
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */

import java.util.*;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 35;
        // print out all movies that have at least minimalRaters ratings, in ascending order
        ArrayList<Rating> ratedMovies = fr.getAverageRatings( minimalRaters );
        // Rating class has Comparable implemented, so its objects can be compared with compareTo():
        // Insertion Sort implemented:
        for( int i = 0; i < ratedMovies.size() - 1; i++ ) {
            int indexOfMinimum = i;
            
            for( int j = i + 1; j < ratedMovies.size(); j++ ) {
                if( ratedMovies.get( indexOfMinimum ).compareTo( ratedMovies.get( j ) ) > 0 ) {
                    indexOfMinimum = j;
                }
            }
            
            Rating currMinimum = ratedMovies.get( indexOfMinimum );
            ratedMovies.set( indexOfMinimum, ratedMovies.get( i ) );
            ratedMovies.set( i, currMinimum );
        }
        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " movies" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
    
    // the criteria this time is based on movies that came out in a specified year or later and
    // have a specified genre as one of its genres
    public void printAverageRatingsByYearAfterAndGenre() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 8;
        
        String genre = "Drama";
        Filter filterGenre = new GenreFilter( genre );
        
        int year = 1990;
        Filter filterYear = new YearAfterFilter( year );
        
        AllFilters filterComplex = new AllFilters();  //note, here, we cannot declare filterComplex as
        // generic Filter type because fileterComplex has special member function: addFilter
        filterComplex.addFilter( filterGenre );
        filterComplex.addFilter( filterYear );
        
        ArrayList<Rating> ratedMovies = fr.getAverageRatingsByFilter( minimalRaters, filterComplex );
        // Rating class has Comparable implemented, so its objects can be compared with compareTo():
        // Insertion Sort implemented:
        for( int i = 0; i < ratedMovies.size() - 1; i++ ) {
            int indexOfMinimum = i;
            
            for( int j = i + 1; j < ratedMovies.size(); j++ ) {
                if( ratedMovies.get( indexOfMinimum ).compareTo( ratedMovies.get( j ) ) > 0 ) {
                    indexOfMinimum = j;
                }
            }
            
            Rating currMinimum = ratedMovies.get( indexOfMinimum );
            ratedMovies.set( indexOfMinimum, ratedMovies.get( i ) );
            ratedMovies.set( i, currMinimum );
        }
        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " movies" );
        /*
        for( Rating rating : ratedMovies ) {
            String IMBD = rating.getItem();
            System.out.println(rating.getValue() + " " + md.getYear(IMBD) + " " + md.getTitle(IMBD)
                               + "\n\t" + md.getGenres(IMBD) );
        }
        */
    }
    
    public void printSimilarRatings() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        String id = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        
        ArrayList<Rating> ratedMovies = fr.getSimilarRatings( id, numSimilarRaters, minimalRaters );
        // Sorting is no longer needed here, anymore, because getSimilarRatings has sorting embedded

        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " recommended movies and ratings" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
    
    public void printSimilarRatingsByGenre() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        String id = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        String genre = "Mystery";
        Filter filterGenre = new GenreFilter( genre );
        
        ArrayList<Rating> ratedMovies =
        fr.getSimilarRatingsByFilter( id, numSimilarRaters, minimalRaters, filterGenre );
        // Sorting is no longer needed here, anymore, because getSimilarRatings has sorting embedded

        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " recommended movies and ratings" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
    
    public void printSimilarRatingsByDirector() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        String id = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String directors =
        "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        Filter filterDirector = new DirectorFilter( directors );
        
        ArrayList<Rating> ratedMovies =
        fr.getSimilarRatingsByFilter( id, numSimilarRaters, minimalRaters, filterDirector );
        // Sorting is no longer needed here, anymore, because getSimilarRatings has sorting embedded

        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " recommended movies and ratings" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        String id = "168";
        int minimalRaters = 3;
        int numSimilarRaters = 10;
        
        String genre = "Adventure";
        Filter filterGenre = new GenreFilter( genre );
        
        int minMinutes = 80;
        int maxMinutes = 160;
        Filter filterMinutes = new MinutesFilter( minMinutes, maxMinutes );
        
        AllFilters filterComplex = new AllFilters();  //note, here, we cannot declare filterComplex as
        // generic Filter type because fileterComplex has special member function: addFilter
        filterComplex.addFilter( filterGenre );
        filterComplex.addFilter( filterMinutes );
        
        ArrayList<Rating> ratedMovies =
        fr.getSimilarRatingsByFilter( id, numSimilarRaters, minimalRaters, filterComplex );
        // Sorting is no longer needed here, anymore, because getSimilarRatings has sorting embedded

        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " recommended movies and ratings" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = "ratings.csv";
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase rd = new RaterDatabase();
        rd.initialize( ratingsfile );
        System.out.println( "Read data for " + rd.size() + " raters" );
        
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        String id = "314";
        int minimalRaters = 5;
        int numSimilarRaters = 10;
        
        int year = 1975;
        Filter filterYear = new YearAfterFilter( year );
        
        int minMinutes = 70;
        int maxMinutes = 200;
        Filter filterMinutes = new MinutesFilter( minMinutes, maxMinutes );
        
        AllFilters filterComplex = new AllFilters();  //note, here, we cannot declare filterComplex as
        // generic Filter type because fileterComplex has special member function: addFilter
        filterComplex.addFilter( filterYear );
        filterComplex.addFilter( filterMinutes );
        
        ArrayList<Rating> ratedMovies =
        fr.getSimilarRatingsByFilter( id, numSimilarRaters, minimalRaters, filterComplex );
        // Sorting is no longer needed here, anymore, because getSimilarRatings has sorting embedded

        // print the sorted Rating arrayList
        System.out.println( "Found " + ratedMovies.size() + " recommended movies and ratings" );
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + md.getTitle( rating.getItem() ) );
        }
    }
}
