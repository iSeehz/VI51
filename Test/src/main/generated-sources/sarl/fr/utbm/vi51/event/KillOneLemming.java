package fr.utbm.vi51.event;

import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import javax.annotation.Generated;

/**
 * @author Tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class KillOneLemming extends Event {
  /**
   * Construct an event. The source of the event is unknown.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public KillOneLemming() {
    super();
  }
  
  /**
   * Construct an event.
   * @param source - address of the agent that is emitting this event.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public KillOneLemming(final Address source) {
    super(source);
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = 588368462L;
}