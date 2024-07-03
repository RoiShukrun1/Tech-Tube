import express from 'express';
import bodyParser from 'body-parser';
import mongoose from 'mongoose';
import cors from 'cors';
import accountRoutes from './routes/accountsRoutes.js';

const mongoURI = 'mongodb+srv://dbadmin:5ql4LLFK1qPXF3fB@techtube.xkwg3ot.mongodb.net/TechTitans?retryWrites=true&w=majority&appName=TechTube';
const server = express();
const port = 5001;

server.use(cors()); // Use the CORS middleware
server.use(bodyParser.json({ limit: '10mb' })); // Adjust the limit as needed
server.use('/uploads', express.static('uploads')); // Serve static files from the uploads folder

server.use('/accounts', accountRoutes);

mongoose.connect(mongoURI, { useNewUrlParser: true, useUnifiedTopology: true }) // Connect to MongoDB
    .then(() => console.log('MongoDB connected')) // Log success message on successful connection
    .catch(err => console.error(err)); // Log error message on connection failure

server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
