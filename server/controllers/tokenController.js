import { generateToken, verifyToken } from '../services/tokenServices.js';
import User from '../models/usersModel.js';

const login = async (req, res) => {
  const { username, password } = req.body;

  try {
    const user = await User.findOne({ username });
    if (user && user.password === password) {
      const token = generateToken(user);
      res.cookie('token', token, { httpOnly: true});
      res.json({ message: 'Login successful',token: token, user:{ username: user.username, image: user.image }});
    } else {
      res.status(401).json({ message: 'Invalid username or password' });
    }
  } catch (err) {
    res.status(500).json({ message: 'Server error' });
  }
};

const authenticate = (req, res, next) => {
  const token = req.cookies.token;
  if (!token) return res.status(403).json({ message: 'No token provided' });

  try {
    const decoded = verifyToken(token);
    req.userId = decoded.id;
    next();
  } catch (err) {
    res.status(500).json({ message: 'Failed to authenticate token' });
  }
};

const checkAuth = async (req, res) => {
  const token = req.cookies.token;
  if (!token) return res.status(200).json({ isAuthenticated: false });

  try {
    const decoded = verifyToken(token);
    const user = await User.findById(decoded.id);
    res.status(200).json({ isAuthenticated: true, user: { username: user.username, image: user.image } });
  } catch (err) {
    res.status(200).json({ isAuthenticated: false });
  }
};

const logout = (req, res) => {
  res.clearCookie('token');
  res.json({ message: 'Logout successful' });
};

export { login, authenticate, checkAuth, logout };
