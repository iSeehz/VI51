package fr.utbm.vi51.agent;

import fr.utbm.vi51.event.AreYouAwoken;
import fr.utbm.vi51.event.GiveBody;
import fr.utbm.vi51.event.IamAwoken;
import fr.utbm.vi51.event.Influence;
import fr.utbm.vi51.event.PerceptionEvent;
import fr.utbm.vi51.event.WantPerception;
import fr.utbm.vi51.model.PossibleMove;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.EarlyExit;
import io.sarl.lang.annotation.FiredEvent;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Percept;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.util.Scopes;
import java.util.UUID;
import javax.annotation.Generated;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class LemmingAgent extends Agent {
  protected Boolean presentation;
  
  protected int index;
  
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
    this.presentation = Boolean.valueOf(false);
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  private boolean _eventhandler_guard_AreYouAwoken_1(final AreYouAwoken it, final AreYouAwoken occurrence) {
    return (!(this.presentation).booleanValue());
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private Boolean _eventhandler_body_AreYouAwoken_1(final AreYouAwoken occurrence) {
    Boolean _xblockexpression = null;
    {
      IamAwoken _iamAwoken = new IamAwoken();
      Address _source = occurrence.getSource();
      Scope<Address> _addresses = Scopes.addresses(_source);
      this.emit(_iamAwoken, _addresses);
      _xblockexpression = this.presentation = Boolean.valueOf(true);
    }
    return _xblockexpression;
  }
  
  @Percept
  public void _handle_AreYouAwoken_1(final AreYouAwoken occurrence) {
    if (_eventhandler_guard_AreYouAwoken_1(occurrence, occurrence)) {
      _eventhandler_body_AreYouAwoken_1(occurrence);
    }
  }
  
  @Percept
  public void _handle_GiveBody_2(final GiveBody occurrence) {
    this.index = occurrence.index;
    WantPerception _wantPerception = new WantPerception();
    Address _source = occurrence.getSource();
    Scope<Address> _addresses = Scopes.addresses(_source);
    this.emit(_wantPerception, _addresses);
  }
  
  @Percept
  public void _handle_PerceptionEvent_3(final PerceptionEvent occurrence) {
    this.println("bien reçu capitaine !");
    Influence _influence = new Influence(PossibleMove.RIGHT);
    Address _source = occurrence.getSource();
    Scope<Address> _addresses = Scopes.addresses(_source);
    this.emit(_influence, _addresses);
  }
  
  @Percept
  public void _handle_Destroy_4(final Destroy occurrence) {
    this.println("GoodBye World !");
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void emit(final Event e) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(e);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event,io.sarl.lang.core.Scope<io.sarl.lang.core.Address>)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event,io.sarl.lang.core.Scope<io.sarl.lang.core.Address>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void emit(final Event e, final Scope<Address> scope) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(e, scope);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#getDefaultAddress()}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#getDefaultAddress()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected Address getDefaultAddress() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultAddress();
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#getDefaultContext()}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#getDefaultContext()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected AgentContext getDefaultContext() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultContext();
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#getDefaultSpace()}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#getDefaultSpace()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected EventSpace getDefaultSpace() {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).getDefaultSpace();
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultContext(io.sarl.lang.core.AgentContext)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultContext(io.sarl.lang.core.AgentContext)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultContext(final AgentContext context) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultContext(context);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultContext(java.util.UUID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultContext(java.util.UUID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultContext(final UUID contextID) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultContext(contextID);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.Space)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.Space)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final Space space) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(space);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.SpaceID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.SpaceID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final SpaceID space) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(space);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(java.util.UUID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(java.util.UUID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final UUID space) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(space);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isInDefaultSpace(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isInDefaultSpace(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isInDefaultSpace(final Event event) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isInDefaultSpace(event);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#receive(java.util.UUID,io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#receive(java.util.UUID,io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void receive(final UUID receiver, final Event e) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).receive(receiver, e);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#spawn(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.lang.Object[])}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#spawn(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected UUID spawn(final Class<? extends Agent> aAgent, final Object... params) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).spawn(aAgent, params);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#debug(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#debug(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void debug(final Object message) {
    getSkill(io.sarl.core.Logging.class).debug(message);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#error(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#error(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void error(final Object message) {
    getSkill(io.sarl.core.Logging.class).error(message);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#error(java.lang.Object,java.lang.Throwable)}.
   * 
   * @see io.sarl.core.Logging#error(java.lang.Object,java.lang.Throwable)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void error(final Object message, final Throwable exception) {
    getSkill(io.sarl.core.Logging.class).error(message, exception);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#getLogLevel()}.
   * 
   * @see io.sarl.core.Logging#getLogLevel()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected int getLogLevel() {
    return getSkill(io.sarl.core.Logging.class).getLogLevel();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#info(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#info(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void info(final Object message) {
    getSkill(io.sarl.core.Logging.class).info(message);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#isDebugLogEnabled()}.
   * 
   * @see io.sarl.core.Logging#isDebugLogEnabled()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected boolean isDebugLogEnabled() {
    return getSkill(io.sarl.core.Logging.class).isDebugLogEnabled();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#isErrorLogEnabled()}.
   * 
   * @see io.sarl.core.Logging#isErrorLogEnabled()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected boolean isErrorLogEnabled() {
    return getSkill(io.sarl.core.Logging.class).isErrorLogEnabled();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#isInfoLogEnabled()}.
   * 
   * @see io.sarl.core.Logging#isInfoLogEnabled()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected boolean isInfoLogEnabled() {
    return getSkill(io.sarl.core.Logging.class).isInfoLogEnabled();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#isWarningLogEnabled()}.
   * 
   * @see io.sarl.core.Logging#isWarningLogEnabled()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected boolean isWarningLogEnabled() {
    return getSkill(io.sarl.core.Logging.class).isWarningLogEnabled();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#println(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#println(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void println(final Object message) {
    getSkill(io.sarl.core.Logging.class).println(message);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#setLogLevel(int)}.
   * 
   * @see io.sarl.core.Logging#setLogLevel(int)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void setLogLevel(final int level) {
    getSkill(io.sarl.core.Logging.class).setLogLevel(level);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#setLoggingName(java.lang.String)}.
   * 
   * @see io.sarl.core.Logging#setLoggingName(java.lang.String)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void setLoggingName(final String name) {
    getSkill(io.sarl.core.Logging.class).setLoggingName(name);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#warning(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#warning(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void warning(final Object message) {
    getSkill(io.sarl.core.Logging.class).warning(message);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#warning(java.lang.Object,java.lang.Throwable)}.
   * 
   * @see io.sarl.core.Logging#warning(java.lang.Object,java.lang.Throwable)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void warning(final Object message, final Throwable exception) {
    getSkill(io.sarl.core.Logging.class).warning(message, exception);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#killMe()}.
   * 
   * @see io.sarl.core.Lifecycle#killMe()
   */
  @EarlyExit
  @FiredEvent({ AgentKilled.class, Destroy.class })
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected void killMe() {
    getSkill(io.sarl.core.Lifecycle.class).killMe();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContext(java.lang.Class<? extends io.sarl.lang.core.Agent>,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContext(final Class<? extends Agent> agentClass, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(agentClass, context, params);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContextWithID(final Class<? extends Agent> agentClass, final UUID agentID, final AgentContext context, final Object... params) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(agentClass, agentID, context, params);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#asEventListener()}.
   * 
   * @see io.sarl.core.Behaviors#asEventListener()
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected EventListener asEventListener() {
    return getSkill(io.sarl.core.Behaviors.class).asEventListener();
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#registerBehavior(io.sarl.lang.core.Behavior)}.
   * 
   * @see io.sarl.core.Behaviors#registerBehavior(io.sarl.lang.core.Behavior)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected Behavior registerBehavior(final Behavior attitude) {
    return getSkill(io.sarl.core.Behaviors.class).registerBehavior(attitude);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#unregisterBehavior(io.sarl.lang.core.Behavior)}.
   * 
   * @see io.sarl.core.Behaviors#unregisterBehavior(io.sarl.lang.core.Behavior)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected Behavior unregisterBehavior(final Behavior attitude) {
    return getSkill(io.sarl.core.Behaviors.class).unregisterBehavior(attitude);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#wake(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.Behaviors#wake(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected void wake(final Event evt) {
    getSkill(io.sarl.core.Behaviors.class).wake(evt);
  }
  
  /**
   * Construct an agent.
   * @param builtinCapacityProvider - provider of the built-in capacities.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Inject
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  public LemmingAgent(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
}
