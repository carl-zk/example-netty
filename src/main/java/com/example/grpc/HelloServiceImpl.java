package com.example.grpc;

import com.example.grpc.proto.HelloRequest;
import com.example.grpc.proto.HelloResponse;
import com.example.grpc.proto.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 *
 * @author Carl
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(
            HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        System.out.println(request);

        String greeting = new StringBuilder()
          .append("Hello, ")
          .append(request.getFirstName())
          .append(" ")
          .append(request.getLastName())
          .toString();

        HelloResponse response = HelloResponse.newBuilder()
          .setGreeting(greeting)
          .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}