import User from '../models/usersModel.js';
import DeletedUser from '../models/deleteUserModel.js';
import fs from 'fs';
import path from 'path';
import { saveBase64Image } from '../services/usersServices.js';
import { getUserFromDB, deleteUserFromDB, 
    patchUserinDB, getSubscribersFromDB} from '../services/usersServices.js';

export const getUser = async (req, res) => {
    try {
        const userUsername = req.params.id;
        const user = await getUserFromDB(userUsername);

        // if (!user) {
        //     return res.status(404).json({ message: 'User not found' });
        // }

        res.status(200).json(user); // Send the user object as JSON response
    } catch (error) {
        console.error('Error fetching user:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const deleteUser = async (req, res) => {
    try {
        const userUsername = req.params.id;
        await deleteUserFromDB(userUsername);

        await DeletedUser.create({ username: userUsername });

        res.status(200).json({ message: 'user deleted successfully' }); // Send success message
    } catch (error) {
        console.error('Error deleting user:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const updateAccount = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        const updatedparms = req.body;

        const result = await patchUserinDB(accountUsername, updatedparms);
        res.status(200).json(result);

    }
    catch (error) {
        console.error('Error update account:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const getSubscribers = async (req, res) => {
    try {
        const username = req.params.id;
        const userSubscribes = await getSubscribersFromDB(username);

        res.status(200).json(userSubscribes);
    } catch (error) {
        console.error('Error fetching subscribers:', error);
        res.status(500).json({ message: 'Internal server error' });
    }

}

export const registerUser = async (req, res) => {
    const session = await User.startSession();
    session.startTransaction();
    try {
        const { nickname, username, password, subscriptions, image } = req.body;

        const existingDeletedUser = await DeletedUser.findOne({ username });
        if (existingDeletedUser) {
          throw new Error('Username has been used before and cannot be reused');
        }    

        // Check if the username is already taken
        const existingUserByUsername = await User.findOne({ username });
        if (existingUserByUsername) {
            return res.status(400).send({ message: 'The username is already in use. Please choose a different one.' });
        }

        // Check if the nickname is already taken
        const existingUserByNickname = await User.findOne({ nickname });
        if (existingUserByNickname) {
            return res.status(400).send({ message: 'The nickname is already in use. Please choose a different one.' });
        }
        // Create a new User instance with the extracted data
        const newUser = new User({ nickname, username, password, image: null, subscriptions });
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


export const saveBanner = async (req, res) => {
    const image = req.body.base64Image;
    const username = req.body.username;
    try {
        const imagePath = `uploads/bannerPictures/${username}.png`;
        if (image) {
            await saveBase64Image(image, path.join(process.cwd(), imagePath));
        }
        res.status(200).json({ imagePath });
    } catch (error) {
        console.error('Error saving image:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
};



