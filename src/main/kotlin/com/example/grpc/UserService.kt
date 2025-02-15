package com.example.grpc

import io.grpc.stub.StreamObserver

class UserService : UserServiceGrpc.UserServiceImplBase() {

    override fun getUser(request: GetUserRequest, responseObserver: StreamObserver<UserResponse>) {
        val userId = request.userId
        val userResponse = UserResponse.newBuilder()
            .setUserId(userId)
            .setName("john")
            .setEmail("john.menacho@grupolafuente.com")
            .build()

        responseObserver.onNext(userResponse)
        responseObserver.onCompleted()
    }

    override fun listUsers(request: ListUsersRequest, responseObserver: StreamObserver<UserResponse>) {
        val filter = request.filter

        val users = listOf(
            UserResponse.newBuilder().setUserId("1").setName("john").setEmail("john.menacho@grupolafuente.com").build(),
            UserResponse.newBuilder().setUserId("2").setName("rambo").setEmail("rambo.firstblood@grupolafuente.com").build(),
            UserResponse.newBuilder().setUserId("3").setName("charly").setEmail("charly.harper@grupolafuente.com").build()
        )

        users.forEach { user ->
            if (filter.isEmpty() || user.name.contains(filter, ignoreCase = true)) {
                responseObserver.onNext(user)
            }
        }

        responseObserver.onCompleted()
    }
}