import express from 'express';
import bodyParser from 'body-parser';
// import mongoose from 'mongoose';
import routerComments from './routes/comments.js';

const server = express();
const port = 5001;

server.use(bodyParser.json);

server.use('/api/Comments', routerComments);

server.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});