package com.example.demo.config;

// import com.example.demo.service.UserService;
import com.example.demo.service.UserService;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
// import com.example.demo.proto.StockTradingServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Config {

    @Bean
    public Server grpcServer(UserService userService) throws IOException {
        return NettyServerBuilder
                .forPort(9090)
                .addService(userService)
                .build()
                .start();
    }
}
