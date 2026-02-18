package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repsitory.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends com.example.user.grpc.UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;
    
    @Override
    public void createUser(com.example.user.grpc.CreateUserRequest createUserRequest, StreamObserver<com.example.user.grpc.UserResponse> userResponseStreamObserver){
        try {
            User user = new User();
            user.setName(createUserRequest.getName());
            user.setEmail(createUserRequest.getEmail());
            
            User savedUser = userRepository.save(user);
            
            com.example.user.grpc.UserResponse response = com.example.user.grpc.UserResponse.newBuilder()
                    .setUser(com.example.user.grpc.User.newBuilder()
                            .setId(savedUser.getId())
                            .setName(savedUser.getName())
                            .setEmail(savedUser.getEmail())
                            .build())
                    .build();
            
            userResponseStreamObserver.onNext(response);
            userResponseStreamObserver.onCompleted();
        } catch (Exception e) {
            userResponseStreamObserver.onError(e);
        }


    }
    
    @Override
    public void getUserById(com.example.user.grpc.GetUserRequest request,
                           io.grpc.stub.StreamObserver<com.example.user.grpc.UserResponse> responseObserver) {
        try {
            User user = userRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            com.example.user.grpc.UserResponse response = com.example.user.grpc.UserResponse.newBuilder()
                    .setUser(com.example.user.grpc.User.newBuilder()
                            .setId(user.getId())
                            .setName(user.getName())
                            .setEmail(user.getEmail())
                            .build())
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
    
    @Override
    public void getAllUsers(com.google.protobuf.Empty request,
                           io.grpc.stub.StreamObserver<com.example.user.grpc.UserListResponse> responseObserver) {
        try {
            List<User> users = userRepository.findAll();
            
            com.example.user.grpc.UserListResponse.Builder builder = com.example.user.grpc.UserListResponse.newBuilder();
            for (User user : users) {
                builder.addUsers(com.example.user.grpc.User.newBuilder()
                        .setId(user.getId())
                        .setName(user.getName())
                        .setEmail(user.getEmail())
                        .build());
            }
            
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
    
    @Override
    public void updateUser(com.example.user.grpc.UpdateUserRequest request,
                          io.grpc.stub.StreamObserver<com.example.user.grpc.UserResponse> responseObserver) {
        try {
            User user = userRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            
            User updatedUser = userRepository.save(user);
            
            com.example.user.grpc.UserResponse response = com.example.user.grpc.UserResponse.newBuilder()
                    .setUser(com.example.user.grpc.User.newBuilder()
                            .setId(updatedUser.getId())
                            .setName(updatedUser.getName())
                            .setEmail(updatedUser.getEmail())
                            .build())
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
    
    @Override
    public void deleteUser(com.example.user.grpc.DeleteUserRequest request,
                          io.grpc.stub.StreamObserver<com.example.user.grpc.DeleteUserResponse> responseObserver) {
        try {
            userRepository.deleteById(request.getId());
            
            com.example.user.grpc.DeleteUserResponse response = com.example.user.grpc.DeleteUserResponse.newBuilder()
                    .setMessage("User deleted successfully")
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
