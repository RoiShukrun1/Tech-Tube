import mongoose from 'mongoose';

const videoSchema = new mongoose.Schema({
    id : String,
    video: String,
    thumbnail: String,
    title: String,
    publisher: String,
    publisherImage: String,
    views: Number,
    date: String,
    description: String,
    relatedVideos: [String],
    usersLikes: [String],
    usersUnlikes: [String],
    playlist: String,
    comments: [String],
});

const Video = mongoose.model('Video', videoSchema);
export default Video;
