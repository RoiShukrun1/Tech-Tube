import express from 'express';
import { getUser, deleteUser, registerUser, getSubscribers, updateAccount, saveBanner } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module
import videosRouter from './videosRouter.js';
import { authenticate } from '../controllers/tokenController.js';

const router = express.Router();

router.post('/register', uploadFile, registerUser);

router.route('/:id')
  .get(getUser)
  .delete(authenticate, deleteUser)
  .patch(authenticate, updateAccount);

router.route('/:id/subscribers')
  .get(getSubscribers);

router.route('/:id/banner')
  .patch(authenticate, saveBanner);

router.use('/:id/videos', videosRouter);

export default router;
