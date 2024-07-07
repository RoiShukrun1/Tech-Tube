import React from 'react';
import './subscribeButton.css';
import axios from 'axios';

function subscribeButton({ setUsers, currentUser, publisher }) {

    // Variable to check if the current user is subscribed to the publisher
    var isSubscribed = false;

    // Check if the current user is subscribed to the publisher
    if (currentUser && currentUser.subscriptions) {
        isSubscribed = currentUser.subscriptions.includes(publisher);
    }

    // Function to switch between subscribe and unsubscribe buttons
    const switchButtons = () => {
        if (!currentUser) {
            alert('You must login to press subscribe!');
            return;
        }
        if (isSubscribed) {
            removeSubscription();
        } else {
            addSubscription();
        }
    }

    // Function to remove subscription
    const removeSubscription = () => {
        setUsers(prevUsers => {
            const updatedUsers = [...prevUsers];
            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions = currentUser.subscriptions.filter(sub => sub !== publisher);
            }
            return updatedUsers;
        });
    };

    // Function to add subscription
    const addSubscription = async () => {
        setUsers(prevUsers => {
            const updatedUsers = [...prevUsers];
            if (currentUser && currentUser.subscriptions) {
                currentUser.subscriptions.push(publisher);
            }
            return updatedUsers;
        });

        // const path = 'http://localhost/api/users/' + currentUser.username;
        // const result = await axios.patch(path, {subscription : ['Aviel Segev', 'ROI GOLAN']});
    };

    return (
        <button type="button" onClick={switchButtons}
            className={isSubscribed ? "btn btn-light unsubscribe-button" : "btn btn-dark subscribe-button"}>
            {isSubscribed ? "unsubscribe" : "subscribe"}
        </button>
    );
}

export default subscribeButton;
