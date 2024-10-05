import { MongoClient } from 'mongodb';

export const patchVideoinDB = async (videoId, updatedParams) => {

    const client = new MongoClient(process.env.CONNECTION_STRING);

    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        // Check if updatedParams contains 'usersLikes' and convert if necessary
        if (updatedParams.usersLikes && typeof updatedParams.usersLikes === 'string') {
            if (updatedParams.usersLikes === '[]') {
                updatedParams.usersLikes = [];
            } else {
                // Assuming the string is in the format "[ab, cd]"
                const users = updatedParams.usersLikes.match(/[\w]+/g); // Extracts words (usernames)
                if (users) {
                    updatedParams.usersLikes = users.map(username => ({ username }));
                }
            }
        }

        // Check if updatedParams contains 'usersLikes' and convert if necessary
        if (updatedParams.usersUnlikes && typeof updatedParams.usersUnlikes === 'string') {
            if (updatedParams.usersUnlikes === '[]') {
                updatedParams.usersUnlikes = [];
            } else {
                // Assuming the string is in the format "[ab, cd]"
                const users = updatedParams.usersUnlikes.match(/[\w]+/g); // Extracts words (usernames)
                if (users) {
                    updatedParams.usersUnlikes = users.map(username => ({ username }));
                }
            }
        }

        // Check if updatedParams contains views as string and convert to intager if necessary
        if (updatedParams.views && typeof updatedParams.views === 'string') {
            if (updatedParams.views === '' && updatedParams.views) {
                updatedParams.views = 0;
            } else {
                updatedParams.views = parseInt(updatedParams.views);
            }
        }        

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
        const topVideos = await collection.find({}).sort({ views: -1 }).limit(10).toArray();
        const topVideoIds = topVideos.map(video => video._id);
        const randomVideos = await collection.aggregate([
            { $match: { _id: { $nin: topVideoIds } } },
            { $sample: { size: 10 } }
        ]).toArray();
        const combinedVideos = [...topVideos, ...randomVideos];
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
export const getRandomVideosFromDB = async (videoId) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB
        const db = client.db('TechTitans');
        const collection = db.collection('videos');
        // Fetch the video corresponding to the given videoId to exclude it from the random selection
        const video = await collection.findOne({ id: videoId });
        // Fetch random videos, excluding the current video
        const randomVideos = await collection.aggregate([
            { $match: { id: { $ne: video.id } } },  // Exclude the current video
            { $sample: { size: 10 } }  // Select 10 random videos
        ]).toArray();
        return randomVideos; 
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; 
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getCatagoryVideosFromDB = async (videoCatagory) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('videos');

        const video = await collection.findOne({ playlist: videoCatagory });

        const playlist = video.playlist;

        const relatedVideos = await collection.find({
            playlist: playlist,
        }).toArray();

        return relatedVideos; // Return the fetched account
    } catch (error) {
        console.error('Error fetching videos:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};
