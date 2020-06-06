package com.toastedbits.osc.transform.faceosc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCSerializeException;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.udp.OSCPortIn;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/*
Normalizes FaceOSC to a single OSC address with 80 values, only sent when face is detected
 */
// * center position: /pose/position
//     * scale: /pose/scale
//     * orientation (which direction you're facing): /pose/orientation
//
//     Gestures
//     * mouth width: /gesture/mouth/width
//     * mouth height: /gesture/mouth/height
//     * left eyebrow height: /gesture/eyebrow/left
//     * right eyebrow height: /gesture/eyebrow/right
//     * left eye openness: /gesture/eye/left
//     * right eye openness: /gesture/eye/right
//     * jaw openness: /gesture/jaw
//     * nostril flate: /gesture/nostrils
//
//     Raw
//     * raw points (66 xy-pairs): /raw
public class FaceOscToWekinator {
    private static final int faceOscPort = 8338;
    private static final int sendToPort = 6448;
    private static final String sendToOscAddress = "/wek/inputs";
    private static final InetAddress sendToHost = getSendToHost();

    private static InetAddress getSendToHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Listening on port " + faceOscPort);
        OSCPortIn receiver = new OSCPortIn(faceOscPort);
        receiver.setDaemonListener(false);

        OSCPortOut sender = new OSCPortOut(sendToHost, sendToPort);

        final FaceModel face = new FaceModel();

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/pose/position"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setCenterPositionX((float)oscArgs.get(0));
                face.setCenterPositionY((float)oscArgs.get(1));
            });
        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/pose/scale"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setScale((float)oscArgs.get(0));
            });
        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/pose/orientation"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setOrientationX((float)oscArgs.get(0));
                face.setOrientationY((float)oscArgs.get(1));
                face.setOrientationZ((float)oscArgs.get(2));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/mouth/width"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setMouthWidth((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/mouth/height"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setMouthHeight((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/eyebrow/left"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setLeftEyebrowHeight((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/eyebrow/right"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setRightEyebrowHeight((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/eye/left"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setLeftEyeOpenness((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/eye/right"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setRightEyeOpenness((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/jaw"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setJawOpenness((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/gesture/nostrils"), (evt) -> {
                List<Object> oscArgs = evt.getMessage().getArguments();
                face.setNostirlFlate((float)oscArgs.get(0));
            });

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/raw"), (evt) -> {
                if(evt.getMessage().getArguments() != null) {
                    face.setRaw((List<Float>) (Object) evt.getMessage().getArguments());
                }
            });

        OSCFaceConverter converter = OSCFaceConverter.builder()
            .withIncludePose(true)
            .withIncludeGesture(true)
            .withIncludeRaw(false)
            .build();

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/found"), (evt) -> {
                boolean found = (int)evt.getMessage().getArguments().get(0) == 1;
                if(found) {
                    try {
                        System.out.println(mapper.writeValueAsString(face));
                        OSCMessage outMessage = new OSCMessage(sendToOscAddress, converter.toOscMessageData(face));
                        sender.send(outMessage);
                    }
                    catch (IOException | OSCSerializeException e) {
                        e.printStackTrace();
                    }
                }
            });

        receiver.startListening();
    }
}
