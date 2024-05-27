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

    private Integer port;
    private String remote_url;
    private String msg;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 소켓 채널이 활성화될 때 포트 번호 저장
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        InetSocketAddress inetSocketAddress = socketChannel.localAddress();
        port = inetSocketAddress.getPort();
        remote_url = inetSocketAddress.getHostString();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf in = (ByteBuf) msg;

        int len = in.writerIndex();
        byte[] msgByte = new byte[len];
        in.readBytes(msgByte);
        this.msg = new String(msgByte, StandardCharsets.UTF_8);
        log.debug("received message : {}, remote_url: {}, port: {}", this.msg, this.remote_url, this.port);

        if(!this.msg.isBlank()) {
            final SocketLog sl = new SocketLog();

            sl.setLog(this.msg);
            sl.setPort(this.port);
            sl.setRemote_url(remote_url);
            socketLogService.addLog(sl);

            socketLogService.esHandler(this.msg);
        }

        in.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        final SocketLog sl = new SocketLog();
        sl.setLog(this.msg);
        sl.setPort(this.port);
        sl.setRemote_url(remote_url);
        sl.setError(cause.toString());
        socketLogService.addLog(sl);
        ctx.close();
    }
}
