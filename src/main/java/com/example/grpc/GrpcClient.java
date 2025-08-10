package com.example.grpc;

import com.example.grpc.proto.HelloRequest;
import com.example.grpc.proto.HelloResponse;
import com.example.grpc.proto.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Carl
 */
@Log4j2
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
          .usePlaintext()
          .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub
          = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
          .setFirstName("Baeldung")
          .setLastName("gRPC")
          .build());

        log.info(helloResponse);

        System.out.println(helloResponse);

        channel.shutdown();
    }
}