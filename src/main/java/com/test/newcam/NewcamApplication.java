package com.test.newcam;

import lombok.val;
import org.librealsense.Context;
import org.librealsense.Native;
import org.librealsense.devices.Device;
import org.librealsense.devices.DeviceList;
import org.librealsense.frames.Frame;
import org.librealsense.frames.FrameList;
import org.librealsense.pipeline.Config;
import org.librealsense.pipeline.Pipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.ByteBuffer;
import java.util.List;

//@SpringBootApplication
public class NewcamApplication {

    public static void main(String[] args) {
        Context context = Context.create();
        DeviceList deviceList = context.queryDevices();
        List<Device> devices = deviceList.getDevices();

        Device device = devices.get(0);
        Pipeline pipeline = context.createPipeline();
        Config config = Config.create();
        config.enableDevice(device);
        config.enableStream(Native.Stream.RS2_STREAM_DEPTH, 0, 640, 480, Native.Format.RS2_FORMAT_Z16, 30);
        pipeline.startWithConfig(config);

        while (true) {
            FrameList frames = pipeline.waitForFrames(5000);

            for ( int i=0;  i<frames.frameCount(); i++) {
                Frame frame = frames.frame(i);
                ByteBuffer buffer = frame.getFrameData();

                // -- use ByteBuffer here
                frame.release();
            }
            frames.release();
        }
    }

}
