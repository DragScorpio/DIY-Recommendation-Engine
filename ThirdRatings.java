
/**
 * This class is designed to apply several pre-defined filters on the movie database
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */
import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    // a second constructor that reads ratingsfile into private fields
    public ThirdRatings( String ratingsfile ) {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters( ratingsfile );
    }
    
    // this method returns the number of raters that were read in and stored in the ArrayList of type Rater
    public int getRaterSize() {
        return myRaters.size();
    }
    
    /*
       This method returns a double representing the average movie rating for this movie ID
       if there are at least minimalRaters ratings. If there are not minimalRaters ratings,
       then it returns 0.0.
    */
    public double getAverageByID( String id, int minimalRaters ) {
        double ratingSum = 0.0;
        int ratersRating = 0;
        
        for( Rater rater : myRaters ) {
            double ratingValue = rater.getRating( id );
            if( ratingValue != -1 ) {  // if the movie is found in this rater's rating list
                ratingSum += ratingValue;
                ratersRating++;
            }
        }
        
        return ratersRating < minimalRaters ? 0.0 : ( ratingSum / ratersRating );
    }
    
    // The method returns an ArrayList of all the Rating objects for movies that
    // have at least the minimal number of raters supplying a rating.
    public ArrayList<Rating> getAverageRatings( int minimalRaters ) {
        ArrayList<String> movies = MovieDatabase.filterBy( new TrueFilter() );
        ArrayList<Rating> ret = new ArrayList<Rating>();
        for( String IMBD : movies ) {
            double ratingValue = getAverageByID( IMBD, minimalRaters );
            
            if( ratingValue != 0 ) {
                Rating rating = new Rating( IMBD, ratingValue );
                ret.add( rating );
            }
        }
        
        return ret;
    }
    
    // This method should create and return an ArrayList of type Rating of all the movies that
    // have at least minimalRaters ratings and satisfies the filter criteria
    public ArrayList<Rating> getAverageRatingsByFilter( int minimalRaters, Filter filterCriteria ) {
        ArrayList<String> movieIDs = MovieDatabase.filterBy( filterCriteria );
        ArrayList<Rating> ret = new ArrayList<Rating>();
        for( String IMBD : movieIDs ) {
            double ratingValue = getAverageByID( IMBD, minimalRaters );
            
            if( ratingValue != 0 ) {
                Rating rating = new Rating( IMBD, ratingValue );
                ret.add( rating );
            }
        }
        
        return ret;
    }
}