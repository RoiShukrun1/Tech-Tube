import React, { useState } from 'react';
import './registration.css'
import logo from '../images/Logo.png'
import { ReactComponent as ManIcon } from '../images/user.svg'; 
import { ReactComponent as LockIcon } from '../images/lock.svg'; 
import { ReactComponent as ImageIcon } from '../images/addimage.svg'; // Replace '../images/image.svg' with the path to your image icon
import { ReactComponent as XIcon } from '../images/X.svg'; // Replace '../images/image.svg' with the path to your image icon
import { ReactComponent as VIcon } from '../images/V.svg'; // Replace '../images/image.svg' with the path to your image icon
import { Link, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { useEffect } from 'react';
import { ThemeContext } from '../contexts/themeContext';
import darkLogo from '../images/darkmodelogo.png';
import defultProfile from '../images/profile.png';
import axios from 'axios';


export const Registration = () => {
    const [image, setImage] = useState(null);
    const { darkMode } = useContext(ThemeContext);
    const [base64Image, setBase64Image] = useState(null);

    useEffect(() => {
        fetch(defultProfile)
          .then((response) => response.blob())
          .then((blob) => {
            const reader = new FileReader();
            reader.onloadend = () => {
                setBase64Image(reader.result);
            };
            reader.readAsDataURL(blob);
          })
          .catch((error) => console.error('Error converting image to base64:', error));
      }, []);

    const handleImageUpload = (event) => {
        setImage(URL.createObjectURL(event.target.files[0]));
        const file = event.target.files[0];
          const reader = new FileReader();
          reader.onloadend = () => {
              setBase64Image(reader.result); // Store the Base64 string
          };
          reader.readAsDataURL(file);
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
    const navigate = useNavigate();
    // Function to handle the nickname input
    const handleNickname = (event) => {
        const newNickname = event.target.value;
        if (nickNameValidation(newNickname)){
            setNicknameMassage("");
            setNicknameImage(false);
        }
        else {  
            setNicknameMassage("Nickname must contain only letters and cannot be less then 2 characters.");
            setNicknameImage(true);
        }
    }
    // Function to validate the nickname
    const handleUsername = (event) => {
        const newUsername = event.target.value;
        if (nameValidation(newUsername)){
            setUsernameMassage("");
            setUsernameImage(false);
        }
        else {
            setUsernameMassage("Username must contain only letters and cannot be less then 2 characters.");
            setUsernameImage(true);
        }
    }
    // Function to validate the username
    const nameValidation = (name) => {
        const namePattern = /^[a-zA-Z]{2,}$/;
        if (!namePattern.test(name)) {
            return false;
          }
        else 
          return true;
    } 

    // Function to validate the username
    const nickNameValidation = (name) => {
        const namePattern = /^[a-zA-Z0-9]{2,}$/; // Updated regex to include digits
        return namePattern.test(name);
    }


    // Function to handle the password input
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
    // Function to validate the password
    const passwordValidtion = (password) => {
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(password)) {
            return false;
          }
        else 
          return true;
    }
    // Function to handle the password confirmation input
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
    // Function to confirm the password
    const passwordConfrmtion = (password, confirmPassword) => {
        if (password === confirmPassword){
            return true;
        }
        else {
            return false;
        }
    }
    // Function to handle the submit button
    const handleSubmit = async (event) => {
        event.preventDefault();
        if (nicknameImage || usernameImage || PasswordImage || ConfirmPasswordImage) {
            alert("Please fill all the fields correctly");
        } else {
            const newData = {
                username: document.getElementById("username").value,
                nickname: document.getElementById("nickname").value,
                password: document.getElementById("password").value,
                subscriptions: [],
                image: base64Image,
                banner: null
            };
            try {
                await axios.post('http://localhost/api/users/register', newData); // Connect to your backend API
                alert("Registration successful");
                navigate('/login'); // Navigate to the login page
            } catch (error) {
                if (error.response && error.response.data && error.response.data.message) {
                    alert(`Registration failed: ${error.response.data.message}`);
                } else {
                    alert("Registration failed. Please try again.");
                }
                console.error(error);
            }
        }
    }

  return (
    <div className={'registration-wrapper'+ (darkMode ? '-dark' : '')}>
    <div className='registration-container'>
        <img className='login-img' src={darkMode ? darkLogo : logo} alt="Logo" /> {/* Conditional logo */}
        <form className='registration-form'>
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
            <p>Already have an user? <Link to="/login">Log-in</Link></p>
            <p>Continue as guest:   <Link to="/mainPage">Hompage</Link></p>    
        </div>
        </form>
    </div>
    </div>
  )
}

export default Registration;