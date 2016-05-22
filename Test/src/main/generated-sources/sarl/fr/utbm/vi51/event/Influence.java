package fr.utbm.vi51.event;

import fr.utbm.vi51.model.PossibleMove;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Event;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class Influence extends Event {
  public PossibleMove move;
  
  public Influence(final PossibleMove move) {
    this.move = move;
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
    Influence other = (Influence) obj;
    if (this.move == null) {
      if (other.move != null)
        return false;
    } else if (!this.move.equals(other.move))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.move== null) ? 0 : this.move.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the Influence event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("move  = ").append(this.move);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = -1772231946L;
}
