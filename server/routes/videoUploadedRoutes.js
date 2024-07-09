import express from 'express';
import multer from 'multer';
import path from 'path';
import { uploadVideo } from '../controllers/videoUploadedController.js';

const router = express.Router();

// Configure multer for file uploads
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/uploadedVideos');
    },
    filename: (req, file, cb) => {
        const videoId = req.body.id; // Get the video ID from the form data
        const extension = path.extname(file.originalname); // Get the file extension
        const newFilename = `${videoId}${extension}`; // Use the video ID as the filename
        cb(null, newFilename);
    }
});

const upload = multer({ storage: storage });

router.post('/videos', upload.single('videoUploaded'), uploadVideo);

export default router;
