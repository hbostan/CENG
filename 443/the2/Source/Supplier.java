public class Supplier extends Logic {
  @Override
  public void run(Robot robot) {

    synchronized (Runner.factory.productionLine) {
      ProductionLine pl = Runner.factory.productionLine;
      if (pl.parts.size() < pl.maxCapacity) {
        synchronized (System.out) {
          System.out.printf("Robot %02d : Supplying a random part on production line.%n",
                            Runner.get(robot, "serialNo"));
        }
        addPart(pl);
      } else if (pl.parts.size() == pl.maxCapacity) {
        synchronized (System.out) {
          System.out.printf("Robot %02d : Production line is full, removing a random part from "
                            + "production line.%n", Runner.get(robot, "serialNo"));
        }
        removePart(pl);
      }
      System.out.printf( "Robot %02d : Waking up waiting builders.%n", Runner.get(robot,"serialNo")) ;
      synchronized (Runner.factory.robots) {
        for (Robot r : Runner.factory.robots) {
          synchronized (r) {
            if (Runner.get(r, "logic") instanceof Builder) {
              if ((boolean) Runner.get(r, "isWaiting")) {
                Runner.set(r, "isWaiting", false);
              }
            }
          }
        }
      }
      pl.notifyAll();
    }
    synchronized (Runner.factory.productionLine.parts) {
      Runner.productionLineDisplay.repaint();
    }
  }

  public void removePart(ProductionLine pl) {
    synchronized (Runner.factory.productionLine.parts){
      int random_part_index = Runner.random.nextInt(pl.maxCapacity);
      pl.parts.remove(random_part_index);
    }
  }

  public void addPart(ProductionLine pl) {
    synchronized (Runner.factory.productionLine.parts){
      pl.parts.add(getRandomPart());
    }
  }

  public Part getRandomPart() {
    switch (Runner.random.nextInt(16)) {
      case 0:
      case 1:
      case 2:
      case 3:
        return Factory.createBase();
      case 4:
      case 5:
      case 6:
      case 7:
        return Factory.createPart("Arm");
      case 8:
        return Factory.createPart("Gripper");
      case 9:
        return Factory.createPart("Welder");
      case 10:
        return Factory.createPart("Camera");
      case 11:
        return Factory.createPart("MaintenanceKit");
      case 12:
        return Factory.createPart("Supplier");
      case 13:
        return Factory.createPart("Builder");
      case 14:
        return Factory.createPart("Inspector");
      case 15:
        return Factory.createPart("Fixer");
      default:
        return Factory.createBase();
    }
  }
}
