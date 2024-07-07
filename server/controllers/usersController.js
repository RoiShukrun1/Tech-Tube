import Account from '../models/usersModel.js';
import path from 'path';
import { getAccountFromDB } from '../services/usersServices.js';
import { deleteAccountFromDB } from '../services/usersServices.js';
import { patchAccountinDB } from '../services/usersServices.js';
import { saveBase64Image } from '../services/usersServices.js';

export const getAccount = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        const account = await getAccountFromDB(accountUsername);

        // if (!account) {
        //     return res.status(404).json({ message: 'Account not found' });
        // }

        res.status(200).json(account); // Send the account object as JSON response
    } catch (error) {
        console.error('Error fetching account:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const deleteAccount = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        await deleteAccountFromDB(accountUsername);

        res.status(200).json({ message: 'Account deleted successfully' }); // Send success message
    } catch (error) {
        console.error('Error deleting account:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const updateAccount = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        const updatedparms = req.body;

        const result = await patchAccountinDB(accountUsername, updatedparms);
        console.log(result)
        res.status(200).json(result);

    }
    catch (error) {
        console.error('Error update account:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const registerAccount = async (req, res) => {
    const session = await Account.startSession();
    session.startTransaction();
    try {
        const { nickname, username, password, subscriptions, image } = req.body;
        // Create a new User instance with the extracted data
        const newUser = new Account({ nickname, username, password, image: null, subscriptions });
        // Save the new user to the database
        await newUser.save({ session });

        // Save the Base64 image to disk
        const imagePath = `uploads/profilePictures/${username}.png`;
        if (image) {
            await saveBase64Image(image, path.join(process.cwd(), imagePath));
            newUser.image = `/${imagePath}`;
            await newUser.save({ session }); // Update the user with the image path
        }

        await session.commitTransaction();
        session.endSession();

        // Send a success response
        res.status(201).json({ message: 'User registered successfully!' });
    } catch (error) {
        await session.abortTransaction();
        session.endSession();
        
        if (error.code === 11000) {
            const field = Object.keys(error.keyValue)[0];
            return res.status(400).send({ message: `The ${field} is already in use. Please choose a different one.` });
        }
        console.error('Error during registration:', error);
        res.status(500).json({ error: error.message });
    }
};

