import React from 'react';
import './subscribeButton.css';
import axios from 'axios';
import { useState, useEffect } from 'react';

function SubscribeButton({ currentUser, publisher }) {

    const [isSubscribed, setIsSubscribed] = useState(false);
    const [publisherObject, setPublisherObject] = useState(null);

    // Function to get user data
    const updatePublisherObject = async (publisher) => {
        const path = "http://localhost/api/users/" + publisher;
        const response = await fetch(path);
        const user = await response.json();
        setPublisherObject(user);
    };

    // Effect to get the subscription status
    useEffect(() => {
        const updateSubscribeStatus = async () => {
            if (currentUser === null) return;

            const path = 'http://localhost/api/users/' + currentUser.username;

            // Get the user data
            const user = (await axios.get(path)).data;

            if (user && user.subscriptions) {
                setIsSubscribed(user.subscriptions.includes(publisher));
            }
        };
        updateSubscribeStatus();
    }, [currentUser]);

    // Effect to get the publisher object
    useEffect(() => {
        updatePublisherObject(publisher);
    }, []);

    // Function to switch between subscribe and unsubscribe buttons
    const switchButtons = async () => {
        if (!currentUser) {
            alert('You must login to press subscribe!');
            return;
        }
        if (!publisherObject) {
            alert('Publisher is not available!');
            return;
        }
        if (currentUser.username === publisher) {
            alert('You cannot subscribe to yourself!');
            return;
        }
        if (isSubscribed) {
            removeSubscription();
        } else {
            addSubscription();
        }
    }

    // Function to remove subscription
    const removeSubscription = async () => {

        setIsSubscribed(!isSubscribed);

        // Path to the user data
        const path = 'http://localhost/api/users/' + currentUser.username;

        // Get the user data
        const user = (await axios.get(path)).data;

        // remove the publisher from the user subscriptions
        user.subscriptions = user.subscriptions.filter(sub => sub !== publisher);

        // Update the user subscriptions on the server
        await axios.patch(path, { subscriptions: user.subscriptions });
    };

    // Function to add subscription
    const addSubscription = async () => {

        setIsSubscribed(!isSubscribed);

        // Path to the user data
        const path = 'http://localhost/api/users/' + currentUser.username;

        // Get the user data
        const user = (await axios.get(path)).data;

        if (!user.subscriptions.includes(publisher)) {
            // Add the publisher to the user subscriptions
            user.subscriptions.push(publisher);

            // Update the user subscriptions on the server
            await axios.patch(path, { subscriptions: user.subscriptions });
        }

    };

    return (
        <button type="button" onClick={switchButtons}
            className={isSubscribed ? "btn btn-light unsubscribe-button" : "btn btn-dark subscribe-button"}>
            {isSubscribed ? "unsubscribe" : "subscribe"}
        </button>
    );
}

export default SubscribeButton;
