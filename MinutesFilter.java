
/**
 * Filter that targets at movies that are at least a specified minutes long
 * 
 * @Xiangzhen Sun
 * @2/19/2018
 */
public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;
    
    public MinutesFilter( int min, int max ) {
        minMinutes = min;
        maxMinutes = max;
    }
    
    @Override
    public boolean satisfies(String id) {
        int length = MovieDatabase.getMinutes(id);
        return length >= minMinutes && length <= maxMinutes;
    }
}
