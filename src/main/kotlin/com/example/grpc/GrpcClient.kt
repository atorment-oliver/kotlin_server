package com.example.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = UserServiceGrpc.newBlockingStub(channel)

    val getUserRequest = GetUserRequest.newBuilder().setUserId("123").build()
    val userResponse = stub.getUser(getUserRequest)
    println("User Response: $userResponse")

    val listUsersRequest = ListUsersRequest.newBuilder().setFilter("a").build()
    println("Streaming users with filter 'a':")
    stub.listUsers(listUsersRequest).forEachRemaining { user ->
        println("User: ${user.name} (${user.email})")
    }

    channel.shutdown()
}