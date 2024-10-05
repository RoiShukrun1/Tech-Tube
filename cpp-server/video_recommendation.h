#ifndef VIDEO_RECOMMENDATION_H
#define VIDEO_RECOMMENDATION_H

#include <unordered_map>
#include <vector>
#include <string>
#include <algorithm>

// Declare the data structures
extern std::unordered_map<int, std::unordered_map<int, int>> videoWatchCounts;
extern std::unordered_map<std::string, std::vector<int>> userWatchHistory;  // Full watch history

// Declare the functions
void incrementWatchCount(int videoId1, int videoId2);
void watchVideo(const std::string& userId, int videoId);
std::vector<int> getRecommendations(int videoId);

#endif
