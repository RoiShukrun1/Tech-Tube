import Account from '../models/accountsModel.js';
import fs from 'fs';
import path from 'path';

const ensureDirectoryExistence = async (filePath) => {
    const dirname = path.dirname(filePath);
    try {
        await fs.promises.access(dirname);
    } catch (e) {
        await ensureDirectoryExistence(dirname);
        await fs.promises.mkdir(dirname);
    }
};

const saveBase64Image = async (base64Image, filePath) => {
    const matches = base64Image.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/);
    if (!matches || matches.length !== 3) {
        throw new Error('Invalid base64 image');
    }
    const imageBuffer = Buffer.from(matches[2], 'base64');
    await ensureDirectoryExistence(filePath);
    await fs.promises.writeFile(filePath, imageBuffer);
    console.log('Image saved at:', filePath);
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
