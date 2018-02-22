
/**
 * Write a description of RecommendationRunner here.
 * 
 * @Xiangzhen Sun
 * @2/20/2018
 */

import java.util.*;

public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> itemsToRate = new ArrayList<String>(
               Arrays.asList("0993846", "1454468", "0770828", "1670345", "0816692", "1535109",
                             "2267998", "0816711", "1045658", "1343092") );

    return itemsToRate;

        /*
        ArrayList<String> list = new ArrayList<String>();
        list.add ( "182789" );
        list.add ( "1714915" );
        list.add( "2277150" );
        list.add( "4437212" );
        list.add( "2820852" );
        list.add( "1255953" );
        list.add( "2051894" );
        list.add( "100161" );
        list.add( "861739" );
        list.add( "76666" );
        
        ArrayList<String> list2 = new ArrayList<String>();
        for( int i = 0; i <= 6; i++ ){
            list2.add( list.get(i) );
        }
        return list2;
        
        return list;
        */
    }
    
    public void printRecommendationsFor(String webRaterID){
        
        FourthRatings fr = new FourthRatings();
        RaterDatabase RDB = new RaterDatabase();
        RDB.initialize("data/ratings.csv");
        MovieDatabase MDB = new MovieDatabase();
        MDB.initialize("data/ratedmoviesfull.csv");
        
        String id = webRaterID;
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList<Rating> list = fr.getSimilarRatings( id, numSimilarRaters, minimalRaters );
        
        ArrayList<Rating> top10RecommendedMovies = new ArrayList<Rating>();
        for( Rating rating : list ) {
            top10RecommendedMovies.add(rating);
        }
        /*
        if(list.size() >= 10){
            for(int i = 0; i < 10; i++){
                top10RecommendedMovies.add(list.get(i));
            }
        }
        */
        
        if(top10RecommendedMovies.isEmpty()){
            System.out.println("Not enough movies to recommend for this user!");
        }

        int k = 1;
        
        System.out.println("<table><tr><th class='column name'>POSTER</th><th class='column name'>TITLE</th><th class='column name'>GENRE</th><th class='column name'>DIRECTORS</th><th class='column name'>MINUTES</th><th class='column name'>YEAR</th><th class='column name'>RATING</th>");
        for( Rating rating: top10RecommendedMovies ){
           String currMovieID = rating.getItem();
           //System.out.println( "<tr><td>" + MDB.getTitle( rating.getItem() ) + "</td></tr>" );
           System.out.println("<tr><td>" + "<img src= \"" + MDB.getPoster(currMovieID) + "\"width=200px height=200px/a> </td>");
                System.out.println("<td>" + MDB.getTitle(currMovieID) + "</td>");
                System.out.println("<td>" + MDB.getCountry(currMovieID)+ "</td>");
                System.out.println("<td>" + MDB.getGenres(currMovieID)+ "</td>");
                System.out.println("<td>" + MDB.getMinutes(currMovieID)+ "</td>");
                System.out.println("<td>" + MDB.getYear(currMovieID)+ "</td>");
                System.out.println("<td>" + rating.getValue() + "</td></tr>");
           k++;
           
           if( k == 11 ){
               break;
           }
        }
        System.out.println("</table>");
        System.out.println("<style>table, tr, td, th {font-size: 1.1em; border: solid; border-width:1px; border-color: solid black;margin: 0px 2px 15px 2px;padding: 9px 17px 5px 17px;font-family: Verdana, Geneva, sans-serif;}</style>");
        System.out.println("<style>table{width: 100%;text-align: center;background-color: #cce6ff;}</style>");
        System.out.println("<style>th{background-color: #ff9999;}</style>");
    }
}
