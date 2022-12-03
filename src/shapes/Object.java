package shapes;

import classes.Color;
import classes.Ray;

public abstract class Object {
    public Color color;

    public abstract double hit(Ray ray);
}