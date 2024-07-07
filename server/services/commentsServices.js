import { MongoClient } from 'mongodb';

export const putCommentinDB = async (videoId, commentId, newComment) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId);
        const commentIdtoNum = parseInt(commentId);

        const result = await collection.updateOne(
            { id: videoIdtoNum, 'comments.id': commentIdtoNum },
            { $set: { 'comments.$': newComment } }
        );

        if (result.matchedCount === 0) {
            throw new Error('Comment not found'); // Throw an error if no comment was updated
        }

        const video = await collection.findOne({ id: parseInt(videoIdtoNum) });

        return video.comments.find(comment => comment.id === commentIdtoNum);

    } catch (error) {
        console.error('Error updating comment:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getCommentFromDB = async (videoId, commentId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId);
        const commentIdtoNum = parseInt(commentId);

        const video = await collection.findOne({ id: parseInt(videoIdtoNum) });

        return video.comments.find(comment => comment.id === commentIdtoNum);

    } catch (error) {
        console.error('Error fetching comment:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getCommentsFromDB = async (videoId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId);

        const video = await collection.findOne({ id: parseInt(videoIdtoNum) });
        return video.comments;

    } catch (error) {
        console.error('Error fetching video:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

// Define the function to delete a specific comment from MongoDB
export const deleteCommentFromDB = async (videoId, commentId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    
    try {
        // Connect to MongoDB
        await client.connect();

        // Access the TechTitans database and Videos collection
        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        // Convert parameters to integers if necessary
        const videoIdNum = parseInt(videoId);
        const commentIdNum = parseInt(commentId);

        // Delete the comment from the video based on videoId and commentId
        const result = await collection.updateOne(
            { id: videoIdNum },
            { $pull: { comments: { id: commentIdNum } } }
        );

        // Check if the deletion was successful
        if (result.modifiedCount > 0) {
            return { success: true };
        } else {
            throw new Error('Comment not found or not deleted');
        }

    } catch (error) {
        console.error('Error deleting comment:', error);
        throw error; // Throw the error to be handled by the caller

    } finally {
        // Ensure client connection is closed after operation
        await client.close();
    }
};