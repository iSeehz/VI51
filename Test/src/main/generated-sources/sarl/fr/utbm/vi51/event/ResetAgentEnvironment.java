package fr.utbm.vi51.event;

import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Event;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class ResetAgentEnvironment extends Event {
  public int numberOfLemmings;
  
  public String level;
  
  public ResetAgentEnvironment(final int numberOfLemmings, final String level) {
    this.numberOfLemmings = numberOfLemmings;
    this.level = level;
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
    ResetAgentEnvironment other = (ResetAgentEnvironment) obj;
    if (other.numberOfLemmings != this.numberOfLemmings)
      return false;
    if (this.level == null) {
      if (other.level != null)
        return false;
    } else if (!this.level.equals(other.level))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.numberOfLemmings;
    result = prime * result + ((this.level== null) ? 0 : this.level.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the ResetAgentEnvironment event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("numberOfLemmings  = ").append(this.numberOfLemmings);
    result.append("level  = ").append(this.level);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = -784323633L;
}
