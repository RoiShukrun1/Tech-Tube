import { getVideoFromDB, getMainPageVideosFromDB, deleteVideoFromDB,
     patchVideoinDB, getPublisherVideosFromDB, getallVideosFromDB, 
     getRelatedVideosFromDB,getCatagoryVideosFromDB,getRandomVideosFromDB } from '../services/videosServices.js';

import { sendWatchEvent } from '../services/cppServerService.js';

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

export const getRelatedVideos = async (req, res) => {
    try {
        const videoId = req.params.pid;
        let relatedVideos = [];

        if (req.user && req.user.username) {
            const relatedVideoIds = await sendWatchEvent(req.user.username, videoId);
            const relatedVideosPromises = relatedVideoIds.map(id => getVideoFromDB(id));
            relatedVideos = await Promise.all(relatedVideosPromises);
            relatedVideos = relatedVideos.filter(video => video !== null);
            if (relatedVideos.length > 10 ) {
                relatedVideos.sort((a, b) => b.views - a.views);  
                relatedVideos = relatedVideos.slice(0, 10);
            }
            if (relatedVideos.length < 6) {
                const remainingCount = 10 - relatedVideos.length;
                const additionalRelatedVideos = await getRandomVideosFromDB(videoId);
                const additionalVideos = additionalRelatedVideos.filter(
                    video => !relatedVideos.some(v => v.id === video.id)
                ).slice(0, remainingCount);

                relatedVideos = relatedVideos.concat(additionalVideos);
            }
        } else {
            // User is not logged in, fetch related videos directly from the DB
            relatedVideos = await getRandomVideosFromDB(videoId);
        }
        relatedVideos.sort((a, b) => b.views - a.views);  
        res.status(200).json(relatedVideos);
    } catch (error) {
        console.error('Error fetching related videos:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
};

export const getCatagoryVideos = async (req, res) => {
    try {
        const videoCatagory = req.params.pid;
        const videos = await getCatagoryVideosFromDB(videoCatagory);
        res.status(200).json(videos);
    } catch (error) {
        console.error('Error fetching videos:', error);
        res.status(500).json({ message: 'Internal server error' });
    }
};