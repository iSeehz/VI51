package fr.utbm.vi51.agent;

import com.google.common.base.Objects;
import fr.utbm.vi51.agent.LemmingAgent;
import fr.utbm.vi51.agent.SimulationState;
import fr.utbm.vi51.controller.Controller;
import fr.utbm.vi51.event.AreYouAwoken;
import fr.utbm.vi51.event.ChangeLevel;
import fr.utbm.vi51.event.CreateLemmingsAgent;
import fr.utbm.vi51.event.GarbageAgent;
import fr.utbm.vi51.event.GiveBody;
import fr.utbm.vi51.event.IamAwoken;
import fr.utbm.vi51.event.Influence;
import fr.utbm.vi51.event.KillLemmingsAgents;
import fr.utbm.vi51.event.MAJGrid;
import fr.utbm.vi51.event.MAJTable;
import fr.utbm.vi51.event.Manager;
import fr.utbm.vi51.event.PerceptionEvent;
import fr.utbm.vi51.event.ResetAgentEnvironment;
import fr.utbm.vi51.event.ResetGrid;
import fr.utbm.vi51.event.StartSimulation;
import fr.utbm.vi51.event.StepByStepSimulation;
import fr.utbm.vi51.event.StopSimulation;
import fr.utbm.vi51.event.TerminateSimulation;
import fr.utbm.vi51.event.WantPerception;
import fr.utbm.vi51.gui.FrameProject;
import fr.utbm.vi51.gui.GridPanel;
import fr.utbm.vi51.gui.MainPanel;
import fr.utbm.vi51.gui.OptionPanel;
import fr.utbm.vi51.model.Cell;
import fr.utbm.vi51.model.EnvironmentModel;
import fr.utbm.vi51.model.LemmingBody;
import fr.utbm.vi51.model.PossibleMove;
import fr.utbm.vi51.time.Clock;
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
import io.sarl.util.Scopes;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Generated;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author tiboty
 */
@SarlSpecification("0.3")
@SuppressWarnings("all")
public class EnvironmentAgent extends Agent {
  protected AgentTask initAgent;
  
  protected List<Address> listOfGUID;
  
  protected HashMap<Address, Integer> mapOfGUID;
  
  protected EnvironmentModel environment;
  
  protected FrameProject gui;
  
  protected int numberOfLemmingsMinds;
  
  protected int numberOfLemmingsBodyLinked = 0;
  
  protected SimulationState state;
  
  protected HashMap<Integer, PossibleMove> moveInfluences;
  
  protected String currentLevel;
  
  protected String nextLevel;
  
  @Percept
  public void _handle_Initialize_0(final Initialize occurrence) {
    this.currentLevel = "lab_parachute.txt";
    this.nextLevel = "lab_parachute.txt";
    this.numberOfLemmingsMinds = 1;
    ResetAgentEnvironment _resetAgentEnvironment = new ResetAgentEnvironment();
    this.wake(_resetAgentEnvironment);
    EventSpace _defaultSpace = this.getDefaultSpace();
    Address _defaultAddress = this.getDefaultAddress();
    Controller controller = new Controller(_defaultSpace, _defaultAddress);
    FrameProject _frameProject = new FrameProject(this.environment, controller);
    this.gui = _frameProject;
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
  public void _handle_ResetAgentEnvironment_2(final ResetAgentEnvironment occurrence) {
    this.state = SimulationState.INIT;
    HashMap<Address, Integer> _hashMap = new HashMap<Address, Integer>();
    this.mapOfGUID = _hashMap;
    HashMap<Integer, PossibleMove> _hashMap_1 = new HashMap<Integer, PossibleMove>();
    this.moveInfluences = _hashMap_1;
    ArrayList<Address> _arrayList = new ArrayList<Address>();
    this.listOfGUID = _arrayList;
    EnvironmentModel _environmentModel = new EnvironmentModel(this.nextLevel, this.numberOfLemmingsMinds);
    this.environment = _environmentModel;
    boolean _notEquals = (!Objects.equal(this.currentLevel, this.nextLevel));
    if (_notEquals) {
      ChangeLevel _changeLevel = new ChangeLevel(this.nextLevel);
      this.wake(_changeLevel);
      this.currentLevel = this.nextLevel;
    } else {
      ResetGrid _resetGrid = new ResetGrid();
      this.wake(_resetGrid);
    }
    CreateLemmingsAgent _createLemmingsAgent = new CreateLemmingsAgent();
    this.wake(_createLemmingsAgent);
  }
  
  @Percept
  public void _handle_IamAwoken_3(final IamAwoken occurrence) {
    Address _source = occurrence.getSource();
    this.listOfGUID.add(_source);
    int _size = this.listOfGUID.size();
    boolean _equals = (_size == this.numberOfLemmingsMinds);
    if (_equals) {
      this.cancel(this.initAgent);
      for (final Address adr : this.listOfGUID) {
        {
          Cell _entry = this.environment.getEntry();
          List<LemmingBody> _listOfBodyInCell = _entry.getListOfBodyInCell();
          int _indexOf = this.listOfGUID.indexOf(adr);
          LemmingBody _get = _listOfBodyInCell.get(_indexOf);
          int _id = _get.getId();
          this.mapOfGUID.put(adr, Integer.valueOf(_id));
          Cell _entry_1 = this.environment.getEntry();
          List<LemmingBody> _listOfBodyInCell_1 = _entry_1.getListOfBodyInCell();
          int _indexOf_1 = this.listOfGUID.indexOf(adr);
          LemmingBody _get_1 = _listOfBodyInCell_1.get(_indexOf_1);
          int _id_1 = _get_1.getId();
          GiveBody _giveBody = new GiveBody(_id_1);
          Scope<Address> _addresses = Scopes.addresses(adr);
          this.emit(_giveBody, _addresses);
        }
      }
    }
  }
  
  @Percept
  public void _handle_WantPerception_4(final WantPerception occurrence) {
    int _numberOfLemmingsBodyLinked = this.numberOfLemmingsBodyLinked;
    this.numberOfLemmingsBodyLinked = (_numberOfLemmingsBodyLinked + 1);
    int _size = this.mapOfGUID.size();
    boolean _equals = (_size == this.numberOfLemmingsBodyLinked);
    if (_equals) {
      this.println("Ready to go !");
    }
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  private boolean _eventhandler_guard_StartSimulation_5(final StartSimulation it, final StartSimulation occurrence) {
    boolean _notEquals = (!Objects.equal(this.state, SimulationState.START));
    return _notEquals;
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private void _eventhandler_body_StartSimulation_5(final StartSimulation occurrence) {
    this.state = SimulationState.START;
    this.println("lancement de le simulation en auto");
    this.sendPerceptionsToAgents();
    MainPanel _mainPanel = this.gui.getMainPanel();
    Clock _time = _mainPanel.getTime();
    _time.start();
  }
  
  @Percept
  public void _handle_StartSimulation_5(final StartSimulation occurrence) {
    if (_eventhandler_guard_StartSimulation_5(occurrence, occurrence)) {
      _eventhandler_body_StartSimulation_5(occurrence);
    }
  }
  
  protected void sendPerceptionsToAgents() {
    Set<Address> _keySet = this.mapOfGUID.keySet();
    Iterator<Address> keySetIterator = _keySet.iterator();
    while (keySetIterator.hasNext()) {
      {
        Address key = keySetIterator.next();
        ArrayList<fr.utbm.vi51.model.Percept> _arrayList = new ArrayList<fr.utbm.vi51.model.Percept>();
        PerceptionEvent _perceptionEvent = new PerceptionEvent(_arrayList);
        Scope<Address> _addresses = Scopes.addresses(key);
        this.emit(_perceptionEvent, _addresses);
      }
    }
  }
  
  @Percept
  public void _handle_StepByStepSimulation_6(final StepByStepSimulation occurrence) {
    this.state = SimulationState.STEP_BY_STEP;
    MainPanel _mainPanel = this.gui.getMainPanel();
    Clock _time = _mainPanel.getTime();
    _time.start();
    this.sendPerceptionsToAgents();
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  private boolean _eventhandler_guard_StopSimulation_7(final StopSimulation it, final StopSimulation occurrence) {
    boolean _notEquals = (!Objects.equal(this.state, SimulationState.STOP));
    return _notEquals;
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private void _eventhandler_body_StopSimulation_7(final StopSimulation occurrence) {
    boolean _notEquals = (!Objects.equal(this.state, SimulationState.INIT));
    if (_notEquals) {
      MainPanel _mainPanel = this.gui.getMainPanel();
      Clock _time = _mainPanel.getTime();
      _time.pause();
      MainPanel _mainPanel_1 = this.gui.getMainPanel();
      Clock _time_1 = _mainPanel_1.getTime();
      _time_1.restart();
      this.state = SimulationState.STOP;
      int _size = this.listOfGUID.size();
      String _plus = ("fin simulation : Terminaison de " + Integer.valueOf(_size));
      String _plus_1 = (_plus + " agent(s) !");
      this.println(_plus_1);
      this.numberOfLemmingsBodyLinked = 0;
      this.mapOfGUID.clear();
      KillLemmingsAgents _killLemmingsAgents = new KillLemmingsAgents();
      this.wake(_killLemmingsAgents);
      this.listOfGUID.clear();
      OptionPanel _optionPanel = this.gui.getOptionPanel();
      _optionPanel.updateTab(0, 0, 0);
      ResetAgentEnvironment _resetAgentEnvironment = new ResetAgentEnvironment();
      this.wake(_resetAgentEnvironment);
    } else {
      this.println("Simulation non lancée");
    }
  }
  
  @Percept
  public void _handle_StopSimulation_7(final StopSimulation occurrence) {
    if (_eventhandler_guard_StopSimulation_7(occurrence, occurrence)) {
      _eventhandler_body_StopSimulation_7(occurrence);
    }
  }
  
  @Percept
  public void _handle_ChangeLevel_8(final ChangeLevel occurrence) {
    this.nextLevel = occurrence.level;
    boolean _equals = Objects.equal(this.state, SimulationState.INIT);
    if (_equals) {
      boolean _or = false;
      boolean _equals_1 = Objects.equal(this.state, SimulationState.STOP);
      if (_equals_1) {
        _or = true;
      } else {
        boolean _equals_2 = Objects.equal(this.state, SimulationState.INIT);
        _or = _equals_2;
      }
      if (_or) {
        EnvironmentModel _environment = this.gui.getEnvironment();
        _environment.setGrid(occurrence.level, this.numberOfLemmingsMinds);
        MainPanel _mainPanel = this.gui.getMainPanel();
        GridPanel _gridPanel = _mainPanel.getGridPanel();
        EnvironmentModel _environment_1 = this.gui.getEnvironment();
        List<List<Cell>> _grid = _environment_1.getGrid();
        _gridPanel.generate(_grid);
      }
    }
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  private boolean _eventhandler_guard_Influence_9(final Influence it, final Influence occurrence) {
    boolean _notEquals = (!Objects.equal(this.state, SimulationState.STOP));
    return _notEquals;
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private void _eventhandler_body_Influence_9(final Influence occurrence) {
    Address _source = occurrence.getSource();
    Integer _get = this.mapOfGUID.get(_source);
    this.println(_get);
    this.println(occurrence.move);
    Address _source_1 = occurrence.getSource();
    Integer _get_1 = this.mapOfGUID.get(_source_1);
    this.moveInfluences.put(_get_1, occurrence.move);
    int _size = this.moveInfluences.size();
    boolean _equals = (_size == this.numberOfLemmingsMinds);
    if (_equals) {
      List<Point> l = new ArrayList<Point>();
      List<List<Cell>> _grid = this.environment.getGrid();
      Cell _entry = this.environment.getEntry();
      int _x = _entry.getX();
      List<Cell> _get_2 = _grid.get(_x);
      Cell _entry_1 = this.environment.getEntry();
      int _y = _entry_1.getY();
      Cell _get_3 = _get_2.get(_y);
      List<LemmingBody> _listOfBodyInCell = _get_3.getListOfBodyInCell();
      int _size_1 = _listOfBodyInCell.size();
      boolean _notEquals = (_size_1 != 0);
      if (_notEquals) {
        List<List<Cell>> _grid_1 = this.environment.getGrid();
        Cell _entry_2 = this.environment.getEntry();
        int _x_1 = _entry_2.getX();
        List<Cell> _get_4 = _grid_1.get(_x_1);
        Cell _entry_3 = this.environment.getEntry();
        int _y_1 = _entry_3.getY();
        int _plus = (_y_1 + 1);
        Cell _get_5 = _get_4.get(_plus);
        List<LemmingBody> _listOfBodyInCell_1 = _get_5.getListOfBodyInCell();
        List<List<Cell>> _grid_2 = this.environment.getGrid();
        Cell _entry_4 = this.environment.getEntry();
        int _x_2 = _entry_4.getX();
        List<Cell> _get_6 = _grid_2.get(_x_2);
        Cell _entry_5 = this.environment.getEntry();
        int _y_2 = _entry_5.getY();
        Cell _get_7 = _get_6.get(_y_2);
        List<LemmingBody> _listOfBodyInCell_2 = _get_7.getListOfBodyInCell();
        LemmingBody _get_8 = _listOfBodyInCell_2.get(0);
        _listOfBodyInCell_1.add(_get_8);
        Cell _entry_6 = this.environment.getEntry();
        int _x_3 = _entry_6.getX();
        Cell _entry_7 = this.environment.getEntry();
        int _y_3 = _entry_7.getY();
        int _plus_1 = (_y_3 + 1);
        Point _point = new Point(_x_3, _plus_1);
        l.add(_point);
        List<List<Cell>> _grid_3 = this.environment.getGrid();
        Cell _entry_8 = this.environment.getEntry();
        int _x_4 = _entry_8.getX();
        List<Cell> _get_9 = _grid_3.get(_x_4);
        Cell _entry_9 = this.environment.getEntry();
        int _y_4 = _entry_9.getY();
        Cell _get_10 = _get_9.get(_y_4);
        List<LemmingBody> _listOfBodyInCell_3 = _get_10.getListOfBodyInCell();
        _listOfBodyInCell_3.remove(0);
        Cell _entry_10 = this.environment.getEntry();
        int _x_5 = _entry_10.getX();
        Cell _entry_11 = this.environment.getEntry();
        int _y_5 = _entry_11.getY();
        Point _point_1 = new Point(_x_5, _y_5);
        l.add(_point_1);
      }
      this.moveInfluences.clear();
      MAJGrid _mAJGrid = new MAJGrid(l);
      this.wake(_mAJGrid);
    }
  }
  
  @Percept
  public void _handle_Influence_9(final Influence occurrence) {
    if (_eventhandler_guard_Influence_9(occurrence, occurrence)) {
      _eventhandler_body_Influence_9(occurrence);
    }
  }
  
  @Percept
  public void _handle_GarbageAgent_10(final GarbageAgent occurrence) {
    Set<Address> _keySet = this.mapOfGUID.keySet();
    Iterator<Address> keySetIterator = _keySet.iterator();
    boolean find = false;
    while (((!find) && keySetIterator.hasNext())) {
      {
        Address key = keySetIterator.next();
        Integer _get = this.mapOfGUID.get(key);
        boolean _equals = Objects.equal(_get, occurrence.bodyIndex);
        if (_equals) {
          Destroy _destroy = new Destroy();
          Scope<Address> _addresses = Scopes.addresses(key);
          this.emit(_destroy, _addresses);
          find = true;
          this.mapOfGUID.remove(key);
          int _indexOf = this.listOfGUID.indexOf(key);
          this.listOfGUID.remove(_indexOf);
        }
      }
    }
  }
  
  @Percept
  public void _handle_Manager_11(final Manager occurrence) {
    try {
      Thread.sleep(1000);
      boolean _equals = Objects.equal(this.state, SimulationState.START);
      if (_equals) {
        this.sendPerceptionsToAgents();
      } else {
        boolean _equals_1 = Objects.equal(this.state, SimulationState.STEP_BY_STEP);
        if (_equals_1) {
          MainPanel _mainPanel = this.gui.getMainPanel();
          Clock _time = _mainPanel.getTime();
          _time.pause();
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Percept
  public void _handle_MAJTable_12(final MAJTable occurrence) {
  }
  
  @Percept
  public void _handle_MAJGrid_13(final MAJGrid occurrence) {
    MainPanel _mainPanel = this.gui.getMainPanel();
    GridPanel _gridPanel = _mainPanel.getGridPanel();
    List<List<Cell>> _grid = this.environment.getGrid();
    _gridPanel.smartPaint(occurrence.list, _grid);
    Manager _manager = new Manager();
    this.wake(_manager);
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @Pure
  private boolean _eventhandler_guard_ResetGrid_14(final ResetGrid it, final ResetGrid occurrence) {
    boolean _notEquals = (!Objects.equal(this.gui, null));
    return _notEquals;
  }
  
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  private void _eventhandler_body_ResetGrid_14(final ResetGrid occurrence) {
    MainPanel _mainPanel = this.gui.getMainPanel();
    GridPanel _gridPanel = _mainPanel.getGridPanel();
    List<List<Cell>> _grid = this.environment.getGrid();
    _gridPanel.paint(_grid);
  }
  
  @Percept
  public void _handle_ResetGrid_14(final ResetGrid occurrence) {
    if (_eventhandler_guard_ResetGrid_14(occurrence, occurrence)) {
      _eventhandler_body_ResetGrid_14(occurrence);
    }
  }
  
  @Percept
  public void _handle_KillLemmingsAgents_15(final KillLemmingsAgents occurrence) {
    for (final Address adr : this.listOfGUID) {
      Destroy _destroy = new Destroy();
      Scope<Address> _addresses = Scopes.addresses(adr);
      this.emit(_destroy, _addresses);
    }
  }
  
  @Percept
  public void _handle_TerminateSimulation_16(final TerminateSimulation occurrence) {
    KillLemmingsAgents _killLemmingsAgents = new KillLemmingsAgents();
    this.wake(_killLemmingsAgents);
    this.killMe();
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void emit(final Event arg0) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event,io.sarl.lang.core.Scope<io.sarl.lang.core.Address>)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#emit(io.sarl.lang.core.Event,io.sarl.lang.core.Scope<io.sarl.lang.core.Address>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void emit(final Event arg0, final Scope<Address> arg1) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).emit(arg0, arg1);
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
  protected boolean isDefaultContext(final AgentContext arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultContext(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultContext(java.util.UUID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultContext(java.util.UUID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultContext(final UUID arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultContext(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.Space)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.Space)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final Space arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.SpaceID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(io.sarl.lang.core.SpaceID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final SpaceID arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isDefaultSpace(java.util.UUID)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isDefaultSpace(java.util.UUID)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isDefaultSpace(final UUID arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isDefaultSpace(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#isInDefaultSpace(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#isInDefaultSpace(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected boolean isInDefaultSpace(final Event arg0) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).isInDefaultSpace(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#receive(java.util.UUID,io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#receive(java.util.UUID,io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected void receive(final UUID arg0, final Event arg1) {
    getSkill(io.sarl.core.DefaultContextInteractions.class).receive(arg0, arg1);
  }
  
  /**
   * See the capacity {@link io.sarl.core.DefaultContextInteractions#spawn(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.lang.Object[])}.
   * 
   * @see io.sarl.core.DefaultContextInteractions#spawn(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  protected UUID spawn(final Class<? extends Agent> arg0, final Object... arg1) {
    return getSkill(io.sarl.core.DefaultContextInteractions.class).spawn(arg0, arg1);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#debug(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#debug(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void debug(final Object arg0) {
    getSkill(io.sarl.core.Logging.class).debug(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#error(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#error(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void error(final Object arg0) {
    getSkill(io.sarl.core.Logging.class).error(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#error(java.lang.Object,java.lang.Throwable)}.
   * 
   * @see io.sarl.core.Logging#error(java.lang.Object,java.lang.Throwable)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void error(final Object arg0, final Throwable arg1) {
    getSkill(io.sarl.core.Logging.class).error(arg0, arg1);
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
  protected void info(final Object arg0) {
    getSkill(io.sarl.core.Logging.class).info(arg0);
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
  protected void println(final Object arg0) {
    getSkill(io.sarl.core.Logging.class).println(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#setLogLevel(int)}.
   * 
   * @see io.sarl.core.Logging#setLogLevel(int)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void setLogLevel(final int arg0) {
    getSkill(io.sarl.core.Logging.class).setLogLevel(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#setLoggingName(java.lang.String)}.
   * 
   * @see io.sarl.core.Logging#setLoggingName(java.lang.String)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void setLoggingName(final String arg0) {
    getSkill(io.sarl.core.Logging.class).setLoggingName(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#warning(java.lang.Object)}.
   * 
   * @see io.sarl.core.Logging#warning(java.lang.Object)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void warning(final Object arg0) {
    getSkill(io.sarl.core.Logging.class).warning(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Logging#warning(java.lang.Object,java.lang.Throwable)}.
   * 
   * @see io.sarl.core.Logging#warning(java.lang.Object,java.lang.Throwable)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Logging.class)
  protected void warning(final Object arg0, final Throwable arg1) {
    getSkill(io.sarl.core.Logging.class).warning(arg0, arg1);
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
  protected UUID spawnInContext(final Class<? extends Agent> arg0, final AgentContext arg1, final Object... arg2) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContext(arg0, arg1, arg2);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])}.
   * 
   * @see io.sarl.core.Lifecycle#spawnInContextWithID(java.lang.Class<? extends io.sarl.lang.core.Agent>,java.util.UUID,io.sarl.lang.core.AgentContext,java.lang.Object[])
   */
  @FiredEvent(AgentSpawned.class)
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Lifecycle.class)
  protected UUID spawnInContextWithID(final Class<? extends Agent> arg0, final UUID arg1, final AgentContext arg2, final Object... arg3) {
    return getSkill(io.sarl.core.Lifecycle.class).spawnInContextWithID(arg0, arg1, arg2, arg3);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask)}.
   * 
   * @see io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected boolean cancel(final AgentTask arg0) {
    return getSkill(io.sarl.core.Schedules.class).cancel(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask,boolean)}.
   * 
   * @see io.sarl.core.Schedules#cancel(io.sarl.core.AgentTask,boolean)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected boolean cancel(final AgentTask arg0, final boolean arg1) {
    return getSkill(io.sarl.core.Schedules.class).cancel(arg0, arg1);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#every(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#every(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask every(final long arg0, final Procedure1<? super Agent> arg1) {
    return getSkill(io.sarl.core.Schedules.class).every(arg0, arg1);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#every(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#every(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask every(final AgentTask arg0, final long arg1, final Procedure1<? super Agent> arg2) {
    return getSkill(io.sarl.core.Schedules.class).every(arg0, arg1, arg2);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#in(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#in(long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask in(final long arg0, final Procedure1<? super Agent> arg1) {
    return getSkill(io.sarl.core.Schedules.class).in(arg0, arg1);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#in(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)}.
   * 
   * @see io.sarl.core.Schedules#in(io.sarl.core.AgentTask,long,org.eclipse.xtext.xbase.lib.Procedures$Procedure1<? extends java.lang.Object & super io.sarl.lang.core.Agent>)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask in(final AgentTask arg0, final long arg1, final Procedure1<? super Agent> arg2) {
    return getSkill(io.sarl.core.Schedules.class).in(arg0, arg1, arg2);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Schedules#task(java.lang.String)}.
   * 
   * @see io.sarl.core.Schedules#task(java.lang.String)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Schedules.class)
  protected AgentTask task(final String arg0) {
    return getSkill(io.sarl.core.Schedules.class).task(arg0);
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
  protected Behavior registerBehavior(final Behavior arg0) {
    return getSkill(io.sarl.core.Behaviors.class).registerBehavior(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#unregisterBehavior(io.sarl.lang.core.Behavior)}.
   * 
   * @see io.sarl.core.Behaviors#unregisterBehavior(io.sarl.lang.core.Behavior)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected Behavior unregisterBehavior(final Behavior arg0) {
    return getSkill(io.sarl.core.Behaviors.class).unregisterBehavior(arg0);
  }
  
  /**
   * See the capacity {@link io.sarl.core.Behaviors#wake(io.sarl.lang.core.Event)}.
   * 
   * @see io.sarl.core.Behaviors#wake(io.sarl.lang.core.Event)
   */
  @Generated("io.sarl.lang.jvmmodel.SARLJvmModelInferrer")
  @ImportedCapacityFeature(Behaviors.class)
  protected void wake(final Event arg0) {
    getSkill(io.sarl.core.Behaviors.class).wake(arg0);
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
