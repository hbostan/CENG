// TODO: imports
public class Inspector extends Logic
{
  @Override public void run ( Robot robot )
  {
    // TODO
    //
    // When implementing this method, the following messages might be printed where appropriate:
    //
    // System.out.printf( "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n" , ... ) ;
    // System.out.printf( "Robot %02d : Notifying waiting fixers.%n"                                        , ... ) ;
    synchronized (Runner.factory.robots) {
      for(Robot r : Runner.factory.robots) {
        if((Runner.get(r,"arm") == null || Runner.get(r, "payload") == null || Runner.get(r, "logic") == null)) {
          synchronized (Runner.factory.brokenRobots){
            if(!Runner.factory.brokenRobots.contains(r)) {
              synchronized (System.out) {
                System.out.printf( "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n", Runner.get(robot, "serialNo"), Runner.get(r, "serialNo")) ;
              }
              Runner.factory.brokenRobots.add(r);
              synchronized (System.out) {
                System.out.printf( "Robot %02d : Notifying waiting fixers.%n", Runner.get(robot, "serialNo")) ;
              }
              for(Robot rob: Runner.factory.robots) {
                if(Runner.get(rob, "logic") instanceof Fixer) {
                  if((boolean)Runner.get(rob, "isWaiting")) {
                    Runner.set(rob, "isWaiting", false);
                  }
                }
              }
              Runner.factory.brokenRobots.notifyAll();
            }
          }
        }
      }
    }
  }
}