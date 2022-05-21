import java.text.RuleBasedCollator;
import java.util.ArrayList;

public class Builder extends Logic {
  @Override
  public void run(Robot robot) {
    // TODO
    //
    // When implementing this method, the following messages might be printed where appropriate:
    //
    // System.out.printf( "Robot %02d : Builder attached some parts or relocated a completed
    // robot.%n" , ... ) ;
    // System.out.printf( "Robot %02d : Builder cannot build anything, waiting!%n"
    //   , ... ) ;
    // System.out.printf( "Robot %02d : Builder woke up, going back to work.%n"
    //   , ... ) ;
    ProductionLine pl = Runner.factory.productionLine;
    boolean moved = false;

    synchronized (pl) {
      while((boolean) Runner.get(robot, "isWaiting")) {
        try {
          pl.wait();
          if(Runner.factory.stopProduction) {
            break;
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      synchronized (System.out) {
        System.out.printf( "Robot %02d : Builder woke up, going back to work.%n", Runner.get(robot, "serialNo"));
      }
      if (!moved) {
        moved = attachArmToBase(pl);
      }
      if (!moved) {
        moved = attachPayloadToArmBase(pl);
      }
      if (!moved) {
        moved = attachLogicToPayloadArmBase(pl);
      }
      if (!moved) {
        moved = moveCompletedRobot(pl);
      }
      if (!moved) {
        synchronized (System.out) {
          System.out.printf("Robot %02d : Builder cannot build anything, waiting!%n",
              Runner.get(robot, "serialNo"));
        }
        synchronized (this) {
          Runner.set(robot, "isWaiting", true);
        }
      }
    }
    if(moved) {
      synchronized (System.out) {
        System.out.printf(
            "Robot %02d : Builder attached some parts or relocated a completed robot.%n",
            Runner.get(robot, "serialNo"));
      }
      synchronized (Runner.factory.productionLine.parts){
        Runner.productionLineDisplay.repaint();
      }
      synchronized (Runner.factory.robots){
        Runner.robotsDisplay.repaint();
      }
      synchronized (Runner.factory.storage.robots){
        Runner.storageDisplay.repaint();
        if(Runner.factory.storage.robots.size() == Runner.factory.storage.maxCapacity) {
          Runner.factory.initiateStop();
        }
      }
    }
  }

  public boolean moveCompletedRobot(ProductionLine pl) {
    synchronized (Runner.factory.productionLine.parts) {
      int first_completed = findFirstCompleted(pl);
      if (first_completed != -1) {
        synchronized (Runner.factory.robots) {
          if (Runner.factory.robots.size() < Runner.factory.maxRobots) {
            Robot r = (Robot) pl.parts.get(first_completed);
            Runner.factory.robots.add(r);
            pl.parts.remove(r);
            new Thread(r).start();
            return true;
          }
        }
        synchronized (Runner.factory.storage.robots) {
          if (Runner.factory.storage.robots.size() < Runner.factory.storage.maxCapacity) {
            Robot r = (Robot) pl.parts.get(first_completed);
            Runner.factory.storage.robots.add(r);
            pl.parts.remove(r);
            return true;
          }
        }
      }
      return false;
    }
  }

  public boolean attachLogicToPayloadArmBase(ProductionLine pl){
    synchronized (Runner.factory.productionLine.parts) {
      int first_base_without_logic = findFirstBaseWithoutLogic(pl, 0);
      if(first_base_without_logic != -1) {
        while(first_base_without_logic != -1) {
          Base b = (Base) pl.parts.get(first_base_without_logic);
          Payload p = (Payload) Runner.get(b, "payload");
          if(p instanceof Gripper) {
            int firstSupplier = findFirstSupplier(pl);
            if(firstSupplier != -1) {
              attachLogic(pl, b, firstSupplier);
              return true;
            }
          } else if (p instanceof Welder) {
            int firstBuilder = findFirstBuilder(pl);
            if(firstBuilder  != -1) {
              attachLogic(pl, b, firstBuilder);
              return true;
            }
          } else if (p instanceof Camera) {
            int firstInspector = findFirstInspector(pl);
            if(firstInspector != -1) {
              attachLogic(pl, b, firstInspector);
              return true;
            }
          } else if (p instanceof MaintenanceKit) {
            int firstFixer = findFirstFixer(pl);
            if(firstFixer != -1) {
              attachLogic(pl, b, firstFixer);
              return true;
            }
          }
          first_base_without_logic = findFirstBaseWithoutLogic(pl, first_base_without_logic+1);
        }
      }
      return false;
    }
  }

  public boolean attachPayloadToArmBase(ProductionLine pl) {
    synchronized (Runner.factory.productionLine.parts){
      int first_base_without_paylaod = findFirstBaseWihtoutPayload(pl);
      if(first_base_without_paylaod != -1) {
        int first_payload = findFirstPayload(pl);
        if(first_payload != -1) {
          Base b = (Base) pl.parts.get(first_base_without_paylaod);
          Payload p = (Payload) pl.parts.get(first_payload);
          Runner.set(b, "payload", p);
          pl.parts.remove(p);
          return true;
        }
      }
    }
    return false;
  }

  public boolean attachArmToBase(ProductionLine pl) {
    synchronized (Runner.factory.productionLine.parts) {
      int first_base_without_arm = findFirstBaseWithoutArm(pl);
      if (first_base_without_arm != -1) {
        int first_arm = findFirstArm(pl);
        if (first_arm != -1) {
          Base b = (Base) pl.parts.get(first_base_without_arm);
          Arm a = (Arm) pl.parts.get(first_arm);
          Runner.set(b, "arm", a);
          pl.parts.remove(a);
          return true;
        }
      }
    }
    return false;
  }




  public void attachLogic(ProductionLine pl, Base b, int idx) {
    Logic l = (Logic) pl.parts.get(idx);
    Runner.set(b, "logic", l);
    pl.parts.remove(l);
  }

  public int findFirstCompleted(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      Part p = pl.parts.get(i);
      if(p instanceof Base) {
        Base b = (Base)p;
        if(Runner.get(b, "arm") != null && Runner.get(b, "payload")!= null &&
           Runner.get(b, "logic") != null) {
          return i;
        }
      }
    }
    return -1;
  }

  public int findFirstSupplier(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      if(pl.parts.get(i) instanceof Supplier) {
        return i;
      }
    }
    return -1;
  }
  public int findFirstBuilder(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      if(pl.parts.get(i) instanceof Builder) {
        return i;
      }
    }
    return -1;
  }
  public int findFirstInspector(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      if(pl.parts.get(i) instanceof Inspector) {
        return i;
      }
    }
    return -1;
  }
  public int findFirstFixer(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      if(pl.parts.get(i) instanceof Fixer) {
        return i;
      }
    }
    return -1;
  }
  public int findFirstBaseWithoutLogic(ProductionLine pl, int start) {
    for(int i=start; i<pl.parts.size(); i++) {
      Part p = pl.parts.get(i);
      if(p instanceof Base) {
        Base b = (Base) p;
        if(Runner.get(b, "arm") != null && Runner.get(b, "payload")!= null &&
           Runner.get(b, "logic") == null) {
          return i;
        }
      }
    }
    return -1;
  }

  public int findFirstBaseWihtoutPayload(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      Part p = pl.parts.get(i);
      if(p instanceof Base) {
        Base b = (Base) p;
        if(Runner.get(b, "arm") != null && Runner.get(b, "payload") == null) {
          return i;
        }
      }
    }
    return -1;
  }

  public int findFirstBaseWithoutArm(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      Part p = pl.parts.get(i);
      if(p instanceof Base){
        Base b = (Base)p;
        if(Runner.get(b, "arm") == null) {
          return i;
        }
      }
    }
    return -1;
  }

  public int findFirstArm(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++) {
      if(pl.parts.get(i) instanceof Arm) {
        return i;
      }
    }
    return -1;
  }

  public int findFirstPayload(ProductionLine pl) {
    for(int i=0; i<pl.parts.size(); i++){
      if(pl.parts.get(i) instanceof Payload) {
        return i;
      }
    }
    return -1;
  }
}
