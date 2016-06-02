package fr.utbm.vi51.event;

import fr.utbm.vi51.model.Orientation;
import fr.utbm.vi51.model.Percept;
import fr.utbm.vi51.qlearning.MoveProb;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Event;
import java.util.List;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class PerceptionEvent extends Event {
  public List<Percept> perceptions;
  
  public int fatigue;
  
  public Orientation orientation;
  
  public boolean climbing;
  
  public MoveProb moveProb;
  
  public PerceptionEvent(final List<Percept> perceptions, final int fatigue, final Orientation orientation, final boolean climbing, final MoveProb moveProb) {
    this.perceptions = perceptions;
    this.fatigue = fatigue;
    this.orientation = orientation;
    this.climbing = climbing;
    this.moveProb = moveProb;
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PerceptionEvent other = (PerceptionEvent) obj;
    if (this.perceptions == null) {
      if (other.perceptions != null)
        return false;
    } else if (!this.perceptions.equals(other.perceptions))
      return false;
    if (other.fatigue != this.fatigue)
      return false;
    if (this.orientation == null) {
      if (other.orientation != null)
        return false;
    } else if (!this.orientation.equals(other.orientation))
      return false;
    if (other.climbing != this.climbing)
      return false;
    if (this.moveProb == null) {
      if (other.moveProb != null)
        return false;
    } else if (!this.moveProb.equals(other.moveProb))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.perceptions== null) ? 0 : this.perceptions.hashCode());
    result = prime * result + this.fatigue;
    result = prime * result + ((this.orientation== null) ? 0 : this.orientation.hashCode());
    result = prime * result + (this.climbing ? 1231 : 1237);
    result = prime * result + ((this.moveProb== null) ? 0 : this.moveProb.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the PerceptionEvent event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("perceptions  = ").append(this.perceptions);
    result.append("fatigue  = ").append(this.fatigue);
    result.append("orientation  = ").append(this.orientation);
    result.append("climbing  = ").append(this.climbing);
    result.append("moveProb  = ").append(this.moveProb);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = 374416011L;
}
