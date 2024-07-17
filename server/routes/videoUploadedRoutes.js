import express from 'express';
import multer from 'multer';
import path from 'path';
import { uploadVideo } from '../controllers/videoUploadedController.js';
import { authenticate } from '../controllers/tokenController.js';

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

// Set the limits for the file size and field size
const upload = multer({
    storage: storage,
    limits: {
        fileSize: 100 * 1024 * 1024, // 100 MB max file size
        fieldSize: 25 * 1024 * 1024, // 25 MB max field size for each field
    }
});

router.post('/videos', upload.single('videoUploaded'),authenticate, uploadVideo);

export default router;
