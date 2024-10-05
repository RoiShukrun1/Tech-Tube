#include <iostream>
#include <cstring>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <thread>
#include <sstream>  // Include for std::istringstream and std::ostringstream
#include "video_recommendation.h"  // Include the new header file

using namespace std;

void handleClient(int client_sock) {
    char buffer[4096];
    int read_bytes;

    while (true) {
        read_bytes = recv(client_sock, buffer, sizeof(buffer), 0);
        if (read_bytes == 0) {
            std::cout << "Connection closed by client" << std::endl;
            break;
        } else if (read_bytes < 0) {
            perror("Error receiving data");
            break;
        } else {
            buffer[read_bytes] = '\0';  // Null-terminate the received string
            std::cout << "Received: " << buffer << std::endl;

            // Parse the received message (e.g., "WATCH user1,74")
            std::string message(buffer);
            std::istringstream iss(message);
            std::string command, userId, videoIdStr;
            if (std::getline(iss, command, ' ') && std::getline(iss, userId, ',') && std::getline(iss, videoIdStr)) {
                int videoId = std::stoi(videoIdStr);

                if (command == "WATCH") {
                    // Track the video watch event
                    watchVideo(userId, videoId);

                    // Get video recommendations
                    std::vector<int> recommendations = getRecommendations(videoId);

                    std::string response;
                    if (recommendations.empty()) {
                        // No related videos found, respond with "-1"
                        response = "-1";
                    } else {
                        // Convert recommendations to a comma-separated string
                        std::ostringstream oss;
                        for (size_t i = 0; i < recommendations.size(); ++i) {
                            oss << recommendations[i];
                            if (i < recommendations.size() - 1) {
                                oss << ",";
                            }
                        }
                        response = oss.str();
                    }

                    int sent_bytes = send(client_sock, response.c_str(), response.length(), 0);
                    if (sent_bytes < 0) {
                        perror("Error sending response to client");
                    }
                }
            }
        }
    }

    close(client_sock);  // Close the client socket when done
}
int main() {
    const int server_port = 5555;
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        perror("Error creating socket");
        return 1;
    }

    struct sockaddr_in sin;
    memset(&sin, 0, sizeof(sin));
    sin.sin_family = AF_INET;
    sin.sin_addr.s_addr = INADDR_ANY;
    sin.sin_port = htons(server_port);

    if (bind(sock, (struct sockaddr *)&sin, sizeof(sin)) < 0) {
        perror("Error binding socket");
        close(sock);
        return 1;
    }

    if (listen(sock, 5) < 0) {
        perror("Error listening on socket");
        close(sock);
        return 1;
    }

    std::cout << "Server is listening on port " << server_port << std::endl;

    while (true) {
        struct sockaddr_in client_sin;
        unsigned int addr_len = sizeof(client_sin);
        int client_sock = accept(sock, (struct sockaddr *)&client_sin, &addr_len);
        if (client_sock < 0) {
            perror("Error accepting client");
            continue;
        }

        // Create a new thread to handle the client
        std::thread clientThread(handleClient, client_sock);
        clientThread.detach();  // Detach the thread to allow it to run independently
    }

    close(sock);
    return 0;
}