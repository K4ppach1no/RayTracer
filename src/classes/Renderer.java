package classes;

import java.awt.image.BufferedImage;

public class Renderer {
    public static final int MAX_RECURSION_DEPTH = 5;
    public static final float EPSILON = 0.0001f;

    public static Color trace(Ray ray, Scene scene, int recursionDepth) {
        // after a certain recursion depth, we stop tracing
        if (recursionDepth > MAX_RECURSION_DEPTH) {
            return new Color(0, 0, 0);
        }

        // if we hit nothing, return the background color
        Intersection intersection = scene.getIntersection(ray);
        if (intersection == null) {
            return scene.backgroundColor;
        }

        Color color = new Color(0, 0, 0);
        for (Light light : scene.lights) {
            Vec3 lightDirection = light.position.sub(intersection.position).normalize();
            float lightDistance = light.position.sub(intersection.position).length();

            Ray shadowRay = new Ray(intersection.position.add(lightDirection.mul(EPSILON)), lightDirection, lightDistance);
            Intersection shadowIntersection = scene.getIntersection(shadowRay);
            if (shadowIntersection != null) {
                continue;
            }

            float diffuse = Math.max(0, lightDirection.dot(intersection.normal));
            color = color.add(intersection.material.color.mul(light.color).mul(diffuse));
        }

        // reflection
        Vec3 reflectionDirection = ray.direction.sub(intersection.normal.mul(2 * ray.direction.dot(intersection.normal))).normalize();
        Ray reflectionRay = new Ray(intersection.position.add(reflectionDirection.mul(EPSILON)), reflectionDirection, Float.MAX_VALUE);
        Color reflection = trace(reflectionRay, scene, recursionDepth + 1);

        color = color.add(reflection.mul(intersection.material.reflectivity));

        return color;
    }

    public static void render(Scene scene, BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float u = (float) x / width;
                float v = (float) y / height;
                Ray ray = scene.camera.getRay(u, v);
                Color color = trace(ray, scene, 2);
                img.setRGB(x, y, color.toInt());
            }
        }
    }
}
