package edu.nazaaaar.perlinnoise.interpretation3D;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

public class CameraMovement {
    public CameraMovement(CameraConfig cameraConfig) {
        this.cameraSpeed = cameraConfig.cameraSpeed();
        this.cameraX = cameraConfig.cameraX();
        this.cameraY = cameraConfig.cameraY();
        this.cameraZ = cameraConfig.cameraZ();
        this.cameraYaw = cameraConfig.cameraYaw();
        this.cameraPitch = cameraConfig.cameraPitch();
        this.cameraRotation = cameraConfig.cameraRotation();
    }

    public void configure(CameraConfig cameraConfig) {
        this.cameraSpeed = cameraConfig.cameraSpeed();
        this.cameraX = cameraConfig.cameraX();
        this.cameraY = cameraConfig.cameraY();
        this.cameraZ = cameraConfig.cameraZ();
        this.cameraYaw = cameraConfig.cameraYaw();
        this.cameraPitch = cameraConfig.cameraPitch();
        this.cameraRotation = cameraConfig.cameraRotation();
    }
    private double cameraSpeed;
    private double cameraX;
    private double cameraY;
    private double cameraZ;
    private double cameraYaw;
    private double cameraPitch;
    private double cameraRotation;
    public void InstantiateCamera(Scene scene) {
        //Initial settings
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0d);
        updateCameraTransforms(camera);
        scene.setCamera(camera);

        //Movement events
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP -> cameraPitch -= cameraRotation;
                case DOWN -> cameraPitch += cameraRotation;
                case LEFT -> cameraYaw += cameraRotation;
                case RIGHT -> cameraYaw -= cameraRotation;
                case W -> {
                    cameraZ += cameraSpeed * Math.cos(Math.toRadians(cameraYaw));
                    cameraX += cameraSpeed * Math.sin(Math.toRadians(cameraYaw));
                }
                case S -> {
                    cameraZ -= cameraSpeed * Math.cos(Math.toRadians(cameraYaw));
                    cameraX -= cameraSpeed * Math.sin(Math.toRadians(cameraYaw));
                }
                case A -> {
                    cameraZ -= cameraSpeed * Math.sin(Math.toRadians(cameraYaw));
                    cameraX += cameraSpeed * Math.cos(Math.toRadians(cameraYaw));
                }
                case D -> {
                    cameraZ += cameraSpeed * Math.sin(Math.toRadians(cameraYaw));
                    cameraX -= cameraSpeed * Math.cos(Math.toRadians(cameraYaw));
                }
                case E -> cameraY += cameraSpeed;
                case Q -> cameraY -= cameraSpeed;
            }
            updateCameraTransforms(camera);
        });

        scene.setCamera(camera);
    }

    private void updateCameraTransforms(PerspectiveCamera camera) {
        camera.getTransforms().clear();
        camera.getTransforms().addAll(
                new Rotate(cameraYaw, Rotate.Y_AXIS),
                new Rotate(cameraPitch, Rotate.X_AXIS),
                new Rotate(180,Rotate.Z_AXIS));
        camera.setTranslateX(cameraX);
        camera.setTranslateY(cameraY);
        camera.setTranslateZ(cameraZ);
    }

    public static record CameraConfig(double cameraSpeed, double cameraX, double cameraY, double cameraZ, double cameraYaw,
                                      double cameraPitch, double cameraRotation) {
    }
}
