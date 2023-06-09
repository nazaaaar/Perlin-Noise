package edu.nazaaaar.perlinnoise.heightmap;

public class PerlinFBM implements Mappable {

    int numOctaves;
    double startAmplitude = 1;
    double startFrequency;
    double amplitudeMultiplier = 0.5;
    double frequencyMultiplier = 2;
    public PerlinFBM(long seed){
        noise=new PerlinNoise(seed);
    }
    PerlinNoise noise;
    @Override
    public double generate(double x, double y) {
        double amplitude = startAmplitude;
        double frequency = startFrequency;
        double maxValue = 0;
        double result = 0;
        for (int octave = 0; octave < numOctaves; octave++) {
            maxValue+=amplitude;

            result+= amplitude*noise.generate(x*frequency,y*frequency);
            amplitude*=amplitudeMultiplier;
            frequency*=frequencyMultiplier;
        }

        double minValue = 0;
        result = (result - minValue) / (maxValue - minValue);
        return result;
    }


    public int getNumOctaves() {
        return numOctaves;
    }

    public void setNumOctaves(int numOctaves) {
        this.numOctaves = numOctaves;
    }

    public double getStartAmplitude() {
        return startAmplitude;
    }

    public void setStartAmplitude(double startAmplitude) {
        this.startAmplitude = startAmplitude;
    }

    public double getStartFrequency() {
        return startFrequency;
    }

    public void setStartFrequency(double startFrequency) {
        this.startFrequency = startFrequency;
    }

    public double getAmplitudeMultiplier() {
        return amplitudeMultiplier;
    }

    public void setAmplitudeMultiplier(double amplitudeMultiplier) {
        this.amplitudeMultiplier = amplitudeMultiplier;
    }

    public double getFrequencyMultiplier() {
        return frequencyMultiplier;
    }

    public void setFrequencyMultiplier(double frequencyMultiplier) {
        this.frequencyMultiplier = frequencyMultiplier;
    }

    public PerlinNoise getNoise() {
        return noise;
    }

    public void setNoise(PerlinNoise noise) {
        this.noise = noise;
    }

    public void setSeed(long seed){
        noise.setSeedMakePermutation(seed);
    }
}
