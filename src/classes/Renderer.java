package classes;

import java.awt.image.BufferedImage;

public class Renderer {
    public static final int MAX_RECURSION_DEPTH = 5;

    // EPSILON is used to prevent self-intersection, also known as acne
    public static final float EPSILON = 0.0001f;

    public static Color trace(Ray ray, Scene scene, int recursionDepth) {

        if (recursionDepth > MAX_RECURSION_DEPTH) {
            // Stop recursing if we've reached the maximum recursion depth
            return new Color(0, 0, 0);
        }

        Intersection intersection = scene.getIntersection(ray);
        if (intersection == null) {
            // If the ray doesn't intersect any objects in the scene, return the background
            // color
            return scene.backgroundColor;
        }

        Material material = intersection.material;
        Color color = material.getColor();
        // Compute the ambient color (a fraction of the material color)
        Color ambientColor = color.scale(0.2f);

        // Compute the diffuse color (based on the angle between the intersection normal
        // and the light direction)
        Color diffuseColor = new Color(0, 0, 0);
        for (Light light : scene.lights) {
            Vec3 lightDir = light.position.sub(intersection.position).normalize();
            float diffuseIntensity = Math.max(0, Vec3.dot(lightDir, intersection.normal));
            diffuseColor = diffuseColor.add(light.color.scale(diffuseIntensity));
        }
        diffuseColor = diffuseColor.scale(color);

        // Compute the specular color (based on the angle between the reflection
        // direction and the view direction)
        Color specularColor = new Color(0, 0, 0);
        Vec3 viewDir = ray.direction.negate().normalize();
        Vec3 reflectionDir = intersection.normal.scale(2 * Vec3.dot(intersection.normal, viewDir)).sub(viewDir)
                .normalize();
        for (Light light : scene.lights) {
            Vec3 lightDir = light.position.sub(intersection.position).normalize();
            float specularIntensity = (float) Math.pow(Math.max(0, Vec3.dot(reflectionDir, lightDir)), 20);
            specularColor = specularColor.add(light.color.scale(specularIntensity));
        }
        specularColor = specularColor.scale(0.5f);

        // Combine the ambient, diffuse, and specular colors
        Color result = ambientColor.add(diffuseColor).add(specularColor);

        // Compute reflections and refractions recursively
        float reflectivity = material.getReflectivity();
        float transparency = material.getTransparency();

        if (reflectivity > 0) {
            Vec3 reflectionDir1 = intersection.normal.scale(2 * Vec3.dot(intersection.normal, ray.direction))
                    .sub(ray.direction).normalize();
            Ray reflectionRay = new Ray(intersection.position.add(reflectionDir1.scale(EPSILON)), reflectionDir1, 0);
            Color reflectionColor = trace(reflectionRay, scene, recursionDepth + 1);
            result = result.scale(1 - reflectivity).add(reflectionColor.scale(reflectivity));
        }

        if (transparency > 0) {
            float n1, n2;
            if (Vec3.dot(ray.direction, intersection.normal) < 0) {
                // Ray is entering the object
                n1 = 1;
                n2 = 1.5f; // Refractive index of glass
            } else {
                // Ray is exiting the object
                n1 = 1.5f; // Refractive index of glass
                n2 = 1;
            }
            float cosTheta1 = -Vec3.dot(ray.direction, intersection.normal);
            float sinTheta1 = (float) Math.sqrt(1 - cosTheta1 * cosTheta1);
            float sinTheta2 = (n1 / n2) * sinTheta1;
            float cosTheta2 = (float) Math.sqrt(1 - sinTheta2 * sinTheta2);
            Vec3 refractionDir = intersection.normal.scale((n1 / n2) * cosTheta1 - cosTheta2)
                    .sub(ray.direction.scale(n1 / n2)).normalize();
            Ray refractionRay = new Ray(intersection.position.add(refractionDir.scale(EPSILON)), refractionDir, 0);
            Color refractionColor = trace(refractionRay, scene, recursionDepth + 1);
            float kr = fresnel(ray.direction, intersection.normal, n1, n2);
            result = result.scale(1 - transparency).add(refractionColor.scale(kr * transparency));
        }
        
        return result;
    }

    private static float fresnel(Vec3 i, Vec3 n, float n1, float n2) {
        float cosTheta1 = -Vec3.dot(i, n);
        float sinTheta1 = (float) Math.sqrt(1 - cosTheta1 * cosTheta1);
        float sinTheta2 = (n1 / n2) * sinTheta1;
        if (sinTheta2 >= 1) {
            // Total internal reflection
            return 1;
        }
        float cosTheta2 = (float) Math.sqrt(1 - sinTheta2 * sinTheta2);
        float rParallel = (n2 * cosTheta1 - n1 * cosTheta2) / (n2 * cosTheta1 + n1 * cosTheta2);
        float rPerpendicular = (n1 * cosTheta1 - n2 * cosTheta2) / (n1 * cosTheta1 + n2 * cosTheta2);
        return (rParallel * rParallel + rPerpendicular * rPerpendicular) / 2;
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
