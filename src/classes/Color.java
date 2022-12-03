package classes;

public class Color {
    public float r, g, b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color add(Color c) {
        return new Color(r + c.r, g + c.g, b + c.b);
    }

    public Color mul(Color color) {
        return new Color(r * color.r, g * color.g, b * color.b);
    }

    public Color mul(float reflectivity) {
        return new Color(r * reflectivity, g * reflectivity, b * reflectivity);
    }

    public int toInt() {
        int r = (int) (255.99 * this.r);
        int g = (int) (255.99 * this.g);
        int b = (int) (255.99 * this.b);
        return (r << 16) | (g << 8) | b;
    }
}
