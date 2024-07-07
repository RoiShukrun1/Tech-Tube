import express from 'express';
import { getMainPageVideos } from '../controllers/videosController.js';

const mainPageVideosRouter = express.Router();

mainPageVideosRouter.route('/')
    .get(getMainPageVideos)

export default mainPageVideosRouter;
