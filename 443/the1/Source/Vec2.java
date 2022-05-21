import java.util.*;

/**
 * A 2d vector class to easily handle positions of the objects.
 */
public class Vec2 {
  public double x;
  public double y;

  /**
   * Initializes the vector to 0
   */
  public Vec2() {
    x = 0;
    y = 0;
  }

  /**
   * Initializes the vector with given parameters
   * @param x X value of the vector
   * @param y Y value of the vector
   */
  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Add the given vector to this vector
   * @param v Vector to be added
   */
  public void  add(Vec2 v) {
    this.x += v.x;
    this.y += v.y;
  }

  /**
   * Creates a new vector by subtracting given vector from this.
   * @param v Vector to be subtracted
   * @return
   */
  public Vec2 sub(Vec2 v) {
    return new Vec2(this.x - v.x, this.y - v.y);
  }

  /**
   * Creates a new vector by multiplying this vector by given value.
   * @param c The multiplier
   * @return
   */
  public Vec2 multiply(double c) {
    return new Vec2(this.x * c, this.y * c);
  }

  /**
   * Normalizes the vector by dividing by its length
   * @return this vector obj
   */
  public Vec2 normalize() {
    double l = length();
    this.x/=l;
    this.y/=l;
    return this;
  }

  /**
   * Length of the vector
   * @return length
   */
  public double length() {
    return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0));
  }

  /**
   * Calculates distance between two poins.
   * @param target the point the distance is calculated to.
   * @return  distance between this and target.
   */
  public double distanceTo(Vec2 target) {
    return Math.sqrt(Math.pow(this.x - target.x, 2.0) + Math.pow(this.y - target.y, 2.0));
  }
}
