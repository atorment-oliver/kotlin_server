package com.example.grpc


import io.grpc.Server
import io.grpc.ServerBuilder

class GrpcServer(private val port: Int) {
    private val server: Server = ServerBuilder.forPort(port)
        .addService(UserService())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down gRPC server...")
            this@GrpcServer.stop()
        })
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}

fun main() {
    val port = 50051
    val server = GrpcServer(port)
    server.start()
    server.blockUntilShutdown()
}