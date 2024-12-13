package com.quant_socket.handlers;

import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.services.SocketLogService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;

    private static final Charset ENCODE_TYPE = Charset.forName("EUC-KR");

    private int port;
    private String remote_url;
    private String msg;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*String filePath = "/Users/sanggong/Documents/test.txt";
        String content = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        for (String logMessage : content.split("�")) {
            socketLogService.esHandler(logMessage.trim());
        }*/
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        port = socketAddress.getPort();
        remote_url = socketAddress.getHostName();
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException, UnsupportedEncodingException {
        final ByteBuf buf = (ByteBuf) msg;

        final StringBuilder sb = new StringBuilder();

// 1. 모든 바이트 데이터를 한 번에 가져오기
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(0, bytes); // Netty ByteBuf에서 모든 데이터를 읽음

// 2. 바이트 배열을 순회하며 처리
        for (int i = 0; i < bytes.length; i++) {
            int currentByte = bytes[i] & 0xFF; // unsigned 처리

            // 0xFF인 경우
            if (currentByte == 0xFF) {
                sb.append("\n"); // 원하는 형식으로 변환
            } else {
                // 한글 포함된 데이터를 처리하기 위해 다음 바이트도 확인
                int nextByte = (i + 1 < bytes.length) ? (bytes[i + 1] & 0xFF) : -1;

                // 한글일 가능성이 있는 경우 처리
                if (nextByte >= 0xA1 && nextByte <= 0xFE && currentByte >= 0xA1) {
                    byte[] multiBytes = new byte[]{(byte) currentByte, (byte) nextByte};
                    sb.append(new String(multiBytes, ENCODE_TYPE)); // 멀티바이트로 변환
                    i++; // 다음 바이트를 이미 처리했으므로 건너뜀
                } else {
                    // 일반 바이트 처리
                    sb.append(new String(new byte[]{(byte) currentByte}, ENCODE_TYPE));
                }
            }
        }


        try {

            this.msg = sb.toString();

            for (String logMessage : this.msg.split("\n")) {
                socketLogService.esHandler(logMessage.trim());
            }
        } finally {
            buf.release();
        }
    }

//    sudo java -Dspring.profiles.active=prod -jar /home/sanggong/quant_socket.jar

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        final SocketLog socketLog = new SocketLog();
        socketLog.setPort(port);
        socketLog.setRemote_url(remote_url);
        socketLog.setLog(msg);
        socketLog.setError(cause.getMessage());
        socketLogService.addLog(socketLog);
        ctx.close();
    }

    public static String hexToEucKrString(String hexString) throws Exception {
        // HEX 문자열 -> 바이트 배열 변환
        byte[] bytes = hexStringToByteArray(hexString);

        // 바이트 배열 -> EUC-KR 문자열 변환
        return new String(bytes, Charset.forName("EUC-KR"));
    }

    private static byte[] hexStringToByteArray(String hexString) throws Exception {
        int length = hexString.length();
        if (length % 2 != 0) {
            throw new Exception("Invalid HEX string length");
        }

        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

}
