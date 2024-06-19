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
        // 소켓 채널이 활성화될 때 포트 번호 저장
        final SocketChannel socketChannel = (SocketChannel) ctx.channel();
        final InetSocketAddress remoteAddress = socketChannel.remoteAddress();
        final InetSocketAddress localAddress = socketChannel.localAddress();
        ctx.channel().attr(AttributeKey.valueOf("port")).set(localAddress.getPort());
        ctx.channel().attr(AttributeKey.valueOf("remote_url")).set(remoteAddress.getHostString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        final ByteBuf in = (ByteBuf) msg;
        final int port = (int) ctx.channel().attr(AttributeKey.valueOf("port")).get();
        final String remoteUrl = (String) ctx.channel().attr(AttributeKey.valueOf("remote_url")).get();

        try {
            final String message = in.toString(Charset.forName("EUC-KR"));

            for (String logMessage : message.split("�")) {
                final SocketLog sl = new SocketLog();
                try {
                    sl.setPort(port);
                    sl.setRemote_url(remoteUrl);
                    sl.setLog(logMessage);
                    socketLogService.esHandler(sl);
                } catch (Exception e) {
                    sl.setError(e.getMessage());
                    e.printStackTrace();
                    // 예외 발생 시 소켓을 닫을지 결정
                    ctx.close();
                }
                socketLogService.addLog(sl);
            }
        } finally {
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        final int port = (int) ctx.channel().attr(AttributeKey.valueOf("port")).get();
        final String remoteUrl = (String) ctx.channel().attr(AttributeKey.valueOf("remote_url")).get();

        final SocketLog sl = new SocketLog();
        sl.setPort(port);
        sl.setRemote_url(remoteUrl);
        sl.setLog(cause.getLocalizedMessage());
        sl.setError(cause.getMessage());
        socketLogService.addLog(sl);
        cause.printStackTrace();
        ctx.close();
    }
}
