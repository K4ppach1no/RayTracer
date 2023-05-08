package classes;

import java.awt.image.BufferedImage;

public class Renderer {
    // EPSILON is used to prevent self-intersection, also known as acne
    public static final float EPSILON = 0.0001f;

    public static Color trace(Ray ray, Scene scene, int recursionDepth) {
        // if the recursion depth is 0, return black
        if (recursionDepth == 0) {
            return new Color(0, 0, 0);
        }

        // get the closest intersection
        Intersection intersection = scene.getIntersection(ray);

        // if there is no intersection, return black
        if (intersection == null) {
            return new Color(0, 0, 0);
        }

        // calculate normal
        Vec3 normal = intersection.normal.normalize();

        // float intensity
        float intensity = 0;

        // for each light in the scene
        for (var light : scene.lights) {
            // get vector between intersection and light
            Vec3 lightDir = light.position.sub(intersection.position).normalize();

            float angle = normal.dot(lightDir);

            // if the angle is less than 0, the light is behind the surface
            if (angle < 0) {
                continue;
            }
            // calculate intensity
            Vec3 distance = light.position.sub(intersection.position);
            float distance2 = distance.dot(distance);
            intensity += angle * light.intensity / distance2;

            // calculate shadow ray
            Ray shadowRay = new Ray(intersection.position.add(lightDir.scale(EPSILON)), lightDir, Float.MAX_VALUE);

            // get the closest intersection
            Intersection shadowIntersection = scene.getIntersection(shadowRay);

            // if there is an intersection, the light is blocked
            if (shadowIntersection != null) {
                intensity = 0;
                break;
            }
        }

        // calculate reflection
        Vec3 reflection = ray.direction.sub(normal.scale(2 * ray.direction.dot(normal))).normalize();

        // calculate the color
        Color color = intersection.material.getColor().mul(intensity);

        // calculate the reflection color
        Color reflectionColor = trace(new Ray(intersection.position.add(reflection.scale(EPSILON)), reflection, Float.MAX_VALUE), scene, recursionDepth - 1);

        // add the reflection color to the color
        color = color.add(reflectionColor.mul(intersection.material.getReflectivity()));

        // calculate the refraction color
        Color refractionColor = trace(new Ray(intersection.position.add(reflection.scale(EPSILON)), reflection, Float.MAX_VALUE), scene, recursionDepth - 1);

        // add the refraction color to the color
        color = color.add(refractionColor.mul(intersection.material.getTransparency()));

        // return the color
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
                Color color = trace(ray, scene, 5);
                img.setRGB(x, y, color.toInt());
            }
        }
    }
}
