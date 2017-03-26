//package com.smartik.backend;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import cloud.artik.model.Acknowledgement;
//import cloud.artik.model.ActionOut;
//import cloud.artik.model.MessageIn;
//import cloud.artik.model.MessageOut;
//import cloud.artik.model.RegisterMessage;
//import cloud.artik.model.WebSocketError;
//import cloud.artik.websocket.ArtikCloudWebSocketCallback;
//import cloud.artik.websocket.DeviceChannelWebSocket;
//import cloud.artik.websocket.FirehoseWebSocket;
//
//public class FirehoseWebSocketTest extends ArtikCloudApiTest {
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void testFirehose1() throws Exception {
//        String deviceId = getProperty("device3.id");
//        String userId = getProperty("user1.id");
//        String accessToken = getProperty("device3.token");
//        
//        final CountDownLatch messageLatch = new CountDownLatch(1000);
//        
//        FirehoseWebSocket firehoseWS = new FirehoseWebSocket(accessToken, deviceId, null, null, userId,
//                new ArtikCloudWebSocketCallback() {
//
//                    @Override
//                    public void onAck(Acknowledgement ack) {
//                        System.out.println("onAck: " + ack);
//                    }
//
//                    @Override
//                    public void onAction(ActionOut action) {
//                        System.out.println("onAction: " + action);
//                    }
//
//                    @Override
//                    public void onClose(int code, String reason, boolean remote) {
//                        System.out.printf("onClose: %d %s %s\n", code, reason,
//                                remote);
//                    }
//
//                    @Override
//                    public void onError(WebSocketError error) {
//                        System.err.println("onError: " + error);
//                    }
//
//                    @Override
//                    public void onMessage(MessageOut message) {
//                        //System.out.println("Firehose: onMessage: " + message);
//                    }
//
//                    @Override
//                    public void onOpen(int httpStatus, String httpStatusMessage) {
//                        System.out.printf("onOpen: %d %s\n", httpStatus,
//                                httpStatusMessage);
//                    }
//
//                    @Override
//                    public void onPing(long timestamp) {
//                        System.err.println("onPing: " + timestamp);
//                    }
//
//                });
//
//        firehoseWS.connect();
//        
//        messageLatch.await(100, TimeUnit.SECONDS);
//
//        firehoseWS.closeBlocking();
//    }
//
//    
//    @Test
//    public void testFirehose2() throws Exception {
//        String deviceId = getProperty("device3.id");
//        String userId = getProperty("user1.id");
//        String accessToken = getProperty("device3.token");
//        
//        final CountDownLatch registerLatch = new CountDownLatch(1);
//        final CountDownLatch messageLatch = new CountDownLatch(1000);
//        DeviceChannelWebSocket ws = new DeviceChannelWebSocket(true, new ArtikCloudWebSocketCallback() {
//
//                    @Override
//                    public void onAck(Acknowledgement ack) {
//                        System.out.println("onAck: " + ack);
//                        if (ack.getCid().equalsIgnoreCase("first")) {
//                            registerLatch.countDown();
//                        } 
//                    }
//
//                    @Override
//                    public void onAction(ActionOut action) {
//                        System.out.println("onAction: " + action);
//                    }
//
//                    @Override
//                    public void onClose(int code, String reason, boolean remote) {
//                        System.out.printf("onClose: %d %s %s\n", code, reason,
//                                remote);
//                    }
//
//                    @Override
//                    public void onError(WebSocketError error) {
//                        System.err.println("onError: " + error);
//                    }
//
//                    @Override
//                    public void onMessage(MessageOut message) {
//                        System.out.println("onMessage: " + message);
//                    }
//
//                    @Override
//                    public void onOpen(int httpStatus, String httpStatusMessage) {
//                        System.out.printf("onOpen: %d %s\n", httpStatus,
//                                httpStatusMessage);
//                    }
//
//                    @Override
//                    public void onPing(long timestamp) {
//                        System.out.println("onPing: " + timestamp);
//                    }
//
//                });
//
//        ws.connectBlocking();
//
//        RegisterMessage msg = new RegisterMessage();
//        msg.setAuthorization("bearer " + accessToken);
//        msg.setSdid(deviceId);
//        msg.setCid("first");
//
//        ws.registerChannel(msg);
//        assertEquals(Boolean.TRUE,
//                new Boolean(registerLatch.await(10, TimeUnit.SECONDS)));
//
//        // Connect Firehose
//        FirehoseWebSocket firehoseWS = new FirehoseWebSocket(accessToken, deviceId, null, null, userId,
//                new ArtikCloudWebSocketCallback() {
//
//                    @Override
//                    public void onAck(Acknowledgement ack) {
//                        System.out.println("onAck: " + ack);
//                    }
//
//                    @Override
//                    public void onAction(ActionOut action) {
//                        System.out.println("onAction: " + action);
//                    }
//
//                    @Override
//                    public void onClose(int code, String reason, boolean remote) {
//                        System.out.printf("onClose: %d %s %s\n", code, reason,
//                                remote);
//                    }
//
//                    @Override
//                    public void onError(WebSocketError error) {
//                        System.err.println("onError: " + error);
//                    }
//
//                    @Override
//                    public void onMessage(MessageOut message) {
//                        System.out.println("Firehose: onMessage: " + message);
//                        messageLatch.countDown();
//                    }
//
//                    @Override
//                    public void onOpen(int httpStatus, String httpStatusMessage) {
//                        System.out.printf("onOpen: %d %s\n", httpStatus,
//                                httpStatusMessage);
//                    }
//
//                    @Override
//                    public void onPing(long timestamp) {
//                        System.err.println("onPing: " + timestamp);
//                    }
//
//                });
//
//        firehoseWS.connect();
//        
//        for (int i = 0; i < 1; i++) {
//            MessageIn message = new MessageIn();
//            message.setSdid(deviceId);
//            message.setTs(new Long(System.currentTimeMillis()));
//            
////            "fields": {
////                "colorRGB": {
////                    "b": {
////                        "type": "Integer",
////                        "unit": "",
////                        "isCollection": false,
////                        "description": "blue component",
////                        "metadata": {},
////                        "tags": null
////                    },
////                    "g": {
////                        "type": "Integer",
////                        "unit": "",
////                        "isCollection": false,
////                        "description": "green component",
////                        "metadata": {},
////                        "tags": null
////                    },
////                    "r": {
////                        "type": "Integer",
////                        "unit": "",
////                        "isCollection": false,
////                        "description": "red component",
////                        "metadata": {},
////                        "tags": null
////                    }
////                },
////                "intensity": {
////                    "type": "Integer",
////                    "unit": "",
////                    "isCollection": false,
////                    "description": "Changes the intensity of the hue light (value in percent)",
////                    "metadata": {},
////                    "tags": null
////                },
////                "state": {
////                    "type": "String",
////                    "unit": "",
////                    "isCollection": false,
////                    "description": "state",
////                    "metadata": {},
////                    "tags": null
////                }
//            
//            message.getData().put("state", "on");
//            message.getData().put("intensity", 100);
//            message.setCid("second");
//
//            ws.sendMessage(message);
//        }
//        
//        messageLatch.await(100, TimeUnit.SECONDS);
//
//        firehoseWS.closeBlocking();
//        
//        ws.closeBlocking();
//    }
//    
//    
//}