package com.toastedbits.osc.transform.vcvrack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCSerializeException;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.udp.OSCPortIn;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

//Wekinator sends all values in single OSCAddress
//We need to split the values into message channels for VCVRack (cvOSCcv plugin) to work with
public class WekinatorToVCVRack {
    private static final int wekinatorOscPort = 12000;
    private static final int sendToPort = 7001;
    private static final String sendToOscBaseAddress = "/trowacv";
    private static final InetAddress sendToHost = getSendToHost();

    private static final AtomicInteger ct = new AtomicInteger();

    private static InetAddress getSendToHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Listening on port " + wekinatorOscPort);
        OSCPortIn receiver = new OSCPortIn(wekinatorOscPort);
        receiver.setDaemonListener(false);

        OSCPortOut sender = new OSCPortOut(sendToHost, sendToPort);

        receiver.getDispatcher()
            .addListener(new OSCPatternAddressMessageSelector("/wek/outputs"), (evt) -> {
                try {
                    System.out.println(ct.addAndGet(1) + ": " + evt.getMessage().getArguments());
                    int ch = 1;
                    for(Object oscObjValue : evt.getMessage().getArguments()) {
                        sender.send(
                            new OSCMessage(
                                sendToOscBaseAddress + "/ch/" + ch,
                                Collections.singletonList(oscObjValue)
                            )
                        );
                        ch++;
                    }
                }
                catch (IOException | OSCSerializeException e) {
                    e.printStackTrace();
                }
            });

        receiver.startListening();
    }
}
