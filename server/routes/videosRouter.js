import express from 'express';
import { getVideo, deleteVideo, updateVideo, 
    getPublisherVideos, getRelatedVideos, 
    getCatagoryVideos} from '../controllers/videosController.js';
import commentsRouter from './commentsRouter.js';
import { authenticate, optionalAuthenticate } from '../controllers/tokenController.js';
const videosRouter = express.Router({ mergeParams: true });

videosRouter.route('/:pid')
    .get(getVideo)
    .delete(authenticate, deleteVideo)
    .patch(authenticate, updateVideo);

videosRouter.route('/:pid/relatedvideos')
    .get(optionalAuthenticate, getRelatedVideos)

videosRouter.route('/:pid/catagoryvideos')
    .get(getCatagoryVideos)

videosRouter.route('/')
    .get(getPublisherVideos)

videosRouter.use('/:pid/comments', commentsRouter);

export default videosRouter;
