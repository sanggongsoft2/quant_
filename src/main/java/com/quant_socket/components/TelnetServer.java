package com.quant_socket.components;

import com.quant_socket.handlers.TelnetServerHandler;
import com.quant_socket.services.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelnetServer implements CommandLineRunner {

    private final SocketLogService socketLogService;
    private final ProductService productService;
    private final SignalService signalService;
    /*private final int[] ports = new int[]{22902, 22903, 22904, 22905, 23902, 23903, 23904, 24103, 24102, 24104, 24902, 24904, 24903};*/
    private final int[] ports = new int[]{22103, 22102, 22104, 22105, 23103, 23102, 23104, 24103, 24102, 24104};
    @Override
    public void run(String... args) throws Exception {
        log.info("SERVER CHARSET : {}", Charset.defaultCharset());
        signalService.refresh();
        if(productService.refreshProducts()) {
            log.info("STARTED TELNET SERVER!!");
            setPorts();
        }
    }

    private void setPorts() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup(ports.length);
        EventLoopGroup workerGroup = new NioEventLoopGroup(32);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new TelnetServerHandler(socketLogService));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            final List<ChannelFuture> futures = new ArrayList<>();

            for (int port : ports) {
                futures.add(b.bind(port));
            }

            for (ChannelFuture future : futures) {
                future.sync().channel().closeFuture().sync();
            }
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}