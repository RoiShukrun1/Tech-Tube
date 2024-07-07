import { getVideoFromDB, getMainPageVideosFromDB, deleteVideoFromDB,
     patchVideoinDB, getPublisherVideosFromDB, getallVideosFromDB } from '../services/videosServices.js';

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
        const videoId = req.params.pid;
        await deleteVideoFromDB(videoId);

        res.status(200).json({ message: 'video deleted successfully' }); // Send success message
    } catch (error) {
        console.error('Error deleting video:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const updateVideo = async (req, res) => {
    try {
        const videoId = req.params.pid;
        const updatedparms = req.body;

        const result = await patchVideoinDB(videoId, updatedparms);

        res.status(200).json(result);
    }
    catch (error) {
        console.error('Error update video:', error);
        res.status(500).json({ message: 'Internal server error' }); // Send 500 error response
    }
};

export const getPublisherVideos = async (req, res) => {
    try {
        const publisher = req.params.id;
        const videos = await getPublisherVideosFromDB(publisher);

        res.status(200).json(videos);
    } catch (error) {
        console.error('Error fetching videos:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
};

export const getMainPageVideos = async (req, res) => {
    try {
        const mainPageVideos = await getMainPageVideosFromDB();

        res.status(200).json(mainPageVideos);
    } catch (error) {
        console.error('Error fetching videos:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
}

export const getAllVideos = async (req, res) => {
    try {
        const allVideos = await getallVideosFromDB();

        res.status(200).json(allVideos);
    } catch (error) {
        console.error('Error fetching videos:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
}