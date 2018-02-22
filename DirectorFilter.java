
/**
 * This class implements a fiter that targets on movies with specified director(s)
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */
public class DirectorFilter implements Filter {
    private String myDirectors;
    
    public DirectorFilter( String directors ) {
        myDirectors = directors;
    }
    
    @Override
    public boolean satisfies(String id) {
        String[] targetDirectors = myDirectors.split( ",\\s*" );
        String directors = MovieDatabase.getDirector(id);
        
        for( int i = 0; i < targetDirectors.length; i++ ) {
            if( directors.contains( targetDirectors[i] ) ) {
                return true;
            }
        }
        return false;
    }
}
