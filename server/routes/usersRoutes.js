import express from 'express';
import { getUser, deleteUser, registerUser, getSubscribers, updateAccount, saveBanner } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module
import videosRouter from './videosRouter.js';

const router = express.Router();

router.post('/register', uploadFile, registerUser);

router.route('/:id')
  .get(getUser)
  .delete(deleteUser)
  .patch(updateAccount);

router.route('/:id/subscribers')
  .get(getSubscribers);

router.route('/:id/banner')
  .patch(saveBanner);

router.use('/:id/videos', videosRouter);

export default router;
