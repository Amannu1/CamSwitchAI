package org.example;

import org.example.detection.PersonDetector;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

public class App {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.load("C:\\Users\\User\\Downloads\\CamSwitchAI\\CamSwitchAI\\lib\\opencv_java4110.dll");

    }

    public static void main( String[] args ) {

        PersonDetector detector = new PersonDetector();
        detector.startDetection();

        /*checkAvailableCameras();*/
    }

   /* public static void checkAvailableCameras(){
        System.out.println("Verificando câmeras disponíveis...");
        for (int i = 0; i < 2; i++) {
            VideoCapture camera = new VideoCapture(i);
            if (camera.isOpened()) {
                System.out.println("Câmera encontrada no índice: " + i);
                camera.release();
            } else {
                System.out.println("Nenhuma câmera encontrada no índice: " + i);
            }
        }
    }*/
}

