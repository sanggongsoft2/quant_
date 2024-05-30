package com.quant_socket.handlers;

import com.quant_socket.models.Logs.*;
import com.quant_socket.services.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;
    private final SocketLog socketLog = new SocketLog();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 소켓 채널이 활성화될 때 포트 번호 저장
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        InetSocketAddress inetSocketAddress = socketChannel.remoteAddress();
        socketLog.setPort(inetSocketAddress.getPort());
        socketLog.setRemote_url(inetSocketAddress.getHostString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf in = (ByteBuf) msg;

        try {
            int len = in.writerIndex();
            byte[] msgByte = new byte[len];
            in.readBytes(msgByte);
            socketLog.setLog(new String(msgByte, StandardCharsets.UTF_8));

            if(!socketLog.getLog().isBlank()) {
                for (String log : socketLog.getLog().split("�")) {
                    socketLogService.esHandler(log+"�");
                }
            }
        } catch (Exception e) {
            socketLog.setError(e.getMessage());
            ctx.close();
        } finally {
            socketLogService.addLog(socketLog);
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        socketLog.setError(cause.getMessage());
        ctx.close();
    }
}
