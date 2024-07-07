import jwt from 'jsonwebtoken';

const generateToken = (user) => {
  const token = jwt.sign({ id: user._id, username: user.username }, process.env.JWT_SECRET, { expiresIn: '1h' });
  return token;
};

const verifyToken = (token) => {
  return jwt.verify(token, process.env.JWT_SECRET);
};

export { generateToken, verifyToken };
