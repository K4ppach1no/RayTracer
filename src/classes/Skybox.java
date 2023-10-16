package classes;

public class Skybox {
	private Color color;
	
	public Skybox(Color color) {
		this.color = color;
	}
	
	public Color calculateColor(Vec3 direction) {
		// Return the same color for all directions
		return color;
	}
}
