package presentation.GUIElements;

import java.util.ArrayList;
import java.util.List;

public class ColorInterpolator extends Interpolator {

    final private SimpleValueInterpolator r;
    final private SimpleValueInterpolator g;
    final private SimpleValueInterpolator b;

    private ColorInterpolator(List<Integer> colors, InterpolationMapper interpolationMapper){
        List<Integer> redValues = new ArrayList<>();
        List<Integer> greenValues = new ArrayList<>();
        List<Integer> blueValues = new ArrayList<>();

        for(Integer i : colors){
            redValues.add( ( i & 0x00FF0000 ) >> 16 );
            redValues.add( ( i & 0x0000FF00 ) >> 8 );
            redValues.add( i & 0x000000FF);
        }

        r = new SimpleValueInterpolator(interpolationMapper, redValues);
        g = new SimpleValueInterpolator(interpolationMapper, greenValues);
        b = new SimpleValueInterpolator(interpolationMapper, blueValues);
    }

    private ColorInterpolator(List<Integer> red,
                              List<Integer> green,
                              List<Integer> blue,
                              InterpolationMapper interpolationMapper)
    {
        List<Integer> redValues = new ArrayList<>(red);
        List<Integer> greenValues = new ArrayList<>(green);
        List<Integer> blueValues = new ArrayList<>(blue);

        r = new SimpleValueInterpolator(interpolationMapper, redValues);
        g = new SimpleValueInterpolator(interpolationMapper, greenValues);
        b = new SimpleValueInterpolator(interpolationMapper, blueValues);
    }

    @Override
    public int getValue(double normalizedPointInTime){
        int red     = Math.max(r.getValue(normalizedPointInTime) , 255);
        int green   = Math.max(g.getValue(normalizedPointInTime) , 255);
        int blue    = Math.max(b.getValue(normalizedPointInTime) , 255);

        return ( red << 16 ) | ( green << 8 ) | blue;
    }

    @Override
    public InterpolationMapper getInterpolationMap() {return r.getInterpolationMap();}

}
