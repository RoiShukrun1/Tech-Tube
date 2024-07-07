import Video from '../models/videoUploadedModel.js';
import fs from 'fs';
import path from 'path';
import { saveBase64Image } from '../services/videoUploadedServices.js';

export const uploadVideo = async (req, res) => {
    try {
        const thumbnailPath = `uploads/thumbnail/${req.body.id}.png`; // Create a path for the thumbnail
        await saveBase64Image(req.body.thumbnail, thumbnailPath); // Save the thumbnail
        const video = new Video({
            id: req.body.id,
            video: req.file.path,
            thumbnail: thumbnailPath, // Save the path to the thumbnail in the document
            title: req.body.title,
            publisher: req.body.publisher,
            publisherImage: req.body.publisherImage,
            views: req.body.views,
            date: req.body.date,
            description: req.body.description,
            relatedVideos: req.body.relatedVideos,
            usersLikes: req.body.usersLikes,
            usersUnlikes: req.body.usersUnlikes,
            playlist: req.body.playlist,
            comments: req.body.comments
        });

        await video.save();
        res.status(201).json({ message: 'Video uploaded successfully', video: video });
    } catch (err) {
        console.error('Error uploading video:', err);
        res.status(400).json({ message: err.message });
    }
};
