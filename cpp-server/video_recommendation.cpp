#include "video_recommendation.h"

// Define the data structures
std::unordered_map<int, std::unordered_map<int, int>> videoWatchCounts;
std::unordered_map<std::string, std::vector<int>> userWatchHistory;  // Full watch history

// Define the functions
void incrementWatchCount(int videoId1, int videoId2) {
    if (videoId1 != videoId2) {
        videoWatchCounts[videoId1][videoId2]++;
    }
}

void watchVideo(const std::string& userId, int videoId) {
    // Check if the user has any previously watched videos
    if (userWatchHistory.find(userId) != userWatchHistory.end()) {
        std::vector<int>& watchedVideos = userWatchHistory[userId];

        // Increment the watch count for each previously watched video
        for (int previousVideoId : watchedVideos) {
            incrementWatchCount(previousVideoId, videoId);
            incrementWatchCount(videoId, previousVideoId);
        }

        // Add the new video to the user's watch history
        watchedVideos.push_back(videoId);
    } else {
        // If it's the user's first video, just add the video to their history
        userWatchHistory[userId] = { videoId };
    }
}

std::vector<int> getRecommendations(int videoId) {
    std::vector<std::pair<int, int>> recommendations;

    for (const auto& pair : videoWatchCounts[videoId]) {
        recommendations.push_back(pair);
    }

    // Sort recommendations by the count in descending order
    std::sort(recommendations.begin(), recommendations.end(),
              [](const std::pair<int, int>& a, const std::pair<int, int>& b) {
                  return b.second > a.second;
              });

    std::vector<int> recommendedVideos;
    for (size_t i = 0; i < recommendations.size() && recommendedVideos.size() < 10; ++i) {
        recommendedVideos.push_back(recommendations[i].first);
    }

    return recommendedVideos;
}
