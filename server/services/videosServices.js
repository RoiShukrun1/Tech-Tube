import { MongoClient } from 'mongodb';

export const patchVideoinDB = async (videoId, updatedParams) => {

    const client = new MongoClient(process.env.CONNECTION_STRING);

    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const result = await collection.updateOne({ id: videoId }, { $set: updatedParams });

        if (result.matchedCount === 0) {
            throw new Error('Video not found'); // Throw an error if no account was updated
        }

        return await collection.findOne({ id: videoId });

    } catch (error) {
        console.error('Error updating video:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }

};

export const getVideoFromDB = async (videoId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const video = await collection.findOne({ id: videoId });

        return video; // Return the fetched account
    } catch (error) {
        console.error('Error fetching video:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const deleteVideoFromDB = async (videoId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const result = await collection.deleteOne({ id: videoId });

        if (result.deletedCount === 0) {
            throw new Error('video not found'); // Throw an error if no account was deleted
        }
    } catch (error) {
        console.error('Error deleting video:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getPublisherVideosFromDB = async (publisher) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const videos = await collection.find({ publisher: publisher }).toArray();

        return videos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getMainPageVideosFromDB = async () => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        // Step 1: Fetch top 10 videos by views
        const topVideos = await collection.find({}).sort({ views: -1 }).limit(10).toArray();

        // Step 2: Add the top 10 video ids to an array
        const topVideoIds = topVideos.map(video => video._id);

        // Step 3: Fetch 10 random videos excluding the top 10
        const randomVideos = await collection.aggregate([
            { $match: { _id: { $nin: topVideoIds } } },
            { $sample: { size: 10 } }
        ]).toArray();

        // Combine top 10 and random 10 videos
        const combinedVideos = [...topVideos, ...randomVideos];

        // You can now use combinedVideos array as per your requirement
        return combinedVideos;

    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getallVideosFromDB = async () => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const videos = await collection.find({}).sort({ id: 1 }).toArray();

        return videos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getRelatedVideosFromDB = async (videoId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const video = await collection.findOne({ id: videoId });

        const playlist = video.playlist;

        const relatedVideos = await collection.find({
            playlist: playlist,
            id: { $ne: video.id }
        }).toArray();

        return relatedVideos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};
