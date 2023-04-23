package edu.nazaaaar.perlinnoise.interpretation3D;

import edu.nazaaaar.perlinnoise.heightmap.Heightmap;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MapInterpreter3D extends Application {

    public MapInterpreter3D(int RESOLUTION, int waterLevel, boolean isWaterUsed, Heightmap heightmap, Map<Integer, Color> colorMap, Color defaultColor) {
        this.RESOLUTION = RESOLUTION;
        this.waterLevel = waterLevel;
        this.isWaterUsed = isWaterUsed;
        this.heightmap = heightmap;
        this.colorMap = colorMap;
        this.defaultColor = defaultColor;
    }
    private final int WIDTH = 1200;  // Width of the JavaFX window
    private final int HEIGHT = 800;  // Height of the JavaFX window
    private int RESOLUTION; // Resolution of the heightmap
    private int waterLevel;
    private boolean isWaterUsed; //true

    /*private final int size = 300;
    private final int maxHeight = 200;
    private final int minHeight = 1;*/
    private Heightmap heightmap; //new Heightmap(new Heightmap.HeightmapConfig(minHeight, maxHeight, size, new PerlinFBM()));

    private final int gap = 40;
    private Map<Integer, Color> colorMap;
        /*colorMap = new TreeMap<>();
        colorMap.put(108, Color.YELLOW);
        colorMap.put(150, Color.GREEN);*/

    private Color defaultColor;


    @Override
    public void start(Stage primaryStage){
        System.setProperty("prism.forceGPU", "true");
        heightmap.create();

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);
        CameraMovement.CameraConfig cameraConfig = new CameraMovement.CameraConfig(
                20*RESOLUTION,
                heightmap.getSize()*RESOLUTION+13000,
                (400)*RESOLUTION,
                10*RESOLUTION-13000,
                -40,
                40,
                10
        );
        CameraMovement cameraMovement = new CameraMovement(cameraConfig);
        cameraMovement.InstantiateCamera(scene);

        InitializeObjects(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void InitializeObjects(Group root) {
        heightmap.forEach((args) ->{
            int height = args.val * RESOLUTION - gap*RESOLUTION;
            Box cube = new Box(RESOLUTION, height, RESOLUTION);
                cube.setTranslateX(args.x * RESOLUTION);
                cube.setTranslateY(height /2.d);
                cube.setTranslateZ(args.y * RESOLUTION);
                cube.setMaterial(new PhongMaterial(getColor(args.val)));
                root.getChildren().add(cube);

            if (isWaterUsed && args.val< waterLevel){
                Box water = getWater(args, height);
                root.getChildren().add(water);
                }
        });
    }
    @NotNull
    private Box getWater(Heightmap.MapArgs args, int height) {
        int height2 = (waterLevel- args.val) * RESOLUTION;
        Box water = new Box(RESOLUTION, height2,RESOLUTION);
        water.setTranslateX(args.x*RESOLUTION);
        water.setTranslateY(height + height2/2.d);
        water.setTranslateZ(args.y*RESOLUTION);
        double brightness_ = Math.max(0.1d, args.val / (double) waterLevel);
        brightness_*=10;
        double brightness=((int)brightness_)/10d;
        water.setMaterial(new PhongMaterial(Color.BLUE.deriveColor(0.8,1,brightness,0.6)));//
        return water;
    }
    private Color getColor(int height) {
        for (Map.Entry<Integer, Color> entry : colorMap.entrySet()) {
            if (height <= entry.getKey()) {
                return entry.getValue();
            }
        }
        return defaultColor;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int getWIDTH() {
        return WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public int getRESOLUTION() {
        return RESOLUTION;
    }
    public void setRESOLUTION(int RESOLUTION) {
        this.RESOLUTION = RESOLUTION;
    }
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
    public void setWaterUsed(boolean waterUsed) {
        isWaterUsed = waterUsed;
    }
    public void setHeightmap(Heightmap heightmap) {
        this.heightmap = heightmap;
    }
    public void setColorMap(Map<Integer, Color> colorMap) {
        this.colorMap = colorMap;
    }
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}
