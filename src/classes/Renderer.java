package classes;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.concurrent.CountDownLatch;

public class Renderer {
    // EPSILON is used to prevent self-intersection, also known as acne
    public static final float EPSILON = 0.0001f;

    /**
     * Traces a ray through the scene and calculates the color at the intersection
     * point.
     *
     * @param ray            The ray to trace.
     * @param scene          The scene through which to trace the ray.
     * @param recursionDepth The maximum number of recursive calls, controlling the
     *                       number of reflections and refractions.
     * @return The color at the intersection point.
     */
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
        Color reflectionColor = trace(
                new Ray(intersection.position.add(reflection.scale(EPSILON)), reflection, Float.MAX_VALUE), scene,
                recursionDepth - 1);

        // add the reflection color to the color
        color = color.add(reflectionColor.mul(intersection.material.getReflectivity()));

        // calculate the refraction color
        Color refractionColor = trace(
                new Ray(intersection.position.add(reflection.scale(EPSILON)), reflection, Float.MAX_VALUE), scene,
                recursionDepth - 1);

        // add the refraction color to the color
        color = color.add(refractionColor.mul(intersection.material.getTransparency()));

        // return the color
        return color;
    }

    /**
     * Renders the entire scene into a BufferedImage.
     *
     * @param scene The scene to render.
     * @param img   The image into which to render the scene.
     */
    public static void render(Scene scene, BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int sample = 100;
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available cores

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<?>[] futures = new Future[width];
        CountDownLatch progressLatch = new CountDownLatch(width);

        // Number of vertical lines each thread will render at a time
        int linesPerThread = width / numThreads;

        for (int x = 0; x < width; x += linesPerThread) {
            final int startX = x;
            final int endX = Math.min(x + linesPerThread, width);
            futures[x] = executor.submit(() -> {
                for (int lineX = startX; lineX < endX; lineX++) {
                    for (int y = 0; y < height; y++) {
                        Color cumColor = new Color(0, 0, 0);
                        for (int s = 0; s < sample; s++) {
                            float u = (float) (lineX + Math.random()) / width;
                            float v = (float) (y + Math.random()) / height;
                            Ray ray = scene.camera.getRay(u, v);
                            Color color = trace(ray, scene, 5);
                            cumColor = cumColor.add(color);
                        }
                        Color avgColor = cumColor.sample(cumColor, sample);
                        img.setRGB(lineX, y, avgColor.toInt());
                    }
                    progressLatch.countDown();
                    double progress = 100.0 - (double) progressLatch.getCount() / width * 100;
                    System.out.println("Rendering progress: " + progress + "%");
                }
            });
        }

        // Wait for all tasks to complete
        try {
            for (int x = 0; x < width; x += linesPerThread) {
                futures[x].get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}