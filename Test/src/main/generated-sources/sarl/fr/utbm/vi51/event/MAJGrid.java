package fr.utbm.vi51.event;

import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Event;
import java.awt.Point;
import java.util.List;
import javax.annotation.Generated;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class MAJGrid extends Event {
  public List<Point> list;
  
  public MAJGrid(final List<Point> list) {
    this.list = list;
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
    MAJGrid other = (MAJGrid) obj;
    if (this.list == null) {
      if (other.list != null)
        return false;
    } else if (!this.list.equals(other.list))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.list== null) ? 0 : this.list.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the MAJGrid event's attributes only.
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("list  = ").append(this.list);
    return result.toString();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private final static long serialVersionUID = -1514341181L;
}
