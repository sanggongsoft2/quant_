package com.quant_socket.components;

import com.quant_socket.handlers.TelnetServerHandler;
import com.quant_socket.repos.*;
import com.quant_socket.services.EquitiesSnapshotService;
import com.quant_socket.services.SocketLogService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelnetServer implements CommandLineRunner {

    private final SocketLogRepo repo;
    private final EquitiesSnapshotRepo esRepo;
    private final ProductRepo productRepo;
    private final EquityIndexIndicatorRepo equityIndexIndicatorRepo;
    private final SecOrderFilledRepo secOrderFilledRepo;

    private final EquitiesSnapshotService esService;
    private final SocketLogService socketLogService;
    private final int[] ports = new int[]{22902, 22903, 22904, 22905, 23902, 23903, 23904};
    @Override
    public void run(String... args) throws Exception {
        if(esService.addProducts(productRepo.findAll())) {
            setPorts();
        }
    }

    private void setPorts() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new TelnetServerHandler(repo, esRepo, equityIndexIndicatorRepo, secOrderFilledRepo, esService, socketLogService));
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