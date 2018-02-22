
/**
 * This class employes FirstRatings class to inquire Movie or Rater objects
 * It also helps perform averaging
 * 
 * Xiangzhen Sun
 * 2/17/2018
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    // a second constructor that reads moviefile and ratingsfile into private fields
    public SecondRatings( String moviefile, String ratingsfile ) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies( moviefile );
        myRaters = fr.loadRaters( ratingsfile );
    }
    
    // this method returns the number of movies that were read in and stored in the ArrayList of type Movie
    public int getMovieSize() {
        return myMovies.size();
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
        ArrayList<Rating> ret = new ArrayList<Rating>();
        for( Movie movie : myMovies ) {
            String IMBD = movie.getID();
            double ratingValue = getAverageByID( IMBD, minimalRaters );
            
            if( ratingValue != 0 ) {
                Rating rating = new Rating( IMBD, ratingValue );
                ret.add( rating );
            }
        }
        
        return ret;
    }
    
    // This method returns the title of the movie with that ID
    public String getTitle( String id ) {
        for( Movie movie : myMovies ) {
            if( id.equals( movie.getID() ) ) {
                return movie.getTitle();
            }
        }
        
        return id + " WAS NOT FOUND!";
    }
    
    // This method returns the movie ID of this movie
    public String getID( String title ) {
        for( Movie movie : myMovies ) {
            if( title.equals( movie.getTitle() ) ) {
                return movie.getID();
            }
        }
        
        return "NO SUCH TITLE";
    }
}
