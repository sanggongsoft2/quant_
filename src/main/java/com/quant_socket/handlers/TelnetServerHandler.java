package com.quant_socket.handlers;

import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.services.SocketLogService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
public class TelnetServerHandler extends ChannelInboundHandlerAdapter {

    private final SocketLogService socketLogService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf buf = (ByteBuf) msg;

        try {
            final String message = buf.toString(Charset.forName("EUC-KR"));

            for (String logMessage : message.split("ÿ")) {
                socketLogService.esHandler(logMessage);
            }
        } finally {
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }
}
