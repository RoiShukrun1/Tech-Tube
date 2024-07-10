import express from 'express';
import bodyParser from 'body-parser';
import mongoose from 'mongoose';
import cors from 'cors';
import usersRoutes from './routes/usersRoutes.js';
import reactRoute from './routes/reactRoute.js';
import customEnv from 'custom-env';
import cookieParser from 'cookie-parser';
import tokenRoutes from './routes/tokenRoutes.js';
import videoUploadedRoutes from './routes/videoUploadedRoutes.js'; // Import video routes
import mainPageVideosRouter from './routes/mainPageVideosRouter.js';
import path from 'path';
import { fileURLToPath } from 'url';

// Load environment variables from ./config based on the NODE_ENV
customEnv.env(process.env.NODE_ENV, './config');

const mongoURI = process.env.CONNECTION_STRING;
const server = express();
const port = process.env.PORT; // Default to 5000 if PORT is not specified

// Middleware setup
server.use(cookieParser());
server.use(cors({
    origin: 'http://localhost:3000',
    credentials: true
}));
server.use(bodyParser.json({ limit: '10mb' }));
server.use('/uploads', express.static('uploads')); // Serve static files correctly

// Route setup
server.use('/api/users', usersRoutes);
server.use('/api/token', tokenRoutes);
server.use('/api/users/:id', videoUploadedRoutes); // Use video routes
server.use('/api/videos',mainPageVideosRouter);
server.use('/', reactRoute); // Use video routes

// Get the directory name of the current module
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Serve the React app
server.use(express.static(path.join(__dirname, '../asp-tech-titans/build')));

// Connect to MongoDB
mongoose.connect(mongoURI)
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error(err));

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
