
/**
 * This class resembles Rater class, but possess more powers
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */
import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    // the key is movieID (IMBD), the value is a rating associated with this movieID
    private HashMap<String, Rating> myRatings;

    public EfficientRater( String id ) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating( String item, double rating ) {
        myRatings.put( item, new Rating(item,rating) );
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey( item );
    }

    public String getID() {
        return myID;
    }

    public double getRating( String item ) {
        Rating rating = myRatings.get( item );
        if( rating != null ) {
            return rating.getValue();
        }
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        
        for( String item : myRatings.keySet() ) {
            list.add( myRatings.get(item).getItem() );
        }
        
        return list;
    }
}
