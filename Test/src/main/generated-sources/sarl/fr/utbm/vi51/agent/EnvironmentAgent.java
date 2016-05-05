package fr.utbm.vi51.agent;

import fr.utbm.vi51.agent.LemmingAgent;
import fr.utbm.vi51.event.AreYouAwoken;
import fr.utbm.vi51.event.CreateLemmingsAgent;
import fr.utbm.vi51.event.IamAwoken;
import fr.utbm.vi51.event.StopSimulation;
import fr.utbm.vi51.gui.FrameProject;
import fr.utbm.vi51.model.EnvironmentModel;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.AgentTask;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Generated;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * @author tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class EnvironmentAgent extends Agent {
  protected AgentTask initAgent;
  
  protected List<Address> listOfGUID;
  
  protected EnvironmentModel environment;
  
  protected FrameProject gui;
  
  protected int numberOfLemmingsMinds;
  
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
    ArrayList<Address> _arrayList = new ArrayList<Address>();
    this.listOfGUID = _arrayList;
    this.numberOfLemmingsMinds = 1;
    EnvironmentModel _environmentModel = new EnvironmentModel("lab_parachute.txt", this.numberOfLemmingsMinds);
    this.environment = _environmentModel;
    FrameProject _frameProject = new FrameProject(this.environment);
    this.gui = _frameProject;
    CreateLemmingsAgent _createLemmingsAgent = new CreateLemmingsAgent();
    this.wake(_createLemmingsAgent);
  }
  
  @Percept
  public void _handle_CreateLemmingsAgent_1(final CreateLemmingsAgent occurrence) {
    int i = 0;
    for (i = 0; (i < this.numberOfLemmingsMinds); i++) {
      this.spawn(LemmingAgent.class);
    }
    AgentTask _task = this.task("presentation");
    this.initAgent = _task;
    final Procedure1<Agent> _function = (Agent it) -> {
      AreYouAwoken _areYouAwoken = new AreYouAwoken();
      this.emit(_areYouAwoken);
    };
    this.every(this.initAgent, 1000, _function);
  }
  
  @Percept
  public void _handle_IamAwoken_2(final IamAwoken occurrence) {
    Address _source = occurrence.getSource();
    this.listOfGUID.add(_source);
    int _size = this.listOfGUID.size();
    boolean _equals = (_size == this.numberOfLemmingsMinds);
    if (_equals) {
      this.cancel(this.initAgent);
    }
  }
  
  @Percept
  public void _handle_StopSimulation_3(final StopSimulation occurrence) {
    this.killMe();
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
   * See the capacity {@link io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask)}.
   * 
   * @see io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected boolean cancel(final AgentTask task) {
    return getSkill(io.sarl.core.Schedules.class).cancel(task);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask,boolean)}.
   * 
   * @see io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask,boolean)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected boolean cancel(final AgentTask task, final boolean mayInterruptIfRunning) {
    return getSkill(io.sarl.core.Schedules.class).cancel(task, mayInterruptIfRunning);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#every(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#every(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask every(final long period, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).every(period, procedure);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#every(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#every(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask every(final AgentTask task, final long period, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).every(task, period, procedure);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#in(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#in(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask in(final long delay, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).in(delay, procedure);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#in(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#in(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask in(final AgentTask task, final long delay, final Procedure1<? super Agent> procedure) {
    return getSkill(io.sarl.core.Schedules.class).in(task, delay, procedure);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#task(java.lang.String)}.
   * 
   * @see io.sarl.core.Schedules#task(java.lang.String)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask task(final String name) {
    return getSkill(io.sarl.core.Schedules.class).task(name);
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
  public EnvironmentAgent(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
}
