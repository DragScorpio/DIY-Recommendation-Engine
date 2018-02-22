
/**
 * An even more fancy version of ThirdRatings Class
 * 
 * @Xiangzhen Sun 
 * @2/19/2018
 */

import java.util.*;

public class FourthRatings {
    // This method should first translate a rating from the scale 0 to 10 to the scale -5 to 5
    // and return the dot product of the ratings of movies that they both rated
    private double dotProduct( Rater me, Rater r ) {
        // we have the basic assumption that all ratings were 100% originally in scale 0-10
        double dProduct = 0.0;
        // first, copy a rating list of "me" to myRatings
        ArrayList<String> myRatings = me.getItemsRated();
        for( String IMBD : myRatings ) {
            // if both me and r have rated a movie IMBD, then add the moduled product to dProduct:
            if( r.hasRating( IMBD ) ) {
                dProduct += ( ( me.getRating(IMBD) - 5 ) * ( r.getRating(IMBD) - 5 ) );
            }
        }
        
        return dProduct;
    }
    
    // this method computes a similarity rating for each rater in the RaterDatabase
    // (except the rater with the ID given by the parameter) to see how similar they are to
    // the Rater whose ID is the parameter to getSimilarities
    private ArrayList<Rating> getSimilarities( String id ) {
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
    
        for( Rater r : RaterDatabase.getRaters() ) {
            String rID = r.getID();
            // add dot_product(r, me) to list if r != me; only positive dot_product are considered!
            if( rID != id ) {
                double value = dotProduct( me, r );
                if( value > 0 ) {
                    list.add( new Rating( rID, value ) );
                }
            }
        }

        Collections.sort( list, Collections.reverseOrder() );
        return list;
    }
    
    /*
     * This method should return an ArrayList of type Rating, of movies and
     * their weighted average ratings using only the top numSimilarRaters with positive ratings and
     * including only those movies that have at least minimalRaters ratings from those most similar raters
     * (not just minimalRaters ratings overall).
     */
    public ArrayList<Rating> getSimilarRatings( String id, int numSimilarRaters, int minimalRaters ) {
        // Rater me = RaterDatabase.getRater( id );
        ArrayList<String> movies = MovieDatabase.filterBy( new TrueFilter() );
        // get a full list of similar ratings by other Raters, which is already sorted in descending order
        ArrayList<Rating> list = getSimilarities( id );
        ArrayList<Rating> ret = new ArrayList<Rating>();  // to be returned
        
        double runningTotals;
        int numOfEffectiveRatings;
        for( String movieID : movies ) {
            runningTotals = 0.0;
            numOfEffectiveRatings = 0;
            // consider only the first numSimilarRaters ratings
            for( int k = 0; k < numSimilarRaters; k++ ) { //loop over just the raters who are close to me
                Rating r = list.get(k);  // Attention: this rating object is not a real Rating object:
                // it's ID is the raterID, its value is the dot_product closeness!!!
                
                // retrieve the Rater object by using its ID
                Rater rater_r = RaterDatabase.getRater( r.getItem() );
                //use Rater id and weight in r to update runnning totoals
                if( rater_r.hasRating(movieID) ) {
                    numOfEffectiveRatings++;
                    // multiply their similarity rating by the rating they gave that movie. then
                    // add to the runningTotals
                    double rRating = rater_r.getRating(movieID);
                    double weight = r.getValue();
                    runningTotals += rRating * weight;
                }
            }
            
            if( numOfEffectiveRatings >= minimalRaters && !RaterDatabase.getRater(id).hasRating(movieID) ) {
                // add Rating for movieID to ret, only if no less than minimalRaters in top
                // numSimilarRaters raters have rated this movie
                double weightedAverage = runningTotals/numOfEffectiveRatings;
                ret.add( new Rating(movieID, weightedAverage) );
            }
        }
        
        // sort first and return
        Collections.sort( ret, Collections.reverseOrder() );
        return ret;
    }
    
    // a fiter-version of the getSimilarRating method
    public ArrayList<Rating> getSimilarRatingsByFilter( String id, int numSimilarRaters, int minimalRaters,
    Filter filterCriteria ) {
        // Rater me = RaterDatabase.getRater( id );
        ArrayList<String> movies = MovieDatabase.filterBy( filterCriteria );
        // get a full list of similar ratings by other Raters, which is already sorted in descending order
        ArrayList<Rating> list = getSimilarities( id );
        ArrayList<Rating> ret = new ArrayList<Rating>();  // to be returned
        
        double runningTotals;
        int numOfEffectiveRatings;
        for( String movieID : movies ) {
            runningTotals = 0.0;
            numOfEffectiveRatings = 0;
            // consider only the first numSimilarRaters ratings
            for( int k = 0; k < numSimilarRaters; k++ ) { //loop over just the raters who are close to me
                Rating r = list.get(k);  // Attention: this rating object is not a real Rating object:
                // it's ID is the raterID, its value is the dot_product closeness!!!
                
                // retrieve the Rater object by using its ID
                Rater rater_r = RaterDatabase.getRater( r.getItem() );
                //use Rater id and weight in r to update runnning totoals
                if( rater_r.hasRating(movieID) ) {
                    numOfEffectiveRatings++;
                    // multiply their similarity rating by the rating they gave that movie. then
                    // add to the runningTotals
                    double rRating = rater_r.getRating(movieID);
                    double weight = r.getValue();
                    runningTotals += rRating * weight;
                }
            }
            
            if( numOfEffectiveRatings >= minimalRaters && !RaterDatabase.getRater(id).hasRating(movieID) ) {
                // add Rating for movieID to ret, only if no less than minimalRaters in top
                // numSimilarRaters raters have rated this movie
                double weightedAverage = runningTotals/numOfEffectiveRatings;
                ret.add( new Rating(movieID, weightedAverage) );
            }
        }
        
        // sort first and return
        Collections.sort( ret, Collections.reverseOrder() );
        return ret;
    }

    /*
       This method returns a double representing the average movie rating for this movie ID
       if there are at least minimalRaters ratings. If there are not minimalRaters ratings,
       then it returns 0.0.
    */
    public double getAverageByID( String id, int minimalRaters ) {
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
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
