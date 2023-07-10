package classes;

public class Color {
    // the color components
    public int r, g, b;

    // constructor
    public Color (int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // add two colors
    public Color add(Color c) {
        return new Color((int) (r + c.r), (int) (g + c.g), (int) (b + c.b));
    }

    // multiply two colors
    public Color mul(Color color) {
        return new Color(r * color.r, g * color.g, b * color.b);
    }

    // multiply a color with a float
    public Color mul(float reflectivity) {
        return new Color((int) (r * reflectivity), (int) (g * reflectivity), (int) (b * reflectivity));
    }

    // scale a color 
    public Color scale(float scale) {
        System.out.println(r * scale);
        return new Color((int) (r * scale), (int) (g * scale), (int) (b * scale));
    }

    // scale a color with another color
    public Color scale (Color color) {
        return new Color((int) (r * color.r / 255f), (int) (g * color.g / 255f), (int) (b * color.b / 255f));
    }

    public Color sample (Color color, int sample){
        return new Color((int) (color.r / sample), (color.g / sample), (color.b / sample));
    }

    // convert a color to an integer
    public int toInt() {
        return (r << 16) | (g << 8) | b;
    }
}
