
/**
 * This class processes the movie and ratings data and answers questions about them
 * 
 * Xiangzhen Sun 
 * 2/17/2018
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    /* This method should process every record from the CSV file whose name is filename,
       a file of movie information, and return an ArrayList of type Movie with all of
       the movie data from the file.
    */
    public ArrayList<Movie> loadMovies( String filename ) {
        ArrayList<Movie> ret = new ArrayList<Movie>();
        FileResource fr = new FileResource( filename );
        CSVParser movieParser = fr.getCSVParser();
        
        for( CSVRecord movieRec : movieParser ) {
            String id = movieRec.get( "id" );
            String title = movieRec.get( "title" );
            String year = movieRec.get( "year" );
            String country = movieRec.get( "country" );
            String genre = movieRec.get( "genre" );
            String director = movieRec.get( "director" );
            int minutes = Integer.parseInt( movieRec.get( "minutes" ) );
            String poster = movieRec.get( "poster" );
            Movie movie = new Movie( id, title, year, genre, director, country, poster, minutes );
            
            ret.add( movie );
        }
        
        return ret;
    }
    
    // this is a helper method. it searches raterList and returns the index of the Rater object
    // that has the same id as rater_id
    private int indexOfRater( String rater_id, ArrayList<Rater> raterList ) {
        if( raterList == null ) {
            return -1;
        }
        
        for( int i = 0; i < raterList.size(); i++ ) {
            if( rater_id.equals( raterList.get( i ).getID() ) ) {
                return i;
            }
        }
        
        return -1;
    }
    
    // Similar to loadMovies, this method load Raters information into memory
    public ArrayList<Rater> loadRaters( String filename ) {
        ArrayList<Rater> ret = new ArrayList<Rater>();
        FileResource fr = new FileResource( filename );
        CSVParser raterParser = fr.getCSVParser();
        
        for( CSVRecord raterRec : raterParser ) {
            String id = raterRec.get( "rater_id" );
            int index = indexOfRater( id, ret );
            String IMDB = raterRec.get( "movie_id" );
            double ratingValue = Double.parseDouble( raterRec.get( "rating" ) );
            
            if( index != -1 ) {  // if rater is already in ret
                Rater rater = ret.get( index );
                rater.addRating( IMDB, ratingValue );
                ret.set( index, rater );
            }
            else {  // if not, just add a new Rater object to ret
                // Rater rater = new PlainRater( id );
                Rater rater = new EfficientRater( id );
                rater.addRating( IMDB, ratingValue );
                ret.add( rater );
            }
        }
        
        return  ret;
    }
    
    // test method "loadMovies()"
    public void testLoadMovies() {
        String path = "data/";
        String filename = path + "ratedmoviesfull.csv";
        ArrayList<Movie> movieList = loadMovies( filename );
        
        // Print the number of movies, and print each movie
        System.out.println( "There are " + movieList.size()+ " movies in " + filename );
        /*
        for( Movie movie : movieList ) {
            System.out.println( movie );
        }
        */
        
        // calculate how many movies include the Comedy genre
        int numOfComedy = 0;
        for( Movie movie : movieList ) {
            if( movie.getGenres().contains( "Comedy" ) ) {
                numOfComedy++;
            }
        }
        System.out.println( "There are " + numOfComedy + " movies include Comedy genre" );
        
        // determine how may movies are longer than 150 minutes
        int numOfLongMovies = 0;
        for( Movie movie : movieList ) {
            if( movie.getMinutes() > 150 ) {
                numOfLongMovies++;
            }
        }
        System.out.println( "There are " + numOfLongMovies + " movies longer than 150 minutes" );
        
        // determine the maximum number of movies by any director,
        // and who the directors are that directed that many movies.
        int maxDirectedMovies = 1;
        HashMap<String, Integer> movieDirec = new HashMap<String, Integer>();
        for( Movie movie : movieList ) {
            String[] directors = movie.getDirector().split( ",\\s*" );
            
            for( int i = 0; i < directors.length; i++ ) {
                System.out.println( directors[i] );
                if( movieDirec.containsKey( directors[i] ) ) {
                    int numOfDirectedMovies = movieDirec.get( directors[i] ) + 1;
                    movieDirec.put( directors[i], numOfDirectedMovies );
                    
                    if( maxDirectedMovies < numOfDirectedMovies ) {
                        maxDirectedMovies = numOfDirectedMovies;
                    }
                }
                else {
                    movieDirec.put( directors[i], 1 );
                }
            }
        }
        
        System.out.println( "The most number of movies directed by an individual director is " +
        maxDirectedMovies + " ,and these directors are:" );
        for( String director : movieDirec.keySet() ) {
            if( movieDirec.get( director ) == maxDirectedMovies ) {
                System.out.println( "\t" + director );
            }
        }
        
    }
    
    // this method tests the "loadRaters()" method
    public void testLoadRaters() {
        String path = "data/";
        String filename = path + "ratings.csv";
        ArrayList<Rater> raterList = loadRaters( filename );
        
        // Print the number of movies, and print each movie
        System.out.println( "There are " + raterList.size()+ " raters in " + filename );
        /*
        for( Rater rater : raterList ) {
            System.out.println( "Rater " + rater.getID() + " completed " + rater.numRatings()
            + " ratings:" );
            for( String IMDB : rater.getItemsRated() ) {
                System.out.println( "\t" + IMDB + ", " + rater.getRating( IMDB ) );
            }
        }
        */
       
       // find the number of rating of a specific rater
       String rater_id = "193";
       int k = indexOfRater( rater_id, raterList );
       System.out.println( "Rater " + rater_id + " has " + raterList.get(k).numRatings() + " ratings" );
       
       // find all raters with the maximum number of ratings
       int maxNumOfRatings = 0;
       HashMap<Rater, Integer> movieRaters = new HashMap<Rater, Integer>();
       for( Rater rater : raterList ) {
           int numOfRatings = rater.numRatings();
           movieRaters.put( rater, numOfRatings );
           if( maxNumOfRatings < numOfRatings ) {
               maxNumOfRatings = numOfRatings;
           }
       }
       
        System.out.println( "The most number of ratings completed by an individual rater is " +
        maxNumOfRatings + " ,and these raters are:" );
       for( Rater rater : movieRaters.keySet() ) {
           if( maxNumOfRatings == movieRaters.get( rater ) ) {
               System.out.println( "\t" + rater.getID() );
           }
       }
       
       // find the number of ratings a particular movie has
       String IMBD = "1798709";
       int numOfRatings = 0;
       for( Rater rater : raterList ) {
           if( rater.hasRating( IMBD ) ) {
               numOfRatings++;
            }
        }
       System.out.println( "Movie " + IMBD + " was rated by " + numOfRatings + " raters" );
       
       // determine how many different movies have been rated by all raters
       ArrayList<String> movies = new ArrayList<String>();
       for( Rater rater : raterList ) {
           for( String ratedItem : rater.getItemsRated() ) {
               if( !movies.contains( ratedItem ) ) {
                   movies.add( ratedItem );
               }
           }
       }
       System.out.println( "There were " + movies.size() + " movies rated" );
    }
}
