
/**
 * This class performs basic calculations on the Movie and Rater objects.
 * 
 * Xiangzhen Sun
 * 2/17/2018
 */

import java.util.*;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        String path = "data/";
        String moviefile = path + "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        SecondRatings sr = new SecondRatings( moviefile, ratingsfile );
        System.out.println( sr.getMovieSize() + " movies were loaded" );
        System.out.println( sr.getRaterSize() + " raters info were loaded" );
        
        int minimalRaters = 12;
        // print out all movies that have at least minimalRaters ratings, in ascending order
        ArrayList<Rating> ratedMovies = sr.getAverageRatings( minimalRaters );
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
        for( Rating rating : ratedMovies ) {
            System.out.println( rating.getValue() + " " + sr.getTitle( rating.getItem() ) );
        }
    }
    
    // this method calculates and prints out the average ratings for a specific movie title
    public void getAverageRatingOneMovie() {
        String path = "data/";
        String moviefile = path + "ratedmoviesfull.csv";
        String ratingsfile = path + "ratings.csv";
        SecondRatings sr = new SecondRatings( moviefile, ratingsfile );
        System.out.println( sr.getMovieSize() + " movies were loaded" );
        System.out.println( sr.getRaterSize() + " raters info were loaded" );
        
        String movieTitle = "Vacation";
        String movieID = sr.getID( movieTitle );
        if( !movieID.equals( "NO SUCH TITLE" ) ) {
            System.out.println( movieTitle + ": " + sr.getAverageByID( movieID, 3 ) );
        }
    }
}
