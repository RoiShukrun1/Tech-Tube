import { MongoClient } from 'mongodb';

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


