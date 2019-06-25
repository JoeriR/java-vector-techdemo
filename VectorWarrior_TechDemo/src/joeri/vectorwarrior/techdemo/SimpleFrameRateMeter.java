package joeri.vectorwarrior.techdemo;

// source: https://stackoverflow.com/a/28287949
public class SimpleFrameRateMeter {
	private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
    
    public double handle(long now) {
        long oldFrameTime = frameTimes[frameTimeIndex] ;
        frameTimes[frameTimeIndex] = now ;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
        if (frameTimeIndex == 0) {
            arrayFilled = true ;
        }
        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime ;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
            double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
            //System.out.println(String.format("Current frame rate: %.3f", frameRate));
            
            return frameRate;
        }
        else {
        	return -1;
        }
    }
}
