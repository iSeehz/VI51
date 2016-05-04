package fr.utbm.vi51.event;

import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class StopSimulation extends Event {
  public Address addr;
  
  public StopSimulation(final Address ad) {
    this.addr = ad;
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
    StopSimulation other = (StopSimulation) obj;
    if (this.addr == null) {
      if (other.addr != null)
        return false;
    } else if (!this.addr.equals(other.addr))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.addr== null) ? 0 : this.addr.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the StopSimulation event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("addr  = ").append(this.addr);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = -1866870922L;
}
