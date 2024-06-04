package com.quant_socket.handlers;

import com.quant_socket.models.Logs.*;
import com.quant_socket.services.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;

    private Integer port;
    private String remote_url;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 소켓 채널이 활성화될 때 포트 번호 저장
        final SocketChannel socketChannel = (SocketChannel) ctx.channel();
        final InetSocketAddress inetSocketAddress = socketChannel.remoteAddress();
        port = inetSocketAddress.getPort();
        remote_url = inetSocketAddress.getHostString();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf in = (ByteBuf) msg;

        final String message = in.toString(CharsetUtil.UTF_8);

        try {
            for (String logMessage : message.split("�")) {
                final SocketLog sl = new SocketLog();
                sl.setPort(port);
                sl.setRemote_url(remote_url);
                sl.setLog(logMessage);
                socketLogService.addLog(sl);
                socketLogService.esHandler(logMessage+"�");
            }
        } finally {
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        final SocketLog sl = new SocketLog();
        sl.setPort(port);
        sl.setRemote_url(remote_url);
        sl.setLog(cause.getLocalizedMessage());
        sl.setError(cause.getMessage());
        socketLogService.addLog(sl);
        log.error("ERROR : {}", cause.getMessage());
        ctx.close();
    }
}
