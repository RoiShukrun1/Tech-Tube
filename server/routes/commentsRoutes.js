import express from 'express';

const router = express.Router();

router.get('/', (req, res) => {
  res.send('Comments route');
  console.log('Comments route');
});

export default router;

