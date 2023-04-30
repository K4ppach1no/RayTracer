package classes;

public class Color {
    public int r, g, b;

    public Color (int r, int g, int b) {
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
        return new Color((int) (r * reflectivity), (int) (g * reflectivity), (int) (b * reflectivity));
    }

    public Color scale(float scale) {
        return new Color((int) (r * scale), (int) (g * scale), (int) (b * scale));
    }

    public Color scale (Color color) {
        return new Color((int) (r * color.r / 255f), (int) (g * color.g / 255f), (int) (b * color.b / 255f));
    }

    public int toInt() {
        return (r << 16) | (g << 8) | b;
    }
}
