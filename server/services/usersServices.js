import { MongoClient } from 'mongodb';

export const getAccountFromDB = async (username) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('accounts');

        const account = await collection.findOne({ username: username });

        return account; // Return the fetched account
    } catch (error) {
        console.error('Error fetching account:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};

export const deleteAccountFromDB = async (username) => {
    const client = new MongoClient(process.env.CONNECTION_STRING);
    try {
        await client.connect(); // Connect to MongoDB

        const db = client.db('TechTitans');
        const collection = db.collection('accounts');

        const result = await collection.deleteOne({ username: username });

        if (result.deletedCount === 0) {
            throw new Error('Account not found'); // Throw an error if no account was deleted
        }
    } catch (error) {
        console.error('Error deleting account:', error);
        throw error; // Throw the error to be handled by the caller
    } finally {
        await client.close(); // Close the MongoDB client connection
    }
};


