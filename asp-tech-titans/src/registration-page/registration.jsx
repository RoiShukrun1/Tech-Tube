import React, { useState } from 'react';
import './registration.css'
import logo from '../images/Logo.png'
import { ReactComponent as ManIcon } from '../images/user.svg'; 
import { ReactComponent as LockIcon } from '../images/lock.svg'; 
import { ReactComponent as ImageIcon } from '../images/addimage.svg'; // Replace '../images/image.svg' with the path to your image icon
import { ReactComponent as XIcon } from '../images/X.svg'; // Replace '../images/image.svg' with the path to your image icon
import { ReactComponent as VIcon } from '../images/V.svg'; // Replace '../images/image.svg' with the path to your image icon

export const Registration = () => {
    const [image, setImage] = useState(null);
    const handleImageUpload = (event) => {
      setImage(URL.createObjectURL(event.target.files[0]));
    }
    const [PasswordImage, setPasswordImage] = useState(true);
    const [ConfirmPasswordImage, setConfirmPasswordImage] = useState(true);
    const [passwordError, setPasswordMassage] = useState(null);
    const [passwordConfermtionError, setPassworConfermtionMassage] = useState(null);
    const [password, setPassword] = useState(null);
    const [confirmPassword, setConfirmPassword] = useState(null);
    const [nicknameMassage, setNicknameMassage] = useState(null);
    const [usernameMassage, setUsernameMassage] = useState(null);
    const [nicknameImage, setNicknameImage] = useState(true);
    const [usernameImage, setUsernameImage] = useState(true);

    const handleNickname = (event) => {
        const newNickname = event.target.value;
        if (nameValidation(newNickname)){
            setNicknameMassage("");
            setNicknameImage(false);
        }
        else {  
            setNicknameMassage("Nickname must contain only letters");
            setNicknameImage(true);
        }
    }

    const handleUsername = (event) => {
        const newUsername = event.target.value;
        if (nameValidation(newUsername)){
            setUsernameMassage("");
            setUsernameImage(false);
        }
        else {
            setUsernameMassage("Username must contain only letters");
            setUsernameImage(true);
        }
    }

    const nameValidation = (name) => {
        const namePattern = /^[a-zA-Z]+$/;
        if (!namePattern.test(name)) {
            return false;
          }
        else 
          return true;
    } 


    const handlePasswordValidation = (event) => {
        const newPassword = event.target.value;
        setPassword(newPassword);
        if (passwordValidtion(newPassword)){
            setPasswordMassage("");
            setPasswordImage(false);
        }
        else {
            setPasswordMassage("Password must contain at least 8 characters, including uppercase, lowercase, numbers and special characters.");
            setPasswordImage(true);
        }
        if (passwordConfrmtion(confirmPassword,newPassword)){
            setPassworConfermtionMassage("");
            setConfirmPasswordImage(false);
        }
        else {
            setPassworConfermtionMassage("Password and Confirm Password must be the same");
            setConfirmPasswordImage(true);

        }
    }

    const passwordValidtion = (password) => {
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(password)) {
            return false;
          }
        else 
          return true;
    }

    const handlePasswordConfirmtion = (event) => {
        const newConfermtionPassword = event.target.value;
        setConfirmPassword(newConfermtionPassword);
        if (passwordConfrmtion(newConfermtionPassword,password)){
            setPassworConfermtionMassage("");
            setConfirmPasswordImage(false);
        }
        else {
            setPassworConfermtionMassage("Password and Confirm Password must be the same");
            setConfirmPasswordImage(true);
        }
    }
    
    const passwordConfrmtion = (password, confirmPassword) => {
        if (password === confirmPassword){
            return true;
        }
        else {
            return false;
        }
    }
    
    const handleSubmit = (event) => {
        if(nicknameImage || usernameImage || PasswordImage || ConfirmPasswordImage){
            alert("Please fill all the fields correctly");
        }
        else {
        const newData = {
            nickname: document.getElementById("nickname").value,
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
            image: image // Assuming you have the image state defined somewhere
        };
        // Retrieve existing data from storage or initialize an empty array
        const existingData = JSON.parse(sessionStorage.getItem('formData')) || [];
        // Add the new data to the existing array
        existingData.push(newData);
        // Save the updated array back to storage
        sessionStorage.setItem('formData', JSON.stringify(existingData));
        alert("Registration successful");
        }
    }
  return (
    <div className='registration-container'>
        <img src={logo} alt="Logo" />
        <form>
            <div>{image && <img src={image} alt="User uploaded "style={{width: '150px', height: '100px'}} />}</div>
        <div>
            <ManIcon className="Icon" /> 
            <input type="text" id="nickname" name="nickname" placeholder="Nickname" onChange={handleNickname}/>
            {nicknameImage ?  <XIcon className="Icon"/>  : <VIcon className="Icon" />}
        </div>
        <div className='error'>{nicknameMassage}</div>
        <div>
            <ManIcon className="Icon" /> 
            <input type="text" id="username" name="username" placeholder="Username" onChange={handleUsername} />
            {usernameImage ?  <XIcon className="Icon"/>  : <VIcon className="Icon" />}
        </div>
        <div className='error'>{usernameMassage}</div>
        <div className="passwordContainer">
            <LockIcon className="Icon" /> 
            <input type="password" id="password" name="password" placeholder="Password" onChange={handlePasswordValidation} />
            {PasswordImage ? <XIcon className="Icon"/> : <VIcon className="Icon" />}
        </div>
        <div className='error'>{passwordError}</div>
        <div>
            <LockIcon className="Icon" /> 
            <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm Password" onChange={handlePasswordConfirmtion} />
            {ConfirmPasswordImage ?  <XIcon className="Icon"/>  : <VIcon className="Icon" />}
        <div className='error'>
            {passwordConfermtionError}
        </div>
        </div>
        <div>
            <label htmlFor="profile-image">
                <ImageIcon className="ImageIcon" />
            </label>
            <input type="file" onChange={handleImageUpload}  id="profile-image" name="profile-image" accept="image/*" style={{display: 'none'}} />
        </div>
        <div>
            <p>Upload profile picture</p>
        </div>
        <input type="submit" value="Register" onClick={handleSubmit} />
        <div>
            <p>Already have an account? <a href="/login">Login</a></p>
        </div>
        </form>
    </div>
  )
}

export default Registration;