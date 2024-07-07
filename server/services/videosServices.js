import { MongoClient } from 'mongodb';

export const patchVideoinDB = async (videoId, updatedParams) => {

    const client = new MongoClient(process.env.CONNECTION_STRING);

    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId); 

        const result = await collection.updateOne({ id: videoIdtoNum }, { $set: updatedParams });

        if (result.matchedCount === 0) {
            throw new Error('Video not found'); // Throw an error if no account was updated
        }

        return await collection.findOne({ id: videoIdtoNum });

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
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId); 
        const video = await collection.findOne({ id: parseInt(videoIdtoNum) });

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
        const collection = db.collection('Videos');

        const videoIdtoNum = parseInt(videoId); 

        const result = await collection.deleteOne({ id: videoIdtoNum });

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
        const collection = db.collection('Videos');

        const videos = await collection.find({ publisher: publisher }).toArray();

        return videos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
}

export const getMainPageVideosFromDB = async () => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('Videos');

        const videos = await collection.find({}).toArray();

        return videos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
}
