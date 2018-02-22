
/**
 * This class performs basic calculations on the Movie and Rater objects,
 * with filters being applied specifically
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */

import java.util.*;

public class MovieRunnerWithFilters {
    public void printAverageRatings() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 35;
        // print out all movies that have at least minimalRaters ratings, in ascending order
        ArrayList<Rating> ratedMovies = tr.getAverageRatings( minimalRaters );
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
    
    public void printAverageRatingsByYearAfter() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 20;
        int year = 2000;
        Filter filterYear = new YearAfterFilter( year );
        // print out all movies that have at least minimalRaters ratings,
        // and produced after year 2000 in ascending order
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterYear );
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
            String IMBD = rating.getItem();
            System.out.println( rating.getValue() + " " + md.getYear(IMBD) + " " + md.getTitle(IMBD) );
        }
    }
    
    public void printAverageRatingsByGenre() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 20;
        String genre = "Comedy";
        Filter filterGenre = new GenreFilter( genre );
        // print out all movies that have at least minimalRaters ratings,
        // and contains "genre" in ascending order
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterGenre );
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
            System.out.println(rating.getValue() + " " + md.getTitle(IMBD) + "\n\t" + md.getGenres(IMBD));
        }
        */
    }
    
    public void printAverageRatingsByMinutes() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 5;
        int minMinutes = 105;
        int maxMinutes = 135;
        Filter filterMinutes = new MinutesFilter( minMinutes, maxMinutes );
        // print out all movies that have at least minimalRaters ratings,
        // and has length in minutes between minMinutes and maxMinutes, in ascending order
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterMinutes );
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
            System.out.println(rating.getValue() + " Time: " + md.getMinutes(IMBD) + " " + md.getTitle(IMBD));
        }
        */
    }
    
    public void printAverageRatingsByDirectors() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 4;
        String directors =
        "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        Filter filterDirector = new DirectorFilter( directors );
        // print out all movies that have at least minimalRaters ratings,
        // and contains at least one of the direcotors in "directors", in ascending order
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterDirector );
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
            System.out.println(rating.getValue() + " " + md.getTitle(IMBD) + "\n\t" + md.getDirector(IMBD));
        }
        */
    }
    
    // the criteria this time is based on movies that came out in a specified year or later and
    // have a specified genre as one of its genres
    public void printAverageRatingsByYearAfterAndGenre() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
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
        
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterComplex );
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
    
    // The criteria now is based on running time (at least a specified minimum number of minutes and
    // at most a specified maximum number of minutes), and directors (at least one of the directors in
    // a list of specified directors—directors are separated by commas)
    public void printAverageRatingsByDirectorsAndMinutes() {
        String path = "data/";
        String moviefile = "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        ThirdRatings tr = new ThirdRatings( ratingsfile );
        System.out.println( "Read data for " + tr.getRaterSize() + " raters" );
        MovieDatabase md = new MovieDatabase();
        md.initialize( moviefile );
        System.out.println( "Read data for " + md.size() + " movies" );
        
        int minimalRaters = 3;
        
        int minMinutes = 90;
        int maxMinutes = 180;
        Filter filterMinutes = new MinutesFilter( minMinutes, maxMinutes );
        
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        Filter filterDirector = new DirectorFilter( directors );
        
        AllFilters filterComplex = new AllFilters();  //note, here, we cannot declare filterComplex as
        // generic Filter type because fileterComplex has special member function: addFilter
        filterComplex.addFilter( filterMinutes );
        filterComplex.addFilter( filterDirector );
        
        ArrayList<Rating> ratedMovies = tr.getAverageRatingsByFilter( minimalRaters, filterComplex );
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
            String IMBD = rating.getItem();
            System.out.println(rating.getValue() + " Time: " + md.getMinutes(IMBD)
                               + " " + md.getTitle(IMBD) + "\n\t" + md.getDirector(IMBD) );
        }
    }
}
