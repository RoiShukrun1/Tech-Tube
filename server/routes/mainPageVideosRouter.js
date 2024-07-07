import express from 'express';
import { getMainPageVideos, getAllVideos } from '../controllers/videosController.js';

const mainPageVideosRouter = express.Router();

mainPageVideosRouter.route('/')
    .get(getMainPageVideos)

mainPageVideosRouter.route('/all')
    .get(getAllVideos)

export default mainPageVideosRouter;
