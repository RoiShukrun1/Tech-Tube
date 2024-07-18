import mongoose from 'mongoose'; // Use import for mongoose

const deletedUserSchema = new mongoose.Schema({
    username: { type: String, unique: true }
  });

// Export the User model based on the userSchema
export default mongoose.model('DeletedUser', deletedUserSchema);