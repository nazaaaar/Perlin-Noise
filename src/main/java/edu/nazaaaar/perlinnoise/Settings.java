package edu.nazaaaar.perlinnoise;

import edu.nazaaaar.perlinnoise.heightmap.Heightmap;
import edu.nazaaaar.perlinnoise.heightmap.PerlinFBM;
import edu.nazaaaar.perlinnoise.interpretation3D.MapInterpreter3D;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

public class Settings {
    private PerlinFBM perlinFBM;
    private Heightmap heightmap;
    private MapInterpreter3D mapInterpreter3D;
    public record SettingsConfig(
            PerlinFBM_Config perlinFBM_config,
            HeightmapConfig heightmapConfig,
            MapInterpreterConfig mapInterpreterConfig
            ){
    }

    static public record PerlinFBM_Config(int numOctaves, double startAmplitude,
                                          double startFrequency, double amplitudeMultiplier,
                                          double frequencyMultiplier, long seed){
    };
    static record HeightmapConfig(int MINVALUE, int MAXVALUE, int size) {
    }

    static record MapInterpreterConfig(int RESOLUTION, int waterLevel, boolean isWaterUsed,
                                               Map<Integer, Color> colorMap, Color defaultColor) {
    }

    public void create(SettingsConfig settingsConfig){
        configure(settingsConfig);

        mapInterpreter3D.start(new Stage());
    };

    private void configure(SettingsConfig settingsConfig){
        if (perlinFBM==null) perlinFBM = new PerlinFBM(settingsConfig.perlinFBM_config.seed);
        else perlinFBM.setSeed(settingsConfig.perlinFBM_config.seed);

        perlinFBM.setNumOctaves(settingsConfig.perlinFBM_config.numOctaves);
        perlinFBM.setFrequencyMultiplier(settingsConfig.perlinFBM_config.frequencyMultiplier);
        perlinFBM.setStartFrequency(settingsConfig.perlinFBM_config.startFrequency);
        perlinFBM.setStartAmplitude(settingsConfig.perlinFBM_config.startAmplitude);
        perlinFBM.setAmplitudeMultiplier(settingsConfig.perlinFBM_config.amplitudeMultiplier);

        if (heightmap==null)heightmap = new Heightmap(settingsConfig.heightmapConfig.MINVALUE,settingsConfig.heightmapConfig.MAXVALUE,
                settingsConfig.heightmapConfig.size,perlinFBM);
        else {
             heightmap.setMaxvalue(settingsConfig.heightmapConfig.MAXVALUE);
             heightmap.setMinvalue(settingsConfig.heightmapConfig.MINVALUE);
             heightmap.setSize(settingsConfig.heightmapConfig.size);
        }

        if (mapInterpreter3D==null) mapInterpreter3D = new MapInterpreter3D(
                settingsConfig.mapInterpreterConfig.RESOLUTION,
                settingsConfig.mapInterpreterConfig.waterLevel,
                settingsConfig.mapInterpreterConfig.isWaterUsed,
                heightmap,
                settingsConfig.mapInterpreterConfig.colorMap,
                settingsConfig.mapInterpreterConfig.defaultColor);
        else{
            mapInterpreter3D.setColorMap(settingsConfig.mapInterpreterConfig.colorMap);
            mapInterpreter3D.setRESOLUTION(settingsConfig.mapInterpreterConfig.RESOLUTION);
            mapInterpreter3D.setDefaultColor(settingsConfig.mapInterpreterConfig().defaultColor);
            mapInterpreter3D.setWaterUsed(settingsConfig.mapInterpreterConfig.isWaterUsed);
            mapInterpreter3D.setWaterLevel(settingsConfig.mapInterpreterConfig.waterLevel);
        }
    }
}
