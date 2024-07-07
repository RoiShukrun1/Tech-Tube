import { getVideoFromDB } from '../services/videosServices.js';
import { deleteVideoFromDB } from '../services/videosServices.js';
import { patchVideoinDB } from '../services/videosServices.js';

export const getVideo = async (req, res) => {
    try {
        // const accountUsername = req.params.id;
        const videoId = req.params.pid;
        const video = await getVideoFromDB(videoId);

        // if (!account) {
        //     return res.status(404).json({ message: 'Account not found' });
        // }

        res.status(200).json(video); // Send the account object as JSON response
    } catch (error) {
        console.error('Error fetching video:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const deleteVideo = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        await deleteVideoFromDB(accountUsername);

        res.status(200).json({ message: 'video deleted successfully' }); // Send success message
    } catch (error) {
        console.error('Error deleting video:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const updateVideo = async (req, res) => {
    try {
        const accountUsername = req.params.id;
        const updatedparms = req.body;

        const result = await patchVideoinDB(accountUsername, updatedparms);
        console.log(result)
        res.status(200).json(result);

    }
    catch (error) {
        console.error('Error update video:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};