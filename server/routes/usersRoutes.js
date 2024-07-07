import express from 'express';
import { getUser } from '../controllers/usersController.js';
import { deleteUser } from '../controllers/usersController.js';
import { registerUser } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module
import videosRouter from './videosRouter.js';

const router = express.Router();

router.post('/register', uploadFile, registerUser);

router.route('/:id')
  .get(getUser)
  .delete(deleteUser);

router.use('/:id/videos', videosRouter);

export default router;
