// Import mongoose library using ES6 import syntax
import mongoose from 'mongoose';

// Define a schema for the comment
const commentSchema = new mongoose.Schema({
    id: { type: Number, required: true }, // Unique identifier for the comment
    username: { type: String, required: true }, // Username of the commenter
    image: { type: String, required: true }, // Image URL of the commenter
    date: { type: Date, required: true }, // Date when the comment was posted
    comment: { type: String, required: true }, // The comment text itself
    likes: { type: Number, required: true }, // Number of likes on the comment
    usersLikedId: [{ type: Number, required: false }], // Array of user IDs who liked the comment
    usersUnLikedId: [{ type: Number, required: false }] // Array of user IDs who unliked the comment
});

// Export the Comment model based on the commentSchema
export default mongoose.model('Comment', commentSchema);
