import express from 'express';
import { getVideo, deleteVideo, updateVideo, 
    getPublisherVideos, getRelatedVideos } from '../controllers/videosController.js';
import commentsRouter from './commentsRouter.js';

const videosRouter = express.Router({ mergeParams: true });

videosRouter.route('/:pid')
    .get(getVideo)
    .delete(deleteVideo)
    .patch(updateVideo);

videosRouter.route('/:pid/relatedvideos')
    .get(getRelatedVideos)

videosRouter.route('/')
    .get(getPublisherVideos)

videosRouter.use('/:pid/comments', commentsRouter);

export default videosRouter;
