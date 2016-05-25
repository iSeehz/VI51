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
public class GarbageAgent extends Event {
  public Integer bodyIndex;
  
  public GarbageAgent(final Integer bodyIndex) {
    this.bodyIndex = bodyIndex;
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
    GarbageAgent other = (GarbageAgent) obj;
    if (this.bodyIndex == null) {
      if (other.bodyIndex != null)
        return false;
    } else if (!this.bodyIndex.equals(other.bodyIndex))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.bodyIndex== null) ? 0 : this.bodyIndex.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the GarbageAgent event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("bodyIndex  = ").append(this.bodyIndex);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = 427243573L;
}
