package presentation.GUIElements;

abstract public class Interpolator {

    public static final InterpolationMapper LINEAR        = (input) -> input;
    public static final InterpolationMapper SQRT          = Math::sqrt;
    public static final InterpolationMapper SQUARED       = (input) -> Math.pow(input,2.0);
    public static final InterpolationMapper CUBED         = (input) -> Math.pow(input,3.0);
    public static final InterpolationMapper LOGARITHMIC   = (input) -> Math.log(input*(Math.E-1) + 1);
    public static final InterpolationMapper MIDDLE_STALL  = (input) -> 0.85 * Math.sin(input * 1.570796326) // x * pi/2
                                                                + 0.15 * Math.sin(input * 7.853981633); // x * pi * 2.5
    public static final InterpolationMapper WIGGLY        = (input) -> 0.85 * Math.sin(input * 1.570796326) // x * pi/2
                                                                + 0.15 * Math.sin(input * 39.26990816); // x * pi * 12.5

    abstract public int getValue(double normalizedPointInTime);

    abstract public InterpolationMapper getInterpolationMap();
}
