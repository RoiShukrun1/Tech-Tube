import express from 'express';
import { getUser } from '../controllers/usersController.js';
import { deleteUser } from '../controllers/usersController.js';
import { registerUser } from '../controllers/usersController.js';
import uploadFile from '../multerConfig.js'; // Import uploadFile from the new module


const router = express.Router();

router.post('/register', uploadFile, registerUser);

router.route('/:id')
  .get(getUser)
  .delete(deleteUser); 

export default router;
