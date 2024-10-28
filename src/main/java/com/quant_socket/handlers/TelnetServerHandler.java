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

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;

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

        try {
            this.msg = buf.toString(Charset.forName("EUC-KR"));
            for (String logMessage : this.msg.split("�")) {
                socketLogService.esHandler(logMessage.trim());
            }
        } catch (Exception ignored) {

        } finally {
            buf.release();
        }
    }

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
}
