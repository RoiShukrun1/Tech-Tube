import mongoose from 'mongoose';

const { Schema } = mongoose;

// Define a schema for the video
const videoSchema = new Schema({
    videoUploaded: { type: String, required: true }, // URL to the uploaded video file
    thumbnail: { type: String, required: true }, // URL to the thumbnail image
    title: { type: String, required: true }, // Title of the video
    publisher: { type: String, required: true }, // Name of the publisher
    publisherImage: { type: String, required: true }, // URL to the publisher's image
    views: { type: Number, default: 0 }, // Number of views, default is 0
    date: { type: Date, default: Date.now }, // Date when the video was uploaded, default is current date/time
    description: { type: String }, // Description of the video
    relatedVideos: [{ id: { type: Number } }], // Array of related video IDs
    usersLikes: [{ type: Schema.Types.ObjectId, ref: 'User' }], // Array of users who liked the video (assuming User model exists)
    usersUnlikes: [{ type: Schema.Types.ObjectId, ref: 'User' }], // Array of users who unliked the video (assuming User model exists)
    comments: [{
        id: { type: Number, required: true }, // Comment ID
        username: { type: String, required: true }, // Username of the commenter
        image: { type: String }, // URL to the commenter's image
        date: { type: Date, default: Date.now }, // Date when the comment was posted
        comment: { type: String, required: true }, // Comment text
        likes: { type: Number, default: 0 }, // Number of likes on the comment
        usersLikedId: [{ id: { type: Number } }], // Array of user IDs who liked the comment
        usersUnlikedId: [{ id: { type: Number } }] // Array of user IDs who unliked the comment
    }]
});

// Export the Video model based on the videoSchema
export default mongoose.model('Video', videoSchema);
