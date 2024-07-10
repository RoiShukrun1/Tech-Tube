import { MongoClient } from 'mongodb';
import fs from 'fs';
import path from 'path';

export const patchUserinDB = async (username, updatedParams) => {

    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('users');

        const result = await collection.updateOne({ username: username }, { $set: updatedParams });

        if (result.matchedCount === 0) {
            throw new Error('Account not found'); // Throw an error if no account was updated
        }


        return await collection.findOne({ username: username });

    } catch (error) {
        console.error('Error updating account:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }

};

export const getUserFromDB = async (username) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('users');

        const user = await collection.findOne({ username: username });

        return user; // Return the fetched user
    } catch (error) {
        console.error('Error fetching user:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const deleteUserFromDB = async (username) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('users');

        const result = await collection.deleteOne({ username: username });

        if (result.deletedCount === 0) {
            throw new Error('User not found'); // Throw an error if no user was deleted
        }
    } catch (error) {
        console.error('Error deleting user:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const getSubscribersFromDB = async (username) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('users');

        // Use $in operator to find documents where subscriptions array contains username
        const subscribers = await collection.find({ subscriptions: { $in: [username] } }).toArray();


        return subscribers; // Return the fetched users as an array
    } catch (error) {
        console.error('Error fetching subscribers:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};
    
export const saveBase64Image = async (base64Image, filePath) => {
    const matches = base64Image.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/);
    if (!matches || matches.length !== 3) {
        throw new Error('Invalid base64 image');
    }
    const imageBuffer = Buffer.from(matches[2], 'base64');
    await ensureDirectoryExistence(filePath);
    await fs.promises.writeFile(filePath, imageBuffer);
};

const ensureDirectoryExistence = async (filePath) => {
    const dirname = path.dirname(filePath);
    try {
        await fs.promises.access(dirname);
    } catch (e) {
        await ensureDirectoryExistence(dirname);
        await fs.promises.mkdir(dirname);
    }
};



