package org.example.detection;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;

public class PersonDetector {
    private int currentCameraIndex = 0;
    private List<VideoCapture> cameras = new ArrayList<>();
    private VideoCapture currentCamera;
    private VideoCapture inactiveCamera;  
    private HOGDescriptor hog;
    private boolean switchingInProgress = false;

    public PersonDetector() {

        initializeCameras();
        hog = new HOGDescriptor();
        hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
    }

    private void initializeCameras() {

        cameras.add(new VideoCapture(0));
        cameras.add(new VideoCapture(1));
        currentCamera = cameras.get(currentCameraIndex);
        inactiveCamera = cameras.get((currentCameraIndex + 1) % cameras.size());
    }

    public void startDetection() {
        new Thread(() -> {
            while (true) {
                Mat frame = new Mat();

                if (currentCamera.isOpened() && currentCamera.read(frame)) {

                    detectPeople(frame, true);

                    HighGui.imshow("People detection - Camera " + (currentCameraIndex + 1), frame);
                    HighGui.waitKey(1);
                }
            }
        }).start();
    }

    private void detectPeople(Mat frame, boolean isActiveCamera) {
        MatOfRect detections = new MatOfRect();
        MatOfDouble weights = new MatOfDouble();

        hog.detectMultiScale(frame, detections, weights, 0, new Size(8, 8), new Size(0, 0), 1.05, 2, false);

        if (detections.toArray().length > 0) {
            for (Rect rect : detections.toArray()) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 3);
            }

            if (isActiveCamera) {
                switchingInProgress = false;
            }
        }
    }


}
