import express from 'express';
import { getVideo } from '../controllers/videosController.js';
import { deleteVideo } from '../controllers/videosController.js';
import { updateVideo } from '../controllers/videosController.js';

const videosRouter = express.Router();

videosRouter.route('/:pid')
  .get(getVideo)
  .delete(deleteVideo)
  .patch(updateVideo);

export default videosRouter;
