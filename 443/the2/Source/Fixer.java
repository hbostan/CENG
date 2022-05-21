// TODO: imports

import java.util.ArrayList;

public class Fixer extends Logic
{
  @Override public void run ( Robot robot )
  {
    // TODO
    //
    // When implementing this method, the following messages might be printed where appropriate:
    //
    // System.out.printf( "Robot %02d : Fixed and waken up robot (%02d).%n"   , ... ) ;
    // System.out.printf( "Robot %02d : Nothing to fix, waiting!%n"           , ... ) ;
    // System.out.printf( "Robot %02d : Fixer woke up, going back to work.%n" , ... ) ;
    synchronized (Runner.factory.brokenRobots) {
      while((boolean)Runner.get(robot, "isWaiting")){
        try {
          Runner.factory.brokenRobots.wait();
          if(Runner.factory.stopProduction) {
            break;
          }
        } catch (InterruptedException e) {

        }
      }
      synchronized (System.out) {
        System.out.printf("Robot %02d : Fixer woke up, going back to work.%n" , Runner.get(robot, "serialNo")) ;
      }

      if(Runner.factory.brokenRobots.size() > 0) {
        Robot broken = Runner.factory.brokenRobots.get(0);
        synchronized (broken) {
          if (Runner.get(broken, "arm") == null) {
            Runner.set(broken, "arm", Runner.factory.createPart("Arm"));
          } else if (Runner.get(broken, "payload") == null) {
            Logic l = (Logic) Runner.get(broken, "logic");
            if(l instanceof Supplier){
              Runner.set(broken, "payload", Runner.factory.createPart("Gripper"));
            } else if(l instanceof Builder) {
              Runner.set(broken, "payload", Runner.factory.createPart("Welder"));
            } else if(l instanceof Inspector) {
              Runner.set(broken, "payload", Runner.factory.createPart("Camera"));
            } else if(l instanceof Fixer){
              Runner.set(broken, "payload", Runner.factory.createPart("MaintenanceKit"));
            }
          } else if (Runner.get(broken, "logic") == null) {
            Payload p = (Payload) Runner.get(broken, "payload");
            if(p instanceof Gripper) {
              Runner.set(broken, "logic", Runner.factory.createPart("Supplier"));
            } else if (p instanceof Welder) {
              Runner.set(broken, "logic", Runner.factory.createPart("Builder"));
            } else if (p instanceof Camera) {
              Runner.set(broken, "logic", Runner.factory.createPart("Inspector"));
            } else if (p instanceof MaintenanceKit){
              Runner.set(broken, "logic", Runner.factory.createPart("Fixer"));
            }
          }
          Runner.factory.brokenRobots.remove(broken);
          broken.notify();
          synchronized (System.out) {
            System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", Runner.get(robot, "serialNo"), Runner.get(broken, "serialNo"));
          }
        }
      }
      if(Runner.factory.brokenRobots.size() == 0) {
        System.out.printf("Robot %02d : Nothing to fix, waiting!%n", Runner.get(robot, "serialNo")) ;
        Runner.set(robot, "isWaiting", true);
      }
    }
  }
}