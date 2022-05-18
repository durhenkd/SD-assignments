package presentation.GUIElements;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SimpleValueInterpolator extends Interpolator {

    /***
     * this in responsible for interpolating values, regardless of how many
     *
     * NOTE: this shouldn't be used to interpolate colors (ex: 0xABCDEF tp 0x123456)
     * because the color (and alpha) channels should be treated independently
     */

    final private ArrayList<Integer> values = new ArrayList<>();

    /** the smallest value from the list */
    final private int floor;
    /** the difference between the max value and floor (min) value*/
    final private int delta;

    /** the values being interpolated, normalized*/
    final private ArrayList<Double> normalizedValues = new ArrayList<>();

    /** responsible for modifying the behavior of the interpolator*/
    final private InterpolationMapper interpolationMap;

    /** what is the normalized size of the time interval between two values*/
    final double valueInterval;

    public SimpleValueInterpolator(InterpolationMapper interpolationMap, List<Integer> values) {

        if(!values.isEmpty()){
            this.floor = values.stream().min(Double::compare).get();
            this.delta = values.stream().max(Double::compare).get() - floor;

            this.values.addAll(values);
            this.normalizedValues.addAll(
                    values.stream().map(e -> (double)(e-floor)/delta).collect(Collectors.toList())
            );
        }else{
            throw new NoSuchElementException();
        }

        this.valueInterval = 1.0/(values.size()-1);

        this.interpolationMap = interpolationMap;

    }

    @Override
    public int getValue(double normalizedPointInTime) {
        if(values.size() == 1)
            return values.get(0);

        int index = (int) (normalizedPointInTime * (values.size()-1));          //find at what index we are
        if(index + 1 == values.size()) return values.get(index);

        /////map the intervalValue - sized interval between two values to [0...1]
        double innerInterval = normalizedPointInTime;                           //start from the point in time
        while (innerInterval > valueInterval) innerInterval -= valueInterval;   //reduce to [0 ... valueInterval];
        innerInterval *= (values.size()-1);                                         //map back to 0-1

        double normalizedValue = //get the normalized value between index and index+1
                (1-innerInterval) * normalizedValues.get(index) + innerInterval * normalizedValues.get(index+1);

        normalizedValue = interpolationMap.map(normalizedValue);                        //apply map function

        return (int) (normalizedValue*delta + floor);
    }

//    public void setValues(List<Long> values){
//        if(!values.isEmpty()){
//            this.floor = values.stream().min(Double::compare).get();
//            this.delta = values.stream().min(Double::compare).get() - floor;
//
//            this.values.addAll(values);
//            this.normalizedValues.addAll(
//                    values.stream().map(e -> (double)(e-floor)/delta).collect(Collectors.toList())
//            );
//        }
//    }

    public ArrayList<Integer> getValues() {return values;}
    public ArrayList<Double> getNormalizedValues() {return normalizedValues;}
    @Override public InterpolationMapper getInterpolationMap() {return interpolationMap;}

}
