import express from 'express';
import bodyParser from 'body-parser';
import mongoose from 'mongoose';
import cors from 'cors';
// import accountRoutes from './routes/accountsRoutes.js';
import usersRoutes from './routes/usersRoutes.js';
import customEnv from 'custom-env';

customEnv.env(process.env.NODE_ENV, './config')

const mongoURI = process.env.CONNECTION_STRING;
const server = express();
const port = process.env.PORT;

server.use(cors()); // Use the CORS middleware
server.use(bodyParser.json({ limit: '10mb' })); // Adjust the limit as needed
server.use('/uploads', express.static('uploads')); // Serve static files from the uploads folder

// server.use('/api/users', accountRoutes);
server.use('/api/users', usersRoutes);

mongoose.connect(mongoURI) // Connect to MongoDB
    .then(() => console.log('MongoDB connected')) // Log success message on successful connection
    .catch(err => console.error(err)); // Log error message on connection failure

server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
