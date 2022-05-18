package presentation.GUIElements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import org.javatuples.Pair;


import java.util.HashMap;
import java.util.List;

abstract public class BFXRegion {

    /******************************************************************************************
     * These are the static variables belonging to the class
     * The interval at which the computation for interpolating happens
     *
     * and two constants for the calculation of the time
     * (to reverse an animation we count time backwards)
     */

    public static final double INTERPOLATION_INTERVAL_MILLIS = 7.0;

    public static final int FORWARD = 1;
    public static final int REVERSE = -1;


    /****************************************************************************************
     * Here we have the private variables
     * This class encapsulates most of the functionality that is shared between all
     *
     * Region inherits Parent which inherits Node
     *
     * Parent and Node don't offer us a lot of properties which make sense to animate
     * Region refers to elements that have size (on screen) and there are a lot of properties describing that size
     * and what's in it, or how it looks.
     *
     * However, some commonly used JavaFX classes that have potential to be animated don't inherit Region
     * Notable Examples:
     *
     * ImageView
     * Shape
     *
     * which all inherit Node. However, it doesn't make a lot of sens to animate ImageView properties.
     * And for Shape already existing classes work really well
     */

    private final Region region; /** The JavaFX element stored as a region Reference */

    private int animation_duration_millis = 0; /** Keeps the makimum duration of the animation */
    //TODO since are plenty of animation with various length animation duration should be tied to the EVENT, not global

    protected int animation_tick = 0;                           /************ PROTECTED */
    protected int animation_direction = 0;                      /************ PROTECTED */

    private EventList currentEvent = EventList.NONE;

    final private KeyFrame keyFrame = new KeyFrame(Duration.millis(INTERPOLATION_INTERVAL_MILLIS),
                            this::animationHandler);

    final protected Timeline timeline = new Timeline(keyFrame); /************ PROTECTED */

    final protected HashMap<EventList, HashMap<PropertiesList, Pair<Integer, SimpleValueInterpolator>>> interpolatorMap = new HashMap<>();
    final protected HashMap<EventList, HashMap<ColorPropertiesList, Pair<Integer, ColorInterpolator>>> colorInterpolatorMap = new HashMap<>();


    public BFXRegion(Region region) {
        this.region = region;

        timeline.setCycleCount(Timeline.INDEFINITE);

        setRegionCallbacks();
    }

    private void setRegionCallbacks(){
        region.setOnMouseEntered((e) -> {
            currentEvent = EventList.MOUSE_HOVER;
            animation_direction = FORWARD;
            timeline.play();
        });

        region.setOnMouseExited((e) -> {
            currentEvent = EventList.MOUSE_HOVER;
            //TODO: sometimes when you move your cursor fast the element
            animation_tick = (animation_tick == 0) ? animation_duration_millis : animation_tick;
            animation_direction = REVERSE;
            timeline.play();
        });

        setChildCallBacks();
    }

    abstract void setChildCallBacks();

    /**
     *  ADDING ANIMATION
     */
    public void addAnimation(List<Integer> values,
                             InterpolationMapper interpolationMapper,
                             PropertiesList property,
                             int durationMillis,
                             EventList event)
    {
        interpolatorMap.computeIfAbsent(event, k -> new HashMap<>());
        interpolatorMap.get(event).put(property, new Pair<>(durationMillis, new SimpleValueInterpolator(interpolationMapper, values)));

        this.animation_duration_millis = Math.max(this.animation_duration_millis, durationMillis);
    }

    /**
     * This is callback for keyFrame, it runs each INTERPOLATION_INTERVAL_MILLIS milliseconds
     *
     * It checks the current event and using the maps it knows what properties it should modify
     *
     * @param event
     * @see KeyFrame
     */
    public void animationHandler(ActionEvent event) {

        if(animation_tick > animation_duration_millis || animation_tick < 0)
        {
            currentEvent = EventList.NONE;
            animation_tick = 0;
            timeline.stop();
            return;
        }

        if(interpolatorMap.containsKey(currentEvent)){
            HashMap<PropertiesList, Pair<Integer, SimpleValueInterpolator>> temp = interpolatorMap.get(currentEvent);
            PropertiesList[] properties = temp.keySet().toArray(new PropertiesList[0]);

            for(PropertiesList p : properties) {
                Integer duration = temp.get(p).getValue0();
                SimpleValueInterpolator interpolator = temp.get(p).getValue1();

                if (animation_tick > duration) continue;

                double normalizedTime = (double)animation_tick/duration;

                double value = interpolator.getValue(normalizedTime);

                setRegionProperty(p, value);
            }
        }

        //TODO color / background / inset interpolation

        animation_tick += animation_direction*INTERPOLATION_INTERVAL_MILLIS;
    }

    private void setRegionProperty(PropertiesList property, double value){

        switch (property){
            case PREF_WIDTH -> region.setPrefWidth(value);
            case PREF_HEIGHT -> region.setPrefHeight(value);
            default -> setChildProperty(property,value);
        }
    }

    abstract void setChildProperty(PropertiesList property, double value);



}
