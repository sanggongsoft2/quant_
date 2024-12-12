package com.quant_socket.handlers;

import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.services.SocketLogService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;

    private static final String ENCODE_TYPE = "EUC-KR";

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf buf = (ByteBuf) msg;

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < buf.readableBytes(); i++) {
            final String hexString = String.format("%02X", buf.getByte(i));
            if(!hexString.equals("FF")) {
                final byte[] originalBytes = new byte[]{buf.getByte(i)};
                final String originalText = new String(originalBytes, Charset.forName("EUC-KR"));
                sb.append(originalText);
            } else {
                sb.append("\n");
            }
        }


        try {
//            final StringBuilder sb = new StringBuilder(buf.toString(Charset.forName(ENCODE_TYPE)));
            /*final int byte_length = buf.readableBytes();

            for (int index = 0; index < byte_length; index++) {
                byte b = buf.getByte(index);
                if (b == (byte) 0xFF) sb.setCharAt(index, '\n');
            }*/

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
