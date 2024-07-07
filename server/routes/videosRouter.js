import express from 'express';
import { getVideo } from '../controllers/videosController.js';
import { deleteVideo } from '../controllers/videosController.js';
import { updateVideo } from '../controllers/videosController.js';
import { getPublisherVideos } from '../controllers/videosController.js';
import commentsRouter from './commentsRouter.js';

const videosRouter = express.Router({ mergeParams: true });

videosRouter.route('/:pid')
    .get(getVideo)
    .delete(deleteVideo)
    .patch(updateVideo);

videosRouter.route('/')
    .get(getPublisherVideos)

videosRouter.use('/:pid/comments', commentsRouter);

export default videosRouter;
