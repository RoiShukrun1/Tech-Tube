import express from 'express';
import { getAccount } from '../controllers/usersController.js';
import { deleteAccount } from '../controllers/usersController.js';
import { registerAccount } from '../controllers/usersController.js';
import { updateAccount } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module
import videosRouter from './videosRouter.js';

const router = express.Router();

router.post('/register', uploadFile, registerAccount);

router.route('/:id')
  .get(getAccount)
  .delete(deleteAccount)
  .patch(updateAccount);

router.use('/:id/videos', videosRouter);

export default router;
