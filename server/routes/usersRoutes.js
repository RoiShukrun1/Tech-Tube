import express from 'express';
import { getAccount } from '../controllers/usersController.js';
import { deleteAccount } from '../controllers/usersController.js';
import { registerAccount } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module


const router = express.Router();

router.post('/register', uploadFile, registerAccount);

router.route('/:id')
  .get(getAccount)
  .delete(deleteAccount); 

export default router;
